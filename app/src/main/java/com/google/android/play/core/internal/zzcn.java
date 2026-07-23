package com.google.android.play.core.internal;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzcn extends zzcm {
    private final zzcm zza;
    private final long zzb;
    private final long zzc;

    public zzcn(zzcm zzcmVar, long j, long j2) {
        this.zza = zzcmVar;
        long jZzd = zzd(j);
        this.zzb = jZzd;
        this.zzc = zzd(jZzd + j2);
    }

    private final long zzd(long j) {
        if (j < 0) {
            return 0L;
        }
        return j > this.zza.zza() ? this.zza.zza() : j;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
    }

    @Override // com.google.android.play.core.internal.zzcm
    public final long zza() {
        return this.zzc - this.zzb;
    }

    @Override // com.google.android.play.core.internal.zzcm
    public final InputStream zzb(long j, long j2) throws IOException {
        long jZzd = zzd(this.zzb);
        return this.zza.zzb(jZzd, zzd(j2 + jZzd) - jZzd);
    }
}
