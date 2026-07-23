package com.google.android.exoplayer2.analytics;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class PlaybackStats {
    public static final PlaybackStats EMPTY = merge(new PlaybackStats[0]);
    public static final int PLAYBACK_STATE_ABANDONED = 15;
    public static final int PLAYBACK_STATE_BUFFERING = 6;
    public static final int PLAYBACK_STATE_ENDED = 11;
    public static final int PLAYBACK_STATE_FAILED = 13;
    public static final int PLAYBACK_STATE_INTERRUPTED_BY_AD = 14;
    public static final int PLAYBACK_STATE_JOINING_BACKGROUND = 1;
    public static final int PLAYBACK_STATE_JOINING_FOREGROUND = 2;
    public static final int PLAYBACK_STATE_NOT_STARTED = 0;
    public static final int PLAYBACK_STATE_PAUSED = 4;
    public static final int PLAYBACK_STATE_PAUSED_BUFFERING = 7;
    public static final int PLAYBACK_STATE_PLAYING = 3;
    public static final int PLAYBACK_STATE_SEEKING = 5;
    public static final int PLAYBACK_STATE_STOPPED = 12;
    public static final int PLAYBACK_STATE_SUPPRESSED = 9;
    public static final int PLAYBACK_STATE_SUPPRESSED_BUFFERING = 10;
    public final int abandonedBeforeReadyCount;
    public final int adPlaybackCount;
    public final List<EventTimeAndFormat> audioFormatHistory;
    public final int backgroundJoiningCount;
    public final int endedCount;
    public final int fatalErrorCount;
    public final List<EventTimeAndException> fatalErrorHistory;
    public final int fatalErrorPlaybackCount;
    public final long firstReportedTimeMs;
    public final int foregroundPlaybackCount;
    public final int initialAudioFormatBitrateCount;
    public final int initialVideoFormatBitrateCount;
    public final int initialVideoFormatHeightCount;
    public final long maxRebufferTimeMs;
    public final List<long[]> mediaTimeHistory;
    public final int nonFatalErrorCount;
    public final List<EventTimeAndException> nonFatalErrorHistory;
    public final int playbackCount;
    private final long[] playbackStateDurationsMs;
    public final List<EventTimeAndPlaybackState> playbackStateHistory;
    public final long totalAudioFormatBitrateTimeProduct;
    public final long totalAudioFormatTimeMs;
    public final long totalAudioUnderruns;
    public final long totalBandwidthBytes;
    public final long totalBandwidthTimeMs;
    public final long totalDroppedFrames;
    public final long totalInitialAudioFormatBitrate;
    public final long totalInitialVideoFormatBitrate;
    public final int totalInitialVideoFormatHeight;
    public final int totalPauseBufferCount;
    public final int totalPauseCount;
    public final int totalRebufferCount;
    public final int totalSeekCount;
    public final long totalValidJoinTimeMs;
    public final long totalVideoFormatBitrateTimeMs;
    public final long totalVideoFormatBitrateTimeProduct;
    public final long totalVideoFormatHeightTimeMs;
    public final long totalVideoFormatHeightTimeProduct;
    public final int validJoinTimeCount;
    public final List<EventTimeAndFormat> videoFormatHistory;

    public static final class EventTimeAndException {
        public final AnalyticsListener.EventTime eventTime;
        public final Exception exception;

        public EventTimeAndException(AnalyticsListener.EventTime eventTime, Exception exc) {
            this.eventTime = eventTime;
            this.exception = exc;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EventTimeAndException.class != obj.getClass()) {
                return false;
            }
            EventTimeAndException eventTimeAndException = (EventTimeAndException) obj;
            if (this.eventTime.equals(eventTimeAndException.eventTime)) {
                return this.exception.equals(eventTimeAndException.exception);
            }
            return false;
        }

        public int hashCode() {
            return this.exception.hashCode() + (this.eventTime.hashCode() * 31);
        }
    }

    public static final class EventTimeAndFormat {
        public final AnalyticsListener.EventTime eventTime;

        @Nullable
        public final Format format;

        public EventTimeAndFormat(AnalyticsListener.EventTime eventTime, @Nullable Format format) {
            this.eventTime = eventTime;
            this.format = format;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EventTimeAndFormat.class != obj.getClass()) {
                return false;
            }
            EventTimeAndFormat eventTimeAndFormat = (EventTimeAndFormat) obj;
            if (!this.eventTime.equals(eventTimeAndFormat.eventTime)) {
                return false;
            }
            Format format = this.format;
            Format format2 = eventTimeAndFormat.format;
            if (format != null) {
                return format.equals(format2);
            }
            return format2 == null;
        }

        public int hashCode() {
            int iHashCode = this.eventTime.hashCode() * 31;
            Format format = this.format;
            return iHashCode + (format != null ? format.hashCode() : 0);
        }
    }

    public static final class EventTimeAndPlaybackState {
        public final AnalyticsListener.EventTime eventTime;
        public final int playbackState;

        public EventTimeAndPlaybackState(AnalyticsListener.EventTime eventTime, int i) {
            this.eventTime = eventTime;
            this.playbackState = i;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EventTimeAndPlaybackState.class != obj.getClass()) {
                return false;
            }
            EventTimeAndPlaybackState eventTimeAndPlaybackState = (EventTimeAndPlaybackState) obj;
            if (this.playbackState != eventTimeAndPlaybackState.playbackState) {
                return false;
            }
            return this.eventTime.equals(eventTimeAndPlaybackState.eventTime);
        }

        public int hashCode() {
            return (this.eventTime.hashCode() * 31) + this.playbackState;
        }
    }

    public PlaybackStats(int i, long[] jArr, List<EventTimeAndPlaybackState> list, List<long[]> list2, long j, int i2, int i3, int i4, int i5, long j2, int i6, int i7, int i8, int i9, int i10, long j3, int i11, List<EventTimeAndFormat> list3, List<EventTimeAndFormat> list4, long j4, long j5, long j6, long j7, long j8, long j9, int i12, int i13, int i14, long j10, int i15, long j11, long j12, long j13, long j14, long j15, int i16, int i17, int i18, List<EventTimeAndException> list5, List<EventTimeAndException> list6) {
        this.playbackCount = i;
        this.playbackStateDurationsMs = jArr;
        this.playbackStateHistory = Collections.unmodifiableList(list);
        this.mediaTimeHistory = Collections.unmodifiableList(list2);
        this.firstReportedTimeMs = j;
        this.foregroundPlaybackCount = i2;
        this.abandonedBeforeReadyCount = i3;
        this.endedCount = i4;
        this.backgroundJoiningCount = i5;
        this.totalValidJoinTimeMs = j2;
        this.validJoinTimeCount = i6;
        this.totalPauseCount = i7;
        this.totalPauseBufferCount = i8;
        this.totalSeekCount = i9;
        this.totalRebufferCount = i10;
        this.maxRebufferTimeMs = j3;
        this.adPlaybackCount = i11;
        this.videoFormatHistory = Collections.unmodifiableList(list3);
        this.audioFormatHistory = Collections.unmodifiableList(list4);
        this.totalVideoFormatHeightTimeMs = j4;
        this.totalVideoFormatHeightTimeProduct = j5;
        this.totalVideoFormatBitrateTimeMs = j6;
        this.totalVideoFormatBitrateTimeProduct = j7;
        this.totalAudioFormatTimeMs = j8;
        this.totalAudioFormatBitrateTimeProduct = j9;
        this.initialVideoFormatHeightCount = i12;
        this.initialVideoFormatBitrateCount = i13;
        this.totalInitialVideoFormatHeight = i14;
        this.totalInitialVideoFormatBitrate = j10;
        this.initialAudioFormatBitrateCount = i15;
        this.totalInitialAudioFormatBitrate = j11;
        this.totalBandwidthTimeMs = j12;
        this.totalBandwidthBytes = j13;
        this.totalDroppedFrames = j14;
        this.totalAudioUnderruns = j15;
        this.fatalErrorPlaybackCount = i16;
        this.fatalErrorCount = i17;
        this.nonFatalErrorCount = i18;
        this.fatalErrorHistory = Collections.unmodifiableList(list5);
        this.nonFatalErrorHistory = Collections.unmodifiableList(list6);
    }

    /* JADX WARN: Code duplicated, block: B:34:0x010a  */
    /* JADX WARN: Code duplicated, block: B:35:0x0111  */
    /* JADX WARN: Code duplicated, block: B:37:0x0119  */
    /* JADX WARN: Code duplicated, block: B:40:0x0123  */
    /* JADX WARN: Code duplicated, block: B:41:0x0128  */
    /* JADX WARN: Code duplicated, block: B:43:0x012e  */
    /* JADX WARN: Code duplicated, block: B:50:0x0130 A[SYNTHETIC] */
    public static PlaybackStats merge(PlaybackStats... playbackStatsArr) {
        int i;
        int i2;
        long j;
        long j2;
        int i3 = 16;
        long[] jArr = new long[16];
        int length = playbackStatsArr.length;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        long j6 = 0;
        long j7 = 0;
        long j8 = 0;
        long j9 = 0;
        long j10 = 0;
        long j11 = 0;
        long j12 = 0;
        long jMax = C.TIME_UNSET;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        long jMin = C.TIME_UNSET;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        long j13 = C.TIME_UNSET;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        long j14 = -1;
        int i18 = 0;
        long j15 = -1;
        int i19 = 0;
        int i20 = 0;
        int i21 = -1;
        int i22 = 0;
        while (i6 < length) {
            PlaybackStats playbackStats = playbackStatsArr[i6];
            int i23 = i4 + playbackStats.playbackCount;
            for (int i24 = 0; i24 < i3; i24++) {
                jArr[i24] = jArr[i24] + playbackStats.playbackStateDurationsMs[i24];
            }
            if (jMin == C.TIME_UNSET) {
                jMin = playbackStats.firstReportedTimeMs;
                i = length;
            } else {
                i = length;
                long j16 = playbackStats.firstReportedTimeMs;
                if (j16 != C.TIME_UNSET) {
                    jMin = Math.min(jMin, j16);
                }
            }
            i5 += playbackStats.foregroundPlaybackCount;
            i7 += playbackStats.abandonedBeforeReadyCount;
            i8 += playbackStats.endedCount;
            i9 += playbackStats.backgroundJoiningCount;
            if (j13 == C.TIME_UNSET) {
                j13 = playbackStats.totalValidJoinTimeMs;
            } else {
                long j17 = playbackStats.totalValidJoinTimeMs;
                if (j17 != C.TIME_UNSET) {
                    j13 += j17;
                }
            }
            i10 += playbackStats.validJoinTimeCount;
            i11 += playbackStats.totalPauseCount;
            i12 += playbackStats.totalPauseBufferCount;
            i13 += playbackStats.totalSeekCount;
            i14 += playbackStats.totalRebufferCount;
            if (jMax == C.TIME_UNSET) {
                jMax = playbackStats.maxRebufferTimeMs;
            } else {
                long j18 = playbackStats.maxRebufferTimeMs;
                if (j18 != C.TIME_UNSET) {
                    jMax = Math.max(jMax, j18);
                }
            }
            i15 += playbackStats.adPlaybackCount;
            j3 += playbackStats.totalVideoFormatHeightTimeMs;
            j4 += playbackStats.totalVideoFormatHeightTimeProduct;
            j5 += playbackStats.totalVideoFormatBitrateTimeMs;
            j6 += playbackStats.totalVideoFormatBitrateTimeProduct;
            j7 += playbackStats.totalAudioFormatTimeMs;
            j8 += playbackStats.totalAudioFormatBitrateTimeProduct;
            i16 += playbackStats.initialVideoFormatHeightCount;
            i17 += playbackStats.initialVideoFormatBitrateCount;
            int i25 = i21;
            if (i25 == -1) {
                i25 = playbackStats.totalInitialVideoFormatHeight;
            } else {
                int i26 = playbackStats.totalInitialVideoFormatHeight;
                if (i26 != -1) {
                    i21 = i25 + i26;
                }
                if (j14 == -1) {
                    i2 = i23;
                    j14 = playbackStats.totalInitialVideoFormatBitrate;
                } else {
                    i2 = i23;
                    j = playbackStats.totalInitialVideoFormatBitrate;
                    if (j != -1) {
                        j14 += j;
                    }
                }
                i18 += playbackStats.initialAudioFormatBitrateCount;
                if (j15 == -1) {
                    j15 = playbackStats.totalInitialAudioFormatBitrate;
                } else {
                    j2 = playbackStats.totalInitialAudioFormatBitrate;
                    if (j2 != -1) {
                        j15 += j2;
                    }
                }
                j9 += playbackStats.totalBandwidthTimeMs;
                j10 += playbackStats.totalBandwidthBytes;
                j11 += playbackStats.totalDroppedFrames;
                j12 += playbackStats.totalAudioUnderruns;
                i19 += playbackStats.fatalErrorPlaybackCount;
                i20 += playbackStats.fatalErrorCount;
                i22 += playbackStats.nonFatalErrorCount;
                i6++;
                i4 = i2;
                length = i;
                i3 = 16;
            }
            i21 = i25;
            if (j14 == -1) {
                i2 = i23;
                j14 = playbackStats.totalInitialVideoFormatBitrate;
            } else {
                i2 = i23;
                j = playbackStats.totalInitialVideoFormatBitrate;
                if (j != -1) {
                    j14 += j;
                }
            }
            i18 += playbackStats.initialAudioFormatBitrateCount;
            if (j15 == -1) {
                j15 = playbackStats.totalInitialAudioFormatBitrate;
            } else {
                j2 = playbackStats.totalInitialAudioFormatBitrate;
                if (j2 != -1) {
                    j15 += j2;
                }
            }
            j9 += playbackStats.totalBandwidthTimeMs;
            j10 += playbackStats.totalBandwidthBytes;
            j11 += playbackStats.totalDroppedFrames;
            j12 += playbackStats.totalAudioUnderruns;
            i19 += playbackStats.fatalErrorPlaybackCount;
            i20 += playbackStats.fatalErrorCount;
            i22 += playbackStats.nonFatalErrorCount;
            i6++;
            i4 = i2;
            length = i;
            i3 = 16;
        }
        return new PlaybackStats(i4, jArr, Collections.emptyList(), Collections.emptyList(), jMin, i5, i7, i8, i9, j13, i10, i11, i12, i13, i14, jMax, i15, Collections.emptyList(), Collections.emptyList(), j3, j4, j5, j6, j7, j8, i16, i17, i21, j14, i18, j15, j9, j10, j11, j12, i19, i20, i22, Collections.emptyList(), Collections.emptyList());
    }

    public float getAbandonedBeforeReadyRatio() {
        int i = this.abandonedBeforeReadyCount;
        int i2 = this.playbackCount;
        int i3 = this.foregroundPlaybackCount;
        int i4 = i - (i2 - i3);
        if (i3 == 0) {
            return 0.0f;
        }
        return i4 / i3;
    }

    public float getAudioUnderrunRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.totalAudioUnderruns * 1000.0f) / totalPlayTimeMs;
    }

    public float getDroppedFramesRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.totalDroppedFrames * 1000.0f) / totalPlayTimeMs;
    }

    public float getEndedRatio() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.endedCount / i;
    }

    public float getFatalErrorRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.fatalErrorCount * 1000.0f) / totalPlayTimeMs;
    }

    public float getFatalErrorRatio() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.fatalErrorPlaybackCount / i;
    }

    public float getJoinTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalJoinTimeMs() / totalPlayAndWaitTimeMs;
    }

    public int getMeanAudioFormatBitrate() {
        long j = this.totalAudioFormatTimeMs;
        if (j == 0) {
            return -1;
        }
        return (int) (this.totalAudioFormatBitrateTimeProduct / j);
    }

    public int getMeanBandwidth() {
        long j = this.totalBandwidthTimeMs;
        if (j == 0) {
            return -1;
        }
        return (int) ((this.totalBandwidthBytes * 8000) / j);
    }

    public long getMeanElapsedTimeMs() {
        return this.playbackCount == 0 ? C.TIME_UNSET : getTotalElapsedTimeMs() / ((long) this.playbackCount);
    }

    public int getMeanInitialAudioFormatBitrate() {
        int i = this.initialAudioFormatBitrateCount;
        if (i == 0) {
            return -1;
        }
        return (int) (this.totalInitialAudioFormatBitrate / ((long) i));
    }

    public int getMeanInitialVideoFormatBitrate() {
        int i = this.initialVideoFormatBitrateCount;
        if (i == 0) {
            return -1;
        }
        return (int) (this.totalInitialVideoFormatBitrate / ((long) i));
    }

    public int getMeanInitialVideoFormatHeight() {
        int i = this.initialVideoFormatHeightCount;
        if (i == 0) {
            return -1;
        }
        return this.totalInitialVideoFormatHeight / i;
    }

    public long getMeanJoinTimeMs() {
        int i = this.validJoinTimeCount;
        return i == 0 ? C.TIME_UNSET : this.totalValidJoinTimeMs / ((long) i);
    }

    public float getMeanNonFatalErrorCount() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.nonFatalErrorCount / i;
    }

    public float getMeanPauseBufferCount() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.totalPauseBufferCount / i;
    }

    public float getMeanPauseCount() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.totalPauseCount / i;
    }

    public long getMeanPausedTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalPausedTimeMs() / ((long) this.foregroundPlaybackCount);
    }

    public long getMeanPlayAndWaitTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalPlayAndWaitTimeMs() / ((long) this.foregroundPlaybackCount);
    }

    public long getMeanPlayTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalPlayTimeMs() / ((long) this.foregroundPlaybackCount);
    }

    public float getMeanRebufferCount() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.totalRebufferCount / i;
    }

    public long getMeanRebufferTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalRebufferTimeMs() / ((long) this.foregroundPlaybackCount);
    }

    public float getMeanSeekCount() {
        int i = this.foregroundPlaybackCount;
        if (i == 0) {
            return 0.0f;
        }
        return this.totalSeekCount / i;
    }

    public long getMeanSeekTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalSeekTimeMs() / ((long) this.foregroundPlaybackCount);
    }

    public long getMeanSingleRebufferTimeMs() {
        if (this.totalRebufferCount == 0) {
            return C.TIME_UNSET;
        }
        return (getPlaybackStateDurationMs(7) + getPlaybackStateDurationMs(6)) / ((long) this.totalRebufferCount);
    }

    public long getMeanSingleSeekTimeMs() {
        return this.totalSeekCount == 0 ? C.TIME_UNSET : getTotalSeekTimeMs() / ((long) this.totalSeekCount);
    }

    public float getMeanTimeBetweenFatalErrors() {
        return 1.0f / getFatalErrorRate();
    }

    public float getMeanTimeBetweenNonFatalErrors() {
        return 1.0f / getNonFatalErrorRate();
    }

    public float getMeanTimeBetweenRebuffers() {
        return 1.0f / getRebufferRate();
    }

    public int getMeanVideoFormatBitrate() {
        long j = this.totalVideoFormatBitrateTimeMs;
        if (j == 0) {
            return -1;
        }
        return (int) (this.totalVideoFormatBitrateTimeProduct / j);
    }

    public int getMeanVideoFormatHeight() {
        long j = this.totalVideoFormatHeightTimeMs;
        if (j == 0) {
            return -1;
        }
        return (int) (this.totalVideoFormatHeightTimeProduct / j);
    }

    public long getMeanWaitTimeMs() {
        return this.foregroundPlaybackCount == 0 ? C.TIME_UNSET : getTotalWaitTimeMs() / ((long) this.foregroundPlaybackCount);
    }

    public long getMediaTimeMsAtRealtimeMs(long j) {
        if (this.mediaTimeHistory.isEmpty()) {
            return C.TIME_UNSET;
        }
        int i = 0;
        while (i < this.mediaTimeHistory.size() && this.mediaTimeHistory.get(i)[0] <= j) {
            i++;
        }
        if (i == 0) {
            return this.mediaTimeHistory.get(0)[1];
        }
        if (i == this.mediaTimeHistory.size()) {
            List<long[]> list = this.mediaTimeHistory;
            return list.get(list.size() - 1)[1];
        }
        int i2 = i - 1;
        long j2 = this.mediaTimeHistory.get(i2)[0];
        long j3 = this.mediaTimeHistory.get(i2)[1];
        long j4 = this.mediaTimeHistory.get(i)[0];
        long j5 = this.mediaTimeHistory.get(i)[1];
        long j6 = j4 - j2;
        if (j6 == 0) {
            return j3;
        }
        return j3 + ((long) ((j5 - j3) * ((j - j2) / j6)));
    }

    public float getNonFatalErrorRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.nonFatalErrorCount * 1000.0f) / totalPlayTimeMs;
    }

    public int getPlaybackStateAtTime(long j) {
        int i = 0;
        for (EventTimeAndPlaybackState eventTimeAndPlaybackState : this.playbackStateHistory) {
            if (eventTimeAndPlaybackState.eventTime.realtimeMs > j) {
                break;
            }
            i = eventTimeAndPlaybackState.playbackState;
        }
        return i;
    }

    public long getPlaybackStateDurationMs(int i) {
        return this.playbackStateDurationsMs[i];
    }

    public float getRebufferRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (this.totalRebufferCount * 1000.0f) / totalPlayTimeMs;
    }

    public float getRebufferTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalRebufferTimeMs() / totalPlayAndWaitTimeMs;
    }

    public float getSeekTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalSeekTimeMs() / totalPlayAndWaitTimeMs;
    }

    public long getTotalElapsedTimeMs() {
        long j = 0;
        for (int i = 0; i < 16; i++) {
            j += this.playbackStateDurationsMs[i];
        }
        return j;
    }

    public long getTotalJoinTimeMs() {
        return getPlaybackStateDurationMs(2);
    }

    public long getTotalPausedTimeMs() {
        return getPlaybackStateDurationMs(7) + getPlaybackStateDurationMs(4);
    }

    public long getTotalPlayAndWaitTimeMs() {
        return getTotalWaitTimeMs() + getTotalPlayTimeMs();
    }

    public long getTotalPlayTimeMs() {
        return getPlaybackStateDurationMs(3);
    }

    public long getTotalRebufferTimeMs() {
        return getPlaybackStateDurationMs(6);
    }

    public long getTotalSeekTimeMs() {
        return getPlaybackStateDurationMs(5);
    }

    public long getTotalWaitTimeMs() {
        return getPlaybackStateDurationMs(5) + getPlaybackStateDurationMs(6) + getPlaybackStateDurationMs(2);
    }

    public float getWaitTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return getTotalWaitTimeMs() / totalPlayAndWaitTimeMs;
    }
}
