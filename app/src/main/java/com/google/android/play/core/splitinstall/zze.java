package com.google.android.play.core.splitinstall;

import com.google.android.play.core.internal.zzcq;
import com.google.android.play.core.internal.zzcs;
import java.io.File;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zze implements zzp {
    private final zze zza = this;
    private final zzcs zzb;
    private final zzcs zzc;
    private final zzcs zzd;
    private final zzcs zze;
    private final zzcs zzf;
    private final zzcs zzg;
    private final zzcs zzh;
    private final zzcs zzi;
    private final zzcs zzj;
    private final zzcs zzk;
    private final zzcs zzl;

    public /* synthetic */ zze(zzac zzacVar) {
        zzad zzadVar = new zzad(zzacVar);
        this.zzb = zzadVar;
        zzcs zzcsVarZzc = zzcq.zzc(new zzbd(zzadVar));
        this.zzc = zzcsVarZzc;
        zzcs zzcsVarZzc2 = zzcq.zzc(new zzag(zzacVar));
        this.zzd = zzcsVarZzc2;
        zzcs zzcsVarZzc3 = zzcq.zzc(new zzt(zzadVar));
        this.zze = zzcsVarZzc3;
        zzcs zzcsVarZzc4 = zzcq.zzc(new zzbf(zzadVar));
        this.zzf = zzcsVarZzc4;
        zzcs zzcsVarZzc5 = zzcq.zzc(new zzab(zzcsVarZzc, zzcsVarZzc2, zzcsVarZzc3, zzcsVarZzc4));
        this.zzg = zzcsVarZzc5;
        zzcs zzcsVarZzc6 = zzcq.zzc(new zzaf(zzadVar));
        this.zzh = zzcsVarZzc6;
        zzae zzaeVar = new zzae(zzcsVarZzc6);
        this.zzi = zzaeVar;
        zzcs zzcsVarZzc7 = zzcq.zzc(new com.google.android.play.core.splitinstall.testing.zzr(zzadVar, zzcsVarZzc6, zzcsVarZzc3, zzaeVar));
        this.zzj = zzcsVarZzc7;
        zzcs zzcsVarZzc8 = zzcq.zzc(new zzm(zzcsVarZzc5, zzcsVarZzc7, zzcsVarZzc6));
        this.zzk = zzcsVarZzc8;
        this.zzl = zzcq.zzc(new zzah(zzacVar, zzcsVarZzc8));
    }

    @Override // com.google.android.play.core.splitinstall.zzp
    public final SplitInstallManager zza() {
        return (SplitInstallManager) this.zzl.zza();
    }

    @Override // com.google.android.play.core.splitinstall.zzp
    public final File zzb() {
        return (File) this.zzh.zza();
    }
}
