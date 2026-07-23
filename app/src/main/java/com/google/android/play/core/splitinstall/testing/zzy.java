package com.google.android.play.core.splitinstall.testing;

import com.google.android.play.core.internal.zzag;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzy {
    private static final zzag zza = new zzag("LocalTestingConfigParser");
    private final XmlPullParser zzb;
    private final zzs zzc;

    public zzy(XmlPullParser xmlPullParser) {
        this.zzb = xmlPullParser;
        zzt zztVar = zzt.zza;
        zza zzaVar = new zza();
        zzaVar.zzb(new HashMap());
        this.zzc = zzaVar;
    }

    public static zzt zza(File file) {
        File file2 = new File(file, "local_testing_config.xml");
        if (!file2.exists()) {
            return zzt.zza;
        }
        try {
            FileReader fileReader = new FileReader(file2);
            try {
                XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
                xmlPullParserNewPullParser.setInput(fileReader);
                final zzy zzyVar = new zzy(xmlPullParserNewPullParser);
                zzyVar.zze("local-testing-config", new zzx() { // from class: com.google.android.play.core.splitinstall.testing.zzu
                    @Override // com.google.android.play.core.splitinstall.testing.zzx
                    public final void zza() throws XmlPullParserException, IOException {
                        this.zza.zzd();
                    }
                });
                zzs zzsVar = zzyVar.zzc;
                zzsVar.zzb(Collections.unmodifiableMap(zzsVar.zzd()));
                zzt zztVarZzc = zzsVar.zzc();
                fileReader.close();
                return zztVarZzc;
            } catch (Throwable th) {
                try {
                    fileReader.close();
                } catch (Throwable unused) {
                }
                throw th;
            }
        } catch (IOException | RuntimeException | XmlPullParserException e) {
            zza.zze("%s can not be parsed, using default. Error: %s", "local_testing_config.xml", e.getMessage());
            return zzt.zza;
        }
    }

    public static /* synthetic */ void zzb(final zzy zzyVar) throws XmlPullParserException, IOException {
        for (int i = 0; i < zzyVar.zzb.getAttributeCount(); i++) {
            if ("defaultErrorCode".equals(zzyVar.zzb.getAttributeName(i))) {
                zzyVar.zzc.zza(com.google.android.play.core.splitinstall.model.zza.zza(zzyVar.zzb.getAttributeValue(i)));
            }
        }
        zzyVar.zze("split-install-error", new zzx() { // from class: com.google.android.play.core.splitinstall.testing.zzw
            @Override // com.google.android.play.core.splitinstall.testing.zzx
            public final void zza() throws XmlPullParserException {
                zzy.zzc(this.zza);
            }
        });
    }

    public static /* synthetic */ void zzc(zzy zzyVar) throws XmlPullParserException {
        String attributeValue = null;
        String attributeValue2 = null;
        for (int i = 0; i < zzyVar.zzb.getAttributeCount(); i++) {
            if ("module".equals(zzyVar.zzb.getAttributeName(i))) {
                attributeValue = zzyVar.zzb.getAttributeValue(i);
            }
            if ("errorCode".equals(zzyVar.zzb.getAttributeName(i))) {
                attributeValue2 = zzyVar.zzb.getAttributeValue(i);
            }
        }
        if (attributeValue == null || attributeValue2 == null) {
            throw new XmlPullParserException(String.format("'%s' element does not contain 'module'/'errorCode' attributes.", "split-install-error"), zzyVar.zzb, null);
        }
        zzyVar.zzc.zzd().put(attributeValue, Integer.valueOf(com.google.android.play.core.splitinstall.model.zza.zza(attributeValue2)));
        while (zzyVar.zzb.next() != 3) {
        }
    }

    private final void zze(String str, zzx zzxVar) throws XmlPullParserException, IOException {
        while (true) {
            int next = this.zzb.next();
            if (next == 3 || next == 1) {
                return;
            }
            if (this.zzb.getEventType() == 2) {
                if (!this.zzb.getName().equals(str)) {
                    throw new XmlPullParserException(String.format("Expected '%s' tag but found '%s'.", str, this.zzb.getName()), this.zzb, null);
                }
                zzxVar.zza();
            }
        }
    }

    public final /* synthetic */ void zzd() throws XmlPullParserException, IOException {
        zze("split-install-errors", new zzx() { // from class: com.google.android.play.core.splitinstall.testing.zzv
            @Override // com.google.android.play.core.splitinstall.testing.zzx
            public final void zza() throws XmlPullParserException, IOException {
                zzy.zzb(this.zza);
            }
        });
    }
}
