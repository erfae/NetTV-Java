package androidx.concurrent.futures;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class ResolvableFuture<V> extends AbstractResolvableFuture<V> {
    private ResolvableFuture() {
    }

    public static <V> ResolvableFuture<V> create() {
        return new ResolvableFuture<>();
    }

    @Override // androidx.concurrent.futures.AbstractResolvableFuture
    public boolean set(@Nullable V v) {
        return super.set(v);
    }

    @Override // androidx.concurrent.futures.AbstractResolvableFuture
    public boolean setException(Throwable th) {
        return super.setException(th);
    }

    public boolean setFuture(ListenableFuture<? extends V> listenableFuture) {
        AbstractResolvableFuture.Failure failure;
        Objects.requireNonNull(listenableFuture);
        Object obj = this.value;
        if (obj == null) {
            if (listenableFuture.isDone()) {
                if (!AbstractResolvableFuture.ATOMIC_HELPER.casValue(this, null, AbstractResolvableFuture.getFutureValue(listenableFuture))) {
                    return false;
                }
                AbstractResolvableFuture.complete(this);
            } else {
                AbstractResolvableFuture.SetFuture setFuture = new AbstractResolvableFuture.SetFuture(this, listenableFuture);
                if (AbstractResolvableFuture.ATOMIC_HELPER.casValue(this, null, setFuture)) {
                    try {
                        listenableFuture.addListener(setFuture, DirectExecutor.INSTANCE);
                    } catch (Throwable th) {
                        try {
                            failure = new AbstractResolvableFuture.Failure(th);
                        } catch (Throwable unused) {
                            failure = AbstractResolvableFuture.Failure.FALLBACK_INSTANCE;
                        }
                        AbstractResolvableFuture.ATOMIC_HELPER.casValue(this, setFuture, failure);
                    }
                } else {
                    obj = this.value;
                }
            }
            return true;
        }
        if (!(obj instanceof AbstractResolvableFuture.Cancellation)) {
            return false;
        }
        listenableFuture.cancel(((AbstractResolvableFuture.Cancellation) obj).wasInterrupted);
        return false;
    }
}
