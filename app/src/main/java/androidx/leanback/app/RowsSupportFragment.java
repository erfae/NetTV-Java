package androidx.leanback.app;

import android.animation.TimeAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.R;
import androidx.leanback.widget.BaseOnItemViewClickedListener;
import androidx.leanback.widget.BaseOnItemViewSelectedListener;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridView;
import androidx.leanback.widget.ViewHolderTask;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class RowsSupportFragment extends BaseRowSupportFragment implements BrowseSupportFragment.MainFragmentRowsAdapterProvider, BrowseSupportFragment.MainFragmentAdapterProvider {
    public ItemBridgeAdapter.AdapterListener mExternalAdapterListener;
    public boolean mFreezeRows;
    private MainFragmentAdapter mMainFragmentAdapter;
    private MainFragmentRowsAdapter mMainFragmentRowsAdapter;
    public BaseOnItemViewClickedListener mOnItemViewClickedListener;
    public BaseOnItemViewSelectedListener mOnItemViewSelectedListener;
    private ArrayList<Presenter> mPresenterMapper;
    private RecyclerView.RecycledViewPool mRecycledViewPool;
    public int mSelectAnimatorDuration;
    public ItemBridgeAdapter.ViewHolder mSelectedViewHolder;
    private int mSubPosition;
    public boolean mViewsCreated;
    public boolean mExpand = true;
    private int mAlignedTop = Integer.MIN_VALUE;
    public boolean mAfterEntranceTransition = true;
    public DecelerateInterpolator mSelectAnimatorInterpolator = new DecelerateInterpolator(2.0f);
    private final ItemBridgeAdapter.AdapterListener mBridgeAdapterListener = new ItemBridgeAdapter.AdapterListener() { // from class: androidx.leanback.app.RowsSupportFragment.1
        @Override // androidx.leanback.widget.ItemBridgeAdapter.AdapterListener
        public void onAddPresenter(Presenter presenter, int i) {
            ItemBridgeAdapter.AdapterListener adapterListener = RowsSupportFragment.this.mExternalAdapterListener;
            if (adapterListener != null) {
                adapterListener.onAddPresenter(presenter, i);
            }
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter.AdapterListener
        public void onAttachedToWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            ((RowPresenter) viewHolder.getPresenter()).setRowViewExpanded(viewHolder.getViewHolder(), RowsSupportFragment.this.mExpand);
            RowPresenter rowPresenter = (RowPresenter) viewHolder.getPresenter();
            RowPresenter.ViewHolder rowViewHolder = rowPresenter.getRowViewHolder(viewHolder.getViewHolder());
            rowPresenter.setEntranceTransitionState(rowViewHolder, RowsSupportFragment.this.mAfterEntranceTransition);
            rowPresenter.freeze(rowViewHolder, RowsSupportFragment.this.mFreezeRows);
            ItemBridgeAdapter.AdapterListener adapterListener = RowsSupportFragment.this.mExternalAdapterListener;
            if (adapterListener != null) {
                adapterListener.onAttachedToWindow(viewHolder);
            }
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter.AdapterListener
        public void onBind(ItemBridgeAdapter.ViewHolder viewHolder) {
            ItemBridgeAdapter.AdapterListener adapterListener = RowsSupportFragment.this.mExternalAdapterListener;
            if (adapterListener != null) {
                adapterListener.onBind(viewHolder);
            }
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter.AdapterListener
        public void onCreate(ItemBridgeAdapter.ViewHolder viewHolder) {
            VerticalGridView verticalGridView = RowsSupportFragment.this.getVerticalGridView();
            if (verticalGridView != null) {
                verticalGridView.setClipChildren(false);
            }
            RowsSupportFragment.this.setupSharedViewPool(viewHolder);
            RowsSupportFragment rowsSupportFragment = RowsSupportFragment.this;
            rowsSupportFragment.mViewsCreated = true;
            viewHolder.setExtraObject(rowsSupportFragment.new RowViewHolderExtra(viewHolder));
            RowsSupportFragment.setRowViewSelected(viewHolder, false, true);
            ItemBridgeAdapter.AdapterListener adapterListener = RowsSupportFragment.this.mExternalAdapterListener;
            if (adapterListener != null) {
                adapterListener.onCreate(viewHolder);
            }
            RowPresenter.ViewHolder rowViewHolder = ((RowPresenter) viewHolder.getPresenter()).getRowViewHolder(viewHolder.getViewHolder());
            rowViewHolder.setOnItemViewSelectedListener(RowsSupportFragment.this.mOnItemViewSelectedListener);
            rowViewHolder.setOnItemViewClickedListener(RowsSupportFragment.this.mOnItemViewClickedListener);
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter.AdapterListener
        public void onDetachedFromWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            ItemBridgeAdapter.ViewHolder viewHolder2 = RowsSupportFragment.this.mSelectedViewHolder;
            if (viewHolder2 == viewHolder) {
                RowsSupportFragment.setRowViewSelected(viewHolder2, false, true);
                RowsSupportFragment.this.mSelectedViewHolder = null;
            }
            ItemBridgeAdapter.AdapterListener adapterListener = RowsSupportFragment.this.mExternalAdapterListener;
            if (adapterListener != null) {
                adapterListener.onDetachedFromWindow(viewHolder);
            }
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter.AdapterListener
        public void onUnbind(ItemBridgeAdapter.ViewHolder viewHolder) {
            RowsSupportFragment.setRowViewSelected(viewHolder, false, true);
            ItemBridgeAdapter.AdapterListener adapterListener = RowsSupportFragment.this.mExternalAdapterListener;
            if (adapterListener != null) {
                adapterListener.onUnbind(viewHolder);
            }
        }
    };

    public static class MainFragmentAdapter extends BrowseSupportFragment.MainFragmentAdapter<RowsSupportFragment> {
        public MainFragmentAdapter(RowsSupportFragment rowsSupportFragment) {
            super(rowsSupportFragment);
            setScalingEnabled(true);
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentAdapter
        public boolean isScrolling() {
            return getFragment().isScrolling();
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentAdapter
        public void onTransitionEnd() {
            getFragment().onTransitionEnd();
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentAdapter
        public boolean onTransitionPrepare() {
            return getFragment().onTransitionPrepare();
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentAdapter
        public void onTransitionStart() {
            getFragment().onTransitionStart();
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentAdapter
        public void setAlignment(int i) {
            getFragment().setAlignment(i);
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentAdapter
        public void setEntranceTransitionState(boolean z) {
            getFragment().setEntranceTransitionState(z);
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentAdapter
        public void setExpand(boolean z) {
            getFragment().setExpand(z);
        }
    }

    public static class MainFragmentRowsAdapter extends BrowseSupportFragment.MainFragmentRowsAdapter<RowsSupportFragment> {
        public MainFragmentRowsAdapter(RowsSupportFragment rowsSupportFragment) {
            super(rowsSupportFragment);
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentRowsAdapter
        public RowPresenter.ViewHolder findRowViewHolderByPosition(int i) {
            return getFragment().findRowViewHolderByPosition(i);
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentRowsAdapter
        public int getSelectedPosition() {
            return getFragment().getSelectedPosition();
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentRowsAdapter
        public void setAdapter(ObjectAdapter objectAdapter) {
            getFragment().setAdapter(objectAdapter);
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentRowsAdapter
        public void setOnItemViewClickedListener(OnItemViewClickedListener onItemViewClickedListener) {
            getFragment().setOnItemViewClickedListener(onItemViewClickedListener);
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentRowsAdapter
        public void setOnItemViewSelectedListener(OnItemViewSelectedListener onItemViewSelectedListener) {
            getFragment().setOnItemViewSelectedListener(onItemViewSelectedListener);
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentRowsAdapter
        public void setSelectedPosition(int i, boolean z, Presenter.ViewHolderTask viewHolderTask) {
            getFragment().setSelectedPosition(i, z, viewHolderTask);
        }

        @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentRowsAdapter
        public void setSelectedPosition(int i, boolean z) {
            getFragment().setSelectedPosition(i, z);
        }
    }

    public final class RowViewHolderExtra implements TimeAnimator.TimeListener {
        public final RowPresenter mRowPresenter;
        public final Presenter.ViewHolder mRowViewHolder;
        public final TimeAnimator mSelectAnimator;
        public int mSelectAnimatorDurationInUse;
        public DecelerateInterpolator mSelectAnimatorInterpolatorInUse;
        public float mSelectLevelAnimDelta;
        public float mSelectLevelAnimStart;

        public RowViewHolderExtra(ItemBridgeAdapter.ViewHolder viewHolder) {
            TimeAnimator timeAnimator = new TimeAnimator();
            this.mSelectAnimator = timeAnimator;
            this.mRowPresenter = (RowPresenter) viewHolder.getPresenter();
            this.mRowViewHolder = viewHolder.getViewHolder();
            timeAnimator.setTimeListener(this);
        }

        @Override // android.animation.TimeAnimator.TimeListener
        public void onTimeUpdate(TimeAnimator timeAnimator, long j, long j2) {
            float interpolation;
            if (this.mSelectAnimator.isRunning()) {
                int i = this.mSelectAnimatorDurationInUse;
                if (j >= i) {
                    interpolation = 1.0f;
                    this.mSelectAnimator.end();
                } else {
                    interpolation = (float) (j / ((double) i));
                }
                DecelerateInterpolator decelerateInterpolator = this.mSelectAnimatorInterpolatorInUse;
                if (decelerateInterpolator != null) {
                    interpolation = decelerateInterpolator.getInterpolation(interpolation);
                }
                this.mRowPresenter.setSelectLevel(this.mRowViewHolder, (interpolation * this.mSelectLevelAnimDelta) + this.mSelectLevelAnimStart);
            }
        }
    }

    private void freezeRows(boolean z) {
        this.mFreezeRows = z;
        VerticalGridView verticalGridView = getVerticalGridView();
        if (verticalGridView != null) {
            int childCount = verticalGridView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ItemBridgeAdapter.ViewHolder viewHolder = (ItemBridgeAdapter.ViewHolder) verticalGridView.getChildViewHolder(verticalGridView.getChildAt(i));
                RowPresenter rowPresenter = (RowPresenter) viewHolder.getPresenter();
                rowPresenter.freeze(rowPresenter.getRowViewHolder(viewHolder.getViewHolder()), z);
            }
        }
    }

    public static void setRowViewSelected(ItemBridgeAdapter.ViewHolder viewHolder, boolean z, boolean z2) {
        RowViewHolderExtra rowViewHolderExtra = (RowViewHolderExtra) viewHolder.getExtraObject();
        rowViewHolderExtra.mSelectAnimator.end();
        float f = z ? 1.0f : 0.0f;
        if (z2) {
            rowViewHolderExtra.mRowPresenter.setSelectLevel(rowViewHolderExtra.mRowViewHolder, f);
        } else if (rowViewHolderExtra.mRowPresenter.getSelectLevel(rowViewHolderExtra.mRowViewHolder) != f) {
            RowsSupportFragment rowsSupportFragment = RowsSupportFragment.this;
            rowViewHolderExtra.mSelectAnimatorDurationInUse = rowsSupportFragment.mSelectAnimatorDuration;
            rowViewHolderExtra.mSelectAnimatorInterpolatorInUse = rowsSupportFragment.mSelectAnimatorInterpolator;
            float selectLevel = rowViewHolderExtra.mRowPresenter.getSelectLevel(rowViewHolderExtra.mRowViewHolder);
            rowViewHolderExtra.mSelectLevelAnimStart = selectLevel;
            rowViewHolderExtra.mSelectLevelAnimDelta = f - selectLevel;
            rowViewHolderExtra.mSelectAnimator.start();
        }
        ((RowPresenter) viewHolder.getPresenter()).setRowViewSelected(viewHolder.getViewHolder(), z);
    }

    @Deprecated
    public void enableRowScaling(boolean z) {
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment
    public final VerticalGridView findGridViewFromRoot(View view) {
        return (VerticalGridView) view.findViewById(R.id.container_list);
    }

    public RowPresenter.ViewHolder findRowViewHolderByPosition(int i) {
        VerticalGridView verticalGridView = this.mVerticalGridView;
        if (verticalGridView == null) {
            return null;
        }
        return getRowViewHolder((ItemBridgeAdapter.ViewHolder) verticalGridView.findViewHolderForAdapterPosition(i));
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment
    public final int getLayoutResourceId() {
        return R.layout.lb_rows_fragment;
    }

    @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentAdapterProvider
    public BrowseSupportFragment.MainFragmentAdapter getMainFragmentAdapter() {
        if (this.mMainFragmentAdapter == null) {
            this.mMainFragmentAdapter = new MainFragmentAdapter(this);
        }
        return this.mMainFragmentAdapter;
    }

    @Override // androidx.leanback.app.BrowseSupportFragment.MainFragmentRowsAdapterProvider
    public BrowseSupportFragment.MainFragmentRowsAdapter getMainFragmentRowsAdapter() {
        if (this.mMainFragmentRowsAdapter == null) {
            this.mMainFragmentRowsAdapter = new MainFragmentRowsAdapter(this);
        }
        return this.mMainFragmentRowsAdapter;
    }

    public BaseOnItemViewClickedListener getOnItemViewClickedListener() {
        return this.mOnItemViewClickedListener;
    }

    public BaseOnItemViewSelectedListener getOnItemViewSelectedListener() {
        return this.mOnItemViewSelectedListener;
    }

    public RowPresenter.ViewHolder getRowViewHolder(int i) {
        VerticalGridView verticalGridView = getVerticalGridView();
        if (verticalGridView == null) {
            return null;
        }
        return getRowViewHolder((ItemBridgeAdapter.ViewHolder) verticalGridView.findViewHolderForAdapterPosition(i));
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment
    public /* bridge */ /* synthetic */ int getSelectedPosition() {
        return super.getSelectedPosition();
    }

    public boolean isScrolling() {
        return (getVerticalGridView() == null || getVerticalGridView().getScrollState() == 0) ? false : true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSelectAnimatorDuration = getResources().getInteger(R.integer.lb_browse_rows_anim_duration);
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment, androidx.fragment.app.Fragment
    public /* bridge */ /* synthetic */ View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.mViewsCreated = false;
        super.onDestroyView();
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment
    public final void onRowSelected(RecyclerView.ViewHolder viewHolder, int i, int i2) {
        ItemBridgeAdapter.ViewHolder viewHolder2 = this.mSelectedViewHolder;
        if (viewHolder2 != viewHolder || this.mSubPosition != i2) {
            this.mSubPosition = i2;
            if (viewHolder2 != null) {
                setRowViewSelected(viewHolder2, false, false);
            }
            ItemBridgeAdapter.ViewHolder viewHolder3 = (ItemBridgeAdapter.ViewHolder) viewHolder;
            this.mSelectedViewHolder = viewHolder3;
            if (viewHolder3 != null) {
                setRowViewSelected(viewHolder3, true, false);
            }
        }
        MainFragmentAdapter mainFragmentAdapter = this.mMainFragmentAdapter;
        if (mainFragmentAdapter != null) {
            mainFragmentAdapter.getFragmentHost().showTitleView(i <= 0);
        }
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment, androidx.fragment.app.Fragment
    public /* bridge */ /* synthetic */ void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment
    public void onTransitionEnd() {
        super.onTransitionEnd();
        freezeRows(false);
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment
    public boolean onTransitionPrepare() {
        boolean zOnTransitionPrepare = super.onTransitionPrepare();
        if (zOnTransitionPrepare) {
            freezeRows(true);
        }
        return zOnTransitionPrepare;
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment
    public /* bridge */ /* synthetic */ void onTransitionStart() {
        super.onTransitionStart();
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment, androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        getVerticalGridView().setItemAlignmentViewId(R.id.row_content);
        getVerticalGridView().setSaveChildrenPolicy(2);
        setAlignment(this.mAlignedTop);
        this.mRecycledViewPool = null;
        this.mPresenterMapper = null;
        MainFragmentAdapter mainFragmentAdapter = this.mMainFragmentAdapter;
        if (mainFragmentAdapter != null) {
            mainFragmentAdapter.getFragmentHost().notifyViewCreated(this.mMainFragmentAdapter);
        }
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment
    public void setAlignment(int i) {
        if (i == Integer.MIN_VALUE) {
            return;
        }
        this.mAlignedTop = i;
        VerticalGridView verticalGridView = getVerticalGridView();
        if (verticalGridView != null) {
            verticalGridView.setItemAlignmentOffset(0);
            verticalGridView.setItemAlignmentOffsetPercent(-1.0f);
            verticalGridView.setItemAlignmentOffsetWithPadding(true);
            verticalGridView.setWindowAlignmentOffset(this.mAlignedTop);
            verticalGridView.setWindowAlignmentOffsetPercent(-1.0f);
            verticalGridView.setWindowAlignment(0);
        }
    }

    public void setEntranceTransitionState(boolean z) {
        this.mAfterEntranceTransition = z;
        VerticalGridView verticalGridView = getVerticalGridView();
        if (verticalGridView != null) {
            int childCount = verticalGridView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ItemBridgeAdapter.ViewHolder viewHolder = (ItemBridgeAdapter.ViewHolder) verticalGridView.getChildViewHolder(verticalGridView.getChildAt(i));
                RowPresenter rowPresenter = (RowPresenter) viewHolder.getPresenter();
                rowPresenter.setEntranceTransitionState(rowPresenter.getRowViewHolder(viewHolder.getViewHolder()), this.mAfterEntranceTransition);
            }
        }
    }

    public void setExpand(boolean z) {
        this.mExpand = z;
        VerticalGridView verticalGridView = getVerticalGridView();
        if (verticalGridView != null) {
            int childCount = verticalGridView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ItemBridgeAdapter.ViewHolder viewHolder = (ItemBridgeAdapter.ViewHolder) verticalGridView.getChildViewHolder(verticalGridView.getChildAt(i));
                ((RowPresenter) viewHolder.getPresenter()).setRowViewExpanded(viewHolder.getViewHolder(), this.mExpand);
            }
        }
    }

    public void setOnItemViewClickedListener(BaseOnItemViewClickedListener baseOnItemViewClickedListener) {
        this.mOnItemViewClickedListener = baseOnItemViewClickedListener;
        if (this.mViewsCreated) {
            throw new IllegalStateException("Item clicked listener must be set before views are created");
        }
    }

    public void setOnItemViewSelectedListener(BaseOnItemViewSelectedListener baseOnItemViewSelectedListener) {
        this.mOnItemViewSelectedListener = baseOnItemViewSelectedListener;
        VerticalGridView verticalGridView = getVerticalGridView();
        if (verticalGridView != null) {
            int childCount = verticalGridView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                getRowViewHolder((ItemBridgeAdapter.ViewHolder) verticalGridView.getChildViewHolder(verticalGridView.getChildAt(i))).setOnItemViewSelectedListener(this.mOnItemViewSelectedListener);
            }
        }
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment
    public /* bridge */ /* synthetic */ void setSelectedPosition(int i) {
        super.setSelectedPosition(i);
    }

    public final void setupSharedViewPool(ItemBridgeAdapter.ViewHolder viewHolder) {
        RowPresenter.ViewHolder rowViewHolder = ((RowPresenter) viewHolder.getPresenter()).getRowViewHolder(viewHolder.getViewHolder());
        if (rowViewHolder instanceof ListRowPresenter.ViewHolder) {
            ListRowPresenter.ViewHolder viewHolder2 = (ListRowPresenter.ViewHolder) rowViewHolder;
            HorizontalGridView gridView = viewHolder2.getGridView();
            RecyclerView.RecycledViewPool recycledViewPool = this.mRecycledViewPool;
            if (recycledViewPool == null) {
                this.mRecycledViewPool = gridView.getRecycledViewPool();
            } else {
                gridView.setRecycledViewPool(recycledViewPool);
            }
            ItemBridgeAdapter bridgeAdapter = viewHolder2.getBridgeAdapter();
            ArrayList<Presenter> arrayList = this.mPresenterMapper;
            if (arrayList == null) {
                this.mPresenterMapper = bridgeAdapter.getPresenterMapper();
            } else {
                bridgeAdapter.setPresenterMapper(arrayList);
            }
        }
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment
    public final void updateAdapter() {
        super.updateAdapter();
        this.mSelectedViewHolder = null;
        this.mViewsCreated = false;
        ItemBridgeAdapter bridgeAdapter = getBridgeAdapter();
        if (bridgeAdapter != null) {
            bridgeAdapter.setAdapterListener(this.mBridgeAdapterListener);
        }
    }

    @Override // androidx.leanback.app.BaseRowSupportFragment
    public /* bridge */ /* synthetic */ void setSelectedPosition(int i, boolean z) {
        super.setSelectedPosition(i, z);
    }

    public void setSelectedPosition(int i, boolean z, final Presenter.ViewHolderTask viewHolderTask) {
        VerticalGridView verticalGridView = getVerticalGridView();
        if (verticalGridView == null) {
            return;
        }
        ViewHolderTask viewHolderTask2 = viewHolderTask != null ? new ViewHolderTask() { // from class: androidx.leanback.app.RowsSupportFragment.2
            @Override // androidx.leanback.widget.ViewHolderTask
            public void run(final RecyclerView.ViewHolder viewHolder) {
                viewHolder.itemView.post(new Runnable() { // from class: androidx.leanback.app.RowsSupportFragment.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        viewHolderTask.run(RowsSupportFragment.getRowViewHolder((ItemBridgeAdapter.ViewHolder) viewHolder));
                    }
                });
            }
        } : null;
        if (z) {
            verticalGridView.setSelectedPositionSmooth(i, viewHolderTask2);
        } else {
            verticalGridView.setSelectedPosition(i, viewHolderTask2);
        }
    }

    public static RowPresenter.ViewHolder getRowViewHolder(ItemBridgeAdapter.ViewHolder viewHolder) {
        if (viewHolder == null) {
            return null;
        }
        return ((RowPresenter) viewHolder.getPresenter()).getRowViewHolder(viewHolder.getViewHolder());
    }
}
