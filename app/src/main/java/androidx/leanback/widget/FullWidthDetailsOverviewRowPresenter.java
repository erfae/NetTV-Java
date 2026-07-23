package androidx.leanback.widget;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.leanback.R;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class FullWidthDetailsOverviewRowPresenter extends RowPresenter {
    public static final int ALIGN_MODE_MIDDLE = 1;
    public static final int ALIGN_MODE_START = 0;
    public static final int STATE_FULL = 1;
    public static final int STATE_HALF = 0;
    public static final int STATE_SMALL = 2;
    public OnActionClickedListener mActionClickedListener;
    private int mActionsBackgroundColor;
    private boolean mActionsBackgroundColorSet;
    private int mAlignmentMode;
    private int mBackgroundColor;
    private boolean mBackgroundColorSet;
    public final DetailsOverviewLogoPresenter mDetailsOverviewLogoPresenter;
    public final Presenter mDetailsPresenter;
    public int mInitialState;
    private Listener mListener;
    private boolean mParticipatingEntranceTransition;
    private static Rect sTmpRect = new Rect();
    public static final Handler sHandler = new Handler();

    public class ActionsItemBridgeAdapter extends ItemBridgeAdapter {
        public ViewHolder mViewHolder;

        public ActionsItemBridgeAdapter(ViewHolder viewHolder) {
            this.mViewHolder = viewHolder;
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public void onAttachedToWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            viewHolder.itemView.removeOnLayoutChangeListener(this.mViewHolder.mLayoutChangeListener);
            viewHolder.itemView.addOnLayoutChangeListener(this.mViewHolder.mLayoutChangeListener);
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public void onBind(final ItemBridgeAdapter.ViewHolder viewHolder) {
            if (this.mViewHolder.getOnItemViewClickedListener() == null && FullWidthDetailsOverviewRowPresenter.this.mActionClickedListener == null) {
                return;
            }
            viewHolder.getPresenter().setOnClickListener(viewHolder.getViewHolder(), new View.OnClickListener() { // from class: androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter.ActionsItemBridgeAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (ActionsItemBridgeAdapter.this.mViewHolder.getOnItemViewClickedListener() != null) {
                        BaseOnItemViewClickedListener onItemViewClickedListener = ActionsItemBridgeAdapter.this.mViewHolder.getOnItemViewClickedListener();
                        Presenter.ViewHolder viewHolder2 = viewHolder.getViewHolder();
                        Object item = viewHolder.getItem();
                        ViewHolder viewHolder3 = ActionsItemBridgeAdapter.this.mViewHolder;
                        onItemViewClickedListener.onItemClicked(viewHolder2, item, viewHolder3, viewHolder3.getRow());
                    }
                    OnActionClickedListener onActionClickedListener = FullWidthDetailsOverviewRowPresenter.this.mActionClickedListener;
                    if (onActionClickedListener != null) {
                        onActionClickedListener.onActionClicked((Action) viewHolder.getItem());
                    }
                }
            });
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public void onDetachedFromWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            viewHolder.itemView.removeOnLayoutChangeListener(this.mViewHolder.mLayoutChangeListener);
            this.mViewHolder.checkFirstAndLastPosition();
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter
        public void onUnbind(ItemBridgeAdapter.ViewHolder viewHolder) {
            if (this.mViewHolder.getOnItemViewClickedListener() == null && FullWidthDetailsOverviewRowPresenter.this.mActionClickedListener == null) {
                return;
            }
            viewHolder.getPresenter().setOnClickListener(viewHolder.getViewHolder(), null);
        }
    }

    public static abstract class Listener {
        public void onBindLogo(ViewHolder viewHolder) {
        }
    }

    public class ViewHolder extends RowPresenter.ViewHolder {
        public ItemBridgeAdapter mActionBridgeAdapter;
        public final HorizontalGridView mActionsRow;
        public final OnChildSelectedListener mChildSelectedListener;
        public final ViewGroup mDetailsDescriptionFrame;
        public final Presenter.ViewHolder mDetailsDescriptionViewHolder;
        public final DetailsOverviewLogoPresenter.ViewHolder mDetailsLogoViewHolder;
        public final View.OnLayoutChangeListener mLayoutChangeListener;
        public int mNumItems;
        public final FrameLayout mOverviewFrame;
        public final DetailsOverviewRowListener mRowListener;
        public int mState;
        public final Runnable mUpdateDrawableCallback;

        public class DetailsOverviewRowListener extends DetailsOverviewRow.Listener {
            public DetailsOverviewRowListener() {
            }

            @Override // androidx.leanback.widget.DetailsOverviewRow.Listener
            public void onActionsAdapterChanged(DetailsOverviewRow detailsOverviewRow) {
                ViewHolder.this.bindActions(detailsOverviewRow.getActionsAdapter());
            }

            @Override // androidx.leanback.widget.DetailsOverviewRow.Listener
            public void onImageDrawableChanged(DetailsOverviewRow detailsOverviewRow) {
                Handler handler = FullWidthDetailsOverviewRowPresenter.sHandler;
                handler.removeCallbacks(ViewHolder.this.mUpdateDrawableCallback);
                handler.post(ViewHolder.this.mUpdateDrawableCallback);
            }

            @Override // androidx.leanback.widget.DetailsOverviewRow.Listener
            public void onItemChanged(DetailsOverviewRow detailsOverviewRow) {
                ViewHolder viewHolder = ViewHolder.this;
                Presenter.ViewHolder viewHolder2 = viewHolder.mDetailsDescriptionViewHolder;
                if (viewHolder2 != null) {
                    FullWidthDetailsOverviewRowPresenter.this.mDetailsPresenter.onUnbindViewHolder(viewHolder2);
                }
                ViewHolder viewHolder3 = ViewHolder.this;
                FullWidthDetailsOverviewRowPresenter.this.mDetailsPresenter.onBindViewHolder(viewHolder3.mDetailsDescriptionViewHolder, detailsOverviewRow.getItem());
            }
        }

        public ViewHolder(View view, Presenter presenter, DetailsOverviewLogoPresenter detailsOverviewLogoPresenter) {
            super(view);
            this.mRowListener = new DetailsOverviewRowListener();
            this.mState = 0;
            this.mUpdateDrawableCallback = new Runnable() { // from class: androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter.ViewHolder.1
                @Override // java.lang.Runnable
                public void run() {
                    Row row = ViewHolder.this.getRow();
                    if (row == null) {
                        return;
                    }
                    ViewHolder viewHolder = ViewHolder.this;
                    FullWidthDetailsOverviewRowPresenter.this.mDetailsOverviewLogoPresenter.onBindViewHolder(viewHolder.mDetailsLogoViewHolder, row);
                }
            };
            this.mLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter.ViewHolder.2
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    ViewHolder.this.checkFirstAndLastPosition();
                }
            };
            OnChildSelectedListener onChildSelectedListener = new OnChildSelectedListener() { // from class: androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter.ViewHolder.3
                @Override // androidx.leanback.widget.OnChildSelectedListener
                public void onChildSelected(ViewGroup viewGroup, View view2, int i, long j) {
                    RecyclerView.ViewHolder viewHolderFindViewHolderForPosition;
                    ViewHolder viewHolder = ViewHolder.this;
                    if (viewHolder.isSelected()) {
                        if (view2 != null) {
                            viewHolderFindViewHolderForPosition = viewHolder.mActionsRow.getChildViewHolder(view2);
                        } else {
                            HorizontalGridView horizontalGridView = viewHolder.mActionsRow;
                            viewHolderFindViewHolderForPosition = horizontalGridView.findViewHolderForPosition(horizontalGridView.getSelectedPosition());
                        }
                        ItemBridgeAdapter.ViewHolder viewHolder2 = (ItemBridgeAdapter.ViewHolder) viewHolderFindViewHolderForPosition;
                        if (viewHolder2 == null) {
                            if (viewHolder.getOnItemViewSelectedListener() != null) {
                                viewHolder.getOnItemViewSelectedListener().onItemSelected(null, null, viewHolder, viewHolder.getRow());
                            }
                        } else if (viewHolder.getOnItemViewSelectedListener() != null) {
                            viewHolder.getOnItemViewSelectedListener().onItemSelected(viewHolder2.getViewHolder(), viewHolder2.getItem(), viewHolder, viewHolder.getRow());
                        }
                    }
                }
            };
            this.mChildSelectedListener = onChildSelectedListener;
            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() { // from class: androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter.ViewHolder.4
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    ViewHolder.this.checkFirstAndLastPosition();
                }
            };
            ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.details_root);
            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.details_frame);
            this.mOverviewFrame = frameLayout;
            ViewGroup viewGroup2 = (ViewGroup) view.findViewById(R.id.details_overview_description);
            this.mDetailsDescriptionFrame = viewGroup2;
            HorizontalGridView horizontalGridView = (HorizontalGridView) frameLayout.findViewById(R.id.details_overview_actions);
            this.mActionsRow = horizontalGridView;
            horizontalGridView.setHasOverlappingRendering(false);
            horizontalGridView.setOnScrollListener(onScrollListener);
            horizontalGridView.setAdapter(this.mActionBridgeAdapter);
            horizontalGridView.setOnChildSelectedListener(onChildSelectedListener);
            int dimensionPixelSize = view.getResources().getDimensionPixelSize(R.dimen.lb_details_overview_actions_fade_size);
            horizontalGridView.setFadingRightEdgeLength(dimensionPixelSize);
            horizontalGridView.setFadingLeftEdgeLength(dimensionPixelSize);
            Presenter.ViewHolder viewHolderOnCreateViewHolder = presenter.onCreateViewHolder(viewGroup2);
            this.mDetailsDescriptionViewHolder = viewHolderOnCreateViewHolder;
            viewGroup2.addView(viewHolderOnCreateViewHolder.view);
            DetailsOverviewLogoPresenter.ViewHolder viewHolder = (DetailsOverviewLogoPresenter.ViewHolder) detailsOverviewLogoPresenter.onCreateViewHolder(viewGroup);
            this.mDetailsLogoViewHolder = viewHolder;
            viewGroup.addView(viewHolder.view);
        }

        private int getViewCenter(View view) {
            return (view.getRight() - view.getLeft()) / 2;
        }

        public final void bindActions(ObjectAdapter objectAdapter) {
            this.mActionBridgeAdapter.setAdapter(objectAdapter);
            this.mActionsRow.setAdapter(this.mActionBridgeAdapter);
            this.mNumItems = this.mActionBridgeAdapter.getItemCount();
        }

        public final void checkFirstAndLastPosition() {
            RecyclerView.ViewHolder viewHolderFindViewHolderForPosition = this.mActionsRow.findViewHolderForPosition(this.mNumItems - 1);
            if (viewHolderFindViewHolderForPosition != null) {
                viewHolderFindViewHolderForPosition.itemView.getRight();
                this.mActionsRow.getWidth();
            }
            RecyclerView.ViewHolder viewHolderFindViewHolderForPosition2 = this.mActionsRow.findViewHolderForPosition(0);
            if (viewHolderFindViewHolderForPosition2 != null) {
                viewHolderFindViewHolderForPosition2.itemView.getLeft();
            }
        }

        public final ViewGroup getActionsRow() {
            return this.mActionsRow;
        }

        public final ViewGroup getDetailsDescriptionFrame() {
            return this.mDetailsDescriptionFrame;
        }

        public final Presenter.ViewHolder getDetailsDescriptionViewHolder() {
            return this.mDetailsDescriptionViewHolder;
        }

        public final DetailsOverviewLogoPresenter.ViewHolder getLogoViewHolder() {
            return this.mDetailsLogoViewHolder;
        }

        public final ViewGroup getOverviewView() {
            return this.mOverviewFrame;
        }

        public final int getState() {
            return this.mState;
        }
    }

    public FullWidthDetailsOverviewRowPresenter(Presenter presenter) {
        this(presenter, new DetailsOverviewLogoPresenter());
    }

    private static int getNonNegativeHeight(Drawable drawable) {
        int intrinsicHeight = drawable == null ? 0 : drawable.getIntrinsicHeight();
        if (intrinsicHeight > 0) {
            return intrinsicHeight;
        }
        return 0;
    }

    private static int getNonNegativeWidth(Drawable drawable) {
        int intrinsicWidth = drawable == null ? 0 : drawable.getIntrinsicWidth();
        if (intrinsicWidth > 0) {
            return intrinsicWidth;
        }
        return 0;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final RowPresenter.ViewHolder createRowViewHolder(ViewGroup viewGroup) {
        final ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lb_fullwidth_details_overview, viewGroup, false), this.mDetailsPresenter, this.mDetailsOverviewLogoPresenter);
        this.mDetailsOverviewLogoPresenter.setContext(viewHolder.mDetailsLogoViewHolder, viewHolder, this);
        setState(viewHolder, this.mInitialState);
        viewHolder.mActionBridgeAdapter = new ActionsItemBridgeAdapter(viewHolder);
        FrameLayout frameLayout = viewHolder.mOverviewFrame;
        if (this.mBackgroundColorSet) {
            frameLayout.setBackgroundColor(this.mBackgroundColor);
        }
        if (this.mActionsBackgroundColorSet) {
            frameLayout.findViewById(R.id.details_overview_actions_background).setBackgroundColor(this.mActionsBackgroundColor);
        }
        RoundedRectHelper.setClipToRoundedOutline(frameLayout, true);
        if (!getSelectEffectEnabled()) {
            viewHolder.mOverviewFrame.setForeground(null);
        }
        viewHolder.mActionsRow.setOnUnhandledKeyListener(new BaseGridView.OnUnhandledKeyListener() { // from class: androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter.1
            @Override // androidx.leanback.widget.BaseGridView.OnUnhandledKeyListener
            public boolean onUnhandledKey(KeyEvent keyEvent) {
                return viewHolder.getOnKeyListener() != null && viewHolder.getOnKeyListener().onKey(viewHolder.view, keyEvent.getKeyCode(), keyEvent);
            }
        });
        return viewHolder;
    }

    public final int getActionsBackgroundColor() {
        return this.mActionsBackgroundColor;
    }

    public final int getAlignmentMode() {
        return this.mAlignmentMode;
    }

    public final int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public final int getInitialState() {
        return this.mInitialState;
    }

    public OnActionClickedListener getOnActionClickedListener() {
        return this.mActionClickedListener;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final boolean isClippingChildren() {
        return true;
    }

    public final boolean isParticipatingEntranceTransition() {
        return this.mParticipatingEntranceTransition;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final boolean isUsingDefaultSelectEffect() {
        return false;
    }

    public final void notifyOnBindLogo(ViewHolder viewHolder) {
        onLayoutOverviewFrame(viewHolder, viewHolder.getState(), true);
        viewHolder.getState();
        onLayoutLogo(viewHolder);
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onBindLogo(viewHolder);
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onBindRowViewHolder(RowPresenter.ViewHolder viewHolder, Object obj) {
        super.onBindRowViewHolder(viewHolder, obj);
        DetailsOverviewRow detailsOverviewRow = (DetailsOverviewRow) obj;
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        this.mDetailsOverviewLogoPresenter.onBindViewHolder(viewHolder2.mDetailsLogoViewHolder, detailsOverviewRow);
        this.mDetailsPresenter.onBindViewHolder(viewHolder2.mDetailsDescriptionViewHolder, detailsOverviewRow.getItem());
        DetailsOverviewRow detailsOverviewRow2 = (DetailsOverviewRow) viewHolder2.getRow();
        viewHolder2.bindActions(detailsOverviewRow2.getActionsAdapter());
        detailsOverviewRow2.addListener(viewHolder2.mRowListener);
    }

    public final void onLayoutLogo(ViewHolder viewHolder) {
        View view = viewHolder.getLogoViewHolder().view;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (this.mAlignmentMode != 1) {
            marginLayoutParams.setMarginStart(view.getResources().getDimensionPixelSize(R.dimen.lb_details_v2_logo_margin_start));
        } else {
            marginLayoutParams.setMarginStart(view.getResources().getDimensionPixelSize(R.dimen.lb_details_v2_left) - marginLayoutParams.width);
        }
        int state = viewHolder.getState();
        if (state == 0) {
            marginLayoutParams.topMargin = view.getResources().getDimensionPixelSize(R.dimen.lb_details_v2_description_margin_top) + view.getResources().getDimensionPixelSize(R.dimen.lb_details_v2_actions_height) + view.getResources().getDimensionPixelSize(R.dimen.lb_details_v2_blank_height);
        } else if (state != 2) {
            marginLayoutParams.topMargin = view.getResources().getDimensionPixelSize(R.dimen.lb_details_v2_blank_height) - (marginLayoutParams.height / 2);
        } else {
            marginLayoutParams.topMargin = 0;
        }
        view.setLayoutParams(marginLayoutParams);
    }

    public final void onLayoutOverviewFrame(ViewHolder viewHolder, int i, boolean z) {
        int dimensionPixelSize;
        boolean z2 = i == 2;
        boolean z3 = viewHolder.getState() == 2;
        if (z2 != z3 || z) {
            Resources resources = viewHolder.view.getResources();
            int dimensionPixelSize2 = this.mDetailsOverviewLogoPresenter.isBoundToImage(viewHolder.getLogoViewHolder(), (DetailsOverviewRow) viewHolder.getRow()) ? viewHolder.getLogoViewHolder().view.getLayoutParams().width : 0;
            if (this.mAlignmentMode != 1) {
                if (z3) {
                    dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.lb_details_v2_logo_margin_start);
                } else {
                    dimensionPixelSize2 += resources.getDimensionPixelSize(R.dimen.lb_details_v2_logo_margin_start);
                    dimensionPixelSize = 0;
                }
            } else if (z3) {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.lb_details_v2_left) - dimensionPixelSize2;
            } else {
                dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.lb_details_v2_left);
                dimensionPixelSize = 0;
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewHolder.getOverviewView().getLayoutParams();
            marginLayoutParams.topMargin = z3 ? 0 : resources.getDimensionPixelSize(R.dimen.lb_details_v2_blank_height);
            marginLayoutParams.rightMargin = dimensionPixelSize;
            marginLayoutParams.leftMargin = dimensionPixelSize;
            viewHolder.getOverviewView().setLayoutParams(marginLayoutParams);
            ViewGroup detailsDescriptionFrame = viewHolder.getDetailsDescriptionFrame();
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) detailsDescriptionFrame.getLayoutParams();
            marginLayoutParams2.setMarginStart(dimensionPixelSize2);
            detailsDescriptionFrame.setLayoutParams(marginLayoutParams2);
            ViewGroup actionsRow = viewHolder.getActionsRow();
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) actionsRow.getLayoutParams();
            marginLayoutParams3.setMarginStart(dimensionPixelSize2);
            marginLayoutParams3.height = z3 ? 0 : resources.getDimensionPixelSize(R.dimen.lb_details_v2_actions_height);
            actionsRow.setLayoutParams(marginLayoutParams3);
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onRowViewAttachedToWindow(RowPresenter.ViewHolder viewHolder) {
        super.onRowViewAttachedToWindow(viewHolder);
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        this.mDetailsPresenter.onViewAttachedToWindow(viewHolder2.mDetailsDescriptionViewHolder);
        this.mDetailsOverviewLogoPresenter.onViewAttachedToWindow(viewHolder2.mDetailsLogoViewHolder);
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onRowViewDetachedFromWindow(RowPresenter.ViewHolder viewHolder) {
        super.onRowViewDetachedFromWindow(viewHolder);
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        this.mDetailsPresenter.onViewDetachedFromWindow(viewHolder2.mDetailsDescriptionViewHolder);
        this.mDetailsOverviewLogoPresenter.onViewDetachedFromWindow(viewHolder2.mDetailsLogoViewHolder);
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onSelectLevelChanged(RowPresenter.ViewHolder viewHolder) {
        super.onSelectLevelChanged(viewHolder);
        if (getSelectEffectEnabled()) {
            ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            ((ColorDrawable) viewHolder2.mOverviewFrame.getForeground().mutate()).setColor(viewHolder2.mColorDimmer.getPaint().getColor());
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onUnbindRowViewHolder(RowPresenter.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        ((DetailsOverviewRow) viewHolder2.getRow()).removeListener(viewHolder2.mRowListener);
        sHandler.removeCallbacks(viewHolder2.mUpdateDrawableCallback);
        this.mDetailsPresenter.onUnbindViewHolder(viewHolder2.mDetailsDescriptionViewHolder);
        this.mDetailsOverviewLogoPresenter.onUnbindViewHolder(viewHolder2.mDetailsLogoViewHolder);
        super.onUnbindRowViewHolder(viewHolder);
    }

    public final void setActionsBackgroundColor(int i) {
        this.mActionsBackgroundColor = i;
        this.mActionsBackgroundColorSet = true;
    }

    public final void setAlignmentMode(int i) {
        this.mAlignmentMode = i;
    }

    public final void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        this.mBackgroundColorSet = true;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public void setEntranceTransitionState(RowPresenter.ViewHolder viewHolder, boolean z) {
        super.setEntranceTransitionState(viewHolder, z);
        if (this.mParticipatingEntranceTransition) {
            viewHolder.view.setVisibility(z ? 0 : 4);
        }
    }

    public final void setInitialState(int i) {
        this.mInitialState = i;
    }

    public final void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void setOnActionClickedListener(OnActionClickedListener onActionClickedListener) {
        this.mActionClickedListener = onActionClickedListener;
    }

    public final void setParticipatingEntranceTransition(boolean z) {
        this.mParticipatingEntranceTransition = z;
    }

    public final void setState(ViewHolder viewHolder, int i) {
        if (viewHolder.getState() != i) {
            int state = viewHolder.getState();
            viewHolder.mState = i;
            onLayoutOverviewFrame(viewHolder, state, false);
            onLayoutLogo(viewHolder);
        }
    }

    public FullWidthDetailsOverviewRowPresenter(Presenter presenter, DetailsOverviewLogoPresenter detailsOverviewLogoPresenter) {
        this.mInitialState = 0;
        this.mBackgroundColor = 0;
        this.mActionsBackgroundColor = 0;
        setHeaderPresenter(null);
        setSelectEffectEnabled(false);
        this.mDetailsPresenter = presenter;
        this.mDetailsOverviewLogoPresenter = detailsOverviewLogoPresenter;
    }
}
