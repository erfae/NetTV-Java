package com.google.android.play.core.splitinstall;

import android.content.Context;
import com.google.android.play.core.internal.zzcr;
import com.google.android.play.core.internal.zzcs;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzad implements zzcs {
    private final zzac zza;

    public zzad(zzac zzacVar) {
        this.zza = zzacVar;
    }

    @Override // com.google.android.play.core.internal.zzcs
    public final /* synthetic */ Object zza() {
        Context contextZza = this.zza.zza();
        zzcr.zza(contextZza);
        return contextZza;
    }

    public final Context zzb() {
        Context contextZza = this.zza.zza();
        zzcr.zza(contextZza);
        return contextZza;
    }
}
