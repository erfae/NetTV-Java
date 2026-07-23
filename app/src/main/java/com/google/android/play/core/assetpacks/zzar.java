package com.google.android.play.core.assetpacks;

import android.os.Bundle;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzar extends zzal {
    public final int zzc;
    public final String zzd;
    public final int zze;
    public final /* synthetic */ zzaw zzf;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzar(zzaw zzawVar, com.google.android.play.core.tasks.zzi zziVar, int i, String str, int i2) {
        super(zzawVar, zziVar);
        this.zzf = zzawVar;
        this.zzc = i;
        this.zzd = str;
        this.zze = i2;
    }

    @Override // com.google.android.play.core.assetpacks.zzal, com.google.android.play.core.internal.zzw
    public final void zzd(Bundle bundle) {
        this.zzf.zzf.zzs(this.zza);
        zzaw.zza.zzb("onError(%d), retrying notifyModuleCompleted...", Integer.valueOf(bundle.getInt("error_code")));
        int i = this.zze;
        if (i > 0) {
            this.zzf.zzD(this.zzc, this.zzd, i - 1);
        }
    }
}
