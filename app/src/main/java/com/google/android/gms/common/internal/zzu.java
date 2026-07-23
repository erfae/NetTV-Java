package com.google.android.gms.common.internal;

import androidx.annotation.Nullable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
public final class zzu {

    @Nullable
    private final String zza;
    private final String zzb;
    private final int zzc;
    private final boolean zzd;

    public zzu(String str, @Nullable String str2, boolean z, int i, boolean z2) {
        this.zzb = str;
        this.zza = str2;
        this.zzc = i;
        this.zzd = z2;
    }

    public final int zza() {
        return this.zzc;
    }

    public final String zzb() {
        return this.zzb;
    }

    @Nullable
    public final String zzc() {
        return this.zza;
    }

    public final boolean zzd() {
        return this.zzd;
    }
}
