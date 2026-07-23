package com.google.android.play.core.assetpacks;

import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcl {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("ExtractorLooper");
    private final zzde zzb;
    private final zzcf zzc;
    private final zzer zzd;
    private final zzdu zze;
    private final zzdz zzf;
    private final zzeg zzg;
    private final zzek zzh;
    private final com.google.android.play.core.internal.zzco zzi;
    private final zzdh zzj;
    private final AtomicBoolean zzk = new AtomicBoolean(false);

    public zzcl(zzde zzdeVar, com.google.android.play.core.internal.zzco zzcoVar, zzcf zzcfVar, zzer zzerVar, zzdu zzduVar, zzdz zzdzVar, zzeg zzegVar, zzek zzekVar, zzdh zzdhVar) {
        this.zzb = zzdeVar;
        this.zzi = zzcoVar;
        this.zzc = zzcfVar;
        this.zzd = zzerVar;
        this.zze = zzduVar;
        this.zzf = zzdzVar;
        this.zzg = zzegVar;
        this.zzh = zzekVar;
        this.zzj = zzdhVar;
    }

    private final void zzb(int i, Exception exc) {
        try {
            this.zzb.zzm(i);
            this.zzb.zzn(i);
        } catch (zzck unused) {
            zza.zzb("Error during error handling: %s", exc.getMessage());
        }
    }

    public final void zza() {
        com.google.android.play.core.internal.zzag zzagVar = zza;
        zzagVar.zza("Run extractor loop", new Object[0]);
        if (!this.zzk.compareAndSet(false, true)) {
            zzagVar.zze("runLoop already looping; return", new Object[0]);
            return;
        }
        while (true) {
            zzdg zzdgVarZza = null;
            try {
                zzdgVarZza = this.zzj.zza();
            } catch (zzck e) {
                zza.zzb("Error while getting next extraction task: %s", e.getMessage());
                if (e.zza >= 0) {
                    ((zzy) this.zzi.zza()).zzi(e.zza);
                    zzb(e.zza, e);
                }
            }
            if (zzdgVarZza == null) {
                this.zzk.set(false);
                return;
            }
            try {
                if (zzdgVarZza instanceof zzce) {
                    this.zzc.zza((zzce) zzdgVarZza);
                } else if (zzdgVarZza instanceof zzeq) {
                    this.zzd.zza((zzeq) zzdgVarZza);
                } else if (zzdgVarZza instanceof zzdt) {
                    this.zze.zza((zzdt) zzdgVarZza);
                } else if (zzdgVarZza instanceof zzdw) {
                    this.zzf.zza((zzdw) zzdgVarZza);
                } else if (zzdgVarZza instanceof zzef) {
                    this.zzg.zza((zzef) zzdgVarZza);
                } else if (zzdgVarZza instanceof zzei) {
                    this.zzh.zza((zzei) zzdgVarZza);
                } else {
                    zza.zzb("Unknown task type: %s", zzdgVarZza.getClass().getName());
                }
            } catch (Exception e2) {
                zza.zzb("Error during extraction task: %s", e2.getMessage());
                ((zzy) this.zzi.zza()).zzi(zzdgVarZza.zzk);
                zzb(zzdgVarZza.zzk, e2);
            }
        }
    }
}
