package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.android.play.core.assetpacks.model.AssetPackErrorCode;
import com.google.android.play.core.assetpacks.model.AssetPackStatus;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AssetPackState {
    public static AssetPackState zzb(@NonNull String str, @AssetPackStatus int i, @AssetPackErrorCode int i2, long j, long j2, double d, int i3, String str2, String str3) {
        return new zzbn(str, i, i2, j, j2, (int) Math.rint(100.0d * d), i3, str2, str3);
    }

    public static AssetPackState zzc(Bundle bundle, String str, zzco zzcoVar, zzeb zzebVar, zzbe zzbeVar) {
        int iZza = zzbeVar.zza(bundle.getInt(com.google.android.play.core.assetpacks.model.zzb.zza(NotificationCompat.CATEGORY_STATUS, str)), str);
        int i = bundle.getInt(com.google.android.play.core.assetpacks.model.zzb.zza("error_code", str));
        long j = bundle.getLong(com.google.android.play.core.assetpacks.model.zzb.zza("bytes_downloaded", str));
        long j2 = bundle.getLong(com.google.android.play.core.assetpacks.model.zzb.zza("total_bytes_to_download", str));
        double dZza = zzcoVar.zza(str);
        long j3 = bundle.getLong(com.google.android.play.core.assetpacks.model.zzb.zza("pack_version", str));
        long j4 = bundle.getLong(com.google.android.play.core.assetpacks.model.zzb.zza("pack_base_version", str));
        int i2 = 1;
        int i3 = 4;
        if (iZza != 4) {
            i3 = iZza;
        } else if (j4 != 0 && j4 != j3) {
            i2 = 2;
        }
        return zzb(str, i3, i, j, j2, dZza, i2, bundle.getString(com.google.android.play.core.assetpacks.model.zzb.zza("pack_version_tag", str), String.valueOf(bundle.getInt("app_version_code"))), zzebVar.zza(str));
    }

    public abstract long bytesDownloaded();

    @AssetPackErrorCode
    public abstract int errorCode();

    public abstract String name();

    @AssetPackStatus
    public abstract int status();

    public abstract long totalBytesToDownload();

    public abstract int transferProgressPercentage();

    public abstract int zza();

    public abstract String zzd();

    public abstract String zze();
}
