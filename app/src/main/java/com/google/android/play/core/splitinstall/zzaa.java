package com.google.android.play.core.splitinstall;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzaa implements SplitInstallManager {
    private final zzbc zza;
    private final zzx zzb;
    private final zzs zzc;
    private final zzbe zzd;
    private final Handler zze = new Handler(Looper.getMainLooper());

    public zzaa(zzbc zzbcVar, zzx zzxVar, zzs zzsVar, zzbe zzbeVar) {
        this.zza = zzbcVar;
        this.zzb = zzxVar;
        this.zzc = zzsVar;
        this.zzd = zzbeVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List zze(List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Locale) it.next()).toLanguageTag());
        }
        return arrayList;
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Void> cancelInstall(int i) {
        return this.zza.zzc(i);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Void> deferredInstall(List<String> list) {
        return this.zza.zzd(list);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Void> deferredLanguageInstall(List<Locale> list) {
        return this.zza.zze(zze(list));
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Void> deferredLanguageUninstall(List<Locale> list) {
        return this.zza.zzf(zze(list));
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Void> deferredUninstall(List<String> list) {
        this.zzd.zzc(list);
        return this.zza.zzg(list);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Set<String> getInstalledLanguages() {
        Set<String> setZzd = this.zzc.zzd();
        return setZzd == null ? Collections.emptySet() : setZzd;
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Set<String> getInstalledModules() {
        return this.zzc.zzc();
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<SplitInstallSessionState> getSessionState(int i) {
        return this.zza.zzh(i);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<List<SplitInstallSessionState>> getSessionStates() {
        return this.zza.zzi();
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final synchronized void registerListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.zzb.zzf(splitInstallStateUpdatedListener);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, Activity activity, int i) throws IntentSender.SendIntentException {
        return startConfirmationDialogForResult(splitInstallSessionState, new zzz(activity), i);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0038  */
    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Integer> startInstall(SplitInstallRequest splitInstallRequest) {
        splitInstallRequest.getLanguages().isEmpty();
        List<Locale> languages = splitInstallRequest.getLanguages();
        Set setZzd = this.zzc.zzd();
        if (setZzd == null) {
            if (this.zzc.zzc().containsAll(splitInstallRequest.getModuleNames()) && Collections.disjoint(splitInstallRequest.getModuleNames(), this.zzd.zza())) {
                this.zze.post(new zzy(this, splitInstallRequest));
                return Tasks.zzb(0);
            }
        } else {
            HashSet hashSet = new HashSet();
            Iterator<Locale> it = languages.iterator();
            while (it.hasNext()) {
                hashSet.add(it.next().getLanguage());
            }
            if (setZzd.containsAll(hashSet)) {
                if (this.zzc.zzc().containsAll(splitInstallRequest.getModuleNames())) {
                    this.zze.post(new zzy(this, splitInstallRequest));
                    return Tasks.zzb(0);
                }
            }
        }
        this.zzd.zzd(splitInstallRequest.getModuleNames());
        return this.zza.zzj(splitInstallRequest.getModuleNames(), zze(splitInstallRequest.getLanguages()));
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final synchronized void unregisterListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.zzb.zzh(splitInstallStateUpdatedListener);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final synchronized void zza(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.zzb.zzk(splitInstallStateUpdatedListener);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final synchronized void zzb(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.zzb.zzl(splitInstallStateUpdatedListener);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, IntentSenderForResultStarter intentSenderForResultStarter, int i) throws IntentSender.SendIntentException {
        if (splitInstallSessionState.status() != 8 || splitInstallSessionState.resolutionIntent() == null) {
            return false;
        }
        intentSenderForResultStarter.startIntentSenderForResult(splitInstallSessionState.resolutionIntent().getIntentSender(), i, null, 0, 0, 0, null);
        return true;
    }
}
