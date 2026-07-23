package androidx.leanback.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ScaleFrameLayout extends FrameLayout {
    private static final int DEFAULT_CHILD_GRAVITY = 8388659;
    private float mChildScale;
    private float mLayoutScaleX;
    private float mLayoutScaleY;

    public ScaleFrameLayout(Context context) {
        this(context, null);
    }

    private static int getScaledMeasureSpec(int i, float f) {
        return f == 1.0f ? i : View.MeasureSpec.makeMeasureSpec((int) ((View.MeasureSpec.getSize(i) / f) + 0.5f), View.MeasureSpec.getMode(i));
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        view.setScaleX(this.mChildScale);
        view.setScaleY(this.mChildScale);
    }

    @Override // android.view.ViewGroup
    public final boolean addViewInLayout(View view, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        boolean zAddViewInLayout = super.addViewInLayout(view, i, layoutParams, z);
        if (zAddViewInLayout) {
            view.setScaleX(this.mChildScale);
            view.setScaleY(this.mChildScale);
        }
        return zAddViewInLayout;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:39:0x00da  */
    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft;
        int i5;
        int paddingRight;
        int paddingTop;
        int i6;
        int paddingBottom;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        ScaleFrameLayout scaleFrameLayout = this;
        int childCount = getChildCount();
        int layoutDirection = getLayoutDirection();
        float width = layoutDirection == 1 ? getWidth() - getPivotX() : getPivotX();
        if (scaleFrameLayout.mLayoutScaleX != 1.0f) {
            int paddingLeft2 = getPaddingLeft();
            float f = scaleFrameLayout.mLayoutScaleX;
            paddingLeft = paddingLeft2 + ((int) ((width - (width / f)) + 0.5f));
            i5 = (int) ((((i3 - i) - width) / f) + width + 0.5f);
            paddingRight = getPaddingRight();
        } else {
            paddingLeft = getPaddingLeft();
            i5 = i3 - i;
            paddingRight = getPaddingRight();
        }
        int i13 = i5 - paddingRight;
        float pivotY = getPivotY();
        if (scaleFrameLayout.mLayoutScaleY != 1.0f) {
            int paddingTop2 = getPaddingTop();
            float f2 = scaleFrameLayout.mLayoutScaleY;
            paddingTop = paddingTop2 + ((int) ((pivotY - (pivotY / f2)) + 0.5f));
            i6 = (int) ((((i4 - i2) - pivotY) / f2) + pivotY + 0.5f);
            paddingBottom = getPaddingBottom();
        } else {
            paddingTop = getPaddingTop();
            i6 = i4 - i2;
            paddingBottom = getPaddingBottom();
        }
        int i14 = i6 - paddingBottom;
        int i15 = 0;
        while (i15 < childCount) {
            View childAt = scaleFrameLayout.getChildAt(i15);
            if (childAt.getVisibility() != 8) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i16 = layoutParams.gravity;
                if (i16 == -1) {
                    i16 = 8388659;
                }
                int absoluteGravity = Gravity.getAbsoluteGravity(i16, layoutDirection);
                int i17 = i16 & 112;
                int i18 = absoluteGravity & 7;
                if (i18 != 1) {
                    if (i18 != 5) {
                        i9 = layoutParams.leftMargin + paddingLeft;
                    } else {
                        i7 = i13 - measuredWidth;
                        i8 = layoutParams.rightMargin;
                    }
                    if (i17 == 16) {
                        i10 = (((i14 - paddingTop) - measuredHeight) / 2) + paddingTop + layoutParams.topMargin;
                        i11 = layoutParams.bottomMargin;
                    } else if (i17 == 48 && i17 == 80) {
                        i10 = i14 - measuredHeight;
                        i11 = layoutParams.bottomMargin;
                    } else {
                        i12 = i + paddingTop;
                        childAt.layout(i9, i12, measuredWidth + i9, measuredHeight + i12);
                        childAt.setPivotX(width - i9);
                        childAt.setPivotY(pivotY - i12);
                    }
                    i12 = i10 - i11;
                    childAt.layout(i9, i12, measuredWidth + i9, measuredHeight + i12);
                    childAt.setPivotX(width - i9);
                    childAt.setPivotY(pivotY - i12);
                } else {
                    i7 = (((i13 - paddingLeft) - measuredWidth) / 2) + paddingLeft + layoutParams.leftMargin;
                    i8 = layoutParams.rightMargin;
                }
                i9 = i7 - i8;
                if (i17 == 16) {
                    int i19 = i17 == 48 ? layoutParams.topMargin : layoutParams.topMargin;
                    i12 = i19 + paddingTop;
                    childAt.layout(i9, i12, measuredWidth + i9, measuredHeight + i12);
                    childAt.setPivotX(width - i9);
                    childAt.setPivotY(pivotY - i12);
                } else {
                    i10 = (((i14 - paddingTop) - measuredHeight) / 2) + paddingTop + layoutParams.topMargin;
                    i11 = layoutParams.bottomMargin;
                }
                i12 = i10 - i11;
                childAt.layout(i9, i12, measuredWidth + i9, measuredHeight + i12);
                childAt.setPivotX(width - i9);
                childAt.setPivotY(pivotY - i12);
            }
            i15++;
            scaleFrameLayout = this;
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        float f = this.mLayoutScaleX;
        if (f == 1.0f && this.mLayoutScaleY == 1.0f) {
            super.onMeasure(i, i2);
        } else {
            super.onMeasure(getScaledMeasureSpec(i, f), getScaledMeasureSpec(i2, this.mLayoutScaleY));
            setMeasuredDimension((int) ((getMeasuredWidth() * this.mLayoutScaleX) + 0.5f), (int) ((getMeasuredHeight() * this.mLayoutScaleY) + 0.5f));
        }
    }

    public void setChildScale(float f) {
        if (this.mChildScale != f) {
            this.mChildScale = f;
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).setScaleX(f);
                getChildAt(i).setScaleY(f);
            }
        }
    }

    @Override // android.view.View
    public void setForeground(Drawable drawable) {
        throw new UnsupportedOperationException();
    }

    public void setLayoutScaleX(float f) {
        if (f != this.mLayoutScaleX) {
            this.mLayoutScaleX = f;
            requestLayout();
        }
    }

    public void setLayoutScaleY(float f) {
        if (f != this.mLayoutScaleY) {
            this.mLayoutScaleY = f;
            requestLayout();
        }
    }

    public ScaleFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScaleFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLayoutScaleX = 1.0f;
        this.mLayoutScaleY = 1.0f;
        this.mChildScale = 1.0f;
    }
}
