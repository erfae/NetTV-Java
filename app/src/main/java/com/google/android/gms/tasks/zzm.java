package com.google.android.gms.tasks;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tasks@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zzm implements Runnable {
    public final /* synthetic */ Task zza;
    public final /* synthetic */ zzn zzb;

    public zzm(zzn zznVar, Task task) {
        this.zzb = zznVar;
        this.zza = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzb.zzb) {
            zzn zznVar = this.zzb;
            if (zznVar.zzc != null) {
                zznVar.zzc.onSuccess(this.zza.getResult());
            }
        }
    }
}
