package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;
import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzao extends zzah {
    public final /* synthetic */ IBinder zza;
    public final /* synthetic */ zzar zzb;

    public zzao(zzar zzarVar, IBinder iBinder) {
        this.zzb = zzarVar;
        this.zza = iBinder;
    }

    @Override // com.google.android.play.core.internal.zzah
    public final void zza() {
        zzas zzasVar = this.zzb.zza;
        zzasVar.zzo = (IInterface) zzasVar.zzj.zza(this.zza);
        zzas.zzo(this.zzb.zza);
        this.zzb.zza.zzh = false;
        Iterator it = this.zzb.zza.zze.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.zzb.zza.zze.clear();
    }
}
