package com.google.android.play.core.splitcompat;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzg implements zzj {
    public final /* synthetic */ zzs zza;
    public final /* synthetic */ Set zzb;
    public final /* synthetic */ AtomicBoolean zzc;
    public final /* synthetic */ zzm zzd;

    public zzg(zzm zzmVar, zzs zzsVar, Set set, AtomicBoolean atomicBoolean) {
        this.zzd = zzmVar;
        this.zza = zzsVar;
        this.zzb = set;
        this.zzc = atomicBoolean;
    }

    @Override // com.google.android.play.core.splitcompat.zzj
    public final void zza(ZipFile zipFile, Set set) throws IOException {
        this.zzd.zzf(this.zza, set, new zzf(this));
    }
}
