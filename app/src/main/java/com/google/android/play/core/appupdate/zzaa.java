package com.google.android.play.core.appupdate;

import com.google.android.play.core.internal.zzcq;
import com.google.android.play.core.internal.zzcs;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzaa {
    private final zzaa zza = this;
    private final zzcs zzb;
    private final zzcs zzc;
    private final zzcs zzd;
    private final zzcs zze;
    private final zzcs zzf;
    private final zzcs zzg;

    public /* synthetic */ zzaa(zzh zzhVar) {
        zzj zzjVar = new zzj(zzhVar);
        this.zzb = zzjVar;
        zzcs zzcsVarZzc = zzcq.zzc(new zzt(zzjVar));
        this.zzc = zzcsVarZzc;
        zzcs zzcsVarZzc2 = zzcq.zzc(new zzr(zzjVar, zzcsVarZzc));
        this.zzd = zzcsVarZzc2;
        zzcs zzcsVarZzc3 = zzcq.zzc(new zzc(zzjVar));
        this.zze = zzcsVarZzc3;
        zzcs zzcsVarZzc4 = zzcq.zzc(new zzg(zzcsVarZzc2, zzcsVarZzc3, zzjVar));
        this.zzf = zzcsVarZzc4;
        this.zzg = zzcq.zzc(new zzi(zzcsVarZzc4));
    }

    public final AppUpdateManager zza() {
        return (AppUpdateManager) this.zzg.zza();
    }
}
