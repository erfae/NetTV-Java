package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class DateOperator extends SetValueOperator<Date> {
    public DateOperator(BaseRealm baseRealm, OsSet osSet, Class<Date> cls) {
        super(baseRealm, osSet, cls);
    }

    @Override // io.realm.SetValueOperator
    public final boolean add(@Nullable Date date) {
        return this.osSet.add(date);
    }

    @Override // io.realm.SetValueOperator
    public final boolean addAllInternal(Collection<? extends Date> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDateCollection(collection), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDateCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsInternal(@Nullable Object obj) {
        return this.osSet.contains(obj == null ? null : (Date) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDateCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeInternal(@Nullable Object obj) {
        return this.osSet.remove((Date) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean retainAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDateCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }
}
