package com.google.android.play.core.splitinstall;

import com.google.android.play.core.internal.zzcs;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzab implements zzcs {
    private final zzcs zza;
    private final zzcs zzb;
    private final zzcs zzc;
    private final zzcs zzd;

    public zzab(zzcs zzcsVar, zzcs zzcsVar2, zzcs zzcsVar3, zzcs zzcsVar4) {
        this.zza = zzcsVar;
        this.zzb = zzcsVar2;
        this.zzc = zzcsVar3;
        this.zzd = zzcsVar4;
    }

    @Override // com.google.android.play.core.internal.zzcs
    public final /* bridge */ /* synthetic */ Object zza() {
        return new zzaa((zzbc) this.zza.zza(), (zzx) this.zzb.zza(), (zzs) this.zzc.zza(), (zzbe) this.zzd.zza());
    }
}
