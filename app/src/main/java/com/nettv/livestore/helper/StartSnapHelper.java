package com.nettv.livestore.helper;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes2.dex */
public class StartSnapHelper extends LinearSnapHelper {
    private OrientationHelper mHorizontalHelper;
    private OrientationHelper mVerticalHelper;

    private int distanceToStart(View view, OrientationHelper orientationHelper) {
        return orientationHelper.getDecoratedStart(view) - orientationHelper.getStartAfterPadding();
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (this.mHorizontalHelper == null) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.mHorizontalHelper;
    }

    private View getStartView(RecyclerView.LayoutManager layoutManager, OrientationHelper orientationHelper) {
        if (!(layoutManager instanceof LinearLayoutManager)) {
            return super.findSnapView(layoutManager);
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        boolean z = linearLayoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1;
        if (iFindFirstVisibleItemPosition == -1 || z) {
            return null;
        }
        View viewFindViewByPosition = layoutManager.findViewByPosition(iFindFirstVisibleItemPosition);
        if (orientationHelper.getDecoratedEnd(viewFindViewByPosition) >= orientationHelper.getDecoratedMeasurement(viewFindViewByPosition) / 2 && orientationHelper.getDecoratedEnd(viewFindViewByPosition) > 0) {
            return viewFindViewByPosition;
        }
        if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
            return null;
        }
        return layoutManager.findViewByPosition(iFindFirstVisibleItemPosition + 1);
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (this.mVerticalHelper == null) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return this.mVerticalHelper;
    }

    @Override // androidx.recyclerview.widget.SnapHelper
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
    }

    @Override // androidx.recyclerview.widget.LinearSnapHelper, androidx.recyclerview.widget.SnapHelper
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View view) {
        int[] iArr = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            iArr[0] = distanceToStart(view, getHorizontalHelper(layoutManager));
        } else {
            iArr[0] = 0;
        }
        if (layoutManager.canScrollVertically()) {
            iArr[1] = distanceToStart(view, getVerticalHelper(layoutManager));
        } else {
            iArr[1] = 0;
        }
        return iArr;
    }

    @Override // androidx.recyclerview.widget.LinearSnapHelper, androidx.recyclerview.widget.SnapHelper
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return layoutManager.canScrollHorizontally() ? getStartView(layoutManager, getHorizontalHelper(layoutManager)) : getStartView(layoutManager, getVerticalHelper(layoutManager));
        }
        return super.findSnapView(layoutManager);
    }
}
