package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.R;
import androidx.leanback.system.Settings;
import androidx.leanback.transition.TransitionHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class ListRowPresenter extends RowPresenter {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_RECYCLED_POOL_SIZE = 24;
    private static final String TAG = "ListRowPresenter";
    private static int sExpandedRowNoHovercardBottomPadding;
    private static int sExpandedSelectedRowTopPadding;
    private static int sSelectedRowTopPadding;
    private int mBrowseRowsFadingEdgeLength;
    private int mExpandedRowHeight;
    private int mFocusZoomFactor;
    private PresenterSelector mHoverCardPresenterSelector;
    private boolean mKeepChildForeground;
    private int mNumRows;
    private HashMap<Presenter, Integer> mRecycledPoolSize;
    private boolean mRoundedCornersEnabled;
    private int mRowHeight;
    private boolean mShadowEnabled;
    public ShadowOverlayHelper mShadowOverlayHelper;
    private ItemBridgeAdapter.Wrapper mShadowOverlayWrapper;
    private boolean mUseFocusDimmer;

    public class ListRowPresenterItemBridgeAdapter extends ItemBridgeAdapter {
        public ViewHolder mRowViewHolder;

        public ListRowPresenterItemBridgeAdapter(ViewHolder viewHolder) {
            this.mRowViewHolder = viewHolder;
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public void onAddPresenter(Presenter presenter, int i) {
            this.mRowViewHolder.getGridView().getRecycledViewPool().setMaxRecycledViews(i, ListRowPresenter.this.getRecycledPoolSize(presenter));
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public void onAttachedToWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            ListRowPresenter.this.applySelectLevelToChild(this.mRowViewHolder, viewHolder.itemView);
            this.mRowViewHolder.syncActivatedStatus(viewHolder.itemView);
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public void onBind(final ItemBridgeAdapter.ViewHolder viewHolder) {
            if (this.mRowViewHolder.getOnItemViewClickedListener() != null) {
                viewHolder.mHolder.view.setOnClickListener(new View.OnClickListener() { // from class: androidx.leanback.widget.ListRowPresenter.ListRowPresenterItemBridgeAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ItemBridgeAdapter.ViewHolder viewHolder2 = (ItemBridgeAdapter.ViewHolder) ListRowPresenterItemBridgeAdapter.this.mRowViewHolder.mGridView.getChildViewHolder(viewHolder.itemView);
                        if (ListRowPresenterItemBridgeAdapter.this.mRowViewHolder.getOnItemViewClickedListener() != null) {
                            BaseOnItemViewClickedListener onItemViewClickedListener = ListRowPresenterItemBridgeAdapter.this.mRowViewHolder.getOnItemViewClickedListener();
                            Presenter.ViewHolder viewHolder3 = viewHolder.mHolder;
                            Object obj = viewHolder2.mItem;
                            ViewHolder viewHolder4 = ListRowPresenterItemBridgeAdapter.this.mRowViewHolder;
                            onItemViewClickedListener.onItemClicked(viewHolder3, obj, viewHolder4, (ListRow) viewHolder4.mRow);
                        }
                    }
                });
            }
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public final void onCreate(ItemBridgeAdapter.ViewHolder viewHolder) {
            View view = viewHolder.itemView;
            if (view instanceof ViewGroup) {
                TransitionHelper.setTransitionGroup((ViewGroup) view, true);
            }
            ShadowOverlayHelper shadowOverlayHelper = ListRowPresenter.this.mShadowOverlayHelper;
            if (shadowOverlayHelper != null) {
                shadowOverlayHelper.onViewCreated(viewHolder.itemView);
            }
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public void onUnbind(ItemBridgeAdapter.ViewHolder viewHolder) {
            if (this.mRowViewHolder.getOnItemViewClickedListener() != null) {
                viewHolder.mHolder.view.setOnClickListener(null);
            }
        }
    }

    public static class SelectItemViewHolderTask extends Presenter.ViewHolderTask {
        private int mItemPosition;
        public Presenter.ViewHolderTask mItemTask;
        private boolean mSmoothScroll = true;

        public SelectItemViewHolderTask(int i) {
            setItemPosition(i);
        }

        public int getItemPosition() {
            return this.mItemPosition;
        }

        public Presenter.ViewHolderTask getItemTask() {
            return this.mItemTask;
        }

        public boolean isSmoothScroll() {
            return this.mSmoothScroll;
        }

        @Override // androidx.leanback.widget.Presenter.ViewHolderTask
        public void run(Presenter.ViewHolder viewHolder) {
            if (viewHolder instanceof ViewHolder) {
                HorizontalGridView gridView = ((ViewHolder) viewHolder).getGridView();
                ViewHolderTask viewHolderTask = this.mItemTask != null ? new ViewHolderTask(this) { // from class: androidx.leanback.widget.ListRowPresenter.SelectItemViewHolderTask.1
                    public final Presenter.ViewHolderTask itemTask;

                    {
                        this.itemTask = this.mItemTask;
                    }

                    @Override // androidx.leanback.widget.ViewHolderTask
                    public void run(RecyclerView.ViewHolder viewHolder2) {
                        this.itemTask.run(((ItemBridgeAdapter.ViewHolder) viewHolder2).getViewHolder());
                    }
                } : null;
                if (isSmoothScroll()) {
                    gridView.setSelectedPositionSmooth(this.mItemPosition, viewHolderTask);
                } else {
                    gridView.setSelectedPosition(this.mItemPosition, viewHolderTask);
                }
            }
        }

        public void setItemPosition(int i) {
            this.mItemPosition = i;
        }

        public void setItemTask(Presenter.ViewHolderTask viewHolderTask) {
            this.mItemTask = viewHolderTask;
        }

        public void setSmoothScroll(boolean z) {
            this.mSmoothScroll = z;
        }
    }

    public static class ViewHolder extends RowPresenter.ViewHolder {
        public final HorizontalGridView mGridView;
        public final HorizontalHoverCardSwitcher mHoverCardViewSwitcher;
        public ItemBridgeAdapter mItemBridgeAdapter;
        public final ListRowPresenter mListRowPresenter;
        public final int mPaddingBottom;
        public final int mPaddingLeft;
        public final int mPaddingRight;
        public final int mPaddingTop;

        public ViewHolder(View view, HorizontalGridView horizontalGridView, ListRowPresenter listRowPresenter) {
            super(view);
            this.mHoverCardViewSwitcher = new HorizontalHoverCardSwitcher();
            this.mGridView = horizontalGridView;
            this.mListRowPresenter = listRowPresenter;
            this.mPaddingTop = horizontalGridView.getPaddingTop();
            this.mPaddingBottom = horizontalGridView.getPaddingBottom();
            this.mPaddingLeft = horizontalGridView.getPaddingLeft();
            this.mPaddingRight = horizontalGridView.getPaddingRight();
        }

        public final ItemBridgeAdapter getBridgeAdapter() {
            return this.mItemBridgeAdapter;
        }

        public final HorizontalGridView getGridView() {
            return this.mGridView;
        }

        public Presenter.ViewHolder getItemViewHolder(int i) {
            ItemBridgeAdapter.ViewHolder viewHolder = (ItemBridgeAdapter.ViewHolder) this.mGridView.findViewHolderForAdapterPosition(i);
            if (viewHolder == null) {
                return null;
            }
            return viewHolder.getViewHolder();
        }

        public final ListRowPresenter getListRowPresenter() {
            return this.mListRowPresenter;
        }

        @Override // androidx.leanback.widget.RowPresenter.ViewHolder
        public Object getSelectedItem() {
            ItemBridgeAdapter.ViewHolder viewHolder = (ItemBridgeAdapter.ViewHolder) this.mGridView.findViewHolderForAdapterPosition(getSelectedPosition());
            if (viewHolder == null) {
                return null;
            }
            return viewHolder.getItem();
        }

        @Override // androidx.leanback.widget.RowPresenter.ViewHolder
        public Presenter.ViewHolder getSelectedItemViewHolder() {
            return getItemViewHolder(getSelectedPosition());
        }

        public int getSelectedPosition() {
            return this.mGridView.getSelectedPosition();
        }
    }

    public ListRowPresenter() {
        this(2);
    }

    private int getSpaceUnderBaseline(ViewHolder viewHolder) {
        RowHeaderPresenter.ViewHolder headerViewHolder = viewHolder.getHeaderViewHolder();
        if (headerViewHolder != null) {
            return getHeaderPresenter() != null ? getHeaderPresenter().getSpaceUnderBaseline(headerViewHolder) : headerViewHolder.view.getPaddingBottom();
        }
        return 0;
    }

    private static void initStatics(Context context) {
        if (sSelectedRowTopPadding == 0) {
            sSelectedRowTopPadding = context.getResources().getDimensionPixelSize(R.dimen.lb_browse_selected_row_top_padding);
            sExpandedSelectedRowTopPadding = context.getResources().getDimensionPixelSize(R.dimen.lb_browse_expanded_selected_row_top_padding);
            sExpandedRowNoHovercardBottomPadding = context.getResources().getDimensionPixelSize(R.dimen.lb_browse_expanded_row_no_hovercard_bottom_padding);
        }
    }

    private void setVerticalPadding(ViewHolder viewHolder) {
        int spaceUnderBaseline;
        int i;
        if (viewHolder.isExpanded()) {
            spaceUnderBaseline = (viewHolder.isSelected() ? sExpandedSelectedRowTopPadding : viewHolder.mPaddingTop) - getSpaceUnderBaseline(viewHolder);
            i = this.mHoverCardPresenterSelector == null ? sExpandedRowNoHovercardBottomPadding : viewHolder.mPaddingBottom;
        } else if (viewHolder.isSelected()) {
            i = sSelectedRowTopPadding;
            spaceUnderBaseline = i - viewHolder.mPaddingBottom;
        } else {
            spaceUnderBaseline = 0;
            i = viewHolder.mPaddingBottom;
        }
        viewHolder.getGridView().setPadding(viewHolder.mPaddingLeft, spaceUnderBaseline, viewHolder.mPaddingRight, i);
    }

    private void setupFadingEffect(ListRowView listRowView) {
        HorizontalGridView gridView = listRowView.getGridView();
        if (this.mBrowseRowsFadingEdgeLength < 0) {
            TypedArray typedArrayObtainStyledAttributes = gridView.getContext().obtainStyledAttributes(R.styleable.LeanbackTheme);
            this.mBrowseRowsFadingEdgeLength = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.LeanbackTheme_browseRowsFadingEdgeLength, 0.0f);
            typedArrayObtainStyledAttributes.recycle();
        }
        gridView.setFadingLeftEdgeLength(this.mBrowseRowsFadingEdgeLength);
    }

    private void updateFooterViewSwitcher(ViewHolder viewHolder) {
        if (!viewHolder.mExpanded || !viewHolder.mSelected) {
            if (this.mHoverCardPresenterSelector != null) {
                viewHolder.mHoverCardViewSwitcher.unselect();
            }
        } else {
            PresenterSelector presenterSelector = this.mHoverCardPresenterSelector;
            if (presenterSelector != null) {
                viewHolder.mHoverCardViewSwitcher.init((ViewGroup) viewHolder.view, presenterSelector);
            }
            HorizontalGridView horizontalGridView = viewHolder.mGridView;
            ItemBridgeAdapter.ViewHolder viewHolder2 = (ItemBridgeAdapter.ViewHolder) horizontalGridView.findViewHolderForPosition(horizontalGridView.getSelectedPosition());
            selectChildView(viewHolder, viewHolder2 == null ? null : viewHolder2.itemView, false);
        }
    }

    public final void applySelectLevelToChild(ViewHolder viewHolder, View view) {
        ShadowOverlayHelper shadowOverlayHelper = this.mShadowOverlayHelper;
        if (shadowOverlayHelper == null || !shadowOverlayHelper.needsOverlay()) {
            return;
        }
        this.mShadowOverlayHelper.setOverlayColor(view, viewHolder.mColorDimmer.getPaint().getColor());
    }

    public final boolean areChildRoundedCornersEnabled() {
        return this.mRoundedCornersEnabled;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final RowPresenter.ViewHolder createRowViewHolder(ViewGroup viewGroup) {
        initStatics(viewGroup.getContext());
        ListRowView listRowView = new ListRowView(viewGroup.getContext());
        setupFadingEffect(listRowView);
        if (this.mRowHeight != 0) {
            listRowView.getGridView().setRowHeight(this.mRowHeight);
        }
        return new ViewHolder(listRowView, listRowView.getGridView(), this);
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void dispatchItemSelectedListener(RowPresenter.ViewHolder viewHolder, boolean z) {
        BaseOnItemViewSelectedListener baseOnItemViewSelectedListener;
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        HorizontalGridView horizontalGridView = viewHolder2.mGridView;
        ItemBridgeAdapter.ViewHolder viewHolder3 = (ItemBridgeAdapter.ViewHolder) horizontalGridView.findViewHolderForPosition(horizontalGridView.getSelectedPosition());
        if (viewHolder3 == null) {
            if (!z || (baseOnItemViewSelectedListener = viewHolder.mOnItemViewSelectedListener) == null) {
                return;
            }
            baseOnItemViewSelectedListener.onItemSelected(null, null, viewHolder, viewHolder.getRowObject());
            return;
        }
        if (!z || viewHolder.getOnItemViewSelectedListener() == null) {
            return;
        }
        viewHolder.getOnItemViewSelectedListener().onItemSelected(viewHolder3.getViewHolder(), viewHolder3.mItem, viewHolder2, viewHolder2.getRow());
    }

    public final void enableChildRoundedCorners(boolean z) {
        this.mRoundedCornersEnabled = z;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public void freeze(RowPresenter.ViewHolder viewHolder, boolean z) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.mGridView.setScrollEnabled(!z);
        viewHolder2.mGridView.setAnimateChildLayout(!z);
    }

    public int getExpandedRowHeight() {
        int i = this.mExpandedRowHeight;
        return i != 0 ? i : this.mRowHeight;
    }

    public final int getFocusZoomFactor() {
        return this.mFocusZoomFactor;
    }

    public final PresenterSelector getHoverCardPresenterSelector() {
        return this.mHoverCardPresenterSelector;
    }

    public int getRecycledPoolSize(Presenter presenter) {
        if (this.mRecycledPoolSize.containsKey(presenter)) {
            return this.mRecycledPoolSize.get(presenter).intValue();
        }
        return 24;
    }

    public int getRowHeight() {
        return this.mRowHeight;
    }

    public final boolean getShadowEnabled() {
        return this.mShadowEnabled;
    }

    @Deprecated
    public final int getZoomFactor() {
        return this.mFocusZoomFactor;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void initializeRowViewHolder(RowPresenter.ViewHolder viewHolder) {
        super.initializeRowViewHolder(viewHolder);
        final ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        Context context = viewHolder.view.getContext();
        if (this.mShadowOverlayHelper == null) {
            ShadowOverlayHelper shadowOverlayHelperBuild = new ShadowOverlayHelper.Builder().needsOverlay(isUsingDefaultListSelectEffect() && getSelectEffectEnabled()).needsShadow(isUsingDefaultShadow() && getShadowEnabled()).needsRoundedCorner(isUsingOutlineClipping(context) && areChildRoundedCornersEnabled()).preferZOrder(isUsingZOrder(context)).keepForegroundDrawable(this.mKeepChildForeground).options(ShadowOverlayHelper.Options.DEFAULT).build(context);
            this.mShadowOverlayHelper = shadowOverlayHelperBuild;
            if (shadowOverlayHelperBuild.needsWrapper()) {
                this.mShadowOverlayWrapper = new ItemBridgeAdapterShadowOverlayWrapper(this.mShadowOverlayHelper);
            }
        }
        ListRowPresenterItemBridgeAdapter listRowPresenterItemBridgeAdapter = new ListRowPresenterItemBridgeAdapter(viewHolder2);
        viewHolder2.mItemBridgeAdapter = listRowPresenterItemBridgeAdapter;
        listRowPresenterItemBridgeAdapter.setWrapper(this.mShadowOverlayWrapper);
        this.mShadowOverlayHelper.prepareParentForShadow(viewHolder2.mGridView);
        FocusHighlightHelper.setupBrowseItemFocusHighlight(viewHolder2.mItemBridgeAdapter, this.mFocusZoomFactor, this.mUseFocusDimmer);
        viewHolder2.mGridView.setFocusDrawingOrderEnabled(this.mShadowOverlayHelper.getShadowType() != 3);
        viewHolder2.mGridView.setOnChildSelectedListener(new OnChildSelectedListener() { // from class: androidx.leanback.widget.ListRowPresenter.1
            @Override // androidx.leanback.widget.OnChildSelectedListener
            public void onChildSelected(ViewGroup viewGroup, View view, int i, long j) {
                ListRowPresenter.this.selectChildView(viewHolder2, view, true);
            }
        });
        viewHolder2.mGridView.setOnUnhandledKeyListener(new BaseGridView.OnUnhandledKeyListener() { // from class: androidx.leanback.widget.ListRowPresenter.2
            @Override // androidx.leanback.widget.BaseGridView.OnUnhandledKeyListener
            public boolean onUnhandledKey(KeyEvent keyEvent) {
                return viewHolder2.getOnKeyListener() != null && viewHolder2.getOnKeyListener().onKey(viewHolder2.view, keyEvent.getKeyCode(), keyEvent);
            }
        });
        viewHolder2.mGridView.setNumRows(this.mNumRows);
    }

    public final boolean isFocusDimmerUsed() {
        return this.mUseFocusDimmer;
    }

    public final boolean isKeepChildForeground() {
        return this.mKeepChildForeground;
    }

    public boolean isUsingDefaultListSelectEffect() {
        return true;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final boolean isUsingDefaultSelectEffect() {
        return false;
    }

    public boolean isUsingDefaultShadow() {
        return ShadowOverlayHelper.supportsShadow();
    }

    public boolean isUsingOutlineClipping(Context context) {
        return !Settings.getInstance(context).isOutlineClippingDisabled();
    }

    public boolean isUsingZOrder(Context context) {
        return !Settings.getInstance(context).preferStaticShadows();
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onBindRowViewHolder(RowPresenter.ViewHolder viewHolder, Object obj) {
        super.onBindRowViewHolder(viewHolder, obj);
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        ListRow listRow = (ListRow) obj;
        viewHolder2.mItemBridgeAdapter.setAdapter(listRow.getAdapter());
        viewHolder2.mGridView.setAdapter(viewHolder2.mItemBridgeAdapter);
        viewHolder2.mGridView.setContentDescription(listRow.getContentDescription());
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onRowViewExpanded(RowPresenter.ViewHolder viewHolder, boolean z) {
        super.onRowViewExpanded(viewHolder, z);
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        if (getRowHeight() != getExpandedRowHeight()) {
            viewHolder2.getGridView().setRowHeight(z ? getExpandedRowHeight() : getRowHeight());
        }
        setVerticalPadding(viewHolder2);
        updateFooterViewSwitcher(viewHolder2);
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onRowViewSelected(RowPresenter.ViewHolder viewHolder, boolean z) {
        super.onRowViewSelected(viewHolder, z);
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        setVerticalPadding(viewHolder2);
        updateFooterViewSwitcher(viewHolder2);
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onSelectLevelChanged(RowPresenter.ViewHolder viewHolder) {
        super.onSelectLevelChanged(viewHolder);
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        int childCount = viewHolder2.mGridView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            applySelectLevelToChild(viewHolder2, viewHolder2.mGridView.getChildAt(i));
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onUnbindRowViewHolder(RowPresenter.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.mGridView.setAdapter(null);
        viewHolder2.mItemBridgeAdapter.clear();
        super.onUnbindRowViewHolder(viewHolder);
    }

    public final void selectChildView(ViewHolder viewHolder, View view, boolean z) {
        if (view == null) {
            if (this.mHoverCardPresenterSelector != null) {
                viewHolder.mHoverCardViewSwitcher.unselect();
            }
            if (!z || viewHolder.getOnItemViewSelectedListener() == null) {
                return;
            }
            viewHolder.getOnItemViewSelectedListener().onItemSelected(null, null, viewHolder, viewHolder.mRow);
            return;
        }
        if (viewHolder.mSelected) {
            ItemBridgeAdapter.ViewHolder viewHolder2 = (ItemBridgeAdapter.ViewHolder) viewHolder.mGridView.getChildViewHolder(view);
            if (this.mHoverCardPresenterSelector != null) {
                viewHolder.mHoverCardViewSwitcher.select(viewHolder.mGridView, view, viewHolder2.mItem);
            }
            if (!z || viewHolder.getOnItemViewSelectedListener() == null) {
                return;
            }
            viewHolder.getOnItemViewSelectedListener().onItemSelected(viewHolder2.mHolder, viewHolder2.mItem, viewHolder, viewHolder.mRow);
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    public void setEntranceTransitionState(RowPresenter.ViewHolder viewHolder, boolean z) {
        super.setEntranceTransitionState(viewHolder, z);
        ((ViewHolder) viewHolder).mGridView.setChildrenVisibility(z ? 0 : 4);
    }

    public void setExpandedRowHeight(int i) {
        this.mExpandedRowHeight = i;
    }

    public final void setHoverCardPresenterSelector(PresenterSelector presenterSelector) {
        this.mHoverCardPresenterSelector = presenterSelector;
    }

    public final void setKeepChildForeground(boolean z) {
        this.mKeepChildForeground = z;
    }

    public void setNumRows(int i) {
        this.mNumRows = i;
    }

    public void setRecycledPoolSize(Presenter presenter, int i) {
        this.mRecycledPoolSize.put(presenter, Integer.valueOf(i));
    }

    public void setRowHeight(int i) {
        this.mRowHeight = i;
    }

    public final void setShadowEnabled(boolean z) {
        this.mShadowEnabled = z;
    }

    public ListRowPresenter(int i) {
        this(i, false);
    }

    public ListRowPresenter(int i, boolean z) {
        boolean z2 = true;
        this.mNumRows = 1;
        this.mShadowEnabled = true;
        this.mBrowseRowsFadingEdgeLength = -1;
        this.mRoundedCornersEnabled = true;
        this.mKeepChildForeground = true;
        this.mRecycledPoolSize = new HashMap<>();
        if (i != 0 && FocusHighlightHelper.getResId(i) <= 0) {
            z2 = false;
        }
        if (z2) {
            this.mFocusZoomFactor = i;
            this.mUseFocusDimmer = z;
            return;
        }
        throw new IllegalArgumentException("Unhandled zoom factor");
    }
}
