package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.lang.ref.PhantomReference;

/* JADX INFO: loaded from: classes2.dex */
@GwtIncompatible
@ElementTypesAreNonnullByDefault
public abstract class FinalizablePhantomReference<T> extends PhantomReference<T> implements FinalizableReference {
}
