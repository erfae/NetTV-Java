package com.google.android.gms.tasks;

import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tasks@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zzk implements Runnable {
    public final /* synthetic */ Task zza;
    public final /* synthetic */ zzl zzb;

    public zzk(zzl zzlVar, Task task) {
        this.zzb = zzlVar;
        this.zza = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzb.zzb) {
            zzl zzlVar = this.zzb;
            if (zzlVar.zzc != null) {
                zzlVar.zzc.onFailure((Exception) Preconditions.checkNotNull(this.zza.getException()));
            }
        }
    }
}
