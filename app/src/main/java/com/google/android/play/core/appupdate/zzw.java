package com.google.android.play.core.appupdate;

import com.google.android.play.core.install.model.AppUpdateType;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzw extends AppUpdateOptions {
    private final int zza;
    private final boolean zzb;

    public /* synthetic */ zzw(int i, boolean z) {
        this.zza = i;
        this.zzb = z;
    }

    @Override // com.google.android.play.core.appupdate.AppUpdateOptions
    public final boolean allowAssetPackDeletion() {
        return this.zzb;
    }

    @Override // com.google.android.play.core.appupdate.AppUpdateOptions
    @AppUpdateType
    public final int appUpdateType() {
        return this.zza;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AppUpdateOptions) {
            AppUpdateOptions appUpdateOptions = (AppUpdateOptions) obj;
            if (this.zza == appUpdateOptions.appUpdateType() && this.zzb == appUpdateOptions.allowAssetPackDeletion()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((this.zza ^ 1000003) * 1000003) ^ (true != this.zzb ? 1237 : 1231);
    }

    public final String toString() {
        int i = this.zza;
        boolean z = this.zzb;
        StringBuilder sb = new StringBuilder(73);
        sb.append("AppUpdateOptions{appUpdateType=");
        sb.append(i);
        sb.append(", allowAssetPackDeletion=");
        sb.append(z);
        sb.append("}");
        return sb.toString();
    }
}
