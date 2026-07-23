package com.google.android.gms.tasks;

import androidx.annotation.NonNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tasks@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zzb extends CancellationToken {
    private final zzw<Void> zza = new zzw<>();

    @Override // com.google.android.gms.tasks.CancellationToken
    public final boolean isCancellationRequested() {
        return this.zza.isComplete();
    }

    @Override // com.google.android.gms.tasks.CancellationToken
    public final CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
        this.zza.addOnSuccessListener(TaskExecutors.MAIN_THREAD, new zza(onTokenCanceledListener));
        return this;
    }

    public final void zza() {
        this.zza.zze(null);
    }
}
