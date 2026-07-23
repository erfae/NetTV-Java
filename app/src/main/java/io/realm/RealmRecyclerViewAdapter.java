package io.realm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import io.realm.RealmModel;

/* JADX INFO: loaded from: classes2.dex */
public abstract class RealmRecyclerViewAdapter<T extends RealmModel, S extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<S> {

    @Nullable
    private OrderedRealmCollection<T> adapterData;
    private final boolean hasAutoUpdates;
    private final OrderedRealmCollectionChangeListener listener;
    private final boolean updateOnModification;

    public RealmRecyclerViewAdapter(@Nullable OrderedRealmCollection<T> orderedRealmCollection, boolean z) {
        this(orderedRealmCollection, z, true);
    }

    private void addListener(@NonNull OrderedRealmCollection<T> orderedRealmCollection) {
        if (orderedRealmCollection instanceof RealmResults) {
            ((RealmResults) orderedRealmCollection).addChangeListener(this.listener);
        } else if (orderedRealmCollection instanceof RealmList) {
            ((RealmList) orderedRealmCollection).addChangeListener(this.listener);
        } else {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("RealmCollection not supported: ");
            sbM.append(orderedRealmCollection.getClass());
            throw new IllegalArgumentException(sbM.toString());
        }
    }

    private OrderedRealmCollectionChangeListener createListener() {
        return new OrderedRealmCollectionChangeListener() { // from class: io.realm.RealmRecyclerViewAdapter.1
            @Override // io.realm.OrderedRealmCollectionChangeListener
            public void onChange(Object obj, OrderedCollectionChangeSet orderedCollectionChangeSet) {
                if (orderedCollectionChangeSet.getState() == OrderedCollectionChangeSet.State.INITIAL) {
                    RealmRecyclerViewAdapter.this.notifyDataSetChanged();
                    return;
                }
                OrderedCollectionChangeSet.Range[] deletionRanges = orderedCollectionChangeSet.getDeletionRanges();
                for (int length = deletionRanges.length - 1; length >= 0; length--) {
                    OrderedCollectionChangeSet.Range range = deletionRanges[length];
                    RealmRecyclerViewAdapter realmRecyclerViewAdapter = RealmRecyclerViewAdapter.this;
                    realmRecyclerViewAdapter.notifyItemRangeRemoved(realmRecyclerViewAdapter.dataOffset() + range.startIndex, range.length);
                }
                for (OrderedCollectionChangeSet.Range range2 : orderedCollectionChangeSet.getInsertionRanges()) {
                    RealmRecyclerViewAdapter realmRecyclerViewAdapter2 = RealmRecyclerViewAdapter.this;
                    realmRecyclerViewAdapter2.notifyItemRangeInserted(realmRecyclerViewAdapter2.dataOffset() + range2.startIndex, range2.length);
                }
                if (RealmRecyclerViewAdapter.this.updateOnModification) {
                    for (OrderedCollectionChangeSet.Range range3 : orderedCollectionChangeSet.getChangeRanges()) {
                        RealmRecyclerViewAdapter realmRecyclerViewAdapter3 = RealmRecyclerViewAdapter.this;
                        realmRecyclerViewAdapter3.notifyItemRangeChanged(realmRecyclerViewAdapter3.dataOffset() + range3.startIndex, range3.length);
                    }
                }
            }
        };
    }

    private boolean isDataValid() {
        OrderedRealmCollection<T> orderedRealmCollection = this.adapterData;
        return orderedRealmCollection != null && orderedRealmCollection.isValid();
    }

    private void removeListener(@NonNull OrderedRealmCollection<T> orderedRealmCollection) {
        if (orderedRealmCollection instanceof RealmResults) {
            ((RealmResults) orderedRealmCollection).removeChangeListener(this.listener);
        } else if (orderedRealmCollection instanceof RealmList) {
            ((RealmList) orderedRealmCollection).removeChangeListener(this.listener);
        } else {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("RealmCollection not supported: ");
            sbM.append(orderedRealmCollection.getClass());
            throw new IllegalArgumentException(sbM.toString());
        }
    }

    public int dataOffset() {
        return 0;
    }

    @Nullable
    public OrderedRealmCollection<T> getData() {
        return this.adapterData;
    }

    @Nullable
    public T getItem(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("Only indexes >= 0 are allowed. Input was: ", i));
        }
        OrderedRealmCollection<T> orderedRealmCollection = this.adapterData;
        if ((orderedRealmCollection == null || i < orderedRealmCollection.size()) && isDataValid()) {
            return this.adapterData.get(i);
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (isDataValid()) {
            return this.adapterData.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (this.hasAutoUpdates && isDataValid()) {
            addListener(this.adapterData);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (this.hasAutoUpdates && isDataValid()) {
            removeListener(this.adapterData);
        }
    }

    public void updateData(@Nullable OrderedRealmCollection<T> orderedRealmCollection) {
        if (this.hasAutoUpdates) {
            if (isDataValid()) {
                removeListener(this.adapterData);
            }
            if (orderedRealmCollection != null) {
                addListener(orderedRealmCollection);
            }
        }
        this.adapterData = orderedRealmCollection;
        notifyDataSetChanged();
    }

    public RealmRecyclerViewAdapter(@Nullable OrderedRealmCollection<T> orderedRealmCollection, boolean z, boolean z2) {
        if (orderedRealmCollection != null && !orderedRealmCollection.isManaged()) {
            throw new IllegalStateException("Only use this adapter with managed RealmCollection, for un-managed lists you can just use the BaseRecyclerViewAdapter");
        }
        this.adapterData = orderedRealmCollection;
        this.hasAutoUpdates = z;
        this.listener = z ? createListener() : null;
        this.updateOnModification = z2;
    }
}
