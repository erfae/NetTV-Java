package io.realm;

import io.realm.internal.OsSet;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class ShortSetIterator extends SetIterator<Short> {
    public ShortSetIterator(OsSet osSet, BaseRealm baseRealm) {
        super(osSet, baseRealm);
    }

    @Override // io.realm.SetIterator
    public final Short getValueAtIndex(int i) {
        Object valueAtIndex = this.osSet.getValueAtIndex(i);
        if (valueAtIndex == null) {
            return null;
        }
        return Short.valueOf(((Long) valueAtIndex).shortValue());
    }
}
