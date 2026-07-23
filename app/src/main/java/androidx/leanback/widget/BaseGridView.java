package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.RestrictTo;
import androidx.leanback.R;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseGridView extends RecyclerView {

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final int FOCUS_SCROLL_ALIGNED = 0;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final int FOCUS_SCROLL_ITEM = 1;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final int FOCUS_SCROLL_PAGE = 2;
    public static final float ITEM_ALIGN_OFFSET_PERCENT_DISABLED = -1.0f;
    public static final int SAVE_ALL_CHILD = 3;
    public static final int SAVE_LIMITED_CHILD = 2;
    public static final int SAVE_NO_CHILD = 0;
    public static final int SAVE_ON_SCREEN_CHILD = 1;
    public static final int WINDOW_ALIGN_BOTH_EDGE = 3;
    public static final int WINDOW_ALIGN_HIGH_EDGE = 2;
    public static final int WINDOW_ALIGN_LOW_EDGE = 1;
    public static final int WINDOW_ALIGN_NO_EDGE = 0;
    public static final float WINDOW_ALIGN_OFFSET_PERCENT_DISABLED = -1.0f;
    private boolean mAnimateChildLayout;
    public RecyclerView.RecyclerListener mChainedRecyclerListener;
    private boolean mHasOverlappingRendering;
    public int mInitialPrefetchItemCount;
    public final GridLayoutManager mLayoutManager;
    private OnKeyInterceptListener mOnKeyInterceptListener;
    private OnMotionInterceptListener mOnMotionInterceptListener;
    private OnTouchInterceptListener mOnTouchInterceptListener;
    private OnUnhandledKeyListener mOnUnhandledKeyListener;
    private RecyclerView.ItemAnimator mSavedItemAnimator;

    public interface OnKeyInterceptListener {
        boolean onInterceptKeyEvent(KeyEvent keyEvent);
    }

    public interface OnMotionInterceptListener {
        boolean onInterceptMotionEvent(MotionEvent motionEvent);
    }

    public interface OnTouchInterceptListener {
        boolean onInterceptTouchEvent(MotionEvent motionEvent);
    }

    public interface OnUnhandledKeyListener {
        boolean onUnhandledKey(KeyEvent keyEvent);
    }

    public BaseGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAnimateChildLayout = true;
        this.mHasOverlappingRendering = true;
        this.mInitialPrefetchItemCount = 4;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this);
        this.mLayoutManager = gridLayoutManager;
        setLayoutManager(gridLayoutManager);
        setPreserveFocusAfterLayout(false);
        setDescendantFocusability(262144);
        setHasFixedSize(true);
        setChildrenDrawingOrderEnabled(true);
        setWillNotDraw(true);
        setOverScrollMode(2);
        ((SimpleItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);
        super.setRecyclerListener(new RecyclerView.RecyclerListener() { // from class: androidx.leanback.widget.BaseGridView.1
            @Override // androidx.recyclerview.widget.RecyclerView.RecyclerListener
            public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
                GridLayoutManager gridLayoutManager2 = BaseGridView.this.mLayoutManager;
                Objects.requireNonNull(gridLayoutManager2);
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != -1) {
                    gridLayoutManager2.mChildrenStates.saveOffscreenView(viewHolder.itemView, adapterPosition);
                }
                RecyclerView.RecyclerListener recyclerListener = BaseGridView.this.mChainedRecyclerListener;
                if (recyclerListener != null) {
                    recyclerListener.onViewRecycled(viewHolder);
                }
            }
        });
    }

    public void addOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener onChildViewHolderSelectedListener) {
        this.mLayoutManager.addOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
    }

    public void animateIn() {
        this.mLayoutManager.slideIn();
    }

    public void animateOut() {
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        int i = gridLayoutManager.mFlag;
        if ((i & 64) != 0) {
            return;
        }
        gridLayoutManager.mFlag = i | 64;
        if (gridLayoutManager.getChildCount() == 0) {
            return;
        }
        if (gridLayoutManager.mOrientation == 1) {
            gridLayoutManager.mBaseGridView.smoothScrollBy(0, gridLayoutManager.getSlideOutDistance(), new AccelerateDecelerateInterpolator());
        } else {
            gridLayoutManager.mBaseGridView.smoothScrollBy(gridLayoutManager.getSlideOutDistance(), 0, new AccelerateDecelerateInterpolator());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchGenericFocusedEvent(MotionEvent motionEvent) {
        OnMotionInterceptListener onMotionInterceptListener = this.mOnMotionInterceptListener;
        if (onMotionInterceptListener == null || !onMotionInterceptListener.onInterceptMotionEvent(motionEvent)) {
            return super.dispatchGenericFocusedEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        OnKeyInterceptListener onKeyInterceptListener = this.mOnKeyInterceptListener;
        if ((onKeyInterceptListener != null && onKeyInterceptListener.onInterceptKeyEvent(keyEvent)) || super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        OnUnhandledKeyListener onUnhandledKeyListener = this.mOnUnhandledKeyListener;
        return onUnhandledKeyListener != null && onUnhandledKeyListener.onUnhandledKey(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        OnTouchInterceptListener onTouchInterceptListener = this.mOnTouchInterceptListener;
        if (onTouchInterceptListener == null || !onTouchInterceptListener.onInterceptTouchEvent(motionEvent)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.View
    public View focusSearch(int i) {
        if (isFocused()) {
            GridLayoutManager gridLayoutManager = this.mLayoutManager;
            View viewFindViewByPosition = gridLayoutManager.findViewByPosition(gridLayoutManager.getSelection());
            if (viewFindViewByPosition != null) {
                return focusSearch(viewFindViewByPosition, i);
            }
        }
        return super.focusSearch(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public int getChildDrawingOrder(int i, int i2) {
        int iIndexOfChild;
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        View viewFindViewByPosition = gridLayoutManager.findViewByPosition(gridLayoutManager.mFocusPosition);
        if (viewFindViewByPosition == null || i2 < (iIndexOfChild = indexOfChild(viewFindViewByPosition))) {
            return i2;
        }
        if (i2 < i - 1) {
            iIndexOfChild = ((iIndexOfChild + i) - 1) - i2;
        }
        return iIndexOfChild;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getExtraLayoutSpace() {
        return this.mLayoutManager.getExtraLayoutSpace();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getFocusScrollStrategy() {
        return this.mLayoutManager.getFocusScrollStrategy();
    }

    @Deprecated
    public int getHorizontalMargin() {
        return this.mLayoutManager.getHorizontalSpacing();
    }

    public int getHorizontalSpacing() {
        return this.mLayoutManager.getHorizontalSpacing();
    }

    public int getInitialPrefetchItemCount() {
        return this.mInitialPrefetchItemCount;
    }

    public int getItemAlignmentOffset() {
        return this.mLayoutManager.getItemAlignmentOffset();
    }

    public float getItemAlignmentOffsetPercent() {
        return this.mLayoutManager.getItemAlignmentOffsetPercent();
    }

    public int getItemAlignmentViewId() {
        return this.mLayoutManager.getItemAlignmentViewId();
    }

    public OnUnhandledKeyListener getOnUnhandledKeyListener() {
        return this.mOnUnhandledKeyListener;
    }

    public final int getSaveChildrenLimitNumber() {
        return this.mLayoutManager.mChildrenStates.getLimitNumber();
    }

    public final int getSaveChildrenPolicy() {
        return this.mLayoutManager.mChildrenStates.getSavePolicy();
    }

    public int getSelectedPosition() {
        return this.mLayoutManager.getSelection();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getSelectedSubPosition() {
        return this.mLayoutManager.getSubSelection();
    }

    @Deprecated
    public int getVerticalMargin() {
        return this.mLayoutManager.getVerticalSpacing();
    }

    public int getVerticalSpacing() {
        return this.mLayoutManager.getVerticalSpacing();
    }

    public void getViewSelectedOffsets(View view, int[] iArr) {
        this.mLayoutManager.getViewSelectedOffsets(view, iArr);
    }

    public int getWindowAlignment() {
        return this.mLayoutManager.getWindowAlignment();
    }

    public int getWindowAlignmentOffset() {
        return this.mLayoutManager.getWindowAlignmentOffset();
    }

    public float getWindowAlignmentOffsetPercent() {
        return this.mLayoutManager.getWindowAlignmentOffsetPercent();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return this.mHasOverlappingRendering;
    }

    public boolean hasPreviousViewInSameRow(int i) {
        return this.mLayoutManager.hasPreviousViewInSameRow(i);
    }

    public final void initBaseGridViewAttributes(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.lbBaseGridView);
        this.mLayoutManager.setFocusOutAllowed(typedArrayObtainStyledAttributes.getBoolean(R.styleable.lbBaseGridView_focusOutFront, false), typedArrayObtainStyledAttributes.getBoolean(R.styleable.lbBaseGridView_focusOutEnd, false));
        this.mLayoutManager.setFocusOutSideAllowed(typedArrayObtainStyledAttributes.getBoolean(R.styleable.lbBaseGridView_focusOutSideStart, true), typedArrayObtainStyledAttributes.getBoolean(R.styleable.lbBaseGridView_focusOutSideEnd, true));
        this.mLayoutManager.setVerticalSpacing(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.lbBaseGridView_android_verticalSpacing, typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.lbBaseGridView_verticalMargin, 0)));
        this.mLayoutManager.setHorizontalSpacing(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.lbBaseGridView_android_horizontalSpacing, typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.lbBaseGridView_horizontalMargin, 0)));
        int i = R.styleable.lbBaseGridView_android_gravity;
        if (typedArrayObtainStyledAttributes.hasValue(i)) {
            setGravity(typedArrayObtainStyledAttributes.getInt(i, 0));
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public boolean isChildLayoutAnimated() {
        return this.mAnimateChildLayout;
    }

    public final boolean isChildrenDrawingOrderEnabledInternal() {
        return isChildrenDrawingOrderEnabled();
    }

    public boolean isFocusDrawingOrderEnabled() {
        return super.isChildrenDrawingOrderEnabled();
    }

    public final boolean isFocusSearchDisabled() {
        return (this.mLayoutManager.mFlag & 32768) != 0;
    }

    public boolean isItemAlignmentOffsetWithPadding() {
        return this.mLayoutManager.isItemAlignmentOffsetWithPadding();
    }

    public boolean isScrollEnabled() {
        return this.mLayoutManager.isScrollEnabled();
    }

    public boolean isWindowAlignmentPreferKeyLineOverHighEdge() {
        return this.mLayoutManager.mWindowAlignment.mainAxis().isPreferKeylineOverHighEdge();
    }

    public boolean isWindowAlignmentPreferKeyLineOverLowEdge() {
        return this.mLayoutManager.mWindowAlignment.mainAxis().isPreferKeylineOverLowEdge();
    }

    @Override // android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        Objects.requireNonNull(gridLayoutManager);
        if (!z) {
            return;
        }
        int i2 = gridLayoutManager.mFocusPosition;
        while (true) {
            View viewFindViewByPosition = gridLayoutManager.findViewByPosition(i2);
            if (viewFindViewByPosition == null) {
                return;
            }
            if (viewFindViewByPosition.getVisibility() == 0 && viewFindViewByPosition.hasFocusable()) {
                viewFindViewByPosition.requestFocus();
                return;
            }
            i2++;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        return this.mLayoutManager.gridOnRequestFocusInDescendants(this, i, rect);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        this.mLayoutManager.onRtlPropertiesChanged(i);
    }

    public void removeOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener onChildViewHolderSelectedListener) {
        this.mLayoutManager.removeOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void scrollToPosition(int i) {
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if ((gridLayoutManager.mFlag & 64) != 0) {
            gridLayoutManager.setSelectionWithSub(i, 0, 0);
        } else {
            super.scrollToPosition(i);
        }
    }

    public void setAnimateChildLayout(boolean z) {
        if (this.mAnimateChildLayout != z) {
            this.mAnimateChildLayout = z;
            if (z) {
                super.setItemAnimator(this.mSavedItemAnimator);
            } else {
                this.mSavedItemAnimator = getItemAnimator();
                super.setItemAnimator(null);
            }
        }
    }

    public void setChildrenVisibility(int i) {
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        gridLayoutManager.mChildVisibility = i;
        if (i != -1) {
            int childCount = gridLayoutManager.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                gridLayoutManager.getChildAt(i2).setVisibility(gridLayoutManager.mChildVisibility);
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setExtraLayoutSpace(int i) {
        this.mLayoutManager.setExtraLayoutSpace(i);
    }

    public void setFocusDrawingOrderEnabled(boolean z) {
        super.setChildrenDrawingOrderEnabled(z);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setFocusScrollStrategy(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException("Invalid scrollStrategy");
        }
        this.mLayoutManager.setFocusScrollStrategy(i);
        requestLayout();
    }

    public final void setFocusSearchDisabled(boolean z) {
        setDescendantFocusability(z ? 393216 : 262144);
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        gridLayoutManager.mFlag = (z ? 32768 : 0) | (gridLayoutManager.mFlag & (-32769));
    }

    public void setGravity(int i) {
        this.mLayoutManager.setGravity(i);
        requestLayout();
    }

    public void setHasOverlappingRendering(boolean z) {
        this.mHasOverlappingRendering = z;
    }

    @Deprecated
    public void setHorizontalMargin(int i) {
        setHorizontalSpacing(i);
    }

    public void setHorizontalSpacing(int i) {
        this.mLayoutManager.setHorizontalSpacing(i);
        requestLayout();
    }

    public void setInitialPrefetchItemCount(int i) {
        this.mInitialPrefetchItemCount = i;
    }

    public void setItemAlignmentOffset(int i) {
        this.mLayoutManager.setItemAlignmentOffset(i);
        requestLayout();
    }

    public void setItemAlignmentOffsetPercent(float f) {
        this.mLayoutManager.setItemAlignmentOffsetPercent(f);
        requestLayout();
    }

    public void setItemAlignmentOffsetWithPadding(boolean z) {
        this.mLayoutManager.setItemAlignmentOffsetWithPadding(z);
        requestLayout();
    }

    public void setItemAlignmentViewId(int i) {
        this.mLayoutManager.setItemAlignmentViewId(i);
    }

    @Deprecated
    public void setItemMargin(int i) {
        setItemSpacing(i);
    }

    public void setItemSpacing(int i) {
        this.mLayoutManager.setItemSpacing(i);
        requestLayout();
    }

    public void setLayoutEnabled(boolean z) {
        this.mLayoutManager.setLayoutEnabled(z);
    }

    public void setOnChildLaidOutListener(OnChildLaidOutListener onChildLaidOutListener) {
        this.mLayoutManager.mChildLaidOutListener = onChildLaidOutListener;
    }

    public void setOnChildSelectedListener(OnChildSelectedListener onChildSelectedListener) {
        this.mLayoutManager.setOnChildSelectedListener(onChildSelectedListener);
    }

    public void setOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener onChildViewHolderSelectedListener) {
        this.mLayoutManager.setOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
    }

    public void setOnKeyInterceptListener(OnKeyInterceptListener onKeyInterceptListener) {
        this.mOnKeyInterceptListener = onKeyInterceptListener;
    }

    public void setOnMotionInterceptListener(OnMotionInterceptListener onMotionInterceptListener) {
        this.mOnMotionInterceptListener = onMotionInterceptListener;
    }

    public void setOnTouchInterceptListener(OnTouchInterceptListener onTouchInterceptListener) {
        this.mOnTouchInterceptListener = onTouchInterceptListener;
    }

    public void setOnUnhandledKeyListener(OnUnhandledKeyListener onUnhandledKeyListener) {
        this.mOnUnhandledKeyListener = onUnhandledKeyListener;
    }

    public void setPruneChild(boolean z) {
        this.mLayoutManager.setPruneChild(z);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setRecyclerListener(RecyclerView.RecyclerListener recyclerListener) {
        this.mChainedRecyclerListener = recyclerListener;
    }

    public final void setSaveChildrenLimitNumber(int i) {
        this.mLayoutManager.mChildrenStates.setLimitNumber(i);
    }

    public final void setSaveChildrenPolicy(int i) {
        this.mLayoutManager.mChildrenStates.setSavePolicy(i);
    }

    public void setScrollEnabled(boolean z) {
        this.mLayoutManager.setScrollEnabled(z);
    }

    public void setSelectedPosition(int i) {
        this.mLayoutManager.setSelection(i, 0);
    }

    public void setSelectedPositionSmooth(int i) {
        this.mLayoutManager.setSelectionSmooth(i);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setSelectedPositionSmoothWithSub(int i, int i2) {
        this.mLayoutManager.setSelectionSmoothWithSub(i, i2);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setSelectedPositionWithSub(int i, int i2) {
        this.mLayoutManager.setSelectionWithSub(i, i2, 0);
    }

    @Deprecated
    public void setVerticalMargin(int i) {
        setVerticalSpacing(i);
    }

    public void setVerticalSpacing(int i) {
        this.mLayoutManager.setVerticalSpacing(i);
        requestLayout();
    }

    public void setWindowAlignment(int i) {
        this.mLayoutManager.setWindowAlignment(i);
        requestLayout();
    }

    public void setWindowAlignmentOffset(int i) {
        this.mLayoutManager.setWindowAlignmentOffset(i);
        requestLayout();
    }

    public void setWindowAlignmentOffsetPercent(float f) {
        this.mLayoutManager.setWindowAlignmentOffsetPercent(f);
        requestLayout();
    }

    public void setWindowAlignmentPreferKeyLineOverHighEdge(boolean z) {
        this.mLayoutManager.mWindowAlignment.mainAxis().setPreferKeylineOverHighEdge(z);
        requestLayout();
    }

    public void setWindowAlignmentPreferKeyLineOverLowEdge(boolean z) {
        this.mLayoutManager.mWindowAlignment.mainAxis().setPreferKeylineOverLowEdge(z);
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void smoothScrollToPosition(int i) {
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if ((gridLayoutManager.mFlag & 64) != 0) {
            gridLayoutManager.setSelectionWithSub(i, 0, 0);
        } else {
            super.smoothScrollToPosition(i);
        }
    }

    public void setSelectedPosition(int i, int i2) {
        this.mLayoutManager.setSelection(i, i2);
    }

    public void setSelectedPositionSmooth(final int i, final ViewHolderTask viewHolderTask) {
        if (viewHolderTask != null) {
            RecyclerView.ViewHolder viewHolderFindViewHolderForPosition = findViewHolderForPosition(i);
            if (viewHolderFindViewHolderForPosition == null || hasPendingAdapterUpdates()) {
                addOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: androidx.leanback.widget.BaseGridView.2
                    @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                    public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i2, int i3) {
                        if (i2 == i) {
                            BaseGridView.this.removeOnChildViewHolderSelectedListener(this);
                            viewHolderTask.run(viewHolder);
                        }
                    }
                });
            } else {
                viewHolderTask.run(viewHolderFindViewHolderForPosition);
            }
        }
        setSelectedPositionSmooth(i);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setSelectedPositionWithSub(int i, int i2, int i3) {
        this.mLayoutManager.setSelectionWithSub(i, i2, i3);
    }

    public void setSelectedPosition(final int i, final ViewHolderTask viewHolderTask) {
        if (viewHolderTask != null) {
            RecyclerView.ViewHolder viewHolderFindViewHolderForPosition = findViewHolderForPosition(i);
            if (viewHolderFindViewHolderForPosition != null && !hasPendingAdapterUpdates()) {
                viewHolderTask.run(viewHolderFindViewHolderForPosition);
            } else {
                addOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: androidx.leanback.widget.BaseGridView.3
                    @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                    public void onChildViewHolderSelectedAndPositioned(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i2, int i3) {
                        if (i2 == i) {
                            BaseGridView.this.removeOnChildViewHolderSelectedListener(this);
                            viewHolderTask.run(viewHolder);
                        }
                    }
                });
            }
        }
        setSelectedPosition(i);
    }
}
