package com.google.android.play.core.assetpacks;

import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzak extends com.google.android.play.core.internal.zzah {
    public final /* synthetic */ com.google.android.play.core.tasks.zzi zza;
    public final /* synthetic */ zzaw zzb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzak(zzaw zzawVar, com.google.android.play.core.tasks.zzi zziVar, com.google.android.play.core.tasks.zzi zziVar2) {
        super(zziVar);
        this.zzb = zzawVar;
        this.zza = zziVar2;
    }

    @Override // com.google.android.play.core.internal.zzah
    public final void zza() {
        try {
            ((com.google.android.play.core.internal.zzu) this.zzb.zzg.zze()).zzf(this.zzb.zzc, zzaw.zzA(), new zzap(this.zzb, this.zza));
        } catch (RemoteException e) {
            zzaw.zza.zzc(e, "keepAlive", new Object[0]);
        }
    }
}
