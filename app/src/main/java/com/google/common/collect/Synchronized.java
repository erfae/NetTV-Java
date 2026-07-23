package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
final class Synchronized {

    public static class SynchronizedAsMap<K, V> extends SynchronizedMap<K, Collection<V>> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        public transient Set<Map.Entry<K, Collection<V>>> asMapEntrySet;

        @CheckForNull
        public transient Collection<Collection<V>> asMapValues;

        public SynchronizedAsMap(Map<K, Collection<V>> map, @CheckForNull Object obj) {
            super(map, obj);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        public boolean containsValue(@CheckForNull Object obj) {
            return values().contains(obj);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        public Set<Map.Entry<K, Collection<V>>> entrySet() {
            Set<Map.Entry<K, Collection<V>>> set;
            synchronized (this.mutex) {
                if (this.asMapEntrySet == null) {
                    this.asMapEntrySet = new SynchronizedAsMapEntries(((Map) this.delegate).entrySet(), this.mutex);
                }
                set = this.asMapEntrySet;
            }
            return set;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        public Collection<Collection<V>> values() {
            Collection<Collection<V>> collection;
            synchronized (this.mutex) {
                if (this.asMapValues == null) {
                    this.asMapValues = new SynchronizedAsMapValues(((Map) this.delegate).values(), this.mutex);
                }
                collection = this.asMapValues;
            }
            return collection;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        @CheckForNull
        public Collection<V> get(@CheckForNull Object obj) {
            Collection<V> collectionTypePreservingCollection;
            synchronized (this.mutex) {
                Collection collection = (Collection) super.get(obj);
                collectionTypePreservingCollection = collection == null ? null : Synchronized.typePreservingCollection(collection, this.mutex);
            }
            return collectionTypePreservingCollection;
        }
    }

    public static class SynchronizedAsMapValues<V> extends SynchronizedCollection<Collection<V>> {
        private static final long serialVersionUID = 0;

        public SynchronizedAsMapValues(Collection<Collection<V>> collection, @CheckForNull Object obj) {
            super(collection, obj);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Collection<V>> iterator() {
            return new TransformedIterator<Collection<V>, Collection<V>>(super.iterator()) { // from class: com.google.common.collect.Synchronized.SynchronizedAsMapValues.1
                @Override // com.google.common.collect.TransformedIterator
                public final Object transform(Object obj) {
                    return Synchronized.typePreservingCollection((Collection) obj, SynchronizedAsMapValues.this.mutex);
                }
            };
        }
    }

    @VisibleForTesting
    public static class SynchronizedBiMap<K, V> extends SynchronizedMap<K, V> implements BiMap<K, V> {
        private static final long serialVersionUID = 0;

        @RetainedWith
        @CheckForNull
        private transient BiMap<V, K> inverse;

        @CheckForNull
        private transient Set<V> valueSet;

        public /* synthetic */ SynchronizedBiMap(BiMap biMap) {
            this(biMap, null, null);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap
        public final BiMap<K, V> delegate() {
            return (BiMap) ((Map) this.delegate);
        }

        @Override // com.google.common.collect.BiMap
        @CheckForNull
        public V forcePut(K k, V v) {
            V vForcePut;
            synchronized (this.mutex) {
                vForcePut = delegate().forcePut(k, v);
            }
            return vForcePut;
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<V, K> inverse() {
            BiMap<V, K> biMap;
            synchronized (this.mutex) {
                if (this.inverse == null) {
                    this.inverse = new SynchronizedBiMap(delegate().inverse(), this.mutex, this);
                }
                biMap = this.inverse;
            }
            return biMap;
        }

        private SynchronizedBiMap(BiMap<K, V> biMap, @CheckForNull Object obj, @CheckForNull BiMap<V, K> biMap2) {
            super(biMap, obj);
            this.inverse = biMap2;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        public Set<V> values() {
            Set<V> set;
            synchronized (this.mutex) {
                if (this.valueSet == null) {
                    this.valueSet = new SynchronizedSet(delegate().values(), this.mutex);
                }
                set = this.valueSet;
            }
            return set;
        }
    }

    @VisibleForTesting
    public static class SynchronizedCollection<E> extends SynchronizedObject implements Collection<E> {
        private static final long serialVersionUID = 0;

        @Override // java.util.Collection
        public boolean add(E e) {
            boolean zAdd;
            synchronized (this.mutex) {
                zAdd = delegate().add(e);
            }
            return zAdd;
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            boolean zAddAll;
            synchronized (this.mutex) {
                zAddAll = delegate().addAll(collection);
            }
            return zAddAll;
        }

        @Override // java.util.Collection
        public void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        public boolean contains(@CheckForNull Object obj) {
            boolean zContains;
            synchronized (this.mutex) {
                zContains = delegate().contains(obj);
            }
            return zContains;
        }

        public boolean containsAll(Collection<?> collection) {
            boolean zContainsAll;
            synchronized (this.mutex) {
                zContainsAll = delegate().containsAll(collection);
            }
            return zContainsAll;
        }

        Collection<E> delegate() {
            return (Collection) this.delegate;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (this.mutex) {
                zIsEmpty = delegate().isEmpty();
            }
            return zIsEmpty;
        }

        public Iterator<E> iterator() {
            return delegate().iterator();
        }

        public boolean remove(@CheckForNull Object obj) {
            boolean zRemove;
            synchronized (this.mutex) {
                zRemove = delegate().remove(obj);
            }
            return zRemove;
        }

        public boolean removeAll(Collection<?> collection) {
            boolean zRemoveAll;
            synchronized (this.mutex) {
                zRemoveAll = delegate().removeAll(collection);
            }
            return zRemoveAll;
        }

        public boolean retainAll(Collection<?> collection) {
            boolean zRetainAll;
            synchronized (this.mutex) {
                zRetainAll = delegate().retainAll(collection);
            }
            return zRetainAll;
        }

        @Override // java.util.Collection
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        public Object[] toArray() {
            Object[] array;
            synchronized (this.mutex) {
                array = delegate().toArray();
            }
            return array;
        }

        private SynchronizedCollection(Collection<E> collection, @CheckForNull Object obj) {
            super(collection, obj);
        }

        public <T> T[] toArray(T[] tArr) {
            T[] tArr2;
            synchronized (this.mutex) {
                tArr2 = (T[]) delegate().toArray(tArr);
            }
            return tArr2;
        }
    }

    @GwtIncompatible
    public static class SynchronizedEntry<K, V> extends SynchronizedObject implements Map.Entry<K, V> {
        private static final long serialVersionUID = 0;

        public SynchronizedEntry(Map.Entry<K, V> entry, @CheckForNull Object obj) {
            super(entry, obj);
        }

        @Override // java.util.Map.Entry
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            synchronized (this.mutex) {
                zEquals = ((Map.Entry) this.delegate).equals(obj);
            }
            return zEquals;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            K k;
            synchronized (this.mutex) {
                k = (K) ((Map.Entry) this.delegate).getKey();
            }
            return k;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            V v;
            synchronized (this.mutex) {
                v = (V) ((Map.Entry) this.delegate).getValue();
            }
            return v;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = ((Map.Entry) this.delegate).hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2;
            synchronized (this.mutex) {
                v2 = (V) ((Map.Entry) this.delegate).setValue(v);
            }
            return v2;
        }
    }

    public static class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E> {
        private static final long serialVersionUID = 0;

        public SynchronizedList(List<E> list, @CheckForNull Object obj) {
            super(list, obj);
        }

        @Override // java.util.List
        public void add(int i, E e) {
            synchronized (this.mutex) {
                delegate().add(i, e);
            }
        }

        @Override // java.util.List
        public boolean addAll(int i, Collection<? extends E> collection) {
            boolean zAddAll;
            synchronized (this.mutex) {
                zAddAll = delegate().addAll(i, collection);
            }
            return zAddAll;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection
        public final List<E> delegate() {
            return (List) ((Collection) this.delegate);
        }

        @Override // java.util.Collection, java.util.List
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        @Override // java.util.List
        public E get(int i) {
            E e;
            synchronized (this.mutex) {
                e = delegate().get(i);
            }
            return e;
        }

        @Override // java.util.Collection, java.util.List
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.List
        public int indexOf(@CheckForNull Object obj) {
            int iIndexOf;
            synchronized (this.mutex) {
                iIndexOf = delegate().indexOf(obj);
            }
            return iIndexOf;
        }

        @Override // java.util.List
        public int lastIndexOf(@CheckForNull Object obj) {
            int iLastIndexOf;
            synchronized (this.mutex) {
                iLastIndexOf = delegate().lastIndexOf(obj);
            }
            return iLastIndexOf;
        }

        @Override // java.util.List
        public ListIterator<E> listIterator() {
            return delegate().listIterator();
        }

        @Override // java.util.List
        public E remove(int i) {
            E eRemove;
            synchronized (this.mutex) {
                eRemove = delegate().remove(i);
            }
            return eRemove;
        }

        @Override // java.util.List
        public E set(int i, E e) {
            E e2;
            synchronized (this.mutex) {
                e2 = delegate().set(i, e);
            }
            return e2;
        }

        @Override // java.util.List
        public List<E> subList(int i, int i2) {
            List<E> list;
            synchronized (this.mutex) {
                list = Synchronized.list(delegate().subList(i, i2), this.mutex);
            }
            return list;
        }

        @Override // java.util.List
        public ListIterator<E> listIterator(int i) {
            return delegate().listIterator(i);
        }
    }

    public static class SynchronizedListMultimap<K, V> extends SynchronizedMultimap<K, V> implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0;

        public SynchronizedListMultimap(ListMultimap listMultimap) {
            super(listMultimap);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap
        public final ListMultimap<K, V> delegate() {
            return (ListMultimap) ((Multimap) this.delegate);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> get(K k) {
            List<V> list;
            synchronized (this.mutex) {
                list = Synchronized.list(delegate().get((Object) k), this.mutex);
            }
            return list;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> removeAll(@CheckForNull Object obj) {
            List<V> listRemoveAll;
            synchronized (this.mutex) {
                listRemoveAll = delegate().removeAll(obj);
            }
            return listRemoveAll;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
            List<V> listReplaceValues;
            synchronized (this.mutex) {
                listReplaceValues = delegate().replaceValues((Object) k, (Iterable) iterable);
            }
            return listReplaceValues;
        }
    }

    public static class SynchronizedMap<K, V> extends SynchronizedObject implements Map<K, V> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        public transient Set<Map.Entry<K, V>> entrySet;

        @CheckForNull
        public transient Set<K> keySet;

        @CheckForNull
        public transient Collection<V> values;

        public SynchronizedMap(Map<K, V> map, @CheckForNull Object obj) {
            super(map, obj);
        }

        @Override // java.util.Map
        public void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        @Override // java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            boolean zContainsKey;
            synchronized (this.mutex) {
                zContainsKey = delegate().containsKey(obj);
            }
            return zContainsKey;
        }

        public boolean containsValue(@CheckForNull Object obj) {
            boolean zContainsValue;
            synchronized (this.mutex) {
                zContainsValue = delegate().containsValue(obj);
            }
            return zContainsValue;
        }

        Map<K, V> delegate() {
            return (Map) this.delegate;
        }

        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set;
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = new SynchronizedSet(delegate().entrySet(), this.mutex);
                }
                set = this.entrySet;
            }
            return set;
        }

        @Override // java.util.Map
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        @CheckForNull
        public V get(@CheckForNull Object obj) {
            V v;
            synchronized (this.mutex) {
                v = delegate().get(obj);
            }
            return v;
        }

        @Override // java.util.Map
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (this.mutex) {
                zIsEmpty = delegate().isEmpty();
            }
            return zIsEmpty;
        }

        @Override // java.util.Map
        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.mutex) {
                if (this.keySet == null) {
                    this.keySet = new SynchronizedSet(delegate().keySet(), this.mutex);
                }
                set = this.keySet;
            }
            return set;
        }

        @Override // java.util.Map
        @CheckForNull
        public V put(K k, V v) {
            V vPut;
            synchronized (this.mutex) {
                vPut = delegate().put(k, v);
            }
            return vPut;
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            synchronized (this.mutex) {
                delegate().putAll(map);
            }
        }

        @Override // java.util.Map
        @CheckForNull
        public V remove(@CheckForNull Object obj) {
            V vRemove;
            synchronized (this.mutex) {
                vRemove = delegate().remove(obj);
            }
            return vRemove;
        }

        @Override // java.util.Map
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.mutex) {
                if (this.values == null) {
                    this.values = Synchronized.collection(delegate().values(), this.mutex);
                }
                collection = this.values;
            }
            return collection;
        }
    }

    public static class SynchronizedObject implements Serializable {

        @GwtIncompatible
        private static final long serialVersionUID = 0;
        public final Object delegate;
        public final Object mutex;

        public SynchronizedObject(Object obj, @CheckForNull Object obj2) {
            this.delegate = Preconditions.checkNotNull(obj);
            this.mutex = obj2 == null ? this : obj2;
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            synchronized (this.mutex) {
                objectOutputStream.defaultWriteObject();
            }
        }

        public String toString() {
            String string;
            synchronized (this.mutex) {
                string = this.delegate.toString();
            }
            return string;
        }
    }

    public static class SynchronizedQueue<E> extends SynchronizedCollection<E> implements Queue<E> {
        private static final long serialVersionUID = 0;

        /* JADX WARN: Illegal instructions before constructor call */
        public SynchronizedQueue(Queue queue) {
            AnonymousClass1 anonymousClass1 = null;
            super(queue, anonymousClass1);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedCollection
        public Queue<E> delegate() {
            return (Queue) ((Collection) this.delegate);
        }

        @Override // java.util.Queue
        public E element() {
            E eElement;
            synchronized (this.mutex) {
                eElement = delegate().element();
            }
            return eElement;
        }

        @Override // java.util.Queue
        public boolean offer(E e) {
            boolean zOffer;
            synchronized (this.mutex) {
                zOffer = delegate().offer(e);
            }
            return zOffer;
        }

        @Override // java.util.Queue
        @CheckForNull
        public E peek() {
            E ePeek;
            synchronized (this.mutex) {
                ePeek = delegate().peek();
            }
            return ePeek;
        }

        @Override // java.util.Queue
        @CheckForNull
        public E poll() {
            E ePoll;
            synchronized (this.mutex) {
                ePoll = delegate().poll();
            }
            return ePoll;
        }

        @Override // java.util.Queue
        public E remove() {
            E eRemove;
            synchronized (this.mutex) {
                eRemove = delegate().remove();
            }
            return eRemove;
        }
    }

    public static class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess {
        private static final long serialVersionUID = 0;

        public SynchronizedRandomAccessList(List<E> list, @CheckForNull Object obj) {
            super(list, obj);
        }
    }

    public static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
        private static final long serialVersionUID = 0;

        public SynchronizedSet(Set<E> set, @CheckForNull Object obj) {
            super(set, obj);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.Synchronized.SynchronizedCollection
        public Set<E> delegate() {
            return (Set) ((Collection) this.delegate);
        }

        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }
    }

    public static class SynchronizedSetMultimap<K, V> extends SynchronizedMultimap<K, V> implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        public transient Set<Map.Entry<K, V>> entrySet;

        public SynchronizedSetMultimap(SetMultimap setMultimap) {
            super(setMultimap);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap
        public SetMultimap<K, V> delegate() {
            return (SetMultimap) ((Multimap) this.delegate);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap
        public Set<Map.Entry<K, V>> entries() {
            Set<Map.Entry<K, V>> set;
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = new SynchronizedSet(delegate().entries(), this.mutex);
                }
                set = this.entrySet;
            }
            return set;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> get(K k) {
            SynchronizedSet synchronizedSet;
            synchronized (this.mutex) {
                synchronizedSet = new SynchronizedSet(delegate().get((Object) k), this.mutex);
            }
            return synchronizedSet;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> removeAll(@CheckForNull Object obj) {
            Set<V> setRemoveAll;
            synchronized (this.mutex) {
                setRemoveAll = delegate().removeAll(obj);
            }
            return setRemoveAll;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            Set<V> setReplaceValues;
            synchronized (this.mutex) {
                setReplaceValues = delegate().replaceValues((Object) k, (Iterable) iterable);
            }
            return setReplaceValues;
        }
    }

    public static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V> {
        private static final long serialVersionUID = 0;

        public SynchronizedSortedMap(SortedMap<K, V> sortedMap, @CheckForNull Object obj) {
            super(sortedMap, obj);
        }

        @Override // java.util.SortedMap
        @CheckForNull
        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator;
            synchronized (this.mutex) {
                comparator = delegate().comparator();
            }
            return comparator;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap
        public SortedMap<K, V> delegate() {
            return (SortedMap) ((Map) this.delegate);
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            K kFirstKey;
            synchronized (this.mutex) {
                kFirstKey = delegate().firstKey();
            }
            return kFirstKey;
        }

        public SortedMap<K, V> headMap(K k) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.mutex) {
                synchronizedSortedMap = new SynchronizedSortedMap(delegate().headMap(k), this.mutex);
            }
            return synchronizedSortedMap;
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            K kLastKey;
            synchronized (this.mutex) {
                kLastKey = delegate().lastKey();
            }
            return kLastKey;
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.mutex) {
                synchronizedSortedMap = new SynchronizedSortedMap(delegate().subMap(k, k2), this.mutex);
            }
            return synchronizedSortedMap;
        }

        public SortedMap<K, V> tailMap(K k) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.mutex) {
                synchronizedSortedMap = new SynchronizedSortedMap(delegate().tailMap(k), this.mutex);
            }
            return synchronizedSortedMap;
        }
    }

    public static final class SynchronizedTable<R, C, V> extends SynchronizedObject implements Table<R, C, V> {
        public SynchronizedTable(Table table) {
            super(table, null);
        }

        @Override // com.google.common.collect.Table
        public Set<Table.Cell<R, C, V>> cellSet() {
            SynchronizedSet synchronizedSet;
            synchronized (this.mutex) {
                synchronizedSet = new SynchronizedSet(((Table) this.delegate).cellSet(), this.mutex);
            }
            return synchronizedSet;
        }

        @Override // com.google.common.collect.Table
        public void clear() {
            synchronized (this.mutex) {
                ((Table) this.delegate).clear();
            }
        }

        @Override // com.google.common.collect.Table
        public Map<R, V> column(C c) {
            SynchronizedMap synchronizedMap;
            synchronized (this.mutex) {
                synchronizedMap = new SynchronizedMap(((Table) this.delegate).column(c), this.mutex);
            }
            return synchronizedMap;
        }

        @Override // com.google.common.collect.Table
        public Set<C> columnKeySet() {
            SynchronizedSet synchronizedSet;
            synchronized (this.mutex) {
                synchronizedSet = new SynchronizedSet(((Table) this.delegate).columnKeySet(), this.mutex);
            }
            return synchronizedSet;
        }

        @Override // com.google.common.collect.Table
        public Map<C, Map<R, V>> columnMap() {
            SynchronizedMap synchronizedMap;
            synchronized (this.mutex) {
                synchronizedMap = new SynchronizedMap(Maps.transformValues(((Table) this.delegate).columnMap(), new Function<Map<R, V>, Map<R, V>>() { // from class: com.google.common.collect.Synchronized.SynchronizedTable.2
                    @Override // com.google.common.base.Function
                    public Map<R, V> apply(Map<R, V> map) {
                        return new SynchronizedMap(map, SynchronizedTable.this.mutex);
                    }
                }), this.mutex);
            }
            return synchronizedMap;
        }

        @Override // com.google.common.collect.Table
        public boolean contains(@CheckForNull Object obj, @CheckForNull Object obj2) {
            boolean zContains;
            synchronized (this.mutex) {
                zContains = ((Table) this.delegate).contains(obj, obj2);
            }
            return zContains;
        }

        @Override // com.google.common.collect.Table
        public boolean containsColumn(@CheckForNull Object obj) {
            boolean zContainsColumn;
            synchronized (this.mutex) {
                zContainsColumn = ((Table) this.delegate).containsColumn(obj);
            }
            return zContainsColumn;
        }

        @Override // com.google.common.collect.Table
        public boolean containsRow(@CheckForNull Object obj) {
            boolean zContainsRow;
            synchronized (this.mutex) {
                zContainsRow = ((Table) this.delegate).containsRow(obj);
            }
            return zContainsRow;
        }

        @Override // com.google.common.collect.Table
        public boolean containsValue(@CheckForNull Object obj) {
            boolean zContainsValue;
            synchronized (this.mutex) {
                zContainsValue = ((Table) this.delegate).containsValue(obj);
            }
            return zContainsValue;
        }

        @Override // com.google.common.collect.Table
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (this == obj) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = ((Table) this.delegate).equals(obj);
            }
            return zEquals;
        }

        @Override // com.google.common.collect.Table
        @CheckForNull
        public V get(@CheckForNull Object obj, @CheckForNull Object obj2) {
            V v;
            synchronized (this.mutex) {
                v = (V) ((Table) this.delegate).get(obj, obj2);
            }
            return v;
        }

        @Override // com.google.common.collect.Table
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = ((Table) this.delegate).hashCode();
            }
            return iHashCode;
        }

        @Override // com.google.common.collect.Table
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (this.mutex) {
                zIsEmpty = ((Table) this.delegate).isEmpty();
            }
            return zIsEmpty;
        }

        @Override // com.google.common.collect.Table
        @CheckForNull
        public V put(R r, C c, V v) {
            V v2;
            synchronized (this.mutex) {
                v2 = (V) ((Table) this.delegate).put(r, c, v);
            }
            return v2;
        }

        @Override // com.google.common.collect.Table
        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            synchronized (this.mutex) {
                ((Table) this.delegate).putAll(table);
            }
        }

        @Override // com.google.common.collect.Table
        @CheckForNull
        public V remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
            V v;
            synchronized (this.mutex) {
                v = (V) ((Table) this.delegate).remove(obj, obj2);
            }
            return v;
        }

        @Override // com.google.common.collect.Table
        public Map<C, V> row(R r) {
            SynchronizedMap synchronizedMap;
            synchronized (this.mutex) {
                synchronizedMap = new SynchronizedMap(((Table) this.delegate).row(r), this.mutex);
            }
            return synchronizedMap;
        }

        @Override // com.google.common.collect.Table
        public Set<R> rowKeySet() {
            SynchronizedSet synchronizedSet;
            synchronized (this.mutex) {
                synchronizedSet = new SynchronizedSet(((Table) this.delegate).rowKeySet(), this.mutex);
            }
            return synchronizedSet;
        }

        @Override // com.google.common.collect.Table
        public Map<R, Map<C, V>> rowMap() {
            SynchronizedMap synchronizedMap;
            synchronized (this.mutex) {
                synchronizedMap = new SynchronizedMap(Maps.transformValues(((Table) this.delegate).rowMap(), new Function<Map<C, V>, Map<C, V>>() { // from class: com.google.common.collect.Synchronized.SynchronizedTable.1
                    @Override // com.google.common.base.Function
                    public Map<C, V> apply(Map<C, V> map) {
                        return new SynchronizedMap(map, SynchronizedTable.this.mutex);
                    }
                }), this.mutex);
            }
            return synchronizedMap;
        }

        @Override // com.google.common.collect.Table
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = ((Table) this.delegate).size();
            }
            return size;
        }

        @Override // com.google.common.collect.Table
        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.mutex) {
                collection = Synchronized.collection(((Table) this.delegate).values(), this.mutex);
            }
            return collection;
        }
    }

    private Synchronized() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Collection<E> collection(Collection<E> collection, @CheckForNull Object obj) {
        return new SynchronizedCollection(collection, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> List<E> list(List<E> list, @CheckForNull Object obj) {
        return list instanceof RandomAccess ? new SynchronizedRandomAccessList(list, obj) : new SynchronizedList(list, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CheckForNull
    @GwtIncompatible
    public static <K, V> Map.Entry<K, V> nullableSynchronizedEntry(@CheckForNull Map.Entry<K, V> entry, @CheckForNull Object obj) {
        if (entry == null) {
            return null;
        }
        return new SynchronizedEntry(entry, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> SortedSet<E> sortedSet(SortedSet<E> sortedSet, @CheckForNull Object obj) {
        return new SynchronizedSortedSet(sortedSet, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Collection<E> typePreservingCollection(Collection<E> collection, @CheckForNull Object obj) {
        if (collection instanceof SortedSet) {
            return sortedSet((SortedSet) collection, obj);
        }
        if (collection instanceof Set) {
            return new SynchronizedSet((Set) collection, obj);
        }
        return collection instanceof List ? list((List) collection, obj) : collection(collection, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Set<E> typePreservingSet(Set<E> set, @CheckForNull Object obj) {
        return set instanceof SortedSet ? sortedSet((SortedSet) set, obj) : new SynchronizedSet(set, obj);
    }

    public static final class SynchronizedDeque<E> extends SynchronizedQueue<E> implements Deque<E> {
        private static final long serialVersionUID = 0;

        public SynchronizedDeque(Deque deque) {
            super(deque);
        }

        @Override // java.util.Deque
        public void addFirst(E e) {
            synchronized (this.mutex) {
                delegate().addFirst(e);
            }
        }

        @Override // java.util.Deque
        public void addLast(E e) {
            synchronized (this.mutex) {
                delegate().addLast(e);
            }
        }

        @Override // java.util.Deque
        public Iterator<E> descendingIterator() {
            Iterator<E> itDescendingIterator;
            synchronized (this.mutex) {
                itDescendingIterator = delegate().descendingIterator();
            }
            return itDescendingIterator;
        }

        @Override // java.util.Deque
        public E getFirst() {
            E first;
            synchronized (this.mutex) {
                first = delegate().getFirst();
            }
            return first;
        }

        @Override // java.util.Deque
        public E getLast() {
            E last;
            synchronized (this.mutex) {
                last = delegate().getLast();
            }
            return last;
        }

        @Override // java.util.Deque
        public boolean offerFirst(E e) {
            boolean zOfferFirst;
            synchronized (this.mutex) {
                zOfferFirst = delegate().offerFirst(e);
            }
            return zOfferFirst;
        }

        @Override // java.util.Deque
        public boolean offerLast(E e) {
            boolean zOfferLast;
            synchronized (this.mutex) {
                zOfferLast = delegate().offerLast(e);
            }
            return zOfferLast;
        }

        @Override // java.util.Deque
        @CheckForNull
        public E peekFirst() {
            E ePeekFirst;
            synchronized (this.mutex) {
                ePeekFirst = delegate().peekFirst();
            }
            return ePeekFirst;
        }

        @Override // java.util.Deque
        @CheckForNull
        public E peekLast() {
            E ePeekLast;
            synchronized (this.mutex) {
                ePeekLast = delegate().peekLast();
            }
            return ePeekLast;
        }

        @Override // java.util.Deque
        @CheckForNull
        public E pollFirst() {
            E ePollFirst;
            synchronized (this.mutex) {
                ePollFirst = delegate().pollFirst();
            }
            return ePollFirst;
        }

        @Override // java.util.Deque
        @CheckForNull
        public E pollLast() {
            E ePollLast;
            synchronized (this.mutex) {
                ePollLast = delegate().pollLast();
            }
            return ePollLast;
        }

        @Override // java.util.Deque
        public E pop() {
            E ePop;
            synchronized (this.mutex) {
                ePop = delegate().pop();
            }
            return ePop;
        }

        @Override // java.util.Deque
        public void push(E e) {
            synchronized (this.mutex) {
                delegate().push(e);
            }
        }

        @Override // java.util.Deque
        public E removeFirst() {
            E eRemoveFirst;
            synchronized (this.mutex) {
                eRemoveFirst = delegate().removeFirst();
            }
            return eRemoveFirst;
        }

        @Override // java.util.Deque
        public boolean removeFirstOccurrence(@CheckForNull Object obj) {
            boolean zRemoveFirstOccurrence;
            synchronized (this.mutex) {
                zRemoveFirstOccurrence = delegate().removeFirstOccurrence(obj);
            }
            return zRemoveFirstOccurrence;
        }

        @Override // java.util.Deque
        public E removeLast() {
            E eRemoveLast;
            synchronized (this.mutex) {
                eRemoveLast = delegate().removeLast();
            }
            return eRemoveLast;
        }

        @Override // java.util.Deque
        public boolean removeLastOccurrence(@CheckForNull Object obj) {
            boolean zRemoveLastOccurrence;
            synchronized (this.mutex) {
                zRemoveLastOccurrence = delegate().removeLastOccurrence(obj);
            }
            return zRemoveLastOccurrence;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedQueue, com.google.common.collect.Synchronized.SynchronizedCollection
        public final Deque<E> delegate() {
            return (Deque) super.delegate();
        }
    }

    @GwtIncompatible
    @VisibleForTesting
    public static class SynchronizedNavigableMap<K, V> extends SynchronizedSortedMap<K, V> implements NavigableMap<K, V> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        public transient NavigableSet<K> descendingKeySet;

        @CheckForNull
        public transient NavigableMap<K, V> descendingMap;

        @CheckForNull
        public transient NavigableSet<K> navigableKeySet;

        public SynchronizedNavigableMap(NavigableMap<K, V> navigableMap, @CheckForNull Object obj) {
            super(navigableMap, obj);
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> ceilingEntry(K k) {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().ceilingEntry(k), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K ceilingKey(K k) {
            K kCeilingKey;
            synchronized (this.mutex) {
                kCeilingKey = delegate().ceilingKey(k);
            }
            return kCeilingKey;
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> descendingKeySet() {
            synchronized (this.mutex) {
                NavigableSet<K> navigableSet = this.descendingKeySet;
                if (navigableSet != null) {
                    return navigableSet;
                }
                SynchronizedNavigableSet synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().descendingKeySet(), this.mutex);
                this.descendingKeySet = synchronizedNavigableSet;
                return synchronizedNavigableSet;
            }
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> descendingMap() {
            synchronized (this.mutex) {
                NavigableMap<K, V> navigableMap = this.descendingMap;
                if (navigableMap != null) {
                    return navigableMap;
                }
                SynchronizedNavigableMap synchronizedNavigableMap = new SynchronizedNavigableMap(delegate().descendingMap(), this.mutex);
                this.descendingMap = synchronizedNavigableMap;
                return synchronizedNavigableMap;
            }
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> firstEntry() {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().firstEntry(), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> floorEntry(K k) {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().floorEntry(k), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K floorKey(K k) {
            K kFloorKey;
            synchronized (this.mutex) {
                kFloorKey = delegate().floorKey(k);
            }
            return kFloorKey;
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> headMap(K k, boolean z) {
            SynchronizedNavigableMap synchronizedNavigableMap;
            synchronized (this.mutex) {
                synchronizedNavigableMap = new SynchronizedNavigableMap(delegate().headMap(k, z), this.mutex);
            }
            return synchronizedNavigableMap;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> higherEntry(K k) {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().higherEntry(k), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K higherKey(K k) {
            K kHigherKey;
            synchronized (this.mutex) {
                kHigherKey = delegate().higherKey(k);
            }
            return kHigherKey;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedMap, java.util.Map
        public Set<K> keySet() {
            return navigableKeySet();
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> lastEntry() {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().lastEntry(), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> lowerEntry(K k) {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().lowerEntry(k), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public K lowerKey(K k) {
            K kLowerKey;
            synchronized (this.mutex) {
                kLowerKey = delegate().lowerKey(k);
            }
            return kLowerKey;
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            synchronized (this.mutex) {
                NavigableSet<K> navigableSet = this.navigableKeySet;
                if (navigableSet != null) {
                    return navigableSet;
                }
                SynchronizedNavigableSet synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().navigableKeySet(), this.mutex);
                this.navigableKeySet = synchronizedNavigableSet;
                return synchronizedNavigableSet;
            }
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> pollFirstEntry() {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().pollFirstEntry(), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        @CheckForNull
        public Map.Entry<K, V> pollLastEntry() {
            Map.Entry<K, V> entryNullableSynchronizedEntry;
            synchronized (this.mutex) {
                entryNullableSynchronizedEntry = Synchronized.nullableSynchronizedEntry(delegate().pollLastEntry(), this.mutex);
            }
            return entryNullableSynchronizedEntry;
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            SynchronizedNavigableMap synchronizedNavigableMap;
            synchronized (this.mutex) {
                synchronizedNavigableMap = new SynchronizedNavigableMap(delegate().subMap(k, z, k2, z2), this.mutex);
            }
            return synchronizedNavigableMap;
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> tailMap(K k, boolean z) {
            SynchronizedNavigableMap synchronizedNavigableMap;
            synchronized (this.mutex) {
                synchronizedNavigableMap = new SynchronizedNavigableMap(delegate().tailMap(k, z), this.mutex);
            }
            return synchronizedNavigableMap;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedMap, com.google.common.collect.Synchronized.SynchronizedMap
        public final NavigableMap<K, V> delegate() {
            return (NavigableMap) super.delegate();
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedMap, java.util.SortedMap, java.util.NavigableMap
        public SortedMap<K, V> headMap(K k) {
            return headMap(k, false);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedMap, java.util.SortedMap, java.util.NavigableMap
        public SortedMap<K, V> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedMap, java.util.SortedMap, java.util.NavigableMap
        public SortedMap<K, V> tailMap(K k) {
            return tailMap(k, true);
        }
    }

    public static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E> {
        private static final long serialVersionUID = 0;

        public SynchronizedSortedSet(SortedSet<E> sortedSet, @CheckForNull Object obj) {
            super(sortedSet, obj);
        }

        @Override // java.util.SortedSet
        @CheckForNull
        public Comparator<? super E> comparator() {
            Comparator<? super E> comparator;
            synchronized (this.mutex) {
                comparator = delegate().comparator();
            }
            return comparator;
        }

        @Override // java.util.SortedSet
        public E first() {
            E eFirst;
            synchronized (this.mutex) {
                eFirst = delegate().first();
            }
            return eFirst;
        }

        public SortedSet<E> headSet(E e) {
            SortedSet<E> sortedSet;
            synchronized (this.mutex) {
                sortedSet = Synchronized.sortedSet(delegate().headSet(e), this.mutex);
            }
            return sortedSet;
        }

        @Override // java.util.SortedSet
        public E last() {
            E eLast;
            synchronized (this.mutex) {
                eLast = delegate().last();
            }
            return eLast;
        }

        public SortedSet<E> subSet(E e, E e2) {
            SortedSet<E> sortedSet;
            synchronized (this.mutex) {
                sortedSet = Synchronized.sortedSet(delegate().subSet(e, e2), this.mutex);
            }
            return sortedSet;
        }

        public SortedSet<E> tailSet(E e) {
            SortedSet<E> sortedSet;
            synchronized (this.mutex) {
                sortedSet = Synchronized.sortedSet(delegate().tailSet(e), this.mutex);
            }
            return sortedSet;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSet, com.google.common.collect.Synchronized.SynchronizedCollection
        public SortedSet<E> delegate() {
            return (SortedSet) super.delegate();
        }
    }

    public static class SynchronizedSortedSetMultimap<K, V> extends SynchronizedSetMultimap<K, V> implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        public SynchronizedSortedSetMultimap(SortedSetMultimap sortedSetMultimap) {
            super(sortedSetMultimap);
        }

        @Override // com.google.common.collect.SortedSetMultimap
        @CheckForNull
        public Comparator<? super V> valueComparator() {
            Comparator<? super V> comparatorValueComparator;
            synchronized (this.mutex) {
                comparatorValueComparator = delegate().valueComparator();
            }
            return comparatorValueComparator;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap
        public final SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap) super.delegate();
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> get(K k) {
            SortedSet<V> sortedSet;
            synchronized (this.mutex) {
                sortedSet = Synchronized.sortedSet(delegate().get((Object) k), this.mutex);
            }
            return sortedSet;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> removeAll(@CheckForNull Object obj) {
            SortedSet<V> sortedSetRemoveAll;
            synchronized (this.mutex) {
                sortedSetRemoveAll = delegate().removeAll(obj);
            }
            return sortedSetRemoveAll;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSetMultimap, com.google.common.collect.Synchronized.SynchronizedMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
            SortedSet<V> sortedSetReplaceValues;
            synchronized (this.mutex) {
                sortedSetReplaceValues = delegate().replaceValues((Object) k, (Iterable) iterable);
            }
            return sortedSetReplaceValues;
        }
    }

    public static class SynchronizedMultimap<K, V> extends SynchronizedObject implements Multimap<K, V> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        public transient Map<K, Collection<V>> asMap;

        @CheckForNull
        public transient Collection<Map.Entry<K, V>> entries;

        @CheckForNull
        public transient Set<K> keySet;

        @CheckForNull
        public transient Multiset<K> keys;

        @CheckForNull
        public transient Collection<V> valuesCollection;

        public SynchronizedMultimap(Multimap multimap) {
            super(multimap, null);
        }

        @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> map;
            synchronized (this.mutex) {
                if (this.asMap == null) {
                    this.asMap = new SynchronizedAsMap(delegate().asMap(), this.mutex);
                }
                map = this.asMap;
            }
            return map;
        }

        @Override // com.google.common.collect.Multimap
        public void clear() {
            synchronized (this.mutex) {
                delegate().clear();
            }
        }

        @Override // com.google.common.collect.Multimap
        public boolean containsEntry(@CheckForNull Object obj, @CheckForNull Object obj2) {
            boolean zContainsEntry;
            synchronized (this.mutex) {
                zContainsEntry = delegate().containsEntry(obj, obj2);
            }
            return zContainsEntry;
        }

        @Override // com.google.common.collect.Multimap
        public boolean containsKey(@CheckForNull Object obj) {
            boolean zContainsKey;
            synchronized (this.mutex) {
                zContainsKey = delegate().containsKey(obj);
            }
            return zContainsKey;
        }

        @Override // com.google.common.collect.Multimap
        public boolean containsValue(@CheckForNull Object obj) {
            boolean zContainsValue;
            synchronized (this.mutex) {
                zContainsValue = delegate().containsValue(obj);
            }
            return zContainsValue;
        }

        public Multimap<K, V> delegate() {
            return (Multimap) this.delegate;
        }

        @Override // com.google.common.collect.Multimap
        public Collection<Map.Entry<K, V>> entries() {
            Collection<Map.Entry<K, V>> collection;
            synchronized (this.mutex) {
                if (this.entries == null) {
                    this.entries = Synchronized.typePreservingCollection(delegate().entries(), this.mutex);
                }
                collection = this.entries;
            }
            return collection;
        }

        @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        public Collection<V> get(K k) {
            Collection<V> collectionTypePreservingCollection;
            synchronized (this.mutex) {
                collectionTypePreservingCollection = Synchronized.typePreservingCollection(delegate().get(k), this.mutex);
            }
            return collectionTypePreservingCollection;
        }

        @Override // com.google.common.collect.Multimap
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }

        @Override // com.google.common.collect.Multimap
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (this.mutex) {
                zIsEmpty = delegate().isEmpty();
            }
            return zIsEmpty;
        }

        @Override // com.google.common.collect.Multimap
        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.mutex) {
                if (this.keySet == null) {
                    this.keySet = Synchronized.typePreservingSet(delegate().keySet(), this.mutex);
                }
                set = this.keySet;
            }
            return set;
        }

        @Override // com.google.common.collect.Multimap
        public Multiset<K> keys() {
            Multiset<K> multiset;
            synchronized (this.mutex) {
                if (this.keys == null) {
                    Multiset<K> multisetKeys = delegate().keys();
                    Object obj = this.mutex;
                    if (!(multisetKeys instanceof SynchronizedMultiset) && !(multisetKeys instanceof ImmutableMultiset)) {
                        multisetKeys = new SynchronizedMultiset(multisetKeys, obj);
                    }
                    this.keys = multisetKeys;
                }
                multiset = this.keys;
            }
            return multiset;
        }

        @Override // com.google.common.collect.Multimap
        public boolean put(K k, V v) {
            boolean zPut;
            synchronized (this.mutex) {
                zPut = delegate().put(k, v);
            }
            return zPut;
        }

        @Override // com.google.common.collect.Multimap
        public boolean putAll(K k, Iterable<? extends V> iterable) {
            boolean zPutAll;
            synchronized (this.mutex) {
                zPutAll = delegate().putAll(k, iterable);
            }
            return zPutAll;
        }

        @Override // com.google.common.collect.Multimap
        public boolean remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
            boolean zRemove;
            synchronized (this.mutex) {
                zRemove = delegate().remove(obj, obj2);
            }
            return zRemove;
        }

        public Collection<V> removeAll(@CheckForNull Object obj) {
            Collection<V> collectionRemoveAll;
            synchronized (this.mutex) {
                collectionRemoveAll = delegate().removeAll(obj);
            }
            return collectionRemoveAll;
        }

        public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
            Collection<V> collectionReplaceValues;
            synchronized (this.mutex) {
                collectionReplaceValues = delegate().replaceValues(k, iterable);
            }
            return collectionReplaceValues;
        }

        @Override // com.google.common.collect.Multimap
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = delegate().size();
            }
            return size;
        }

        @Override // com.google.common.collect.Multimap
        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.mutex) {
                if (this.valuesCollection == null) {
                    this.valuesCollection = Synchronized.collection(delegate().values(), this.mutex);
                }
                collection = this.valuesCollection;
            }
            return collection;
        }

        @Override // com.google.common.collect.Multimap
        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            boolean zPutAll;
            synchronized (this.mutex) {
                zPutAll = delegate().putAll(multimap);
            }
            return zPutAll;
        }
    }

    public static class SynchronizedMultiset<E> extends SynchronizedCollection<E> implements Multiset<E> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        public transient Set<E> elementSet;

        @CheckForNull
        public transient Set<Multiset.Entry<E>> entrySet;

        public SynchronizedMultiset(Multiset<E> multiset, @CheckForNull Object obj) {
            super(multiset, obj);
        }

        @Override // com.google.common.collect.Multiset
        public int add(E e, int i) {
            int iAdd;
            synchronized (this.mutex) {
                iAdd = delegate().add(e, i);
            }
            return iAdd;
        }

        @Override // com.google.common.collect.Multiset
        public int count(@CheckForNull Object obj) {
            int iCount;
            synchronized (this.mutex) {
                iCount = delegate().count(obj);
            }
            return iCount;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection
        public final Multiset<E> delegate() {
            return (Multiset) ((Collection) this.delegate);
        }

        @Override // com.google.common.collect.Multiset
        public Set<E> elementSet() {
            Set<E> set;
            synchronized (this.mutex) {
                if (this.elementSet == null) {
                    this.elementSet = Synchronized.typePreservingSet(delegate().elementSet(), this.mutex);
                }
                set = this.elementSet;
            }
            return set;
        }

        @Override // com.google.common.collect.Multiset
        public Set<Multiset.Entry<E>> entrySet() {
            Set<Multiset.Entry<E>> set;
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = Synchronized.typePreservingSet(delegate().entrySet(), this.mutex);
                }
                set = this.entrySet;
            }
            return set;
        }

        @Override // java.util.Collection, com.google.common.collect.Multiset
        public boolean equals(@CheckForNull Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = delegate().equals(obj);
            }
            return zEquals;
        }

        @Override // java.util.Collection, com.google.common.collect.Multiset
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = delegate().hashCode();
            }
            return iHashCode;
        }

        @Override // com.google.common.collect.Multiset
        public int remove(@CheckForNull Object obj, int i) {
            int iRemove;
            synchronized (this.mutex) {
                iRemove = delegate().remove(obj, i);
            }
            return iRemove;
        }

        @Override // com.google.common.collect.Multiset
        public int setCount(E e, int i) {
            int count;
            synchronized (this.mutex) {
                count = delegate().setCount(e, i);
            }
            return count;
        }

        @Override // com.google.common.collect.Multiset
        public boolean setCount(E e, int i, int i2) {
            boolean count;
            synchronized (this.mutex) {
                count = delegate().setCount(e, i, i2);
            }
            return count;
        }
    }

    @GwtIncompatible
    @VisibleForTesting
    public static class SynchronizedNavigableSet<E> extends SynchronizedSortedSet<E> implements NavigableSet<E> {
        private static final long serialVersionUID = 0;

        @CheckForNull
        public transient NavigableSet<E> descendingSet;

        public SynchronizedNavigableSet(NavigableSet<E> navigableSet, @CheckForNull Object obj) {
            super(navigableSet, obj);
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E ceiling(E e) {
            E eCeiling;
            synchronized (this.mutex) {
                eCeiling = delegate().ceiling(e);
            }
            return eCeiling;
        }

        @Override // java.util.NavigableSet
        public Iterator<E> descendingIterator() {
            return delegate().descendingIterator();
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> descendingSet() {
            synchronized (this.mutex) {
                NavigableSet<E> navigableSet = this.descendingSet;
                if (navigableSet != null) {
                    return navigableSet;
                }
                SynchronizedNavigableSet synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().descendingSet(), this.mutex);
                this.descendingSet = synchronizedNavigableSet;
                return synchronizedNavigableSet;
            }
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E floor(E e) {
            E eFloor;
            synchronized (this.mutex) {
                eFloor = delegate().floor(e);
            }
            return eFloor;
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> headSet(E e, boolean z) {
            SynchronizedNavigableSet synchronizedNavigableSet;
            synchronized (this.mutex) {
                synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().headSet(e, z), this.mutex);
            }
            return synchronizedNavigableSet;
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E higher(E e) {
            E eHigher;
            synchronized (this.mutex) {
                eHigher = delegate().higher(e);
            }
            return eHigher;
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E lower(E e) {
            E eLower;
            synchronized (this.mutex) {
                eLower = delegate().lower(e);
            }
            return eLower;
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E pollFirst() {
            E ePollFirst;
            synchronized (this.mutex) {
                ePollFirst = delegate().pollFirst();
            }
            return ePollFirst;
        }

        @Override // java.util.NavigableSet
        @CheckForNull
        public E pollLast() {
            E ePollLast;
            synchronized (this.mutex) {
                ePollLast = delegate().pollLast();
            }
            return ePollLast;
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            SynchronizedNavigableSet synchronizedNavigableSet;
            synchronized (this.mutex) {
                synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().subSet(e, z, e2, z2), this.mutex);
            }
            return synchronizedNavigableSet;
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> tailSet(E e, boolean z) {
            SynchronizedNavigableSet synchronizedNavigableSet;
            synchronized (this.mutex) {
                synchronizedNavigableSet = new SynchronizedNavigableSet(delegate().tailSet(e, z), this.mutex);
            }
            return synchronizedNavigableSet;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedSet, com.google.common.collect.Synchronized.SynchronizedSet, com.google.common.collect.Synchronized.SynchronizedCollection
        public final NavigableSet<E> delegate() {
            return (NavigableSet) super.delegate();
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedSet, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<E> headSet(E e) {
            return headSet(e, false);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedSet, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<E> subSet(E e, E e2) {
            return subSet(e, true, e2, false);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSortedSet, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<E> tailSet(E e) {
            return tailSet(e, true);
        }
    }

    public static class SynchronizedAsMapEntries<K, V> extends SynchronizedSet<Map.Entry<K, Collection<V>>> {
        private static final long serialVersionUID = 0;

        public SynchronizedAsMapEntries(Set<Map.Entry<K, Collection<V>>> set, @CheckForNull Object obj) {
            super(set, obj);
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            boolean zContains;
            synchronized (this.mutex) {
                zContains = !(obj instanceof Map.Entry) ? false : delegate().contains(Maps.unmodifiableEntry((Map.Entry) obj));
            }
            return zContains;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            boolean zContainsAllImpl;
            synchronized (this.mutex) {
                zContainsAllImpl = Collections2.containsAllImpl(delegate(), collection);
            }
            return zContainsAllImpl;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedSet, java.util.Collection, java.util.Set
        public boolean equals(@CheckForNull Object obj) {
            boolean zEqualsImpl;
            if (obj == this) {
                return true;
            }
            synchronized (this.mutex) {
                zEqualsImpl = Sets.equalsImpl(delegate(), obj);
            }
            return zEqualsImpl;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, Collection<V>>> iterator() {
            return new TransformedIterator<Map.Entry<K, Collection<V>>, Map.Entry<K, Collection<V>>>(super.iterator()) { // from class: com.google.common.collect.Synchronized.SynchronizedAsMapEntries.1
                @Override // com.google.common.collect.TransformedIterator
                public final Object transform(Object obj) {
                    final Map.Entry entry = (Map.Entry) obj;
                    return new ForwardingMapEntry<Object, Collection<Object>>() { // from class: com.google.common.collect.Synchronized.SynchronizedAsMapEntries.1.1
                        @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                        public final Object delegate() {
                            return entry;
                        }

                        @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                        public final Map.Entry<Object, Collection<Object>> delegate() {
                            return entry;
                        }

                        @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                        public Collection<Object> getValue() {
                            return Synchronized.typePreservingCollection((Collection) entry.getValue(), SynchronizedAsMapEntries.this.mutex);
                        }
                    };
                }
            };
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            boolean zRemove;
            synchronized (this.mutex) {
                zRemove = !(obj instanceof Map.Entry) ? false : delegate().remove(Maps.unmodifiableEntry((Map.Entry) obj));
            }
            return zRemove;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            boolean zRemoveAll;
            synchronized (this.mutex) {
                zRemoveAll = Iterators.removeAll(delegate().iterator(), collection);
            }
            return zRemoveAll;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            boolean zRetainAll;
            synchronized (this.mutex) {
                zRetainAll = Iterators.retainAll(delegate().iterator(), collection);
            }
            return zRetainAll;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            Object[] arrayImpl;
            synchronized (this.mutex) {
                arrayImpl = ObjectArrays.toArrayImpl(delegate());
            }
            return arrayImpl;
        }

        @Override // com.google.common.collect.Synchronized.SynchronizedCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            T[] tArr2;
            synchronized (this.mutex) {
                tArr2 = (T[]) ObjectArrays.toArrayImpl(delegate(), tArr);
            }
            return tArr2;
        }
    }
}
