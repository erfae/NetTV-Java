package com.google.android.play.core.appupdate;

import android.content.Context;
import com.google.android.play.core.internal.zzcr;
import com.google.android.play.core.internal.zzcs;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzj implements zzcs {
    private final zzh zza;

    public zzj(zzh zzhVar) {
        this.zza = zzhVar;
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
