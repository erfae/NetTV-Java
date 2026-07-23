package com.google.android.play.core.tasks;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzb implements zzg {
    private final Executor zza;
    private final Object zzb = new Object();

    @Nullable
    @GuardedBy("lock")
    private final OnCompleteListener zzc;

    public zzb(Executor executor, OnCompleteListener onCompleteListener) {
        this.zza = executor;
        this.zzc = onCompleteListener;
    }

    @Override // com.google.android.play.core.tasks.zzg
    public final void zzc(Task task) {
        synchronized (this.zzb) {
            if (this.zzc == null) {
                return;
            }
            this.zza.execute(new zza(this, task));
        }
    }
}
