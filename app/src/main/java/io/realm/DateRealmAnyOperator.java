package io.realm;

import io.realm.internal.core.NativeRealmAny;
import java.util.Date;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes2.dex */
final class DateRealmAnyOperator extends PrimitiveRealmAnyOperator {
    public DateRealmAnyOperator(Date date) {
        super(date, RealmAny.Type.DATE);
    }

    @Override // io.realm.RealmAnyOperator
    public final NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((Date) getValue(Date.class));
    }

    public DateRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(nativeRealmAny.asDate(), RealmAny.Type.DATE, nativeRealmAny);
    }
}
