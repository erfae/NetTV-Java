package com.google.android.play.core.splitinstall;

import androidx.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzr {
    private static final AtomicReference zza = new AtomicReference(null);

    @Nullable
    public static zzq zza() {
        return (zzq) zza.get();
    }

    public static void zzb(zzq zzqVar) {
        zza.compareAndSet(null, zzqVar);
    }
}
