package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
class MapIteratorCache<K, V> {
    private final Map<K, V> backingMap;

    @CheckForNull
    private volatile transient Map.Entry<K, V> cacheEntry;

    /* JADX INFO: renamed from: com.google.common.graph.MapIteratorCache$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractSet<Object> {
        public AnonymousClass1() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            return MapIteratorCache.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return MapIteratorCache.this.backingMap.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public UnmodifiableIterator<Object> iterator() {
            final Iterator<Map.Entry<K, V>> it = MapIteratorCache.this.backingMap.entrySet().iterator();
            return new UnmodifiableIterator<Object>() { // from class: com.google.common.graph.MapIteratorCache.1.1
                @Override // java.util.Iterator
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override // java.util.Iterator
                public Object next() {
                    Map.Entry entry = (Map.Entry) it.next();
                    MapIteratorCache.this.cacheEntry = entry;
                    return entry.getKey();
                }
            };
        }
    }

    public MapIteratorCache(Map<K, V> map) {
        this.backingMap = (Map) Preconditions.checkNotNull(map);
    }

    public void clearCache() {
        this.cacheEntry = null;
    }

    public final boolean containsKey(@CheckForNull Object obj) {
        return getIfCached(obj) != null || this.backingMap.containsKey(obj);
    }

    @CheckForNull
    public V get(Object obj) {
        Preconditions.checkNotNull(obj);
        V ifCached = getIfCached(obj);
        return ifCached == null ? getWithoutCaching(obj) : ifCached;
    }

    @CheckForNull
    public V getIfCached(@CheckForNull Object obj) {
        Map.Entry<K, V> entry = this.cacheEntry;
        if (entry == null || entry.getKey() != obj) {
            return null;
        }
        return entry.getValue();
    }

    @CheckForNull
    public final V getWithoutCaching(Object obj) {
        Preconditions.checkNotNull(obj);
        return this.backingMap.get(obj);
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public final V put(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        clearCache();
        return this.backingMap.put(k, v);
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public final V remove(Object obj) {
        Preconditions.checkNotNull(obj);
        clearCache();
        return this.backingMap.remove(obj);
    }
}
