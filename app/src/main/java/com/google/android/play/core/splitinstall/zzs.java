package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzs {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("SplitInstallInfoProvider");
    private final Context zzb;
    private final String zzc;

    public zzs(Context context) {
        this.zzb = context;
        this.zzc = context.getPackageName();
    }

    public zzs(Context context, String str) {
        this.zzb = context;
        this.zzc = str;
    }

    public static String zzb(String str) {
        return str.startsWith("config.") ? "" : str.split("\\.config\\.", 2)[0];
    }

    public static boolean zze(String str) {
        return str.startsWith("config.");
    }

    public static boolean zzf(String str) {
        return str.startsWith("config.") || str.contains(".config.");
    }

    @Nullable
    private final Bundle zzg() {
        Bundle bundle;
        try {
            ApplicationInfo applicationInfo = this.zzb.getPackageManager().getApplicationInfo(this.zzc, 128);
            if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
                return bundle;
            }
            zza.zza("App has no applicationInfo or metaData", new Object[0]);
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            zza.zze("App is not found in PackageManager", new Object[0]);
            return null;
        }
    }

    private final Set zzh() {
        HashSet hashSet = new HashSet();
        Bundle bundleZzg = zzg();
        if (bundleZzg != null) {
            String string = bundleZzg.getString("com.android.dynamic.apk.fused.modules");
            if (string == null || string.isEmpty()) {
                zza.zza("App has no fused modules.", new Object[0]);
            } else {
                Collections.addAll(hashSet, string.split(",", -1));
                hashSet.remove("");
                hashSet.remove("base");
            }
        }
        String[] strArr = null;
        try {
            PackageInfo packageInfo = this.zzb.getPackageManager().getPackageInfo(this.zzc, 0);
            if (packageInfo != null) {
                strArr = packageInfo.splitNames;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            zza.zze("App is not found in PackageManager", new Object[0]);
        }
        if (strArr != null) {
            zza.zza("Adding splits from package manager: %s", Arrays.toString(strArr));
            Collections.addAll(hashSet, strArr);
        } else {
            zza.zza("No splits are found or app cannot be found in package manager.", new Object[0]);
        }
        zzq zzqVarZza = zzr.zza();
        if (zzqVarZza != null) {
            hashSet.addAll(zzqVarZza.zza());
        }
        return hashSet;
    }

    @Nullable
    public final zzk zza() {
        Bundle bundleZzg = zzg();
        if (bundleZzg == null) {
            zza.zze("No metadata found in Context.", new Object[0]);
            return null;
        }
        int i = bundleZzg.getInt("com.android.vending.splits");
        if (i == 0) {
            zza.zze("No metadata found in AndroidManifest.", new Object[0]);
            return null;
        }
        try {
            zzk zzkVarZza = zzbg.zza(this.zzb.getResources().getXml(i), new zzi());
            if (zzkVarZza == null) {
                zza.zze("Can't parse languages metadata.", new Object[0]);
            }
            return zzkVarZza;
        } catch (Resources.NotFoundException unused) {
            zza.zze("Resource with languages metadata doesn't exist.", new Object[0]);
            return null;
        }
    }

    public final Set zzc() {
        HashSet hashSet = new HashSet();
        for (String str : zzh()) {
            if (!zzf(str)) {
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    @Nullable
    public final Set zzd() {
        zzk zzkVarZza = zza();
        if (zzkVarZza == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Set setZzh = zzh();
        setZzh.add("");
        Set setZzc = zzc();
        setZzc.add("");
        for (Map.Entry entry : zzkVarZza.zza(setZzc).entrySet()) {
            if (setZzh.containsAll((Collection) entry.getValue())) {
                hashSet.add((String) entry.getKey());
            }
        }
        return hashSet;
    }
}
