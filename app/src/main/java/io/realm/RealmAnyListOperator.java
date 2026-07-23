package io.realm;

import io.realm.internal.OsList;
import io.realm.internal.core.NativeRealmAny;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedListOperator.java */
/* JADX INFO: loaded from: classes2.dex */
final class RealmAnyListOperator extends ManagedListOperator<RealmAny> {
    public RealmAnyListOperator(BaseRealm baseRealm, OsList osList, Class<RealmAny> cls) {
        super(baseRealm, osList, cls);
    }

    @Override // io.realm.ManagedListOperator
    public void appendValue(Object obj) {
        this.osList.addRealmAny(CollectionUtils.copyToRealmIfNeeded(this.realm, (RealmAny) obj).getNativePtr());
    }

    @Override // io.realm.ManagedListOperator
    public final void checkValidValue(@Nullable Object obj) {
        if (obj != null && !(obj instanceof RealmAny)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "java.util.RealmAny", obj.getClass().getName()));
        }
    }

    @Override // io.realm.ManagedListOperator
    public boolean forRealmModel() {
        return false;
    }

    @Override // io.realm.ManagedListOperator
    public void insertValue(int i, Object obj) {
        checkInsertIndex(i);
        this.osList.insertRealmAny(i, CollectionUtils.copyToRealmIfNeeded(this.realm, (RealmAny) obj).getNativePtr());
    }

    @Override // io.realm.ManagedListOperator
    public final void setValue(int i, Object obj) {
        this.osList.setRealmAny(i, CollectionUtils.copyToRealmIfNeeded(this.realm, (RealmAny) obj).getNativePtr());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.realm.ManagedListOperator
    public RealmAny get(int i) {
        NativeRealmAny nativeRealmAny = (NativeRealmAny) this.osList.getValue(i);
        if (nativeRealmAny == null) {
            nativeRealmAny = new NativeRealmAny();
        }
        return new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.realm, nativeRealmAny));
    }
}
