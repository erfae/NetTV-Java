package com.google.common.util.concurrent;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible
class ImmediateFuture<V> implements ListenableFuture<V> {
    public static final ListenableFuture<?> NULL = new ImmediateFuture(null);
    private static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());

    @ParametricNullness
    private final V value;

    public static final class ImmediateCancelledFuture<V> extends AbstractFuture.TrustedFuture<V> {
        public ImmediateCancelledFuture() {
            cancel(false);
        }
    }

    public static final class ImmediateFailedFuture<V> extends AbstractFuture.TrustedFuture<V> {
        public ImmediateFailedFuture(Throwable th) {
            setException(th);
        }
    }

    public ImmediateFuture(@ParametricNullness V v) {
        this.value = v;
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            Logger logger = log;
            Level level = Level.SEVERE;
            String strValueOf = String.valueOf(runnable);
            String strValueOf2 = String.valueOf(executor);
            logger.log(level, MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + strValueOf.length() + 57, "RuntimeException while executing runnable ", strValueOf, " with executor ", strValueOf2), (Throwable) e);
        }
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return false;
    }

    @Override // java.util.concurrent.Future
    @ParametricNullness
    public V get() {
        return this.value;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return true;
    }

    public String toString() {
        String string = super.toString();
        String strValueOf = String.valueOf(this.value);
        return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + Insets$$ExternalSyntheticOutline0.m(string, 27), string, "[status=SUCCESS, result=[", strValueOf, "]]");
    }

    @Override // java.util.concurrent.Future
    @ParametricNullness
    public V get(long j, TimeUnit timeUnit) throws ExecutionException {
        Preconditions.checkNotNull(timeUnit);
        return get();
    }
}
