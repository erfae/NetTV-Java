package com.google.android.play.core.splitinstall;

import android.app.Activity;
import android.content.IntentSender;
import androidx.annotation.NonNull;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.internal.zzco;
import com.google.android.play.core.tasks.Task;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzl implements SplitInstallManager {
    private final zzco zza;
    private final zzco zzb;
    private final zzco zzc;

    public zzl(zzco zzcoVar, zzco zzcoVar2, zzco zzcoVar3) {
        this.zza = zzcoVar;
        this.zzb = zzcoVar2;
        this.zzc = zzcoVar3;
    }

    private final SplitInstallManager zzc() {
        return this.zzc.zza() == null ? (SplitInstallManager) this.zza.zza() : (SplitInstallManager) this.zzb.zza();
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    @NonNull
    public final Task<Void> cancelInstall(int i) {
        return zzc().cancelInstall(i);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    @NonNull
    public final Task<Void> deferredInstall(List<String> list) {
        return zzc().deferredInstall(list);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    @NonNull
    public final Task<Void> deferredLanguageInstall(List<Locale> list) {
        return zzc().deferredLanguageInstall(list);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    @NonNull
    public final Task<Void> deferredLanguageUninstall(List<Locale> list) {
        return zzc().deferredLanguageUninstall(list);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    @NonNull
    public final Task<Void> deferredUninstall(List<String> list) {
        return zzc().deferredUninstall(list);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    @NonNull
    public final Set<String> getInstalledLanguages() {
        return zzc().getInstalledLanguages();
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    @NonNull
    public final Set<String> getInstalledModules() {
        return zzc().getInstalledModules();
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    @NonNull
    public final Task<SplitInstallSessionState> getSessionState(int i) {
        return zzc().getSessionState(i);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    @NonNull
    public final Task<List<SplitInstallSessionState>> getSessionStates() {
        return zzc().getSessionStates();
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final void registerListener(@NonNull SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        zzc().registerListener(splitInstallStateUpdatedListener);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final boolean startConfirmationDialogForResult(@NonNull SplitInstallSessionState splitInstallSessionState, @NonNull Activity activity, int i) throws IntentSender.SendIntentException {
        return zzc().startConfirmationDialogForResult(splitInstallSessionState, activity, i);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Integer> startInstall(@NonNull SplitInstallRequest splitInstallRequest) {
        return zzc().startInstall(splitInstallRequest);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final void unregisterListener(@NonNull SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        zzc().unregisterListener(splitInstallStateUpdatedListener);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final void zza(@NonNull SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        zzc().zza(splitInstallStateUpdatedListener);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final void zzb(@NonNull SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        zzc().zzb(splitInstallStateUpdatedListener);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final boolean startConfirmationDialogForResult(@NonNull SplitInstallSessionState splitInstallSessionState, @NonNull IntentSenderForResultStarter intentSenderForResultStarter, int i) throws IntentSender.SendIntentException {
        return zzc().startConfirmationDialogForResult(splitInstallSessionState, intentSenderForResultStarter, i);
    }
}
