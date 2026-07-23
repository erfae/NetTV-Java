package com.google.android.play.core.internal;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzal extends zzah {
    public final /* synthetic */ zzas zza;

    public zzal(zzas zzasVar) {
        this.zza = zzasVar;
    }

    @Override // com.google.android.play.core.internal.zzah
    public final void zza() {
        zzas zzasVar = this.zza;
        if (zzasVar.zzo != null) {
            zzasVar.zzc.zzd("Unbind from service.", new Object[0]);
            zzas zzasVar2 = this.zza;
            zzasVar2.zzb.unbindService(zzasVar2.zzn);
            this.zza.zzh = false;
            this.zza.zzo = null;
            this.zza.zzn = null;
        }
        this.zza.zzu();
    }
}
