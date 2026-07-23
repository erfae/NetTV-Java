package com.google.android.play.core.assetpacks;

import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzab extends com.google.android.play.core.internal.zzah {
    public final /* synthetic */ String zza;
    public final /* synthetic */ com.google.android.play.core.tasks.zzi zzb;
    public final /* synthetic */ zzaw zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzab(zzaw zzawVar, com.google.android.play.core.tasks.zzi zziVar, String str, com.google.android.play.core.tasks.zzi zziVar2) {
        super(zziVar);
        this.zzc = zzawVar;
        this.zza = str;
        this.zzb = zziVar2;
    }

    @Override // com.google.android.play.core.internal.zzah
    public final void zza() {
        try {
            ((com.google.android.play.core.internal.zzu) this.zzc.zzf.zze()).zzj(this.zzc.zzc, zzaw.zzz(0, this.zza), zzaw.zzA(), new zzat(this.zzc, this.zzb));
        } catch (RemoteException e) {
            zzaw.zza.zzc(e, "removePack(%s)", this.zza);
        }
    }
}
