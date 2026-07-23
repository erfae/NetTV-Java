package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Iterator;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public abstract class ForwardingIterator<T> extends ForwardingObject implements Iterator<T> {
    @Override // com.google.common.collect.ForwardingObject
    public abstract Iterator<T> delegate();

    @Override // java.util.Iterator
    public boolean hasNext() {
        return delegate().hasNext();
    }

    @ParametricNullness
    @CanIgnoreReturnValue
    public T next() {
        return delegate().next();
    }

    public void remove() {
        delegate().remove();
    }
}
