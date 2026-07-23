package com.google.android.play.core.assetpacks;

import java.io.File;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzek {
    private final zzbh zza;
    private final com.google.android.play.core.internal.zzco zzb;
    private final zzde zzc;
    private final com.google.android.play.core.internal.zzco zzd;
    private final zzco zze;

    public zzek(zzbh zzbhVar, com.google.android.play.core.internal.zzco zzcoVar, zzde zzdeVar, com.google.android.play.core.internal.zzco zzcoVar2, zzco zzcoVar3) {
        this.zza = zzbhVar;
        this.zzb = zzcoVar;
        this.zzc = zzdeVar;
        this.zzd = zzcoVar2;
        this.zze = zzcoVar3;
    }

    public final void zza(final zzei zzeiVar) {
        File fileZzh = this.zza.zzh(zzeiVar.zzl, zzeiVar.zza, zzeiVar.zzc);
        if (!fileZzh.exists()) {
            throw new zzck(String.format("Cannot find pack files to promote for pack %s at %s", zzeiVar.zzl, fileZzh.getAbsolutePath()), zzeiVar.zzk);
        }
        File fileZzh2 = this.zza.zzh(zzeiVar.zzl, zzeiVar.zzb, zzeiVar.zzc);
        fileZzh2.mkdirs();
        if (!fileZzh.renameTo(fileZzh2)) {
            throw new zzck(String.format("Cannot promote pack %s from %s to %s", zzeiVar.zzl, fileZzh.getAbsolutePath(), fileZzh2.getAbsolutePath()), zzeiVar.zzk);
        }
        ((Executor) this.zzd.zza()).execute(new Runnable() { // from class: com.google.android.play.core.assetpacks.zzej
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzb(zzeiVar);
            }
        });
        this.zzc.zzk(zzeiVar.zzl, zzeiVar.zzb, zzeiVar.zzc);
        this.zze.zzc(zzeiVar.zzl);
        ((zzy) this.zzb.zza()).zzh(zzeiVar.zzk, zzeiVar.zzl);
    }

    public final /* synthetic */ void zzb(zzei zzeiVar) {
        this.zza.zzB(zzeiVar.zzl, zzeiVar.zzb, zzeiVar.zzc);
    }
}
