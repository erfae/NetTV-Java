package com.google.android.play.core.assetpacks;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdz {
    private final zzbh zza;
    private final com.google.android.play.core.internal.zzco zzb;
    private final zzde zzc;
    private final com.google.android.play.core.internal.zzco zzd;
    private final zzco zze;
    private final com.google.android.play.core.common.zza zzf;
    private final zzeb zzg;

    public zzdz(zzbh zzbhVar, com.google.android.play.core.internal.zzco zzcoVar, zzde zzdeVar, com.google.android.play.core.internal.zzco zzcoVar2, zzco zzcoVar3, com.google.android.play.core.common.zza zzaVar, zzeb zzebVar) {
        this.zza = zzbhVar;
        this.zzb = zzcoVar;
        this.zzc = zzdeVar;
        this.zzd = zzcoVar2;
        this.zze = zzcoVar3;
        this.zzf = zzaVar;
        this.zzg = zzebVar;
    }

    public final void zza(final zzdw zzdwVar) {
        File fileZzj = this.zza.zzj(zzdwVar.zzl, zzdwVar.zza, zzdwVar.zzb);
        File fileZzl = this.zza.zzl(zzdwVar.zzl, zzdwVar.zza, zzdwVar.zzb);
        if (!fileZzj.exists() || !fileZzl.exists()) {
            throw new zzck(String.format("Cannot find pack files to move for pack %s.", zzdwVar.zzl), zzdwVar.zzk);
        }
        File fileZzh = this.zza.zzh(zzdwVar.zzl, zzdwVar.zza, zzdwVar.zzb);
        fileZzh.mkdirs();
        if (!fileZzj.renameTo(fileZzh)) {
            throw new zzck("Cannot move merged pack files to final location.", zzdwVar.zzk);
        }
        new File(this.zza.zzh(zzdwVar.zzl, zzdwVar.zza, zzdwVar.zzb), "merge.tmp").delete();
        File fileZzi = this.zza.zzi(zzdwVar.zzl, zzdwVar.zza, zzdwVar.zzb);
        fileZzi.mkdirs();
        if (!fileZzl.renameTo(fileZzi)) {
            throw new zzck("Cannot move metadata files to final location.", zzdwVar.zzk);
        }
        if (this.zzf.zza("assetOnlyUpdates")) {
            try {
                this.zzg.zzb(zzdwVar.zzl, zzdwVar.zza, zzdwVar.zzb, zzdwVar.zzc);
                ((Executor) this.zzd.zza()).execute(new Runnable() { // from class: com.google.android.play.core.assetpacks.zzdy
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.zza.zzb(zzdwVar);
                    }
                });
            } catch (IOException e) {
                throw new zzck(String.format("Could not write asset pack version tag for pack %s: %s", zzdwVar.zzl, e.getMessage()), zzdwVar.zzk);
            }
        } else {
            Executor executor = (Executor) this.zzd.zza();
            final zzbh zzbhVar = this.zza;
            Objects.requireNonNull(zzbhVar);
            executor.execute(new Runnable() { // from class: com.google.android.play.core.assetpacks.zzdx
                @Override // java.lang.Runnable
                public final void run() {
                    zzbhVar.zzw();
                }
            });
        }
        this.zzc.zzk(zzdwVar.zzl, zzdwVar.zza, zzdwVar.zzb);
        this.zze.zzc(zzdwVar.zzl);
        ((zzy) this.zzb.zza()).zzh(zzdwVar.zzk, zzdwVar.zzl);
    }

    public final /* synthetic */ void zzb(zzdw zzdwVar) {
        this.zza.zzB(zzdwVar.zzl, zzdwVar.zza, zzdwVar.zzb);
    }
}
