package com.google.android.play.core.assetpacks;

import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbq extends zzet {
    private final String zza;
    private final long zzb;
    private final int zzc;
    private final boolean zzd;
    private final boolean zze;
    private final byte[] zzf;

    public zzbq(@Nullable String str, long j, int i, boolean z, boolean z2, @Nullable byte[] bArr) {
        this.zza = str;
        this.zzb = j;
        this.zzc = i;
        this.zzd = z;
        this.zze = z2;
        this.zzf = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzet) {
            zzet zzetVar = (zzet) obj;
            String str = this.zza;
            if (str != null ? str.equals(zzetVar.zzc()) : zzetVar.zzc() == null) {
                if (this.zzb == zzetVar.zzb() && this.zzc == zzetVar.zza() && this.zzd == zzetVar.zze() && this.zze == zzetVar.zzd()) {
                    if (Arrays.equals(this.zzf, zzetVar instanceof zzbq ? ((zzbq) zzetVar).zzf : zzetVar.zzf())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        String str = this.zza;
        int iHashCode = str == null ? 0 : str.hashCode();
        long j = this.zzb;
        return ((((((((((iHashCode ^ 1000003) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ this.zzc) * 1000003) ^ (true != this.zzd ? 1237 : 1231)) * 1000003) ^ (true == this.zze ? 1231 : 1237)) * 1000003) ^ Arrays.hashCode(this.zzf);
    }

    public final String toString() {
        String str = this.zza;
        long j = this.zzb;
        int i = this.zzc;
        boolean z = this.zzd;
        boolean z2 = this.zze;
        String string = Arrays.toString(this.zzf);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 126 + String.valueOf(string).length());
        sb.append("ZipEntry{name=");
        sb.append(str);
        sb.append(", size=");
        sb.append(j);
        sb.append(", compressionMethod=");
        sb.append(i);
        sb.append(", isPartial=");
        sb.append(z);
        sb.append(", isEndOfArchive=");
        sb.append(z2);
        return Insets$$ExternalSyntheticOutline0.m(sb, ", headerBytes=", string, "}");
    }

    @Override // com.google.android.play.core.assetpacks.zzet
    public final int zza() {
        return this.zzc;
    }

    @Override // com.google.android.play.core.assetpacks.zzet
    public final long zzb() {
        return this.zzb;
    }

    @Override // com.google.android.play.core.assetpacks.zzet
    @Nullable
    public final String zzc() {
        return this.zza;
    }

    @Override // com.google.android.play.core.assetpacks.zzet
    public final boolean zzd() {
        return this.zze;
    }

    @Override // com.google.android.play.core.assetpacks.zzet
    public final boolean zze() {
        return this.zzd;
    }

    @Override // com.google.android.play.core.assetpacks.zzet
    @Nullable
    public final byte[] zzf() {
        return this.zzf;
    }
}
