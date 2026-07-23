package com.google.android.play.core.tasks;

import androidx.annotation.GuardedBy;
import java.util.ArrayDeque;
import java.util.Queue;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzh {
    private final Object zza = new Object();

    @GuardedBy("lock")
    private Queue zzb;

    @GuardedBy("lock")
    private boolean zzc;

    public final void zza(zzg zzgVar) {
        synchronized (this.zza) {
            if (this.zzb == null) {
                this.zzb = new ArrayDeque();
            }
            this.zzb.add(zzgVar);
        }
    }

    public final void zzb(Task task) {
        zzg zzgVar;
        synchronized (this.zza) {
            if (this.zzb != null && !this.zzc) {
                this.zzc = true;
                while (true) {
                    synchronized (this.zza) {
                        zzgVar = (zzg) this.zzb.poll();
                        if (zzgVar == null) {
                            this.zzc = false;
                            return;
                        }
                    }
                    zzgVar.zzc(task);
                }
            }
        }
    }
}
