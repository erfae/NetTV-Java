package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
public class BsonJavaScriptWithScope extends BsonValue {
    private final String code;
    private final BsonDocument scope;

    public BsonJavaScriptWithScope(String str, BsonDocument bsonDocument) {
        if (str == null) {
            throw new IllegalArgumentException("code can not be null");
        }
        if (bsonDocument == null) {
            throw new IllegalArgumentException("scope can not be null");
        }
        this.code = str;
        this.scope = bsonDocument;
    }

    public static BsonJavaScriptWithScope clone(BsonJavaScriptWithScope bsonJavaScriptWithScope) {
        return new BsonJavaScriptWithScope(bsonJavaScriptWithScope.code, bsonJavaScriptWithScope.scope.clone());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BsonJavaScriptWithScope bsonJavaScriptWithScope = (BsonJavaScriptWithScope) obj;
        return this.code.equals(bsonJavaScriptWithScope.code) && this.scope.equals(bsonJavaScriptWithScope.scope);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.JAVASCRIPT_WITH_SCOPE;
    }

    public String getCode() {
        return this.code;
    }

    public BsonDocument getScope() {
        return this.scope;
    }

    public int hashCode() {
        return this.scope.hashCode() + (this.code.hashCode() * 31);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("BsonJavaScriptWithScope{code=");
        sbM.append(getCode());
        sbM.append("scope=");
        sbM.append(this.scope);
        sbM.append('}');
        return sbM.toString();
    }
}
