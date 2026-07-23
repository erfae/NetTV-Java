package androidx.leanback.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.graphics.ColorOverlayDimmer;

/* JADX INFO: loaded from: classes.dex */
public abstract class RowPresenter extends Presenter {
    public static final int SYNC_ACTIVATED_CUSTOM = 0;
    public static final int SYNC_ACTIVATED_TO_EXPANDED = 1;
    public static final int SYNC_ACTIVATED_TO_EXPANDED_AND_SELECTED = 3;
    public static final int SYNC_ACTIVATED_TO_SELECTED = 2;
    private RowHeaderPresenter mHeaderPresenter;
    public boolean mSelectEffectEnabled;
    public int mSyncActivatePolicy;

    public static class ContainerViewHolder extends Presenter.ViewHolder {
        public final ViewHolder mRowViewHolder;

        public ContainerViewHolder(RowContainerView rowContainerView, ViewHolder viewHolder) {
            super(rowContainerView);
            rowContainerView.addRowView(viewHolder.view);
            RowHeaderPresenter.ViewHolder viewHolder2 = viewHolder.mHeaderViewHolder;
            if (viewHolder2 != null) {
                rowContainerView.addHeaderView(viewHolder2.view);
            }
            this.mRowViewHolder = viewHolder;
            viewHolder.mContainerViewHolder = this;
        }
    }

    public static class ViewHolder extends Presenter.ViewHolder {
        private static final int ACTIVATED = 1;
        private static final int ACTIVATED_NOT_ASSIGNED = 0;
        private static final int NOT_ACTIVATED = 2;
        public int mActivated;
        public final ColorOverlayDimmer mColorDimmer;
        public ContainerViewHolder mContainerViewHolder;
        public boolean mExpanded;
        public RowHeaderPresenter.ViewHolder mHeaderViewHolder;
        public boolean mInitialzed;
        private BaseOnItemViewClickedListener mOnItemViewClickedListener;
        public BaseOnItemViewSelectedListener mOnItemViewSelectedListener;
        private View.OnKeyListener mOnKeyListener;
        public Row mRow;
        public Object mRowObject;
        public float mSelectLevel;
        public boolean mSelected;

        public ViewHolder(View view) {
            super(view);
            this.mActivated = 0;
            this.mSelectLevel = 0.0f;
            this.mColorDimmer = ColorOverlayDimmer.createDefault(view.getContext());
        }

        public final RowHeaderPresenter.ViewHolder getHeaderViewHolder() {
            return this.mHeaderViewHolder;
        }

        public final BaseOnItemViewClickedListener getOnItemViewClickedListener() {
            return this.mOnItemViewClickedListener;
        }

        public final BaseOnItemViewSelectedListener getOnItemViewSelectedListener() {
            return this.mOnItemViewSelectedListener;
        }

        public View.OnKeyListener getOnKeyListener() {
            return this.mOnKeyListener;
        }

        public final Row getRow() {
            return this.mRow;
        }

        public final Object getRowObject() {
            return this.mRowObject;
        }

        public final float getSelectLevel() {
            return this.mSelectLevel;
        }

        public Object getSelectedItem() {
            return null;
        }

        public Presenter.ViewHolder getSelectedItemViewHolder() {
            return null;
        }

        public final boolean isExpanded() {
            return this.mExpanded;
        }

        public final boolean isSelected() {
            return this.mSelected;
        }

        public final void setActivated(boolean z) {
            this.mActivated = z ? 1 : 2;
        }

        public final void setOnItemViewClickedListener(BaseOnItemViewClickedListener baseOnItemViewClickedListener) {
            this.mOnItemViewClickedListener = baseOnItemViewClickedListener;
        }

        public final void setOnItemViewSelectedListener(BaseOnItemViewSelectedListener baseOnItemViewSelectedListener) {
            this.mOnItemViewSelectedListener = baseOnItemViewSelectedListener;
        }

        public void setOnKeyListener(View.OnKeyListener onKeyListener) {
            this.mOnKeyListener = onKeyListener;
        }

        public final void syncActivatedStatus(View view) {
            int i = this.mActivated;
            if (i == 1) {
                view.setActivated(true);
            } else if (i == 2) {
                view.setActivated(false);
            }
        }
    }

    public RowPresenter() {
        RowHeaderPresenter rowHeaderPresenter = new RowHeaderPresenter();
        this.mHeaderPresenter = rowHeaderPresenter;
        this.mSelectEffectEnabled = true;
        this.mSyncActivatePolicy = 1;
        rowHeaderPresenter.setNullItemVisibilityGone(true);
    }

    private void updateActivateStatus(ViewHolder viewHolder, View view) {
        int i = this.mSyncActivatePolicy;
        if (i == 1) {
            viewHolder.setActivated(viewHolder.isExpanded());
        } else if (i == 2) {
            viewHolder.setActivated(viewHolder.isSelected());
        } else if (i == 3) {
            viewHolder.setActivated(viewHolder.isExpanded() && viewHolder.isSelected());
        }
        viewHolder.syncActivatedStatus(view);
    }

    private void updateHeaderViewVisibility(ViewHolder viewHolder) {
        if (this.mHeaderPresenter == null || viewHolder.mHeaderViewHolder == null) {
            return;
        }
        ((RowContainerView) viewHolder.mContainerViewHolder.view).showHeader(viewHolder.isExpanded());
    }

    public abstract ViewHolder createRowViewHolder(ViewGroup viewGroup);

    public void dispatchItemSelectedListener(ViewHolder viewHolder, boolean z) {
        BaseOnItemViewSelectedListener baseOnItemViewSelectedListener;
        if (!z || (baseOnItemViewSelectedListener = viewHolder.mOnItemViewSelectedListener) == null) {
            return;
        }
        baseOnItemViewSelectedListener.onItemSelected(null, null, viewHolder, viewHolder.getRowObject());
    }

    public void freeze(ViewHolder viewHolder, boolean z) {
    }

    public final RowHeaderPresenter getHeaderPresenter() {
        return this.mHeaderPresenter;
    }

    public final ViewHolder getRowViewHolder(Presenter.ViewHolder viewHolder) {
        return viewHolder instanceof ContainerViewHolder ? ((ContainerViewHolder) viewHolder).mRowViewHolder : (ViewHolder) viewHolder;
    }

    public final boolean getSelectEffectEnabled() {
        return this.mSelectEffectEnabled;
    }

    public final float getSelectLevel(Presenter.ViewHolder viewHolder) {
        return getRowViewHolder(viewHolder).mSelectLevel;
    }

    public final int getSyncActivatePolicy() {
        return this.mSyncActivatePolicy;
    }

    public void initializeRowViewHolder(ViewHolder viewHolder) {
        viewHolder.mInitialzed = true;
        if (isClippingChildren()) {
            return;
        }
        View view = viewHolder.view;
        if (view instanceof ViewGroup) {
            ((ViewGroup) view).setClipChildren(false);
        }
        ContainerViewHolder containerViewHolder = viewHolder.mContainerViewHolder;
        if (containerViewHolder != null) {
            ((ViewGroup) containerViewHolder.view).setClipChildren(false);
        }
    }

    public boolean isClippingChildren() {
        return this instanceof AbstractMediaItemPresenter;
    }

    public boolean isUsingDefaultSelectEffect() {
        return true;
    }

    public void onBindRowViewHolder(ViewHolder viewHolder, Object obj) {
        viewHolder.mRowObject = obj;
        viewHolder.mRow = obj instanceof Row ? (Row) obj : null;
        if (viewHolder.mHeaderViewHolder == null || viewHolder.getRow() == null) {
            return;
        }
        this.mHeaderPresenter.onBindViewHolder(viewHolder.mHeaderViewHolder, obj);
    }

    @Override // androidx.leanback.widget.Presenter
    public final void onBindViewHolder(Presenter.ViewHolder viewHolder, Object obj) {
        onBindRowViewHolder(getRowViewHolder(viewHolder), obj);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001d  */
    @Override // androidx.leanback.widget.Presenter
    public final Presenter.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        boolean z;
        Presenter.ViewHolder containerViewHolder;
        ViewHolder viewHolderCreateRowViewHolder = createRowViewHolder(viewGroup);
        viewHolderCreateRowViewHolder.mInitialzed = false;
        if (this.mHeaderPresenter == null) {
            z = isUsingDefaultSelectEffect() && getSelectEffectEnabled();
        }
        if (z) {
            RowContainerView rowContainerView = new RowContainerView(viewGroup.getContext());
            RowHeaderPresenter rowHeaderPresenter = this.mHeaderPresenter;
            if (rowHeaderPresenter != null) {
                viewHolderCreateRowViewHolder.mHeaderViewHolder = (RowHeaderPresenter.ViewHolder) rowHeaderPresenter.onCreateViewHolder((ViewGroup) viewHolderCreateRowViewHolder.view);
            }
            containerViewHolder = new ContainerViewHolder(rowContainerView, viewHolderCreateRowViewHolder);
        } else {
            containerViewHolder = viewHolderCreateRowViewHolder;
        }
        initializeRowViewHolder(viewHolderCreateRowViewHolder);
        if (viewHolderCreateRowViewHolder.mInitialzed) {
            return containerViewHolder;
        }
        throw new RuntimeException("super.initializeRowViewHolder() must be called");
    }

    public void onRowViewAttachedToWindow(ViewHolder viewHolder) {
        RowHeaderPresenter.ViewHolder viewHolder2 = viewHolder.mHeaderViewHolder;
        if (viewHolder2 != null) {
            this.mHeaderPresenter.onViewAttachedToWindow(viewHolder2);
        }
    }

    public void onRowViewDetachedFromWindow(ViewHolder viewHolder) {
        RowHeaderPresenter.ViewHolder viewHolder2 = viewHolder.mHeaderViewHolder;
        if (viewHolder2 != null) {
            this.mHeaderPresenter.onViewDetachedFromWindow(viewHolder2);
        }
        Presenter.cancelAnimationsRecursive(viewHolder.view);
    }

    public void onRowViewExpanded(ViewHolder viewHolder, boolean z) {
        updateHeaderViewVisibility(viewHolder);
        updateActivateStatus(viewHolder, viewHolder.view);
    }

    public void onRowViewSelected(ViewHolder viewHolder, boolean z) {
        dispatchItemSelectedListener(viewHolder, z);
        updateHeaderViewVisibility(viewHolder);
        updateActivateStatus(viewHolder, viewHolder.view);
    }

    public void onSelectLevelChanged(ViewHolder viewHolder) {
        if (getSelectEffectEnabled()) {
            viewHolder.mColorDimmer.setActiveLevel(viewHolder.mSelectLevel);
            RowHeaderPresenter.ViewHolder viewHolder2 = viewHolder.mHeaderViewHolder;
            if (viewHolder2 != null) {
                this.mHeaderPresenter.setSelectLevel(viewHolder2, viewHolder.mSelectLevel);
            }
            if (isUsingDefaultSelectEffect()) {
                ((RowContainerView) viewHolder.mContainerViewHolder.view).setForegroundColor(viewHolder.mColorDimmer.getPaint().getColor());
            }
        }
    }

    public void onUnbindRowViewHolder(ViewHolder viewHolder) {
        RowHeaderPresenter.ViewHolder viewHolder2 = viewHolder.mHeaderViewHolder;
        if (viewHolder2 != null) {
            this.mHeaderPresenter.onUnbindViewHolder(viewHolder2);
        }
        viewHolder.mRow = null;
        viewHolder.mRowObject = null;
    }

    @Override // androidx.leanback.widget.Presenter
    public final void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        onUnbindRowViewHolder(getRowViewHolder(viewHolder));
    }

    @Override // androidx.leanback.widget.Presenter
    public final void onViewAttachedToWindow(Presenter.ViewHolder viewHolder) {
        onRowViewAttachedToWindow(getRowViewHolder(viewHolder));
    }

    @Override // androidx.leanback.widget.Presenter
    public final void onViewDetachedFromWindow(Presenter.ViewHolder viewHolder) {
        onRowViewDetachedFromWindow(getRowViewHolder(viewHolder));
    }

    public void setEntranceTransitionState(ViewHolder viewHolder, boolean z) {
        RowHeaderPresenter.ViewHolder viewHolder2 = viewHolder.mHeaderViewHolder;
        if (viewHolder2 == null || viewHolder2.view.getVisibility() == 8) {
            return;
        }
        viewHolder.mHeaderViewHolder.view.setVisibility(z ? 0 : 4);
    }

    public final void setHeaderPresenter(RowHeaderPresenter rowHeaderPresenter) {
        this.mHeaderPresenter = rowHeaderPresenter;
    }

    public final void setRowViewExpanded(Presenter.ViewHolder viewHolder, boolean z) {
        ViewHolder rowViewHolder = getRowViewHolder(viewHolder);
        rowViewHolder.mExpanded = z;
        onRowViewExpanded(rowViewHolder, z);
    }

    public final void setRowViewSelected(Presenter.ViewHolder viewHolder, boolean z) {
        ViewHolder rowViewHolder = getRowViewHolder(viewHolder);
        rowViewHolder.mSelected = z;
        onRowViewSelected(rowViewHolder, z);
    }

    public final void setSelectEffectEnabled(boolean z) {
        this.mSelectEffectEnabled = z;
    }

    public final void setSelectLevel(Presenter.ViewHolder viewHolder, float f) {
        ViewHolder rowViewHolder = getRowViewHolder(viewHolder);
        rowViewHolder.mSelectLevel = f;
        onSelectLevelChanged(rowViewHolder);
    }

    public final void setSyncActivatePolicy(int i) {
        this.mSyncActivatePolicy = i;
    }
}
