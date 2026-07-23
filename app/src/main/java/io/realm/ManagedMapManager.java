package io.realm;

import io.realm.internal.Freezable;
import io.realm.internal.ManageableObject;
import io.realm.internal.ObservableMap;
import io.realm.internal.ObserverPairList;
import io.realm.internal.OsMap;
import io.realm.internal.util.Pair;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
abstract class ManagedMapManager<K, V> implements Map<K, V>, ManageableObject, Freezable<RealmMap<K, V>>, ObservableMap {
    public final BaseRealm baseRealm;
    public final ObserverPairList<ObservableMap.MapObserverPair<K, V>> mapObserverPairs = new ObserverPairList<>();
    public final MapValueOperator<K, V> mapValueOperator;
    public final TypeSelectorForMap<K, V> typeSelectorForMap;

    public ManagedMapManager(BaseRealm baseRealm, MapValueOperator<K, V> mapValueOperator, TypeSelectorForMap<K, V> typeSelectorForMap) {
        this.baseRealm = baseRealm;
        this.mapValueOperator = mapValueOperator;
        this.typeSelectorForMap = typeSelectorForMap;
    }

    public final void addChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
        CollectionUtils.checkForAddRemoveListener(this.baseRealm, mapChangeListener, true);
        if (this.mapObserverPairs.isEmpty()) {
            this.mapValueOperator.osMap.startListening(this);
        }
        this.mapObserverPairs.add(new ObservableMap.MapObserverPair(realmMap, mapChangeListener));
    }

    public abstract MapChangeSet<K> changeSetFactory(long j);

    @Override // java.util.Map
    public void clear() {
        this.mapValueOperator.osMap.clear();
    }

    @Override // java.util.Map
    public boolean containsKey(@Nullable Object obj) {
        return containsKeyInternal(obj);
    }

    public abstract boolean containsKeyInternal(@Nullable Object obj);

    @Override // java.util.Map
    public boolean containsValue(@Nullable Object obj) {
        return this.mapValueOperator.containsValue(obj);
    }

    @Override // java.util.Map
    public abstract Set<Map.Entry<K, V>> entrySet();

    public abstract RealmMap<K, V> freezeInternal(Pair<BaseRealm, OsMap> pair);

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.mapValueOperator.osMap.size() == 0;
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isFrozen() {
        return this.mapValueOperator.baseRealm.isFrozen();
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isManaged() {
        return true;
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isValid() {
        MapValueOperator<K, V> mapValueOperator = this.mapValueOperator;
        if (mapValueOperator.baseRealm.isClosed()) {
            return false;
        }
        return mapValueOperator.osMap.isValid();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return this.mapValueOperator.typeSelectorForMap.keySet();
    }

    @Override // io.realm.internal.ObservableMap
    public void notifyChangeListeners(long j) {
        MapChangeSetImpl mapChangeSetImpl = new MapChangeSetImpl(changeSetFactory(j));
        if (mapChangeSetImpl.isEmpty()) {
            return;
        }
        this.mapObserverPairs.foreach(new ObservableMap.Callback(mapChangeSetImpl));
    }

    @Override // java.util.Map
    public abstract V put(@Nullable K k, @Nullable V v);

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        validateMap(map);
        MapValueOperator<K, V> mapValueOperator = this.mapValueOperator;
        Objects.requireNonNull(mapValueOperator);
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            mapValueOperator.put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        Objects.requireNonNull(obj, "Null keys are not allowed.");
        V v = this.mapValueOperator.get(obj);
        this.mapValueOperator.osMap.remove(obj);
        return v;
    }

    public final void removeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
        this.mapObserverPairs.remove(realmMap, mapChangeListener);
        if (this.mapObserverPairs.isEmpty()) {
            this.mapValueOperator.osMap.stopListening();
        }
    }

    @Override // java.util.Map
    public int size() {
        return (int) this.mapValueOperator.osMap.size();
    }

    public abstract void validateMap(Map<? extends K, ? extends V> map);

    @Override // java.util.Map
    public Collection<V> values() {
        return this.mapValueOperator.typeSelectorForMap.getValues();
    }

    @Override // io.realm.internal.Freezable
    public RealmMap<K, V> freeze() {
        MapValueOperator<K, V> mapValueOperator = this.mapValueOperator;
        BaseRealm baseRealmFreeze = mapValueOperator.baseRealm.freeze();
        return freezeInternal(new Pair<>(baseRealmFreeze, mapValueOperator.osMap.freeze(baseRealmFreeze.sharedRealm)));
    }
}
