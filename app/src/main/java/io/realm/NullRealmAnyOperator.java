package io.realm;

import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes2.dex */
final class NullRealmAnyOperator extends RealmAnyOperator {
    public NullRealmAnyOperator() {
        super(RealmAny.Type.NULL);
    }

    @Override // io.realm.RealmAnyOperator
    public final NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny();
    }

    public boolean equals(Object obj) {
        return obj != null && NullRealmAnyOperator.class.equals(obj.getClass());
    }

    @Override // io.realm.RealmAnyOperator
    public <T> T getValue(Class<T> cls) {
        return null;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        return "null";
    }

    public NullRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(RealmAny.Type.NULL, nativeRealmAny);
    }
}
