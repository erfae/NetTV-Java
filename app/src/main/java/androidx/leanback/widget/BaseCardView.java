package androidx.leanback.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BaseCardView extends FrameLayout {
    public static final int CARD_REGION_VISIBLE_ACTIVATED = 1;
    public static final int CARD_REGION_VISIBLE_ALWAYS = 0;
    public static final int CARD_REGION_VISIBLE_SELECTED = 2;
    public static final int CARD_TYPE_INFO_OVER = 1;
    public static final int CARD_TYPE_INFO_UNDER = 2;
    public static final int CARD_TYPE_INFO_UNDER_WITH_EXTRA = 3;
    private static final int CARD_TYPE_INVALID = 4;
    public static final int CARD_TYPE_MAIN_ONLY = 0;
    private static final boolean DEBUG = false;
    private static final int[] LB_PRESSED_STATE_SET = {R.attr.state_pressed};
    private static final String TAG = "BaseCardView";
    private final int mActivatedAnimDuration;
    private Animation mAnim;
    private final Runnable mAnimationTrigger;
    private int mCardType;
    private boolean mDelaySelectedAnim;
    public ArrayList<View> mExtraViewList;
    private int mExtraVisibility;
    public float mInfoAlpha;
    public float mInfoOffset;
    public ArrayList<View> mInfoViewList;
    public float mInfoVisFraction;
    private int mInfoVisibility;
    private ArrayList<View> mMainViewList;
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private final int mSelectedAnimDuration;
    private int mSelectedAnimationDelay;

    public class AnimationBase extends Animation {
    }

    public final class InfoAlphaAnimation extends AnimationBase {
        private float mDelta;
        private float mStartValue;

        public InfoAlphaAnimation(float f, float f2) {
            this.mStartValue = f;
            this.mDelta = f2 - f;
        }

        @Override // android.view.animation.Animation
        public final void applyTransformation(float f, Transformation transformation) {
            BaseCardView.this.mInfoAlpha = (f * this.mDelta) + this.mStartValue;
            for (int i = 0; i < BaseCardView.this.mInfoViewList.size(); i++) {
                BaseCardView.this.mInfoViewList.get(i).setAlpha(BaseCardView.this.mInfoAlpha);
            }
        }
    }

    public final class InfoHeightAnimation extends AnimationBase {
        private float mDelta;
        private float mStartValue;

        public InfoHeightAnimation(float f, float f2) {
            this.mStartValue = f;
            this.mDelta = f2 - f;
        }

        @Override // android.view.animation.Animation
        public final void applyTransformation(float f, Transformation transformation) {
            BaseCardView baseCardView = BaseCardView.this;
            baseCardView.mInfoVisFraction = (f * this.mDelta) + this.mStartValue;
            baseCardView.requestLayout();
        }
    }

    public final class InfoOffsetAnimation extends AnimationBase {
        private float mDelta;
        private float mStartValue;

        public InfoOffsetAnimation(float f, float f2) {
            this.mStartValue = f;
            this.mDelta = f2 - f;
        }

        @Override // android.view.animation.Animation
        public final void applyTransformation(float f, Transformation transformation) {
            BaseCardView baseCardView = BaseCardView.this;
            baseCardView.mInfoOffset = (f * this.mDelta) + this.mStartValue;
            baseCardView.requestLayout();
        }
    }

    public BaseCardView(Context context) {
        this(context, null);
    }

    private void animateInfoAlpha(boolean z) {
        cancelAnimations();
        if (z) {
            for (int i = 0; i < this.mInfoViewList.size(); i++) {
                this.mInfoViewList.get(i).setVisibility(0);
            }
        }
        if ((z ? 1.0f : 0.0f) == this.mInfoAlpha) {
            return;
        }
        InfoAlphaAnimation infoAlphaAnimation = new InfoAlphaAnimation(this.mInfoAlpha, z ? 1.0f : 0.0f);
        this.mAnim = infoAlphaAnimation;
        infoAlphaAnimation.setDuration(this.mActivatedAnimDuration);
        this.mAnim.setInterpolator(new DecelerateInterpolator());
        this.mAnim.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.leanback.widget.BaseCardView.4
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (BaseCardView.this.mInfoAlpha == 0.0d) {
                    for (int i2 = 0; i2 < BaseCardView.this.mInfoViewList.size(); i2++) {
                        BaseCardView.this.mInfoViewList.get(i2).setVisibility(8);
                    }
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        });
        startAnimation(this.mAnim);
    }

    private void animateInfoHeight(boolean z) {
        cancelAnimations();
        if (z) {
            for (int i = 0; i < this.mInfoViewList.size(); i++) {
                this.mInfoViewList.get(i).setVisibility(0);
            }
        }
        float f = z ? 1.0f : 0.0f;
        if (this.mInfoVisFraction == f) {
            return;
        }
        InfoHeightAnimation infoHeightAnimation = new InfoHeightAnimation(this.mInfoVisFraction, f);
        this.mAnim = infoHeightAnimation;
        infoHeightAnimation.setDuration(this.mSelectedAnimDuration);
        this.mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mAnim.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.leanback.widget.BaseCardView.3
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (BaseCardView.this.mInfoVisFraction == 0.0f) {
                    for (int i2 = 0; i2 < BaseCardView.this.mInfoViewList.size(); i2++) {
                        BaseCardView.this.mInfoViewList.get(i2).setVisibility(8);
                    }
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        });
        startAnimation(this.mAnim);
    }

    private void applyActiveState(boolean z) {
        int i;
        if (hasInfoRegion() && (i = this.mInfoVisibility) == 1) {
            setInfoViewVisibility(isRegionVisible(i));
        }
    }

    private void applySelectedState(boolean z) {
        removeCallbacks(this.mAnimationTrigger);
        if (this.mCardType != 3) {
            if (this.mInfoVisibility == 2) {
                setInfoViewVisibility(z);
            }
        } else if (!z) {
            animateInfoOffset(false);
        } else if (this.mDelaySelectedAnim) {
            postDelayed(this.mAnimationTrigger, this.mSelectedAnimationDelay);
        } else {
            post(this.mAnimationTrigger);
            this.mDelaySelectedAnim = true;
        }
    }

    private void findChildrenViews() {
        this.mMainViewList.clear();
        this.mInfoViewList.clear();
        this.mExtraViewList.clear();
        int childCount = getChildCount();
        boolean z = hasInfoRegion() && isCurrentRegionVisible(this.mInfoVisibility);
        boolean z2 = hasExtraRegion() && this.mInfoOffset > 0.0f;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                int i2 = ((LayoutParams) childAt.getLayoutParams()).viewType;
                if (i2 == 1) {
                    childAt.setAlpha(this.mInfoAlpha);
                    this.mInfoViewList.add(childAt);
                    childAt.setVisibility(z ? 0 : 8);
                } else if (i2 == 2) {
                    this.mExtraViewList.add(childAt);
                    childAt.setVisibility(z2 ? 0 : 8);
                } else {
                    this.mMainViewList.add(childAt);
                    childAt.setVisibility(0);
                }
            }
        }
    }

    private boolean hasExtraRegion() {
        return this.mCardType == 3;
    }

    private boolean hasInfoRegion() {
        return this.mCardType != 0;
    }

    private boolean isCurrentRegionVisible(int i) {
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            return isActivated();
        }
        if (i != 2) {
            return false;
        }
        if (this.mCardType == 2) {
            return this.mInfoVisFraction > 0.0f;
        }
        return isSelected();
    }

    private boolean isRegionVisible(int i) {
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            return isActivated();
        }
        if (i != 2) {
            return false;
        }
        return isSelected();
    }

    private void setInfoViewVisibility(boolean z) {
        int i = this.mCardType;
        if (i != 3) {
            if (i != 2) {
                if (i == 1) {
                    animateInfoAlpha(z);
                    return;
                }
                return;
            } else {
                if (this.mInfoVisibility == 2) {
                    animateInfoHeight(z);
                    return;
                }
                for (int i2 = 0; i2 < this.mInfoViewList.size(); i2++) {
                    this.mInfoViewList.get(i2).setVisibility(z ? 0 : 8);
                }
                return;
            }
        }
        if (z) {
            for (int i3 = 0; i3 < this.mInfoViewList.size(); i3++) {
                this.mInfoViewList.get(i3).setVisibility(0);
            }
            return;
        }
        for (int i4 = 0; i4 < this.mInfoViewList.size(); i4++) {
            this.mInfoViewList.get(i4).setVisibility(8);
        }
        for (int i5 = 0; i5 < this.mExtraViewList.size(); i5++) {
            this.mExtraViewList.get(i5).setVisibility(8);
        }
        this.mInfoOffset = 0.0f;
    }

    public final void animateInfoOffset(boolean z) {
        cancelAnimations();
        int i = 0;
        if (z) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mMeasuredWidth, 1073741824);
            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
            int iMax = 0;
            for (int i2 = 0; i2 < this.mExtraViewList.size(); i2++) {
                View view = this.mExtraViewList.get(i2);
                view.setVisibility(0);
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                iMax = Math.max(iMax, view.getMeasuredHeight());
            }
            i = iMax;
        }
        InfoOffsetAnimation infoOffsetAnimation = new InfoOffsetAnimation(this.mInfoOffset, z ? i : 0.0f);
        this.mAnim = infoOffsetAnimation;
        infoOffsetAnimation.setDuration(this.mSelectedAnimDuration);
        this.mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mAnim.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.leanback.widget.BaseCardView.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (BaseCardView.this.mInfoOffset == 0.0f) {
                    for (int i3 = 0; i3 < BaseCardView.this.mExtraViewList.size(); i3++) {
                        BaseCardView.this.mExtraViewList.get(i3).setVisibility(8);
                    }
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        });
        startAnimation(this.mAnim);
    }

    public final void cancelAnimations() {
        Animation animation = this.mAnim;
        if (animation != null) {
            animation.cancel();
            this.mAnim = null;
            clearAnimation();
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public int getCardType() {
        return this.mCardType;
    }

    @Deprecated
    public int getExtraVisibility() {
        return this.mExtraVisibility;
    }

    public final float getFinalInfoAlpha() {
        return (this.mCardType == 1 && this.mInfoVisibility == 2 && !isSelected()) ? 0.0f : 1.0f;
    }

    public final float getFinalInfoVisFraction() {
        return (this.mCardType == 2 && this.mInfoVisibility == 2 && !isSelected()) ? 0.0f : 1.0f;
    }

    public int getInfoVisibility() {
        return this.mInfoVisibility;
    }

    public boolean isSelectedAnimationDelayed() {
        return this.mDelaySelectedAnim;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i);
        int length = iArrOnCreateDrawableState.length;
        boolean z = false;
        boolean z2 = false;
        for (int i2 = 0; i2 < length; i2++) {
            if (iArrOnCreateDrawableState[i2] == 16842919) {
                z = true;
            }
            if (iArrOnCreateDrawableState[i2] == 16842910) {
                z2 = true;
            }
        }
        if (z && z2) {
            return View.PRESSED_ENABLED_STATE_SET;
        }
        if (z) {
            return LB_PRESSED_STATE_SET;
        }
        return z2 ? View.ENABLED_STATE_SET : View.EMPTY_STATE_SET;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mAnimationTrigger);
        cancelAnimations();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float paddingTop = getPaddingTop();
        for (int i5 = 0; i5 < this.mMainViewList.size(); i5++) {
            View view = this.mMainViewList.get(i5);
            if (view.getVisibility() != 8) {
                view.layout(getPaddingLeft(), (int) paddingTop, getPaddingLeft() + this.mMeasuredWidth, (int) (view.getMeasuredHeight() + paddingTop));
                paddingTop += view.getMeasuredHeight();
            }
        }
        if (hasInfoRegion()) {
            float measuredHeight = 0.0f;
            for (int i6 = 0; i6 < this.mInfoViewList.size(); i6++) {
                measuredHeight += this.mInfoViewList.get(i6).getMeasuredHeight();
            }
            int i7 = this.mCardType;
            if (i7 == 1) {
                paddingTop -= measuredHeight;
                if (paddingTop < 0.0f) {
                    paddingTop = 0.0f;
                }
            } else if (i7 != 2) {
                paddingTop -= this.mInfoOffset;
            } else if (this.mInfoVisibility == 2) {
                measuredHeight *= this.mInfoVisFraction;
            }
            for (int i8 = 0; i8 < this.mInfoViewList.size(); i8++) {
                View view2 = this.mInfoViewList.get(i8);
                if (view2.getVisibility() != 8) {
                    int measuredHeight2 = view2.getMeasuredHeight();
                    if (measuredHeight2 > measuredHeight) {
                        measuredHeight2 = (int) measuredHeight;
                    }
                    float f = measuredHeight2;
                    paddingTop += f;
                    view2.layout(getPaddingLeft(), (int) paddingTop, getPaddingLeft() + this.mMeasuredWidth, (int) paddingTop);
                    measuredHeight -= f;
                    if (measuredHeight <= 0.0f) {
                        break;
                    }
                }
            }
            if (hasExtraRegion()) {
                for (int i9 = 0; i9 < this.mExtraViewList.size(); i9++) {
                    View view3 = this.mExtraViewList.get(i9);
                    if (view3.getVisibility() != 8) {
                        view3.layout(getPaddingLeft(), (int) paddingTop, getPaddingLeft() + this.mMeasuredWidth, (int) (view3.getMeasuredHeight() + paddingTop));
                        paddingTop += view3.getMeasuredHeight();
                    }
                }
            }
        }
        onSizeChanged(0, 0, i3 - i, i4 - i2);
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00d5  */
    /* JADX WARN: Code duplicated, block: B:42:0x00de  */
    /* JADX WARN: Code duplicated, block: B:43:0x00e0  */
    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int measuredHeight;
        int measuredHeight2;
        float f;
        float f2;
        boolean z = false;
        this.mMeasuredWidth = 0;
        this.mMeasuredHeight = 0;
        findChildrenViews();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int measuredHeight3 = 0;
        int iCombineMeasuredStates = 0;
        for (int i3 = 0; i3 < this.mMainViewList.size(); i3++) {
            View view = this.mMainViewList.get(i3);
            if (view.getVisibility() != 8) {
                measureChild(view, iMakeMeasureSpec, iMakeMeasureSpec);
                this.mMeasuredWidth = Math.max(this.mMeasuredWidth, view.getMeasuredWidth());
                measuredHeight3 += view.getMeasuredHeight();
                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view.getMeasuredState());
            }
        }
        setPivotX(this.mMeasuredWidth / 2);
        setPivotY(measuredHeight3 / 2);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mMeasuredWidth, 1073741824);
        if (hasInfoRegion()) {
            measuredHeight = 0;
            for (int i4 = 0; i4 < this.mInfoViewList.size(); i4++) {
                View view2 = this.mInfoViewList.get(i4);
                if (view2.getVisibility() != 8) {
                    measureChild(view2, iMakeMeasureSpec2, iMakeMeasureSpec);
                    if (this.mCardType != 1) {
                        measuredHeight = view2.getMeasuredHeight() + measuredHeight;
                    }
                    iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view2.getMeasuredState());
                }
            }
            if (hasExtraRegion()) {
                measuredHeight2 = 0;
                for (int i5 = 0; i5 < this.mExtraViewList.size(); i5++) {
                    View view3 = this.mExtraViewList.get(i5);
                    if (view3.getVisibility() != 8) {
                        measureChild(view3, iMakeMeasureSpec2, iMakeMeasureSpec);
                        measuredHeight2 += view3.getMeasuredHeight();
                        iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view3.getMeasuredState());
                    }
                }
            }
            if (hasInfoRegion() && this.mInfoVisibility == 2) {
                z = true;
            }
            float f3 = measuredHeight3;
            f = measuredHeight;
            if (z) {
                f *= this.mInfoVisFraction;
            }
            float f4 = f3 + f + measuredHeight2;
            if (z) {
                f2 = 0.0f;
            } else {
                f2 = this.mInfoOffset;
            }
            this.mMeasuredHeight = (int) (f4 - f2);
            setMeasuredDimension(View.resolveSizeAndState(getPaddingRight() + getPaddingLeft() + this.mMeasuredWidth, i, iCombineMeasuredStates), View.resolveSizeAndState(getPaddingBottom() + getPaddingTop() + this.mMeasuredHeight, i2, iCombineMeasuredStates << 16));
        }
        measuredHeight = 0;
        measuredHeight2 = 0;
        if (hasInfoRegion()) {
            z = true;
        }
        float f5 = measuredHeight3;
        f = measuredHeight;
        if (z) {
            f *= this.mInfoVisFraction;
        }
        float f6 = f5 + f + measuredHeight2;
        if (z) {
            f2 = 0.0f;
        } else {
            f2 = this.mInfoOffset;
        }
        this.mMeasuredHeight = (int) (f6 - f2);
        setMeasuredDimension(View.resolveSizeAndState(getPaddingRight() + getPaddingLeft() + this.mMeasuredWidth, i, iCombineMeasuredStates), View.resolveSizeAndState(getPaddingBottom() + getPaddingTop() + this.mMeasuredHeight, i2, iCombineMeasuredStates << 16));
    }

    @Override // android.view.View
    public void setActivated(boolean z) {
        if (z != isActivated()) {
            super.setActivated(z);
            applyActiveState(isActivated());
        }
    }

    public void setCardType(int i) {
        if (this.mCardType != i) {
            if (i < 0 || i >= 4) {
                Log.e(TAG, "Invalid card type specified: " + i + ". Defaulting to type CARD_TYPE_MAIN_ONLY.");
                this.mCardType = 0;
            } else {
                this.mCardType = i;
            }
            requestLayout();
        }
    }

    @Deprecated
    public void setExtraVisibility(int i) {
        if (this.mExtraVisibility != i) {
            this.mExtraVisibility = i;
        }
    }

    public void setInfoVisibility(int i) {
        if (this.mInfoVisibility != i) {
            cancelAnimations();
            this.mInfoVisibility = i;
            this.mInfoVisFraction = getFinalInfoVisFraction();
            requestLayout();
            float finalInfoAlpha = getFinalInfoAlpha();
            if (finalInfoAlpha != this.mInfoAlpha) {
                this.mInfoAlpha = finalInfoAlpha;
                for (int i2 = 0; i2 < this.mInfoViewList.size(); i2++) {
                    this.mInfoViewList.get(i2).setAlpha(this.mInfoAlpha);
                }
            }
        }
    }

    @Override // android.view.View
    public void setSelected(boolean z) {
        if (z != isSelected()) {
            super.setSelected(z);
            applySelectedState(isSelected());
        }
    }

    public void setSelectedAnimationDelayed(boolean z) {
        this.mDelaySelectedAnim = z;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.View
    public String toString() {
        return super.toString();
    }

    public BaseCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, androidx.leanback.R.attr.baseCardViewStyle);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public BaseCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAnimationTrigger = new Runnable() { // from class: androidx.leanback.widget.BaseCardView.1
            @Override // java.lang.Runnable
            public void run() {
                BaseCardView.this.animateInfoOffset(true);
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.leanback.R.styleable.lbBaseCardView, i, 0);
        try {
            this.mCardType = typedArrayObtainStyledAttributes.getInteger(androidx.leanback.R.styleable.lbBaseCardView_cardType, 0);
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(androidx.leanback.R.styleable.lbBaseCardView_cardForeground);
            if (drawable != null) {
                setForeground(drawable);
            }
            Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(androidx.leanback.R.styleable.lbBaseCardView_cardBackground);
            if (drawable2 != null) {
                setBackground(drawable2);
            }
            this.mInfoVisibility = typedArrayObtainStyledAttributes.getInteger(androidx.leanback.R.styleable.lbBaseCardView_infoVisibility, 1);
            int integer = typedArrayObtainStyledAttributes.getInteger(androidx.leanback.R.styleable.lbBaseCardView_extraVisibility, 2);
            this.mExtraVisibility = integer;
            int i2 = this.mInfoVisibility;
            if (integer < i2) {
                this.mExtraVisibility = i2;
            }
            this.mSelectedAnimationDelay = typedArrayObtainStyledAttributes.getInteger(androidx.leanback.R.styleable.lbBaseCardView_selectedAnimationDelay, getResources().getInteger(androidx.leanback.R.integer.lb_card_selected_animation_delay));
            this.mSelectedAnimDuration = typedArrayObtainStyledAttributes.getInteger(androidx.leanback.R.styleable.lbBaseCardView_selectedAnimationDuration, getResources().getInteger(androidx.leanback.R.integer.lb_card_selected_animation_duration));
            this.mActivatedAnimDuration = typedArrayObtainStyledAttributes.getInteger(androidx.leanback.R.styleable.lbBaseCardView_activatedAnimationDuration, getResources().getInteger(androidx.leanback.R.integer.lb_card_activated_animation_duration));
            typedArrayObtainStyledAttributes.recycle();
            this.mDelaySelectedAnim = true;
            this.mMainViewList = new ArrayList<>();
            this.mInfoViewList = new ArrayList<>();
            this.mExtraViewList = new ArrayList<>();
            this.mInfoOffset = 0.0f;
            this.mInfoVisFraction = getFinalInfoVisFraction();
            this.mInfoAlpha = getFinalInfoAlpha();
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        public static final int VIEW_TYPE_EXTRA = 2;
        public static final int VIEW_TYPE_INFO = 1;
        public static final int VIEW_TYPE_MAIN = 0;

        @ViewDebug.ExportedProperty(category = TtmlNode.TAG_LAYOUT, mapping = {@ViewDebug.IntToString(from = 0, to = "MAIN"), @ViewDebug.IntToString(from = 1, to = "INFO"), @ViewDebug.IntToString(from = 2, to = "EXTRA")})
        public int viewType;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.viewType = 0;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.leanback.R.styleable.lbBaseCardView_Layout);
            this.viewType = typedArrayObtainStyledAttributes.getInt(androidx.leanback.R.styleable.lbBaseCardView_Layout_layout_viewType, 0);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.viewType = 0;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.viewType = 0;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.viewType = 0;
            this.viewType = layoutParams.viewType;
        }
    }
}
