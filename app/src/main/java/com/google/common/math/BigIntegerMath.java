package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
public final class BigIntegerMath {

    @VisibleForTesting
    public static final BigInteger SQRT2_PRECOMPUTED_BITS = new BigInteger("16a09e667f3bcc908b2fb1366ea957d3e3adec17512775099da2f590b0667322a", 16);
    private static final double LN_10 = Math.log(10.0d);
    private static final double LN_2 = Math.log(2.0d);

    /* JADX INFO: renamed from: com.google.common.math.BigIntegerMath$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode;

        static {
            int[] iArr = new int[RoundingMode.values().length];
            $SwitchMap$java$math$RoundingMode = iArr;
            try {
                iArr[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.CEILING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    @GwtIncompatible
    public static class BigIntegerToDoubleRounder extends ToDoubleRounder<BigInteger> {
        public static final BigIntegerToDoubleRounder INSTANCE = new BigIntegerToDoubleRounder();

        private BigIntegerToDoubleRounder() {
        }

        @Override // com.google.common.math.ToDoubleRounder
        public final Number minus(Number number, Number number2) {
            return ((BigInteger) number).subtract((BigInteger) number2);
        }

        @Override // com.google.common.math.ToDoubleRounder
        public final double roundToDoubleArbitrarily(Number number) {
            return DoubleUtils.bigToDouble((BigInteger) number);
        }

        @Override // com.google.common.math.ToDoubleRounder
        public final int sign(Number number) {
            return ((BigInteger) number).signum();
        }

        @Override // com.google.common.math.ToDoubleRounder
        public final Number toX(double d, RoundingMode roundingMode) {
            return DoubleMath.roundToBigInteger(d, roundingMode);
        }
    }

    private BigIntegerMath() {
    }

    public static BigInteger binomial(int i, int i2) {
        MathPreconditions.checkNonNegative("n", i);
        MathPreconditions.checkNonNegative("k", i2);
        int i3 = 1;
        Preconditions.checkArgument(i2 <= i, "k (%s) > n (%s)", i2, i);
        if (i2 > (i >> 1)) {
            i2 = i - i2;
        }
        int[] iArr = LongMath.biggestBinomials;
        if (i2 < 34 && i <= iArr[i2]) {
            return BigInteger.valueOf(LongMath.binomial(i, i2));
        }
        BigInteger bigIntegerDivide = BigInteger.ONE;
        long j = i;
        long j2 = 1;
        int iLog2 = LongMath.log2(j, RoundingMode.CEILING);
        while (true) {
            int i4 = iLog2;
            while (i3 < i2) {
                int i5 = i - i3;
                i3++;
                i4 += iLog2;
                if (i4 >= 63) {
                    bigIntegerDivide = bigIntegerDivide.multiply(BigInteger.valueOf(j)).divide(BigInteger.valueOf(j2));
                    j = i5;
                    j2 = i3;
                } else {
                    j *= (long) i5;
                    j2 *= (long) i3;
                }
            }
            return bigIntegerDivide.multiply(BigInteger.valueOf(j)).divide(BigInteger.valueOf(j2));
        }
    }

    @Beta
    public static BigInteger ceilingPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(log2(bigInteger, RoundingMode.CEILING));
    }

    @GwtIncompatible
    public static BigInteger divide(BigInteger bigInteger, BigInteger bigInteger2, RoundingMode roundingMode) {
        return new BigDecimal(bigInteger).divide(new BigDecimal(bigInteger2), 0, roundingMode).toBigIntegerExact();
    }

    public static BigInteger factorial(int i) {
        MathPreconditions.checkNonNegative("n", i);
        long[] jArr = LongMath.factorials;
        if (i < 21) {
            return BigInteger.valueOf(jArr[i]);
        }
        ArrayList arrayList = new ArrayList(IntMath.divide(IntMath.log2(i, RoundingMode.CEILING) * i, 64, RoundingMode.CEILING));
        long j = jArr[20];
        int iNumberOfTrailingZeros = Long.numberOfTrailingZeros(j);
        long j2 = j >> iNumberOfTrailingZeros;
        int iLog2 = LongMath.log2(j2, RoundingMode.FLOOR) + 1;
        long j3 = 21;
        int iLog3 = LongMath.log2(j3, RoundingMode.FLOOR) + 1;
        int i2 = 1 << (iLog3 - 1);
        while (j3 <= i) {
            if ((((long) i2) & j3) != 0) {
                i2 <<= 1;
                iLog3++;
            }
            int iNumberOfTrailingZeros2 = Long.numberOfTrailingZeros(j3);
            long j4 = j3 >> iNumberOfTrailingZeros2;
            iNumberOfTrailingZeros += iNumberOfTrailingZeros2;
            if ((iLog3 - iNumberOfTrailingZeros2) + iLog2 >= 64) {
                arrayList.add(BigInteger.valueOf(j2));
                j2 = 1;
            }
            j2 *= j4;
            iLog2 = LongMath.log2(j2, RoundingMode.FLOOR) + 1;
            j3++;
        }
        if (j2 > 1) {
            arrayList.add(BigInteger.valueOf(j2));
        }
        return listProduct(arrayList, 0, arrayList.size()).shiftLeft(iNumberOfTrailingZeros);
    }

    @Beta
    public static BigInteger floorPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(log2(bigInteger, RoundingMode.FLOOR));
    }

    public static boolean isPowerOfTwo(BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        return bigInteger.signum() > 0 && bigInteger.getLowestSetBit() == bigInteger.bitLength() - 1;
    }

    public static BigInteger listProduct(List<BigInteger> list, int i, int i2) {
        int i3 = i2 - i;
        if (i3 == 0) {
            return BigInteger.ONE;
        }
        if (i3 == 1) {
            return list.get(i);
        }
        if (i3 == 2) {
            return list.get(i).multiply(list.get(i + 1));
        }
        if (i3 == 3) {
            return list.get(i).multiply(list.get(i + 1)).multiply(list.get(i + 2));
        }
        int i4 = (i2 + i) >>> 1;
        return listProduct(list, i, i4).multiply(listProduct(list, i4, i2));
    }

    @GwtIncompatible
    public static int log10(BigInteger bigInteger, RoundingMode roundingMode) {
        int iCompareTo;
        MathPreconditions.checkPositive(bigInteger);
        if (bigInteger.bitLength() <= 63) {
            return LongMath.log10(bigInteger.longValue(), roundingMode);
        }
        int iLog2 = (int) ((((double) log2(bigInteger, RoundingMode.FLOOR)) * LN_2) / LN_10);
        BigInteger bigIntegerPow = BigInteger.TEN.pow(iLog2);
        int iCompareTo2 = bigIntegerPow.compareTo(bigInteger);
        if (iCompareTo2 > 0) {
            do {
                iLog2--;
                bigIntegerPow = bigIntegerPow.divide(BigInteger.TEN);
                iCompareTo = bigIntegerPow.compareTo(bigInteger);
            } while (iCompareTo > 0);
        } else {
            BigInteger bigIntegerMultiply = BigInteger.TEN.multiply(bigIntegerPow);
            int iCompareTo3 = bigIntegerMultiply.compareTo(bigInteger);
            BigInteger bigInteger2 = bigIntegerPow;
            BigInteger bigInteger3 = bigIntegerMultiply;
            int i = iCompareTo2;
            while (iCompareTo3 <= 0) {
                iLog2++;
                BigInteger bigIntegerMultiply2 = BigInteger.TEN.multiply(bigInteger3);
                int iCompareTo4 = bigIntegerMultiply2.compareTo(bigInteger);
                bigInteger2 = bigInteger3;
                bigInteger3 = bigIntegerMultiply2;
                int i2 = iCompareTo3;
                iCompareTo3 = iCompareTo4;
                i = i2;
            }
            bigIntegerPow = bigInteger2;
            iCompareTo = i;
        }
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(iCompareTo == 0);
            case 2:
            case 3:
                return iLog2;
            case 4:
            case 5:
                return bigIntegerPow.equals(bigInteger) ? iLog2 : iLog2 + 1;
            case 6:
            case 7:
            case 8:
                return bigInteger.pow(2).compareTo(bigIntegerPow.pow(2).multiply(BigInteger.TEN)) <= 0 ? iLog2 : iLog2 + 1;
            default:
                throw new AssertionError();
        }
    }

    public static int log2(BigInteger bigInteger, RoundingMode roundingMode) {
        MathPreconditions.checkPositive((BigInteger) Preconditions.checkNotNull(bigInteger));
        int iBitLength = bigInteger.bitLength() - 1;
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(bigInteger));
            case 2:
            case 3:
                return iBitLength;
            case 4:
            case 5:
                return isPowerOfTwo(bigInteger) ? iBitLength : iBitLength + 1;
            case 6:
            case 7:
            case 8:
                if (iBitLength < 256) {
                    return bigInteger.compareTo(SQRT2_PRECOMPUTED_BITS.shiftRight(256 - iBitLength)) <= 0 ? iBitLength : iBitLength + 1;
                }
                return bigInteger.pow(2).bitLength() + (-1) < (iBitLength * 2) + 1 ? iBitLength : iBitLength + 1;
            default:
                throw new AssertionError();
        }
    }

    @GwtIncompatible
    public static double roundToDouble(BigInteger bigInteger, RoundingMode roundingMode) {
        return BigIntegerToDoubleRounder.INSTANCE.roundToDouble(bigInteger, roundingMode);
    }

    @GwtIncompatible
    public static BigInteger sqrt(BigInteger bigInteger, RoundingMode roundingMode) {
        if (bigInteger.signum() < 0) {
            String strValueOf = String.valueOf(bigInteger);
            throw new IllegalArgumentException(MainActivity$$ExternalSyntheticOutline0.m(strValueOf.length() + "x".length() + 16, "x", " (", strValueOf, ") must be >= 0"));
        }
        if (bigInteger.bitLength() <= 63) {
            return BigInteger.valueOf(LongMath.sqrt(bigInteger.longValue(), roundingMode));
        }
        BigInteger bigIntegerSqrtFloor = sqrtFloor(bigInteger);
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(bigIntegerSqrtFloor.pow(2).equals(bigInteger));
            case 2:
            case 3:
                return bigIntegerSqrtFloor;
            case 4:
            case 5:
                int iIntValue = bigIntegerSqrtFloor.intValue();
                return iIntValue * iIntValue == bigInteger.intValue() && bigIntegerSqrtFloor.pow(2).equals(bigInteger) ? bigIntegerSqrtFloor : bigIntegerSqrtFloor.add(BigInteger.ONE);
            case 6:
            case 7:
            case 8:
                return bigIntegerSqrtFloor.pow(2).add(bigIntegerSqrtFloor).compareTo(bigInteger) >= 0 ? bigIntegerSqrtFloor : bigIntegerSqrtFloor.add(BigInteger.ONE);
            default:
                throw new AssertionError();
        }
    }

    @GwtIncompatible
    private static BigInteger sqrtApproxWithDoubles(BigInteger bigInteger) {
        return DoubleMath.roundToBigInteger(Math.sqrt(DoubleUtils.bigToDouble(bigInteger)), RoundingMode.HALF_EVEN);
    }

    @GwtIncompatible
    private static BigInteger sqrtFloor(BigInteger bigInteger) {
        BigInteger bigIntegerShiftLeft;
        int iLog2 = log2(bigInteger, RoundingMode.FLOOR);
        if (iLog2 < 1023) {
            bigIntegerShiftLeft = sqrtApproxWithDoubles(bigInteger);
        } else {
            int i = (iLog2 - 52) & (-2);
            bigIntegerShiftLeft = sqrtApproxWithDoubles(bigInteger.shiftRight(i)).shiftLeft(i >> 1);
        }
        BigInteger bigIntegerShiftRight = bigIntegerShiftLeft.add(bigInteger.divide(bigIntegerShiftLeft)).shiftRight(1);
        if (bigIntegerShiftLeft.equals(bigIntegerShiftRight)) {
            return bigIntegerShiftLeft;
        }
        while (true) {
            BigInteger bigIntegerShiftRight2 = bigIntegerShiftRight.add(bigInteger.divide(bigIntegerShiftRight)).shiftRight(1);
            if (bigIntegerShiftRight2.compareTo(bigIntegerShiftRight) >= 0) {
                return bigIntegerShiftRight;
            }
            bigIntegerShiftRight = bigIntegerShiftRight2;
        }
    }
}
