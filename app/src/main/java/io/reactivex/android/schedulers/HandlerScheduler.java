package io.reactivex.android.schedulers;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
final class HandlerScheduler extends Scheduler {
    private final boolean async;
    private final Handler handler;

    public static final class HandlerWorker extends Scheduler.Worker {
        private final boolean async;
        private volatile boolean disposed;
        private final Handler handler;

        public HandlerWorker(Handler handler, boolean z) {
            this.handler = handler;
            this.async = z;
        }

        public void dispose() {
            this.disposed = true;
            this.handler.removeCallbacksAndMessages(this);
        }

        public boolean isDisposed() {
            return this.disposed;
        }

        @SuppressLint({"NewApi"})
        public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            Objects.requireNonNull(runnable, "run == null");
            Objects.requireNonNull(timeUnit, "unit == null");
            if (this.disposed) {
                return Disposables.disposed();
            }
            Runnable runnableOnSchedule = RxJavaPlugins.onSchedule(runnable);
            Handler handler = this.handler;
            ScheduledRunnable scheduledRunnable = new ScheduledRunnable(handler, runnableOnSchedule);
            Message messageObtain = Message.obtain(handler, scheduledRunnable);
            messageObtain.obj = this;
            if (this.async) {
                messageObtain.setAsynchronous(true);
            }
            this.handler.sendMessageDelayed(messageObtain, timeUnit.toMillis(j));
            if (!this.disposed) {
                return scheduledRunnable;
            }
            this.handler.removeCallbacks(scheduledRunnable);
            return Disposables.disposed();
        }
    }

    public static final class ScheduledRunnable implements Runnable, Disposable {
        private final Runnable delegate;
        private volatile boolean disposed;
        private final Handler handler;

        public ScheduledRunnable(Handler handler, Runnable runnable) {
            this.handler = handler;
            this.delegate = runnable;
        }

        public void dispose() {
            this.handler.removeCallbacks(this);
            this.disposed = true;
        }

        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.delegate.run();
            } catch (Throwable th) {
                RxJavaPlugins.onError(th);
            }
        }
    }

    public HandlerScheduler(Handler handler, boolean z) {
        this.handler = handler;
        this.async = z;
    }

    public Scheduler.Worker createWorker() {
        return new HandlerWorker(this.handler, this.async);
    }

    @SuppressLint({"NewApi"})
    public Disposable scheduleDirect(Runnable runnable, long j, TimeUnit timeUnit) {
        Objects.requireNonNull(runnable, "run == null");
        Objects.requireNonNull(timeUnit, "unit == null");
        Runnable runnableOnSchedule = RxJavaPlugins.onSchedule(runnable);
        Handler handler = this.handler;
        ScheduledRunnable scheduledRunnable = new ScheduledRunnable(handler, runnableOnSchedule);
        Message messageObtain = Message.obtain(handler, scheduledRunnable);
        if (this.async) {
            messageObtain.setAsynchronous(true);
        }
        this.handler.sendMessageDelayed(messageObtain, timeUnit.toMillis(j));
        return scheduledRunnable;
    }
}
