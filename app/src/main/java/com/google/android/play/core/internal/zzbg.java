package com.google.android.play.core.internal;

import java.io.File;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbg implements zzaz {
    @Override // com.google.android.play.core.internal.zzaz
    public final void zza(ClassLoader classLoader, Set set) {
        zzbf.zzd(classLoader, set);
    }

    @Override // com.google.android.play.core.internal.zzaz
    public final boolean zzb(ClassLoader classLoader, File file, File file2, boolean z) {
        return zzbf.zze(classLoader, file, file2, z, new zzbb(), "zip", new zzbc());
    }
}
