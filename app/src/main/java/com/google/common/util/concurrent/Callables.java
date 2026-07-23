package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
public final class Callables {

    /* JADX INFO: renamed from: com.google.common.util.concurrent.Callables$3, reason: invalid class name */
    class AnonymousClass3 implements Callable<Object> {
        public final /* synthetic */ Callable val$callable;
        public final /* synthetic */ Supplier val$nameSupplier;

        public AnonymousClass3(Supplier supplier, Callable callable) {
            this.val$nameSupplier = supplier;
            this.val$callable = callable;
        }

        @Override // java.util.concurrent.Callable
        @ParametricNullness
        public Object call() throws Exception {
            Thread threadCurrentThread = Thread.currentThread();
            String name = threadCurrentThread.getName();
            boolean zTrySetName = Callables.trySetName((String) this.val$nameSupplier.get(), threadCurrentThread);
            try {
                return this.val$callable.call();
            } finally {
                if (zTrySetName) {
                    Callables.trySetName(name, threadCurrentThread);
                }
            }
        }
    }

    private Callables() {
    }

    @Beta
    @GwtIncompatible
    public static <T> AsyncCallable<T> asAsyncCallable(final Callable<T> callable, final ListeningExecutorService listeningExecutorService) {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(listeningExecutorService);
        return new AsyncCallable<T>() { // from class: com.google.common.util.concurrent.Callables.2
            @Override // com.google.common.util.concurrent.AsyncCallable
            public ListenableFuture<T> call() throws Exception {
                return listeningExecutorService.submit((Callable) callable);
            }
        };
    }

    public static <T> Callable<T> returning(@ParametricNullness final T t) {
        return new Callable<T>() { // from class: com.google.common.util.concurrent.Callables.1
            @Override // java.util.concurrent.Callable
            @ParametricNullness
            public T call() {
                return (T) t;
            }
        };
    }

    @GwtIncompatible
    public static Runnable threadRenaming(final Runnable runnable, final Supplier<String> supplier) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(runnable);
        return new Runnable() { // from class: com.google.common.util.concurrent.Callables.4
            @Override // java.lang.Runnable
            public void run() {
                Thread threadCurrentThread = Thread.currentThread();
                String name = threadCurrentThread.getName();
                boolean zTrySetName = Callables.trySetName((String) supplier.get(), threadCurrentThread);
                try {
                    runnable.run();
                } finally {
                    if (zTrySetName) {
                        Callables.trySetName(name, threadCurrentThread);
                    }
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GwtIncompatible
    public static boolean trySetName(String str, Thread thread) {
        try {
            thread.setName(str);
            return true;
        } catch (SecurityException unused) {
            return false;
        }
    }
}
