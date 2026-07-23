package androidx.swiperefreshlayout.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.util.Preconditions;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class CircularProgressDrawable extends Drawable implements Animatable {
    private static final int ANIMATION_DURATION = 1332;
    private static final int ARROW_HEIGHT = 5;
    private static final int ARROW_HEIGHT_LARGE = 6;
    private static final int ARROW_WIDTH = 10;
    private static final int ARROW_WIDTH_LARGE = 12;
    private static final float CENTER_RADIUS = 7.5f;
    private static final float CENTER_RADIUS_LARGE = 11.0f;
    private static final float COLOR_CHANGE_OFFSET = 0.75f;
    public static final int DEFAULT = 1;
    private static final float GROUP_FULL_ROTATION = 216.0f;
    public static final int LARGE = 0;
    private static final float MAX_PROGRESS_ARC = 0.8f;
    private static final float MIN_PROGRESS_ARC = 0.01f;
    private static final float RING_ROTATION = 0.20999998f;
    private static final float SHRINK_OFFSET = 0.5f;
    private static final float STROKE_WIDTH = 2.5f;
    private static final float STROKE_WIDTH_LARGE = 3.0f;
    private Animator mAnimator;
    public boolean mFinishing;
    private Resources mResources;
    private final Ring mRing;
    private float mRotation;
    public float mRotationCount;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final int[] COLORS = {-16777216};

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ProgressDrawableSize {
    }

    public static class Ring {
        public int mAlpha;
        public Path mArrow;
        public int mArrowHeight;
        public final Paint mArrowPaint;
        public float mArrowScale;
        public int mArrowWidth;
        public final Paint mCirclePaint;
        public int mColorIndex;
        public int[] mColors;
        public int mCurrentColor;
        public float mEndTrim;
        public final Paint mPaint;
        public float mRingCenterRadius;
        public float mRotation;
        public boolean mShowArrow;
        public float mStartTrim;
        public float mStartingEndTrim;
        public float mStartingRotation;
        public float mStartingStartTrim;
        public float mStrokeWidth;
        public final RectF mTempBounds = new RectF();

        public Ring() {
            Paint paint = new Paint();
            this.mPaint = paint;
            Paint paint2 = new Paint();
            this.mArrowPaint = paint2;
            Paint paint3 = new Paint();
            this.mCirclePaint = paint3;
            this.mStartTrim = 0.0f;
            this.mEndTrim = 0.0f;
            this.mRotation = 0.0f;
            this.mStrokeWidth = 5.0f;
            this.mArrowScale = 1.0f;
            this.mAlpha = 255;
            paint.setStrokeCap(Paint.Cap.SQUARE);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint2.setStyle(Paint.Style.FILL);
            paint2.setAntiAlias(true);
            paint3.setColor(0);
        }

        public final void setColorIndex(int i) {
            this.mColorIndex = i;
            this.mCurrentColor = this.mColors[i];
        }

        public final void setShowArrow(boolean z) {
            if (this.mShowArrow != z) {
                this.mShowArrow = z;
            }
        }
    }

    public CircularProgressDrawable(@NonNull Context context) {
        this.mResources = ((Context) Preconditions.checkNotNull(context)).getResources();
        Ring ring = new Ring();
        this.mRing = ring;
        ring.mColors = COLORS;
        ring.setColorIndex(0);
        setStrokeWidth(STROKE_WIDTH);
        setupAnimators();
    }

    private void applyFinishTranslation(float f, Ring ring) {
        updateRingColor(f, ring);
        float fFloor = (float) (Math.floor(ring.mStartingRotation / MAX_PROGRESS_ARC) + 1.0d);
        float f2 = ring.mStartingStartTrim;
        float f3 = ring.mStartingEndTrim;
        ring.mStartTrim = (((f3 - MIN_PROGRESS_ARC) - f2) * f) + f2;
        ring.mEndTrim = f3;
        float f4 = ring.mStartingRotation;
        ring.mRotation = Insets$$ExternalSyntheticOutline0.m(fFloor, f4, f, f4);
    }

    private int evaluateColorChange(float f, int i, int i2) {
        int i3 = (i >> 24) & 255;
        int i4 = (i >> 16) & 255;
        int i5 = (i >> 8) & 255;
        int i6 = i & 255;
        return ((i3 + ((int) ((((i2 >> 24) & 255) - i3) * f))) << 24) | ((i4 + ((int) ((((i2 >> 16) & 255) - i4) * f))) << 16) | ((i5 + ((int) ((((i2 >> 8) & 255) - i5) * f))) << 8) | (i6 + ((int) (f * ((i2 & 255) - i6))));
    }

    private float getRotation() {
        return this.mRotation;
    }

    private void setRotation(float f) {
        this.mRotation = f;
    }

    private void setSizeParameters(float f, float f2, float f3, float f4) {
        Ring ring = this.mRing;
        float f5 = this.mResources.getDisplayMetrics().density;
        float f6 = f2 * f5;
        ring.mStrokeWidth = f6;
        ring.mPaint.setStrokeWidth(f6);
        ring.mRingCenterRadius = f * f5;
        ring.setColorIndex(0);
        ring.mArrowWidth = (int) (f3 * f5);
        ring.mArrowHeight = (int) (f4 * f5);
    }

    private void setupAnimators() {
        final Ring ring = this.mRing;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.swiperefreshlayout.widget.CircularProgressDrawable.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                CircularProgressDrawable.this.updateRingColor(fFloatValue, ring);
                CircularProgressDrawable.this.applyTransformation(fFloatValue, ring, false);
                CircularProgressDrawable.this.invalidateSelf();
            }
        });
        valueAnimatorOfFloat.setRepeatCount(-1);
        valueAnimatorOfFloat.setRepeatMode(1);
        valueAnimatorOfFloat.setInterpolator(LINEAR_INTERPOLATOR);
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: androidx.swiperefreshlayout.widget.CircularProgressDrawable.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
                CircularProgressDrawable.this.applyTransformation(1.0f, ring, true);
                Ring ring2 = ring;
                ring2.mStartingStartTrim = ring2.mStartTrim;
                ring2.mStartingEndTrim = ring2.mEndTrim;
                ring2.mStartingRotation = ring2.mRotation;
                ring2.setColorIndex((ring2.mColorIndex + 1) % ring2.mColors.length);
                CircularProgressDrawable circularProgressDrawable = CircularProgressDrawable.this;
                if (!circularProgressDrawable.mFinishing) {
                    circularProgressDrawable.mRotationCount += 1.0f;
                    return;
                }
                circularProgressDrawable.mFinishing = false;
                animator.cancel();
                animator.setDuration(1332L);
                animator.start();
                ring.setShowArrow(false);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                CircularProgressDrawable.this.mRotationCount = 0.0f;
            }
        });
        this.mAnimator = valueAnimatorOfFloat;
    }

    public final void applyTransformation(float f, Ring ring, boolean z) {
        float interpolation;
        float interpolation2;
        if (this.mFinishing) {
            applyFinishTranslation(f, ring);
            return;
        }
        if (f != 1.0f || z) {
            float f2 = ring.mStartingRotation;
            if (f < 0.5f) {
                interpolation = ring.mStartingStartTrim;
                interpolation2 = (MATERIAL_INTERPOLATOR.getInterpolation(f / 0.5f) * 0.79f) + MIN_PROGRESS_ARC + interpolation;
            } else {
                float f3 = ring.mStartingStartTrim + 0.79f;
                interpolation = f3 - (((1.0f - MATERIAL_INTERPOLATOR.getInterpolation((f - 0.5f) / 0.5f)) * 0.79f) + MIN_PROGRESS_ARC);
                interpolation2 = f3;
            }
            float f4 = (RING_ROTATION * f) + f2;
            float f5 = (f + this.mRotationCount) * GROUP_FULL_ROTATION;
            ring.mStartTrim = interpolation;
            ring.mEndTrim = interpolation2;
            ring.mRotation = f4;
            setRotation(f5);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(this.mRotation, bounds.exactCenterX(), bounds.exactCenterY());
        Ring ring = this.mRing;
        RectF rectF = ring.mTempBounds;
        float f = ring.mRingCenterRadius;
        float fMin = (ring.mStrokeWidth / 2.0f) + f;
        if (f <= 0.0f) {
            fMin = (Math.min(bounds.width(), bounds.height()) / 2.0f) - Math.max((ring.mArrowWidth * ring.mArrowScale) / 2.0f, ring.mStrokeWidth / 2.0f);
        }
        rectF.set(bounds.centerX() - fMin, bounds.centerY() - fMin, bounds.centerX() + fMin, bounds.centerY() + fMin);
        float f2 = ring.mStartTrim;
        float f3 = ring.mRotation;
        float f4 = (f2 + f3) * 360.0f;
        float f5 = ((ring.mEndTrim + f3) * 360.0f) - f4;
        ring.mPaint.setColor(ring.mCurrentColor);
        ring.mPaint.setAlpha(ring.mAlpha);
        float f6 = ring.mStrokeWidth / 2.0f;
        rectF.inset(f6, f6);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, ring.mCirclePaint);
        float f7 = -f6;
        rectF.inset(f7, f7);
        canvas.drawArc(rectF, f4, f5, false, ring.mPaint);
        if (ring.mShowArrow) {
            Path path = ring.mArrow;
            if (path == null) {
                Path path2 = new Path();
                ring.mArrow = path2;
                path2.setFillType(Path.FillType.EVEN_ODD);
            } else {
                path.reset();
            }
            float fMin2 = Math.min(rectF.width(), rectF.height()) / 2.0f;
            float f8 = (ring.mArrowWidth * ring.mArrowScale) / 2.0f;
            ring.mArrow.moveTo(0.0f, 0.0f);
            ring.mArrow.lineTo(ring.mArrowWidth * ring.mArrowScale, 0.0f);
            Path path3 = ring.mArrow;
            float f9 = ring.mArrowWidth;
            float f10 = ring.mArrowScale;
            path3.lineTo((f9 * f10) / 2.0f, ring.mArrowHeight * f10);
            ring.mArrow.offset((rectF.centerX() + fMin2) - f8, (ring.mStrokeWidth / 2.0f) + rectF.centerY());
            ring.mArrow.close();
            ring.mArrowPaint.setColor(ring.mCurrentColor);
            ring.mArrowPaint.setAlpha(ring.mAlpha);
            canvas.save();
            canvas.rotate(f4 + f5, rectF.centerX(), rectF.centerY());
            canvas.drawPath(ring.mArrow, ring.mArrowPaint);
            canvas.restore();
        }
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mRing.mAlpha;
    }

    public boolean getArrowEnabled() {
        return this.mRing.mShowArrow;
    }

    public float getArrowHeight() {
        return this.mRing.mArrowHeight;
    }

    public float getArrowScale() {
        return this.mRing.mArrowScale;
    }

    public float getArrowWidth() {
        return this.mRing.mArrowWidth;
    }

    public int getBackgroundColor() {
        return this.mRing.mCirclePaint.getColor();
    }

    public float getCenterRadius() {
        return this.mRing.mRingCenterRadius;
    }

    @NonNull
    public int[] getColorSchemeColors() {
        return this.mRing.mColors;
    }

    public float getEndTrim() {
        return this.mRing.mEndTrim;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public float getProgressRotation() {
        return this.mRing.mRotation;
    }

    public float getStartTrim() {
        return this.mRing.mStartTrim;
    }

    @NonNull
    public Paint.Cap getStrokeCap() {
        return this.mRing.mPaint.getStrokeCap();
    }

    public float getStrokeWidth() {
        return this.mRing.mStrokeWidth;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mAnimator.isRunning();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mRing.mAlpha = i;
        invalidateSelf();
    }

    public void setArrowDimensions(float f, float f2) {
        Ring ring = this.mRing;
        ring.mArrowWidth = (int) f;
        ring.mArrowHeight = (int) f2;
        invalidateSelf();
    }

    public void setArrowEnabled(boolean z) {
        this.mRing.setShowArrow(z);
        invalidateSelf();
    }

    public void setArrowScale(float f) {
        Ring ring = this.mRing;
        if (f != ring.mArrowScale) {
            ring.mArrowScale = f;
        }
        invalidateSelf();
    }

    public void setBackgroundColor(int i) {
        this.mRing.mCirclePaint.setColor(i);
        invalidateSelf();
    }

    public void setCenterRadius(float f) {
        this.mRing.mRingCenterRadius = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mRing.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setColorSchemeColors(@NonNull int... iArr) {
        Ring ring = this.mRing;
        ring.mColors = iArr;
        ring.setColorIndex(0);
        this.mRing.setColorIndex(0);
        invalidateSelf();
    }

    public void setProgressRotation(float f) {
        this.mRing.mRotation = f;
        invalidateSelf();
    }

    public void setStartEndTrim(float f, float f2) {
        Ring ring = this.mRing;
        ring.mStartTrim = f;
        ring.mEndTrim = f2;
        invalidateSelf();
    }

    public void setStrokeCap(@NonNull Paint.Cap cap) {
        this.mRing.mPaint.setStrokeCap(cap);
        invalidateSelf();
    }

    public void setStrokeWidth(float f) {
        Ring ring = this.mRing;
        ring.mStrokeWidth = f;
        ring.mPaint.setStrokeWidth(f);
        invalidateSelf();
    }

    public void setStyle(int i) {
        if (i == 0) {
            setSizeParameters(CENTER_RADIUS_LARGE, STROKE_WIDTH_LARGE, 12.0f, 6.0f);
        } else {
            setSizeParameters(CENTER_RADIUS, STROKE_WIDTH, 10.0f, 5.0f);
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.mAnimator.cancel();
        Ring ring = this.mRing;
        float f = ring.mStartTrim;
        ring.mStartingStartTrim = f;
        float f2 = ring.mEndTrim;
        ring.mStartingEndTrim = f2;
        ring.mStartingRotation = ring.mRotation;
        if (f2 != f) {
            this.mFinishing = true;
            this.mAnimator.setDuration(666L);
            this.mAnimator.start();
            return;
        }
        ring.setColorIndex(0);
        Ring ring2 = this.mRing;
        ring2.mStartingStartTrim = 0.0f;
        ring2.mStartingEndTrim = 0.0f;
        ring2.mStartingRotation = 0.0f;
        ring2.mStartTrim = 0.0f;
        ring2.mEndTrim = 0.0f;
        ring2.mRotation = 0.0f;
        this.mAnimator.setDuration(1332L);
        this.mAnimator.start();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mAnimator.cancel();
        setRotation(0.0f);
        this.mRing.setShowArrow(false);
        this.mRing.setColorIndex(0);
        Ring ring = this.mRing;
        ring.mStartingStartTrim = 0.0f;
        ring.mStartingEndTrim = 0.0f;
        ring.mStartingRotation = 0.0f;
        ring.mStartTrim = 0.0f;
        ring.mEndTrim = 0.0f;
        ring.mRotation = 0.0f;
        invalidateSelf();
    }

    public final void updateRingColor(float f, Ring ring) {
        if (f <= 0.75f) {
            ring.mCurrentColor = ring.mColors[ring.mColorIndex];
            return;
        }
        int[] iArr = ring.mColors;
        int i = ring.mColorIndex;
        ring.mCurrentColor = evaluateColorChange((f - 0.75f) / 0.25f, iArr[i], iArr[(i + 1) % iArr.length]);
    }
}
