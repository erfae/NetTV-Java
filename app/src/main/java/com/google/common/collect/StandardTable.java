package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
class StandardTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;

    @GwtTransient
    public final Map<R, Map<C, V>> backingMap;

    @CheckForNull
    private transient Set<C> columnKeySet;

    @CheckForNull
    private transient StandardTable<R, C, V>.ColumnMap columnMap;

    @GwtTransient
    public final Supplier<? extends Map<C, V>> factory;

    @CheckForNull
    private transient Map<R, Map<C, V>> rowMap;

    public class CellIterator implements Iterator<Table.Cell<R, C, V>> {
        public Iterator<Map.Entry<C, V>> columnIterator;

        @CheckForNull
        public Map.Entry<R, Map<C, V>> rowEntry;
        public final Iterator<Map.Entry<R, Map<C, V>>> rowIterator;

        private CellIterator(StandardTable standardTable) {
            this.rowIterator = standardTable.backingMap.entrySet().iterator();
            this.columnIterator = Iterators.EmptyModifiableIterator.INSTANCE;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.rowIterator.hasNext() || this.columnIterator.hasNext();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.columnIterator.remove();
            Map.Entry<R, Map<C, V>> entry = this.rowEntry;
            Objects.requireNonNull(entry);
            if (entry.getValue().isEmpty()) {
                this.rowIterator.remove();
                this.rowEntry = null;
            }
        }

        @Override // java.util.Iterator
        public Table.Cell<R, C, V> next() {
            if (!this.columnIterator.hasNext()) {
                Map.Entry<R, Map<C, V>> next = this.rowIterator.next();
                this.rowEntry = next;
                this.columnIterator = next.getValue().entrySet().iterator();
            }
            Objects.requireNonNull(this.rowEntry);
            Map.Entry<C, V> next2 = this.columnIterator.next();
            return Tables.immutableCell(this.rowEntry.getKey(), next2.getKey(), next2.getValue());
        }
    }

    public class Column extends Maps.ViewCachingAbstractMap<R, V> {
        public final C columnKey;

        public class EntrySet extends Sets.ImprovedAbstractSet<Map.Entry<R, V>> {
            private EntrySet() {
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                Column.this.removeFromColumnIf(Predicates.alwaysTrue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return StandardTable.this.containsMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean isEmpty() {
                Column column = Column.this;
                return !StandardTable.this.containsColumn(column.columnKey);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<R, V>> iterator() {
                return new EntrySetIterator();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(@CheckForNull Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return StandardTable.this.removeMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Predicates.not(Predicates.in(collection)));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                Iterator<Map<C, V>> it = StandardTable.this.backingMap.values().iterator();
                int i = 0;
                while (it.hasNext()) {
                    if (it.next().containsKey(Column.this.columnKey)) {
                        i++;
                    }
                }
                return i;
            }
        }

        public class EntrySetIterator extends AbstractIterator<Map.Entry<R, V>> {
            public final Iterator<Map.Entry<R, Map<C, V>>> iterator;

            private EntrySetIterator() {
                this.iterator = StandardTable.this.backingMap.entrySet().iterator();
            }

            @Override // com.google.common.collect.AbstractIterator
            @CheckForNull
            public final Object computeNext() {
                while (this.iterator.hasNext()) {
                    final Map.Entry<R, Map<C, V>> next = this.iterator.next();
                    if (next.getValue().containsKey(Column.this.columnKey)) {
                        return new AbstractMapEntry<Object, Object>() { // from class: com.google.common.collect.StandardTable.Column.EntrySetIterator.1EntryImpl
                            @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                            public Object getKey() {
                                return next.getKey();
                            }

                            @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                            public Object getValue() {
                                return ((Map) next.getValue()).get(Column.this.columnKey);
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                            public Object setValue(Object obj) {
                                return ((Map) next.getValue()).put(Column.this.columnKey, Preconditions.checkNotNull(obj));
                            }
                        };
                    }
                }
                endOfData();
                return null;
            }
        }

        public class KeySet extends Maps.KeySet<R, V> {
            public KeySet() {
                super(Column.this);
            }

            @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                Column column = Column.this;
                return StandardTable.this.contains(obj, column.columnKey);
            }

            @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(@CheckForNull Object obj) {
                Column column = Column.this;
                return StandardTable.this.remove(obj, column.columnKey) != null;
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }

        public class Values extends Maps.Values<R, V> {
            public Values() {
                super(Column.this);
            }

            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean remove(@CheckForNull Object obj) {
                return obj != null && Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.equalTo(obj)));
            }

            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean removeAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.in(collection)));
            }

            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean retainAll(Collection<?> collection) {
                return Column.this.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }

        public Column(C c) {
            this.columnKey = (C) Preconditions.checkNotNull(c);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            return StandardTable.this.contains(obj, this.columnKey);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public final Set<Map.Entry<R, V>> createEntrySet() {
            return new EntrySet();
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public final Set<R> createKeySet() {
            return new KeySet();
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public final Collection<V> createValues() {
            return new Values();
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V get(@CheckForNull Object obj) {
            return (V) StandardTable.this.get(obj, this.columnKey);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V put(R r, V v) {
            return (V) StandardTable.this.put(r, this.columnKey, v);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V remove(@CheckForNull Object obj) {
            return (V) StandardTable.this.remove(obj, this.columnKey);
        }

        @CanIgnoreReturnValue
        public final boolean removeFromColumnIf(Predicate<? super Map.Entry<R, V>> predicate) {
            Iterator<Map.Entry<R, Map<C, V>>> it = StandardTable.this.backingMap.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map.Entry<R, Map<C, V>> next = it.next();
                Map<C, V> value = next.getValue();
                V v = value.get(this.columnKey);
                if (v != null && predicate.apply(Maps.immutableEntry(next.getKey(), v))) {
                    value.remove(this.columnKey);
                    z = true;
                    if (value.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }
    }

    public class ColumnKeyIterator extends AbstractIterator<C> {
        public Iterator<Map.Entry<C, V>> entryIterator;
        public final Iterator<Map<C, V>> mapIterator;
        public final Map<C, V> seen;

        private ColumnKeyIterator(StandardTable standardTable) {
            this.seen = standardTable.factory.get();
            this.mapIterator = standardTable.backingMap.values().iterator();
            this.entryIterator = Iterators.ArrayItr.EMPTY;
        }

        @Override // com.google.common.collect.AbstractIterator
        @CheckForNull
        public final C computeNext() {
            while (true) {
                if (this.entryIterator.hasNext()) {
                    Map.Entry<C, V> next = this.entryIterator.next();
                    if (!this.seen.containsKey(next.getKey())) {
                        this.seen.put(next.getKey(), next.getValue());
                        return next.getKey();
                    }
                } else {
                    if (!this.mapIterator.hasNext()) {
                        endOfData();
                        return null;
                    }
                    this.entryIterator = this.mapIterator.next().entrySet().iterator();
                }
            }
        }
    }

    public class ColumnKeySet extends StandardTable<R, C, V>.TableSet<C> {
        private ColumnKeySet() {
            super();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            return StandardTable.this.containsColumn(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<C> iterator() {
            return StandardTable.this.createColumnKeyIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            boolean z = false;
            if (obj == null) {
                return false;
            }
            Iterator<Map<C, V>> it = StandardTable.this.backingMap.values().iterator();
            while (it.hasNext()) {
                Map<C, V> next = it.next();
                if (next.keySet().remove(obj)) {
                    z = true;
                    if (next.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }

        @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Iterator<Map<C, V>> it = StandardTable.this.backingMap.values().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map<C, V> next = it.next();
                if (Iterators.removeAll(next.keySet().iterator(), collection)) {
                    z = true;
                    if (next.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }

        @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Iterator<Map<C, V>> it = StandardTable.this.backingMap.values().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map<C, V> next = it.next();
                if (next.keySet().retainAll(collection)) {
                    z = true;
                    if (next.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Iterators.size(iterator());
        }
    }

    public class ColumnMap extends Maps.ViewCachingAbstractMap<C, Map<R, V>> {

        public class ColumnMapEntrySet extends StandardTable<R, C, V>.TableSet<Map.Entry<C, Map<R, V>>> {
            public ColumnMapEntrySet() {
                super();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                if (!StandardTable.this.containsColumn(entry.getKey())) {
                    return false;
                }
                Map<R, V> map = ColumnMap.this.get(entry.getKey());
                Objects.requireNonNull(map);
                return map.equals(entry.getValue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<C, Map<R, V>>> iterator() {
                return Maps.asMapEntryIterator(StandardTable.this.columnKeySet(), new Function<C, Map<R, V>>() { // from class: com.google.common.collect.StandardTable.ColumnMap.ColumnMapEntrySet.1
                    @Override // com.google.common.base.Function
                    public Map<R, V> apply(C c) {
                        return StandardTable.this.column(c);
                    }
                });
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(@CheckForNull Object obj) {
                if (!contains(obj) || !(obj instanceof Map.Entry)) {
                    return false;
                }
                StandardTable.this.removeColumn(((Map.Entry) obj).getKey());
                return true;
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                return Sets.removeAllImpl(this, collection.iterator());
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean z = false;
                for (Object obj : Lists.newArrayList(StandardTable.this.columnKeySet().iterator())) {
                    if (!collection.contains(Maps.immutableEntry(obj, StandardTable.this.column(obj)))) {
                        StandardTable.this.removeColumn(obj);
                        z = true;
                    }
                }
                return z;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return StandardTable.this.columnKeySet().size();
            }
        }

        public class ColumnMapValues extends Maps.Values<C, Map<R, V>> {
            public ColumnMapValues() {
                super(ColumnMap.this);
            }

            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean remove(@CheckForNull Object obj) {
                for (Map.Entry<C, Map<R, V>> entry : ColumnMap.this.entrySet()) {
                    if (entry.getValue().equals(obj)) {
                        StandardTable.this.removeColumn(entry.getKey());
                        return true;
                    }
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean z = false;
                for (Object obj : Lists.newArrayList(StandardTable.this.columnKeySet().iterator())) {
                    if (collection.contains(StandardTable.this.column(obj))) {
                        StandardTable.this.removeColumn(obj);
                        z = true;
                    }
                }
                return z;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean z = false;
                for (Object obj : Lists.newArrayList(StandardTable.this.columnKeySet().iterator())) {
                    if (!collection.contains(StandardTable.this.column(obj))) {
                        StandardTable.this.removeColumn(obj);
                        z = true;
                    }
                }
                return z;
            }
        }

        private ColumnMap() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            return StandardTable.this.containsColumn(obj);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<Map.Entry<C, Map<R, V>>> createEntrySet() {
            return new ColumnMapEntrySet();
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public final Collection<Map<R, V>> createValues() {
            return new ColumnMapValues();
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap, java.util.AbstractMap, java.util.Map
        public Set<C> keySet() {
            return StandardTable.this.columnKeySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Map<R, V> get(@CheckForNull Object obj) {
            if (!StandardTable.this.containsColumn(obj)) {
                return null;
            }
            StandardTable standardTable = StandardTable.this;
            Objects.requireNonNull(obj);
            return standardTable.column(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Map<R, V> remove(@CheckForNull Object obj) {
            if (StandardTable.this.containsColumn(obj)) {
                return StandardTable.this.removeColumn(obj);
            }
            return null;
        }
    }

    public class Row extends Maps.IteratorBasedAbstractMap<C, V> {

        @CheckForNull
        public Map<C, V> backingRowMap;
        public final R rowKey;

        public Row(R r) {
            this.rowKey = (R) Preconditions.checkNotNull(r);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public void clear() {
            updateBackingRowMapField();
            Map<C, V> map = this.backingRowMap;
            if (map != null) {
                map.clear();
            }
            maintainEmptyInvariant();
        }

        @CheckForNull
        public Map<C, V> computeBackingRowMap() {
            return StandardTable.this.backingMap.get(this.rowKey);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            Map<C, V> map;
            updateBackingRowMapField();
            return (obj == null || (map = this.backingRowMap) == null || !Maps.safeContainsKey(map, obj)) ? false : true;
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
        public final Iterator<Map.Entry<C, V>> entryIterator() {
            updateBackingRowMapField();
            Map<C, V> map = this.backingRowMap;
            if (map == null) {
                return Iterators.EmptyModifiableIterator.INSTANCE;
            }
            final Iterator<Map.Entry<C, V>> it = map.entrySet().iterator();
            return new Iterator<Map.Entry<C, V>>() { // from class: com.google.common.collect.StandardTable.Row.1
                @Override // java.util.Iterator
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override // java.util.Iterator
                public void remove() {
                    it.remove();
                    Row.this.maintainEmptyInvariant();
                }

                @Override // java.util.Iterator
                public Map.Entry<C, V> next() {
                    Row row = Row.this;
                    final Map.Entry entry = (Map.Entry) it.next();
                    Objects.requireNonNull(row);
                    return new ForwardingMapEntry<Object, Object>() { // from class: com.google.common.collect.StandardTable.Row.2
                        @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                        public final Object delegate() {
                            return entry;
                        }

                        @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                        public boolean equals(@CheckForNull Object obj) {
                            if (!(obj instanceof Map.Entry)) {
                                return false;
                            }
                            Map.Entry entry2 = (Map.Entry) obj;
                            return com.google.common.base.Objects.equal(getKey(), entry2.getKey()) && com.google.common.base.Objects.equal(getValue(), entry2.getValue());
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                        public Object setValue(Object obj) {
                            return super.setValue(Preconditions.checkNotNull(obj));
                        }

                        @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                        public final Map.Entry<Object, Object> delegate() {
                            return entry;
                        }
                    };
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V get(@CheckForNull Object obj) {
            Map<C, V> map;
            updateBackingRowMapField();
            if (obj == null || (map = this.backingRowMap) == null) {
                return null;
            }
            return (V) Maps.safeGet(map, obj);
        }

        public void maintainEmptyInvariant() {
            updateBackingRowMapField();
            Map<C, V> map = this.backingRowMap;
            if (map == null || !map.isEmpty()) {
                return;
            }
            StandardTable.this.backingMap.remove(this.rowKey);
            this.backingRowMap = null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V put(C c, V v) {
            Preconditions.checkNotNull(c);
            Preconditions.checkNotNull(v);
            Map<C, V> map = this.backingRowMap;
            return (map == null || map.isEmpty()) ? (V) StandardTable.this.put(this.rowKey, c, v) : this.backingRowMap.put(c, v);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V remove(@CheckForNull Object obj) {
            updateBackingRowMapField();
            Map<C, V> map = this.backingRowMap;
            if (map == null) {
                return null;
            }
            V v = (V) Maps.safeRemove(map, obj);
            maintainEmptyInvariant();
            return v;
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public int size() {
            updateBackingRowMapField();
            Map<C, V> map = this.backingRowMap;
            if (map == null) {
                return 0;
            }
            return map.size();
        }

        public final void updateBackingRowMapField() {
            Map<C, V> map = this.backingRowMap;
            if (map == null || (map.isEmpty() && StandardTable.this.backingMap.containsKey(this.rowKey))) {
                this.backingRowMap = computeBackingRowMap();
            }
        }
    }

    public class RowMap extends Maps.ViewCachingAbstractMap<R, Map<C, V>> {

        public class EntrySet extends StandardTable<R, C, V>.TableSet<Map.Entry<R, Map<C, V>>> {
            public EntrySet() {
                super();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@CheckForNull Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return entry.getKey() != null && (entry.getValue() instanceof Map) && Collections2.safeContains(StandardTable.this.backingMap.entrySet(), entry);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<R, Map<C, V>>> iterator() {
                return Maps.asMapEntryIterator(StandardTable.this.backingMap.keySet(), new Function<R, Map<C, V>>() { // from class: com.google.common.collect.StandardTable.RowMap.EntrySet.1
                    @Override // com.google.common.base.Function
                    public Map<C, V> apply(R r) {
                        return StandardTable.this.row(r);
                    }
                });
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(@CheckForNull Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return entry.getKey() != null && (entry.getValue() instanceof Map) && StandardTable.this.backingMap.entrySet().remove(entry);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return StandardTable.this.backingMap.size();
            }
        }

        public RowMap() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            return StandardTable.this.containsRow(obj);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public final Set<Map.Entry<R, Map<C, V>>> createEntrySet() {
            return new EntrySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Map<C, V> get(@CheckForNull Object obj) {
            if (!StandardTable.this.containsRow(obj)) {
                return null;
            }
            StandardTable standardTable = StandardTable.this;
            Objects.requireNonNull(obj);
            return standardTable.row(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Map<C, V> remove(@CheckForNull Object obj) {
            if (obj == null) {
                return null;
            }
            return StandardTable.this.backingMap.remove(obj);
        }
    }

    public abstract class TableSet<T> extends Sets.ImprovedAbstractSet<T> {
        private TableSet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            StandardTable.this.backingMap.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return StandardTable.this.backingMap.isEmpty();
        }
    }

    public StandardTable(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        this.backingMap = map;
        this.factory = supplier;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean containsMapping(@CheckForNull Object obj, @CheckForNull Object obj2, @CheckForNull Object obj3) {
        return obj3 != null && obj3.equals(get(obj, obj2));
    }

    private Map<C, V> getOrCreate(R r) {
        Map<C, V> map = this.backingMap.get(r);
        if (map != null) {
            return map;
        }
        Map<C, V> map2 = this.factory.get();
        this.backingMap.put(r, map2);
        return map2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CanIgnoreReturnValue
    public Map<R, V> removeColumn(@CheckForNull Object obj) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<Map.Entry<R, Map<C, V>>> it = this.backingMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<R, Map<C, V>> next = it.next();
            V vRemove = next.getValue().remove(obj);
            if (vRemove != null) {
                linkedHashMap.put(next.getKey(), vRemove);
                if (next.getValue().isEmpty()) {
                    it.remove();
                }
            }
        }
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean removeMapping(@CheckForNull Object obj, @CheckForNull Object obj2, @CheckForNull Object obj3) {
        if (!containsMapping(obj, obj2, obj3)) {
            return false;
        }
        remove(obj, obj2);
        return true;
    }

    @Override // com.google.common.collect.AbstractTable
    public final Iterator<Table.Cell<R, C, V>> cellIterator() {
        return new CellIterator();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public void clear() {
        this.backingMap.clear();
    }

    @Override // com.google.common.collect.Table
    public Map<R, V> column(C c) {
        return new Column(c);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Set<C> columnKeySet() {
        Set<C> set = this.columnKeySet;
        if (set != null) {
            return set;
        }
        ColumnKeySet columnKeySet = new ColumnKeySet();
        this.columnKeySet = columnKeySet;
        return columnKeySet;
    }

    @Override // com.google.common.collect.Table
    public Map<C, Map<R, V>> columnMap() {
        StandardTable<R, C, V>.ColumnMap columnMap = this.columnMap;
        if (columnMap != null) {
            return columnMap;
        }
        StandardTable<R, C, V>.ColumnMap columnMap2 = new ColumnMap();
        this.columnMap = columnMap2;
        return columnMap2;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean contains(@CheckForNull Object obj, @CheckForNull Object obj2) {
        return (obj == null || obj2 == null || !super.contains(obj, obj2)) ? false : true;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsColumn(@CheckForNull Object obj) {
        if (obj == null) {
            return false;
        }
        Iterator<Map<C, V>> it = this.backingMap.values().iterator();
        while (it.hasNext()) {
            if (Maps.safeContainsKey(it.next(), obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsRow(@CheckForNull Object obj) {
        return obj != null && Maps.safeContainsKey(this.backingMap, obj);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsValue(@CheckForNull Object obj) {
        return obj != null && super.containsValue(obj);
    }

    public Iterator<C> createColumnKeyIterator() {
        return new ColumnKeyIterator();
    }

    public Map<R, Map<C, V>> createRowMap() {
        return new RowMap();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @CheckForNull
    public V get(@CheckForNull Object obj, @CheckForNull Object obj2) {
        if (obj == null || obj2 == null) {
            return null;
        }
        return (V) super.get(obj, obj2);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @CanIgnoreReturnValue
    @CheckForNull
    public V put(R r, C c, V v) {
        Preconditions.checkNotNull(r);
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(v);
        return getOrCreate(r).put(c, v);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @CanIgnoreReturnValue
    @CheckForNull
    public V remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
        Map map;
        if (obj == null || obj2 == null || (map = (Map) Maps.safeGet(this.backingMap, obj)) == null) {
            return null;
        }
        V v = (V) map.remove(obj2);
        if (map.isEmpty()) {
            this.backingMap.remove(obj);
        }
        return v;
    }

    @Override // com.google.common.collect.Table
    public Map<C, V> row(R r) {
        return new Row(r);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Set<R> rowKeySet() {
        return rowMap().keySet();
    }

    @Override // com.google.common.collect.Table
    public Map<R, Map<C, V>> rowMap() {
        Map<R, Map<C, V>> map = this.rowMap;
        if (map != null) {
            return map;
        }
        Map<R, Map<C, V>> mapCreateRowMap = createRowMap();
        this.rowMap = mapCreateRowMap;
        return mapCreateRowMap;
    }

    @Override // com.google.common.collect.Table
    public int size() {
        Iterator<Map<C, V>> it = this.backingMap.values().iterator();
        int size = 0;
        while (it.hasNext()) {
            size += it.next().size();
        }
        return size;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Collection<V> values() {
        return super.values();
    }
}
