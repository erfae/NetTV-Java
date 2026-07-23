package com.iarcuschin.simpleratingbar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import com.google.android.exoplayer2.ExoPlayer;

/* JADX INFO: loaded from: classes2.dex */
public class SimpleRatingBar extends View {

    @ColorInt
    private int backgroundColor;

    @ColorInt
    private int borderColor;
    private View.OnClickListener clickListener;
    private CornerPathEffect cornerPathEffect;
    private float currentStarSize;
    private float defaultStarSize;
    private float desiredStarSize;
    private boolean drawBorderEnabled;

    @ColorInt
    private int fillColor;
    private Gravity gravity;
    private Bitmap internalBitmap;
    private Canvas internalCanvas;
    private boolean isIndicator;
    private float maxStarSize;
    private int numberOfStars;
    private Paint paintStarBackground;
    private Paint paintStarBorder;
    private Paint paintStarFill;
    private Paint paintStarOutline;

    @ColorInt
    private int pressedBackgroundColor;

    @ColorInt
    private int pressedBorderColor;

    @ColorInt
    private int pressedFillColor;

    @ColorInt
    private int pressedStarBackgroundColor;
    private float rating;
    private ValueAnimator ratingAnimator;
    private OnRatingBarChangeListener ratingListener;

    @ColorInt
    private int starBackgroundColor;
    private float starBorderWidth;
    private float starCornerRadius;
    private Path starPath;
    private float[] starVertex;
    private RectF starsDrawingSpace;
    private float starsSeparation;
    private RectF starsTouchSpace;
    private float stepSize;
    private boolean touchInProgress;

    public class AnimationBuilder {
        private Animator.AnimatorListener animatorListener;
        private long duration;
        private Interpolator interpolator;
        private SimpleRatingBar ratingBar;
        private float ratingTarget;
        private int repeatCount;
        private int repeatMode;

        public AnimationBuilder setAnimatorListener(Animator.AnimatorListener animatorListener) {
            this.animatorListener = animatorListener;
            return this;
        }

        public AnimationBuilder setDuration(long j) {
            this.duration = j;
            return this;
        }

        public AnimationBuilder setInterpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public AnimationBuilder setRatingTarget(float f) {
            this.ratingTarget = f;
            return this;
        }

        public AnimationBuilder setRepeatCount(int i) {
            this.repeatCount = i;
            return this;
        }

        public AnimationBuilder setRepeatMode(int i) {
            this.repeatMode = i;
            return this;
        }

        public void start() {
            this.ratingBar.animateRating(this);
        }

        private AnimationBuilder(SimpleRatingBar simpleRatingBar, SimpleRatingBar simpleRatingBar2) {
            this.ratingBar = simpleRatingBar2;
            this.duration = ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS;
            this.interpolator = new BounceInterpolator();
            this.ratingTarget = simpleRatingBar2.getNumberOfStars();
            this.repeatCount = 1;
            this.repeatMode = 2;
        }
    }

    public enum Gravity {
        Left(0),
        Right(1);

        public int id;

        Gravity(int i) {
            this.id = i;
        }
    }

    public interface OnRatingBarChangeListener {
        void onRatingChanged(SimpleRatingBar simpleRatingBar, float f, boolean z);
    }

    public SimpleRatingBar(Context context) {
        super(context);
        initView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateRating(AnimationBuilder animationBuilder) {
        animationBuilder.ratingTarget = normalizeRating(animationBuilder.ratingTarget);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, animationBuilder.ratingTarget);
        this.ratingAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(animationBuilder.duration);
        this.ratingAnimator.setRepeatCount(animationBuilder.repeatCount);
        this.ratingAnimator.setRepeatMode(animationBuilder.repeatMode);
        this.ratingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.iarcuschin.simpleratingbar.SimpleRatingBar.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                SimpleRatingBar.this.setRating(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        if (animationBuilder.interpolator != null) {
            this.ratingAnimator.setInterpolator(animationBuilder.interpolator);
        }
        if (animationBuilder.animatorListener != null) {
            this.ratingAnimator.addListener(animationBuilder.animatorListener);
        }
        this.ratingAnimator.addListener(new Animator.AnimatorListener() { // from class: com.iarcuschin.simpleratingbar.SimpleRatingBar.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                if (SimpleRatingBar.this.ratingListener != null) {
                    OnRatingBarChangeListener onRatingBarChangeListener = SimpleRatingBar.this.ratingListener;
                    SimpleRatingBar simpleRatingBar = SimpleRatingBar.this;
                    onRatingBarChangeListener.onRatingChanged(simpleRatingBar, simpleRatingBar.rating, false);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (SimpleRatingBar.this.ratingListener != null) {
                    OnRatingBarChangeListener onRatingBarChangeListener = SimpleRatingBar.this.ratingListener;
                    SimpleRatingBar simpleRatingBar = SimpleRatingBar.this;
                    onRatingBarChangeListener.onRatingChanged(simpleRatingBar, simpleRatingBar.rating, false);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
                if (SimpleRatingBar.this.ratingListener != null) {
                    OnRatingBarChangeListener onRatingBarChangeListener = SimpleRatingBar.this.ratingListener;
                    SimpleRatingBar simpleRatingBar = SimpleRatingBar.this;
                    onRatingBarChangeListener.onRatingChanged(simpleRatingBar, simpleRatingBar.rating, false);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        });
        this.ratingAnimator.start();
    }

    private float calculateBestStarSize(int i, int i2) {
        float f = this.maxStarSize;
        if (f == 2.1474836E9f) {
            float paddingLeft = (i - getPaddingLeft()) - getPaddingRight();
            float f2 = this.starsSeparation;
            int i3 = this.numberOfStars;
            return Math.min((paddingLeft - (f2 * (i3 - 1))) / i3, (i2 - getPaddingTop()) - getPaddingBottom());
        }
        float fCalculateTotalWidth = calculateTotalWidth(f, this.numberOfStars, this.starsSeparation, true);
        float fCalculateTotalHeight = calculateTotalHeight(this.maxStarSize, this.numberOfStars, this.starsSeparation, true);
        if (fCalculateTotalWidth < i && fCalculateTotalHeight < i2) {
            return this.maxStarSize;
        }
        float paddingLeft2 = (i - getPaddingLeft()) - getPaddingRight();
        float f3 = this.starsSeparation;
        int i4 = this.numberOfStars;
        return Math.min((paddingLeft2 - (f3 * (i4 - 1))) / i4, (i2 - getPaddingTop()) - getPaddingBottom());
    }

    private int calculateTotalHeight(float f, int i, float f2, boolean z) {
        int paddingBottom;
        int iRound = Math.round(f);
        if (z) {
            paddingBottom = getPaddingBottom() + getPaddingTop();
        } else {
            paddingBottom = 0;
        }
        return iRound + paddingBottom;
    }

    private int calculateTotalWidth(float f, int i, float f2, boolean z) {
        int paddingRight;
        int iRound = Math.round((f2 * (i - 1)) + (f * i));
        if (z) {
            paddingRight = getPaddingRight() + getPaddingLeft();
        } else {
            paddingRight = 0;
        }
        return iRound + paddingRight;
    }

    private void drawFromLeftToRight(Canvas canvas) {
        float f = this.rating;
        RectF rectF = this.starsDrawingSpace;
        float f2 = rectF.left;
        float f3 = rectF.top;
        float f4 = f;
        for (int i = 0; i < this.numberOfStars; i++) {
            if (f4 >= 1.0f) {
                drawStar(canvas, f2, f3, 1.0f, Gravity.Left);
                f4 -= 1.0f;
            } else {
                drawStar(canvas, f2, f3, f4, Gravity.Left);
                f4 = 0.0f;
            }
            f2 += this.starsSeparation + this.currentStarSize;
        }
    }

    private void drawFromRightToLeft(Canvas canvas) {
        float f = this.rating;
        RectF rectF = this.starsDrawingSpace;
        float f2 = rectF.right - this.currentStarSize;
        float f3 = rectF.top;
        float f4 = f;
        for (int i = 0; i < this.numberOfStars; i++) {
            if (f4 >= 1.0f) {
                drawStar(canvas, f2, f3, 1.0f, Gravity.Right);
                f4 -= 1.0f;
            } else {
                drawStar(canvas, f2, f3, f4, Gravity.Right);
                f4 = 0.0f;
            }
            f2 -= this.starsSeparation + this.currentStarSize;
        }
    }

    private void drawStar(Canvas canvas, float f, float f2, float f3, Gravity gravity) {
        float f4 = this.currentStarSize * f3;
        this.starPath.reset();
        Path path = this.starPath;
        float[] fArr = this.starVertex;
        path.moveTo(fArr[0] + f, fArr[1] + f2);
        int i = 2;
        while (true) {
            float[] fArr2 = this.starVertex;
            if (i >= fArr2.length) {
                break;
            }
            this.starPath.lineTo(fArr2[i] + f, fArr2[i + 1] + f2);
            i += 2;
        }
        this.starPath.close();
        canvas.drawPath(this.starPath, this.paintStarOutline);
        if (gravity == Gravity.Left) {
            float f5 = f + f4;
            float f6 = this.currentStarSize;
            canvas.drawRect(f, f2, (0.02f * f6) + f5, f2 + f6, this.paintStarFill);
            float f7 = this.currentStarSize;
            canvas.drawRect(f5, f2, f + f7, f2 + f7, this.paintStarBackground);
        } else {
            float f8 = this.currentStarSize;
            canvas.drawRect((f + f8) - ((0.02f * f8) + f4), f2, f + f8, f2 + f8, this.paintStarFill);
            float f9 = this.currentStarSize;
            canvas.drawRect(f, f2, (f + f9) - f4, f2 + f9, this.paintStarBackground);
        }
        if (this.drawBorderEnabled) {
            canvas.drawPath(this.starPath, this.paintStarBorder);
        }
    }

    private void generateInternalCanvas(int i, int i2) {
        Bitmap bitmap = this.internalBitmap;
        if (bitmap != null) {
            bitmap.recycle();
        }
        if (i <= 0 || i2 <= 0) {
            return;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        this.internalBitmap = bitmapCreateBitmap;
        bitmapCreateBitmap.eraseColor(0);
        this.internalCanvas = new Canvas(this.internalBitmap);
    }

    private void initView() {
        this.starPath = new Path();
        this.cornerPathEffect = new CornerPathEffect(this.starCornerRadius);
        Paint paint = new Paint(5);
        this.paintStarOutline = paint;
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paintStarOutline.setAntiAlias(true);
        this.paintStarOutline.setDither(true);
        this.paintStarOutline.setStrokeJoin(Paint.Join.ROUND);
        this.paintStarOutline.setStrokeCap(Paint.Cap.ROUND);
        this.paintStarOutline.setColor(-16777216);
        this.paintStarOutline.setPathEffect(this.cornerPathEffect);
        Paint paint2 = new Paint(5);
        this.paintStarBorder = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.paintStarBorder.setStrokeJoin(Paint.Join.ROUND);
        this.paintStarBorder.setStrokeCap(Paint.Cap.ROUND);
        this.paintStarBorder.setStrokeWidth(this.starBorderWidth);
        this.paintStarBorder.setPathEffect(this.cornerPathEffect);
        Paint paint3 = new Paint(5);
        this.paintStarBackground = paint3;
        paint3.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paintStarBackground.setAntiAlias(true);
        this.paintStarBackground.setDither(true);
        this.paintStarBackground.setStrokeJoin(Paint.Join.ROUND);
        this.paintStarBackground.setStrokeCap(Paint.Cap.ROUND);
        Paint paint4 = new Paint(5);
        this.paintStarFill = paint4;
        paint4.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paintStarFill.setAntiAlias(true);
        this.paintStarFill.setDither(true);
        this.paintStarFill.setStrokeJoin(Paint.Join.ROUND);
        this.paintStarFill.setStrokeCap(Paint.Cap.ROUND);
        this.defaultStarSize = TypedValue.applyDimension(1, 30.0f, getResources().getDisplayMetrics());
    }

    private float normalizeRating(float f) {
        if (f < 0.0f) {
            Log.w("SimpleRatingBar", String.format("Assigned rating is less than 0 (%f < 0), I will set it to exactly 0", Float.valueOf(f)));
            return 0.0f;
        }
        if (f <= this.numberOfStars) {
            return f;
        }
        Log.w("SimpleRatingBar", String.format("Assigned rating is greater than numberOfStars (%f > %d), I will set it to exactly numberOfStars", Float.valueOf(f), Integer.valueOf(this.numberOfStars)));
        return this.numberOfStars;
    }

    private void parseAttrs(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.SimpleRatingBar);
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.SimpleRatingBar_srb_borderColor, getResources().getColor(R.color.golden_stars));
        this.borderColor = color;
        this.fillColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SimpleRatingBar_srb_fillColor, color);
        this.starBackgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SimpleRatingBar_srb_starBackgroundColor, 0);
        this.backgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SimpleRatingBar_srb_backgroundColor, 0);
        this.pressedBorderColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SimpleRatingBar_srb_pressedBorderColor, this.borderColor);
        this.pressedFillColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SimpleRatingBar_srb_pressedFillColor, this.fillColor);
        this.pressedStarBackgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SimpleRatingBar_srb_pressedStarBackgroundColor, this.starBackgroundColor);
        this.pressedBackgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SimpleRatingBar_srb_pressedBackgroundColor, this.backgroundColor);
        this.numberOfStars = typedArrayObtainStyledAttributes.getInteger(R.styleable.SimpleRatingBar_srb_numberOfStars, 5);
        this.starsSeparation = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.SimpleRatingBar_srb_starsSeparation, (int) valueToPixels(4.0f, 0));
        this.maxStarSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.SimpleRatingBar_srb_maxStarSize, Integer.MAX_VALUE);
        this.desiredStarSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.SimpleRatingBar_srb_starSize, Integer.MAX_VALUE);
        this.stepSize = typedArrayObtainStyledAttributes.getFloat(R.styleable.SimpleRatingBar_srb_stepSize, 0.1f);
        this.starBorderWidth = typedArrayObtainStyledAttributes.getFloat(R.styleable.SimpleRatingBar_srb_starBorderWidth, 5.0f);
        this.starCornerRadius = typedArrayObtainStyledAttributes.getFloat(R.styleable.SimpleRatingBar_srb_starCornerRadius, 6.0f);
        this.rating = normalizeRating(typedArrayObtainStyledAttributes.getFloat(R.styleable.SimpleRatingBar_srb_rating, 0.0f));
        this.isIndicator = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SimpleRatingBar_srb_isIndicator, false);
        this.drawBorderEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SimpleRatingBar_srb_drawBorderEnabled, true);
        int i = typedArrayObtainStyledAttributes.getInt(R.styleable.SimpleRatingBar_srb_gravity, Gravity.Left.id);
        for (Gravity gravity : Gravity.values()) {
            if (gravity.id == i) {
                this.gravity = gravity;
                typedArrayObtainStyledAttributes.recycle();
                validateAttrs();
            }
        }
        Log.w("SimpleRatingBar", String.format("Gravity chosen is neither 'left' nor 'right', I will set it to Left", new Object[0]));
        gravity = Gravity.Left;
        this.gravity = gravity;
        typedArrayObtainStyledAttributes.recycle();
        validateAttrs();
    }

    private void performStarSizeAssociatedCalculations(int i, int i2) {
        float fCalculateTotalWidth = calculateTotalWidth(this.currentStarSize, this.numberOfStars, this.starsSeparation, false);
        float fCalculateTotalHeight = calculateTotalHeight(this.currentStarSize, this.numberOfStars, this.starsSeparation, false);
        float paddingLeft = ((((i - getPaddingLeft()) - getPaddingRight()) / 2) - (fCalculateTotalWidth / 2.0f)) + getPaddingLeft();
        float paddingTop = ((((i2 - getPaddingTop()) - getPaddingBottom()) / 2) - (fCalculateTotalHeight / 2.0f)) + getPaddingTop();
        RectF rectF = new RectF(paddingLeft, paddingTop, fCalculateTotalWidth + paddingLeft, fCalculateTotalHeight + paddingTop);
        this.starsDrawingSpace = rectF;
        float fWidth = rectF.width() * 0.05f;
        RectF rectF2 = this.starsDrawingSpace;
        this.starsTouchSpace = new RectF(rectF2.left - fWidth, rectF2.top, rectF2.right + fWidth, rectF2.bottom);
        float f = this.currentStarSize;
        float f2 = 0.2f * f;
        float f3 = 0.35f * f;
        float f4 = 0.5f * f;
        float f5 = 0.05f * f;
        float f6 = 0.03f * f;
        float f7 = 0.38f * f;
        float f8 = 0.32f * f;
        float f9 = 0.6f * f;
        this.starVertex = new float[]{f6, f7, f6 + f3, f7, f4, f5, (f - f6) - f3, f7, f - f6, f7, f - f8, f9, f - f2, f - f5, f4, f - (0.27f * f), f2, f - f5, f8, f9};
    }

    private void setNewRatingFromTouch(float f, float f2) {
        if (this.gravity != Gravity.Left) {
            f = getWidth() - f;
        }
        RectF rectF = this.starsDrawingSpace;
        float f3 = rectF.left;
        if (f < f3) {
            this.rating = 0.0f;
            return;
        }
        if (f > rectF.right) {
            this.rating = this.numberOfStars;
            return;
        }
        float fWidth = (this.numberOfStars / rectF.width()) * (f - f3);
        this.rating = fWidth;
        float f4 = this.stepSize;
        float f5 = fWidth % f4;
        if (f5 < f4 / 4.0f) {
            float f6 = fWidth - f5;
            this.rating = f6;
            this.rating = Math.max(0.0f, f6);
        } else {
            float f7 = (fWidth - f5) + f4;
            this.rating = f7;
            this.rating = Math.min(this.numberOfStars, f7);
        }
    }

    private void setupColorsInPaint() {
        if (this.touchInProgress) {
            this.paintStarBorder.setColor(this.pressedBorderColor);
            this.paintStarFill.setColor(this.pressedFillColor);
            if (this.pressedFillColor != 0) {
                this.paintStarFill.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            } else {
                this.paintStarFill.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            }
            this.paintStarBackground.setColor(this.pressedStarBackgroundColor);
            if (this.pressedStarBackgroundColor != 0) {
                this.paintStarBackground.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
                return;
            } else {
                this.paintStarBackground.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                return;
            }
        }
        this.paintStarBorder.setColor(this.borderColor);
        this.paintStarFill.setColor(this.fillColor);
        if (this.fillColor != 0) {
            this.paintStarFill.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        } else {
            this.paintStarFill.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        this.paintStarBackground.setColor(this.starBackgroundColor);
        if (this.starBackgroundColor != 0) {
            this.paintStarBackground.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        } else {
            this.paintStarBackground.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
    }

    private void validateAttrs() {
        if (this.numberOfStars <= 0) {
            throw new IllegalArgumentException(String.format("SimpleRatingBar initialized with invalid value for numberOfStars. Found %d, but should be greater than 0", Integer.valueOf(this.numberOfStars)));
        }
        float f = this.desiredStarSize;
        if (f != 2.1474836E9f) {
            float f2 = this.maxStarSize;
            if (f2 != 2.1474836E9f && f > f2) {
                Log.w("SimpleRatingBar", String.format("Initialized with conflicting values: starSize is greater than maxStarSize (%f > %f). I will ignore maxStarSize", Float.valueOf(f), Float.valueOf(this.maxStarSize)));
            }
        }
        if (this.stepSize <= 0.0f) {
            throw new IllegalArgumentException(String.format("SimpleRatingBar initialized with invalid value for stepSize. Found %f, but should be greater than 0", Float.valueOf(this.stepSize)));
        }
        if (this.starBorderWidth <= 0.0f) {
            throw new IllegalArgumentException(String.format("SimpleRatingBar initialized with invalid value for starBorderWidth. Found %f, but should be greater than 0", Float.valueOf(this.starBorderWidth)));
        }
        if (this.starCornerRadius < 0.0f) {
            throw new IllegalArgumentException(String.format("SimpleRatingBar initialized with invalid value for starCornerRadius. Found %f, but should be greater or equal than 0", Float.valueOf(this.starBorderWidth)));
        }
    }

    private float valueFromPixels(float f, @Dimension int i) {
        float f2;
        if (i == 0) {
            f2 = getResources().getDisplayMetrics().density;
        } else {
            if (i != 2) {
                return f;
            }
            f2 = getResources().getDisplayMetrics().scaledDensity;
        }
        return f / f2;
    }

    private float valueToPixels(float f, @Dimension int i) {
        if (i != 0) {
            return i != 2 ? f : TypedValue.applyDimension(2, f, getResources().getDisplayMetrics());
        }
        return TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }

    public AnimationBuilder getAnimationBuilder() {
        return new AnimationBuilder(this);
    }

    @ColorInt
    public int getBorderColor() {
        return this.borderColor;
    }

    @ColorInt
    public int getFillColor() {
        return this.fillColor;
    }

    public Gravity getGravity() {
        return this.gravity;
    }

    public float getMaxStarSize() {
        return this.maxStarSize;
    }

    public int getNumberOfStars() {
        return this.numberOfStars;
    }

    @ColorInt
    public int getPressedBorderColor() {
        return this.pressedBorderColor;
    }

    @ColorInt
    public int getPressedFillColor() {
        return this.pressedFillColor;
    }

    @ColorInt
    public int getPressedStarBackgroundColor() {
        return this.pressedStarBackgroundColor;
    }

    public float getRating() {
        return this.rating;
    }

    @ColorInt
    public int getStarBackgroundColor() {
        return this.starBackgroundColor;
    }

    public float getStarBorderWidth() {
        return this.starBorderWidth;
    }

    public float getStarCornerRadius() {
        return this.starCornerRadius;
    }

    public float getStarSize() {
        return this.currentStarSize;
    }

    public float getStarsSeparation() {
        return this.starsSeparation;
    }

    public float getStepSize() {
        return this.stepSize;
    }

    public boolean isDrawBorderEnabled() {
        return this.drawBorderEnabled;
    }

    public boolean isIndicator() {
        return this.isIndicator;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        if (getWidth() == 0 || height == 0) {
            return;
        }
        this.internalCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        setupColorsInPaint();
        if (this.gravity == Gravity.Left) {
            drawFromLeftToRight(this.internalCanvas);
        } else {
            drawFromRightToLeft(this.internalCanvas);
        }
        if (this.touchInProgress) {
            canvas.drawColor(this.pressedBackgroundColor);
        } else {
            canvas.drawColor(this.backgroundColor);
        }
        canvas.drawBitmap(this.internalBitmap, 0.0f, 0.0f, (Paint) null);
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int width = getWidth();
        int height = getHeight();
        float f = this.desiredStarSize;
        if (f == 2.1474836E9f) {
            this.currentStarSize = calculateBestStarSize(width, height);
        } else {
            this.currentStarSize = f;
        }
        performStarSizeAssociatedCalculations(width, height);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            if (mode == Integer.MIN_VALUE) {
                float f = this.desiredStarSize;
                if (f != 2.1474836E9f) {
                    size = Math.min(calculateTotalWidth(f, this.numberOfStars, this.starsSeparation, true), size);
                } else {
                    float f2 = this.maxStarSize;
                    size = f2 != 2.1474836E9f ? Math.min(calculateTotalWidth(f2, this.numberOfStars, this.starsSeparation, true), size) : Math.min(calculateTotalWidth(this.defaultStarSize, this.numberOfStars, this.starsSeparation, true), size);
                }
            } else {
                float f3 = this.desiredStarSize;
                if (f3 != 2.1474836E9f) {
                    size = calculateTotalWidth(f3, this.numberOfStars, this.starsSeparation, true);
                } else {
                    float f4 = this.maxStarSize;
                    size = f4 != 2.1474836E9f ? calculateTotalWidth(f4, this.numberOfStars, this.starsSeparation, true) : calculateTotalWidth(this.defaultStarSize, this.numberOfStars, this.starsSeparation, true);
                }
            }
        }
        float paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        float f5 = this.starsSeparation;
        int i3 = this.numberOfStars;
        float f6 = (paddingLeft - ((i3 - 1) * f5)) / i3;
        if (mode2 != 1073741824) {
            if (mode2 == Integer.MIN_VALUE) {
                float f7 = this.desiredStarSize;
                if (f7 != 2.1474836E9f) {
                    size2 = Math.min(calculateTotalHeight(f7, i3, f5, true), size2);
                } else {
                    float f8 = this.maxStarSize;
                    size2 = f8 != 2.1474836E9f ? Math.min(calculateTotalHeight(f8, i3, f5, true), size2) : Math.min(calculateTotalHeight(f6, i3, f5, true), size2);
                }
            } else {
                float f9 = this.desiredStarSize;
                if (f9 != 2.1474836E9f) {
                    size2 = calculateTotalHeight(f9, i3, f5, true);
                } else {
                    float f10 = this.maxStarSize;
                    size2 = f10 != 2.1474836E9f ? calculateTotalHeight(f10, i3, f5, true) : calculateTotalHeight(f6, i3, f5, true);
                }
            }
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setRating(savedState.rating);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.rating = getRating();
        return savedState;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        generateInternalCanvas(i, i2);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0040  */
    /* JADX WARN: Code duplicated, block: B:26:0x0050  */
    /* JADX WARN: Code duplicated, block: B:29:0x0061  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ValueAnimator valueAnimator;
        OnRatingBarChangeListener onRatingBarChangeListener;
        if (!this.isIndicator && ((valueAnimator = this.ratingAnimator) == null || !valueAnimator.isRunning())) {
            int action = motionEvent.getAction() & 255;
            if (action != 0) {
                if (action == 1) {
                    setNewRatingFromTouch(motionEvent.getX(), motionEvent.getY());
                    View.OnClickListener onClickListener = this.clickListener;
                    if (onClickListener != null) {
                        onClickListener.onClick(this);
                    }
                } else if (action != 2) {
                    if (action == 3) {
                    }
                } else if (this.starsTouchSpace.contains(motionEvent.getX(), motionEvent.getY())) {
                    this.touchInProgress = true;
                    setNewRatingFromTouch(motionEvent.getX(), motionEvent.getY());
                } else {
                    if (this.touchInProgress) {
                        onRatingBarChangeListener.onRatingChanged(this, this.rating, true);
                    }
                    this.touchInProgress = false;
                }
                OnRatingBarChangeListener onRatingBarChangeListener2 = this.ratingListener;
                if (onRatingBarChangeListener2 != null) {
                    onRatingBarChangeListener2.onRatingChanged(this, this.rating, true);
                }
                this.touchInProgress = false;
            } else if (this.starsTouchSpace.contains(motionEvent.getX(), motionEvent.getY())) {
                this.touchInProgress = true;
                setNewRatingFromTouch(motionEvent.getX(), motionEvent.getY());
            } else {
                if (this.touchInProgress && (onRatingBarChangeListener = this.ratingListener) != null) {
                    onRatingBarChangeListener.onRatingChanged(this, this.rating, true);
                }
                this.touchInProgress = false;
            }
            invalidate();
            return true;
        }
        return false;
    }

    public void setBorderColor(@ColorInt int i) {
        this.borderColor = i;
        invalidate();
    }

    public void setDrawBorderEnabled(boolean z) {
        this.drawBorderEnabled = z;
        invalidate();
    }

    public void setFillColor(@ColorInt int i) {
        this.fillColor = i;
        invalidate();
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
        invalidate();
    }

    public void setIndicator(boolean z) {
        this.isIndicator = z;
        this.touchInProgress = false;
    }

    public void setMaxStarSize(float f) {
        this.maxStarSize = f;
        if (this.currentStarSize > f) {
            requestLayout();
            generateInternalCanvas(getWidth(), getHeight());
            invalidate();
        }
    }

    public void setNumberOfStars(int i) {
        this.numberOfStars = i;
        if (i <= 0) {
            throw new IllegalArgumentException(String.format("SimpleRatingBar initialized with invalid value for numberOfStars. Found %d, but should be greater than 0", Integer.valueOf(i)));
        }
        this.rating = 0.0f;
        requestLayout();
        generateInternalCanvas(getWidth(), getHeight());
        invalidate();
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.clickListener = onClickListener;
    }

    public void setOnRatingBarChangeListener(OnRatingBarChangeListener onRatingBarChangeListener) {
        this.ratingListener = onRatingBarChangeListener;
    }

    public void setPressedBorderColor(@ColorInt int i) {
        this.pressedBorderColor = i;
        invalidate();
    }

    public void setPressedFillColor(@ColorInt int i) {
        this.pressedFillColor = i;
        invalidate();
    }

    public void setPressedStarBackgroundColor(@ColorInt int i) {
        this.pressedStarBackgroundColor = i;
        invalidate();
    }

    public void setRating(float f) {
        this.rating = normalizeRating(f);
        invalidate();
        if (this.ratingListener != null) {
            ValueAnimator valueAnimator = this.ratingAnimator;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                this.ratingListener.onRatingChanged(this, f, false);
            }
        }
    }

    public void setStarBackgroundColor(@ColorInt int i) {
        this.starBackgroundColor = i;
        invalidate();
    }

    public void setStarBorderWidth(float f) {
        this.starBorderWidth = f;
        if (f <= 0.0f) {
            throw new IllegalArgumentException(String.format("SimpleRatingBar initialized with invalid value for starBorderWidth. Found %f, but should be greater than 0", Float.valueOf(f)));
        }
        this.paintStarBorder.setStrokeWidth(f);
        invalidate();
    }

    public void setStarCornerRadius(float f) {
        this.starCornerRadius = f;
        if (f < 0.0f) {
            throw new IllegalArgumentException(String.format("SimpleRatingBar initialized with invalid value for starCornerRadius. Found %f, but should be greater or equal than 0", Float.valueOf(f)));
        }
        CornerPathEffect cornerPathEffect = new CornerPathEffect(f);
        this.cornerPathEffect = cornerPathEffect;
        this.paintStarBorder.setPathEffect(cornerPathEffect);
        this.paintStarOutline.setPathEffect(this.cornerPathEffect);
        invalidate();
    }

    public void setStarSize(float f) {
        this.desiredStarSize = f;
        if (f != 2.1474836E9f) {
            float f2 = this.maxStarSize;
            if (f2 != 2.1474836E9f && f > f2) {
                Log.w("SimpleRatingBar", String.format("Initialized with conflicting values: starSize is greater than maxStarSize (%f > %f). I will ignore maxStarSize", Float.valueOf(f), Float.valueOf(this.maxStarSize)));
            }
        }
        requestLayout();
        generateInternalCanvas(getWidth(), getHeight());
        invalidate();
    }

    public void setStarsSeparation(float f) {
        this.starsSeparation = f;
        requestLayout();
        generateInternalCanvas(getWidth(), getHeight());
        invalidate();
    }

    public void setStepSize(float f) {
        this.stepSize = f;
        if (f <= 0.0f) {
            throw new IllegalArgumentException(String.format("SimpleRatingBar initialized with invalid value for stepSize. Found %f, but should be greater than 0", Float.valueOf(f)));
        }
        invalidate();
    }

    public float getMaxStarSize(@Dimension int i) {
        return valueFromPixels(this.maxStarSize, i);
    }

    public float getStarBorderWidth(@Dimension int i) {
        return valueFromPixels(this.starBorderWidth, i);
    }

    public float getStarCornerRadius(@Dimension int i) {
        return valueFromPixels(this.starCornerRadius, i);
    }

    public float getStarSize(@Dimension int i) {
        return valueFromPixels(this.currentStarSize, i);
    }

    public float getStarsSeparation(@Dimension int i) {
        return valueFromPixels(this.starsSeparation, i);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.iarcuschin.simpleratingbar.SimpleRatingBar.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private float rating;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.rating = 0.0f;
            this.rating = parcel.readFloat();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.rating);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
            this.rating = 0.0f;
        }
    }

    public SimpleRatingBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        parseAttrs(attributeSet);
        initView();
    }

    public void setStarsSeparation(float f, @Dimension int i) {
        setStarsSeparation(valueToPixels(f, i));
    }

    public SimpleRatingBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        parseAttrs(attributeSet);
        initView();
    }

    public void setMaxStarSize(float f, @Dimension int i) {
        setMaxStarSize(valueToPixels(f, i));
    }

    public void setStarBorderWidth(float f, @Dimension int i) {
        setStarBorderWidth(valueToPixels(f, i));
    }

    public void setStarSize(float f, @Dimension int i) {
        setStarSize(valueToPixels(f, i));
    }

    public void setStarCornerRadius(float f, @Dimension int i) {
        setStarCornerRadius(valueToPixels(f, i));
    }
}
