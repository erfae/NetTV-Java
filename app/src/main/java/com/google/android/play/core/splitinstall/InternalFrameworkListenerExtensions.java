package com.google.android.play.core.splitinstall;

import android.content.Context;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class InternalFrameworkListenerExtensions {
    private InternalFrameworkListenerExtensions() {
    }

    public static void registerFrameworkListener(Context context, SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        zzu.zza(context).zza().zza(splitInstallStateUpdatedListener);
    }

    public static void unregisterFrameworkListener(Context context, SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        zzu.zza(context).zza().zzb(splitInstallStateUpdatedListener);
    }
}
