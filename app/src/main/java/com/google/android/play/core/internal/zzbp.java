package com.google.android.play.core.internal;

import java.io.File;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbp implements zzaz {
    public static void zzc(ClassLoader classLoader, Set set) {
        zzbk.zzc(classLoader, set, new zzbn());
    }

    public static boolean zzd(ClassLoader classLoader, File file, File file2, boolean z) {
        return zzbf.zze(classLoader, file, file2, z, new zzbh(), "path", new zzbo());
    }

    @Override // com.google.android.play.core.internal.zzaz
    public final void zza(ClassLoader classLoader, Set set) {
        zzc(classLoader, set);
    }

    @Override // com.google.android.play.core.internal.zzaz
    public final boolean zzb(ClassLoader classLoader, File file, File file2, boolean z) {
        return zzd(classLoader, file, file2, z);
    }
}
