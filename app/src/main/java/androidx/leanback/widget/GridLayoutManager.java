package androidx.leanback.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.FocusFinder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.VisibleForTesting;
import androidx.collection.CircularIntArray;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
final class GridLayoutManager extends RecyclerView.LayoutManager {
    private static final int NEXT_ITEM = 1;
    private static final int NEXT_ROW = 3;
    private static final int PREV_ITEM = 0;
    private static final int PREV_ROW = 2;
    private static final String TAG = "GridLayoutManager";
    private static final Rect sTempRect = new Rect();
    public static int[] sTwoInts = new int[2];
    public final BaseGridView mBaseGridView;
    public GridLinearSmoothScroller mCurrentSmoothScroller;
    public int[] mDisappearingPositions;
    private int mExtraLayoutSpace;
    public int mExtraLayoutSpaceInPreLayout;
    private FacetProviderAdapter mFacetProviderAdapter;
    private int mFixedRowSizeSecondary;
    public Grid mGrid;
    private int mHorizontalSpacing;
    private int mMaxSizeSecondary;
    public int mNumRows;
    public PendingMoveSmoothScroller mPendingMoveSmoothScroller;
    public int mPositionDeltaInPreLayout;
    private int mPrimaryScrollExtra;
    public RecyclerView.Recycler mRecycler;
    private int[] mRowSizeSecondary;
    private int mRowSizeSecondaryRequested;
    public int mScrollOffsetSecondary;
    private int mSizePrimary;
    private int mSpacingPrimary;
    private int mSpacingSecondary;
    public RecyclerView.State mState;
    private int mVerticalSpacing;
    public int mMaxPendingMoves = 10;
    public int mOrientation = 0;
    private OrientationHelper mOrientationHelper = OrientationHelper.createHorizontalHelper(this);
    public final SparseIntArray mPositionToRowInPostLayout = new SparseIntArray();
    public int mFlag = 221696;
    private OnChildSelectedListener mChildSelectedListener = null;
    private ArrayList<OnChildViewHolderSelectedListener> mChildViewHolderSelectedListeners = null;
    public OnChildLaidOutListener mChildLaidOutListener = null;
    public int mFocusPosition = -1;
    public int mSubFocusPosition = 0;
    private int mFocusPositionOffset = 0;
    private int mGravity = 8388659;
    private int mNumRowsRequested = 1;
    private int mFocusScrollStrategy = 0;
    public final WindowAlignment mWindowAlignment = new WindowAlignment();
    private final ItemAlignment mItemAlignment = new ItemAlignment();
    private int[] mMeasuredDimension = new int[2];
    public final ViewsStateBundle mChildrenStates = new ViewsStateBundle();
    private final Runnable mRequestLayoutRunnable = new Runnable() { // from class: androidx.leanback.widget.GridLayoutManager.1
        @Override // java.lang.Runnable
        public void run() {
            GridLayoutManager.this.requestLayout();
        }
    };
    private Grid.Provider mGridProvider = new Grid.Provider() { // from class: androidx.leanback.widget.GridLayoutManager.2
        @Override // androidx.leanback.widget.Grid.Provider
        public void addItem(Object obj, int i, int i2, int i3, int i4) {
            int i5;
            int i6;
            PendingMoveSmoothScroller pendingMoveSmoothScroller;
            View view = (View) obj;
            if (i4 == Integer.MIN_VALUE || i4 == Integer.MAX_VALUE) {
                i4 = !GridLayoutManager.this.mGrid.isReversedFlow() ? GridLayoutManager.this.mWindowAlignment.mainAxis().getPaddingMin() : GridLayoutManager.this.mWindowAlignment.mainAxis().getSize() - GridLayoutManager.this.mWindowAlignment.mainAxis().getPaddingMax();
            }
            if (!GridLayoutManager.this.mGrid.isReversedFlow()) {
                i6 = i2 + i4;
                i5 = i4;
            } else {
                i5 = i4 - i2;
                i6 = i4;
            }
            int paddingMin = GridLayoutManager.this.mWindowAlignment.secondAxis().getPaddingMin() + GridLayoutManager.this.getRowStartSecondary(i3);
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            int i7 = paddingMin - gridLayoutManager.mScrollOffsetSecondary;
            gridLayoutManager.mChildrenStates.loadView(view, i);
            GridLayoutManager.this.layoutChild(i3, view, i5, i6, i7);
            if (!GridLayoutManager.this.mState.isPreLayout()) {
                GridLayoutManager.this.updateScrollLimits();
            }
            GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
            if ((gridLayoutManager2.mFlag & 3) != 1 && (pendingMoveSmoothScroller = gridLayoutManager2.mPendingMoveSmoothScroller) != null) {
                pendingMoveSmoothScroller.consumePendingMovesAfterLayout();
            }
            GridLayoutManager gridLayoutManager3 = GridLayoutManager.this;
            if (gridLayoutManager3.mChildLaidOutListener != null) {
                RecyclerView.ViewHolder childViewHolder = gridLayoutManager3.mBaseGridView.getChildViewHolder(view);
                GridLayoutManager gridLayoutManager4 = GridLayoutManager.this;
                gridLayoutManager4.mChildLaidOutListener.onChildLaidOut(gridLayoutManager4.mBaseGridView, view, i, childViewHolder == null ? -1L : childViewHolder.getItemId());
            }
        }

        @Override // androidx.leanback.widget.Grid.Provider
        public int createItem(int i, boolean z, Object[] objArr, boolean z2) {
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            View viewForPosition = gridLayoutManager.mRecycler.getViewForPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
            LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
            layoutParams.setItemAlignmentFacet((ItemAlignmentFacet) GridLayoutManager.this.getFacet(GridLayoutManager.this.mBaseGridView.getChildViewHolder(viewForPosition)));
            if (!layoutParams.isItemRemoved()) {
                if (z2) {
                    if (z) {
                        GridLayoutManager.this.addDisappearingView(viewForPosition);
                    } else {
                        GridLayoutManager.this.addDisappearingView(viewForPosition, 0);
                    }
                } else if (z) {
                    GridLayoutManager.this.addView(viewForPosition);
                } else {
                    GridLayoutManager.this.addView(viewForPosition, 0);
                }
                int i2 = GridLayoutManager.this.mChildVisibility;
                if (i2 != -1) {
                    viewForPosition.setVisibility(i2);
                }
                PendingMoveSmoothScroller pendingMoveSmoothScroller = GridLayoutManager.this.mPendingMoveSmoothScroller;
                if (pendingMoveSmoothScroller != null) {
                    pendingMoveSmoothScroller.consumePendingMovesBeforeLayout();
                }
                int subPositionByView = GridLayoutManager.this.getSubPositionByView(viewForPosition, viewForPosition.findFocus());
                GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                int i3 = gridLayoutManager2.mFlag;
                if ((i3 & 3) != 1) {
                    if (i == gridLayoutManager2.mFocusPosition && subPositionByView == gridLayoutManager2.mSubFocusPosition && gridLayoutManager2.mPendingMoveSmoothScroller == null) {
                        gridLayoutManager2.dispatchChildSelected();
                    }
                } else if ((i3 & 4) == 0) {
                    int i4 = i3 & 16;
                    if (i4 == 0 && i == gridLayoutManager2.mFocusPosition && subPositionByView == gridLayoutManager2.mSubFocusPosition) {
                        gridLayoutManager2.dispatchChildSelected();
                    } else if (i4 != 0 && i >= gridLayoutManager2.mFocusPosition && viewForPosition.hasFocusable()) {
                        GridLayoutManager gridLayoutManager3 = GridLayoutManager.this;
                        gridLayoutManager3.mFocusPosition = i;
                        gridLayoutManager3.mSubFocusPosition = subPositionByView;
                        gridLayoutManager3.mFlag &= -17;
                        gridLayoutManager3.dispatchChildSelected();
                    }
                }
                GridLayoutManager.this.measureChild(viewForPosition);
            }
            objArr[0] = viewForPosition;
            GridLayoutManager gridLayoutManager4 = GridLayoutManager.this;
            return gridLayoutManager4.mOrientation == 0 ? gridLayoutManager4.getDecoratedMeasuredWidthWithMargin(viewForPosition) : gridLayoutManager4.getDecoratedMeasuredHeightWithMargin(viewForPosition);
        }

        @Override // androidx.leanback.widget.Grid.Provider
        public int getCount() {
            return GridLayoutManager.this.mState.getItemCount() + GridLayoutManager.this.mPositionDeltaInPreLayout;
        }

        @Override // androidx.leanback.widget.Grid.Provider
        public int getEdge(int i) {
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            View viewFindViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
            GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
            return (gridLayoutManager2.mFlag & 262144) != 0 ? gridLayoutManager2.getViewMax(viewFindViewByPosition) : gridLayoutManager2.getViewMin(viewFindViewByPosition);
        }

        @Override // androidx.leanback.widget.Grid.Provider
        public int getMinIndex() {
            return GridLayoutManager.this.mPositionDeltaInPreLayout;
        }

        @Override // androidx.leanback.widget.Grid.Provider
        public int getSize(int i) {
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            return gridLayoutManager.getViewPrimarySize(gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout));
        }

        @Override // androidx.leanback.widget.Grid.Provider
        public void removeItem(int i) {
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            View viewFindViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
            GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
            if ((gridLayoutManager2.mFlag & 3) == 1) {
                gridLayoutManager2.detachAndScrapView(viewFindViewByPosition, gridLayoutManager2.mRecycler);
            } else {
                gridLayoutManager2.removeAndRecycleView(viewFindViewByPosition, gridLayoutManager2.mRecycler);
            }
        }
    };
    public int mChildVisibility = -1;

    public abstract class GridLinearSmoothScroller extends LinearSmoothScroller {
        public boolean mSkipOnStopInternal;

        public GridLinearSmoothScroller() {
            super(GridLayoutManager.this.mBaseGridView.getContext());
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public final int calculateTimeForScrolling(int i) {
            int iCalculateTimeForScrolling = super.calculateTimeForScrolling(i);
            if (GridLayoutManager.this.mWindowAlignment.mainAxis().getSize() <= 0) {
                return iCalculateTimeForScrolling;
            }
            float size = (30.0f / GridLayoutManager.this.mWindowAlignment.mainAxis().getSize()) * i;
            return ((float) iCalculateTimeForScrolling) < size ? (int) size : iCalculateTimeForScrolling;
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
        public final void onStop() {
            this.mInterimTargetDy = 0;
            this.mInterimTargetDx = 0;
            this.mTargetVector = null;
            if (!this.mSkipOnStopInternal) {
                onStopInternal();
            }
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            if (gridLayoutManager.mCurrentSmoothScroller == this) {
                gridLayoutManager.mCurrentSmoothScroller = null;
            }
            if (gridLayoutManager.mPendingMoveSmoothScroller == this) {
                gridLayoutManager.mPendingMoveSmoothScroller = null;
            }
        }

        public void onStopInternal() {
            View viewFindViewByPosition = findViewByPosition(getTargetPosition());
            if (viewFindViewByPosition == null) {
                if (getTargetPosition() >= 0) {
                    GridLayoutManager.this.scrollToSelection(getTargetPosition(), 0, false, 0);
                    return;
                }
                return;
            }
            if (GridLayoutManager.this.mFocusPosition != getTargetPosition()) {
                GridLayoutManager.this.mFocusPosition = getTargetPosition();
            }
            if (GridLayoutManager.this.hasFocus()) {
                GridLayoutManager.this.mFlag |= 32;
                viewFindViewByPosition.requestFocus();
                GridLayoutManager.this.mFlag &= -33;
            }
            GridLayoutManager.this.dispatchChildSelected();
            GridLayoutManager.this.dispatchChildSelectedAndPositioned();
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
        public final void onTargetFound(View view, RecyclerView.SmoothScroller.Action action) {
            int i;
            int i2;
            if (GridLayoutManager.this.getScrollPosition(view, GridLayoutManager.sTwoInts)) {
                if (GridLayoutManager.this.mOrientation == 0) {
                    int[] iArr = GridLayoutManager.sTwoInts;
                    i2 = iArr[0];
                    i = iArr[1];
                } else {
                    int[] iArr2 = GridLayoutManager.sTwoInts;
                    int i3 = iArr2[1];
                    i = iArr2[0];
                    i2 = i3;
                }
                action.update(i2, i, calculateTimeForDeceleration((int) Math.sqrt((i * i) + (i2 * i2))), this.mDecelerateInterpolator);
            }
        }
    }

    public static final class LayoutParams extends RecyclerView.LayoutParams {
        private int[] mAlignMultiple;
        private int mAlignX;
        private int mAlignY;
        private ItemAlignmentFacet mAlignmentFacet;
        public int mBottomInset;
        public int mLeftInset;
        public int mRightInset;
        public int mTopInset;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public final void calculateItemAlignments(int i, View view) {
            ItemAlignmentFacet.ItemAlignmentDef[] alignmentDefs = this.mAlignmentFacet.getAlignmentDefs();
            int[] iArr = this.mAlignMultiple;
            if (iArr == null || iArr.length != alignmentDefs.length) {
                this.mAlignMultiple = new int[alignmentDefs.length];
            }
            for (int i2 = 0; i2 < alignmentDefs.length; i2++) {
                this.mAlignMultiple[i2] = ItemAlignmentFacetHelper.getAlignmentPosition(view, alignmentDefs[i2], i);
            }
            if (i == 0) {
                this.mAlignX = this.mAlignMultiple[0];
            } else {
                this.mAlignY = this.mAlignMultiple[0];
            }
        }

        public final int[] getAlignMultiple() {
            return this.mAlignMultiple;
        }

        public final int getAlignX() {
            return this.mAlignX;
        }

        public final int getAlignY() {
            return this.mAlignY;
        }

        public final ItemAlignmentFacet getItemAlignmentFacet() {
            return this.mAlignmentFacet;
        }

        public final int getOpticalWidth(View view) {
            return (view.getWidth() - this.mLeftInset) - this.mRightInset;
        }

        public final void setAlignX(int i) {
            this.mAlignX = i;
        }

        public final void setAlignY(int i) {
            this.mAlignY = i;
        }

        public final void setItemAlignmentFacet(ItemAlignmentFacet itemAlignmentFacet) {
            this.mAlignmentFacet = itemAlignmentFacet;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((RecyclerView.LayoutParams) layoutParams);
        }
    }

    @VisibleForTesting
    public static class OnLayoutCompleteListener {
        public void onLayoutCompleted(RecyclerView.State state) {
        }
    }

    public final class PendingMoveSmoothScroller extends GridLinearSmoothScroller {
        private int mPendingMoves;
        private final boolean mStaggeredGrid;

        public PendingMoveSmoothScroller(int i, boolean z) {
            super();
            this.mPendingMoves = i;
            this.mStaggeredGrid = z;
            setTargetPosition(-2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
        public PointF computeScrollVectorForPosition(int i) {
            int i2 = this.mPendingMoves;
            if (i2 == 0) {
                return null;
            }
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            int i3 = ((gridLayoutManager.mFlag & 262144) == 0 ? i2 >= 0 : i2 <= 0) ? 1 : -1;
            return gridLayoutManager.mOrientation == 0 ? new PointF(i3, 0.0f) : new PointF(0.0f, i3);
        }

        public final void consumePendingMovesAfterLayout() {
            int i;
            if (this.mStaggeredGrid && (i = this.mPendingMoves) != 0) {
                this.mPendingMoves = GridLayoutManager.this.processSelectionMoves(true, i);
            }
            int i2 = this.mPendingMoves;
            if (i2 == 0 || ((i2 > 0 && GridLayoutManager.this.hasCreatedLastItem()) || (this.mPendingMoves < 0 && GridLayoutManager.this.hasCreatedFirstItem()))) {
                setTargetPosition(GridLayoutManager.this.mFocusPosition);
                stop();
            }
        }

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x004a -> B:28:0x004e). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:13:0x001f
            	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
            	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
            	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
            */
        public final void consumePendingMovesBeforeLayout() {
            /*
                r5 = this;
                boolean r0 = r5.mStaggeredGrid
                if (r0 != 0) goto L72
                int r0 = r5.mPendingMoves
                if (r0 != 0) goto La
                goto L72
            La:
                r1 = 0
                if (r0 <= 0) goto L15
                androidx.leanback.widget.GridLayoutManager r0 = androidx.leanback.widget.GridLayoutManager.this
                int r2 = r0.mFocusPosition
                int r0 = r0.mNumRows
                r3 = r5
                goto L4e
            L15:
                androidx.leanback.widget.GridLayoutManager r0 = androidx.leanback.widget.GridLayoutManager.this
                int r2 = r0.mFocusPosition
                int r0 = r0.mNumRows
                r3 = r5
            L1c:
                int r2 = r2 - r0
            L1d:
                int r0 = r3.mPendingMoves
                if (r0 == 0) goto L55
                android.view.View r0 = r3.findViewByPosition(r2)
                if (r0 != 0) goto L28
                goto L55
            L28:
                androidx.leanback.widget.GridLayoutManager r4 = androidx.leanback.widget.GridLayoutManager.this
                boolean r4 = r4.canScrollTo(r0)
                if (r4 != 0) goto L31
                goto L46
            L31:
                androidx.leanback.widget.GridLayoutManager r1 = androidx.leanback.widget.GridLayoutManager.this
                r1.mFocusPosition = r2
                r4 = 0
                r1.mSubFocusPosition = r4
                int r1 = r3.mPendingMoves
                if (r1 <= 0) goto L41
                int r1 = r1 + (-1)
                r3.mPendingMoves = r1
                goto L45
            L41:
                int r1 = r1 + 1
                r3.mPendingMoves = r1
            L45:
                r1 = r0
            L46:
                int r0 = r3.mPendingMoves
                if (r0 <= 0) goto L50
                androidx.leanback.widget.GridLayoutManager r0 = androidx.leanback.widget.GridLayoutManager.this
                int r0 = r0.mNumRows
            L4e:
                int r2 = r2 + r0
                goto L1d
            L50:
                androidx.leanback.widget.GridLayoutManager r0 = androidx.leanback.widget.GridLayoutManager.this
                int r0 = r0.mNumRows
                goto L1c
            L55:
                if (r1 == 0) goto L72
                androidx.leanback.widget.GridLayoutManager r0 = androidx.leanback.widget.GridLayoutManager.this
                boolean r0 = r0.hasFocus()
                if (r0 == 0) goto L72
                androidx.leanback.widget.GridLayoutManager r0 = androidx.leanback.widget.GridLayoutManager.this
                int r2 = r0.mFlag
                r2 = r2 | 32
                r0.mFlag = r2
                r1.requestFocus()
                androidx.leanback.widget.GridLayoutManager r0 = androidx.leanback.widget.GridLayoutManager.this
                int r1 = r0.mFlag
                r1 = r1 & (-33)
                r0.mFlag = r1
            L72:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.PendingMoveSmoothScroller.consumePendingMovesBeforeLayout():void");
        }

        public final void decreasePendingMoves() {
            int i = this.mPendingMoves;
            if (i > (-GridLayoutManager.this.mMaxPendingMoves)) {
                this.mPendingMoves = i - 1;
            }
        }

        public final void increasePendingMoves() {
            int i = this.mPendingMoves;
            if (i < GridLayoutManager.this.mMaxPendingMoves) {
                this.mPendingMoves = i + 1;
            }
        }

        @Override // androidx.leanback.widget.GridLayoutManager.GridLinearSmoothScroller
        public final void onStopInternal() {
            super.onStopInternal();
            this.mPendingMoves = 0;
            View viewFindViewByPosition = findViewByPosition(getTargetPosition());
            if (viewFindViewByPosition != null) {
                GridLayoutManager.this.scrollToView(viewFindViewByPosition, true);
            }
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public final void updateActionForInterimTarget(RecyclerView.SmoothScroller.Action action) {
            if (this.mPendingMoves == 0) {
                return;
            }
            super.updateActionForInterimTarget(action);
        }
    }

    public GridLayoutManager(BaseGridView baseGridView) {
        this.mBaseGridView = baseGridView;
        setItemPrefetchEnabled(false);
    }

    private boolean appendOneColumnVisibleItems() {
        return this.mGrid.appendOneColumnVisibleItems();
    }

    private void appendVisibleItems() {
        this.mGrid.appendVisibleItems((this.mFlag & 262144) != 0 ? (-this.mExtraLayoutSpace) - this.mExtraLayoutSpaceInPreLayout : this.mSizePrimary + this.mExtraLayoutSpace + this.mExtraLayoutSpaceInPreLayout);
    }

    private void discardLayoutInfo() {
        this.mGrid = null;
        this.mRowSizeSecondary = null;
        this.mFlag &= -1025;
    }

    private void fastRelayout() {
        Grid.Location location;
        int childCount = getChildCount();
        int firstVisibleIndex = this.mGrid.getFirstVisibleIndex();
        this.mFlag &= -9;
        boolean z = false;
        int i = 0;
        while (i < childCount) {
            View childAt = getChildAt(i);
            if (firstVisibleIndex == getAdapterPositionByView(childAt) && (location = this.mGrid.getLocation(firstVisibleIndex)) != null) {
                int paddingMin = (this.mWindowAlignment.secondAxis().getPaddingMin() + getRowStartSecondary(location.row)) - this.mScrollOffsetSecondary;
                int viewMin = getViewMin(childAt);
                Rect rect = sTempRect;
                getDecoratedBoundsWithMargins(childAt, rect);
                int iWidth = this.mOrientation == 0 ? rect.width() : rect.height();
                if (((LayoutParams) childAt.getLayoutParams()).viewNeedsUpdate()) {
                    this.mFlag |= 8;
                    detachAndScrapView(childAt, this.mRecycler);
                    childAt = this.mRecycler.getViewForPosition(firstVisibleIndex);
                    addView(childAt, i);
                }
                View view = childAt;
                measureChild(view);
                int decoratedMeasuredWidthWithMargin = this.mOrientation == 0 ? getDecoratedMeasuredWidthWithMargin(view) : getDecoratedMeasuredHeightWithMargin(view);
                layoutChild(location.row, view, viewMin, viewMin + decoratedMeasuredWidthWithMargin, paddingMin);
                if (iWidth == decoratedMeasuredWidthWithMargin) {
                    i++;
                    firstVisibleIndex++;
                }
            }
            z = true;
        }
        if (z) {
            int lastVisibleIndex = this.mGrid.getLastVisibleIndex();
            for (int i2 = childCount - 1; i2 >= i; i2--) {
                detachAndScrapView(getChildAt(i2), this.mRecycler);
            }
            this.mGrid.invalidateItemsAfter(firstVisibleIndex);
            if ((this.mFlag & 65536) != 0) {
                appendVisibleItems();
                int i3 = this.mFocusPosition;
                if (i3 >= 0 && i3 <= lastVisibleIndex) {
                    while (this.mGrid.getLastVisibleIndex() < this.mFocusPosition) {
                        this.mGrid.appendOneColumnVisibleItems();
                    }
                }
            } else {
                while (this.mGrid.appendOneColumnVisibleItems() && this.mGrid.getLastVisibleIndex() < lastVisibleIndex) {
                }
            }
        }
        updateScrollLimits();
        updateSecondaryScrollLimits();
    }

    private int findImmediateChildIndex(View view) {
        View viewFindContainingItemView;
        BaseGridView baseGridView = this.mBaseGridView;
        if (baseGridView == null || view == baseGridView || (viewFindContainingItemView = findContainingItemView(view)) == null) {
            return -1;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i) == viewFindContainingItemView) {
                return i;
            }
        }
        return -1;
    }

    private void focusToViewInLayout(boolean z, boolean z2, int i, int i2) {
        View viewFindViewByPosition = findViewByPosition(this.mFocusPosition);
        if (viewFindViewByPosition != null && z2) {
            scrollToView(viewFindViewByPosition, viewFindViewByPosition.findFocus(), false, i, i2);
        }
        if (viewFindViewByPosition != null && z && !viewFindViewByPosition.hasFocus()) {
            viewFindViewByPosition.requestFocus();
            return;
        }
        if (z || this.mBaseGridView.hasFocus()) {
            return;
        }
        if (viewFindViewByPosition == null || !viewFindViewByPosition.hasFocusable()) {
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                viewFindViewByPosition = getChildAt(i3);
                if (viewFindViewByPosition != null && viewFindViewByPosition.hasFocusable()) {
                    this.mBaseGridView.focusableViewAvailable(viewFindViewByPosition);
                    break;
                }
            }
        } else {
            this.mBaseGridView.focusableViewAvailable(viewFindViewByPosition);
        }
        View view = viewFindViewByPosition;
        if (z2 && view != null && view.hasFocus()) {
            scrollToView(view, view.findFocus(), false, i, i2);
        }
    }

    private void forceRequestLayout() {
        ViewCompat.postOnAnimation(this.mBaseGridView, this.mRequestLayoutRunnable);
    }

    private int getAdapterPositionByIndex(int i) {
        return getAdapterPositionByView(getChildAt(i));
    }

    private int getAdapterPositionByView(View view) {
        LayoutParams layoutParams;
        if (view == null || (layoutParams = (LayoutParams) view.getLayoutParams()) == null || layoutParams.isItemRemoved()) {
            return -1;
        }
        return layoutParams.getViewAdapterPosition();
    }

    private int getAdjustedPrimaryAlignedScrollDistance(int i, View view, View view2) {
        int subPositionByView = getSubPositionByView(view, view2);
        if (subPositionByView == 0) {
            return i;
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return i + (layoutParams.getAlignMultiple()[subPositionByView] - layoutParams.getAlignMultiple()[0]);
    }

    private int getAdjustedViewCenter(View view) {
        View viewFindFocus;
        return (!view.hasFocus() || (viewFindFocus = view.findFocus()) == null || viewFindFocus == view) ? getViewCenter(view) : getAdjustedPrimaryAlignedScrollDistance(getViewCenter(view), view, viewFindFocus);
    }

    private boolean getAlignedPosition(View view, View view2, int[] iArr) {
        int primaryAlignedScrollDistance = getPrimaryAlignedScrollDistance(view);
        if (view2 != null) {
            primaryAlignedScrollDistance = getAdjustedPrimaryAlignedScrollDistance(primaryAlignedScrollDistance, view, view2);
        }
        int secondaryScrollDistance = getSecondaryScrollDistance(view);
        int i = primaryAlignedScrollDistance + this.mPrimaryScrollExtra;
        if (i == 0 && secondaryScrollDistance == 0) {
            iArr[0] = 0;
            iArr[1] = 0;
            return false;
        }
        iArr[0] = i;
        iArr[1] = secondaryScrollDistance;
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001b A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:14:0x0023 A[RETURN, SYNTHETIC] */
    private int getMovement(int i) {
        int i2 = this.mOrientation;
        if (i2 == 0) {
            if (i != 17) {
                if (i != 33) {
                    if (i != 66) {
                        if (i != 130) {
                            return 17;
                        }
                        return 3;
                    }
                    if ((this.mFlag & 262144) != 0) {
                        return 0;
                    }
                }
                return 2;
            }
            if ((this.mFlag & 262144) == 0) {
                return 0;
            }
            return 1;
        }
        if (i2 == 1) {
            if (i == 17) {
                if ((this.mFlag & 524288) == 0) {
                    return 2;
                }
                return 3;
            }
            if (i == 33) {
                return 0;
            }
            if (i == 66) {
                if ((this.mFlag & 524288) == 0) {
                    return 3;
                }
                return 2;
            }
            if (i == 130) {
                return 1;
            }
        }
        return 17;
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:40:0x00ba A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x00bc  */
    private boolean getNoneAlignedPosition(View view, int[] iArr) {
        View viewFindViewByPosition;
        int i;
        int viewMax;
        int secondaryScrollDistance;
        int adapterPositionByView = getAdapterPositionByView(view);
        int viewMin = getViewMin(view);
        int viewMax2 = getViewMax(view);
        int paddingMin = this.mWindowAlignment.mainAxis().getPaddingMin();
        int clientSize = this.mWindowAlignment.mainAxis().getClientSize();
        int rowIndex = this.mGrid.getRowIndex(adapterPositionByView);
        View viewFindViewByPosition2 = null;
        if (viewMin < paddingMin) {
            if (this.mFocusScrollStrategy == 2) {
                View view2 = view;
                while (true) {
                    if (!prependOneColumnVisibleItems()) {
                        viewFindViewByPosition = null;
                        viewFindViewByPosition2 = view2;
                        break;
                    }
                    Grid grid = this.mGrid;
                    CircularIntArray circularIntArray = grid.getItemPositionsInRows(grid.getFirstVisibleIndex(), adapterPositionByView)[rowIndex];
                    View viewFindViewByPosition3 = findViewByPosition(circularIntArray.get(0));
                    if (viewMax2 - getViewMin(viewFindViewByPosition3) > clientSize) {
                        if (circularIntArray.size() <= 2) {
                            viewFindViewByPosition = null;
                            viewFindViewByPosition2 = viewFindViewByPosition3;
                            break;
                        }
                        viewFindViewByPosition = null;
                        viewFindViewByPosition2 = findViewByPosition(circularIntArray.get(2));
                        break;
                    }
                    view2 = viewFindViewByPosition3;
                }
            } else {
                viewFindViewByPosition = null;
                viewFindViewByPosition2 = view;
            }
        } else if (viewMax2 <= clientSize + paddingMin) {
            viewFindViewByPosition = null;
        } else if (this.mFocusScrollStrategy == 2) {
            do {
                Grid grid2 = this.mGrid;
                CircularIntArray circularIntArray2 = grid2.getItemPositionsInRows(adapterPositionByView, grid2.getLastVisibleIndex())[rowIndex];
                viewFindViewByPosition = findViewByPosition(circularIntArray2.get(circularIntArray2.size() - 1));
                if (getViewMax(viewFindViewByPosition) - viewMin > clientSize) {
                    viewFindViewByPosition = null;
                    break;
                }
            } while (appendOneColumnVisibleItems());
            if (viewFindViewByPosition == null) {
                viewFindViewByPosition2 = view;
            }
        } else {
            viewFindViewByPosition = view;
        }
        if (viewFindViewByPosition2 == null) {
            if (viewFindViewByPosition != null) {
                viewMax = getViewMax(viewFindViewByPosition);
                paddingMin += clientSize;
            } else {
                i = 0;
            }
            if (viewFindViewByPosition2 != null) {
                view = viewFindViewByPosition2;
            } else if (viewFindViewByPosition != null) {
                view = viewFindViewByPosition;
            }
            secondaryScrollDistance = getSecondaryScrollDistance(view);
            if (i != 0 && secondaryScrollDistance == 0) {
                return false;
            }
            iArr[0] = i;
            iArr[1] = secondaryScrollDistance;
            return true;
        }
        viewMax = getViewMin(viewFindViewByPosition2);
        i = viewMax - paddingMin;
        if (viewFindViewByPosition2 != null) {
            view = viewFindViewByPosition2;
        } else if (viewFindViewByPosition != null) {
            view = viewFindViewByPosition;
        }
        secondaryScrollDistance = getSecondaryScrollDistance(view);
        if (i != 0) {
        }
        iArr[0] = i;
        iArr[1] = secondaryScrollDistance;
        return true;
    }

    private int getPrimaryAlignedScrollDistance(View view) {
        return this.mWindowAlignment.mainAxis().getScroll(getViewCenter(view));
    }

    private int getRowSizeSecondary(int i) {
        int i2 = this.mFixedRowSizeSecondary;
        if (i2 != 0) {
            return i2;
        }
        int[] iArr = this.mRowSizeSecondary;
        if (iArr == null) {
            return 0;
        }
        return iArr[i];
    }

    private int getSecondaryScrollDistance(View view) {
        return this.mWindowAlignment.secondAxis().getScroll(getViewCenterSecondary(view));
    }

    private int getSizeSecondary() {
        int i = (this.mFlag & 524288) != 0 ? 0 : this.mNumRows - 1;
        return getRowStartSecondary(i) + getRowSizeSecondary(i);
    }

    private int getViewCenter(View view) {
        return this.mOrientation == 0 ? getViewCenterX(view) : getViewCenterY(view);
    }

    private int getViewCenterSecondary(View view) {
        return this.mOrientation == 0 ? getViewCenterY(view) : getViewCenterX(view);
    }

    private int getViewCenterX(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Objects.requireNonNull(layoutParams);
        return view.getLeft() + layoutParams.mLeftInset + layoutParams.getAlignX();
    }

    private int getViewCenterY(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Objects.requireNonNull(layoutParams);
        return view.getTop() + layoutParams.mTopInset + layoutParams.getAlignY();
    }

    private boolean gridOnRequestFocusInDescendantsAligned(RecyclerView recyclerView, int i, Rect rect) {
        View viewFindViewByPosition = findViewByPosition(this.mFocusPosition);
        if (viewFindViewByPosition != null) {
            return viewFindViewByPosition.requestFocus(i, rect);
        }
        return false;
    }

    private boolean gridOnRequestFocusInDescendantsUnaligned(RecyclerView recyclerView, int i, Rect rect) {
        int i2;
        int i3;
        int childCount = getChildCount();
        int i4 = -1;
        if ((i & 2) != 0) {
            i4 = childCount;
            i2 = 0;
            i3 = 1;
        } else {
            i2 = childCount - 1;
            i3 = -1;
        }
        int paddingMin = this.mWindowAlignment.mainAxis().getPaddingMin();
        int clientSize = this.mWindowAlignment.mainAxis().getClientSize() + paddingMin;
        while (i2 != i4) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && getViewMin(childAt) >= paddingMin && getViewMax(childAt) <= clientSize && childAt.requestFocus(i, rect)) {
                return true;
            }
            i2 += i3;
        }
        return false;
    }

    private void initScrollController() {
        this.mWindowAlignment.reset();
        this.mWindowAlignment.horizontal.setSize(getWidth());
        this.mWindowAlignment.vertical.setSize(getHeight());
        this.mWindowAlignment.horizontal.setPadding(getPaddingLeft(), getPaddingRight());
        this.mWindowAlignment.vertical.setPadding(getPaddingTop(), getPaddingBottom());
        this.mSizePrimary = this.mWindowAlignment.mainAxis().getSize();
        this.mScrollOffsetSecondary = 0;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0076  */
    /* JADX WARN: Code duplicated, block: B:36:0x008b  */
    private boolean layoutInit() {
        Grid grid;
        int itemCount = this.mState.getItemCount();
        if (itemCount == 0) {
            this.mFocusPosition = -1;
            this.mSubFocusPosition = 0;
        } else {
            int i = this.mFocusPosition;
            if (i >= itemCount) {
                this.mFocusPosition = itemCount - 1;
                this.mSubFocusPosition = 0;
            } else if (i == -1 && itemCount > 0) {
                this.mFocusPosition = 0;
                this.mSubFocusPosition = 0;
            }
        }
        if (!this.mState.didStructureChange() && (grid = this.mGrid) != null && grid.getFirstVisibleIndex() >= 0 && (this.mFlag & 256) == 0 && this.mGrid.getNumRows() == this.mNumRows) {
            updateScrollController();
            updateSecondaryScrollLimits();
            this.mGrid.setSpacing(this.mSpacingPrimary);
            return true;
        }
        this.mFlag &= -257;
        Grid grid2 = this.mGrid;
        if (grid2 == null || this.mNumRows != grid2.getNumRows()) {
            Grid gridCreateGrid = Grid.createGrid(this.mNumRows);
            this.mGrid = gridCreateGrid;
            gridCreateGrid.setProvider(this.mGridProvider);
            this.mGrid.setReversedFlow((262144 & this.mFlag) != 0);
        } else {
            if (((this.mFlag & 262144) != 0) != this.mGrid.isReversedFlow()) {
                Grid gridCreateGrid2 = Grid.createGrid(this.mNumRows);
                this.mGrid = gridCreateGrid2;
                gridCreateGrid2.setProvider(this.mGridProvider);
                this.mGrid.setReversedFlow((262144 & this.mFlag) != 0);
            }
        }
        initScrollController();
        updateSecondaryScrollLimits();
        this.mGrid.setSpacing(this.mSpacingPrimary);
        detachAndScrapAttachedViews(this.mRecycler);
        this.mGrid.resetVisibleIndex();
        this.mWindowAlignment.mainAxis().invalidateScrollMin();
        this.mWindowAlignment.mainAxis().invalidateScrollMax();
        return false;
    }

    private void leaveContext() {
        this.mRecycler = null;
        this.mState = null;
        this.mPositionDeltaInPreLayout = 0;
        this.mExtraLayoutSpaceInPreLayout = 0;
    }

    private void measureScrapChild(int i, int i2, int i3, int[] iArr) {
        View viewForPosition = this.mRecycler.getViewForPosition(i);
        if (viewForPosition != null) {
            LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
            Rect rect = sTempRect;
            calculateItemDecorationsForChild(viewForPosition, rect);
            int i4 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.left + rect.right;
            int i5 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect.top + rect.bottom;
            viewForPosition.measure(ViewGroup.getChildMeasureSpec(i2, getPaddingRight() + getPaddingLeft() + i4, ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(i3, getPaddingBottom() + getPaddingTop() + i5, ((ViewGroup.MarginLayoutParams) layoutParams).height));
            iArr[0] = getDecoratedMeasuredWidthWithMargin(viewForPosition);
            iArr[1] = getDecoratedMeasuredHeightWithMargin(viewForPosition);
            this.mRecycler.recycleView(viewForPosition);
        }
    }

    private void offsetChildrenPrimary(int i) {
        int childCount = getChildCount();
        int i2 = 0;
        if (this.mOrientation == 1) {
            while (i2 < childCount) {
                getChildAt(i2).offsetTopAndBottom(i);
                i2++;
            }
        } else {
            while (i2 < childCount) {
                getChildAt(i2).offsetLeftAndRight(i);
                i2++;
            }
        }
    }

    private void offsetChildrenSecondary(int i) {
        int childCount = getChildCount();
        int i2 = 0;
        if (this.mOrientation == 0) {
            while (i2 < childCount) {
                getChildAt(i2).offsetTopAndBottom(i);
                i2++;
            }
        } else {
            while (i2 < childCount) {
                getChildAt(i2).offsetLeftAndRight(i);
                i2++;
            }
        }
    }

    private boolean prependOneColumnVisibleItems() {
        return this.mGrid.prependOneColumnVisibleItems();
    }

    private void prependVisibleItems() {
        this.mGrid.prependVisibleItems((this.mFlag & 262144) != 0 ? this.mSizePrimary + this.mExtraLayoutSpace + this.mExtraLayoutSpaceInPreLayout : (-this.mExtraLayoutSpace) - this.mExtraLayoutSpaceInPreLayout);
    }

    private void processPendingMovement(boolean z) {
        if (z) {
            if (hasCreatedLastItem()) {
                return;
            }
        } else if (hasCreatedFirstItem()) {
            return;
        }
        PendingMoveSmoothScroller pendingMoveSmoothScroller = this.mPendingMoveSmoothScroller;
        if (pendingMoveSmoothScroller == null) {
            this.mBaseGridView.stopScroll();
            PendingMoveSmoothScroller pendingMoveSmoothScroller2 = new PendingMoveSmoothScroller(z ? 1 : -1, this.mNumRows > 1);
            this.mFocusPositionOffset = 0;
            startSmoothScroll(pendingMoveSmoothScroller2);
            return;
        }
        if (z) {
            pendingMoveSmoothScroller.increasePendingMoves();
        } else {
            pendingMoveSmoothScroller.decreasePendingMoves();
        }
    }

    private boolean processRowSizeSecondary(boolean z) {
        if (this.mFixedRowSizeSecondary != 0 || this.mRowSizeSecondary == null) {
            return false;
        }
        Grid grid = this.mGrid;
        CircularIntArray[] itemPositionsInRows = grid == null ? null : grid.getItemPositionsInRows();
        boolean z2 = false;
        int i = -1;
        for (int i2 = 0; i2 < this.mNumRows; i2++) {
            CircularIntArray circularIntArray = itemPositionsInRows == null ? null : itemPositionsInRows[i2];
            int size = circularIntArray == null ? 0 : circularIntArray.size();
            int i3 = -1;
            for (int i4 = 0; i4 < size; i4 += 2) {
                int i5 = circularIntArray.get(i4 + 1);
                for (int i6 = circularIntArray.get(i4); i6 <= i5; i6++) {
                    View viewFindViewByPosition = findViewByPosition(i6 - this.mPositionDeltaInPreLayout);
                    if (viewFindViewByPosition != null) {
                        if (z) {
                            measureChild(viewFindViewByPosition);
                        }
                        int decoratedMeasuredHeightWithMargin = this.mOrientation == 0 ? getDecoratedMeasuredHeightWithMargin(viewFindViewByPosition) : getDecoratedMeasuredWidthWithMargin(viewFindViewByPosition);
                        if (decoratedMeasuredHeightWithMargin > i3) {
                            i3 = decoratedMeasuredHeightWithMargin;
                        }
                    }
                }
            }
            int itemCount = this.mState.getItemCount();
            if (!this.mBaseGridView.hasFixedSize() && z && i3 < 0 && itemCount > 0) {
                if (i < 0) {
                    int i7 = this.mFocusPosition;
                    if (i7 < 0) {
                        i7 = 0;
                    } else if (i7 >= itemCount) {
                        i7 = itemCount - 1;
                    }
                    if (getChildCount() > 0) {
                        int layoutPosition = this.mBaseGridView.getChildViewHolder(getChildAt(0)).getLayoutPosition();
                        int layoutPosition2 = this.mBaseGridView.getChildViewHolder(getChildAt(getChildCount() - 1)).getLayoutPosition();
                        if (i7 >= layoutPosition && i7 <= layoutPosition2) {
                            i7 = i7 - layoutPosition <= layoutPosition2 - i7 ? layoutPosition - 1 : layoutPosition2 + 1;
                            if (i7 < 0 && layoutPosition2 < itemCount - 1) {
                                i7 = layoutPosition2 + 1;
                            } else if (i7 >= itemCount && layoutPosition > 0) {
                                i7 = layoutPosition - 1;
                            }
                        }
                    }
                    if (i7 >= 0 && i7 < itemCount) {
                        measureScrapChild(i7, View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0), this.mMeasuredDimension);
                        i = this.mOrientation == 0 ? this.mMeasuredDimension[1] : this.mMeasuredDimension[0];
                    }
                }
                if (i >= 0) {
                    i3 = i;
                }
            }
            if (i3 < 0) {
                i3 = 0;
            }
            int[] iArr = this.mRowSizeSecondary;
            if (iArr[i2] != i3) {
                iArr[i2] = i3;
                z2 = true;
            }
        }
        return z2;
    }

    private void removeInvisibleViewsAtEnd() {
        int i = this.mFlag;
        if ((65600 & i) == 65536) {
            this.mGrid.removeInvisibleItemsAtEnd(this.mFocusPosition, (i & 262144) != 0 ? -this.mExtraLayoutSpace : this.mSizePrimary + this.mExtraLayoutSpace);
        }
    }

    private void removeInvisibleViewsAtFront() {
        int i = this.mFlag;
        if ((65600 & i) == 65536) {
            this.mGrid.removeInvisibleItemsAtFront(this.mFocusPosition, (i & 262144) != 0 ? this.mSizePrimary + this.mExtraLayoutSpace : -this.mExtraLayoutSpace);
        }
    }

    private void saveContext(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mRecycler != null || this.mState != null) {
            Log.e(TAG, "Recycler information was not released, bug!");
        }
        this.mRecycler = recycler;
        this.mState = state;
        this.mPositionDeltaInPreLayout = 0;
        this.mExtraLayoutSpaceInPreLayout = 0;
    }

    private int scrollDirectionPrimary(int i) {
        int minScroll;
        int i2 = this.mFlag;
        if ((i2 & 64) == 0 && (i2 & 3) != 1 && (i <= 0 ? !(i >= 0 || this.mWindowAlignment.mainAxis().isMinUnknown() || i >= (minScroll = this.mWindowAlignment.mainAxis().getMinScroll())) : !(this.mWindowAlignment.mainAxis().isMaxUnknown() || i <= (minScroll = this.mWindowAlignment.mainAxis().getMaxScroll())))) {
            i = minScroll;
        }
        if (i == 0) {
            return 0;
        }
        offsetChildrenPrimary(-i);
        if ((this.mFlag & 3) == 1) {
            updateScrollLimits();
            return i;
        }
        int childCount = getChildCount();
        if ((this.mFlag & 262144) == 0 ? i >= 0 : i <= 0) {
            appendVisibleItems();
        } else {
            prependVisibleItems();
        }
        boolean z = getChildCount() > childCount;
        int childCount2 = getChildCount();
        if ((262144 & this.mFlag) == 0 ? i >= 0 : i <= 0) {
            removeInvisibleViewsAtFront();
        } else {
            removeInvisibleViewsAtEnd();
        }
        if (z | (getChildCount() < childCount2)) {
            updateRowSecondarySizeRefresh();
        }
        this.mBaseGridView.invalidate();
        updateScrollLimits();
        return i;
    }

    private int scrollDirectionSecondary(int i) {
        if (i == 0) {
            return 0;
        }
        offsetChildrenSecondary(-i);
        this.mScrollOffsetSecondary += i;
        updateSecondaryScrollLimits();
        this.mBaseGridView.invalidate();
        return i;
    }

    private void scrollGrid(int i, int i2, boolean z) {
        if ((this.mFlag & 3) == 1) {
            scrollDirectionPrimary(i);
            scrollDirectionSecondary(i2);
            return;
        }
        if (this.mOrientation != 0) {
            i2 = i;
            i = i2;
        }
        if (z) {
            this.mBaseGridView.smoothScrollBy(i, i2);
        } else {
            this.mBaseGridView.scrollBy(i, i2);
            dispatchChildSelectedAndPositioned();
        }
    }

    private void updateChildAlignments(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams.getItemAlignmentFacet() == null) {
            layoutParams.setAlignX(this.mItemAlignment.horizontal.getAlignmentPosition(view));
            layoutParams.setAlignY(this.mItemAlignment.vertical.getAlignmentPosition(view));
            return;
        }
        layoutParams.calculateItemAlignments(this.mOrientation, view);
        if (this.mOrientation == 0) {
            layoutParams.setAlignY(this.mItemAlignment.vertical.getAlignmentPosition(view));
        } else {
            layoutParams.setAlignX(this.mItemAlignment.horizontal.getAlignmentPosition(view));
        }
    }

    private void updateRowSecondarySizeRefresh() {
        int i = (this.mFlag & (-1025)) | (processRowSizeSecondary(false) ? 1024 : 0);
        this.mFlag = i;
        if ((i & 1024) != 0) {
            forceRequestLayout();
        }
    }

    private void updateScrollController() {
        this.mWindowAlignment.horizontal.setSize(getWidth());
        this.mWindowAlignment.vertical.setSize(getHeight());
        this.mWindowAlignment.horizontal.setPadding(getPaddingLeft(), getPaddingRight());
        this.mWindowAlignment.vertical.setPadding(getPaddingTop(), getPaddingBottom());
        this.mSizePrimary = this.mWindowAlignment.mainAxis().getSize();
    }

    private void updateSecondaryScrollLimits() {
        WindowAlignment.Axis axisSecondAxis = this.mWindowAlignment.secondAxis();
        int paddingMin = axisSecondAxis.getPaddingMin() - this.mScrollOffsetSecondary;
        int sizeSecondary = getSizeSecondary() + paddingMin;
        axisSecondAxis.updateMinMax(paddingMin, sizeSecondary, paddingMin, sizeSecondary);
    }

    public void addOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener onChildViewHolderSelectedListener) {
        if (this.mChildViewHolderSelectedListeners == null) {
            this.mChildViewHolderSelectedListeners = new ArrayList<>();
        }
        this.mChildViewHolderSelectedListeners.add(onChildViewHolderSelectedListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return this.mOrientation == 0 || this.mNumRows > 1;
    }

    public final boolean canScrollTo(View view) {
        return view.getVisibility() == 0 && (!hasFocus() || view.hasFocusable());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.mOrientation == 1 || this.mNumRows > 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        try {
            saveContext(null, state);
            if (this.mOrientation != 0) {
                i = i2;
            }
            if (getChildCount() != 0 && i != 0) {
                this.mGrid.collectAdjacentPrefetchPositions(i < 0 ? -this.mExtraLayoutSpace : this.mSizePrimary + this.mExtraLayoutSpace, i, layoutPrefetchRegistry);
            }
        } finally {
            leaveContext();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void collectInitialPrefetchPositions(int i, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i2 = this.mBaseGridView.mInitialPrefetchItemCount;
        if (i == 0 || i2 == 0) {
            return;
        }
        int iMax = Math.max(0, Math.min(this.mFocusPosition - ((i2 - 1) / 2), i - i2));
        for (int i3 = iMax; i3 < i && i3 < iMax + i2; i3++) {
            layoutPrefetchRegistry.addPosition(i3, 0);
        }
    }

    public final void dispatchChildSelected() {
        if (this.mChildSelectedListener == null) {
            ArrayList<OnChildViewHolderSelectedListener> arrayList = this.mChildViewHolderSelectedListeners;
            if (!(arrayList != null && arrayList.size() > 0)) {
                return;
            }
        }
        int i = this.mFocusPosition;
        View viewFindViewByPosition = i == -1 ? null : findViewByPosition(i);
        if (viewFindViewByPosition != null) {
            RecyclerView.ViewHolder childViewHolder = this.mBaseGridView.getChildViewHolder(viewFindViewByPosition);
            OnChildSelectedListener onChildSelectedListener = this.mChildSelectedListener;
            if (onChildSelectedListener != null) {
                onChildSelectedListener.onChildSelected(this.mBaseGridView, viewFindViewByPosition, this.mFocusPosition, childViewHolder == null ? -1L : childViewHolder.getItemId());
            }
            fireOnChildViewHolderSelected(this.mBaseGridView, childViewHolder, this.mFocusPosition, this.mSubFocusPosition);
        } else {
            OnChildSelectedListener onChildSelectedListener2 = this.mChildSelectedListener;
            if (onChildSelectedListener2 != null) {
                onChildSelectedListener2.onChildSelected(this.mBaseGridView, null, -1, -1L);
            }
            fireOnChildViewHolderSelected(this.mBaseGridView, null, -1, 0);
        }
        if ((this.mFlag & 3) == 1 || this.mBaseGridView.isLayoutRequested()) {
            return;
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            if (getChildAt(i2).isLayoutRequested()) {
                forceRequestLayout();
                return;
            }
        }
    }

    public final void dispatchChildSelectedAndPositioned() {
        ArrayList<OnChildViewHolderSelectedListener> arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList != null && arrayList.size() > 0) {
            int i = this.mFocusPosition;
            View viewFindViewByPosition = i == -1 ? null : findViewByPosition(i);
            if (viewFindViewByPosition != null) {
                fireOnChildViewHolderSelectedAndPositioned(this.mBaseGridView, this.mBaseGridView.getChildViewHolder(viewFindViewByPosition), this.mFocusPosition, this.mSubFocusPosition);
                return;
            }
            OnChildSelectedListener onChildSelectedListener = this.mChildSelectedListener;
            if (onChildSelectedListener != null) {
                onChildSelectedListener.onChildSelected(this.mBaseGridView, null, -1, -1L);
            }
            fireOnChildViewHolderSelectedAndPositioned(this.mBaseGridView, null, -1, 0);
        }
    }

    public final void fireOnChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
        ArrayList<OnChildViewHolderSelectedListener> arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            this.mChildViewHolderSelectedListeners.get(size).onChildViewHolderSelected(recyclerView, viewHolder, i, i2);
        }
    }

    public final void fireOnChildViewHolderSelectedAndPositioned(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
        ArrayList<OnChildViewHolderSelectedListener> arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            this.mChildViewHolderSelectedListeners.get(size).onChildViewHolderSelectedAndPositioned(recyclerView, viewHolder, i, i2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Grid grid;
        return (this.mOrientation != 1 || (grid = this.mGrid) == null) ? super.getColumnCountForAccessibility(recycler, state) : grid.getNumRows();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getDecoratedBottom(View view) {
        return super.getDecoratedBottom(view) - ((LayoutParams) view.getLayoutParams()).mBottomInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        super.getDecoratedBoundsWithMargins(view, rect);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        rect.left += layoutParams.mLeftInset;
        rect.top += layoutParams.mTopInset;
        rect.right -= layoutParams.mRightInset;
        rect.bottom -= layoutParams.mBottomInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getDecoratedLeft(View view) {
        return super.getDecoratedLeft(view) + ((LayoutParams) view.getLayoutParams()).mLeftInset;
    }

    public final int getDecoratedMeasuredHeightWithMargin(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
    }

    public final int getDecoratedMeasuredWidthWithMargin(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getDecoratedRight(View view) {
        return super.getDecoratedRight(view) - ((LayoutParams) view.getLayoutParams()).mRightInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getDecoratedTop(View view) {
        return super.getDecoratedTop(view) + ((LayoutParams) view.getLayoutParams()).mTopInset;
    }

    public final int getExtraLayoutSpace() {
        return this.mExtraLayoutSpace;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Object getFacet(RecyclerView.ViewHolder viewHolder) {
        FacetProviderAdapter facetProviderAdapter;
        FacetProvider facetProvider;
        Object facet = viewHolder instanceof FacetProvider ? ((FacetProvider) viewHolder).getFacet(ItemAlignmentFacet.class) : null;
        return (facet != null || (facetProviderAdapter = this.mFacetProviderAdapter) == null || (facetProvider = facetProviderAdapter.getFacetProvider(viewHolder.getItemViewType())) == null) ? facet : facetProvider.getFacet(ItemAlignmentFacet.class);
    }

    public int getFocusScrollStrategy() {
        return this.mFocusScrollStrategy;
    }

    public int getHorizontalSpacing() {
        return this.mHorizontalSpacing;
    }

    public int getItemAlignmentOffset() {
        return this.mItemAlignment.mainAxis().getItemAlignmentOffset();
    }

    public float getItemAlignmentOffsetPercent() {
        return this.mItemAlignment.mainAxis().getItemAlignmentOffsetPercent();
    }

    public int getItemAlignmentViewId() {
        return this.mItemAlignment.mainAxis().getItemAlignmentViewId();
    }

    public boolean getPruneChild() {
        return (this.mFlag & 65536) != 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Grid grid;
        return (this.mOrientation != 0 || (grid = this.mGrid) == null) ? super.getRowCountForAccessibility(recycler, state) : grid.getNumRows();
    }

    public final int getRowStartSecondary(int i) {
        int rowSizeSecondary = 0;
        if ((this.mFlag & 524288) != 0) {
            for (int i2 = this.mNumRows - 1; i2 > i; i2--) {
                rowSizeSecondary += getRowSizeSecondary(i2) + this.mSpacingSecondary;
            }
            return rowSizeSecondary;
        }
        int rowSizeSecondary2 = 0;
        while (rowSizeSecondary < i) {
            rowSizeSecondary2 += getRowSizeSecondary(rowSizeSecondary) + this.mSpacingSecondary;
            rowSizeSecondary++;
        }
        return rowSizeSecondary2;
    }

    public final boolean getScrollPosition(View view, int[] iArr) {
        int i = this.mFocusScrollStrategy;
        return (i == 1 || i == 2) ? getNoneAlignedPosition(view, iArr) : getAlignedPosition(view, null, iArr);
    }

    public int getSelection() {
        return this.mFocusPosition;
    }

    public final int getSlideOutDistance() {
        int i;
        int left;
        int right;
        if (this.mOrientation == 1) {
            i = -getHeight();
            if (getChildCount() <= 0 || (left = getChildAt(0).getTop()) >= 0) {
                return i;
            }
        } else {
            if ((this.mFlag & 262144) != 0) {
                int width = getWidth();
                return (getChildCount() <= 0 || (right = getChildAt(0).getRight()) <= width) ? width : right;
            }
            i = -getWidth();
            if (getChildCount() <= 0 || (left = getChildAt(0).getLeft()) >= 0) {
                return i;
            }
        }
        return i + left;
    }

    public final int getSubPositionByView(View view, View view2) {
        ItemAlignmentFacet itemAlignmentFacet;
        if (view != null && view2 != null && (itemAlignmentFacet = ((LayoutParams) view.getLayoutParams()).getItemAlignmentFacet()) != null) {
            ItemAlignmentFacet.ItemAlignmentDef[] alignmentDefs = itemAlignmentFacet.getAlignmentDefs();
            if (alignmentDefs.length > 1) {
                while (view2 != view) {
                    int id = view2.getId();
                    if (id != -1) {
                        for (int i = 1; i < alignmentDefs.length; i++) {
                            if (alignmentDefs[i].getItemAlignmentFocusViewId() == id) {
                                return i;
                            }
                        }
                    }
                    view2 = (View) view2.getParent();
                }
            }
        }
        return 0;
    }

    public int getSubSelection() {
        return this.mSubFocusPosition;
    }

    public int getVerticalSpacing() {
        return this.mVerticalSpacing;
    }

    public final int getViewMax(View view) {
        return this.mOrientationHelper.getDecoratedEnd(view);
    }

    public final int getViewMin(View view) {
        return this.mOrientationHelper.getDecoratedStart(view);
    }

    public final int getViewPrimarySize(View view) {
        Rect rect = sTempRect;
        getDecoratedBoundsWithMargins(view, rect);
        return this.mOrientation == 0 ? rect.width() : rect.height();
    }

    public void getViewSelectedOffsets(View view, int[] iArr) {
        if (this.mOrientation == 0) {
            iArr[0] = getPrimaryAlignedScrollDistance(view);
            iArr[1] = getSecondaryScrollDistance(view);
        } else {
            iArr[1] = getPrimaryAlignedScrollDistance(view);
            iArr[0] = getSecondaryScrollDistance(view);
        }
    }

    public int getWindowAlignment() {
        return this.mWindowAlignment.mainAxis().getWindowAlignment();
    }

    public int getWindowAlignmentOffset() {
        return this.mWindowAlignment.mainAxis().getWindowAlignmentOffset();
    }

    public float getWindowAlignmentOffsetPercent() {
        return this.mWindowAlignment.mainAxis().getWindowAlignmentOffsetPercent();
    }

    public final boolean gridOnRequestFocusInDescendants(RecyclerView recyclerView, int i, Rect rect) {
        int i2 = this.mFocusScrollStrategy;
        return (i2 == 1 || i2 == 2) ? gridOnRequestFocusInDescendantsUnaligned(recyclerView, i, rect) : gridOnRequestFocusInDescendantsAligned(recyclerView, i, rect);
    }

    public final boolean hasCreatedFirstItem() {
        return getItemCount() == 0 || this.mBaseGridView.findViewHolderForAdapterPosition(0) != null;
    }

    public final boolean hasCreatedLastItem() {
        int itemCount = getItemCount();
        return itemCount == 0 || this.mBaseGridView.findViewHolderForAdapterPosition(itemCount - 1) != null;
    }

    public final boolean hasDoneFirstLayout() {
        return this.mGrid != null;
    }

    public final boolean hasPreviousViewInSameRow(int i) {
        Grid grid = this.mGrid;
        if (grid == null || i == -1 || grid.getFirstVisibleIndex() < 0) {
            return false;
        }
        if (this.mGrid.getFirstVisibleIndex() > 0) {
            return true;
        }
        int i2 = this.mGrid.getLocation(i).row;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            int adapterPositionByIndex = getAdapterPositionByIndex(childCount);
            Grid.Location location = this.mGrid.getLocation(adapterPositionByIndex);
            if (location != null && location.row == i2 && adapterPositionByIndex < i) {
                return true;
            }
        }
        return false;
    }

    public boolean isItemAlignmentOffsetWithPadding() {
        return this.mItemAlignment.mainAxis().isItemAlignmentOffsetWithPadding();
    }

    public final boolean isItemFullyVisible(int i) {
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.mBaseGridView.findViewHolderForAdapterPosition(i);
        return viewHolderFindViewHolderForAdapterPosition != null && viewHolderFindViewHolderForAdapterPosition.itemView.getLeft() >= 0 && viewHolderFindViewHolderForAdapterPosition.itemView.getRight() <= this.mBaseGridView.getWidth() && viewHolderFindViewHolderForAdapterPosition.itemView.getTop() >= 0 && viewHolderFindViewHolderForAdapterPosition.itemView.getBottom() <= this.mBaseGridView.getHeight();
    }

    public boolean isScrollEnabled() {
        return (this.mFlag & 131072) != 0;
    }

    public final void layoutChild(int i, View view, int i2, int i3, int i4) {
        int rowSizeSecondary;
        int i5;
        int decoratedMeasuredHeightWithMargin = this.mOrientation == 0 ? getDecoratedMeasuredHeightWithMargin(view) : getDecoratedMeasuredWidthWithMargin(view);
        int i6 = this.mFixedRowSizeSecondary;
        if (i6 > 0) {
            decoratedMeasuredHeightWithMargin = Math.min(decoratedMeasuredHeightWithMargin, i6);
        }
        int i7 = this.mGravity;
        int i8 = i7 & 112;
        int absoluteGravity = (this.mFlag & 786432) != 0 ? Gravity.getAbsoluteGravity(i7 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK, 1) : i7 & 7;
        int i9 = this.mOrientation;
        if ((i9 != 0 || i8 != 48) && (i9 != 1 || absoluteGravity != 3)) {
            if ((i9 == 0 && i8 == 80) || (i9 == 1 && absoluteGravity == 5)) {
                rowSizeSecondary = getRowSizeSecondary(i) - decoratedMeasuredHeightWithMargin;
            } else if ((i9 == 0 && i8 == 16) || (i9 == 1 && absoluteGravity == 1)) {
                rowSizeSecondary = (getRowSizeSecondary(i) - decoratedMeasuredHeightWithMargin) / 2;
            }
            i4 += rowSizeSecondary;
        }
        if (this.mOrientation == 0) {
            i5 = decoratedMeasuredHeightWithMargin + i4;
        } else {
            int i10 = decoratedMeasuredHeightWithMargin + i4;
            int i11 = i4;
            i4 = i2;
            i2 = i11;
            i5 = i3;
            i3 = i10;
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutDecoratedWithMargins(view, i2, i4, i3, i5);
        Rect rect = sTempRect;
        super.getDecoratedBoundsWithMargins(view, rect);
        int i12 = i2 - rect.left;
        int i13 = i4 - rect.top;
        int i14 = rect.right - i3;
        int i15 = rect.bottom - i5;
        layoutParams.mLeftInset = i12;
        layoutParams.mTopInset = i13;
        layoutParams.mRightInset = i14;
        layoutParams.mBottomInset = i15;
        updateChildAlignments(view);
    }

    public final void measureChild(View view) {
        int childMeasureSpec;
        int childMeasureSpec2;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = sTempRect;
        calculateItemDecorationsForChild(view, rect);
        int i = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.left + rect.right;
        int i2 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect.top + rect.bottom;
        int iMakeMeasureSpec = this.mRowSizeSecondaryRequested == -2 ? View.MeasureSpec.makeMeasureSpec(0, 0) : View.MeasureSpec.makeMeasureSpec(this.mFixedRowSizeSecondary, 1073741824);
        if (this.mOrientation == 0) {
            childMeasureSpec = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), i, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            childMeasureSpec2 = ViewGroup.getChildMeasureSpec(iMakeMeasureSpec, i2, ((ViewGroup.MarginLayoutParams) layoutParams).height);
        } else {
            int childMeasureSpec3 = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), i2, ((ViewGroup.MarginLayoutParams) layoutParams).height);
            childMeasureSpec = ViewGroup.getChildMeasureSpec(iMakeMeasureSpec, i, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            childMeasureSpec2 = childMeasureSpec3;
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onAdapterChanged(RecyclerView.Adapter adapter, RecyclerView.Adapter adapter2) {
        if (adapter != null) {
            discardLayoutInfo();
            this.mFocusPosition = -1;
            this.mFocusPositionOffset = 0;
            this.mChildrenStates.clear();
        }
        if (adapter2 instanceof FacetProviderAdapter) {
            this.mFacetProviderAdapter = (FacetProviderAdapter) adapter2;
        } else {
            this.mFacetProviderAdapter = null;
        }
        super.onAdapterChanged(adapter, adapter2);
    }

    /* JADX WARN: Code duplicated, block: B:51:0x0096  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int i, int i2) {
        View childAt;
        int i3;
        if ((this.mFlag & 32768) != 0) {
            return true;
        }
        if (!recyclerView.hasFocus()) {
            int size = arrayList.size();
            if (this.mFocusScrollStrategy != 0) {
                int paddingMin = this.mWindowAlignment.mainAxis().getPaddingMin();
                int clientSize = this.mWindowAlignment.mainAxis().getClientSize() + paddingMin;
                int childCount = getChildCount();
                for (int i4 = 0; i4 < childCount; i4++) {
                    View childAt2 = getChildAt(i4);
                    if (childAt2.getVisibility() == 0 && getViewMin(childAt2) >= paddingMin && getViewMax(childAt2) <= clientSize) {
                        childAt2.addFocusables(arrayList, i, i2);
                    }
                }
                if (arrayList.size() == size) {
                    int childCount2 = getChildCount();
                    for (int i5 = 0; i5 < childCount2; i5++) {
                        View childAt3 = getChildAt(i5);
                        if (childAt3.getVisibility() == 0) {
                            childAt3.addFocusables(arrayList, i, i2);
                        }
                    }
                }
            } else {
                View viewFindViewByPosition = findViewByPosition(this.mFocusPosition);
                if (viewFindViewByPosition != null) {
                    viewFindViewByPosition.addFocusables(arrayList, i, i2);
                }
            }
            if (arrayList.size() == size && recyclerView.isFocusable()) {
                arrayList.add(recyclerView);
            }
        } else {
            if (this.mPendingMoveSmoothScroller != null) {
                return true;
            }
            int movement = getMovement(i);
            int iFindImmediateChildIndex = findImmediateChildIndex(recyclerView.findFocus());
            int adapterPositionByIndex = getAdapterPositionByIndex(iFindImmediateChildIndex);
            View viewFindViewByPosition2 = adapterPositionByIndex == -1 ? null : findViewByPosition(adapterPositionByIndex);
            if (viewFindViewByPosition2 != null) {
                viewFindViewByPosition2.addFocusables(arrayList, i, i2);
            }
            if (this.mGrid == null || getChildCount() == 0) {
                return true;
            }
            if ((movement == 3 || movement == 2) && this.mGrid.getNumRows() <= 1) {
                return true;
            }
            Grid grid = this.mGrid;
            int i6 = (grid == null || viewFindViewByPosition2 == null) ? -1 : grid.getLocation(adapterPositionByIndex).row;
            int size2 = arrayList.size();
            int i7 = (movement == 1 || movement == 3) ? 1 : -1;
            int childCount3 = i7 > 0 ? getChildCount() - 1 : 0;
            int childCount4 = iFindImmediateChildIndex == -1 ? i7 > 0 ? 0 : getChildCount() - 1 : iFindImmediateChildIndex + i7;
            while (true) {
                if (i7 <= 0) {
                    if (childCount4 < childCount3) {
                        break;
                    }
                    childAt = getChildAt(childCount4);
                    if (childAt.getVisibility() != 0) {
                    }
                    childCount4 += i7;
                } else {
                    if (childCount4 > childCount3) {
                        break;
                    }
                    childAt = getChildAt(childCount4);
                    if (childAt.getVisibility() != 0 && childAt.hasFocusable()) {
                        if (viewFindViewByPosition2 == null) {
                            childAt.addFocusables(arrayList, i, i2);
                            if (arrayList.size() > size2) {
                                break;
                            }
                        } else {
                            int adapterPositionByIndex2 = getAdapterPositionByIndex(childCount4);
                            Grid.Location location = this.mGrid.getLocation(adapterPositionByIndex2);
                            if (location != null) {
                                if (movement == 1) {
                                    if (location.row == i6 && adapterPositionByIndex2 > adapterPositionByIndex) {
                                        childAt.addFocusables(arrayList, i, i2);
                                        if (arrayList.size() > size2) {
                                            break;
                                        }
                                    }
                                } else if (movement == 0) {
                                    if (location.row == i6 && adapterPositionByIndex2 < adapterPositionByIndex) {
                                        childAt.addFocusables(arrayList, i, i2);
                                        if (arrayList.size() > size2) {
                                            break;
                                        }
                                    }
                                } else if (movement == 3) {
                                    int i8 = location.row;
                                    if (i8 != i6) {
                                        if (i8 < i6) {
                                            break;
                                        }
                                        childAt.addFocusables(arrayList, i, i2);
                                    }
                                } else if (movement == 2 && (i3 = location.row) != i6) {
                                    if (i3 > i6) {
                                        break;
                                    }
                                    childAt.addFocusables(arrayList, i, i2);
                                }
                            }
                        }
                    }
                    childCount4 += i7;
                }
            }
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        saveContext(recycler, state);
        int itemCount = state.getItemCount();
        boolean z = (this.mFlag & 262144) != 0;
        if (itemCount > 1 && !isItemFullyVisible(0)) {
            if (Build.VERSION.SDK_INT < 23) {
                accessibilityNodeInfoCompat.addAction(8192);
            } else if (this.mOrientation == 0) {
                accessibilityNodeInfoCompat.addAction(z ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT);
            } else {
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP);
            }
            accessibilityNodeInfoCompat.setScrollable(true);
        }
        if (itemCount > 1 && !isItemFullyVisible(itemCount - 1)) {
            if (Build.VERSION.SDK_INT < 23) {
                accessibilityNodeInfoCompat.addAction(4096);
            } else if (this.mOrientation == 0) {
                accessibilityNodeInfoCompat.addAction(z ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT);
            } else {
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN);
            }
            accessibilityNodeInfoCompat.setScrollable(true);
        }
        accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), isLayoutHierarchical(recycler, state), getSelectionModeForAccessibility(recycler, state)));
        leaveContext();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (this.mGrid == null || !(layoutParams instanceof LayoutParams)) {
            return;
        }
        int viewAdapterPosition = ((LayoutParams) layoutParams).getViewAdapterPosition();
        int rowIndex = viewAdapterPosition >= 0 ? this.mGrid.getRowIndex(viewAdapterPosition) : -1;
        if (rowIndex < 0) {
            return;
        }
        int numRows = viewAdapterPosition / this.mGrid.getNumRows();
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(rowIndex, 1, numRows, 1, false, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(numRows, 1, rowIndex, 1, false, false));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public View onInterceptFocusSearch(View view, int i) {
        View viewFindNextFocus;
        if ((this.mFlag & 32768) != 0) {
            return view;
        }
        FocusFinder focusFinder = FocusFinder.getInstance();
        View viewFindNextFocus2 = null;
        if (i == 2 || i == 1) {
            if (canScrollVertically()) {
                viewFindNextFocus2 = focusFinder.findNextFocus(this.mBaseGridView, view, i == 2 ? TsExtractor.TS_STREAM_TYPE_HDMV_DTS : 33);
            }
            if (canScrollHorizontally()) {
                viewFindNextFocus = focusFinder.findNextFocus(this.mBaseGridView, view, (getLayoutDirection() == 1) ^ (i == 2) ? 66 : 17);
            } else {
                viewFindNextFocus = viewFindNextFocus2;
            }
        } else {
            viewFindNextFocus = focusFinder.findNextFocus(this.mBaseGridView, view, i);
        }
        if (viewFindNextFocus != null) {
            return viewFindNextFocus;
        }
        if (this.mBaseGridView.getDescendantFocusability() == 393216) {
            return this.mBaseGridView.getParent().focusSearch(view, i);
        }
        int movement = getMovement(i);
        boolean z = this.mBaseGridView.getScrollState() != 0;
        if (movement == 1) {
            if (z || (this.mFlag & 4096) == 0) {
                viewFindNextFocus = view;
            }
            if ((this.mFlag & 131072) != 0 && !hasCreatedLastItem()) {
                processPendingMovement(true);
                viewFindNextFocus = view;
            }
        } else if (movement == 0) {
            if (z || (this.mFlag & 2048) == 0) {
                viewFindNextFocus = view;
            }
            if ((this.mFlag & 131072) != 0 && !hasCreatedFirstItem()) {
                processPendingMovement(false);
                viewFindNextFocus = view;
            }
        } else if (movement == 3) {
        }
        if (viewFindNextFocus != null) {
            return viewFindNextFocus;
        }
        View viewFocusSearch = this.mBaseGridView.getParent().focusSearch(view, i);
        if (viewFocusSearch != null) {
            return viewFocusSearch;
        }
        return view != null ? view : this.mBaseGridView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        Grid grid;
        int i3;
        if (this.mFocusPosition != -1 && (grid = this.mGrid) != null && grid.getFirstVisibleIndex() >= 0 && (i3 = this.mFocusPositionOffset) != Integer.MIN_VALUE && i <= this.mFocusPosition + i3) {
            this.mFocusPositionOffset = i3 + i2;
        }
        this.mChildrenStates.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsChanged(RecyclerView recyclerView) {
        this.mFocusPositionOffset = 0;
        this.mChildrenStates.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        int i4;
        int i5 = this.mFocusPosition;
        if (i5 != -1 && (i4 = this.mFocusPositionOffset) != Integer.MIN_VALUE) {
            int i6 = i5 + i4;
            if (i <= i6 && i6 < i + i3) {
                this.mFocusPositionOffset = (i2 - i) + i4;
            } else if (i < i6 && i2 > i6 - i3) {
                this.mFocusPositionOffset = i4 - i3;
            } else if (i > i6 && i2 < i6) {
                this.mFocusPositionOffset = i4 + i3;
            }
        }
        this.mChildrenStates.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        Grid grid;
        int i3;
        int i4;
        int i5;
        if (this.mFocusPosition != -1 && (grid = this.mGrid) != null && grid.getFirstVisibleIndex() >= 0 && (i3 = this.mFocusPositionOffset) != Integer.MIN_VALUE && i <= (i5 = (i4 = this.mFocusPosition) + i3)) {
            if (i + i2 > i5) {
                this.mFocusPosition = (i - i5) + i3 + i4;
                this.mFocusPositionOffset = Integer.MIN_VALUE;
            } else {
                this.mFocusPositionOffset = i3 - i2;
            }
        }
        this.mChildrenStates.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            this.mChildrenStates.remove(i);
            i++;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int remainingScrollHorizontal;
        int remainingScrollVertical;
        int i;
        int i2;
        List<RecyclerView.ViewHolder> scrapList;
        int size;
        int i3;
        Grid.Location location;
        if (this.mNumRows != 0 && state.getItemCount() >= 0) {
            if ((this.mFlag & 64) != 0 && getChildCount() > 0) {
                this.mFlag |= 128;
                return;
            }
            int i4 = this.mFlag;
            if ((i4 & 512) == 0) {
                discardLayoutInfo();
                removeAndRecycleAllViews(recycler);
                return;
            }
            this.mFlag = (i4 & (-4)) | 1;
            saveContext(recycler, state);
            int iMax = Integer.MIN_VALUE;
            if (state.isPreLayout()) {
                updatePositionDeltaInPreLayout();
                int childCount = getChildCount();
                if (this.mGrid != null && childCount > 0) {
                    int iMin = Integer.MAX_VALUE;
                    int oldPosition = this.mBaseGridView.getChildViewHolder(getChildAt(0)).getOldPosition();
                    int oldPosition2 = this.mBaseGridView.getChildViewHolder(getChildAt(childCount - 1)).getOldPosition();
                    for (int i5 = 0; i5 < childCount; i5++) {
                        View childAt = getChildAt(i5);
                        LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                        int childAdapterPosition = this.mBaseGridView.getChildAdapterPosition(childAt);
                        if (layoutParams.isItemChanged() || layoutParams.isItemRemoved() || childAt.isLayoutRequested() || ((!childAt.hasFocus() && this.mFocusPosition == layoutParams.getViewAdapterPosition()) || ((childAt.hasFocus() && this.mFocusPosition != layoutParams.getViewAdapterPosition()) || childAdapterPosition < oldPosition || childAdapterPosition > oldPosition2))) {
                            iMin = Math.min(iMin, getViewMin(childAt));
                            iMax = Math.max(iMax, getViewMax(childAt));
                        }
                    }
                    if (iMax > iMin) {
                        this.mExtraLayoutSpaceInPreLayout = iMax - iMin;
                    }
                    appendVisibleItems();
                    prependVisibleItems();
                }
                this.mFlag &= -4;
                leaveContext();
                return;
            }
            if (state.willRunPredictiveAnimations()) {
                this.mPositionToRowInPostLayout.clear();
                int childCount2 = getChildCount();
                for (int i6 = 0; i6 < childCount2; i6++) {
                    int oldPosition3 = this.mBaseGridView.getChildViewHolder(getChildAt(i6)).getOldPosition();
                    if (oldPosition3 >= 0 && (location = this.mGrid.getLocation(oldPosition3)) != null) {
                        this.mPositionToRowInPostLayout.put(oldPosition3, location.row);
                    }
                }
            }
            boolean z = !isSmoothScrolling() && this.mFocusScrollStrategy == 0;
            int i7 = this.mFocusPosition;
            if (i7 != -1 && (i3 = this.mFocusPositionOffset) != Integer.MIN_VALUE) {
                this.mFocusPosition = i7 + i3;
                this.mSubFocusPosition = 0;
            }
            this.mFocusPositionOffset = 0;
            View viewFindViewByPosition = findViewByPosition(this.mFocusPosition);
            int i8 = this.mFocusPosition;
            int i9 = this.mSubFocusPosition;
            boolean zHasFocus = this.mBaseGridView.hasFocus();
            Grid grid = this.mGrid;
            int firstVisibleIndex = grid != null ? grid.getFirstVisibleIndex() : -1;
            Grid grid2 = this.mGrid;
            int lastVisibleIndex = grid2 != null ? grid2.getLastVisibleIndex() : -1;
            if (this.mOrientation == 0) {
                remainingScrollVertical = state.getRemainingScrollHorizontal();
                remainingScrollHorizontal = state.getRemainingScrollVertical();
            } else {
                remainingScrollHorizontal = state.getRemainingScrollHorizontal();
                remainingScrollVertical = state.getRemainingScrollVertical();
            }
            if (layoutInit()) {
                this.mFlag |= 4;
                this.mGrid.setStart(this.mFocusPosition);
                fastRelayout();
            } else {
                int i10 = this.mFlag & (-5);
                this.mFlag = i10;
                this.mFlag = (i10 & (-17)) | (zHasFocus ? 16 : 0);
                if (z && (firstVisibleIndex < 0 || (i = this.mFocusPosition) > lastVisibleIndex || i < firstVisibleIndex)) {
                    firstVisibleIndex = this.mFocusPosition;
                    lastVisibleIndex = firstVisibleIndex;
                }
                this.mGrid.setStart(firstVisibleIndex);
                if (lastVisibleIndex != -1) {
                    while (appendOneColumnVisibleItems() && findViewByPosition(lastVisibleIndex) == null) {
                    }
                }
            }
            while (true) {
                updateScrollLimits();
                int firstVisibleIndex2 = this.mGrid.getFirstVisibleIndex();
                int lastVisibleIndex2 = this.mGrid.getLastVisibleIndex();
                focusToViewInLayout(zHasFocus, z, -remainingScrollVertical, -remainingScrollHorizontal);
                appendVisibleItems();
                prependVisibleItems();
                if (this.mGrid.getFirstVisibleIndex() == firstVisibleIndex2 && this.mGrid.getLastVisibleIndex() == lastVisibleIndex2) {
                    break;
                }
            }
            removeInvisibleViewsAtFront();
            removeInvisibleViewsAtEnd();
            if (state.willRunPredictiveAnimations() && (size = (scrapList = this.mRecycler.getScrapList()).size()) != 0) {
                int[] iArr = this.mDisappearingPositions;
                if (iArr == null || size > iArr.length) {
                    int length = iArr == null ? 16 : iArr.length;
                    while (length < size) {
                        length <<= 1;
                    }
                    this.mDisappearingPositions = new int[length];
                }
                int i11 = 0;
                for (int i12 = 0; i12 < size; i12++) {
                    int adapterPosition = scrapList.get(i12).getAdapterPosition();
                    if (adapterPosition >= 0) {
                        this.mDisappearingPositions[i11] = adapterPosition;
                        i11++;
                    }
                }
                if (i11 > 0) {
                    Arrays.sort(this.mDisappearingPositions, 0, i11);
                    this.mGrid.fillDisappearingItems(this.mDisappearingPositions, i11, this.mPositionToRowInPostLayout);
                }
                this.mPositionToRowInPostLayout.clear();
            }
            int i13 = this.mFlag;
            if ((i13 & 1024) != 0) {
                this.mFlag = i13 & (-1025);
            } else {
                updateRowSecondarySizeRefresh();
            }
            if (((this.mFlag & 4) != 0 && ((i2 = this.mFocusPosition) != i8 || this.mSubFocusPosition != i9 || findViewByPosition(i2) != viewFindViewByPosition || (this.mFlag & 8) != 0)) || (this.mFlag & 20) == 16) {
                dispatchChildSelected();
            }
            dispatchChildSelectedAndPositioned();
            if ((this.mFlag & 64) != 0) {
                scrollDirectionPrimary(getSlideOutDistance());
            }
            this.mFlag &= -4;
            leaveContext();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int i, int i2) {
        int size;
        int size2;
        int mode;
        int paddingLeft;
        int paddingRight;
        int sizeSecondary;
        saveContext(recycler, state);
        if (this.mOrientation == 0) {
            size2 = View.MeasureSpec.getSize(i);
            size = View.MeasureSpec.getSize(i2);
            mode = View.MeasureSpec.getMode(i2);
            paddingLeft = getPaddingTop();
            paddingRight = getPaddingBottom();
        } else {
            size = View.MeasureSpec.getSize(i);
            size2 = View.MeasureSpec.getSize(i2);
            mode = View.MeasureSpec.getMode(i);
            paddingLeft = getPaddingLeft();
            paddingRight = getPaddingRight();
        }
        int i3 = paddingRight + paddingLeft;
        this.mMaxSizeSecondary = size;
        int i4 = this.mRowSizeSecondaryRequested;
        if (i4 == -2) {
            int i5 = this.mNumRowsRequested;
            if (i5 == 0) {
                i5 = 1;
            }
            this.mNumRows = i5;
            this.mFixedRowSizeSecondary = 0;
            int[] iArr = this.mRowSizeSecondary;
            if (iArr == null || iArr.length != i5) {
                this.mRowSizeSecondary = new int[i5];
            }
            if (this.mState.isPreLayout()) {
                updatePositionDeltaInPreLayout();
            }
            processRowSizeSecondary(true);
            if (mode == Integer.MIN_VALUE) {
                size = Math.min(getSizeSecondary() + i3, this.mMaxSizeSecondary);
            } else if (mode == 0) {
                sizeSecondary = getSizeSecondary();
                size = sizeSecondary + i3;
            } else {
                if (mode != 1073741824) {
                    throw new IllegalStateException("wrong spec");
                }
                size = this.mMaxSizeSecondary;
            }
        } else {
            if (mode != Integer.MIN_VALUE) {
                if (mode == 0) {
                    if (i4 == 0) {
                        i4 = size - i3;
                    }
                    this.mFixedRowSizeSecondary = i4;
                    int i6 = this.mNumRowsRequested;
                    if (i6 == 0) {
                        i6 = 1;
                    }
                    this.mNumRows = i6;
                    sizeSecondary = ((i6 - 1) * this.mSpacingSecondary) + (i4 * i6);
                    size = sizeSecondary + i3;
                } else if (mode != 1073741824) {
                    throw new IllegalStateException("wrong spec");
                }
            }
            int i7 = this.mNumRowsRequested;
            if (i7 == 0 && i4 == 0) {
                this.mNumRows = 1;
                this.mFixedRowSizeSecondary = size - i3;
            } else if (i7 == 0) {
                this.mFixedRowSizeSecondary = i4;
                int i8 = this.mSpacingSecondary;
                this.mNumRows = (size + i8) / (i4 + i8);
            } else if (i4 == 0) {
                this.mNumRows = i7;
                this.mFixedRowSizeSecondary = ((size - i3) - ((i7 - 1) * this.mSpacingSecondary)) / i7;
            } else {
                this.mNumRows = i7;
                this.mFixedRowSizeSecondary = i4;
            }
            if (mode == Integer.MIN_VALUE) {
                int i9 = this.mFixedRowSizeSecondary;
                int i10 = this.mNumRows;
                int i11 = ((i10 - 1) * this.mSpacingSecondary) + (i9 * i10) + i3;
                if (i11 < size) {
                    size = i11;
                }
            }
        }
        if (this.mOrientation == 0) {
            setMeasuredDimension(size2, size);
        } else {
            setMeasuredDimension(size, size2);
        }
        leaveContext();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
        if ((this.mFlag & 32768) == 0 && getAdapterPositionByView(view) != -1 && (this.mFlag & 35) == 0) {
            scrollToView(view, view2, true);
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.mFocusPosition = savedState.index;
            this.mFocusPositionOffset = 0;
            this.mChildrenStates.loadFromBundle(savedState.childStates);
            this.mFlag |= 256;
            requestLayout();
        }
    }

    /* JADX WARN: Code duplicated, block: B:6:0x000b  */
    public void onRtlPropertiesChanged(int i) {
        int i2;
        if (this.mOrientation == 0) {
            if (i == 1) {
                i2 = 262144;
            } else {
                i2 = 0;
            }
        } else if (i == 1) {
            i2 = 524288;
        } else {
            i2 = 0;
        }
        int i3 = this.mFlag;
        if ((786432 & i3) == i2) {
            return;
        }
        this.mFlag = i2 | (i3 & (-786433)) | 256;
        this.mWindowAlignment.horizontal.setReversedFlow(i == 1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.index = getSelection();
        Bundle bundleSaveAsBundle = this.mChildrenStates.saveAsBundle();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int adapterPositionByView = getAdapterPositionByView(childAt);
            if (adapterPositionByView != -1) {
                bundleSaveAsBundle = this.mChildrenStates.saveOnScreenView(bundleSaveAsBundle, childAt, adapterPositionByView);
            }
        }
        savedState.childStates = bundleSaveAsBundle;
        return savedState;
    }

    /* JADX WARN: Code duplicated, block: B:16:0x002e  */
    /* JADX WARN: Code duplicated, block: B:17:0x0031  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean performAccessibilityAction(RecyclerView.Recycler recycler, RecyclerView.State state, int i, Bundle bundle) {
        if (!isScrollEnabled()) {
            return true;
        }
        saveContext(recycler, state);
        boolean z = (this.mFlag & 262144) != 0;
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.mOrientation == 0) {
                if (i == AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId()) {
                    if (z) {
                        i = 4096;
                    } else {
                        i = 8192;
                    }
                } else if (i == AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId()) {
                    if (z) {
                        i = 8192;
                    } else {
                        i = 4096;
                    }
                }
            } else if (i == AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId()) {
                i = 8192;
            } else if (i == AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId()) {
                i = 4096;
            }
        }
        if (i == 4096) {
            processPendingMovement(true);
            processSelectionMoves(false, 1);
        } else if (i == 8192) {
            processPendingMovement(false);
            processSelectionMoves(false, -1);
        }
        leaveContext();
        return true;
    }

    public final int processSelectionMoves(boolean z, int i) {
        Grid grid = this.mGrid;
        if (grid == null) {
            return i;
        }
        int i2 = this.mFocusPosition;
        int rowIndex = i2 != -1 ? grid.getRowIndex(i2) : -1;
        View view = null;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount && i != 0; i3++) {
            int i4 = i > 0 ? i3 : (childCount - 1) - i3;
            View childAt = getChildAt(i4);
            if (canScrollTo(childAt)) {
                int adapterPositionByIndex = getAdapterPositionByIndex(i4);
                int rowIndex2 = this.mGrid.getRowIndex(adapterPositionByIndex);
                if (rowIndex == -1) {
                    i2 = adapterPositionByIndex;
                    view = childAt;
                    rowIndex = rowIndex2;
                } else if (rowIndex2 == rowIndex && ((i > 0 && adapterPositionByIndex > i2) || (i < 0 && adapterPositionByIndex < i2))) {
                    i = i > 0 ? i - 1 : i + 1;
                    i2 = adapterPositionByIndex;
                    view = childAt;
                }
            }
        }
        if (view != null) {
            if (z) {
                if (hasFocus()) {
                    this.mFlag |= 32;
                    view.requestFocus();
                    this.mFlag &= -33;
                }
                this.mFocusPosition = i2;
                this.mSubFocusPosition = 0;
            } else {
                scrollToView(view, true);
            }
        }
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            removeAndRecycleViewAt(childCount, recycler);
        }
    }

    public void removeOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener onChildViewHolderSelectedListener) {
        ArrayList<OnChildViewHolderSelectedListener> arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList != null) {
            arrayList.remove(onChildViewHolderSelectedListener);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z) {
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if ((this.mFlag & 512) == 0 || !hasDoneFirstLayout()) {
            return 0;
        }
        saveContext(recycler, state);
        this.mFlag = (this.mFlag & (-4)) | 2;
        int iScrollDirectionPrimary = this.mOrientation == 0 ? scrollDirectionPrimary(i) : scrollDirectionSecondary(i);
        leaveContext();
        this.mFlag &= -4;
        return iScrollDirectionPrimary;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int i) {
        setSelection(i, 0, false, 0);
    }

    public final void scrollToSelection(int i, int i2, boolean z, int i3) {
        this.mPrimaryScrollExtra = i3;
        View viewFindViewByPosition = findViewByPosition(i);
        boolean z2 = !isSmoothScrolling();
        if (z2 && !this.mBaseGridView.isLayoutRequested() && viewFindViewByPosition != null && getAdapterPositionByView(viewFindViewByPosition) == i) {
            this.mFlag |= 32;
            scrollToView(viewFindViewByPosition, z);
            this.mFlag &= -33;
            return;
        }
        int i4 = this.mFlag;
        if ((i4 & 512) == 0 || (i4 & 64) != 0) {
            this.mFocusPosition = i;
            this.mSubFocusPosition = i2;
            this.mFocusPositionOffset = Integer.MIN_VALUE;
            return;
        }
        if (z && !this.mBaseGridView.isLayoutRequested()) {
            this.mFocusPosition = i;
            this.mSubFocusPosition = i2;
            this.mFocusPositionOffset = Integer.MIN_VALUE;
            if (!hasDoneFirstLayout()) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("GridLayoutManager:");
                sbM.append(this.mBaseGridView.getId());
                Log.w(sbM.toString(), "setSelectionSmooth should not be called before first layout pass");
                return;
            }
            GridLinearSmoothScroller gridLinearSmoothScroller = new GridLinearSmoothScroller() { // from class: androidx.leanback.widget.GridLayoutManager.4
                @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
                public PointF computeScrollVectorForPosition(int i5) {
                    if (getChildCount() == 0) {
                        return null;
                    }
                    GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                    boolean z3 = false;
                    int position = gridLayoutManager.getPosition(gridLayoutManager.getChildAt(0));
                    GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                    if ((gridLayoutManager2.mFlag & 262144) == 0 ? i5 < position : i5 > position) {
                        z3 = true;
                    }
                    int i6 = z3 ? -1 : 1;
                    return gridLayoutManager2.mOrientation == 0 ? new PointF(i6, 0.0f) : new PointF(0.0f, i6);
                }
            };
            gridLinearSmoothScroller.setTargetPosition(i);
            startSmoothScroll(gridLinearSmoothScroller);
            int targetPosition = gridLinearSmoothScroller.getTargetPosition();
            if (targetPosition != this.mFocusPosition) {
                this.mFocusPosition = targetPosition;
                this.mSubFocusPosition = 0;
                return;
            }
            return;
        }
        if (!z2) {
            GridLinearSmoothScroller gridLinearSmoothScroller2 = this.mCurrentSmoothScroller;
            if (gridLinearSmoothScroller2 != null) {
                gridLinearSmoothScroller2.mSkipOnStopInternal = true;
            }
            this.mBaseGridView.stopScroll();
        }
        if (!this.mBaseGridView.isLayoutRequested() && viewFindViewByPosition != null && getAdapterPositionByView(viewFindViewByPosition) == i) {
            this.mFlag |= 32;
            scrollToView(viewFindViewByPosition, z);
            this.mFlag &= -33;
        } else {
            this.mFocusPosition = i;
            this.mSubFocusPosition = i2;
            this.mFocusPositionOffset = Integer.MIN_VALUE;
            this.mFlag |= 256;
            requestLayout();
        }
    }

    public final void scrollToView(View view, boolean z) {
        scrollToView(view, view.findFocus(), z);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if ((this.mFlag & 512) == 0 || !hasDoneFirstLayout()) {
            return 0;
        }
        this.mFlag = (this.mFlag & (-4)) | 2;
        saveContext(recycler, state);
        int iScrollDirectionPrimary = this.mOrientation == 1 ? scrollDirectionPrimary(i) : scrollDirectionSecondary(i);
        leaveContext();
        this.mFlag &= -4;
        return iScrollDirectionPrimary;
    }

    public final void setExtraLayoutSpace(int i) {
        int i2 = this.mExtraLayoutSpace;
        if (i2 == i) {
            return;
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("ExtraLayoutSpace must >= 0");
        }
        this.mExtraLayoutSpace = i;
        requestLayout();
    }

    public void setFocusOutAllowed(boolean z, boolean z2) {
        this.mFlag = (z ? 2048 : 0) | (this.mFlag & (-6145)) | (z2 ? 4096 : 0);
    }

    public void setFocusOutSideAllowed(boolean z, boolean z2) {
        this.mFlag = (z ? 8192 : 0) | (this.mFlag & (-24577)) | (z2 ? 16384 : 0);
    }

    public void setFocusScrollStrategy(int i) {
        this.mFocusScrollStrategy = i;
    }

    public void setGravity(int i) {
        this.mGravity = i;
    }

    public void setHorizontalSpacing(int i) {
        if (this.mOrientation == 0) {
            this.mHorizontalSpacing = i;
            this.mSpacingPrimary = i;
        } else {
            this.mHorizontalSpacing = i;
            this.mSpacingSecondary = i;
        }
    }

    public void setItemAlignmentOffset(int i) {
        this.mItemAlignment.mainAxis().setItemAlignmentOffset(i);
        updateChildAlignments();
    }

    public void setItemAlignmentOffsetPercent(float f) {
        this.mItemAlignment.mainAxis().setItemAlignmentOffsetPercent(f);
        updateChildAlignments();
    }

    public void setItemAlignmentOffsetWithPadding(boolean z) {
        this.mItemAlignment.mainAxis().setItemAlignmentOffsetWithPadding(z);
        updateChildAlignments();
    }

    public void setItemAlignmentViewId(int i) {
        this.mItemAlignment.mainAxis().setItemAlignmentViewId(i);
        updateChildAlignments();
    }

    public void setItemSpacing(int i) {
        this.mHorizontalSpacing = i;
        this.mVerticalSpacing = i;
        this.mSpacingSecondary = i;
        this.mSpacingPrimary = i;
    }

    public void setLayoutEnabled(boolean z) {
        int i = this.mFlag;
        if (((i & 512) != 0) != z) {
            this.mFlag = (i & (-513)) | (z ? 512 : 0);
            requestLayout();
        }
    }

    public void setNumRows(int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        this.mNumRowsRequested = i;
    }

    public void setOnChildSelectedListener(OnChildSelectedListener onChildSelectedListener) {
        this.mChildSelectedListener = onChildSelectedListener;
    }

    public void setOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener onChildViewHolderSelectedListener) {
        if (onChildViewHolderSelectedListener == null) {
            this.mChildViewHolderSelectedListeners = null;
            return;
        }
        ArrayList<OnChildViewHolderSelectedListener> arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList == null) {
            this.mChildViewHolderSelectedListeners = new ArrayList<>();
        } else {
            arrayList.clear();
        }
        this.mChildViewHolderSelectedListeners.add(onChildViewHolderSelectedListener);
    }

    public void setOrientation(int i) {
        if (i == 0 || i == 1) {
            this.mOrientation = i;
            this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, i);
            this.mWindowAlignment.setOrientation(i);
            this.mItemAlignment.setOrientation(i);
            this.mFlag |= 256;
        }
    }

    public void setPruneChild(boolean z) {
        int i = this.mFlag;
        if (((i & 65536) != 0) != z) {
            this.mFlag = (i & (-65537)) | (z ? 65536 : 0);
            if (z) {
                requestLayout();
            }
        }
    }

    public void setRowHeight(int i) {
        if (i < 0 && i != -2) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("Invalid row height: ", i));
        }
        this.mRowSizeSecondaryRequested = i;
    }

    public void setScrollEnabled(boolean z) {
        int i;
        int i2 = this.mFlag;
        if (((i2 & 131072) != 0) != z) {
            int i3 = (i2 & (-131073)) | (z ? 131072 : 0);
            this.mFlag = i3;
            if ((i3 & 131072) == 0 || this.mFocusScrollStrategy != 0 || (i = this.mFocusPosition) == -1) {
                return;
            }
            scrollToSelection(i, this.mSubFocusPosition, true, this.mPrimaryScrollExtra);
        }
    }

    public void setSelection(int i, int i2) {
        setSelection(i, 0, false, i2);
    }

    public void setSelectionSmooth(int i) {
        setSelection(i, 0, true, 0);
    }

    public void setSelectionSmoothWithSub(int i, int i2) {
        setSelection(i, i2, true, 0);
    }

    public void setSelectionWithSub(int i, int i2, int i3) {
        setSelection(i, i2, false, i3);
    }

    public void setVerticalSpacing(int i) {
        if (this.mOrientation == 1) {
            this.mVerticalSpacing = i;
            this.mSpacingPrimary = i;
        } else {
            this.mVerticalSpacing = i;
            this.mSpacingSecondary = i;
        }
    }

    public void setWindowAlignment(int i) {
        this.mWindowAlignment.mainAxis().setWindowAlignment(i);
    }

    public void setWindowAlignmentOffset(int i) {
        this.mWindowAlignment.mainAxis().setWindowAlignmentOffset(i);
    }

    public void setWindowAlignmentOffsetPercent(float f) {
        this.mWindowAlignment.mainAxis().setWindowAlignmentOffsetPercent(f);
    }

    public final void slideIn() {
        int i = this.mFlag;
        if ((i & 64) != 0) {
            int i2 = i & (-65);
            this.mFlag = i2;
            int i3 = this.mFocusPosition;
            if (i3 >= 0) {
                scrollToSelection(i3, this.mSubFocusPosition, true, this.mPrimaryScrollExtra);
            } else {
                this.mFlag = i2 & (-129);
                requestLayout();
            }
            int i4 = this.mFlag;
            if ((i4 & 128) != 0) {
                this.mFlag = i4 & (-129);
                if (this.mBaseGridView.getScrollState() != 0 || isSmoothScrolling()) {
                    this.mBaseGridView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: androidx.leanback.widget.GridLayoutManager.3
                        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                        public void onScrollStateChanged(RecyclerView recyclerView, int i5) {
                            if (i5 == 0) {
                                GridLayoutManager.this.mBaseGridView.removeOnScrollListener(this);
                                GridLayoutManager.this.requestLayout();
                            }
                        }
                    });
                } else {
                    requestLayout();
                }
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        setSelection(i, 0, true, 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void startSmoothScroll(RecyclerView.SmoothScroller smoothScroller) {
        GridLinearSmoothScroller gridLinearSmoothScroller = this.mCurrentSmoothScroller;
        if (gridLinearSmoothScroller != null) {
            gridLinearSmoothScroller.mSkipOnStopInternal = true;
        }
        super.startSmoothScroll(smoothScroller);
        if (!smoothScroller.isRunning() || !(smoothScroller instanceof GridLinearSmoothScroller)) {
            this.mCurrentSmoothScroller = null;
            this.mPendingMoveSmoothScroller = null;
            return;
        }
        GridLinearSmoothScroller gridLinearSmoothScroller2 = (GridLinearSmoothScroller) smoothScroller;
        this.mCurrentSmoothScroller = gridLinearSmoothScroller2;
        if (gridLinearSmoothScroller2 instanceof PendingMoveSmoothScroller) {
            this.mPendingMoveSmoothScroller = (PendingMoveSmoothScroller) gridLinearSmoothScroller2;
        } else {
            this.mPendingMoveSmoothScroller = null;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }

    public final void updatePositionDeltaInPreLayout() {
        if (getChildCount() <= 0) {
            this.mPositionDeltaInPreLayout = 0;
        } else {
            this.mPositionDeltaInPreLayout = this.mGrid.getFirstVisibleIndex() - ((LayoutParams) getChildAt(0).getLayoutParams()).getViewLayoutPosition();
        }
    }

    public final void updateScrollLimits() {
        int firstVisibleIndex;
        int lastVisibleIndex;
        int itemCount;
        int itemCount2;
        int viewCenter;
        int viewCenter2;
        if (this.mState.getItemCount() == 0) {
            return;
        }
        if ((this.mFlag & 262144) == 0) {
            firstVisibleIndex = this.mGrid.getLastVisibleIndex();
            itemCount2 = this.mState.getItemCount() - 1;
            lastVisibleIndex = this.mGrid.getFirstVisibleIndex();
            itemCount = 0;
        } else {
            firstVisibleIndex = this.mGrid.getFirstVisibleIndex();
            lastVisibleIndex = this.mGrid.getLastVisibleIndex();
            itemCount = this.mState.getItemCount() - 1;
            itemCount2 = 0;
        }
        if (firstVisibleIndex < 0 || lastVisibleIndex < 0) {
            return;
        }
        boolean z = firstVisibleIndex == itemCount2;
        boolean z2 = lastVisibleIndex == itemCount;
        if (z || !this.mWindowAlignment.mainAxis().isMaxUnknown() || z2 || !this.mWindowAlignment.mainAxis().isMinUnknown()) {
            int iFindRowMax = Integer.MAX_VALUE;
            if (z) {
                iFindRowMax = this.mGrid.findRowMax(true, sTwoInts);
                View viewFindViewByPosition = findViewByPosition(sTwoInts[1]);
                viewCenter = getViewCenter(viewFindViewByPosition);
                int[] alignMultiple = ((LayoutParams) viewFindViewByPosition.getLayoutParams()).getAlignMultiple();
                if (alignMultiple != null && alignMultiple.length > 0) {
                    viewCenter = (alignMultiple[alignMultiple.length - 1] - alignMultiple[0]) + viewCenter;
                }
            } else {
                viewCenter = Integer.MAX_VALUE;
            }
            int iFindRowMin = Integer.MIN_VALUE;
            if (z2) {
                iFindRowMin = this.mGrid.findRowMin(false, sTwoInts);
                viewCenter2 = getViewCenter(findViewByPosition(sTwoInts[1]));
            } else {
                viewCenter2 = Integer.MIN_VALUE;
            }
            this.mWindowAlignment.mainAxis().updateMinMax(iFindRowMin, iFindRowMax, viewCenter2, viewCenter);
        }
    }

    private void scrollToView(View view, View view2, boolean z) {
        scrollToView(view, view2, z, 0, 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof RecyclerView.LayoutParams) {
            return new LayoutParams((RecyclerView.LayoutParams) layoutParams);
        }
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public void setSelection(int i, int i2, boolean z, int i3) {
        if ((this.mFocusPosition == i || i == -1) && i2 == this.mSubFocusPosition && i3 == this.mPrimaryScrollExtra) {
            return;
        }
        scrollToSelection(i, i2, z, i3);
    }

    private void scrollToView(View view, View view2, boolean z, int i, int i2) {
        boolean noneAlignedPosition;
        if ((this.mFlag & 64) != 0) {
            return;
        }
        int adapterPositionByView = getAdapterPositionByView(view);
        int subPositionByView = getSubPositionByView(view, view2);
        if (adapterPositionByView != this.mFocusPosition || subPositionByView != this.mSubFocusPosition) {
            this.mFocusPosition = adapterPositionByView;
            this.mSubFocusPosition = subPositionByView;
            this.mFocusPositionOffset = 0;
            if ((this.mFlag & 3) != 1) {
                dispatchChildSelected();
            }
            if (this.mBaseGridView.isChildrenDrawingOrderEnabledInternal()) {
                this.mBaseGridView.invalidate();
            }
        }
        if (view == null) {
            return;
        }
        if (!view.hasFocus() && this.mBaseGridView.hasFocus()) {
            view.requestFocus();
        }
        if ((this.mFlag & 131072) == 0 && z) {
            return;
        }
        int[] iArr = sTwoInts;
        int i3 = this.mFocusScrollStrategy;
        if (i3 != 1 && i3 != 2) {
            noneAlignedPosition = getAlignedPosition(view, view2, iArr);
        } else {
            noneAlignedPosition = getNoneAlignedPosition(view, iArr);
        }
        if (!noneAlignedPosition && i == 0 && i2 == 0) {
            return;
        }
        int[] iArr2 = sTwoInts;
        scrollGrid(iArr2[0] + i, iArr2[1] + i2, z);
    }

    public static final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: androidx.leanback.widget.GridLayoutManager.SavedState.1
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
        public Bundle childStates;
        public int index;

        public SavedState(Parcel parcel) {
            this.childStates = Bundle.EMPTY;
            this.index = parcel.readInt();
            this.childStates = parcel.readBundle(GridLayoutManager.class.getClassLoader());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.index);
            parcel.writeBundle(this.childStates);
        }

        public SavedState() {
            this.childStates = Bundle.EMPTY;
        }
    }

    private void updateChildAlignments() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            updateChildAlignments(getChildAt(i));
        }
    }
}
