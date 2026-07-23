package io.realm;

import io.realm.internal.OsMap;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes2.dex */
class IntegerValueOperator<K> extends GenericPrimitiveValueOperator<K, Integer> {
    public IntegerValueOperator(BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, Integer> typeSelectorForMap) {
        super(Integer.class, baseRealm, osMap, typeSelectorForMap, RealmMapEntrySet.IteratorType.INTEGER);
    }

    @Override // io.realm.GenericPrimitiveValueOperator
    public final Integer processValue(Object obj) {
        return Integer.valueOf(((Long) obj).intValue());
    }
}
