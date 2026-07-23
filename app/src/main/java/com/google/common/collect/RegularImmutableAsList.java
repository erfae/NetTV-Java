package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
class RegularImmutableAsList<E> extends ImmutableAsList<E> {
    private final ImmutableCollection<E> delegate;
    private final ImmutableList<? extends E> delegateList;

    @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    public final int copyIntoArray(Object[] objArr, int i) {
        return this.delegateList.copyIntoArray(objArr, i);
    }

    @Override // com.google.common.collect.ImmutableAsList
    public final ImmutableCollection<E> delegateCollection() {
        return this.delegate;
    }

    @Override // java.util.List
    public E get(int i) {
        return this.delegateList.get(i);
    }

    @Override // com.google.common.collect.ImmutableCollection
    @CheckForNull
    public final Object[] internalArray() {
        return this.delegateList.internalArray();
    }

    @Override // com.google.common.collect.ImmutableCollection
    public final int internalArrayEnd() {
        return this.delegateList.internalArrayEnd();
    }

    @Override // com.google.common.collect.ImmutableCollection
    public final int internalArrayStart() {
        return this.delegateList.internalArrayStart();
    }

    @Override // com.google.common.collect.ImmutableList, java.util.List
    public UnmodifiableListIterator<E> listIterator(int i) {
        return this.delegateList.listIterator(i);
    }
}
