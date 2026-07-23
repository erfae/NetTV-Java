package androidx.leanback.widget;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
class ControlBarPresenter extends Presenter {
    private static int sChildMarginDefault;
    private static int sControlIconWidth;
    public boolean mDefaultFocusToMiddle = true;
    private int mLayoutResourceId;
    public OnControlClickedListener mOnControlClickedListener;
    public OnControlSelectedListener mOnControlSelectedListener;

    public static class BoundData {
        public ObjectAdapter adapter;
        public Presenter presenter;
    }

    public interface OnControlClickedListener {
        void onControlClicked(Presenter.ViewHolder viewHolder, Object obj, BoundData boundData);
    }

    public interface OnControlSelectedListener {
        void onControlSelected(Presenter.ViewHolder viewHolder, Object obj, BoundData boundData);
    }

    public class ViewHolder extends Presenter.ViewHolder {
        public ObjectAdapter mAdapter;
        public ControlBar mControlBar;
        public View mControlsContainer;
        public BoundData mData;
        public ObjectAdapter.DataObserver mDataObserver;
        public Presenter mPresenter;
        public SparseArray<Presenter.ViewHolder> mViewHolders;

        public ViewHolder(View view) {
            super(view);
            this.mViewHolders = new SparseArray<>();
            this.mControlsContainer = view.findViewById(R.id.controls_container);
            ControlBar controlBar = (ControlBar) view.findViewById(R.id.control_bar);
            this.mControlBar = controlBar;
            if (controlBar == null) {
                throw new IllegalStateException("Couldn't find control_bar");
            }
            controlBar.mDefaultFocusToMiddle = ControlBarPresenter.this.mDefaultFocusToMiddle;
            controlBar.setOnChildFocusedListener(new ControlBar.OnChildFocusedListener() { // from class: androidx.leanback.widget.ControlBarPresenter.ViewHolder.1
                @Override // androidx.leanback.widget.ControlBar.OnChildFocusedListener
                public void onChildFocusedListener(View view2, View view3) {
                    if (ControlBarPresenter.this.mOnControlSelectedListener == null) {
                        return;
                    }
                    for (int i = 0; i < ViewHolder.this.mViewHolders.size(); i++) {
                        if (ViewHolder.this.mViewHolders.get(i).view == view2) {
                            ViewHolder viewHolder = ViewHolder.this;
                            ControlBarPresenter.this.mOnControlSelectedListener.onControlSelected(viewHolder.mViewHolders.get(i), ViewHolder.this.getDisplayedAdapter().get(i), ViewHolder.this.mData);
                            return;
                        }
                    }
                }
            });
            this.mDataObserver = new ObjectAdapter.DataObserver() { // from class: androidx.leanback.widget.ControlBarPresenter.ViewHolder.2
                @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
                public void onChanged() {
                    ViewHolder viewHolder = ViewHolder.this;
                    if (viewHolder.mAdapter == viewHolder.getDisplayedAdapter()) {
                        ViewHolder viewHolder2 = ViewHolder.this;
                        viewHolder2.showControls(viewHolder2.mPresenter);
                    }
                }

                @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
                public void onItemRangeChanged(int i, int i2) {
                    ViewHolder viewHolder = ViewHolder.this;
                    if (viewHolder.mAdapter == viewHolder.getDisplayedAdapter()) {
                        for (int i3 = 0; i3 < i2; i3++) {
                            ViewHolder viewHolder2 = ViewHolder.this;
                            viewHolder2.bindControlToAction(i + i3, viewHolder2.mPresenter);
                        }
                    }
                }
            };
        }

        public final void bindControlToAction(int i, Presenter presenter) {
            bindControlToAction(i, getDisplayedAdapter(), presenter);
        }

        public int getChildMarginFromCenter(Context context, int i) {
            return ControlBarPresenter.this.getChildMarginDefault(context) + ControlBarPresenter.this.getControlIconWidth(context);
        }

        public ObjectAdapter getDisplayedAdapter() {
            return this.mAdapter;
        }

        public final void showControls(Presenter presenter) {
            ObjectAdapter displayedAdapter = getDisplayedAdapter();
            int size = displayedAdapter == null ? 0 : displayedAdapter.size();
            View focusedChild = this.mControlBar.getFocusedChild();
            if (focusedChild != null && size > 0 && this.mControlBar.indexOfChild(focusedChild) >= size) {
                this.mControlBar.getChildAt(displayedAdapter.size() - 1).requestFocus();
            }
            for (int childCount = this.mControlBar.getChildCount() - 1; childCount >= size; childCount--) {
                this.mControlBar.removeViewAt(childCount);
            }
            for (int i = 0; i < size && i < 7; i++) {
                bindControlToAction(i, displayedAdapter, presenter);
            }
            ControlBar controlBar = this.mControlBar;
            controlBar.setChildMarginFromCenter(getChildMarginFromCenter(controlBar.getContext(), size));
        }

        private void bindControlToAction(final int i, ObjectAdapter objectAdapter, Presenter presenter) {
            final Presenter.ViewHolder viewHolderOnCreateViewHolder = this.mViewHolders.get(i);
            Object obj = objectAdapter.get(i);
            if (viewHolderOnCreateViewHolder == null) {
                viewHolderOnCreateViewHolder = presenter.onCreateViewHolder(this.mControlBar);
                this.mViewHolders.put(i, viewHolderOnCreateViewHolder);
                presenter.setOnClickListener(viewHolderOnCreateViewHolder, new View.OnClickListener() { // from class: androidx.leanback.widget.ControlBarPresenter.ViewHolder.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        Object obj2 = ViewHolder.this.getDisplayedAdapter().get(i);
                        ViewHolder viewHolder = ViewHolder.this;
                        OnControlClickedListener onControlClickedListener = ControlBarPresenter.this.mOnControlClickedListener;
                        if (onControlClickedListener != null) {
                            onControlClickedListener.onControlClicked(viewHolderOnCreateViewHolder, obj2, viewHolder.mData);
                        }
                    }
                });
            }
            if (viewHolderOnCreateViewHolder.view.getParent() == null) {
                this.mControlBar.addView(viewHolderOnCreateViewHolder.view);
            }
            presenter.onBindViewHolder(viewHolderOnCreateViewHolder, obj);
        }
    }

    public ControlBarPresenter(int i) {
        this.mLayoutResourceId = i;
    }

    public final int getChildMarginDefault(Context context) {
        if (sChildMarginDefault == 0) {
            sChildMarginDefault = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_controls_child_margin_default);
        }
        return sChildMarginDefault;
    }

    public final int getControlIconWidth(Context context) {
        if (sControlIconWidth == 0) {
            sControlIconWidth = context.getResources().getDimensionPixelSize(R.dimen.lb_control_icon_width);
        }
        return sControlIconWidth;
    }

    public int getLayoutResourceId() {
        return this.mLayoutResourceId;
    }

    public OnControlSelectedListener getOnItemControlListener() {
        return this.mOnControlSelectedListener;
    }

    public OnControlClickedListener getOnItemViewClickedListener() {
        return this.mOnControlClickedListener;
    }

    @Override // androidx.leanback.widget.Presenter
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object obj) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        BoundData boundData = (BoundData) obj;
        ObjectAdapter objectAdapter = viewHolder2.mAdapter;
        ObjectAdapter objectAdapter2 = boundData.adapter;
        if (objectAdapter != objectAdapter2) {
            viewHolder2.mAdapter = objectAdapter2;
            if (objectAdapter2 != null) {
                objectAdapter2.registerObserver(viewHolder2.mDataObserver);
            }
        }
        Presenter presenter = boundData.presenter;
        viewHolder2.mPresenter = presenter;
        viewHolder2.mData = boundData;
        viewHolder2.showControls(presenter);
    }

    @Override // androidx.leanback.widget.Presenter
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutResourceId(), viewGroup, false));
    }

    @Override // androidx.leanback.widget.Presenter
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        ObjectAdapter objectAdapter = viewHolder2.mAdapter;
        if (objectAdapter != null) {
            objectAdapter.unregisterObserver(viewHolder2.mDataObserver);
            viewHolder2.mAdapter = null;
        }
        viewHolder2.mData = null;
    }

    public void setBackgroundColor(ViewHolder viewHolder, int i) {
        viewHolder.mControlsContainer.setBackgroundColor(i);
    }

    public void setOnControlClickedListener(OnControlClickedListener onControlClickedListener) {
        this.mOnControlClickedListener = onControlClickedListener;
    }

    public void setOnControlSelectedListener(OnControlSelectedListener onControlSelectedListener) {
        this.mOnControlSelectedListener = onControlSelectedListener;
    }
}
