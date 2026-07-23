package com.google.android.play.core.appupdate;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.internal.zzag;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzp extends zzn {
    public final /* synthetic */ zzq zzd;
    private final String zze;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzp(zzq zzqVar, com.google.android.play.core.tasks.zzi zziVar, String str) {
        super(zzqVar, new zzag("OnRequestInstallCallback"), zziVar);
        this.zzd = zzqVar;
        this.zze = str;
    }

    @Override // com.google.android.play.core.appupdate.zzn, com.google.android.play.core.internal.zzr
    public final void zzc(Bundle bundle) throws RemoteException {
        super.zzc(bundle);
        int i = zzq.$r8$clinit;
        if (bundle.getInt("error.code", -2) != 0) {
            this.zzb.zzd(new InstallException(bundle.getInt("error.code", -2)));
            return;
        }
        this.zzb.zze(AppUpdateInfo.zzb(this.zze, bundle.getInt("version.code", -1), bundle.getInt("update.availability"), bundle.getInt("install.status", 0), bundle.getInt("client.version.staleness", -1) == -1 ? null : Integer.valueOf(bundle.getInt("client.version.staleness")), bundle.getInt("in.app.update.priority", 0), bundle.getLong("bytes.downloaded"), bundle.getLong("total.bytes.to.download"), bundle.getLong("additional.size.required"), this.zzd.zzf.zza(), (PendingIntent) bundle.getParcelable("blocking.intent"), (PendingIntent) bundle.getParcelable("nonblocking.intent"), (PendingIntent) bundle.getParcelable("blocking.destructive.intent"), (PendingIntent) bundle.getParcelable("nonblocking.destructive.intent")));
    }
}
