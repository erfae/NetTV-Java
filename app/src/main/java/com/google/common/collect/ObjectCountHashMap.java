package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(emulated = true, serializable = true)
@ElementTypesAreNonnullByDefault
class ObjectCountHashMap<K> {
    private static final long HASH_MASK = -4294967296L;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    private static final long NEXT_MASK = 4294967295L;

    @VisibleForTesting
    public transient long[] entries;
    public transient Object[] keys;
    private transient float loadFactor;
    public transient int modCount;
    public transient int size;
    private transient int[] table;
    private transient int threshold;
    public transient int[] values;

    public class MapEntry extends Multisets.AbstractEntry<K> {

        @ParametricNullness
        public final K key;
        public int lastKnownIndex;

        public MapEntry(int i) {
            this.key = (K) ObjectCountHashMap.this.keys[i];
            this.lastKnownIndex = i;
        }

        @Override // com.google.common.collect.Multiset.Entry
        public int getCount() {
            updateLastKnownIndex();
            int i = this.lastKnownIndex;
            if (i == -1) {
                return 0;
            }
            return ObjectCountHashMap.this.values[i];
        }

        @Override // com.google.common.collect.Multiset.Entry
        @ParametricNullness
        public K getElement() {
            return this.key;
        }

        @CanIgnoreReturnValue
        public int setCount(int i) {
            updateLastKnownIndex();
            int i2 = this.lastKnownIndex;
            if (i2 == -1) {
                ObjectCountHashMap.this.put(this.key, i);
                return 0;
            }
            int[] iArr = ObjectCountHashMap.this.values;
            int i3 = iArr[i2];
            iArr[i2] = i;
            return i3;
        }

        public final void updateLastKnownIndex() {
            int i = this.lastKnownIndex;
            if (i != -1) {
                ObjectCountHashMap objectCountHashMap = ObjectCountHashMap.this;
                if (i < objectCountHashMap.size && Objects.equal(this.key, objectCountHashMap.keys[i])) {
                    return;
                }
            }
            this.lastKnownIndex = ObjectCountHashMap.this.indexOf(this.key);
        }
    }

    public ObjectCountHashMap() {
        init(3);
    }

    private static int getHash(long j) {
        return (int) (j >>> 32);
    }

    private static int getNext(long j) {
        return (int) j;
    }

    private int hashTableMask() {
        return this.table.length - 1;
    }

    private static long[] newEntries(int i) {
        long[] jArr = new long[i];
        Arrays.fill(jArr, -1L);
        return jArr;
    }

    private static int[] newTable(int i) {
        int[] iArr = new int[i];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private void resizeMeMaybe(int i) {
        int length = this.entries.length;
        if (i > length) {
            int iMax = Math.max(1, length >>> 1) + length;
            if (iMax < 0) {
                iMax = Integer.MAX_VALUE;
            }
            if (iMax != length) {
                resizeEntries(iMax);
            }
        }
    }

    private void resizeTable(int i) {
        if (this.table.length >= 1073741824) {
            this.threshold = Integer.MAX_VALUE;
            return;
        }
        int i2 = ((int) (i * this.loadFactor)) + 1;
        int[] iArrNewTable = newTable(i);
        long[] jArr = this.entries;
        int length = iArrNewTable.length - 1;
        for (int i3 = 0; i3 < this.size; i3++) {
            int hash = getHash(jArr[i3]);
            int i4 = hash & length;
            int i5 = iArrNewTable[i4];
            iArrNewTable[i4] = i3;
            jArr[i3] = (((long) hash) << 32) | (((long) i5) & NEXT_MASK);
        }
        this.threshold = i2;
        this.table = iArrNewTable;
    }

    private static long swapNext(long j, int i) {
        return (j & HASH_MASK) | (((long) i) & NEXT_MASK);
    }

    public void clear() {
        this.modCount++;
        Arrays.fill(this.keys, 0, this.size, (Object) null);
        Arrays.fill(this.values, 0, this.size, 0);
        Arrays.fill(this.table, -1);
        Arrays.fill(this.entries, -1L);
        this.size = 0;
    }

    public boolean containsKey(@CheckForNull Object obj) {
        return indexOf(obj) != -1;
    }

    public final void ensureCapacity(int i) {
        if (i > this.entries.length) {
            resizeEntries(i);
        }
        if (i >= this.threshold) {
            resizeTable(Math.max(2, Integer.highestOneBit(i - 1) << 1));
        }
    }

    public int firstIndex() {
        return this.size == 0 ? -1 : 0;
    }

    public int get(@CheckForNull Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf == -1) {
            return 0;
        }
        return this.values[iIndexOf];
    }

    @ParametricNullness
    public final K getKey(int i) {
        Preconditions.checkElementIndex(i, this.size);
        return (K) this.keys[i];
    }

    public final int getValue(int i) {
        Preconditions.checkElementIndex(i, this.size);
        return this.values[i];
    }

    public final int indexOf(@CheckForNull Object obj) {
        int iSmearedHash = Hashing.smearedHash(obj);
        int next = this.table[hashTableMask() & iSmearedHash];
        while (next != -1) {
            long j = this.entries[next];
            if (getHash(j) == iSmearedHash && Objects.equal(obj, this.keys[next])) {
                return next;
            }
            next = getNext(j);
        }
        return -1;
    }

    public void init(int i) {
        Preconditions.checkArgument(i >= 0, "Initial capacity must be non-negative");
        Preconditions.checkArgument(true, "Illegal load factor");
        int iClosedTableSize = Hashing.closedTableSize(i, 1.0f);
        this.table = newTable(iClosedTableSize);
        this.loadFactor = 1.0f;
        this.keys = new Object[i];
        this.values = new int[i];
        this.entries = newEntries(i);
        this.threshold = Math.max(1, (int) (iClosedTableSize * 1.0f));
    }

    public void insertEntry(int i, @ParametricNullness K k, int i2, int i3) {
        this.entries[i] = (((long) i3) << 32) | NEXT_MASK;
        this.keys[i] = k;
        this.values[i] = i2;
    }

    public void moveLastEntry(int i) {
        int i2 = this.size - 1;
        if (i >= i2) {
            this.keys[i] = null;
            this.values[i] = 0;
            this.entries[i] = -1;
            return;
        }
        Object[] objArr = this.keys;
        objArr[i] = objArr[i2];
        int[] iArr = this.values;
        iArr[i] = iArr[i2];
        objArr[i2] = null;
        iArr[i2] = 0;
        long[] jArr = this.entries;
        long j = jArr[i2];
        jArr[i] = j;
        jArr[i2] = -1;
        int hash = getHash(j) & hashTableMask();
        int[] iArr2 = this.table;
        int i3 = iArr2[hash];
        if (i3 == i2) {
            iArr2[hash] = i;
            return;
        }
        while (true) {
            long j2 = this.entries[i3];
            int next = getNext(j2);
            if (next == i2) {
                this.entries[i3] = swapNext(j2, i);
                return;
            }
            i3 = next;
        }
    }

    public int nextIndex(int i) {
        int i2 = i + 1;
        if (i2 < this.size) {
            return i2;
        }
        return -1;
    }

    public int nextIndexAfterRemove(int i, int i2) {
        return i - 1;
    }

    @CanIgnoreReturnValue
    public int put(@ParametricNullness K k, int i) {
        CollectPreconditions.checkPositive(i, "count");
        long[] jArr = this.entries;
        Object[] objArr = this.keys;
        int[] iArr = this.values;
        int iSmearedHash = Hashing.smearedHash(k);
        int iHashTableMask = hashTableMask() & iSmearedHash;
        int i2 = this.size;
        int[] iArr2 = this.table;
        int i3 = iArr2[iHashTableMask];
        if (i3 == -1) {
            iArr2[iHashTableMask] = i2;
        } else {
            while (true) {
                long j = jArr[i3];
                if (getHash(j) == iSmearedHash && Objects.equal(k, objArr[i3])) {
                    int i4 = iArr[i3];
                    iArr[i3] = i;
                    return i4;
                }
                int next = getNext(j);
                if (next == -1) {
                    jArr[i3] = swapNext(j, i2);
                    break;
                }
                i3 = next;
            }
        }
        if (i2 == Integer.MAX_VALUE) {
            throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
        }
        int i5 = i2 + 1;
        resizeMeMaybe(i5);
        insertEntry(i2, k, i, iSmearedHash);
        this.size = i5;
        if (i2 >= this.threshold) {
            resizeTable(this.table.length * 2);
        }
        this.modCount++;
        return 0;
    }

    @CanIgnoreReturnValue
    public int remove(@CheckForNull Object obj) {
        return remove(obj, Hashing.smearedHash(obj));
    }

    @CanIgnoreReturnValue
    public final int removeEntry(int i) {
        return remove(this.keys[i], getHash(this.entries[i]));
    }

    public void resizeEntries(int i) {
        this.keys = Arrays.copyOf(this.keys, i);
        this.values = Arrays.copyOf(this.values, i);
        long[] jArr = this.entries;
        int length = jArr.length;
        long[] jArrCopyOf = Arrays.copyOf(jArr, i);
        if (i > length) {
            Arrays.fill(jArrCopyOf, length, i, -1L);
        }
        this.entries = jArrCopyOf;
    }

    public final void setValue(int i, int i2) {
        Preconditions.checkElementIndex(i, this.size);
        this.values[i] = i2;
    }

    private int remove(@CheckForNull Object obj, int i) {
        int iHashTableMask = hashTableMask() & i;
        int i2 = this.table[iHashTableMask];
        if (i2 == -1) {
            return 0;
        }
        int i3 = -1;
        while (true) {
            if (getHash(this.entries[i2]) == i && Objects.equal(obj, this.keys[i2])) {
                int i4 = this.values[i2];
                if (i3 == -1) {
                    this.table[iHashTableMask] = getNext(this.entries[i2]);
                } else {
                    long[] jArr = this.entries;
                    jArr[i3] = swapNext(jArr[i3], getNext(jArr[i2]));
                }
                moveLastEntry(i2);
                this.size--;
                this.modCount++;
                return i4;
            }
            int next = getNext(this.entries[i2]);
            if (next == -1) {
                return 0;
            }
            i3 = i2;
            i2 = next;
        }
    }

    public ObjectCountHashMap(ObjectCountHashMap<? extends K> objectCountHashMap) {
        init(objectCountHashMap.size);
        int iFirstIndex = objectCountHashMap.firstIndex();
        while (iFirstIndex != -1) {
            put(objectCountHashMap.getKey(iFirstIndex), objectCountHashMap.getValue(iFirstIndex));
            iFirstIndex = objectCountHashMap.nextIndex(iFirstIndex);
        }
    }

    public ObjectCountHashMap(int i) {
        init(i);
    }

    public ObjectCountHashMap(int i, float f) {
        init(i);
    }
}
