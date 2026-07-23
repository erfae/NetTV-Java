package com.google.android.play.core.assetpacks;

import com.google.android.play.core.assetpacks.model.AssetPackErrorCode;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class AssetPackException extends com.google.android.play.core.tasks.zzj {

    @AssetPackErrorCode
    private final int zza;

    public AssetPackException(@AssetPackErrorCode int i) {
        super(String.format("Asset Pack Download Error(%d): %s", Integer.valueOf(i), com.google.android.play.core.assetpacks.model.zza.zza(i)));
        if (i == 0) {
            throw new IllegalArgumentException("errorCode should not be 0.");
        }
        this.zza = i;
    }

    @Override // com.google.android.play.core.tasks.zzj
    @AssetPackErrorCode
    public int getErrorCode() {
        return this.zza;
    }
}
