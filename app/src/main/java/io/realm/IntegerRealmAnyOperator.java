package io.realm;

import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes2.dex */
final class IntegerRealmAnyOperator extends PrimitiveRealmAnyOperator {
    public IntegerRealmAnyOperator(Byte b) {
        super(b, RealmAny.Type.INTEGER);
    }

    @Override // io.realm.RealmAnyOperator
    public final NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((Number) getValue(Number.class));
    }

    @Override // io.realm.PrimitiveRealmAnyOperator
    public boolean equals(Object obj) {
        return obj != null && IntegerRealmAnyOperator.class.equals(obj.getClass()) && ((Number) getValue(Number.class)).longValue() == ((Number) ((RealmAnyOperator) obj).getValue(Number.class)).longValue();
    }

    public IntegerRealmAnyOperator(Short sh) {
        super(sh, RealmAny.Type.INTEGER);
    }

    public IntegerRealmAnyOperator(Integer num) {
        super(num, RealmAny.Type.INTEGER);
    }

    public IntegerRealmAnyOperator(Long l) {
        super(l, RealmAny.Type.INTEGER);
    }

    public IntegerRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(Long.valueOf(nativeRealmAny.asLong()), RealmAny.Type.INTEGER, nativeRealmAny);
    }
}
