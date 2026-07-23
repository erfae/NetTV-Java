package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.Collection;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class Decimal128Operator extends SetValueOperator<Decimal128> {
    public Decimal128Operator(BaseRealm baseRealm, OsSet osSet, Class<Decimal128> cls) {
        super(baseRealm, osSet, cls);
    }

    @Override // io.realm.SetValueOperator
    public final boolean add(@Nullable Decimal128 decimal128) {
        return this.osSet.add(decimal128);
    }

    @Override // io.realm.SetValueOperator
    public final boolean addAllInternal(Collection<? extends Decimal128> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDecimal128Collection(collection), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDecimal128Collection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsInternal(@Nullable Object obj) {
        return this.osSet.contains(obj == null ? null : (Decimal128) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDecimal128Collection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeInternal(@Nullable Object obj) {
        return this.osSet.remove((Decimal128) obj);
    }

    @Override // io.realm.SetValueOperator
    public final boolean retainAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newDecimal128Collection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }
}
