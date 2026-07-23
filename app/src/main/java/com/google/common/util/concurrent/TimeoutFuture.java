package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
final class TimeoutFuture<V> extends FluentFuture.TrustedFuture<V> {

    @CheckForNull
    private ListenableFuture<V> delegateRef;

    @CheckForNull
    private ScheduledFuture<?> timer;

    public static final class Fire<V> implements Runnable {

        @CheckForNull
        public TimeoutFuture<V> timeoutFutureRef;

        public Fire(TimeoutFuture<V> timeoutFuture) {
            this.timeoutFutureRef = timeoutFuture;
        }

        @Override // java.lang.Runnable
        public void run() {
            ListenableFuture<? extends V> listenableFuture;
            TimeoutFuture<V> timeoutFuture = this.timeoutFutureRef;
            if (timeoutFuture == null || (listenableFuture = ((TimeoutFuture) timeoutFuture).delegateRef) == null) {
                return;
            }
            this.timeoutFutureRef = null;
            if (listenableFuture.isDone()) {
                timeoutFuture.setFuture(listenableFuture);
                return;
            }
            try {
                ScheduledFuture scheduledFuture = ((TimeoutFuture) timeoutFuture).timer;
                ((TimeoutFuture) timeoutFuture).timer = null;
                String string = "Timed out";
                if (scheduledFuture != null) {
                    try {
                        long jAbs = Math.abs(scheduledFuture.getDelay(TimeUnit.MILLISECONDS));
                        if (jAbs > 10) {
                            StringBuilder sb = new StringBuilder(75);
                            sb.append("Timed out");
                            sb.append(" (timeout delayed by ");
                            sb.append(jAbs);
                            sb.append(" ms after scheduled time)");
                            string = sb.toString();
                        }
                    } catch (Throwable th) {
                        timeoutFuture.setException(new TimeoutFutureException(string));
                        throw th;
                    }
                }
                String strValueOf = String.valueOf(string);
                String strValueOf2 = String.valueOf(listenableFuture);
                StringBuilder sb2 = new StringBuilder(strValueOf.length() + 2 + strValueOf2.length());
                sb2.append(strValueOf);
                sb2.append(": ");
                sb2.append(strValueOf2);
                timeoutFuture.setException(new TimeoutFutureException(sb2.toString()));
                listenableFuture.cancel(true);
            } catch (Throwable th2) {
                listenableFuture.cancel(true);
                throw th2;
            }
        }
    }

    public static final class TimeoutFutureException extends TimeoutException {
        @Override // java.lang.Throwable
        public synchronized Throwable fillInStackTrace() {
            setStackTrace(new StackTraceElement[0]);
            return this;
        }

        private TimeoutFutureException(String str) {
            super(str);
        }
    }

    private TimeoutFuture(ListenableFuture<V> listenableFuture) {
        this.delegateRef = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
    }

    public static <V> ListenableFuture<V> create(ListenableFuture<V> listenableFuture, long j, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        TimeoutFuture timeoutFuture = new TimeoutFuture(listenableFuture);
        Fire fire = new Fire(timeoutFuture);
        timeoutFuture.timer = scheduledExecutorService.schedule(fire, j, timeUnit);
        listenableFuture.addListener(fire, MoreExecutors.directExecutor());
        return timeoutFuture;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        maybePropagateCancellationTo(this.delegateRef);
        ScheduledFuture<?> scheduledFuture = this.timer;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.delegateRef = null;
        this.timer = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    @CheckForNull
    public final String pendingToString() {
        ListenableFuture<V> listenableFuture = this.delegateRef;
        ScheduledFuture<?> scheduledFuture = this.timer;
        if (listenableFuture == null) {
            return null;
        }
        String strValueOf = String.valueOf(listenableFuture);
        String strM = MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 14, "inputFuture=[", strValueOf, "]");
        if (scheduledFuture == null) {
            return strM;
        }
        long delay = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
        if (delay <= 0) {
            return strM;
        }
        String strValueOf2 = String.valueOf(strM);
        StringBuilder sb = new StringBuilder(strValueOf2.length() + 43);
        sb.append(strValueOf2);
        sb.append(", remaining delay=[");
        sb.append(delay);
        sb.append(" ms]");
        return sb.toString();
    }
}
