package io.realm;

import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes2.dex */
final class FloatRealmAnyOperator extends PrimitiveRealmAnyOperator {
    public FloatRealmAnyOperator(Float f) {
        super(f, RealmAny.Type.FLOAT);
    }

    @Override // io.realm.RealmAnyOperator
    public final NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((Float) getValue(Float.class));
    }

    public FloatRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(Float.valueOf(nativeRealmAny.asFloat()), RealmAny.Type.FLOAT, nativeRealmAny);
    }
}
