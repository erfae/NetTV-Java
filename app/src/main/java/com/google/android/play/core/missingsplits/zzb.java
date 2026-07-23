package com.google.android.play.core.missingsplits;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.play.core.internal.zzag;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzb implements MissingSplitsManager {
    private static final zzag zza = new zzag("MissingSplitsManagerImpl");
    private final Context zzb;
    private final Runtime zzc;
    private final zza zzd;
    private final AtomicReference zze;

    public zzb(Context context, Runtime runtime, zza zzaVar, AtomicReference atomicReference) {
        this.zzb = context;
        this.zzc = runtime;
        this.zzd = zzaVar;
        this.zze = atomicReference;
    }

    @TargetApi(21)
    private final List zza() {
        List<ActivityManager.AppTask> appTasks = ((ActivityManager) this.zzb.getSystemService("activity")).getAppTasks();
        return appTasks != null ? appTasks : Collections.emptyList();
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0081  */
    @Override // com.google.android.play.core.missingsplits.MissingSplitsManager
    public final boolean disableAppIfMissingRequiredSplits() {
        boolean zBooleanValue;
        boolean z;
        Intent intent;
        boolean z2;
        Bundle bundle;
        Set setEmptySet;
        String[] strArr;
        synchronized (this.zze) {
            if (((Boolean) this.zze.get()) == null) {
                AtomicReference atomicReference = this.zze;
                try {
                    ApplicationInfo applicationInfo = this.zzb.getPackageManager().getApplicationInfo(this.zzb.getPackageName(), 128);
                    if (applicationInfo == null || (bundle = applicationInfo.metaData) == null || !Boolean.TRUE.equals(bundle.get("com.android.vending.splits.required"))) {
                        z2 = false;
                        atomicReference.set(Boolean.valueOf(z2));
                        zBooleanValue = ((Boolean) this.zze.get()).booleanValue();
                    } else {
                        try {
                            PackageInfo packageInfo = this.zzb.getPackageManager().getPackageInfo(this.zzb.getPackageName(), 0);
                            setEmptySet = new HashSet();
                            if (packageInfo != null && (strArr = packageInfo.splitNames) != null) {
                                Collections.addAll(setEmptySet, strArr);
                            }
                        } catch (PackageManager.NameNotFoundException unused) {
                            zza.zze("App '%s' is not found in PackageManager", this.zzb.getPackageName());
                            setEmptySet = Collections.emptySet();
                        }
                        if (setEmptySet.isEmpty() || (setEmptySet.size() == 1 && setEmptySet.contains(""))) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        atomicReference.set(Boolean.valueOf(z2));
                        zBooleanValue = ((Boolean) this.zze.get()).booleanValue();
                    }
                } catch (PackageManager.NameNotFoundException unused2) {
                    zza.zze("App '%s' is not found in the PackageManager", this.zzb.getPackageName());
                }
            } else {
                zBooleanValue = ((Boolean) this.zze.get()).booleanValue();
            }
            throw th;
        }
        if (!zBooleanValue) {
            if (this.zzd.zzc()) {
                this.zzd.zzb();
                this.zzc.exit(0);
            }
            return false;
        }
        for (ActivityManager.AppTask appTask : zza()) {
            if (appTask.getTaskInfo() != null && appTask.getTaskInfo().baseIntent != null && appTask.getTaskInfo().baseIntent.getComponent() != null && PlayCoreMissingSplitsActivity.class.getName().equals(appTask.getTaskInfo().baseIntent.getComponent().getClassName())) {
                return true;
            }
        }
        Iterator it = zza().iterator();
        loop1: while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            ActivityManager.RecentTaskInfo taskInfo = ((ActivityManager.AppTask) it.next()).getTaskInfo();
            if (taskInfo != null && (intent = taskInfo.baseIntent) != null && intent.getComponent() != null) {
                ComponentName component = taskInfo.baseIntent.getComponent();
                String className = component.getClassName();
                try {
                    Class<?> cls = Class.forName(className);
                    while (true) {
                        if (cls == null) {
                            continue;
                        } else {
                            if (cls.equals(Activity.class)) {
                                z = true;
                                break;
                            }
                            Class<? super Object> superclass = cls.getSuperclass();
                            cls = superclass != cls ? superclass : null;
                        }
                    }
                } catch (ClassNotFoundException unused3) {
                    zza.zze("ClassNotFoundException when scanning class hierarchy of '%s'", className);
                    try {
                        if (this.zzb.getPackageManager().getActivityInfo(component, 0) != null) {
                        }
                    } catch (PackageManager.NameNotFoundException unused4) {
                    }
                }
            }
        }
        this.zzd.zza();
        Iterator it2 = zza().iterator();
        while (it2.hasNext()) {
            ((ActivityManager.AppTask) it2.next()).finishAndRemoveTask();
        }
        if (z) {
            this.zzb.getPackageManager().setComponentEnabledSetting(new ComponentName(this.zzb, (Class<?>) PlayCoreMissingSplitsActivity.class), 1, 1);
            this.zzb.startActivity(new Intent(this.zzb, (Class<?>) PlayCoreMissingSplitsActivity.class).addFlags(884998144));
        }
        this.zzc.exit(0);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0080  */
    @Override // com.google.android.play.core.missingsplits.MissingSplitsManager
    public final boolean isMissingRequiredSplits() {
        boolean zBooleanValue;
        Bundle bundle;
        Set setEmptySet;
        String[] strArr;
        synchronized (this.zze) {
            if (((Boolean) this.zze.get()) == null) {
                AtomicReference atomicReference = this.zze;
                boolean z = true;
                try {
                    ApplicationInfo applicationInfo = this.zzb.getPackageManager().getApplicationInfo(this.zzb.getPackageName(), 128);
                    if (applicationInfo == null || (bundle = applicationInfo.metaData) == null || !Boolean.TRUE.equals(bundle.get("com.android.vending.splits.required"))) {
                        z = false;
                        atomicReference.set(Boolean.valueOf(z));
                        zBooleanValue = ((Boolean) this.zze.get()).booleanValue();
                    } else {
                        try {
                            PackageInfo packageInfo = this.zzb.getPackageManager().getPackageInfo(this.zzb.getPackageName(), 0);
                            setEmptySet = new HashSet();
                            if (packageInfo != null && (strArr = packageInfo.splitNames) != null) {
                                Collections.addAll(setEmptySet, strArr);
                            }
                        } catch (PackageManager.NameNotFoundException unused) {
                            zza.zze("App '%s' is not found in PackageManager", this.zzb.getPackageName());
                            setEmptySet = Collections.emptySet();
                        }
                        if (!setEmptySet.isEmpty() && (setEmptySet.size() != 1 || !setEmptySet.contains(""))) {
                            z = false;
                        }
                        atomicReference.set(Boolean.valueOf(z));
                        zBooleanValue = ((Boolean) this.zze.get()).booleanValue();
                    }
                } catch (PackageManager.NameNotFoundException unused2) {
                    zza.zze("App '%s' is not found in the PackageManager", this.zzb.getPackageName());
                }
            } else {
                zBooleanValue = ((Boolean) this.zze.get()).booleanValue();
            }
            throw th;
        }
        return zBooleanValue;
    }
}
