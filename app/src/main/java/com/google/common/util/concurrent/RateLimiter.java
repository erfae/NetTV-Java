package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@Beta
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public abstract class RateLimiter {

    @CheckForNull
    private volatile Object mutexDoNotUseDirectly;
    private final SleepingStopwatch stopwatch;

    public static abstract class SleepingStopwatch {
        public static SleepingStopwatch createFromSystemTimer() {
            return new SleepingStopwatch() { // from class: com.google.common.util.concurrent.RateLimiter.SleepingStopwatch.1
                public final Stopwatch stopwatch = Stopwatch.createStarted();

                @Override // com.google.common.util.concurrent.RateLimiter.SleepingStopwatch
                public final long readMicros() {
                    return this.stopwatch.elapsed(TimeUnit.MICROSECONDS);
                }

                @Override // com.google.common.util.concurrent.RateLimiter.SleepingStopwatch
                public final void sleepMicrosUninterruptibly(long j) {
                    if (j > 0) {
                        Uninterruptibles.sleepUninterruptibly(j, TimeUnit.MICROSECONDS);
                    }
                }
            };
        }

        public abstract long readMicros();

        public abstract void sleepMicrosUninterruptibly(long j);
    }

    public RateLimiter(SleepingStopwatch sleepingStopwatch) {
        this.stopwatch = (SleepingStopwatch) Preconditions.checkNotNull(sleepingStopwatch);
    }

    private boolean canAcquire(long j, long j2) {
        return queryEarliestAvailable() - j2 <= j;
    }

    private static void checkPermits(int i) {
        Preconditions.checkArgument(i > 0, "Requested permits (%s) must be positive", i);
    }

    public static RateLimiter create(double d) {
        SmoothRateLimiter.SmoothBursty smoothBursty = new SmoothRateLimiter.SmoothBursty(SleepingStopwatch.createFromSystemTimer());
        smoothBursty.setRate(d);
        return smoothBursty;
    }

    private Object mutex() {
        Object obj = this.mutexDoNotUseDirectly;
        if (obj == null) {
            synchronized (this) {
                obj = this.mutexDoNotUseDirectly;
                if (obj == null) {
                    obj = new Object();
                    this.mutexDoNotUseDirectly = obj;
                }
            }
        }
        return obj;
    }

    @CanIgnoreReturnValue
    public double acquire() {
        return acquire(1);
    }

    public abstract double doGetRate();

    public abstract void doSetRate(double d, long j);

    public final double getRate() {
        double dDoGetRate;
        synchronized (mutex()) {
            dDoGetRate = doGetRate();
        }
        return dDoGetRate;
    }

    public abstract long queryEarliestAvailable();

    public abstract long reserveEarliestAvailable(int i, long j);

    public final void setRate(double d) {
        Preconditions.checkArgument(d > 0.0d && !Double.isNaN(d), "rate must be positive");
        synchronized (mutex()) {
            doSetRate(d, this.stopwatch.readMicros());
        }
    }

    public String toString() {
        return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", Double.valueOf(getRate()));
    }

    public boolean tryAcquire(long j, TimeUnit timeUnit) {
        return tryAcquire(1, j, timeUnit);
    }

    @CanIgnoreReturnValue
    public double acquire(int i) {
        long jMax;
        checkPermits(i);
        synchronized (mutex()) {
            long micros = this.stopwatch.readMicros();
            jMax = Math.max(reserveEarliestAvailable(i, micros) - micros, 0L);
        }
        this.stopwatch.sleepMicrosUninterruptibly(jMax);
        return (jMax * 1.0d) / TimeUnit.SECONDS.toMicros(1L);
    }

    public boolean tryAcquire(int i) {
        return tryAcquire(i, 0L, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire() {
        return tryAcquire(1, 0L, TimeUnit.MICROSECONDS);
    }

    public static RateLimiter create(double d, long j, TimeUnit timeUnit) {
        Preconditions.checkArgument(j >= 0, "warmupPeriod must not be negative: %s", j);
        SmoothRateLimiter.SmoothWarmingUp smoothWarmingUp = new SmoothRateLimiter.SmoothWarmingUp(SleepingStopwatch.createFromSystemTimer(), j, timeUnit);
        smoothWarmingUp.setRate(d);
        return smoothWarmingUp;
    }

    public boolean tryAcquire(int i, long j, TimeUnit timeUnit) {
        long jMax = Math.max(timeUnit.toMicros(j), 0L);
        checkPermits(i);
        synchronized (mutex()) {
            long micros = this.stopwatch.readMicros();
            if (!canAcquire(micros, jMax)) {
                return false;
            }
            this.stopwatch.sleepMicrosUninterruptibly(Math.max(reserveEarliestAvailable(i, micros) - micros, 0L));
            return true;
        }
    }
}
