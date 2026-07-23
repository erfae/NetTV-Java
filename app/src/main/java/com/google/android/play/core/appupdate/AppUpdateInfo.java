package com.google.android.play.core.appupdate;

import android.app.PendingIntent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class AppUpdateInfo {

    @NonNull
    private final String zza;
    private final int zzb;

    @UpdateAvailability
    private final int zzc;

    @InstallStatus
    private final int zzd;

    @Nullable
    private final Integer zze;
    private final int zzf;
    private final long zzg;
    private final long zzh;
    private final long zzi;
    private final long zzj;

    @Nullable
    private final PendingIntent zzk;

    @Nullable
    private final PendingIntent zzl;

    @Nullable
    private final PendingIntent zzm;

    @Nullable
    private final PendingIntent zzn;
    private boolean zzo = false;

    private AppUpdateInfo(@NonNull String str, int i, @UpdateAvailability int i2, @InstallStatus int i3, @Nullable Integer num, int i4, long j, long j2, long j3, long j4, @Nullable PendingIntent pendingIntent, @Nullable PendingIntent pendingIntent2, @Nullable PendingIntent pendingIntent3, @Nullable PendingIntent pendingIntent4) {
        this.zza = str;
        this.zzb = i;
        this.zzc = i2;
        this.zzd = i3;
        this.zze = num;
        this.zzf = i4;
        this.zzg = j;
        this.zzh = j2;
        this.zzi = j3;
        this.zzj = j4;
        this.zzk = pendingIntent;
        this.zzl = pendingIntent2;
        this.zzm = pendingIntent3;
        this.zzn = pendingIntent4;
    }

    public static AppUpdateInfo zzb(@NonNull String str, int i, @UpdateAvailability int i2, @InstallStatus int i3, @Nullable Integer num, int i4, long j, long j2, long j3, long j4, @Nullable PendingIntent pendingIntent, @Nullable PendingIntent pendingIntent2, @Nullable PendingIntent pendingIntent3, @Nullable PendingIntent pendingIntent4) {
        return new AppUpdateInfo(str, i, i2, i3, num, i4, j, j2, j3, j4, pendingIntent, pendingIntent2, pendingIntent3, pendingIntent4);
    }

    private final boolean zze(AppUpdateOptions appUpdateOptions) {
        return appUpdateOptions.allowAssetPackDeletion() && this.zzi <= this.zzj;
    }

    public int availableVersionCode() {
        return this.zzb;
    }

    public long bytesDownloaded() {
        return this.zzg;
    }

    @Nullable
    public Integer clientVersionStalenessDays() {
        return this.zze;
    }

    @InstallStatus
    public int installStatus() {
        return this.zzd;
    }

    public boolean isUpdateTypeAllowed(@AppUpdateType int i) {
        return zza(AppUpdateOptions.defaultOptions(i)) != null;
    }

    @NonNull
    public String packageName() {
        return this.zza;
    }

    public long totalBytesToDownload() {
        return this.zzh;
    }

    @UpdateAvailability
    public int updateAvailability() {
        return this.zzc;
    }

    public int updatePriority() {
        return this.zzf;
    }

    @Nullable
    public final PendingIntent zza(AppUpdateOptions appUpdateOptions) {
        if (appUpdateOptions.appUpdateType() == 0) {
            PendingIntent pendingIntent = this.zzl;
            if (pendingIntent != null) {
                return pendingIntent;
            }
            if (zze(appUpdateOptions)) {
                return this.zzn;
            }
            return null;
        }
        if (appUpdateOptions.appUpdateType() == 1) {
            PendingIntent pendingIntent2 = this.zzk;
            if (pendingIntent2 != null) {
                return pendingIntent2;
            }
            if (zze(appUpdateOptions)) {
                return this.zzm;
            }
        }
        return null;
    }

    public final void zzc() {
        this.zzo = true;
    }

    public final boolean zzd() {
        return this.zzo;
    }

    public boolean isUpdateTypeAllowed(@NonNull AppUpdateOptions appUpdateOptions) {
        return zza(appUpdateOptions) != null;
    }
}
