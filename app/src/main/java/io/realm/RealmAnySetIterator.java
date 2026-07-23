package io.realm;

import io.realm.internal.OsSet;
import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class RealmAnySetIterator extends SetIterator<RealmAny> {
    public RealmAnySetIterator(OsSet osSet, BaseRealm baseRealm) {
        super(osSet, baseRealm);
    }

    @Override // io.realm.SetIterator
    public final RealmAny getValueAtIndex(int i) {
        return new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.baseRealm, new NativeRealmAny(this.osSet.getRealmAny(i))));
    }
}
