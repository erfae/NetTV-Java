package io.realm;

import java.util.Arrays;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: RealmMapEntrySet.java */
/* JADX INFO: loaded from: classes2.dex */
class BinaryEquals<K> extends EqualsHelper<K, byte[]> {
    @Override // io.realm.EqualsHelper
    public final boolean compareInternal(@Nullable byte[] bArr, @Nullable byte[] bArr2) {
        return Arrays.equals(bArr, bArr2);
    }
}
