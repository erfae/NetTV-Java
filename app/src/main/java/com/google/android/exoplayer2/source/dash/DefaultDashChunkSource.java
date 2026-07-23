package com.google.android.exoplayer2.source.dash;

import android.os.SystemClock;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.analytics.PlayerId;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.InitializationChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.SingleSampleMediaChunk;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.BaseUrl;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DefaultDashChunkSource implements DashChunkSource {
    private final int[] adaptationSetIndices;
    private final BaseUrlExclusionList baseUrlExclusionList;
    private final DataSource dataSource;
    private final long elapsedRealtimeOffsetMs;

    @Nullable
    private IOException fatalError;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int maxSegmentsPerLoad;
    private boolean missingLastSegment;
    private int periodIndex;

    @Nullable
    private final PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler;
    public final RepresentationHolder[] representationHolders;
    private ExoTrackSelection trackSelection;
    private final int trackType;

    public static final class Factory implements DashChunkSource.Factory {
        private final ChunkExtractor.Factory chunkExtractorFactory;
        private final DataSource.Factory dataSourceFactory;
        private final int maxSegmentsPerLoad;

        public Factory(DataSource.Factory factory) {
            this(factory, 1);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashChunkSource.Factory
        public DashChunkSource createDashChunkSource(LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, BaseUrlExclusionList baseUrlExclusionList, int i, int[] iArr, ExoTrackSelection exoTrackSelection, int i2, long j, boolean z, List<Format> list, @Nullable PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler, @Nullable TransferListener transferListener, PlayerId playerId) {
            DataSource dataSourceCreateDataSource = this.dataSourceFactory.createDataSource();
            if (transferListener != null) {
                dataSourceCreateDataSource.addTransferListener(transferListener);
            }
            return new DefaultDashChunkSource(this.chunkExtractorFactory, loaderErrorThrower, dashManifest, baseUrlExclusionList, i, iArr, exoTrackSelection, i2, dataSourceCreateDataSource, j, this.maxSegmentsPerLoad, z, list, playerTrackEmsgHandler, playerId);
        }

        public Factory(DataSource.Factory factory, int i) {
            this(BundledChunkExtractor.FACTORY, factory, i);
        }

        public Factory(ChunkExtractor.Factory factory, DataSource.Factory factory2, int i) {
            this.chunkExtractorFactory = factory;
            this.dataSourceFactory = factory2;
            this.maxSegmentsPerLoad = i;
        }
    }

    public static final class RepresentationHolder {

        @Nullable
        public final ChunkExtractor chunkExtractor;
        private final long periodDurationUs;
        public final Representation representation;

        @Nullable
        public final DashSegmentIndex segmentIndex;
        private final long segmentNumShift;
        public final BaseUrl selectedBaseUrl;

        public RepresentationHolder(long j, Representation representation, BaseUrl baseUrl, @Nullable ChunkExtractor chunkExtractor, long j2, @Nullable DashSegmentIndex dashSegmentIndex) {
            this.periodDurationUs = j;
            this.representation = representation;
            this.selectedBaseUrl = baseUrl;
            this.segmentNumShift = j2;
            this.chunkExtractor = chunkExtractor;
            this.segmentIndex = dashSegmentIndex;
        }

        @CheckResult
        public final RepresentationHolder copyWithNewRepresentation(long j, Representation representation) throws BehindLiveWindowException {
            long segmentNum;
            long segmentNum2;
            DashSegmentIndex index = this.representation.getIndex();
            DashSegmentIndex index2 = representation.getIndex();
            if (index == null) {
                return new RepresentationHolder(j, representation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, index);
            }
            if (!index.isExplicit()) {
                return new RepresentationHolder(j, representation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, index2);
            }
            long segmentCount = index.getSegmentCount(j);
            if (segmentCount == 0) {
                return new RepresentationHolder(j, representation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, index2);
            }
            long firstSegmentNum = index.getFirstSegmentNum();
            long timeUs = index.getTimeUs(firstSegmentNum);
            long j2 = (segmentCount + firstSegmentNum) - 1;
            long durationUs = index.getDurationUs(j2, j) + index.getTimeUs(j2);
            long firstSegmentNum2 = index2.getFirstSegmentNum();
            long timeUs2 = index2.getTimeUs(firstSegmentNum2);
            long j3 = this.segmentNumShift;
            if (durationUs != timeUs2) {
                if (durationUs < timeUs2) {
                    throw new BehindLiveWindowException();
                }
                if (timeUs2 < timeUs) {
                    segmentNum2 = j3 - (index2.getSegmentNum(timeUs, j) - firstSegmentNum);
                } else {
                    segmentNum = index.getSegmentNum(timeUs2, j);
                }
                return new RepresentationHolder(j, representation, this.selectedBaseUrl, this.chunkExtractor, segmentNum2, index2);
            }
            segmentNum = j2 + 1;
            segmentNum2 = (segmentNum - firstSegmentNum2) + j3;
            return new RepresentationHolder(j, representation, this.selectedBaseUrl, this.chunkExtractor, segmentNum2, index2);
        }

        @CheckResult
        public final RepresentationHolder copyWithNewSegmentIndex(DashSegmentIndex dashSegmentIndex) {
            return new RepresentationHolder(this.periodDurationUs, this.representation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, dashSegmentIndex);
        }

        @CheckResult
        public final RepresentationHolder copyWithNewSelectedBaseUrl(BaseUrl baseUrl) {
            return new RepresentationHolder(this.periodDurationUs, this.representation, baseUrl, this.chunkExtractor, this.segmentNumShift, this.segmentIndex);
        }

        public long getFirstAvailableSegmentNum(long j) {
            return this.segmentIndex.getFirstAvailableSegmentNum(this.periodDurationUs, j) + this.segmentNumShift;
        }

        public long getFirstSegmentNum() {
            return this.segmentIndex.getFirstSegmentNum() + this.segmentNumShift;
        }

        public long getLastAvailableSegmentNum(long j) {
            return (this.segmentIndex.getAvailableSegmentCount(this.periodDurationUs, j) + getFirstAvailableSegmentNum(j)) - 1;
        }

        public long getSegmentCount() {
            return this.segmentIndex.getSegmentCount(this.periodDurationUs);
        }

        public long getSegmentEndTimeUs(long j) {
            return this.segmentIndex.getDurationUs(j - this.segmentNumShift, this.periodDurationUs) + getSegmentStartTimeUs(j);
        }

        public long getSegmentNum(long j) {
            return this.segmentIndex.getSegmentNum(j, this.periodDurationUs) + this.segmentNumShift;
        }

        public long getSegmentStartTimeUs(long j) {
            return this.segmentIndex.getTimeUs(j - this.segmentNumShift);
        }

        public RangedUri getSegmentUrl(long j) {
            return this.segmentIndex.getSegmentUrl(j - this.segmentNumShift);
        }

        public boolean isSegmentAvailableAtFullNetworkSpeed(long j, long j2) {
            return this.segmentIndex.isExplicit() || j2 == C.TIME_UNSET || getSegmentEndTimeUs(j) <= j2;
        }
    }

    public static final class RepresentationSegmentIterator extends BaseMediaChunkIterator {
        private final long nowPeriodTimeUs;
        private final RepresentationHolder representationHolder;

        public RepresentationSegmentIterator(RepresentationHolder representationHolder, long j, long j2, long j3) {
            super(j, j2);
            this.representationHolder = representationHolder;
            this.nowPeriodTimeUs = j3;
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentEndTimeUs(getCurrentIndex());
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentStartTimeUs(getCurrentIndex());
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            checkInBounds();
            long currentIndex = getCurrentIndex();
            RangedUri segmentUrl = this.representationHolder.getSegmentUrl(currentIndex);
            int i = this.representationHolder.isSegmentAvailableAtFullNetworkSpeed(currentIndex, this.nowPeriodTimeUs) ? 0 : 8;
            RepresentationHolder representationHolder = this.representationHolder;
            return DashUtil.buildDataSpec(representationHolder.representation, representationHolder.selectedBaseUrl.url, segmentUrl, i);
        }
    }

    public DefaultDashChunkSource(ChunkExtractor.Factory factory, LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, BaseUrlExclusionList baseUrlExclusionList, int i, int[] iArr, ExoTrackSelection exoTrackSelection, int i2, DataSource dataSource, long j, int i3, boolean z, List<Format> list, @Nullable PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler, PlayerId playerId) {
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.manifest = dashManifest;
        this.baseUrlExclusionList = baseUrlExclusionList;
        this.adaptationSetIndices = iArr;
        this.trackSelection = exoTrackSelection;
        this.trackType = i2;
        this.dataSource = dataSource;
        this.periodIndex = i;
        this.elapsedRealtimeOffsetMs = j;
        this.maxSegmentsPerLoad = i3;
        this.playerTrackEmsgHandler = playerTrackEmsgHandler;
        long periodDurationUs = dashManifest.getPeriodDurationUs(i);
        ArrayList<Representation> representations = getRepresentations();
        this.representationHolders = new RepresentationHolder[exoTrackSelection.length()];
        int i4 = 0;
        while (i4 < this.representationHolders.length) {
            Representation representation = representations.get(exoTrackSelection.getIndexInTrackGroup(i4));
            BaseUrl baseUrlSelectBaseUrl = baseUrlExclusionList.selectBaseUrl(representation.baseUrls);
            RepresentationHolder[] representationHolderArr = this.representationHolders;
            if (baseUrlSelectBaseUrl == null) {
                baseUrlSelectBaseUrl = representation.baseUrls.get(0);
            }
            int i5 = i4;
            representationHolderArr[i5] = new RepresentationHolder(periodDurationUs, representation, baseUrlSelectBaseUrl, factory.createProgressiveMediaExtractor(i2, representation.format, z, list, playerTrackEmsgHandler, playerId), 0L, representation.getIndex());
            i4 = i5 + 1;
        }
    }

    private LoadErrorHandlingPolicy.FallbackOptions createFallbackOptions(ExoTrackSelection exoTrackSelection, List<BaseUrl> list) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        int length = exoTrackSelection.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (exoTrackSelection.isBlacklisted(i2, jElapsedRealtime)) {
                i++;
            }
        }
        int priorityCount = BaseUrlExclusionList.getPriorityCount(list);
        return new LoadErrorHandlingPolicy.FallbackOptions(priorityCount, priorityCount - this.baseUrlExclusionList.getPriorityCountAfterExclusion(list), length, i);
    }

    private long getAvailableLiveDurationUs(long j, long j2) {
        if (!this.manifest.dynamic || this.representationHolders[0].getSegmentCount() == 0) {
            return C.TIME_UNSET;
        }
        return Math.max(0L, Math.min(getNowPeriodTimeUs(j), this.representationHolders[0].getSegmentEndTimeUs(this.representationHolders[0].getLastAvailableSegmentNum(j))) - j2);
    }

    private long getNowPeriodTimeUs(long j) {
        DashManifest dashManifest = this.manifest;
        long j2 = dashManifest.availabilityStartTimeMs;
        return j2 == C.TIME_UNSET ? C.TIME_UNSET : j - Util.msToUs(j2 + dashManifest.getPeriod(this.periodIndex).startMs);
    }

    private ArrayList<Representation> getRepresentations() {
        List<AdaptationSet> list = this.manifest.getPeriod(this.periodIndex).adaptationSets;
        ArrayList<Representation> arrayList = new ArrayList<>();
        for (int i : this.adaptationSetIndices) {
            arrayList.addAll(list.get(i).representations);
        }
        return arrayList;
    }

    private long getSegmentNum(RepresentationHolder representationHolder, @Nullable MediaChunk mediaChunk, long j, long j2, long j3) {
        return mediaChunk != null ? mediaChunk.getNextChunkIndex() : Util.constrainValue(representationHolder.getSegmentNum(j), j2, j3);
    }

    private RepresentationHolder updateSelectedBaseUrl(int i) {
        RepresentationHolder representationHolder = this.representationHolders[i];
        BaseUrl baseUrlSelectBaseUrl = this.baseUrlExclusionList.selectBaseUrl(representationHolder.representation.baseUrls);
        if (baseUrlSelectBaseUrl == null || baseUrlSelectBaseUrl.equals(representationHolder.selectedBaseUrl)) {
            return representationHolder;
        }
        RepresentationHolder representationHolderCopyWithNewSelectedBaseUrl = representationHolder.copyWithNewSelectedBaseUrl(baseUrlSelectBaseUrl);
        this.representationHolders[i] = representationHolderCopyWithNewSelectedBaseUrl;
        return representationHolderCopyWithNewSelectedBaseUrl;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        for (RepresentationHolder representationHolder : this.representationHolders) {
            if (representationHolder.segmentIndex != null) {
                long segmentCount = representationHolder.getSegmentCount();
                if (segmentCount != 0) {
                    long segmentNum = representationHolder.getSegmentNum(j);
                    long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(segmentNum);
                    return seekParameters.resolveSeekPositionUs(j, segmentStartTimeUs, (segmentStartTimeUs >= j || (segmentCount != -1 && segmentNum >= (representationHolder.getFirstSegmentNum() + segmentCount) - 1)) ? segmentStartTimeUs : representationHolder.getSegmentStartTimeUs(segmentNum + 1));
                }
            }
        }
        return j;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void getNextChunk(long j, long j2, List<? extends MediaChunk> list, ChunkHolder chunkHolder) {
        Chunk containerMediaChunk;
        MediaChunkIterator[] mediaChunkIteratorArr;
        int i;
        long j3;
        long j4;
        long j5;
        int i2;
        if (this.fatalError != null) {
            return;
        }
        long j6 = j2 - j;
        long jMsToUs = Util.msToUs(this.manifest.getPeriod(this.periodIndex).startMs) + Util.msToUs(this.manifest.availabilityStartTimeMs) + j2;
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler == null || !playerTrackEmsgHandler.maybeRefreshManifestBeforeLoadingNextChunk(jMsToUs)) {
            long jMsToUs2 = Util.msToUs(Util.getNowUnixTimeMs(this.elapsedRealtimeOffsetMs));
            long nowPeriodTimeUs = getNowPeriodTimeUs(jMsToUs2);
            MediaChunk mediaChunk = list.isEmpty() ? null : list.get(list.size() - 1);
            int length = this.trackSelection.length();
            MediaChunkIterator[] mediaChunkIteratorArr2 = new MediaChunkIterator[length];
            int i3 = 0;
            while (i3 < length) {
                RepresentationHolder representationHolder = this.representationHolders[i3];
                if (representationHolder.segmentIndex == null) {
                    mediaChunkIteratorArr2[i3] = MediaChunkIterator.EMPTY;
                    i2 = i3;
                    mediaChunkIteratorArr = mediaChunkIteratorArr2;
                    i = length;
                    j3 = nowPeriodTimeUs;
                    j4 = j6;
                    j5 = jMsToUs2;
                } else {
                    long firstAvailableSegmentNum = representationHolder.getFirstAvailableSegmentNum(jMsToUs2);
                    long lastAvailableSegmentNum = representationHolder.getLastAvailableSegmentNum(jMsToUs2);
                    int i4 = i3;
                    mediaChunkIteratorArr = mediaChunkIteratorArr2;
                    i = length;
                    j3 = nowPeriodTimeUs;
                    j4 = j6;
                    j5 = jMsToUs2;
                    long segmentNum = getSegmentNum(representationHolder, mediaChunk, j2, firstAvailableSegmentNum, lastAvailableSegmentNum);
                    if (segmentNum < firstAvailableSegmentNum) {
                        mediaChunkIteratorArr[i4] = MediaChunkIterator.EMPTY;
                        i2 = i4;
                    } else {
                        i2 = i4;
                        mediaChunkIteratorArr[i2] = new RepresentationSegmentIterator(updateSelectedBaseUrl(i4), segmentNum, lastAvailableSegmentNum, j3);
                    }
                    i3 = i2 + 1;
                    jMsToUs2 = j5;
                    mediaChunkIteratorArr2 = mediaChunkIteratorArr;
                    length = i;
                    nowPeriodTimeUs = j3;
                    j6 = j4;
                }
                i3 = i2 + 1;
                jMsToUs2 = j5;
                mediaChunkIteratorArr2 = mediaChunkIteratorArr;
                length = i;
                nowPeriodTimeUs = j3;
                j6 = j4;
            }
            long j7 = nowPeriodTimeUs;
            long j8 = jMsToUs2;
            int i5 = 1;
            this.trackSelection.updateSelectedTrack(j, j6, getAvailableLiveDurationUs(j8, j), list, mediaChunkIteratorArr2);
            RepresentationHolder representationHolderUpdateSelectedBaseUrl = updateSelectedBaseUrl(this.trackSelection.getSelectedIndex());
            ChunkExtractor chunkExtractor = representationHolderUpdateSelectedBaseUrl.chunkExtractor;
            if (chunkExtractor != null) {
                Representation representation = representationHolderUpdateSelectedBaseUrl.representation;
                RangedUri initializationUri = chunkExtractor.getSampleFormats() == null ? representation.getInitializationUri() : null;
                RangedUri indexUri = representationHolderUpdateSelectedBaseUrl.segmentIndex == null ? representation.getIndexUri() : null;
                if (initializationUri != null || indexUri != null) {
                    DataSource dataSource = this.dataSource;
                    Format selectedFormat = this.trackSelection.getSelectedFormat();
                    int selectionReason = this.trackSelection.getSelectionReason();
                    Object selectionData = this.trackSelection.getSelectionData();
                    Representation representation2 = representationHolderUpdateSelectedBaseUrl.representation;
                    if (initializationUri == null || (indexUri = initializationUri.attemptMerge(indexUri, representationHolderUpdateSelectedBaseUrl.selectedBaseUrl.url)) != null) {
                        initializationUri = indexUri;
                    }
                    chunkHolder.chunk = new InitializationChunk(dataSource, DashUtil.buildDataSpec(representation2, representationHolderUpdateSelectedBaseUrl.selectedBaseUrl.url, initializationUri, 0), selectedFormat, selectionReason, selectionData, representationHolderUpdateSelectedBaseUrl.chunkExtractor);
                    return;
                }
            }
            long j9 = representationHolderUpdateSelectedBaseUrl.periodDurationUs;
            boolean z = j9 != C.TIME_UNSET;
            if (representationHolderUpdateSelectedBaseUrl.getSegmentCount() == 0) {
                chunkHolder.endOfStream = z;
                return;
            }
            long firstAvailableSegmentNum2 = representationHolderUpdateSelectedBaseUrl.getFirstAvailableSegmentNum(j8);
            long lastAvailableSegmentNum2 = representationHolderUpdateSelectedBaseUrl.getLastAvailableSegmentNum(j8);
            boolean z2 = z;
            long segmentNum2 = getSegmentNum(representationHolderUpdateSelectedBaseUrl, mediaChunk, j2, firstAvailableSegmentNum2, lastAvailableSegmentNum2);
            if (segmentNum2 < firstAvailableSegmentNum2) {
                this.fatalError = new BehindLiveWindowException();
                return;
            }
            if (segmentNum2 > lastAvailableSegmentNum2 || (this.missingLastSegment && segmentNum2 >= lastAvailableSegmentNum2)) {
                chunkHolder.endOfStream = z2;
                return;
            }
            if (z2 && representationHolderUpdateSelectedBaseUrl.getSegmentStartTimeUs(segmentNum2) >= j9) {
                chunkHolder.endOfStream = true;
                return;
            }
            int iMin = (int) Math.min(this.maxSegmentsPerLoad, (lastAvailableSegmentNum2 - segmentNum2) + 1);
            if (j9 != C.TIME_UNSET) {
                while (iMin > 1 && representationHolderUpdateSelectedBaseUrl.getSegmentStartTimeUs((((long) iMin) + segmentNum2) - 1) >= j9) {
                    iMin--;
                }
            }
            long j10 = list.isEmpty() ? j2 : -9223372036854775807L;
            DataSource dataSource2 = this.dataSource;
            int i6 = this.trackType;
            Format selectedFormat2 = this.trackSelection.getSelectedFormat();
            int selectionReason2 = this.trackSelection.getSelectionReason();
            Object selectionData2 = this.trackSelection.getSelectionData();
            Representation representation3 = representationHolderUpdateSelectedBaseUrl.representation;
            long segmentStartTimeUs = representationHolderUpdateSelectedBaseUrl.getSegmentStartTimeUs(segmentNum2);
            RangedUri segmentUrl = representationHolderUpdateSelectedBaseUrl.getSegmentUrl(segmentNum2);
            if (representationHolderUpdateSelectedBaseUrl.chunkExtractor == null) {
                containerMediaChunk = new SingleSampleMediaChunk(dataSource2, DashUtil.buildDataSpec(representation3, representationHolderUpdateSelectedBaseUrl.selectedBaseUrl.url, segmentUrl, representationHolderUpdateSelectedBaseUrl.isSegmentAvailableAtFullNetworkSpeed(segmentNum2, j7) ? 0 : 8), selectedFormat2, selectionReason2, selectionData2, segmentStartTimeUs, representationHolderUpdateSelectedBaseUrl.getSegmentEndTimeUs(segmentNum2), segmentNum2, i6, selectedFormat2);
            } else {
                int i7 = 1;
                while (i5 < iMin) {
                    RangedUri rangedUriAttemptMerge = segmentUrl.attemptMerge(representationHolderUpdateSelectedBaseUrl.getSegmentUrl(((long) i5) + segmentNum2), representationHolderUpdateSelectedBaseUrl.selectedBaseUrl.url);
                    if (rangedUriAttemptMerge == null) {
                        break;
                    }
                    i7++;
                    i5++;
                    segmentUrl = rangedUriAttemptMerge;
                }
                long j11 = (((long) i7) + segmentNum2) - 1;
                long segmentEndTimeUs = representationHolderUpdateSelectedBaseUrl.getSegmentEndTimeUs(j11);
                long j12 = representationHolderUpdateSelectedBaseUrl.periodDurationUs;
                containerMediaChunk = new ContainerMediaChunk(dataSource2, DashUtil.buildDataSpec(representation3, representationHolderUpdateSelectedBaseUrl.selectedBaseUrl.url, segmentUrl, representationHolderUpdateSelectedBaseUrl.isSegmentAvailableAtFullNetworkSpeed(j11, j7) ? 0 : 8), selectedFormat2, selectionReason2, selectionData2, segmentStartTimeUs, segmentEndTimeUs, j10, (j12 == C.TIME_UNSET || j12 > segmentEndTimeUs) ? -9223372036854775807L : j12, segmentNum2, i7, -representation3.presentationTimeOffsetUs, representationHolderUpdateSelectedBaseUrl.chunkExtractor);
            }
            chunkHolder.chunk = containerMediaChunk;
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public int getPreferredQueueSize(long j, List<? extends MediaChunk> list) {
        return (this.fatalError != null || this.trackSelection.length() < 2) ? list.size() : this.trackSelection.evaluateQueueSize(j, list);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void maybeThrowError() throws IOException {
        IOException iOException = this.fatalError;
        if (iOException != null) {
            throw iOException;
        }
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void onChunkLoadCompleted(Chunk chunk) {
        ChunkIndex chunkIndex;
        if (chunk instanceof InitializationChunk) {
            int iIndexOf = this.trackSelection.indexOf(((InitializationChunk) chunk).trackFormat);
            RepresentationHolder representationHolder = this.representationHolders[iIndexOf];
            if (representationHolder.segmentIndex == null && (chunkIndex = representationHolder.chunkExtractor.getChunkIndex()) != null) {
                this.representationHolders[iIndexOf] = representationHolder.copyWithNewSegmentIndex(new DashWrappingSegmentIndex(chunkIndex, representationHolder.representation.presentationTimeOffsetUs));
            }
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler != null) {
            playerTrackEmsgHandler.onChunkLoadCompleted(chunk);
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean onChunkLoadError(Chunk chunk, boolean z, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor;
        if (!z) {
            return false;
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler != null && playerTrackEmsgHandler.onChunkLoadError(chunk)) {
            return true;
        }
        if (!this.manifest.dynamic && (chunk instanceof MediaChunk)) {
            IOException iOException = loadErrorInfo.exception;
            if ((iOException instanceof HttpDataSource.InvalidResponseCodeException) && ((HttpDataSource.InvalidResponseCodeException) iOException).responseCode == 404) {
                RepresentationHolder representationHolder = this.representationHolders[this.trackSelection.indexOf(chunk.trackFormat)];
                long segmentCount = representationHolder.getSegmentCount();
                if (segmentCount != -1 && segmentCount != 0) {
                    if (((MediaChunk) chunk).getNextChunkIndex() > (representationHolder.getFirstSegmentNum() + segmentCount) - 1) {
                        this.missingLastSegment = true;
                        return true;
                    }
                }
            }
        }
        RepresentationHolder representationHolder2 = this.representationHolders[this.trackSelection.indexOf(chunk.trackFormat)];
        BaseUrl baseUrlSelectBaseUrl = this.baseUrlExclusionList.selectBaseUrl(representationHolder2.representation.baseUrls);
        if (baseUrlSelectBaseUrl != null && !representationHolder2.selectedBaseUrl.equals(baseUrlSelectBaseUrl)) {
            return true;
        }
        LoadErrorHandlingPolicy.FallbackOptions fallbackOptionsCreateFallbackOptions = createFallbackOptions(this.trackSelection, representationHolder2.representation.baseUrls);
        if ((!fallbackOptionsCreateFallbackOptions.isFallbackAvailable(2) && !fallbackOptionsCreateFallbackOptions.isFallbackAvailable(1)) || (fallbackSelectionFor = loadErrorHandlingPolicy.getFallbackSelectionFor(fallbackOptionsCreateFallbackOptions, loadErrorInfo)) == null || !fallbackOptionsCreateFallbackOptions.isFallbackAvailable(fallbackSelectionFor.type)) {
            return false;
        }
        int i = fallbackSelectionFor.type;
        if (i == 2) {
            ExoTrackSelection exoTrackSelection = this.trackSelection;
            return exoTrackSelection.blacklist(exoTrackSelection.indexOf(chunk.trackFormat), fallbackSelectionFor.exclusionDurationMs);
        }
        if (i != 1) {
            return false;
        }
        this.baseUrlExclusionList.exclude(representationHolder2.selectedBaseUrl, fallbackSelectionFor.exclusionDurationMs);
        return true;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void release() {
        for (RepresentationHolder representationHolder : this.representationHolders) {
            ChunkExtractor chunkExtractor = representationHolder.chunkExtractor;
            if (chunkExtractor != null) {
                chunkExtractor.release();
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean shouldCancelLoad(long j, Chunk chunk, List<? extends MediaChunk> list) {
        if (this.fatalError != null) {
            return false;
        }
        return this.trackSelection.shouldCancelChunkLoad(j, chunk, list);
    }

    @Override // com.google.android.exoplayer2.source.dash.DashChunkSource
    public void updateManifest(DashManifest dashManifest, int i) {
        try {
            this.manifest = dashManifest;
            this.periodIndex = i;
            long periodDurationUs = dashManifest.getPeriodDurationUs(i);
            ArrayList<Representation> representations = getRepresentations();
            for (int i2 = 0; i2 < this.representationHolders.length; i2++) {
                Representation representation = representations.get(this.trackSelection.getIndexInTrackGroup(i2));
                RepresentationHolder[] representationHolderArr = this.representationHolders;
                representationHolderArr[i2] = representationHolderArr[i2].copyWithNewRepresentation(periodDurationUs, representation);
            }
        } catch (BehindLiveWindowException e) {
            this.fatalError = e;
        }
    }

    @Override // com.google.android.exoplayer2.source.dash.DashChunkSource
    public void updateTrackSelection(ExoTrackSelection exoTrackSelection) {
        this.trackSelection = exoTrackSelection;
    }
}
