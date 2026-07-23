package com.google.android.play.core.tasks;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zza implements Runnable {
    public final /* synthetic */ Task zza;
    public final /* synthetic */ zzb zzb;

    public zza(zzb zzbVar, Task task) {
        this.zzb = zzbVar;
        this.zza = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzb.zzb) {
            zzb zzbVar = this.zzb;
            if (zzbVar.zzc != null) {
                zzbVar.zzc.onComplete(this.zza);
            }
        }
    }
}
