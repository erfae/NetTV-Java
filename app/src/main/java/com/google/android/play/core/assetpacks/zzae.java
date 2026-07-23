package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzae extends com.google.android.play.core.internal.zzah {
    public final /* synthetic */ Map zza;
    public final /* synthetic */ com.google.android.play.core.tasks.zzi zzb;
    public final /* synthetic */ zzaw zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzae(zzaw zzawVar, com.google.android.play.core.tasks.zzi zziVar, Map map, com.google.android.play.core.tasks.zzi zziVar2) {
        super(zziVar);
        this.zzc = zzawVar;
        this.zza = map;
        this.zzb = zziVar2;
    }

    @Override // com.google.android.play.core.internal.zzah
    public final void zza() {
        try {
            ((com.google.android.play.core.internal.zzu) this.zzc.zzf.zze()).zze(this.zzc.zzc, zzaw.zzn(this.zza), new zzao(this.zzc, this.zzb));
        } catch (RemoteException e) {
            zzaw.zza.zzc(e, "syncPacks", new Object[0]);
            this.zzb.zzd(new RuntimeException(e));
        }
    }
}
