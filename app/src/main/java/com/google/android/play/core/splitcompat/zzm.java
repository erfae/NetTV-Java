package com.google.android.play.core.splitcompat;

import android.os.Build;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzm {
    private static final Pattern zza = Pattern.compile("lib/([^/]+)/(.*\\.so)$");
    private final zze zzb;

    public zzm(zze zzeVar) throws IOException {
        this.zzb = zzeVar;
    }

    public static /* bridge */ /* synthetic */ Set zza(zzm zzmVar, Set set, zzs zzsVar, ZipFile zipFile) throws IOException {
        HashSet hashSet = new HashSet();
        zzmVar.zzf(zzsVar, set, new zzi(hashSet, zzsVar, zipFile));
        return hashSet;
    }

    @RequiresApi(21)
    private static void zze(zzs zzsVar, zzj zzjVar) throws IOException {
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(zzsVar.zza());
            try {
                String strZzb = zzsVar.zzb();
                HashMap map = new HashMap();
                Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
                while (enumerationEntries.hasMoreElements()) {
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                    Matcher matcher = zza.matcher(zipEntryNextElement.getName());
                    if (matcher.matches()) {
                        String strGroup = matcher.group(1);
                        String strGroup2 = matcher.group(2);
                        Log.d("SplitCompat", String.format("NativeLibraryExtractor: split '%s' has native library '%s' for ABI '%s'", strZzb, strGroup2, strGroup));
                        Set hashSet = (Set) map.get(strGroup);
                        if (hashSet == null) {
                            hashSet = new HashSet();
                            map.put(strGroup, hashSet);
                        }
                        hashSet.add(new zzl(zipEntryNextElement, strGroup2));
                    }
                }
                HashMap map2 = new HashMap();
                for (String str : Build.SUPPORTED_ABIS) {
                    if (map.containsKey(str)) {
                        Log.d("SplitCompat", String.format("NativeLibraryExtractor: there are native libraries for supported ABI %s; will use this ABI", str));
                        for (zzl zzlVar : (Set) map.get(str)) {
                            if (map2.containsKey(zzlVar.zza)) {
                                Log.d("SplitCompat", String.format("NativeLibraryExtractor: skipping library %s for ABI %s; already present for a better ABI", zzlVar.zza, str));
                            } else {
                                map2.put(zzlVar.zza, zzlVar);
                                Log.d("SplitCompat", String.format("NativeLibraryExtractor: using library %s for ABI %s", zzlVar.zza, str));
                            }
                        }
                    } else {
                        Log.d("SplitCompat", String.format("NativeLibraryExtractor: there are no native libraries for supported ABI %s", str));
                    }
                }
                zzjVar.zza(zipFile, new HashSet(map2.values()));
                zipFile.close();
            } catch (IOException e) {
                e = e;
                if (zipFile != null) {
                    try {
                        zipFile.close();
                    } catch (IOException unused) {
                    }
                }
                throw e;
            }
        } catch (IOException e2) {
            e = e2;
            zipFile = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzf(zzs zzsVar, Set set, zzk zzkVar) throws IOException {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            zzl zzlVar = (zzl) it.next();
            File fileZzc = this.zzb.zzc(zzsVar.zzb(), zzlVar.zza);
            boolean z = false;
            if (fileZzc.exists() && fileZzc.length() == zzlVar.zzb.getSize() && zze.zzp(fileZzc)) {
                z = true;
            }
            zzkVar.zza(zzlVar, fileZzc, z);
        }
    }

    @Nullable
    @RequiresApi(21)
    public final Set zzb(zzs zzsVar) throws IOException {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        HashSet hashSet = new HashSet();
        zze(zzsVar, new zzg(this, zzsVar, hashSet, atomicBoolean));
        if (atomicBoolean.get()) {
            return hashSet;
        }
        return null;
    }

    @RequiresApi(21)
    public final Set zzc() throws IOException {
        Log.d("SplitCompat", "NativeLibraryExtractor: synchronizing native libraries");
        Set setZzj = this.zzb.zzj();
        for (String str : (ArrayList) this.zzb.zzh()) {
            Iterator it = ((HashSet) setZzj).iterator();
            do {
                if (!it.hasNext()) {
                    Log.i("SplitCompat", String.format("NativeLibraryExtractor: extracted split '%s' has no corresponding split; deleting", str));
                    this.zzb.zzn(str);
                    break;
                }
            } while (!((zzs) it.next()).zzb().equals(str));
        }
        HashSet hashSet = new HashSet();
        for (zzs zzsVar : (HashSet) setZzj) {
            HashSet hashSet2 = new HashSet();
            zze(zzsVar, new zzh(this, hashSet2, zzsVar));
            for (File file : (HashSet) this.zzb.zzi(zzsVar.zzb())) {
                if (!hashSet2.contains(file)) {
                    Log.i("SplitCompat", String.format("NativeLibraryExtractor: file '%s' found in split '%s' that is not in the split file '%s'; removing", file.getAbsolutePath(), zzsVar.zzb(), zzsVar.zza().getAbsolutePath()));
                    this.zzb.zzo(file);
                }
            }
            hashSet.addAll(hashSet2);
        }
        return hashSet;
    }
}
