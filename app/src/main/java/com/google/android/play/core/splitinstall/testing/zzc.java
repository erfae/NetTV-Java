package com.google.android.play.core.splitinstall.testing;

import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzc extends zzt {
    private final Integer zzb;
    private final Map zzc;

    public /* synthetic */ zzc(Integer num, Map map) {
        this.zzb = num;
        this.zzc = map;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzt) {
            zzt zztVar = (zzt) obj;
            Integer num = this.zzb;
            if (num != null ? num.equals(zztVar.zza()) : zztVar.zza() == null) {
                if (this.zzc.equals(zztVar.zzb())) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        Integer num = this.zzb;
        return (((num == null ? 0 : num.hashCode()) ^ 1000003) * 1000003) ^ this.zzc.hashCode();
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzb);
        String strValueOf2 = String.valueOf(this.zzc);
        StringBuilder sb = new StringBuilder(strValueOf.length() + 81 + strValueOf2.length());
        Insets$$ExternalSyntheticOutline0.m29m(sb, "LocalTestingConfig{defaultSplitInstallErrorCode=", strValueOf, ", splitInstallErrorCodeByModule=", strValueOf2);
        sb.append("}");
        return sb.toString();
    }

    @Override // com.google.android.play.core.splitinstall.testing.zzt
    @Nullable
    @SplitInstallErrorCode
    public final Integer zza() {
        return this.zzb;
    }

    @Override // com.google.android.play.core.splitinstall.testing.zzt
    public final Map zzb() {
        return this.zzc;
    }
}
