package io.realm.rx;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.ObjectChangeSet;
import io.realm.RealmModel;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public class ObjectChange<E extends RealmModel> {
    private final ObjectChangeSet changeset;
    private final E object;

    public ObjectChange(E e, @Nullable ObjectChangeSet objectChangeSet) {
        this.object = e;
        this.changeset = objectChangeSet;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ObjectChange objectChange = (ObjectChange) obj;
        if (!this.object.equals(objectChange.object)) {
            return false;
        }
        ObjectChangeSet objectChangeSet = this.changeset;
        ObjectChangeSet objectChangeSet2 = objectChange.changeset;
        if (objectChangeSet != null) {
            return objectChangeSet.equals(objectChangeSet2);
        }
        return objectChangeSet2 == null;
    }

    @Nullable
    public ObjectChangeSet getChangeset() {
        return this.changeset;
    }

    public E getObject() {
        return this.object;
    }

    public int hashCode() {
        int iHashCode = this.object.hashCode() * 31;
        ObjectChangeSet objectChangeSet = this.changeset;
        return iHashCode + (objectChangeSet != null ? objectChangeSet.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("ObjectChange{object=");
        sbM.append(this.object);
        sbM.append(", changeset=");
        sbM.append(this.changeset);
        sbM.append('}');
        return sbM.toString();
    }
}
