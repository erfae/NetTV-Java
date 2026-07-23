package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
public class BsonString extends BsonValue implements Comparable<BsonString> {
    private final String value;

    public BsonString(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Value can not be null");
        }
        this.value = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value.equals(((BsonString) obj).value);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.STRING;
    }

    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("BsonString{value='");
        sbM.append(this.value);
        sbM.append('\'');
        sbM.append('}');
        return sbM.toString();
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonString bsonString) {
        return this.value.compareTo(bsonString.value);
    }
}
