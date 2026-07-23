package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public class BsonObjectId extends BsonValue implements Comparable<BsonObjectId> {
    private final ObjectId value;

    public BsonObjectId() {
        this(new ObjectId());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value.equals(((BsonObjectId) obj).value);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.OBJECT_ID;
    }

    public ObjectId getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("BsonObjectId{value=");
        sbM.append(this.value.toHexString());
        sbM.append('}');
        return sbM.toString();
    }

    public BsonObjectId(ObjectId objectId) {
        if (objectId == null) {
            throw new IllegalArgumentException("value may not be null");
        }
        this.value = objectId;
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonObjectId bsonObjectId) {
        return this.value.compareTo(bsonObjectId.value);
    }
}
