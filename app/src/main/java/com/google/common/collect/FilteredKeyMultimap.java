package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
class FilteredKeyMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {
    public final Predicate<? super K> keyPredicate;
    public final Multimap<K, V> unfiltered;

    public static class AddRejectingList<K, V> extends ForwardingList<V> {

        @ParametricNullness
        public final K key;

        public AddRejectingList(@ParametricNullness K k) {
            this.key = k;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
        public boolean add(@ParametricNullness V v) {
            add(0, v);
            return true;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            addAll(0, collection);
            return true;
        }

        @Override // com.google.common.collect.ForwardingList, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public final Object delegate() {
            return Collections.emptyList();
        }

        @Override // com.google.common.collect.ForwardingList, java.util.List
        public void add(int i, @ParametricNullness V v) {
            Preconditions.checkPositionIndex(i, 0);
            String strValueOf = String.valueOf(this.key);
            throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 32, "Key does not satisfy predicate: ", strValueOf));
        }

        @Override // com.google.common.collect.ForwardingList, java.util.List
        @CanIgnoreReturnValue
        public boolean addAll(int i, Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            Preconditions.checkPositionIndex(i, 0);
            String strValueOf = String.valueOf(this.key);
            throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 32, "Key does not satisfy predicate: ", strValueOf));
        }

        @Override // com.google.common.collect.ForwardingList, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public final Collection delegate() {
            return Collections.emptyList();
        }

        @Override // com.google.common.collect.ForwardingList, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public final List<V> delegate() {
            return Collections.emptyList();
        }
    }

    public static class AddRejectingSet<K, V> extends ForwardingSet<V> {

        @ParametricNullness
        public final K key;

        public AddRejectingSet(@ParametricNullness K k) {
            this.key = k;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
        public boolean add(@ParametricNullness V v) {
            String strValueOf = String.valueOf(this.key);
            throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 32, "Key does not satisfy predicate: ", strValueOf));
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            String strValueOf = String.valueOf(this.key);
            throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 32, "Key does not satisfy predicate: ", strValueOf));
        }

        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public final Object delegate() {
            return Collections.emptySet();
        }

        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public final Collection delegate() {
            return Collections.emptySet();
        }

        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public final Set<V> delegate() {
            return Collections.emptySet();
        }
    }

    public class Entries extends ForwardingCollection<Map.Entry<K, V>> {
        public Entries() {
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (FilteredKeyMultimap.this.unfiltered.containsKey(entry.getKey()) && FilteredKeyMultimap.this.keyPredicate.apply((Object) entry.getKey())) {
                return FilteredKeyMultimap.this.unfiltered.remove(entry.getKey(), entry.getValue());
            }
            return false;
        }

        @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public final Collection<Map.Entry<K, V>> delegate() {
            return Collections2.filter(FilteredKeyMultimap.this.unfiltered.entries(), FilteredKeyMultimap.this.entryPredicate());
        }
    }

    public FilteredKeyMultimap(Multimap<K, V> multimap, Predicate<? super K> predicate) {
        this.unfiltered = (Multimap) Preconditions.checkNotNull(multimap);
        this.keyPredicate = (Predicate) Preconditions.checkNotNull(predicate);
    }

    @Override // com.google.common.collect.Multimap
    public void clear() {
        keySet().clear();
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@CheckForNull Object obj) {
        if (this.unfiltered.containsKey(obj)) {
            return this.keyPredicate.apply(obj);
        }
        return false;
    }

    @Override // com.google.common.collect.AbstractMultimap
    public final Map<K, Collection<V>> createAsMap() {
        return Maps.filterKeys(this.unfiltered.asMap(), this.keyPredicate);
    }

    @Override // com.google.common.collect.AbstractMultimap
    public Collection<Map.Entry<K, V>> createEntries() {
        return new Entries();
    }

    @Override // com.google.common.collect.AbstractMultimap
    public final Set<K> createKeySet() {
        return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
    }

    @Override // com.google.common.collect.AbstractMultimap
    public final Multiset<K> createKeys() {
        return Multisets.filter(this.unfiltered.keys(), this.keyPredicate);
    }

    @Override // com.google.common.collect.AbstractMultimap
    public final Collection<V> createValues() {
        return new FilteredMultimapValues(this);
    }

    @Override // com.google.common.collect.AbstractMultimap
    public final Iterator<Map.Entry<K, V>> entryIterator() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.FilteredMultimap
    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return Maps.keyPredicateOnEntries(this.keyPredicate);
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> get(@ParametricNullness K k) {
        if (this.keyPredicate.apply(k)) {
            return this.unfiltered.get(k);
        }
        return this.unfiltered instanceof SetMultimap ? new AddRejectingSet(k) : new AddRejectingList(k);
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Collection<V> removeAll(@CheckForNull Object obj) {
        if (containsKey(obj)) {
            return this.unfiltered.removeAll(obj);
        }
        return this.unfiltered instanceof SetMultimap ? Collections.emptySet() : Collections.emptyList();
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        Iterator<Collection<V>> it = asMap().values().iterator();
        int size = 0;
        while (it.hasNext()) {
            size += it.next().size();
        }
        return size;
    }

    public Multimap<K, V> unfiltered() {
        return this.unfiltered;
    }
}
