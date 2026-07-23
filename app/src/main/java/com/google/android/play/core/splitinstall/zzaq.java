package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.zzca;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzaq extends com.google.android.play.core.internal.zzah {
    public final /* synthetic */ int zza;
    public final /* synthetic */ com.google.android.play.core.tasks.zzi zzb;
    public final /* synthetic */ zzbc zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzaq(zzbc zzbcVar, com.google.android.play.core.tasks.zzi zziVar, int i, com.google.android.play.core.tasks.zzi zziVar2) {
        super(zziVar);
        this.zzc = zzbcVar;
        this.zza = i;
        this.zzb = zziVar2;
    }

    @Override // com.google.android.play.core.internal.zzah
    public final void zza() {
        try {
            zzca zzcaVar = (zzca) this.zzc.zza.zze();
            zzbc zzbcVar = this.zzc;
            zzcaVar.zzh(zzbcVar.zzd, this.zza, new zzay(zzbcVar, this.zzb));
        } catch (RemoteException e) {
            zzbc.zzb.zzc(e, "getSessionState(%d)", Integer.valueOf(this.zza));
            this.zzb.zzd(new RuntimeException(e));
        }
    }
}
