package com.google.android.play.core.internal;

import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbf implements zzaz {
    public static Object zzc(ClassLoader classLoader) {
        return zzbw.zzb(classLoader, "pathList", Object.class).zzc();
    }

    public static void zzd(ClassLoader classLoader, Set set) {
        if (set.isEmpty()) {
            return;
        }
        HashSet hashSet = new HashSet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            String strValueOf = String.valueOf(file.getParentFile().getAbsolutePath());
            Log.d("Splitcompat", strValueOf.length() != 0 ? "Adding native library parent directory: ".concat(strValueOf) : new String("Adding native library parent directory: "));
            hashSet.add(file.getParentFile());
        }
        zzbu zzbuVarZza = zzbw.zza(zzc(classLoader), "nativeLibraryDirectories", File.class);
        hashSet.removeAll(Arrays.asList((File[]) zzbuVarZza.zzc()));
        synchronized (com.google.android.play.core.splitinstall.zzn.class) {
            int size = hashSet.size();
            StringBuilder sb = new StringBuilder(30);
            sb.append("Adding directories ");
            sb.append(size);
            Log.d("Splitcompat", sb.toString());
            zzbuVarZza.zzb(hashSet);
        }
    }

    public static boolean zze(ClassLoader classLoader, File file, File file2, boolean z, zzbe zzbeVar, String str, zzbd zzbdVar) {
        ArrayList arrayList = new ArrayList();
        Object objZzc = zzc(classLoader);
        zzbu zzbuVarZza = zzbw.zza(objZzc, "dexElements", Object.class);
        List listAsList = Arrays.asList((Object[]) zzbuVarZza.zzc());
        ArrayList arrayList2 = new ArrayList();
        Iterator it = listAsList.iterator();
        while (it.hasNext()) {
            arrayList2.add((File) zzbw.zzb(it.next(), str, File.class).zzc());
        }
        if (arrayList2.contains(file2)) {
            return true;
        }
        if (!z && !zzbdVar.zza(objZzc, file2, file)) {
            String strValueOf = String.valueOf(file2.getPath());
            Log.w("SplitCompat", strValueOf.length() != 0 ? "Should be optimized ".concat(strValueOf) : new String("Should be optimized "));
            return false;
        }
        zzbuVarZza.zza(Arrays.asList(zzbeVar.zza(objZzc, new ArrayList(Collections.singleton(file2)), file, arrayList)));
        if (arrayList.isEmpty()) {
            return true;
        }
        zzbt zzbtVar = new zzbt("DexPathList.makeDexElement failed");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Log.e("SplitCompat", "DexPathList.makeDexElement failed", (IOException) arrayList.get(i));
        }
        zzbw.zza(objZzc, "dexElementsSuppressedExceptions", IOException.class).zza(arrayList);
        throw zzbtVar;
    }

    @Override // com.google.android.play.core.internal.zzaz
    public final void zza(ClassLoader classLoader, Set set) {
        zzd(classLoader, set);
    }

    @Override // com.google.android.play.core.internal.zzaz
    public final boolean zzb(ClassLoader classLoader, File file, File file2, boolean z) {
        return zze(classLoader, file, file2, z, new zzbb(), "zip", new zzbc());
    }
}
