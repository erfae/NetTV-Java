package com.google.android.play.core.internal;

import androidx.annotation.Nullable;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzah implements Runnable {

    @Nullable
    private final com.google.android.play.core.tasks.zzi zza;

    public zzah() {
        this.zza = null;
    }

    public zzah(@Nullable com.google.android.play.core.tasks.zzi zziVar) {
        this.zza = zziVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            zza();
        } catch (Exception e) {
            zzc(e);
        }
    }

    public abstract void zza();

    @Nullable
    public final com.google.android.play.core.tasks.zzi zzb() {
        return this.zza;
    }

    public final void zzc(Exception exc) {
        com.google.android.play.core.tasks.zzi zziVar = this.zza;
        if (zziVar != null) {
            zziVar.zzd(exc);
        }
    }
}
