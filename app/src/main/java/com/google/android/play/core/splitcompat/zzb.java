package com.google.android.play.core.splitcompat;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.File;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzb extends zzs {
    private final File zza;
    private final String zzb;

    public zzb(File file, String str) {
        this.zza = file;
        Objects.requireNonNull(str, "Null splitId");
        this.zzb = str;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzs) {
            zzs zzsVar = (zzs) obj;
            if (this.zza.equals(zzsVar.zza()) && this.zzb.equals(zzsVar.zzb())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb.hashCode();
    }

    public final String toString() {
        String string = this.zza.toString();
        String str = this.zzb;
        StringBuilder sb = new StringBuilder(str.length() + string.length() + 35);
        Insets$$ExternalSyntheticOutline0.m29m(sb, "SplitFileInfo{splitFile=", string, ", splitId=", str);
        sb.append("}");
        return sb.toString();
    }

    @Override // com.google.android.play.core.splitcompat.zzs
    @NonNull
    public final File zza() {
        return this.zza;
    }

    @Override // com.google.android.play.core.splitcompat.zzs
    @NonNull
    public final String zzb() {
        return this.zzb;
    }
}
