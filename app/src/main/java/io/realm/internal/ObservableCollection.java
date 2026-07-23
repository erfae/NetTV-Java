package io.realm.internal;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmChangeListener;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
@Keep
public interface ObservableCollection {

    public static class Callback implements ObserverPairList.Callback<CollectionObserverPair> {
        private final OsCollectionChangeSet changeSet;

        public Callback(OsCollectionChangeSet osCollectionChangeSet) {
            this.changeSet = osCollectionChangeSet;
        }

        @Override // io.realm.internal.ObserverPairList.Callback
        public void onCalled(CollectionObserverPair collectionObserverPair, Object obj) {
            collectionObserverPair.onChange(obj, this.changeSet);
        }
    }

    public static class CollectionObserverPair<T> extends ObserverPairList.ObserverPair<T, Object> {
        public CollectionObserverPair(T t, Object obj) {
            super(t, obj);
        }

        public void onChange(T t, OsCollectionChangeSet osCollectionChangeSet) {
            S s = this.listener;
            if (s instanceof OrderedRealmCollectionChangeListener) {
                ((OrderedRealmCollectionChangeListener) s).onChange(t, new StatefulCollectionChangeSet(osCollectionChangeSet));
            } else if (s instanceof RealmChangeListener) {
                ((RealmChangeListener) s).onChange(t);
            } else {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Unsupported listener type: ");
                sbM.append(this.listener);
                throw new RuntimeException(sbM.toString());
            }
        }
    }

    public static class RealmChangeListenerWrapper<T> implements OrderedRealmCollectionChangeListener<T> {
        private final RealmChangeListener<T> listener;

        public RealmChangeListenerWrapper(RealmChangeListener<T> realmChangeListener) {
            this.listener = realmChangeListener;
        }

        public boolean equals(Object obj) {
            return (obj instanceof RealmChangeListenerWrapper) && this.listener == ((RealmChangeListenerWrapper) obj).listener;
        }

        public int hashCode() {
            return this.listener.hashCode();
        }

        @Override // io.realm.OrderedRealmCollectionChangeListener
        public void onChange(T t, @Nullable OrderedCollectionChangeSet orderedCollectionChangeSet) {
            this.listener.onChange(t);
        }
    }

    void notifyChangeListeners(long j);
}
