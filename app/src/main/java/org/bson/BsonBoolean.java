package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
public final class BsonBoolean extends BsonValue implements Comparable<BsonBoolean> {
    private final boolean value;
    public static final BsonBoolean TRUE = new BsonBoolean(true);
    public static final BsonBoolean FALSE = new BsonBoolean(false);

    public BsonBoolean(boolean z) {
        this.value = z;
    }

    public static BsonBoolean valueOf(boolean z) {
        return z ? TRUE : FALSE;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && BsonBoolean.class == obj.getClass() && this.value == ((BsonBoolean) obj).value;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.BOOLEAN;
    }

    public boolean getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value ? 1 : 0;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("BsonBoolean{value=");
        sbM.append(this.value);
        sbM.append('}');
        return sbM.toString();
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonBoolean bsonBoolean) {
        return Boolean.valueOf(this.value).compareTo(Boolean.valueOf(bsonBoolean.value));
    }
}
