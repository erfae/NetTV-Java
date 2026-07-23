package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public final class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V>, Serializable {
    private static final int ABSENT = -1;
    private static final int ENDPOINT = -2;
    private transient Set<Map.Entry<K, V>> entrySet;
    private transient int firstInInsertionOrder;
    private transient int[] hashTableKToV;
    private transient int[] hashTableVToK;

    @RetainedWith
    @CheckForNull
    @LazyInit
    private transient BiMap<V, K> inverse;
    private transient Set<K> keySet;
    public transient K[] keys;
    private transient int lastInInsertionOrder;
    public transient int modCount;
    private transient int[] nextInBucketKToV;
    private transient int[] nextInBucketVToK;
    private transient int[] nextInInsertionOrder;
    private transient int[] prevInInsertionOrder;
    public transient int size;
    private transient Set<V> valueSet;
    public transient V[] values;

    public final class EntryForKey extends AbstractMapEntry<K, V> {
        public int index;

        @ParametricNullness
        public final K key;

        public EntryForKey(int i) {
            this.key = HashBiMap.this.keys[i];
            this.index = i;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public K getKey() {
            return this.key;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V getValue() {
            updateIndex();
            int i = this.index;
            if (i == -1) {
                return null;
            }
            return HashBiMap.this.values[i];
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V setValue(@ParametricNullness V v) {
            updateIndex();
            int i = this.index;
            if (i == -1) {
                HashBiMap.this.put(this.key, v);
                return null;
            }
            V v2 = HashBiMap.this.values[i];
            if (Objects.equal(v2, v)) {
                return v;
            }
            HashBiMap.this.replaceValueInEntry(this.index, v, false);
            return v2;
        }

        public final void updateIndex() {
            int i = this.index;
            if (i != -1) {
                HashBiMap hashBiMap = HashBiMap.this;
                if (i <= hashBiMap.size && Objects.equal(hashBiMap.keys[i], this.key)) {
                    return;
                }
            }
            this.index = HashBiMap.this.findEntryByKey(this.key);
        }
    }

    public static final class EntryForValue<K, V> extends AbstractMapEntry<V, K> {
        public final HashBiMap<K, V> biMap;
        public int index;

        @ParametricNullness
        public final V value;

        public EntryForValue(HashBiMap<K, V> hashBiMap, int i) {
            this.biMap = hashBiMap;
            this.value = hashBiMap.values[i];
            this.index = i;
        }

        private void updateIndex() {
            int i = this.index;
            if (i != -1) {
                HashBiMap<K, V> hashBiMap = this.biMap;
                if (i <= hashBiMap.size && Objects.equal(this.value, hashBiMap.values[i])) {
                    return;
                }
            }
            this.index = this.biMap.findEntryByValue(this.value);
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V getKey() {
            return this.value;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public K getValue() {
            updateIndex();
            int i = this.index;
            if (i == -1) {
                return null;
            }
            return this.biMap.keys[i];
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public K setValue(@ParametricNullness K k) {
            updateIndex();
            int i = this.index;
            if (i == -1) {
                this.biMap.putInverse(this.value, k, false);
                return null;
            }
            K k2 = this.biMap.keys[i];
            if (Objects.equal(k2, k)) {
                return k;
            }
            this.biMap.replaceKeyInEntry(this.index, k, false);
            return k2;
        }
    }

    public final class EntrySet extends View<K, V, Map.Entry<K, V>> {
        public EntrySet() {
            super(HashBiMap.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iFindEntryByKey = HashBiMap.this.findEntryByKey(key);
            return iFindEntryByKey != -1 && Objects.equal(value, HashBiMap.this.values[iFindEntryByKey]);
        }

        @Override // com.google.common.collect.HashBiMap.View
        public final Object forEntry(int i) {
            return new EntryForKey(i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        @CanIgnoreReturnValue
        public boolean remove(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iSmearedHash = Hashing.smearedHash(key);
            int iFindEntryByKey = HashBiMap.this.findEntryByKey(key, iSmearedHash);
            if (iFindEntryByKey == -1 || !Objects.equal(value, HashBiMap.this.values[iFindEntryByKey])) {
                return false;
            }
            HashBiMap.this.removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash);
            return true;
        }
    }

    public static class Inverse<K, V> extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {
        private final HashBiMap<K, V> forward;
        private transient Set<Map.Entry<V, K>> inverseEntrySet;

        public Inverse(HashBiMap<K, V> hashBiMap) {
            this.forward = hashBiMap;
        }

        @GwtIncompatible("serialization")
        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            ((HashBiMap) this.forward).inverse = this;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            this.forward.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object obj) {
            return this.forward.containsValue(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsValue(@CheckForNull Object obj) {
            return this.forward.containsKey(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<V, K>> entrySet() {
            Set<Map.Entry<V, K>> set = this.inverseEntrySet;
            if (set != null) {
                return set;
            }
            InverseEntrySet inverseEntrySet = new InverseEntrySet(this.forward);
            this.inverseEntrySet = inverseEntrySet;
            return inverseEntrySet;
        }

        @Override // com.google.common.collect.BiMap
        @CanIgnoreReturnValue
        @CheckForNull
        public K forcePut(@ParametricNullness V v, @ParametricNullness K k) {
            return this.forward.putInverse(v, k, true);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public K get(@CheckForNull Object obj) {
            HashBiMap<K, V> hashBiMap = this.forward;
            int iFindEntryByValue = hashBiMap.findEntryByValue(obj);
            if (iFindEntryByValue == -1) {
                return null;
            }
            return hashBiMap.keys[iFindEntryByValue];
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<K, V> inverse() {
            return this.forward;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<V> keySet() {
            return this.forward.values();
        }

        @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
        @CanIgnoreReturnValue
        @CheckForNull
        public K put(@ParametricNullness V v, @ParametricNullness K k) {
            return this.forward.putInverse(v, k, false);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CanIgnoreReturnValue
        @CheckForNull
        public K remove(@CheckForNull Object obj) {
            HashBiMap<K, V> hashBiMap = this.forward;
            java.util.Objects.requireNonNull(hashBiMap);
            int iSmearedHash = Hashing.smearedHash(obj);
            int iFindEntryByValue = hashBiMap.findEntryByValue(obj, iSmearedHash);
            if (iFindEntryByValue == -1) {
                return null;
            }
            K k = hashBiMap.keys[iFindEntryByValue];
            hashBiMap.removeEntryValueHashKnown(iFindEntryByValue, iSmearedHash);
            return k;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.forward.size;
        }

        @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
        public Set<K> values() {
            return this.forward.keySet();
        }
    }

    public static class InverseEntrySet<K, V> extends View<K, V, Map.Entry<V, K>> {
        public InverseEntrySet(HashBiMap<K, V> hashBiMap) {
            super(hashBiMap);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iFindEntryByValue = this.biMap.findEntryByValue(key);
            return iFindEntryByValue != -1 && Objects.equal(this.biMap.keys[iFindEntryByValue], value);
        }

        @Override // com.google.common.collect.HashBiMap.View
        public final Object forEntry(int i) {
            return new EntryForValue(this.biMap, i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iSmearedHash = Hashing.smearedHash(key);
            int iFindEntryByValue = this.biMap.findEntryByValue(key, iSmearedHash);
            if (iFindEntryByValue == -1 || !Objects.equal(this.biMap.keys[iFindEntryByValue], value)) {
                return false;
            }
            this.biMap.removeEntryValueHashKnown(iFindEntryByValue, iSmearedHash);
            return true;
        }
    }

    public final class KeySet extends View<K, V, K> {
        public KeySet() {
            super(HashBiMap.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            return HashBiMap.this.containsKey(obj);
        }

        @Override // com.google.common.collect.HashBiMap.View
        @ParametricNullness
        public final K forEntry(int i) {
            return HashBiMap.this.keys[i];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            int iSmearedHash = Hashing.smearedHash(obj);
            int iFindEntryByKey = HashBiMap.this.findEntryByKey(obj, iSmearedHash);
            if (iFindEntryByKey == -1) {
                return false;
            }
            HashBiMap.this.removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash);
            return true;
        }
    }

    public final class ValueSet extends View<K, V, V> {
        public ValueSet() {
            super(HashBiMap.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object obj) {
            return HashBiMap.this.containsValue(obj);
        }

        @Override // com.google.common.collect.HashBiMap.View
        @ParametricNullness
        public final V forEntry(int i) {
            return HashBiMap.this.values[i];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object obj) {
            int iSmearedHash = Hashing.smearedHash(obj);
            int iFindEntryByValue = HashBiMap.this.findEntryByValue(obj, iSmearedHash);
            if (iFindEntryByValue == -1) {
                return false;
            }
            HashBiMap.this.removeEntryValueHashKnown(iFindEntryByValue, iSmearedHash);
            return true;
        }
    }

    public static abstract class View<K, V, T> extends AbstractSet<T> {
        public final HashBiMap<K, V> biMap;

        public View(HashBiMap<K, V> hashBiMap) {
            this.biMap = hashBiMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.biMap.clear();
        }

        @ParametricNullness
        public abstract T forEntry(int i);

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<T> iterator() {
            return new Iterator<T>() { // from class: com.google.common.collect.HashBiMap.View.1
                private int expectedModCount;
                private int index;
                private int indexToRemove = -1;
                private int remaining;

                {
                    this.index = ((HashBiMap) View.this.biMap).firstInInsertionOrder;
                    HashBiMap<K, V> hashBiMap = View.this.biMap;
                    this.expectedModCount = hashBiMap.modCount;
                    this.remaining = hashBiMap.size;
                }

                private void checkForComodification() {
                    if (View.this.biMap.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    checkForComodification();
                    return this.index != -2 && this.remaining > 0;
                }

                @Override // java.util.Iterator
                @ParametricNullness
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    T t = (T) View.this.forEntry(this.index);
                    this.indexToRemove = this.index;
                    this.index = ((HashBiMap) View.this.biMap).nextInInsertionOrder[this.index];
                    this.remaining--;
                    return t;
                }

                @Override // java.util.Iterator
                public void remove() {
                    checkForComodification();
                    CollectPreconditions.checkRemove(this.indexToRemove != -1);
                    HashBiMap<K, V> hashBiMap = View.this.biMap;
                    int i = this.indexToRemove;
                    hashBiMap.removeEntryKeyHashKnown(i, Hashing.smearedHash(hashBiMap.keys[i]));
                    int i2 = this.index;
                    HashBiMap<K, V> hashBiMap2 = View.this.biMap;
                    if (i2 == hashBiMap2.size) {
                        this.index = this.indexToRemove;
                    }
                    this.indexToRemove = -1;
                    this.expectedModCount = hashBiMap2.modCount;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.biMap.size;
        }
    }

    private HashBiMap(int i) {
        init(i);
    }

    private int bucket(int i) {
        return i & (this.hashTableKToV.length - 1);
    }

    public static <K, V> HashBiMap<K, V> create() {
        return create(16);
    }

    private static int[] createFilledWithAbsent(int i) {
        int[] iArr = new int[i];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private void deleteFromTableKToV(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int iBucket = bucket(i2);
        int[] iArr = this.hashTableKToV;
        if (iArr[iBucket] == i) {
            int[] iArr2 = this.nextInBucketKToV;
            iArr[iBucket] = iArr2[i];
            iArr2[i] = -1;
            return;
        }
        int i3 = iArr[iBucket];
        int i4 = this.nextInBucketKToV[i3];
        while (true) {
            int i5 = i4;
            int i6 = i3;
            i3 = i5;
            if (i3 == -1) {
                String strValueOf = String.valueOf(this.keys[i]);
                throw new AssertionError(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 32, "Expected to find entry with key ", strValueOf));
            }
            if (i3 == i) {
                int[] iArr3 = this.nextInBucketKToV;
                iArr3[i6] = iArr3[i];
                iArr3[i] = -1;
                return;
            }
            i4 = this.nextInBucketKToV[i3];
        }
    }

    private void deleteFromTableVToK(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int iBucket = bucket(i2);
        int[] iArr = this.hashTableVToK;
        if (iArr[iBucket] == i) {
            int[] iArr2 = this.nextInBucketVToK;
            iArr[iBucket] = iArr2[i];
            iArr2[i] = -1;
            return;
        }
        int i3 = iArr[iBucket];
        int i4 = this.nextInBucketVToK[i3];
        while (true) {
            int i5 = i4;
            int i6 = i3;
            i3 = i5;
            if (i3 == -1) {
                String strValueOf = String.valueOf(this.values[i]);
                throw new AssertionError(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 34, "Expected to find entry with value ", strValueOf));
            }
            if (i3 == i) {
                int[] iArr3 = this.nextInBucketVToK;
                iArr3[i6] = iArr3[i];
                iArr3[i] = -1;
                return;
            }
            i4 = this.nextInBucketVToK[i3];
        }
    }

    private void ensureCapacity(int i) {
        int[] iArr = this.nextInBucketKToV;
        if (iArr.length < i) {
            int iExpandedCapacity = ImmutableCollection.Builder.expandedCapacity(iArr.length, i);
            this.keys = (K[]) Arrays.copyOf(this.keys, iExpandedCapacity);
            this.values = (V[]) Arrays.copyOf(this.values, iExpandedCapacity);
            this.nextInBucketKToV = expandAndFillWithAbsent(this.nextInBucketKToV, iExpandedCapacity);
            this.nextInBucketVToK = expandAndFillWithAbsent(this.nextInBucketVToK, iExpandedCapacity);
            this.prevInInsertionOrder = expandAndFillWithAbsent(this.prevInInsertionOrder, iExpandedCapacity);
            this.nextInInsertionOrder = expandAndFillWithAbsent(this.nextInInsertionOrder, iExpandedCapacity);
        }
        if (this.hashTableKToV.length < i) {
            int iClosedTableSize = Hashing.closedTableSize(i, 1.0d);
            this.hashTableKToV = createFilledWithAbsent(iClosedTableSize);
            this.hashTableVToK = createFilledWithAbsent(iClosedTableSize);
            for (int i2 = 0; i2 < this.size; i2++) {
                int iBucket = bucket(Hashing.smearedHash(this.keys[i2]));
                int[] iArr2 = this.nextInBucketKToV;
                int[] iArr3 = this.hashTableKToV;
                iArr2[i2] = iArr3[iBucket];
                iArr3[iBucket] = i2;
                int iBucket2 = bucket(Hashing.smearedHash(this.values[i2]));
                int[] iArr4 = this.nextInBucketVToK;
                int[] iArr5 = this.hashTableVToK;
                iArr4[i2] = iArr5[iBucket2];
                iArr5[iBucket2] = i2;
            }
        }
    }

    private static int[] expandAndFillWithAbsent(int[] iArr, int i) {
        int length = iArr.length;
        int[] iArrCopyOf = Arrays.copyOf(iArr, i);
        Arrays.fill(iArrCopyOf, length, i, -1);
        return iArrCopyOf;
    }

    private void insertIntoTableKToV(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int iBucket = bucket(i2);
        int[] iArr = this.nextInBucketKToV;
        int[] iArr2 = this.hashTableKToV;
        iArr[i] = iArr2[iBucket];
        iArr2[iBucket] = i;
    }

    private void insertIntoTableVToK(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int iBucket = bucket(i2);
        int[] iArr = this.nextInBucketVToK;
        int[] iArr2 = this.hashTableVToK;
        iArr[i] = iArr2[iBucket];
        iArr2[iBucket] = i;
    }

    private void moveEntryToIndex(int i, int i2) {
        int i3;
        int i4;
        if (i == i2) {
            return;
        }
        int i5 = this.prevInInsertionOrder[i];
        int i6 = this.nextInInsertionOrder[i];
        setSucceeds(i5, i2);
        setSucceeds(i2, i6);
        K[] kArr = this.keys;
        K k = kArr[i];
        V[] vArr = this.values;
        V v = vArr[i];
        kArr[i2] = k;
        vArr[i2] = v;
        int iBucket = bucket(Hashing.smearedHash(k));
        int[] iArr = this.hashTableKToV;
        if (iArr[iBucket] == i) {
            iArr[iBucket] = i2;
        } else {
            int i7 = iArr[iBucket];
            int i8 = this.nextInBucketKToV[i7];
            while (true) {
                int i9 = i8;
                i3 = i7;
                i7 = i9;
                if (i7 == i) {
                    break;
                } else {
                    i8 = this.nextInBucketKToV[i7];
                }
            }
            this.nextInBucketKToV[i3] = i2;
        }
        int[] iArr2 = this.nextInBucketKToV;
        iArr2[i2] = iArr2[i];
        iArr2[i] = -1;
        int iBucket2 = bucket(Hashing.smearedHash(v));
        int[] iArr3 = this.hashTableVToK;
        if (iArr3[iBucket2] == i) {
            iArr3[iBucket2] = i2;
        } else {
            int i10 = iArr3[iBucket2];
            int i11 = this.nextInBucketVToK[i10];
            while (true) {
                int i12 = i11;
                i4 = i10;
                i10 = i12;
                if (i10 == i) {
                    break;
                } else {
                    i11 = this.nextInBucketVToK[i10];
                }
            }
            this.nextInBucketVToK[i4] = i2;
        }
        int[] iArr4 = this.nextInBucketVToK;
        iArr4[i2] = iArr4[i];
        iArr4[i] = -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i = objectInputStream.readInt();
        init(16);
        for (int i2 = 0; i2 < i; i2++) {
            put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }

    private void removeEntry(int i, int i2, int i3) {
        Preconditions.checkArgument(i != -1);
        deleteFromTableKToV(i, i2);
        deleteFromTableVToK(i, i3);
        setSucceeds(this.prevInInsertionOrder[i], this.nextInInsertionOrder[i]);
        moveEntryToIndex(this.size - 1, i);
        K[] kArr = this.keys;
        int i4 = this.size;
        kArr[i4 - 1] = null;
        this.values[i4 - 1] = null;
        this.size = i4 - 1;
        this.modCount++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceKeyInEntry(int i, @ParametricNullness K k, boolean z) {
        Preconditions.checkArgument(i != -1);
        int iSmearedHash = Hashing.smearedHash(k);
        int iFindEntryByKey = findEntryByKey(k, iSmearedHash);
        int i2 = this.lastInInsertionOrder;
        int i3 = -2;
        if (iFindEntryByKey != -1) {
            if (!z) {
                String strValueOf = String.valueOf(k);
                throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 28, "Key already present in map: ", strValueOf));
            }
            i2 = this.prevInInsertionOrder[iFindEntryByKey];
            i3 = this.nextInInsertionOrder[iFindEntryByKey];
            removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash);
            if (i == this.size) {
                i = iFindEntryByKey;
            }
        }
        if (i2 == i) {
            i2 = this.prevInInsertionOrder[i];
        } else if (i2 == this.size) {
            i2 = iFindEntryByKey;
        }
        if (i3 == i) {
            iFindEntryByKey = this.nextInInsertionOrder[i];
        } else if (i3 != this.size) {
            iFindEntryByKey = i3;
        }
        setSucceeds(this.prevInInsertionOrder[i], this.nextInInsertionOrder[i]);
        deleteFromTableKToV(i, Hashing.smearedHash(this.keys[i]));
        this.keys[i] = k;
        insertIntoTableKToV(i, Hashing.smearedHash(k));
        setSucceeds(i2, i);
        setSucceeds(i, iFindEntryByKey);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceValueInEntry(int i, @ParametricNullness V v, boolean z) {
        Preconditions.checkArgument(i != -1);
        int iSmearedHash = Hashing.smearedHash(v);
        int iFindEntryByValue = findEntryByValue(v, iSmearedHash);
        if (iFindEntryByValue != -1) {
            if (!z) {
                String strValueOf = String.valueOf(v);
                throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + 30, "Value already present in map: ", strValueOf));
            }
            removeEntryValueHashKnown(iFindEntryByValue, iSmearedHash);
            if (i == this.size) {
                i = iFindEntryByValue;
            }
        }
        deleteFromTableVToK(i, Hashing.smearedHash(this.values[i]));
        this.values[i] = v;
        insertIntoTableVToK(i, iSmearedHash);
    }

    private void setSucceeds(int i, int i2) {
        if (i == -2) {
            this.firstInInsertionOrder = i2;
        } else {
            this.nextInInsertionOrder[i] = i2;
        }
        if (i2 == -2) {
            this.lastInInsertionOrder = i;
        } else {
            this.prevInInsertionOrder[i2] = i;
        }
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMap(this, objectOutputStream);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Arrays.fill(this.keys, 0, this.size, (Object) null);
        Arrays.fill(this.values, 0, this.size, (Object) null);
        Arrays.fill(this.hashTableKToV, -1);
        Arrays.fill(this.hashTableVToK, -1);
        Arrays.fill(this.nextInBucketKToV, 0, this.size, -1);
        Arrays.fill(this.nextInBucketVToK, 0, this.size, -1);
        Arrays.fill(this.prevInInsertionOrder, 0, this.size, -1);
        Arrays.fill(this.nextInInsertionOrder, 0, this.size, -1);
        this.size = 0;
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.modCount++;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@CheckForNull Object obj) {
        return findEntryByKey(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@CheckForNull Object obj) {
        return findEntryByValue(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySet;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.entrySet = entrySet;
        return entrySet;
    }

    public final int findEntry(@CheckForNull Object obj, int i, int[] iArr, int[] iArr2, Object[] objArr) {
        int i2 = iArr[bucket(i)];
        while (i2 != -1) {
            if (Objects.equal(objArr[i2], obj)) {
                return i2;
            }
            i2 = iArr2[i2];
        }
        return -1;
    }

    public final int findEntryByKey(@CheckForNull Object obj) {
        return findEntryByKey(obj, Hashing.smearedHash(obj));
    }

    public final int findEntryByValue(@CheckForNull Object obj) {
        return findEntryByValue(obj, Hashing.smearedHash(obj));
    }

    @Override // com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    @CheckForNull
    public V forcePut(@ParametricNullness K k, @ParametricNullness V v) {
        return put(k, v, true);
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public V get(@CheckForNull Object obj) {
        int iFindEntryByKey = findEntryByKey(obj);
        if (iFindEntryByKey == -1) {
            return null;
        }
        return this.values[iFindEntryByKey];
    }

    public final void init(int i) {
        CollectPreconditions.checkNonnegative(i, "expectedSize");
        int iClosedTableSize = Hashing.closedTableSize(i, 1.0d);
        this.size = 0;
        this.keys = (K[]) new Object[i];
        this.values = (V[]) new Object[i];
        this.hashTableKToV = createFilledWithAbsent(iClosedTableSize);
        this.hashTableVToK = createFilledWithAbsent(iClosedTableSize);
        this.nextInBucketKToV = createFilledWithAbsent(i);
        this.nextInBucketVToK = createFilledWithAbsent(i);
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.prevInInsertionOrder = createFilledWithAbsent(i);
        this.nextInInsertionOrder = createFilledWithAbsent(i);
    }

    @Override // com.google.common.collect.BiMap
    public BiMap<V, K> inverse() {
        BiMap<V, K> biMap = this.inverse;
        if (biMap != null) {
            return biMap;
        }
        Inverse inverse = new Inverse(this);
        this.inverse = inverse;
        return inverse;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.keySet;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet();
        this.keySet = keySet;
        return keySet;
    }

    @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    @CheckForNull
    public V put(@ParametricNullness K k, @ParametricNullness V v) {
        return put(k, v, false);
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public final K putInverse(@ParametricNullness V v, @ParametricNullness K k, boolean z) {
        int iSmearedHash = Hashing.smearedHash(v);
        int iFindEntryByValue = findEntryByValue(v, iSmearedHash);
        if (iFindEntryByValue != -1) {
            K k2 = this.keys[iFindEntryByValue];
            if (Objects.equal(k2, k)) {
                return k;
            }
            replaceKeyInEntry(iFindEntryByValue, k, z);
            return k2;
        }
        int i = this.lastInInsertionOrder;
        int iSmearedHash2 = Hashing.smearedHash(k);
        int iFindEntryByKey = findEntryByKey(k, iSmearedHash2);
        if (!z) {
            Preconditions.checkArgument(iFindEntryByKey == -1, "Key already present: %s", k);
        } else if (iFindEntryByKey != -1) {
            i = this.prevInInsertionOrder[iFindEntryByKey];
            removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash2);
        }
        ensureCapacity(this.size + 1);
        K[] kArr = this.keys;
        int i2 = this.size;
        kArr[i2] = k;
        this.values[i2] = v;
        insertIntoTableKToV(i2, iSmearedHash2);
        insertIntoTableVToK(this.size, iSmearedHash);
        int i3 = i == -2 ? this.firstInInsertionOrder : this.nextInInsertionOrder[i];
        setSucceeds(i, this.size);
        setSucceeds(this.size, i3);
        this.size++;
        this.modCount++;
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    @CheckForNull
    public V remove(@CheckForNull Object obj) {
        int iSmearedHash = Hashing.smearedHash(obj);
        int iFindEntryByKey = findEntryByKey(obj, iSmearedHash);
        if (iFindEntryByKey == -1) {
            return null;
        }
        V v = this.values[iFindEntryByKey];
        removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash);
        return v;
    }

    public final void removeEntryKeyHashKnown(int i, int i2) {
        removeEntry(i, i2, Hashing.smearedHash(this.values[i]));
    }

    public final void removeEntryValueHashKnown(int i, int i2) {
        removeEntry(i, Hashing.smearedHash(this.keys[i]), i2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    public static <K, V> HashBiMap<K, V> create(int i) {
        return new HashBiMap<>(i);
    }

    public final int findEntryByKey(@CheckForNull Object obj, int i) {
        return findEntry(obj, i, this.hashTableKToV, this.nextInBucketKToV, this.keys);
    }

    public final int findEntryByValue(@CheckForNull Object obj, int i) {
        return findEntry(obj, i, this.hashTableVToK, this.nextInBucketVToK, this.values);
    }

    @CheckForNull
    public final V put(@ParametricNullness K k, @ParametricNullness V v, boolean z) {
        int iSmearedHash = Hashing.smearedHash(k);
        int iFindEntryByKey = findEntryByKey(k, iSmearedHash);
        if (iFindEntryByKey != -1) {
            V v2 = this.values[iFindEntryByKey];
            if (Objects.equal(v2, v)) {
                return v;
            }
            replaceValueInEntry(iFindEntryByKey, v, z);
            return v2;
        }
        int iSmearedHash2 = Hashing.smearedHash(v);
        int iFindEntryByValue = findEntryByValue(v, iSmearedHash2);
        if (!z) {
            Preconditions.checkArgument(iFindEntryByValue == -1, "Value already present: %s", v);
        } else if (iFindEntryByValue != -1) {
            removeEntryValueHashKnown(iFindEntryByValue, iSmearedHash2);
        }
        ensureCapacity(this.size + 1);
        K[] kArr = this.keys;
        int i = this.size;
        kArr[i] = k;
        this.values[i] = v;
        insertIntoTableKToV(i, iSmearedHash);
        insertIntoTableVToK(this.size, iSmearedHash2);
        setSucceeds(this.lastInInsertionOrder, this.size);
        setSucceeds(this.size, -2);
        this.size++;
        this.modCount++;
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
    public Set<V> values() {
        Set<V> set = this.valueSet;
        if (set != null) {
            return set;
        }
        ValueSet valueSet = new ValueSet();
        this.valueSet = valueSet;
        return valueSet;
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> hashBiMapCreate = create(map.size());
        hashBiMapCreate.putAll(map);
        return hashBiMapCreate;
    }
}
