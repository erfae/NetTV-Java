package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public abstract class AbstractExecutionThreadService implements Service {
    private static final Logger logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
    private final Service delegate = new AbstractService() { // from class: com.google.common.util.concurrent.AbstractExecutionThreadService.1
        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStart() {
            final AbstractExecutionThreadService abstractExecutionThreadService = AbstractExecutionThreadService.this;
            Objects.requireNonNull(abstractExecutionThreadService);
            MoreExecutors.renamingDecorator(new Executor() { // from class: com.google.common.util.concurrent.AbstractExecutionThreadService.2
                @Override // java.util.concurrent.Executor
                public void execute(Runnable runnable) {
                    MoreExecutors.newThread(AbstractExecutionThreadService.this.serviceName(), runnable).start();
                }
            }, new Supplier<String>() { // from class: com.google.common.util.concurrent.AbstractExecutionThreadService.1.1
                @Override // com.google.common.base.Supplier
                public String get() {
                    return AbstractExecutionThreadService.this.serviceName();
                }
            }).execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractExecutionThreadService.1.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Objects.requireNonNull(AbstractExecutionThreadService.this);
                        notifyStarted();
                        if (isRunning()) {
                            try {
                                AbstractExecutionThreadService.this.run();
                            } catch (Throwable th) {
                                try {
                                    Objects.requireNonNull(AbstractExecutionThreadService.this);
                                } catch (Exception e) {
                                    AbstractExecutionThreadService.logger.log(Level.WARNING, "Error while attempting to shut down the service after failure.", (Throwable) e);
                                }
                                notifyFailed(th);
                                return;
                            }
                        }
                        Objects.requireNonNull(AbstractExecutionThreadService.this);
                        notifyStopped();
                    } catch (Throwable th2) {
                        notifyFailed(th2);
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStop() {
            Objects.requireNonNull(AbstractExecutionThreadService.this);
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public String toString() {
            return AbstractExecutionThreadService.this.toString();
        }
    };

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

    public abstract void run() throws Exception;

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
