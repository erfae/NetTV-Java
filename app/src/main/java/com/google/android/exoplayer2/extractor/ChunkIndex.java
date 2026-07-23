package com.google.android.exoplayer2.extractor;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class ChunkIndex implements SeekMap {
    private final long durationUs;
    public final long[] durationsUs;
    public final int length;
    public final long[] offsets;
    public final int[] sizes;
    public final long[] timesUs;

    public ChunkIndex(int[] iArr, long[] jArr, long[] jArr2, long[] jArr3) {
        this.sizes = iArr;
        this.offsets = jArr;
        this.durationsUs = jArr2;
        this.timesUs = jArr3;
        int length = iArr.length;
        this.length = length;
        if (length > 0) {
            this.durationUs = jArr2[length - 1] + jArr3[length - 1];
        } else {
            this.durationUs = 0L;
        }
    }

    public int getChunkIndex(long j) {
        return Util.binarySearchFloor(this.timesUs, j, true, true);
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j) {
        int chunkIndex = getChunkIndex(j);
        SeekPoint seekPoint = new SeekPoint(this.timesUs[chunkIndex], this.offsets[chunkIndex]);
        if (seekPoint.timeUs >= j || chunkIndex == this.length - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i = chunkIndex + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(this.timesUs[i], this.offsets[i]));
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("ChunkIndex(length=");
        sbM.append(this.length);
        sbM.append(", sizes=");
        sbM.append(Arrays.toString(this.sizes));
        sbM.append(", offsets=");
        sbM.append(Arrays.toString(this.offsets));
        sbM.append(", timeUs=");
        sbM.append(Arrays.toString(this.timesUs));
        sbM.append(", durationsUs=");
        sbM.append(Arrays.toString(this.durationsUs));
        sbM.append(")");
        return sbM.toString();
    }
}
