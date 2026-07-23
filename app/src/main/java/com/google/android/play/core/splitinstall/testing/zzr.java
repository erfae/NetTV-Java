package com.google.android.play.core.splitinstall.testing;

import com.google.android.play.core.internal.zzcq;
import com.google.android.play.core.internal.zzcs;
import com.google.android.play.core.splitinstall.zzad;
import java.io.File;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzr implements zzcs {
    private final zzcs zza;
    private final zzcs zzb;
    private final zzcs zzc;
    private final zzcs zzd;

    public zzr(zzcs zzcsVar, zzcs zzcsVar2, zzcs zzcsVar3, zzcs zzcsVar4) {
        this.zza = zzcsVar;
        this.zzb = zzcsVar2;
        this.zzc = zzcsVar3;
        this.zzd = zzcsVar4;
    }

    @Override // com.google.android.play.core.internal.zzcs
    public final /* bridge */ /* synthetic */ Object zza() {
        return new FakeSplitInstallManager(((zzad) this.zza).zzb(), (File) this.zzb.zza(), (com.google.android.play.core.splitinstall.zzs) this.zzc.zza(), zzcq.zzb(this.zzd));
    }
}
