package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public abstract class AbstractIdleService implements Service {
    private final Service delegate;
    private final Supplier<String> threadNameSupplier;

    /* JADX INFO: renamed from: com.google.common.util.concurrent.AbstractIdleService$1, reason: invalid class name */
    class AnonymousClass1 implements Executor {
        public AnonymousClass1() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            MoreExecutors.newThread((String) AbstractIdleService.this.threadNameSupplier.get(), runnable).start();
        }
    }

    public final class DelegateService extends AbstractService {
        private DelegateService() {
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStart() {
            AbstractIdleService abstractIdleService = AbstractIdleService.this;
            Objects.requireNonNull(abstractIdleService);
            MoreExecutors.renamingDecorator(abstractIdleService.new AnonymousClass1(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractIdleService.DelegateService.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AbstractIdleService.this.startUp();
                        DelegateService.this.notifyStarted();
                    } catch (Throwable th) {
                        DelegateService.this.notifyFailed(th);
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStop() {
            AbstractIdleService abstractIdleService = AbstractIdleService.this;
            Objects.requireNonNull(abstractIdleService);
            MoreExecutors.renamingDecorator(abstractIdleService.new AnonymousClass1(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractIdleService.DelegateService.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AbstractIdleService.this.shutDown();
                        DelegateService.this.notifyStopped();
                    } catch (Throwable th) {
                        DelegateService.this.notifyFailed(th);
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public String toString() {
            return AbstractIdleService.this.toString();
        }

        public /* synthetic */ DelegateService(AbstractIdleService abstractIdleService, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public final class ThreadNameSupplier implements Supplier<String> {
        private ThreadNameSupplier() {
        }

        public /* synthetic */ ThreadNameSupplier(AbstractIdleService abstractIdleService, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.google.common.base.Supplier
        public String get() {
            String simpleName = AbstractIdleService.this.getClass().getSimpleName();
            String strValueOf = String.valueOf(AbstractIdleService.this.state());
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + simpleName.length() + 1, simpleName, " ", strValueOf);
        }
    }

    public AbstractIdleService() {
        AnonymousClass1 anonymousClass1 = null;
        this.threadNameSupplier = new ThreadNameSupplier(this, anonymousClass1);
        this.delegate = new DelegateService(this, anonymousClass1);
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

    public abstract void shutDown() throws Exception;

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    public abstract void startUp() throws Exception;

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
        String simpleName = getClass().getSimpleName();
        String strValueOf = String.valueOf(state());
        return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + simpleName.length() + 3, simpleName, " [", strValueOf, "]");
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
