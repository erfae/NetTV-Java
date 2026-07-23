package io.realm;

import io.realm.internal.OsMap;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes2.dex */
class ShortValueOperator<K> extends GenericPrimitiveValueOperator<K, Short> {
    public ShortValueOperator(BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, Short> typeSelectorForMap) {
        super(Short.class, baseRealm, osMap, typeSelectorForMap, RealmMapEntrySet.IteratorType.SHORT);
    }

    @Override // io.realm.GenericPrimitiveValueOperator
    public final Short processValue(Object obj) {
        return Short.valueOf(((Long) obj).shortValue());
    }
}
