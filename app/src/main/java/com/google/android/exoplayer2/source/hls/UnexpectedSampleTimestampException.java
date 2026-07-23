package com.google.android.exoplayer2.source.hls;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class UnexpectedSampleTimestampException extends IOException {
    public final long lastAcceptedSampleTimeUs;
    public final MediaChunk mediaChunk;
    public final long rejectedSampleTimeUs;

    /* JADX WARN: Illegal instructions before constructor call */
    public UnexpectedSampleTimestampException(MediaChunk mediaChunk, long j, long j2) {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Unexpected sample timestamp: ");
        sbM.append(Util.usToMs(j2));
        sbM.append(" in chunk [");
        sbM.append(mediaChunk.startTimeUs);
        sbM.append(", ");
        sbM.append(mediaChunk.endTimeUs);
        sbM.append("]");
        super(sbM.toString());
        this.mediaChunk = mediaChunk;
        this.lastAcceptedSampleTimeUs = j;
        this.rejectedSampleTimeUs = j2;
    }
}
