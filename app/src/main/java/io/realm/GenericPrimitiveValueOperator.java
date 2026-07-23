package io.realm;

import io.realm.internal.OsMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes2.dex */
class GenericPrimitiveValueOperator<K, V> extends MapValueOperator<K, V> {
    private final EqualsHelper<K, V> equalsHelper;

    public GenericPrimitiveValueOperator(Class<V> cls, BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, V> typeSelectorForMap, RealmMapEntrySet.IteratorType iteratorType) {
        this(cls, baseRealm, osMap, typeSelectorForMap, iteratorType, new GenericEquals());
    }

    @Override // io.realm.MapValueOperator
    public final boolean containsValueInternal(@Nullable Object obj) {
        return this.osMap.containsPrimitiveValue(obj);
    }

    @Override // io.realm.MapValueOperator
    public final Set<Map.Entry<K, V>> entrySet() {
        return new RealmMapEntrySet(this.baseRealm, this.osMap, this.iteratorType, this.equalsHelper);
    }

    @Override // io.realm.MapValueOperator
    @Nullable
    public final V get(Object obj) {
        Object obj2 = this.osMap.get(obj);
        if (obj2 == null) {
            return null;
        }
        return processValue(obj2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public V processValue(Object obj) {
        return obj;
    }

    @Override // io.realm.MapValueOperator
    @Nullable
    public final V put(K k, @Nullable V v) {
        V v2 = get(k);
        this.osMap.put(k, v);
        return v2;
    }

    public GenericPrimitiveValueOperator(Class<V> cls, BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, V> typeSelectorForMap, RealmMapEntrySet.IteratorType iteratorType, EqualsHelper<K, V> equalsHelper) {
        super(cls, baseRealm, osMap, typeSelectorForMap, iteratorType);
        this.equalsHelper = equalsHelper;
    }
}
