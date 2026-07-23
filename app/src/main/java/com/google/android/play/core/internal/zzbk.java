package com.google.android.play.core.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbk implements zzaz {
    public static void zzc(ClassLoader classLoader, Set set, zzbj zzbjVar) {
        if (set.isEmpty()) {
            return;
        }
        HashSet hashSet = new HashSet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            hashSet.add(((File) it.next()).getParentFile());
        }
        Object objZzc = zzbf.zzc(classLoader);
        zzbv zzbvVarZzb = zzbw.zzb(objZzc, "nativeLibraryDirectories", List.class);
        synchronized (com.google.android.play.core.splitinstall.zzn.class) {
            ArrayList arrayList = new ArrayList((Collection) zzbvVarZzb.zzc());
            hashSet.removeAll(arrayList);
            arrayList.addAll(hashSet);
            zzbvVarZzb.zze(arrayList);
        }
        ArrayList arrayList2 = new ArrayList();
        Object[] objArrZza = zzbjVar.zza(objZzc, new ArrayList(hashSet), null, arrayList2);
        if (arrayList2.isEmpty()) {
            synchronized (com.google.android.play.core.splitinstall.zzn.class) {
                zzbw.zza(objZzc, "nativeLibraryPathElements", Object.class).zzb(Arrays.asList(objArrZza));
            }
            return;
        }
        zzbt zzbtVar = new zzbt("Error in makePathElements");
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
        }
        throw zzbtVar;
    }

    public static boolean zzd(ClassLoader classLoader, File file, File file2, boolean z, String str) {
        return zzbf.zze(classLoader, file, file2, z, new zzbh(), "zip", new zzbc());
    }

    @Override // com.google.android.play.core.internal.zzaz
    public final void zza(ClassLoader classLoader, Set set) {
        zzc(classLoader, set, new zzbi());
    }

    @Override // com.google.android.play.core.internal.zzaz
    public final boolean zzb(ClassLoader classLoader, File file, File file2, boolean z) {
        return zzd(classLoader, file, file2, z, "zip");
    }
}
