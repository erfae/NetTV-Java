package com.google.android.play.core.tasks;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzc implements Runnable {
    public final /* synthetic */ Task zza;
    public final /* synthetic */ zzd zzb;

    public zzc(zzd zzdVar, Task task) {
        this.zzb = zzdVar;
        this.zza = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzb.zzb) {
            zzd zzdVar = this.zzb;
            if (zzdVar.zzc != null) {
                zzdVar.zzc.onFailure(this.zza.getException());
            }
        }
    }
}
