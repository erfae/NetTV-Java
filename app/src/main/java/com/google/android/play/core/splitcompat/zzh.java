package com.google.android.play.core.splitcompat;

import java.io.IOException;
import java.util.Set;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzh implements zzj {
    public final /* synthetic */ Set zza;
    public final /* synthetic */ zzs zzb;
    public final /* synthetic */ zzm zzc;

    public zzh(zzm zzmVar, Set set, zzs zzsVar) {
        this.zzc = zzmVar;
        this.zza = set;
        this.zzb = zzsVar;
    }

    @Override // com.google.android.play.core.splitcompat.zzj
    public final void zza(ZipFile zipFile, Set set) throws IOException {
        this.zza.addAll(zzm.zza(this.zzc, set, this.zzb, zipFile));
    }
}
