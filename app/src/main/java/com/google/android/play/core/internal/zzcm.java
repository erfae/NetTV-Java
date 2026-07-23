package com.google.android.play.core.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzcm implements Closeable {
    public abstract long zza();

    public abstract InputStream zzb(long j, long j2) throws IOException;

    public final synchronized InputStream zzc() throws IOException {
        return zzb(0L, zza());
    }
}
