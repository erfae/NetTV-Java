package io.realm;

import io.realm.internal.core.NativeRealmAny;
import java.util.Arrays;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes2.dex */
final class BinaryRealmAnyOperator extends PrimitiveRealmAnyOperator {
    public BinaryRealmAnyOperator(byte[] bArr) {
        super(bArr, RealmAny.Type.BINARY);
    }

    @Override // io.realm.RealmAnyOperator
    public final NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((byte[]) getValue(byte[].class));
    }

    @Override // io.realm.PrimitiveRealmAnyOperator
    public boolean equals(Object obj) {
        if (obj == null || !BinaryRealmAnyOperator.class.equals(obj.getClass())) {
            return false;
        }
        return Arrays.equals((byte[]) getValue(byte[].class), (byte[]) ((RealmAnyOperator) obj).getValue(byte[].class));
    }

    public BinaryRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(nativeRealmAny.asBinary(), RealmAny.Type.BINARY, nativeRealmAny);
    }
}
