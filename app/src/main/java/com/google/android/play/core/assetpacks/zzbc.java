package com.google.android.play.core.assetpacks;

import android.content.Context;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzbc implements com.google.android.play.core.internal.zzcs {
    private final com.google.android.play.core.internal.zzcs zza;
    private final com.google.android.play.core.internal.zzcs zzb;
    private final com.google.android.play.core.internal.zzcs zzc;
    private final com.google.android.play.core.internal.zzcs zzd;
    private final com.google.android.play.core.internal.zzcs zze;
    private final com.google.android.play.core.internal.zzcs zzf;
    private final com.google.android.play.core.internal.zzcs zzg;
    private final com.google.android.play.core.internal.zzcs zzh;
    private final com.google.android.play.core.internal.zzcs zzi;

    public zzbc(com.google.android.play.core.internal.zzcs zzcsVar, com.google.android.play.core.internal.zzcs zzcsVar2, com.google.android.play.core.internal.zzcs zzcsVar3, com.google.android.play.core.internal.zzcs zzcsVar4, com.google.android.play.core.internal.zzcs zzcsVar5, com.google.android.play.core.internal.zzcs zzcsVar6, com.google.android.play.core.internal.zzcs zzcsVar7, com.google.android.play.core.internal.zzcs zzcsVar8, com.google.android.play.core.internal.zzcs zzcsVar9) {
        this.zza = zzcsVar;
        this.zzb = zzcsVar2;
        this.zzc = zzcsVar3;
        this.zzd = zzcsVar4;
        this.zze = zzcsVar5;
        this.zzf = zzcsVar6;
        this.zzg = zzcsVar7;
        this.zzh = zzcsVar8;
        this.zzi = zzcsVar9;
    }

    @Override // com.google.android.play.core.internal.zzcs
    public final /* bridge */ /* synthetic */ Object zza() {
        Context contextZzb = ((zzu) this.zza).zzb();
        Object objZza = this.zzb.zza();
        Object objZza2 = this.zzc.zza();
        com.google.android.play.core.internal.zzco zzcoVarZzb = com.google.android.play.core.internal.zzcq.zzb(this.zzd);
        Object objZza3 = this.zze.zza();
        return new zzbb(contextZzb, (zzde) objZza, (zzcl) objZza2, zzcoVarZzb, (zzco) objZza3, (zzbx) this.zzf.zza(), com.google.android.play.core.internal.zzcq.zzb(this.zzg), com.google.android.play.core.internal.zzcq.zzb(this.zzh), (zzeb) this.zzi.zza());
    }
}
