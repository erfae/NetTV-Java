package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class RealmAnySetOperator extends SetValueOperator<RealmAny> {
    public RealmAnySetOperator(BaseRealm baseRealm, OsSet osSet) {
        super(baseRealm, osSet, RealmAny.class);
    }

    private void checkValidObject(RealmAny realmAny) {
        try {
            realmAny.checkValidObject(this.baseRealm);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("RealmAny collection contains unmanaged objects.", e);
        }
    }

    @NotNull
    private RealmAny getManagedRealmAny(@Nullable RealmAny realmAny) {
        if (realmAny == null) {
            return RealmAny.nullValue();
        }
        if (realmAny.getType() != RealmAny.Type.OBJECT) {
            return realmAny;
        }
        RealmModel realmModelAsRealmModel = realmAny.asRealmModel(RealmModel.class);
        if (CollectionUtils.checkCanObjectBeCopied(this.baseRealm, realmModelAsRealmModel, this.valueClass.getName(), CollectionUtils.SET_TYPE)) {
            realmModelAsRealmModel = CollectionUtils.copyToRealm(this.baseRealm, realmModelAsRealmModel);
        }
        return RealmAny.valueOf(realmModelAsRealmModel);
    }

    @NotNull
    private NativeRealmAnyCollection getNativeRealmAnyCollection(Collection<? extends RealmAny> collection) {
        long[] jArr = new long[collection.size()];
        boolean[] zArr = new boolean[collection.size()];
        int i = 0;
        for (RealmAny realmAny : collection) {
            if (realmAny != null) {
                checkValidObject(realmAny);
                jArr[i] = realmAny.getNativePtr();
                zArr[i] = true;
            }
            i++;
        }
        return NativeRealmAnyCollection.newRealmAnyCollection(jArr, zArr);
    }

    @Override // io.realm.SetValueOperator
    public final boolean add(@Nullable RealmAny realmAny) {
        return this.osSet.addRealmAny(getManagedRealmAny(realmAny).getNativePtr());
    }

    @Override // io.realm.SetValueOperator
    public final boolean addAllInternal(Collection<? extends RealmAny> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<? extends RealmAny> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(getManagedRealmAny(it.next()));
        }
        return this.osSet.collectionFunnel(getNativeRealmAnyCollection(arrayList), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(getNativeRealmAnyCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsInternal(@Nullable Object obj) {
        RealmAny realmAnyNullValue = obj == null ? RealmAny.nullValue() : (RealmAny) obj;
        checkValidObject(realmAnyNullValue);
        return this.osSet.containsRealmAny(realmAnyNullValue.getNativePtr());
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(getNativeRealmAnyCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeInternal(@Nullable Object obj) {
        RealmAny realmAnyNullValue = obj == null ? RealmAny.nullValue() : (RealmAny) obj;
        checkValidObject(realmAnyNullValue);
        return this.osSet.removeRealmAny(realmAnyNullValue.getNativePtr());
    }

    @Override // io.realm.SetValueOperator
    public final boolean retainAllInternal(Collection<?> collection) {
        return this.osSet.collectionFunnel(getNativeRealmAnyCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }
}
