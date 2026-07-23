package io.realm;

import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: RealmMapEntrySet.java */
/* JADX INFO: loaded from: classes2.dex */
class GenericEquals<K, V> extends EqualsHelper<K, V> {
    @Override // io.realm.EqualsHelper
    public final boolean compareInternal(@Nullable V v, @Nullable V v2) {
        if (v == null) {
            return v2 == null;
        }
        return v.equals(v2);
    }
}
