package com.google.android.play.core.splitinstall.testing;

import java.util.List;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzo implements com.google.android.play.core.splitinstall.zzf {
    public final /* synthetic */ List zza;
    public final /* synthetic */ List zzb;
    public final /* synthetic */ long zzc;
    public final /* synthetic */ boolean zzd;
    public final /* synthetic */ List zze;
    public final /* synthetic */ FakeSplitInstallManager zzf;

    public zzo(FakeSplitInstallManager fakeSplitInstallManager, List list, List list2, long j, boolean z, List list3) {
        this.zzf = fakeSplitInstallManager;
        this.zza = list;
        this.zzb = list2;
        this.zzc = j;
        this.zzd = z;
        this.zze = list3;
    }

    @Override // com.google.android.play.core.splitinstall.zzf
    public final void zza() {
        this.zzf.zzr(this.zza, this.zzb, this.zzc);
    }

    @Override // com.google.android.play.core.splitinstall.zzf
    public final void zzb(int i) {
        this.zzf.zzs(6, i, null, null, null, null, null);
    }

    @Override // com.google.android.play.core.splitinstall.zzf
    public final void zzc() {
        if (this.zzd) {
            return;
        }
        this.zzf.zzp(this.zze, this.zza, this.zzb, this.zzc, true);
    }
}
