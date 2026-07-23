package com.google.android.play.core.splitinstall.testing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.internal.zzaf;
import com.google.android.play.core.internal.zzcf;
import com.google.android.play.core.internal.zzcj;
import com.google.android.play.core.internal.zzco;
import com.google.android.play.core.splitinstall.SplitInstallException;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class FakeSplitInstallManager implements SplitInstallManager {
    public static final /* synthetic */ int zza = 0;
    private static final long zzb = TimeUnit.SECONDS.toMillis(1);
    private final Handler zzc;
    private final Context zzd;
    private final com.google.android.play.core.splitinstall.zzs zze;
    private final zzco zzf;
    private final zzcf zzg;
    private final zzaf zzh;
    private final zzaf zzi;
    private final Executor zzj;
    private final com.google.android.play.core.splitinstall.zzg zzk;
    private final File zzl;
    private final AtomicReference zzm;
    private final Set zzn;
    private final Set zzo;
    private final AtomicBoolean zzp;
    private final zzd zzq;

    @Deprecated
    public FakeSplitInstallManager(Context context, File file) {
        this(context, file, new com.google.android.play.core.splitinstall.zzs(context, context.getPackageName()), new zzco() { // from class: com.google.android.play.core.splitinstall.testing.zzj
            @Override // com.google.android.play.core.internal.zzco
            public final Object zza() {
                int i = FakeSplitInstallManager.zza;
                return zzt.zza;
            }
        });
    }

    private final com.google.android.play.core.splitinstall.zzk zzk() {
        com.google.android.play.core.splitinstall.zzk zzkVarZza = this.zze.zza();
        if (zzkVarZza != null) {
            return zzkVarZza;
        }
        throw new IllegalStateException("Language information could not be found. Make sure you are using the target application context, not the tests context, and the app is built as a bundle.");
    }

    @Nullable
    private final SplitInstallSessionState zzl() {
        return (SplitInstallSessionState) this.zzm.get();
    }

    @Nullable
    private final synchronized SplitInstallSessionState zzm(zzp zzpVar) {
        SplitInstallSessionState splitInstallSessionStateZzl = zzl();
        SplitInstallSessionState splitInstallSessionStateZza = zzpVar.zza(splitInstallSessionStateZzl);
        if (this.zzm.compareAndSet(splitInstallSessionStateZzl, splitInstallSessionStateZza)) {
            return splitInstallSessionStateZza;
        }
        return null;
    }

    private final Task zzn(@SplitInstallErrorCode final int i) {
        zzm(new zzp() { // from class: com.google.android.play.core.splitinstall.testing.zzg
            @Override // com.google.android.play.core.splitinstall.testing.zzp
            public final SplitInstallSessionState zza(SplitInstallSessionState splitInstallSessionState) {
                int i2 = i;
                int i3 = FakeSplitInstallManager.zza;
                if (splitInstallSessionState == null) {
                    return null;
                }
                return SplitInstallSessionState.create(splitInstallSessionState.sessionId(), 6, i2, splitInstallSessionState.bytesDownloaded(), splitInstallSessionState.totalBytesToDownload(), splitInstallSessionState.moduleNames(), splitInstallSessionState.languages());
            }
        });
        return Tasks.zza(new SplitInstallException(i));
    }

    private static String zzo(String str) {
        return str.split("\\.config\\.", 2)[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzp(List list, List list2, List list3, long j, boolean z) {
        this.zzk.zza().zzd(list, new zzo(this, list2, list3, j, z, list));
    }

    private final void zzq(final SplitInstallSessionState splitInstallSessionState) {
        this.zzc.post(new Runnable() { // from class: com.google.android.play.core.splitinstall.testing.zzm
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzg(splitInstallSessionState);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzr(List list, List list2, long j) {
        this.zzn.addAll(list);
        this.zzo.addAll(list2);
        Long lValueOf = Long.valueOf(j);
        zzs(5, 0, lValueOf, lValueOf, null, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zzs(final int i, final int i2, @Nullable final Long l, @Nullable final Long l2, @Nullable final List list, @Nullable final Integer num, @Nullable final List list2) {
        SplitInstallSessionState splitInstallSessionStateZzm = zzm(new zzp() { // from class: com.google.android.play.core.splitinstall.testing.zzi
            @Override // com.google.android.play.core.splitinstall.testing.zzp
            public final SplitInstallSessionState zza(SplitInstallSessionState splitInstallSessionState) {
                Integer num2 = num;
                int i3 = i;
                int i4 = i2;
                Long l3 = l;
                Long l4 = l2;
                List<String> list3 = list;
                List<String> list4 = list2;
                int i5 = FakeSplitInstallManager.zza;
                SplitInstallSessionState splitInstallSessionStateCreate = splitInstallSessionState == null ? SplitInstallSessionState.create(0, 0, 0, 0L, 0L, new ArrayList(), new ArrayList()) : splitInstallSessionState;
                return SplitInstallSessionState.create(num2 == null ? splitInstallSessionStateCreate.sessionId() : num2.intValue(), i3, i4, l3 == null ? splitInstallSessionStateCreate.bytesDownloaded() : l3.longValue(), l4 == null ? splitInstallSessionStateCreate.totalBytesToDownload() : l4.longValue(), list3 == null ? splitInstallSessionStateCreate.moduleNames() : list3, list4 == null ? splitInstallSessionStateCreate.languages() : list4);
            }
        });
        if (splitInstallSessionStateZzm == null) {
            return false;
        }
        zzq(splitInstallSessionStateZzm);
        return true;
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Void> cancelInstall(final int i) {
        try {
            SplitInstallSessionState splitInstallSessionStateZzm = zzm(new zzp() { // from class: com.google.android.play.core.splitinstall.testing.zzf
                @Override // com.google.android.play.core.splitinstall.testing.zzp
                public final SplitInstallSessionState zza(SplitInstallSessionState splitInstallSessionState) {
                    int iStatus;
                    int i2 = i;
                    int i3 = FakeSplitInstallManager.zza;
                    if (splitInstallSessionState != null && i2 == splitInstallSessionState.sessionId() && ((iStatus = splitInstallSessionState.status()) == 1 || iStatus == 2 || iStatus == 8 || iStatus == 9 || iStatus == 7)) {
                        return SplitInstallSessionState.create(i2, 7, splitInstallSessionState.errorCode(), splitInstallSessionState.bytesDownloaded(), splitInstallSessionState.totalBytesToDownload(), splitInstallSessionState.moduleNames(), splitInstallSessionState.languages());
                    }
                    throw new SplitInstallException(-3);
                }
            });
            if (splitInstallSessionStateZzm != null) {
                zzq(splitInstallSessionStateZzm);
            }
            return Tasks.zzb(null);
        } catch (SplitInstallException e) {
            return Tasks.zza(e);
        }
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Void> deferredInstall(List<String> list) {
        return Tasks.zza(new SplitInstallException(-5));
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Void> deferredLanguageInstall(List<Locale> list) {
        return Tasks.zza(new SplitInstallException(-5));
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Void> deferredLanguageUninstall(List<Locale> list) {
        return Tasks.zza(new SplitInstallException(-5));
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Void> deferredUninstall(List<String> list) {
        return Tasks.zza(new SplitInstallException(-5));
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Set<String> getInstalledLanguages() {
        HashSet hashSet = new HashSet();
        if (this.zze.zzd() != null) {
            hashSet.addAll(this.zze.zzd());
        }
        hashSet.addAll(this.zzo);
        return hashSet;
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Set<String> getInstalledModules() {
        HashSet hashSet = new HashSet();
        hashSet.addAll(this.zze.zzc());
        hashSet.addAll(this.zzn);
        return hashSet;
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<SplitInstallSessionState> getSessionState(int i) {
        SplitInstallSessionState splitInstallSessionStateZzl = zzl();
        return (splitInstallSessionStateZzl == null || splitInstallSessionStateZzl.sessionId() != i) ? Tasks.zza(new SplitInstallException(-4)) : Tasks.zzb(splitInstallSessionStateZzl);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<List<SplitInstallSessionState>> getSessionStates() {
        SplitInstallSessionState splitInstallSessionStateZzl = zzl();
        return Tasks.zzb(splitInstallSessionStateZzl != null ? Collections.singletonList(splitInstallSessionStateZzl) : Collections.emptyList());
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final void registerListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.zzi.zza(splitInstallStateUpdatedListener);
    }

    public void setShouldNetworkError(boolean z) {
        this.zzp.set(z);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, Activity activity, int i) throws IntentSender.SendIntentException {
        return false;
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, IntentSenderForResultStarter intentSenderForResultStarter, int i) throws IntentSender.SendIntentException {
        return false;
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final Task<Integer> startInstall(final SplitInstallRequest splitInstallRequest) {
        Integer numZza;
        int i;
        File[] fileArr;
        int i2;
        try {
            SplitInstallSessionState splitInstallSessionStateZzm = zzm(new zzp() { // from class: com.google.android.play.core.splitinstall.testing.zzh
                @Override // com.google.android.play.core.splitinstall.testing.zzp
                public final SplitInstallSessionState zza(SplitInstallSessionState splitInstallSessionState) {
                    SplitInstallRequest splitInstallRequest2 = splitInstallRequest;
                    int i3 = FakeSplitInstallManager.zza;
                    if (splitInstallSessionState == null || splitInstallSessionState.hasTerminalStatus()) {
                        return SplitInstallSessionState.create(splitInstallSessionState == null ? 1 : 1 + splitInstallSessionState.sessionId(), 1, 0, 0L, 0L, splitInstallRequest2.getModuleNames(), new ArrayList());
                    }
                    throw new SplitInstallException(-1);
                }
            });
            if (splitInstallSessionStateZzm == null) {
                return zzn(-100);
            }
            int iSessionId = splitInstallSessionStateZzm.sessionId();
            final ArrayList arrayList = new ArrayList();
            Iterator<Locale> it = splitInstallRequest.getLanguages().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getLanguage());
            }
            HashSet hashSet = new HashSet();
            final ArrayList arrayList2 = new ArrayList();
            File[] fileArrListFiles = this.zzl.listFiles(new FileFilter() { // from class: com.google.android.play.core.splitinstall.testing.zzk
                @Override // java.io.FileFilter
                public final boolean accept(File file) {
                    int i3 = FakeSplitInstallManager.zza;
                    return file.getName().endsWith(".apk");
                }
            });
            if (fileArrListFiles == null) {
                Log.w("FakeSplitInstallManager", "Specified splits directory does not exist.");
                return zzn(-5);
            }
            int length = fileArrListFiles.length;
            int i3 = 0;
            long j = 0;
            while (i3 < length) {
                File file = fileArrListFiles[i3];
                String strZza = zzcj.zza(file);
                String strZzo = zzo(strZza);
                hashSet.add(strZza);
                if (splitInstallRequest.getModuleNames().contains(strZzo)) {
                    String strZzo2 = zzo(strZza);
                    HashSet hashSet2 = new HashSet(this.zzg.zza());
                    fileArr = fileArrListFiles;
                    Map mapZza = zzk().zza(Arrays.asList(strZzo2));
                    HashSet hashSet3 = new HashSet();
                    Iterator it2 = mapZza.values().iterator();
                    while (it2.hasNext()) {
                        hashSet3.addAll((Set) it2.next());
                        length = length;
                    }
                    i2 = length;
                    HashSet hashSet4 = new HashSet();
                    Iterator it3 = hashSet2.iterator();
                    while (it3.hasNext()) {
                        String str = (String) it3.next();
                        Iterator it4 = it3;
                        if (str.contains("_")) {
                            str = str.split("_", -1)[0];
                        }
                        hashSet4.add(str);
                        it3 = it4;
                        iSessionId = iSessionId;
                    }
                    i = iSessionId;
                    hashSet4.addAll(this.zzo);
                    hashSet4.addAll(arrayList);
                    HashSet hashSet5 = new HashSet();
                    for (Map.Entry entry : mapZza.entrySet()) {
                        if (hashSet4.contains(entry.getKey())) {
                            hashSet5.addAll((Collection) entry.getValue());
                        }
                    }
                    if (!hashSet3.contains(strZza) || hashSet5.contains(strZza)) {
                        long length2 = file.length() + j;
                        arrayList2.add(file);
                        j = length2;
                        break;
                    }
                    i3++;
                    fileArrListFiles = fileArr;
                    length = i2;
                    iSessionId = i;
                } else {
                    i = iSessionId;
                    fileArr = fileArrListFiles;
                    i2 = length;
                }
                List<Locale> languages = splitInstallRequest.getLanguages();
                ArrayList arrayList3 = new ArrayList(this.zzn);
                arrayList3.addAll(Arrays.asList("", "base"));
                Map mapZza2 = zzk().zza(arrayList3);
                for (Locale locale : languages) {
                    if (mapZza2.containsKey(locale.getLanguage()) && ((Set) mapZza2.get(locale.getLanguage())).contains(strZza)) {
                        long length3 = file.length() + j;
                        arrayList2.add(file);
                        j = length3;
                        break;
                        break;
                    }
                }
                i3++;
                fileArrListFiles = fileArr;
                length = i2;
                iSessionId = i;
            }
            int i4 = iSessionId;
            String string = hashSet.toString();
            String strValueOf = String.valueOf(splitInstallRequest.getModuleNames());
            Log.i("FakeSplitInstallManager", Insets$$ExternalSyntheticOutline0.m(new StringBuilder(string.length() + 22 + strValueOf.length()), "availableSplits ", string, " want ", strValueOf));
            if (splitInstallRequest.getModuleNames().size() != 1 || (numZza = (Integer) ((zzt) this.zzf.zza()).zzb().get(splitInstallRequest.getModuleNames().get(0))) == null) {
                numZza = ((zzt) this.zzf.zza()).zza();
            }
            if (numZza != null) {
                return zzn(numZza.intValue());
            }
            if (!hashSet.containsAll(new HashSet(splitInstallRequest.getModuleNames()))) {
                return zzn(-2);
            }
            Long lValueOf = Long.valueOf(j);
            List moduleNames = splitInstallRequest.getModuleNames();
            Integer numValueOf = Integer.valueOf(i4);
            zzs(1, 0, 0L, lValueOf, moduleNames, numValueOf, arrayList);
            this.zzj.execute(new Runnable() { // from class: com.google.android.play.core.splitinstall.testing.zzn
                @Override // java.lang.Runnable
                public final void run() {
                    this.zza.zzi(arrayList2, arrayList);
                }
            });
            return Tasks.zzb(numValueOf);
        } catch (SplitInstallException e) {
            return zzn(e.getErrorCode());
        }
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final void unregisterListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.zzi.zzb(splitInstallStateUpdatedListener);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final void zza(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.zzh.zza(splitInstallStateUpdatedListener);
    }

    @Override // com.google.android.play.core.splitinstall.SplitInstallManager
    public final void zzb(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.zzh.zzb(splitInstallStateUpdatedListener);
    }

    public final File zzc() {
        return this.zzl;
    }

    public final /* synthetic */ void zzf(final long j, final List list, final List list2, final List list3) {
        long j2 = j / 3;
        long jMin = 0;
        for (int i = 0; i < 3; i++) {
            jMin = Math.min(j, jMin + j2);
            zzs(2, 0, Long.valueOf(jMin), Long.valueOf(j), null, null, null);
            SystemClock.sleep(zzb);
            SplitInstallSessionState splitInstallSessionStateZzl = zzl();
            if (splitInstallSessionStateZzl.status() == 9 || splitInstallSessionStateZzl.status() == 7 || splitInstallSessionStateZzl.status() == 6) {
                return;
            }
        }
        this.zzj.execute(new Runnable() { // from class: com.google.android.play.core.splitinstall.testing.zze
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzh(list, list2, list3, j);
            }
        });
    }

    public final /* synthetic */ void zzg(SplitInstallSessionState splitInstallSessionState) {
        this.zzh.zzc(splitInstallSessionState);
        this.zzi.zzc(splitInstallSessionState);
    }

    public final /* synthetic */ void zzh(List list, List list2, List list3, long j) {
        if (this.zzp.get()) {
            zzs(6, -6, null, null, null, null, null);
        } else if (this.zzk.zza() != null) {
            zzp(list, list2, list3, j, false);
        } else {
            zzr(list2, list3, j);
        }
    }

    public final /* synthetic */ void zzi(List list, final List list2) {
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            String strZza = zzcj.zza(file);
            Uri uriFromFile = Uri.fromFile(file);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(uriFromFile, this.zzd.getContentResolver().getType(uriFromFile));
            intent.addFlags(1);
            intent.putExtra("module_name", zzo(strZza));
            intent.putExtra("split_id", strZza);
            arrayList.add(intent);
            arrayList2.add(zzo(zzcj.zza(file)));
        }
        SplitInstallSessionState splitInstallSessionStateZzl = zzl();
        if (splitInstallSessionStateZzl == null) {
            return;
        }
        final long j = splitInstallSessionStateZzl.totalBytesToDownload();
        this.zzj.execute(new Runnable() { // from class: com.google.android.play.core.splitinstall.testing.zzl
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzf(j, arrayList, arrayList2, list2);
            }
        });
    }

    public FakeSplitInstallManager(Context context, @Nullable File file, com.google.android.play.core.splitinstall.zzs zzsVar, zzco zzcoVar) {
        Executor executorZza = com.google.android.play.core.splitcompat.zzd.zza();
        zzcf zzcfVar = new zzcf(context);
        zzd zzdVar = new Object() { // from class: com.google.android.play.core.splitinstall.testing.zzd
        };
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzm = new AtomicReference();
        this.zzn = Collections.synchronizedSet(new HashSet());
        this.zzo = Collections.synchronizedSet(new HashSet());
        this.zzp = new AtomicBoolean(false);
        this.zzd = context;
        this.zzl = file;
        this.zze = zzsVar;
        this.zzf = zzcoVar;
        this.zzj = executorZza;
        this.zzg = zzcfVar;
        this.zzq = zzdVar;
        this.zzi = new zzaf();
        this.zzh = new zzaf();
        this.zzk = com.google.android.play.core.splitinstall.zzo.INSTANCE;
    }
}
