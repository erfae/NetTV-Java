package androidx.leanback.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.R;
import androidx.leanback.system.Settings;
import androidx.leanback.transition.TransitionHelper;

/* JADX INFO: loaded from: classes.dex */
public class VerticalGridPresenter extends Presenter {
    private static final boolean DEBUG = false;
    private static final String TAG = "GridPresenter";
    private int mFocusZoomFactor;
    private boolean mKeepChildForeground;
    private int mNumColumns;
    private OnItemViewClickedListener mOnItemViewClickedListener;
    private OnItemViewSelectedListener mOnItemViewSelectedListener;
    private boolean mRoundedCornersEnabled;
    private boolean mShadowEnabled;
    public ShadowOverlayHelper mShadowOverlayHelper;
    private ItemBridgeAdapter.Wrapper mShadowOverlayWrapper;
    private boolean mUseFocusDimmer;

    public class VerticalGridItemBridgeAdapter extends ItemBridgeAdapter {
        public VerticalGridItemBridgeAdapter() {
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public void onAttachedToWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            viewHolder.itemView.setActivated(true);
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public void onBind(final ItemBridgeAdapter.ViewHolder viewHolder) {
            if (VerticalGridPresenter.this.getOnItemViewClickedListener() != null) {
                viewHolder.mHolder.view.setOnClickListener(new View.OnClickListener() { // from class: androidx.leanback.widget.VerticalGridPresenter.VerticalGridItemBridgeAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (VerticalGridPresenter.this.getOnItemViewClickedListener() != null) {
                            OnItemViewClickedListener onItemViewClickedListener = VerticalGridPresenter.this.getOnItemViewClickedListener();
                            ItemBridgeAdapter.ViewHolder viewHolder2 = viewHolder;
                            onItemViewClickedListener.onItemClicked(viewHolder2.mHolder, viewHolder2.mItem, null, null);
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
            ShadowOverlayHelper shadowOverlayHelper = VerticalGridPresenter.this.mShadowOverlayHelper;
            if (shadowOverlayHelper != null) {
                shadowOverlayHelper.onViewCreated(viewHolder.itemView);
            }
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public void onUnbind(ItemBridgeAdapter.ViewHolder viewHolder) {
            if (VerticalGridPresenter.this.getOnItemViewClickedListener() != null) {
                viewHolder.mHolder.view.setOnClickListener(null);
            }
        }
    }

    public static class ViewHolder extends Presenter.ViewHolder {
        public final VerticalGridView mGridView;
        public boolean mInitialized;
        public ItemBridgeAdapter mItemBridgeAdapter;

        public ViewHolder(VerticalGridView verticalGridView) {
            super(verticalGridView);
            this.mGridView = verticalGridView;
        }

        public VerticalGridView getGridView() {
            return this.mGridView;
        }
    }

    public VerticalGridPresenter() {
        this(3);
    }

    public final boolean areChildRoundedCornersEnabled() {
        return this.mRoundedCornersEnabled;
    }

    public final void enableChildRoundedCorners(boolean z) {
        this.mRoundedCornersEnabled = z;
    }

    public final int getFocusZoomFactor() {
        return this.mFocusZoomFactor;
    }

    public final boolean getKeepChildForeground() {
        return this.mKeepChildForeground;
    }

    public int getNumberOfColumns() {
        return this.mNumColumns;
    }

    public final OnItemViewClickedListener getOnItemViewClickedListener() {
        return this.mOnItemViewClickedListener;
    }

    public final OnItemViewSelectedListener getOnItemViewSelectedListener() {
        return this.mOnItemViewSelectedListener;
    }

    public final boolean getShadowEnabled() {
        return this.mShadowEnabled;
    }

    public final boolean isFocusDimmerUsed() {
        return this.mUseFocusDimmer;
    }

    public boolean isUsingDefaultShadow() {
        return ShadowOverlayHelper.supportsShadow();
    }

    public boolean isUsingZOrder(Context context) {
        return !Settings.getInstance(context).preferStaticShadows();
    }

    @Override // androidx.leanback.widget.Presenter
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object obj) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.mItemBridgeAdapter.setAdapter((ObjectAdapter) obj);
        viewHolder2.getGridView().setAdapter(viewHolder2.mItemBridgeAdapter);
    }

    @Override // androidx.leanback.widget.Presenter
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.mItemBridgeAdapter.setAdapter(null);
        viewHolder2.getGridView().setAdapter(null);
    }

    public void setEntranceTransitionState(ViewHolder viewHolder, boolean z) {
        viewHolder.mGridView.setChildrenVisibility(z ? 0 : 4);
    }

    public final void setKeepChildForeground(boolean z) {
        this.mKeepChildForeground = z;
    }

    public void setNumberOfColumns(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Invalid number of columns");
        }
        if (this.mNumColumns != i) {
            this.mNumColumns = i;
        }
    }

    public final void setOnItemViewClickedListener(OnItemViewClickedListener onItemViewClickedListener) {
        this.mOnItemViewClickedListener = onItemViewClickedListener;
    }

    public final void setOnItemViewSelectedListener(OnItemViewSelectedListener onItemViewSelectedListener) {
        this.mOnItemViewSelectedListener = onItemViewSelectedListener;
    }

    public final void setShadowEnabled(boolean z) {
        this.mShadowEnabled = z;
    }

    public VerticalGridPresenter(int i) {
        this(i, true);
    }

    @Override // androidx.leanback.widget.Presenter
    public final ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        final ViewHolder viewHolder = new ViewHolder((VerticalGridView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lb_vertical_grid, viewGroup, false).findViewById(R.id.browse_grid));
        viewHolder.mInitialized = false;
        viewHolder.mItemBridgeAdapter = new VerticalGridItemBridgeAdapter();
        if (this.mNumColumns == -1) {
            throw new IllegalStateException("Number of columns must be set");
        }
        viewHolder.getGridView().setNumColumns(this.mNumColumns);
        viewHolder.mInitialized = true;
        Context context = viewHolder.mGridView.getContext();
        if (this.mShadowOverlayHelper == null) {
            ShadowOverlayHelper shadowOverlayHelperBuild = new ShadowOverlayHelper.Builder().needsOverlay(this.mUseFocusDimmer).needsShadow(isUsingDefaultShadow() && getShadowEnabled()).needsRoundedCorner(areChildRoundedCornersEnabled()).preferZOrder(isUsingZOrder(context)).keepForegroundDrawable(this.mKeepChildForeground).options(ShadowOverlayHelper.Options.DEFAULT).build(context);
            this.mShadowOverlayHelper = shadowOverlayHelperBuild;
            if (shadowOverlayHelperBuild.needsWrapper()) {
                this.mShadowOverlayWrapper = new ItemBridgeAdapterShadowOverlayWrapper(this.mShadowOverlayHelper);
            }
        }
        viewHolder.mItemBridgeAdapter.setWrapper(this.mShadowOverlayWrapper);
        this.mShadowOverlayHelper.prepareParentForShadow(viewHolder.mGridView);
        viewHolder.getGridView().setFocusDrawingOrderEnabled(this.mShadowOverlayHelper.getShadowType() != 3);
        FocusHighlightHelper.setupBrowseItemFocusHighlight(viewHolder.mItemBridgeAdapter, this.mFocusZoomFactor, this.mUseFocusDimmer);
        viewHolder.getGridView().setOnChildSelectedListener(new OnChildSelectedListener() { // from class: androidx.leanback.widget.VerticalGridPresenter.1
            @Override // androidx.leanback.widget.OnChildSelectedListener
            public void onChildSelected(ViewGroup viewGroup2, View view, int i, long j) {
                VerticalGridPresenter verticalGridPresenter = VerticalGridPresenter.this;
                ViewHolder viewHolder2 = viewHolder;
                if (verticalGridPresenter.getOnItemViewSelectedListener() != null) {
                    ItemBridgeAdapter.ViewHolder viewHolder3 = view == null ? null : (ItemBridgeAdapter.ViewHolder) viewHolder2.getGridView().getChildViewHolder(view);
                    if (viewHolder3 == null) {
                        verticalGridPresenter.getOnItemViewSelectedListener().onItemSelected(null, null, null, null);
                    } else {
                        verticalGridPresenter.getOnItemViewSelectedListener().onItemSelected(viewHolder3.mHolder, viewHolder3.mItem, null, null);
                    }
                }
            }
        });
        if (viewHolder.mInitialized) {
            return viewHolder;
        }
        throw new RuntimeException("super.initializeGridViewHolder() must be called");
    }

    public VerticalGridPresenter(int i, boolean z) {
        this.mNumColumns = -1;
        this.mShadowEnabled = true;
        this.mKeepChildForeground = true;
        this.mRoundedCornersEnabled = true;
        this.mFocusZoomFactor = i;
        this.mUseFocusDimmer = z;
    }
}
