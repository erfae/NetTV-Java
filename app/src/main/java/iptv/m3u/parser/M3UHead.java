package iptv.m3u.parser;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
public class M3UHead {
    private String mDLNAExtras;
    private String mName;
    private String mPlugin;
    private String mType;

    public String getDLNAExtras() {
        return this.mDLNAExtras;
    }

    public String getName() {
        return this.mName;
    }

    public String getPlugin() {
        return this.mPlugin;
    }

    public String getType() {
        return this.mType;
    }

    public void setDLNAExtras(String str) {
        this.mDLNAExtras = str;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public void setPlugin(String str) {
        this.mPlugin = str;
    }

    public void setType(String str) {
        this.mType = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[Head]");
        if (this.mName != null) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("\nName: ");
            sbM.append(this.mName);
            stringBuffer.append(sbM.toString());
        }
        if (this.mType != null) {
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("\nType: ");
            sbM2.append(this.mType);
            stringBuffer.append(sbM2.toString());
        }
        if (this.mDLNAExtras != null) {
            StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("\nDLNA Extras: ");
            sbM3.append(this.mDLNAExtras);
            stringBuffer.append(sbM3.toString());
        }
        if (this.mPlugin != null) {
            StringBuilder sbM4 = Insets$$ExternalSyntheticOutline0.m("\nPlugin: ");
            sbM4.append(this.mPlugin);
            stringBuffer.append(sbM4.toString());
        }
        return stringBuffer.toString();
    }
}
