package com.google.android.exoplayer2.source.dash;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.source.dash.manifest.ServiceDescriptionElement;
import com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.SntpClient;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import com.google.common.math.LongMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class DashMediaSource extends BaseMediaSource {
    public static final long DEFAULT_FALLBACK_TARGET_LIVE_OFFSET_MS = 30000;

    @Deprecated
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_MS = 30000;
    public static final String DEFAULT_MEDIA_ID = "DashMediaSource";
    private static final long DEFAULT_NOTIFY_MANIFEST_INTERVAL_MS = 5000;
    private static final long MIN_LIVE_DEFAULT_START_POSITION_US = 5000000;
    private static final String TAG = "DashMediaSource";
    private final BaseUrlExclusionList baseUrlExclusionList;
    private final DashChunkSource.Factory chunkSourceFactory;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private DataSource dataSource;
    private final DrmSessionManager drmSessionManager;
    private long elapsedRealtimeOffsetMs;
    private long expiredManifestPublishTimeUs;
    private final long fallbackTargetLiveOffsetMs;
    private int firstPeriodId;
    private Handler handler;
    private Uri initialManifestUri;
    private MediaItem.LiveConfiguration liveConfiguration;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private Loader loader;
    private DashManifest manifest;
    private final ManifestCallback manifestCallback;
    private final DataSource.Factory manifestDataSourceFactory;
    private final MediaSourceEventListener.EventDispatcher manifestEventDispatcher;
    private IOException manifestFatalError;
    private long manifestLoadEndTimestampMs;
    private final LoaderErrorThrower manifestLoadErrorThrower;
    private boolean manifestLoadPending;
    private long manifestLoadStartTimestampMs;
    private final ParsingLoadable.Parser<? extends DashManifest> manifestParser;
    private Uri manifestUri;
    private final Object manifestUriLock;
    private final MediaItem mediaItem;

    @Nullable
    private TransferListener mediaTransferListener;
    private final SparseArray<DashMediaPeriod> periodsById;
    private final PlayerEmsgHandler.PlayerEmsgCallback playerEmsgCallback;
    private final Runnable refreshManifestRunnable;
    private final boolean sideloadedManifest;
    private final Runnable simulateManifestRefreshRunnable;
    private int staleManifestReloadAttempt;

    public static final class DashTimeline extends Timeline {
        private final long elapsedRealtimeEpochOffsetMs;
        private final int firstPeriodId;

        @Nullable
        private final MediaItem.LiveConfiguration liveConfiguration;
        private final DashManifest manifest;
        private final MediaItem mediaItem;
        private final long offsetInFirstPeriodUs;
        private final long presentationStartTimeMs;
        private final long windowDefaultStartPositionUs;
        private final long windowDurationUs;
        private final long windowStartTimeMs;

        public DashTimeline(long j, long j2, long j3, int i, long j4, long j5, long j6, DashManifest dashManifest, MediaItem mediaItem, @Nullable MediaItem.LiveConfiguration liveConfiguration) {
            Assertions.checkState(dashManifest.dynamic == (liveConfiguration != null));
            this.presentationStartTimeMs = j;
            this.windowStartTimeMs = j2;
            this.elapsedRealtimeEpochOffsetMs = j3;
            this.firstPeriodId = i;
            this.offsetInFirstPeriodUs = j4;
            this.windowDurationUs = j5;
            this.windowDefaultStartPositionUs = j6;
            this.manifest = dashManifest;
            this.mediaItem = mediaItem;
            this.liveConfiguration = liveConfiguration;
        }

        private long getAdjustedWindowDefaultStartPositionUs(long j) {
            DashSegmentIndex index;
            long j2 = this.windowDefaultStartPositionUs;
            if (!isMovingLiveWindow(this.manifest)) {
                return j2;
            }
            if (j > 0) {
                j2 += j;
                if (j2 > this.windowDurationUs) {
                    return C.TIME_UNSET;
                }
            }
            long j3 = this.offsetInFirstPeriodUs + j2;
            long periodDurationUs = this.manifest.getPeriodDurationUs(0);
            int i = 0;
            while (i < this.manifest.getPeriodCount() - 1 && j3 >= periodDurationUs) {
                j3 -= periodDurationUs;
                i++;
                periodDurationUs = this.manifest.getPeriodDurationUs(i);
            }
            Period period = this.manifest.getPeriod(i);
            int adaptationSetIndex = period.getAdaptationSetIndex(2);
            return (adaptationSetIndex == -1 || (index = period.adaptationSets.get(adaptationSetIndex).representations.get(0).getIndex()) == null || index.getSegmentCount(periodDurationUs) == 0) ? j2 : (index.getTimeUs(index.getSegmentNum(j3, periodDurationUs)) + j2) - j3;
        }

        private static boolean isMovingLiveWindow(DashManifest dashManifest) {
            return dashManifest.dynamic && dashManifest.minUpdatePeriodMs != C.TIME_UNSET && dashManifest.durationMs == C.TIME_UNSET;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object obj) {
            int iIntValue;
            if ((obj instanceof Integer) && (iIntValue = ((Integer) obj).intValue() - this.firstPeriodId) >= 0 && iIntValue < getPeriodCount()) {
                return iIntValue;
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            Assertions.checkIndex(i, 0, getPeriodCount());
            return period.set(z ? this.manifest.getPeriod(i).id : null, z ? Integer.valueOf(this.firstPeriodId + i) : null, 0, this.manifest.getPeriodDurationUs(i), Util.msToUs(this.manifest.getPeriod(i).startMs - this.manifest.getPeriod(0).startMs) - this.offsetInFirstPeriodUs);
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.manifest.getPeriodCount();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Object getUidOfPeriod(int i) {
            Assertions.checkIndex(i, 0, getPeriodCount());
            return Integer.valueOf(this.firstPeriodId + i);
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            Assertions.checkIndex(i, 0, 1);
            long adjustedWindowDefaultStartPositionUs = getAdjustedWindowDefaultStartPositionUs(j);
            Object obj = Timeline.Window.SINGLE_WINDOW_UID;
            MediaItem mediaItem = this.mediaItem;
            DashManifest dashManifest = this.manifest;
            return window.set(obj, mediaItem, dashManifest, this.presentationStartTimeMs, this.windowStartTimeMs, this.elapsedRealtimeEpochOffsetMs, true, isMovingLiveWindow(dashManifest), this.liveConfiguration, adjustedWindowDefaultStartPositionUs, this.windowDurationUs, 0, getPeriodCount() - 1, this.offsetInFirstPeriodUs);
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return 1;
        }
    }

    public final class DefaultPlayerEmsgCallback implements PlayerEmsgHandler.PlayerEmsgCallback {
        private DefaultPlayerEmsgCallback() {
        }

        @Override // com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerEmsgCallback
        public void onDashManifestPublishTimeExpired(long j) {
            DashMediaSource.this.onDashManifestPublishTimeExpired(j);
        }

        @Override // com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerEmsgCallback
        public void onDashManifestRefreshRequested() {
            DashMediaSource.this.onDashManifestRefreshRequested();
        }
    }

    public static final class Factory implements MediaSourceFactory {
        private final DashChunkSource.Factory chunkSourceFactory;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private DrmSessionManagerProvider drmSessionManagerProvider;
        private long fallbackTargetLiveOffsetMs;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;

        @Nullable
        private final DataSource.Factory manifestDataSourceFactory;

        @Nullable
        private ParsingLoadable.Parser<? extends DashManifest> manifestParser;

        public Factory(DataSource.Factory factory) {
            this(new DefaultDashChunkSource.Factory(factory), factory);
        }

        @Override // com.google.android.exoplayer2.source.MediaSource.Factory
        public int[] getSupportedTypes() {
            return new int[]{0};
        }

        @CanIgnoreReturnValue
        public Factory setCompositeSequenceableLoaderFactory(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory) {
            this.compositeSequenceableLoaderFactory = (CompositeSequenceableLoaderFactory) Assertions.checkNotNull(compositeSequenceableLoaderFactory, "DashMediaSource.Factory#setCompositeSequenceableLoaderFactory no longer handles null by instantiating a new DefaultCompositeSequenceableLoaderFactory. Explicitly construct and pass an instance in order to retain the old behavior.");
            return this;
        }

        @CanIgnoreReturnValue
        public Factory setFallbackTargetLiveOffsetMs(long j) {
            this.fallbackTargetLiveOffsetMs = j;
            return this;
        }

        @CanIgnoreReturnValue
        public Factory setManifestParser(@Nullable ParsingLoadable.Parser<? extends DashManifest> parser) {
            this.manifestParser = parser;
            return this;
        }

        public Factory(DashChunkSource.Factory factory, @Nullable DataSource.Factory factory2) {
            this.chunkSourceFactory = (DashChunkSource.Factory) Assertions.checkNotNull(factory);
            this.manifestDataSourceFactory = factory2;
            this.drmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.fallbackTargetLiveOffsetMs = 30000L;
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
        }

        public DashMediaSource createMediaSource(DashManifest dashManifest) {
            return createMediaSource(dashManifest, new MediaItem.Builder().setUri(Uri.EMPTY).setMediaId("DashMediaSource").setMimeType(MimeTypes.APPLICATION_MPD).build());
        }

        @Override // com.google.android.exoplayer2.source.MediaSource.Factory
        @CanIgnoreReturnValue
        public Factory setDrmSessionManagerProvider(DrmSessionManagerProvider drmSessionManagerProvider) {
            this.drmSessionManagerProvider = (DrmSessionManagerProvider) Assertions.checkNotNull(drmSessionManagerProvider, "MediaSource.Factory#setDrmSessionManagerProvider no longer handles null by instantiating a new DefaultDrmSessionManagerProvider. Explicitly construct and pass an instance in order to retain the old behavior.");
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSource.Factory
        @CanIgnoreReturnValue
        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            this.loadErrorHandlingPolicy = (LoadErrorHandlingPolicy) Assertions.checkNotNull(loadErrorHandlingPolicy, "MediaSource.Factory#setLoadErrorHandlingPolicy no longer handles null by instantiating a new DefaultLoadErrorHandlingPolicy. Explicitly construct and pass an instance in order to retain the old behavior.");
            return this;
        }

        public DashMediaSource createMediaSource(DashManifest dashManifest, MediaItem mediaItem) {
            Assertions.checkArgument(!dashManifest.dynamic);
            MediaItem.Builder mimeType = mediaItem.buildUpon().setMimeType(MimeTypes.APPLICATION_MPD);
            if (mediaItem.localConfiguration == null) {
                mimeType.setUri(Uri.EMPTY);
            }
            MediaItem mediaItemBuild = mimeType.build();
            return new DashMediaSource(mediaItemBuild, dashManifest, null, null, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.drmSessionManagerProvider.get(mediaItemBuild), this.loadErrorHandlingPolicy, this.fallbackTargetLiveOffsetMs);
        }

        @Override // com.google.android.exoplayer2.source.MediaSource.Factory
        public DashMediaSource createMediaSource(MediaItem mediaItem) {
            Assertions.checkNotNull(mediaItem.localConfiguration);
            ParsingLoadable.Parser dashManifestParser = this.manifestParser;
            if (dashManifestParser == null) {
                dashManifestParser = new DashManifestParser();
            }
            List<StreamKey> list = mediaItem.localConfiguration.streamKeys;
            return new DashMediaSource(mediaItem, null, this.manifestDataSourceFactory, !list.isEmpty() ? new FilteringManifestParser(dashManifestParser, list) : dashManifestParser, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.drmSessionManagerProvider.get(mediaItem), this.loadErrorHandlingPolicy, this.fallbackTargetLiveOffsetMs);
        }
    }

    public static final class Iso8601Parser implements ParsingLoadable.Parser<Long> {
        private static final Pattern TIMESTAMP_WITH_TIMEZONE_PATTERN = Pattern.compile("(.+?)(Z|((\\+|-|−)(\\d\\d)(:?(\\d\\d))?))");

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            String line = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8)).readLine();
            try {
                Matcher matcher = TIMESTAMP_WITH_TIMEZONE_PATTERN.matcher(line);
                if (!matcher.matches()) {
                    throw ParserException.createForMalformedManifest("Couldn't parse timestamp: " + line, null);
                }
                String strGroup = matcher.group(1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                long time = simpleDateFormat.parse(strGroup).getTime();
                if (!"Z".equals(matcher.group(2))) {
                    long j = "+".equals(matcher.group(4)) ? 1L : -1L;
                    long j2 = Long.parseLong(matcher.group(5));
                    String strGroup2 = matcher.group(7);
                    time -= ((((j2 * 60) + (TextUtils.isEmpty(strGroup2) ? 0L : Long.parseLong(strGroup2))) * 60) * 1000) * j;
                }
                return Long.valueOf(time);
            } catch (ParseException e) {
                throw ParserException.createForMalformedManifest(null, e);
            }
        }
    }

    public final class ManifestCallback implements Loader.Callback<ParsingLoadable<DashManifest>> {
        private ManifestCallback() {
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCanceled(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.onLoadCanceled(parsingLoadable, j, j2);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCompleted(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2) {
            DashMediaSource.this.onManifestLoadCompleted(parsingLoadable, j, j2);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException, int i) {
            return DashMediaSource.this.onManifestLoadError(parsingLoadable, j, j2, iOException, i);
        }
    }

    public final class UtcTimestampCallback implements Loader.Callback<ParsingLoadable<Long>> {
        private UtcTimestampCallback() {
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCanceled(ParsingLoadable<Long> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.onLoadCanceled(parsingLoadable, j, j2);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCompleted(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
            DashMediaSource.this.onUtcTimestampLoadCompleted(parsingLoadable, j, j2);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException, int i) {
            return DashMediaSource.this.onUtcTimestampLoadError(parsingLoadable, j, j2, iOException);
        }
    }

    public static final class XsDateTimeParser implements ParsingLoadable.Parser<Long> {
        private XsDateTimeParser() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            return Long.valueOf(Util.parseXsDateTime(new BufferedReader(new InputStreamReader(inputStream)).readLine()));
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.dash");
    }

    private static long getAvailableEndTimeInManifestUs(Period period, long j, long j2) {
        long jMsToUs = Util.msToUs(period.startMs);
        boolean zHasVideoOrAudioAdaptationSets = hasVideoOrAudioAdaptationSets(period);
        long jMin = Long.MAX_VALUE;
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            AdaptationSet adaptationSet = period.adaptationSets.get(i);
            List<Representation> list = adaptationSet.representations;
            int i2 = adaptationSet.type;
            boolean z = (i2 == 1 || i2 == 2) ? false : true;
            if ((!zHasVideoOrAudioAdaptationSets || !z) && !list.isEmpty()) {
                DashSegmentIndex index = list.get(0).getIndex();
                if (index == null) {
                    return jMsToUs + j;
                }
                long availableSegmentCount = index.getAvailableSegmentCount(j, j2);
                if (availableSegmentCount == 0) {
                    return jMsToUs;
                }
                long firstAvailableSegmentNum = (index.getFirstAvailableSegmentNum(j, j2) + availableSegmentCount) - 1;
                jMin = Math.min(jMin, index.getDurationUs(firstAvailableSegmentNum, j) + index.getTimeUs(firstAvailableSegmentNum) + jMsToUs);
            }
        }
        return jMin;
    }

    private static long getAvailableStartTimeInManifestUs(Period period, long j, long j2) {
        long jMsToUs = Util.msToUs(period.startMs);
        boolean zHasVideoOrAudioAdaptationSets = hasVideoOrAudioAdaptationSets(period);
        long jMax = jMsToUs;
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            AdaptationSet adaptationSet = period.adaptationSets.get(i);
            List<Representation> list = adaptationSet.representations;
            int i2 = adaptationSet.type;
            boolean z = (i2 == 1 || i2 == 2) ? false : true;
            if ((!zHasVideoOrAudioAdaptationSets || !z) && !list.isEmpty()) {
                DashSegmentIndex index = list.get(0).getIndex();
                if (index == null || index.getAvailableSegmentCount(j, j2) == 0) {
                    return jMsToUs;
                }
                jMax = Math.max(jMax, index.getTimeUs(index.getFirstAvailableSegmentNum(j, j2)) + jMsToUs);
            }
        }
        return jMax;
    }

    private static long getIntervalUntilNextManifestRefreshMs(DashManifest dashManifest, long j) {
        DashSegmentIndex index;
        int periodCount = dashManifest.getPeriodCount() - 1;
        Period period = dashManifest.getPeriod(periodCount);
        long jMsToUs = Util.msToUs(period.startMs);
        long periodDurationUs = dashManifest.getPeriodDurationUs(periodCount);
        long jMsToUs2 = Util.msToUs(j);
        long jMsToUs3 = Util.msToUs(dashManifest.availabilityStartTimeMs);
        long jMsToUs4 = Util.msToUs(5000L);
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            List<Representation> list = period.adaptationSets.get(i).representations;
            if (!list.isEmpty() && (index = list.get(0).getIndex()) != null) {
                long nextSegmentAvailableTimeUs = (index.getNextSegmentAvailableTimeUs(periodDurationUs, jMsToUs2) + (jMsToUs3 + jMsToUs)) - jMsToUs2;
                if (nextSegmentAvailableTimeUs < jMsToUs4 - 100000 || (nextSegmentAvailableTimeUs > jMsToUs4 && nextSegmentAvailableTimeUs < 100000 + jMsToUs4)) {
                    jMsToUs4 = nextSegmentAvailableTimeUs;
                }
            }
        }
        return LongMath.divide(jMsToUs4, 1000L, RoundingMode.CEILING);
    }

    private long getManifestLoadRetryDelayMillis() {
        return Math.min((this.staleManifestReloadAttempt - 1) * 1000, 5000);
    }

    private static boolean hasVideoOrAudioAdaptationSets(Period period) {
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            int i2 = period.adaptationSets.get(i).type;
            if (i2 == 1 || i2 == 2) {
                return true;
            }
        }
        return false;
    }

    private static boolean isIndexExplicit(Period period) {
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            DashSegmentIndex index = period.adaptationSets.get(i).representations.get(0).getIndex();
            if (index == null || index.isExplicit()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        processManifest(false);
    }

    private void loadNtpTimeOffset() {
        SntpClient.initialize(this.loader, new SntpClient.InitializationCallback() { // from class: com.google.android.exoplayer2.source.dash.DashMediaSource.1
            @Override // com.google.android.exoplayer2.util.SntpClient.InitializationCallback
            public void onInitializationFailed(IOException iOException) {
                DashMediaSource.this.onUtcTimestampResolutionError(iOException);
            }

            @Override // com.google.android.exoplayer2.util.SntpClient.InitializationCallback
            public void onInitialized() {
                DashMediaSource.this.onUtcTimestampResolved(SntpClient.getElapsedRealtimeOffsetMs());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUtcTimestampResolutionError(IOException iOException) {
        Log.e("DashMediaSource", "Failed to resolve time offset.", iOException);
        processManifest(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUtcTimestampResolved(long j) {
        this.elapsedRealtimeOffsetMs = j;
        processManifest(true);
    }

    private void processManifest(boolean z) {
        long j;
        long j2;
        for (int i = 0; i < this.periodsById.size(); i++) {
            int iKeyAt = this.periodsById.keyAt(i);
            if (iKeyAt >= this.firstPeriodId) {
                this.periodsById.valueAt(i).updateManifest(this.manifest, iKeyAt - this.firstPeriodId);
            }
        }
        Period period = this.manifest.getPeriod(0);
        int periodCount = this.manifest.getPeriodCount() - 1;
        Period period2 = this.manifest.getPeriod(periodCount);
        long periodDurationUs = this.manifest.getPeriodDurationUs(periodCount);
        long jMsToUs = Util.msToUs(Util.getNowUnixTimeMs(this.elapsedRealtimeOffsetMs));
        long availableStartTimeInManifestUs = getAvailableStartTimeInManifestUs(period, this.manifest.getPeriodDurationUs(0), jMsToUs);
        long availableEndTimeInManifestUs = getAvailableEndTimeInManifestUs(period2, periodDurationUs, jMsToUs);
        boolean z2 = this.manifest.dynamic && !isIndexExplicit(period2);
        if (z2) {
            long j3 = this.manifest.timeShiftBufferDepthMs;
            if (j3 != C.TIME_UNSET) {
                availableStartTimeInManifestUs = Math.max(availableStartTimeInManifestUs, availableEndTimeInManifestUs - Util.msToUs(j3));
            }
        }
        long j4 = availableEndTimeInManifestUs - availableStartTimeInManifestUs;
        DashManifest dashManifest = this.manifest;
        if (dashManifest.dynamic) {
            Assertions.checkState(dashManifest.availabilityStartTimeMs != C.TIME_UNSET);
            long jMsToUs2 = (jMsToUs - Util.msToUs(this.manifest.availabilityStartTimeMs)) - availableStartTimeInManifestUs;
            updateLiveConfiguration(jMsToUs2, j4);
            long jUsToMs = Util.usToMs(availableStartTimeInManifestUs) + this.manifest.availabilityStartTimeMs;
            long jMsToUs3 = jMsToUs2 - Util.msToUs(this.liveConfiguration.targetOffsetMs);
            long jMin = Math.min(MIN_LIVE_DEFAULT_START_POSITION_US, j4 / 2);
            j2 = jMsToUs3 < jMin ? jMin : jMsToUs3;
            j = jUsToMs;
        } else {
            j = C.TIME_UNSET;
            j2 = 0;
        }
        long jMsToUs4 = availableStartTimeInManifestUs - Util.msToUs(period.startMs);
        DashManifest dashManifest2 = this.manifest;
        refreshSourceInfo(new DashTimeline(dashManifest2.availabilityStartTimeMs, j, this.elapsedRealtimeOffsetMs, this.firstPeriodId, jMsToUs4, j4, j2, dashManifest2, this.mediaItem, dashManifest2.dynamic ? this.liveConfiguration : null));
        if (this.sideloadedManifest) {
            return;
        }
        this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
        if (z2) {
            this.handler.postDelayed(this.simulateManifestRefreshRunnable, getIntervalUntilNextManifestRefreshMs(this.manifest, Util.getNowUnixTimeMs(this.elapsedRealtimeOffsetMs)));
        }
        if (this.manifestLoadPending) {
            startLoadingManifest();
            return;
        }
        if (z) {
            DashManifest dashManifest3 = this.manifest;
            if (dashManifest3.dynamic) {
                long j5 = dashManifest3.minUpdatePeriodMs;
                if (j5 != C.TIME_UNSET) {
                    if (j5 == 0) {
                        j5 = 5000;
                    }
                    scheduleManifestRefresh(Math.max(0L, (this.manifestLoadStartTimestampMs + j5) - SystemClock.elapsedRealtime()));
                }
            }
        }
    }

    private void resolveUtcTimingElement(UtcTimingElement utcTimingElement) {
        String str = utcTimingElement.schemeIdUri;
        if (Util.areEqual(str, "urn:mpeg:dash:utc:direct:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:direct:2012")) {
            resolveUtcTimingElementDirect(utcTimingElement);
            return;
        }
        if (Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2012")) {
            resolveUtcTimingElementHttp(utcTimingElement, new Iso8601Parser());
            return;
        }
        if (Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2012")) {
            resolveUtcTimingElementHttp(utcTimingElement, new XsDateTimeParser());
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:ntp:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:ntp:2012")) {
            loadNtpTimeOffset();
        } else {
            onUtcTimestampResolutionError(new IOException("Unsupported UTC timing scheme"));
        }
    }

    private void resolveUtcTimingElementDirect(UtcTimingElement utcTimingElement) {
        try {
            onUtcTimestampResolved(Util.parseXsDateTime(utcTimingElement.value) - this.manifestLoadEndTimestampMs);
        } catch (ParserException e) {
            onUtcTimestampResolutionError(e);
        }
    }

    private void resolveUtcTimingElementHttp(UtcTimingElement utcTimingElement, ParsingLoadable.Parser<Long> parser) {
        startLoading(new ParsingLoadable(this.dataSource, Uri.parse(utcTimingElement.value), 5, parser), new UtcTimestampCallback(), 1);
    }

    private void scheduleManifestRefresh(long j) {
        this.handler.postDelayed(this.refreshManifestRunnable, j);
    }

    private <T> void startLoading(ParsingLoadable<T> parsingLoadable, Loader.Callback<ParsingLoadable<T>> callback, int i) {
        this.manifestEventDispatcher.loadStarted(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, this.loader.startLoading(parsingLoadable, callback, i)), parsingLoadable.type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLoadingManifest() {
        Uri uri;
        this.handler.removeCallbacks(this.refreshManifestRunnable);
        if (this.loader.hasFatalError()) {
            return;
        }
        if (this.loader.isLoading()) {
            this.manifestLoadPending = true;
            return;
        }
        synchronized (this.manifestUriLock) {
            uri = this.manifestUri;
        }
        this.manifestLoadPending = false;
        startLoading(new ParsingLoadable(this.dataSource, uri, 4, this.manifestParser), this.manifestCallback, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(4));
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0039  */
    /* JADX WARN: Code duplicated, block: B:19:0x0046  */
    /* JADX WARN: Code duplicated, block: B:22:0x0056  */
    /* JADX WARN: Code duplicated, block: B:23:0x005b  */
    /* JADX WARN: Code duplicated, block: B:25:0x0061  */
    /* JADX WARN: Code duplicated, block: B:27:0x0067  */
    /* JADX WARN: Code duplicated, block: B:30:0x006f  */
    /* JADX WARN: Code duplicated, block: B:34:0x0079  */
    /* JADX WARN: Code duplicated, block: B:36:0x007f  */
    /* JADX WARN: Code duplicated, block: B:38:0x0085  */
    /* JADX WARN: Code duplicated, block: B:39:0x0087  */
    /* JADX WARN: Code duplicated, block: B:42:0x008e  */
    /* JADX WARN: Code duplicated, block: B:45:0x0094  */
    /* JADX WARN: Code duplicated, block: B:48:0x0099  */
    /* JADX WARN: Code duplicated, block: B:52:0x00be  */
    /* JADX WARN: Code duplicated, block: B:54:0x00c4  */
    /* JADX WARN: Code duplicated, block: B:55:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:59:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:61:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:62:0x00da  */
    /* JADX WARN: Code duplicated, block: B:65:0x00e3  */
    private void updateLiveConfiguration(long j, long j2) {
        long j3;
        long jMin;
        long jUsToMs;
        long j4;
        long jConstrainValue;
        long j5;
        ServiceDescriptionElement serviceDescriptionElement;
        long j6;
        long jConstrainValue2;
        float f;
        float f2;
        ServiceDescriptionElement serviceDescriptionElement2;
        ServiceDescriptionElement serviceDescriptionElement3;
        ServiceDescriptionElement serviceDescriptionElement4;
        DashManifest dashManifest;
        ServiceDescriptionElement serviceDescriptionElement5;
        long j7;
        long jUsToMs2 = Util.usToMs(j);
        long j8 = this.mediaItem.liveConfiguration.maxOffsetMs;
        if (j8 == C.TIME_UNSET) {
            ServiceDescriptionElement serviceDescriptionElement6 = this.manifest.serviceDescription;
            if (serviceDescriptionElement6 != null) {
                long j9 = serviceDescriptionElement6.maxOffsetMs;
                if (j9 != C.TIME_UNSET) {
                    jMin = Math.min(jUsToMs2, j9);
                }
                jUsToMs = Util.usToMs(j - j2);
                if (jUsToMs < 0 && j3 > 0) {
                    jUsToMs = 0;
                }
                j4 = this.manifest.minBufferTimeMs;
                if (j4 != C.TIME_UNSET) {
                    jUsToMs = Math.min(jUsToMs + j4, jUsToMs2);
                }
                jConstrainValue = jUsToMs;
                j5 = this.mediaItem.liveConfiguration.minOffsetMs;
                if (j5 != C.TIME_UNSET) {
                    jConstrainValue = Util.constrainValue(j5, jConstrainValue, jUsToMs2);
                } else {
                    serviceDescriptionElement = this.manifest.serviceDescription;
                    if (serviceDescriptionElement != null) {
                        j6 = serviceDescriptionElement.minOffsetMs;
                        if (j6 != C.TIME_UNSET) {
                            jConstrainValue = Util.constrainValue(j6, jConstrainValue, jUsToMs2);
                        }
                    }
                }
                if (jConstrainValue > j3) {
                    j3 = jConstrainValue;
                }
                jConstrainValue2 = this.liveConfiguration.targetOffsetMs;
                if (jConstrainValue2 == C.TIME_UNSET) {
                    dashManifest = this.manifest;
                    serviceDescriptionElement5 = dashManifest.serviceDescription;
                    if (serviceDescriptionElement5 != null) {
                        j7 = serviceDescriptionElement5.targetOffsetMs;
                        if (j7 != C.TIME_UNSET) {
                            jConstrainValue2 = j7;
                        } else {
                            jConstrainValue2 = dashManifest.suggestedPresentationDelayMs;
                            if (jConstrainValue2 == C.TIME_UNSET) {
                                jConstrainValue2 = this.fallbackTargetLiveOffsetMs;
                            }
                        }
                    } else {
                        jConstrainValue2 = dashManifest.suggestedPresentationDelayMs;
                        if (jConstrainValue2 == C.TIME_UNSET) {
                            jConstrainValue2 = this.fallbackTargetLiveOffsetMs;
                        }
                    }
                }
                if (jConstrainValue2 < jConstrainValue) {
                    jConstrainValue2 = jConstrainValue;
                }
                if (jConstrainValue2 > j3) {
                    jConstrainValue2 = Util.constrainValue(Util.usToMs(j - Math.min(MIN_LIVE_DEFAULT_START_POSITION_US, j2 / 2)), jConstrainValue, j3);
                }
                MediaItem.LiveConfiguration liveConfiguration = this.mediaItem.liveConfiguration;
                f = liveConfiguration.minPlaybackSpeed;
                if (f == -3.4028235E38f) {
                    serviceDescriptionElement4 = this.manifest.serviceDescription;
                    if (serviceDescriptionElement4 != null) {
                        f = serviceDescriptionElement4.minPlaybackSpeed;
                    } else {
                        f = -3.4028235E38f;
                    }
                }
                f2 = liveConfiguration.maxPlaybackSpeed;
                if (f2 == -3.4028235E38f) {
                    serviceDescriptionElement3 = this.manifest.serviceDescription;
                    if (serviceDescriptionElement3 != null) {
                        f2 = serviceDescriptionElement3.maxPlaybackSpeed;
                    } else {
                        f2 = -3.4028235E38f;
                    }
                }
                if (f == -3.4028235E38f && f2 == -3.4028235E38f && ((serviceDescriptionElement2 = this.manifest.serviceDescription) == null || serviceDescriptionElement2.targetOffsetMs == C.TIME_UNSET)) {
                    f2 = 1.0f;
                    f = 1.0f;
                }
                this.liveConfiguration = new MediaItem.LiveConfiguration.Builder().setTargetOffsetMs(jConstrainValue2).setMinOffsetMs(jConstrainValue).setMaxOffsetMs(j3).setMinPlaybackSpeed(f).setMaxPlaybackSpeed(f2).build();
            }
            j3 = jUsToMs2;
            jUsToMs = Util.usToMs(j - j2);
            if (jUsToMs < 0) {
                jUsToMs = 0;
            }
            j4 = this.manifest.minBufferTimeMs;
            if (j4 != C.TIME_UNSET) {
                jUsToMs = Math.min(jUsToMs + j4, jUsToMs2);
            }
            jConstrainValue = jUsToMs;
            j5 = this.mediaItem.liveConfiguration.minOffsetMs;
            if (j5 != C.TIME_UNSET) {
                jConstrainValue = Util.constrainValue(j5, jConstrainValue, jUsToMs2);
            } else {
                serviceDescriptionElement = this.manifest.serviceDescription;
                if (serviceDescriptionElement != null) {
                    j6 = serviceDescriptionElement.minOffsetMs;
                    if (j6 != C.TIME_UNSET) {
                        jConstrainValue = Util.constrainValue(j6, jConstrainValue, jUsToMs2);
                    }
                }
            }
            if (jConstrainValue > j3) {
                j3 = jConstrainValue;
            }
            jConstrainValue2 = this.liveConfiguration.targetOffsetMs;
            if (jConstrainValue2 == C.TIME_UNSET) {
                dashManifest = this.manifest;
                serviceDescriptionElement5 = dashManifest.serviceDescription;
                if (serviceDescriptionElement5 != null) {
                    j7 = serviceDescriptionElement5.targetOffsetMs;
                    if (j7 != C.TIME_UNSET) {
                        jConstrainValue2 = j7;
                    } else {
                        jConstrainValue2 = dashManifest.suggestedPresentationDelayMs;
                        if (jConstrainValue2 == C.TIME_UNSET) {
                            jConstrainValue2 = this.fallbackTargetLiveOffsetMs;
                        }
                    }
                } else {
                    jConstrainValue2 = dashManifest.suggestedPresentationDelayMs;
                    if (jConstrainValue2 == C.TIME_UNSET) {
                        jConstrainValue2 = this.fallbackTargetLiveOffsetMs;
                    }
                }
            }
            if (jConstrainValue2 < jConstrainValue) {
                jConstrainValue2 = jConstrainValue;
            }
            if (jConstrainValue2 > j3) {
                jConstrainValue2 = Util.constrainValue(Util.usToMs(j - Math.min(MIN_LIVE_DEFAULT_START_POSITION_US, j2 / 2)), jConstrainValue, j3);
            }
            MediaItem.LiveConfiguration liveConfiguration2 = this.mediaItem.liveConfiguration;
            f = liveConfiguration2.minPlaybackSpeed;
            if (f == -3.4028235E38f) {
                serviceDescriptionElement4 = this.manifest.serviceDescription;
                if (serviceDescriptionElement4 != null) {
                    f = serviceDescriptionElement4.minPlaybackSpeed;
                } else {
                    f = -3.4028235E38f;
                }
            }
            f2 = liveConfiguration2.maxPlaybackSpeed;
            if (f2 == -3.4028235E38f) {
                serviceDescriptionElement3 = this.manifest.serviceDescription;
                if (serviceDescriptionElement3 != null) {
                    f2 = serviceDescriptionElement3.maxPlaybackSpeed;
                } else {
                    f2 = -3.4028235E38f;
                }
            }
            if (f == -3.4028235E38f) {
                f2 = 1.0f;
                f = 1.0f;
            }
            this.liveConfiguration = new MediaItem.LiveConfiguration.Builder().setTargetOffsetMs(jConstrainValue2).setMinOffsetMs(jConstrainValue).setMaxOffsetMs(j3).setMinPlaybackSpeed(f).setMaxPlaybackSpeed(f2).build();
        }
        jMin = Math.min(jUsToMs2, j8);
        j3 = jMin;
        jUsToMs = Util.usToMs(j - j2);
        if (jUsToMs < 0) {
            jUsToMs = 0;
        }
        j4 = this.manifest.minBufferTimeMs;
        if (j4 != C.TIME_UNSET) {
            jUsToMs = Math.min(jUsToMs + j4, jUsToMs2);
        }
        jConstrainValue = jUsToMs;
        j5 = this.mediaItem.liveConfiguration.minOffsetMs;
        if (j5 != C.TIME_UNSET) {
            jConstrainValue = Util.constrainValue(j5, jConstrainValue, jUsToMs2);
        } else {
            serviceDescriptionElement = this.manifest.serviceDescription;
            if (serviceDescriptionElement != null) {
                j6 = serviceDescriptionElement.minOffsetMs;
                if (j6 != C.TIME_UNSET) {
                    jConstrainValue = Util.constrainValue(j6, jConstrainValue, jUsToMs2);
                }
            }
        }
        if (jConstrainValue > j3) {
            j3 = jConstrainValue;
        }
        jConstrainValue2 = this.liveConfiguration.targetOffsetMs;
        if (jConstrainValue2 == C.TIME_UNSET) {
            dashManifest = this.manifest;
            serviceDescriptionElement5 = dashManifest.serviceDescription;
            if (serviceDescriptionElement5 != null) {
                j7 = serviceDescriptionElement5.targetOffsetMs;
                if (j7 != C.TIME_UNSET) {
                    jConstrainValue2 = j7;
                } else {
                    jConstrainValue2 = dashManifest.suggestedPresentationDelayMs;
                    if (jConstrainValue2 == C.TIME_UNSET) {
                        jConstrainValue2 = this.fallbackTargetLiveOffsetMs;
                    }
                }
            } else {
                jConstrainValue2 = dashManifest.suggestedPresentationDelayMs;
                if (jConstrainValue2 == C.TIME_UNSET) {
                    jConstrainValue2 = this.fallbackTargetLiveOffsetMs;
                }
            }
        }
        if (jConstrainValue2 < jConstrainValue) {
            jConstrainValue2 = jConstrainValue;
        }
        if (jConstrainValue2 > j3) {
            jConstrainValue2 = Util.constrainValue(Util.usToMs(j - Math.min(MIN_LIVE_DEFAULT_START_POSITION_US, j2 / 2)), jConstrainValue, j3);
        }
        MediaItem.LiveConfiguration liveConfiguration3 = this.mediaItem.liveConfiguration;
        f = liveConfiguration3.minPlaybackSpeed;
        if (f == -3.4028235E38f) {
            serviceDescriptionElement4 = this.manifest.serviceDescription;
            if (serviceDescriptionElement4 != null) {
                f = serviceDescriptionElement4.minPlaybackSpeed;
            } else {
                f = -3.4028235E38f;
            }
        }
        f2 = liveConfiguration3.maxPlaybackSpeed;
        if (f2 == -3.4028235E38f) {
            serviceDescriptionElement3 = this.manifest.serviceDescription;
            if (serviceDescriptionElement3 != null) {
                f2 = serviceDescriptionElement3.maxPlaybackSpeed;
            } else {
                f2 = -3.4028235E38f;
            }
        }
        if (f == -3.4028235E38f) {
            f2 = 1.0f;
            f = 1.0f;
        }
        this.liveConfiguration = new MediaItem.LiveConfiguration.Builder().setTargetOffsetMs(jConstrainValue2).setMinOffsetMs(jConstrainValue).setMaxOffsetMs(j3).setMinPlaybackSpeed(f).setMaxPlaybackSpeed(f2).build();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        int iIntValue = ((Integer) mediaPeriodId.periodUid).intValue() - this.firstPeriodId;
        MediaSourceEventListener.EventDispatcher eventDispatcherCreateEventDispatcher = createEventDispatcher(mediaPeriodId, this.manifest.getPeriod(iIntValue).startMs);
        DashMediaPeriod dashMediaPeriod = new DashMediaPeriod(iIntValue + this.firstPeriodId, this.manifest, this.baseUrlExclusionList, iIntValue, this.chunkSourceFactory, this.mediaTransferListener, this.drmSessionManager, createDrmEventDispatcher(mediaPeriodId), this.loadErrorHandlingPolicy, eventDispatcherCreateEventDispatcher, this.elapsedRealtimeOffsetMs, this.manifestLoadErrorThrower, allocator, this.compositeSequenceableLoaderFactory, this.playerEmsgCallback, getPlayerId());
        this.periodsById.put(dashMediaPeriod.id, dashMediaPeriod);
        return dashMediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.manifestLoadErrorThrower.maybeThrowError();
    }

    public final void onDashManifestPublishTimeExpired(long j) {
        long j2 = this.expiredManifestPublishTimeUs;
        if (j2 == C.TIME_UNSET || j2 < j) {
            this.expiredManifestPublishTimeUs = j;
        }
    }

    public final void onDashManifestRefreshRequested() {
        this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
        startLoadingManifest();
    }

    public final void onLoadCanceled(ParsingLoadable<?> parsingLoadable, long j, long j2) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.manifestEventDispatcher.loadCanceled(loadEventInfo, parsingLoadable.type);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x00a7  */
    /* JADX WARN: Code duplicated, block: B:26:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:27:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:29:0x00c7  */
    public final void onManifestLoadCompleted(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2) {
        boolean z;
        int i;
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.manifestEventDispatcher.loadCompleted(loadEventInfo, parsingLoadable.type);
        DashManifest result = parsingLoadable.getResult();
        DashManifest dashManifest = this.manifest;
        int periodCount = dashManifest == null ? 0 : dashManifest.getPeriodCount();
        long j3 = result.getPeriod(0).startMs;
        int i2 = 0;
        while (i2 < periodCount && this.manifest.getPeriod(i2).startMs < j3) {
            i2++;
        }
        if (result.dynamic) {
            if (periodCount - i2 > result.getPeriodCount()) {
                Log.w("DashMediaSource", "Loaded out of sync manifest");
            } else {
                long j4 = this.expiredManifestPublishTimeUs;
                if (j4 == C.TIME_UNSET || result.publishTimeMs * 1000 > j4) {
                    z = false;
                } else {
                    StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Loaded stale dynamic manifest: ");
                    sbM.append(result.publishTimeMs);
                    sbM.append(", ");
                    sbM.append(this.expiredManifestPublishTimeUs);
                    Log.w("DashMediaSource", sbM.toString());
                }
                if (z) {
                    i = this.staleManifestReloadAttempt;
                    this.staleManifestReloadAttempt = i + 1;
                    if (i < this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(parsingLoadable.type)) {
                        scheduleManifestRefresh(getManifestLoadRetryDelayMillis());
                        return;
                    } else {
                        this.manifestFatalError = new DashManifestStaleException();
                        return;
                    }
                }
                this.staleManifestReloadAttempt = 0;
            }
            z = true;
            if (z) {
                i = this.staleManifestReloadAttempt;
                this.staleManifestReloadAttempt = i + 1;
                if (i < this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(parsingLoadable.type)) {
                    scheduleManifestRefresh(getManifestLoadRetryDelayMillis());
                    return;
                } else {
                    this.manifestFatalError = new DashManifestStaleException();
                    return;
                }
            }
            this.staleManifestReloadAttempt = 0;
        }
        this.manifest = result;
        this.manifestLoadPending = result.dynamic & this.manifestLoadPending;
        this.manifestLoadStartTimestampMs = j - j2;
        this.manifestLoadEndTimestampMs = j;
        synchronized (this.manifestUriLock) {
            if (parsingLoadable.dataSpec.uri == this.manifestUri) {
                Uri uri = this.manifest.location;
                if (uri == null) {
                    uri = parsingLoadable.getUri();
                }
                this.manifestUri = uri;
            }
        }
        if (periodCount != 0) {
            this.firstPeriodId += i2;
            processManifest(true);
            return;
        }
        DashManifest dashManifest2 = this.manifest;
        if (!dashManifest2.dynamic) {
            processManifest(true);
            return;
        }
        UtcTimingElement utcTimingElement = dashManifest2.utcTiming;
        if (utcTimingElement != null) {
            resolveUtcTimingElement(utcTimingElement);
        } else {
            loadNtpTimeOffset();
        }
    }

    public final Loader.LoadErrorAction onManifestLoadError(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException, int i) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        long retryDelayMsFor = this.loadErrorHandlingPolicy.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(parsingLoadable.type), iOException, i));
        Loader.LoadErrorAction loadErrorActionCreateRetryAction = retryDelayMsFor == C.TIME_UNSET ? Loader.DONT_RETRY_FATAL : Loader.createRetryAction(false, retryDelayMsFor);
        boolean z = !loadErrorActionCreateRetryAction.isRetry();
        this.manifestEventDispatcher.loadError(loadEventInfo, parsingLoadable.type, iOException, z);
        if (z) {
            this.loadErrorHandlingPolicy.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        }
        return loadErrorActionCreateRetryAction;
    }

    public final void onUtcTimestampLoadCompleted(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.manifestEventDispatcher.loadCompleted(loadEventInfo, parsingLoadable.type);
        onUtcTimestampResolved(parsingLoadable.getResult().longValue() - j);
    }

    public final Loader.LoadErrorAction onUtcTimestampLoadError(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException) {
        this.manifestEventDispatcher.loadError(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded()), parsingLoadable.type, iOException, true);
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        onUtcTimestampResolutionError(iOException);
        return Loader.DONT_RETRY;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    public final void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.mediaTransferListener = transferListener;
        this.drmSessionManager.prepare();
        this.drmSessionManager.setPlayer(Looper.myLooper(), getPlayerId());
        if (this.sideloadedManifest) {
            processManifest(false);
            return;
        }
        this.dataSource = this.manifestDataSourceFactory.createDataSource();
        this.loader = new Loader("DashMediaSource");
        this.handler = Util.createHandlerForCurrentLooper();
        startLoadingManifest();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        DashMediaPeriod dashMediaPeriod = (DashMediaPeriod) mediaPeriod;
        dashMediaPeriod.release();
        this.periodsById.remove(dashMediaPeriod.id);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    public final void releaseSourceInternal() {
        this.manifestLoadPending = false;
        this.dataSource = null;
        Loader loader = this.loader;
        if (loader != null) {
            loader.release();
            this.loader = null;
        }
        this.manifestLoadStartTimestampMs = 0L;
        this.manifestLoadEndTimestampMs = 0L;
        this.manifest = this.sideloadedManifest ? this.manifest : null;
        this.manifestUri = this.initialManifestUri;
        this.manifestFatalError = null;
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.handler = null;
        }
        this.elapsedRealtimeOffsetMs = C.TIME_UNSET;
        this.staleManifestReloadAttempt = 0;
        this.expiredManifestPublishTimeUs = C.TIME_UNSET;
        this.firstPeriodId = 0;
        this.periodsById.clear();
        this.baseUrlExclusionList.reset();
        this.drmSessionManager.release();
    }

    public void replaceManifestUri(Uri uri) {
        synchronized (this.manifestUriLock) {
            this.manifestUri = uri;
            this.initialManifestUri = uri;
        }
    }

    public final class ManifestLoadErrorThrower implements LoaderErrorThrower {
        public ManifestLoadErrorThrower() {
        }

        private void maybeThrowManifestError() throws IOException {
            if (DashMediaSource.this.manifestFatalError != null) {
                throw DashMediaSource.this.manifestFatalError;
            }
        }

        @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
        public void maybeThrowError() throws IOException {
            DashMediaSource.this.loader.maybeThrowError();
            maybeThrowManifestError();
        }

        @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
        public void maybeThrowError(int i) throws IOException {
            DashMediaSource.this.loader.maybeThrowError(i);
            maybeThrowManifestError();
        }
    }

    private DashMediaSource(MediaItem mediaItem, @Nullable DashManifest dashManifest, @Nullable DataSource.Factory factory, @Nullable ParsingLoadable.Parser<? extends DashManifest> parser, DashChunkSource.Factory factory2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, long j) {
        this.mediaItem = mediaItem;
        this.liveConfiguration = mediaItem.liveConfiguration;
        this.manifestUri = ((MediaItem.LocalConfiguration) Assertions.checkNotNull(mediaItem.localConfiguration)).uri;
        this.initialManifestUri = mediaItem.localConfiguration.uri;
        this.manifest = dashManifest;
        this.manifestDataSourceFactory = factory;
        this.manifestParser = parser;
        this.chunkSourceFactory = factory2;
        this.drmSessionManager = drmSessionManager;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.fallbackTargetLiveOffsetMs = j;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.baseUrlExclusionList = new BaseUrlExclusionList();
        final int i = 0;
        final int i2 = 1;
        boolean z = dashManifest != null;
        this.sideloadedManifest = z;
        this.manifestEventDispatcher = createEventDispatcher(null);
        this.manifestUriLock = new Object();
        this.periodsById = new SparseArray<>();
        this.playerEmsgCallback = new DefaultPlayerEmsgCallback();
        this.expiredManifestPublishTimeUs = C.TIME_UNSET;
        this.elapsedRealtimeOffsetMs = C.TIME_UNSET;
        if (!z) {
            this.manifestCallback = new ManifestCallback();
            this.manifestLoadErrorThrower = new ManifestLoadErrorThrower();
            this.refreshManifestRunnable = new Runnable(this) { // from class: com.google.android.exoplayer2.source.dash.DashMediaSource$$ExternalSyntheticLambda0
                public final /* synthetic */ DashMediaSource f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i) {
                        case 0:
                            this.f$0.startLoadingManifest();
                            break;
                        default:
                            this.f$0.lambda$new$0();
                            break;
                    }
                }
            };
            this.simulateManifestRefreshRunnable = new Runnable(this) { // from class: com.google.android.exoplayer2.source.dash.DashMediaSource$$ExternalSyntheticLambda0
                public final /* synthetic */ DashMediaSource f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            this.f$0.startLoadingManifest();
                            break;
                        default:
                            this.f$0.lambda$new$0();
                            break;
                    }
                }
            };
            return;
        }
        Assertions.checkState(!dashManifest.dynamic);
        this.manifestCallback = null;
        this.refreshManifestRunnable = null;
        this.simulateManifestRefreshRunnable = null;
        this.manifestLoadErrorThrower = new LoaderErrorThrower.Dummy();
    }
}
