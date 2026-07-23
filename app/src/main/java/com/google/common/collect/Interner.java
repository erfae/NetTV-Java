package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotMock;

/* JADX INFO: loaded from: classes2.dex */
@DoNotMock("Use Interners.new*Interner")
@GwtIncompatible
@ElementTypesAreNonnullByDefault
public interface Interner<E> {
    @CanIgnoreReturnValue
    E intern(E e);
}
