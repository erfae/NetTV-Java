package io.realm;

import io.realm.internal.Freezable;
import io.realm.internal.ManageableObject;
import io.realm.internal.ObservableMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public abstract class RealmMap<K, V> implements Map<K, V>, ManageableObject, Freezable<RealmMap<K, V>> {
    public final MapStrategy<K, V> mapStrategy;

    public static class ManagedMapStrategy<K, V> extends MapStrategy<K, V> {
        private final ManagedMapManager<K, V> managedMapManager;

        public ManagedMapStrategy(ManagedMapManager<K, V> managedMapManager) {
            this.managedMapManager = managedMapManager;
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final void addChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
            this.managedMapManager.addChangeListener(realmMap, mapChangeListener);
        }

        @Override // java.util.Map
        public void clear() {
            this.managedMapManager.clear();
        }

        @Override // java.util.Map
        public boolean containsKey(@Nullable Object obj) {
            return this.managedMapManager.containsKey(obj);
        }

        @Override // java.util.Map
        public boolean containsValue(@Nullable Object obj) {
            return this.managedMapManager.containsValue(obj);
        }

        @Override // java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            return this.managedMapManager.entrySet();
        }

        @Override // java.util.Map
        public V get(Object obj) {
            return this.managedMapManager.get(obj);
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final Class<V> getValueClass() {
            return this.managedMapManager.typeSelectorForMap.getValueClass();
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final String getValueClassName() {
            return this.managedMapManager.typeSelectorForMap.getValueClassName();
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return this.managedMapManager.isEmpty();
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isFrozen() {
            return this.managedMapManager.isFrozen();
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isManaged() {
            return this.managedMapManager.isManaged();
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isValid() {
            return this.managedMapManager.isValid();
        }

        @Override // java.util.Map
        public Set<K> keySet() {
            return this.managedMapManager.keySet();
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            this.managedMapManager.putAll(map);
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final V putInternal(K k, V v) {
            return this.managedMapManager.put(k, v);
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            return this.managedMapManager.remove(obj);
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final void removeAllChangeListeners() {
            ManagedMapManager<K, V> managedMapManager = this.managedMapManager;
            CollectionUtils.checkForAddRemoveListener(managedMapManager.baseRealm, null, false);
            managedMapManager.mapObserverPairs.clear();
            managedMapManager.mapValueOperator.osMap.stopListening();
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final void removeChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
            this.managedMapManager.removeListener(realmMap, mapChangeListener);
        }

        @Override // java.util.Map
        public int size() {
            return this.managedMapManager.size();
        }

        @Override // java.util.Map
        public Collection<V> values() {
            return this.managedMapManager.values();
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final void addChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
            ManagedMapManager<K, V> managedMapManager = this.managedMapManager;
            Objects.requireNonNull(managedMapManager);
            managedMapManager.addChangeListener(realmMap, new ObservableMap.RealmChangeListenerWrapper(realmChangeListener));
        }

        @Override // io.realm.internal.Freezable
        public RealmMap<K, V> freeze() {
            return this.managedMapManager.freeze();
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final void removeChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
            ManagedMapManager<K, V> managedMapManager = this.managedMapManager;
            Objects.requireNonNull(managedMapManager);
            managedMapManager.removeListener(realmMap, new ObservableMap.RealmChangeListenerWrapper(realmChangeListener));
        }
    }

    public static abstract class MapStrategy<K, V> implements Map<K, V>, ManageableObject, Freezable<RealmMap<K, V>> {
        public abstract void addChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener);

        public abstract void addChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener);

        public abstract Class<V> getValueClass();

        public abstract String getValueClassName();

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Map
        public V put(K k, V v) {
            Objects.requireNonNull(k, "Null keys are not allowed.");
            if (k.getClass() == String.class) {
                String str = (String) k;
                if (str.contains(".") || str.contains("$")) {
                    throw new IllegalArgumentException("Keys containing dots ('.') or dollar signs ('$') are not allowed.");
                }
            }
            return putInternal(k, v);
        }

        public abstract V putInternal(K k, @Nullable V v);

        public abstract void removeAllChangeListeners();

        public abstract void removeChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener);

        public abstract void removeChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener);
    }

    public static class UnmanagedMapStrategy<K, V> extends MapStrategy<K, V> {
        private final Map<K, V> unmanagedMap;

        private UnmanagedMapStrategy() {
            this.unmanagedMap = new HashMap();
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final void addChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
            throw new UnsupportedOperationException("Unmanaged RealmMaps do not support change listeners.");
        }

        @Override // java.util.Map
        public void clear() {
            this.unmanagedMap.clear();
        }

        @Override // java.util.Map
        public boolean containsKey(@Nullable Object obj) {
            return this.unmanagedMap.containsKey(obj);
        }

        @Override // java.util.Map
        public boolean containsValue(@Nullable Object obj) {
            return this.unmanagedMap.containsValue(obj);
        }

        @Override // java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            return this.unmanagedMap.entrySet();
        }

        @Override // java.util.Map
        public V get(Object obj) {
            return this.unmanagedMap.get(obj);
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final Class<V> getValueClass() {
            throw new UnsupportedOperationException("Unmanaged maps do not support retrieving the value class.");
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final String getValueClassName() {
            throw new UnsupportedOperationException("Unmanaged maps do not support retrieving the value class name.");
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return this.unmanagedMap.isEmpty();
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isFrozen() {
            return false;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isManaged() {
            return false;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isValid() {
            return true;
        }

        @Override // java.util.Map
        public Set<K> keySet() {
            return this.unmanagedMap.keySet();
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            this.unmanagedMap.putAll(map);
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final V putInternal(K k, @Nullable V v) {
            return this.unmanagedMap.put(k, v);
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            return this.unmanagedMap.remove(obj);
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final void removeAllChangeListeners() {
            throw new UnsupportedOperationException("Cannot remove change listener because unmanaged RealmMaps do not support change listeners.");
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final void removeChangeListener(RealmMap<K, V> realmMap, MapChangeListener<K, V> mapChangeListener) {
            throw new UnsupportedOperationException("Cannot remove change listener because unmanaged RealmMaps do not support change listeners.");
        }

        @Override // java.util.Map
        public int size() {
            return this.unmanagedMap.size();
        }

        @Override // java.util.Map
        public Collection<V> values() {
            return this.unmanagedMap.values();
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final void addChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
            throw new UnsupportedOperationException("Unmanaged RealmMaps do not support change listeners.");
        }

        @Override // io.realm.internal.Freezable
        public RealmMap<K, V> freeze() {
            throw new UnsupportedOperationException("Unmanaged RealmMaps cannot be frozen.");
        }

        @Override // io.realm.RealmMap.MapStrategy
        public final void removeChangeListener(RealmMap<K, V> realmMap, RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
            throw new UnsupportedOperationException("Cannot remove change listener because unmanaged RealmMaps do not support change listeners.");
        }
    }

    public RealmMap() {
        this.mapStrategy = new UnmanagedMapStrategy();
    }

    public void addChangeListener(MapChangeListener<K, V> mapChangeListener) {
        this.mapStrategy.addChangeListener(this, mapChangeListener);
    }

    @Override // java.util.Map
    public void clear() {
        this.mapStrategy.clear();
    }

    @Override // java.util.Map
    public boolean containsKey(@Nullable Object obj) {
        return this.mapStrategy.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(@Nullable Object obj) {
        return this.mapStrategy.containsValue(obj);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return this.mapStrategy.entrySet();
    }

    @Override // java.util.Map
    public V get(Object obj) {
        return this.mapStrategy.get(obj);
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.mapStrategy.isEmpty();
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isFrozen() {
        return this.mapStrategy.isFrozen();
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isManaged() {
        return this.mapStrategy.isManaged();
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isValid() {
        return this.mapStrategy.isValid();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return this.mapStrategy.keySet();
    }

    @Override // java.util.Map
    public V put(K k, @Nullable V v) {
        return this.mapStrategy.put(k, v);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        this.mapStrategy.putAll(map);
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        return this.mapStrategy.remove(obj);
    }

    public void removeAllChangeListeners() {
        this.mapStrategy.removeAllChangeListeners();
    }

    public void removeChangeListener(MapChangeListener<K, V> mapChangeListener) {
        this.mapStrategy.removeChangeListener(this, mapChangeListener);
    }

    @Override // java.util.Map
    public int size() {
        return this.mapStrategy.size();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return this.mapStrategy.values();
    }

    public void addChangeListener(RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
        this.mapStrategy.addChangeListener(this, realmChangeListener);
    }

    @Override // io.realm.internal.Freezable
    public RealmMap<K, V> freeze() {
        return this.mapStrategy.freeze();
    }

    public void removeChangeListener(RealmChangeListener<RealmMap<K, V>> realmChangeListener) {
        this.mapStrategy.removeChangeListener(this, realmChangeListener);
    }

    public RealmMap(MapStrategy<K, V> mapStrategy) {
        this.mapStrategy = mapStrategy;
    }
}
