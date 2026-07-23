package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public class BsonDbPointer extends BsonValue {
    private final ObjectId id;
    private final String namespace;

    public BsonDbPointer(String str, ObjectId objectId) {
        if (str == null) {
            throw new IllegalArgumentException("namespace can not be null");
        }
        if (objectId == null) {
            throw new IllegalArgumentException("id can not be null");
        }
        this.namespace = str;
        this.id = objectId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BsonDbPointer bsonDbPointer = (BsonDbPointer) obj;
        return this.id.equals(bsonDbPointer.id) && this.namespace.equals(bsonDbPointer.namespace);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.DB_POINTER;
    }

    public ObjectId getId() {
        return this.id;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public int hashCode() {
        return this.id.hashCode() + (this.namespace.hashCode() * 31);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("BsonDbPointer{namespace='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.namespace, '\'', ", id=");
        sbM.append(this.id);
        sbM.append('}');
        return sbM.toString();
    }
}
