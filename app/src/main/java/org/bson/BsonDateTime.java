package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
public class BsonDateTime extends BsonValue implements Comparable<BsonDateTime> {
    private final long value;

    public BsonDateTime(long j) {
        this.value = j;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((BsonDateTime) obj).value;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.DATE_TIME;
    }

    public long getValue() {
        return this.value;
    }

    public int hashCode() {
        long j = this.value;
        return (int) (j ^ (j >>> 32));
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("BsonDateTime{value=");
        sbM.append(this.value);
        sbM.append('}');
        return sbM.toString();
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonDateTime bsonDateTime) {
        return Long.valueOf(this.value).compareTo(Long.valueOf(bsonDateTime.value));
    }
}
