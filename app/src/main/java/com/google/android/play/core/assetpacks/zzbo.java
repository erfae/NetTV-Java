package com.google.android.play.core.assetpacks;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbo extends AssetPackStates {
    private final long zza;
    private final Map zzb;

    public zzbo(long j, Map map) {
        this.zza = j;
        this.zzb = map;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AssetPackStates) {
            AssetPackStates assetPackStates = (AssetPackStates) obj;
            if (this.zza == assetPackStates.totalBytes() && this.zzb.equals(assetPackStates.packStates())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        long j = this.zza;
        return this.zzb.hashCode() ^ ((((int) (j ^ (j >>> 32))) ^ 1000003) * 1000003);
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackStates
    public final Map<String, AssetPackState> packStates() {
        return this.zzb;
    }

    public final String toString() {
        long j = this.zza;
        String string = this.zzb.toString();
        StringBuilder sb = new StringBuilder(string.length() + 61);
        sb.append("AssetPackStates{totalBytes=");
        sb.append(j);
        sb.append(", packStates=");
        return Insets$$ExternalSyntheticOutline0.m(sb, string, "}");
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackStates
    public final long totalBytes() {
        return this.zza;
    }
}
