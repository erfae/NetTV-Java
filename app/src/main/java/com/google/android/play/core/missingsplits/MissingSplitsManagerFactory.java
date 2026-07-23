package com.google.android.play.core.missingsplits;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public final class MissingSplitsManagerFactory {
    private static final AtomicReference zza = new AtomicReference(null);

    private MissingSplitsManagerFactory() {
    }

    @NonNull
    @Deprecated
    public static MissingSplitsManager create(@NonNull Context context) {
        return new zzb(context, Runtime.getRuntime(), new zza(context, context.getPackageManager()), zza);
    }
}
