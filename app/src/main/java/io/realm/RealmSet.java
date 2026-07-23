package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.internal.ManageableObject;
import io.realm.internal.OsSet;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public class RealmSet<E> implements Set<E>, ManageableObject, RealmCollection<E> {
    public final SetStrategy<E> setStrategy;

    public static class ManagedSetStrategy<E> extends SetStrategy<E> {
        private final SetValueOperator<E> setValueOperator;
        private Class<E> valueClass;

        public ManagedSetStrategy(SetValueOperator<E> setValueOperator, Class<E> cls) {
            super();
            this.setValueOperator = setValueOperator;
            this.valueClass = cls;
        }

        private <T> void checkValidArray(T[] tArr) {
            Objects.requireNonNull(tArr, "Cannot pass a null array when calling 'toArray'.");
            String simpleName = this.valueClass.getSimpleName();
            String simpleName2 = tArr.getClass().getComponentType().getSimpleName();
            if (simpleName.equals(simpleName2)) {
                return;
            }
            throw new ArrayStoreException("Array type must be of type '" + simpleName + "' but it was of type '" + simpleName2 + "'.");
        }

        private void checkValidCollection(Collection<?> collection) {
            Objects.requireNonNull(collection, "Collection must not be null.");
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(@Nullable E e) {
            return this.setValueOperator.add(e);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            checkValidCollection(collection);
            return this.setValueOperator.addAll(collection);
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final void addChangeListener(RealmSet<E> realmSet, final RealmChangeListener<RealmSet<E>> realmChangeListener) {
            SetValueOperator<E> setValueOperator = this.setValueOperator;
            Objects.requireNonNull(setValueOperator);
            setValueOperator.addChangeListener(realmSet, (SetChangeListener<E>) new SetChangeListener<Object>() { // from class: io.realm.SetValueOperator.1
                @Override // io.realm.SetChangeListener
                public void onChange(RealmSet<Object> realmSet2, SetChangeSet setChangeSet) {
                    realmChangeListener.onChange(realmSet2);
                }
            });
        }

        @Override // io.realm.RealmCollection
        public double average(String str) {
            return where().average(str);
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            this.setValueOperator.osSet.clear();
        }

        @Override // java.util.Set, java.util.Collection, io.realm.RealmCollection, java.util.List
        public boolean contains(@Nullable Object obj) {
            return this.setValueOperator.contains(obj);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            checkValidCollection(collection);
            return this.setValueOperator.containsAll(collection);
        }

        @Override // io.realm.RealmCollection
        public boolean deleteAllFromRealm() {
            this.setValueOperator.baseRealm.checkIfValid();
            if (Long.valueOf(this.setValueOperator.osSet.size()).intValue() == 0) {
                return false;
            }
            this.setValueOperator.osSet.deleteAll();
            return true;
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final OsSet getOsSet() {
            return this.setValueOperator.osSet;
        }

        @Override // io.realm.RealmSet.SetStrategy
        public Class<E> getValueClass() {
            return this.setValueOperator.getValueClass();
        }

        @Override // io.realm.RealmSet.SetStrategy
        public String getValueClassName() {
            return this.setValueOperator.getValueClassName();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return Long.valueOf(this.setValueOperator.osSet.size()).intValue() == 0;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isFrozen() {
            return this.setValueOperator.baseRealm.isFrozen();
        }

        @Override // io.realm.RealmCollection
        public boolean isLoaded() {
            return true;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isManaged() {
            return true;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isValid() {
            SetValueOperator<E> setValueOperator = this.setValueOperator;
            if (setValueOperator.baseRealm.isClosed()) {
                return false;
            }
            return setValueOperator.osSet.isValid();
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<E> iterator() {
            return this.setValueOperator.iterator();
        }

        @Override // io.realm.RealmCollection
        public boolean load() {
            return true;
        }

        @Override // io.realm.RealmCollection
        @Nullable
        public Number max(String str) {
            return where().max(str);
        }

        @Override // io.realm.RealmCollection
        @Nullable
        public Date maxDate(String str) {
            return where().maximumDate(str);
        }

        @Override // io.realm.RealmCollection
        @Nullable
        public Number min(String str) {
            return where().min(str);
        }

        @Override // io.realm.RealmCollection
        @Nullable
        public Date minDate(String str) {
            return where().minimumDate(str);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(@Nullable Object obj) {
            return this.setValueOperator.remove(obj);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            checkValidCollection(collection);
            return this.setValueOperator.removeAll(collection);
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final void removeAllChangeListeners() {
            SetValueOperator<E> setValueOperator = this.setValueOperator;
            CollectionUtils.checkForAddRemoveListener(setValueOperator.baseRealm, null, false);
            setValueOperator.setObserverPairs.clear();
            setValueOperator.osSet.stopListening();
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final void removeChangeListener(RealmSet<E> realmSet, final RealmChangeListener<RealmSet<E>> realmChangeListener) {
            SetValueOperator<E> setValueOperator = this.setValueOperator;
            Objects.requireNonNull(setValueOperator);
            setValueOperator.setObserverPairs.remove(realmSet, new SetChangeListener<Object>() { // from class: io.realm.SetValueOperator.2
                @Override // io.realm.SetChangeListener
                public void onChange(RealmSet<Object> realmSet2, SetChangeSet setChangeSet) {
                    realmChangeListener.onChange(realmSet2);
                }
            });
            if (setValueOperator.setObserverPairs.isEmpty()) {
                setValueOperator.osSet.stopListening();
            }
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            checkValidCollection(collection);
            return this.setValueOperator.retainAll(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return Long.valueOf(this.setValueOperator.osSet.size()).intValue();
        }

        @Override // io.realm.RealmCollection
        public Number sum(String str) {
            return where().sum(str);
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            Object[] objArr = new Object[size()];
            Iterator<E> it = iterator();
            int i = 0;
            while (it.hasNext()) {
                objArr[i] = it.next();
                i++;
            }
            return objArr;
        }

        @Override // io.realm.RealmCollection
        public RealmQuery<E> where() {
            return this.setValueOperator.where();
        }

        @Override // io.realm.RealmSet.SetStrategy, io.realm.internal.Freezable
        public RealmSet<E> freeze() {
            SetValueOperator<E> setValueOperator = this.setValueOperator;
            BaseRealm baseRealmFreeze = setValueOperator.baseRealm.freeze();
            return new RealmSet<>(baseRealmFreeze, setValueOperator.osSet.freeze(baseRealmFreeze.sharedRealm), setValueOperator.valueClass);
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final void addChangeListener(RealmSet<E> realmSet, SetChangeListener<E> setChangeListener) {
            this.setValueOperator.addChangeListener(realmSet, setChangeListener);
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            checkValidArray(tArr);
            long size = size();
            Object[] objArr = (((long) tArr.length) == size || ((long) tArr.length) > size) ? tArr : (T[]) ((Object[]) Array.newInstance((Class<?>) this.valueClass, (int) size));
            int i = 0;
            for (E e : this) {
                if (e == null) {
                    objArr[i] = null;
                } else {
                    objArr[i] = e;
                }
                i++;
            }
            if (tArr.length > size) {
                objArr[i] = null;
            }
            return (T[]) objArr;
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final void removeChangeListener(RealmSet<E> realmSet, SetChangeListener<E> setChangeListener) {
            SetValueOperator<E> setValueOperator = this.setValueOperator;
            setValueOperator.setObserverPairs.remove(realmSet, setChangeListener);
            if (setValueOperator.setObserverPairs.isEmpty()) {
                setValueOperator.osSet.stopListening();
            }
        }
    }

    public static abstract class SetStrategy<E> implements Set<E>, ManageableObject, RealmCollection<E> {
        private SetStrategy() {
        }

        public abstract void addChangeListener(RealmSet<E> realmSet, RealmChangeListener<RealmSet<E>> realmChangeListener);

        public abstract void addChangeListener(RealmSet<E> realmSet, SetChangeListener<E> setChangeListener);

        @Override // io.realm.internal.Freezable
        public abstract RealmSet<E> freeze();

        public abstract OsSet getOsSet();

        public abstract Class<E> getValueClass();

        public abstract String getValueClassName();

        public abstract void removeAllChangeListeners();

        public abstract void removeChangeListener(RealmSet<E> realmSet, RealmChangeListener<RealmSet<E>> realmChangeListener);

        public abstract void removeChangeListener(RealmSet<E> realmSet, SetChangeListener<E> setChangeListener);
    }

    public static class UnmanagedSetStrategy<E> extends SetStrategy<E> {
        private static final String ONLY_IN_MANAGED_MODE_MESSAGE = "This method is only available in managed mode.";
        private final Set<E> unmanagedSet;

        public UnmanagedSetStrategy() {
            super();
            this.unmanagedSet = new HashSet();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(@Nullable E e) {
            return this.unmanagedSet.add(e);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            return this.unmanagedSet.addAll(collection);
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final void addChangeListener(RealmSet<E> realmSet, RealmChangeListener<RealmSet<E>> realmChangeListener) {
            throw new UnsupportedOperationException("Unmanaged RealmSets do not support change listeners.");
        }

        @Override // io.realm.RealmCollection
        public double average(String str) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            this.unmanagedSet.clear();
        }

        @Override // java.util.Set, java.util.Collection, io.realm.RealmCollection, java.util.List
        public boolean contains(@Nullable Object obj) {
            return this.unmanagedSet.contains(obj);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return this.unmanagedSet.containsAll(collection);
        }

        @Override // io.realm.RealmCollection
        public boolean deleteAllFromRealm() {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final OsSet getOsSet() {
            throw new UnsupportedOperationException("Unmanaged RealmSets do not have a representation in native code.");
        }

        @Override // io.realm.RealmSet.SetStrategy
        public Class<E> getValueClass() {
            throw new UnsupportedOperationException("Unmanaged sets do not support retrieving the value class.");
        }

        @Override // io.realm.RealmSet.SetStrategy
        public String getValueClassName() {
            throw new UnsupportedOperationException("Unmanaged sets do not support retrieving the value class name.");
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return this.unmanagedSet.isEmpty();
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isFrozen() {
            return false;
        }

        @Override // io.realm.RealmCollection
        public boolean isLoaded() {
            return true;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isManaged() {
            return false;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isValid() {
            return true;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<E> iterator() {
            return this.unmanagedSet.iterator();
        }

        @Override // io.realm.RealmCollection
        public boolean load() {
            return true;
        }

        @Override // io.realm.RealmCollection
        @Nullable
        public Number max(String str) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }

        @Override // io.realm.RealmCollection
        @Nullable
        public Date maxDate(String str) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }

        @Override // io.realm.RealmCollection
        @Nullable
        public Number min(String str) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }

        @Override // io.realm.RealmCollection
        @Nullable
        public Date minDate(String str) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(@Nullable Object obj) {
            return this.unmanagedSet.remove(obj);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            return this.unmanagedSet.removeAll(collection);
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final void removeAllChangeListeners() {
            throw new UnsupportedOperationException("Cannot remove change listeners because unmanaged RealmSets do not support change listeners.");
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final void removeChangeListener(RealmSet<E> realmSet, RealmChangeListener<RealmSet<E>> realmChangeListener) {
            throw new UnsupportedOperationException("Cannot remove change listener because unmanaged RealmSets do not support change listeners.");
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            return this.unmanagedSet.retainAll(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return this.unmanagedSet.size();
        }

        @Override // io.realm.RealmCollection
        public Number sum(String str) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            return this.unmanagedSet.toArray();
        }

        @Override // io.realm.RealmCollection
        public RealmQuery<E> where() {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final void addChangeListener(RealmSet<E> realmSet, SetChangeListener<E> setChangeListener) {
            throw new UnsupportedOperationException("Unmanaged RealmSets do not support change listeners.");
        }

        @Override // io.realm.RealmSet.SetStrategy, io.realm.internal.Freezable
        public RealmSet<E> freeze() {
            throw new UnsupportedOperationException("Unmanaged RealmSets cannot be frozen.");
        }

        @Override // io.realm.RealmSet.SetStrategy
        public final void removeChangeListener(RealmSet<E> realmSet, SetChangeListener<E> setChangeListener) {
            throw new UnsupportedOperationException("Cannot remove change listener because unmanaged RealmSets do not support change listeners.");
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) this.unmanagedSet.toArray(tArr);
        }

        public UnmanagedSetStrategy(Collection<E> collection) {
            this();
            this.unmanagedSet.addAll(collection);
        }
    }

    public RealmSet() {
        this.setStrategy = new UnmanagedSetStrategy();
    }

    private static <T> ManagedSetStrategy<T> getStrategy(BaseRealm baseRealm, OsSet osSet, Class<T> cls) {
        SetValueOperator numberOperator;
        if (CollectionUtils.isClassForRealmModel(cls)) {
            return new ManagedSetStrategy<>(new RealmModelSetOperator(baseRealm, osSet, cls), cls);
        }
        if (cls == Boolean.class) {
            numberOperator = new BooleanOperator(baseRealm, osSet);
        } else if (cls == String.class) {
            numberOperator = new StringOperator(baseRealm, osSet);
        } else if (cls == Integer.class) {
            numberOperator = new IntegerOperator(baseRealm, osSet);
        } else if (cls == Long.class) {
            numberOperator = new LongOperator(baseRealm, osSet);
        } else if (cls == Short.class) {
            numberOperator = new ShortOperator(baseRealm, osSet);
        } else if (cls == Byte.class) {
            numberOperator = new ByteOperator(baseRealm, osSet);
        } else if (cls == Float.class) {
            numberOperator = new FloatOperator(baseRealm, osSet);
        } else if (cls == Double.class) {
            numberOperator = new DoubleOperator(baseRealm, osSet);
        } else if (cls == byte[].class) {
            numberOperator = new BinaryOperator(baseRealm, osSet);
        } else if (cls == Date.class) {
            numberOperator = new DateOperator(baseRealm, osSet, Date.class);
        } else if (cls == Decimal128.class) {
            numberOperator = new Decimal128Operator(baseRealm, osSet, Decimal128.class);
        } else if (cls == ObjectId.class) {
            numberOperator = new ObjectIdOperator(baseRealm, osSet, ObjectId.class);
        } else if (cls == UUID.class) {
            numberOperator = new UUIDOperator(baseRealm, osSet, UUID.class);
        } else if (cls == RealmAny.class) {
            numberOperator = new RealmAnySetOperator(baseRealm, osSet);
        } else {
            if (cls != Number.class) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("getStrategy: missing class '");
                sbM.append(cls.getSimpleName());
                sbM.append("'");
                throw new UnsupportedOperationException(sbM.toString());
            }
            numberOperator = new NumberOperator(baseRealm, osSet, Number.class);
        }
        return new ManagedSetStrategy<>(numberOperator, cls);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(@Nullable E e) {
        return this.setStrategy.add(e);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        return this.setStrategy.addAll(collection);
    }

    public void addChangeListener(RealmChangeListener<RealmSet<E>> realmChangeListener) {
        this.setStrategy.addChangeListener(this, realmChangeListener);
    }

    @Override // io.realm.RealmCollection
    public double average(String str) {
        return this.setStrategy.average(str);
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        this.setStrategy.clear();
    }

    @Override // java.util.Set, java.util.Collection, io.realm.RealmCollection, java.util.List
    public boolean contains(@Nullable Object obj) {
        return this.setStrategy.contains(obj);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.setStrategy.containsAll(collection);
    }

    @Override // io.realm.RealmCollection
    public boolean deleteAllFromRealm() {
        return this.setStrategy.deleteAllFromRealm();
    }

    public final OsSet getOsSet() {
        return this.setStrategy.getOsSet();
    }

    public Class<E> getValueClass() {
        return this.setStrategy.getValueClass();
    }

    public String getValueClassName() {
        return this.setStrategy.getValueClassName();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.setStrategy.isEmpty();
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isFrozen() {
        return this.setStrategy.isFrozen();
    }

    @Override // io.realm.RealmCollection
    public boolean isLoaded() {
        return true;
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isManaged() {
        return this.setStrategy.isManaged();
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isValid() {
        return this.setStrategy.isValid();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return this.setStrategy.iterator();
    }

    @Override // io.realm.RealmCollection
    public boolean load() {
        return true;
    }

    @Override // io.realm.RealmCollection
    @Nullable
    public Number max(String str) {
        return this.setStrategy.max(str);
    }

    @Override // io.realm.RealmCollection
    @Nullable
    public Date maxDate(String str) {
        return this.setStrategy.maxDate(str);
    }

    @Override // io.realm.RealmCollection
    @Nullable
    public Number min(String str) {
        return this.setStrategy.min(str);
    }

    @Override // io.realm.RealmCollection
    @Nullable
    public Date minDate(String str) {
        return this.setStrategy.minDate(str);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(@Nullable Object obj) {
        return this.setStrategy.remove(obj);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return this.setStrategy.removeAll(collection);
    }

    public void removeAllChangeListeners() {
        this.setStrategy.removeAllChangeListeners();
    }

    public void removeChangeListener(RealmChangeListener<RealmSet<E>> realmChangeListener) {
        this.setStrategy.removeChangeListener(this, realmChangeListener);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return this.setStrategy.retainAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public int size() {
        return this.setStrategy.size();
    }

    @Override // io.realm.RealmCollection
    public Number sum(String str) {
        return this.setStrategy.sum(str);
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return this.setStrategy.toArray();
    }

    @Override // io.realm.RealmCollection
    public RealmQuery<E> where() {
        return this.setStrategy.where();
    }

    public void addChangeListener(SetChangeListener<E> setChangeListener) {
        this.setStrategy.addChangeListener(this, setChangeListener);
    }

    @Override // io.realm.internal.Freezable
    public RealmSet<E> freeze() {
        return this.setStrategy.freeze();
    }

    public void removeChangeListener(SetChangeListener<E> setChangeListener) {
        this.setStrategy.removeChangeListener(this, setChangeListener);
    }

    @Override // java.util.Set, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) this.setStrategy.toArray(tArr);
    }

    public RealmSet(Collection<E> collection) {
        this.setStrategy = new UnmanagedSetStrategy(collection);
    }

    public RealmSet(BaseRealm baseRealm, OsSet osSet, Class<E> cls) {
        this.setStrategy = getStrategy(baseRealm, osSet, cls);
    }

    public RealmSet(BaseRealm baseRealm, OsSet osSet, String str) {
        this.setStrategy = getStrategy(baseRealm, osSet, str);
    }

    private static <T> ManagedSetStrategy<T> getStrategy(BaseRealm baseRealm, OsSet osSet, String str) {
        SetValueOperator dynamicSetOperator;
        if (str.equals(Boolean.class.getCanonicalName())) {
            dynamicSetOperator = new BooleanOperator(baseRealm, osSet);
        } else if (str.equals(String.class.getCanonicalName())) {
            dynamicSetOperator = new StringOperator(baseRealm, osSet);
        } else if (str.equals(Integer.class.getCanonicalName())) {
            dynamicSetOperator = new IntegerOperator(baseRealm, osSet);
        } else if (str.equals(Long.class.getCanonicalName())) {
            dynamicSetOperator = new LongOperator(baseRealm, osSet);
        } else if (str.equals(Short.class.getCanonicalName())) {
            dynamicSetOperator = new ShortOperator(baseRealm, osSet);
        } else if (str.equals(Byte.class.getCanonicalName())) {
            dynamicSetOperator = new ByteOperator(baseRealm, osSet);
        } else if (str.equals(Float.class.getCanonicalName())) {
            dynamicSetOperator = new FloatOperator(baseRealm, osSet);
        } else if (str.equals(Double.class.getCanonicalName())) {
            dynamicSetOperator = new DoubleOperator(baseRealm, osSet);
        } else if (str.equals(byte[].class.getCanonicalName())) {
            dynamicSetOperator = new BinaryOperator(baseRealm, osSet);
        } else if (str.equals(Date.class.getCanonicalName())) {
            dynamicSetOperator = new DateOperator(baseRealm, osSet, Date.class);
        } else if (str.equals(Decimal128.class.getCanonicalName())) {
            dynamicSetOperator = new Decimal128Operator(baseRealm, osSet, Decimal128.class);
        } else if (str.equals(ObjectId.class.getCanonicalName())) {
            dynamicSetOperator = new ObjectIdOperator(baseRealm, osSet, ObjectId.class);
        } else if (str.equals(UUID.class.getCanonicalName())) {
            dynamicSetOperator = new UUIDOperator(baseRealm, osSet, UUID.class);
        } else if (str.equals(RealmAny.class.getCanonicalName())) {
            dynamicSetOperator = new RealmAnySetOperator(baseRealm, osSet);
        } else {
            dynamicSetOperator = new DynamicSetOperator(baseRealm, osSet, str);
        }
        return new ManagedSetStrategy<>(dynamicSetOperator, dynamicSetOperator.getValueClass());
    }
}
