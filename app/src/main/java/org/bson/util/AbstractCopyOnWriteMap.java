package org.bson.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes2.dex */
abstract class AbstractCopyOnWriteMap<K, V, M extends Map<K, V>> implements ConcurrentMap<K, V> {
    private volatile M delegate;
    private final transient Lock lock = new ReentrantLock();
    private final View<K, V> view;

    public static abstract class CollectionView<E> implements Collection<E> {
        @Override // java.util.Collection
        public final boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public final boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public final boolean contains(Object obj) {
            return getDelegate().contains(obj);
        }

        @Override // java.util.Collection
        public final boolean containsAll(Collection<?> collection) {
            return getDelegate().containsAll(collection);
        }

        @Override // java.util.Collection
        public boolean equals(Object obj) {
            return getDelegate().equals(obj);
        }

        abstract Collection<E> getDelegate();

        @Override // java.util.Collection
        public int hashCode() {
            return getDelegate().hashCode();
        }

        @Override // java.util.Collection
        public final boolean isEmpty() {
            return getDelegate().isEmpty();
        }

        @Override // java.util.Collection, java.lang.Iterable
        public final Iterator<E> iterator() {
            return new UnmodifiableIterator(getDelegate().iterator());
        }

        @Override // java.util.Collection
        public final int size() {
            return getDelegate().size();
        }

        @Override // java.util.Collection
        public final Object[] toArray() {
            return getDelegate().toArray();
        }

        public String toString() {
            return getDelegate().toString();
        }

        @Override // java.util.Collection
        public final <T> T[] toArray(T[] tArr) {
            return (T[]) getDelegate().toArray(tArr);
        }
    }

    public class EntrySet extends CollectionView<Map.Entry<K, V>> implements Set<Map.Entry<K, V>> {
        private EntrySet() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public void clear() {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                mapCopy.entrySet().clear();
                AbstractCopyOnWriteMap.this.set(mapCopy);
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.CollectionView
        public final Collection<Map.Entry<K, V>> getDelegate() {
            return AbstractCopyOnWriteMap.this.delegate.entrySet();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            boolean zRemove;
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                if (contains(obj)) {
                    Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                    try {
                        zRemove = mapCopy.entrySet().remove(obj);
                        AbstractCopyOnWriteMap.this.set(mapCopy);
                    } catch (Throwable th) {
                        AbstractCopyOnWriteMap.this.set(mapCopy);
                        throw th;
                    }
                } else {
                    zRemove = false;
                }
                AbstractCopyOnWriteMap.this.lock.unlock();
                return zRemove;
            } catch (Throwable th2) {
                AbstractCopyOnWriteMap.this.lock.unlock();
                throw th2;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    boolean zRemoveAll = mapCopy.entrySet().removeAll(collection);
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    AbstractCopyOnWriteMap.this.lock.unlock();
                    return zRemoveAll;
                } catch (Throwable th) {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    throw th;
                }
            } catch (Throwable th2) {
                AbstractCopyOnWriteMap.this.lock.unlock();
                throw th2;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    boolean zRetainAll = mapCopy.entrySet().retainAll(collection);
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    AbstractCopyOnWriteMap.this.lock.unlock();
                    return zRetainAll;
                } catch (Throwable th) {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    throw th;
                }
            } catch (Throwable th2) {
                AbstractCopyOnWriteMap.this.lock.unlock();
                throw th2;
            }
        }
    }

    public final class Immutable extends View<K, V> {
        public Immutable() {
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Set<Map.Entry<K, V>> entrySet() {
            return Collections.unmodifiableSet(AbstractCopyOnWriteMap.this.delegate.entrySet());
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Set<K> keySet() {
            return Collections.unmodifiableSet(AbstractCopyOnWriteMap.this.delegate.keySet());
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Collection<V> values() {
            return Collections.unmodifiableCollection(AbstractCopyOnWriteMap.this.delegate.values());
        }
    }

    public class KeySet extends CollectionView<K> implements Set<K> {
        private KeySet() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public void clear() {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                mapCopy.keySet().clear();
                AbstractCopyOnWriteMap.this.set(mapCopy);
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.CollectionView
        public final Collection<K> getDelegate() {
            return AbstractCopyOnWriteMap.this.delegate.keySet();
        }

        @Override // java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return AbstractCopyOnWriteMap.this.remove(obj) != null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    boolean zRemoveAll = mapCopy.keySet().removeAll(collection);
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    AbstractCopyOnWriteMap.this.lock.unlock();
                    return zRemoveAll;
                } catch (Throwable th) {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    throw th;
                }
            } catch (Throwable th2) {
                AbstractCopyOnWriteMap.this.lock.unlock();
                throw th2;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    boolean zRetainAll = mapCopy.keySet().retainAll(collection);
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    AbstractCopyOnWriteMap.this.lock.unlock();
                    return zRetainAll;
                } catch (Throwable th) {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    throw th;
                }
            } catch (Throwable th2) {
                AbstractCopyOnWriteMap.this.lock.unlock();
                throw th2;
            }
        }
    }

    public final class Mutable extends View<K, V> {
        private final transient AbstractCopyOnWriteMap<K, V, M>.EntrySet entrySet;
        private final transient AbstractCopyOnWriteMap<K, V, M>.KeySet keySet;
        private final transient AbstractCopyOnWriteMap<K, V, M>.Values values;

        public Mutable(AbstractCopyOnWriteMap abstractCopyOnWriteMap) {
            this.keySet = new KeySet();
            this.entrySet = new EntrySet();
            this.values = new Values();
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Set<Map.Entry<K, V>> entrySet() {
            return this.entrySet;
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Set<K> keySet() {
            return this.keySet;
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Collection<V> values() {
            return this.values;
        }
    }

    public static class UnmodifiableIterator<T> implements Iterator<T> {
        private final Iterator<T> delegate;

        public UnmodifiableIterator(Iterator<T> it) {
            this.delegate = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.delegate.hasNext();
        }

        @Override // java.util.Iterator
        public T next() {
            return this.delegate.next();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public final class Values extends CollectionView<V> {
        private Values() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection
        public void clear() {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                mapCopy.values().clear();
                AbstractCopyOnWriteMap.this.set(mapCopy);
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.CollectionView
        public final Collection<V> getDelegate() {
            return AbstractCopyOnWriteMap.this.delegate.values();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection
        public boolean remove(Object obj) {
            boolean zRemove;
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                if (contains(obj)) {
                    Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                    try {
                        zRemove = mapCopy.values().remove(obj);
                        AbstractCopyOnWriteMap.this.set(mapCopy);
                    } catch (Throwable th) {
                        AbstractCopyOnWriteMap.this.set(mapCopy);
                        throw th;
                    }
                } else {
                    zRemove = false;
                }
                AbstractCopyOnWriteMap.this.lock.unlock();
                return zRemove;
            } catch (Throwable th2) {
                AbstractCopyOnWriteMap.this.lock.unlock();
                throw th2;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    boolean zRemoveAll = mapCopy.values().removeAll(collection);
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    AbstractCopyOnWriteMap.this.lock.unlock();
                    return zRemoveAll;
                } catch (Throwable th) {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    throw th;
                }
            } catch (Throwable th2) {
                AbstractCopyOnWriteMap.this.lock.unlock();
                throw th2;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    boolean zRetainAll = mapCopy.values().retainAll(collection);
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    AbstractCopyOnWriteMap.this.lock.unlock();
                    return zRetainAll;
                } catch (Throwable th) {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                    throw th;
                }
            } catch (Throwable th2) {
                AbstractCopyOnWriteMap.this.lock.unlock();
                throw th2;
            }
        }
    }

    public static abstract class View<K, V> {

        public enum Type {
            STABLE { // from class: org.bson.util.AbstractCopyOnWriteMap.View.Type.1
                @Override // org.bson.util.AbstractCopyOnWriteMap.View.Type
                public final <K, V, M extends Map<K, V>> View<K, V> get(AbstractCopyOnWriteMap<K, V, M> abstractCopyOnWriteMap) {
                    return abstractCopyOnWriteMap.new Immutable();
                }
            },
            LIVE { // from class: org.bson.util.AbstractCopyOnWriteMap.View.Type.2
                @Override // org.bson.util.AbstractCopyOnWriteMap.View.Type
                public final <K, V, M extends Map<K, V>> View<K, V> get(AbstractCopyOnWriteMap<K, V, M> abstractCopyOnWriteMap) {
                    return new Mutable(abstractCopyOnWriteMap);
                }
            };

            public abstract <K, V, M extends Map<K, V>> View<K, V> get(AbstractCopyOnWriteMap<K, V, M> abstractCopyOnWriteMap);
        }

        public abstract Set<Map.Entry<K, V>> entrySet();

        public abstract Set<K> keySet();

        public abstract Collection<V> values();
    }

    public <N extends Map<? extends K, ? extends V>> AbstractCopyOnWriteMap(N n, View.Type type) {
        this.delegate = (M) Assertions.notNull("delegate", copy((Map) Assertions.notNull("map", n)));
        this.view = ((View.Type) Assertions.notNull("viewType", type)).get(this);
    }

    @Override // java.util.Map
    public final void clear() {
        this.lock.lock();
        try {
            this.delegate = (M) copy(Collections.emptyMap());
        } finally {
            this.lock.unlock();
        }
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.delegate.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.delegate.containsValue(obj);
    }

    public final M copy() {
        this.lock.lock();
        try {
            return (M) copy(this.delegate);
        } finally {
            this.lock.unlock();
        }
    }

    public abstract <N extends Map<? extends K, ? extends V>> M copy(N n);

    @Override // java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        return this.view.entrySet();
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        return this.delegate.equals(obj);
    }

    @Override // java.util.Map
    public final V get(Object obj) {
        return (V) this.delegate.get(obj);
    }

    @Override // java.util.Map
    public final int hashCode() {
        return this.delegate.hashCode();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override // java.util.Map
    public final Set<K> keySet() {
        return this.view.keySet();
    }

    @Override // java.util.Map
    public final V put(K k, V v) {
        this.lock.lock();
        try {
            M m = (M) copy();
            try {
                V v2 = (V) m.put(k, v);
                this.delegate = m;
                this.lock.unlock();
                return v2;
            } catch (Throwable th) {
                this.delegate = m;
                throw th;
            }
        } catch (Throwable th2) {
            this.lock.unlock();
            throw th2;
        }
    }

    @Override // java.util.Map
    public final void putAll(Map<? extends K, ? extends V> map) {
        this.lock.lock();
        try {
            M m = (M) copy();
            m.putAll(map);
            this.delegate = m;
        } finally {
            this.lock.unlock();
        }
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V putIfAbsent(K k, V v) {
        V v2;
        this.lock.lock();
        try {
            if (this.delegate.containsKey(k)) {
                v2 = (V) this.delegate.get(k);
            } else {
                M m = (M) copy();
                try {
                    v2 = (V) m.put(k, v);
                    this.delegate = m;
                } catch (Throwable th) {
                    this.delegate = m;
                    throw th;
                }
            }
            this.lock.unlock();
            return v2;
        } catch (Throwable th2) {
            this.lock.unlock();
            throw th2;
        }
    }

    @Override // java.util.Map
    public final V remove(Object obj) {
        V v;
        this.lock.lock();
        try {
            if (this.delegate.containsKey(obj)) {
                M m = (M) copy();
                try {
                    v = (V) m.remove(obj);
                    this.delegate = m;
                } catch (Throwable th) {
                    this.delegate = m;
                    throw th;
                }
            } else {
                v = null;
            }
            this.lock.unlock();
            return v;
        } catch (Throwable th2) {
            this.lock.unlock();
            throw th2;
        }
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean replace(K k, V v, V v2) {
        boolean z;
        this.lock.lock();
        try {
            if (this.delegate.containsKey(k) && equals(v, this.delegate.get(k))) {
                M m = (M) copy();
                m.put(k, v2);
                this.delegate = m;
                z = true;
            } else {
                z = false;
            }
            return z;
        } finally {
            this.lock.unlock();
        }
    }

    public final void set(M m) {
        this.delegate = m;
    }

    @Override // java.util.Map
    public final int size() {
        return this.delegate.size();
    }

    public String toString() {
        return this.delegate.toString();
    }

    @Override // java.util.Map
    public final Collection<V> values() {
        return this.view.values();
    }

    private boolean equals(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V replace(K k, V v) {
        V v2;
        this.lock.lock();
        try {
            if (this.delegate.containsKey(k)) {
                M m = (M) copy();
                try {
                    v2 = (V) m.put(k, v);
                    this.delegate = m;
                } catch (Throwable th) {
                    this.delegate = m;
                    throw th;
                }
            } else {
                v2 = null;
            }
            this.lock.unlock();
            return v2;
        } catch (Throwable th2) {
            this.lock.unlock();
            throw th2;
        }
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean remove(Object obj, Object obj2) {
        boolean z;
        this.lock.lock();
        try {
            if (this.delegate.containsKey(obj) && equals(obj2, this.delegate.get(obj))) {
                M m = (M) copy();
                m.remove(obj);
                this.delegate = m;
                z = true;
            } else {
                z = false;
            }
            return z;
        } finally {
            this.lock.unlock();
        }
    }
}
