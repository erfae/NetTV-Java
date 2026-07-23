package com.google.android.exoplayer2.source.dash.manifest;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Util;
import com.google.common.math.BigIntegerMath;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class SegmentBase {

    @Nullable
    public final RangedUri initialization;
    public final long presentationTimeOffset;
    public final long timescale;

    public static abstract class MultiSegmentBase extends SegmentBase {

        @VisibleForTesting
        public final long availabilityTimeOffsetUs;
        public final long duration;
        private final long periodStartUnixTimeUs;

        @Nullable
        public final List<SegmentTimelineElement> segmentTimeline;
        public final long startNumber;
        private final long timeShiftBufferDepthUs;

        public MultiSegmentBase(@Nullable RangedUri rangedUri, long j, long j2, long j3, long j4, @Nullable List<SegmentTimelineElement> list, long j5, long j6, long j7) {
            super(rangedUri, j, j2);
            this.startNumber = j3;
            this.duration = j4;
            this.segmentTimeline = list;
            this.availabilityTimeOffsetUs = j5;
            this.timeShiftBufferDepthUs = j6;
            this.periodStartUnixTimeUs = j7;
        }

        public long getAvailableSegmentCount(long j, long j2) {
            long segmentCount = getSegmentCount(j);
            return segmentCount != -1 ? segmentCount : (int) (getSegmentNum((j2 - this.periodStartUnixTimeUs) + this.availabilityTimeOffsetUs, j) - getFirstAvailableSegmentNum(j, j2));
        }

        public long getFirstAvailableSegmentNum(long j, long j2) {
            if (getSegmentCount(j) == -1) {
                long j3 = this.timeShiftBufferDepthUs;
                if (j3 != C.TIME_UNSET) {
                    return Math.max(getFirstSegmentNum(), getSegmentNum((j2 - this.periodStartUnixTimeUs) - j3, j));
                }
            }
            return getFirstSegmentNum();
        }

        public long getFirstSegmentNum() {
            return this.startNumber;
        }

        public long getNextSegmentAvailableTimeUs(long j, long j2) {
            if (this.segmentTimeline != null) {
                return C.TIME_UNSET;
            }
            long availableSegmentCount = getAvailableSegmentCount(j, j2) + getFirstAvailableSegmentNum(j, j2);
            return (getSegmentDurationUs(availableSegmentCount, j) + getSegmentTimeUs(availableSegmentCount)) - this.availabilityTimeOffsetUs;
        }

        public abstract long getSegmentCount(long j);

        public final long getSegmentDurationUs(long j, long j2) {
            List<SegmentTimelineElement> list = this.segmentTimeline;
            if (list != null) {
                return (list.get((int) (j - this.startNumber)).duration * 1000000) / this.timescale;
            }
            long segmentCount = getSegmentCount(j2);
            return (segmentCount == -1 || j != (getFirstSegmentNum() + segmentCount) - 1) ? (this.duration * 1000000) / this.timescale : j2 - getSegmentTimeUs(j);
        }

        public long getSegmentNum(long j, long j2) {
            long firstSegmentNum = getFirstSegmentNum();
            long segmentCount = getSegmentCount(j2);
            if (segmentCount == 0) {
                return firstSegmentNum;
            }
            if (this.segmentTimeline == null) {
                long j3 = (j / ((this.duration * 1000000) / this.timescale)) + this.startNumber;
                if (j3 < firstSegmentNum) {
                    return firstSegmentNum;
                }
                return segmentCount == -1 ? j3 : Math.min(j3, (firstSegmentNum + segmentCount) - 1);
            }
            long j4 = (segmentCount + firstSegmentNum) - 1;
            long j5 = firstSegmentNum;
            while (j5 <= j4) {
                long j6 = ((j4 - j5) / 2) + j5;
                long segmentTimeUs = getSegmentTimeUs(j6);
                if (segmentTimeUs < j) {
                    j5 = j6 + 1;
                } else {
                    if (segmentTimeUs <= j) {
                        return j6;
                    }
                    j4 = j6 - 1;
                }
            }
            return j5 == firstSegmentNum ? j5 : j4;
        }

        public final long getSegmentTimeUs(long j) {
            List<SegmentTimelineElement> list = this.segmentTimeline;
            return Util.scaleLargeTimestamp(list != null ? list.get((int) (j - this.startNumber)).startTime - this.presentationTimeOffset : (j - this.startNumber) * this.duration, 1000000L, this.timescale);
        }

        public abstract RangedUri getSegmentUrl(Representation representation, long j);

        public boolean isExplicit() {
            return this.segmentTimeline != null;
        }
    }

    public static final class SegmentList extends MultiSegmentBase {

        @Nullable
        public final List<RangedUri> mediaSegments;

        public SegmentList(RangedUri rangedUri, long j, long j2, long j3, long j4, @Nullable List<SegmentTimelineElement> list, long j5, @Nullable List<RangedUri> list2, long j6, long j7) {
            super(rangedUri, j, j2, j3, j4, list, j5, j6, j7);
            this.mediaSegments = list2;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public long getSegmentCount(long j) {
            return this.mediaSegments.size();
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public RangedUri getSegmentUrl(Representation representation, long j) {
            return this.mediaSegments.get((int) (j - this.startNumber));
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public boolean isExplicit() {
            return true;
        }
    }

    public static final class SegmentTemplate extends MultiSegmentBase {
        public final long endNumber;

        @Nullable
        public final UrlTemplate initializationTemplate;

        @Nullable
        public final UrlTemplate mediaTemplate;

        public SegmentTemplate(RangedUri rangedUri, long j, long j2, long j3, long j4, long j5, @Nullable List<SegmentTimelineElement> list, long j6, @Nullable UrlTemplate urlTemplate, @Nullable UrlTemplate urlTemplate2, long j7, long j8) {
            super(rangedUri, j, j2, j3, j5, list, j6, j7, j8);
            this.initializationTemplate = urlTemplate;
            this.mediaTemplate = urlTemplate2;
            this.endNumber = j4;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase
        @Nullable
        public RangedUri getInitialization(Representation representation) {
            UrlTemplate urlTemplate = this.initializationTemplate;
            if (urlTemplate == null) {
                return super.getInitialization(representation);
            }
            Format format = representation.format;
            return new RangedUri(urlTemplate.buildUri(format.id, 0L, format.bitrate, 0L), 0L, -1L);
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public long getSegmentCount(long j) {
            List<SegmentTimelineElement> list = this.segmentTimeline;
            if (list != null) {
                return list.size();
            }
            long j2 = this.endNumber;
            if (j2 != -1) {
                return (j2 - this.startNumber) + 1;
            }
            if (j != C.TIME_UNSET) {
                return BigIntegerMath.divide(BigInteger.valueOf(j).multiply(BigInteger.valueOf(this.timescale)), BigInteger.valueOf(this.duration).multiply(BigInteger.valueOf(1000000L)), RoundingMode.CEILING).longValue();
            }
            return -1L;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public RangedUri getSegmentUrl(Representation representation, long j) {
            List<SegmentTimelineElement> list = this.segmentTimeline;
            long j2 = list != null ? list.get((int) (j - this.startNumber)).startTime : (j - this.startNumber) * this.duration;
            UrlTemplate urlTemplate = this.mediaTemplate;
            Format format = representation.format;
            return new RangedUri(urlTemplate.buildUri(format.id, j, format.bitrate, j2), 0L, -1L);
        }
    }

    public static final class SegmentTimelineElement {
        public final long duration;
        public final long startTime;

        public SegmentTimelineElement(long j, long j2) {
            this.startTime = j;
            this.duration = j2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || SegmentTimelineElement.class != obj.getClass()) {
                return false;
            }
            SegmentTimelineElement segmentTimelineElement = (SegmentTimelineElement) obj;
            return this.startTime == segmentTimelineElement.startTime && this.duration == segmentTimelineElement.duration;
        }

        public int hashCode() {
            return (((int) this.startTime) * 31) + ((int) this.duration);
        }
    }

    public SegmentBase(@Nullable RangedUri rangedUri, long j, long j2) {
        this.initialization = rangedUri;
        this.timescale = j;
        this.presentationTimeOffset = j2;
    }

    @Nullable
    public RangedUri getInitialization(Representation representation) {
        return this.initialization;
    }

    public long getPresentationTimeOffsetUs() {
        return Util.scaleLargeTimestamp(this.presentationTimeOffset, 1000000L, this.timescale);
    }

    public static class SingleSegmentBase extends SegmentBase {
        public final long indexLength;
        public final long indexStart;

        public SingleSegmentBase(@Nullable RangedUri rangedUri, long j, long j2, long j3, long j4) {
            super(rangedUri, j, j2);
            this.indexStart = j3;
            this.indexLength = j4;
        }

        @Nullable
        public RangedUri getIndex() {
            long j = this.indexLength;
            if (j <= 0) {
                return null;
            }
            return new RangedUri(null, this.indexStart, j);
        }

        public SingleSegmentBase() {
            this(null, 1L, 0L, 0L, 0L);
        }
    }
}
