package com.google.android.gms.common;

import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
abstract class zzk extends zzi {
    private static final WeakReference<byte[]> zza = new WeakReference<>(null);
    private WeakReference<byte[]> zzb;

    public zzk(byte[] bArr) {
        super(bArr);
        this.zzb = zza;
    }

    public abstract byte[] zzb();

    @Override // com.google.android.gms.common.zzi
    public final byte[] zzf() {
        byte[] bArrZzb;
        synchronized (this) {
            bArrZzb = this.zzb.get();
            if (bArrZzb == null) {
                bArrZzb = zzb();
                this.zzb = new WeakReference<>(bArrZzb);
            }
        }
        return bArrZzb;
    }
}
