package io.realm;

import io.realm.internal.core.NativeRealmAny;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes2.dex */
abstract class PrimitiveRealmAnyOperator extends RealmAnyOperator {

    @Nullable
    private final Object value;

    public PrimitiveRealmAnyOperator(@Nullable Object obj, @Nonnull RealmAny.Type type) {
        super(type);
        this.value = obj;
    }

    public boolean equals(Object obj) {
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        Object obj2 = this.value;
        Object obj3 = ((PrimitiveRealmAnyOperator) obj).value;
        if (obj2 == null) {
            return obj3 == null;
        }
        return obj2.equals(obj3);
    }

    @Override // io.realm.RealmAnyOperator
    public final <T> T getValue(Class<T> cls) {
        return cls.cast(this.value);
    }

    public final int hashCode() {
        Object obj = this.value;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public String toString() {
        return this.value.toString();
    }

    public PrimitiveRealmAnyOperator(@Nullable Object obj, @Nonnull RealmAny.Type type, @Nonnull NativeRealmAny nativeRealmAny) {
        super(type, nativeRealmAny);
        this.value = obj;
    }
}
