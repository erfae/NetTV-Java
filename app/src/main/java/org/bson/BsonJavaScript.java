package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
public class BsonJavaScript extends BsonValue {
    private final String code;

    public BsonJavaScript(String str) {
        this.code = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.code.equals(((BsonJavaScript) obj).code);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.JAVASCRIPT;
    }

    public String getCode() {
        return this.code;
    }

    public int hashCode() {
        return this.code.hashCode();
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("BsonJavaScript{code='");
        sbM.append(this.code);
        sbM.append('\'');
        sbM.append('}');
        return sbM.toString();
    }
}
