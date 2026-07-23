package io.realm;

import android.widget.BaseAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.RealmModel;

/* JADX INFO: loaded from: classes2.dex */
public abstract class RealmBaseAdapter<T extends RealmModel> extends BaseAdapter {

    @Nullable
    public OrderedRealmCollection<T> adapterData;
    private final RealmChangeListener<OrderedRealmCollection<T>> listener;

    public RealmBaseAdapter(@Nullable OrderedRealmCollection<T> orderedRealmCollection) {
        if (orderedRealmCollection != null && !orderedRealmCollection.isManaged()) {
            throw new IllegalStateException("Only use this adapter with managed list, for un-managed lists you can just use the BaseAdapter");
        }
        this.adapterData = orderedRealmCollection;
        this.listener = (RealmChangeListener<OrderedRealmCollection<T>>) new RealmChangeListener<OrderedRealmCollection<T>>() { // from class: io.realm.RealmBaseAdapter.1
            @Override // io.realm.RealmChangeListener
            public void onChange(OrderedRealmCollection<T> orderedRealmCollection2) {
                RealmBaseAdapter.this.notifyDataSetChanged();
            }
        };
        if (isDataValid()) {
            addListener(orderedRealmCollection);
        }
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

    @Override // android.widget.Adapter
    public int getCount() {
        if (isDataValid()) {
            return this.adapterData.size();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public void updateData(@Nullable OrderedRealmCollection<T> orderedRealmCollection) {
        if (this.listener != null) {
            if (isDataValid()) {
                removeListener(this.adapterData);
            }
            if (orderedRealmCollection != null && orderedRealmCollection.isValid()) {
                addListener(orderedRealmCollection);
            }
        }
        this.adapterData = orderedRealmCollection;
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    @Nullable
    public T getItem(int i) {
        if (isDataValid()) {
            return this.adapterData.get(i);
        }
        return null;
    }
}
