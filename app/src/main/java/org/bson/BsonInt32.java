package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import org.bson.types.Decimal128;

/* JADX INFO: loaded from: classes2.dex */
public final class BsonInt32 extends BsonNumber implements Comparable<BsonInt32> {
    private final int value;

    public BsonInt32(int i) {
        this.value = i;
    }

    @Override // org.bson.BsonNumber
    public Decimal128 decimal128Value() {
        return new Decimal128(this.value);
    }

    @Override // org.bson.BsonNumber
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && BsonInt32.class == obj.getClass() && this.value == ((BsonInt32) obj).value;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.INT32;
    }

    public int getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value;
    }

    @Override // org.bson.BsonNumber
    public int intValue() {
        return this.value;
    }

    @Override // org.bson.BsonNumber
    public long longValue() {
        return this.value;
    }

    public String toString() {
        return Insets$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m("BsonInt32{value="), this.value, '}');
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonInt32 bsonInt32) {
        int i = this.value;
        int i2 = bsonInt32.value;
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }
}
