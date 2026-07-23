package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.util.BitSet;

/* JADX INFO: loaded from: classes2.dex */
@GwtIncompatible
@ElementTypesAreNonnullByDefault
final class SmallCharMatcher extends CharMatcher.NamedFastMatcher {
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final double DESIRED_LOAD_FACTOR = 0.5d;
    private final boolean containsZero;
    private final long filter;
    private final char[] table;

    private SmallCharMatcher(char[] cArr, long j, boolean z, String str) {
        super(str);
        this.table = cArr;
        this.filter = j;
        this.containsZero = z;
    }

    private boolean checkFilter(int i) {
        return 1 == ((this.filter >> i) & 1);
    }

    public static CharMatcher from(BitSet bitSet, String str) {
        int i;
        int i2;
        int iCardinality = bitSet.cardinality();
        boolean z = bitSet.get(0);
        if (iCardinality == 1) {
            i = 2;
        } else {
            int iHighestOneBit = Integer.highestOneBit(iCardinality - 1) << 1;
            while (((double) iHighestOneBit) * DESIRED_LOAD_FACTOR < iCardinality) {
                iHighestOneBit <<= 1;
            }
            i = iHighestOneBit;
        }
        char[] cArr = new char[i];
        int i3 = i - 1;
        int iNextSetBit = bitSet.nextSetBit(0);
        long j = 0;
        while (iNextSetBit != -1) {
            long j2 = (1 << iNextSetBit) | j;
            int iRotateLeft = Integer.rotateLeft(C1 * iNextSetBit, 15) * C2;
            while (true) {
                i2 = iRotateLeft & i3;
                if (cArr[i2] == 0) {
                    break;
                }
                iRotateLeft = i2 + 1;
            }
            cArr[i2] = (char) iNextSetBit;
            iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1);
            j = j2;
        }
        return new SmallCharMatcher(cArr, j, z, str);
    }

    @Override // com.google.common.base.CharMatcher
    public boolean matches(char c) {
        if (c == 0) {
            return this.containsZero;
        }
        if (!checkFilter(c)) {
            return false;
        }
        int length = this.table.length - 1;
        int iRotateLeft = (Integer.rotateLeft(C1 * c, 15) * C2) & length;
        int i = iRotateLeft;
        do {
            char[] cArr = this.table;
            if (cArr[i] == 0) {
                return false;
            }
            if (cArr[i] == c) {
                return true;
            }
            i = (i + 1) & length;
        } while (i != iRotateLeft);
        return false;
    }

    @Override // com.google.common.base.CharMatcher
    public final void setBits(BitSet bitSet) {
        if (this.containsZero) {
            bitSet.set(0);
        }
        for (char c : this.table) {
            if (c != 0) {
                bitSet.set(c);
            }
        }
    }
}
