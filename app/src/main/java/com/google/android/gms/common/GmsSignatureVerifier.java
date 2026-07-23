package com.google.android.gms.common;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.internal.common.zzag;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
@ShowFirstParty
@KeepForSdk
public class GmsSignatureVerifier {
    private static final zzz zza;
    private static final zzz zzb;

    static {
        zzx zzxVar = new zzx();
        zzxVar.zzd("com.google.android.gms");
        zzxVar.zza(204200000L);
        zzh zzhVar = zzm.zzd;
        zzxVar.zzc(zzag.zzn(zzhVar.zzf(), zzm.zzb.zzf()));
        zzg zzgVar = zzm.zzc;
        zzxVar.zzb(zzag.zzn(zzgVar.zzf(), zzm.zza.zzf()));
        zza = zzxVar.zze();
        zzx zzxVar2 = new zzx();
        zzxVar2.zzd("com.android.vending");
        zzxVar2.zza(82240000L);
        zzxVar2.zzc(zzag.zzm(zzhVar.zzf()));
        zzxVar2.zzb(zzag.zzm(zzgVar.zzf()));
        zzb = zzxVar2.zze();
    }
}
