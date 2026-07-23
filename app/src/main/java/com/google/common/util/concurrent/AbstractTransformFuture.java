package com.google.common.util.concurrent;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.ForOverride;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible
abstract class AbstractTransformFuture<I, O, F, T> extends FluentFuture.TrustedFuture<O> implements Runnable {
    public static final /* synthetic */ int $r8$clinit = 0;

    @CheckForNull
    public F function;

    @CheckForNull
    public ListenableFuture<? extends I> inputFuture;

    public static final class AsyncTransformFuture<I, O> extends AbstractTransformFuture<I, O, AsyncFunction<? super I, ? extends O>, ListenableFuture<? extends O>> {
        public AsyncTransformFuture(ListenableFuture<? extends I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction) {
            super(listenableFuture, asyncFunction);
        }

        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        public final Object doTransform(Object obj, @ParametricNullness Object obj2) throws Exception {
            AsyncFunction asyncFunction = (AsyncFunction) obj;
            ListenableFuture<O> listenableFutureApply = asyncFunction.apply(obj2);
            Preconditions.checkNotNull(listenableFutureApply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", asyncFunction);
            return listenableFutureApply;
        }

        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        public final void setResult(Object obj) {
            setFuture((ListenableFuture) obj);
        }
    }

    public static final class TransformFuture<I, O> extends AbstractTransformFuture<I, O, Function<? super I, ? extends O>, O> {
        public TransformFuture(ListenableFuture<? extends I> listenableFuture, Function<? super I, ? extends O> function) {
            super(listenableFuture, function);
        }

        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        @ParametricNullness
        public final Object doTransform(Object obj, @ParametricNullness Object obj2) throws Exception {
            return ((Function) obj).apply(obj2);
        }

        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        public final void setResult(@ParametricNullness O o) {
            set(o);
        }
    }

    public AbstractTransformFuture(ListenableFuture<? extends I> listenableFuture, F f) {
        this.inputFuture = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.function = (F) Preconditions.checkNotNull(f);
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        maybePropagateCancellationTo(this.inputFuture);
        this.inputFuture = null;
        this.function = null;
    }

    @ParametricNullness
    @ForOverride
    public abstract T doTransform(F f, @ParametricNullness I i) throws Exception;

    @Override // com.google.common.util.concurrent.AbstractFuture
    @CheckForNull
    public final String pendingToString() {
        String strM;
        ListenableFuture<? extends I> listenableFuture = this.inputFuture;
        F f = this.function;
        String strPendingToString = super.pendingToString();
        if (listenableFuture != null) {
            String strValueOf = String.valueOf(listenableFuture);
            strM = MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 16, "inputFuture=[", strValueOf, "], ");
        } else {
            strM = "";
        }
        if (f != null) {
            String strValueOf2 = String.valueOf(f);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf2.length() + Insets$$ExternalSyntheticOutline0.m(strM, 11), strM, "function=[", strValueOf2, "]");
        }
        if (strPendingToString == null) {
            return null;
        }
        String strValueOf3 = String.valueOf(strM);
        return strPendingToString.length() != 0 ? strValueOf3.concat(strPendingToString) : new String(strValueOf3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Runnable
    public final void run() {
        ListenableFuture<? extends I> listenableFuture = this.inputFuture;
        F f = this.function;
        if ((isCancelled() | (listenableFuture == null)) || (f == null)) {
            return;
        }
        this.inputFuture = null;
        if (listenableFuture.isCancelled()) {
            setFuture(listenableFuture);
            return;
        }
        try {
            try {
                Object objDoTransform = doTransform(f, Futures.getDone(listenableFuture));
                this.function = null;
                setResult(objDoTransform);
            } catch (Throwable th) {
                try {
                    setException(th);
                } finally {
                    this.function = null;
                }
            }
        } catch (Error e) {
            setException(e);
        } catch (CancellationException unused) {
            cancel(false);
        } catch (RuntimeException e2) {
            setException(e2);
        } catch (ExecutionException e3) {
            setException(e3.getCause());
        }
    }

    @ForOverride
    public abstract void setResult(@ParametricNullness T t);
}
