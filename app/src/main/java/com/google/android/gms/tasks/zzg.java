package com.google.android.gms.tasks;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tasks@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zzg implements Runnable {
    public final /* synthetic */ zzh zza;

    public zzg(zzh zzhVar) {
        this.zza = zzhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zza.zzb) {
            zzh zzhVar = this.zza;
            if (zzhVar.zzc != null) {
                zzhVar.zzc.onCanceled();
            }
        }
    }
}
