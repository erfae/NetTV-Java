package com.google.android.material.sidesheet;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
final class RightSheetDelegate extends SheetDelegate {
    public final SideSheetBehavior<? extends View> sheetBehavior;

    public RightSheetDelegate(@NonNull SideSheetBehavior<? extends View> sideSheetBehavior) {
        this.sheetBehavior = sideSheetBehavior;
    }

    private boolean isReleasedCloseToOriginEdge(@NonNull View view) {
        return view.getLeft() > (getHiddenOffset() - getExpandedOffset()) / 2;
    }

    private boolean isSwipeSignificant(float f, float f2) {
        if (Math.abs(f) > Math.abs(f2)) {
            Objects.requireNonNull(this.sheetBehavior);
            if (f2 > 500) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final float calculateSlideOffsetBasedOnOutwardEdge(int i) {
        float hiddenOffset = getHiddenOffset();
        return (hiddenOffset - i) / (hiddenOffset - getExpandedOffset());
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004a  */
    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int calculateTargetStateOnViewReleased(@NonNull View view, float f, float f2) {
        int left;
        if (f >= 0.0f) {
            float fAbs = Math.abs((this.sheetBehavior.getHideFriction() * f) + view.getRight());
            Objects.requireNonNull(this.sheetBehavior);
            if (!(fAbs > 0.5f)) {
                if (f == 0.0f) {
                    left = view.getLeft();
                    if (Math.abs(left - getExpandedOffset()) < Math.abs(left - getHiddenOffset())) {
                    }
                } else {
                    if (!(Math.abs(f) > Math.abs(f2))) {
                        left = view.getLeft();
                        if (Math.abs(left - getExpandedOffset()) < Math.abs(left - getHiddenOffset())) {
                        }
                    }
                }
                return 5;
            }
            if (isSwipeSignificant(f, f2) || isReleasedCloseToOriginEdge(view)) {
                return 5;
            }
        }
        return 3;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getExpandedOffset() {
        return Math.max(0, getHiddenOffset() - this.sheetBehavior.getChildWidth());
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getHiddenOffset() {
        return this.sheetBehavior.getParentWidth();
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final <V extends View> int getOutwardEdge(@NonNull V v) {
        return v.getLeft();
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final void getSheetEdge() {
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final boolean isSettling(View view, int i, boolean z) {
        int outwardEdgeOffsetForState = this.sheetBehavior.getOutwardEdgeOffsetForState(i);
        ViewDragHelper viewDragHelper = this.sheetBehavior.getViewDragHelper();
        return viewDragHelper != null && (!z ? !viewDragHelper.smoothSlideViewTo(view, outwardEdgeOffsetForState, view.getTop()) : !viewDragHelper.settleCapturedViewAt(outwardEdgeOffsetForState, view.getTop()));
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final void updateCoplanarSiblingLayoutParams(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        int parentWidth = this.sheetBehavior.getParentWidth();
        if (i <= parentWidth) {
            marginLayoutParams.rightMargin = parentWidth - i;
        }
    }
}
