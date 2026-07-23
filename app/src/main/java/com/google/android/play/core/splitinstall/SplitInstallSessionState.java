package com.google.android.play.core.splitinstall;

import android.app.PendingIntent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class SplitInstallSessionState {
    public static SplitInstallSessionState create(int i, @SplitInstallSessionStatus int i2, @SplitInstallErrorCode int i3, long j, long j2, List<String> list, List<String> list2) {
        if (i2 != 8) {
            return new zza(i, i2, i3, j, j2, list, list2, null, null);
        }
        throw new IllegalArgumentException("REQUIRES_USER_CONFIRMATION state not supported.");
    }

    public static SplitInstallSessionState zzd(Bundle bundle) {
        return new zza(bundle.getInt("session_id"), bundle.getInt(NotificationCompat.CATEGORY_STATUS), bundle.getInt("error_code"), bundle.getLong("bytes_downloaded"), bundle.getLong("total_bytes_to_download"), bundle.getStringArrayList("module_names"), bundle.getStringArrayList("languages"), (PendingIntent) bundle.getParcelable("user_confirmation_intent"), bundle.getParcelableArrayList("split_file_intents"));
    }

    public abstract long bytesDownloaded();

    @SplitInstallErrorCode
    public abstract int errorCode();

    public boolean hasTerminalStatus() {
        int iStatus = status();
        return iStatus == 0 || iStatus == 5 || iStatus == 6 || iStatus == 7;
    }

    @NonNull
    public List<String> languages() {
        return zza() != null ? new ArrayList(zza()) : new ArrayList();
    }

    @NonNull
    public List<String> moduleNames() {
        return zzb() != null ? new ArrayList(zzb()) : new ArrayList();
    }

    @Nullable
    @Deprecated
    public abstract PendingIntent resolutionIntent();

    public abstract int sessionId();

    @SplitInstallSessionStatus
    public abstract int status();

    public abstract long totalBytesToDownload();

    @Nullable
    public abstract List zza();

    @Nullable
    public abstract List zzb();

    @Nullable
    public abstract List zzc();
}
