package androidx.leanback.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ItemBridgeAdapter extends RecyclerView.Adapter implements FacetProviderAdapter {
    private ObjectAdapter mAdapter;
    private AdapterListener mAdapterListener;
    private ObjectAdapter.DataObserver mDataObserver;
    public FocusHighlightHandler mFocusHighlight;
    private PresenterSelector mPresenterSelector;
    private ArrayList<Presenter> mPresenters;
    public Wrapper mWrapper;

    public static class AdapterListener {
        public void onAddPresenter(Presenter presenter, int i) {
        }

        public void onAttachedToWindow(ViewHolder viewHolder) {
        }

        public void onBind(ViewHolder viewHolder) {
        }

        public void onBind(ViewHolder viewHolder, List list) {
            onBind(viewHolder);
        }

        public void onCreate(ViewHolder viewHolder) {
        }

        public void onDetachedFromWindow(ViewHolder viewHolder) {
        }

        public void onUnbind(ViewHolder viewHolder) {
        }
    }

    public final class OnFocusChangeListener implements View.OnFocusChangeListener {
        public View.OnFocusChangeListener mChainedListener;

        public OnFocusChangeListener() {
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            if (ItemBridgeAdapter.this.mWrapper != null) {
                view = (View) view.getParent();
            }
            FocusHighlightHandler focusHighlightHandler = ItemBridgeAdapter.this.mFocusHighlight;
            if (focusHighlightHandler != null) {
                focusHighlightHandler.onItemFocused(view, z);
            }
            View.OnFocusChangeListener onFocusChangeListener = this.mChainedListener;
            if (onFocusChangeListener != null) {
                onFocusChangeListener.onFocusChange(view, z);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements FacetProvider {
        public Object mExtraObject;
        public final OnFocusChangeListener mFocusChangeListener;
        public final Presenter.ViewHolder mHolder;
        public Object mItem;
        public final Presenter mPresenter;

        public ViewHolder(ItemBridgeAdapter itemBridgeAdapter, Presenter presenter, View view, Presenter.ViewHolder viewHolder) {
            super(view);
            this.mFocusChangeListener = itemBridgeAdapter.new OnFocusChangeListener();
            this.mPresenter = presenter;
            this.mHolder = viewHolder;
        }

        public final Object getExtraObject() {
            return this.mExtraObject;
        }

        @Override // androidx.leanback.widget.FacetProvider
        public Object getFacet(Class<?> cls) {
            return this.mHolder.getFacet(cls);
        }

        public final Object getItem() {
            return this.mItem;
        }

        public final Presenter getPresenter() {
            return this.mPresenter;
        }

        public final Presenter.ViewHolder getViewHolder() {
            return this.mHolder;
        }

        public void setExtraObject(Object obj) {
            this.mExtraObject = obj;
        }
    }

    public static abstract class Wrapper {
        public abstract View createWrapper(View view);

        public abstract void wrap(View view, View view2);
    }

    public ItemBridgeAdapter(ObjectAdapter objectAdapter, PresenterSelector presenterSelector) {
        this.mPresenters = new ArrayList<>();
        this.mDataObserver = new ObjectAdapter.DataObserver() { // from class: androidx.leanback.widget.ItemBridgeAdapter.1
            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onChanged() {
                ItemBridgeAdapter.this.notifyDataSetChanged();
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemMoved(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemMoved(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeChanged(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeInserted(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeInserted(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeRemoved(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeRemoved(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeChanged(int i, int i2, Object obj) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(i, i2, obj);
            }
        };
        setAdapter(objectAdapter);
        this.mPresenterSelector = presenterSelector;
    }

    public void clear() {
        setAdapter(null);
    }

    @Override // androidx.leanback.widget.FacetProviderAdapter
    public FacetProvider getFacetProvider(int i) {
        return this.mPresenters.get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ObjectAdapter objectAdapter = this.mAdapter;
        if (objectAdapter != null) {
            return objectAdapter.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return this.mAdapter.getId(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        PresenterSelector presenterSelector = this.mPresenterSelector;
        if (presenterSelector == null) {
            presenterSelector = this.mAdapter.getPresenterSelector();
        }
        Presenter presenter = presenterSelector.getPresenter(this.mAdapter.get(i));
        int iIndexOf = this.mPresenters.indexOf(presenter);
        if (iIndexOf < 0) {
            this.mPresenters.add(presenter);
            iIndexOf = this.mPresenters.indexOf(presenter);
            onAddPresenter(presenter, iIndexOf);
            AdapterListener adapterListener = this.mAdapterListener;
            if (adapterListener != null) {
                adapterListener.onAddPresenter(presenter, iIndexOf);
            }
        }
        return iIndexOf;
    }

    public ArrayList<Presenter> getPresenterMapper() {
        return this.mPresenters;
    }

    public Wrapper getWrapper() {
        return this.mWrapper;
    }

    public void onAddPresenter(Presenter presenter, int i) {
    }

    public void onAttachedToWindow(ViewHolder viewHolder) {
    }

    public void onBind(ViewHolder viewHolder) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        Object obj = this.mAdapter.get(i);
        viewHolder2.mItem = obj;
        viewHolder2.mPresenter.onBindViewHolder(viewHolder2.mHolder, obj);
        onBind(viewHolder2);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onBind(viewHolder2);
        }
    }

    public void onCreate(ViewHolder viewHolder) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Presenter.ViewHolder viewHolderOnCreateViewHolder;
        View viewCreateWrapper;
        Presenter presenter = this.mPresenters.get(i);
        Wrapper wrapper = this.mWrapper;
        if (wrapper != null) {
            viewCreateWrapper = wrapper.createWrapper(viewGroup);
            viewHolderOnCreateViewHolder = presenter.onCreateViewHolder(viewGroup);
            this.mWrapper.wrap(viewCreateWrapper, viewHolderOnCreateViewHolder.view);
        } else {
            viewHolderOnCreateViewHolder = presenter.onCreateViewHolder(viewGroup);
            viewCreateWrapper = viewHolderOnCreateViewHolder.view;
        }
        ViewHolder viewHolder = new ViewHolder(this, presenter, viewCreateWrapper, viewHolderOnCreateViewHolder);
        onCreate(viewHolder);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onCreate(viewHolder);
        }
        View view = viewHolder.mHolder.view;
        if (view != null) {
            viewHolder.mFocusChangeListener.mChainedListener = view.getOnFocusChangeListener();
            view.setOnFocusChangeListener(viewHolder.mFocusChangeListener);
        }
        FocusHighlightHandler focusHighlightHandler = this.mFocusHighlight;
        if (focusHighlightHandler != null) {
            focusHighlightHandler.onInitializeView(viewCreateWrapper);
        }
        return viewHolder;
    }

    public void onDetachedFromWindow(ViewHolder viewHolder) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final boolean onFailedToRecycleView(RecyclerView.ViewHolder viewHolder) {
        onViewRecycled(viewHolder);
        return false;
    }

    public void onUnbind(ViewHolder viewHolder) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        onAttachedToWindow(viewHolder2);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onAttachedToWindow(viewHolder2);
        }
        viewHolder2.mPresenter.onViewAttachedToWindow(viewHolder2.mHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.mPresenter.onViewDetachedFromWindow(viewHolder2.mHolder);
        onDetachedFromWindow(viewHolder2);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onDetachedFromWindow(viewHolder2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.mPresenter.onUnbindViewHolder(viewHolder2.mHolder);
        onUnbind(viewHolder2);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onUnbind(viewHolder2);
        }
        viewHolder2.mItem = null;
    }

    public void setAdapter(ObjectAdapter objectAdapter) {
        ObjectAdapter objectAdapter2 = this.mAdapter;
        if (objectAdapter == objectAdapter2) {
            return;
        }
        if (objectAdapter2 != null) {
            objectAdapter2.unregisterObserver(this.mDataObserver);
        }
        this.mAdapter = objectAdapter;
        if (objectAdapter == null) {
            notifyDataSetChanged();
            return;
        }
        objectAdapter.registerObserver(this.mDataObserver);
        if (hasStableIds() != this.mAdapter.hasStableIds()) {
            setHasStableIds(this.mAdapter.hasStableIds());
        }
        notifyDataSetChanged();
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.mAdapterListener = adapterListener;
    }

    public void setPresenter(PresenterSelector presenterSelector) {
        this.mPresenterSelector = presenterSelector;
        notifyDataSetChanged();
    }

    public void setPresenterMapper(ArrayList<Presenter> arrayList) {
        this.mPresenters = arrayList;
    }

    public void setWrapper(Wrapper wrapper) {
        this.mWrapper = wrapper;
    }

    public ItemBridgeAdapter(ObjectAdapter objectAdapter) {
        this(objectAdapter, null);
    }

    public ItemBridgeAdapter() {
        this.mPresenters = new ArrayList<>();
        this.mDataObserver = new ObjectAdapter.DataObserver() { // from class: androidx.leanback.widget.ItemBridgeAdapter.1
            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onChanged() {
                ItemBridgeAdapter.this.notifyDataSetChanged();
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemMoved(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemMoved(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeChanged(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeInserted(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeInserted(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeRemoved(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeRemoved(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeChanged(int i, int i2, Object obj) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(i, i2, obj);
            }
        };
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        Object obj = this.mAdapter.get(i);
        viewHolder2.mItem = obj;
        viewHolder2.mPresenter.onBindViewHolder(viewHolder2.mHolder, obj, list);
        onBind(viewHolder2);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onBind(viewHolder2, list);
        }
    }
}
