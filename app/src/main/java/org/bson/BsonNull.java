package org.bson;

/* JADX INFO: loaded from: classes2.dex */
public final class BsonNull extends BsonValue {
    public static final BsonNull VALUE = new BsonNull();

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && BsonNull.class == obj.getClass();
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.NULL;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "BsonNull";
    }
}
