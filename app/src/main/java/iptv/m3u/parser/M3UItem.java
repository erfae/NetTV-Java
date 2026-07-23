package iptv.m3u.parser;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
public class M3UItem {
    private String mChannelId;
    private String mChannelName;
    private String mDLNAExtras;
    private int mDuration;
    private String mGroupTitle;
    private String mLogoURL;
    private String mPlugin;
    private String mStreamURL;
    private String mType;

    public String getChannelId() {
        return this.mChannelId;
    }

    public String getChannelName() {
        return this.mChannelName;
    }

    public String getDLNAExtras() {
        return this.mDLNAExtras;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public String getGroupTitle() {
        String str = this.mGroupTitle;
        return str == null ? "" : str;
    }

    public String getLogoURL() {
        return this.mLogoURL;
    }

    public String getPlugin() {
        return this.mPlugin;
    }

    public String getStreamURL() {
        return this.mStreamURL;
    }

    public String getType() {
        return this.mType;
    }

    public void setChannelID(String str) {
        this.mChannelId = str;
    }

    public void setChannelName(String str) {
        this.mChannelName = str;
    }

    public void setDLNAExtras(String str) {
        this.mDLNAExtras = str;
    }

    public void setDuration(int i) {
        this.mDuration = i;
    }

    public void setGroupTitle(String str) {
        this.mGroupTitle = str;
    }

    public void setLogoURL(String str) {
        this.mLogoURL = str;
    }

    public void setPlugin(String str) {
        this.mPlugin = str;
    }

    public void setStreamURL(String str) {
        this.mStreamURL = str;
    }

    public void setType(String str) {
        this.mType = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[Item]");
        if (this.mChannelId != null) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("\n Channel Id: ");
            sbM.append(this.mChannelId);
            stringBuffer.append(sbM.toString());
        }
        if (this.mChannelName != null) {
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("\nChannel Name: ");
            sbM2.append(this.mChannelName);
            stringBuffer.append(sbM2.toString());
        }
        StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("\nDuration: ");
        sbM3.append(this.mDuration);
        stringBuffer.append(sbM3.toString());
        if (this.mStreamURL != null) {
            StringBuilder sbM4 = Insets$$ExternalSyntheticOutline0.m("\nStream URL: ");
            sbM4.append(this.mStreamURL);
            stringBuffer.append(sbM4.toString());
        }
        if (this.mGroupTitle != null) {
            StringBuilder sbM5 = Insets$$ExternalSyntheticOutline0.m("\nGroup: ");
            sbM5.append(this.mGroupTitle);
            stringBuffer.append(sbM5.toString());
        }
        if (this.mLogoURL != null) {
            StringBuilder sbM6 = Insets$$ExternalSyntheticOutline0.m("\nLogo: ");
            sbM6.append(this.mLogoURL);
            stringBuffer.append(sbM6.toString());
        }
        if (this.mType != null) {
            StringBuilder sbM7 = Insets$$ExternalSyntheticOutline0.m("\nType: ");
            sbM7.append(this.mType);
            stringBuffer.append(sbM7.toString());
        }
        if (this.mDLNAExtras != null) {
            StringBuilder sbM8 = Insets$$ExternalSyntheticOutline0.m("\nDLNA Extras: ");
            sbM8.append(this.mDLNAExtras);
            stringBuffer.append(sbM8.toString());
        }
        if (this.mPlugin != null) {
            StringBuilder sbM9 = Insets$$ExternalSyntheticOutline0.m("\nPlugin: ");
            sbM9.append(this.mPlugin);
            stringBuffer.append(sbM9.toString());
        }
        return stringBuffer.toString();
    }
}
