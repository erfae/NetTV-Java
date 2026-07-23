package com.google.android.play.core.assetpacks;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzck extends RuntimeException {
    public final int zza;

    public zzck(String str) {
        super(str);
        this.zza = -1;
    }

    public zzck(String str, int i) {
        super(str);
        this.zza = i;
    }

    public zzck(String str, Exception exc) {
        super(str, exc);
        this.zza = -1;
    }

    public zzck(String str, Exception exc, int i) {
        super(str, exc);
        this.zza = i;
    }
}
