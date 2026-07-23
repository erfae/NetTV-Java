package com.google.common.util.concurrent;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;
import com.google.errorprone.annotations.ForOverride;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.lang.Throwable;
import java.util.concurrent.ExecutionException;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible
abstract class AbstractCatchingFuture<V, X extends Throwable, F, T> extends FluentFuture.TrustedFuture<V> implements Runnable {
    public static final /* synthetic */ int $r8$clinit = 0;

    @CheckForNull
    public Class<X> exceptionType;

    @CheckForNull
    public F fallback;

    @CheckForNull
    public ListenableFuture<? extends V> inputFuture;

    public static final class AsyncCatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, AsyncFunction<? super X, ? extends V>, ListenableFuture<? extends V>> {
        public AsyncCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction) {
            super(listenableFuture, cls, asyncFunction);
        }

        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        public final Object doFallback(Object obj, Throwable th) throws Exception {
            AsyncFunction asyncFunction = (AsyncFunction) obj;
            ListenableFuture listenableFutureApply = asyncFunction.apply(th);
            Preconditions.checkNotNull(listenableFutureApply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", asyncFunction);
            return listenableFutureApply;
        }

        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        public final void setResult(Object obj) {
            setFuture((ListenableFuture) obj);
        }
    }

    public static final class CatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, Function<? super X, ? extends V>, V> {
        public CatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function) {
            super(listenableFuture, cls, function);
        }

        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        @ParametricNullness
        public final Object doFallback(Object obj, Throwable th) throws Exception {
            return ((Function) obj).apply(th);
        }

        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        public final void setResult(@ParametricNullness V v) {
            set(v);
        }
    }

    public AbstractCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, F f) {
        this.inputFuture = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.exceptionType = (Class) Preconditions.checkNotNull(cls);
        this.fallback = (F) Preconditions.checkNotNull(f);
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        maybePropagateCancellationTo(this.inputFuture);
        this.inputFuture = null;
        this.exceptionType = null;
        this.fallback = null;
    }

    @ParametricNullness
    @ForOverride
    public abstract T doFallback(F f, X x) throws Exception;

    @Override // com.google.common.util.concurrent.AbstractFuture
    @CheckForNull
    public final String pendingToString() {
        String strM;
        ListenableFuture<? extends V> listenableFuture = this.inputFuture;
        Class<X> cls = this.exceptionType;
        F f = this.fallback;
        String strPendingToString = super.pendingToString();
        if (listenableFuture != null) {
            String strValueOf = String.valueOf(listenableFuture);
            strM = MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 16, "inputFuture=[", strValueOf, "], ");
        } else {
            strM = "";
        }
        if (cls != null && f != null) {
            String strValueOf2 = String.valueOf(cls);
            String strValueOf3 = String.valueOf(f);
            return Insets$$ExternalSyntheticOutline0.m(MainActivity$$ExternalSyntheticOutline0.m209m(strValueOf3.length() + strValueOf2.length() + Insets$$ExternalSyntheticOutline0.m(strM, 29), strM, "exceptionType=[", strValueOf2, "], fallback=["), strValueOf3, "]");
        }
        if (strPendingToString == null) {
            return null;
        }
        String strValueOf4 = String.valueOf(strM);
        return strPendingToString.length() != 0 ? strValueOf4.concat(strPendingToString) : new String(strValueOf4);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v4, types: [F, java.lang.Class<X extends java.lang.Throwable>] */
    @Override // java.lang.Runnable
    public final void run() {
        Object done;
        ListenableFuture<? extends V> listenableFuture = this.inputFuture;
        Class<X> cls = this.exceptionType;
        F f = this.fallback;
        if (((f == null) || ((listenableFuture == 0) | (cls == null))) || isCancelled()) {
            return;
        }
        ?? r3 = (Class<X>) null;
        this.inputFuture = null;
        try {
            th = listenableFuture instanceof InternalFutureFailureAccess ? InternalFutures.tryInternalFastPathGetFailure((InternalFutureFailureAccess) listenableFuture) : null;
            done = th == null ? Futures.getDone(listenableFuture) : null;
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause == null) {
                String strValueOf = String.valueOf(listenableFuture.getClass());
                String strValueOf2 = String.valueOf(e.getClass());
                StringBuilder sbM209m = MainActivity$$ExternalSyntheticOutline0.m209m(strValueOf2.length() + strValueOf.length() + 35, "Future type ", strValueOf, " threw ", strValueOf2);
                sbM209m.append(" without a cause");
                cause = new NullPointerException(sbM209m.toString());
            }
            th = cause;
        } catch (Throwable th) {
            th = th;
        }
        if (th == null) {
            set(done);
            return;
        }
        if (!cls.isInstance(th)) {
            setFuture(listenableFuture);
            return;
        }
        try {
            Object objDoFallback = doFallback(f, th);
            this.exceptionType = null;
            this.fallback = null;
            setResult(objDoFallback);
        } catch (Throwable th2) {
            try {
                setException(th2);
            } finally {
                this.exceptionType = null;
                this.fallback = null;
            }
        }
    }

    @ForOverride
    public abstract void setResult(@ParametricNullness T t);
}
