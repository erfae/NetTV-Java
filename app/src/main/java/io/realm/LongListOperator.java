package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.internal.OsList;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedListOperator.java */
/* JADX INFO: loaded from: classes2.dex */
final class LongListOperator<T> extends ManagedListOperator<T> {
    public LongListOperator(BaseRealm baseRealm, OsList osList, Class<T> cls) {
        super(baseRealm, osList, cls);
    }

    @Override // io.realm.ManagedListOperator
    public void appendValue(Object obj) {
        this.osList.addLong(((Number) obj).longValue());
    }

    @Override // io.realm.ManagedListOperator
    public final void checkValidValue(@Nullable Object obj) {
        if (obj != null && !(obj instanceof Number)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "java.lang.Long, java.lang.Integer, java.lang.Short, java.lang.Byte", obj.getClass().getName()));
        }
    }

    @Override // io.realm.ManagedListOperator
    public boolean forRealmModel() {
        return false;
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [T, java.lang.Long] */
    @Override // io.realm.ManagedListOperator
    @Nullable
    public T get(int i) {
        ?? r4 = (T) ((Long) this.osList.getValue(i));
        if (r4 == 0) {
            return null;
        }
        Class<T> cls = this.clazz;
        if (cls == Long.class) {
            return r4;
        }
        if (cls == Integer.class) {
            return cls.cast(Integer.valueOf(r4.intValue()));
        }
        if (cls == Short.class) {
            return cls.cast(Short.valueOf(r4.shortValue()));
        }
        if (cls == Byte.class) {
            return cls.cast(Byte.valueOf(r4.byteValue()));
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Unexpected element type: ");
        sbM.append(this.clazz.getName());
        throw new IllegalStateException(sbM.toString());
    }

    @Override // io.realm.ManagedListOperator
    public void insertValue(int i, Object obj) {
        this.osList.insertLong(i, ((Number) obj).longValue());
    }

    @Override // io.realm.ManagedListOperator
    public final void setValue(int i, Object obj) {
        this.osList.setLong(i, ((Number) obj).longValue());
    }
}
