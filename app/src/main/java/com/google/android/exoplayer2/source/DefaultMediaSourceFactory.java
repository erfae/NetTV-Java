package com.google.android.exoplayer2.source;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.text.SubtitleDecoderFactory;
import com.google.android.exoplayer2.text.SubtitleExtractor;
import com.google.android.exoplayer2.ui.AdViewProvider;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.search.SearchBar$$ExternalSyntheticLambda0;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.lang.reflect.GenericDeclaration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class DefaultMediaSourceFactory implements MediaSourceFactory {
    private static final String TAG = "DMediaSourceFactory";

    @Nullable
    private AdViewProvider adViewProvider;

    @Nullable
    private AdsLoader.Provider adsLoaderProvider;
    private DataSource.Factory dataSourceFactory;
    private final DelegateFactoryLoader delegateFactoryLoader;
    private long liveMaxOffsetMs;
    private float liveMaxSpeed;
    private long liveMinOffsetMs;
    private float liveMinSpeed;
    private long liveTargetOffsetMs;

    @Nullable
    private LoadErrorHandlingPolicy loadErrorHandlingPolicy;

    @Nullable
    private MediaSource.Factory serverSideAdInsertionMediaSourceFactory;
    private boolean useProgressiveMediaSourceForSubtitles;

    @Deprecated
    public interface AdsLoaderProvider extends AdsLoader.Provider {
    }

    public static final class DelegateFactoryLoader {
        private DataSource.Factory dataSourceFactory;

        @Nullable
        private DrmSessionManagerProvider drmSessionManagerProvider;
        private final ExtractorsFactory extractorsFactory;

        @Nullable
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private final Map<Integer, Supplier<MediaSource.Factory>> mediaSourceFactorySuppliers = new HashMap();
        private final Set<Integer> supportedTypes = new HashSet();
        private final Map<Integer, MediaSource.Factory> mediaSourceFactories = new HashMap();

        public DelegateFactoryLoader(ExtractorsFactory extractorsFactory) {
            this.extractorsFactory = extractorsFactory;
        }

        private void ensureAllSuppliersAreLoaded() {
            maybeLoadSupplier(0);
            maybeLoadSupplier(1);
            maybeLoadSupplier(2);
            maybeLoadSupplier(3);
            maybeLoadSupplier(4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ MediaSource.Factory lambda$maybeLoadSupplier$4(DataSource.Factory factory) {
            return new ProgressiveMediaSource.Factory(factory, this.extractorsFactory);
        }

        @Nullable
        private Supplier<MediaSource.Factory> maybeLoadSupplier(int i) {
            Supplier<MediaSource.Factory> supplier;
            if (this.mediaSourceFactorySuppliers.containsKey(Integer.valueOf(i))) {
                return this.mediaSourceFactorySuppliers.get(Integer.valueOf(i));
            }
            Supplier<MediaSource.Factory> supplier2 = null;
            final DataSource.Factory factory = (DataSource.Factory) Assertions.checkNotNull(this.dataSourceFactory);
            try {
                if (i != 0) {
                    final int i2 = 1;
                    if (i != 1) {
                        final int i3 = 2;
                        if (i != 2) {
                            final int i4 = 3;
                            if (i == 3) {
                                final Class<? extends U> clsAsSubclass = Class.forName("com.google.android.exoplayer2.source.rtsp.RtspMediaSource$Factory").asSubclass(MediaSource.Factory.class);
                                supplier2 = new Supplier() { // from class: com.google.android.exoplayer2.source.DefaultMediaSourceFactory$DelegateFactoryLoader$$ExternalSyntheticLambda1
                                    @Override // com.google.common.base.Supplier
                                    public final Object get() {
                                        return DefaultMediaSourceFactory.access$000(clsAsSubclass);
                                    }
                                };
                            } else if (i == 4) {
                                supplier2 = new Supplier() { // from class: com.google.android.exoplayer2.source.DefaultMediaSourceFactory$DelegateFactoryLoader$$ExternalSyntheticLambda0
                                    @Override // com.google.common.base.Supplier
                                    public final Object get() {
                                        switch (i4) {
                                            case 0:
                                                return DefaultMediaSourceFactory.access$100((Class) this, factory);
                                            case 1:
                                                return DefaultMediaSourceFactory.access$100((Class) this, factory);
                                            case 2:
                                                return DefaultMediaSourceFactory.access$100((Class) this, factory);
                                            default:
                                                return ((DefaultMediaSourceFactory.DelegateFactoryLoader) this).lambda$maybeLoadSupplier$4(factory);
                                        }
                                    }
                                };
                            }
                        } else {
                            final Class clsAsSubclass2 = HlsMediaSource.Factory.class.asSubclass(MediaSource.Factory.class);
                            supplier = new Supplier() { // from class: com.google.android.exoplayer2.source.DefaultMediaSourceFactory$DelegateFactoryLoader$$ExternalSyntheticLambda0
                                @Override // com.google.common.base.Supplier
                                public final Object get() {
                                    switch (i3) {
                                        case 0:
                                            return DefaultMediaSourceFactory.access$100((Class) clsAsSubclass2, factory);
                                        case 1:
                                            return DefaultMediaSourceFactory.access$100((Class) clsAsSubclass2, factory);
                                        case 2:
                                            return DefaultMediaSourceFactory.access$100((Class) clsAsSubclass2, factory);
                                        default:
                                            return ((DefaultMediaSourceFactory.DelegateFactoryLoader) clsAsSubclass2).lambda$maybeLoadSupplier$4(factory);
                                    }
                                }
                            };
                        }
                    } else {
                        final GenericDeclaration genericDeclarationAsSubclass = Class.forName("com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource$Factory").asSubclass(MediaSource.Factory.class);
                        supplier = new Supplier() { // from class: com.google.android.exoplayer2.source.DefaultMediaSourceFactory$DelegateFactoryLoader$$ExternalSyntheticLambda0
                            @Override // com.google.common.base.Supplier
                            public final Object get() {
                                switch (i2) {
                                    case 0:
                                        return DefaultMediaSourceFactory.access$100((Class) genericDeclarationAsSubclass, factory);
                                    case 1:
                                        return DefaultMediaSourceFactory.access$100((Class) genericDeclarationAsSubclass, factory);
                                    case 2:
                                        return DefaultMediaSourceFactory.access$100((Class) genericDeclarationAsSubclass, factory);
                                    default:
                                        return ((DefaultMediaSourceFactory.DelegateFactoryLoader) genericDeclarationAsSubclass).lambda$maybeLoadSupplier$4(factory);
                                }
                            }
                        };
                    }
                    supplier2 = supplier;
                } else {
                    final Class clsAsSubclass3 = DashMediaSource.Factory.class.asSubclass(MediaSource.Factory.class);
                    final int i5 = 0;
                    supplier2 = new Supplier() { // from class: com.google.android.exoplayer2.source.DefaultMediaSourceFactory$DelegateFactoryLoader$$ExternalSyntheticLambda0
                        @Override // com.google.common.base.Supplier
                        public final Object get() {
                            switch (i5) {
                                case 0:
                                    return DefaultMediaSourceFactory.access$100((Class) clsAsSubclass3, factory);
                                case 1:
                                    return DefaultMediaSourceFactory.access$100((Class) clsAsSubclass3, factory);
                                case 2:
                                    return DefaultMediaSourceFactory.access$100((Class) clsAsSubclass3, factory);
                                default:
                                    return ((DefaultMediaSourceFactory.DelegateFactoryLoader) clsAsSubclass3).lambda$maybeLoadSupplier$4(factory);
                            }
                        }
                    };
                }
            } catch (ClassNotFoundException unused) {
            }
            this.mediaSourceFactorySuppliers.put(Integer.valueOf(i), supplier2);
            if (supplier2 != null) {
                this.supportedTypes.add(Integer.valueOf(i));
            }
            return supplier2;
        }

        @Nullable
        public MediaSource.Factory getMediaSourceFactory(int i) {
            MediaSource.Factory factory = this.mediaSourceFactories.get(Integer.valueOf(i));
            if (factory != null) {
                return factory;
            }
            Supplier<MediaSource.Factory> supplierMaybeLoadSupplier = maybeLoadSupplier(i);
            if (supplierMaybeLoadSupplier == null) {
                return null;
            }
            MediaSource.Factory factory2 = supplierMaybeLoadSupplier.get();
            DrmSessionManagerProvider drmSessionManagerProvider = this.drmSessionManagerProvider;
            if (drmSessionManagerProvider != null) {
                factory2.setDrmSessionManagerProvider(drmSessionManagerProvider);
            }
            LoadErrorHandlingPolicy loadErrorHandlingPolicy = this.loadErrorHandlingPolicy;
            if (loadErrorHandlingPolicy != null) {
                factory2.setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
            }
            this.mediaSourceFactories.put(Integer.valueOf(i), factory2);
            return factory2;
        }

        public int[] getSupportedTypes() {
            ensureAllSuppliersAreLoaded();
            return Ints.toArray(this.supportedTypes);
        }

        public void setDataSourceFactory(DataSource.Factory factory) {
            if (factory != this.dataSourceFactory) {
                this.dataSourceFactory = factory;
                this.mediaSourceFactorySuppliers.clear();
                this.mediaSourceFactories.clear();
            }
        }

        public void setDrmSessionManagerProvider(DrmSessionManagerProvider drmSessionManagerProvider) {
            this.drmSessionManagerProvider = drmSessionManagerProvider;
            Iterator<MediaSource.Factory> it = this.mediaSourceFactories.values().iterator();
            while (it.hasNext()) {
                it.next().setDrmSessionManagerProvider(drmSessionManagerProvider);
            }
        }

        public void setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
            Iterator<MediaSource.Factory> it = this.mediaSourceFactories.values().iterator();
            while (it.hasNext()) {
                it.next().setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
            }
        }
    }

    public static final class UnknownSubtitlesExtractor implements Extractor {
        private final Format format;

        public UnknownSubtitlesExtractor(Format format) {
            this.format = format;
        }

        @Override // com.google.android.exoplayer2.extractor.Extractor
        public void init(ExtractorOutput extractorOutput) {
            TrackOutput trackOutputTrack = extractorOutput.track(0, 3);
            extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
            extractorOutput.endTracks();
            trackOutputTrack.format(this.format.buildUpon().setSampleMimeType(MimeTypes.TEXT_UNKNOWN).setCodecs(this.format.sampleMimeType).build());
        }

        @Override // com.google.android.exoplayer2.extractor.Extractor
        public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
            return extractorInput.skip(Integer.MAX_VALUE) == -1 ? -1 : 0;
        }

        @Override // com.google.android.exoplayer2.extractor.Extractor
        public void release() {
        }

        @Override // com.google.android.exoplayer2.extractor.Extractor
        public void seek(long j, long j2) {
        }

        @Override // com.google.android.exoplayer2.extractor.Extractor
        public boolean sniff(ExtractorInput extractorInput) {
            return true;
        }
    }

    public DefaultMediaSourceFactory(Context context) {
        this(new DefaultDataSource.Factory(context));
    }

    public static /* synthetic */ MediaSource.Factory access$000(Class cls) {
        return newInstance(cls);
    }

    public static /* synthetic */ MediaSource.Factory access$100(Class cls, DataSource.Factory factory) {
        return newInstance(cls, factory);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] lambda$createMediaSource$0(Format format) {
        Extractor[] extractorArr = new Extractor[1];
        SubtitleDecoderFactory subtitleDecoderFactory = SubtitleDecoderFactory.DEFAULT;
        extractorArr[0] = subtitleDecoderFactory.supportsFormat(format) ? new SubtitleExtractor(subtitleDecoderFactory.createDecoder(format), format) : new UnknownSubtitlesExtractor(format);
        return extractorArr;
    }

    private static MediaSource maybeClipMediaSource(MediaItem mediaItem, MediaSource mediaSource) {
        MediaItem.ClippingConfiguration clippingConfiguration = mediaItem.clippingConfiguration;
        long j = clippingConfiguration.startPositionMs;
        if (j == 0 && clippingConfiguration.endPositionMs == Long.MIN_VALUE && !clippingConfiguration.relativeToDefaultPosition) {
            return mediaSource;
        }
        long jMsToUs = Util.msToUs(j);
        long jMsToUs2 = Util.msToUs(mediaItem.clippingConfiguration.endPositionMs);
        MediaItem.ClippingConfiguration clippingConfiguration2 = mediaItem.clippingConfiguration;
        return new ClippingMediaSource(mediaSource, jMsToUs, jMsToUs2, !clippingConfiguration2.startsAtKeyFrame, clippingConfiguration2.relativeToLiveWindow, clippingConfiguration2.relativeToDefaultPosition);
    }

    private MediaSource maybeWrapWithAdsMediaSource(MediaItem mediaItem, MediaSource mediaSource) {
        Assertions.checkNotNull(mediaItem.localConfiguration);
        MediaItem.AdsConfiguration adsConfiguration = mediaItem.localConfiguration.adsConfiguration;
        if (adsConfiguration == null) {
            return mediaSource;
        }
        AdsLoader.Provider provider = this.adsLoaderProvider;
        AdViewProvider adViewProvider = this.adViewProvider;
        if (provider == null || adViewProvider == null) {
            Log.w(TAG, "Playing media without ads. Configure ad support by calling setAdsLoaderProvider and setAdViewProvider.");
            return mediaSource;
        }
        AdsLoader adsLoader = provider.getAdsLoader(adsConfiguration);
        if (adsLoader == null) {
            Log.w(TAG, "Playing media without ads, as no AdsLoader was provided.");
            return mediaSource;
        }
        DataSpec dataSpec = new DataSpec(adsConfiguration.adTagUri);
        Object obj = adsConfiguration.adsId;
        return new AdsMediaSource(mediaSource, dataSpec, obj != null ? obj : ImmutableList.of((Uri) mediaItem.mediaId, mediaItem.localConfiguration.uri, adsConfiguration.adTagUri), this, adsLoader, adViewProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaSource.Factory newInstance(Class<? extends MediaSource.Factory> cls, DataSource.Factory factory) {
        try {
            return cls.getConstructor(DataSource.Factory.class).newInstance(factory);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory clearLocalAdInsertionComponents() {
        this.adsLoaderProvider = null;
        this.adViewProvider = null;
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource.Factory
    public MediaSource createMediaSource(MediaItem mediaItem) {
        Assertions.checkNotNull(mediaItem.localConfiguration);
        String scheme = mediaItem.localConfiguration.uri.getScheme();
        if (scheme != null && scheme.equals(C.SSAI_SCHEME)) {
            return ((MediaSource.Factory) Assertions.checkNotNull(this.serverSideAdInsertionMediaSourceFactory)).createMediaSource(mediaItem);
        }
        MediaItem.LocalConfiguration localConfiguration = mediaItem.localConfiguration;
        int iInferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(localConfiguration.uri, localConfiguration.mimeType);
        MediaSource.Factory mediaSourceFactory = this.delegateFactoryLoader.getMediaSourceFactory(iInferContentTypeForUriAndMimeType);
        Assertions.checkStateNotNull(mediaSourceFactory, "No suitable media source factory found for content type: " + iInferContentTypeForUriAndMimeType);
        MediaItem.LiveConfiguration.Builder builderBuildUpon = mediaItem.liveConfiguration.buildUpon();
        if (mediaItem.liveConfiguration.targetOffsetMs == C.TIME_UNSET) {
            builderBuildUpon.setTargetOffsetMs(this.liveTargetOffsetMs);
        }
        if (mediaItem.liveConfiguration.minPlaybackSpeed == -3.4028235E38f) {
            builderBuildUpon.setMinPlaybackSpeed(this.liveMinSpeed);
        }
        if (mediaItem.liveConfiguration.maxPlaybackSpeed == -3.4028235E38f) {
            builderBuildUpon.setMaxPlaybackSpeed(this.liveMaxSpeed);
        }
        if (mediaItem.liveConfiguration.minOffsetMs == C.TIME_UNSET) {
            builderBuildUpon.setMinOffsetMs(this.liveMinOffsetMs);
        }
        if (mediaItem.liveConfiguration.maxOffsetMs == C.TIME_UNSET) {
            builderBuildUpon.setMaxOffsetMs(this.liveMaxOffsetMs);
        }
        MediaItem.LiveConfiguration liveConfigurationBuild = builderBuildUpon.build();
        if (!liveConfigurationBuild.equals(mediaItem.liveConfiguration)) {
            mediaItem = mediaItem.buildUpon().setLiveConfiguration(liveConfigurationBuild).build();
        }
        MediaSource mediaSourceCreateMediaSource = mediaSourceFactory.createMediaSource(mediaItem);
        ImmutableList<MediaItem.SubtitleConfiguration> immutableList = ((MediaItem.LocalConfiguration) Util.castNonNull(mediaItem.localConfiguration)).subtitleConfigurations;
        if (!immutableList.isEmpty()) {
            MediaSource[] mediaSourceArr = new MediaSource[immutableList.size() + 1];
            mediaSourceArr[0] = mediaSourceCreateMediaSource;
            for (int i = 0; i < immutableList.size(); i++) {
                if (this.useProgressiveMediaSourceForSubtitles) {
                    ProgressiveMediaSource.Factory factory = new ProgressiveMediaSource.Factory(this.dataSourceFactory, (ExtractorsFactory) new SearchBar$$ExternalSyntheticLambda0(new Format.Builder().setSampleMimeType(immutableList.get(i).mimeType).setLanguage(immutableList.get(i).language).setSelectionFlags(immutableList.get(i).selectionFlags).setRoleFlags(immutableList.get(i).roleFlags).setLabel(immutableList.get(i).label).setId(immutableList.get(i).id).build()));
                    LoadErrorHandlingPolicy loadErrorHandlingPolicy = this.loadErrorHandlingPolicy;
                    if (loadErrorHandlingPolicy != null) {
                        factory.setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
                    }
                    mediaSourceArr[i + 1] = factory.createMediaSource(MediaItem.fromUri(immutableList.get(i).uri.toString()));
                } else {
                    SingleSampleMediaSource.Factory factory2 = new SingleSampleMediaSource.Factory(this.dataSourceFactory);
                    LoadErrorHandlingPolicy loadErrorHandlingPolicy2 = this.loadErrorHandlingPolicy;
                    if (loadErrorHandlingPolicy2 != null) {
                        factory2.setLoadErrorHandlingPolicy(loadErrorHandlingPolicy2);
                    }
                    mediaSourceArr[i + 1] = factory2.createMediaSource(immutableList.get(i), C.TIME_UNSET);
                }
            }
            mediaSourceCreateMediaSource = new MergingMediaSource(mediaSourceArr);
        }
        return maybeWrapWithAdsMediaSource(mediaItem, maybeClipMediaSource(mediaItem, mediaSourceCreateMediaSource));
    }

    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory experimentalUseProgressiveMediaSourceForSubtitles(boolean z) {
        this.useProgressiveMediaSourceForSubtitles = z;
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource.Factory
    public int[] getSupportedTypes() {
        return this.delegateFactoryLoader.getSupportedTypes();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public DefaultMediaSourceFactory setAdViewProvider(@Nullable AdViewProvider adViewProvider) {
        this.adViewProvider = adViewProvider;
        return this;
    }

    @CanIgnoreReturnValue
    @Deprecated
    public DefaultMediaSourceFactory setAdsLoaderProvider(@Nullable AdsLoader.Provider provider) {
        this.adsLoaderProvider = provider;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory setDataSourceFactory(DataSource.Factory factory) {
        this.dataSourceFactory = factory;
        this.delegateFactoryLoader.setDataSourceFactory(factory);
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory setLiveMaxOffsetMs(long j) {
        this.liveMaxOffsetMs = j;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory setLiveMaxSpeed(float f) {
        this.liveMaxSpeed = f;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory setLiveMinOffsetMs(long j) {
        this.liveMinOffsetMs = j;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory setLiveMinSpeed(float f) {
        this.liveMinSpeed = f;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory setLiveTargetOffsetMs(long j) {
        this.liveTargetOffsetMs = j;
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory setLocalAdInsertionComponents(AdsLoader.Provider provider, AdViewProvider adViewProvider) {
        this.adsLoaderProvider = (AdsLoader.Provider) Assertions.checkNotNull(provider);
        this.adViewProvider = (AdViewProvider) Assertions.checkNotNull(adViewProvider);
        return this;
    }

    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory setServerSideAdInsertionMediaSourceFactory(@Nullable MediaSource.Factory factory) {
        this.serverSideAdInsertionMediaSourceFactory = factory;
        return this;
    }

    public DefaultMediaSourceFactory(Context context, ExtractorsFactory extractorsFactory) {
        this(new DefaultDataSource.Factory(context), extractorsFactory);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource.Factory
    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory setDrmSessionManagerProvider(DrmSessionManagerProvider drmSessionManagerProvider) {
        this.delegateFactoryLoader.setDrmSessionManagerProvider((DrmSessionManagerProvider) Assertions.checkNotNull(drmSessionManagerProvider, "MediaSource.Factory#setDrmSessionManagerProvider no longer handles null by instantiating a new DefaultDrmSessionManagerProvider. Explicitly construct and pass an instance in order to retain the old behavior."));
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource.Factory
    @CanIgnoreReturnValue
    public DefaultMediaSourceFactory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        this.loadErrorHandlingPolicy = (LoadErrorHandlingPolicy) Assertions.checkNotNull(loadErrorHandlingPolicy, "MediaSource.Factory#setLoadErrorHandlingPolicy no longer handles null by instantiating a new DefaultLoadErrorHandlingPolicy. Explicitly construct and pass an instance in order to retain the old behavior.");
        this.delegateFactoryLoader.setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
        return this;
    }

    public DefaultMediaSourceFactory(DataSource.Factory factory) {
        this(factory, new DefaultExtractorsFactory());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaSource.Factory newInstance(Class<? extends MediaSource.Factory> cls) {
        try {
            return cls.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public DefaultMediaSourceFactory(DataSource.Factory factory, ExtractorsFactory extractorsFactory) {
        this.dataSourceFactory = factory;
        DelegateFactoryLoader delegateFactoryLoader = new DelegateFactoryLoader(extractorsFactory);
        this.delegateFactoryLoader = delegateFactoryLoader;
        delegateFactoryLoader.setDataSourceFactory(factory);
        this.liveTargetOffsetMs = C.TIME_UNSET;
        this.liveMinOffsetMs = C.TIME_UNSET;
        this.liveMaxOffsetMs = C.TIME_UNSET;
        this.liveMinSpeed = -3.4028235E38f;
        this.liveMaxSpeed = -3.4028235E38f;
    }
}
