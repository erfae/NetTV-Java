package com.google.common.collect;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotCall;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@Beta
@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
public final class ArrayTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;
    private final V[][] array;
    private final ImmutableMap<C, Integer> columnKeyToIndex;
    private final ImmutableList<C> columnList;

    @CheckForNull
    private transient ArrayTable<R, C, V>.ColumnMap columnMap;
    private final ImmutableMap<R, Integer> rowKeyToIndex;
    private final ImmutableList<R> rowList;

    @CheckForNull
    private transient ArrayTable<R, C, V>.RowMap rowMap;

    public static abstract class ArrayMap<K, V> extends Maps.IteratorBasedAbstractMap<K, V> {
        private final ImmutableMap<K, Integer> keyIndex;

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            return this.keyIndex.containsKey(obj);
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
        public final Iterator<Map.Entry<K, V>> entryIterator() {
            return new AbstractIndexedListIterator<Map.Entry<K, V>>(size()) { // from class: com.google.common.collect.ArrayTable.ArrayMap.2
                @Override // com.google.common.collect.AbstractIndexedListIterator
                public final Object get(final int i) {
                    final ArrayMap arrayMap = ArrayMap.this;
                    Preconditions.checkElementIndex(i, arrayMap.size());
                    return new AbstractMapEntry<Object, Object>() { // from class: com.google.common.collect.ArrayTable.ArrayMap.1
                        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                        public Object getKey() {
                            return ArrayMap.this.getKey(i);
                        }

                        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                        @ParametricNullness
                        public Object getValue() {
                            return ArrayMap.this.getValue(i);
                        }

                        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                        @ParametricNullness
                        public Object setValue(@ParametricNullness Object obj) {
                            return ArrayMap.this.setValue(i, obj);
                        }
                    };
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V get(@CheckForNull Object obj) {
            Integer num = this.keyIndex.get(obj);
            if (num == null) {
                return null;
            }
            return getValue(num.intValue());
        }

        public final K getKey(int i) {
            return this.keyIndex.keySet().asList().get(i);
        }

        public abstract String getKeyRole();

        @ParametricNullness
        public abstract V getValue(int i);

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return this.keyIndex.isEmpty();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return this.keyIndex.keySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V put(K k, @ParametricNullness V v) {
            Integer num = this.keyIndex.get(k);
            if (num != null) {
                return setValue(num.intValue(), v);
            }
            String keyRole = getKeyRole();
            String strValueOf = String.valueOf(k);
            String strValueOf2 = String.valueOf(this.keyIndex.keySet());
            StringBuilder sbM209m = MainActivity$$ExternalSyntheticOutline0.m209m(strValueOf2.length() + strValueOf.length() + Insets$$ExternalSyntheticOutline0.m(keyRole, 9), keyRole, " ", strValueOf, " not in ");
            sbM209m.append(strValueOf2);
            throw new IllegalArgumentException(sbM209m.toString());
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public V remove(@CheckForNull Object obj) {
            throw new UnsupportedOperationException();
        }

        @ParametricNullness
        public abstract V setValue(int i, @ParametricNullness V v);

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public int size() {
            return this.keyIndex.size();
        }

        private ArrayMap(ImmutableMap<K, Integer> immutableMap) {
            this.keyIndex = immutableMap;
        }
    }

    public class Column extends ArrayMap<R, V> {
        public final int columnIndex;

        public Column(int i) {
            super(ArrayTable.this.rowKeyToIndex);
            this.columnIndex = i;
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        public final String getKeyRole() {
            return "Row";
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        @CheckForNull
        public final V getValue(int i) {
            return (V) ArrayTable.this.at(i, this.columnIndex);
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        @CheckForNull
        public final V setValue(int i, @CheckForNull V v) {
            return (V) ArrayTable.this.set(i, this.columnIndex, v);
        }
    }

    public class ColumnMap extends ArrayMap<C, Map<R, V>> {
        @Override // com.google.common.collect.ArrayTable.ArrayMap
        public final String getKeyRole() {
            return "Column";
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        public final Object getValue(int i) {
            return new Column(i);
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        public final Object setValue(int i, Object obj) {
            throw new UnsupportedOperationException();
        }

        private ColumnMap() {
            super(ArrayTable.this.columnKeyToIndex);
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap, java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Map<R, V> put(C c, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    public class Row extends ArrayMap<C, V> {
        public final int rowIndex;

        public Row(int i) {
            super(ArrayTable.this.columnKeyToIndex);
            this.rowIndex = i;
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        public final String getKeyRole() {
            return "Column";
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        @CheckForNull
        public final V getValue(int i) {
            return (V) ArrayTable.this.at(this.rowIndex, i);
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        @CheckForNull
        public final V setValue(int i, @CheckForNull V v) {
            return (V) ArrayTable.this.set(this.rowIndex, i, v);
        }
    }

    public class RowMap extends ArrayMap<R, Map<C, V>> {
        @Override // com.google.common.collect.ArrayTable.ArrayMap
        public final String getKeyRole() {
            return "Row";
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        public final Object getValue(int i) {
            return new Row(i);
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap
        public final Object setValue(int i, Object obj) {
            throw new UnsupportedOperationException();
        }

        private RowMap() {
            super(ArrayTable.this.rowKeyToIndex);
        }

        @Override // com.google.common.collect.ArrayTable.ArrayMap, java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Map<C, V> put(R r, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    private ArrayTable(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        ImmutableList<R> immutableListCopyOf = ImmutableList.copyOf(iterable);
        this.rowList = immutableListCopyOf;
        ImmutableList<C> immutableListCopyOf2 = ImmutableList.copyOf(iterable2);
        this.columnList = immutableListCopyOf2;
        Preconditions.checkArgument(immutableListCopyOf.isEmpty() == immutableListCopyOf2.isEmpty());
        this.rowKeyToIndex = Maps.indexMap(immutableListCopyOf);
        this.columnKeyToIndex = Maps.indexMap(immutableListCopyOf2);
        this.array = (V[][]) ((Object[][]) Array.newInstance((Class<?>) Object.class, immutableListCopyOf.size(), immutableListCopyOf2.size()));
        eraseAll();
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        return new ArrayTable<>(iterable, iterable2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Table.Cell<R, C, V> getCell(int i) {
        return new Tables.AbstractCell<R, C, V>(i) { // from class: com.google.common.collect.ArrayTable.2
            public final int columnIndex;
            public final int rowIndex;

            {
                this.rowIndex = i / ArrayTable.this.columnList.size();
                this.columnIndex = i % ArrayTable.this.columnList.size();
            }

            @Override // com.google.common.collect.Table.Cell
            public C getColumnKey() {
                return (C) ArrayTable.this.columnList.get(this.columnIndex);
            }

            @Override // com.google.common.collect.Table.Cell
            public R getRowKey() {
                return (R) ArrayTable.this.rowList.get(this.rowIndex);
            }

            @Override // com.google.common.collect.Table.Cell
            @CheckForNull
            public V getValue() {
                return (V) ArrayTable.this.at(this.rowIndex, this.columnIndex);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CheckForNull
    public V getValue(int i) {
        return at(i / this.columnList.size(), i % this.columnList.size());
    }

    @CheckForNull
    public V at(int i, int i2) {
        Preconditions.checkElementIndex(i, this.rowList.size());
        Preconditions.checkElementIndex(i2, this.columnList.size());
        return this.array[i][i2];
    }

    @Override // com.google.common.collect.AbstractTable
    public final Iterator<Table.Cell<R, C, V>> cellIterator() {
        return new AbstractIndexedListIterator<Table.Cell<R, C, V>>(size()) { // from class: com.google.common.collect.ArrayTable.1
            @Override // com.google.common.collect.AbstractIndexedListIterator
            public final Object get(int i) {
                return ArrayTable.this.getCell(i);
            }
        };
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.Table
    public Map<R, V> column(C c) {
        Preconditions.checkNotNull(c);
        Integer num = this.columnKeyToIndex.get(c);
        return num == null ? Collections.emptyMap() : new Column(num.intValue());
    }

    public ImmutableList<C> columnKeyList() {
        return this.columnList;
    }

    @Override // com.google.common.collect.Table
    public Map<C, Map<R, V>> columnMap() {
        ArrayTable<R, C, V>.ColumnMap columnMap = this.columnMap;
        if (columnMap != null) {
            return columnMap;
        }
        ArrayTable<R, C, V>.ColumnMap columnMap2 = new ColumnMap();
        this.columnMap = columnMap2;
        return columnMap2;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean contains(@CheckForNull Object obj, @CheckForNull Object obj2) {
        return containsRow(obj) && containsColumn(obj2);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsColumn(@CheckForNull Object obj) {
        return this.columnKeyToIndex.containsKey(obj);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsRow(@CheckForNull Object obj) {
        return this.rowKeyToIndex.containsKey(obj);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean containsValue(@CheckForNull Object obj) {
        for (V[] vArr : this.array) {
            for (V v : vArr) {
                if (Objects.equal(obj, v)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean equals(@CheckForNull Object obj) {
        return super.equals(obj);
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public V erase(@CheckForNull Object obj, @CheckForNull Object obj2) {
        Integer num = this.rowKeyToIndex.get(obj);
        Integer num2 = this.columnKeyToIndex.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return set(num.intValue(), num2.intValue(), null);
    }

    public void eraseAll() {
        for (V[] vArr : this.array) {
            Arrays.fill(vArr, (Object) null);
        }
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @CheckForNull
    public V get(@CheckForNull Object obj, @CheckForNull Object obj2) {
        Integer num = this.rowKeyToIndex.get(obj);
        Integer num2 = this.columnKeyToIndex.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return at(num.intValue(), num2.intValue());
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public boolean isEmpty() {
        return this.rowList.isEmpty() || this.columnList.isEmpty();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @CanIgnoreReturnValue
    @CheckForNull
    public V put(R r, C c, @CheckForNull V v) {
        Preconditions.checkNotNull(r);
        Preconditions.checkNotNull(c);
        Integer num = this.rowKeyToIndex.get(r);
        Preconditions.checkArgument(num != null, "Row %s not in %s", r, this.rowList);
        Integer num2 = this.columnKeyToIndex.get(c);
        Preconditions.checkArgument(num2 != null, "Column %s not in %s", c, this.columnList);
        return set(num.intValue(), num2.intValue(), v);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        super.putAll(table);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    @CheckForNull
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    @CanIgnoreReturnValue
    public V remove(@CheckForNull Object obj, @CheckForNull Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.Table
    public Map<C, V> row(R r) {
        Preconditions.checkNotNull(r);
        Integer num = this.rowKeyToIndex.get(r);
        return num == null ? Collections.emptyMap() : new Row(num.intValue());
    }

    public ImmutableList<R> rowKeyList() {
        return this.rowList;
    }

    @Override // com.google.common.collect.Table
    public Map<R, Map<C, V>> rowMap() {
        ArrayTable<R, C, V>.RowMap rowMap = this.rowMap;
        if (rowMap != null) {
            return rowMap;
        }
        ArrayTable<R, C, V>.RowMap rowMap2 = new RowMap();
        this.rowMap = rowMap2;
        return rowMap2;
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public V set(int i, int i2, @CheckForNull V v) {
        Preconditions.checkElementIndex(i, this.rowList.size());
        Preconditions.checkElementIndex(i2, this.columnList.size());
        V[][] vArr = this.array;
        V v2 = vArr[i][i2];
        vArr[i][i2] = v;
        return v2;
    }

    @Override // com.google.common.collect.Table
    public int size() {
        return this.columnList.size() * this.rowList.size();
    }

    @GwtIncompatible
    public V[][] toArray(Class<V> cls) {
        V[][] vArr = (V[][]) ((Object[][]) Array.newInstance((Class<?>) cls, this.rowList.size(), this.columnList.size()));
        for (int i = 0; i < this.rowList.size(); i++) {
            V[][] vArr2 = this.array;
            System.arraycopy(vArr2[i], 0, vArr[i], 0, vArr2[i].length);
        }
        return vArr;
    }

    @Override // com.google.common.collect.AbstractTable
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public Collection<V> values() {
        return super.values();
    }

    @Override // com.google.common.collect.AbstractTable
    public final Iterator<V> valuesIterator() {
        return new AbstractIndexedListIterator<V>(size()) { // from class: com.google.common.collect.ArrayTable.3
            @Override // com.google.common.collect.AbstractIndexedListIterator
            @CheckForNull
            public final V get(int i) {
                return (V) ArrayTable.this.getValue(i);
            }
        };
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Table<R, C, ? extends V> table) {
        return table instanceof ArrayTable ? new ArrayTable<>((ArrayTable) table) : new ArrayTable<>(table);
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public ImmutableSet<C> columnKeySet() {
        return this.columnKeyToIndex.keySet();
    }

    @Override // com.google.common.collect.AbstractTable, com.google.common.collect.Table
    public ImmutableSet<R> rowKeySet() {
        return this.rowKeyToIndex.keySet();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ArrayTable(Table<R, C, ? extends V> table) {
        this(table.rowKeySet(), table.columnKeySet());
        putAll(table);
    }

    private ArrayTable(ArrayTable<R, C, V> arrayTable) {
        ImmutableList<R> immutableList = arrayTable.rowList;
        this.rowList = immutableList;
        ImmutableList<C> immutableList2 = arrayTable.columnList;
        this.columnList = immutableList2;
        this.rowKeyToIndex = arrayTable.rowKeyToIndex;
        this.columnKeyToIndex = arrayTable.columnKeyToIndex;
        V[][] vArr = (V[][]) ((Object[][]) Array.newInstance((Class<?>) Object.class, immutableList.size(), immutableList2.size()));
        this.array = vArr;
        for (int i = 0; i < this.rowList.size(); i++) {
            V[][] vArr2 = arrayTable.array;
            System.arraycopy(vArr2[i], 0, vArr[i], 0, vArr2[i].length);
        }
    }
}
