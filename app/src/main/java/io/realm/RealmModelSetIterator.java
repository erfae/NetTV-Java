package io.realm;

import io.realm.RealmModel;
import io.realm.internal.OsSet;
import java.util.ArrayList;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class RealmModelSetIterator<T extends RealmModel> extends SetIterator<T> {
    private final Class<T> valueClass;

    public RealmModelSetIterator(OsSet osSet, BaseRealm baseRealm, Class<T> cls) {
        super(osSet, baseRealm);
        this.valueClass = cls;
    }

    @Override // io.realm.SetIterator
    public final Object getValueAtIndex(int i) {
        return this.baseRealm.get(this.valueClass, this.osSet.getRow(i), new ArrayList());
    }
}
