package com.google.android.play.core.assetpacks;

import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.play.core.assetpacks.model.AssetPackStorageMethod;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbm extends AssetPackLocation {
    private final int zza;
    private final String zzb;
    private final String zzc;

    public zzbm(int i, @Nullable String str, @Nullable String str2) {
        this.zza = i;
        this.zzb = str;
        this.zzc = str2;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackLocation
    @Nullable
    public final String assetsPath() {
        return this.zzc;
    }

    public final boolean equals(Object obj) {
        String str;
        String str2;
        if (obj == this) {
            return true;
        }
        if (obj instanceof AssetPackLocation) {
            AssetPackLocation assetPackLocation = (AssetPackLocation) obj;
            if (this.zza == assetPackLocation.packStorageMethod() && ((str = this.zzb) != null ? str.equals(assetPackLocation.path()) : assetPackLocation.path() == null) && ((str2 = this.zzc) != null ? str2.equals(assetPackLocation.assetsPath()) : assetPackLocation.assetsPath() == null)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = (this.zza ^ 1000003) * 1000003;
        String str = this.zzb;
        int iHashCode = (i ^ (str == null ? 0 : str.hashCode())) * 1000003;
        String str2 = this.zzc;
        return iHashCode ^ (str2 != null ? str2.hashCode() : 0);
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackLocation
    @AssetPackStorageMethod
    public final int packStorageMethod() {
        return this.zza;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackLocation
    @Nullable
    public final String path() {
        return this.zzb;
    }

    public final String toString() {
        int i = this.zza;
        String str = this.zzb;
        String str2 = this.zzc;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 68 + String.valueOf(str2).length());
        sb.append("AssetPackLocation{packStorageMethod=");
        sb.append(i);
        sb.append(", path=");
        sb.append(str);
        return Insets$$ExternalSyntheticOutline0.m(sb, ", assetsPath=", str2, "}");
    }
}
