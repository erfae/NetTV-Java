package com.google.android.play.core.splitinstall;

import android.content.Context;
import androidx.annotation.NonNull;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class SplitInstallManagerFactory {
    private SplitInstallManagerFactory() {
    }

    @NonNull
    public static SplitInstallManager create(@NonNull Context context) {
        return zzu.zza(context).zza();
    }
}
