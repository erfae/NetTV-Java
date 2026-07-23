package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbh {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("AssetPackStorage");
    private static final long zzb;
    private static final long zzc;
    private final Context zzd;
    private final zzed zze;

    static {
        TimeUnit timeUnit = TimeUnit.DAYS;
        zzb = timeUnit.toMillis(14L);
        zzc = timeUnit.toMillis(28L);
    }

    public zzbh(Context context, zzed zzedVar) {
        this.zzd = context;
        this.zze = zzedVar;
    }

    private static long zzH(File file, boolean z) {
        if (!file.exists()) {
            return -1L;
        }
        ArrayList arrayList = new ArrayList();
        if (z && file.listFiles().length > 1) {
            zza.zze("Multiple pack versions found, using highest version code.", new Object[0]);
        }
        try {
            for (File file2 : file.listFiles()) {
                if (!file2.getName().equals("stale.tmp")) {
                    arrayList.add(Long.valueOf(file2.getName()));
                }
            }
        } catch (NumberFormatException e) {
            zza.zzc(e, "Corrupt asset pack directories.", new Object[0]);
        }
        if (arrayList.isEmpty()) {
            return -1L;
        }
        Collections.sort(arrayList);
        return ((Long) arrayList.get(arrayList.size() - 1)).longValue();
    }

    private final File zzI(String str) {
        return new File(zzL(), str);
    }

    private final File zzJ(String str, int i, long j) {
        return new File(zzj(str, i, j), "merge.tmp");
    }

    private final File zzK(String str, int i, long j) {
        return new File(new File(new File(zzM(), str), String.valueOf(i)), String.valueOf(j));
    }

    private final File zzL() {
        return new File(this.zzd.getFilesDir(), "assetpacks");
    }

    private final File zzM() {
        return new File(zzL(), "_tmp");
    }

    @RequiresApi(21)
    private static List zzN(PackageInfo packageInfo, String str) {
        ArrayList arrayList = new ArrayList();
        String[] strArr = packageInfo.splitNames;
        if (strArr == null) {
            return arrayList;
        }
        int i = (-Arrays.binarySearch(strArr, str)) - 1;
        while (true) {
            String[] strArr2 = packageInfo.splitNames;
            if (i >= strArr2.length || !strArr2[i].startsWith(str)) {
                break;
            }
            arrayList.add(packageInfo.applicationInfo.splitSourceDirs[i]);
            i++;
        }
        return arrayList;
    }

    private final List zzO() {
        ArrayList arrayList = new ArrayList();
        try {
            if (zzL().exists() && zzL().listFiles() != null) {
                for (File file : zzL().listFiles()) {
                    if (!file.getCanonicalPath().equals(zzM().getCanonicalPath())) {
                        arrayList.add(file);
                    }
                }
                return arrayList;
            }
            return arrayList;
        } catch (IOException e) {
            zza.zzb("Could not process directory while scanning installed packs. %s", e);
        }
    }

    private static void zzP(File file) {
        if (file.listFiles() == null || file.listFiles().length <= 1) {
            return;
        }
        long jZzH = zzH(file, false);
        for (File file2 : file.listFiles()) {
            if (!file2.getName().equals(String.valueOf(jZzH)) && !file2.getName().equals("stale.tmp")) {
                zzQ(file2);
            }
        }
    }

    private static boolean zzQ(File file) {
        File[] fileArrListFiles = file.listFiles();
        boolean zZzQ = true;
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                zZzQ &= zzQ(file2);
            }
        }
        if (file.delete()) {
            return zZzQ;
        }
        return false;
    }

    public final void zzA(String str, int i, long j, int i2) throws IOException {
        File fileZzJ = zzJ(str, i, j);
        Properties properties = new Properties();
        properties.put("numberOfMerges", String.valueOf(i2));
        fileZzJ.getParentFile().mkdirs();
        fileZzJ.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(fileZzJ);
        properties.store(fileOutputStream, (String) null);
        fileOutputStream.close();
    }

    public final void zzB(String str, int i, long j) {
        File fileZzI = zzI(str);
        if (fileZzI.exists()) {
            for (File file : fileZzI.listFiles()) {
                if (!file.getName().equals(String.valueOf(i)) && !file.getName().equals("stale.tmp")) {
                    zzQ(file);
                } else if (file.getName().equals(String.valueOf(i))) {
                    for (File file2 : file.listFiles()) {
                        if (!file2.getName().equals(String.valueOf(j))) {
                            zzQ(file2);
                        }
                    }
                }
            }
        }
    }

    public final void zzC(List list) {
        int iZza = this.zze.zza();
        for (File file : zzO()) {
            if (!list.contains(file.getName()) && zzH(file, true) != iZza) {
                zzQ(file);
            }
        }
    }

    public final boolean zzD(String str) {
        if (zzI(str).exists()) {
            return zzQ(zzI(str));
        }
        return true;
    }

    public final boolean zzE(String str, int i, long j) {
        if (zzK(str, i, j).exists()) {
            return zzQ(zzK(str, i, j));
        }
        return true;
    }

    public final boolean zzF(String str, int i, long j) {
        if (zzh(str, i, j).exists()) {
            return zzQ(zzh(str, i, j));
        }
        return true;
    }

    public final boolean zzG(String str) {
        try {
            return zzr(str) != null;
        } catch (IOException unused) {
            return false;
        }
    }

    public final int zza(String str) {
        return (int) zzH(zzI(str), true);
    }

    public final int zzb(String str, int i, long j) throws IOException {
        File fileZzJ = zzJ(str, i, j);
        if (!fileZzJ.exists()) {
            return 0;
        }
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(fileZzJ);
        try {
            properties.load(fileInputStream);
            fileInputStream.close();
            if (properties.getProperty("numberOfMerges") == null) {
                throw new zzck("Merge checkpoint file corrupt.");
            }
            try {
                return Integer.parseInt(properties.getProperty("numberOfMerges"));
            } catch (NumberFormatException e) {
                throw new zzck("Merge checkpoint file corrupt.", e);
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable unused) {
            }
            throw th;
        }
    }

    public final long zzc(String str) {
        return zzH(zzg(str, (int) zzH(zzI(str), true)), true);
    }

    @Nullable
    @VisibleForTesting
    public final AssetLocation zzd(String str, String str2, List list) {
        if (list == null) {
            return null;
        }
        String path = new File("assets", str2).getPath();
        for (String str3 : (ArrayList) list) {
            try {
                AssetLocation assetLocationZza = zzbt.zza(str3, path);
                if (assetLocationZza != null) {
                    return assetLocationZza;
                }
            } catch (IOException e) {
                zza.zzc(e, "Failed to parse APK file '%s' looking for asset '%s'.", str3, str2);
                return null;
            }
        }
        zza.zza("The asset %s is not present in Asset Pack %s. Searched in APKs: %s", str2, str, list);
        return null;
    }

    @Nullable
    public final AssetLocation zze(String str, String str2, AssetPackLocation assetPackLocation) {
        File file = new File(assetPackLocation.assetsPath(), str2);
        if (file.exists()) {
            return new zzbl(file.getPath(), 0L, file.length());
        }
        zza.zza("The asset %s is not present in Asset Pack %s. Searched in folder: %s", str2, str, assetPackLocation.assetsPath());
        return null;
    }

    @Nullable
    public final AssetPackLocation zzf(String str) throws IOException {
        String strZzr = zzr(str);
        if (strZzr == null) {
            return null;
        }
        File file = new File(strZzr, "assets");
        if (file.isDirectory()) {
            return new zzbm(0, strZzr, file.getCanonicalPath());
        }
        zza.zzb("Failed to find assets directory: %s", file);
        return null;
    }

    public final File zzg(String str, int i) {
        return new File(zzI(str), String.valueOf(i));
    }

    public final File zzh(String str, int i, long j) {
        return new File(zzg(str, i), String.valueOf(j));
    }

    public final File zzi(String str, int i, long j) {
        return new File(zzh(str, i, j), "_metadata");
    }

    public final File zzj(String str, int i, long j) {
        return new File(zzK(str, i, j), "_packs");
    }

    public final File zzl(String str, int i, long j) {
        return new File(new File(zzK(str, i, j), "_slices"), "_metadata");
    }

    public final File zzn(String str, int i, long j, String str2) {
        return new File(zzo(str, i, j, str2), "checkpoint.dat");
    }

    public final File zzo(String str, int i, long j, String str2) {
        return new File(new File(new File(zzK(str, i, j), "_slices"), "_metadata"), str2);
    }

    public final File zzp(String str, int i, long j, String str2) {
        return new File(new File(new File(zzK(str, i, j), "_slices"), "_unverified"), str2);
    }

    public final File zzq(String str, int i, long j, String str2) {
        return new File(new File(new File(zzK(str, i, j), "_slices"), "_verified"), str2);
    }

    @Nullable
    public final String zzr(String str) throws IOException {
        int length;
        File file = new File(zzL(), str);
        if (!file.exists()) {
            zza.zza("Pack not found with pack name: %s", str);
            return null;
        }
        File file2 = new File(file, String.valueOf(this.zze.zza()));
        if (!file2.exists()) {
            zza.zza("Pack not found with pack name: %s app version: %s", str, Integer.valueOf(this.zze.zza()));
            return null;
        }
        File[] fileArrListFiles = file2.listFiles();
        if (fileArrListFiles == null || (length = fileArrListFiles.length) == 0) {
            zza.zza("No pack version found for pack name: %s app version: %s", str, Integer.valueOf(this.zze.zza()));
            return null;
        }
        if (length <= 1) {
            return fileArrListFiles[0].getCanonicalPath();
        }
        zza.zzb("Multiple pack versions found for pack name: %s app version: %s", str, Integer.valueOf(this.zze.zza()));
        return null;
    }

    @Nullable
    public final List zzs(String str) {
        PackageInfo packageInfo;
        String str2 = null;
        try {
            packageInfo = this.zzd.getPackageManager().getPackageInfo(this.zzd.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException unused) {
            zza.zzb("Could not find PackageInfo.", new Object[0]);
            packageInfo = null;
        }
        if (packageInfo == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        String[] strArr = packageInfo.splitNames;
        if (strArr == null || packageInfo.applicationInfo.splitSourceDirs == null) {
            zza.zza("No splits present for package %s.", str);
        } else {
            int iBinarySearch = Arrays.binarySearch(strArr, str);
            if (iBinarySearch < 0) {
                zza.zza("Asset Pack '%s' is not installed.", str);
            } else {
                str2 = packageInfo.applicationInfo.splitSourceDirs[iBinarySearch];
            }
        }
        if (str2 == null) {
            arrayList.add(packageInfo.applicationInfo.sourceDir);
            arrayList.addAll(zzN(packageInfo, "config."));
            return arrayList;
        }
        arrayList.add(str2);
        arrayList.addAll(zzN(packageInfo, String.valueOf(str).concat(".config.")));
        return arrayList;
    }

    public final Map zzt() {
        HashMap map = new HashMap();
        Iterator it = zzO().iterator();
        while (it.hasNext()) {
            String name = ((File) it.next()).getName();
            int iZzH = (int) zzH(zzI(name), true);
            long jZzH = zzH(zzg(name, iZzH), true);
            if (zzh(name, iZzH, jZzH).exists()) {
                map.put(name, Long.valueOf(jZzH));
            }
        }
        return map;
    }

    public final Map zzu() {
        HashMap map = new HashMap();
        for (String str : ((HashMap) zzv()).keySet()) {
            map.put(str, Long.valueOf(zzc(str)));
        }
        return map;
    }

    public final Map zzv() {
        HashMap map = new HashMap();
        try {
            for (File file : zzO()) {
                AssetPackLocation assetPackLocationZzf = zzf(file.getName());
                if (assetPackLocationZzf != null) {
                    map.put(file.getName(), assetPackLocationZzf);
                }
            }
        } catch (IOException e) {
            zza.zzb("Could not process directory while scanning installed packs: %s", e);
        }
        return map;
    }

    public final void zzw() {
        for (File file : zzO()) {
            if (file.listFiles() != null) {
                zzP(file);
                long jZzH = zzH(file, false);
                if (this.zze.zza() != jZzH) {
                    try {
                        new File(new File(file, String.valueOf(jZzH)), "stale.tmp").createNewFile();
                    } catch (IOException unused) {
                        zza.zzb("Could not write staleness marker.", new Object[0]);
                    }
                }
                for (File file2 : file.listFiles()) {
                    zzP(file2);
                }
            }
        }
    }

    public final void zzx() {
        if (zzM().exists()) {
            for (File file : zzM().listFiles()) {
                if (System.currentTimeMillis() - file.lastModified() > zzb) {
                    zzQ(file);
                } else {
                    zzP(file);
                }
            }
        }
    }

    public final void zzy() {
        for (File file : zzO()) {
            if (file.listFiles() != null) {
                for (File file2 : file.listFiles()) {
                    File file3 = new File(file2, "stale.tmp");
                    if (file3.exists() && System.currentTimeMillis() - file3.lastModified() > zzc) {
                        zzQ(file2);
                    }
                }
            }
        }
    }

    public final void zzz() {
        zzQ(zzL());
    }
}
