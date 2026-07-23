package com.google.android.play.core.appupdate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.common.PlayCoreDialogWrapperActivity;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzf implements AppUpdateManager {
    private final zzq zza;
    private final zzb zzb;
    private final Context zzc;
    private final Handler zzd = new Handler(Looper.getMainLooper());

    public zzf(zzq zzqVar, zzb zzbVar, Context context) {
        this.zza = zzqVar;
        this.zzb = zzbVar;
        this.zzc = context;
    }

    @Override // com.google.android.play.core.appupdate.AppUpdateManager
    public final Task<Void> completeUpdate() {
        return this.zza.zzf(this.zzc.getPackageName());
    }

    @Override // com.google.android.play.core.appupdate.AppUpdateManager
    public final Task<AppUpdateInfo> getAppUpdateInfo() {
        return this.zza.zzg(this.zzc.getPackageName());
    }

    @Override // com.google.android.play.core.appupdate.AppUpdateManager
    public final synchronized void registerListener(InstallStateUpdatedListener installStateUpdatedListener) {
        this.zzb.zzf(installStateUpdatedListener);
    }

    @Override // com.google.android.play.core.appupdate.AppUpdateManager
    public final Task<Integer> startUpdateFlow(AppUpdateInfo appUpdateInfo, Activity activity, AppUpdateOptions appUpdateOptions) {
        if (appUpdateInfo == null || activity == null || appUpdateOptions == null || appUpdateInfo.zzd()) {
            return Tasks.zza(new InstallException(-4));
        }
        if (!appUpdateInfo.isUpdateTypeAllowed(appUpdateOptions)) {
            return Tasks.zza(new InstallException(-6));
        }
        appUpdateInfo.zzc();
        Intent intent = new Intent(activity, (Class<?>) PlayCoreDialogWrapperActivity.class);
        intent.putExtra("confirmation_intent", appUpdateInfo.zza(appUpdateOptions));
        com.google.android.play.core.tasks.zzi zziVar = new com.google.android.play.core.tasks.zzi();
        intent.putExtra("result_receiver", new zzd(this.zzd, zziVar));
        activity.startActivity(intent);
        return zziVar.zza();
    }

    @Override // com.google.android.play.core.appupdate.AppUpdateManager
    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, @AppUpdateType int i, Activity activity, int i2) throws IntentSender.SendIntentException {
        AppUpdateOptions appUpdateOptionsDefaultOptions = AppUpdateOptions.defaultOptions(i);
        if (activity == null) {
            return false;
        }
        return startUpdateFlowForResult(appUpdateInfo, new zze(activity), appUpdateOptionsDefaultOptions, i2);
    }

    @Override // com.google.android.play.core.appupdate.AppUpdateManager
    public final synchronized void unregisterListener(InstallStateUpdatedListener installStateUpdatedListener) {
        this.zzb.zzh(installStateUpdatedListener);
    }

    @Override // com.google.android.play.core.appupdate.AppUpdateManager
    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, @AppUpdateType int i, IntentSenderForResultStarter intentSenderForResultStarter, int i2) throws IntentSender.SendIntentException {
        return startUpdateFlowForResult(appUpdateInfo, intentSenderForResultStarter, AppUpdateOptions.defaultOptions(i), i2);
    }

    @Override // com.google.android.play.core.appupdate.AppUpdateManager
    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, Activity activity, AppUpdateOptions appUpdateOptions, int i) throws IntentSender.SendIntentException {
        if (activity == null) {
            return false;
        }
        return startUpdateFlowForResult(appUpdateInfo, new zze(activity), appUpdateOptions, i);
    }

    @Override // com.google.android.play.core.appupdate.AppUpdateManager
    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, IntentSenderForResultStarter intentSenderForResultStarter, AppUpdateOptions appUpdateOptions, int i) throws IntentSender.SendIntentException {
        if (appUpdateInfo == null || intentSenderForResultStarter == null || appUpdateOptions == null || !appUpdateInfo.isUpdateTypeAllowed(appUpdateOptions) || appUpdateInfo.zzd()) {
            return false;
        }
        appUpdateInfo.zzc();
        intentSenderForResultStarter.startIntentSenderForResult(appUpdateInfo.zza(appUpdateOptions).getIntentSender(), i, null, 0, 0, 0, null);
        return true;
    }
}
