package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.math.IntMath;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public final class Collections2 {

    public static class FilteredCollection<E> extends AbstractCollection<E> {
        public final Predicate<? super E> predicate;
        public final Collection<E> unfiltered;

        public FilteredCollection(Collection<E> collection, Predicate<? super E> predicate) {
            this.unfiltered = collection;
            this.predicate = predicate;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean add(@ParametricNullness E e) {
            Preconditions.checkArgument(this.predicate.apply(e));
            return this.unfiltered.add(e);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            Iterator<? extends E> it = collection.iterator();
            while (it.hasNext()) {
                Preconditions.checkArgument(this.predicate.apply(it.next()));
            }
            return this.unfiltered.addAll(collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            Iterables.removeIf(this.unfiltered, this.predicate);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@CheckForNull Object obj) {
            if (Collections2.safeContains(this.unfiltered, obj)) {
                return this.predicate.apply(obj);
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return Collections2.containsAllImpl(this, collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return !Iterables.any(this.unfiltered, this.predicate);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<E> iterator() {
            return Iterators.filter(this.unfiltered.iterator(), this.predicate);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(@CheckForNull Object obj) {
            return contains(obj) && this.unfiltered.remove(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            Iterator<E> it = this.unfiltered.iterator();
            boolean z = false;
            while (it.hasNext()) {
                E next = it.next();
                if (this.predicate.apply(next) && collection.contains(next)) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            Iterator<E> it = this.unfiltered.iterator();
            boolean z = false;
            while (it.hasNext()) {
                E next = it.next();
                if (this.predicate.apply(next) && !collection.contains(next)) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            Iterator<E> it = this.unfiltered.iterator();
            int i = 0;
            while (it.hasNext()) {
                if (this.predicate.apply(it.next())) {
                    i++;
                }
            }
            return i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) Lists.newArrayList(iterator()).toArray(tArr);
        }
    }

    public static final class OrderedPermutationCollection<E> extends AbstractCollection<List<E>> {
        public final Comparator<? super E> comparator;
        public final ImmutableList<E> inputList;
        public final int size;

        public OrderedPermutationCollection(Iterable<E> iterable, Comparator<? super E> comparator) {
            ImmutableList<E> immutableListSortedCopyOf = ImmutableList.sortedCopyOf(comparator, iterable);
            this.inputList = immutableListSortedCopyOf;
            this.comparator = comparator;
            this.size = calculateSize(immutableListSortedCopyOf, comparator);
        }

        private static <E> int calculateSize(List<E> list, Comparator<? super E> comparator) {
            int i = 1;
            int iSaturatedMultiply = 1;
            int i2 = 1;
            while (i < list.size()) {
                if (comparator.compare(list.get(i - 1), list.get(i)) < 0) {
                    iSaturatedMultiply = IntMath.saturatedMultiply(iSaturatedMultiply, IntMath.binomial(i, i2));
                    i2 = 0;
                    if (iSaturatedMultiply == Integer.MAX_VALUE) {
                        return Integer.MAX_VALUE;
                    }
                }
                i++;
                i2++;
            }
            return IntMath.saturatedMultiply(iSaturatedMultiply, IntMath.binomial(i, i2));
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@CheckForNull Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.isPermutation(this.inputList, (List) obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<List<E>> iterator() {
            return new OrderedPermutationIterator(this.inputList, this.comparator);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.size;
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            String strValueOf = String.valueOf(this.inputList);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 30, "orderedPermutationCollection(", strValueOf, ")");
        }
    }

    public static final class OrderedPermutationIterator<E> extends AbstractIterator<List<E>> {
        public final Comparator<? super E> comparator;

        @CheckForNull
        public List<E> nextPermutation;

        public OrderedPermutationIterator(List<E> list, Comparator<? super E> comparator) {
            this.nextPermutation = Lists.newArrayList(list);
            this.comparator = comparator;
        }

        @Override // com.google.common.collect.AbstractIterator
        @CheckForNull
        public final Object computeNext() {
            List<E> list = this.nextPermutation;
            if (list == null) {
                endOfData();
                return null;
            }
            ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) list);
            Objects.requireNonNull(this.nextPermutation);
            int size = this.nextPermutation.size() - 2;
            while (true) {
                if (size < 0) {
                    size = -1;
                    break;
                }
                if (this.comparator.compare(this.nextPermutation.get(size), this.nextPermutation.get(size + 1)) < 0) {
                    break;
                }
                size--;
            }
            if (size != -1) {
                Objects.requireNonNull(this.nextPermutation);
                Objects.requireNonNull(this.nextPermutation);
                E e = this.nextPermutation.get(size);
                for (int size2 = this.nextPermutation.size() - 1; size2 > size; size2--) {
                    if (this.comparator.compare(e, this.nextPermutation.get(size2)) < 0) {
                        Collections.swap(this.nextPermutation, size, size2);
                        Collections.reverse(this.nextPermutation.subList(size + 1, this.nextPermutation.size()));
                    }
                }
                throw new AssertionError("this statement should be unreachable");
            }
            this.nextPermutation = null;
            return immutableListCopyOf;
        }
    }

    public static final class PermutationCollection<E> extends AbstractCollection<List<E>> {
        public final ImmutableList<E> inputList;

        public PermutationCollection(ImmutableList<E> immutableList) {
            this.inputList = immutableList;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@CheckForNull Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.isPermutation(this.inputList, (List) obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<List<E>> iterator() {
            return new PermutationIterator(this.inputList);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return IntMath.factorial(this.inputList.size());
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            String strValueOf = String.valueOf(this.inputList);
            return MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 14, "permutations(", strValueOf, ")");
        }
    }

    public static class PermutationIterator<E> extends AbstractIterator<List<E>> {
        public final int[] c;
        public int j;
        public final List<E> list;
        public final int[] o;

        public PermutationIterator(List<E> list) {
            this.list = new ArrayList(list);
            int size = list.size();
            int[] iArr = new int[size];
            this.c = iArr;
            int[] iArr2 = new int[size];
            this.o = iArr2;
            Arrays.fill(iArr, 0);
            Arrays.fill(iArr2, 1);
            this.j = Integer.MAX_VALUE;
        }

        @Override // com.google.common.collect.AbstractIterator
        @CheckForNull
        public final Object computeNext() {
            if (this.j <= 0) {
                endOfData();
                return null;
            }
            ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) this.list);
            int size = this.list.size() - 1;
            this.j = size;
            if (size == -1) {
                return immutableListCopyOf;
            }
            int i = 0;
            while (true) {
                int[] iArr = this.c;
                int i2 = this.j;
                int i3 = iArr[i2];
                int[] iArr2 = this.o;
                int i4 = i3 + iArr2[i2];
                if (i4 < 0) {
                    iArr2[i2] = -iArr2[i2];
                    this.j = i2 - 1;
                } else {
                    if (i4 != i2 + 1) {
                        Collections.swap(this.list, (i2 - iArr[i2]) + i, (i2 - i4) + i);
                        this.c[this.j] = i4;
                        return immutableListCopyOf;
                    }
                    if (i2 == 0) {
                        return immutableListCopyOf;
                    }
                    i++;
                    iArr2[i2] = -iArr2[i2];
                    this.j = i2 - 1;
                }
            }
        }
    }

    public static class TransformedCollection<F, T> extends AbstractCollection<T> {
        public final Collection<F> fromCollection;
        public final Function<? super F, ? extends T> function;

        public TransformedCollection(Collection<F> collection, Function<? super F, ? extends T> function) {
            this.fromCollection = (Collection) Preconditions.checkNotNull(collection);
            this.function = (Function) Preconditions.checkNotNull(function);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            this.fromCollection.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return this.fromCollection.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<T> iterator() {
            return Iterators.transform(this.fromCollection.iterator(), this.function);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.fromCollection.size();
        }
    }

    private Collections2() {
    }

    public static boolean containsAllImpl(Collection<?> collection, Collection<?> collection2) {
        Iterator<?> it = collection2.iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    private static <E> ObjectCountHashMap<E> counts(Collection<E> collection) {
        ObjectCountHashMap<E> objectCountHashMap = new ObjectCountHashMap<>();
        for (E e : collection) {
            objectCountHashMap.put(e, objectCountHashMap.get(e) + 1);
        }
        return objectCountHashMap;
    }

    public static <E> Collection<E> filter(Collection<E> collection, Predicate<? super E> predicate) {
        if (!(collection instanceof FilteredCollection)) {
            return new FilteredCollection((Collection) Preconditions.checkNotNull(collection), (Predicate) Preconditions.checkNotNull(predicate));
        }
        FilteredCollection filteredCollection = (FilteredCollection) collection;
        return new FilteredCollection(filteredCollection.unfiltered, Predicates.and(filteredCollection.predicate, predicate));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPermutation(List<?> list, List<?> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        ObjectCountHashMap objectCountHashMapCounts = counts(list);
        ObjectCountHashMap objectCountHashMapCounts2 = counts(list2);
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (objectCountHashMapCounts.getValue(i) != objectCountHashMapCounts2.get(objectCountHashMapCounts.getKey(i))) {
                return false;
            }
        }
        return true;
    }

    public static StringBuilder newStringBuilderForCollection(int i) {
        CollectPreconditions.checkNonnegative(i, "size");
        return new StringBuilder((int) Math.min(((long) i) * 8, 1073741824L));
    }

    @Beta
    public static <E extends Comparable<? super E>> Collection<List<E>> orderedPermutations(Iterable<E> iterable) {
        return orderedPermutations(iterable, Ordering.natural());
    }

    @Beta
    public static <E> Collection<List<E>> permutations(Collection<E> collection) {
        return new PermutationCollection(ImmutableList.copyOf((Collection) collection));
    }

    public static boolean safeContains(Collection<?> collection, @CheckForNull Object obj) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.contains(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    public static <F, T> Collection<T> transform(Collection<F> collection, Function<? super F, T> function) {
        return new TransformedCollection(collection, function);
    }

    @Beta
    public static <E> Collection<List<E>> orderedPermutations(Iterable<E> iterable, Comparator<? super E> comparator) {
        return new OrderedPermutationCollection(iterable, comparator);
    }
}
