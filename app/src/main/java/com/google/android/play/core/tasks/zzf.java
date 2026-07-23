package com.google.android.play.core.tasks;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzf implements zzg {
    private final Executor zza;
    private final Object zzb = new Object();

    @Nullable
    @GuardedBy("lock")
    private final OnSuccessListener zzc;

    public zzf(Executor executor, OnSuccessListener onSuccessListener) {
        this.zza = executor;
        this.zzc = onSuccessListener;
    }

    @Override // com.google.android.play.core.tasks.zzg
    public final void zzc(Task task) {
        if (task.isSuccessful()) {
            synchronized (this.zzb) {
                if (this.zzc == null) {
                    return;
                }
                this.zza.execute(new zze(this, task));
            }
        }
    }
}
