package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;
import android.util.TypedValue;
import android.widget.MediaController;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.ws.WebSocketProtocol;
import pl.droidsonroids.gif.transforms.CornerRadiusTransform;
import pl.droidsonroids.gif.transforms.Transform;

/* JADX INFO: loaded from: classes2.dex */
public class GifDrawable extends Drawable implements Animatable, MediaController.MediaPlayerControl {
    public final Bitmap mBuffer;
    private final Rect mDstRect;
    public final ScheduledThreadPoolExecutor mExecutor;
    public final InvalidationHandler mInvalidationHandler;
    public final boolean mIsRenderingTriggeredOnDraw;
    public volatile boolean mIsRunning;
    public final ConcurrentLinkedQueue<AnimationListener> mListeners;
    public final GifInfoHandle mNativeInfoHandle;
    public long mNextFrameRenderTime;
    public final Paint mPaint;
    private final RenderTask mRenderTask;
    public ScheduledFuture<?> mRenderTaskSchedule;
    private int mScaledHeight;
    private int mScaledWidth;
    private final Rect mSrcRect;
    private ColorStateList mTint;
    private PorterDuffColorFilter mTintFilter;
    private PorterDuff.Mode mTintMode;
    private Transform mTransform;

    public GifDrawable(@NonNull Resources resources, @DrawableRes @RawRes int i) throws Resources.NotFoundException, IOException {
        this(resources.openRawResourceFd(i));
        List<String> list = GifViewUtils.SUPPORTED_RESOURCE_TYPE_NAMES;
        TypedValue typedValue = new TypedValue();
        resources.getValue(i, typedValue, true);
        int i2 = typedValue.density;
        if (i2 == 0) {
            i2 = 160;
        } else if (i2 == 65535) {
            i2 = 0;
        }
        int i3 = resources.getDisplayMetrics().densityDpi;
        float f = (i2 <= 0 || i3 <= 0) ? 1.0f : i3 / i2;
        this.mScaledHeight = (int) (this.mNativeInfoHandle.getHeight() * f);
        this.mScaledWidth = (int) (this.mNativeInfoHandle.getWidth() * f);
    }

    private void cancelPendingRenderTask() {
        ScheduledFuture<?> scheduledFuture = this.mRenderTaskSchedule;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.mInvalidationHandler.removeMessages(-1);
    }

    @Nullable
    public static GifDrawable createFromResource(@NonNull Resources resources, @DrawableRes @RawRes int i) {
        try {
            return new GifDrawable(resources, i);
        } catch (IOException unused) {
            return null;
        }
    }

    private void scheduleNextRender() {
        if (this.mIsRenderingTriggeredOnDraw && this.mIsRunning) {
            long j = this.mNextFrameRenderTime;
            if (j != Long.MIN_VALUE) {
                long jMax = Math.max(0L, j - SystemClock.uptimeMillis());
                this.mNextFrameRenderTime = Long.MIN_VALUE;
                this.mExecutor.remove(this.mRenderTask);
                this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, jMax, TimeUnit.MILLISECONDS);
            }
        }
    }

    private void shutdown() {
        this.mIsRunning = false;
        this.mInvalidationHandler.removeMessages(-1);
        this.mNativeInfoHandle.recycle();
    }

    private PorterDuffColorFilter updateTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    public void addAnimationListener(@NonNull AnimationListener animationListener) {
        this.mListeners.add(animationListener);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canPause() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return getNumberOfFrames() > 1;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return getNumberOfFrames() > 1;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        boolean z;
        if (this.mTintFilter == null || this.mPaint.getColorFilter() != null) {
            z = false;
        } else {
            this.mPaint.setColorFilter(this.mTintFilter);
            z = true;
        }
        Transform transform = this.mTransform;
        if (transform == null) {
            canvas.drawBitmap(this.mBuffer, this.mSrcRect, this.mDstRect, this.mPaint);
        } else {
            transform.onDraw(canvas, this.mPaint, this.mBuffer);
        }
        if (z) {
            this.mPaint.setColorFilter(null);
        }
    }

    public long getAllocationByteCount() {
        return this.mNativeInfoHandle.getAllocationByteCount() + ((long) this.mBuffer.getAllocationByteCount());
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getAudioSessionId() {
        return 0;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getBufferPercentage() {
        return 100;
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @Nullable
    public String getComment() {
        return this.mNativeInfoHandle.getComment();
    }

    @FloatRange(from = 0.0d)
    public float getCornerRadius() {
        Transform transform = this.mTransform;
        if (transform instanceof CornerRadiusTransform) {
            return ((CornerRadiusTransform) transform).getCornerRadius();
        }
        return 0.0f;
    }

    public Bitmap getCurrentFrame() {
        Bitmap bitmap = this.mBuffer;
        Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(), this.mBuffer.isMutable());
        bitmapCopy.setHasAlpha(this.mBuffer.hasAlpha());
        return bitmapCopy;
    }

    public int getCurrentFrameIndex() {
        return this.mNativeInfoHandle.getCurrentFrameIndex();
    }

    public int getCurrentLoop() {
        int currentLoop = this.mNativeInfoHandle.getCurrentLoop();
        return (currentLoop == 0 || currentLoop < this.mNativeInfoHandle.getLoopCount()) ? currentLoop : currentLoop - 1;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getCurrentPosition() {
        return this.mNativeInfoHandle.getCurrentPosition();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getDuration() {
        return this.mNativeInfoHandle.getDuration();
    }

    @NonNull
    public GifError getError() {
        return GifError.fromCode(this.mNativeInfoHandle.getNativeErrorCode());
    }

    public int getFrameByteCount() {
        return this.mBuffer.getHeight() * this.mBuffer.getRowBytes();
    }

    public int getFrameDuration(@IntRange(from = 0) int i) {
        return this.mNativeInfoHandle.getFrameDuration(i);
    }

    public long getInputSourceByteCount() {
        return this.mNativeInfoHandle.getSourceLength();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mScaledHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mScaledWidth;
    }

    public int getLoopCount() {
        return this.mNativeInfoHandle.getLoopCount();
    }

    public long getMetadataAllocationByteCount() {
        return this.mNativeInfoHandle.getMetadataByteCount();
    }

    public int getNumberOfFrames() {
        return this.mNativeInfoHandle.getNumberOfFrames();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return (!this.mNativeInfoHandle.isOpaque() || this.mPaint.getAlpha() < 255) ? -2 : -1;
    }

    @NonNull
    public final Paint getPaint() {
        return this.mPaint;
    }

    public int getPixel(@IntRange(from = 0) int i, @IntRange(from = 0) int i2) {
        if (i >= this.mNativeInfoHandle.getWidth()) {
            throw new IllegalArgumentException("x must be < width");
        }
        if (i2 < this.mNativeInfoHandle.getHeight()) {
            return this.mBuffer.getPixel(i, i2);
        }
        throw new IllegalArgumentException("y must be < height");
    }

    public void getPixels(@NonNull int[] iArr) {
        this.mBuffer.getPixels(iArr, 0, this.mNativeInfoHandle.getWidth(), 0, 0, this.mNativeInfoHandle.getWidth(), this.mNativeInfoHandle.getHeight());
    }

    @Nullable
    public Transform getTransform() {
        return this.mTransform;
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        super.invalidateSelf();
        scheduleNextRender();
    }

    public boolean isAnimationCompleted() {
        return this.mNativeInfoHandle.isAnimationCompleted();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        return this.mIsRunning;
    }

    public boolean isRecycled() {
        return this.mNativeInfoHandle.isRecycled();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mIsRunning;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ColorStateList colorStateList;
        return super.isStateful() || ((colorStateList = this.mTint) != null && colorStateList.isStateful());
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        this.mDstRect.set(rect);
        Transform transform = this.mTransform;
        if (transform != null) {
            transform.onBoundsChange(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.mTint;
        if (colorStateList == null || (mode = this.mTintMode) == null) {
            return false;
        }
        this.mTintFilter = updateTintFilter(colorStateList, mode);
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void pause() {
        stop();
    }

    public void recycle() {
        shutdown();
        this.mBuffer.recycle();
    }

    public boolean removeAnimationListener(AnimationListener animationListener) {
        return this.mListeners.remove(animationListener);
    }

    public void reset() {
        this.mExecutor.execute(new SafeRunnable(this) { // from class: pl.droidsonroids.gif.GifDrawable.1
            @Override // pl.droidsonroids.gif.SafeRunnable
            public void doWork() {
                if (GifDrawable.this.mNativeInfoHandle.reset()) {
                    GifDrawable.this.start();
                }
            }
        });
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void seekTo(@IntRange(from = 0, to = 2147483647L) final int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        this.mExecutor.execute(new SafeRunnable(this) { // from class: pl.droidsonroids.gif.GifDrawable.2
            @Override // pl.droidsonroids.gif.SafeRunnable
            public void doWork() {
                GifDrawable gifDrawable = GifDrawable.this;
                gifDrawable.mNativeInfoHandle.seekToTime(i, gifDrawable.mBuffer);
                this.mGifDrawable.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
            }
        });
    }

    public void seekToBlocking(@IntRange(from = 0, to = 2147483647L) int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        synchronized (this.mNativeInfoHandle) {
            this.mNativeInfoHandle.seekToTime(i, this.mBuffer);
        }
        this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
    }

    public void seekToFrame(@IntRange(from = 0, to = 2147483647L) final int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Frame index is not positive");
        }
        this.mExecutor.execute(new SafeRunnable(this) { // from class: pl.droidsonroids.gif.GifDrawable.3
            @Override // pl.droidsonroids.gif.SafeRunnable
            public void doWork() {
                GifDrawable gifDrawable = GifDrawable.this;
                gifDrawable.mNativeInfoHandle.seekToFrame(i, gifDrawable.mBuffer);
                GifDrawable.this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
            }
        });
    }

    public Bitmap seekToFrameAndGet(@IntRange(from = 0, to = 2147483647L) int i) {
        Bitmap currentFrame;
        if (i < 0) {
            throw new IndexOutOfBoundsException("Frame index is not positive");
        }
        synchronized (this.mNativeInfoHandle) {
            this.mNativeInfoHandle.seekToFrame(i, this.mBuffer);
            currentFrame = getCurrentFrame();
        }
        this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
        return currentFrame;
    }

    public Bitmap seekToPositionAndGet(@IntRange(from = 0, to = 2147483647L) int i) {
        Bitmap currentFrame;
        if (i < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        synchronized (this.mNativeInfoHandle) {
            this.mNativeInfoHandle.seekToTime(i, this.mBuffer);
            currentFrame = getCurrentFrame();
        }
        this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
        return currentFrame;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(@IntRange(from = 0, to = 255) int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setCornerRadius(@FloatRange(from = 0.0d) float f) {
        CornerRadiusTransform cornerRadiusTransform = new CornerRadiusTransform(f);
        this.mTransform = cornerRadiusTransform;
        cornerRadiusTransform.onBoundsChange(this.mDstRect);
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mPaint.setDither(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    public void setLoopCount(@IntRange(from = 0, to = WebSocketProtocol.PAYLOAD_SHORT_MAX) int i) {
        this.mNativeInfoHandle.setLoopCount(i);
    }

    public void setSpeed(@FloatRange(from = 0.0d, fromInclusive = false) float f) {
        this.mNativeInfoHandle.setSpeedFactor(f);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mTint = colorStateList;
        this.mTintFilter = updateTintFilter(colorStateList, this.mTintMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(@Nullable PorterDuff.Mode mode) {
        this.mTintMode = mode;
        this.mTintFilter = updateTintFilter(this.mTint, mode);
        invalidateSelf();
    }

    public void setTransform(@Nullable Transform transform) {
        this.mTransform = transform;
        if (transform != null) {
            transform.onBoundsChange(this.mDstRect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (!this.mIsRenderingTriggeredOnDraw) {
            if (z) {
                if (z2) {
                    reset();
                }
                if (visible) {
                    start();
                }
            } else if (visible) {
                stop();
            }
        }
        return visible;
    }

    @Override // android.graphics.drawable.Animatable, android.widget.MediaController.MediaPlayerControl
    public void start() {
        synchronized (this) {
            if (this.mIsRunning) {
                return;
            }
            this.mIsRunning = true;
            startAnimation(this.mNativeInfoHandle.restoreRemainder());
        }
    }

    public final void startAnimation(long j) {
        if (this.mIsRenderingTriggeredOnDraw) {
            this.mNextFrameRenderTime = 0L;
            this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
        } else {
            cancelPendingRenderTask();
            this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, Math.max(j, 0L), TimeUnit.MILLISECONDS);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        synchronized (this) {
            if (this.mIsRunning) {
                this.mIsRunning = false;
                cancelPendingRenderTask();
                this.mNativeInfoHandle.saveRemainder();
            }
        }
    }

    @NonNull
    public String toString() {
        return String.format(Locale.ENGLISH, "GIF: size: %dx%d, frames: %d, error: %d", Integer.valueOf(this.mNativeInfoHandle.getWidth()), Integer.valueOf(this.mNativeInfoHandle.getHeight()), Integer.valueOf(this.mNativeInfoHandle.getNumberOfFrames()), Integer.valueOf(this.mNativeInfoHandle.getNativeErrorCode()));
    }

    public GifDrawable(@NonNull AssetManager assetManager, @NonNull String str) throws IOException {
        this(assetManager.openFd(str));
    }

    public GifDrawable(@NonNull String str) throws IOException {
        this(new GifInfoHandle(str), null, null, true);
    }

    public GifDrawable(@NonNull File file) throws IOException {
        this(file.getPath());
    }

    public GifDrawable(@NonNull InputStream inputStream) throws IOException {
        this(new GifInfoHandle(inputStream), null, null, true);
    }

    public GifDrawable(@NonNull AssetFileDescriptor assetFileDescriptor) throws IOException {
        this(new GifInfoHandle(assetFileDescriptor), null, null, true);
    }

    public GifDrawable(@NonNull FileDescriptor fileDescriptor) throws IOException {
        this(new GifInfoHandle(fileDescriptor), null, null, true);
    }

    public GifDrawable(@NonNull byte[] bArr) throws IOException {
        this(new GifInfoHandle(bArr), null, null, true);
    }

    public GifDrawable(@NonNull ByteBuffer byteBuffer) throws IOException {
        this(new GifInfoHandle(byteBuffer), null, null, true);
    }

    public GifDrawable(@Nullable ContentResolver contentResolver, @NonNull Uri uri) throws IOException {
        this(GifInfoHandle.openUri(contentResolver, uri), null, null, true);
    }

    public GifDrawable(GifInfoHandle gifInfoHandle, GifDrawable gifDrawable, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, boolean z) {
        this.mIsRunning = true;
        this.mNextFrameRenderTime = Long.MIN_VALUE;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mListeners = new ConcurrentLinkedQueue<>();
        RenderTask renderTask = new RenderTask(this);
        this.mRenderTask = renderTask;
        this.mIsRenderingTriggeredOnDraw = z;
        if (scheduledThreadPoolExecutor == null) {
            int i = GifRenderingExecutor.$r8$clinit;
            scheduledThreadPoolExecutor = GifRenderingExecutor.InstanceHolder.INSTANCE;
        }
        this.mExecutor = scheduledThreadPoolExecutor;
        this.mNativeInfoHandle = gifInfoHandle;
        Bitmap bitmap = null;
        if (gifDrawable != null) {
            synchronized (gifDrawable.mNativeInfoHandle) {
                if (!gifDrawable.mNativeInfoHandle.isRecycled() && gifDrawable.mNativeInfoHandle.getHeight() >= gifInfoHandle.getHeight() && gifDrawable.mNativeInfoHandle.getWidth() >= gifInfoHandle.getWidth()) {
                    gifDrawable.shutdown();
                    Bitmap bitmap2 = gifDrawable.mBuffer;
                    bitmap2.eraseColor(0);
                    bitmap = bitmap2;
                }
            }
        }
        if (bitmap == null) {
            this.mBuffer = Bitmap.createBitmap(gifInfoHandle.getWidth(), gifInfoHandle.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            this.mBuffer = bitmap;
        }
        this.mBuffer.setHasAlpha(!gifInfoHandle.isOpaque());
        this.mSrcRect = new Rect(0, 0, gifInfoHandle.getWidth(), gifInfoHandle.getHeight());
        this.mInvalidationHandler = new InvalidationHandler(this);
        renderTask.doWork();
        this.mScaledWidth = gifInfoHandle.getWidth();
        this.mScaledHeight = gifInfoHandle.getHeight();
    }
}
