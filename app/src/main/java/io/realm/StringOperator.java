package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.Collection;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class StringOperator extends SetValueOperator<String> {
    public StringOperator(BaseRealm baseRealm, OsSet osSet) {
        super(baseRealm, osSet, String.class);
    }

    @Override // io.realm.SetValueOperator
    public final boolean add(@Nullable String str) {
        return this.osSet.add(str);
    }

    @Override // io.realm.SetValueOperator
    public final boolean addAllInternal(Collection<? extends String> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newStringCollection(collection), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newStringCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsInternal(@Nullable Object obj) {
        return this.osSet.contains((String) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newStringCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeInternal(@Nullable Object obj) {
        return this.osSet.remove((String) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean retainAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newStringCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }
}
