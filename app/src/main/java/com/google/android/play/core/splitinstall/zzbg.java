package com.google.android.play.core.splitinstall;

import android.util.Log;
import androidx.annotation.Nullable;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbg {
    @Nullable
    public static final zzk zza(XmlPullParser xmlPullParser, zzi zziVar) {
        while (xmlPullParser.next() != 1) {
            try {
                if (xmlPullParser.getEventType() == 2) {
                    if (xmlPullParser.getName().equals("splits")) {
                        while (xmlPullParser.next() != 3) {
                            if (xmlPullParser.getEventType() == 2) {
                                if (xmlPullParser.getName().equals("module")) {
                                    String strZzb = zzb("name", xmlPullParser, zziVar);
                                    if (strZzb != null) {
                                        while (xmlPullParser.next() != 3) {
                                            if (xmlPullParser.getEventType() == 2) {
                                                if (xmlPullParser.getName().equals("language")) {
                                                    while (xmlPullParser.next() != 3) {
                                                        if (xmlPullParser.getEventType() == 2) {
                                                            if (xmlPullParser.getName().equals("entry")) {
                                                                String strZzb2 = zzb("key", xmlPullParser, zziVar);
                                                                String strZzb3 = zzb("split", xmlPullParser, zziVar);
                                                                zzc(xmlPullParser, zziVar);
                                                                if (strZzb2 != null && strZzb3 != null) {
                                                                    zziVar.zza(strZzb, strZzb2, strZzb3);
                                                                }
                                                            } else {
                                                                zzc(xmlPullParser, zziVar);
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    zzc(xmlPullParser, zziVar);
                                                }
                                            }
                                        }
                                    } else {
                                        zzc(xmlPullParser, zziVar);
                                    }
                                } else {
                                    zzc(xmlPullParser, zziVar);
                                }
                            }
                        }
                    } else {
                        zzc(xmlPullParser, zziVar);
                    }
                }
            } catch (IOException | IllegalStateException | XmlPullParserException e) {
                Log.e("SplitInstall", "Error while parsing splits.xml", e);
                return null;
            }
        }
        return zziVar.zzb();
    }

    @Nullable
    private static final String zzb(String str, XmlPullParser xmlPullParser, zzi zziVar) {
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            if (xmlPullParser.getAttributeName(i).equals(str)) {
                return xmlPullParser.getAttributeValue(i);
            }
        }
        return null;
    }

    private static final void zzc(XmlPullParser xmlPullParser, zzi zziVar) throws XmlPullParserException, IOException {
        int i = 1;
        while (i != 0) {
            int next = xmlPullParser.next();
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }
}
