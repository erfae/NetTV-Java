package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.zzca;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzas extends com.google.android.play.core.internal.zzah {
    public final /* synthetic */ int zza;
    public final /* synthetic */ com.google.android.play.core.tasks.zzi zzb;
    public final /* synthetic */ zzbc zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzas(zzbc zzbcVar, com.google.android.play.core.tasks.zzi zziVar, int i, com.google.android.play.core.tasks.zzi zziVar2) {
        super(zziVar);
        this.zzc = zzbcVar;
        this.zza = i;
        this.zzb = zziVar2;
    }

    @Override // com.google.android.play.core.internal.zzah
    public final void zza() {
        try {
            ((zzca) this.zzc.zza.zze()).zzc(this.zzc.zzd, this.zza, zzbc.zza(), new zzat(this.zzc, this.zzb));
        } catch (RemoteException e) {
            zzbc.zzb.zzc(e, "cancelInstall(%d)", Integer.valueOf(this.zza));
            this.zzb.zzd(new RuntimeException(e));
        }
    }
}
