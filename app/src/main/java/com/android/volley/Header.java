package com.android.volley;

import android.text.TextUtils;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes.dex */
public final class Header {
    private final String mName;
    private final String mValue;

    public Header(String str, String str2) {
        this.mName = str;
        this.mValue = str2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Header.class != obj.getClass()) {
            return false;
        }
        Header header = (Header) obj;
        return TextUtils.equals(this.mName, header.mName) && TextUtils.equals(this.mValue, header.mValue);
    }

    public final String getName() {
        return this.mName;
    }

    public final String getValue() {
        return this.mValue;
    }

    public int hashCode() {
        return this.mValue.hashCode() + (this.mName.hashCode() * 31);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Header[name=");
        sbM.append(this.mName);
        sbM.append(",value=");
        return Insets$$ExternalSyntheticOutline0.m(sbM, this.mValue, "]");
    }
}
