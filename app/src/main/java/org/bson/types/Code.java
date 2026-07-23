package org.bson.types;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class Code implements Serializable {
    private static final long serialVersionUID = 475535263314046697L;
    private final String code;

    public Code(String str) {
        this.code = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.code.equals(((Code) obj).code);
    }

    public String getCode() {
        return this.code;
    }

    public int hashCode() {
        return this.code.hashCode();
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Code{code='");
        sbM.append(this.code);
        sbM.append('\'');
        sbM.append('}');
        return sbM.toString();
    }
}
