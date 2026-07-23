package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tasks@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zzd<TResult, TContinuationResult> implements zzq<TResult> {
    private final Executor zza;
    private final Continuation<TResult, TContinuationResult> zzb;
    private final zzw<TContinuationResult> zzc;

    public zzd(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzw<TContinuationResult> zzwVar) {
        this.zza = executor;
        this.zzb = continuation;
        this.zzc = zzwVar;
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void zzc() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void zzd(@NonNull Task<TResult> task) {
        this.zza.execute(new zzc(this, task));
    }
}
