package io.realm;

import io.realm.internal.OsMap;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes2.dex */
class ByteValueOperator<K> extends GenericPrimitiveValueOperator<K, Byte> {
    public ByteValueOperator(BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, Byte> typeSelectorForMap) {
        super(Byte.class, baseRealm, osMap, typeSelectorForMap, RealmMapEntrySet.IteratorType.BYTE);
    }

    @Override // io.realm.GenericPrimitiveValueOperator
    public final Byte processValue(Object obj) {
        return Byte.valueOf(((Long) obj).byteValue());
    }
}
