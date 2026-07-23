package com.google.android.play.core.assetpacks;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzer {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("VerifySliceTaskHandler");
    private final zzbh zzb;

    public zzer(zzbh zzbhVar) {
        this.zzb = zzbhVar;
    }

    private final void zzb(zzeq zzeqVar, File file) {
        try {
            File fileZzo = this.zzb.zzo(zzeqVar.zzl, zzeqVar.zza, zzeqVar.zzb, zzeqVar.zzc);
            if (!fileZzo.exists()) {
                throw new zzck(String.format("Cannot find metadata files for slice %s.", zzeqVar.zzc), zzeqVar.zzk);
            }
            try {
                if (!zzdq.zza(zzep.zza(file, fileZzo)).equals(zzeqVar.zzd)) {
                    throw new zzck(String.format("Verification failed for slice %s.", zzeqVar.zzc), zzeqVar.zzk);
                }
                zza.zzd("Verification of slice %s of pack %s successful.", zzeqVar.zzc, zzeqVar.zzl);
            } catch (IOException e) {
                throw new zzck(String.format("Could not digest file during verification for slice %s.", zzeqVar.zzc), e, zzeqVar.zzk);
            } catch (NoSuchAlgorithmException e2) {
                throw new zzck("SHA256 algorithm not supported.", e2, zzeqVar.zzk);
            }
        } catch (IOException e3) {
            throw new zzck(String.format("Could not reconstruct slice archive during verification for slice %s.", zzeqVar.zzc), e3, zzeqVar.zzk);
        }
    }

    public final void zza(zzeq zzeqVar) {
        File fileZzp = this.zzb.zzp(zzeqVar.zzl, zzeqVar.zza, zzeqVar.zzb, zzeqVar.zzc);
        if (!fileZzp.exists()) {
            throw new zzck(String.format("Cannot find unverified files for slice %s.", zzeqVar.zzc), zzeqVar.zzk);
        }
        zzb(zzeqVar, fileZzp);
        File fileZzq = this.zzb.zzq(zzeqVar.zzl, zzeqVar.zza, zzeqVar.zzb, zzeqVar.zzc);
        if (!fileZzq.exists()) {
            fileZzq.mkdirs();
        }
        if (!fileZzp.renameTo(fileZzq)) {
            throw new zzck(String.format("Failed to move slice %s after verification.", zzeqVar.zzc), zzeqVar.zzk);
        }
    }
}
