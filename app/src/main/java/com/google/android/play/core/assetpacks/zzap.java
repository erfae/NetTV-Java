package com.google.android.play.core.assetpacks;

import android.os.Bundle;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzap extends zzal {
    public final /* synthetic */ zzaw zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzap(zzaw zzawVar, com.google.android.play.core.tasks.zzi zziVar) {
        super(zzawVar, zziVar);
        this.zzc = zzawVar;
    }

    @Override // com.google.android.play.core.assetpacks.zzal, com.google.android.play.core.internal.zzw
    public final void zzd(Bundle bundle) {
        this.zzc.zzg.zzs(this.zza);
        int i = bundle.getInt("error_code");
        zzaw.zza.zzb("onError(%d)", Integer.valueOf(i));
        this.zza.zzd(new AssetPackException(i));
    }

    @Override // com.google.android.play.core.assetpacks.zzal, com.google.android.play.core.internal.zzw
    public final void zzh(Bundle bundle, Bundle bundle2) {
        super.zzh(bundle, bundle2);
        if (!this.zzc.zzh.compareAndSet(true, false)) {
            zzaw.zza.zze("Expected keepingAlive to be true, but was false.", new Object[0]);
        }
        if (bundle.getBoolean("keep_alive")) {
            this.zzc.zzf();
        }
    }
}
