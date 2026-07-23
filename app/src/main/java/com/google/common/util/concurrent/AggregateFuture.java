package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible
abstract class AggregateFuture<InputT, OutputT> extends AggregateFutureState<OutputT> {
    private static final Logger logger = Logger.getLogger(AggregateFuture.class.getName());
    private final boolean allMustSucceed;
    private final boolean collectsValues;

    @CheckForNull
    private ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures;

    public enum ReleaseResourcesReason {
        OUTPUT_FUTURE_DONE,
        ALL_INPUT_FUTURES_PROCESSED
    }

    public AggregateFuture(ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection, boolean z, boolean z2) {
        super(immutableCollection.size());
        this.futures = (ImmutableCollection) Preconditions.checkNotNull(immutableCollection);
        this.allMustSucceed = z;
        this.collectsValues = z2;
    }

    private static boolean addCausalChain(Set<Throwable> set, Throwable th) {
        while (th != null) {
            if (!set.add(th)) {
                return false;
            }
            th = th.getCause();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void collectValueFromNonCancelledFuture(int i, Future<? extends InputT> future) {
        try {
            collectOneValue(i, Futures.getDone(future));
        } catch (ExecutionException e) {
            handleException(e.getCause());
        } catch (Throwable th) {
            handleException(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decrementCountAndMaybeComplete(@CheckForNull ImmutableCollection<? extends Future<? extends InputT>> immutableCollection) {
        int iDecrementRemainingAndGet = decrementRemainingAndGet();
        Preconditions.checkState(iDecrementRemainingAndGet >= 0, "Less than 0 remaining futures");
        if (iDecrementRemainingAndGet == 0) {
            processCompleted(immutableCollection);
        }
    }

    private void handleException(Throwable th) {
        Preconditions.checkNotNull(th);
        if (this.allMustSucceed && !setException(th) && addCausalChain(getOrInitSeenExceptions(), th)) {
            log(th);
        } else if (th instanceof Error) {
            log(th);
        }
    }

    private static void log(Throwable th) {
        logger.log(Level.SEVERE, th instanceof Error ? "Input Future failed with Error" : "Got more than one input Future failure. Logging failures after the first", th);
    }

    private void processCompleted(@CheckForNull ImmutableCollection<? extends Future<? extends InputT>> immutableCollection) {
        if (immutableCollection != null) {
            int i = 0;
            UnmodifiableIterator<? extends Future<? extends InputT>> it = immutableCollection.iterator();
            while (it.hasNext()) {
                Future<? extends InputT> next = it.next();
                if (!next.isCancelled()) {
                    collectValueFromNonCancelledFuture(i, next);
                }
                i++;
            }
        }
        clearSeenExceptions();
        handleAllCompleted();
        releaseResources(ReleaseResourcesReason.ALL_INPUT_FUTURES_PROCESSED);
    }

    @Override // com.google.common.util.concurrent.AggregateFutureState
    public final void addInitialException(Set<Throwable> set) {
        Preconditions.checkNotNull(set);
        if (isCancelled()) {
            return;
        }
        Throwable thTryInternalFastPathGetFailure = tryInternalFastPathGetFailure();
        Objects.requireNonNull(thTryInternalFastPathGetFailure);
        addCausalChain(set, thTryInternalFastPathGetFailure);
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection = this.futures;
        releaseResources(ReleaseResourcesReason.OUTPUT_FUTURE_DONE);
        if (isCancelled() && (immutableCollection != null)) {
            boolean zWasInterrupted = wasInterrupted();
            UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it = immutableCollection.iterator();
            while (it.hasNext()) {
                it.next().cancel(zWasInterrupted);
            }
        }
    }

    public abstract void collectOneValue(int i, @ParametricNullness InputT inputt);

    public abstract void handleAllCompleted();

    public final void init() {
        Objects.requireNonNull(this.futures);
        if (this.futures.isEmpty()) {
            handleAllCompleted();
            return;
        }
        if (!this.allMustSucceed) {
            final ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection = this.collectsValues ? this.futures : null;
            Runnable runnable = new Runnable() { // from class: com.google.common.util.concurrent.AggregateFuture.2
                @Override // java.lang.Runnable
                public void run() {
                    AggregateFuture.this.decrementCountAndMaybeComplete(immutableCollection);
                }
            };
            UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it = this.futures.iterator();
            while (it.hasNext()) {
                it.next().addListener(runnable, MoreExecutors.directExecutor());
            }
            return;
        }
        final int i = 0;
        UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it2 = this.futures.iterator();
        while (it2.hasNext()) {
            final ListenableFuture<? extends InputT> next = it2.next();
            next.addListener(new Runnable() { // from class: com.google.common.util.concurrent.AggregateFuture.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (next.isCancelled()) {
                            AggregateFuture.this.futures = null;
                            AggregateFuture.this.cancel(false);
                        } else {
                            AggregateFuture.this.collectValueFromNonCancelledFuture(i, next);
                        }
                    } finally {
                        AggregateFuture.this.decrementCountAndMaybeComplete(null);
                    }
                }
            }, MoreExecutors.directExecutor());
            i++;
        }
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    @CheckForNull
    public final String pendingToString() {
        ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection = this.futures;
        if (immutableCollection == null) {
            return super.pendingToString();
        }
        String strValueOf = String.valueOf(immutableCollection);
        return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 8, "futures=", strValueOf);
    }

    @ForOverride
    @OverridingMethodsMustInvokeSuper
    public void releaseResources(ReleaseResourcesReason releaseResourcesReason) {
        Preconditions.checkNotNull(releaseResourcesReason);
        this.futures = null;
    }
}
