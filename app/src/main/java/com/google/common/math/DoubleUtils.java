package com.google.common.math;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
final class DoubleUtils {
    private DoubleUtils() {
    }

    public static double bigToDouble(BigInteger bigInteger) {
        BigInteger bigIntegerAbs = bigInteger.abs();
        boolean z = true;
        int iBitLength = bigIntegerAbs.bitLength() - 1;
        if (iBitLength < 63) {
            return bigInteger.longValue();
        }
        if (iBitLength > 1023) {
            return ((double) bigInteger.signum()) * Double.POSITIVE_INFINITY;
        }
        int i = (iBitLength - 52) - 1;
        long jLongValue = bigIntegerAbs.shiftRight(i).longValue();
        long j = (jLongValue >> 1) & 4503599627370495L;
        if ((jLongValue & 1) == 0 || ((j & 1) == 0 && bigIntegerAbs.getLowestSetBit() >= i)) {
            z = false;
        }
        if (z) {
            j++;
        }
        return Double.longBitsToDouble(((((long) (iBitLength + AnalyticsListener.EVENT_DRM_KEYS_LOADED)) << 52) + j) | (((long) bigInteger.signum()) & Long.MIN_VALUE));
    }

    public static double ensureNonNegative(double d) {
        Preconditions.checkArgument(!Double.isNaN(d));
        return Math.max(d, 0.0d);
    }

    public static long getSignificand(double d) {
        Preconditions.checkArgument(isFinite(d), "not a normal value");
        int exponent = Math.getExponent(d);
        long jDoubleToRawLongBits = Double.doubleToRawLongBits(d) & 4503599627370495L;
        return exponent == -1023 ? jDoubleToRawLongBits << 1 : jDoubleToRawLongBits | 4503599627370496L;
    }

    public static boolean isFinite(double d) {
        return Math.getExponent(d) <= 1023;
    }

    public static double nextDown(double d) {
        return -Math.nextUp(-d);
    }
}
