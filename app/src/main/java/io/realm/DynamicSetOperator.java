package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.core.NativeRealmAnyCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class DynamicSetOperator extends SetValueOperator<DynamicRealmObject> {
    public DynamicSetOperator(BaseRealm baseRealm, OsSet osSet, String str) {
        super(baseRealm, osSet, str);
    }

    private void checkValidCollection(Collection<? extends DynamicRealmObject> collection) {
        Iterator<? extends DynamicRealmObject> it = collection.iterator();
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

    private DynamicRealmObject getManagedObject(DynamicRealmObject dynamicRealmObject) {
        Objects.requireNonNull(dynamicRealmObject, "This set does not permit null values.");
        boolean zCheckCanObjectBeCopied = CollectionUtils.checkCanObjectBeCopied(this.baseRealm, dynamicRealmObject, this.className, CollectionUtils.SET_TYPE);
        RealmModel realmModelCopyToRealm = dynamicRealmObject;
        if (zCheckCanObjectBeCopied) {
            realmModelCopyToRealm = CollectionUtils.copyToRealm(this.baseRealm, dynamicRealmObject);
        }
        return (DynamicRealmObject) realmModelCopyToRealm;
    }

    @Override // io.realm.SetValueOperator
    public final boolean add(DynamicRealmObject dynamicRealmObject) {
        return this.osSet.addRow(getManagedObject(dynamicRealmObject).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    @Override // io.realm.SetValueOperator
    public final boolean addAllInternal(Collection<? extends DynamicRealmObject> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<? extends DynamicRealmObject> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(getManagedObject(it.next()));
        }
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newRealmModelCollection(arrayList), OsSet.ExternalCollectionOperation.ADD_ALL);
    }

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

    @Override // io.realm.SetValueOperator
    public final boolean retainAllInternal(Collection<?> collection) {
        checkValidCollection(collection);
        return this.osSet.collectionFunnel(NativeRealmAnyCollection.newRealmModelCollection(collection), OsSet.ExternalCollectionOperation.RETAIN_ALL);
    }

    @Override // io.realm.SetValueOperator
    public final RealmQuery<DynamicRealmObject> where() {
        return new RealmQuery<>(this.baseRealm, this.osSet, this.valueClass);
    }
}
