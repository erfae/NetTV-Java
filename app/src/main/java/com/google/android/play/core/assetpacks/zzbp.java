package com.google.android.play.core.assetpacks;

import androidx.annotation.Nullable;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbp extends zzem {
    private final int zza;
    private final String zzb;
    private final long zzc;
    private final long zzd;
    private final int zze;

    public zzbp(int i, @Nullable String str, long j, long j2, int i2) {
        this.zza = i;
        this.zzb = str;
        this.zzc = j;
        this.zzd = j2;
        this.zze = i2;
    }

    public final boolean equals(Object obj) {
        String str;
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzem) {
            zzem zzemVar = (zzem) obj;
            if (this.zza == zzemVar.zza() && ((str = this.zzb) != null ? str.equals(zzemVar.zze()) : zzemVar.zze() == null) && this.zzc == zzemVar.zzc() && this.zzd == zzemVar.zzd() && this.zze == zzemVar.zzb()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = (this.zza ^ 1000003) * 1000003;
        String str = this.zzb;
        int iHashCode = str == null ? 0 : str.hashCode();
        long j = this.zzc;
        long j2 = this.zzd;
        return ((((((i ^ iHashCode) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003) ^ this.zze;
    }

    public final String toString() {
        int i = this.zza;
        String str = this.zzb;
        long j = this.zzc;
        long j2 = this.zzd;
        int i2 = this.zze;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 157);
        sb.append("SliceCheckpoint{fileExtractionStatus=");
        sb.append(i);
        sb.append(", filePath=");
        sb.append(str);
        sb.append(", fileOffset=");
        sb.append(j);
        sb.append(", remainingBytes=");
        sb.append(j2);
        sb.append(", previousChunk=");
        sb.append(i2);
        sb.append("}");
        return sb.toString();
    }

    @Override // com.google.android.play.core.assetpacks.zzem
    public final int zza() {
        return this.zza;
    }

    @Override // com.google.android.play.core.assetpacks.zzem
    public final int zzb() {
        return this.zze;
    }

    @Override // com.google.android.play.core.assetpacks.zzem
    public final long zzc() {
        return this.zzc;
    }

    @Override // com.google.android.play.core.assetpacks.zzem
    public final long zzd() {
        return this.zzd;
    }

    @Override // com.google.android.play.core.assetpacks.zzem
    @Nullable
    public final String zze() {
        return this.zzb;
    }
}
