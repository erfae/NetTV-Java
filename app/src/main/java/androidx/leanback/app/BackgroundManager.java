package androidx.leanback.app;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.leanback.R;
import androidx.leanback.widget.BackgroundHelper;
import com.google.android.exoplayer2.extractor.ogg.OggPageHeader;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class BackgroundManager {
    private static final int CHANGE_BG_DELAY_MS = 500;
    private static final int FADE_DURATION = 500;
    private static final String FRAGMENT_TAG = "androidx.leanback.app.BackgroundManager";
    private final Interpolator mAccelerateInterpolator;
    private final Animator.AnimatorListener mAnimationListener;
    private final ValueAnimator.AnimatorUpdateListener mAnimationUpdateListener;
    public final ValueAnimator mAnimator;
    public boolean mAttached;
    private boolean mAutoReleaseOnStop = true;
    public int mBackgroundColor;
    public Drawable mBackgroundDrawable;
    private View mBgView;
    public ChangeBackgroundRunnable mChangeRunnable;
    private boolean mChangeRunnablePending;
    public Activity mContext;
    private final Interpolator mDecelerateInterpolator;
    private BackgroundFragment mFragmentState;
    public Handler mHandler;
    private int mHeightPx;
    public int mImageInWrapperIndex;
    private long mLastSetTime;
    public TranslucentLayerDrawable mLayerDrawable;
    private BackgroundContinuityService mService;
    private int mThemeDrawableResourceId;
    private int mWidthPx;

    public static class BackgroundContinuityService {
        private static final boolean DEBUG = false;
        private static final String TAG = "BackgroundContinuity";
        private static BackgroundContinuityService sService = new BackgroundContinuityService();
        private int mColor;
        private int mCount;
        private Drawable mDrawable;
        private int mLastThemeDrawableId;
        private WeakReference<Drawable.ConstantState> mLastThemeDrawableState;

        private BackgroundContinuityService() {
            reset();
        }

        public static BackgroundContinuityService getInstance() {
            BackgroundContinuityService backgroundContinuityService = sService;
            backgroundContinuityService.mCount++;
            return backgroundContinuityService;
        }

        private void reset() {
            this.mColor = 0;
            this.mDrawable = null;
        }

        public int getColor() {
            return this.mColor;
        }

        public Drawable getDrawable() {
            return this.mDrawable;
        }

        public Drawable getThemeDrawable(Context context, int i) {
            Drawable.ConstantState constantState;
            WeakReference<Drawable.ConstantState> weakReference = this.mLastThemeDrawableState;
            Drawable drawableNewDrawable = (weakReference == null || this.mLastThemeDrawableId != i || (constantState = weakReference.get()) == null) ? null : constantState.newDrawable();
            if (drawableNewDrawable != null) {
                return drawableNewDrawable;
            }
            Drawable drawable = ContextCompat.getDrawable(context, i);
            this.mLastThemeDrawableState = new WeakReference<>(drawable.getConstantState());
            this.mLastThemeDrawableId = i;
            return drawable;
        }

        public void setColor(int i) {
            this.mColor = i;
            this.mDrawable = null;
        }

        public void setDrawable(Drawable drawable) {
            this.mDrawable = drawable;
        }

        public void unref() {
            int i = this.mCount;
            if (i <= 0) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Can't unref, count ");
                sbM.append(this.mCount);
                throw new IllegalStateException(sbM.toString());
            }
            int i2 = i - 1;
            this.mCount = i2;
            if (i2 == 0) {
                reset();
            }
        }
    }

    public static class BitmapDrawable extends Drawable {
        public boolean mMutated;
        public ConstantState mState;

        public BitmapDrawable() {
            this.mState = new ConstantState(null, null);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            ConstantState constantState = this.mState;
            if (constantState.mBitmap == null) {
                return;
            }
            if (constantState.mPaint.getAlpha() < 255 && this.mState.mPaint.getColorFilter() != null) {
                throw new IllegalStateException("Can't draw with translucent alpha and color filter");
            }
            ConstantState constantState2 = this.mState;
            canvas.drawBitmap(constantState2.mBitmap, constantState2.mMatrix, constantState2.mPaint);
        }

        @Override // android.graphics.drawable.Drawable
        public ColorFilter getColorFilter() {
            return this.mState.mPaint.getColorFilter();
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        @Override // android.graphics.drawable.Drawable
        @NonNull
        public Drawable mutate() {
            if (!this.mMutated) {
                this.mMutated = true;
                this.mState = new ConstantState(this.mState);
            }
            return this;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            mutate();
            if (this.mState.mPaint.getAlpha() != i) {
                this.mState.mPaint.setAlpha(i);
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            mutate();
            this.mState.mPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public ConstantState getConstantState() {
            return this.mState;
        }

        public BitmapDrawable(Bitmap bitmap, Matrix matrix) {
            this.mState = new ConstantState(bitmap, matrix);
        }

        public static final class ConstantState extends Drawable.ConstantState {
            public final Bitmap mBitmap;
            public final Matrix mMatrix;
            public final Paint mPaint;

            public ConstantState(Bitmap bitmap, Matrix matrix) {
                Paint paint = new Paint();
                this.mPaint = paint;
                this.mBitmap = bitmap;
                this.mMatrix = matrix == null ? new Matrix() : matrix;
                paint.setFilterBitmap(true);
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return 0;
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public Drawable newDrawable() {
                return new BitmapDrawable(this);
            }

            public ConstantState(ConstantState constantState) {
                Paint paint = new Paint();
                this.mPaint = paint;
                this.mBitmap = constantState.mBitmap;
                this.mMatrix = constantState.mMatrix != null ? new Matrix(constantState.mMatrix) : new Matrix();
                if (constantState.mPaint.getAlpha() != 255) {
                    paint.setAlpha(constantState.mPaint.getAlpha());
                }
                if (constantState.mPaint.getColorFilter() != null) {
                    paint.setColorFilter(constantState.mPaint.getColorFilter());
                }
                paint.setFilterBitmap(true);
            }
        }

        public BitmapDrawable(ConstantState constantState) {
            this.mState = constantState;
        }
    }

    public final class ChangeBackgroundRunnable implements Runnable {
        public final Drawable mDrawable;

        public ChangeBackgroundRunnable(Drawable drawable) {
            this.mDrawable = drawable;
        }

        private void runTask() {
            Drawable drawable;
            BackgroundManager backgroundManager = BackgroundManager.this;
            TranslucentLayerDrawable translucentLayerDrawable = backgroundManager.mLayerDrawable;
            if (translucentLayerDrawable == null) {
                return;
            }
            DrawableWrapper drawableWrapper = translucentLayerDrawable == null ? null : translucentLayerDrawable.mWrapper[backgroundManager.mImageInWrapperIndex];
            if (drawableWrapper != null) {
                if (backgroundManager.sameDrawable(this.mDrawable, drawableWrapper.getDrawable())) {
                    return;
                }
                BackgroundManager backgroundManager2 = BackgroundManager.this;
                backgroundManager2.mLayerDrawable.clearDrawable(R.id.background_imagein, backgroundManager2.mContext);
                BackgroundManager.this.mLayerDrawable.updateDrawable(R.id.background_imageout, drawableWrapper.getDrawable());
            }
            BackgroundManager backgroundManager3 = BackgroundManager.this;
            if (backgroundManager3.mAttached) {
                TranslucentLayerDrawable translucentLayerDrawable2 = backgroundManager3.mLayerDrawable;
                if ((translucentLayerDrawable2 != null ? translucentLayerDrawable2.mWrapper[backgroundManager3.mImageInWrapperIndex] : null) == null && (drawable = this.mDrawable) != null) {
                    translucentLayerDrawable2.updateDrawable(R.id.background_imagein, drawable);
                    BackgroundManager backgroundManager4 = BackgroundManager.this;
                    TranslucentLayerDrawable translucentLayerDrawable3 = backgroundManager4.mLayerDrawable;
                    int i = backgroundManager4.mImageInWrapperIndex;
                    DrawableWrapper[] drawableWrapperArr = translucentLayerDrawable3.mWrapper;
                    if (drawableWrapperArr[i] != null) {
                        drawableWrapperArr[i].mAlpha = 0;
                        translucentLayerDrawable3.invalidateSelf();
                    }
                }
                BackgroundManager.this.mAnimator.setDuration(500L);
                BackgroundManager.this.mAnimator.start();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            runTask();
            BackgroundManager.this.mChangeRunnable = null;
        }
    }

    public static class EmptyDrawable extends BitmapDrawable {
        public EmptyDrawable(Resources resources) {
        }
    }

    public static final class TranslucentLayerDrawable extends LayerDrawable {
        public int mAlpha;
        public WeakReference<BackgroundManager> mManagerWeakReference;
        public boolean mSuspendInvalidation;
        public DrawableWrapper[] mWrapper;

        public TranslucentLayerDrawable(BackgroundManager backgroundManager, Drawable[] drawableArr) {
            super(drawableArr);
            this.mAlpha = 255;
            this.mManagerWeakReference = new WeakReference<>(backgroundManager);
            int length = drawableArr.length;
            this.mWrapper = new DrawableWrapper[length];
            for (int i = 0; i < length; i++) {
                this.mWrapper[i] = new DrawableWrapper(drawableArr[i]);
            }
        }

        public void clearDrawable(int i, Context context) {
            for (int i2 = 0; i2 < getNumberOfLayers(); i2++) {
                if (getId(i2) == i) {
                    this.mWrapper[i2] = null;
                    if (getDrawable(i2) instanceof EmptyDrawable) {
                        return;
                    }
                    super.setDrawableByLayerId(i, new EmptyDrawable(context.getResources()));
                    return;
                }
            }
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            Drawable drawable;
            int i;
            int i2;
            int i3 = 0;
            while (true) {
                DrawableWrapper[] drawableWrapperArr = this.mWrapper;
                if (i3 >= drawableWrapperArr.length) {
                    return;
                }
                if (drawableWrapperArr[i3] != null && (drawable = drawableWrapperArr[i3].getDrawable()) != null) {
                    int alpha = DrawableCompat.getAlpha(drawable);
                    int i4 = this.mAlpha;
                    if (i4 < 255) {
                        i = i4 * alpha;
                        i2 = 1;
                    } else {
                        i = alpha;
                        i2 = 0;
                    }
                    DrawableWrapper[] drawableWrapperArr2 = this.mWrapper;
                    if (drawableWrapperArr2[i3].mAlpha < 255) {
                        i *= drawableWrapperArr2[i3].mAlpha;
                        i2++;
                    }
                    if (i2 == 0) {
                        drawable.draw(canvas);
                    } else {
                        if (i2 == 1) {
                            i /= 255;
                        } else if (i2 == 2) {
                            i /= OggPageHeader.MAX_PAGE_PAYLOAD;
                        }
                        try {
                            this.mSuspendInvalidation = true;
                            drawable.setAlpha(i);
                            drawable.draw(canvas);
                            drawable.setAlpha(alpha);
                            this.mSuspendInvalidation = false;
                        } catch (Throwable th) {
                            this.mSuspendInvalidation = false;
                            throw th;
                        }
                    }
                }
                i3++;
            }
        }

        public int findWrapperIndexById(int i) {
            for (int i2 = 0; i2 < getNumberOfLayers(); i2++) {
                if (getId(i2) == i) {
                    return i2;
                }
            }
            return -1;
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public int getAlpha() {
            return this.mAlpha;
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable.Callback
        public void invalidateDrawable(Drawable drawable) {
            if (this.mSuspendInvalidation) {
                return;
            }
            super.invalidateDrawable(drawable);
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public Drawable mutate() {
            Drawable drawableMutate = super.mutate();
            int numberOfLayers = getNumberOfLayers();
            for (int i = 0; i < numberOfLayers; i++) {
                DrawableWrapper[] drawableWrapperArr = this.mWrapper;
                if (drawableWrapperArr[i] != null) {
                    drawableWrapperArr[i] = new DrawableWrapper(drawableWrapperArr[i], getDrawable(i));
                }
            }
            return drawableMutate;
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            if (this.mAlpha != i) {
                this.mAlpha = i;
                invalidateSelf();
                BackgroundManager backgroundManager = this.mManagerWeakReference.get();
                if (backgroundManager != null) {
                    backgroundManager.postChangeRunnable();
                }
            }
        }

        @Override // android.graphics.drawable.LayerDrawable
        public boolean setDrawableByLayerId(int i, Drawable drawable) {
            return updateDrawable(i, drawable) != null;
        }

        public DrawableWrapper updateDrawable(int i, Drawable drawable) {
            super.setDrawableByLayerId(i, drawable);
            for (int i2 = 0; i2 < getNumberOfLayers(); i2++) {
                if (getId(i2) == i) {
                    this.mWrapper[i2] = new DrawableWrapper(drawable);
                    invalidateSelf();
                    return this.mWrapper[i2];
                }
            }
            return null;
        }
    }

    private BackgroundManager(Activity activity) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() { // from class: androidx.leanback.app.BackgroundManager.1
            public final Runnable mRunnable = new Runnable() { // from class: androidx.leanback.app.BackgroundManager.1.1
                @Override // java.lang.Runnable
                public void run() {
                    BackgroundManager.this.postChangeRunnable();
                }
            };

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BackgroundManager backgroundManager = BackgroundManager.this;
                TranslucentLayerDrawable translucentLayerDrawable = backgroundManager.mLayerDrawable;
                if (translucentLayerDrawable != null) {
                    translucentLayerDrawable.clearDrawable(R.id.background_imageout, backgroundManager.mContext);
                }
                BackgroundManager.this.mHandler.post(this.mRunnable);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        };
        this.mAnimationListener = animatorListener;
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.leanback.app.BackgroundManager.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                BackgroundManager backgroundManager = BackgroundManager.this;
                int i = backgroundManager.mImageInWrapperIndex;
                if (i != -1) {
                    TranslucentLayerDrawable translucentLayerDrawable = backgroundManager.mLayerDrawable;
                    DrawableWrapper[] drawableWrapperArr = translucentLayerDrawable.mWrapper;
                    if (drawableWrapperArr[i] != null) {
                        drawableWrapperArr[i].mAlpha = iIntValue;
                        translucentLayerDrawable.invalidateSelf();
                    }
                }
            }
        };
        this.mAnimationUpdateListener = animatorUpdateListener;
        this.mContext = activity;
        this.mService = BackgroundContinuityService.getInstance();
        this.mHeightPx = this.mContext.getResources().getDisplayMetrics().heightPixels;
        this.mWidthPx = this.mContext.getResources().getDisplayMetrics().widthPixels;
        this.mHandler = new Handler();
        FastOutLinearInInterpolator fastOutLinearInInterpolator = new FastOutLinearInInterpolator();
        this.mAccelerateInterpolator = AnimationUtils.loadInterpolator(this.mContext, android.R.anim.accelerate_interpolator);
        this.mDecelerateInterpolator = AnimationUtils.loadInterpolator(this.mContext, android.R.anim.decelerate_interpolator);
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 255);
        this.mAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.addListener(animatorListener);
        valueAnimatorOfInt.addUpdateListener(animatorUpdateListener);
        valueAnimatorOfInt.setInterpolator(fastOutLinearInInterpolator);
        TypedArray typedArrayObtainStyledAttributes = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
        this.mThemeDrawableResourceId = typedArrayObtainStyledAttributes.getResourceId(0, -1);
        typedArrayObtainStyledAttributes.recycle();
        createFragment(activity);
    }

    private void createFragment(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        String str = FRAGMENT_TAG;
        BackgroundFragment backgroundFragment = (BackgroundFragment) fragmentManager.findFragmentByTag(str);
        if (backgroundFragment == null) {
            backgroundFragment = new BackgroundFragment();
            activity.getFragmentManager().beginTransaction().add(backgroundFragment, str).commit();
        } else if (backgroundFragment.getBackgroundManager() != null) {
            throw new IllegalStateException("Created duplicated BackgroundManager for same activity, please use getInstance() instead");
        }
        backgroundFragment.setBackgroundManager(this);
        this.mFragmentState = backgroundFragment;
    }

    public static BackgroundManager getInstance(Activity activity) {
        BackgroundManager backgroundManager;
        BackgroundFragment backgroundFragment = (BackgroundFragment) activity.getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        return (backgroundFragment == null || (backgroundManager = backgroundFragment.getBackgroundManager()) == null) ? new BackgroundManager(activity) : backgroundManager;
    }

    private long getRunnableDelay() {
        return Math.max(0L, (this.mLastSetTime + 500) - System.currentTimeMillis());
    }

    private Drawable getThemeDrawable() {
        int i = this.mThemeDrawableResourceId;
        Drawable themeDrawable = i != -1 ? this.mService.getThemeDrawable(this.mContext, i) : null;
        return themeDrawable == null ? new EmptyDrawable(this.mContext.getResources()) : themeDrawable;
    }

    private void lazyInit() {
        if (this.mLayerDrawable != null) {
            return;
        }
        LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(this.mContext, R.drawable.lb_background).mutate();
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        Drawable[] drawableArr = new Drawable[numberOfLayers];
        for (int i = 0; i < numberOfLayers; i++) {
            drawableArr[i] = layerDrawable.getDrawable(i);
        }
        TranslucentLayerDrawable translucentLayerDrawable = new TranslucentLayerDrawable(this, drawableArr);
        for (int i2 = 0; i2 < numberOfLayers; i2++) {
            translucentLayerDrawable.setId(i2, layerDrawable.getId(i2));
        }
        this.mLayerDrawable = translucentLayerDrawable;
        this.mImageInWrapperIndex = translucentLayerDrawable.findWrapperIndexById(R.id.background_imagein);
        this.mLayerDrawable.findWrapperIndexById(R.id.background_imageout);
        BackgroundHelper.setBackgroundPreservingAlpha(this.mBgView, this.mLayerDrawable);
    }

    private void setDrawableInternal(Drawable drawable) {
        if (!this.mAttached) {
            throw new IllegalStateException("Must attach before setting background drawable");
        }
        ChangeBackgroundRunnable changeBackgroundRunnable = this.mChangeRunnable;
        if (changeBackgroundRunnable != null) {
            if (sameDrawable(drawable, changeBackgroundRunnable.mDrawable)) {
                return;
            }
            this.mHandler.removeCallbacks(this.mChangeRunnable);
            this.mChangeRunnable = null;
        }
        this.mChangeRunnable = new ChangeBackgroundRunnable(drawable);
        this.mChangeRunnablePending = true;
        postChangeRunnable();
    }

    private void syncWithService() {
        int color = this.mService.getColor();
        Drawable drawable = this.mService.getDrawable();
        this.mBackgroundColor = color;
        this.mBackgroundDrawable = drawable == null ? null : drawable.getConstantState().newDrawable().mutate();
        updateImmediate();
    }

    private void updateImmediate() {
        if (this.mAttached) {
            lazyInit();
            Drawable drawable = this.mBackgroundDrawable;
            if (drawable == null) {
                this.mLayerDrawable.updateDrawable(R.id.background_imagein, getDefaultDrawable());
            } else {
                this.mLayerDrawable.updateDrawable(R.id.background_imagein, drawable);
            }
            this.mLayerDrawable.clearDrawable(R.id.background_imageout, this.mContext);
        }
    }

    public void attach(Window window) {
        attachToViewInternal(window.getDecorView());
    }

    public void attachToView(View view) {
        attachToViewInternal(view);
        this.mContext.getWindow().getDecorView().setBackground(Build.VERSION.SDK_INT >= 26 ? null : new ColorDrawable(0));
    }

    public final void attachToViewInternal(View view) {
        if (this.mAttached) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Already attached to ");
            sbM.append(this.mBgView);
            throw new IllegalStateException(sbM.toString());
        }
        this.mBgView = view;
        this.mAttached = true;
        syncWithService();
    }

    public void clearDrawable() {
        setDrawable(null);
    }

    public final void detach() {
        release();
        this.mBgView = null;
        this.mAttached = false;
        BackgroundContinuityService backgroundContinuityService = this.mService;
        if (backgroundContinuityService != null) {
            backgroundContinuityService.unref();
            this.mService = null;
        }
    }

    @ColorInt
    public final int getColor() {
        return this.mBackgroundColor;
    }

    @Deprecated
    public Drawable getDefaultDimLayer() {
        return ContextCompat.getDrawable(this.mContext, R.color.lb_background_protection);
    }

    public final Drawable getDefaultDrawable() {
        return this.mBackgroundColor != 0 ? new ColorDrawable(this.mBackgroundColor) : getThemeDrawable();
    }

    @Deprecated
    public Drawable getDimLayer() {
        return null;
    }

    public Drawable getDrawable() {
        return this.mBackgroundDrawable;
    }

    public boolean isAttached() {
        return this.mAttached;
    }

    public boolean isAutoReleaseOnStop() {
        return this.mAutoReleaseOnStop;
    }

    public final void onActivityStart() {
        updateImmediate();
    }

    public final void postChangeRunnable() {
        if (this.mChangeRunnable == null || !this.mChangeRunnablePending || this.mAnimator.isStarted() || !this.mFragmentState.isResumed() || this.mLayerDrawable.getAlpha() < 255) {
            return;
        }
        long runnableDelay = getRunnableDelay();
        this.mLastSetTime = System.currentTimeMillis();
        this.mHandler.postDelayed(this.mChangeRunnable, runnableDelay);
        this.mChangeRunnablePending = false;
    }

    public void release() {
        ChangeBackgroundRunnable changeBackgroundRunnable = this.mChangeRunnable;
        if (changeBackgroundRunnable != null) {
            this.mHandler.removeCallbacks(changeBackgroundRunnable);
            this.mChangeRunnable = null;
        }
        if (this.mAnimator.isStarted()) {
            this.mAnimator.cancel();
        }
        TranslucentLayerDrawable translucentLayerDrawable = this.mLayerDrawable;
        if (translucentLayerDrawable != null) {
            translucentLayerDrawable.clearDrawable(R.id.background_imagein, this.mContext);
            this.mLayerDrawable.clearDrawable(R.id.background_imageout, this.mContext);
            this.mLayerDrawable = null;
        }
        this.mBackgroundDrawable = null;
    }

    public final boolean sameDrawable(Drawable drawable, Drawable drawable2) {
        if (drawable != null && drawable2 != null) {
            if (drawable == drawable2) {
                return true;
            }
            if ((drawable instanceof BitmapDrawable) && (drawable2 instanceof BitmapDrawable) && ((BitmapDrawable) drawable).mState.mBitmap.sameAs(((BitmapDrawable) drawable2).mState.mBitmap)) {
                return true;
            }
            if ((drawable instanceof ColorDrawable) && (drawable2 instanceof ColorDrawable) && ((ColorDrawable) drawable).getColor() == ((ColorDrawable) drawable2).getColor()) {
                return true;
            }
        }
        return false;
    }

    public void setAutoReleaseOnStop(boolean z) {
        this.mAutoReleaseOnStop = z;
    }

    public void setBitmap(Bitmap bitmap) {
        Matrix matrix = null;
        if (bitmap == null) {
            setDrawable(null);
            return;
        }
        if (bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
            return;
        }
        if (bitmap.getWidth() != this.mWidthPx || bitmap.getHeight() != this.mHeightPx) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int i = this.mHeightPx;
            int i2 = width * i;
            int i3 = this.mWidthPx;
            float f = i2 > i3 * height ? i / height : i3 / width;
            int iMax = Math.max(0, (width - Math.min((int) (i3 / f), width)) / 2);
            Matrix matrix2 = new Matrix();
            matrix2.setScale(f, f);
            matrix2.preTranslate(-iMax, 0.0f);
            matrix = matrix2;
        }
        this.mContext.getResources();
        setDrawable(new BitmapDrawable(bitmap, matrix));
    }

    public void setColor(@ColorInt int i) {
        this.mService.setColor(i);
        this.mBackgroundColor = i;
        this.mBackgroundDrawable = null;
        if (this.mLayerDrawable == null) {
            return;
        }
        setDrawableInternal(getDefaultDrawable());
    }

    @Deprecated
    public void setDimLayer(Drawable drawable) {
    }

    public void setDrawable(Drawable drawable) {
        this.mService.setDrawable(drawable);
        this.mBackgroundDrawable = drawable;
        if (this.mLayerDrawable == null) {
            return;
        }
        if (drawable == null) {
            setDrawableInternal(getDefaultDrawable());
        } else {
            setDrawableInternal(drawable);
        }
    }

    public void setThemeDrawableResourceId(int i) {
        this.mThemeDrawableResourceId = i;
    }

    public static final class DrawableWrapper {
        public int mAlpha;
        public final Drawable mDrawable;

        public DrawableWrapper(Drawable drawable) {
            this.mAlpha = 255;
            this.mDrawable = drawable;
        }

        public Drawable getDrawable() {
            return this.mDrawable;
        }

        public void setColor(int i) {
            ((ColorDrawable) this.mDrawable).setColor(i);
        }

        public DrawableWrapper(DrawableWrapper drawableWrapper, Drawable drawable) {
            this.mAlpha = 255;
            this.mDrawable = drawable;
            this.mAlpha = drawableWrapper.mAlpha;
        }
    }
}
