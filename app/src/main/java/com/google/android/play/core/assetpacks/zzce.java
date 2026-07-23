package com.google.android.play.core.assetpacks;

import com.google.android.play.core.assetpacks.model.AssetPackStatus;
import java.io.InputStream;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzce extends zzdg {
    public final int zza;
    public final long zzb;
    public final String zzc;
    public final String zzd;
    public final int zze;
    public final int zzf;
    public final int zzg;
    public final long zzh;

    @AssetPackStatus
    public final int zzi;
    public final InputStream zzj;

    public zzce(int i, String str, int i2, long j, String str2, String str3, int i3, int i4, int i5, long j2, @AssetPackStatus int i6, InputStream inputStream) {
        super(i, str);
        this.zza = i2;
        this.zzb = j;
        this.zzc = str2;
        this.zzd = str3;
        this.zze = i3;
        this.zzf = i4;
        this.zzg = i5;
        this.zzh = j2;
        this.zzi = i6;
        this.zzj = inputStream;
    }

    public final boolean zza() {
        return this.zzf + 1 == this.zzg;
    }
}
