package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class ByteArrayDataSource extends BaseDataSource {
    private int bytesRemaining;
    private final byte[] data;
    private boolean opened;
    private int readPosition;

    @Nullable
    private Uri uri;

    public ByteArrayDataSource(byte[] bArr) {
        super(false);
        Assertions.checkNotNull(bArr);
        Assertions.checkArgument(bArr.length > 0);
        this.data = bArr;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.upstream.HttpDataSource
    public void close() {
        if (this.opened) {
            this.opened = false;
            transferEnded();
        }
        this.uri = null;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.upstream.HttpDataSource
    public long open(DataSpec dataSpec) throws IOException {
        this.uri = dataSpec.uri;
        transferInitializing(dataSpec);
        long j = dataSpec.position;
        byte[] bArr = this.data;
        if (j > bArr.length) {
            throw new DataSourceException(2008);
        }
        this.readPosition = (int) j;
        int length = bArr.length - ((int) j);
        this.bytesRemaining = length;
        long j2 = dataSpec.length;
        if (j2 != -1) {
            this.bytesRemaining = (int) Math.min(length, j2);
        }
        this.opened = true;
        transferStarted(dataSpec);
        long j3 = dataSpec.length;
        return j3 != -1 ? j3 : this.bytesRemaining;
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader, com.google.android.exoplayer2.upstream.HttpDataSource
    public int read(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return 0;
        }
        int i3 = this.bytesRemaining;
        if (i3 == 0) {
            return -1;
        }
        int iMin = Math.min(i2, i3);
        System.arraycopy(this.data, this.readPosition, bArr, i, iMin);
        this.readPosition += iMin;
        this.bytesRemaining -= iMin;
        bytesTransferred(iMin);
        return iMin;
    }
}
