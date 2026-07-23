package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.zzca;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzap extends com.google.android.play.core.internal.zzah {
    public final /* synthetic */ List zza;
    public final /* synthetic */ com.google.android.play.core.tasks.zzi zzb;
    public final /* synthetic */ zzbc zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzap(zzbc zzbcVar, com.google.android.play.core.tasks.zzi zziVar, List list, com.google.android.play.core.tasks.zzi zziVar2) {
        super(zziVar);
        this.zzc = zzbcVar;
        this.zza = list;
        this.zzb = zziVar2;
    }

    @Override // com.google.android.play.core.internal.zzah
    public final void zza() {
        try {
            ((zzca) this.zzc.zza.zze()).zzf(this.zzc.zzd, zzbc.zzl(this.zza), zzbc.zza(), new zzaw(this.zzc, this.zzb));
        } catch (RemoteException e) {
            zzbc.zzb.zzc(e, "deferredLanguageUninstall(%s)", this.zza);
            this.zzb.zzd(new RuntimeException(e));
        }
    }
}
