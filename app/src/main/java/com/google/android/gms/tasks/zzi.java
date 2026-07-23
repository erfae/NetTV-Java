package com.google.android.gms.tasks;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tasks@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zzi implements Runnable {
    public final /* synthetic */ Task zza;
    public final /* synthetic */ zzj zzb;

    public zzi(zzj zzjVar, Task task) {
        this.zzb = zzjVar;
        this.zza = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzb.zzb) {
            zzj zzjVar = this.zzb;
            if (zzjVar.zzc != null) {
                zzjVar.zzc.onComplete(this.zza);
            }
        }
    }
}
