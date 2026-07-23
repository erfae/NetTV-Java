package com.google.android.exoplayer2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.AudioDeviceInfo;
import android.media.AudioTrack;
import android.media.MediaFormat;
import android.media.metrics.LogSessionId;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.MediaMetricsListener;
import com.google.android.exoplayer2.analytics.PlayerId;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.CueGroup;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Size;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoDecoderOutputBufferRenderer;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
final class ExoPlayerImpl extends BasePlayer implements ExoPlayer, ExoPlayer.AudioComponent, ExoPlayer.VideoComponent, ExoPlayer.TextComponent, ExoPlayer.DeviceComponent {
    private static final String TAG = "ExoPlayerImpl";
    private final AnalyticsCollector analyticsCollector;
    private final Context applicationContext;
    private final Looper applicationLooper;
    private AudioAttributes audioAttributes;
    private final AudioBecomingNoisyManager audioBecomingNoisyManager;

    @Nullable
    private DecoderCounters audioDecoderCounters;
    private final AudioFocusManager audioFocusManager;

    @Nullable
    private Format audioFormat;
    private final CopyOnWriteArraySet<ExoPlayer.AudioOffloadListener> audioOffloadListeners;
    private int audioSessionId;
    private Player.Commands availableCommands;
    private final BandwidthMeter bandwidthMeter;

    @Nullable
    private CameraMotionListener cameraMotionListener;
    private final Clock clock;
    private final ComponentListener componentListener;
    private final ConditionVariable constructorFinished;
    private CueGroup currentCueGroup;
    private final long detachSurfaceTimeoutMs;
    private DeviceInfo deviceInfo;
    public final TrackSelectorResult emptyTrackSelectorResult;
    private boolean foregroundMode;
    private final FrameMetadataListener frameMetadataListener;
    private boolean hasNotifiedFullWrongThreadWarning;
    private final ExoPlayerImplInternal internalPlayer;
    private boolean isPriorityTaskManagerRegistered;

    @Nullable
    private AudioTrack keepSessionIdAudioTrack;
    private final ListenerSet<Player.Listener> listeners;
    private int maskingPeriodIndex;
    private int maskingWindowIndex;
    private long maskingWindowPositionMs;
    private MediaMetadata mediaMetadata;
    private final MediaSource.Factory mediaSourceFactory;
    private final List<MediaSourceHolderSnapshot> mediaSourceHolderSnapshots;

    @Nullable
    private Surface ownedSurface;
    private boolean pauseAtEndOfMediaItems;
    private boolean pendingDiscontinuity;
    private int pendingDiscontinuityReason;
    private int pendingOperationAcks;
    private int pendingPlayWhenReadyChangeReason;
    private final Timeline.Period period;
    public final Player.Commands permanentAvailableCommands;
    private PlaybackInfo playbackInfo;
    private final HandlerWrapper playbackInfoUpdateHandler;
    private final ExoPlayerImplInternal.PlaybackInfoUpdateListener playbackInfoUpdateListener;
    private boolean playerReleased;
    private MediaMetadata playlistMetadata;

    @Nullable
    private PriorityTaskManager priorityTaskManager;
    private final Renderer[] renderers;
    private int repeatMode;
    private final long seekBackIncrementMs;
    private final long seekForwardIncrementMs;
    private SeekParameters seekParameters;
    private boolean shuffleModeEnabled;
    private ShuffleOrder shuffleOrder;
    private boolean skipSilenceEnabled;

    @Nullable
    private SphericalGLSurfaceView sphericalGLSurfaceView;
    private MediaMetadata staticAndDynamicMediaMetadata;
    private final StreamVolumeManager streamVolumeManager;

    @Nullable
    private SurfaceHolder surfaceHolder;
    private boolean surfaceHolderSurfaceIsVideoOutput;
    private Size surfaceSize;

    @Nullable
    private TextureView textureView;
    private boolean throwsWhenUsingWrongThread;
    private final TrackSelector trackSelector;
    private final boolean useLazyPreparation;
    private int videoChangeFrameRateStrategy;

    @Nullable
    private DecoderCounters videoDecoderCounters;

    @Nullable
    private Format videoFormat;

    @Nullable
    private VideoFrameMetadataListener videoFrameMetadataListener;

    @Nullable
    private Object videoOutput;
    private int videoScalingMode;
    private VideoSize videoSize;
    private float volume;
    private final WakeLockManager wakeLockManager;
    private final WifiLockManager wifiLockManager;
    private final Player wrappingPlayer;

    @RequiresApi(31)
    public static final class Api31 {
        private Api31() {
        }

        @DoNotInline
        public static PlayerId registerMediaMetricsListener(Context context, ExoPlayerImpl exoPlayerImpl, boolean z) {
            MediaMetricsListener mediaMetricsListenerCreate = MediaMetricsListener.create(context);
            if (mediaMetricsListenerCreate == null) {
                Log.w(ExoPlayerImpl.TAG, "MediaMetricsService unavailable.");
                return new PlayerId(LogSessionId.LOG_SESSION_ID_NONE);
            }
            if (z) {
                exoPlayerImpl.addAnalyticsListener(mediaMetricsListenerCreate);
            }
            return new PlayerId(mediaMetricsListenerCreate.getLogSessionId());
        }
    }

    public final class ComponentListener implements VideoRendererEventListener, AudioRendererEventListener, TextOutput, MetadataOutput, SurfaceHolder.Callback, TextureView.SurfaceTextureListener, SphericalGLSurfaceView.VideoSurfaceListener, AudioFocusManager.PlayerControl, AudioBecomingNoisyManager.EventListener, StreamVolumeManager.Listener, ExoPlayer.AudioOffloadListener {
        private ComponentListener() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMetadata$4(Player.Listener listener) {
            listener.onMediaMetadataChanged(ExoPlayerImpl.this.mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.AudioFocusManager.PlayerControl
        public void executePlayerCommand(int i) {
            boolean playWhenReady = ExoPlayerImpl.this.getPlayWhenReady();
            ExoPlayerImpl.this.updatePlayWhenReady(playWhenReady, i, ExoPlayerImpl.getPlayWhenReadyChangeReason(playWhenReady, i));
        }

        @Override // com.google.android.exoplayer2.AudioBecomingNoisyManager.EventListener
        public void onAudioBecomingNoisy() {
            ExoPlayerImpl.this.updatePlayWhenReady(false, -1, 3);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioCodecError(Exception exc) {
            ExoPlayerImpl.this.analyticsCollector.onAudioCodecError(exc);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDecoderInitialized(String str, long j, long j2) {
            ExoPlayerImpl.this.analyticsCollector.onAudioDecoderInitialized(str, j, j2);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDecoderReleased(String str) {
            ExoPlayerImpl.this.analyticsCollector.onAudioDecoderReleased(str);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDisabled(DecoderCounters decoderCounters) {
            ExoPlayerImpl.this.analyticsCollector.onAudioDisabled(decoderCounters);
            ExoPlayerImpl.this.audioFormat = null;
            ExoPlayerImpl.this.audioDecoderCounters = null;
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioEnabled(DecoderCounters decoderCounters) {
            ExoPlayerImpl.this.audioDecoderCounters = decoderCounters;
            ExoPlayerImpl.this.analyticsCollector.onAudioEnabled(decoderCounters);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public final /* synthetic */ void onAudioInputFormatChanged(Format format) {
            AudioRendererEventListener.CC.$default$onAudioInputFormatChanged(this, format);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            ExoPlayerImpl.this.audioFormat = format;
            ExoPlayerImpl.this.analyticsCollector.onAudioInputFormatChanged(format, decoderReuseEvaluation);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioPositionAdvancing(long j) {
            ExoPlayerImpl.this.analyticsCollector.onAudioPositionAdvancing(j);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioSinkError(Exception exc) {
            ExoPlayerImpl.this.analyticsCollector.onAudioSinkError(exc);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioUnderrun(int i, long j, long j2) {
            ExoPlayerImpl.this.analyticsCollector.onAudioUnderrun(i, j, j2);
        }

        @Override // com.google.android.exoplayer2.text.TextOutput
        public void onCues(List<Cue> list) {
            ExoPlayerImpl.this.listeners.sendEvent(27, new ExoPlayerImpl$$ExternalSyntheticLambda9(list, 4));
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onDroppedFrames(int i, long j) {
            ExoPlayerImpl.this.analyticsCollector.onDroppedFrames(i, j);
        }

        @Override // com.google.android.exoplayer2.ExoPlayer.AudioOffloadListener
        public final /* synthetic */ void onExperimentalOffloadSchedulingEnabledChanged(boolean z) {
            ExoPlayer.AudioOffloadListener.CC.$default$onExperimentalOffloadSchedulingEnabledChanged(this, z);
        }

        @Override // com.google.android.exoplayer2.ExoPlayer.AudioOffloadListener
        public final /* synthetic */ void onExperimentalOffloadedPlayback(boolean z) {
            ExoPlayer.AudioOffloadListener.CC.$default$onExperimentalOffloadedPlayback(this, z);
        }

        @Override // com.google.android.exoplayer2.ExoPlayer.AudioOffloadListener
        public void onExperimentalSleepingForOffloadChanged(boolean z) {
            ExoPlayerImpl.this.updateWakeAndWifiLock();
        }

        @Override // com.google.android.exoplayer2.metadata.MetadataOutput
        public void onMetadata(Metadata metadata) {
            ExoPlayerImpl exoPlayerImpl = ExoPlayerImpl.this;
            exoPlayerImpl.staticAndDynamicMediaMetadata = exoPlayerImpl.staticAndDynamicMediaMetadata.buildUpon().populateFromMetadata(metadata).build();
            MediaMetadata mediaMetadataBuildUpdatedMediaMetadata = ExoPlayerImpl.this.buildUpdatedMediaMetadata();
            if (!mediaMetadataBuildUpdatedMediaMetadata.equals(ExoPlayerImpl.this.mediaMetadata)) {
                ExoPlayerImpl.this.mediaMetadata = mediaMetadataBuildUpdatedMediaMetadata;
                ExoPlayerImpl.this.listeners.queueEvent(14, new ExoPlayerImpl$$ExternalSyntheticLambda9(this, 2));
            }
            ExoPlayerImpl.this.listeners.queueEvent(28, new ExoPlayerImpl$$ExternalSyntheticLambda9(metadata, 3));
            ExoPlayerImpl.this.listeners.flushEvents();
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onRenderedFirstFrame(Object obj, long j) {
            ExoPlayerImpl.this.analyticsCollector.onRenderedFirstFrame(obj, j);
            if (ExoPlayerImpl.this.videoOutput == obj) {
                ExoPlayerImpl.this.listeners.sendEvent(26, Format$$ExternalSyntheticLambda0.INSTANCE$5);
            }
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onSkipSilenceEnabledChanged(boolean z) {
            if (ExoPlayerImpl.this.skipSilenceEnabled == z) {
                return;
            }
            ExoPlayerImpl.this.skipSilenceEnabled = z;
            ExoPlayerImpl.this.listeners.sendEvent(23, new ExoPlayerImpl$$ExternalSyntheticLambda6(z, 2));
        }

        @Override // com.google.android.exoplayer2.StreamVolumeManager.Listener
        public void onStreamTypeChanged(int i) {
            DeviceInfo deviceInfoCreateDeviceInfo = ExoPlayerImpl.createDeviceInfo(ExoPlayerImpl.this.streamVolumeManager);
            if (deviceInfoCreateDeviceInfo.equals(ExoPlayerImpl.this.deviceInfo)) {
                return;
            }
            ExoPlayerImpl.this.deviceInfo = deviceInfoCreateDeviceInfo;
            ExoPlayerImpl.this.listeners.sendEvent(29, new ExoPlayerImpl$$ExternalSyntheticLambda9(deviceInfoCreateDeviceInfo, 5));
        }

        @Override // com.google.android.exoplayer2.StreamVolumeManager.Listener
        public void onStreamVolumeChanged(final int i, final boolean z) {
            ExoPlayerImpl.this.listeners.sendEvent(30, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$ComponentListener$$ExternalSyntheticLambda0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onDeviceVolumeChanged(i, z);
                }
            });
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            ExoPlayerImpl.this.setSurfaceTextureInternal(surfaceTexture);
            ExoPlayerImpl.this.maybeNotifySurfaceSizeChanged(i, i2);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            ExoPlayerImpl.this.setVideoOutputInternal(null);
            ExoPlayerImpl.this.maybeNotifySurfaceSizeChanged(0, 0);
            return true;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            ExoPlayerImpl.this.maybeNotifySurfaceSizeChanged(i, i2);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoCodecError(Exception exc) {
            ExoPlayerImpl.this.analyticsCollector.onVideoCodecError(exc);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDecoderInitialized(String str, long j, long j2) {
            ExoPlayerImpl.this.analyticsCollector.onVideoDecoderInitialized(str, j, j2);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDecoderReleased(String str) {
            ExoPlayerImpl.this.analyticsCollector.onVideoDecoderReleased(str);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDisabled(DecoderCounters decoderCounters) {
            ExoPlayerImpl.this.analyticsCollector.onVideoDisabled(decoderCounters);
            ExoPlayerImpl.this.videoFormat = null;
            ExoPlayerImpl.this.videoDecoderCounters = null;
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoEnabled(DecoderCounters decoderCounters) {
            ExoPlayerImpl.this.videoDecoderCounters = decoderCounters;
            ExoPlayerImpl.this.analyticsCollector.onVideoEnabled(decoderCounters);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoFrameProcessingOffset(long j, int i) {
            ExoPlayerImpl.this.analyticsCollector.onVideoFrameProcessingOffset(j, i);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public final /* synthetic */ void onVideoInputFormatChanged(Format format) {
            VideoRendererEventListener.CC.$default$onVideoInputFormatChanged(this, format);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            ExoPlayerImpl.this.videoFormat = format;
            ExoPlayerImpl.this.analyticsCollector.onVideoInputFormatChanged(format, decoderReuseEvaluation);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoSizeChanged(VideoSize videoSize) {
            ExoPlayerImpl.this.videoSize = videoSize;
            ExoPlayerImpl.this.listeners.sendEvent(25, new ExoPlayerImpl$$ExternalSyntheticLambda9(videoSize, 7));
        }

        @Override // com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView.VideoSurfaceListener
        public void onVideoSurfaceCreated(Surface surface) {
            ExoPlayerImpl.this.setVideoOutputInternal(surface);
        }

        @Override // com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView.VideoSurfaceListener
        public void onVideoSurfaceDestroyed(Surface surface) {
            ExoPlayerImpl.this.setVideoOutputInternal(null);
        }

        @Override // com.google.android.exoplayer2.AudioFocusManager.PlayerControl
        public void setVolumeMultiplier(float f) {
            ExoPlayerImpl.this.sendVolumeToRenderers();
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            ExoPlayerImpl.this.maybeNotifySurfaceSizeChanged(i2, i3);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (ExoPlayerImpl.this.surfaceHolderSurfaceIsVideoOutput) {
                ExoPlayerImpl.this.setVideoOutputInternal(surfaceHolder.getSurface());
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (ExoPlayerImpl.this.surfaceHolderSurfaceIsVideoOutput) {
                ExoPlayerImpl.this.setVideoOutputInternal(null);
            }
            ExoPlayerImpl.this.maybeNotifySurfaceSizeChanged(0, 0);
        }

        @Override // com.google.android.exoplayer2.text.TextOutput
        public void onCues(CueGroup cueGroup) {
            ExoPlayerImpl.this.currentCueGroup = cueGroup;
            ExoPlayerImpl.this.listeners.sendEvent(27, new ExoPlayerImpl$$ExternalSyntheticLambda9(cueGroup, 6));
        }
    }

    public static final class FrameMetadataListener implements VideoFrameMetadataListener, CameraMotionListener, PlayerMessage.Target {
        public static final int MSG_SET_CAMERA_MOTION_LISTENER = 8;
        public static final int MSG_SET_SPHERICAL_SURFACE_VIEW = 10000;
        public static final int MSG_SET_VIDEO_FRAME_METADATA_LISTENER = 7;

        @Nullable
        private CameraMotionListener cameraMotionListener;

        @Nullable
        private CameraMotionListener internalCameraMotionListener;

        @Nullable
        private VideoFrameMetadataListener internalVideoFrameMetadataListener;

        @Nullable
        private VideoFrameMetadataListener videoFrameMetadataListener;

        private FrameMetadataListener() {
        }

        @Override // com.google.android.exoplayer2.PlayerMessage.Target
        public void handleMessage(int i, @Nullable Object obj) {
            if (i == 7) {
                this.videoFrameMetadataListener = (VideoFrameMetadataListener) obj;
                return;
            }
            if (i == 8) {
                this.cameraMotionListener = (CameraMotionListener) obj;
                return;
            }
            if (i != 10000) {
                return;
            }
            SphericalGLSurfaceView sphericalGLSurfaceView = (SphericalGLSurfaceView) obj;
            if (sphericalGLSurfaceView == null) {
                this.internalVideoFrameMetadataListener = null;
                this.internalCameraMotionListener = null;
            } else {
                this.internalVideoFrameMetadataListener = sphericalGLSurfaceView.getVideoFrameMetadataListener();
                this.internalCameraMotionListener = sphericalGLSurfaceView.getCameraMotionListener();
            }
        }

        @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
        public void onCameraMotion(long j, float[] fArr) {
            CameraMotionListener cameraMotionListener = this.internalCameraMotionListener;
            if (cameraMotionListener != null) {
                cameraMotionListener.onCameraMotion(j, fArr);
            }
            CameraMotionListener cameraMotionListener2 = this.cameraMotionListener;
            if (cameraMotionListener2 != null) {
                cameraMotionListener2.onCameraMotion(j, fArr);
            }
        }

        @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
        public void onCameraMotionReset() {
            CameraMotionListener cameraMotionListener = this.internalCameraMotionListener;
            if (cameraMotionListener != null) {
                cameraMotionListener.onCameraMotionReset();
            }
            CameraMotionListener cameraMotionListener2 = this.cameraMotionListener;
            if (cameraMotionListener2 != null) {
                cameraMotionListener2.onCameraMotionReset();
            }
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameMetadataListener
        public void onVideoFrameAboutToBeRendered(long j, long j2, Format format, @Nullable MediaFormat mediaFormat) {
            VideoFrameMetadataListener videoFrameMetadataListener = this.internalVideoFrameMetadataListener;
            if (videoFrameMetadataListener != null) {
                videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j, j2, format, mediaFormat);
            }
            VideoFrameMetadataListener videoFrameMetadataListener2 = this.videoFrameMetadataListener;
            if (videoFrameMetadataListener2 != null) {
                videoFrameMetadataListener2.onVideoFrameAboutToBeRendered(j, j2, format, mediaFormat);
            }
        }
    }

    public static final class MediaSourceHolderSnapshot implements MediaSourceInfoHolder {
        private Timeline timeline;
        private final Object uid;

        public MediaSourceHolderSnapshot(Object obj, Timeline timeline) {
            this.uid = obj;
            this.timeline = timeline;
        }

        @Override // com.google.android.exoplayer2.MediaSourceInfoHolder
        public Timeline getTimeline() {
            return this.timeline;
        }

        @Override // com.google.android.exoplayer2.MediaSourceInfoHolder
        public Object getUid() {
            return this.uid;
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.exoplayer");
    }

    @SuppressLint({"HandlerLeak"})
    public ExoPlayerImpl(ExoPlayer.Builder builder, @Nullable Player player) {
        ConditionVariable conditionVariable = new ConditionVariable();
        this.constructorFinished = conditionVariable;
        try {
            Log.i(TAG, "Init " + Integer.toHexString(System.identityHashCode(this)) + " [" + ExoPlayerLibraryInfo.VERSION_SLASHY + "] [" + Util.DEVICE_DEBUG_INFO + "]");
            Context applicationContext = builder.context.getApplicationContext();
            this.applicationContext = applicationContext;
            AnalyticsCollector analyticsCollectorApply = builder.analyticsCollectorFunction.apply(builder.clock);
            this.analyticsCollector = analyticsCollectorApply;
            this.priorityTaskManager = builder.priorityTaskManager;
            this.audioAttributes = builder.audioAttributes;
            this.videoScalingMode = builder.videoScalingMode;
            this.videoChangeFrameRateStrategy = builder.videoChangeFrameRateStrategy;
            this.skipSilenceEnabled = builder.skipSilenceEnabled;
            this.detachSurfaceTimeoutMs = builder.detachSurfaceTimeoutMs;
            ComponentListener componentListener = new ComponentListener();
            this.componentListener = componentListener;
            FrameMetadataListener frameMetadataListener = new FrameMetadataListener();
            this.frameMetadataListener = frameMetadataListener;
            Handler handler = new Handler(builder.looper);
            Renderer[] rendererArrCreateRenderers = builder.renderersFactorySupplier.get().createRenderers(handler, componentListener, componentListener, componentListener, componentListener);
            this.renderers = rendererArrCreateRenderers;
            int i = 0;
            Assertions.checkState(rendererArrCreateRenderers.length > 0);
            TrackSelector trackSelector = builder.trackSelectorSupplier.get();
            this.trackSelector = trackSelector;
            this.mediaSourceFactory = builder.mediaSourceFactorySupplier.get();
            BandwidthMeter bandwidthMeter = builder.bandwidthMeterSupplier.get();
            this.bandwidthMeter = bandwidthMeter;
            this.useLazyPreparation = builder.useLazyPreparation;
            this.seekParameters = builder.seekParameters;
            this.seekBackIncrementMs = builder.seekBackIncrementMs;
            this.seekForwardIncrementMs = builder.seekForwardIncrementMs;
            this.pauseAtEndOfMediaItems = builder.pauseAtEndOfMediaItems;
            Looper looper = builder.looper;
            this.applicationLooper = looper;
            Clock clock = builder.clock;
            this.clock = clock;
            Player player2 = player == null ? this : player;
            this.wrappingPlayer = player2;
            this.listeners = new ListenerSet<>(looper, clock, new ExoPlayerImpl$$ExternalSyntheticLambda10(this, i));
            this.audioOffloadListeners = new CopyOnWriteArraySet<>();
            this.mediaSourceHolderSnapshots = new ArrayList();
            this.shuffleOrder = new ShuffleOrder.DefaultShuffleOrder(0);
            TrackSelectorResult trackSelectorResult = new TrackSelectorResult(new RendererConfiguration[rendererArrCreateRenderers.length], new ExoTrackSelection[rendererArrCreateRenderers.length], Tracks.EMPTY, null);
            this.emptyTrackSelectorResult = trackSelectorResult;
            this.period = new Timeline.Period();
            Player.Commands commandsBuild = new Player.Commands.Builder().addAll(1, 2, 3, 13, 14, 15, 16, 17, 18, 19, 31, 20, 30, 21, 22, 23, 24, 25, 26, 27, 28).addIf(29, trackSelector.isSetParametersSupported()).build();
            this.permanentAvailableCommands = commandsBuild;
            this.availableCommands = new Player.Commands.Builder().addAll(commandsBuild).add(4).add(10).build();
            this.playbackInfoUpdateHandler = clock.createHandler(looper, null);
            ExoPlayerImpl$$ExternalSyntheticLambda10 exoPlayerImpl$$ExternalSyntheticLambda10 = new ExoPlayerImpl$$ExternalSyntheticLambda10(this, 1);
            this.playbackInfoUpdateListener = exoPlayerImpl$$ExternalSyntheticLambda10;
            this.playbackInfo = PlaybackInfo.createDummy(trackSelectorResult);
            analyticsCollectorApply.setPlayer(player2, looper);
            int i2 = Util.SDK_INT;
            ExoPlayerImplInternal exoPlayerImplInternal = new ExoPlayerImplInternal(rendererArrCreateRenderers, trackSelector, trackSelectorResult, builder.loadControlSupplier.get(), bandwidthMeter, this.repeatMode, this.shuffleModeEnabled, analyticsCollectorApply, this.seekParameters, builder.livePlaybackSpeedControl, builder.releaseTimeoutMs, this.pauseAtEndOfMediaItems, looper, clock, exoPlayerImpl$$ExternalSyntheticLambda10, i2 < 31 ? new PlayerId() : Api31.registerMediaMetricsListener(applicationContext, this, builder.usePlatformDiagnostics), builder.playbackLooper);
            this.internalPlayer = exoPlayerImplInternal;
            this.volume = 1.0f;
            this.repeatMode = 0;
            MediaMetadata mediaMetadata = MediaMetadata.EMPTY;
            this.mediaMetadata = mediaMetadata;
            this.playlistMetadata = mediaMetadata;
            this.staticAndDynamicMediaMetadata = mediaMetadata;
            this.maskingWindowIndex = -1;
            if (i2 < 21) {
                this.audioSessionId = initializeKeepSessionIdAudioTrack(0);
            } else {
                this.audioSessionId = Util.generateAudioSessionIdV21(applicationContext);
            }
            this.currentCueGroup = CueGroup.EMPTY_TIME_ZERO;
            this.throwsWhenUsingWrongThread = true;
            addListener(analyticsCollectorApply);
            bandwidthMeter.addEventListener(new Handler(looper), analyticsCollectorApply);
            addAudioOffloadListener(componentListener);
            long j = builder.foregroundModeTimeoutMs;
            if (j > 0) {
                exoPlayerImplInternal.experimentalSetForegroundModeTimeoutMs(j);
            }
            AudioBecomingNoisyManager audioBecomingNoisyManager = new AudioBecomingNoisyManager(builder.context, handler, componentListener);
            this.audioBecomingNoisyManager = audioBecomingNoisyManager;
            audioBecomingNoisyManager.setEnabled(builder.handleAudioBecomingNoisy);
            AudioFocusManager audioFocusManager = new AudioFocusManager(builder.context, handler, componentListener);
            this.audioFocusManager = audioFocusManager;
            audioFocusManager.setAudioAttributes(builder.handleAudioFocus ? this.audioAttributes : null);
            StreamVolumeManager streamVolumeManager = new StreamVolumeManager(builder.context, handler, componentListener);
            this.streamVolumeManager = streamVolumeManager;
            streamVolumeManager.setStreamType(Util.getStreamTypeForAudioUsage(this.audioAttributes.usage));
            WakeLockManager wakeLockManager = new WakeLockManager(builder.context);
            this.wakeLockManager = wakeLockManager;
            wakeLockManager.setEnabled(builder.wakeMode != 0);
            WifiLockManager wifiLockManager = new WifiLockManager(builder.context);
            this.wifiLockManager = wifiLockManager;
            wifiLockManager.setEnabled(builder.wakeMode == 2);
            this.deviceInfo = createDeviceInfo(streamVolumeManager);
            this.videoSize = VideoSize.UNKNOWN;
            this.surfaceSize = Size.UNKNOWN;
            trackSelector.setAudioAttributes(this.audioAttributes);
            sendRendererMessage(1, 10, Integer.valueOf(this.audioSessionId));
            sendRendererMessage(2, 10, Integer.valueOf(this.audioSessionId));
            sendRendererMessage(1, 3, this.audioAttributes);
            sendRendererMessage(2, 4, Integer.valueOf(this.videoScalingMode));
            sendRendererMessage(2, 5, Integer.valueOf(this.videoChangeFrameRateStrategy));
            sendRendererMessage(1, 9, Boolean.valueOf(this.skipSilenceEnabled));
            sendRendererMessage(2, 7, frameMetadataListener);
            sendRendererMessage(6, 8, frameMetadataListener);
            conditionVariable.open();
        } catch (Throwable th) {
            this.constructorFinished.open();
            throw th;
        }
    }

    private List<MediaSourceList.MediaSourceHolder> addMediaSourceHolders(int i, List<MediaSource> list) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            MediaSourceList.MediaSourceHolder mediaSourceHolder = new MediaSourceList.MediaSourceHolder(list.get(i2), this.useLazyPreparation);
            arrayList.add(mediaSourceHolder);
            this.mediaSourceHolderSnapshots.add(i2 + i, new MediaSourceHolderSnapshot(mediaSourceHolder.uid, mediaSourceHolder.mediaSource.getTimeline()));
        }
        this.shuffleOrder = this.shuffleOrder.cloneAndInsert(i, arrayList.size());
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MediaMetadata buildUpdatedMediaMetadata() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return this.staticAndDynamicMediaMetadata;
        }
        return this.staticAndDynamicMediaMetadata.buildUpon().populate(currentTimeline.getWindow(getCurrentMediaItemIndex(), this.window).mediaItem.mediaMetadata).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DeviceInfo createDeviceInfo(StreamVolumeManager streamVolumeManager) {
        return new DeviceInfo(0, streamVolumeManager.getMinVolume(), streamVolumeManager.getMaxVolume());
    }

    private Timeline createMaskingTimeline() {
        return new PlaylistTimeline(this.mediaSourceHolderSnapshots, this.shuffleOrder);
    }

    private List<MediaSource> createMediaSources(List<MediaItem> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(this.mediaSourceFactory.createMediaSource(list.get(i)));
        }
        return arrayList;
    }

    private PlayerMessage createMessageInternal(PlayerMessage.Target target) {
        int currentWindowIndexInternal = getCurrentWindowIndexInternal();
        ExoPlayerImplInternal exoPlayerImplInternal = this.internalPlayer;
        return new PlayerMessage(exoPlayerImplInternal, target, this.playbackInfo.timeline, currentWindowIndexInternal == -1 ? 0 : currentWindowIndexInternal, this.clock, exoPlayerImplInternal.getPlaybackLooper());
    }

    private Pair<Boolean, Integer> evaluateMediaItemTransitionReason(PlaybackInfo playbackInfo, PlaybackInfo playbackInfo2, boolean z, int i, boolean z2, boolean z3) {
        Timeline timeline = playbackInfo2.timeline;
        Timeline timeline2 = playbackInfo.timeline;
        if (timeline2.isEmpty() && timeline.isEmpty()) {
            return new Pair<>(Boolean.FALSE, -1);
        }
        int i2 = 3;
        if (timeline2.isEmpty() != timeline.isEmpty()) {
            return new Pair<>(Boolean.TRUE, 3);
        }
        if (timeline.getWindow(timeline.getPeriodByUid(playbackInfo2.periodId.periodUid, this.period).windowIndex, this.window).uid.equals(timeline2.getWindow(timeline2.getPeriodByUid(playbackInfo.periodId.periodUid, this.period).windowIndex, this.window).uid)) {
            if (z && i == 0 && playbackInfo2.periodId.windowSequenceNumber < playbackInfo.periodId.windowSequenceNumber) {
                return new Pair<>(Boolean.TRUE, 0);
            }
            return (z && i == 1 && z3) ? new Pair<>(Boolean.TRUE, 2) : new Pair<>(Boolean.FALSE, -1);
        }
        if (z && i == 0) {
            i2 = 1;
        } else if (z && i == 1) {
            i2 = 2;
        } else if (!z2) {
            throw new IllegalStateException();
        }
        return new Pair<>(Boolean.TRUE, Integer.valueOf(i2));
    }

    private long getCurrentPositionUsInternal(PlaybackInfo playbackInfo) {
        if (playbackInfo.timeline.isEmpty()) {
            return Util.msToUs(this.maskingWindowPositionMs);
        }
        return playbackInfo.periodId.isAd() ? playbackInfo.positionUs : periodPositionUsToWindowPositionUs(playbackInfo.timeline, playbackInfo.periodId, playbackInfo.positionUs);
    }

    private int getCurrentWindowIndexInternal() {
        if (this.playbackInfo.timeline.isEmpty()) {
            return this.maskingWindowIndex;
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        return playbackInfo.timeline.getPeriodByUid(playbackInfo.periodId.periodUid, this.period).windowIndex;
    }

    @Nullable
    private Pair<Object, Long> getPeriodPositionUsAfterTimelineChanged(Timeline timeline, Timeline timeline2) {
        long contentPosition = getContentPosition();
        if (timeline.isEmpty() || timeline2.isEmpty()) {
            boolean z = !timeline.isEmpty() && timeline2.isEmpty();
            int currentWindowIndexInternal = z ? -1 : getCurrentWindowIndexInternal();
            if (z) {
                contentPosition = -9223372036854775807L;
            }
            return maskWindowPositionMsOrGetPeriodPositionUs(timeline2, currentWindowIndexInternal, contentPosition);
        }
        Pair<Object, Long> periodPositionUs = timeline.getPeriodPositionUs(this.window, this.period, getCurrentMediaItemIndex(), Util.msToUs(contentPosition));
        Object obj = ((Pair) Util.castNonNull(periodPositionUs)).first;
        if (timeline2.getIndexOfPeriod(obj) != -1) {
            return periodPositionUs;
        }
        Object objResolveSubsequentPeriod = ExoPlayerImplInternal.resolveSubsequentPeriod(this.window, this.period, this.repeatMode, this.shuffleModeEnabled, obj, timeline, timeline2);
        if (objResolveSubsequentPeriod == null) {
            return maskWindowPositionMsOrGetPeriodPositionUs(timeline2, -1, C.TIME_UNSET);
        }
        timeline2.getPeriodByUid(objResolveSubsequentPeriod, this.period);
        int i = this.period.windowIndex;
        return maskWindowPositionMsOrGetPeriodPositionUs(timeline2, i, timeline2.getWindow(i, this.window).getDefaultPositionMs());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getPlayWhenReadyChangeReason(boolean z, int i) {
        return (!z || i == 1) ? 1 : 2;
    }

    private Player.PositionInfo getPositionInfo(long j) {
        MediaItem mediaItem;
        Object obj;
        int indexOfPeriod;
        int currentMediaItemIndex = getCurrentMediaItemIndex();
        Object obj2 = null;
        if (this.playbackInfo.timeline.isEmpty()) {
            mediaItem = null;
            obj = null;
            indexOfPeriod = -1;
        } else {
            PlaybackInfo playbackInfo = this.playbackInfo;
            Object obj3 = playbackInfo.periodId.periodUid;
            playbackInfo.timeline.getPeriodByUid(obj3, this.period);
            indexOfPeriod = this.playbackInfo.timeline.getIndexOfPeriod(obj3);
            obj = obj3;
            obj2 = this.playbackInfo.timeline.getWindow(currentMediaItemIndex, this.window).uid;
            mediaItem = this.window.mediaItem;
        }
        long jUsToMs = Util.usToMs(j);
        long jUsToMs2 = this.playbackInfo.periodId.isAd() ? Util.usToMs(getRequestedContentPositionUs(this.playbackInfo)) : jUsToMs;
        MediaSource.MediaPeriodId mediaPeriodId = this.playbackInfo.periodId;
        return new Player.PositionInfo(obj2, currentMediaItemIndex, mediaItem, obj, indexOfPeriod, jUsToMs, jUsToMs2, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
    }

    private Player.PositionInfo getPreviousPositionInfo(int i, PlaybackInfo playbackInfo, int i2) {
        int i3;
        Object obj;
        MediaItem mediaItem;
        Object obj2;
        int indexOfPeriod;
        long requestedContentPositionUs;
        long requestedContentPositionUs2;
        Timeline.Period period = new Timeline.Period();
        if (playbackInfo.timeline.isEmpty()) {
            i3 = i2;
            obj = null;
            mediaItem = null;
            obj2 = null;
            indexOfPeriod = -1;
        } else {
            Object obj3 = playbackInfo.periodId.periodUid;
            playbackInfo.timeline.getPeriodByUid(obj3, period);
            int i4 = period.windowIndex;
            i3 = i4;
            obj2 = obj3;
            indexOfPeriod = playbackInfo.timeline.getIndexOfPeriod(obj3);
            obj = playbackInfo.timeline.getWindow(i4, this.window).uid;
            mediaItem = this.window.mediaItem;
        }
        if (i == 0) {
            if (playbackInfo.periodId.isAd()) {
                MediaSource.MediaPeriodId mediaPeriodId = playbackInfo.periodId;
                requestedContentPositionUs = period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
                requestedContentPositionUs2 = getRequestedContentPositionUs(playbackInfo);
            } else {
                requestedContentPositionUs = playbackInfo.periodId.nextAdGroupIndex != -1 ? getRequestedContentPositionUs(this.playbackInfo) : period.positionInWindowUs + period.durationUs;
                requestedContentPositionUs2 = requestedContentPositionUs;
            }
        } else if (playbackInfo.periodId.isAd()) {
            requestedContentPositionUs = playbackInfo.positionUs;
            requestedContentPositionUs2 = getRequestedContentPositionUs(playbackInfo);
        } else {
            requestedContentPositionUs = period.positionInWindowUs + playbackInfo.positionUs;
            requestedContentPositionUs2 = requestedContentPositionUs;
        }
        long jUsToMs = Util.usToMs(requestedContentPositionUs);
        long jUsToMs2 = Util.usToMs(requestedContentPositionUs2);
        MediaSource.MediaPeriodId mediaPeriodId2 = playbackInfo.periodId;
        return new Player.PositionInfo(obj, i3, mediaItem, obj2, indexOfPeriod, jUsToMs, jUsToMs2, mediaPeriodId2.adGroupIndex, mediaPeriodId2.adIndexInAdGroup);
    }

    private static long getRequestedContentPositionUs(PlaybackInfo playbackInfo) {
        Timeline.Window window = new Timeline.Window();
        Timeline.Period period = new Timeline.Period();
        playbackInfo.timeline.getPeriodByUid(playbackInfo.periodId.periodUid, period);
        return playbackInfo.requestedContentPositionUs == C.TIME_UNSET ? playbackInfo.timeline.getWindow(period.windowIndex, window).getDefaultPositionUs() : period.getPositionInWindowUs() + playbackInfo.requestedContentPositionUs;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handlePlaybackInfo, reason: merged with bridge method [inline-methods] */
    public void lambda$new$1(ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
        long j;
        boolean z;
        long jPeriodPositionUsToWindowPositionUs;
        int i = this.pendingOperationAcks - playbackInfoUpdate.operationAcks;
        this.pendingOperationAcks = i;
        boolean z2 = true;
        if (playbackInfoUpdate.positionDiscontinuity) {
            this.pendingDiscontinuityReason = playbackInfoUpdate.discontinuityReason;
            this.pendingDiscontinuity = true;
        }
        if (playbackInfoUpdate.hasPlayWhenReadyChangeReason) {
            this.pendingPlayWhenReadyChangeReason = playbackInfoUpdate.playWhenReadyChangeReason;
        }
        if (i == 0) {
            Timeline timeline = playbackInfoUpdate.playbackInfo.timeline;
            if (!this.playbackInfo.timeline.isEmpty() && timeline.isEmpty()) {
                this.maskingWindowIndex = -1;
                this.maskingWindowPositionMs = 0L;
                this.maskingPeriodIndex = 0;
            }
            if (!timeline.isEmpty()) {
                List<Timeline> childTimelines = ((PlaylistTimeline) timeline).getChildTimelines();
                Assertions.checkState(childTimelines.size() == this.mediaSourceHolderSnapshots.size());
                for (int i2 = 0; i2 < childTimelines.size(); i2++) {
                    this.mediaSourceHolderSnapshots.get(i2).timeline = childTimelines.get(i2);
                }
            }
            if (this.pendingDiscontinuity) {
                if (playbackInfoUpdate.playbackInfo.periodId.equals(this.playbackInfo.periodId) && playbackInfoUpdate.playbackInfo.discontinuityStartPositionUs == this.playbackInfo.positionUs) {
                    z2 = false;
                }
                if (z2) {
                    if (timeline.isEmpty() || playbackInfoUpdate.playbackInfo.periodId.isAd()) {
                        jPeriodPositionUsToWindowPositionUs = playbackInfoUpdate.playbackInfo.discontinuityStartPositionUs;
                    } else {
                        PlaybackInfo playbackInfo = playbackInfoUpdate.playbackInfo;
                        jPeriodPositionUsToWindowPositionUs = periodPositionUsToWindowPositionUs(timeline, playbackInfo.periodId, playbackInfo.discontinuityStartPositionUs);
                    }
                    j = jPeriodPositionUsToWindowPositionUs;
                } else {
                    j = -9223372036854775807L;
                }
                z = z2;
            } else {
                j = -9223372036854775807L;
                z = false;
            }
            this.pendingDiscontinuity = false;
            updatePlaybackInfo(playbackInfoUpdate.playbackInfo, 1, this.pendingPlayWhenReadyChangeReason, false, z, this.pendingDiscontinuityReason, j, -1, false);
        }
    }

    private int initializeKeepSessionIdAudioTrack(int i) {
        AudioTrack audioTrack = this.keepSessionIdAudioTrack;
        if (audioTrack != null && audioTrack.getAudioSessionId() != i) {
            this.keepSessionIdAudioTrack.release();
            this.keepSessionIdAudioTrack = null;
        }
        if (this.keepSessionIdAudioTrack == null) {
            this.keepSessionIdAudioTrack = new AudioTrack(3, 4000, 4, 2, 2, 0, i);
        }
        return this.keepSessionIdAudioTrack.getAudioSessionId();
    }

    private static boolean isPlaying(PlaybackInfo playbackInfo) {
        return playbackInfo.playbackState == 3 && playbackInfo.playWhenReady && playbackInfo.playbackSuppressionReason == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Player.Listener listener, FlagSet flagSet) {
        listener.onEvents(this.wrappingPlayer, new Player.Events(flagSet));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
        this.playbackInfoUpdateHandler.post(new ExoPlayerImpl$$ExternalSyntheticLambda11(this, playbackInfoUpdate, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$release$5(Player.Listener listener) {
        listener.onPlayerError(ExoPlaybackException.createForUnexpected(new ExoTimeoutException(1), 1003));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPlaylistMetadata$7(Player.Listener listener) {
        listener.onPlaylistMetadataChanged(this.playlistMetadata);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAvailableCommands$26(Player.Listener listener) {
        listener.onAvailableCommandsChanged(this.availableCommands);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$12(PlaybackInfo playbackInfo, int i, Player.Listener listener) {
        listener.onTimelineChanged(playbackInfo.timeline, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$13(int i, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, Player.Listener listener) {
        listener.onPositionDiscontinuity(i);
        listener.onPositionDiscontinuity(positionInfo, positionInfo2, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$15(PlaybackInfo playbackInfo, Player.Listener listener) {
        listener.onPlayerErrorChanged(playbackInfo.playbackError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$16(PlaybackInfo playbackInfo, Player.Listener listener) {
        listener.onPlayerError(playbackInfo.playbackError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$17(PlaybackInfo playbackInfo, Player.Listener listener) {
        listener.onTracksChanged(playbackInfo.trackSelectorResult.tracks);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$19(PlaybackInfo playbackInfo, Player.Listener listener) {
        listener.onLoadingChanged(playbackInfo.isLoading);
        listener.onIsLoadingChanged(playbackInfo.isLoading);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$20(PlaybackInfo playbackInfo, Player.Listener listener) {
        listener.onPlayerStateChanged(playbackInfo.playWhenReady, playbackInfo.playbackState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$21(PlaybackInfo playbackInfo, Player.Listener listener) {
        listener.onPlaybackStateChanged(playbackInfo.playbackState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$22(PlaybackInfo playbackInfo, int i, Player.Listener listener) {
        listener.onPlayWhenReadyChanged(playbackInfo.playWhenReady, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$23(PlaybackInfo playbackInfo, Player.Listener listener) {
        listener.onPlaybackSuppressionReasonChanged(playbackInfo.playbackSuppressionReason);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$24(PlaybackInfo playbackInfo, Player.Listener listener) {
        listener.onIsPlayingChanged(isPlaying(playbackInfo));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$25(PlaybackInfo playbackInfo, Player.Listener listener) {
        listener.onPlaybackParametersChanged(playbackInfo.playbackParameters);
    }

    private PlaybackInfo maskTimelineAndPosition(PlaybackInfo playbackInfo, Timeline timeline, @Nullable Pair<Object, Long> pair) {
        Assertions.checkArgument(timeline.isEmpty() || pair != null);
        Timeline timeline2 = playbackInfo.timeline;
        PlaybackInfo playbackInfoCopyWithTimeline = playbackInfo.copyWithTimeline(timeline);
        if (timeline.isEmpty()) {
            MediaSource.MediaPeriodId dummyPeriodForEmptyTimeline = PlaybackInfo.getDummyPeriodForEmptyTimeline();
            long jMsToUs = Util.msToUs(this.maskingWindowPositionMs);
            PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId = playbackInfoCopyWithTimeline.copyWithNewPosition(dummyPeriodForEmptyTimeline, jMsToUs, jMsToUs, jMsToUs, 0L, TrackGroupArray.EMPTY, this.emptyTrackSelectorResult, ImmutableList.of()).copyWithLoadingMediaPeriodId(dummyPeriodForEmptyTimeline);
            playbackInfoCopyWithLoadingMediaPeriodId.bufferedPositionUs = playbackInfoCopyWithLoadingMediaPeriodId.positionUs;
            return playbackInfoCopyWithLoadingMediaPeriodId;
        }
        Object obj = playbackInfoCopyWithTimeline.periodId.periodUid;
        boolean z = !obj.equals(((Pair) Util.castNonNull(pair)).first);
        MediaSource.MediaPeriodId mediaPeriodId = z ? new MediaSource.MediaPeriodId(pair.first) : playbackInfoCopyWithTimeline.periodId;
        long jLongValue = ((Long) pair.second).longValue();
        long jMsToUs2 = Util.msToUs(getContentPosition());
        if (!timeline2.isEmpty()) {
            jMsToUs2 -= timeline2.getPeriodByUid(obj, this.period).getPositionInWindowUs();
        }
        if (z || jLongValue < jMsToUs2) {
            Assertions.checkState(!mediaPeriodId.isAd());
            PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId2 = playbackInfoCopyWithTimeline.copyWithNewPosition(mediaPeriodId, jLongValue, jLongValue, jLongValue, 0L, z ? TrackGroupArray.EMPTY : playbackInfoCopyWithTimeline.trackGroups, z ? this.emptyTrackSelectorResult : playbackInfoCopyWithTimeline.trackSelectorResult, z ? ImmutableList.of() : playbackInfoCopyWithTimeline.staticMetadata).copyWithLoadingMediaPeriodId(mediaPeriodId);
            playbackInfoCopyWithLoadingMediaPeriodId2.bufferedPositionUs = jLongValue;
            return playbackInfoCopyWithLoadingMediaPeriodId2;
        }
        if (jLongValue == jMsToUs2) {
            int indexOfPeriod = timeline.getIndexOfPeriod(playbackInfoCopyWithTimeline.loadingMediaPeriodId.periodUid);
            if (indexOfPeriod == -1 || timeline.getPeriod(indexOfPeriod, this.period).windowIndex != timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).windowIndex) {
                timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
                long adDurationUs = mediaPeriodId.isAd() ? this.period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup) : this.period.durationUs;
                playbackInfoCopyWithTimeline = playbackInfoCopyWithTimeline.copyWithNewPosition(mediaPeriodId, playbackInfoCopyWithTimeline.positionUs, playbackInfoCopyWithTimeline.positionUs, playbackInfoCopyWithTimeline.discontinuityStartPositionUs, adDurationUs - playbackInfoCopyWithTimeline.positionUs, playbackInfoCopyWithTimeline.trackGroups, playbackInfoCopyWithTimeline.trackSelectorResult, playbackInfoCopyWithTimeline.staticMetadata).copyWithLoadingMediaPeriodId(mediaPeriodId);
                playbackInfoCopyWithTimeline.bufferedPositionUs = adDurationUs;
            }
        } else {
            Assertions.checkState(!mediaPeriodId.isAd());
            long jMax = Math.max(0L, playbackInfoCopyWithTimeline.totalBufferedDurationUs - (jLongValue - jMsToUs2));
            long j = playbackInfoCopyWithTimeline.bufferedPositionUs;
            if (playbackInfoCopyWithTimeline.loadingMediaPeriodId.equals(playbackInfoCopyWithTimeline.periodId)) {
                j = jLongValue + jMax;
            }
            playbackInfoCopyWithTimeline = playbackInfoCopyWithTimeline.copyWithNewPosition(mediaPeriodId, jLongValue, jLongValue, jLongValue, jMax, playbackInfoCopyWithTimeline.trackGroups, playbackInfoCopyWithTimeline.trackSelectorResult, playbackInfoCopyWithTimeline.staticMetadata);
            playbackInfoCopyWithTimeline.bufferedPositionUs = j;
        }
        return playbackInfoCopyWithTimeline;
    }

    @Nullable
    private Pair<Object, Long> maskWindowPositionMsOrGetPeriodPositionUs(Timeline timeline, int i, long j) {
        if (timeline.isEmpty()) {
            this.maskingWindowIndex = i;
            if (j == C.TIME_UNSET) {
                j = 0;
            }
            this.maskingWindowPositionMs = j;
            this.maskingPeriodIndex = 0;
            return null;
        }
        if (i == -1 || i >= timeline.getWindowCount()) {
            i = timeline.getFirstWindowIndex(this.shuffleModeEnabled);
            j = timeline.getWindow(i, this.window).getDefaultPositionMs();
        }
        return timeline.getPeriodPositionUs(this.window, this.period, i, Util.msToUs(j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeNotifySurfaceSizeChanged(final int i, final int i2) {
        if (i == this.surfaceSize.getWidth() && i2 == this.surfaceSize.getHeight()) {
            return;
        }
        this.surfaceSize = new Size(i, i2);
        this.listeners.sendEvent(24, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda8
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((Player.Listener) obj).onSurfaceSizeChanged(i, i2);
            }
        });
    }

    private long periodPositionUsToWindowPositionUs(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, long j) {
        timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        return this.period.getPositionInWindowUs() + j;
    }

    private PlaybackInfo removeMediaItemsInternal(int i, int i2) {
        int currentMediaItemIndex = getCurrentMediaItemIndex();
        Timeline currentTimeline = getCurrentTimeline();
        int size = this.mediaSourceHolderSnapshots.size();
        this.pendingOperationAcks++;
        removeMediaSourceHolders(i, i2);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionUsAfterTimelineChanged(currentTimeline, timelineCreateMaskingTimeline));
        int i3 = playbackInfoMaskTimelineAndPosition.playbackState;
        if (i3 != 1 && i3 != 4 && i < i2 && i2 == size && currentMediaItemIndex >= playbackInfoMaskTimelineAndPosition.timeline.getWindowCount()) {
            playbackInfoMaskTimelineAndPosition = playbackInfoMaskTimelineAndPosition.copyWithPlaybackState(4);
        }
        this.internalPlayer.removeMediaSources(i, i2, this.shuffleOrder);
        return playbackInfoMaskTimelineAndPosition;
    }

    private void removeMediaSourceHolders(int i, int i2) {
        for (int i3 = i2 - 1; i3 >= i; i3--) {
            this.mediaSourceHolderSnapshots.remove(i3);
        }
        this.shuffleOrder = this.shuffleOrder.cloneAndRemove(i, i2);
    }

    private void removeSurfaceCallbacks() {
        if (this.sphericalGLSurfaceView != null) {
            createMessageInternal(this.frameMetadataListener).setType(10000).setPayload(null).send();
            this.sphericalGLSurfaceView.removeVideoSurfaceListener(this.componentListener);
            this.sphericalGLSurfaceView = null;
        }
        TextureView textureView = this.textureView;
        if (textureView != null) {
            if (textureView.getSurfaceTextureListener() != this.componentListener) {
                Log.w(TAG, "SurfaceTextureListener already unset or replaced.");
            } else {
                this.textureView.setSurfaceTextureListener(null);
            }
            this.textureView = null;
        }
        SurfaceHolder surfaceHolder = this.surfaceHolder;
        if (surfaceHolder != null) {
            surfaceHolder.removeCallback(this.componentListener);
            this.surfaceHolder = null;
        }
    }

    private void sendRendererMessage(int i, int i2, @Nullable Object obj) {
        for (Renderer renderer : this.renderers) {
            if (renderer.getTrackType() == i) {
                createMessageInternal(renderer).setType(i2).setPayload(obj).send();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendVolumeToRenderers() {
        sendRendererMessage(1, 2, Float.valueOf(this.volume * this.audioFocusManager.getVolumeMultiplier()));
    }

    private void setMediaSourcesInternal(List<MediaSource> list, int i, long j, boolean z) {
        int i2;
        long j2;
        int currentWindowIndexInternal = getCurrentWindowIndexInternal();
        long currentPosition = getCurrentPosition();
        this.pendingOperationAcks++;
        if (!this.mediaSourceHolderSnapshots.isEmpty()) {
            removeMediaSourceHolders(0, this.mediaSourceHolderSnapshots.size());
        }
        List<MediaSourceList.MediaSourceHolder> listAddMediaSourceHolders = addMediaSourceHolders(0, list);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        if (!timelineCreateMaskingTimeline.isEmpty() && i >= timelineCreateMaskingTimeline.getWindowCount()) {
            throw new IllegalSeekPositionException(timelineCreateMaskingTimeline, i, j);
        }
        if (z) {
            int firstWindowIndex = timelineCreateMaskingTimeline.getFirstWindowIndex(this.shuffleModeEnabled);
            j2 = C.TIME_UNSET;
            i2 = firstWindowIndex;
        } else if (i == -1) {
            i2 = currentWindowIndexInternal;
            j2 = currentPosition;
        } else {
            i2 = i;
            j2 = j;
        }
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, maskWindowPositionMsOrGetPeriodPositionUs(timelineCreateMaskingTimeline, i2, j2));
        int i3 = playbackInfoMaskTimelineAndPosition.playbackState;
        if (i2 != -1 && i3 != 1) {
            i3 = (timelineCreateMaskingTimeline.isEmpty() || i2 >= timelineCreateMaskingTimeline.getWindowCount()) ? 4 : 2;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackState = playbackInfoMaskTimelineAndPosition.copyWithPlaybackState(i3);
        this.internalPlayer.setMediaSources(listAddMediaSourceHolders, i2, Util.msToUs(j2), this.shuffleOrder);
        updatePlaybackInfo(playbackInfoCopyWithPlaybackState, 0, 1, false, (this.playbackInfo.periodId.periodUid.equals(playbackInfoCopyWithPlaybackState.periodId.periodUid) || this.playbackInfo.timeline.isEmpty()) ? false : true, 4, getCurrentPositionUsInternal(playbackInfoCopyWithPlaybackState), -1, false);
    }

    private void setNonVideoOutputSurfaceHolderInternal(SurfaceHolder surfaceHolder) {
        this.surfaceHolderSurfaceIsVideoOutput = false;
        this.surfaceHolder = surfaceHolder;
        surfaceHolder.addCallback(this.componentListener);
        Surface surface = this.surfaceHolder.getSurface();
        if (surface == null || !surface.isValid()) {
            maybeNotifySurfaceSizeChanged(0, 0);
        } else {
            Rect surfaceFrame = this.surfaceHolder.getSurfaceFrame();
            maybeNotifySurfaceSizeChanged(surfaceFrame.width(), surfaceFrame.height());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSurfaceTextureInternal(SurfaceTexture surfaceTexture) {
        Surface surface = new Surface(surfaceTexture);
        setVideoOutputInternal(surface);
        this.ownedSurface = surface;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoOutputInternal(@Nullable Object obj) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        Renderer[] rendererArr = this.renderers;
        int length = rendererArr.length;
        int i = 0;
        while (true) {
            z = true;
            if (i >= length) {
                break;
            }
            Renderer renderer = rendererArr[i];
            if (renderer.getTrackType() == 2) {
                arrayList.add(createMessageInternal(renderer).setType(1).setPayload(obj).send());
            }
            i++;
        }
        Object obj2 = this.videoOutput;
        if (obj2 == null || obj2 == obj) {
            z = false;
        } else {
            try {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((PlayerMessage) it.next()).blockUntilDelivered(this.detachSurfaceTimeoutMs);
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (TimeoutException unused2) {
            }
            z = false;
            Object obj3 = this.videoOutput;
            Surface surface = this.ownedSurface;
            if (obj3 == surface) {
                surface.release();
                this.ownedSurface = null;
            }
        }
        this.videoOutput = obj;
        if (z) {
            stopInternal(false, ExoPlaybackException.createForUnexpected(new ExoTimeoutException(3), 1003));
        }
    }

    private void stopInternal(boolean z, @Nullable ExoPlaybackException exoPlaybackException) {
        PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId;
        if (z) {
            playbackInfoCopyWithLoadingMediaPeriodId = removeMediaItemsInternal(0, this.mediaSourceHolderSnapshots.size()).copyWithPlaybackError(null);
        } else {
            PlaybackInfo playbackInfo = this.playbackInfo;
            playbackInfoCopyWithLoadingMediaPeriodId = playbackInfo.copyWithLoadingMediaPeriodId(playbackInfo.periodId);
            playbackInfoCopyWithLoadingMediaPeriodId.bufferedPositionUs = playbackInfoCopyWithLoadingMediaPeriodId.positionUs;
            playbackInfoCopyWithLoadingMediaPeriodId.totalBufferedDurationUs = 0L;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackState = playbackInfoCopyWithLoadingMediaPeriodId.copyWithPlaybackState(1);
        if (exoPlaybackException != null) {
            playbackInfoCopyWithPlaybackState = playbackInfoCopyWithPlaybackState.copyWithPlaybackError(exoPlaybackException);
        }
        PlaybackInfo playbackInfo2 = playbackInfoCopyWithPlaybackState;
        this.pendingOperationAcks++;
        this.internalPlayer.stop();
        updatePlaybackInfo(playbackInfo2, 0, 1, false, playbackInfo2.timeline.isEmpty() && !this.playbackInfo.timeline.isEmpty(), 4, getCurrentPositionUsInternal(playbackInfo2), -1, false);
    }

    private void updateAvailableCommands() {
        Player.Commands commands = this.availableCommands;
        Player.Commands availableCommands = Util.getAvailableCommands(this.wrappingPlayer, this.permanentAvailableCommands);
        this.availableCommands = availableCommands;
        if (availableCommands.equals(commands)) {
            return;
        }
        this.listeners.queueEvent(13, new ExoPlayerImpl$$ExternalSyntheticLambda10(this, 3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePlayWhenReady(boolean z, int i, int i2) {
        int i3 = 0;
        boolean z2 = z && i != -1;
        if (z2 && i != 1) {
            i3 = 1;
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        if (playbackInfo.playWhenReady == z2 && playbackInfo.playbackSuppressionReason == i3) {
            return;
        }
        this.pendingOperationAcks++;
        PlaybackInfo playbackInfoCopyWithPlayWhenReady = playbackInfo.copyWithPlayWhenReady(z2, i3);
        this.internalPlayer.setPlayWhenReady(z2, i3);
        updatePlaybackInfo(playbackInfoCopyWithPlayWhenReady, 0, i2, false, false, 5, C.TIME_UNSET, -1, false);
    }

    private void updatePlaybackInfo(final PlaybackInfo playbackInfo, int i, int i2, boolean z, boolean z2, int i3, long j, int i4, boolean z3) {
        final int i5;
        PlaybackInfo playbackInfo2 = this.playbackInfo;
        this.playbackInfo = playbackInfo;
        boolean z4 = !playbackInfo2.timeline.equals(playbackInfo.timeline);
        Pair<Boolean, Integer> pairEvaluateMediaItemTransitionReason = evaluateMediaItemTransitionReason(playbackInfo, playbackInfo2, z2, i3, z4, z3);
        boolean zBooleanValue = ((Boolean) pairEvaluateMediaItemTransitionReason.first).booleanValue();
        int iIntValue = ((Integer) pairEvaluateMediaItemTransitionReason.second).intValue();
        MediaMetadata mediaMetadataBuildUpdatedMediaMetadata = this.mediaMetadata;
        MediaItem mediaItem = null;
        if (zBooleanValue) {
            if (!playbackInfo.timeline.isEmpty()) {
                mediaItem = playbackInfo.timeline.getWindow(playbackInfo.timeline.getPeriodByUid(playbackInfo.periodId.periodUid, this.period).windowIndex, this.window).mediaItem;
            }
            this.staticAndDynamicMediaMetadata = MediaMetadata.EMPTY;
        }
        if (zBooleanValue || !playbackInfo2.staticMetadata.equals(playbackInfo.staticMetadata)) {
            this.staticAndDynamicMediaMetadata = this.staticAndDynamicMediaMetadata.buildUpon().populateFromMetadata(playbackInfo.staticMetadata).build();
            mediaMetadataBuildUpdatedMediaMetadata = buildUpdatedMediaMetadata();
        }
        boolean z5 = !mediaMetadataBuildUpdatedMediaMetadata.equals(this.mediaMetadata);
        this.mediaMetadata = mediaMetadataBuildUpdatedMediaMetadata;
        final int i6 = 0;
        boolean z6 = playbackInfo2.playWhenReady != playbackInfo.playWhenReady;
        boolean z7 = playbackInfo2.playbackState != playbackInfo.playbackState;
        if (z7 || z6) {
            updateWakeAndWifiLock();
        }
        boolean z8 = playbackInfo2.isLoading;
        boolean z9 = playbackInfo.isLoading;
        boolean z10 = z8 != z9;
        if (z10) {
            updatePriorityTaskManagerForIsLoadingChange(z9);
        }
        if (z4) {
            this.listeners.queueEvent(0, new ExoPlayerImpl$$ExternalSyntheticLambda5(playbackInfo, i, i6));
        }
        if (z2) {
            this.listeners.queueEvent(11, new ExoPlayerImpl$$ExternalSyntheticLambda1(i3, getPreviousPositionInfo(i3, playbackInfo2, i4), getPositionInfo(j), i6));
        }
        if (zBooleanValue) {
            this.listeners.queueEvent(1, new ExoPlayerImpl$$ExternalSyntheticLambda2(mediaItem, iIntValue, i6));
        }
        final int i7 = 4;
        if (playbackInfo2.playbackError != playbackInfo.playbackError) {
            this.listeners.queueEvent(10, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda4
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    switch (i6) {
                        case 0:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$15(playbackInfo, (Player.Listener) obj);
                            break;
                        case 1:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$23(playbackInfo, (Player.Listener) obj);
                            break;
                        case 2:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$24(playbackInfo, (Player.Listener) obj);
                            break;
                        case 3:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$25(playbackInfo, (Player.Listener) obj);
                            break;
                        case 4:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$16(playbackInfo, (Player.Listener) obj);
                            break;
                        case 5:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$17(playbackInfo, (Player.Listener) obj);
                            break;
                        case 6:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$19(playbackInfo, (Player.Listener) obj);
                            break;
                        case 7:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$20(playbackInfo, (Player.Listener) obj);
                            break;
                        default:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$21(playbackInfo, (Player.Listener) obj);
                            break;
                    }
                }
            });
            if (playbackInfo.playbackError != null) {
                this.listeners.queueEvent(10, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda4
                    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                    public final void invoke(Object obj) {
                        switch (i7) {
                            case 0:
                                ExoPlayerImpl.lambda$updatePlaybackInfo$15(playbackInfo, (Player.Listener) obj);
                                break;
                            case 1:
                                ExoPlayerImpl.lambda$updatePlaybackInfo$23(playbackInfo, (Player.Listener) obj);
                                break;
                            case 2:
                                ExoPlayerImpl.lambda$updatePlaybackInfo$24(playbackInfo, (Player.Listener) obj);
                                break;
                            case 3:
                                ExoPlayerImpl.lambda$updatePlaybackInfo$25(playbackInfo, (Player.Listener) obj);
                                break;
                            case 4:
                                ExoPlayerImpl.lambda$updatePlaybackInfo$16(playbackInfo, (Player.Listener) obj);
                                break;
                            case 5:
                                ExoPlayerImpl.lambda$updatePlaybackInfo$17(playbackInfo, (Player.Listener) obj);
                                break;
                            case 6:
                                ExoPlayerImpl.lambda$updatePlaybackInfo$19(playbackInfo, (Player.Listener) obj);
                                break;
                            case 7:
                                ExoPlayerImpl.lambda$updatePlaybackInfo$20(playbackInfo, (Player.Listener) obj);
                                break;
                            default:
                                ExoPlayerImpl.lambda$updatePlaybackInfo$21(playbackInfo, (Player.Listener) obj);
                                break;
                        }
                    }
                });
            }
        }
        TrackSelectorResult trackSelectorResult = playbackInfo2.trackSelectorResult;
        TrackSelectorResult trackSelectorResult2 = playbackInfo.trackSelectorResult;
        final int i8 = 5;
        final int i9 = 2;
        if (trackSelectorResult != trackSelectorResult2) {
            this.trackSelector.onSelectionActivated(trackSelectorResult2.info);
            this.listeners.queueEvent(2, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda4
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    switch (i8) {
                        case 0:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$15(playbackInfo, (Player.Listener) obj);
                            break;
                        case 1:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$23(playbackInfo, (Player.Listener) obj);
                            break;
                        case 2:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$24(playbackInfo, (Player.Listener) obj);
                            break;
                        case 3:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$25(playbackInfo, (Player.Listener) obj);
                            break;
                        case 4:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$16(playbackInfo, (Player.Listener) obj);
                            break;
                        case 5:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$17(playbackInfo, (Player.Listener) obj);
                            break;
                        case 6:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$19(playbackInfo, (Player.Listener) obj);
                            break;
                        case 7:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$20(playbackInfo, (Player.Listener) obj);
                            break;
                        default:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$21(playbackInfo, (Player.Listener) obj);
                            break;
                    }
                }
            });
        }
        if (z5) {
            this.listeners.queueEvent(14, new ExoPlayerImpl$$ExternalSyntheticLambda3(this.mediaMetadata, i6));
        }
        final int i10 = 6;
        final int i11 = 3;
        if (z10) {
            this.listeners.queueEvent(3, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda4
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    switch (i10) {
                        case 0:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$15(playbackInfo, (Player.Listener) obj);
                            break;
                        case 1:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$23(playbackInfo, (Player.Listener) obj);
                            break;
                        case 2:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$24(playbackInfo, (Player.Listener) obj);
                            break;
                        case 3:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$25(playbackInfo, (Player.Listener) obj);
                            break;
                        case 4:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$16(playbackInfo, (Player.Listener) obj);
                            break;
                        case 5:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$17(playbackInfo, (Player.Listener) obj);
                            break;
                        case 6:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$19(playbackInfo, (Player.Listener) obj);
                            break;
                        case 7:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$20(playbackInfo, (Player.Listener) obj);
                            break;
                        default:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$21(playbackInfo, (Player.Listener) obj);
                            break;
                    }
                }
            });
        }
        final int i12 = 7;
        if (z7 || z6) {
            this.listeners.queueEvent(-1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda4
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    switch (i12) {
                        case 0:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$15(playbackInfo, (Player.Listener) obj);
                            break;
                        case 1:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$23(playbackInfo, (Player.Listener) obj);
                            break;
                        case 2:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$24(playbackInfo, (Player.Listener) obj);
                            break;
                        case 3:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$25(playbackInfo, (Player.Listener) obj);
                            break;
                        case 4:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$16(playbackInfo, (Player.Listener) obj);
                            break;
                        case 5:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$17(playbackInfo, (Player.Listener) obj);
                            break;
                        case 6:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$19(playbackInfo, (Player.Listener) obj);
                            break;
                        case 7:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$20(playbackInfo, (Player.Listener) obj);
                            break;
                        default:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$21(playbackInfo, (Player.Listener) obj);
                            break;
                    }
                }
            });
        }
        if (z7) {
            final int i13 = 8;
            this.listeners.queueEvent(4, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda4
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    switch (i13) {
                        case 0:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$15(playbackInfo, (Player.Listener) obj);
                            break;
                        case 1:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$23(playbackInfo, (Player.Listener) obj);
                            break;
                        case 2:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$24(playbackInfo, (Player.Listener) obj);
                            break;
                        case 3:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$25(playbackInfo, (Player.Listener) obj);
                            break;
                        case 4:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$16(playbackInfo, (Player.Listener) obj);
                            break;
                        case 5:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$17(playbackInfo, (Player.Listener) obj);
                            break;
                        case 6:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$19(playbackInfo, (Player.Listener) obj);
                            break;
                        case 7:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$20(playbackInfo, (Player.Listener) obj);
                            break;
                        default:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$21(playbackInfo, (Player.Listener) obj);
                            break;
                    }
                }
            });
        }
        if (z6 != 0) {
            i5 = 1;
            this.listeners.queueEvent(5, new ExoPlayerImpl$$ExternalSyntheticLambda5(playbackInfo, i2, i5));
        } else {
            i5 = 1;
        }
        if (playbackInfo2.playbackSuppressionReason != playbackInfo.playbackSuppressionReason) {
            this.listeners.queueEvent(6, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda4
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    switch (i5) {
                        case 0:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$15(playbackInfo, (Player.Listener) obj);
                            break;
                        case 1:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$23(playbackInfo, (Player.Listener) obj);
                            break;
                        case 2:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$24(playbackInfo, (Player.Listener) obj);
                            break;
                        case 3:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$25(playbackInfo, (Player.Listener) obj);
                            break;
                        case 4:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$16(playbackInfo, (Player.Listener) obj);
                            break;
                        case 5:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$17(playbackInfo, (Player.Listener) obj);
                            break;
                        case 6:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$19(playbackInfo, (Player.Listener) obj);
                            break;
                        case 7:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$20(playbackInfo, (Player.Listener) obj);
                            break;
                        default:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$21(playbackInfo, (Player.Listener) obj);
                            break;
                    }
                }
            });
        }
        if (isPlaying(playbackInfo2) != isPlaying(playbackInfo)) {
            this.listeners.queueEvent(7, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda4
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    switch (i9) {
                        case 0:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$15(playbackInfo, (Player.Listener) obj);
                            break;
                        case 1:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$23(playbackInfo, (Player.Listener) obj);
                            break;
                        case 2:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$24(playbackInfo, (Player.Listener) obj);
                            break;
                        case 3:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$25(playbackInfo, (Player.Listener) obj);
                            break;
                        case 4:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$16(playbackInfo, (Player.Listener) obj);
                            break;
                        case 5:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$17(playbackInfo, (Player.Listener) obj);
                            break;
                        case 6:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$19(playbackInfo, (Player.Listener) obj);
                            break;
                        case 7:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$20(playbackInfo, (Player.Listener) obj);
                            break;
                        default:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$21(playbackInfo, (Player.Listener) obj);
                            break;
                    }
                }
            });
        }
        if (!playbackInfo2.playbackParameters.equals(playbackInfo.playbackParameters)) {
            this.listeners.queueEvent(12, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda4
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    switch (i11) {
                        case 0:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$15(playbackInfo, (Player.Listener) obj);
                            break;
                        case 1:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$23(playbackInfo, (Player.Listener) obj);
                            break;
                        case 2:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$24(playbackInfo, (Player.Listener) obj);
                            break;
                        case 3:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$25(playbackInfo, (Player.Listener) obj);
                            break;
                        case 4:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$16(playbackInfo, (Player.Listener) obj);
                            break;
                        case 5:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$17(playbackInfo, (Player.Listener) obj);
                            break;
                        case 6:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$19(playbackInfo, (Player.Listener) obj);
                            break;
                        case 7:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$20(playbackInfo, (Player.Listener) obj);
                            break;
                        default:
                            ExoPlayerImpl.lambda$updatePlaybackInfo$21(playbackInfo, (Player.Listener) obj);
                            break;
                    }
                }
            });
        }
        if (z) {
            this.listeners.queueEvent(-1, Format$$ExternalSyntheticLambda0.INSTANCE$4);
        }
        updateAvailableCommands();
        this.listeners.flushEvents();
        if (playbackInfo2.sleepingForOffload != playbackInfo.sleepingForOffload) {
            Iterator<ExoPlayer.AudioOffloadListener> it = this.audioOffloadListeners.iterator();
            while (it.hasNext()) {
                it.next().onExperimentalSleepingForOffloadChanged(playbackInfo.sleepingForOffload);
            }
        }
    }

    private void updatePriorityTaskManagerForIsLoadingChange(boolean z) {
        PriorityTaskManager priorityTaskManager = this.priorityTaskManager;
        if (priorityTaskManager != null) {
            if (z && !this.isPriorityTaskManagerRegistered) {
                priorityTaskManager.add(0);
                this.isPriorityTaskManagerRegistered = true;
            } else {
                if (z || !this.isPriorityTaskManagerRegistered) {
                    return;
                }
                priorityTaskManager.remove(0);
                this.isPriorityTaskManagerRegistered = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWakeAndWifiLock() {
        int playbackState = getPlaybackState();
        if (playbackState != 1) {
            if (playbackState == 2 || playbackState == 3) {
                this.wakeLockManager.setStayAwake(getPlayWhenReady() && !experimentalIsSleepingForOffload());
                this.wifiLockManager.setStayAwake(getPlayWhenReady());
                return;
            } else if (playbackState != 4) {
                throw new IllegalStateException();
            }
        }
        this.wakeLockManager.setStayAwake(false);
        this.wifiLockManager.setStayAwake(false);
    }

    private void verifyApplicationThread() {
        this.constructorFinished.blockUninterruptible();
        if (Thread.currentThread() != getApplicationLooper().getThread()) {
            String invariant = Util.formatInvariant("Player is accessed on the wrong thread.\nCurrent thread: '%s'\nExpected thread: '%s'\nSee https://developer.android.com/guide/topics/media/issues/player-accessed-on-wrong-thread", Thread.currentThread().getName(), getApplicationLooper().getThread().getName());
            if (this.throwsWhenUsingWrongThread) {
                throw new IllegalStateException(invariant);
            }
            Log.w(TAG, invariant, this.hasNotifiedFullWrongThreadWarning ? null : new IllegalStateException());
            this.hasNotifiedFullWrongThreadWarning = true;
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addAnalyticsListener(AnalyticsListener analyticsListener) {
        this.analyticsCollector.addListener((AnalyticsListener) Assertions.checkNotNull(analyticsListener));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.audioOffloadListeners.add(audioOffloadListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.Listener listener) {
        this.listeners.add((Player.Listener) Assertions.checkNotNull(listener));
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(int i, List<MediaItem> list) {
        verifyApplicationThread();
        addMediaSources(i, createMediaSources(list));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(MediaSource mediaSource) {
        verifyApplicationThread();
        addMediaSources(Collections.singletonList(mediaSource));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(List<MediaSource> list) {
        verifyApplicationThread();
        addMediaSources(this.mediaSourceHolderSnapshots.size(), list);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void clearAuxEffectInfo() {
        verifyApplicationThread();
        setAuxEffectInfo(new AuxEffectInfo(0, 0.0f));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearCameraMotionListener(CameraMotionListener cameraMotionListener) {
        verifyApplicationThread();
        if (this.cameraMotionListener != cameraMotionListener) {
            return;
        }
        createMessageInternal(this.frameMetadataListener).setType(8).setPayload(null).send();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        verifyApplicationThread();
        if (this.videoFrameMetadataListener != videoFrameMetadataListener) {
            return;
        }
        createMessageInternal(this.frameMetadataListener).setType(7).setPayload(null).send();
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearVideoSurface() {
        verifyApplicationThread();
        removeSurfaceCallbacks();
        setVideoOutputInternal(null);
        maybeNotifySurfaceSizeChanged(0, 0);
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        verifyApplicationThread();
        if (surfaceHolder == null || surfaceHolder != this.surfaceHolder) {
            return;
        }
        clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        verifyApplicationThread();
        clearVideoSurfaceHolder(surfaceView == null ? null : surfaceView.getHolder());
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearVideoTextureView(@Nullable TextureView textureView) {
        verifyApplicationThread();
        if (textureView == null || textureView != this.textureView) {
            return;
        }
        clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public PlayerMessage createMessage(PlayerMessage.Target target) {
        verifyApplicationThread();
        return createMessageInternal(target);
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.DeviceComponent
    public void decreaseDeviceVolume() {
        verifyApplicationThread();
        this.streamVolumeManager.decreaseVolume();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean experimentalIsSleepingForOffload() {
        verifyApplicationThread();
        return this.playbackInfo.sleepingForOffload;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void experimentalSetOffloadSchedulingEnabled(boolean z) {
        verifyApplicationThread();
        this.internalPlayer.experimentalSetOffloadSchedulingEnabled(z);
        Iterator<ExoPlayer.AudioOffloadListener> it = this.audioOffloadListeners.iterator();
        while (it.hasNext()) {
            it.next().onExperimentalOffloadSchedulingEnabledChanged(z);
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public AnalyticsCollector getAnalyticsCollector() {
        verifyApplicationThread();
        return this.analyticsCollector;
    }

    @Override // com.google.android.exoplayer2.Player
    public Looper getApplicationLooper() {
        return this.applicationLooper;
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public AudioAttributes getAudioAttributes() {
        verifyApplicationThread();
        return this.audioAttributes;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @CanIgnoreReturnValue
    @Deprecated
    public ExoPlayer.AudioComponent getAudioComponent() {
        verifyApplicationThread();
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public DecoderCounters getAudioDecoderCounters() {
        verifyApplicationThread();
        return this.audioDecoderCounters;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public Format getAudioFormat() {
        verifyApplicationThread();
        return this.audioFormat;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public int getAudioSessionId() {
        verifyApplicationThread();
        return this.audioSessionId;
    }

    @Override // com.google.android.exoplayer2.Player
    public Player.Commands getAvailableCommands() {
        verifyApplicationThread();
        return this.availableCommands;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getBufferedPosition() {
        verifyApplicationThread();
        if (!isPlayingAd()) {
            return getContentBufferedPosition();
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        return playbackInfo.loadingMediaPeriodId.equals(playbackInfo.periodId) ? Util.usToMs(this.playbackInfo.bufferedPositionUs) : getDuration();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Clock getClock() {
        return this.clock;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentBufferedPosition() {
        verifyApplicationThread();
        if (this.playbackInfo.timeline.isEmpty()) {
            return this.maskingWindowPositionMs;
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        if (playbackInfo.loadingMediaPeriodId.windowSequenceNumber != playbackInfo.periodId.windowSequenceNumber) {
            return playbackInfo.timeline.getWindow(getCurrentMediaItemIndex(), this.window).getDurationMs();
        }
        long j = playbackInfo.bufferedPositionUs;
        if (this.playbackInfo.loadingMediaPeriodId.isAd()) {
            PlaybackInfo playbackInfo2 = this.playbackInfo;
            Timeline.Period periodByUid = playbackInfo2.timeline.getPeriodByUid(playbackInfo2.loadingMediaPeriodId.periodUid, this.period);
            long adGroupTimeUs = periodByUid.getAdGroupTimeUs(this.playbackInfo.loadingMediaPeriodId.adGroupIndex);
            j = adGroupTimeUs == Long.MIN_VALUE ? periodByUid.durationUs : adGroupTimeUs;
        }
        PlaybackInfo playbackInfo3 = this.playbackInfo;
        return Util.usToMs(periodPositionUsToWindowPositionUs(playbackInfo3.timeline, playbackInfo3.loadingMediaPeriodId, j));
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentPosition() {
        verifyApplicationThread();
        if (!isPlayingAd()) {
            return getCurrentPosition();
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        playbackInfo.timeline.getPeriodByUid(playbackInfo.periodId.periodUid, this.period);
        PlaybackInfo playbackInfo2 = this.playbackInfo;
        return playbackInfo2.requestedContentPositionUs == C.TIME_UNSET ? playbackInfo2.timeline.getWindow(getCurrentMediaItemIndex(), this.window).getDefaultPositionMs() : this.period.getPositionInWindowMs() + Util.usToMs(this.playbackInfo.requestedContentPositionUs);
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdGroupIndex() {
        verifyApplicationThread();
        if (isPlayingAd()) {
            return this.playbackInfo.periodId.adGroupIndex;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdIndexInAdGroup() {
        verifyApplicationThread();
        if (isPlayingAd()) {
            return this.playbackInfo.periodId.adIndexInAdGroup;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.TextComponent
    public CueGroup getCurrentCues() {
        verifyApplicationThread();
        return this.currentCueGroup;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentMediaItemIndex() {
        verifyApplicationThread();
        int currentWindowIndexInternal = getCurrentWindowIndexInternal();
        if (currentWindowIndexInternal == -1) {
            return 0;
        }
        return currentWindowIndexInternal;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentPeriodIndex() {
        verifyApplicationThread();
        if (this.playbackInfo.timeline.isEmpty()) {
            return this.maskingPeriodIndex;
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        return playbackInfo.timeline.getIndexOfPeriod(playbackInfo.periodId.periodUid);
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentPosition() {
        verifyApplicationThread();
        return Util.usToMs(getCurrentPositionUsInternal(this.playbackInfo));
    }

    @Override // com.google.android.exoplayer2.Player
    public Timeline getCurrentTimeline() {
        verifyApplicationThread();
        return this.playbackInfo.timeline;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public TrackGroupArray getCurrentTrackGroups() {
        verifyApplicationThread();
        return this.playbackInfo.trackGroups;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public TrackSelectionArray getCurrentTrackSelections() {
        verifyApplicationThread();
        return new TrackSelectionArray(this.playbackInfo.trackSelectorResult.selections);
    }

    @Override // com.google.android.exoplayer2.Player
    public Tracks getCurrentTracks() {
        verifyApplicationThread();
        return this.playbackInfo.trackSelectorResult.tracks;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @CanIgnoreReturnValue
    @Deprecated
    public ExoPlayer.DeviceComponent getDeviceComponent() {
        verifyApplicationThread();
        return this;
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.DeviceComponent
    public DeviceInfo getDeviceInfo() {
        verifyApplicationThread();
        return this.deviceInfo;
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.DeviceComponent
    public int getDeviceVolume() {
        verifyApplicationThread();
        return this.streamVolumeManager.getVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getDuration() {
        verifyApplicationThread();
        if (!isPlayingAd()) {
            return getContentDuration();
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        MediaSource.MediaPeriodId mediaPeriodId = playbackInfo.periodId;
        playbackInfo.timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        return Util.usToMs(this.period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup));
    }

    @Override // com.google.android.exoplayer2.Player
    public long getMaxSeekToPreviousPosition() {
        verifyApplicationThread();
        return C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS;
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getMediaMetadata() {
        verifyApplicationThread();
        return this.mediaMetadata;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean getPauseAtEndOfMediaItems() {
        verifyApplicationThread();
        return this.pauseAtEndOfMediaItems;
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getPlayWhenReady() {
        verifyApplicationThread();
        return this.playbackInfo.playWhenReady;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Looper getPlaybackLooper() {
        return this.internalPlayer.getPlaybackLooper();
    }

    @Override // com.google.android.exoplayer2.Player
    public PlaybackParameters getPlaybackParameters() {
        verifyApplicationThread();
        return this.playbackInfo.playbackParameters;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackState() {
        verifyApplicationThread();
        return this.playbackInfo.playbackState;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackSuppressionReason() {
        verifyApplicationThread();
        return this.playbackInfo.playbackSuppressionReason;
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getPlaylistMetadata() {
        verifyApplicationThread();
        return this.playlistMetadata;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Renderer getRenderer(int i) {
        verifyApplicationThread();
        return this.renderers[i];
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererCount() {
        verifyApplicationThread();
        return this.renderers.length;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererType(int i) {
        verifyApplicationThread();
        return this.renderers[i].getTrackType();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getRepeatMode() {
        verifyApplicationThread();
        return this.repeatMode;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekBackIncrement() {
        verifyApplicationThread();
        return this.seekBackIncrementMs;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekForwardIncrement() {
        verifyApplicationThread();
        return this.seekForwardIncrementMs;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public SeekParameters getSeekParameters() {
        verifyApplicationThread();
        return this.seekParameters;
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getShuffleModeEnabled() {
        verifyApplicationThread();
        return this.shuffleModeEnabled;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public boolean getSkipSilenceEnabled() {
        verifyApplicationThread();
        return this.skipSilenceEnabled;
    }

    @Override // com.google.android.exoplayer2.Player
    public Size getSurfaceSize() {
        verifyApplicationThread();
        return this.surfaceSize;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @CanIgnoreReturnValue
    @Deprecated
    public ExoPlayer.TextComponent getTextComponent() {
        verifyApplicationThread();
        return this;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getTotalBufferedDuration() {
        verifyApplicationThread();
        return Util.usToMs(this.playbackInfo.totalBufferedDurationUs);
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionParameters getTrackSelectionParameters() {
        verifyApplicationThread();
        return this.trackSelector.getParameters();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public TrackSelector getTrackSelector() {
        verifyApplicationThread();
        return this.trackSelector;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public int getVideoChangeFrameRateStrategy() {
        verifyApplicationThread();
        return this.videoChangeFrameRateStrategy;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @CanIgnoreReturnValue
    @Deprecated
    public ExoPlayer.VideoComponent getVideoComponent() {
        verifyApplicationThread();
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public DecoderCounters getVideoDecoderCounters() {
        verifyApplicationThread();
        return this.videoDecoderCounters;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public Format getVideoFormat() {
        verifyApplicationThread();
        return this.videoFormat;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public int getVideoScalingMode() {
        verifyApplicationThread();
        return this.videoScalingMode;
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public VideoSize getVideoSize() {
        verifyApplicationThread();
        return this.videoSize;
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public float getVolume() {
        verifyApplicationThread();
        return this.volume;
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.DeviceComponent
    public void increaseDeviceVolume() {
        verifyApplicationThread();
        this.streamVolumeManager.increaseVolume();
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.DeviceComponent
    public boolean isDeviceMuted() {
        verifyApplicationThread();
        return this.streamVolumeManager.isMuted();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isLoading() {
        verifyApplicationThread();
        return this.playbackInfo.isLoading;
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlayingAd() {
        verifyApplicationThread();
        return this.playbackInfo.periodId.isAd();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean isTunnelingEnabled() {
        verifyApplicationThread();
        for (RendererConfiguration rendererConfiguration : this.playbackInfo.trackSelectorResult.rendererConfigurations) {
            if (rendererConfiguration != null && rendererConfiguration.tunneling) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItems(int i, int i2, int i3) {
        verifyApplicationThread();
        Assertions.checkArgument(i >= 0 && i <= i2 && i3 >= 0);
        int size = this.mediaSourceHolderSnapshots.size();
        int iMin = Math.min(i2, size);
        int iMin2 = Math.min(i3, size - (iMin - i));
        if (i >= size || i == iMin || i == iMin2) {
            return;
        }
        Timeline currentTimeline = getCurrentTimeline();
        this.pendingOperationAcks++;
        Util.moveItems(this.mediaSourceHolderSnapshots, i, iMin, iMin2);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionUsAfterTimelineChanged(currentTimeline, timelineCreateMaskingTimeline));
        this.internalPlayer.moveMediaSources(i, iMin, iMin2, this.shuffleOrder);
        updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, false, false, 5, C.TIME_UNSET, -1, false);
    }

    @Override // com.google.android.exoplayer2.Player
    public void prepare() {
        verifyApplicationThread();
        boolean playWhenReady = getPlayWhenReady();
        int iUpdateAudioFocus = this.audioFocusManager.updateAudioFocus(playWhenReady, 2);
        updatePlayWhenReady(playWhenReady, iUpdateAudioFocus, getPlayWhenReadyChangeReason(playWhenReady, iUpdateAudioFocus));
        PlaybackInfo playbackInfo = this.playbackInfo;
        if (playbackInfo.playbackState != 1) {
            return;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackError = playbackInfo.copyWithPlaybackError(null);
        PlaybackInfo playbackInfoCopyWithPlaybackState = playbackInfoCopyWithPlaybackError.copyWithPlaybackState(playbackInfoCopyWithPlaybackError.timeline.isEmpty() ? 4 : 2);
        this.pendingOperationAcks++;
        this.internalPlayer.prepare();
        updatePlaybackInfo(playbackInfoCopyWithPlaybackState, 1, 1, false, false, 5, C.TIME_UNSET, -1, false);
    }

    @Override // com.google.android.exoplayer2.Player
    public void release() {
        AudioTrack audioTrack;
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Release ");
        sbM.append(Integer.toHexString(System.identityHashCode(this)));
        sbM.append(" [");
        sbM.append(ExoPlayerLibraryInfo.VERSION_SLASHY);
        sbM.append("] [");
        sbM.append(Util.DEVICE_DEBUG_INFO);
        sbM.append("] [");
        sbM.append(ExoPlayerLibraryInfo.registeredModules());
        sbM.append("]");
        Log.i(TAG, sbM.toString());
        verifyApplicationThread();
        if (Util.SDK_INT < 21 && (audioTrack = this.keepSessionIdAudioTrack) != null) {
            audioTrack.release();
            this.keepSessionIdAudioTrack = null;
        }
        this.audioBecomingNoisyManager.setEnabled(false);
        this.streamVolumeManager.release();
        this.wakeLockManager.setStayAwake(false);
        this.wifiLockManager.setStayAwake(false);
        this.audioFocusManager.release();
        if (!this.internalPlayer.release()) {
            this.listeners.sendEvent(10, Format$$ExternalSyntheticLambda0.INSTANCE$1);
        }
        this.listeners.release();
        this.playbackInfoUpdateHandler.removeCallbacksAndMessages(null);
        this.bandwidthMeter.removeEventListener(this.analyticsCollector);
        PlaybackInfo playbackInfoCopyWithPlaybackState = this.playbackInfo.copyWithPlaybackState(1);
        this.playbackInfo = playbackInfoCopyWithPlaybackState;
        PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId = playbackInfoCopyWithPlaybackState.copyWithLoadingMediaPeriodId(playbackInfoCopyWithPlaybackState.periodId);
        this.playbackInfo = playbackInfoCopyWithLoadingMediaPeriodId;
        playbackInfoCopyWithLoadingMediaPeriodId.bufferedPositionUs = playbackInfoCopyWithLoadingMediaPeriodId.positionUs;
        this.playbackInfo.totalBufferedDurationUs = 0L;
        this.analyticsCollector.release();
        this.trackSelector.release();
        removeSurfaceCallbacks();
        Surface surface = this.ownedSurface;
        if (surface != null) {
            surface.release();
            this.ownedSurface = null;
        }
        if (this.isPriorityTaskManagerRegistered) {
            ((PriorityTaskManager) Assertions.checkNotNull(this.priorityTaskManager)).remove(0);
            this.isPriorityTaskManagerRegistered = false;
        }
        this.currentCueGroup = CueGroup.EMPTY_TIME_ZERO;
        this.playerReleased = true;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void removeAnalyticsListener(AnalyticsListener analyticsListener) {
        verifyApplicationThread();
        this.analyticsCollector.removeListener((AnalyticsListener) Assertions.checkNotNull(analyticsListener));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void removeAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        verifyApplicationThread();
        this.audioOffloadListeners.remove(audioOffloadListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.Listener listener) {
        verifyApplicationThread();
        this.listeners.remove((Player.Listener) Assertions.checkNotNull(listener));
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItems(int i, int i2) {
        verifyApplicationThread();
        Assertions.checkArgument(i >= 0 && i2 >= i);
        int size = this.mediaSourceHolderSnapshots.size();
        int iMin = Math.min(i2, size);
        if (i >= size || i == iMin) {
            return;
        }
        PlaybackInfo playbackInfoRemoveMediaItemsInternal = removeMediaItemsInternal(i, iMin);
        updatePlaybackInfo(playbackInfoRemoveMediaItemsInternal, 0, 1, false, !playbackInfoRemoveMediaItemsInternal.periodId.periodUid.equals(this.playbackInfo.periodId.periodUid), 4, getCurrentPositionUsInternal(playbackInfoRemoveMediaItemsInternal), -1, false);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void retry() {
        verifyApplicationThread();
        prepare();
    }

    @Override // com.google.android.exoplayer2.BasePlayer
    public void seekTo(int i, long j, int i2, boolean z) {
        verifyApplicationThread();
        Assertions.checkArgument(i >= 0);
        this.analyticsCollector.notifySeekStarted();
        Timeline timeline = this.playbackInfo.timeline;
        if (timeline.isEmpty() || i < timeline.getWindowCount()) {
            this.pendingOperationAcks++;
            if (isPlayingAd()) {
                Log.w(TAG, "seekTo ignored because an ad is playing");
                ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate = new ExoPlayerImplInternal.PlaybackInfoUpdate(this.playbackInfo);
                playbackInfoUpdate.incrementPendingOperationAcks(1);
                this.playbackInfoUpdateListener.onPlaybackInfoUpdate(playbackInfoUpdate);
                return;
            }
            int i3 = getPlaybackState() != 1 ? 2 : 1;
            int currentMediaItemIndex = getCurrentMediaItemIndex();
            PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo.copyWithPlaybackState(i3), timeline, maskWindowPositionMsOrGetPeriodPositionUs(timeline, i, j));
            this.internalPlayer.seekTo(timeline, i, Util.msToUs(j));
            updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, true, true, 1, getCurrentPositionUsInternal(playbackInfoMaskTimelineAndPosition), currentMediaItemIndex, z);
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAudioAttributes(AudioAttributes audioAttributes, boolean z) {
        verifyApplicationThread();
        if (this.playerReleased) {
            return;
        }
        if (!Util.areEqual(this.audioAttributes, audioAttributes)) {
            this.audioAttributes = audioAttributes;
            sendRendererMessage(1, 3, audioAttributes);
            this.streamVolumeManager.setStreamType(Util.getStreamTypeForAudioUsage(audioAttributes.usage));
            this.listeners.queueEvent(20, new ExoPlayerImpl$$ExternalSyntheticLambda9(audioAttributes, 0));
        }
        this.audioFocusManager.setAudioAttributes(z ? audioAttributes : null);
        this.trackSelector.setAudioAttributes(audioAttributes);
        boolean playWhenReady = getPlayWhenReady();
        int iUpdateAudioFocus = this.audioFocusManager.updateAudioFocus(playWhenReady, getPlaybackState());
        updatePlayWhenReady(playWhenReady, iUpdateAudioFocus, getPlayWhenReadyChangeReason(playWhenReady, iUpdateAudioFocus));
        this.listeners.flushEvents();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAudioSessionId(int i) {
        verifyApplicationThread();
        if (this.audioSessionId == i) {
            return;
        }
        int i2 = 0;
        if (i == 0) {
            i = Util.SDK_INT < 21 ? initializeKeepSessionIdAudioTrack(0) : Util.generateAudioSessionIdV21(this.applicationContext);
        } else if (Util.SDK_INT < 21) {
            initializeKeepSessionIdAudioTrack(i);
        }
        this.audioSessionId = i;
        sendRendererMessage(1, 10, Integer.valueOf(i));
        sendRendererMessage(2, 10, Integer.valueOf(i));
        this.listeners.sendEvent(21, new ExoPlayerImpl$$ExternalSyntheticLambda0(i, i2));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        verifyApplicationThread();
        sendRendererMessage(1, 6, auxEffectInfo);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setCameraMotionListener(CameraMotionListener cameraMotionListener) {
        verifyApplicationThread();
        this.cameraMotionListener = cameraMotionListener;
        createMessageInternal(this.frameMetadataListener).setType(8).setPayload(cameraMotionListener).send();
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.DeviceComponent
    public void setDeviceMuted(boolean z) {
        verifyApplicationThread();
        this.streamVolumeManager.setMuted(z);
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.DeviceComponent
    public void setDeviceVolume(int i) {
        verifyApplicationThread();
        this.streamVolumeManager.setVolume(i);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setForegroundMode(boolean z) {
        verifyApplicationThread();
        if (this.foregroundMode != z) {
            this.foregroundMode = z;
            if (this.internalPlayer.setForegroundMode(z)) {
                return;
            }
            stopInternal(false, ExoPlaybackException.createForUnexpected(new ExoTimeoutException(2), 1003));
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setHandleAudioBecomingNoisy(boolean z) {
        verifyApplicationThread();
        if (this.playerReleased) {
            return;
        }
        this.audioBecomingNoisyManager.setEnabled(z);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setHandleWakeLock(boolean z) {
        verifyApplicationThread();
        setWakeMode(z ? 1 : 0);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, boolean z) {
        verifyApplicationThread();
        setMediaSources(createMediaSources(list), z);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource) {
        verifyApplicationThread();
        setMediaSources(Collections.singletonList(mediaSource));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list) {
        verifyApplicationThread();
        setMediaSources(list, true);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setPauseAtEndOfMediaItems(boolean z) {
        verifyApplicationThread();
        if (this.pauseAtEndOfMediaItems == z) {
            return;
        }
        this.pauseAtEndOfMediaItems = z;
        this.internalPlayer.setPauseAtEndOfWindow(z);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlayWhenReady(boolean z) {
        verifyApplicationThread();
        int iUpdateAudioFocus = this.audioFocusManager.updateAudioFocus(z, getPlaybackState());
        updatePlayWhenReady(z, iUpdateAudioFocus, getPlayWhenReadyChangeReason(z, iUpdateAudioFocus));
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        verifyApplicationThread();
        if (playbackParameters == null) {
            playbackParameters = PlaybackParameters.DEFAULT;
        }
        if (this.playbackInfo.playbackParameters.equals(playbackParameters)) {
            return;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackParameters = this.playbackInfo.copyWithPlaybackParameters(playbackParameters);
        this.pendingOperationAcks++;
        this.internalPlayer.setPlaybackParameters(playbackParameters);
        updatePlaybackInfo(playbackInfoCopyWithPlaybackParameters, 0, 1, false, false, 5, C.TIME_UNSET, -1, false);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaylistMetadata(MediaMetadata mediaMetadata) {
        verifyApplicationThread();
        Assertions.checkNotNull(mediaMetadata);
        if (mediaMetadata.equals(this.playlistMetadata)) {
            return;
        }
        this.playlistMetadata = mediaMetadata;
        this.listeners.sendEvent(15, new ExoPlayerImpl$$ExternalSyntheticLambda10(this, 2));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @RequiresApi(23)
    public void setPreferredAudioDevice(@Nullable AudioDeviceInfo audioDeviceInfo) {
        verifyApplicationThread();
        sendRendererMessage(1, 12, audioDeviceInfo);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
        verifyApplicationThread();
        if (Util.areEqual(this.priorityTaskManager, priorityTaskManager)) {
            return;
        }
        if (this.isPriorityTaskManagerRegistered) {
            ((PriorityTaskManager) Assertions.checkNotNull(this.priorityTaskManager)).remove(0);
        }
        if (priorityTaskManager == null || !isLoading()) {
            this.isPriorityTaskManagerRegistered = false;
        } else {
            priorityTaskManager.add(0);
            this.isPriorityTaskManagerRegistered = true;
        }
        this.priorityTaskManager = priorityTaskManager;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setRepeatMode(int i) {
        verifyApplicationThread();
        if (this.repeatMode != i) {
            this.repeatMode = i;
            this.internalPlayer.setRepeatMode(i);
            this.listeners.queueEvent(8, new ExoPlayerImpl$$ExternalSyntheticLambda0(i, 1));
            updateAvailableCommands();
            this.listeners.flushEvents();
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setSeekParameters(@Nullable SeekParameters seekParameters) {
        verifyApplicationThread();
        if (seekParameters == null) {
            seekParameters = SeekParameters.DEFAULT;
        }
        if (this.seekParameters.equals(seekParameters)) {
            return;
        }
        this.seekParameters = seekParameters;
        this.internalPlayer.setSeekParameters(seekParameters);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setShuffleModeEnabled(boolean z) {
        verifyApplicationThread();
        if (this.shuffleModeEnabled != z) {
            this.shuffleModeEnabled = z;
            this.internalPlayer.setShuffleModeEnabled(z);
            this.listeners.queueEvent(9, new ExoPlayerImpl$$ExternalSyntheticLambda6(z, 1));
            updateAvailableCommands();
            this.listeners.flushEvents();
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        verifyApplicationThread();
        this.shuffleOrder = shuffleOrder;
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, maskWindowPositionMsOrGetPeriodPositionUs(timelineCreateMaskingTimeline, getCurrentMediaItemIndex(), getCurrentPosition()));
        this.pendingOperationAcks++;
        this.internalPlayer.setShuffleOrder(shuffleOrder);
        updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, false, false, 5, C.TIME_UNSET, -1, false);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setSkipSilenceEnabled(boolean z) {
        verifyApplicationThread();
        if (this.skipSilenceEnabled == z) {
            return;
        }
        this.skipSilenceEnabled = z;
        sendRendererMessage(1, 9, Boolean.valueOf(z));
        this.listeners.sendEvent(23, new ExoPlayerImpl$$ExternalSyntheticLambda6(z, 0));
    }

    @Override // com.google.android.exoplayer2.Player
    public void setTrackSelectionParameters(TrackSelectionParameters trackSelectionParameters) {
        verifyApplicationThread();
        if (!this.trackSelector.isSetParametersSupported() || trackSelectionParameters.equals(this.trackSelector.getParameters())) {
            return;
        }
        this.trackSelector.setParameters(trackSelectionParameters);
        this.listeners.sendEvent(19, new ExoPlayerImpl$$ExternalSyntheticLambda9(trackSelectionParameters, 1));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoChangeFrameRateStrategy(int i) {
        verifyApplicationThread();
        if (this.videoChangeFrameRateStrategy == i) {
            return;
        }
        this.videoChangeFrameRateStrategy = i;
        sendRendererMessage(2, 5, Integer.valueOf(i));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        verifyApplicationThread();
        this.videoFrameMetadataListener = videoFrameMetadataListener;
        createMessageInternal(this.frameMetadataListener).setType(7).setPayload(videoFrameMetadataListener).send();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoScalingMode(int i) {
        verifyApplicationThread();
        this.videoScalingMode = i;
        sendRendererMessage(2, 4, Integer.valueOf(i));
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoSurface(@Nullable Surface surface) {
        verifyApplicationThread();
        removeSurfaceCallbacks();
        setVideoOutputInternal(surface);
        int i = surface == null ? 0 : -1;
        maybeNotifySurfaceSizeChanged(i, i);
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        verifyApplicationThread();
        if (surfaceHolder == null) {
            clearVideoSurface();
            return;
        }
        removeSurfaceCallbacks();
        this.surfaceHolderSurfaceIsVideoOutput = true;
        this.surfaceHolder = surfaceHolder;
        surfaceHolder.addCallback(this.componentListener);
        Surface surface = surfaceHolder.getSurface();
        if (surface == null || !surface.isValid()) {
            setVideoOutputInternal(null);
            maybeNotifySurfaceSizeChanged(0, 0);
        } else {
            setVideoOutputInternal(surface);
            Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
            maybeNotifySurfaceSizeChanged(surfaceFrame.width(), surfaceFrame.height());
        }
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        verifyApplicationThread();
        if (surfaceView instanceof VideoDecoderOutputBufferRenderer) {
            removeSurfaceCallbacks();
            setVideoOutputInternal(surfaceView);
            setNonVideoOutputSurfaceHolderInternal(surfaceView.getHolder());
        } else {
            if (!(surfaceView instanceof SphericalGLSurfaceView)) {
                setVideoSurfaceHolder(surfaceView == null ? null : surfaceView.getHolder());
                return;
            }
            removeSurfaceCallbacks();
            this.sphericalGLSurfaceView = (SphericalGLSurfaceView) surfaceView;
            createMessageInternal(this.frameMetadataListener).setType(10000).setPayload(this.sphericalGLSurfaceView).send();
            this.sphericalGLSurfaceView.addVideoSurfaceListener(this.componentListener);
            setVideoOutputInternal(this.sphericalGLSurfaceView.getVideoSurface());
            setNonVideoOutputSurfaceHolderInternal(surfaceView.getHolder());
        }
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoTextureView(@Nullable TextureView textureView) {
        verifyApplicationThread();
        if (textureView == null) {
            clearVideoSurface();
            return;
        }
        removeSurfaceCallbacks();
        this.textureView = textureView;
        if (textureView.getSurfaceTextureListener() != null) {
            Log.w(TAG, "Replacing existing SurfaceTextureListener.");
        }
        textureView.setSurfaceTextureListener(this.componentListener);
        SurfaceTexture surfaceTexture = textureView.isAvailable() ? textureView.getSurfaceTexture() : null;
        if (surfaceTexture == null) {
            setVideoOutputInternal(null);
            maybeNotifySurfaceSizeChanged(0, 0);
        } else {
            setSurfaceTextureInternal(surfaceTexture);
            maybeNotifySurfaceSizeChanged(textureView.getWidth(), textureView.getHeight());
        }
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setVolume(float f) {
        verifyApplicationThread();
        final float fConstrainValue = Util.constrainValue(f, 0.0f, 1.0f);
        if (this.volume == fConstrainValue) {
            return;
        }
        this.volume = fConstrainValue;
        sendVolumeToRenderers();
        this.listeners.sendEvent(22, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda7
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((Player.Listener) obj).onVolumeChanged(fConstrainValue);
            }
        });
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setWakeMode(int i) {
        verifyApplicationThread();
        if (i == 0) {
            this.wakeLockManager.setEnabled(false);
            this.wifiLockManager.setEnabled(false);
        } else if (i == 1) {
            this.wakeLockManager.setEnabled(true);
            this.wifiLockManager.setEnabled(false);
        } else {
            if (i != 2) {
                return;
            }
            this.wakeLockManager.setEnabled(true);
            this.wifiLockManager.setEnabled(true);
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void stop() {
        verifyApplicationThread();
        stop(false);
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public ExoPlaybackException getPlayerError() {
        verifyApplicationThread();
        return this.playbackInfo.playbackError;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(int i, MediaSource mediaSource) {
        verifyApplicationThread();
        addMediaSources(i, Collections.singletonList(mediaSource));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(int i, List<MediaSource> list) {
        verifyApplicationThread();
        Assertions.checkArgument(i >= 0);
        int iMin = Math.min(i, this.mediaSourceHolderSnapshots.size());
        Timeline currentTimeline = getCurrentTimeline();
        this.pendingOperationAcks++;
        List<MediaSourceList.MediaSourceHolder> listAddMediaSourceHolders = addMediaSourceHolders(iMin, list);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionUsAfterTimelineChanged(currentTimeline, timelineCreateMaskingTimeline));
        this.internalPlayer.addMediaSources(iMin, listAddMediaSourceHolders, this.shuffleOrder);
        updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, false, false, 5, C.TIME_UNSET, -1, false);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, int i, long j) {
        verifyApplicationThread();
        setMediaSources(createMediaSources(list), i, j);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, long j) {
        verifyApplicationThread();
        setMediaSources(Collections.singletonList(mediaSource), 0, j);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list, boolean z) {
        verifyApplicationThread();
        setMediaSourcesInternal(list, -1, C.TIME_UNSET, z);
    }

    @Override // com.google.android.exoplayer2.Player
    public void stop(boolean z) {
        verifyApplicationThread();
        this.audioFocusManager.updateAudioFocus(getPlayWhenReady(), 1);
        stopInternal(z, null);
        this.currentCueGroup = new CueGroup(ImmutableList.of(), this.playbackInfo.positionUs);
    }

    @Override // com.google.android.exoplayer2.Player, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearVideoSurface(@Nullable Surface surface) {
        verifyApplicationThread();
        if (surface == null || surface != this.videoOutput) {
            return;
        }
        clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list, int i, long j) {
        verifyApplicationThread();
        setMediaSourcesInternal(list, i, j, false);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, boolean z) {
        verifyApplicationThread();
        setMediaSources(Collections.singletonList(mediaSource), z);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource) {
        verifyApplicationThread();
        setMediaSource(mediaSource);
        prepare();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource, boolean z, boolean z2) {
        verifyApplicationThread();
        setMediaSource(mediaSource, z);
        prepare();
    }
}
