package androidx.leanback.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
class ItemAlignmentFacetHelper {
    private static Rect sRect = new Rect();

    private ItemAlignmentFacetHelper() {
    }

    public static int getAlignmentPosition(View view, ItemAlignmentFacet.ItemAlignmentDef itemAlignmentDef, int i) {
        View viewFindViewById;
        int i2;
        int height;
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int i3 = itemAlignmentDef.mViewId;
        if (i3 == 0 || (viewFindViewById = view.findViewById(i3)) == null) {
            viewFindViewById = view;
        }
        int paddingBottom = itemAlignmentDef.mOffset;
        if (i != 0) {
            if (itemAlignmentDef.mOffsetWithPadding) {
                float f = itemAlignmentDef.mOffsetPercent;
                if (f == 0.0f) {
                    paddingBottom += viewFindViewById.getPaddingTop();
                } else if (f == 100.0f) {
                    paddingBottom -= viewFindViewById.getPaddingBottom();
                }
            }
            if (itemAlignmentDef.mOffsetPercent != -1.0f) {
                if (viewFindViewById == view) {
                    Objects.requireNonNull(layoutParams);
                    height = (viewFindViewById.getHeight() - layoutParams.mTopInset) - layoutParams.mBottomInset;
                } else {
                    height = viewFindViewById.getHeight();
                }
                paddingBottom += (int) ((height * itemAlignmentDef.mOffsetPercent) / 100.0f);
            }
            if (view != viewFindViewById) {
                Rect rect = sRect;
                rect.top = paddingBottom;
                ((ViewGroup) view).offsetDescendantRectToMyCoords(viewFindViewById, rect);
                i2 = sRect.top - layoutParams.mTopInset;
            } else {
                i2 = paddingBottom;
            }
            return itemAlignmentDef.isAlignedToTextViewBaseLine() ? i2 + viewFindViewById.getBaseline() : i2;
        }
        if (view.getLayoutDirection() == 1) {
            int opticalWidth = (viewFindViewById == view ? layoutParams.getOpticalWidth(viewFindViewById) : viewFindViewById.getWidth()) - paddingBottom;
            if (itemAlignmentDef.mOffsetWithPadding) {
                float f2 = itemAlignmentDef.mOffsetPercent;
                if (f2 == 0.0f) {
                    opticalWidth -= viewFindViewById.getPaddingRight();
                } else if (f2 == 100.0f) {
                    opticalWidth += viewFindViewById.getPaddingLeft();
                }
            }
            if (itemAlignmentDef.mOffsetPercent != -1.0f) {
                opticalWidth -= (int) (((viewFindViewById == view ? layoutParams.getOpticalWidth(viewFindViewById) : viewFindViewById.getWidth()) * itemAlignmentDef.mOffsetPercent) / 100.0f);
            }
            if (view == viewFindViewById) {
                return opticalWidth;
            }
            Rect rect2 = sRect;
            rect2.right = opticalWidth;
            ((ViewGroup) view).offsetDescendantRectToMyCoords(viewFindViewById, rect2);
            return sRect.right + layoutParams.mRightInset;
        }
        if (itemAlignmentDef.mOffsetWithPadding) {
            float f3 = itemAlignmentDef.mOffsetPercent;
            if (f3 == 0.0f) {
                paddingBottom += viewFindViewById.getPaddingLeft();
            } else if (f3 == 100.0f) {
                paddingBottom -= viewFindViewById.getPaddingRight();
            }
        }
        if (itemAlignmentDef.mOffsetPercent != -1.0f) {
            paddingBottom += (int) (((viewFindViewById == view ? layoutParams.getOpticalWidth(viewFindViewById) : viewFindViewById.getWidth()) * itemAlignmentDef.mOffsetPercent) / 100.0f);
        }
        int i4 = paddingBottom;
        if (view == viewFindViewById) {
            return i4;
        }
        Rect rect3 = sRect;
        rect3.left = i4;
        ((ViewGroup) view).offsetDescendantRectToMyCoords(viewFindViewById, rect3);
        return sRect.left - layoutParams.mLeftInset;
    }
}
