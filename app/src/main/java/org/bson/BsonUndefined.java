package org.bson;

/* JADX INFO: loaded from: classes2.dex */
public final class BsonUndefined extends BsonValue {
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && BsonUndefined.class == obj.getClass();
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.UNDEFINED;
    }

    public int hashCode() {
        return 0;
    }
}
