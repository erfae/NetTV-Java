package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.Collection;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class BinaryOperator extends SetValueOperator<byte[]> {
    public BinaryOperator(BaseRealm baseRealm, OsSet osSet) {
        super(baseRealm, osSet, byte[].class);
    }

    @Override // io.realm.SetValueOperator
    public final boolean add(@Nullable byte[] bArr) {
        return this.osSet.add(bArr);
    }

    @Override // io.realm.SetValueOperator
    public final boolean addAllInternal(Collection<? extends byte[]> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBinaryCollection(collection), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBinaryCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsInternal(@Nullable Object obj) {
        return this.osSet.contains(obj == null ? null : (byte[]) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBinaryCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeInternal(@Nullable Object obj) {
        return this.osSet.remove((byte[]) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean retainAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBinaryCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }
}
