package com.google.common.util.concurrent;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.j2objc.annotations.ReflectionSupport;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.LockSupport;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
@ReflectionSupport(ReflectionSupport.Level.FULL)
abstract class InterruptibleTask<T> extends AtomicReference<Runnable> implements Runnable {
    private static final Runnable DONE;
    private static final int MAX_BUSY_WAIT_SPINS = 1000;
    private static final Runnable PARKED;

    @VisibleForTesting
    public static final class Blocker extends AbstractOwnableSynchronizer implements Runnable {
        private final InterruptibleTask<?> task;

        /* JADX INFO: Access modifiers changed from: private */
        public void setOwner(Thread thread) {
            setExclusiveOwnerThread(thread);
        }

        @Override // java.lang.Runnable
        public void run() {
        }

        public String toString() {
            return this.task.toString();
        }

        private Blocker(InterruptibleTask<?> interruptibleTask) {
            this.task = interruptibleTask;
        }
    }

    public static final class DoNothingRunnable implements Runnable {
        private DoNothingRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    static {
        DONE = new DoNothingRunnable();
        PARKED = new DoNothingRunnable();
    }

    private void waitForInterrupt(Thread thread) {
        Runnable runnable = get();
        Blocker blocker = null;
        boolean z = false;
        int i = 0;
        while (true) {
            boolean z2 = runnable instanceof Blocker;
            if (!z2 && runnable != PARKED) {
                break;
            }
            if (z2) {
                blocker = (Blocker) runnable;
            }
            i++;
            if (i > 1000) {
                Runnable runnable2 = PARKED;
                if (runnable == runnable2 || compareAndSet(runnable, runnable2)) {
                    z = Thread.interrupted() || z;
                    LockSupport.park(blocker);
                }
            } else {
                Thread.yield();
            }
            runnable = get();
        }
        if (z) {
            thread.interrupt();
        }
    }

    public abstract void afterRanInterruptiblyFailure(Throwable th);

    public abstract void afterRanInterruptiblySuccess(@ParametricNullness T t);

    public final void interruptTask() {
        Runnable runnable = get();
        if (runnable instanceof Thread) {
            Blocker blocker = new Blocker();
            blocker.setOwner(Thread.currentThread());
            if (compareAndSet(runnable, blocker)) {
                try {
                    ((Thread) runnable).interrupt();
                } finally {
                    if (getAndSet(DONE) == PARKED) {
                        LockSupport.unpark((Thread) runnable);
                    }
                }
            }
        }
    }

    public abstract boolean isDone();

    @Override // java.lang.Runnable
    public final void run() {
        Thread threadCurrentThread = Thread.currentThread();
        T tRunInterruptibly = null;
        if (compareAndSet(null, threadCurrentThread)) {
            boolean z = !isDone();
            if (z) {
                try {
                    tRunInterruptibly = runInterruptibly();
                } catch (Throwable th) {
                    if (!compareAndSet(threadCurrentThread, DONE)) {
                        waitForInterrupt(threadCurrentThread);
                    }
                    if (z) {
                        afterRanInterruptiblyFailure(th);
                        return;
                    }
                    return;
                }
            }
            if (!compareAndSet(threadCurrentThread, DONE)) {
                waitForInterrupt(threadCurrentThread);
            }
            if (z) {
                afterRanInterruptiblySuccess(tRunInterruptibly);
            }
        }
    }

    @ParametricNullness
    public abstract T runInterruptibly() throws Exception;

    public abstract String toPendingString();

    @Override // java.util.concurrent.atomic.AtomicReference
    public final String toString() {
        String strM;
        Runnable runnable = get();
        if (runnable == DONE) {
            strM = "running=[DONE]";
        } else if (runnable instanceof Blocker) {
            strM = "running=[INTERRUPTED]";
        } else if (runnable instanceof Thread) {
            String name = ((Thread) runnable).getName();
            strM = MainActivity$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m(name, 21), "running=[RUNNING ON ", name, "]");
        } else {
            strM = "running=[NOT STARTED YET]";
        }
        String pendingString = toPendingString();
        return MainActivity$$ExternalSyntheticOutline0.m(Insets$$ExternalSyntheticOutline0.m(pendingString, Insets$$ExternalSyntheticOutline0.m(strM, 2)), strM, ", ", pendingString);
    }
}
