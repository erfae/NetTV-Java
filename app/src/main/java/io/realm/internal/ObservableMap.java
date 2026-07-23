package io.realm.internal;

import io.realm.MapChangeListener;
import io.realm.MapChangeSet;
import io.realm.RealmChangeListener;
import io.realm.RealmMap;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes2.dex */
@Keep
public interface ObservableMap {

    public static class Callback<K, V> implements ObserverPairList.Callback<MapObserverPair<K, V>> {
        private final MapChangeSet<K> changeSet;

        public Callback(MapChangeSet<K> mapChangeSet) {
            this.changeSet = mapChangeSet;
        }

        @Override // io.realm.internal.ObserverPairList.Callback
        public void onCalled(MapObserverPair<K, V> mapObserverPair, Object obj) {
            mapObserverPair.onChange(obj, this.changeSet);
        }
    }

    public static class MapObserverPair<K, V> extends ObserverPairList.ObserverPair<RealmMap<K, V>, Object> {
        public MapObserverPair(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
            super(realmMap, mapChangeListener);
        }

        public void onChange(Object obj, MapChangeSet<K> mapChangeSet) {
            ((MapChangeListener) this.listener).onChange((RealmMap) obj, mapChangeSet);
        }
    }

    public static class RealmChangeListenerWrapper<K, V> implements MapChangeListener<K, V> {
        private final RealmChangeListener<RealmMap<K, V>> listener;

        public RealmChangeListenerWrapper(RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
            this.listener = realmChangeListener;
        }

        public boolean equals(Object obj) {
            return (obj instanceof RealmChangeListenerWrapper) && this.listener == ((RealmChangeListenerWrapper) obj).listener;
        }

        public int hashCode() {
            return this.listener.hashCode();
        }

        @Override // io.realm.MapChangeListener
        public void onChange(RealmMap<K, V> realmMap, @Nullable MapChangeSet<K> mapChangeSet) {
            this.listener.onChange(realmMap);
        }
    }

    void notifyChangeListeners(long j);
}
