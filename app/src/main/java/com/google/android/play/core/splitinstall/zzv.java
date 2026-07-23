package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.Intent;
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzv implements zzf {
    public final /* synthetic */ SplitInstallSessionState zza;
    public final /* synthetic */ Intent zzb;
    public final /* synthetic */ Context zzc;
    public final /* synthetic */ zzx zzd;

    public zzv(zzx zzxVar, SplitInstallSessionState splitInstallSessionState, Intent intent, Context context) {
        this.zzd = zzxVar;
        this.zza = splitInstallSessionState;
        this.zzb = intent;
        this.zzc = context;
    }

    @Override // com.google.android.play.core.splitinstall.zzf
    public final void zza() {
        zzx zzxVar = this.zzd;
        zzxVar.zzd.post(new zzw(zzxVar, this.zza, 5, 0));
    }

    @Override // com.google.android.play.core.splitinstall.zzf
    public final void zzb(@SplitInstallErrorCode int i) {
        zzx zzxVar = this.zzd;
        zzxVar.zzd.post(new zzw(zzxVar, this.zza, 6, i));
    }

    @Override // com.google.android.play.core.splitinstall.zzf
    public final void zzc() {
        if (this.zzb.getBooleanExtra("triggered_from_app_after_verification", false)) {
            this.zzd.zza.zzb("Splits copied and verified more than once.", new Object[0]);
        } else {
            this.zzb.putExtra("triggered_from_app_after_verification", true);
            this.zzc.sendBroadcast(this.zzb);
        }
    }
}
