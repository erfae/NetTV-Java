package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.Collection;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class LongOperator extends SetValueOperator<Long> {
    public LongOperator(BaseRealm baseRealm, OsSet osSet) {
        super(baseRealm, osSet, Long.class);
    }

    @Override // io.realm.SetValueOperator
    public final boolean add(@Nullable Long l) {
        return this.osSet.add(l);
    }

    @Override // io.realm.SetValueOperator
    public final boolean addAllInternal(Collection<? extends Long> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newIntegerCollection(collection), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newIntegerCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsInternal(@Nullable Object obj) {
        return this.osSet.contains((Long) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newIntegerCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeInternal(@Nullable Object obj) {
        return this.osSet.remove((Long) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean retainAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newIntegerCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }
}
