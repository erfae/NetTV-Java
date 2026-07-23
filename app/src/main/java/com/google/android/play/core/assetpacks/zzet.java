package com.google.android.play.core.assetpacks;

import androidx.annotation.Nullable;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
abstract class zzet {
    public abstract int zza();

    public abstract long zzb();

    @Nullable
    public abstract String zzc();

    public abstract boolean zzd();

    public abstract boolean zze();

    @Nullable
    public abstract byte[] zzf();

    public final boolean zzg() {
        if (zzc() == null) {
            return false;
        }
        return zzc().endsWith("/");
    }

    public final boolean zzh() {
        return zza() == 0;
    }
}
