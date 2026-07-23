package io.realm;

import io.realm.internal.RealmObjectProxy;
import io.realm.internal.core.NativeRealmAny;
import java.util.Collections;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes2.dex */
class RealmModelOperator extends RealmAnyOperator {
    private final Class<? extends RealmModel> clazz;
    private final RealmModel value;

    public RealmModelOperator(RealmModel realmModel) {
        super(RealmAny.Type.OBJECT);
        this.value = realmModel;
        this.clazz = realmModel.getClass();
    }

    private static <T extends RealmModel> T getRealmModel(BaseRealm baseRealm, Class<T> cls, NativeRealmAny nativeRealmAny) {
        return (T) baseRealm.get(cls, nativeRealmAny.getRealmModelRowKey(), Collections.emptyList());
    }

    @Override // io.realm.RealmAnyOperator
    public void checkValidObject(BaseRealm baseRealm) {
        if (!RealmObject.isValid(this.value) || !RealmObject.isManaged(this.value)) {
            throw new IllegalArgumentException("Realm object is not a valid managed object.");
        }
        if (((RealmObjectProxy) this.value).realmGet$proxyState().getRealm$realm() != baseRealm) {
            throw new IllegalArgumentException("Realm object belongs to a different Realm.");
        }
    }

    @Override // io.realm.RealmAnyOperator
    public final NativeRealmAny createNativeRealmAny() {
        if (this.value instanceof RealmObjectProxy) {
            return new NativeRealmAny((RealmObjectProxy) RealmObjectProxy.class.cast(this.value));
        }
        throw new IllegalStateException("Native RealmAny instances only allow managed Realm objects or primitives");
    }

    public boolean equals(Object obj) {
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        RealmModel realmModel = this.value;
        RealmModel realmModel2 = ((RealmModelOperator) obj).value;
        if (realmModel == null) {
            return realmModel2 == null;
        }
        return realmModel.equals(realmModel2);
    }

    @Override // io.realm.RealmAnyOperator
    public Class<?> getTypedClass() {
        return RealmObjectProxy.class.isAssignableFrom(this.clazz) ? this.clazz.getSuperclass() : this.clazz;
    }

    @Override // io.realm.RealmAnyOperator
    public final <T> T getValue(Class<T> cls) {
        return cls.cast(this.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        return this.value.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends RealmModel> RealmModelOperator(BaseRealm baseRealm, NativeRealmAny nativeRealmAny, Class<T> cls) {
        super(RealmAny.Type.OBJECT, nativeRealmAny);
        this.clazz = cls;
        this.value = getRealmModel(baseRealm, cls, nativeRealmAny);
    }
}
