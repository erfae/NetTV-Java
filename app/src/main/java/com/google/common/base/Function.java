package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public interface Function<F, T> {
    @CanIgnoreReturnValue
    @ParametricNullness
    T apply(@ParametricNullness F f);

    boolean equals(@CheckForNull Object obj);
}
