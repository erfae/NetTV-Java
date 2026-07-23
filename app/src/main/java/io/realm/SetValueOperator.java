package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.internal.ObservableSet;
import io.realm.internal.ObserverPairList;
import io.realm.internal.OsSet;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
abstract class SetValueOperator<E> implements ObservableSet {
    public final BaseRealm baseRealm;
    public final String className;
    public final OsSet osSet;
    public final ObserverPairList<ObservableSet.SetObserverPair<E>> setObserverPairs;
    public final Class<E> valueClass;

    /* JADX INFO: renamed from: io.realm.SetValueOperator$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ int[] $SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation;

        static {
            int[] iArr = new int[OsSet.ExternalCollectionOperation.values().length];
            $SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation = iArr;
            try {
                iArr[OsSet.ExternalCollectionOperation.CONTAINS_ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation[OsSet.ExternalCollectionOperation.ADD_ALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation[OsSet.ExternalCollectionOperation.REMOVE_ALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation[OsSet.ExternalCollectionOperation.RETAIN_ALL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public SetValueOperator(BaseRealm baseRealm, OsSet osSet, Class<E> cls) {
        String simpleName = cls.getSimpleName();
        this.setObserverPairs = new ObserverPairList<>();
        this.baseRealm = baseRealm;
        this.osSet = osSet;
        this.valueClass = cls;
        this.className = simpleName;
    }

    private boolean isCollectionSameType(Collection<?> collection) {
        if (collection.isEmpty()) {
            return true;
        }
        for (Object obj : collection) {
            if (obj != null && !this.valueClass.isAssignableFrom(obj.getClass())) {
                return false;
            }
        }
        return true;
    }

    private boolean isObjectSameType(@Nullable Object obj) {
        if (obj != null) {
            return this.valueClass.isAssignableFrom(obj.getClass());
        }
        return true;
    }

    private boolean isUpperBoundCollectionSameType(Collection<? extends E> collection) {
        if (collection.isEmpty()) {
            return true;
        }
        for (E e : collection) {
            if (e != null && !this.valueClass.isAssignableFrom(e.getClass())) {
                return false;
            }
        }
        return true;
    }

    private static <T> SetIterator<T> iteratorFactory(Class<T> cls, OsSet osSet, BaseRealm baseRealm, String str) {
        if (cls == Boolean.class) {
            return new BooleanSetIterator(osSet, baseRealm);
        }
        if (cls == String.class) {
            return new StringSetIterator(osSet, baseRealm);
        }
        if (cls == Integer.class) {
            return new IntegerSetIterator(osSet, baseRealm);
        }
        if (cls == Long.class) {
            return new LongSetIterator(osSet, baseRealm);
        }
        if (cls == Short.class) {
            return new ShortSetIterator(osSet, baseRealm);
        }
        if (cls == Byte.class) {
            return new ByteSetIterator(osSet, baseRealm);
        }
        if (cls == Float.class) {
            return new FloatSetIterator(osSet, baseRealm);
        }
        if (cls == Double.class) {
            return new DoubleSetIterator(osSet, baseRealm);
        }
        if (cls == byte[].class) {
            return new BinarySetIterator(osSet, baseRealm);
        }
        if (cls == Date.class) {
            return new DateSetIterator(osSet, baseRealm);
        }
        if (cls == Decimal128.class) {
            return new Decimal128SetIterator(osSet, baseRealm);
        }
        if (cls == ObjectId.class) {
            return new ObjectIdSetIterator(osSet, baseRealm);
        }
        if (cls == UUID.class) {
            return new UUIDSetIterator(osSet, baseRealm);
        }
        if (cls == RealmAny.class) {
            return new RealmAnySetIterator(osSet, baseRealm);
        }
        if (cls == DynamicRealmObject.class) {
            return new DynamicSetIterator(osSet, baseRealm, str);
        }
        if (CollectionUtils.isClassForRealmModel(cls)) {
            return new RealmModelSetIterator(osSet, baseRealm, cls);
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Unknown class for iterator: ");
        sbM.append(cls.getSimpleName());
        throw new IllegalArgumentException(sbM.toString());
    }

    public abstract boolean add(@Nullable E e);

    public final boolean addAll(Collection<? extends E> collection) {
        if (isRealmCollection(collection)) {
            return funnelCollection(((RealmSet) collection).getOsSet(), OsSet.ExternalCollectionOperation.ADD_ALL);
        }
        if (isUpperBoundCollectionSameType(collection)) {
            return addAllInternal(collection);
        }
        throw new ClassCastException("Set contents and collection must be the same type when calling 'addAll'.");
    }

    public abstract boolean addAllInternal(Collection<? extends E> collection);

    public final void addChangeListener(RealmSet<E> realmSet, SetChangeListener<E> setChangeListener) {
        CollectionUtils.checkForAddRemoveListener(this.baseRealm, setChangeListener, true);
        if (this.setObserverPairs.isEmpty()) {
            this.osSet.startListening(this);
        }
        this.setObserverPairs.add(new ObservableSet.SetObserverPair(realmSet, setChangeListener));
    }

    public final boolean contains(@Nullable Object obj) {
        if (isObjectSameType(obj)) {
            return containsInternal(obj);
        }
        throw new ClassCastException("Set contents and object must be the same type when calling 'contains'.");
    }

    public final boolean containsAll(Collection<?> collection) {
        if (isRealmCollection(collection)) {
            return funnelCollection(((RealmSet) collection).getOsSet(), OsSet.ExternalCollectionOperation.CONTAINS_ALL);
        }
        if (isCollectionSameType(collection)) {
            return containsAllInternal(collection);
        }
        throw new ClassCastException("Set contents and collection must be the same type when calling 'containsAll'.");
    }

    public abstract boolean containsAllInternal(Collection<?> collection);

    public abstract boolean containsInternal(@Nullable Object obj);

    public final boolean funnelCollection(OsSet osSet, OsSet.ExternalCollectionOperation externalCollectionOperation) {
        if (this.osSet.getNativePtr() != osSet.getNativePtr()) {
            int i = AnonymousClass3.$SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation[externalCollectionOperation.ordinal()];
            if (i == 1) {
                return this.osSet.containsAll(osSet);
            }
            if (i == 2) {
                return this.osSet.union(osSet);
            }
            if (i == 3) {
                return this.osSet.asymmetricDifference(osSet);
            }
            if (i == 4) {
                return this.osSet.intersect(osSet);
            }
            throw new IllegalStateException("Unexpected value: " + externalCollectionOperation);
        }
        int i2 = AnonymousClass3.$SwitchMap$io$realm$internal$OsSet$ExternalCollectionOperation[externalCollectionOperation.ordinal()];
        if (i2 == 1) {
            return true;
        }
        if (i2 == 2) {
            return false;
        }
        if (i2 == 3) {
            this.osSet.clear();
            return true;
        }
        if (i2 == 4) {
            return false;
        }
        throw new IllegalStateException("Unexpected value: " + externalCollectionOperation);
    }

    public Class<E> getValueClass() {
        return this.valueClass;
    }

    public String getValueClassName() {
        return this.className;
    }

    public final boolean isRealmCollection(Collection<?> collection) {
        return (collection instanceof RealmSet) && ((RealmSet) collection).isManaged();
    }

    public final Iterator<E> iterator() {
        return iteratorFactory(this.valueClass, this.osSet, this.baseRealm, this.className);
    }

    @Override // io.realm.internal.ObservableSet
    public void notifyChangeListeners(long j) {
        this.osSet.notifyChangeListeners(j, this.setObserverPairs);
    }

    public final boolean remove(@Nullable Object obj) {
        if (isObjectSameType(obj)) {
            return removeInternal(obj);
        }
        throw new ClassCastException("Set contents and object must be the same type when calling 'remove'.");
    }

    public final boolean removeAll(Collection<?> collection) {
        if (isRealmCollection(collection)) {
            return funnelCollection(((RealmSet) collection).getOsSet(), OsSet.ExternalCollectionOperation.REMOVE_ALL);
        }
        if (isCollectionSameType(collection)) {
            return removeAllInternal(collection);
        }
        throw new ClassCastException("Set contents and collection must be the same type when calling 'removeAll'.");
    }

    public abstract boolean removeAllInternal(Collection<?> collection);

    public abstract boolean removeInternal(@Nullable Object obj);

    public final boolean retainAll(Collection<?> collection) {
        if (isRealmCollection(collection)) {
            return funnelCollection(((RealmSet) collection).getOsSet(), OsSet.ExternalCollectionOperation.RETAIN_ALL);
        }
        if (isCollectionSameType(collection)) {
            return retainAllInternal(collection);
        }
        throw new ClassCastException("Set contents and collection must be the same type when calling 'retainAll'.");
    }

    public abstract boolean retainAllInternal(Collection<?> collection);

    public RealmQuery<E> where() {
        throw new UnsupportedOperationException("This feature is available only when the element type is implementing RealmModel.");
    }

    public SetValueOperator(BaseRealm baseRealm, OsSet osSet, String str) {
        this.setObserverPairs = new ObserverPairList<>();
        this.baseRealm = baseRealm;
        this.osSet = osSet;
        this.valueClass = DynamicRealmObject.class;
        this.className = str;
    }
}
