package com.google.android.play.core.missingsplits;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import com.google.android.play.core.internal.zzag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zza {
    private static final zzag zza = new zzag("MissingSplitsAppComponentsHelper");
    private final Context zzb;
    private final PackageManager zzc;

    public zza(Context context, PackageManager packageManager) {
        this.zzb = context;
        this.zzc = packageManager;
    }

    private final List zzd() {
        try {
            ArrayList arrayList = new ArrayList();
            PackageInfo packageInfo = this.zzc.getPackageInfo(this.zzb.getPackageName(), 526);
            ProviderInfo[] providerInfoArr = packageInfo.providers;
            if (providerInfoArr != null) {
                Collections.addAll(arrayList, providerInfoArr);
            }
            ActivityInfo[] activityInfoArr = packageInfo.receivers;
            if (activityInfoArr != null) {
                Collections.addAll(arrayList, activityInfoArr);
            }
            ServiceInfo[] serviceInfoArr = packageInfo.services;
            if (serviceInfoArr != null) {
                Collections.addAll(arrayList, serviceInfoArr);
            }
            return arrayList;
        } catch (PackageManager.NameNotFoundException e) {
            zza.zze("Failed to resolve own package : %s", e);
            return Collections.emptyList();
        }
    }

    private final void zze(List list, int i) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ComponentInfo componentInfo = (ComponentInfo) it.next();
            this.zzc.setComponentEnabledSetting(new ComponentName(componentInfo.packageName, componentInfo.name), i, 1);
        }
    }

    public final void zza() {
        zza.zzd("Disabling all non-activity components", new Object[0]);
        zze(zzd(), 2);
    }

    public final void zzb() {
        zza.zzd("Resetting enabled state of all non-activity components", new Object[0]);
        zze(zzd(), 0);
    }

    public final boolean zzc() {
        for (ComponentInfo componentInfo : zzd()) {
            if (this.zzc.getComponentEnabledSetting(new ComponentName(componentInfo.packageName, componentInfo.name)) != 2) {
                zza.zza("Not all non-activity components are disabled", new Object[0]);
                return false;
            }
        }
        zza.zza("All non-activity components are disabled", new Object[0]);
        return true;
    }
}
