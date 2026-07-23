package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import org.bson.assertions.Assertions;
import org.bson.types.Decimal128;

/* JADX INFO: loaded from: classes2.dex */
public final class BsonDecimal128 extends BsonNumber {
    private final Decimal128 value;

    public BsonDecimal128(Decimal128 decimal128) {
        Assertions.notNull("value", decimal128);
        this.value = decimal128;
    }

    @Override // org.bson.BsonNumber
    public Decimal128 decimal128Value() {
        return this.value;
    }

    @Override // org.bson.BsonNumber
    public double doubleValue() {
        return this.value.bigDecimalValue().doubleValue();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && BsonDecimal128.class == obj.getClass() && this.value.equals(((BsonDecimal128) obj).value);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.DECIMAL128;
    }

    public Decimal128 getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override // org.bson.BsonNumber
    public int intValue() {
        return this.value.bigDecimalValue().intValue();
    }

    @Override // org.bson.BsonNumber
    public long longValue() {
        return this.value.bigDecimalValue().longValue();
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("BsonDecimal128{value=");
        sbM.append(this.value);
        sbM.append('}');
        return sbM.toString();
    }
}
