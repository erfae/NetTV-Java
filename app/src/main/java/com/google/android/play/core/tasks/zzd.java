package com.google.android.play.core.tasks;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzd implements zzg {
    private final Executor zza;
    private final Object zzb = new Object();

    @Nullable
    @GuardedBy("lock")
    private final OnFailureListener zzc;

    public zzd(Executor executor, OnFailureListener onFailureListener) {
        this.zza = executor;
        this.zzc = onFailureListener;
    }

    @Override // com.google.android.play.core.tasks.zzg
    public final void zzc(Task task) {
        if (task.isSuccessful()) {
            return;
        }
        synchronized (this.zzb) {
            if (this.zzc == null) {
                return;
            }
            this.zza.execute(new zzc(this, task));
        }
    }
}
