package com.google.android.play.core.assetpacks;

import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzdr extends InputStream {
    private final Enumeration zza;

    @Nullable
    private InputStream zzb;

    public zzdr(Enumeration enumeration) throws IOException {
        this.zza = enumeration;
        zza();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        super.close();
        InputStream inputStream = this.zzb;
        if (inputStream != null) {
            inputStream.close();
            this.zzb = null;
        }
    }

    @Override // java.io.InputStream
    public final int read() throws IOException {
        while (true) {
            InputStream inputStream = this.zzb;
            if (inputStream == null) {
                return -1;
            }
            int i = inputStream.read();
            if (i != -1) {
                return i;
            }
            zza();
        }
    }

    public final void zza() throws IOException {
        InputStream inputStream = this.zzb;
        if (inputStream != null) {
            inputStream.close();
        }
        this.zzb = this.zza.hasMoreElements() ? new FileInputStream((File) this.zza.nextElement()) : null;
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.zzb == null) {
            return -1;
        }
        Objects.requireNonNull(bArr);
        if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return 0;
        }
        do {
            int i3 = this.zzb.read(bArr, i, i2);
            if (i3 > 0) {
                return i3;
            }
            zza();
        } while (this.zzb != null);
        return -1;
    }
}
