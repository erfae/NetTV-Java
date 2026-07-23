package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.Collection;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class BooleanOperator extends SetValueOperator<Boolean> {
    public BooleanOperator(BaseRealm baseRealm, OsSet osSet) {
        super(baseRealm, osSet, Boolean.class);
    }

    @Override // io.realm.SetValueOperator
    public final boolean add(@Nullable Boolean bool) {
        return this.osSet.add(bool);
    }

    @Override // io.realm.SetValueOperator
    public final boolean addAllInternal(Collection<? extends Boolean> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBooleanCollection(collection), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBooleanCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsInternal(@Nullable Object obj) {
        return this.osSet.contains((Boolean) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBooleanCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeInternal(@Nullable Object obj) {
        return this.osSet.remove((Boolean) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean retainAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newBooleanCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }
}
