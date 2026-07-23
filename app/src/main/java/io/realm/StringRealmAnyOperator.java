package io.realm;

import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes2.dex */
final class StringRealmAnyOperator extends PrimitiveRealmAnyOperator {
    public StringRealmAnyOperator(String str) {
        super(str, RealmAny.Type.STRING);
    }

    @Override // io.realm.RealmAnyOperator
    public final NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((String) getValue(String.class));
    }

    public StringRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(nativeRealmAny.asString(), RealmAny.Type.STRING, nativeRealmAny);
    }
}
