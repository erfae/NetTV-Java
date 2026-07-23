package androidx.leanback.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.core.view.ViewCompat;
import androidx.leanback.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractMediaItemPresenter extends RowPresenter {
    public static final int PLAY_STATE_INITIAL = 0;
    public static final int PLAY_STATE_PAUSED = 1;
    public static final int PLAY_STATE_PLAYING = 2;
    public static final Rect sTempRect = new Rect();
    private int mBackgroundColor;
    private boolean mBackgroundColorSet;
    private Presenter mMediaItemActionPresenter;
    private boolean mMediaRowSeparator;
    private int mThemeId;

    public static class ViewHolder extends RowPresenter.ViewHolder {
        private final List<Presenter.ViewHolder> mActionViewHolders;
        public ValueAnimator mFocusViewAnimator;
        private final ViewGroup mMediaItemActionsContainer;
        private final View mMediaItemDetailsView;
        private final TextView mMediaItemDurationView;
        private final TextView mMediaItemNameView;
        public final TextView mMediaItemNumberView;
        public final ViewFlipper mMediaItemNumberViewFlipper;
        public final View mMediaItemPausedView;
        public final View mMediaItemPlayingView;
        public MultiActionsProvider.MultiAction[] mMediaItemRowActions;
        private final View mMediaItemRowSeparator;
        public final View mMediaRowView;
        public AbstractMediaItemPresenter mRowPresenter;
        public final View mSelectorView;

        public ViewHolder(View view) {
            super(view);
            this.mSelectorView = view.findViewById(R.id.mediaRowSelector);
            this.mMediaRowView = view.findViewById(R.id.mediaItemRow);
            this.mMediaItemDetailsView = view.findViewById(R.id.mediaItemDetails);
            this.mMediaItemNameView = (TextView) view.findViewById(R.id.mediaItemName);
            this.mMediaItemDurationView = (TextView) view.findViewById(R.id.mediaItemDuration);
            this.mMediaItemRowSeparator = view.findViewById(R.id.mediaRowSeparator);
            this.mMediaItemActionsContainer = (ViewGroup) view.findViewById(R.id.mediaItemActionsContainer);
            this.mActionViewHolders = new ArrayList();
            getMediaItemDetailsView().setOnClickListener(new View.OnClickListener() { // from class: androidx.leanback.widget.AbstractMediaItemPresenter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (ViewHolder.this.getOnItemViewClickedListener() != null) {
                        BaseOnItemViewClickedListener onItemViewClickedListener = ViewHolder.this.getOnItemViewClickedListener();
                        ViewHolder viewHolder = ViewHolder.this;
                        onItemViewClickedListener.onItemClicked(null, null, viewHolder, viewHolder.getRowObject());
                    }
                }
            });
            getMediaItemDetailsView().setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.leanback.widget.AbstractMediaItemPresenter.ViewHolder.2
                @Override // android.view.View.OnFocusChangeListener
                public void onFocusChange(View view2, boolean z) {
                    ViewHolder viewHolder = ViewHolder.this;
                    viewHolder.mFocusViewAnimator = AbstractMediaItemPresenter.updateSelector(viewHolder.mSelectorView, view2, viewHolder.mFocusViewAnimator, true);
                }
            });
            ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.mediaItemNumberViewFlipper);
            this.mMediaItemNumberViewFlipper = viewFlipper;
            TypedValue typedValue = new TypedValue();
            View viewInflate = LayoutInflater.from(view.getContext()).inflate(view.getContext().getTheme().resolveAttribute(R.attr.playbackMediaItemNumberViewFlipperLayout, typedValue, true) ? typedValue.resourceId : R.layout.lb_media_item_number_view_flipper, (ViewGroup) viewFlipper, true);
            this.mMediaItemNumberView = (TextView) viewInflate.findViewById(R.id.initial);
            this.mMediaItemPausedView = viewInflate.findViewById(R.id.paused);
            this.mMediaItemPlayingView = viewInflate.findViewById(R.id.playing);
        }

        public ViewGroup getMediaItemActionsContainer() {
            return this.mMediaItemActionsContainer;
        }

        public View getMediaItemDetailsView() {
            return this.mMediaItemDetailsView;
        }

        public TextView getMediaItemDurationView() {
            return this.mMediaItemDurationView;
        }

        public TextView getMediaItemNameView() {
            return this.mMediaItemNameView;
        }

        public TextView getMediaItemNumberView() {
            return this.mMediaItemNumberView;
        }

        public ViewFlipper getMediaItemNumberViewFlipper() {
            return this.mMediaItemNumberViewFlipper;
        }

        public View getMediaItemPausedView() {
            return this.mMediaItemPausedView;
        }

        public View getMediaItemPlayingView() {
            return this.mMediaItemPlayingView;
        }

        public MultiActionsProvider.MultiAction[] getMediaItemRowActions() {
            return this.mMediaItemRowActions;
        }

        public View getMediaItemRowSeparator() {
            return this.mMediaItemRowSeparator;
        }

        public View getSelectorView() {
            return this.mSelectorView;
        }

        public void notifyActionChanged(MultiActionsProvider.MultiAction multiAction) {
            int i;
            Presenter actionPresenter = this.mRowPresenter.getActionPresenter();
            if (actionPresenter == null) {
                return;
            }
            if (this.mMediaItemRowActions == null) {
                i = -1;
                break;
            }
            i = 0;
            while (true) {
                MultiActionsProvider.MultiAction[] multiActionArr = this.mMediaItemRowActions;
                if (i >= multiActionArr.length) {
                    i = -1;
                    break;
                } else if (multiActionArr[i] == multiAction) {
                    break;
                } else {
                    i++;
                }
            }
            if (i >= 0) {
                Presenter.ViewHolder viewHolder = this.mActionViewHolders.get(i);
                actionPresenter.onUnbindViewHolder(viewHolder);
                actionPresenter.onBindViewHolder(viewHolder, multiAction);
            }
        }

        public void notifyDetailsChanged() {
            Objects.requireNonNull(this.mRowPresenter);
            AbstractMediaItemPresenter abstractMediaItemPresenter = this.mRowPresenter;
            getRowObject();
            abstractMediaItemPresenter.onBindMediaDetails();
        }

        public void notifyPlayStateChanged() {
            this.mRowPresenter.onBindMediaPlayState(this);
        }

        public void onBindRowActions() {
            int childCount = getMediaItemActionsContainer().getChildCount();
            while (true) {
                childCount--;
                if (childCount < this.mActionViewHolders.size()) {
                    break;
                }
                getMediaItemActionsContainer().removeViewAt(childCount);
                this.mActionViewHolders.remove(childCount);
            }
            this.mMediaItemRowActions = null;
            Object rowObject = getRowObject();
            if (rowObject instanceof MultiActionsProvider) {
                MultiActionsProvider.MultiAction[] actions = ((MultiActionsProvider) rowObject).getActions();
                Presenter actionPresenter = this.mRowPresenter.getActionPresenter();
                if (actionPresenter == null) {
                    return;
                }
                this.mMediaItemRowActions = actions;
                for (final int size = this.mActionViewHolders.size(); size < actions.length; size++) {
                    final Presenter.ViewHolder viewHolderOnCreateViewHolder = actionPresenter.onCreateViewHolder(getMediaItemActionsContainer());
                    getMediaItemActionsContainer().addView(viewHolderOnCreateViewHolder.view);
                    this.mActionViewHolders.add(viewHolderOnCreateViewHolder);
                    viewHolderOnCreateViewHolder.view.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.leanback.widget.AbstractMediaItemPresenter.ViewHolder.3
                        @Override // android.view.View.OnFocusChangeListener
                        public void onFocusChange(View view, boolean z) {
                            ViewHolder viewHolder = ViewHolder.this;
                            viewHolder.mFocusViewAnimator = AbstractMediaItemPresenter.updateSelector(viewHolder.mSelectorView, view, viewHolder.mFocusViewAnimator, false);
                        }
                    });
                    viewHolderOnCreateViewHolder.view.setOnClickListener(new View.OnClickListener() { // from class: androidx.leanback.widget.AbstractMediaItemPresenter.ViewHolder.4
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            if (ViewHolder.this.getOnItemViewClickedListener() != null) {
                                BaseOnItemViewClickedListener onItemViewClickedListener = ViewHolder.this.getOnItemViewClickedListener();
                                Presenter.ViewHolder viewHolder = viewHolderOnCreateViewHolder;
                                ViewHolder viewHolder2 = ViewHolder.this;
                                onItemViewClickedListener.onItemClicked(viewHolder, viewHolder2.mMediaItemRowActions[size], viewHolder2, viewHolder2.getRowObject());
                            }
                        }
                    });
                }
                if (this.mMediaItemActionsContainer != null) {
                    for (int i = 0; i < actions.length; i++) {
                        Presenter.ViewHolder viewHolder = this.mActionViewHolders.get(i);
                        actionPresenter.onUnbindViewHolder(viewHolder);
                        actionPresenter.onBindViewHolder(viewHolder, this.mMediaItemRowActions[i]);
                    }
                }
            }
        }

        public void setSelectedMediaItemNumberView(int i) {
            if (i < 0 || i >= this.mMediaItemNumberViewFlipper.getChildCount()) {
                return;
            }
            this.mMediaItemNumberViewFlipper.setDisplayedChild(i);
        }
    }

    public AbstractMediaItemPresenter() {
        this(0);
    }

    public static ValueAnimator updateSelector(final View view, View view2, ValueAnimator valueAnimator, boolean z) {
        ValueAnimator valueAnimator2;
        int integer = view2.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
        int layoutDirection = ViewCompat.getLayoutDirection(view);
        if (!view2.hasFocus()) {
            view.animate().cancel();
            view.animate().alpha(0.0f).setDuration(integer).setInterpolator(decelerateInterpolator).start();
            return valueAnimator;
        }
        if (valueAnimator != null) {
            valueAnimator.cancel();
            valueAnimator2 = null;
        } else {
            valueAnimator2 = valueAnimator;
        }
        float alpha = view.getAlpha();
        long j = integer;
        view.animate().alpha(1.0f).setDuration(j).setInterpolator(decelerateInterpolator).start();
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        Rect rect = sTempRect;
        rect.set(0, 0, view2.getWidth(), view2.getHeight());
        viewGroup.offsetDescendantRectToMyCoords(view2, rect);
        if (z) {
            if (layoutDirection == 1) {
                rect.right = viewGroup.getHeight() + rect.right;
                rect.left -= viewGroup.getHeight() / 2;
            } else {
                rect.left -= viewGroup.getHeight();
                rect.right = (viewGroup.getHeight() / 2) + rect.right;
            }
        }
        final int i = rect.left;
        final int iWidth = rect.width();
        final float f = marginLayoutParams.width - iWidth;
        final float f2 = marginLayoutParams.leftMargin - i;
        if (f2 == 0.0f && f == 0.0f) {
            return valueAnimator2;
        }
        if (alpha == 0.0f) {
            marginLayoutParams.width = iWidth;
            marginLayoutParams.leftMargin = i;
            view.requestLayout();
            return valueAnimator2;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(j);
        valueAnimatorOfFloat.setInterpolator(decelerateInterpolator);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.leanback.widget.AbstractMediaItemPresenter.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator3) {
                float animatedFraction = 1.0f - valueAnimator3.getAnimatedFraction();
                marginLayoutParams.leftMargin = Math.round((f2 * animatedFraction) + i);
                marginLayoutParams.width = Math.round((f * animatedFraction) + iWidth);
                view.requestLayout();
            }
        });
        valueAnimatorOfFloat.start();
        return valueAnimatorOfFloat;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final RowPresenter.ViewHolder createRowViewHolder(ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if (this.mThemeId != 0) {
            context = new ContextThemeWrapper(context, this.mThemeId);
        }
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.lb_row_media_item, viewGroup, false));
        viewHolder.mRowPresenter = this;
        if (this.mBackgroundColorSet) {
            viewHolder.mMediaRowView.setBackgroundColor(this.mBackgroundColor);
        }
        return viewHolder;
    }

    public Presenter getActionPresenter() {
        return this.mMediaItemActionPresenter;
    }

    public int getThemeId() {
        return this.mThemeId;
    }

    public boolean hasMediaRowSeparator() {
        return this.mMediaRowSeparator;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public boolean isUsingDefaultSelectEffect() {
        return false;
    }

    public abstract void onBindMediaDetails();

    public void onBindMediaPlayState(ViewHolder viewHolder) {
        AbstractMediaItemPresenter abstractMediaItemPresenter = viewHolder.mRowPresenter;
        viewHolder.getRowObject();
        Objects.requireNonNull(abstractMediaItemPresenter);
        TextView textView = viewHolder.mMediaItemNumberView;
        int iIndexOfChild = textView == null ? -1 : viewHolder.mMediaItemNumberViewFlipper.indexOfChild(textView);
        if (iIndexOfChild == -1 || viewHolder.mMediaItemNumberViewFlipper.getDisplayedChild() == iIndexOfChild) {
            return;
        }
        viewHolder.mMediaItemNumberViewFlipper.setDisplayedChild(iIndexOfChild);
    }

    @Override // androidx.leanback.widget.RowPresenter
    public final void onBindRowViewHolder(RowPresenter.ViewHolder viewHolder, Object obj) {
        super.onBindRowViewHolder(viewHolder, obj);
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.onBindRowActions();
        viewHolder2.getMediaItemRowSeparator().setVisibility(hasMediaRowSeparator() ? 0 : 8);
        onBindMediaPlayState(viewHolder2);
        onBindMediaDetails();
    }

    public void onUnbindMediaPlayState(ViewHolder viewHolder) {
    }

    public void setActionPresenter(Presenter presenter) {
        this.mMediaItemActionPresenter = presenter;
    }

    public void setBackgroundColor(int i) {
        this.mBackgroundColorSet = true;
        this.mBackgroundColor = i;
    }

    public void setHasMediaRowSeparator(boolean z) {
        this.mMediaRowSeparator = z;
    }

    public void setThemeId(int i) {
        this.mThemeId = i;
    }

    public AbstractMediaItemPresenter(int i) {
        this.mBackgroundColor = 0;
        this.mMediaItemActionPresenter = new MediaItemActionPresenter();
        this.mThemeId = i;
        setHeaderPresenter(null);
    }
}
