package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.zzca;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzar extends com.google.android.play.core.internal.zzah {
    public final /* synthetic */ com.google.android.play.core.tasks.zzi zza;
    public final /* synthetic */ zzbc zzb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzar(zzbc zzbcVar, com.google.android.play.core.tasks.zzi zziVar, com.google.android.play.core.tasks.zzi zziVar2) {
        super(zziVar);
        this.zzb = zzbcVar;
        this.zza = zziVar2;
    }

    @Override // com.google.android.play.core.internal.zzah
    public final void zza() {
        try {
            zzca zzcaVar = (zzca) this.zzb.zza.zze();
            zzbc zzbcVar = this.zzb;
            zzcaVar.zzi(zzbcVar.zzd, new zzaz(zzbcVar, this.zza));
        } catch (RemoteException e) {
            zzbc.zzb.zzc(e, "getSessionStates", new Object[0]);
            this.zza.zzd(new RuntimeException(e));
        }
    }
}
