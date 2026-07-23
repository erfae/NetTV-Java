package com.google.common.collect;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import java.util.Arrays;
import javax.annotation.CheckForNull;
import kotlin.UShort;

/* JADX INFO: loaded from: classes2.dex */
@GwtIncompatible
@ElementTypesAreNonnullByDefault
final class CompactHashing {
    private static final int BYTE_MASK = 255;
    private static final int BYTE_MAX_SIZE = 256;
    private static final int HASH_TABLE_BITS_MAX_BITS = 5;
    private static final int MIN_HASH_TABLE_SIZE = 4;
    private static final int SHORT_MASK = 65535;
    private static final int SHORT_MAX_SIZE = 65536;

    private CompactHashing() {
    }

    public static Object createTable(int i) {
        if (i < 2 || i > 1073741824 || Integer.highestOneBit(i) != i) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(52, "must be power of 2 between 2^1 and 2^30: ", i));
        }
        if (i <= 256) {
            return new byte[i];
        }
        return i <= 65536 ? new short[i] : new int[i];
    }

    public static int newCapacity(int i) {
        return (i + 1) * (i < 32 ? 4 : 2);
    }

    public static int remove(@CheckForNull Object obj, @CheckForNull Object obj2, int i, Object obj3, int[] iArr, Object[] objArr, @CheckForNull Object[] objArr2) {
        int iSmearedHash = Hashing.smearedHash(obj);
        int i2 = iSmearedHash & i;
        int iTableGet = tableGet(obj3, i2);
        if (iTableGet == 0) {
            return -1;
        }
        int i3 = ~i;
        int i4 = iSmearedHash & i3;
        int i5 = -1;
        while (true) {
            int i6 = iTableGet - 1;
            int i7 = iArr[i6];
            if ((i7 & i3) == i4 && Objects.equal(obj, objArr[i6]) && (objArr2 == null || Objects.equal(obj2, objArr2[i6]))) {
                int i8 = i7 & i;
                if (i5 == -1) {
                    tableSet(obj3, i2, i8);
                } else {
                    iArr[i5] = (i8 & i) | (iArr[i5] & i3);
                }
                return i6;
            }
            int i9 = i7 & i;
            if (i9 == 0) {
                return -1;
            }
            i5 = i6;
            iTableGet = i9;
        }
    }

    public static void tableClear(Object obj) {
        if (obj instanceof byte[]) {
            Arrays.fill((byte[]) obj, (byte) 0);
        } else if (obj instanceof short[]) {
            Arrays.fill((short[]) obj, (short) 0);
        } else {
            Arrays.fill((int[]) obj, 0);
        }
    }

    public static int tableGet(Object obj, int i) {
        if (obj instanceof byte[]) {
            return ((byte[]) obj)[i] & 255;
        }
        return obj instanceof short[] ? ((short[]) obj)[i] & UShort.MAX_VALUE : ((int[]) obj)[i];
    }

    public static void tableSet(Object obj, int i, int i2) {
        if (obj instanceof byte[]) {
            ((byte[]) obj)[i] = (byte) i2;
        } else if (obj instanceof short[]) {
            ((short[]) obj)[i] = (short) i2;
        } else {
            ((int[]) obj)[i] = i2;
        }
    }

    public static int tableSize(int i) {
        return Math.max(4, Hashing.closedTableSize(i + 1, 1.0d));
    }
}
