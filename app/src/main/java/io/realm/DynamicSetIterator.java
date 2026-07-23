package io.realm;

import io.realm.internal.OsSet;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class DynamicSetIterator extends SetIterator<DynamicRealmObject> {
    private final String className;

    public DynamicSetIterator(OsSet osSet, BaseRealm baseRealm, String str) {
        super(osSet, baseRealm);
        this.className = str;
    }

    @Override // io.realm.SetIterator
    public final DynamicRealmObject getValueAtIndex(int i) {
        return (DynamicRealmObject) this.baseRealm.get(DynamicRealmObject.class, this.className, this.osSet.getRow(i));
    }
}
