package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.annotation.GravityInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.view.GravityCompat;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class LinearLayoutCompat extends ViewGroup {
    private static final String ACCESSIBILITY_CLASS_NAME = "androidx.appcompat.widget.LinearLayoutCompat";
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface DividerMode {
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface OrientationMode {
    }

    public LinearLayoutCompat(@NonNull Context context) {
        this(context, null);
    }

    private void forceUniformHeight(int i, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (((LinearLayout.LayoutParams) layoutParams).height == -1) {
                    int i4 = ((LinearLayout.LayoutParams) layoutParams).width;
                    ((LinearLayout.LayoutParams) layoutParams).width = childAt.getMeasuredWidth();
                    measureChildWithMargins(childAt, i2, 0, iMakeMeasureSpec, 0);
                    ((LinearLayout.LayoutParams) layoutParams).width = i4;
                }
            }
        }
    }

    private void forceUniformWidth(int i, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (((LinearLayout.LayoutParams) layoutParams).width == -1) {
                    int i4 = ((LinearLayout.LayoutParams) layoutParams).height;
                    ((LinearLayout.LayoutParams) layoutParams).height = childAt.getMeasuredHeight();
                    measureChildWithMargins(childAt, iMakeMeasureSpec, 0, i2, 0);
                    ((LinearLayout.LayoutParams) layoutParams).height = i4;
                }
            }
        }
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i3 + i, i4 + i2);
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public final void drawHorizontalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i);
        this.mDivider.draw(canvas);
    }

    public final void drawVerticalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(i, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + i, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    @Override // android.view.View
    public int getBaseline() {
        int i;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i2 = this.mBaselineAlignedChildIndex;
        if (childCount <= i2) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(i2);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int bottom = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (i = this.mGravity & 112) != 48) {
            if (i == 16) {
                bottom += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
            } else if (i == 80) {
                bottom = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
            }
        }
        return bottom + ((LinearLayout.LayoutParams) ((LayoutParams) childAt.getLayoutParams())).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    @GravityInt
    public int getGravity() {
        return this.mGravity;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final boolean hasDividerBeforeChildAt(int i) {
        if (i == 0) {
            return (this.mShowDividers & 1) != 0;
        }
        if (i == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        }
        if ((this.mShowDividers & 2) == 0) {
            return false;
        }
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (getChildAt(i2).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int right;
        int left;
        int i;
        int bottom;
        if (this.mDivider == null) {
            return;
        }
        int i2 = 0;
        if (this.mOrientation == 1) {
            int virtualChildCount = getVirtualChildCount();
            while (i2 < virtualChildCount) {
                View childAt = getChildAt(i2);
                if (childAt != null && childAt.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                    drawHorizontalDivider(canvas, (childAt.getTop() - ((LinearLayout.LayoutParams) ((LayoutParams) childAt.getLayoutParams())).topMargin) - this.mDividerHeight);
                }
                i2++;
            }
            if (hasDividerBeforeChildAt(virtualChildCount)) {
                View childAt2 = getChildAt(virtualChildCount - 1);
                if (childAt2 == null) {
                    bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
                } else {
                    bottom = childAt2.getBottom() + ((LinearLayout.LayoutParams) ((LayoutParams) childAt2.getLayoutParams())).bottomMargin;
                }
                drawHorizontalDivider(canvas, bottom);
                return;
            }
            return;
        }
        int virtualChildCount2 = getVirtualChildCount();
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        while (i2 < virtualChildCount2) {
            View childAt3 = getChildAt(i2);
            if (childAt3 != null && childAt3.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                LayoutParams layoutParams = (LayoutParams) childAt3.getLayoutParams();
                drawVerticalDivider(canvas, zIsLayoutRtl ? childAt3.getRight() + ((LinearLayout.LayoutParams) layoutParams).rightMargin : (childAt3.getLeft() - ((LinearLayout.LayoutParams) layoutParams).leftMargin) - this.mDividerWidth);
            }
            i2++;
        }
        if (hasDividerBeforeChildAt(virtualChildCount2)) {
            View childAt4 = getChildAt(virtualChildCount2 - 1);
            if (childAt4 != null) {
                LayoutParams layoutParams2 = (LayoutParams) childAt4.getLayoutParams();
                if (zIsLayoutRtl) {
                    left = childAt4.getLeft() - ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                    i = this.mDividerWidth;
                    right = left - i;
                } else {
                    right = childAt4.getRight() + ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                }
            } else if (zIsLayoutRtl) {
                right = getPaddingLeft();
            } else {
                left = getWidth() - getPaddingRight();
                i = this.mDividerWidth;
                right = left - i;
            }
            drawVerticalDivider(canvas, right);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    /* JADX WARN: Code duplicated, block: B:29:0x009e  */
    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft;
        int i5;
        int i6;
        int i7;
        int i8;
        int measuredHeight;
        int paddingTop;
        int i9;
        int i10;
        int i11;
        int i12 = 8;
        int i13 = 16;
        int i14 = 2;
        if (this.mOrientation == 1) {
            int paddingLeft2 = getPaddingLeft();
            int i15 = i3 - i;
            int paddingRight = i15 - getPaddingRight();
            int paddingRight2 = (i15 - paddingLeft2) - getPaddingRight();
            int virtualChildCount = getVirtualChildCount();
            int i16 = this.mGravity;
            int i17 = i16 & 112;
            int i18 = i16 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
            if (i17 != 16) {
                paddingTop = i17 != 80 ? getPaddingTop() : ((getPaddingTop() + i4) - i2) - this.mTotalLength;
            } else {
                paddingTop = getPaddingTop() + (((i4 - i2) - this.mTotalLength) / 2);
            }
            int i19 = 0;
            while (i19 < virtualChildCount) {
                View childAt = getChildAt(i19);
                if (childAt == null) {
                    paddingTop += 0;
                } else {
                    if (childAt.getVisibility() != i12) {
                        int measuredWidth = childAt.getMeasuredWidth();
                        int measuredHeight2 = childAt.getMeasuredHeight();
                        LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                        int i20 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                        if (i20 < 0) {
                            i20 = i18;
                        }
                        int absoluteGravity = GravityCompat.getAbsoluteGravity(i20, ViewCompat.getLayoutDirection(this)) & 7;
                        if (absoluteGravity != 1) {
                            if (absoluteGravity != 5) {
                                i11 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + paddingLeft2;
                            } else {
                                i9 = paddingRight - measuredWidth;
                                i10 = ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                            }
                            if (hasDividerBeforeChildAt(i19)) {
                                paddingTop += this.mDividerHeight;
                            }
                            int i21 = ((LinearLayout.LayoutParams) layoutParams).topMargin + paddingTop;
                            setChildFrame(childAt, i11, i21 + 0, measuredWidth, measuredHeight2);
                            i19 += 0;
                            paddingTop = measuredHeight2 + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + 0 + i21;
                        } else {
                            i9 = ((paddingRight2 - measuredWidth) / i14) + paddingLeft2 + ((LinearLayout.LayoutParams) layoutParams).leftMargin;
                            i10 = ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                        }
                        i11 = i9 - i10;
                        if (hasDividerBeforeChildAt(i19)) {
                            paddingTop += this.mDividerHeight;
                        }
                        int i22 = ((LinearLayout.LayoutParams) layoutParams).topMargin + paddingTop;
                        setChildFrame(childAt, i11, i22 + 0, measuredWidth, measuredHeight2);
                        i19 += 0;
                        paddingTop = measuredHeight2 + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + 0 + i22;
                    }
                    i19++;
                    virtualChildCount = virtualChildCount;
                    i12 = 8;
                    i14 = 2;
                }
                i19++;
                virtualChildCount = virtualChildCount;
                i12 = 8;
                i14 = 2;
            }
            return;
        }
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop2 = getPaddingTop();
        int i23 = i4 - i2;
        int paddingBottom = i23 - getPaddingBottom();
        int paddingBottom2 = (i23 - paddingTop2) - getPaddingBottom();
        int virtualChildCount2 = getVirtualChildCount();
        int i24 = this.mGravity;
        int i25 = i24 & 112;
        boolean z2 = this.mBaselineAligned;
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        int absoluteGravity2 = GravityCompat.getAbsoluteGravity(8388615 & i24, ViewCompat.getLayoutDirection(this));
        if (absoluteGravity2 != 1) {
            paddingLeft = absoluteGravity2 != 5 ? getPaddingLeft() : ((getPaddingLeft() + i3) - i) - this.mTotalLength;
        } else {
            paddingLeft = getPaddingLeft() + (((i3 - i) - this.mTotalLength) / 2);
        }
        int i26 = -1;
        if (zIsLayoutRtl) {
            i5 = virtualChildCount2 - 1;
            i6 = -1;
        } else {
            i5 = 0;
            i6 = 1;
        }
        int i27 = 0;
        while (i27 < virtualChildCount2) {
            int i28 = (i6 * i27) + i5;
            View childAt2 = getChildAt(i28);
            if (childAt2 == null) {
                paddingLeft += 0;
            } else {
                if (childAt2.getVisibility() != 8) {
                    int measuredWidth2 = childAt2.getMeasuredWidth();
                    int measuredHeight3 = childAt2.getMeasuredHeight();
                    LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                    int baseline = (!z2 || ((LinearLayout.LayoutParams) layoutParams2).height == i26) ? -1 : childAt2.getBaseline();
                    int i29 = ((LinearLayout.LayoutParams) layoutParams2).gravity;
                    if (i29 < 0) {
                        i29 = i25;
                    }
                    int i30 = i29 & 112;
                    if (i30 != i13) {
                        if (i30 == 48) {
                            measuredHeight = ((LinearLayout.LayoutParams) layoutParams2).topMargin + paddingTop2;
                            if (baseline != -1) {
                                i7 = (iArr[1] - baseline) + measuredHeight;
                            }
                        } else if (i30 != 80) {
                            measuredHeight = paddingTop2;
                        } else {
                            measuredHeight = (paddingBottom - measuredHeight3) - ((LinearLayout.LayoutParams) layoutParams2).bottomMargin;
                            if (baseline != -1) {
                                measuredHeight -= iArr2[2] - (childAt2.getMeasuredHeight() - baseline);
                            }
                        }
                        i7 = measuredHeight;
                    } else {
                        i7 = ((((paddingBottom2 - measuredHeight3) / 2) + paddingTop2) + ((LinearLayout.LayoutParams) layoutParams2).topMargin) - ((LinearLayout.LayoutParams) layoutParams2).bottomMargin;
                    }
                    if (hasDividerBeforeChildAt(i28)) {
                        paddingLeft += this.mDividerWidth;
                    }
                    int i31 = paddingLeft + ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                    setChildFrame(childAt2, i31 + 0, i7, measuredWidth2, measuredHeight3);
                    i8 = i27 + 0;
                    paddingLeft = measuredWidth2 + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + 0 + i31;
                }
                i27 = i8 + 1;
                iArr2 = iArr2;
                iArr = iArr;
                z2 = z2;
                i13 = 16;
                i26 = -1;
            }
            i8 = i27;
            i27 = i8 + 1;
            iArr2 = iArr2;
            iArr = iArr;
            z2 = z2;
            i13 = 16;
            i26 = -1;
        }
    }

    /* JADX WARN: Code duplicated, block: B:153:0x02fc  */
    /* JADX WARN: Code duplicated, block: B:159:0x0309  */
    /* JADX WARN: Code duplicated, block: B:213:0x0466  */
    /* JADX WARN: Code duplicated, block: B:214:0x046b  */
    /* JADX WARN: Code duplicated, block: B:217:0x0493  */
    /* JADX WARN: Code duplicated, block: B:218:0x0498  */
    /* JADX WARN: Code duplicated, block: B:221:0x04a0  */
    /* JADX WARN: Code duplicated, block: B:222:0x04ae  */
    /* JADX WARN: Code duplicated, block: B:224:0x04c2  */
    /* JADX WARN: Code duplicated, block: B:230:0x04d5  */
    /* JADX WARN: Code duplicated, block: B:239:0x0518  */
    /* JADX WARN: Code duplicated, block: B:245:0x0529  */
    /* JADX WARN: Code duplicated, block: B:248:0x0531 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:249:0x0533  */
    /* JADX WARN: Code duplicated, block: B:251:0x053c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:252:0x053e  */
    /* JADX WARN: Code duplicated, block: B:279:0x05c6  */
    /* JADX WARN: Code duplicated, block: B:281:0x05cc  */
    /* JADX WARN: Code duplicated, block: B:282:0x05d2  */
    /* JADX WARN: Code duplicated, block: B:284:0x05da  */
    /* JADX WARN: Code duplicated, block: B:285:0x05dd  */
    /* JADX WARN: Code duplicated, block: B:287:0x05e5  */
    /* JADX WARN: Code duplicated, block: B:288:0x05f3  */
    /* JADX WARN: Code duplicated, block: B:312:0x067d  */
    /* JADX WARN: Code duplicated, block: B:314:0x0684  */
    /* JADX WARN: Code duplicated, block: B:317:0x06a1  */
    /* JADX WARN: Code duplicated, block: B:319:0x06a7  */
    /* JADX WARN: Code duplicated, block: B:367:0x07b3  */
    /* JADX WARN: Code duplicated, block: B:369:0x07ba  */
    /* JADX WARN: Code duplicated, block: B:373:0x07e6  */
    /* JADX WARN: Code duplicated, block: B:380:0x07f5  */
    /* JADX WARN: Code duplicated, block: B:386:0x082c  */
    /* JADX WARN: Code duplicated, block: B:389:0x084e  */
    /* JADX WARN: Code duplicated, block: B:425:? A[RETURN, SYNTHETIC] */
    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        char c;
        int i3;
        float f;
        int i4;
        int iCombineMeasuredStates;
        int i5;
        int i6;
        int i7;
        char c2;
        View childAt;
        int i8;
        int i9;
        int baseline;
        int i10;
        int i11;
        int i12;
        View childAt2;
        LayoutParams layoutParams;
        int i13;
        int i14;
        float f2;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        boolean z;
        boolean z2;
        LayoutParams layoutParams2;
        int measuredWidth;
        boolean z3;
        int i21;
        boolean z4;
        int i22;
        int measuredHeight;
        boolean z5;
        int baseline2;
        int i23;
        int i24;
        int i25;
        boolean z6;
        boolean z7;
        int i26;
        int i27;
        LayoutParams layoutParams3;
        boolean z8;
        int i28;
        boolean z9;
        int iMax;
        int i29 = -2;
        int i30 = Integer.MIN_VALUE;
        int i31 = 8;
        float f3 = 0.0f;
        int i32 = 1073741824;
        int i33 = 0;
        if (this.mOrientation == 1) {
            this.mTotalLength = 0;
            int virtualChildCount = getVirtualChildCount();
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int i34 = this.mBaselineAlignedChildIndex;
            boolean z10 = this.mUseLargestChild;
            int i35 = 0;
            int i36 = 0;
            int iMax2 = 0;
            int iMax3 = 0;
            int iMax4 = 0;
            float f4 = 0.0f;
            boolean z11 = false;
            int i37 = 0;
            boolean z12 = true;
            boolean z13 = false;
            while (i35 < virtualChildCount) {
                View childAt3 = getChildAt(i35);
                if (childAt3 == null) {
                    this.mTotalLength += i33;
                } else {
                    if (childAt3.getVisibility() == i31) {
                        i35 += 0;
                    } else {
                        if (hasDividerBeforeChildAt(i35)) {
                            this.mTotalLength += this.mDividerHeight;
                        }
                        LayoutParams layoutParams4 = (LayoutParams) childAt3.getLayoutParams();
                        float f5 = ((LinearLayout.LayoutParams) layoutParams4).weight;
                        f4 += f5;
                        if (mode2 == i32 && ((LinearLayout.LayoutParams) layoutParams4).height == 0 && f5 > f3) {
                            int i38 = this.mTotalLength;
                            this.mTotalLength = Math.max(i38, ((LinearLayout.LayoutParams) layoutParams4).topMargin + i38 + ((LinearLayout.LayoutParams) layoutParams4).bottomMargin);
                            i34 = i34;
                            layoutParams3 = layoutParams4;
                            z8 = true;
                        } else {
                            if (((LinearLayout.LayoutParams) layoutParams4).height != 0 || f5 <= f3) {
                                i27 = Integer.MIN_VALUE;
                            } else {
                                ((LinearLayout.LayoutParams) layoutParams4).height = i29;
                                i27 = 0;
                            }
                            int i39 = f4 == f3 ? this.mTotalLength : 0;
                            layoutParams3 = layoutParams4;
                            measureChildWithMargins(childAt3, i, 0, i2, i39);
                            if (i27 != i30) {
                                ((LinearLayout.LayoutParams) layoutParams3).height = i27;
                            }
                            int measuredHeight2 = childAt3.getMeasuredHeight();
                            int i40 = this.mTotalLength;
                            this.mTotalLength = Math.max(i40, i40 + measuredHeight2 + ((LinearLayout.LayoutParams) layoutParams3).topMargin + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin + 0);
                            if (z10) {
                                iMax2 = Math.max(measuredHeight2, iMax2);
                            }
                            z8 = z11;
                        }
                        if (i34 >= 0 && i34 == i35 + 1) {
                            this.mBaselineChildTop = this.mTotalLength;
                        }
                        if (i35 < i34 && ((LinearLayout.LayoutParams) layoutParams3).weight > 0.0f) {
                            throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                        }
                        i28 = mode;
                        if (i28 == 1073741824 || ((LinearLayout.LayoutParams) layoutParams3).width != -1) {
                            z9 = false;
                        } else {
                            z9 = true;
                            z13 = true;
                        }
                        int i41 = ((LinearLayout.LayoutParams) layoutParams3).leftMargin + ((LinearLayout.LayoutParams) layoutParams3).rightMargin;
                        int measuredWidth2 = childAt3.getMeasuredWidth() + i41;
                        iMax = Math.max(i36, measuredWidth2);
                        int iCombineMeasuredStates2 = View.combineMeasuredStates(i37, childAt3.getMeasuredState());
                        boolean z14 = z12 && ((LinearLayout.LayoutParams) layoutParams3).width == -1;
                        if (((LinearLayout.LayoutParams) layoutParams3).weight > 0.0f) {
                            if (!z9) {
                                i41 = measuredWidth2;
                            }
                            iMax4 = Math.max(iMax4, i41);
                        } else {
                            int i42 = iMax4;
                            if (!z9) {
                                i41 = measuredWidth2;
                            }
                            iMax3 = Math.max(iMax3, i41);
                            iMax4 = i42;
                        }
                        i35 += 0;
                        z11 = z8;
                        z12 = z14;
                        i37 = iCombineMeasuredStates2;
                    }
                    i35++;
                    mode = i28;
                    i36 = iMax;
                    i34 = i34;
                    mode2 = mode2;
                    virtualChildCount = virtualChildCount;
                    i33 = 0;
                    i29 = -2;
                    i30 = Integer.MIN_VALUE;
                    i31 = 8;
                    f3 = 0.0f;
                    i32 = 1073741824;
                }
                i34 = i34;
                mode2 = mode2;
                i28 = mode;
                virtualChildCount = virtualChildCount;
                iMax = i36;
                i35++;
                mode = i28;
                i36 = iMax;
                i34 = i34;
                mode2 = mode2;
                virtualChildCount = virtualChildCount;
                i33 = 0;
                i29 = -2;
                i30 = Integer.MIN_VALUE;
                i31 = 8;
                f3 = 0.0f;
                i32 = 1073741824;
            }
            int i43 = mode2;
            int i44 = mode;
            int i45 = virtualChildCount;
            int iMax5 = i36;
            int i46 = iMax2;
            int iMax6 = iMax3;
            int i47 = iMax4;
            int iCombineMeasuredStates3 = i37;
            if (this.mTotalLength > 0) {
                i23 = i45;
                if (hasDividerBeforeChildAt(i23)) {
                    this.mTotalLength += this.mDividerHeight;
                }
            } else {
                i23 = i45;
            }
            int i48 = i43;
            if (z10 && (i48 == Integer.MIN_VALUE || i48 == 0)) {
                int i49 = 0;
                this.mTotalLength = 0;
                int i50 = 0;
                while (i50 < i23) {
                    View childAt4 = getChildAt(i50);
                    if (childAt4 == null) {
                        this.mTotalLength += i49;
                    } else if (childAt4.getVisibility() == 8) {
                        i50 += 0;
                    } else {
                        LayoutParams layoutParams5 = (LayoutParams) childAt4.getLayoutParams();
                        int i51 = this.mTotalLength;
                        this.mTotalLength = Math.max(i51, i51 + i46 + ((LinearLayout.LayoutParams) layoutParams5).topMargin + ((LinearLayout.LayoutParams) layoutParams5).bottomMargin + 0);
                    }
                    i50++;
                    i49 = 0;
                }
            }
            int paddingBottom = getPaddingBottom() + getPaddingTop() + this.mTotalLength;
            this.mTotalLength = paddingBottom;
            int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingBottom, getSuggestedMinimumHeight()), i2, 0);
            int i52 = (16777215 & iResolveSizeAndState) - this.mTotalLength;
            if (z11 || (i52 != 0 && f4 > 0.0f)) {
                float f6 = this.mWeightSum;
                if (f6 > 0.0f) {
                    f4 = f6;
                }
                this.mTotalLength = 0;
                int i53 = 0;
                while (i53 < i23) {
                    View childAt5 = getChildAt(i53);
                    if (childAt5.getVisibility() != 8) {
                        LayoutParams layoutParams6 = (LayoutParams) childAt5.getLayoutParams();
                        float f7 = ((LinearLayout.LayoutParams) layoutParams6).weight;
                        if (f7 > 0.0f) {
                            int i54 = (int) ((i52 * f7) / f4);
                            f4 -= f7;
                            int i55 = i52 - i54;
                            int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + ((LinearLayout.LayoutParams) layoutParams6).leftMargin + ((LinearLayout.LayoutParams) layoutParams6).rightMargin, ((LinearLayout.LayoutParams) layoutParams6).width);
                            if (((LinearLayout.LayoutParams) layoutParams6).height == 0) {
                                i26 = 1073741824;
                                if (i48 == 1073741824) {
                                    if (i54 <= 0) {
                                        i54 = 0;
                                    }
                                    childAt5.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(i54, 1073741824));
                                }
                                iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, childAt5.getMeasuredState() & InputDeviceCompat.SOURCE_ANY);
                                i52 = i55;
                            } else {
                                i26 = 1073741824;
                            }
                            int measuredHeight3 = childAt5.getMeasuredHeight() + i54;
                            if (measuredHeight3 < 0) {
                                measuredHeight3 = 0;
                            }
                            childAt5.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight3, i26));
                            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, childAt5.getMeasuredState() & InputDeviceCompat.SOURCE_ANY);
                            i52 = i55;
                        }
                        int i56 = ((LinearLayout.LayoutParams) layoutParams6).leftMargin + ((LinearLayout.LayoutParams) layoutParams6).rightMargin;
                        int measuredWidth3 = childAt5.getMeasuredWidth() + i56;
                        iMax5 = Math.max(iMax5, measuredWidth3);
                        if (i44 != 1073741824) {
                            i24 = i52;
                            i25 = -1;
                            z6 = ((LinearLayout.LayoutParams) layoutParams6).width == -1;
                            if (!z6) {
                                i56 = measuredWidth3;
                            }
                            iMax6 = Math.max(iMax6, i56);
                            if (z12 || ((LinearLayout.LayoutParams) layoutParams6).width != i25) {
                                z7 = false;
                            } else {
                                z7 = true;
                            }
                            int i57 = this.mTotalLength;
                            this.mTotalLength = Math.max(i57, childAt5.getMeasuredHeight() + i57 + ((LinearLayout.LayoutParams) layoutParams6).topMargin + ((LinearLayout.LayoutParams) layoutParams6).bottomMargin + 0);
                            z12 = z7;
                            i52 = i24;
                        } else {
                            i24 = i52;
                            i25 = -1;
                        }
                        if (!z6) {
                            i56 = measuredWidth3;
                        }
                        iMax6 = Math.max(iMax6, i56);
                        if (z12) {
                            z7 = false;
                        } else {
                            z7 = false;
                        }
                        int i58 = this.mTotalLength;
                        this.mTotalLength = Math.max(i58, childAt5.getMeasuredHeight() + i58 + ((LinearLayout.LayoutParams) layoutParams6).topMargin + ((LinearLayout.LayoutParams) layoutParams6).bottomMargin + 0);
                        z12 = z7;
                        i52 = i24;
                    }
                    i53++;
                    i48 = i48;
                }
                this.mTotalLength = getPaddingBottom() + getPaddingTop() + this.mTotalLength;
            } else {
                iMax6 = Math.max(iMax6, i47);
                if (z10 && i48 != 1073741824) {
                    for (int i59 = 0; i59 < i23; i59++) {
                        View childAt6 = getChildAt(i59);
                        if (childAt6 != null && childAt6.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) childAt6.getLayoutParams())).weight > 0.0f) {
                            childAt6.measure(View.MeasureSpec.makeMeasureSpec(childAt6.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i46, 1073741824));
                        }
                    }
                }
            }
            int i60 = iMax5;
            if (z12 || i44 == 1073741824) {
                iMax6 = i60;
            }
            setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + iMax6, getSuggestedMinimumWidth()), i, iCombineMeasuredStates3), iResolveSizeAndState);
            if (z13) {
                forceUniformWidth(i23, i2);
                return;
            }
            return;
        }
        this.mTotalLength = 0;
        int virtualChildCount2 = getVirtualChildCount();
        int mode3 = View.MeasureSpec.getMode(i);
        int mode4 = View.MeasureSpec.getMode(i2);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        iArr[3] = -1;
        iArr[2] = -1;
        iArr[1] = -1;
        iArr[0] = -1;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        boolean z15 = this.mBaselineAligned;
        boolean z16 = this.mUseLargestChild;
        boolean z17 = mode3 == 1073741824;
        int iMax7 = 0;
        float f8 = 0.0f;
        int iMax8 = 0;
        int i61 = 0;
        int iMax9 = 0;
        int i62 = 0;
        int iMax10 = 0;
        boolean z18 = false;
        boolean z19 = true;
        boolean z20 = false;
        while (i61 < virtualChildCount2) {
            View childAt7 = getChildAt(i61);
            if (childAt7 == null) {
                this.mTotalLength += 0;
                i13 = iMax7;
                i14 = iMax8;
            } else {
                i13 = iMax7;
                i14 = iMax8;
                if (childAt7.getVisibility() == 8) {
                    i61 += 0;
                } else {
                    if (hasDividerBeforeChildAt(i61)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    LayoutParams layoutParams7 = (LayoutParams) childAt7.getLayoutParams();
                    float f9 = ((LinearLayout.LayoutParams) layoutParams7).weight;
                    float f10 = f8 + f9;
                    if (mode3 == 1073741824 && ((LinearLayout.LayoutParams) layoutParams7).width == 0 && f9 > 0.0f) {
                        if (z17) {
                            this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams7).leftMargin + ((LinearLayout.LayoutParams) layoutParams7).rightMargin + this.mTotalLength;
                        } else {
                            int i63 = this.mTotalLength;
                            this.mTotalLength = Math.max(i63, ((LinearLayout.LayoutParams) layoutParams7).leftMargin + i63 + ((LinearLayout.LayoutParams) layoutParams7).rightMargin);
                        }
                        if (z15) {
                            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            childAt7.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                            layoutParams2 = layoutParams7;
                            i17 = i13;
                            i18 = i14;
                            i20 = i61;
                            z = z16;
                            z2 = z15;
                        } else {
                            layoutParams2 = layoutParams7;
                            i17 = i13;
                            i18 = i14;
                            i20 = i61;
                            i21 = 1073741824;
                            z = z16;
                            z2 = z15;
                            z3 = true;
                        }
                        if (mode4 == i21 && ((LinearLayout.LayoutParams) layoutParams2).height == -1) {
                            z4 = true;
                            z20 = true;
                        } else {
                            z4 = false;
                        }
                        i22 = ((LinearLayout.LayoutParams) layoutParams2).topMargin + ((LinearLayout.LayoutParams) layoutParams2).bottomMargin;
                        measuredHeight = childAt7.getMeasuredHeight() + i22;
                        int iCombineMeasuredStates4 = View.combineMeasuredStates(i62, childAt7.getMeasuredState());
                        if (!z2 && (baseline2 = childAt7.getBaseline()) != -1) {
                            int i64 = ((LinearLayout.LayoutParams) layoutParams2).gravity;
                            if (i64 < 0) {
                                i64 = this.mGravity;
                            }
                            int i65 = (((i64 & 112) >> 4) & (-2)) >> 1;
                            iArr[i65] = Math.max(iArr[i65], baseline2);
                            iArr2[i65] = Math.max(iArr2[i65], measuredHeight - baseline2);
                        }
                        iMax8 = Math.max(i18, measuredHeight);
                        if (z19 || ((LinearLayout.LayoutParams) layoutParams2).height != -1) {
                            z5 = false;
                        } else {
                            z5 = true;
                        }
                        if (((LinearLayout.LayoutParams) layoutParams2).weight > 0.0f) {
                            if (z4) {
                                measuredHeight = i22;
                            }
                            iMax10 = Math.max(iMax10, measuredHeight);
                            iMax7 = i17;
                        } else {
                            if (z4) {
                                measuredHeight = i22;
                            }
                            iMax7 = Math.max(i17, measuredHeight);
                        }
                        i61 = i20 + 0;
                        i62 = iCombineMeasuredStates4;
                        z18 = z3;
                        z19 = z5;
                        f8 = f10;
                    } else {
                        int i66 = i61;
                        if (((LinearLayout.LayoutParams) layoutParams7).width == 0) {
                            f2 = 0.0f;
                            if (f9 > 0.0f) {
                                ((LinearLayout.LayoutParams) layoutParams7).width = -2;
                                i15 = 0;
                            }
                            if (f10 == f2) {
                                i16 = this.mTotalLength;
                            } else {
                                i16 = 0;
                            }
                            i17 = i13;
                            i18 = i14;
                            i19 = i15;
                            i20 = i66;
                            z = z16;
                            z2 = z15;
                            measureChildWithMargins(childAt7, i, i16, i2, 0);
                            if (i19 != Integer.MIN_VALUE) {
                                layoutParams2 = layoutParams7;
                                ((LinearLayout.LayoutParams) layoutParams2).width = i19;
                            } else {
                                layoutParams2 = layoutParams7;
                            }
                            measuredWidth = childAt7.getMeasuredWidth();
                            if (z17) {
                                this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams2).leftMargin + measuredWidth + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + 0 + this.mTotalLength;
                            } else {
                                int i67 = this.mTotalLength;
                                this.mTotalLength = Math.max(i67, i67 + measuredWidth + ((LinearLayout.LayoutParams) layoutParams2).leftMargin + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + 0);
                            }
                            if (z) {
                                iMax9 = Math.max(measuredWidth, iMax9);
                            }
                        } else {
                            f2 = 0.0f;
                        }
                        i15 = Integer.MIN_VALUE;
                        if (f10 == f2) {
                            i16 = this.mTotalLength;
                        } else {
                            i16 = 0;
                        }
                        i17 = i13;
                        i18 = i14;
                        i19 = i15;
                        i20 = i66;
                        z = z16;
                        z2 = z15;
                        measureChildWithMargins(childAt7, i, i16, i2, 0);
                        if (i19 != Integer.MIN_VALUE) {
                            layoutParams2 = layoutParams7;
                            ((LinearLayout.LayoutParams) layoutParams2).width = i19;
                        } else {
                            layoutParams2 = layoutParams7;
                        }
                        measuredWidth = childAt7.getMeasuredWidth();
                        if (z17) {
                            this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams2).leftMargin + measuredWidth + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + 0 + this.mTotalLength;
                        } else {
                            int i68 = this.mTotalLength;
                            this.mTotalLength = Math.max(i68, i68 + measuredWidth + ((LinearLayout.LayoutParams) layoutParams2).leftMargin + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + 0);
                        }
                        if (z) {
                            iMax9 = Math.max(measuredWidth, iMax9);
                        }
                    }
                    z3 = z18;
                    i21 = 1073741824;
                    if (mode4 == i21) {
                        z4 = false;
                    } else {
                        z4 = false;
                    }
                    i22 = ((LinearLayout.LayoutParams) layoutParams2).topMargin + ((LinearLayout.LayoutParams) layoutParams2).bottomMargin;
                    measuredHeight = childAt7.getMeasuredHeight() + i22;
                    int iCombineMeasuredStates5 = View.combineMeasuredStates(i62, childAt7.getMeasuredState());
                    if (!z2) {
                    }
                    iMax8 = Math.max(i18, measuredHeight);
                    if (z19) {
                        z5 = false;
                    } else {
                        z5 = false;
                    }
                    if (((LinearLayout.LayoutParams) layoutParams2).weight > 0.0f) {
                        if (z4) {
                            measuredHeight = i22;
                        }
                        iMax10 = Math.max(iMax10, measuredHeight);
                        iMax7 = i17;
                    } else {
                        if (z4) {
                            measuredHeight = i22;
                        }
                        iMax7 = Math.max(i17, measuredHeight);
                    }
                    i61 = i20 + 0;
                    i62 = iCombineMeasuredStates5;
                    z18 = z3;
                    z19 = z5;
                    f8 = f10;
                }
                i61++;
                z16 = z;
                z15 = z2;
            }
            z2 = z15;
            iMax7 = i13;
            iMax8 = i14;
            z = z16;
            i61++;
            z16 = z;
            z15 = z2;
        }
        boolean z21 = z16;
        boolean z22 = z15;
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(virtualChildCount2)) {
            this.mTotalLength += this.mDividerWidth;
        }
        if (iArr[1] == -1 && iArr[0] == -1 && iArr[2] == -1) {
            c = 3;
            if (iArr[3] != -1) {
            }
            if (z21 && (mode3 == Integer.MIN_VALUE || mode3 == 0)) {
                i11 = 0;
                this.mTotalLength = 0;
                i12 = 0;
                while (i12 < virtualChildCount2) {
                    childAt2 = getChildAt(i12);
                    if (childAt2 == null) {
                        this.mTotalLength += i11;
                    } else if (childAt2.getVisibility() == 8) {
                        i12 += 0;
                    } else {
                        layoutParams = (LayoutParams) childAt2.getLayoutParams();
                        if (z17) {
                            this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams).leftMargin + iMax9 + ((LinearLayout.LayoutParams) layoutParams).rightMargin + 0 + this.mTotalLength;
                        } else {
                            int i69 = this.mTotalLength;
                            this.mTotalLength = Math.max(i69, i69 + iMax9 + ((LinearLayout.LayoutParams) layoutParams).leftMargin + ((LinearLayout.LayoutParams) layoutParams).rightMargin + 0);
                        }
                    }
                    i12++;
                    i11 = 0;
                }
            }
            int paddingRight = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
            this.mTotalLength = paddingRight;
            int iResolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingRight, getSuggestedMinimumWidth()), i, 0);
            i3 = (16777215 & iResolveSizeAndState2) - this.mTotalLength;
            if (!z18 || (i3 != 0 && f8 > 0.0f)) {
                f = this.mWeightSum;
                if (f > 0.0f) {
                    f8 = f;
                }
                iArr[3] = -1;
                iArr[2] = -1;
                iArr[1] = -1;
                iArr[0] = -1;
                iArr2[3] = -1;
                iArr2[2] = -1;
                iArr2[1] = -1;
                iArr2[0] = -1;
                this.mTotalLength = 0;
                i4 = i3;
                iCombineMeasuredStates = i62;
                iMax8 = -1;
                i5 = 0;
                while (i5 < virtualChildCount2) {
                    childAt = getChildAt(i5);
                    if (childAt != null || childAt.getVisibility() == 8) {
                        virtualChildCount2 = virtualChildCount2;
                        i8 = i4;
                    } else {
                        LayoutParams layoutParams8 = (LayoutParams) childAt.getLayoutParams();
                        float f11 = ((LinearLayout.LayoutParams) layoutParams8).weight;
                        if (f11 > 0.0f) {
                            int i70 = (int) ((i4 * f11) / f8);
                            float f12 = f8 - f11;
                            int i71 = i4 - i70;
                            int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i2, getPaddingBottom() + getPaddingTop() + ((LinearLayout.LayoutParams) layoutParams8).topMargin + ((LinearLayout.LayoutParams) layoutParams8).bottomMargin, ((LinearLayout.LayoutParams) layoutParams8).height);
                            if (((LinearLayout.LayoutParams) layoutParams8).width == 0) {
                                i10 = 1073741824;
                                if (mode3 == 1073741824) {
                                    if (i70 <= 0) {
                                        i70 = 0;
                                    }
                                    childAt.measure(View.MeasureSpec.makeMeasureSpec(i70, 1073741824), childMeasureSpec2);
                                }
                                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState() & (-16777216));
                                f8 = f12;
                                i8 = i71;
                            } else {
                                i10 = 1073741824;
                            }
                            int measuredWidth4 = childAt.getMeasuredWidth() + i70;
                            if (measuredWidth4 < 0) {
                                measuredWidth4 = 0;
                            }
                            childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth4, i10), childMeasureSpec2);
                            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState() & (-16777216));
                            f8 = f12;
                            i8 = i71;
                        } else {
                            i8 = i4;
                        }
                        if (z17) {
                            this.mTotalLength = childAt.getMeasuredWidth() + ((LinearLayout.LayoutParams) layoutParams8).leftMargin + ((LinearLayout.LayoutParams) layoutParams8).rightMargin + 0 + this.mTotalLength;
                        } else {
                            int i72 = this.mTotalLength;
                            this.mTotalLength = Math.max(i72, childAt.getMeasuredWidth() + i72 + ((LinearLayout.LayoutParams) layoutParams8).leftMargin + ((LinearLayout.LayoutParams) layoutParams8).rightMargin + 0);
                        }
                        boolean z23 = mode4 != 1073741824 && ((LinearLayout.LayoutParams) layoutParams8).height == -1;
                        int i73 = ((LinearLayout.LayoutParams) layoutParams8).topMargin + ((LinearLayout.LayoutParams) layoutParams8).bottomMargin;
                        int measuredHeight4 = childAt.getMeasuredHeight() + i73;
                        iMax8 = Math.max(iMax8, measuredHeight4);
                        if (!z23) {
                            i73 = measuredHeight4;
                        }
                        iMax7 = Math.max(iMax7, i73);
                        if (z19) {
                            i9 = -1;
                            boolean z24 = ((LinearLayout.LayoutParams) layoutParams8).height == -1;
                            if (!z22 && (baseline = childAt.getBaseline()) != i9) {
                                int i74 = ((LinearLayout.LayoutParams) layoutParams8).gravity;
                                if (i74 < 0) {
                                    i74 = this.mGravity;
                                }
                                int i75 = (((i74 & 112) >> 4) & (-2)) >> 1;
                                iArr[i75] = Math.max(iArr[i75], baseline);
                                iArr2[i75] = Math.max(iArr2[i75], measuredHeight4 - baseline);
                            }
                            z19 = z24;
                            f8 = f8;
                        } else {
                            i9 = -1;
                        }
                        if (!z22) {
                        }
                        z19 = z24;
                        f8 = f8;
                    }
                    i5++;
                    virtualChildCount2 = virtualChildCount2;
                    i4 = i8;
                }
                i6 = i2;
                i7 = virtualChildCount2;
                this.mTotalLength = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
                if (iArr[1] != -1 && iArr[0] == -1 && iArr[2] == -1) {
                    c2 = 3;
                    if (iArr[3] != -1) {
                    }
                } else {
                    c2 = 3;
                }
                iMax8 = Math.max(iMax8, Math.max(iArr2[c2], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c2], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
            } else {
                iMax7 = Math.max(iMax7, iMax10);
                if (z21 && mode3 != 1073741824) {
                    for (int i76 = 0; i76 < virtualChildCount2; i76++) {
                        View childAt8 = getChildAt(i76);
                        if (childAt8 != null && childAt8.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) childAt8.getLayoutParams())).weight > 0.0f) {
                            childAt8.measure(View.MeasureSpec.makeMeasureSpec(iMax9, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt8.getMeasuredHeight(), 1073741824));
                        }
                    }
                }
                i6 = i2;
                i7 = virtualChildCount2;
                iCombineMeasuredStates = i62;
            }
            if (z19 || mode4 == 1073741824) {
                iMax7 = iMax8;
            }
            setMeasuredDimension(((-16777216) & iCombineMeasuredStates) | iResolveSizeAndState2, View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + iMax7, getSuggestedMinimumHeight()), i6, iCombineMeasuredStates << 16));
            if (z20) {
                forceUniformHeight(i7, i);
            }
        }
        c = 3;
        iMax8 = Math.max(iMax8, Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
        if (z21) {
            i11 = 0;
            this.mTotalLength = 0;
            i12 = 0;
            while (i12 < virtualChildCount2) {
                childAt2 = getChildAt(i12);
                if (childAt2 == null) {
                    this.mTotalLength += i11;
                } else if (childAt2.getVisibility() == 8) {
                    i12 += 0;
                } else {
                    layoutParams = (LayoutParams) childAt2.getLayoutParams();
                    if (z17) {
                        this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams).leftMargin + iMax9 + ((LinearLayout.LayoutParams) layoutParams).rightMargin + 0 + this.mTotalLength;
                    } else {
                        int i610 = this.mTotalLength;
                        this.mTotalLength = Math.max(i610, i610 + iMax9 + ((LinearLayout.LayoutParams) layoutParams).leftMargin + ((LinearLayout.LayoutParams) layoutParams).rightMargin + 0);
                    }
                }
                i12++;
                i11 = 0;
            }
        }
        int paddingRight2 = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
        this.mTotalLength = paddingRight2;
        int iResolveSizeAndState3 = View.resolveSizeAndState(Math.max(paddingRight2, getSuggestedMinimumWidth()), i, 0);
        i3 = (16777215 & iResolveSizeAndState3) - this.mTotalLength;
        if (z18) {
            f = this.mWeightSum;
            if (f > 0.0f) {
                f8 = f;
            }
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            this.mTotalLength = 0;
            i4 = i3;
            iCombineMeasuredStates = i62;
            iMax8 = -1;
            i5 = 0;
            while (i5 < virtualChildCount2) {
                childAt = getChildAt(i5);
                if (childAt != null) {
                    virtualChildCount2 = virtualChildCount2;
                    i8 = i4;
                } else {
                    virtualChildCount2 = virtualChildCount2;
                    i8 = i4;
                }
                i5++;
                virtualChildCount2 = virtualChildCount2;
                i4 = i8;
            }
            i6 = i2;
            i7 = virtualChildCount2;
            this.mTotalLength = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
            if (iArr[1] != -1) {
                c2 = 3;
                iMax8 = Math.max(iMax8, Math.max(iArr2[c2], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c2], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
            } else {
                c2 = 3;
                iMax8 = Math.max(iMax8, Math.max(iArr2[c2], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c2], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
            }
        } else {
            f = this.mWeightSum;
            if (f > 0.0f) {
                f8 = f;
            }
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            this.mTotalLength = 0;
            i4 = i3;
            iCombineMeasuredStates = i62;
            iMax8 = -1;
            i5 = 0;
            while (i5 < virtualChildCount2) {
                childAt = getChildAt(i5);
                if (childAt != null) {
                    virtualChildCount2 = virtualChildCount2;
                    i8 = i4;
                } else {
                    virtualChildCount2 = virtualChildCount2;
                    i8 = i4;
                }
                i5++;
                virtualChildCount2 = virtualChildCount2;
                i4 = i8;
            }
            i6 = i2;
            i7 = virtualChildCount2;
            this.mTotalLength = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
            if (iArr[1] != -1) {
                c2 = 3;
                iMax8 = Math.max(iMax8, Math.max(iArr2[c2], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c2], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
            } else {
                c2 = 3;
                iMax8 = Math.max(iMax8, Math.max(iArr2[c2], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c2], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
            }
        }
        if (z19) {
            iMax7 = iMax8;
        } else {
            iMax7 = iMax8;
        }
        setMeasuredDimension(((-16777216) & iCombineMeasuredStates) | iResolveSizeAndState3, View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + iMax7, getSuggestedMinimumHeight()), i6, iCombineMeasuredStates << 16));
        if (z20) {
            forceUniformHeight(i7, i);
        }
    }

    public void setBaselineAligned(boolean z) {
        this.mBaselineAligned = z;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i >= 0 && i < getChildCount()) {
            this.mBaselineAlignedChildIndex = i;
            return;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("base aligned child index out of range (0, ");
        sbM.append(getChildCount());
        sbM.append(")");
        throw new IllegalArgumentException(sbM.toString());
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.mDivider) {
            return;
        }
        this.mDivider = drawable;
        if (drawable != null) {
            this.mDividerWidth = drawable.getIntrinsicWidth();
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        setWillNotDraw(drawable == null);
        requestLayout();
    }

    public void setDividerPadding(int i) {
        this.mDividerPadding = i;
    }

    public void setGravity(@GravityInt int i) {
        if (this.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= GravityCompat.START;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.mGravity = i;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i) {
        int i2 = i & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int i3 = this.mGravity;
        if ((8388615 & i3) != i2) {
            this.mGravity = i2 | ((-8388616) & i3);
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.mUseLargestChild = z;
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            requestLayout();
        }
    }

    public void setShowDividers(int i) {
        if (i != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = i;
    }

    public void setVerticalGravity(int i) {
        int i2 = i & 112;
        int i3 = this.mGravity;
        if ((i3 & 112) != i2) {
            this.mGravity = i2 | (i3 & (-113));
            requestLayout();
        }
    }

    public void setWeightSum(float f) {
        this.mWeightSum = Math.max(0.0f, f);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public LinearLayoutCompat(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        int i = this.mOrientation;
        if (i == 0) {
            return new LayoutParams(-2, -2);
        }
        if (i == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    public LinearLayoutCompat(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        int[] iArr = R.styleable.LinearLayoutCompat;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, iArr, attributeSet, tintTypedArrayObtainStyledAttributes.getWrappedTypeArray(), i, 0);
        int i2 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (i2 >= 0) {
            setOrientation(i2);
        }
        int i3 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (i3 >= 0) {
            setGravity(i3);
        }
        boolean z = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.mWeightSum = tintTypedArrayObtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        tintTypedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }
}
