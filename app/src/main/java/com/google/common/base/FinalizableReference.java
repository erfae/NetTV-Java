package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.DoNotMock;

/* JADX INFO: loaded from: classes2.dex */
@DoNotMock("Use an instance of one of the Finalizable*Reference classes")
@GwtIncompatible
@ElementTypesAreNonnullByDefault
public interface FinalizableReference {
    void finalizeReferent();
}
