package io.realm;

import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes2.dex */
final class BooleanRealmAnyOperator extends PrimitiveRealmAnyOperator {
    public BooleanRealmAnyOperator(Boolean bool) {
        super(bool, RealmAny.Type.BOOLEAN);
    }

    @Override // io.realm.RealmAnyOperator
    public final NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((Boolean) getValue(Boolean.class));
    }

    public BooleanRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(Boolean.valueOf(nativeRealmAny.asBoolean()), RealmAny.Type.BOOLEAN, nativeRealmAny);
    }
}
