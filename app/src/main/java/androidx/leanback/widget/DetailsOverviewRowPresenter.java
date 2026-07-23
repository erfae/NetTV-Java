package androidx.leanback.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.leanback.R;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class DetailsOverviewRowPresenter extends RowPresenter {
    private static final long DEFAULT_TIMEOUT = 5000;
    private static final int MORE_ACTIONS_FADE_MS = 100;
    public OnActionClickedListener mActionClickedListener;
    private boolean mBackgroundColorSet;
    public final Presenter mDetailsPresenter;
    private DetailsOverviewSharedElementHelper mSharedElementHelper;
    private int mBackgroundColor = 0;
    private boolean mIsStyleLarge = true;

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
            if (this.mViewHolder.getOnItemViewClickedListener() == null && DetailsOverviewRowPresenter.this.mActionClickedListener == null) {
                return;
            }
            viewHolder.getPresenter().setOnClickListener(viewHolder.getViewHolder(), new View.OnClickListener() { // from class: androidx.leanback.widget.DetailsOverviewRowPresenter.ActionsItemBridgeAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (ActionsItemBridgeAdapter.this.mViewHolder.getOnItemViewClickedListener() != null) {
                        BaseOnItemViewClickedListener onItemViewClickedListener = ActionsItemBridgeAdapter.this.mViewHolder.getOnItemViewClickedListener();
                        Presenter.ViewHolder viewHolder2 = viewHolder.getViewHolder();
                        Object item = viewHolder.getItem();
                        ViewHolder viewHolder3 = ActionsItemBridgeAdapter.this.mViewHolder;
                        onItemViewClickedListener.onItemClicked(viewHolder2, item, viewHolder3, viewHolder3.getRow());
                    }
                    OnActionClickedListener onActionClickedListener = DetailsOverviewRowPresenter.this.mActionClickedListener;
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
            if (this.mViewHolder.getOnItemViewClickedListener() == null && DetailsOverviewRowPresenter.this.mActionClickedListener == null) {
                return;
            }
            viewHolder.getPresenter().setOnClickListener(viewHolder.getViewHolder(), null);
        }
    }

    public final class ViewHolder extends RowPresenter.ViewHolder {
        public ItemBridgeAdapter mActionBridgeAdapter;
        public final HorizontalGridView mActionsRow;
        public final OnChildSelectedListener mChildSelectedListener;
        public final FrameLayout mDetailsDescriptionFrame;
        public final Presenter.ViewHolder mDetailsDescriptionViewHolder;
        public final Handler mHandler;
        public final ImageView mImageView;
        public final View.OnLayoutChangeListener mLayoutChangeListener;
        public final DetailsOverviewRow.Listener mListener;
        public int mNumItems;
        public final FrameLayout mOverviewFrame;
        public final ViewGroup mOverviewView;
        public final ViewGroup mRightPanel;
        public boolean mShowMoreLeft;
        public boolean mShowMoreRight;
        public final Runnable mUpdateDrawableCallback;

        public ViewHolder(View view, Presenter presenter) {
            super(view);
            this.mHandler = new Handler();
            this.mUpdateDrawableCallback = new Runnable() { // from class: androidx.leanback.widget.DetailsOverviewRowPresenter.ViewHolder.1
                @Override // java.lang.Runnable
                public void run() {
                    ViewHolder viewHolder = ViewHolder.this;
                    DetailsOverviewRowPresenter.this.bindImageDrawable(viewHolder);
                }
            };
            this.mListener = new DetailsOverviewRow.Listener() { // from class: androidx.leanback.widget.DetailsOverviewRowPresenter.ViewHolder.2
                @Override // androidx.leanback.widget.DetailsOverviewRow.Listener
                public void onActionsAdapterChanged(DetailsOverviewRow detailsOverviewRow) {
                    ViewHolder.this.bindActions(detailsOverviewRow.getActionsAdapter());
                }

                @Override // androidx.leanback.widget.DetailsOverviewRow.Listener
                public void onImageDrawableChanged(DetailsOverviewRow detailsOverviewRow) {
                    ViewHolder viewHolder = ViewHolder.this;
                    viewHolder.mHandler.removeCallbacks(viewHolder.mUpdateDrawableCallback);
                    ViewHolder viewHolder2 = ViewHolder.this;
                    viewHolder2.mHandler.post(viewHolder2.mUpdateDrawableCallback);
                }

                @Override // androidx.leanback.widget.DetailsOverviewRow.Listener
                public void onItemChanged(DetailsOverviewRow detailsOverviewRow) {
                    ViewHolder viewHolder = ViewHolder.this;
                    Presenter.ViewHolder viewHolder2 = viewHolder.mDetailsDescriptionViewHolder;
                    if (viewHolder2 != null) {
                        DetailsOverviewRowPresenter.this.mDetailsPresenter.onUnbindViewHolder(viewHolder2);
                    }
                    ViewHolder viewHolder3 = ViewHolder.this;
                    DetailsOverviewRowPresenter.this.mDetailsPresenter.onBindViewHolder(viewHolder3.mDetailsDescriptionViewHolder, detailsOverviewRow.getItem());
                }
            };
            this.mLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: androidx.leanback.widget.DetailsOverviewRowPresenter.ViewHolder.3
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    ViewHolder.this.checkFirstAndLastPosition();
                }
            };
            OnChildSelectedListener onChildSelectedListener = new OnChildSelectedListener() { // from class: androidx.leanback.widget.DetailsOverviewRowPresenter.ViewHolder.4
                @Override // androidx.leanback.widget.OnChildSelectedListener
                public void onChildSelected(ViewGroup viewGroup, View view2, int i, long j) {
                    ViewHolder.this.dispatchItemSelection(view2);
                }
            };
            this.mChildSelectedListener = onChildSelectedListener;
            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() { // from class: androidx.leanback.widget.DetailsOverviewRowPresenter.ViewHolder.5
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    ViewHolder.this.checkFirstAndLastPosition();
                }
            };
            this.mOverviewFrame = (FrameLayout) view.findViewById(R.id.details_frame);
            this.mOverviewView = (ViewGroup) view.findViewById(R.id.details_overview);
            this.mImageView = (ImageView) view.findViewById(R.id.details_overview_image);
            ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.details_overview_right_panel);
            this.mRightPanel = viewGroup;
            FrameLayout frameLayout = (FrameLayout) viewGroup.findViewById(R.id.details_overview_description);
            this.mDetailsDescriptionFrame = frameLayout;
            HorizontalGridView horizontalGridView = (HorizontalGridView) viewGroup.findViewById(R.id.details_overview_actions);
            this.mActionsRow = horizontalGridView;
            horizontalGridView.setHasOverlappingRendering(false);
            horizontalGridView.setOnScrollListener(onScrollListener);
            horizontalGridView.setAdapter(this.mActionBridgeAdapter);
            horizontalGridView.setOnChildSelectedListener(onChildSelectedListener);
            int dimensionPixelSize = view.getResources().getDimensionPixelSize(R.dimen.lb_details_overview_actions_fade_size);
            horizontalGridView.setFadingRightEdgeLength(dimensionPixelSize);
            horizontalGridView.setFadingLeftEdgeLength(dimensionPixelSize);
            Presenter.ViewHolder viewHolderOnCreateViewHolder = presenter.onCreateViewHolder(frameLayout);
            this.mDetailsDescriptionViewHolder = viewHolderOnCreateViewHolder;
            frameLayout.addView(viewHolderOnCreateViewHolder.view);
        }

        private int getViewCenter(View view) {
            return (view.getRight() - view.getLeft()) / 2;
        }

        private void showMoreLeft(boolean z) {
            if (z != this.mShowMoreLeft) {
                this.mActionsRow.setFadingLeftEdge(z);
                this.mShowMoreLeft = z;
            }
        }

        private void showMoreRight(boolean z) {
            if (z != this.mShowMoreRight) {
                this.mActionsRow.setFadingRightEdge(z);
                this.mShowMoreRight = z;
            }
        }

        public final void bindActions(ObjectAdapter objectAdapter) {
            this.mActionBridgeAdapter.setAdapter(objectAdapter);
            this.mActionsRow.setAdapter(this.mActionBridgeAdapter);
            this.mNumItems = this.mActionBridgeAdapter.getItemCount();
            this.mShowMoreRight = false;
            this.mShowMoreLeft = true;
            showMoreLeft(false);
        }

        public final void checkFirstAndLastPosition() {
            boolean z = true;
            RecyclerView.ViewHolder viewHolderFindViewHolderForPosition = this.mActionsRow.findViewHolderForPosition(this.mNumItems - 1);
            boolean z2 = viewHolderFindViewHolderForPosition == null || viewHolderFindViewHolderForPosition.itemView.getRight() > this.mActionsRow.getWidth();
            RecyclerView.ViewHolder viewHolderFindViewHolderForPosition2 = this.mActionsRow.findViewHolderForPosition(0);
            if (viewHolderFindViewHolderForPosition2 != null && viewHolderFindViewHolderForPosition2.itemView.getLeft() >= 0) {
                z = false;
            }
            showMoreRight(z2);
            showMoreLeft(z);
        }

        public final void dispatchItemSelection(View view) {
            RecyclerView.ViewHolder viewHolderFindViewHolderForPosition;
            if (isSelected()) {
                if (view != null) {
                    viewHolderFindViewHolderForPosition = this.mActionsRow.getChildViewHolder(view);
                } else {
                    HorizontalGridView horizontalGridView = this.mActionsRow;
                    viewHolderFindViewHolderForPosition = horizontalGridView.findViewHolderForPosition(horizontalGridView.getSelectedPosition());
                }
                ItemBridgeAdapter.ViewHolder viewHolder = (ItemBridgeAdapter.ViewHolder) viewHolderFindViewHolderForPosition;
                if (viewHolder == null) {
                    if (getOnItemViewSelectedListener() != null) {
                        getOnItemViewSelectedListener().onItemSelected(null, null, this, getRow());
                    }
                } else if (getOnItemViewSelectedListener() != null) {
                    getOnItemViewSelectedListener().onItemSelected(viewHolder.getViewHolder(), viewHolder.getItem(), this, getRow());
                }
            }
        }
    }

    public DetailsOverviewRowPresenter(Presenter presenter) {
        setHeaderPresenter(null);
        setSelectEffectEnabled(false);
        this.mDetailsPresenter = presenter;
    }

    private int getCardHeight(Context context) {
        return context.getResources().getDimensionPixelSize(this.mIsStyleLarge ? R.dimen.lb_details_overview_height_large : R.dimen.lb_details_overview_height_small);
    }

    private int getDefaultBackgroundColor(Context context) {
        TypedValue typedValue = new TypedValue();
        return context.getTheme().resolveAttribute(R.attr.defaultBrandColor, typedValue, true) ? context.getResources().getColor(typedValue.resourceId) : context.getResources().getColor(R.color.lb_default_brand_color);
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

    private void initDetailsOverview(final ViewHolder viewHolder) {
        viewHolder.mActionBridgeAdapter = new ActionsItemBridgeAdapter(viewHolder);
        FrameLayout frameLayout = viewHolder.mOverviewFrame;
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.height = getCardHeight(frameLayout.getContext());
        frameLayout.setLayoutParams(layoutParams);
        if (!getSelectEffectEnabled()) {
            viewHolder.mOverviewFrame.setForeground(null);
        }
        viewHolder.mActionsRow.setOnUnhandledKeyListener(new BaseGridView.OnUnhandledKeyListener() { // from class: androidx.leanback.widget.DetailsOverviewRowPresenter.1
            @Override // androidx.leanback.widget.BaseGridView.OnUnhandledKeyListener
            public boolean onUnhandledKey(KeyEvent keyEvent) {
                return viewHolder.getOnKeyListener() != null && viewHolder.getOnKeyListener().onKey(viewHolder.view, keyEvent.getKeyCode(), keyEvent);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0064  */
    public final void bindImageDrawable(ViewHolder viewHolder) {
        boolean z;
        final DetailsOverviewSharedElementHelper detailsOverviewSharedElementHelper;
        boolean z2;
        DetailsOverviewRow detailsOverviewRow = (DetailsOverviewRow) viewHolder.getRow();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewHolder.mImageView.getLayoutParams();
        int cardHeight = getCardHeight(viewHolder.mImageView.getContext());
        int dimensionPixelSize = viewHolder.mImageView.getResources().getDimensionPixelSize(R.dimen.lb_details_overview_image_margin_vertical);
        int dimensionPixelSize2 = viewHolder.mImageView.getResources().getDimensionPixelSize(R.dimen.lb_details_overview_image_margin_horizontal);
        int nonNegativeWidth = getNonNegativeWidth(detailsOverviewRow.getImageDrawable());
        int nonNegativeHeight = getNonNegativeHeight(detailsOverviewRow.getImageDrawable());
        boolean zIsImageScaleUpAllowed = detailsOverviewRow.isImageScaleUpAllowed();
        if (detailsOverviewRow.getImageDrawable() != null) {
            if (nonNegativeWidth > nonNegativeHeight) {
                if (this.mIsStyleLarge) {
                    z2 = true;
                    z = true;
                } else {
                    z2 = true;
                }
                if ((!z2 && nonNegativeWidth > cardHeight) || (!z2 && nonNegativeHeight > cardHeight)) {
                }
                if (!zIsImageScaleUpAllowed) {
                    z = true;
                }
                if (z && !zIsImageScaleUpAllowed && ((z2 && nonNegativeWidth > cardHeight - dimensionPixelSize2) || (!z2 && nonNegativeHeight > cardHeight - (dimensionPixelSize * 2)))) {
                    zIsImageScaleUpAllowed = true;
                }
            } else {
                z2 = false;
            }
            z = false;
            zIsImageScaleUpAllowed = !z2 ? true : true;
            if (!zIsImageScaleUpAllowed) {
                z = true;
            }
            if (z) {
                zIsImageScaleUpAllowed = true;
            }
        } else {
            z = false;
        }
        int defaultBackgroundColor = this.mBackgroundColorSet ? this.mBackgroundColor : getDefaultBackgroundColor(viewHolder.mOverviewView.getContext());
        if (z) {
            marginLayoutParams.setMarginStart(dimensionPixelSize2);
            marginLayoutParams.bottomMargin = dimensionPixelSize;
            marginLayoutParams.topMargin = dimensionPixelSize;
            viewHolder.mOverviewFrame.setBackgroundColor(defaultBackgroundColor);
            viewHolder.mRightPanel.setBackground(null);
            viewHolder.mImageView.setBackground(null);
        } else {
            marginLayoutParams.bottomMargin = 0;
            marginLayoutParams.topMargin = 0;
            marginLayoutParams.leftMargin = 0;
            viewHolder.mRightPanel.setBackgroundColor(defaultBackgroundColor);
            viewHolder.mImageView.setBackgroundColor(defaultBackgroundColor);
            viewHolder.mOverviewFrame.setBackground(null);
        }
        RoundedRectHelper.setClipToRoundedOutline(viewHolder.mOverviewFrame, true);
        if (zIsImageScaleUpAllowed) {
            viewHolder.mImageView.setScaleType(ImageView.ScaleType.FIT_START);
            viewHolder.mImageView.setAdjustViewBounds(true);
            viewHolder.mImageView.setMaxWidth(cardHeight);
            marginLayoutParams.height = -1;
            marginLayoutParams.width = -2;
        } else {
            viewHolder.mImageView.setScaleType(ImageView.ScaleType.CENTER);
            viewHolder.mImageView.setAdjustViewBounds(false);
            marginLayoutParams.height = -2;
            marginLayoutParams.width = Math.min(cardHeight, nonNegativeWidth);
        }
        viewHolder.mImageView.setLayoutParams(marginLayoutParams);
        viewHolder.mImageView.setImageDrawable(detailsOverviewRow.getImageDrawable());
        if (detailsOverviewRow.getImageDrawable() == null || (detailsOverviewSharedElementHelper = this.mSharedElementHelper) == null) {
            return;
        }
        ViewHolder viewHolder2 = detailsOverviewSharedElementHelper.mViewHolder;
        if (viewHolder2 != null) {
            ViewCompat.setTransitionName(viewHolder2.mOverviewFrame, null);
        }
        detailsOverviewSharedElementHelper.mViewHolder = viewHolder;
        viewHolder.mRightPanel.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.leanback.widget.DetailsOverviewSharedElementHelper.1
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                DetailsOverviewSharedElementHelper.this.mViewHolder.mRightPanel.removeOnLayoutChangeListener(this);
                DetailsOverviewSharedElementHelper detailsOverviewSharedElementHelper2 = DetailsOverviewSharedElementHelper.this;
                detailsOverviewSharedElementHelper2.mRightPanelWidth = detailsOverviewSharedElementHelper2.mViewHolder.mRightPanel.getWidth();
                DetailsOverviewSharedElementHelper detailsOverviewSharedElementHelper3 = DetailsOverviewSharedElementHelper.this;
                detailsOverviewSharedElementHelper3.mRightPanelHeight = detailsOverviewSharedElementHelper3.mViewHolder.mRightPanel.getHeight();
            }
        });
        detailsOverviewSharedElementHelper.mViewHolder.mRightPanel.postOnAnimation(new Runnable() { // from class: androidx.leanback.widget.DetailsOverviewSharedElementHelper.2
            @Override // java.lang.Runnable
            public void run() {
                DetailsOverviewSharedElementHelper detailsOverviewSharedElementHelper2 = DetailsOverviewSharedElementHelper.this;
                ViewCompat.setTransitionName(detailsOverviewSharedElementHelper2.mViewHolder.mOverviewFrame, detailsOverviewSharedElementHelper2.mSharedElementName);
                Object sharedElementEnterTransition = TransitionHelper.getSharedElementEnterTransition(DetailsOverviewSharedElementHelper.this.mActivityToRunTransition.getWindow());
                if (sharedElementEnterTransition != null) {
                    TransitionHelper.addTransitionListener(sharedElementEnterTransition, new TransitionListener() { // from class: androidx.leanback.widget.DetailsOverviewSharedElementHelper.2.1
                        @Override // androidx.leanback.transition.TransitionListener
                        public void onTransitionEnd(Object obj) {
                            if (DetailsOverviewSharedElementHelper.this.mViewHolder.mActionsRow.isFocused()) {
                                DetailsOverviewSharedElementHelper.this.mViewHolder.mActionsRow.requestFocus();
                            }
                            TransitionHelper.removeTransitionListener(obj, this);
                        }
                    });
                }
                DetailsOverviewSharedElementHelper detailsOverviewSharedElementHelper3 = DetailsOverviewSharedElementHelper.this;
                if (detailsOverviewSharedElementHelper3.mStartedPostpone) {
                    return;
                }
                ActivityCompat.startPostponedEnterTransition(detailsOverviewSharedElementHelper3.mActivityToRunTransition);
                detailsOverviewSharedElementHelper3.mStartedPostpone = true;
            }
        });
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final RowPresenter.ViewHolder createRowViewHolder(ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lb_details_overview, viewGroup, false), this.mDetailsPresenter);
        initDetailsOverview(viewHolder);
        return viewHolder;
    }

    @ColorInt
    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public OnActionClickedListener getOnActionClickedListener() {
        return this.mActionClickedListener;
    }

    public boolean isStyleLarge() {
        return this.mIsStyleLarge;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final boolean isUsingDefaultSelectEffect() {
        return false;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onBindRowViewHolder(RowPresenter.ViewHolder viewHolder, Object obj) {
        super.onBindRowViewHolder(viewHolder, obj);
        DetailsOverviewRow detailsOverviewRow = (DetailsOverviewRow) obj;
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        bindImageDrawable(viewHolder2);
        this.mDetailsPresenter.onBindViewHolder(viewHolder2.mDetailsDescriptionViewHolder, detailsOverviewRow.getItem());
        viewHolder2.bindActions(detailsOverviewRow.getActionsAdapter());
        detailsOverviewRow.addListener(viewHolder2.mListener);
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onRowViewAttachedToWindow(RowPresenter.ViewHolder viewHolder) {
        super.onRowViewAttachedToWindow(viewHolder);
        Presenter presenter = this.mDetailsPresenter;
        if (presenter != null) {
            presenter.onViewAttachedToWindow(((ViewHolder) viewHolder).mDetailsDescriptionViewHolder);
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onRowViewDetachedFromWindow(RowPresenter.ViewHolder viewHolder) {
        super.onRowViewDetachedFromWindow(viewHolder);
        Presenter presenter = this.mDetailsPresenter;
        if (presenter != null) {
            presenter.onViewDetachedFromWindow(((ViewHolder) viewHolder).mDetailsDescriptionViewHolder);
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onRowViewSelected(RowPresenter.ViewHolder viewHolder, boolean z) {
        super.onRowViewSelected(viewHolder, z);
        if (z) {
            ((ViewHolder) viewHolder).dispatchItemSelection(null);
        }
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
        ((DetailsOverviewRow) viewHolder2.getRow()).removeListener(viewHolder2.mListener);
        Presenter.ViewHolder viewHolder3 = viewHolder2.mDetailsDescriptionViewHolder;
        if (viewHolder3 != null) {
            this.mDetailsPresenter.onUnbindViewHolder(viewHolder3);
        }
        super.onUnbindRowViewHolder(viewHolder);
    }

    public void setBackgroundColor(@ColorInt int i) {
        this.mBackgroundColor = i;
        this.mBackgroundColorSet = true;
    }

    public void setOnActionClickedListener(OnActionClickedListener onActionClickedListener) {
        this.mActionClickedListener = onActionClickedListener;
    }

    public final void setSharedElementEnterTransition(Activity activity, String str, long j) {
        if (this.mSharedElementHelper == null) {
            this.mSharedElementHelper = new DetailsOverviewSharedElementHelper();
        }
        DetailsOverviewSharedElementHelper detailsOverviewSharedElementHelper = this.mSharedElementHelper;
        Objects.requireNonNull(detailsOverviewSharedElementHelper);
        if ((activity == null && !TextUtils.isEmpty(str)) || (activity != null && TextUtils.isEmpty(str))) {
            throw new IllegalArgumentException();
        }
        if (activity == detailsOverviewSharedElementHelper.mActivityToRunTransition && TextUtils.equals(str, detailsOverviewSharedElementHelper.mSharedElementName)) {
            return;
        }
        Activity activity2 = detailsOverviewSharedElementHelper.mActivityToRunTransition;
        if (activity2 != null) {
            ActivityCompat.setEnterSharedElementCallback(activity2, null);
        }
        detailsOverviewSharedElementHelper.mActivityToRunTransition = activity;
        detailsOverviewSharedElementHelper.mSharedElementName = str;
        ActivityCompat.setEnterSharedElementCallback(activity, detailsOverviewSharedElementHelper);
        ActivityCompat.postponeEnterTransition(detailsOverviewSharedElementHelper.mActivityToRunTransition);
        if (j > 0) {
            new Handler().postDelayed(new DetailsOverviewSharedElementHelper.TransitionTimeOutRunnable(detailsOverviewSharedElementHelper), j);
        }
    }

    public void setStyleLarge(boolean z) {
        this.mIsStyleLarge = z;
    }

    public final void setSharedElementEnterTransition(Activity activity, String str) {
        setSharedElementEnterTransition(activity, str, 5000L);
    }
}
