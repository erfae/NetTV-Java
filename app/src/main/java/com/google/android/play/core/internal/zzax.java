package com.google.android.play.core.internal;

import android.content.res.AssetManager;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzax {
    private final com.google.android.play.core.splitcompat.zza zza;

    @Nullable
    private XmlPullParser zzb;

    public zzax(com.google.android.play.core.splitcompat.zza zzaVar) {
        this.zza = zzaVar;
    }

    public final long zza() throws XmlPullParserException, IOException {
        int next;
        if (this.zzb == null) {
            throw new XmlPullParserException("Manifest file needs to be loaded before parsing.");
        }
        do {
            next = this.zzb.next();
            if (next == 2) {
                if (!this.zzb.getName().equals("manifest")) {
                    break;
                }
                String attributeValue = this.zzb.getAttributeValue("http://schemas.android.com/apk/res/android", "versionCode");
                String attributeValue2 = this.zzb.getAttributeValue("http://schemas.android.com/apk/res/android", "versionCodeMajor");
                if (attributeValue == null) {
                    throw new XmlPullParserException("Manifest entry doesn't contain 'versionCode' attribute.");
                }
                try {
                    int i = Integer.parseInt(attributeValue);
                    if (attributeValue2 == null) {
                        return i;
                    }
                    try {
                        return (((long) Integer.parseInt(attributeValue2)) << 32) | (((long) i) & 4294967295L);
                    } catch (NumberFormatException e) {
                        throw new XmlPullParserException(String.format("Couldn't parse versionCodeMajor to int: %s", e.getMessage()));
                    }
                } catch (NumberFormatException e2) {
                    throw new XmlPullParserException(String.format("Couldn't parse versionCode to int: %s", e2.getMessage()));
                }
            }
        } while (next != 1);
        throw new XmlPullParserException("Couldn't find manifest entry at top-level.");
    }

    public final void zzb(AssetManager assetManager, File file) throws IOException {
        this.zzb = assetManager.openXmlResourceParser(com.google.android.play.core.splitcompat.zza.zzc(assetManager, file), "AndroidManifest.xml");
    }
}
