package io.realm;

import io.realm.internal.Table;
import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes2.dex */
final class DynamicRealmModelRealmAnyOperator extends RealmModelOperator {
    public DynamicRealmModelRealmAnyOperator(BaseRealm baseRealm, NativeRealmAny nativeRealmAny) {
        super(getRealmModel(baseRealm, nativeRealmAny));
    }

    private static <T extends RealmModel> T getRealmModel(BaseRealm baseRealm, NativeRealmAny nativeRealmAny) {
        return (T) baseRealm.get(DynamicRealmObject.class, Table.getClassNameForTable(nativeRealmAny.getRealmModelTableName(baseRealm.sharedRealm)), nativeRealmAny.getRealmModelRowKey());
    }

    @Override // io.realm.RealmModelOperator, io.realm.RealmAnyOperator
    public final Class<?> getTypedClass() {
        return DynamicRealmObject.class;
    }
}
