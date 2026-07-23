package com.google.android.play.core.splitinstall;

import com.google.android.play.core.internal.zzcr;
import com.google.android.play.core.internal.zzcs;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzah implements zzcs {
    private final zzac zza;
    private final zzcs zzb;

    public zzah(zzac zzacVar, zzcs zzcsVar) {
        this.zza = zzacVar;
        this.zzb = zzcsVar;
    }

    @Override // com.google.android.play.core.internal.zzcs
    public final /* bridge */ /* synthetic */ Object zza() {
        zzl zzlVar = (zzl) this.zzb.zza();
        zzcr.zza(zzlVar);
        return zzlVar;
    }
}
