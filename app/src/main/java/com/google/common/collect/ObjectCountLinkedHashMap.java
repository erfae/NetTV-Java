package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(emulated = true, serializable = true)
@ElementTypesAreNonnullByDefault
class ObjectCountLinkedHashMap<K> extends ObjectCountHashMap<K> {
    private static final int ENDPOINT = -2;
    private transient int firstEntry;
    private transient int lastEntry;

    @VisibleForTesting
    public transient long[] links;

    public ObjectCountLinkedHashMap() {
        super(3, 1.0f);
    }

    private int getPredecessor(int i) {
        return (int) (this.links[i] >>> 32);
    }

    private int getSuccessor(int i) {
        return (int) this.links[i];
    }

    private void setPredecessor(int i, int i2) {
        long[] jArr = this.links;
        jArr[i] = (jArr[i] & 4294967295L) | (((long) i2) << 32);
    }

    private void setSucceeds(int i, int i2) {
        if (i == -2) {
            this.firstEntry = i2;
        } else {
            setSuccessor(i, i2);
        }
        if (i2 == -2) {
            this.lastEntry = i;
        } else {
            setPredecessor(i2, i);
        }
    }

    private void setSuccessor(int i, int i2) {
        long[] jArr = this.links;
        jArr[i] = (jArr[i] & (-4294967296L)) | (((long) i2) & 4294967295L);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public void clear() {
        super.clear();
        this.firstEntry = -2;
        this.lastEntry = -2;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public final int firstIndex() {
        int i = this.firstEntry;
        if (i == -2) {
            return -1;
        }
        return i;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public final void init(int i) {
        super.init(i);
        this.firstEntry = -2;
        this.lastEntry = -2;
        long[] jArr = new long[i];
        this.links = jArr;
        Arrays.fill(jArr, -1L);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public final void insertEntry(int i, @ParametricNullness K k, int i2, int i3) {
        super.insertEntry(i, k, i2, i3);
        setSucceeds(this.lastEntry, i);
        setSucceeds(i, -2);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public final void moveLastEntry(int i) {
        int i2 = this.size - 1;
        setSucceeds(getPredecessor(i), getSuccessor(i));
        if (i < i2) {
            setSucceeds(getPredecessor(i2), i);
            setSucceeds(i, getSuccessor(i2));
        }
        super.moveLastEntry(i);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public final int nextIndex(int i) {
        int successor = getSuccessor(i);
        if (successor == -2) {
            return -1;
        }
        return successor;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public final int nextIndexAfterRemove(int i, int i2) {
        return i == this.size ? i2 : i;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public final void resizeEntries(int i) {
        super.resizeEntries(i);
        long[] jArr = this.links;
        int length = jArr.length;
        long[] jArrCopyOf = Arrays.copyOf(jArr, i);
        this.links = jArrCopyOf;
        Arrays.fill(jArrCopyOf, length, i, -1L);
    }

    public ObjectCountLinkedHashMap(int i) {
        super(i, 1.0f);
    }

    public ObjectCountLinkedHashMap(ObjectCountHashMap<K> objectCountHashMap) {
        init(objectCountHashMap.size);
        int iFirstIndex = objectCountHashMap.firstIndex();
        while (iFirstIndex != -1) {
            put(objectCountHashMap.getKey(iFirstIndex), objectCountHashMap.getValue(iFirstIndex));
            iFirstIndex = objectCountHashMap.nextIndex(iFirstIndex);
        }
    }
}
