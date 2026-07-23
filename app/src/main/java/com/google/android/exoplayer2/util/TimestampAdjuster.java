package com.google.android.exoplayer2.util;

import androidx.annotation.GuardedBy;
import com.google.android.exoplayer2.C;

/* JADX INFO: loaded from: classes.dex */
public final class TimestampAdjuster {
    private static final long MAX_PTS_PLUS_ONE = 8589934592L;
    public static final long MODE_NO_OFFSET = Long.MAX_VALUE;
    public static final long MODE_SHARED = 9223372036854775806L;

    @GuardedBy("this")
    private long firstSampleTimestampUs;

    @GuardedBy("this")
    private long lastUnadjustedTimestampUs;
    private final ThreadLocal<Long> nextSampleTimestampUs = new ThreadLocal<>();

    @GuardedBy("this")
    private long timestampOffsetUs;

    public TimestampAdjuster(long j) {
        reset(j);
    }

    public static long ptsToUs(long j) {
        return (j * 1000000) / 90000;
    }

    public static long usToNonWrappedPts(long j) {
        return (j * 90000) / 1000000;
    }

    public static long usToWrappedPts(long j) {
        return usToNonWrappedPts(j) % MAX_PTS_PLUS_ONE;
    }

    public synchronized long adjustSampleTimestamp(long j) {
        if (j == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        if (this.timestampOffsetUs == C.TIME_UNSET) {
            long jLongValue = this.firstSampleTimestampUs;
            if (jLongValue == MODE_SHARED) {
                jLongValue = ((Long) Assertions.checkNotNull(this.nextSampleTimestampUs.get())).longValue();
            }
            this.timestampOffsetUs = jLongValue - j;
            notifyAll();
        }
        this.lastUnadjustedTimestampUs = j;
        return j + this.timestampOffsetUs;
    }

    public synchronized long adjustTsTimestamp(long j) {
        if (j == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        long j2 = this.lastUnadjustedTimestampUs;
        if (j2 != C.TIME_UNSET) {
            long jUsToNonWrappedPts = usToNonWrappedPts(j2);
            long j3 = (4294967296L + jUsToNonWrappedPts) / MAX_PTS_PLUS_ONE;
            long j4 = ((j3 - 1) * MAX_PTS_PLUS_ONE) + j;
            long j5 = (j3 * MAX_PTS_PLUS_ONE) + j;
            j = Math.abs(j4 - jUsToNonWrappedPts) < Math.abs(j5 - jUsToNonWrappedPts) ? j4 : j5;
        }
        return adjustSampleTimestamp(ptsToUs(j));
    }

    public synchronized long getFirstSampleTimestampUs() {
        long j;
        j = this.firstSampleTimestampUs;
        if (j == Long.MAX_VALUE || j == MODE_SHARED) {
            j = C.TIME_UNSET;
        }
        return j;
    }

    public synchronized long getLastAdjustedTimestampUs() {
        long j;
        j = this.lastUnadjustedTimestampUs;
        return j != C.TIME_UNSET ? j + this.timestampOffsetUs : getFirstSampleTimestampUs();
    }

    public synchronized long getTimestampOffsetUs() {
        return this.timestampOffsetUs;
    }

    public synchronized void reset(long j) {
        this.firstSampleTimestampUs = j;
        this.timestampOffsetUs = j == Long.MAX_VALUE ? 0L : -9223372036854775807L;
        this.lastUnadjustedTimestampUs = C.TIME_UNSET;
    }

    public synchronized void sharedInitializeOrWait(boolean z, long j) throws InterruptedException {
        Assertions.checkState(this.firstSampleTimestampUs == MODE_SHARED);
        if (this.timestampOffsetUs != C.TIME_UNSET) {
            return;
        }
        if (z) {
            this.nextSampleTimestampUs.set(Long.valueOf(j));
        } else {
            while (this.timestampOffsetUs == C.TIME_UNSET) {
                wait();
            }
        }
    }
}
