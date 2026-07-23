package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import org.bson.internal.UnsignedLongs;

/* JADX INFO: loaded from: classes2.dex */
public final class BsonTimestamp extends BsonValue implements Comparable<BsonTimestamp> {
    private final long value;

    public BsonTimestamp() {
        this.value = 0L;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && BsonTimestamp.class == obj.getClass() && this.value == ((BsonTimestamp) obj).value;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.TIMESTAMP;
    }

    public int getInc() {
        return (int) this.value;
    }

    public int getTime() {
        return (int) (this.value >> 32);
    }

    public long getValue() {
        return this.value;
    }

    public int hashCode() {
        long j = this.value;
        return (int) (j ^ (j >>> 32));
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Timestamp{value=");
        sbM.append(getValue());
        sbM.append(", seconds=");
        sbM.append(getTime());
        sbM.append(", inc=");
        sbM.append(getInc());
        sbM.append('}');
        return sbM.toString();
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonTimestamp bsonTimestamp) {
        return UnsignedLongs.compare(this.value, bsonTimestamp.value);
    }

    public BsonTimestamp(long j) {
        this.value = j;
    }

    public BsonTimestamp(int i, int i2) {
        this.value = (((long) i2) & 4294967295L) | (((long) i) << 32);
    }
}
