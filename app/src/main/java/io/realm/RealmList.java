package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.emoji2.text.flatbuffer.Utf8Safe$UnpairedSurrogateException$$ExternalSyntheticOutline0;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.internal.InvalidRow;
import io.realm.internal.OsList;
import io.realm.internal.OsResults;
import io.realm.internal.RealmObjectProxy;
import io.realm.rx.CollectionChange;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public class RealmList<E> extends AbstractList<E> implements OrderedRealmCollection<E> {
    private static final String ONLY_IN_MANAGED_MODE_MESSAGE = "This method is only available in managed mode.";
    private static final String REMOVE_OUTSIDE_TRANSACTION_ERROR = "Objects can only be removed from inside a write transaction.";
    public final BaseRealm baseRealm;

    @Nullable
    public String className;

    @Nullable
    public Class<E> clazz;
    private final ManagedListOperator<E> osListOperator;
    private List<E> unmanagedList;

    public class RealmItr implements Iterator<E> {
        public int cursor;
        public int expectedModCount;
        public int lastRet;

        private RealmItr() {
            this.cursor = 0;
            this.lastRet = -1;
            this.expectedModCount = ((AbstractList) RealmList.this).modCount;
        }

        public final void checkConcurrentModification() {
            if (((AbstractList) RealmList.this).modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            RealmList.this.checkValidRealm();
            checkConcurrentModification();
            return this.cursor != RealmList.this.size();
        }

        @Override // java.util.Iterator
        @Nullable
        public E next() {
            RealmList.this.checkValidRealm();
            checkConcurrentModification();
            int i = this.cursor;
            try {
                E e = (E) RealmList.this.get(i);
                this.lastRet = i;
                this.cursor = i + 1;
                return e;
            } catch (IndexOutOfBoundsException unused) {
                checkConcurrentModification();
                StringBuilder sbM24m = Insets$$ExternalSyntheticOutline0.m24m("Cannot access index ", i, " when size is ");
                sbM24m.append(RealmList.this.size());
                sbM24m.append(". Remember to check hasNext() before using next().");
                throw new NoSuchElementException(sbM24m.toString());
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            RealmList.this.checkValidRealm();
            if (this.lastRet < 0) {
                throw new IllegalStateException("Cannot call remove() twice. Must call next() in between.");
            }
            checkConcurrentModification();
            try {
                RealmList.this.remove(this.lastRet);
                int i = this.lastRet;
                int i2 = this.cursor;
                if (i < i2) {
                    this.cursor = i2 - 1;
                }
                this.lastRet = -1;
                this.expectedModCount = ((AbstractList) RealmList.this).modCount;
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public class RealmListItr extends RealmList<E>.RealmItr implements ListIterator<E> {
        public RealmListItr(int i) {
            super();
            if (i >= 0 && i <= RealmList.this.size()) {
                this.cursor = i;
                return;
            }
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Starting location must be a valid index: [0, ");
            sbM.append(RealmList.this.size() - 1);
            sbM.append("]. Index was ");
            sbM.append(i);
            throw new IndexOutOfBoundsException(sbM.toString());
        }

        @Override // java.util.ListIterator
        public void add(@Nullable E e) {
            RealmList.this.baseRealm.checkIfValid();
            checkConcurrentModification();
            try {
                int i = this.cursor;
                RealmList.this.add(i, e);
                this.lastRet = -1;
                this.cursor = i + 1;
                this.expectedModCount = ((AbstractList) RealmList.this).modCount;
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.cursor != 0;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.cursor;
        }

        @Override // java.util.ListIterator
        @Nullable
        public E previous() {
            checkConcurrentModification();
            int i = this.cursor - 1;
            try {
                E e = (E) RealmList.this.get(i);
                this.cursor = i;
                this.lastRet = i;
                return e;
            } catch (IndexOutOfBoundsException unused) {
                checkConcurrentModification();
                throw new NoSuchElementException(Insets$$ExternalSyntheticOutline0.m("Cannot access index less than zero. This was ", i, ". Remember to check hasPrevious() before using previous()."));
            }
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.cursor - 1;
        }

        @Override // java.util.ListIterator
        public void set(@Nullable E e) {
            RealmList.this.baseRealm.checkIfValid();
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            checkConcurrentModification();
            try {
                RealmList.this.set(this.lastRet, e);
                this.expectedModCount = ((AbstractList) RealmList.this).modCount;
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public RealmList() {
        this.baseRealm = null;
        this.osListOperator = null;
        this.unmanagedList = new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkValidRealm() {
        this.baseRealm.checkIfValid();
    }

    @Nullable
    private E firstImpl(boolean z, @Nullable E e) {
        if (isManaged()) {
            checkValidRealm();
            if (!this.osListOperator.isEmpty()) {
                return get(0);
            }
        } else {
            List<E> list = this.unmanagedList;
            if (list != null && !list.isEmpty()) {
                return this.unmanagedList.get(0);
            }
        }
        if (z) {
            throw new IndexOutOfBoundsException("The list is empty.");
        }
        return e;
    }

    private ManagedListOperator<E> getOperator(BaseRealm baseRealm, OsList osList, @Nullable Class<E> cls, @Nullable String str) {
        if (cls == null || isClassForRealmModel(cls)) {
            return new RealmModelListOperator(baseRealm, osList, cls, str);
        }
        if (cls == String.class) {
            return new StringListOperator(baseRealm, osList, cls);
        }
        if (cls == Long.class || cls == Integer.class || cls == Short.class || cls == Byte.class) {
            return new LongListOperator(baseRealm, osList, cls);
        }
        if (cls == Boolean.class) {
            return new BooleanListOperator(baseRealm, osList, cls);
        }
        if (cls == byte[].class) {
            return new BinaryListOperator(baseRealm, osList, cls);
        }
        if (cls == Double.class) {
            return new DoubleListOperator(baseRealm, osList, cls);
        }
        if (cls == Float.class) {
            return new FloatListOperator(baseRealm, osList, cls);
        }
        if (cls == Date.class) {
            return new DateListOperator(baseRealm, osList, cls);
        }
        if (cls == Decimal128.class) {
            return new Decimal128ListOperator(baseRealm, osList, cls);
        }
        if (cls == ObjectId.class) {
            return new ObjectIdListOperator(baseRealm, osList, cls);
        }
        if (cls == UUID.class) {
            return new UUIDListOperator(baseRealm, osList, cls);
        }
        if (cls == RealmAny.class) {
            return new RealmAnyListOperator(baseRealm, osList, cls);
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Unexpected value class: ");
        sbM.append(cls.getName());
        throw new IllegalArgumentException(sbM.toString());
    }

    private boolean isAttached() {
        ManagedListOperator<E> managedListOperator = this.osListOperator;
        return managedListOperator != null && managedListOperator.isValid();
    }

    private static boolean isClassForRealmModel(Class<?> cls) {
        return RealmModel.class.isAssignableFrom(cls);
    }

    @Nullable
    private E lastImpl(boolean z, @Nullable E e) {
        if (isManaged()) {
            checkValidRealm();
            if (!this.osListOperator.isEmpty()) {
                return get(this.osListOperator.size() - 1);
            }
        } else {
            List<E> list = this.unmanagedList;
            if (list != null && !list.isEmpty()) {
                List<E> list2 = this.unmanagedList;
                return list2.get(list2.size() - 1);
            }
        }
        if (z) {
            throw new IndexOutOfBoundsException("The list is empty.");
        }
        return e;
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i, @Nullable E e) {
        if (isManaged()) {
            checkValidRealm();
            this.osListOperator.insert(i, e);
        } else {
            this.unmanagedList.add(i, e);
        }
        ((AbstractList) this).modCount++;
    }

    public void addChangeListener(OrderedRealmCollectionChangeListener<RealmList<E>> orderedRealmCollectionChangeListener) {
        CollectionUtils.checkForAddRemoveListener(this.baseRealm, orderedRealmCollectionChangeListener, true);
        this.osListOperator.getOsList().addListener(this, orderedRealmCollectionChangeListener);
    }

    public Observable<CollectionChange<RealmList<E>>> asChangesetObservable() {
        BaseRealm baseRealm = this.baseRealm;
        if (baseRealm instanceof Realm) {
            return baseRealm.configuration.getRxFactory().changesetsFrom((Realm) this.baseRealm, this);
        }
        if (baseRealm instanceof DynamicRealm) {
            return baseRealm.configuration.getRxFactory().changesetsFrom((DynamicRealm) baseRealm, this);
        }
        throw new UnsupportedOperationException(this.baseRealm.getClass() + " does not support RxJava2.");
    }

    public Flowable<RealmList<E>> asFlowable() {
        BaseRealm baseRealm = this.baseRealm;
        if (baseRealm instanceof Realm) {
            return baseRealm.configuration.getRxFactory().from((Realm) this.baseRealm, this);
        }
        if (baseRealm instanceof DynamicRealm) {
            return baseRealm.configuration.getRxFactory().from((DynamicRealm) this.baseRealm, this);
        }
        throw new UnsupportedOperationException(this.baseRealm.getClass() + " does not support RxJava2.");
    }

    @Override // io.realm.RealmCollection
    public double average(String str) {
        return where().average(str);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        if (isManaged()) {
            checkValidRealm();
            this.osListOperator.osList.removeAll();
        } else {
            this.unmanagedList.clear();
        }
        ((AbstractList) this).modCount++;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, io.realm.RealmCollection
    public boolean contains(@Nullable Object obj) {
        if (!isManaged()) {
            return this.unmanagedList.contains(obj);
        }
        this.baseRealm.checkIfValid();
        if ((obj instanceof RealmObjectProxy) && ((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm() == InvalidRow.INSTANCE) {
            return false;
        }
        return super.contains(obj);
    }

    @Override // io.realm.OrderedRealmCollection
    public OrderedRealmCollectionSnapshot<E> createSnapshot() {
        if (!isManaged()) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }
        checkValidRealm();
        if (!this.osListOperator.forRealmModel()) {
            throw new UnsupportedOperationException("This feature is available only when the element type is implementing RealmModel.");
        }
        if (this.className != null) {
            BaseRealm baseRealm = this.baseRealm;
            return new OrderedRealmCollectionSnapshot<>(baseRealm, OsResults.createFromQuery(baseRealm.sharedRealm, this.osListOperator.getOsList().getQuery()), this.className);
        }
        BaseRealm baseRealm2 = this.baseRealm;
        return new OrderedRealmCollectionSnapshot<>(baseRealm2, OsResults.createFromQuery(baseRealm2.sharedRealm, this.osListOperator.getOsList().getQuery()), this.clazz);
    }

    @Override // io.realm.RealmCollection
    public boolean deleteAllFromRealm() {
        if (!isManaged()) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }
        checkValidRealm();
        if (this.osListOperator.isEmpty()) {
            return false;
        }
        this.osListOperator.osList.deleteAll();
        ((AbstractList) this).modCount++;
        return true;
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteFirstFromRealm() {
        if (!isManaged()) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }
        if (this.osListOperator.isEmpty()) {
            return false;
        }
        deleteFromRealm(0);
        ((AbstractList) this).modCount++;
        return true;
    }

    @Override // io.realm.OrderedRealmCollection
    public void deleteFromRealm(int i) {
        if (!isManaged()) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }
        checkValidRealm();
        this.osListOperator.osList.delete(i);
        ((AbstractList) this).modCount++;
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteLastFromRealm() {
        if (!isManaged()) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }
        if (this.osListOperator.isEmpty()) {
            return false;
        }
        OsList osList = this.osListOperator.osList;
        osList.delete(osList.size() - 1);
        ((AbstractList) this).modCount++;
        return true;
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E first() {
        return firstImpl(true, null);
    }

    @Override // java.util.AbstractList, java.util.List
    @Nullable
    public E get(int i) {
        if (!isManaged()) {
            return this.unmanagedList.get(i);
        }
        checkValidRealm();
        return this.osListOperator.get(i);
    }

    public final OsList getOsList() {
        return this.osListOperator.getOsList();
    }

    public Realm getRealm() {
        BaseRealm baseRealm = this.baseRealm;
        if (baseRealm == null) {
            return null;
        }
        baseRealm.checkIfValid();
        BaseRealm baseRealm2 = this.baseRealm;
        if (baseRealm2 instanceof Realm) {
            return (Realm) baseRealm2;
        }
        throw new IllegalStateException("This method is only available for typed Realms");
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isFrozen() {
        BaseRealm baseRealm = this.baseRealm;
        return baseRealm != null && baseRealm.isFrozen();
    }

    @Override // io.realm.RealmCollection
    public boolean isLoaded() {
        return true;
    }

    @Override // io.realm.RealmCollection, io.realm.internal.ManageableObject
    public boolean isManaged() {
        return this.baseRealm != null;
    }

    @Override // io.realm.RealmCollection, io.realm.internal.ManageableObject
    public boolean isValid() {
        BaseRealm baseRealm = this.baseRealm;
        if (baseRealm == null) {
            return true;
        }
        if (baseRealm.isClosed()) {
            return false;
        }
        return isAttached();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    @Nonnull
    public Iterator<E> iterator() {
        return isManaged() ? new RealmItr() : super.iterator();
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E last() {
        return lastImpl(true, null);
    }

    @Override // java.util.AbstractList, java.util.List
    @Nonnull
    public ListIterator<E> listIterator() {
        return listIterator(0);
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

    public void move(int i, int i2) {
        if (isManaged()) {
            checkValidRealm();
            this.osListOperator.osList.move(i, i2);
            return;
        }
        int size = this.unmanagedList.size();
        if (i < 0 || size <= i) {
            throw new IndexOutOfBoundsException(Utf8Safe$UnpairedSurrogateException$$ExternalSyntheticOutline0.m("Invalid index ", i, ", size is ", size));
        }
        if (i2 < 0 || size <= i2) {
            throw new IndexOutOfBoundsException(Utf8Safe$UnpairedSurrogateException$$ExternalSyntheticOutline0.m("Invalid index ", i2, ", size is ", size));
        }
        this.unmanagedList.add(i2, this.unmanagedList.remove(i));
    }

    @Override // java.util.AbstractList, java.util.List
    public E remove(int i) {
        E eRemove;
        if (isManaged()) {
            checkValidRealm();
            eRemove = get(i);
            this.osListOperator.osList.remove(i);
        } else {
            eRemove = this.unmanagedList.remove(i);
        }
        ((AbstractList) this).modCount++;
        return eRemove;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean removeAll(Collection<?> collection) {
        if (!isManaged() || this.baseRealm.isInTransaction()) {
            return super.removeAll(collection);
        }
        throw new IllegalStateException(REMOVE_OUTSIDE_TRANSACTION_ERROR);
    }

    public void removeAllChangeListeners() {
        CollectionUtils.checkForAddRemoveListener(this.baseRealm, null, false);
        this.osListOperator.getOsList().removeAllListeners();
    }

    public void removeChangeListener(OrderedRealmCollectionChangeListener<RealmList<E>> orderedRealmCollectionChangeListener) {
        CollectionUtils.checkForAddRemoveListener(this.baseRealm, orderedRealmCollectionChangeListener, true);
        this.osListOperator.getOsList().removeListener(this, orderedRealmCollectionChangeListener);
    }

    @Override // java.util.AbstractList, java.util.List
    public E set(int i, @Nullable E e) {
        if (!isManaged()) {
            return this.unmanagedList.set(i, e);
        }
        checkValidRealm();
        return this.osListOperator.set(i, e);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        if (!isManaged()) {
            return this.unmanagedList.size();
        }
        checkValidRealm();
        return this.osListOperator.size();
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str) {
        return sort(str, Sort.ASCENDING);
    }

    @Override // io.realm.RealmCollection
    public Number sum(String str) {
        return where().sum(str);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (isManaged()) {
            sb.append("RealmList<");
            String str = this.className;
            if (str != null) {
                sb.append(str);
            } else if (isClassForRealmModel(this.clazz)) {
                sb.append(this.baseRealm.getSchema().getSchemaForClass((Class<? extends RealmModel>) this.clazz).getClassName());
            } else {
                Class<E> cls = this.clazz;
                if (cls == byte[].class) {
                    sb.append(cls.getSimpleName());
                } else {
                    sb.append(cls.getName());
                }
            }
            sb.append(">@[");
            if (!isAttached()) {
                sb.append("invalid");
            } else if (isClassForRealmModel(this.clazz)) {
                while (i < size()) {
                    sb.append(((RealmObjectProxy) get(i)).realmGet$proxyState().getRow$realm().getObjectKey());
                    sb.append(",");
                    i++;
                }
                if (size() > 0) {
                    sb.setLength(sb.length() - 1);
                }
            } else {
                while (i < size()) {
                    Object obj = get(i);
                    if (obj instanceof byte[]) {
                        sb.append("byte[");
                        sb.append(((byte[]) obj).length);
                        sb.append("]");
                    } else {
                        sb.append(obj);
                    }
                    sb.append(",");
                    i++;
                }
                if (size() > 0) {
                    sb.setLength(sb.length() - 1);
                }
            }
            sb.append("]");
        } else {
            sb.append("RealmList<?>@[");
            int size = size();
            while (i < size) {
                Object obj2 = get(i);
                if (obj2 instanceof RealmModel) {
                    sb.append(System.identityHashCode(obj2));
                } else if (obj2 instanceof byte[]) {
                    sb.append("byte[");
                    sb.append(((byte[]) obj2).length);
                    sb.append("]");
                } else {
                    sb.append(obj2);
                }
                sb.append(",");
                i++;
            }
            if (size() > 0) {
                sb.setLength(sb.length() - 1);
            }
            sb.append("]");
        }
        return sb.toString();
    }

    @Override // io.realm.RealmCollection
    public RealmQuery<E> where() {
        if (!isManaged()) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }
        checkValidRealm();
        if (this.osListOperator.forRealmModel()) {
            return RealmQuery.createQueryFromList(this);
        }
        throw new UnsupportedOperationException("This feature is available only when the element type is implementing RealmModel.");
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E first(@Nullable E e) {
        return firstImpl(false, e);
    }

    @Override // io.realm.internal.Freezable
    public RealmList<E> freeze() {
        if (!isManaged()) {
            throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
        }
        if (!isValid()) {
            throw new IllegalStateException("Only valid, managed RealmLists can be frozen.");
        }
        BaseRealm baseRealmFreeze = this.baseRealm.freeze();
        OsList osListFreeze = getOsList().freeze(baseRealmFreeze.sharedRealm);
        String str = this.className;
        return str != null ? new RealmList<>(str, osListFreeze, baseRealmFreeze) : new RealmList<>(this.clazz, osListFreeze, baseRealmFreeze);
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E last(@Nullable E e) {
        return lastImpl(false, e);
    }

    @Override // java.util.AbstractList, java.util.List
    @Nonnull
    public ListIterator<E> listIterator(int i) {
        return isManaged() ? new RealmListItr(i) : super.listIterator(i);
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort) {
        if (isManaged()) {
            return where().sort(str, sort).findAll();
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    public void addChangeListener(RealmChangeListener<RealmList<E>> realmChangeListener) {
        CollectionUtils.checkForAddRemoveListener(this.baseRealm, realmChangeListener, true);
        this.osListOperator.getOsList().addListener(this, realmChangeListener);
    }

    public void removeChangeListener(RealmChangeListener<RealmList<E>> realmChangeListener) {
        CollectionUtils.checkForAddRemoveListener(this.baseRealm, realmChangeListener, true);
        this.osListOperator.getOsList().removeListener(this, realmChangeListener);
    }

    public RealmList(E... eArr) {
        if (eArr != null) {
            this.baseRealm = null;
            this.osListOperator = null;
            ArrayList arrayList = new ArrayList(eArr.length);
            this.unmanagedList = arrayList;
            Collections.addAll(arrayList, eArr);
            return;
        }
        throw new IllegalArgumentException("The objects argument cannot be null");
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort, String str2, Sort sort2) {
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(@Nullable E e) {
        if (isManaged()) {
            checkValidRealm();
            this.osListOperator.append(e);
        } else {
            this.unmanagedList.add(e);
        }
        ((AbstractList) this).modCount++;
        return true;
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String[] strArr, Sort[] sortArr) {
        if (isManaged()) {
            return where().sort(strArr, sortArr).findAll();
        }
        throw new UnsupportedOperationException(ONLY_IN_MANAGED_MODE_MESSAGE);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(@Nullable Object obj) {
        if (isManaged() && !this.baseRealm.isInTransaction()) {
            throw new IllegalStateException(REMOVE_OUTSIDE_TRANSACTION_ERROR);
        }
        return super.remove(obj);
    }

    public RealmList(Class<E> cls, OsList osList, BaseRealm baseRealm) {
        this.clazz = cls;
        this.osListOperator = getOperator(baseRealm, osList, cls, null);
        this.baseRealm = baseRealm;
    }

    public RealmList(String str, OsList osList, BaseRealm baseRealm) {
        this.baseRealm = baseRealm;
        this.className = str;
        this.osListOperator = getOperator(baseRealm, osList, null, str);
    }
}
