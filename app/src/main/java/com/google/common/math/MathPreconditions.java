package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.math.BigInteger;
import java.math.RoundingMode;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@CanIgnoreReturnValue
@GwtCompatible
final class MathPreconditions {
    private MathPreconditions() {
    }

    public static void checkInRangeForRoundingInputs(boolean z, double d, RoundingMode roundingMode) {
        if (z) {
            return;
        }
        String strValueOf = String.valueOf(roundingMode);
        StringBuilder sb = new StringBuilder(strValueOf.length() + 83);
        sb.append("rounded value is out of range for input ");
        sb.append(d);
        sb.append(" and rounding mode ");
        sb.append(strValueOf);
        throw new ArithmeticException(sb.toString());
    }

    public static void checkNoOverflow(boolean z, String str, int i, int i2) {
        if (z) {
            return;
        }
        StringBuilder sb = new StringBuilder(str.length() + 36);
        sb.append("overflow: ");
        sb.append(str);
        sb.append("(");
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        sb.append(")");
        throw new ArithmeticException(sb.toString());
    }

    public static int checkNonNegative(String str, int i) {
        if (i >= 0) {
            return i;
        }
        StringBuilder sb = new StringBuilder(str.length() + 27);
        sb.append(str);
        sb.append(" (");
        sb.append(i);
        sb.append(") must be >= 0");
        throw new IllegalArgumentException(sb.toString());
    }

    public static int checkPositive(int i) {
        if (i > 0) {
            return i;
        }
        StringBuilder sb = new StringBuilder("x".length() + 26);
        sb.append("x");
        sb.append(" (");
        sb.append(i);
        sb.append(") must be > 0");
        throw new IllegalArgumentException(sb.toString());
    }

    public static void checkRoundingUnnecessary(boolean z) {
        if (!z) {
            throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
        }
    }

    public static void checkNoOverflow(boolean z, String str, long j, long j2) {
        if (z) {
            return;
        }
        StringBuilder sb = new StringBuilder(str.length() + 54);
        sb.append("overflow: ");
        sb.append(str);
        sb.append("(");
        sb.append(j);
        sb.append(", ");
        sb.append(j2);
        sb.append(")");
        throw new ArithmeticException(sb.toString());
    }

    public static long checkNonNegative(String str, long j) {
        if (j >= 0) {
            return j;
        }
        StringBuilder sb = new StringBuilder(str.length() + 36);
        sb.append(str);
        sb.append(" (");
        sb.append(j);
        sb.append(") must be >= 0");
        throw new IllegalArgumentException(sb.toString());
    }

    public static long checkPositive(long j) {
        if (j > 0) {
            return j;
        }
        StringBuilder sb = new StringBuilder("x".length() + 35);
        sb.append("x");
        sb.append(" (");
        sb.append(j);
        sb.append(") must be > 0");
        throw new IllegalArgumentException(sb.toString());
    }

    public static BigInteger checkPositive(BigInteger bigInteger) {
        if (bigInteger.signum() > 0) {
            return bigInteger;
        }
        String strValueOf = String.valueOf(bigInteger);
        throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + "x".length() + 15, "x", " (", strValueOf, ") must be > 0"));
    }
}
