package com.google.android.play.core.assetpacks;

import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzah extends com.google.android.play.core.internal.zzah {
    public final /* synthetic */ int zza;
    public final /* synthetic */ String zzb;
    public final /* synthetic */ com.google.android.play.core.tasks.zzi zzc;
    public final /* synthetic */ int zzd;
    public final /* synthetic */ zzaw zze;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzah(zzaw zzawVar, com.google.android.play.core.tasks.zzi zziVar, int i, String str, com.google.android.play.core.tasks.zzi zziVar2, int i2) {
        super(zziVar);
        this.zze = zzawVar;
        this.zza = i;
        this.zzb = str;
        this.zzc = zziVar2;
        this.zzd = i2;
    }

    @Override // com.google.android.play.core.internal.zzah
    public final void zza() {
        try {
            ((com.google.android.play.core.internal.zzu) this.zze.zzf.zze()).zzh(this.zze.zzc, zzaw.zzz(this.zza, this.zzb), zzaw.zzA(), new zzar(this.zze, this.zzc, this.zza, this.zzb, this.zzd));
        } catch (RemoteException e) {
            zzaw.zza.zzc(e, "notifyModuleCompleted", new Object[0]);
        }
    }
}
