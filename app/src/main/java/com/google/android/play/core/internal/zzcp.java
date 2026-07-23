package com.google.android.play.core.internal;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzcp implements zzcs {
    private zzcs zza;

    public static void zzb(zzcs zzcsVar, zzcs zzcsVar2) {
        zzcp zzcpVar = (zzcp) zzcsVar;
        if (zzcpVar.zza != null) {
            throw new IllegalStateException();
        }
        zzcpVar.zza = zzcsVar2;
    }

    @Override // com.google.android.play.core.internal.zzcs
    public final Object zza() {
        zzcs zzcsVar = this.zza;
        if (zzcsVar != null) {
            return zzcsVar.zza();
        }
        throw new IllegalStateException();
    }
}
