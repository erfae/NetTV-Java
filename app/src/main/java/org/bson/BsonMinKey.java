package org.bson;

/* JADX INFO: loaded from: classes2.dex */
public final class BsonMinKey extends BsonValue {
    public boolean equals(Object obj) {
        return obj instanceof BsonMinKey;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.MIN_KEY;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "BsonMinKey";
    }
}
