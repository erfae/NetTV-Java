package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
abstract class ImmutableMapEntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {

    @GwtIncompatible
    public static class EntrySetSerializedForm<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
    }

    public static final class RegularEntrySet<K, V> extends ImmutableMapEntrySet<K, V> {
        private final transient ImmutableList<Map.Entry<K, V>> entries;
        private final transient ImmutableMap<K, V> map;

        @Override // com.google.common.collect.ImmutableCollection
        @GwtIncompatible("not used in GWT")
        public final int copyIntoArray(Object[] objArr, int i) {
            return this.entries.copyIntoArray(objArr, i);
        }

        @Override // com.google.common.collect.ImmutableSet
        public final ImmutableList<Map.Entry<K, V>> createAsList() {
            return this.entries;
        }

        @Override // com.google.common.collect.ImmutableMapEntrySet
        public final ImmutableMap<K, V> map() {
            return this.map;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.entries.iterator();
        }
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@CheckForNull Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        V v = map().get(entry.getKey());
        return v != null && v.equals(entry.getValue());
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return map().hashCode();
    }

    @Override // com.google.common.collect.ImmutableSet
    @GwtIncompatible
    public final boolean isHashCodeFast() {
        return map().isHashCodeFast();
    }

    @Override // com.google.common.collect.ImmutableCollection
    public final boolean isPartialView() {
        return map().isPartialView();
    }

    public abstract ImmutableMap<K, V> map();

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return map().size();
    }
}
