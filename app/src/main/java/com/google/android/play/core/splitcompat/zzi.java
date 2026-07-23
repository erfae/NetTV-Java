package com.google.android.play.core.splitcompat;

import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzi implements zzk {
    public final /* synthetic */ Set zza;
    public final /* synthetic */ zzs zzb;
    public final /* synthetic */ ZipFile zzc;

    public zzi(Set set, zzs zzsVar, ZipFile zipFile) {
        this.zza = set;
        this.zzb = zzsVar;
        this.zzc = zipFile;
    }

    @Override // com.google.android.play.core.splitcompat.zzk
    public final void zza(zzl zzlVar, File file, boolean z) throws IOException {
        this.zza.add(file);
        if (z) {
            return;
        }
        Log.i("SplitCompat", String.format("NativeLibraryExtractor: split '%s' has native library '%s' that does not exist; extracting from '%s!%s' to '%s'", this.zzb.zzb(), zzlVar.zza, this.zzb.zza().getAbsolutePath(), zzlVar.zzb.getName(), file.getAbsolutePath()));
        ZipFile zipFile = this.zzc;
        ZipEntry zipEntry = zzlVar.zzb;
        byte[] bArr = new byte[4096];
        if (file.exists()) {
            file.delete();
        }
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                zze.zzm(file);
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i <= 0) {
                        fileOutputStream.close();
                        inputStream.close();
                        return;
                    }
                    fileOutputStream.write(bArr, 0, i);
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable unused) {
                }
                throw th;
            }
        } catch (Throwable th2) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable unused2) {
                }
            }
            throw th2;
        }
    }
}
