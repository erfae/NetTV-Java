package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.util.Util;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.RoundingMode;

/* JADX INFO: loaded from: classes.dex */
public class DefaultAudioTrackBufferSizeProvider implements DefaultAudioSink.AudioTrackBufferSizeProvider {
    private static final int AC3_BUFFER_MULTIPLICATION_FACTOR = 2;
    private static final int MAX_PCM_BUFFER_DURATION_US = 750000;
    private static final int MIN_PCM_BUFFER_DURATION_US = 250000;
    private static final int OFFLOAD_BUFFER_DURATION_US = 50000000;
    private static final int PASSTHROUGH_BUFFER_DURATION_US = 250000;
    private static final int PCM_BUFFER_MULTIPLICATION_FACTOR = 4;
    public final int ac3BufferMultiplicationFactor;
    public final int maxPcmBufferDurationUs;
    public final int minPcmBufferDurationUs;
    public final int offloadBufferDurationUs;
    public final int passthroughBufferDurationUs;
    public final int pcmBufferMultiplicationFactor;

    public static class Builder {
        private int minPcmBufferDurationUs = 250000;
        private int maxPcmBufferDurationUs = DefaultAudioTrackBufferSizeProvider.MAX_PCM_BUFFER_DURATION_US;
        private int pcmBufferMultiplicationFactor = 4;
        private int passthroughBufferDurationUs = 250000;
        private int offloadBufferDurationUs = DefaultAudioTrackBufferSizeProvider.OFFLOAD_BUFFER_DURATION_US;
        private int ac3BufferMultiplicationFactor = 2;

        public DefaultAudioTrackBufferSizeProvider build() {
            return new DefaultAudioTrackBufferSizeProvider(this);
        }

        @CanIgnoreReturnValue
        public Builder setAc3BufferMultiplicationFactor(int i) {
            this.ac3BufferMultiplicationFactor = i;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMaxPcmBufferDurationUs(int i) {
            this.maxPcmBufferDurationUs = i;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setMinPcmBufferDurationUs(int i) {
            this.minPcmBufferDurationUs = i;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setOffloadBufferDurationUs(int i) {
            this.offloadBufferDurationUs = i;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPassthroughBufferDurationUs(int i) {
            this.passthroughBufferDurationUs = i;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPcmBufferMultiplicationFactor(int i) {
            this.pcmBufferMultiplicationFactor = i;
            return this;
        }
    }

    public DefaultAudioTrackBufferSizeProvider(Builder builder) {
        this.minPcmBufferDurationUs = builder.minPcmBufferDurationUs;
        this.maxPcmBufferDurationUs = builder.maxPcmBufferDurationUs;
        this.pcmBufferMultiplicationFactor = builder.pcmBufferMultiplicationFactor;
        this.passthroughBufferDurationUs = builder.passthroughBufferDurationUs;
        this.offloadBufferDurationUs = builder.offloadBufferDurationUs;
        this.ac3BufferMultiplicationFactor = builder.ac3BufferMultiplicationFactor;
    }

    public static int getMaximumEncodedRateBytesPerSecond(int i) {
        switch (i) {
            case 5:
                return Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND;
            case 6:
            case 18:
                return Ac3Util.E_AC3_MAX_RATE_BYTES_PER_SECOND;
            case 7:
                return DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND;
            case 8:
                return DtsUtil.DTS_HD_MAX_RATE_BYTES_PER_SECOND;
            case 9:
                return MpegAudioUtil.MAX_RATE_BYTES_PER_SECOND;
            case 10:
                return AacUtil.AAC_LC_MAX_RATE_BYTES_PER_SECOND;
            case 11:
                return AacUtil.AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND;
            case 12:
                return AacUtil.AAC_HE_V2_MAX_RATE_BYTES_PER_SECOND;
            case 13:
            case 19:
            default:
                throw new IllegalArgumentException();
            case 14:
                return Ac3Util.TRUEHD_MAX_RATE_BYTES_PER_SECOND;
            case 15:
                return 8000;
            case 16:
                return AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND;
            case 17:
                return Ac4Util.MAX_RATE_BYTES_PER_SECOND;
            case 20:
                return OpusUtil.MAX_BYTES_PER_SECOND;
        }
    }

    @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioTrackBufferSizeProvider
    public int getBufferSizeInBytes(int i, int i2, int i3, int i4, int i5, int i6, double d) {
        int iConstrainValue;
        if (i3 == 0) {
            long j = i5;
            long j2 = i4;
            iConstrainValue = Util.constrainValue(this.pcmBufferMultiplicationFactor * i, Ints.checkedCast(((((long) this.minPcmBufferDurationUs) * j) * j2) / 1000000), Ints.checkedCast(((((long) this.maxPcmBufferDurationUs) * j) * j2) / 1000000));
        } else if (i3 == 1) {
            iConstrainValue = Ints.checkedCast((((long) this.offloadBufferDurationUs) * ((long) getMaximumEncodedRateBytesPerSecond(i2))) / 1000000);
        } else {
            if (i3 != 2) {
                throw new IllegalArgumentException();
            }
            int i7 = this.passthroughBufferDurationUs;
            if (i2 == 5) {
                i7 *= this.ac3BufferMultiplicationFactor;
            }
            iConstrainValue = Ints.checkedCast((((long) i7) * ((long) (i6 != -1 ? IntMath.divide(i6, 8, RoundingMode.CEILING) : getMaximumEncodedRateBytesPerSecond(i2)))) / 1000000);
        }
        return (((Math.max(i, (int) (((double) iConstrainValue) * d)) + i4) - 1) / i4) * i4;
    }
}
