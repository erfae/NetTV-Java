package com.google.android.play.core.assetpacks;

import android.content.Context;
import androidx.annotation.NonNull;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class AssetPackManagerFactory {
    private AssetPackManagerFactory() {
    }

    @NonNull
    public static synchronized AssetPackManager getInstance(@NonNull Context context) {
        return zzd.zza(context).zza();
    }
}
