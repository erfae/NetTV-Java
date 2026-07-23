package com.google.android.play.core.review;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.zzad;
import com.google.android.play.core.internal.zzag;
import com.google.android.play.core.internal.zzas;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
class zzg extends zzad {
    public final zzag zza;
    public final com.google.android.play.core.tasks.zzi zzb;
    public final /* synthetic */ zzi zzc;

    public zzg(zzi zziVar, zzag zzagVar, com.google.android.play.core.tasks.zzi zziVar2) {
        this.zzc = zziVar;
        this.zza = zzagVar;
        this.zzb = zziVar2;
    }

    @Override // com.google.android.play.core.internal.zzae
    public void zzb(Bundle bundle) throws RemoteException {
        zzas zzasVar = this.zzc.zza;
        if (zzasVar != null) {
            zzasVar.zzs(this.zzb);
        }
        this.zza.zzd("onGetLaunchReviewFlowInfo", new Object[0]);
    }
}
