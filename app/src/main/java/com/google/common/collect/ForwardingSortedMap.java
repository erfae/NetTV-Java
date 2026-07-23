package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.util.Comparator;
import java.util.SortedMap;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public abstract class ForwardingSortedMap<K, V> extends ForwardingMap<K, V> implements SortedMap<K, V> {

    @Beta
    public class StandardKeySet extends Maps.SortedKeySet<K, V> {
        public StandardKeySet(ForwardingSortedMap forwardingSortedMap) {
            super(forwardingSortedMap);
        }
    }

    @Override // java.util.SortedMap
    @CheckForNull
    public Comparator<? super K> comparator() {
        return delegate().comparator();
    }

    @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
    public abstract SortedMap<K, V> delegate();

    @Override // java.util.SortedMap
    @ParametricNullness
    public K firstKey() {
        return delegate().firstKey();
    }

    @Override // java.util.SortedMap
    public SortedMap<K, V> headMap(@ParametricNullness K k) {
        return delegate().headMap(k);
    }

    @Override // java.util.SortedMap
    @ParametricNullness
    public K lastKey() {
        return delegate().lastKey();
    }

    @Override // java.util.SortedMap
    public SortedMap<K, V> subMap(@ParametricNullness K k, @ParametricNullness K k2) {
        return delegate().subMap(k, k2);
    }

    @Override // java.util.SortedMap
    public SortedMap<K, V> tailMap(@ParametricNullness K k) {
        return delegate().tailMap(k);
    }
}
