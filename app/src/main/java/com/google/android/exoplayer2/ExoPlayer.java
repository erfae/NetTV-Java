package com.google.android.exoplayer2;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.DefaultAnalyticsCollector;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.CueGroup;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface ExoPlayer extends Player {
    public static final long DEFAULT_DETACH_SURFACE_TIMEOUT_MS = 2000;
    public static final long DEFAULT_RELEASE_TIMEOUT_MS = 500;

    /* JADX INFO: renamed from: com.google.android.exoplayer2.ExoPlayer$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
    }

    @Deprecated
    public interface AudioComponent {
        @Deprecated
        void clearAuxEffectInfo();

        @Deprecated
        AudioAttributes getAudioAttributes();

        @Deprecated
        int getAudioSessionId();

        @Deprecated
        boolean getSkipSilenceEnabled();

        @Deprecated
        float getVolume();

        @Deprecated
        void setAudioAttributes(AudioAttributes audioAttributes, boolean z);

        @Deprecated
        void setAudioSessionId(int i);

        @Deprecated
        void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

        @Deprecated
        void setSkipSilenceEnabled(boolean z);

        @Deprecated
        void setVolume(float f);
    }

    public interface AudioOffloadListener {

        /* JADX INFO: renamed from: com.google.android.exoplayer2.ExoPlayer$AudioOffloadListener$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static void $default$onExperimentalOffloadSchedulingEnabledChanged(AudioOffloadListener audioOffloadListener, boolean z) {
            }

            public static void $default$onExperimentalOffloadedPlayback(AudioOffloadListener audioOffloadListener, boolean z) {
            }

            public static void $default$onExperimentalSleepingForOffloadChanged(AudioOffloadListener audioOffloadListener, boolean z) {
            }
        }

        void onExperimentalOffloadSchedulingEnabledChanged(boolean z);

        void onExperimentalOffloadedPlayback(boolean z);

        void onExperimentalSleepingForOffloadChanged(boolean z);
    }

    public static final class Builder {
        public Function<Clock, AnalyticsCollector> analyticsCollectorFunction;
        public AudioAttributes audioAttributes;
        public Supplier<BandwidthMeter> bandwidthMeterSupplier;
        public boolean buildCalled;
        public Clock clock;
        public final Context context;
        public long detachSurfaceTimeoutMs;
        public long foregroundModeTimeoutMs;
        public boolean handleAudioBecomingNoisy;
        public boolean handleAudioFocus;
        public LivePlaybackSpeedControl livePlaybackSpeedControl;
        public Supplier<LoadControl> loadControlSupplier;
        public Looper looper;
        public Supplier<MediaSource.Factory> mediaSourceFactorySupplier;
        public boolean pauseAtEndOfMediaItems;

        @Nullable
        public Looper playbackLooper;

        @Nullable
        public PriorityTaskManager priorityTaskManager;
        public long releaseTimeoutMs;
        public Supplier<RenderersFactory> renderersFactorySupplier;
        public long seekBackIncrementMs;
        public long seekForwardIncrementMs;
        public SeekParameters seekParameters;
        public boolean skipSilenceEnabled;
        public Supplier<TrackSelector> trackSelectorSupplier;
        public boolean useLazyPreparation;
        public boolean usePlatformDiagnostics;
        public int videoChangeFrameRateStrategy;
        public int videoScalingMode;
        public int wakeMode;

        /* JADX WARN: Illegal instructions before constructor call */
        public Builder(final Context context) {
            final int i = 2;
            final int i2 = 3;
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: com.google.android.exoplayer2.ExoPlayer$Builder$$ExternalSyntheticLambda2
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    switch (i) {
                        case 0:
                            return ExoPlayer.Builder.lambda$new$14(context);
                        case 1:
                            return DefaultBandwidthMeter.getSingletonInstance(context);
                        case 2:
                            return ExoPlayer.Builder.lambda$new$0(context);
                        case 3:
                            return ExoPlayer.Builder.lambda$new$1(context);
                        case 4:
                            return ExoPlayer.Builder.lambda$new$4(context);
                        default:
                            return ExoPlayer.Builder.lambda$new$3(context);
                    }
                }
            }, (Supplier<MediaSource.Factory>) new Supplier() { // from class: com.google.android.exoplayer2.ExoPlayer$Builder$$ExternalSyntheticLambda2
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    switch (i2) {
                        case 0:
                            return ExoPlayer.Builder.lambda$new$14(context);
                        case 1:
                            return DefaultBandwidthMeter.getSingletonInstance(context);
                        case 2:
                            return ExoPlayer.Builder.lambda$new$0(context);
                        case 3:
                            return ExoPlayer.Builder.lambda$new$1(context);
                        case 4:
                            return ExoPlayer.Builder.lambda$new$4(context);
                        default:
                            return ExoPlayer.Builder.lambda$new$3(context);
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$0(Context context) {
            return new DefaultRenderersFactory(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$new$1(Context context) {
            return new DefaultMediaSourceFactory(context, new DefaultExtractorsFactory());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ TrackSelector lambda$new$10(TrackSelector trackSelector) {
            return trackSelector;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ LoadControl lambda$new$11(LoadControl loadControl) {
            return loadControl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ BandwidthMeter lambda$new$12(BandwidthMeter bandwidthMeter) {
            return bandwidthMeter;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ AnalyticsCollector lambda$new$13(AnalyticsCollector analyticsCollector, Clock clock) {
            return analyticsCollector;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ TrackSelector lambda$new$14(Context context) {
            return new DefaultTrackSelector(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$2(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$new$3(Context context) {
            return new DefaultMediaSourceFactory(context, new DefaultExtractorsFactory());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$4(Context context) {
            return new DefaultRenderersFactory(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$new$5(MediaSource.Factory factory) {
            return factory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$6(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$new$7(MediaSource.Factory factory) {
            return factory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$8(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$new$9(MediaSource.Factory factory) {
            return factory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ AnalyticsCollector lambda$setAnalyticsCollector$21(AnalyticsCollector analyticsCollector, Clock clock) {
            return analyticsCollector;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ BandwidthMeter lambda$setBandwidthMeter$20(BandwidthMeter bandwidthMeter) {
            return bandwidthMeter;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ LoadControl lambda$setLoadControl$19(LoadControl loadControl) {
            return loadControl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSource.Factory lambda$setMediaSourceFactory$17(MediaSource.Factory factory) {
            return factory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$setRenderersFactory$16(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ TrackSelector lambda$setTrackSelector$18(TrackSelector trackSelector) {
            return trackSelector;
        }

        public ExoPlayer build() {
            Assertions.checkState(!this.buildCalled);
            this.buildCalled = true;
            return new ExoPlayerImpl(this, null);
        }

        @CanIgnoreReturnValue
        public Builder experimentalSetForegroundModeTimeoutMs(long j) {
            Assertions.checkState(!this.buildCalled);
            this.foregroundModeTimeoutMs = j;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setAnalyticsCollector(AnalyticsCollector analyticsCollector) {
            Assertions.checkState(!this.buildCalled);
            Assertions.checkNotNull(analyticsCollector);
            this.analyticsCollectorFunction = new ExoPlayer$Builder$$ExternalSyntheticLambda0(analyticsCollector, 0);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setAudioAttributes(AudioAttributes audioAttributes, boolean z) {
            Assertions.checkState(!this.buildCalled);
            this.audioAttributes = (AudioAttributes) Assertions.checkNotNull(audioAttributes);
            this.handleAudioFocus = z;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setBandwidthMeter(BandwidthMeter bandwidthMeter) {
            Assertions.checkState(!this.buildCalled);
            Assertions.checkNotNull(bandwidthMeter);
            this.bandwidthMeterSupplier = new ExoPlayer$Builder$$ExternalSyntheticLambda7(bandwidthMeter, 0);
            return this;
        }

        @CanIgnoreReturnValue
        @VisibleForTesting
        public Builder setClock(Clock clock) {
            Assertions.checkState(!this.buildCalled);
            this.clock = clock;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setDetachSurfaceTimeoutMs(long j) {
            Assertions.checkState(!this.buildCalled);
            this.detachSurfaceTimeoutMs = j;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setHandleAudioBecomingNoisy(boolean z) {
            Assertions.checkState(!this.buildCalled);
            this.handleAudioBecomingNoisy = z;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setLivePlaybackSpeedControl(LivePlaybackSpeedControl livePlaybackSpeedControl) {
            Assertions.checkState(!this.buildCalled);
            this.livePlaybackSpeedControl = (LivePlaybackSpeedControl) Assertions.checkNotNull(livePlaybackSpeedControl);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setLoadControl(LoadControl loadControl) {
            Assertions.checkState(!this.buildCalled);
            Assertions.checkNotNull(loadControl);
            this.loadControlSupplier = new ExoPlayer$Builder$$ExternalSyntheticLambda3(loadControl, 0);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setLooper(Looper looper) {
            Assertions.checkState(!this.buildCalled);
            Assertions.checkNotNull(looper);
            this.looper = looper;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMediaSourceFactory(MediaSource.Factory factory) {
            Assertions.checkState(!this.buildCalled);
            Assertions.checkNotNull(factory);
            this.mediaSourceFactorySupplier = new ExoPlayer$Builder$$ExternalSyntheticLambda5(factory, 0);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPauseAtEndOfMediaItems(boolean z) {
            Assertions.checkState(!this.buildCalled);
            this.pauseAtEndOfMediaItems = z;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPlaybackLooper(Looper looper) {
            Assertions.checkState(!this.buildCalled);
            this.playbackLooper = looper;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
            Assertions.checkState(!this.buildCalled);
            this.priorityTaskManager = priorityTaskManager;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setReleaseTimeoutMs(long j) {
            Assertions.checkState(!this.buildCalled);
            this.releaseTimeoutMs = j;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setRenderersFactory(RenderersFactory renderersFactory) {
            Assertions.checkState(!this.buildCalled);
            Assertions.checkNotNull(renderersFactory);
            this.renderersFactorySupplier = new ExoPlayer$Builder$$ExternalSyntheticLambda4(renderersFactory, 0);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setSeekBackIncrementMs(@IntRange(from = 1) long j) {
            Assertions.checkArgument(j > 0);
            Assertions.checkState(true ^ this.buildCalled);
            this.seekBackIncrementMs = j;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setSeekForwardIncrementMs(@IntRange(from = 1) long j) {
            Assertions.checkArgument(j > 0);
            Assertions.checkState(true ^ this.buildCalled);
            this.seekForwardIncrementMs = j;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setSeekParameters(SeekParameters seekParameters) {
            Assertions.checkState(!this.buildCalled);
            this.seekParameters = (SeekParameters) Assertions.checkNotNull(seekParameters);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setSkipSilenceEnabled(boolean z) {
            Assertions.checkState(!this.buildCalled);
            this.skipSilenceEnabled = z;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setTrackSelector(TrackSelector trackSelector) {
            Assertions.checkState(!this.buildCalled);
            Assertions.checkNotNull(trackSelector);
            this.trackSelectorSupplier = new ExoPlayer$Builder$$ExternalSyntheticLambda6(trackSelector, 0);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setUseLazyPreparation(boolean z) {
            Assertions.checkState(!this.buildCalled);
            this.useLazyPreparation = z;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setUsePlatformDiagnostics(boolean z) {
            Assertions.checkState(!this.buildCalled);
            this.usePlatformDiagnostics = z;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setVideoChangeFrameRateStrategy(int i) {
            Assertions.checkState(!this.buildCalled);
            this.videoChangeFrameRateStrategy = i;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setVideoScalingMode(int i) {
            Assertions.checkState(!this.buildCalled);
            this.videoScalingMode = i;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setWakeMode(int i) {
            Assertions.checkState(!this.buildCalled);
            this.wakeMode = i;
            return this;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public Builder(final Context context, RenderersFactory renderersFactory) {
            final int i = 5;
            this(context, new ExoPlayer$Builder$$ExternalSyntheticLambda4(renderersFactory, 2), (Supplier<MediaSource.Factory>) new Supplier() { // from class: com.google.android.exoplayer2.ExoPlayer$Builder$$ExternalSyntheticLambda2
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    switch (i) {
                        case 0:
                            return ExoPlayer.Builder.lambda$new$14(context);
                        case 1:
                            return DefaultBandwidthMeter.getSingletonInstance(context);
                        case 2:
                            return ExoPlayer.Builder.lambda$new$0(context);
                        case 3:
                            return ExoPlayer.Builder.lambda$new$1(context);
                        case 4:
                            return ExoPlayer.Builder.lambda$new$4(context);
                        default:
                            return ExoPlayer.Builder.lambda$new$3(context);
                    }
                }
            });
            Assertions.checkNotNull(renderersFactory);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public Builder(final Context context, MediaSource.Factory factory) {
            final int i = 4;
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: com.google.android.exoplayer2.ExoPlayer$Builder$$ExternalSyntheticLambda2
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    switch (i) {
                        case 0:
                            return ExoPlayer.Builder.lambda$new$14(context);
                        case 1:
                            return DefaultBandwidthMeter.getSingletonInstance(context);
                        case 2:
                            return ExoPlayer.Builder.lambda$new$0(context);
                        case 3:
                            return ExoPlayer.Builder.lambda$new$1(context);
                        case 4:
                            return ExoPlayer.Builder.lambda$new$4(context);
                        default:
                            return ExoPlayer.Builder.lambda$new$3(context);
                    }
                }
            }, new ExoPlayer$Builder$$ExternalSyntheticLambda5(factory, 1));
            Assertions.checkNotNull(factory);
        }

        public Builder(Context context, RenderersFactory renderersFactory, MediaSource.Factory factory) {
            this(context, new ExoPlayer$Builder$$ExternalSyntheticLambda4(renderersFactory, 1), new ExoPlayer$Builder$$ExternalSyntheticLambda5(factory, 2));
            Assertions.checkNotNull(renderersFactory);
            Assertions.checkNotNull(factory);
        }

        public Builder(Context context, RenderersFactory renderersFactory, MediaSource.Factory factory, TrackSelector trackSelector, LoadControl loadControl, BandwidthMeter bandwidthMeter, AnalyticsCollector analyticsCollector) {
            int i = 3;
            ExoPlayer$Builder$$ExternalSyntheticLambda4 exoPlayer$Builder$$ExternalSyntheticLambda4 = new ExoPlayer$Builder$$ExternalSyntheticLambda4(renderersFactory, i);
            ExoPlayer$Builder$$ExternalSyntheticLambda5 exoPlayer$Builder$$ExternalSyntheticLambda5 = new ExoPlayer$Builder$$ExternalSyntheticLambda5(factory, i);
            int i2 = 1;
            this(context, exoPlayer$Builder$$ExternalSyntheticLambda4, exoPlayer$Builder$$ExternalSyntheticLambda5, new ExoPlayer$Builder$$ExternalSyntheticLambda6(trackSelector, i2), new ExoPlayer$Builder$$ExternalSyntheticLambda3(loadControl, i2), new ExoPlayer$Builder$$ExternalSyntheticLambda7(bandwidthMeter, i2), new ExoPlayer$Builder$$ExternalSyntheticLambda0(analyticsCollector, i2));
            Assertions.checkNotNull(renderersFactory);
            Assertions.checkNotNull(factory);
            Assertions.checkNotNull(trackSelector);
            Assertions.checkNotNull(bandwidthMeter);
            Assertions.checkNotNull(analyticsCollector);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        private Builder(final Context context, Supplier<RenderersFactory> supplier, Supplier<MediaSource.Factory> supplier2) {
            final int i = 0;
            final int i2 = 1;
            this(context, supplier, supplier2, (Supplier<TrackSelector>) new Supplier() { // from class: com.google.android.exoplayer2.ExoPlayer$Builder$$ExternalSyntheticLambda2
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    switch (i) {
                        case 0:
                            return ExoPlayer.Builder.lambda$new$14(context);
                        case 1:
                            return DefaultBandwidthMeter.getSingletonInstance(context);
                        case 2:
                            return ExoPlayer.Builder.lambda$new$0(context);
                        case 3:
                            return ExoPlayer.Builder.lambda$new$1(context);
                        case 4:
                            return ExoPlayer.Builder.lambda$new$4(context);
                        default:
                            return ExoPlayer.Builder.lambda$new$3(context);
                    }
                }
            }, new Supplier() { // from class: com.google.android.exoplayer2.ExoPlayer$Builder$$ExternalSyntheticLambda8
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return new DefaultLoadControl();
                }
            }, (Supplier<BandwidthMeter>) new Supplier() { // from class: com.google.android.exoplayer2.ExoPlayer$Builder$$ExternalSyntheticLambda2
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    switch (i2) {
                        case 0:
                            return ExoPlayer.Builder.lambda$new$14(context);
                        case 1:
                            return DefaultBandwidthMeter.getSingletonInstance(context);
                        case 2:
                            return ExoPlayer.Builder.lambda$new$0(context);
                        case 3:
                            return ExoPlayer.Builder.lambda$new$1(context);
                        case 4:
                            return ExoPlayer.Builder.lambda$new$4(context);
                        default:
                            return ExoPlayer.Builder.lambda$new$3(context);
                    }
                }
            }, new Function() { // from class: com.google.android.exoplayer2.ExoPlayer$Builder$$ExternalSyntheticLambda1
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return new DefaultAnalyticsCollector((Clock) obj);
                }
            });
        }

        private Builder(Context context, Supplier<RenderersFactory> supplier, Supplier<MediaSource.Factory> supplier2, Supplier<TrackSelector> supplier3, Supplier<LoadControl> supplier4, Supplier<BandwidthMeter> supplier5, Function<Clock, AnalyticsCollector> function) {
            this.context = (Context) Assertions.checkNotNull(context);
            this.renderersFactorySupplier = supplier;
            this.mediaSourceFactorySupplier = supplier2;
            this.trackSelectorSupplier = supplier3;
            this.loadControlSupplier = supplier4;
            this.bandwidthMeterSupplier = supplier5;
            this.analyticsCollectorFunction = function;
            this.looper = Util.getCurrentOrMainLooper();
            this.audioAttributes = AudioAttributes.DEFAULT;
            this.wakeMode = 0;
            this.videoScalingMode = 1;
            this.videoChangeFrameRateStrategy = 0;
            this.useLazyPreparation = true;
            this.seekParameters = SeekParameters.DEFAULT;
            this.seekBackIncrementMs = 5000L;
            this.seekForwardIncrementMs = C.DEFAULT_SEEK_FORWARD_INCREMENT_MS;
            this.livePlaybackSpeedControl = new DefaultLivePlaybackSpeedControl.Builder().build();
            this.clock = Clock.DEFAULT;
            this.releaseTimeoutMs = 500L;
            this.detachSurfaceTimeoutMs = ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS;
            this.usePlatformDiagnostics = true;
        }
    }

    @Deprecated
    public interface DeviceComponent {
        @Deprecated
        void decreaseDeviceVolume();

        @Deprecated
        DeviceInfo getDeviceInfo();

        @Deprecated
        int getDeviceVolume();

        @Deprecated
        void increaseDeviceVolume();

        @Deprecated
        boolean isDeviceMuted();

        @Deprecated
        void setDeviceMuted(boolean z);

        @Deprecated
        void setDeviceVolume(int i);
    }

    @Deprecated
    public interface TextComponent {
        @Deprecated
        CueGroup getCurrentCues();
    }

    @Deprecated
    public interface VideoComponent {
        @Deprecated
        void clearCameraMotionListener(CameraMotionListener cameraMotionListener);

        @Deprecated
        void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        @Deprecated
        void clearVideoSurface();

        @Deprecated
        void clearVideoSurface(@Nullable Surface surface);

        @Deprecated
        void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

        @Deprecated
        void clearVideoSurfaceView(@Nullable SurfaceView surfaceView);

        @Deprecated
        void clearVideoTextureView(@Nullable TextureView textureView);

        @Deprecated
        int getVideoChangeFrameRateStrategy();

        @Deprecated
        int getVideoScalingMode();

        @Deprecated
        VideoSize getVideoSize();

        @Deprecated
        void setCameraMotionListener(CameraMotionListener cameraMotionListener);

        @Deprecated
        void setVideoChangeFrameRateStrategy(int i);

        @Deprecated
        void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        @Deprecated
        void setVideoScalingMode(int i);

        @Deprecated
        void setVideoSurface(@Nullable Surface surface);

        @Deprecated
        void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

        @Deprecated
        void setVideoSurfaceView(@Nullable SurfaceView surfaceView);

        @Deprecated
        void setVideoTextureView(@Nullable TextureView textureView);
    }

    void addAnalyticsListener(AnalyticsListener analyticsListener);

    void addAudioOffloadListener(AudioOffloadListener audioOffloadListener);

    void addMediaSource(int i, MediaSource mediaSource);

    void addMediaSource(MediaSource mediaSource);

    void addMediaSources(int i, List<MediaSource> list);

    void addMediaSources(List<MediaSource> list);

    void clearAuxEffectInfo();

    void clearCameraMotionListener(CameraMotionListener cameraMotionListener);

    void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

    PlayerMessage createMessage(PlayerMessage.Target target);

    boolean experimentalIsSleepingForOffload();

    void experimentalSetOffloadSchedulingEnabled(boolean z);

    AnalyticsCollector getAnalyticsCollector();

    @Nullable
    @Deprecated
    AudioComponent getAudioComponent();

    @Nullable
    DecoderCounters getAudioDecoderCounters();

    @Nullable
    Format getAudioFormat();

    int getAudioSessionId();

    Clock getClock();

    @Deprecated
    TrackGroupArray getCurrentTrackGroups();

    @Deprecated
    TrackSelectionArray getCurrentTrackSelections();

    @Nullable
    @Deprecated
    DeviceComponent getDeviceComponent();

    boolean getPauseAtEndOfMediaItems();

    Looper getPlaybackLooper();

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    ExoPlaybackException getPlayerError();

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    /* bridge */ /* synthetic */ PlaybackException getPlayerError();

    Renderer getRenderer(int i);

    int getRendererCount();

    int getRendererType(int i);

    SeekParameters getSeekParameters();

    boolean getSkipSilenceEnabled();

    @Nullable
    @Deprecated
    TextComponent getTextComponent();

    @Nullable
    TrackSelector getTrackSelector();

    int getVideoChangeFrameRateStrategy();

    @Nullable
    @Deprecated
    VideoComponent getVideoComponent();

    @Nullable
    DecoderCounters getVideoDecoderCounters();

    @Nullable
    Format getVideoFormat();

    int getVideoScalingMode();

    boolean isTunnelingEnabled();

    @Deprecated
    void prepare(MediaSource mediaSource);

    @Deprecated
    void prepare(MediaSource mediaSource, boolean z, boolean z2);

    void removeAnalyticsListener(AnalyticsListener analyticsListener);

    void removeAudioOffloadListener(AudioOffloadListener audioOffloadListener);

    @Deprecated
    void retry();

    void setAudioAttributes(AudioAttributes audioAttributes, boolean z);

    void setAudioSessionId(int i);

    void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

    void setCameraMotionListener(CameraMotionListener cameraMotionListener);

    void setForegroundMode(boolean z);

    void setHandleAudioBecomingNoisy(boolean z);

    @Deprecated
    void setHandleWakeLock(boolean z);

    void setMediaSource(MediaSource mediaSource);

    void setMediaSource(MediaSource mediaSource, long j);

    void setMediaSource(MediaSource mediaSource, boolean z);

    void setMediaSources(List<MediaSource> list);

    void setMediaSources(List<MediaSource> list, int i, long j);

    void setMediaSources(List<MediaSource> list, boolean z);

    void setPauseAtEndOfMediaItems(boolean z);

    @RequiresApi(23)
    void setPreferredAudioDevice(@Nullable AudioDeviceInfo audioDeviceInfo);

    void setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager);

    void setSeekParameters(@Nullable SeekParameters seekParameters);

    void setShuffleOrder(ShuffleOrder shuffleOrder);

    void setSkipSilenceEnabled(boolean z);

    void setVideoChangeFrameRateStrategy(int i);

    void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

    void setVideoScalingMode(int i);

    void setWakeMode(int i);
}
