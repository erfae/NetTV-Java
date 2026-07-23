package com.google.android.play.core.splitinstall;

import com.google.android.play.core.internal.zzcr;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzc {
    private zzac zza;

    private zzc() {
    }

    public /* synthetic */ zzc(zzb zzbVar) {
    }

    public final zzc zza(zzac zzacVar) {
        this.zza = zzacVar;
        return this;
    }

    public final zzp zzb() {
        zzcr.zzb(this.zza, zzac.class);
        return new zze(this.zza);
    }
}
