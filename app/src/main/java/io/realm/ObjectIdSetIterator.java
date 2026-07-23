package io.realm;

import io.realm.internal.OsSet;
import org.bson.types.ObjectId;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class ObjectIdSetIterator extends SetIterator<ObjectId> {
    public ObjectIdSetIterator(OsSet osSet, BaseRealm baseRealm) {
        super(osSet, baseRealm);
    }
}
