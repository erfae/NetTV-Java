package com.google.android.play.core.assetpacks;

import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzag extends com.google.android.play.core.internal.zzah {
    public final /* synthetic */ int zza;
    public final /* synthetic */ String zzb;
    public final /* synthetic */ String zzc;
    public final /* synthetic */ int zzd;
    public final /* synthetic */ com.google.android.play.core.tasks.zzi zze;
    public final /* synthetic */ zzaw zzf;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzag(zzaw zzawVar, com.google.android.play.core.tasks.zzi zziVar, int i, String str, String str2, int i2, com.google.android.play.core.tasks.zzi zziVar2) {
        super(zziVar);
        this.zzf = zzawVar;
        this.zza = i;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = i2;
        this.zze = zziVar2;
    }

    @Override // com.google.android.play.core.internal.zzah
    public final void zza() {
        try {
            ((com.google.android.play.core.internal.zzu) this.zzf.zzf.zze()).zzg(this.zzf.zzc, zzaw.zzk(this.zza, this.zzb, this.zzc, this.zzd), zzaw.zzA(), new zzaq(this.zzf, this.zze));
        } catch (RemoteException e) {
            zzaw.zza.zzc(e, "notifyChunkTransferred", new Object[0]);
        }
    }
}
