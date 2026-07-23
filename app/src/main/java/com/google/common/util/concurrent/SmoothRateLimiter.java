package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.math.LongMath;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
abstract class SmoothRateLimiter extends RateLimiter {
    public double maxPermits;
    private long nextFreeTicketMicros;
    public double stableIntervalMicros;
    public double storedPermits;

    public static final class SmoothBursty extends SmoothRateLimiter {
        public final double maxBurstSeconds;

        public SmoothBursty(RateLimiter.SleepingStopwatch sleepingStopwatch) {
            super(sleepingStopwatch);
            this.maxBurstSeconds = 1.0d;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public final double coolDownIntervalMicros() {
            return this.stableIntervalMicros;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public final void doSetRate(double d, double d2) {
            double d3 = this.maxPermits;
            double d4 = this.maxBurstSeconds * d;
            this.maxPermits = d4;
            if (d3 == Double.POSITIVE_INFINITY) {
                this.storedPermits = d4;
            } else {
                this.storedPermits = d3 != 0.0d ? (this.storedPermits * d4) / d3 : 0.0d;
            }
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public final long storedPermitsToWaitTime(double d, double d2) {
            return 0L;
        }
    }

    public static final class SmoothWarmingUp extends SmoothRateLimiter {
        private double coldFactor;
        private double slope;
        private double thresholdPermits;
        private final long warmupPeriodMicros;

        public SmoothWarmingUp(RateLimiter.SleepingStopwatch sleepingStopwatch, long j, TimeUnit timeUnit) {
            super(sleepingStopwatch);
            this.warmupPeriodMicros = timeUnit.toMicros(j);
            this.coldFactor = 3.0d;
        }

        private double permitsToTime(double d) {
            return (d * this.slope) + this.stableIntervalMicros;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public final double coolDownIntervalMicros() {
            return this.warmupPeriodMicros / this.maxPermits;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public final void doSetRate(double d, double d2) {
            double d3 = this.maxPermits;
            double d4 = this.coldFactor * d2;
            double d5 = this.warmupPeriodMicros;
            double d6 = (0.5d * d5) / d2;
            this.thresholdPermits = d6;
            double d7 = ((d5 * 2.0d) / (d2 + d4)) + d6;
            this.maxPermits = d7;
            this.slope = (d4 - d2) / (d7 - d6);
            if (d3 == Double.POSITIVE_INFINITY) {
                this.storedPermits = 0.0d;
                return;
            }
            if (d3 != 0.0d) {
                d7 = (this.storedPermits * d7) / d3;
            }
            this.storedPermits = d7;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        public final long storedPermitsToWaitTime(double d, double d2) {
            long jPermitsToTime;
            double d3 = d - this.thresholdPermits;
            if (d3 > 0.0d) {
                double dMin = Math.min(d3, d2);
                jPermitsToTime = (long) (((permitsToTime(d3) + permitsToTime(d3 - dMin)) * dMin) / 2.0d);
                d2 -= dMin;
            } else {
                jPermitsToTime = 0;
            }
            return jPermitsToTime + ((long) (this.stableIntervalMicros * d2));
        }
    }

    public abstract double coolDownIntervalMicros();

    @Override // com.google.common.util.concurrent.RateLimiter
    public final double doGetRate() {
        return TimeUnit.SECONDS.toMicros(1L) / this.stableIntervalMicros;
    }

    public abstract void doSetRate(double d, double d2);

    @Override // com.google.common.util.concurrent.RateLimiter
    public final void doSetRate(double d, long j) {
        resync(j);
        double micros = TimeUnit.SECONDS.toMicros(1L) / d;
        this.stableIntervalMicros = micros;
        doSetRate(d, micros);
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    public final long queryEarliestAvailable() {
        return this.nextFreeTicketMicros;
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    public final long reserveEarliestAvailable(int i, long j) {
        resync(j);
        long j2 = this.nextFreeTicketMicros;
        double d = i;
        double dMin = Math.min(d, this.storedPermits);
        this.nextFreeTicketMicros = LongMath.saturatedAdd(this.nextFreeTicketMicros, storedPermitsToWaitTime(this.storedPermits, dMin) + ((long) ((d - dMin) * this.stableIntervalMicros)));
        this.storedPermits -= dMin;
        return j2;
    }

    public final void resync(long j) {
        long j2 = this.nextFreeTicketMicros;
        if (j > j2) {
            this.storedPermits = Math.min(this.maxPermits, this.storedPermits + ((j - j2) / coolDownIntervalMicros()));
            this.nextFreeTicketMicros = j;
        }
    }

    public abstract long storedPermitsToWaitTime(double d, double d2);

    private SmoothRateLimiter(RateLimiter.SleepingStopwatch sleepingStopwatch) {
        super(sleepingStopwatch);
        this.nextFreeTicketMicros = 0L;
    }
}
