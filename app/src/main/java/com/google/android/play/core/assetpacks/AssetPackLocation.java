package com.google.android.play.core.assetpacks;

import androidx.annotation.Nullable;
import com.google.android.play.core.assetpacks.model.AssetPackStorageMethod;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AssetPackLocation {
    private static final AssetPackLocation zza = new zzbm(1, null, null);

    public static AssetPackLocation zza() {
        return zza;
    }

    @Nullable
    public abstract String assetsPath();

    @AssetPackStorageMethod
    public abstract int packStorageMethod();

    @Nullable
    public abstract String path();
}
