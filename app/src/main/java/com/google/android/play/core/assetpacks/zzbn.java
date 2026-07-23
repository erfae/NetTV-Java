package com.google.android.play.core.assetpacks;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.play.core.assetpacks.model.AssetPackErrorCode;
import com.google.android.play.core.assetpacks.model.AssetPackStatus;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbn extends AssetPackState {
    private final String zza;
    private final int zzb;
    private final int zzc;
    private final long zzd;
    private final long zze;
    private final int zzf;
    private final int zzg;
    private final String zzh;
    private final String zzi;

    public zzbn(String str, int i, int i2, long j, long j2, int i3, int i4, String str2, String str3) {
        Objects.requireNonNull(str, "Null name");
        this.zza = str;
        this.zzb = i;
        this.zzc = i2;
        this.zzd = j;
        this.zze = j2;
        this.zzf = i3;
        this.zzg = i4;
        Objects.requireNonNull(str2, "Null availableVersionTag");
        this.zzh = str2;
        Objects.requireNonNull(str3, "Null installedVersionTag");
        this.zzi = str3;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackState
    public final long bytesDownloaded() {
        return this.zzd;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AssetPackState) {
            AssetPackState assetPackState = (AssetPackState) obj;
            if (this.zza.equals(assetPackState.name()) && this.zzb == assetPackState.status() && this.zzc == assetPackState.errorCode() && this.zzd == assetPackState.bytesDownloaded() && this.zze == assetPackState.totalBytesToDownload() && this.zzf == assetPackState.transferProgressPercentage() && this.zzg == assetPackState.zza() && this.zzh.equals(assetPackState.zzd()) && this.zzi.equals(assetPackState.zze())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackState
    @AssetPackErrorCode
    public final int errorCode() {
        return this.zzc;
    }

    public final int hashCode() {
        int iHashCode = this.zza.hashCode();
        int i = this.zzb;
        int i2 = this.zzc;
        long j = this.zzd;
        long j2 = this.zze;
        return ((((((((((((((((iHashCode ^ 1000003) * 1000003) ^ i) * 1000003) ^ i2) * 1000003) ^ ((int) ((j >>> 32) ^ j))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003) ^ this.zzf) * 1000003) ^ this.zzg) * 1000003) ^ this.zzh.hashCode()) * 1000003) ^ this.zzi.hashCode();
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackState
    public final String name() {
        return this.zza;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackState
    @AssetPackStatus
    public final int status() {
        return this.zzb;
    }

    public final String toString() {
        String str = this.zza;
        int i = this.zzb;
        int i2 = this.zzc;
        long j = this.zzd;
        long j2 = this.zze;
        int i3 = this.zzf;
        int i4 = this.zzg;
        String str2 = this.zzh;
        String str3 = this.zzi;
        StringBuilder sb = new StringBuilder(str3.length() + str2.length() + str.length() + 261);
        sb.append("AssetPackState{name=");
        sb.append(str);
        sb.append(", status=");
        sb.append(i);
        sb.append(", errorCode=");
        sb.append(i2);
        sb.append(", bytesDownloaded=");
        sb.append(j);
        sb.append(", totalBytesToDownload=");
        sb.append(j2);
        sb.append(", transferProgressPercentage=");
        sb.append(i3);
        sb.append(", updateAvailability=");
        sb.append(i4);
        sb.append(", availableVersionTag=");
        return Insets$$ExternalSyntheticOutline0.m(sb, str2, ", installedVersionTag=", str3, "}");
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackState
    public final long totalBytesToDownload() {
        return this.zze;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackState
    public final int transferProgressPercentage() {
        return this.zzf;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackState
    public final int zza() {
        return this.zzg;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackState
    public final String zzd() {
        return this.zzh;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackState
    public final String zze() {
        return this.zzi;
    }
}
