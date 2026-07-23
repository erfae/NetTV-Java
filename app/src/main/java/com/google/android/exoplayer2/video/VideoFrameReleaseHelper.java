package com.google.android.exoplayer2.video;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Choreographer;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.nettv.livestore.activities.MovieActivity$$ExternalSyntheticLambda2;

/* JADX INFO: loaded from: classes.dex */
public final class VideoFrameReleaseHelper {
    private static final long MAX_ALLOWED_ADJUSTMENT_NS = 20000000;
    private static final int MINIMUM_FRAMES_WITHOUT_SYNC_TO_CLEAR_SURFACE_FRAME_RATE = 30;
    private static final long MINIMUM_MATCHING_FRAME_DURATION_FOR_HIGH_CONFIDENCE_NS = 5000000000L;
    private static final float MINIMUM_MEDIA_FRAME_RATE_CHANGE_FOR_UPDATE_HIGH_CONFIDENCE = 0.02f;
    private static final float MINIMUM_MEDIA_FRAME_RATE_CHANGE_FOR_UPDATE_LOW_CONFIDENCE = 1.0f;
    private static final String TAG = "VideoFrameReleaseHelper";
    private static final long VSYNC_OFFSET_PERCENTAGE = 80;
    private static final long VSYNC_SAMPLE_UPDATE_PERIOD_MS = 500;
    private int changeFrameRateStrategy;

    @Nullable
    private final DisplayHelper displayHelper;
    private float formatFrameRate;
    private long frameIndex;
    private final FixedFrameRateEstimator frameRateEstimator = new FixedFrameRateEstimator();
    private long lastAdjustedFrameIndex;
    private long lastAdjustedReleaseTimeNs;
    private long pendingLastAdjustedFrameIndex;
    private long pendingLastAdjustedReleaseTimeNs;
    private float playbackSpeed;
    private boolean started;

    @Nullable
    private Surface surface;
    private float surfaceMediaFrameRate;
    private float surfacePlaybackFrameRate;
    private long vsyncDurationNs;
    private long vsyncOffsetNs;

    @Nullable
    private final VSyncSampler vsyncSampler;

    @RequiresApi(30)
    public static final class Api30 {
        private Api30() {
        }

        @DoNotInline
        public static void setSurfaceFrameRate(Surface surface, float f) {
            try {
                surface.setFrameRate(f, f == 0.0f ? 0 : 1);
            } catch (IllegalStateException e) {
                Log.e(VideoFrameReleaseHelper.TAG, "Failed to call Surface.setFrameRate", e);
            }
        }
    }

    public interface DisplayHelper {

        public interface Listener {
            void onDefaultDisplayChanged(@Nullable Display display);
        }

        void register(Listener listener);

        void unregister();
    }

    public static final class DisplayHelperV16 implements DisplayHelper {
        private final WindowManager windowManager;

        private DisplayHelperV16(WindowManager windowManager) {
            this.windowManager = windowManager;
        }

        @Nullable
        public static DisplayHelper maybeBuildNewInstance(Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null) {
                return new DisplayHelperV16(windowManager);
            }
            return null;
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void register(DisplayHelper.Listener listener) {
            listener.onDefaultDisplayChanged(this.windowManager.getDefaultDisplay());
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void unregister() {
        }
    }

    @RequiresApi(17)
    public static final class DisplayHelperV17 implements DisplayHelper, DisplayManager.DisplayListener {
        private final DisplayManager displayManager;

        @Nullable
        private DisplayHelper.Listener listener;

        private DisplayHelperV17(DisplayManager displayManager) {
            this.displayManager = displayManager;
        }

        private Display getDefaultDisplay() {
            return this.displayManager.getDisplay(0);
        }

        @Nullable
        public static DisplayHelper maybeBuildNewInstance(Context context) {
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            if (displayManager != null) {
                return new DisplayHelperV17(displayManager);
            }
            return null;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            DisplayHelper.Listener listener = this.listener;
            if (listener == null || i != 0) {
                return;
            }
            listener.onDefaultDisplayChanged(getDefaultDisplay());
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void register(DisplayHelper.Listener listener) {
            this.listener = listener;
            this.displayManager.registerDisplayListener(this, Util.createHandlerForCurrentLooper());
            listener.onDefaultDisplayChanged(getDefaultDisplay());
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void unregister() {
            this.displayManager.unregisterDisplayListener(this);
            this.listener = null;
        }
    }

    public static final class VSyncSampler implements Choreographer.FrameCallback, Handler.Callback {
        private static final int CREATE_CHOREOGRAPHER = 0;
        private static final VSyncSampler INSTANCE = new VSyncSampler();
        private static final int MSG_ADD_OBSERVER = 1;
        private static final int MSG_REMOVE_OBSERVER = 2;
        private Choreographer choreographer;
        private final HandlerThread choreographerOwnerThread;
        private final Handler handler;
        private int observerCount;
        public volatile long sampledVsyncTimeNs = C.TIME_UNSET;

        private VSyncSampler() {
            HandlerThread handlerThread = new HandlerThread("ExoPlayer:FrameReleaseChoreographer");
            this.choreographerOwnerThread = handlerThread;
            handlerThread.start();
            Handler handlerCreateHandler = Util.createHandler(handlerThread.getLooper(), this);
            this.handler = handlerCreateHandler;
            handlerCreateHandler.sendEmptyMessage(0);
        }

        private void addObserverInternal() {
            Choreographer choreographer = this.choreographer;
            if (choreographer != null) {
                int i = this.observerCount + 1;
                this.observerCount = i;
                if (i == 1) {
                    choreographer.postFrameCallback(this);
                }
            }
        }

        private void createChoreographerInstanceInternal() {
            try {
                this.choreographer = Choreographer.getInstance();
            } catch (RuntimeException e) {
                Log.w(VideoFrameReleaseHelper.TAG, "Vsync sampling disabled due to platform error", e);
            }
        }

        public static VSyncSampler getInstance() {
            return INSTANCE;
        }

        private void removeObserverInternal() {
            Choreographer choreographer = this.choreographer;
            if (choreographer != null) {
                int i = this.observerCount - 1;
                this.observerCount = i;
                if (i == 0) {
                    choreographer.removeFrameCallback(this);
                    this.sampledVsyncTimeNs = C.TIME_UNSET;
                }
            }
        }

        public void addObserver() {
            this.handler.sendEmptyMessage(1);
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            this.sampledVsyncTimeNs = j;
            ((Choreographer) Assertions.checkNotNull(this.choreographer)).postFrameCallbackDelayed(this, 500L);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                createChoreographerInstanceInternal();
                return true;
            }
            if (i == 1) {
                addObserverInternal();
                return true;
            }
            if (i != 2) {
                return false;
            }
            removeObserverInternal();
            return true;
        }

        public void removeObserver() {
            this.handler.sendEmptyMessage(2);
        }
    }

    public VideoFrameReleaseHelper(@Nullable Context context) {
        DisplayHelper displayHelperMaybeBuildDisplayHelper = maybeBuildDisplayHelper(context);
        this.displayHelper = displayHelperMaybeBuildDisplayHelper;
        this.vsyncSampler = displayHelperMaybeBuildDisplayHelper != null ? VSyncSampler.getInstance() : null;
        this.vsyncDurationNs = C.TIME_UNSET;
        this.vsyncOffsetNs = C.TIME_UNSET;
        this.formatFrameRate = -1.0f;
        this.playbackSpeed = 1.0f;
        this.changeFrameRateStrategy = 0;
    }

    private static boolean adjustmentAllowed(long j, long j2) {
        return Math.abs(j - j2) <= MAX_ALLOWED_ADJUSTMENT_NS;
    }

    private void clearSurfaceFrameRate() {
        Surface surface;
        if (Util.SDK_INT < 30 || (surface = this.surface) == null || this.changeFrameRateStrategy == Integer.MIN_VALUE || this.surfacePlaybackFrameRate == 0.0f) {
            return;
        }
        this.surfacePlaybackFrameRate = 0.0f;
        Api30.setSurfaceFrameRate(surface, 0.0f);
    }

    private static long closestVsync(long j, long j2, long j3) {
        long j4;
        long j5 = (((j - j2) / j3) * j3) + j2;
        if (j <= j5) {
            j4 = j5 - j3;
        } else {
            j4 = j5;
            j5 = j3 + j5;
        }
        return j5 - j < j - j4 ? j5 : j4;
    }

    @Nullable
    private static DisplayHelper maybeBuildDisplayHelper(@Nullable Context context) {
        if (context == null) {
            return null;
        }
        Context applicationContext = context.getApplicationContext();
        DisplayHelper displayHelperMaybeBuildNewInstance = Util.SDK_INT >= 17 ? DisplayHelperV17.maybeBuildNewInstance(applicationContext) : null;
        return displayHelperMaybeBuildNewInstance == null ? DisplayHelperV16.maybeBuildNewInstance(applicationContext) : displayHelperMaybeBuildNewInstance;
    }

    private void resetAdjustment() {
        this.frameIndex = 0L;
        this.lastAdjustedFrameIndex = -1L;
        this.pendingLastAdjustedFrameIndex = -1L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDefaultDisplayRefreshRateParams(@Nullable Display display) {
        if (display != null) {
            long refreshRate = (long) (1.0E9d / ((double) display.getRefreshRate()));
            this.vsyncDurationNs = refreshRate;
            this.vsyncOffsetNs = (refreshRate * VSYNC_OFFSET_PERCENTAGE) / 100;
        } else {
            Log.w(TAG, "Unable to query display refresh rate");
            this.vsyncDurationNs = C.TIME_UNSET;
            this.vsyncOffsetNs = C.TIME_UNSET;
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x005f  */
    private void updateSurfaceMediaFrameRate() {
        if (Util.SDK_INT < 30 || this.surface == null) {
            return;
        }
        float frameRate = this.frameRateEstimator.isSynced() ? this.frameRateEstimator.getFrameRate() : this.formatFrameRate;
        float f = this.surfaceMediaFrameRate;
        if (frameRate == f) {
            return;
        }
        boolean z = true;
        if (frameRate != -1.0f && f != -1.0f) {
            if (Math.abs(frameRate - this.surfaceMediaFrameRate) < (this.frameRateEstimator.isSynced() && (this.frameRateEstimator.getMatchingFrameDurationSumNs() > MINIMUM_MATCHING_FRAME_DURATION_FOR_HIGH_CONFIDENCE_NS ? 1 : (this.frameRateEstimator.getMatchingFrameDurationSumNs() == MINIMUM_MATCHING_FRAME_DURATION_FOR_HIGH_CONFIDENCE_NS ? 0 : -1)) >= 0 ? MINIMUM_MEDIA_FRAME_RATE_CHANGE_FOR_UPDATE_HIGH_CONFIDENCE : 1.0f)) {
                z = false;
            }
        } else if (frameRate == -1.0f && this.frameRateEstimator.getFramesWithoutSyncCount() < 30) {
            z = false;
        }
        if (z) {
            this.surfaceMediaFrameRate = frameRate;
            updateSurfacePlaybackFrameRate(false);
        }
    }

    private void updateSurfacePlaybackFrameRate(boolean z) {
        Surface surface;
        if (Util.SDK_INT < 30 || (surface = this.surface) == null || this.changeFrameRateStrategy == Integer.MIN_VALUE) {
            return;
        }
        float f = 0.0f;
        if (this.started) {
            float f2 = this.surfaceMediaFrameRate;
            if (f2 != -1.0f) {
                f = this.playbackSpeed * f2;
            }
        }
        if (z || this.surfacePlaybackFrameRate != f) {
            this.surfacePlaybackFrameRate = f;
            Api30.setSurfaceFrameRate(surface, f);
        }
    }

    public long adjustReleaseTime(long j) {
        long j2;
        if (this.lastAdjustedFrameIndex == -1 || !this.frameRateEstimator.isSynced()) {
            j2 = j;
        } else {
            long frameDurationNs = this.lastAdjustedReleaseTimeNs + ((long) (((this.frameIndex - this.lastAdjustedFrameIndex) * this.frameRateEstimator.getFrameDurationNs()) / this.playbackSpeed));
            if (adjustmentAllowed(j, frameDurationNs)) {
                j2 = frameDurationNs;
            } else {
                resetAdjustment();
                j2 = j;
            }
        }
        this.pendingLastAdjustedFrameIndex = this.frameIndex;
        this.pendingLastAdjustedReleaseTimeNs = j2;
        VSyncSampler vSyncSampler = this.vsyncSampler;
        if (vSyncSampler == null || this.vsyncDurationNs == C.TIME_UNSET) {
            return j2;
        }
        long j3 = vSyncSampler.sampledVsyncTimeNs;
        return j3 == C.TIME_UNSET ? j2 : closestVsync(j2, j3, this.vsyncDurationNs) - this.vsyncOffsetNs;
    }

    public void onFormatChanged(float f) {
        this.formatFrameRate = f;
        this.frameRateEstimator.reset();
        updateSurfaceMediaFrameRate();
    }

    public void onNextFrame(long j) {
        long j2 = this.pendingLastAdjustedFrameIndex;
        if (j2 != -1) {
            this.lastAdjustedFrameIndex = j2;
            this.lastAdjustedReleaseTimeNs = this.pendingLastAdjustedReleaseTimeNs;
        }
        this.frameIndex++;
        this.frameRateEstimator.onNextFrame(j * 1000);
        updateSurfaceMediaFrameRate();
    }

    public void onPlaybackSpeed(float f) {
        this.playbackSpeed = f;
        resetAdjustment();
        updateSurfacePlaybackFrameRate(false);
    }

    public void onPositionReset() {
        resetAdjustment();
    }

    public void onStarted() {
        this.started = true;
        resetAdjustment();
        if (this.displayHelper != null) {
            ((VSyncSampler) Assertions.checkNotNull(this.vsyncSampler)).addObserver();
            this.displayHelper.register(new MovieActivity$$ExternalSyntheticLambda2(this, 5));
        }
        updateSurfacePlaybackFrameRate(false);
    }

    public void onStopped() {
        this.started = false;
        DisplayHelper displayHelper = this.displayHelper;
        if (displayHelper != null) {
            displayHelper.unregister();
            ((VSyncSampler) Assertions.checkNotNull(this.vsyncSampler)).removeObserver();
        }
        clearSurfaceFrameRate();
    }

    public void onSurfaceChanged(@Nullable Surface surface) {
        if (surface instanceof PlaceholderSurface) {
            surface = null;
        }
        if (this.surface == surface) {
            return;
        }
        clearSurfaceFrameRate();
        this.surface = surface;
        updateSurfacePlaybackFrameRate(true);
    }

    public void setChangeFrameRateStrategy(int i) {
        if (this.changeFrameRateStrategy == i) {
            return;
        }
        this.changeFrameRateStrategy = i;
        updateSurfacePlaybackFrameRate(true);
    }
}
