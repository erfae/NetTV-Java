package com.google.android.play.core.appupdate;

import com.google.android.play.core.internal.zzcs;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzg implements zzcs {
    private final zzcs zza;
    private final zzcs zzb;
    private final zzcs zzc;

    public zzg(zzcs zzcsVar, zzcs zzcsVar2, zzcs zzcsVar3) {
        this.zza = zzcsVar;
        this.zzb = zzcsVar2;
        this.zzc = zzcsVar3;
    }

    @Override // com.google.android.play.core.internal.zzcs
    public final /* bridge */ /* synthetic */ Object zza() {
        return new zzf((zzq) this.zza.zza(), (zzb) this.zzb.zza(), ((zzj) this.zzc).zzb());
    }
}
