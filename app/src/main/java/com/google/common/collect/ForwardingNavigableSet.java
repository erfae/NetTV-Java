package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import java.util.Iterator;
import java.util.NavigableSet;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtIncompatible
@ElementTypesAreNonnullByDefault
public abstract class ForwardingNavigableSet<E> extends ForwardingSortedSet<E> implements NavigableSet<E> {

    @Beta
    public class StandardDescendingSet extends Sets.DescendingSet<E> {
        public StandardDescendingSet(ForwardingNavigableSet forwardingNavigableSet) {
            super(forwardingNavigableSet);
        }
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E ceiling(@ParametricNullness E e) {
        return delegate().ceiling(e);
    }

    @Override // com.google.common.collect.ForwardingSortedSet, com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    public abstract NavigableSet<E> delegate();

    @Override // java.util.NavigableSet
    public Iterator<E> descendingIterator() {
        return delegate().descendingIterator();
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> descendingSet() {
        return delegate().descendingSet();
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E floor(@ParametricNullness E e) {
        return delegate().floor(e);
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> headSet(@ParametricNullness E e, boolean z) {
        return delegate().headSet(e, z);
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E higher(@ParametricNullness E e) {
        return delegate().higher(e);
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E lower(@ParametricNullness E e) {
        return delegate().lower(e);
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E pollFirst() {
        return delegate().pollFirst();
    }

    @Override // java.util.NavigableSet
    @CheckForNull
    public E pollLast() {
        return delegate().pollLast();
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> subSet(@ParametricNullness E e, boolean z, @ParametricNullness E e2, boolean z2) {
        return delegate().subSet(e, z, e2, z2);
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> tailSet(@ParametricNullness E e, boolean z) {
        return delegate().tailSet(e, z);
    }
}
