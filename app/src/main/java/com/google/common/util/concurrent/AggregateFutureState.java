package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Sets;
import com.google.j2objc.annotations.ReflectionSupport;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
@ReflectionSupport(ReflectionSupport.Level.FULL)
abstract class AggregateFutureState<OutputT> extends AbstractFuture.TrustedFuture<OutputT> {
    private static final AtomicHelper ATOMIC_HELPER;
    private static final Logger log = Logger.getLogger(AggregateFutureState.class.getName());
    private volatile int remaining;

    @CheckForNull
    private volatile Set<Throwable> seenExceptions = null;

    public static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        public abstract void compareAndSetSeenExceptions(AggregateFutureState aggregateFutureState, Set set);

        public abstract int decrementAndGetRemainingCount(AggregateFutureState<?> aggregateFutureState);
    }

    public static final class SafeAtomicHelper extends AtomicHelper {
        public final AtomicIntegerFieldUpdater<AggregateFutureState<?>> remainingCountUpdater;
        public final AtomicReferenceFieldUpdater<AggregateFutureState<?>, Set<Throwable>> seenExceptionsUpdater;

        public SafeAtomicHelper(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicIntegerFieldUpdater atomicIntegerFieldUpdater) {
            super();
            this.seenExceptionsUpdater = atomicReferenceFieldUpdater;
            this.remainingCountUpdater = atomicIntegerFieldUpdater;
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        public final void compareAndSetSeenExceptions(AggregateFutureState aggregateFutureState, Set set) {
            AtomicReferenceFieldUpdater<AggregateFutureState<?>, Set<Throwable>> atomicReferenceFieldUpdater = this.seenExceptionsUpdater;
            while (!atomicReferenceFieldUpdater.compareAndSet(aggregateFutureState, null, set) && atomicReferenceFieldUpdater.get(aggregateFutureState) == null) {
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        public final int decrementAndGetRemainingCount(AggregateFutureState<?> aggregateFutureState) {
            return this.remainingCountUpdater.decrementAndGet(aggregateFutureState);
        }
    }

    public static final class SynchronizedAtomicHelper extends AtomicHelper {
        private SynchronizedAtomicHelper() {
            super();
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        public final void compareAndSetSeenExceptions(AggregateFutureState aggregateFutureState, Set set) {
            synchronized (aggregateFutureState) {
                if (aggregateFutureState.seenExceptions == null) {
                    aggregateFutureState.seenExceptions = set;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        public final int decrementAndGetRemainingCount(AggregateFutureState<?> aggregateFutureState) {
            int iAccess$306;
            synchronized (aggregateFutureState) {
                iAccess$306 = AggregateFutureState.access$306(aggregateFutureState);
            }
            return iAccess$306;
        }
    }

    static {
        AtomicHelper safeAtomicHelper;
        Throwable th = null;
        byte b = 0;
        try {
            safeAtomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(AggregateFutureState.class, Set.class, "seenExceptions"), AtomicIntegerFieldUpdater.newUpdater(AggregateFutureState.class, "remaining"));
        } catch (Throwable th2) {
            SynchronizedAtomicHelper synchronizedAtomicHelper = new SynchronizedAtomicHelper();
            th = th2;
            safeAtomicHelper = synchronizedAtomicHelper;
        }
        ATOMIC_HELPER = safeAtomicHelper;
        if (th != null) {
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
    }

    public AggregateFutureState(int i) {
        this.remaining = i;
    }

    public static /* synthetic */ int access$306(AggregateFutureState aggregateFutureState) {
        int i = aggregateFutureState.remaining - 1;
        aggregateFutureState.remaining = i;
        return i;
    }

    public abstract void addInitialException(Set<Throwable> set);

    public final void clearSeenExceptions() {
        this.seenExceptions = null;
    }

    public final int decrementRemainingAndGet() {
        return ATOMIC_HELPER.decrementAndGetRemainingCount(this);
    }

    public final Set<Throwable> getOrInitSeenExceptions() {
        Set<Throwable> set = this.seenExceptions;
        if (set != null) {
            return set;
        }
        Set<Throwable> setNewConcurrentHashSet = Sets.newConcurrentHashSet();
        addInitialException(setNewConcurrentHashSet);
        ATOMIC_HELPER.compareAndSetSeenExceptions(this, setNewConcurrentHashSet);
        Set<Throwable> set2 = this.seenExceptions;
        Objects.requireNonNull(set2);
        return set2;
    }
}
