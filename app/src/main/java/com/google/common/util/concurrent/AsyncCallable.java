package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: classes2.dex */
@Beta
@ElementTypesAreNonnullByDefault
@GwtCompatible
public interface AsyncCallable<V> {
    ListenableFuture<V> call() throws Exception;
}
