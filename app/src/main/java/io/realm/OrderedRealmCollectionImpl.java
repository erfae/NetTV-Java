package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.internal.InvalidRow;
import io.realm.internal.OsResults;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.UncheckedRow;
import io.realm.internal.core.NativeRealmAny;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes2.dex */
public abstract class OrderedRealmCollectionImpl<E> extends AbstractList<E> implements OrderedRealmCollection<E> {
    private static final String NOT_SUPPORTED_MESSAGE = "This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.";
    public final BaseRealm baseRealm;

    @Nullable
    public final String className;

    @Nullable
    public final Class<E> classSpec;
    public final CollectionOperator<E> operator;
    public final OsResults osResults;

    public static class ByteValueOperator extends PrimitiveValueOperator<Byte> {
        public ByteValueOperator(BaseRealm baseRealm, OsResults osResults, @Nullable String str) {
            super(baseRealm, osResults, Byte.class, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Byte get(int i) {
            return Byte.valueOf(((Long) this.osResults.getValue(i)).byteValue());
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Byte getFromResults(int i, OsResults osResults) {
            Long l = (Long) osResults.getValue(i);
            if (l == null) {
                return null;
            }
            return Byte.valueOf(l.byteValue());
        }
    }

    public static abstract class CollectionOperator<T> {
        public final BaseRealm baseRealm;

        @Nullable
        public final String className;

        @Nullable
        public final Class<T> classSpec;
        public final OsResults osResults;

        public CollectionOperator(BaseRealm baseRealm, OsResults osResults, @Nullable Class<T> cls, @Nullable String str) {
            this.baseRealm = baseRealm;
            this.osResults = osResults;
            this.classSpec = cls;
            this.className = str;
        }

        public abstract T convertRowToObject(UncheckedRow uncheckedRow);

        public final T convertToObject(@Nullable UncheckedRow uncheckedRow, boolean z, @Nullable T t) {
            if (uncheckedRow != null) {
                return (T) this.baseRealm.get(this.classSpec, this.className, uncheckedRow);
            }
            if (z) {
                throw new IndexOutOfBoundsException("No results were found.");
            }
            return t;
        }

        @Nullable
        public abstract T firstImpl(boolean z, @Nullable T t);

        public abstract T get(int i);

        public abstract T getFromResults(int i, OsResults osResults);

        @Nullable
        public abstract T lastImpl(boolean z, @Nullable T t);
    }

    public static class IntegerValueOperator extends PrimitiveValueOperator<Integer> {
        public IntegerValueOperator(BaseRealm baseRealm, OsResults osResults, @Nullable String str) {
            super(baseRealm, osResults, Integer.class, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Integer get(int i) {
            return Integer.valueOf(((Long) this.osResults.getValue(i)).intValue());
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Integer getFromResults(int i, OsResults osResults) {
            Long l = (Long) osResults.getValue(i);
            if (l == null) {
                return null;
            }
            return Integer.valueOf(l.intValue());
        }
    }

    public static class ModelCollectionOperator<T> extends CollectionOperator<T> {
        public ModelCollectionOperator(BaseRealm baseRealm, OsResults osResults, @Nullable Class<T> cls, @Nullable String str) {
            super(baseRealm, osResults, cls, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T convertRowToObject(UncheckedRow uncheckedRow) {
            return (T) this.baseRealm.get(this.classSpec, this.className, uncheckedRow);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        @Nullable
        public T firstImpl(boolean z, @Nullable T t) {
            return convertToObject(this.osResults.firstUncheckedRow(), z, t);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T get(int i) {
            return (T) this.baseRealm.get(this.classSpec, this.className, this.osResults.getUncheckedRow(i));
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T getFromResults(int i, OsResults osResults) {
            return convertRowToObject(osResults.getUncheckedRow(i));
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        @Nullable
        public T lastImpl(boolean z, @Nullable T t) {
            return convertToObject(this.osResults.lastUncheckedRow(), z, t);
        }
    }

    public static class PrimitiveValueOperator<T> extends CollectionOperator<T> {
        public PrimitiveValueOperator(BaseRealm baseRealm, OsResults osResults, @Nullable Class<T> cls, @Nullable String str) {
            super(baseRealm, osResults, cls, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T convertRowToObject(UncheckedRow uncheckedRow) {
            throw new UnsupportedOperationException("Method 'convertRowToObject' cannot be used on primitive Realm collections.");
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        @Nullable
        public T firstImpl(boolean z, @Nullable T t) {
            return this.osResults.size() != 0 ? (T) this.osResults.getValue(0) : t;
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T get(int i) {
            return (T) this.osResults.getValue(i);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public T getFromResults(int i, OsResults osResults) {
            return (T) osResults.getValue(i);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.CollectionOperator
        @Nullable
        public T lastImpl(boolean z, @Nullable T t) {
            int size = (int) this.osResults.size();
            return size != 0 ? (T) this.osResults.getValue(size - 1) : t;
        }
    }

    public static class RealmAnyValueOperator extends PrimitiveValueOperator<RealmAny> {
        public RealmAnyValueOperator(BaseRealm baseRealm, OsResults osResults, @Nullable String str) {
            super(baseRealm, osResults, RealmAny.class, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public RealmAny get(int i) {
            return new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.baseRealm, (NativeRealmAny) this.osResults.getValue(i)));
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public RealmAny getFromResults(int i, OsResults osResults) {
            return new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.baseRealm, (NativeRealmAny) osResults.getValue(i)));
        }
    }

    public class RealmCollectionIterator extends OsResults.Iterator<E> {
        public RealmCollectionIterator() {
            super(OrderedRealmCollectionImpl.this.osResults);
        }

        @Override // io.realm.internal.OsResults.Iterator
        public final E getInternal(int i, OsResults osResults) {
            return OrderedRealmCollectionImpl.this.operator.getFromResults(i, osResults);
        }
    }

    public class RealmCollectionListIterator extends OsResults.ListIterator<E> {
        public RealmCollectionListIterator(int i) {
            super(OrderedRealmCollectionImpl.this.osResults, i);
        }

        @Override // io.realm.internal.OsResults.Iterator
        public final E getInternal(int i, OsResults osResults) {
            return OrderedRealmCollectionImpl.this.operator.getFromResults(i, osResults);
        }
    }

    public static class ShortValueOperator extends PrimitiveValueOperator<Short> {
        public ShortValueOperator(BaseRealm baseRealm, OsResults osResults, @Nullable String str) {
            super(baseRealm, osResults, Short.class, str);
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Short get(int i) {
            return Short.valueOf(((Long) this.osResults.getValue(i)).shortValue());
        }

        @Override // io.realm.OrderedRealmCollectionImpl.PrimitiveValueOperator, io.realm.OrderedRealmCollectionImpl.CollectionOperator
        public Short getFromResults(int i, OsResults osResults) {
            Long l = (Long) osResults.getValue(i);
            if (l == null) {
                return null;
            }
            return Short.valueOf(l.shortValue());
        }
    }

    public OrderedRealmCollectionImpl(BaseRealm baseRealm, OsResults osResults, Class<E> cls) {
        this(baseRealm, osResults, cls, null, getCollectionOperator(false, baseRealm, osResults, cls, null));
    }

    @Nullable
    private E firstImpl(boolean z, @Nullable E e) {
        return this.operator.firstImpl(z, e);
    }

    public static <T> CollectionOperator<T> getCollectionOperator(boolean z, BaseRealm baseRealm, OsResults osResults, @Nullable Class<T> cls, @Nullable String str) {
        if (!z) {
            return new ModelCollectionOperator(baseRealm, osResults, cls, str);
        }
        if (cls == Integer.class) {
            return new IntegerValueOperator(baseRealm, osResults, str);
        }
        if (cls == Short.class) {
            return new ShortValueOperator(baseRealm, osResults, str);
        }
        if (cls == Byte.class) {
            return new ByteValueOperator(baseRealm, osResults, str);
        }
        return cls == RealmAny.class ? new RealmAnyValueOperator(baseRealm, osResults, str) : new PrimitiveValueOperator(baseRealm, osResults, cls, str);
    }

    private long getColumnKeyForSort(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Non-empty field name required.");
        }
        if (str.contains(".")) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Aggregates on child object fields are not supported: ", str));
        }
        long columnKey = this.osResults.getTable().getColumnKey(str);
        if (columnKey >= 0) {
            return columnKey;
        }
        throw new IllegalArgumentException(String.format(Locale.US, "Field '%s' does not exist.", str));
    }

    @Nullable
    private E lastImpl(boolean z, @Nullable E e) {
        return this.operator.lastImpl(z, e);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean add(E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // io.realm.RealmCollection
    public double average(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateNumber(OsResults.Aggregate.AVERAGE, getColumnKeyForSort(str)).doubleValue();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, io.realm.RealmCollection
    public boolean contains(@Nullable Object obj) {
        if (!isLoaded() || ((obj instanceof RealmObjectProxy) && ((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm() == InvalidRow.INSTANCE)) {
            return false;
        }
        for (E e : this) {
            if ((e instanceof byte[]) && (obj instanceof byte[])) {
                if (Arrays.equals((byte[]) e, (byte[]) obj)) {
                    return true;
                }
            } else {
                if (e != null && e.equals(obj)) {
                    return true;
                }
                if (e == null && obj == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public final RealmResults<E> createLoadedResults(OsResults osResults) {
        String str = this.className;
        RealmResults<E> realmResults = str != null ? new RealmResults<>(this.baseRealm, osResults, str) : new RealmResults<>(this.baseRealm, osResults, this.classSpec);
        realmResults.load();
        return realmResults;
    }

    @Override // io.realm.OrderedRealmCollection
    public OrderedRealmCollectionSnapshot<E> createSnapshot() {
        String str = this.className;
        return str != null ? new OrderedRealmCollectionSnapshot<>(this.baseRealm, this.osResults, str) : new OrderedRealmCollectionSnapshot<>(this.baseRealm, this.osResults, this.classSpec);
    }

    @Override // io.realm.RealmCollection
    public boolean deleteAllFromRealm() {
        this.baseRealm.checkIfValid();
        if (size() <= 0) {
            return false;
        }
        this.osResults.clear();
        return true;
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteFirstFromRealm() {
        this.baseRealm.checkIfValidAndInTransaction();
        return this.osResults.deleteFirst();
    }

    @Override // io.realm.OrderedRealmCollection
    public void deleteFromRealm(int i) {
        this.baseRealm.checkIfValidAndInTransaction();
        this.osResults.delete(i);
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteLastFromRealm() {
        this.baseRealm.checkIfValidAndInTransaction();
        return this.osResults.deleteLast();
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E first() {
        return firstImpl(true, null);
    }

    @Override // java.util.AbstractList, java.util.List
    @Nullable
    public E get(int i) {
        this.baseRealm.checkIfValid();
        return this.operator.get(i);
    }

    public Realm getRealm() {
        this.baseRealm.checkIfValid();
        BaseRealm baseRealm = this.baseRealm;
        if (baseRealm instanceof Realm) {
            return (Realm) baseRealm;
        }
        throw new IllegalStateException("This method is only available for typed Realms");
    }

    @Override // io.realm.RealmCollection, io.realm.internal.ManageableObject
    public boolean isManaged() {
        return true;
    }

    @Override // io.realm.RealmCollection, io.realm.internal.ManageableObject
    public boolean isValid() {
        return this.osResults.isValid();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<E> iterator() {
        return new RealmCollectionIterator();
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E last() {
        return lastImpl(true, null);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator() {
        return new RealmCollectionListIterator(0);
    }

    @Override // io.realm.RealmCollection
    public Number max(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateNumber(OsResults.Aggregate.MAXIMUM, getColumnKeyForSort(str));
    }

    @Override // io.realm.RealmCollection
    @Nullable
    public Date maxDate(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateDate(OsResults.Aggregate.MAXIMUM, getColumnKeyForSort(str));
    }

    @Override // io.realm.RealmCollection
    public Number min(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateNumber(OsResults.Aggregate.MINIMUM, getColumnKeyForSort(str));
    }

    @Override // io.realm.RealmCollection
    public Date minDate(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateDate(OsResults.Aggregate.MINIMUM, getColumnKeyForSort(str));
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public E remove(int i) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public E set(int i, E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        if (!isLoaded()) {
            return 0;
        }
        long size = this.osResults.size();
        if (size > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) size;
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str) {
        return createLoadedResults(this.osResults.sort(this.baseRealm.getSchema().getKeyPathMapping(), str, Sort.ASCENDING));
    }

    @Override // io.realm.RealmCollection
    public Number sum(String str) {
        this.baseRealm.checkIfValid();
        return this.osResults.aggregateNumber(OsResults.Aggregate.SUM, getColumnKeyForSort(str));
    }

    public OrderedRealmCollectionImpl(BaseRealm baseRealm, OsResults osResults, Class<E> cls, CollectionOperator<E> collectionOperator) {
        this(baseRealm, osResults, cls, null, collectionOperator);
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public void add(int i, E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E first(@Nullable E e) {
        return firstImpl(false, e);
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E last(@Nullable E e) {
        return lastImpl(false, e);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator(int i) {
        return new RealmCollectionListIterator(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
    }

    public OrderedRealmCollectionImpl(BaseRealm baseRealm, OsResults osResults, String str) {
        this(baseRealm, osResults, null, str, getCollectionOperator(false, baseRealm, osResults, null, str));
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort) {
        return createLoadedResults(this.osResults.sort(this.baseRealm.getSchema().getKeyPathMapping(), str, sort));
    }

    public OrderedRealmCollectionImpl(BaseRealm baseRealm, OsResults osResults, String str, CollectionOperator<E> collectionOperator) {
        this(baseRealm, osResults, null, str, collectionOperator);
    }

    private OrderedRealmCollectionImpl(BaseRealm baseRealm, OsResults osResults, @Nullable Class<E> cls, @Nullable String str, CollectionOperator<E> collectionOperator) {
        this.baseRealm = baseRealm;
        this.osResults = osResults;
        this.classSpec = cls;
        this.className = str;
        this.operator = collectionOperator;
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String[] strArr, Sort[] sortArr) {
        return createLoadedResults(this.osResults.sort(this.baseRealm.getSchema().getKeyPathMapping(), strArr, sortArr));
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort, String str2, Sort sort2) {
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }
}
