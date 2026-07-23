package io.realm.rx;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollection;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public class CollectionChange<E extends OrderedRealmCollection> {
    private final OrderedCollectionChangeSet changeset;
    private final E collection;

    public CollectionChange(E e, @Nullable OrderedCollectionChangeSet orderedCollectionChangeSet) {
        this.collection = e;
        this.changeset = orderedCollectionChangeSet;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CollectionChange collectionChange = (CollectionChange) obj;
        if (!this.collection.equals(collectionChange.collection)) {
            return false;
        }
        OrderedCollectionChangeSet orderedCollectionChangeSet = this.changeset;
        OrderedCollectionChangeSet orderedCollectionChangeSet2 = collectionChange.changeset;
        if (orderedCollectionChangeSet != null) {
            return orderedCollectionChangeSet.equals(orderedCollectionChangeSet2);
        }
        return orderedCollectionChangeSet2 == null;
    }

    @Nullable
    public OrderedCollectionChangeSet getChangeset() {
        return this.changeset;
    }

    public E getCollection() {
        return this.collection;
    }

    public int hashCode() {
        int iHashCode = this.collection.hashCode() * 31;
        OrderedCollectionChangeSet orderedCollectionChangeSet = this.changeset;
        return iHashCode + (orderedCollectionChangeSet != null ? orderedCollectionChangeSet.hashCode() : 0);
    }
}
