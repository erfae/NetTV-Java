package androidx.leanback.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.ColorInt;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class PagingIndicator extends View {
    private static final TimeInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final Property<Dot, Float> DOT_ALPHA = new Property<Dot, Float>() { // from class: androidx.leanback.widget.PagingIndicator.1
        @Override // android.util.Property
        public Float get(Dot dot) {
            return Float.valueOf(dot.getAlpha());
        }

        @Override // android.util.Property
        public void set(Dot dot, Float f) {
            dot.setAlpha(f.floatValue());
        }
    };
    private static final Property<Dot, Float> DOT_DIAMETER = new Property<Dot, Float>() { // from class: androidx.leanback.widget.PagingIndicator.2
        @Override // android.util.Property
        public Float get(Dot dot) {
            return Float.valueOf(dot.getDiameter());
        }

        @Override // android.util.Property
        public void set(Dot dot, Float f) {
            dot.setDiameter(f.floatValue());
        }
    };
    private static final Property<Dot, Float> DOT_TRANSLATION_X = new Property<Dot, Float>() { // from class: androidx.leanback.widget.PagingIndicator.3
        @Override // android.util.Property
        public Float get(Dot dot) {
            return Float.valueOf(dot.getTranslationX());
        }

        @Override // android.util.Property
        public void set(Dot dot, Float f) {
            dot.setTranslationX(f.floatValue());
        }
    };
    private static final long DURATION_ALPHA = 167;
    private static final long DURATION_DIAMETER = 417;
    private static final long DURATION_TRANSLATION_X = 417;
    private final AnimatorSet mAnimator;
    public Bitmap mArrow;
    public final int mArrowDiameter;
    private final int mArrowGap;
    public Paint mArrowPaint;
    public final int mArrowRadius;
    public final Rect mArrowRect;
    public final float mArrowToBgRatio;
    public final Paint mBgPaint;
    private int mCurrentPage;
    public int mDotCenterY;
    public final int mDotDiameter;

    @ColorInt
    public int mDotFgSelectColor;
    private final int mDotGap;
    public final int mDotRadius;
    private int[] mDotSelectedNextX;
    private int[] mDotSelectedPrevX;
    private int[] mDotSelectedX;
    private Dot[] mDots;
    public final Paint mFgPaint;
    private final AnimatorSet mHideAnimator;
    public boolean mIsLtr;
    private int mPageCount;
    private int mPreviousPage;
    private final int mShadowRadius;
    private final AnimatorSet mShowAnimator;

    public class Dot {
        public float mAlpha;
        public float mArrowImageRadius;
        public float mCenterX;
        public float mDiameter;
        public float mDirection = 1.0f;

        @ColorInt
        public int mFgColor;
        public float mLayoutDirection;
        public float mRadius;
        public float mTranslationX;

        public Dot() {
            this.mLayoutDirection = PagingIndicator.this.mIsLtr ? 1.0f : -1.0f;
        }

        public void adjustAlpha() {
            this.mFgColor = Color.argb(Math.round(this.mAlpha * 255.0f), Color.red(PagingIndicator.this.mDotFgSelectColor), Color.green(PagingIndicator.this.mDotFgSelectColor), Color.blue(PagingIndicator.this.mDotFgSelectColor));
        }

        public final void deselect() {
            this.mTranslationX = 0.0f;
            this.mCenterX = 0.0f;
            PagingIndicator pagingIndicator = PagingIndicator.this;
            this.mDiameter = pagingIndicator.mDotDiameter;
            float f = pagingIndicator.mDotRadius;
            this.mRadius = f;
            this.mArrowImageRadius = f * pagingIndicator.mArrowToBgRatio;
            this.mAlpha = 0.0f;
            adjustAlpha();
        }

        public float getAlpha() {
            return this.mAlpha;
        }

        public float getDiameter() {
            return this.mDiameter;
        }

        public float getTranslationX() {
            return this.mTranslationX;
        }

        public void setAlpha(float f) {
            this.mAlpha = f;
            adjustAlpha();
            PagingIndicator.this.invalidate();
        }

        public void setDiameter(float f) {
            this.mDiameter = f;
            float f2 = f / 2.0f;
            this.mRadius = f2;
            PagingIndicator pagingIndicator = PagingIndicator.this;
            this.mArrowImageRadius = f2 * pagingIndicator.mArrowToBgRatio;
            pagingIndicator.invalidate();
        }

        public void setTranslationX(float f) {
            this.mTranslationX = f * this.mDirection * this.mLayoutDirection;
            PagingIndicator.this.invalidate();
        }
    }

    public PagingIndicator(Context context) {
        this(context, null, 0);
    }

    private void adjustDotPosition() {
        int i;
        int i2 = 0;
        while (true) {
            i = this.mCurrentPage;
            float f = -1.0f;
            if (i2 >= i) {
                break;
            }
            this.mDots[i2].deselect();
            Dot[] dotArr = this.mDots;
            Dot dot = dotArr[i2];
            if (i2 != this.mPreviousPage) {
                f = 1.0f;
            }
            dot.mDirection = f;
            dotArr[i2].mCenterX = this.mDotSelectedPrevX[i2];
            i2++;
        }
        Dot dot2 = this.mDots[i];
        dot2.mTranslationX = 0.0f;
        dot2.mCenterX = 0.0f;
        PagingIndicator pagingIndicator = PagingIndicator.this;
        dot2.mDiameter = pagingIndicator.mArrowDiameter;
        float f2 = pagingIndicator.mArrowRadius;
        dot2.mRadius = f2;
        dot2.mArrowImageRadius = f2 * pagingIndicator.mArrowToBgRatio;
        dot2.mAlpha = 1.0f;
        dot2.adjustAlpha();
        Dot[] dotArr2 = this.mDots;
        int i3 = this.mCurrentPage;
        dotArr2[i3].mDirection = this.mPreviousPage >= i3 ? 1.0f : -1.0f;
        dotArr2[i3].mCenterX = this.mDotSelectedX[i3];
        while (true) {
            i3++;
            if (i3 >= this.mPageCount) {
                return;
            }
            this.mDots[i3].deselect();
            Dot[] dotArr3 = this.mDots;
            dotArr3[i3].mDirection = 1.0f;
            dotArr3[i3].mCenterX = this.mDotSelectedNextX[i3];
        }
    }

    private void calculateDotPositions() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int width = getWidth() - getPaddingRight();
        int requiredWidth = getRequiredWidth();
        int i = (paddingLeft + width) / 2;
        int i2 = this.mPageCount;
        int[] iArr = new int[i2];
        this.mDotSelectedX = iArr;
        int[] iArr2 = new int[i2];
        this.mDotSelectedPrevX = iArr2;
        int[] iArr3 = new int[i2];
        this.mDotSelectedNextX = iArr3;
        int i3 = 1;
        if (this.mIsLtr) {
            int i4 = i - (requiredWidth / 2);
            int i5 = this.mDotRadius;
            int i6 = this.mDotGap;
            int i7 = this.mArrowGap;
            iArr[0] = ((i4 + i5) - i6) + i7;
            iArr2[0] = i4 + i5;
            iArr3[0] = (i7 * 2) + ((i4 + i5) - (i6 * 2));
            while (i3 < this.mPageCount) {
                int[] iArr4 = this.mDotSelectedX;
                int[] iArr5 = this.mDotSelectedPrevX;
                int i8 = i3 - 1;
                int i9 = iArr5[i8];
                int i10 = this.mArrowGap;
                iArr4[i3] = i9 + i10;
                iArr5[i3] = iArr5[i8] + this.mDotGap;
                this.mDotSelectedNextX[i3] = iArr4[i8] + i10;
                i3++;
            }
        } else {
            int i11 = (requiredWidth / 2) + i;
            int i12 = this.mDotRadius;
            int i13 = this.mDotGap;
            int i14 = this.mArrowGap;
            iArr[0] = ((i11 - i12) + i13) - i14;
            iArr2[0] = i11 - i12;
            iArr3[0] = ((i13 * 2) + (i11 - i12)) - (i14 * 2);
            while (i3 < this.mPageCount) {
                int[] iArr6 = this.mDotSelectedX;
                int[] iArr7 = this.mDotSelectedPrevX;
                int i15 = i3 - 1;
                int i16 = iArr7[i15];
                int i17 = this.mArrowGap;
                iArr6[i3] = i16 - i17;
                iArr7[i3] = iArr7[i15] - this.mDotGap;
                this.mDotSelectedNextX[i3] = iArr6[i15] - i17;
                i3++;
            }
        }
        this.mDotCenterY = paddingTop + this.mArrowRadius;
        adjustDotPosition();
    }

    private Animator createDotAlphaAnimator(float f, float f2) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat((Object) null, DOT_ALPHA, f, f2);
        objectAnimatorOfFloat.setDuration(DURATION_ALPHA);
        objectAnimatorOfFloat.setInterpolator(DECELERATE_INTERPOLATOR);
        return objectAnimatorOfFloat;
    }

    private Animator createDotDiameterAnimator(float f, float f2) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat((Object) null, DOT_DIAMETER, f, f2);
        objectAnimatorOfFloat.setDuration(417L);
        objectAnimatorOfFloat.setInterpolator(DECELERATE_INTERPOLATOR);
        return objectAnimatorOfFloat;
    }

    private Animator createDotTranslationXAnimator() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat((Object) null, DOT_TRANSLATION_X, (-this.mArrowGap) + this.mDotGap, 0.0f);
        objectAnimatorOfFloat.setDuration(417L);
        objectAnimatorOfFloat.setInterpolator(DECELERATE_INTERPOLATOR);
        return objectAnimatorOfFloat;
    }

    private int getColorFromTypedArray(TypedArray typedArray, int i, int i2) {
        return typedArray.getColor(i, getResources().getColor(i2));
    }

    private int getDesiredHeight() {
        return getPaddingBottom() + getPaddingTop() + this.mArrowDiameter + this.mShadowRadius;
    }

    private int getDesiredWidth() {
        return getPaddingRight() + getPaddingLeft() + getRequiredWidth();
    }

    private int getDimensionFromTypedArray(TypedArray typedArray, int i, int i2) {
        return typedArray.getDimensionPixelOffset(i, getResources().getDimensionPixelOffset(i2));
    }

    private int getRequiredWidth() {
        return ((this.mPageCount - 3) * this.mDotGap) + (this.mArrowGap * 2) + (this.mDotRadius * 2);
    }

    private Bitmap loadArrow() {
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.lb_ic_nav_arrow);
        if (this.mIsLtr) {
            return bitmapDecodeResource;
        }
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(bitmapDecodeResource, 0, 0, bitmapDecodeResource.getWidth(), bitmapDecodeResource.getHeight(), matrix, false);
    }

    private void setSelectedPage(int i) {
        if (i == this.mCurrentPage) {
            return;
        }
        this.mCurrentPage = i;
        adjustDotPosition();
    }

    @VisibleForTesting
    public int[] getDotSelectedLeftX() {
        return this.mDotSelectedPrevX;
    }

    @VisibleForTesting
    public int[] getDotSelectedRightX() {
        return this.mDotSelectedNextX;
    }

    @VisibleForTesting
    public int[] getDotSelectedX() {
        return this.mDotSelectedX;
    }

    @VisibleForTesting
    public int getPageCount() {
        return this.mPageCount;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        for (int i = 0; i < this.mPageCount; i++) {
            Dot dot = this.mDots[i];
            float f = dot.mCenterX + dot.mTranslationX;
            PagingIndicator pagingIndicator = PagingIndicator.this;
            canvas.drawCircle(f, pagingIndicator.mDotCenterY, dot.mRadius, pagingIndicator.mBgPaint);
            if (dot.mAlpha > 0.0f) {
                PagingIndicator.this.mFgPaint.setColor(dot.mFgColor);
                PagingIndicator pagingIndicator2 = PagingIndicator.this;
                canvas.drawCircle(f, pagingIndicator2.mDotCenterY, dot.mRadius, pagingIndicator2.mFgPaint);
                PagingIndicator pagingIndicator3 = PagingIndicator.this;
                Bitmap bitmap = pagingIndicator3.mArrow;
                Rect rect = pagingIndicator3.mArrowRect;
                float f2 = dot.mArrowImageRadius;
                float f3 = PagingIndicator.this.mDotCenterY;
                canvas.drawBitmap(bitmap, rect, new Rect((int) (f - f2), (int) (f3 - f2), (int) (f + f2), (int) (f3 + f2)), PagingIndicator.this.mArrowPaint);
            }
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int desiredHeight = getDesiredHeight();
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            desiredHeight = Math.min(desiredHeight, View.MeasureSpec.getSize(i2));
        } else if (mode == 1073741824) {
            desiredHeight = View.MeasureSpec.getSize(i2);
        }
        int desiredWidth = getDesiredWidth();
        int mode2 = View.MeasureSpec.getMode(i);
        if (mode2 == Integer.MIN_VALUE) {
            desiredWidth = Math.min(desiredWidth, View.MeasureSpec.getSize(i));
        } else if (mode2 == 1073741824) {
            desiredWidth = View.MeasureSpec.getSize(i);
        }
        setMeasuredDimension(desiredWidth, desiredHeight);
    }

    public void onPageSelected(int i, boolean z) {
        if (this.mCurrentPage == i) {
            return;
        }
        if (this.mAnimator.isStarted()) {
            this.mAnimator.end();
        }
        int i2 = this.mCurrentPage;
        this.mPreviousPage = i2;
        if (z) {
            this.mHideAnimator.setTarget(this.mDots[i2]);
            this.mShowAnimator.setTarget(this.mDots[i]);
            this.mAnimator.start();
        }
        setSelectedPage(i);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        boolean z = i == 0;
        if (this.mIsLtr != z) {
            this.mIsLtr = z;
            this.mArrow = loadArrow();
            Dot[] dotArr = this.mDots;
            if (dotArr != null) {
                for (Dot dot : dotArr) {
                    dot.mLayoutDirection = PagingIndicator.this.mIsLtr ? 1.0f : -1.0f;
                }
            }
            calculateDotPositions();
            invalidate();
        }
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        setMeasuredDimension(i, i2);
        calculateDotPositions();
    }

    public void setArrowBackgroundColor(@ColorInt int i) {
        this.mDotFgSelectColor = i;
    }

    public void setArrowColor(@ColorInt int i) {
        if (this.mArrowPaint == null) {
            this.mArrowPaint = new Paint();
        }
        this.mArrowPaint.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN));
    }

    public void setDotBackgroundColor(@ColorInt int i) {
        this.mBgPaint.setColor(i);
    }

    public void setPageCount(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("The page count should be a positive integer");
        }
        this.mPageCount = i;
        this.mDots = new Dot[i];
        for (int i2 = 0; i2 < this.mPageCount; i2++) {
            this.mDots[i2] = new Dot();
        }
        calculateDotPositions();
        setSelectedPage(0);
    }

    public PagingIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PagingIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        AnimatorSet animatorSet = new AnimatorSet();
        this.mAnimator = animatorSet;
        Resources resources = getResources();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PagingIndicator, i, 0);
        int dimensionFromTypedArray = getDimensionFromTypedArray(typedArrayObtainStyledAttributes, R.styleable.PagingIndicator_lbDotRadius, R.dimen.lb_page_indicator_dot_radius);
        this.mDotRadius = dimensionFromTypedArray;
        this.mDotDiameter = dimensionFromTypedArray * 2;
        int dimensionFromTypedArray2 = getDimensionFromTypedArray(typedArrayObtainStyledAttributes, R.styleable.PagingIndicator_arrowRadius, R.dimen.lb_page_indicator_arrow_radius);
        this.mArrowRadius = dimensionFromTypedArray2;
        int i2 = dimensionFromTypedArray2 * 2;
        this.mArrowDiameter = i2;
        this.mDotGap = getDimensionFromTypedArray(typedArrayObtainStyledAttributes, R.styleable.PagingIndicator_dotToDotGap, R.dimen.lb_page_indicator_dot_gap);
        this.mArrowGap = getDimensionFromTypedArray(typedArrayObtainStyledAttributes, R.styleable.PagingIndicator_dotToArrowGap, R.dimen.lb_page_indicator_arrow_gap);
        int colorFromTypedArray = getColorFromTypedArray(typedArrayObtainStyledAttributes, R.styleable.PagingIndicator_dotBgColor, R.color.lb_page_indicator_dot);
        Paint paint = new Paint(1);
        this.mBgPaint = paint;
        paint.setColor(colorFromTypedArray);
        this.mDotFgSelectColor = getColorFromTypedArray(typedArrayObtainStyledAttributes, R.styleable.PagingIndicator_arrowBgColor, R.color.lb_page_indicator_arrow_background);
        if (this.mArrowPaint == null) {
            int i3 = R.styleable.PagingIndicator_arrowColor;
            if (typedArrayObtainStyledAttributes.hasValue(i3)) {
                setArrowColor(typedArrayObtainStyledAttributes.getColor(i3, 0));
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mIsLtr = resources.getConfiguration().getLayoutDirection() == 0;
        int color = resources.getColor(R.color.lb_page_indicator_arrow_shadow);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.lb_page_indicator_arrow_shadow_radius);
        this.mShadowRadius = dimensionPixelSize;
        Paint paint2 = new Paint(1);
        this.mFgPaint = paint2;
        float dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.lb_page_indicator_arrow_shadow_offset);
        paint2.setShadowLayer(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize2, color);
        this.mArrow = loadArrow();
        this.mArrowRect = new Rect(0, 0, this.mArrow.getWidth(), this.mArrow.getHeight());
        this.mArrowToBgRatio = this.mArrow.getWidth() / i2;
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mShowAnimator = animatorSet2;
        animatorSet2.playTogether(createDotAlphaAnimator(0.0f, 1.0f), createDotDiameterAnimator(dimensionFromTypedArray * 2, dimensionFromTypedArray2 * 2), createDotTranslationXAnimator());
        AnimatorSet animatorSet3 = new AnimatorSet();
        this.mHideAnimator = animatorSet3;
        animatorSet3.playTogether(createDotAlphaAnimator(1.0f, 0.0f), createDotDiameterAnimator(dimensionFromTypedArray2 * 2, dimensionFromTypedArray * 2), createDotTranslationXAnimator());
        animatorSet.playTogether(animatorSet2, animatorSet3);
        setLayerType(1, null);
    }
}
