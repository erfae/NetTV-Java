package io.realm;

import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes2.dex */
final class DoubleRealmAnyOperator extends PrimitiveRealmAnyOperator {
    public DoubleRealmAnyOperator(Double d) {
        super(d, RealmAny.Type.DOUBLE);
    }

    @Override // io.realm.RealmAnyOperator
    public final NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((Double) getValue(Double.class));
    }

    public DoubleRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(Double.valueOf(nativeRealmAny.asDouble()), RealmAny.Type.DOUBLE, nativeRealmAny);
    }
}
