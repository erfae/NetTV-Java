package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.internal.OsList;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
abstract class ManagedListOperator<T> {

    @Nullable
    public final Class<T> clazz;
    public final OsList osList;
    public final BaseRealm realm;

    public ManagedListOperator(BaseRealm baseRealm, OsList osList, @Nullable Class<T> cls) {
        this.realm = baseRealm;
        this.clazz = cls;
        this.osList = osList;
    }

    private void appendNull() {
        this.osList.addNull();
    }

    public final void append(@Nullable Object obj) {
        checkValidValue(obj);
        if (obj == null) {
            appendNull();
        } else {
            appendValue(obj);
        }
    }

    public abstract void appendValue(Object obj);

    public final void checkInsertIndex(int i) {
        int size = size();
        if (i < 0 || size < i) {
            StringBuilder sbM24m = Insets$$ExternalSyntheticOutline0.m24m("Invalid index ", i, ", size is ");
            sbM24m.append(this.osList.size());
            throw new IndexOutOfBoundsException(sbM24m.toString());
        }
    }

    public abstract void checkValidValue(@Nullable Object obj);

    public abstract boolean forRealmModel();

    @Nullable
    public abstract T get(int i);

    public final OsList getOsList() {
        return this.osList;
    }

    public final void insert(int i, @Nullable T t) {
        checkValidValue(t);
        if (t == null) {
            insertNull(i);
        } else {
            insertValue(i, t);
        }
    }

    public void insertNull(int i) {
        this.osList.insertNull(i);
    }

    public abstract void insertValue(int i, Object obj);

    public final boolean isEmpty() {
        return this.osList.isEmpty();
    }

    public final boolean isValid() {
        return this.osList.isValid();
    }

    @Nullable
    public final T set(int i, @Nullable Object obj) {
        checkValidValue(obj);
        T t = get(i);
        if (obj == null) {
            setNull(i);
        } else {
            setValue(i, obj);
        }
        return t;
    }

    public void setNull(int i) {
        this.osList.setNull(i);
    }

    public abstract void setValue(int i, Object obj);

    public final int size() {
        long size = this.osList.size();
        if (size < 2147483647L) {
            return (int) size;
        }
        return Integer.MAX_VALUE;
    }
}
