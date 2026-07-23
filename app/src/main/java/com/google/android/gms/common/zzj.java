package com.google.android.gms.common;

import java.util.Arrays;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzj extends zzi {
    private final byte[] zza;

    public zzj(byte[] bArr) {
        super(Arrays.copyOfRange(bArr, 0, 25));
        this.zza = bArr;
    }

    @Override // com.google.android.gms.common.zzi
    public final byte[] zzf() {
        return this.zza;
    }
}
