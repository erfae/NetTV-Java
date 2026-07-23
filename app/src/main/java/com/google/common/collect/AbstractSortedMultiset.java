package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
abstract class AbstractSortedMultiset<E> extends AbstractMultiset<E> implements SortedMultiset<E> {

    @GwtTransient
    public final Comparator<? super E> comparator;

    @CheckForNull
    private transient SortedMultiset<E> descendingMultiset;

    public AbstractSortedMultiset() {
        this(Ordering.natural());
    }

    @Override // com.google.common.collect.SortedMultiset, com.google.common.collect.SortedIterable
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    @Override // com.google.common.collect.AbstractMultiset
    public final Set createElementSet() {
        return new SortedMultisets.NavigableElementSet(this);
    }

    public abstract Iterator<Multiset.Entry<E>> descendingEntryIterator();

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> descendingMultiset() {
        SortedMultiset<E> sortedMultiset = this.descendingMultiset;
        if (sortedMultiset != null) {
            return sortedMultiset;
        }
        DescendingMultiset<Object> descendingMultiset = new DescendingMultiset<Object>() { // from class: com.google.common.collect.AbstractSortedMultiset.1DescendingMultisetImpl
            @Override // com.google.common.collect.DescendingMultiset
            public final Iterator<Multiset.Entry<Object>> entryIterator() {
                return AbstractSortedMultiset.this.descendingEntryIterator();
            }

            @Override // com.google.common.collect.DescendingMultiset
            public final SortedMultiset<Object> forwardMultiset() {
                return AbstractSortedMultiset.this;
            }

            @Override // com.google.common.collect.DescendingMultiset, com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Object> iterator() {
                return Multisets.iteratorImpl(AbstractSortedMultiset.this.descendingMultiset());
            }
        };
        this.descendingMultiset = descendingMultiset;
        return descendingMultiset;
    }

    @Override // com.google.common.collect.SortedMultiset
    @CheckForNull
    public Multiset.Entry<E> firstEntry() {
        Iterator<Multiset.Entry<E>> itEntryIterator = entryIterator();
        if (itEntryIterator.hasNext()) {
            return itEntryIterator.next();
        }
        return null;
    }

    @Override // com.google.common.collect.SortedMultiset
    @CheckForNull
    public Multiset.Entry<E> lastEntry() {
        Iterator<Multiset.Entry<E>> itDescendingEntryIterator = descendingEntryIterator();
        if (itDescendingEntryIterator.hasNext()) {
            return itDescendingEntryIterator.next();
        }
        return null;
    }

    @Override // com.google.common.collect.SortedMultiset
    @CheckForNull
    public Multiset.Entry<E> pollFirstEntry() {
        Iterator<Multiset.Entry<E>> itEntryIterator = entryIterator();
        if (!itEntryIterator.hasNext()) {
            return null;
        }
        Multiset.Entry<E> next = itEntryIterator.next();
        Multiset.Entry<E> entryImmutableEntry = Multisets.immutableEntry(next.getElement(), next.getCount());
        itEntryIterator.remove();
        return entryImmutableEntry;
    }

    @Override // com.google.common.collect.SortedMultiset
    @CheckForNull
    public Multiset.Entry<E> pollLastEntry() {
        Iterator<Multiset.Entry<E>> itDescendingEntryIterator = descendingEntryIterator();
        if (!itDescendingEntryIterator.hasNext()) {
            return null;
        }
        Multiset.Entry<E> next = itDescendingEntryIterator.next();
        Multiset.Entry<E> entryImmutableEntry = Multisets.immutableEntry(next.getElement(), next.getCount());
        itDescendingEntryIterator.remove();
        return entryImmutableEntry;
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> subMultiset(@ParametricNullness E e, BoundType boundType, @ParametricNullness E e2, BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        return tailMultiset(e, boundType).headMultiset(e2, boundType2);
    }

    public AbstractSortedMultiset(Comparator<? super E> comparator) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator);
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    public NavigableSet<E> elementSet() {
        return (NavigableSet) super.elementSet();
    }
}
