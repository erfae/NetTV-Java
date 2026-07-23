package com.google.android.play.core.assetpacks;

import android.content.ComponentName;
import android.content.Context;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzs implements com.google.android.play.core.internal.zzcs {
    private final com.google.android.play.core.internal.zzcs zza;
    private final com.google.android.play.core.internal.zzcs zzb;

    public zzs(com.google.android.play.core.internal.zzcs zzcsVar, com.google.android.play.core.internal.zzcs zzcsVar2) {
        this.zza = zzcsVar;
        this.zzb = zzcsVar2;
    }

    @Override // com.google.android.play.core.internal.zzcs
    public final /* bridge */ /* synthetic */ Object zza() {
        Object objZza = this.zza.zza();
        Context contextZzb = ((zzu) this.zzb).zzb();
        zzl zzlVar = (zzl) objZza;
        com.google.android.play.core.internal.zzcd.zza(contextZzb.getPackageManager(), new ComponentName(contextZzb.getPackageName(), "com.google.android.play.core.assetpacks.AssetPackExtractionService"), 4);
        com.google.android.play.core.internal.zzcd.zza(contextZzb.getPackageManager(), new ComponentName(contextZzb.getPackageName(), "com.google.android.play.core.assetpacks.ExtractionForegroundService"), 4);
        com.google.android.play.core.internal.zzcr.zza(zzlVar);
        return zzlVar;
    }
}
