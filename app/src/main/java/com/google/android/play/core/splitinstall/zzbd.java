package com.google.android.play.core.splitinstall;

import android.content.Context;
import com.google.android.play.core.internal.zzcs;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzbd implements zzcs {
    private final zzcs zza;

    public zzbd(zzcs zzcsVar) {
        this.zza = zzcsVar;
    }

    @Override // com.google.android.play.core.internal.zzcs
    public final /* bridge */ /* synthetic */ Object zza() {
        Context contextZzb = ((zzad) this.zza).zzb();
        return new zzbc(contextZzb, contextZzb.getPackageName());
    }
}
