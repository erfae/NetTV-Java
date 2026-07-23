package io.realm;

import io.realm.RealmModel;
import io.realm.internal.OsSet;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class RealmModelSetOperator<T extends RealmModel> extends SetValueOperator<T> {
    public RealmModelSetOperator(BaseRealm baseRealm, OsSet osSet, Class<T> cls) {
        super(baseRealm, osSet, cls);
    }

    private void checkValidCollection(Collection<? extends T> collection) {
        Iterator<? extends T> it = collection.iterator();
        while (it.hasNext()) {
            checkValidObject(it.next());
        }
    }

    private void checkValidObject(RealmModel realmModel) {
        Objects.requireNonNull(realmModel, "This set does not permit null values.");
        if (!RealmObject.isValid(realmModel) || !RealmObject.isManaged(realmModel)) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy) realmModel).realmGet$proxyState().getRealm$realm() != this.baseRealm) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
    }

    private T getManagedObject(T t) {
        Objects.requireNonNull(t, "This set does not permit null values.");
        return CollectionUtils.checkCanObjectBeCopied(this.baseRealm, t, this.valueClass.getName(), CollectionUtils.SET_TYPE) ? (T) CollectionUtils.copyToRealm(this.baseRealm, t) : t;
    }

    @Override // io.realm.SetValueOperator
    public final boolean add(Object obj) {
        return this.osSet.addRow(((RealmObjectProxy) getManagedObject((RealmModel) obj)).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    @Override // io.realm.SetValueOperator
    public final boolean addAllInternal(Collection<? extends T> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<? extends T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(getManagedObject(it.next()));
        }
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newRealmModelCollection(arrayList), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.SetValueOperator
    public final boolean containsAllInternal(Collection<?> collection) {
        checkValidCollection(collection);
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newRealmModelCollection(collection), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean containsInternal(Object obj) {
        checkValidObject((RealmModel) obj);
        return this.osSet.containsRow(((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.SetValueOperator
    public final boolean removeAllInternal(Collection<?> collection) {
        checkValidCollection(collection);
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newRealmModelCollection(collection), OsSet.ExternalCollectionOperation.REMOVE_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final boolean removeInternal(Object obj) {
        checkValidObject((RealmModel) obj);
        return this.osSet.removeRow(((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.SetValueOperator
    public final boolean retainAllInternal(Collection<?> collection) {
        checkValidCollection(collection);
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newRealmModelCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final RealmQuery<T> where() {
        return new RealmQuery<>(this.baseRealm, this.osSet, this.valueClass);
    }
}
