package com.google.android.play.core.internal;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzd implements zzc {
    private final FileChannel zza;
    private final long zzb;
    private final long zzc;

    public zzd(FileChannel fileChannel, long j, long j2) {
        this.zza = fileChannel;
        this.zzb = j;
        this.zzc = j2;
    }

    @Override // com.google.android.play.core.internal.zzc
    public final long zza() {
        return this.zzc;
    }

    @Override // com.google.android.play.core.internal.zzc
    public final void zzb(MessageDigest[] messageDigestArr, long j, int i) throws IOException {
        MappedByteBuffer map = this.zza.map(FileChannel.MapMode.READ_ONLY, this.zzb + j, i);
        map.load();
        for (MessageDigest messageDigest : messageDigestArr) {
            map.position(0);
            messageDigest.update(map);
        }
    }
}
