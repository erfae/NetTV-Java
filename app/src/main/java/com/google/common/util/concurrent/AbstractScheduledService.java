package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public abstract class AbstractScheduledService implements Service {
    private static final Logger logger = Logger.getLogger(AbstractScheduledService.class.getName());
    private final AbstractService delegate = new ServiceDelegate();

    public interface Cancellable {
        void cancel(boolean z);

        boolean isCancelled();
    }

    public static abstract class CustomScheduler extends Scheduler {

        public final class ReschedulableCallable implements Callable<Void> {

            @CheckForNull
            @GuardedBy("lock")
            private SupplantableFuture cancellationDelegate;
            private final ScheduledExecutorService executor;
            private final ReentrantLock lock = new ReentrantLock();
            private final AbstractService service;
            private final Runnable wrappedRunnable;

            public ReschedulableCallable(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                this.wrappedRunnable = runnable;
                this.executor = scheduledExecutorService;
                this.service = abstractService;
            }

            @GuardedBy("lock")
            private Cancellable initializeOrUpdateCancellationDelegate(Schedule schedule) {
                SupplantableFuture supplantableFuture = this.cancellationDelegate;
                if (supplantableFuture == null) {
                    SupplantableFuture supplantableFuture2 = new SupplantableFuture(this.lock, submitToExecutor(schedule));
                    this.cancellationDelegate = supplantableFuture2;
                    return supplantableFuture2;
                }
                if (!supplantableFuture.currentFuture.isCancelled()) {
                    this.cancellationDelegate.currentFuture = submitToExecutor(schedule);
                }
                return this.cancellationDelegate;
            }

            private ScheduledFuture<Void> submitToExecutor(Schedule schedule) {
                return this.executor.schedule(this, schedule.delay, schedule.unit);
            }

            @CanIgnoreReturnValue
            public Cancellable reschedule() {
                Cancellable futureAsCancellable;
                try {
                    Schedule nextSchedule = CustomScheduler.this.getNextSchedule();
                    Throwable th = null;
                    this.lock.lock();
                    try {
                        futureAsCancellable = initializeOrUpdateCancellationDelegate(nextSchedule);
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            futureAsCancellable = new FutureAsCancellable(Futures.immediateCancelledFuture());
                        } catch (Throwable th3) {
                            this.lock.unlock();
                            throw th3;
                        }
                    }
                    this.lock.unlock();
                    if (th != null) {
                        this.service.notifyFailed(th);
                    }
                    return futureAsCancellable;
                } catch (Throwable th4) {
                    this.service.notifyFailed(th4);
                    return new FutureAsCancellable(Futures.immediateCancelledFuture());
                }
            }

            @Override // java.util.concurrent.Callable
            @CheckForNull
            public Void call() throws Exception {
                this.wrappedRunnable.run();
                reschedule();
                return null;
            }
        }

        public static final class Schedule {
            private final long delay;
            private final TimeUnit unit;

            public Schedule(long j, TimeUnit timeUnit) {
                this.delay = j;
                this.unit = (TimeUnit) Preconditions.checkNotNull(timeUnit);
            }
        }

        public static final class SupplantableFuture implements Cancellable {

            @GuardedBy("lock")
            private Future<Void> currentFuture;
            private final ReentrantLock lock;

            public SupplantableFuture(ReentrantLock reentrantLock, Future<Void> future) {
                this.lock = reentrantLock;
                this.currentFuture = future;
            }

            @Override // com.google.common.util.concurrent.AbstractScheduledService.Cancellable
            public void cancel(boolean z) {
                this.lock.lock();
                try {
                    this.currentFuture.cancel(z);
                } finally {
                    this.lock.unlock();
                }
            }

            @Override // com.google.common.util.concurrent.AbstractScheduledService.Cancellable
            public boolean isCancelled() {
                this.lock.lock();
                try {
                    return this.currentFuture.isCancelled();
                } finally {
                    this.lock.unlock();
                }
            }
        }

        public CustomScheduler() {
            super();
        }

        public abstract Schedule getNextSchedule() throws Exception;

        @Override // com.google.common.util.concurrent.AbstractScheduledService.Scheduler
        public final Cancellable schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
            return new ReschedulableCallable(abstractService, scheduledExecutorService, runnable).reschedule();
        }
    }

    public static final class FutureAsCancellable implements Cancellable {
        private final Future<?> delegate;

        public FutureAsCancellable(Future<?> future) {
            this.delegate = future;
        }

        @Override // com.google.common.util.concurrent.AbstractScheduledService.Cancellable
        public void cancel(boolean z) {
            this.delegate.cancel(z);
        }

        @Override // com.google.common.util.concurrent.AbstractScheduledService.Cancellable
        public boolean isCancelled() {
            return this.delegate.isCancelled();
        }
    }

    public static abstract class Scheduler {
        public static Scheduler newFixedDelaySchedule(final long j, final long j2, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(j2 > 0, "delay must be > 0, found %s", j2);
            return new Scheduler() { // from class: com.google.common.util.concurrent.AbstractScheduledService.Scheduler.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.google.common.util.concurrent.AbstractScheduledService.Scheduler
                public Cancellable schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return new FutureAsCancellable(scheduledExecutorService.scheduleWithFixedDelay(runnable, j, j2, timeUnit));
                }
            };
        }

        public static Scheduler newFixedRateSchedule(final long j, final long j2, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(j2 > 0, "period must be > 0, found %s", j2);
            return new Scheduler() { // from class: com.google.common.util.concurrent.AbstractScheduledService.Scheduler.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.google.common.util.concurrent.AbstractScheduledService.Scheduler
                public Cancellable schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return new FutureAsCancellable(scheduledExecutorService.scheduleAtFixedRate(runnable, j, j2, timeUnit));
                }
            };
        }

        public abstract Cancellable schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable);

        private Scheduler() {
        }
    }

    public final class ServiceDelegate extends AbstractService {

        @CheckForNull
        private volatile ScheduledExecutorService executorService;
        private final ReentrantLock lock;

        @CheckForNull
        private volatile Cancellable runningTask;
        private final Runnable task;

        public class Task implements Runnable {
            public Task() {
            }

            @Override // java.lang.Runnable
            public void run() {
                ServiceDelegate.this.lock.lock();
                try {
                    Cancellable cancellable = ServiceDelegate.this.runningTask;
                    Objects.requireNonNull(cancellable);
                    if (cancellable.isCancelled()) {
                        ServiceDelegate.this.lock.unlock();
                        return;
                    }
                    AbstractScheduledService.this.runOneIteration();
                } catch (Throwable th) {
                    try {
                        try {
                            Objects.requireNonNull(AbstractScheduledService.this);
                        } finally {
                            ServiceDelegate.this.lock.unlock();
                        }
                    } catch (Exception e) {
                        AbstractScheduledService.logger.log(Level.WARNING, "Error while attempting to shut down the service after failure.", (Throwable) e);
                    }
                    ServiceDelegate.this.notifyFailed(th);
                    Cancellable cancellable2 = ServiceDelegate.this.runningTask;
                    Objects.requireNonNull(cancellable2);
                    cancellable2.cancel(false);
                }
            }
        }

        private ServiceDelegate() {
            this.lock = new ReentrantLock();
            this.task = new Task();
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStart() {
            final AbstractScheduledService abstractScheduledService = AbstractScheduledService.this;
            Objects.requireNonNull(abstractScheduledService);
            final ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() { // from class: com.google.common.util.concurrent.AbstractScheduledService.1ThreadFactoryImpl
                @Override // java.util.concurrent.ThreadFactory
                public Thread newThread(Runnable runnable) {
                    return MoreExecutors.newThread(AbstractScheduledService.this.serviceName(), runnable);
                }
            });
            abstractScheduledService.addListener(new Service.Listener() { // from class: com.google.common.util.concurrent.AbstractScheduledService.1
                @Override // com.google.common.util.concurrent.Service.Listener
                public void failed(Service.State state, Throwable th) {
                    scheduledExecutorServiceNewSingleThreadScheduledExecutor.shutdown();
                }

                @Override // com.google.common.util.concurrent.Service.Listener
                public void terminated(Service.State state) {
                    scheduledExecutorServiceNewSingleThreadScheduledExecutor.shutdown();
                }
            }, MoreExecutors.directExecutor());
            Supplier<String> supplier = new Supplier<String>() { // from class: com.google.common.util.concurrent.AbstractScheduledService.ServiceDelegate.1
                @Override // com.google.common.base.Supplier
                public String get() {
                    String strServiceName = AbstractScheduledService.this.serviceName();
                    String strValueOf = String.valueOf(ServiceDelegate.this.state());
                    return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + strServiceName.length() + 1, strServiceName, " ", strValueOf);
                }
            };
            Preconditions.checkNotNull(scheduledExecutorServiceNewSingleThreadScheduledExecutor);
            Preconditions.checkNotNull(supplier);
            this.executorService = new WrappingScheduledExecutorService(scheduledExecutorServiceNewSingleThreadScheduledExecutor) { // from class: com.google.common.util.concurrent.MoreExecutors.4
                public final /* synthetic */ Supplier val$nameSupplier;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass4(final ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor2) {
                    super(scheduledExecutorServiceNewSingleThreadScheduledExecutor2);
                    supplier = supplier;
                }

                @Override // com.google.common.util.concurrent.WrappingExecutorService
                public final <T> Callable<T> wrapTask(Callable<T> callable) {
                    Supplier supplier2 = supplier;
                    Preconditions.checkNotNull(supplier2);
                    Preconditions.checkNotNull(callable);
                    return new Callables.AnonymousClass3(supplier2, callable);
                }

                @Override // com.google.common.util.concurrent.WrappingExecutorService
                public final Runnable wrapTask(Runnable runnable) {
                    return Callables.threadRenaming(runnable, supplier);
                }
            };
            this.executorService.execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractScheduledService.ServiceDelegate.2
                @Override // java.lang.Runnable
                public void run() {
                    ServiceDelegate.this.lock.lock();
                    try {
                        Objects.requireNonNull(AbstractScheduledService.this);
                        ServiceDelegate serviceDelegate = ServiceDelegate.this;
                        serviceDelegate.runningTask = AbstractScheduledService.this.scheduler().schedule(AbstractScheduledService.this.delegate, ServiceDelegate.this.executorService, ServiceDelegate.this.task);
                        ServiceDelegate.this.notifyStarted();
                    } catch (Throwable th) {
                        try {
                            ServiceDelegate.this.notifyFailed(th);
                            if (ServiceDelegate.this.runningTask != null) {
                                ServiceDelegate.this.runningTask.cancel(false);
                            }
                        } finally {
                            ServiceDelegate.this.lock.unlock();
                        }
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStop() {
            Objects.requireNonNull(this.runningTask);
            Objects.requireNonNull(this.executorService);
            this.runningTask.cancel(false);
            this.executorService.execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractScheduledService.ServiceDelegate.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ServiceDelegate.this.lock.lock();
                        try {
                            if (ServiceDelegate.this.state() != Service.State.STOPPING) {
                                ServiceDelegate.this.lock.unlock();
                                return;
                            }
                            Objects.requireNonNull(AbstractScheduledService.this);
                            ServiceDelegate.this.lock.unlock();
                            ServiceDelegate.this.notifyStopped();
                        } catch (Throwable th) {
                            ServiceDelegate.this.lock.unlock();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        ServiceDelegate.this.notifyFailed(th2);
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public String toString() {
            return AbstractScheduledService.this.toString();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final void addListener(Service.Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    @Override // com.google.common.util.concurrent.Service
    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    @Override // com.google.common.util.concurrent.Service
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    public abstract void runOneIteration() throws Exception;

    public abstract Scheduler scheduler();

    public final String serviceName() {
        return getClass().getSimpleName();
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    @Override // com.google.common.util.concurrent.Service
    public final Service.State state() {
        return this.delegate.state();
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    public String toString() {
        String strServiceName = serviceName();
        String strValueOf = String.valueOf(state());
        return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + strServiceName.length() + 3, strServiceName, " [", strValueOf, "]");
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning(long j, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitRunning(j, timeUnit);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated(long j, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitTerminated(j, timeUnit);
    }
}
