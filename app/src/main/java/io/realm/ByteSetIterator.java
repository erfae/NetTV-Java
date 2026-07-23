package io.realm;

import io.realm.internal.OsSet;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class ByteSetIterator extends SetIterator<Byte> {
    public ByteSetIterator(OsSet osSet, BaseRealm baseRealm) {
        super(osSet, baseRealm);
    }

    @Override // io.realm.SetIterator
    public final Byte getValueAtIndex(int i) {
        Object valueAtIndex = this.osSet.getValueAtIndex(i);
        if (valueAtIndex == null) {
            return null;
        }
        return Byte.valueOf(((Long) valueAtIndex).byteValue());
    }
}
