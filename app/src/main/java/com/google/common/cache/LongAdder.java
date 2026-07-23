package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
final class LongAdder extends Striped64 implements LongAddable {
    private static final long serialVersionUID = 7249069246863182397L;

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.busy = 0;
        this.cells = null;
        this.base = objectInputStream.readLong();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeLong(sum());
    }

    /* JADX WARN: Code duplicated, block: B:44:0x007c  */
    @Override // com.google.common.cache.LongAddable
    public void add(long j) {
        boolean zCas;
        int iNextInt;
        Striped64.Cell[] cellArr;
        boolean z;
        int length;
        boolean z2;
        int length2;
        int length3;
        Striped64.Cell cell;
        Striped64.Cell[] cellArr2 = this.cells;
        if (cellArr2 == null) {
            long j2 = this.base;
            if (casBase(j2, j2 + j)) {
                return;
            }
        }
        ThreadLocal<int[]> threadLocal = Striped64.threadHashCode;
        int[] iArr = threadLocal.get();
        if (iArr == null || cellArr2 == null || (length3 = cellArr2.length) < 1 || (cell = cellArr2[(length3 - 1) & iArr[0]]) == null) {
            zCas = true;
        } else {
            long j3 = cell.value;
            zCas = cell.cas(j3, j3 + j);
            if (zCas) {
                return;
            }
        }
        if (iArr == null) {
            iArr = new int[1];
            threadLocal.set(iArr);
            iNextInt = Striped64.rng.nextInt();
            if (iNextInt == 0) {
                iNextInt = 1;
            }
            iArr[0] = iNextInt;
        } else {
            iNextInt = iArr[0];
        }
        while (true) {
            boolean z3 = false;
            while (true) {
                cellArr = this.cells;
                if (cellArr != null && (length = cellArr.length) > 0) {
                    Striped64.Cell cell2 = cellArr[(length - 1) & iNextInt];
                    if (cell2 != null) {
                        if (zCas) {
                            long j4 = cell2.value;
                            if (cell2.cas(j4, fn(j4, j))) {
                                return;
                            }
                            if (length < Striped64.NCPU && this.cells == cellArr) {
                                if (z3) {
                                    if (this.busy == 0 && casBusy()) {
                                        break;
                                    }
                                } else {
                                    z3 = true;
                                }
                            }
                        } else {
                            zCas = true;
                        }
                        int i = iNextInt ^ (iNextInt << 13);
                        int i2 = i ^ (i >>> 17);
                        iNextInt = i2 ^ (i2 << 5);
                        iArr[0] = iNextInt;
                    } else if (this.busy == 0) {
                        Striped64.Cell cell3 = new Striped64.Cell(j);
                        if (this.busy == 0 && casBusy()) {
                            try {
                                Striped64.Cell[] cellArr3 = this.cells;
                                if (cellArr3 == null || (length2 = cellArr3.length) <= 0) {
                                    z2 = false;
                                } else {
                                    int i3 = (length2 - 1) & iNextInt;
                                    if (cellArr3[i3] == null) {
                                        cellArr3[i3] = cell3;
                                        z2 = true;
                                    } else {
                                        z2 = false;
                                    }
                                }
                                this.busy = 0;
                                if (z2) {
                                    return;
                                }
                            } catch (Throwable th) {
                                this.busy = 0;
                                throw th;
                            }
                        }
                    }
                    z3 = false;
                    int i4 = iNextInt ^ (iNextInt << 13);
                    int i5 = i4 ^ (i4 >>> 17);
                    iNextInt = i5 ^ (i5 << 5);
                    iArr[0] = iNextInt;
                } else if (this.busy == 0 && this.cells == cellArr && casBusy()) {
                    try {
                        if (this.cells == cellArr) {
                            Striped64.Cell[] cellArr4 = new Striped64.Cell[2];
                            cellArr4[iNextInt & 1] = new Striped64.Cell(j);
                            this.cells = cellArr4;
                            z = true;
                        } else {
                            z = false;
                        }
                        this.busy = 0;
                        if (z) {
                            return;
                        }
                    } catch (Throwable th2) {
                        this.busy = 0;
                        throw th2;
                    }
                } else {
                    long j5 = this.base;
                    if (casBase(j5, fn(j5, j))) {
                        return;
                    }
                }
            }
            try {
                if (this.cells == cellArr) {
                    Striped64.Cell[] cellArr5 = new Striped64.Cell[length << 1];
                    for (int i6 = 0; i6 < length; i6++) {
                        cellArr5[i6] = cellArr[i6];
                    }
                    this.cells = cellArr5;
                }
                this.busy = 0;
            } catch (Throwable th3) {
                this.busy = 0;
                throw th3;
            }
        }
    }

    public void decrement() {
        add(-1L);
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return sum();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return sum();
    }

    public final long fn(long j, long j2) {
        return j + j2;
    }

    @Override // com.google.common.cache.LongAddable
    public void increment() {
        add(1L);
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) sum();
    }

    @Override // java.lang.Number
    public long longValue() {
        return sum();
    }

    public void reset() {
        Striped64.Cell[] cellArr = this.cells;
        this.base = 0L;
        if (cellArr != null) {
            for (Striped64.Cell cell : cellArr) {
                if (cell != null) {
                    cell.value = 0L;
                }
            }
        }
    }

    @Override // com.google.common.cache.LongAddable
    public long sum() {
        long j = this.base;
        Striped64.Cell[] cellArr = this.cells;
        if (cellArr != null) {
            for (Striped64.Cell cell : cellArr) {
                if (cell != null) {
                    j += cell.value;
                }
            }
        }
        return j;
    }

    public long sumThenReset() {
        long j = this.base;
        Striped64.Cell[] cellArr = this.cells;
        this.base = 0L;
        if (cellArr != null) {
            for (Striped64.Cell cell : cellArr) {
                if (cell != null) {
                    j += cell.value;
                    cell.value = 0L;
                }
            }
        }
        return j;
    }

    public String toString() {
        return Long.toString(sum());
    }
}
