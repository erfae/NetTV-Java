package com.google.common.primitives;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Comparator;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@Beta
@GwtCompatible
public final class UnsignedInts {

    public enum LexicographicalComparator implements Comparator<int[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "UnsignedInts.lexicographicalComparator()";
        }

        @Override // java.util.Comparator
        public int compare(int[] iArr, int[] iArr2) {
            int iMin = Math.min(iArr.length, iArr2.length);
            for (int i = 0; i < iMin; i++) {
                if (iArr[i] != iArr2[i]) {
                    return UnsignedInts.compare(iArr[i], iArr2[i]);
                }
            }
            return iArr.length - iArr2.length;
        }
    }

    private UnsignedInts() {
    }

    public static int checkedCast(long j) {
        Preconditions.checkArgument((j >> 32) == 0, "out of range: %s", j);
        return (int) j;
    }

    public static int compare(int i, int i2) {
        return Ints.compare(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE);
    }

    @CanIgnoreReturnValue
    public static int decode(String str) {
        ParseRequest parseRequestFromString = ParseRequest.fromString(str);
        try {
            return parseUnsignedInt(parseRequestFromString.rawValue, parseRequestFromString.radix);
        } catch (NumberFormatException e) {
            NumberFormatException numberFormatException = new NumberFormatException(str.length() != 0 ? "Error parsing value: ".concat(str) : new String("Error parsing value: "));
            numberFormatException.initCause(e);
            throw numberFormatException;
        }
    }

    public static int divide(int i, int i2) {
        return (int) (toLong(i) / toLong(i2));
    }

    public static String join(String str, int... iArr) {
        Preconditions.checkNotNull(str);
        if (iArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(iArr.length * 5);
        sb.append(toString(iArr[0]));
        for (int i = 1; i < iArr.length; i++) {
            sb.append(str);
            sb.append(toString(iArr[i]));
        }
        return sb.toString();
    }

    public static Comparator<int[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static int max(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0);
        int i = iArr[0] ^ Integer.MIN_VALUE;
        for (int i2 = 1; i2 < iArr.length; i2++) {
            int i3 = iArr[i2] ^ Integer.MIN_VALUE;
            if (i3 > i) {
                i = i3;
            }
        }
        return i ^ Integer.MIN_VALUE;
    }

    public static int min(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0);
        int i = iArr[0] ^ Integer.MIN_VALUE;
        for (int i2 = 1; i2 < iArr.length; i2++) {
            int i3 = iArr[i2] ^ Integer.MIN_VALUE;
            if (i3 < i) {
                i = i3;
            }
        }
        return i ^ Integer.MIN_VALUE;
    }

    @CanIgnoreReturnValue
    public static int parseUnsignedInt(String str) {
        return parseUnsignedInt(str, 10);
    }

    public static int remainder(int i, int i2) {
        return (int) (toLong(i) % toLong(i2));
    }

    public static int saturatedCast(long j) {
        if (j <= 0) {
            return 0;
        }
        if (j >= 4294967296L) {
            return -1;
        }
        return (int) j;
    }

    public static void sort(int[] iArr) {
        Preconditions.checkNotNull(iArr);
        sort(iArr, 0, iArr.length);
    }

    public static void sortDescending(int[] iArr) {
        Preconditions.checkNotNull(iArr);
        sortDescending(iArr, 0, iArr.length);
    }

    public static long toLong(int i) {
        return ((long) i) & 4294967295L;
    }

    public static String toString(int i) {
        return toString(i, 10);
    }

    @CanIgnoreReturnValue
    public static int parseUnsignedInt(String str, int i) {
        Preconditions.checkNotNull(str);
        long j = Long.parseLong(str, i);
        if ((4294967295L & j) == j) {
            return (int) j;
        }
        StringBuilder sb = new StringBuilder(Insets$$ExternalSyntheticOutline0.m(str, 69));
        sb.append("Input ");
        sb.append(str);
        sb.append(" in base ");
        sb.append(i);
        sb.append(" is not in the range of an unsigned integer");
        throw new NumberFormatException(sb.toString());
    }

    public static String toString(int i, int i2) {
        return Long.toString(((long) i) & 4294967295L, i2);
    }

    public static void sort(int[] iArr, int i, int i2) {
        Preconditions.checkNotNull(iArr);
        Preconditions.checkPositionIndexes(i, i2, iArr.length);
        for (int i3 = i; i3 < i2; i3++) {
            iArr[i3] = Integer.MIN_VALUE ^ iArr[i3];
        }
        Arrays.sort(iArr, i, i2);
        while (i < i2) {
            iArr[i] = iArr[i] ^ Integer.MIN_VALUE;
            i++;
        }
    }

    public static void sortDescending(int[] iArr, int i, int i2) {
        Preconditions.checkNotNull(iArr);
        Preconditions.checkPositionIndexes(i, i2, iArr.length);
        for (int i3 = i; i3 < i2; i3++) {
            iArr[i3] = Integer.MAX_VALUE ^ iArr[i3];
        }
        Arrays.sort(iArr, i, i2);
        while (i < i2) {
            iArr[i] = iArr[i] ^ Integer.MAX_VALUE;
            i++;
        }
    }
}
