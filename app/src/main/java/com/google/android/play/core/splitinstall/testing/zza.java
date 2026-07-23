package com.google.android.play.core.splitinstall.testing;

import java.util.Map;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zza extends zzs {
    private Integer zza;
    private Map zzb;

    @Override // com.google.android.play.core.splitinstall.testing.zzs
    public final zzs zza(int i) {
        this.zza = Integer.valueOf(i);
        return this;
    }

    @Override // com.google.android.play.core.splitinstall.testing.zzs
    public final zzs zzb(Map map) {
        Objects.requireNonNull(map, "Null splitInstallErrorCodeByModule");
        this.zzb = map;
        return this;
    }

    @Override // com.google.android.play.core.splitinstall.testing.zzs
    public final zzt zzc() {
        if (this.zzb != null) {
            return new zzc(this.zza, this.zzb);
        }
        throw new IllegalStateException("Missing required properties: splitInstallErrorCodeByModule");
    }

    @Override // com.google.android.play.core.splitinstall.testing.zzs
    public final Map zzd() {
        Map map = this.zzb;
        if (map != null) {
            return map;
        }
        throw new IllegalStateException("Property \"splitInstallErrorCodeByModule\" has not been set");
    }
}
