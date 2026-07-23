package io.realm.internal;

import io.realm.RealmSet;
import io.realm.SetChangeListener;
import io.realm.SetChangeSet;

/* JADX INFO: loaded from: classes2.dex */
@Keep
public interface ObservableSet {

    public static class Callback<T> implements ObserverPairList.Callback<SetObserverPair<T>> {
        private final SetChangeSet changeSet;

        public Callback(SetChangeSet setChangeSet) {
            this.changeSet = setChangeSet;
        }

        @Override // io.realm.internal.ObserverPairList.Callback
        public void onCalled(SetObserverPair<T> setObserverPair, Object obj) {
            setObserverPair.onChange(obj, this.changeSet);
        }
    }

    public static class SetObserverPair<T> extends ObserverPairList.ObserverPair<RealmSet<T>, Object> {
        public SetObserverPair(RealmSet<T> realmSet, Object obj) {
            super(realmSet, obj);
        }

        public void onChange(Object obj, SetChangeSet setChangeSet) {
            ((SetChangeListener) this.listener).onChange((RealmSet) obj, setChangeSet);
        }
    }

    void notifyChangeListeners(long j);
}
