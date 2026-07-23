package com.google.android.play.core.assetpacks;

import com.google.android.play.core.assetpacks.model.AssetPackStatus;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzda {
    public final String zza;
    public final long zzb;
    public final String zzc;

    @AssetPackStatus
    public int zzd;
    public final long zze;
    public final List zzf;

    public zzda(String str, long j, @AssetPackStatus int i, long j2, List list, String str2) {
        this.zza = str;
        this.zzb = j;
        this.zzd = i;
        this.zze = j2;
        this.zzf = list;
        this.zzc = str2;
    }
}
