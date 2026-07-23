package com.google.common.math;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;
import okhttp3.internal.http2.Http2Connection;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
public final class IntMath {

    @VisibleForTesting
    public static final byte[] maxLog10ForLeadingZeros = {9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0};

    @VisibleForTesting
    public static final int[] powersOf10 = {1, 10, 100, 1000, 10000, AacUtil.AAC_LC_MAX_RATE_BYTES_PER_SECOND, 1000000, 10000000, 100000000, Http2Connection.DEGRADED_PONG_TIMEOUT_NS};

    @VisibleForTesting
    public static final int[] halfPowersOf10 = {3, 31, TypedValues.AttributesType.TYPE_PATH_ROTATE, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
    private static final int[] factorials = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600};

    @VisibleForTesting
    public static int[] biggestBinomials = {Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, 477, 193, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};

    /* JADX INFO: renamed from: com.google.common.math.IntMath$1, reason: invalid class name */
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

    private IntMath() {
    }

    public static int binomial(int i, int i2) {
        MathPreconditions.checkNonNegative("n", i);
        MathPreconditions.checkNonNegative("k", i2);
        int i3 = 0;
        Preconditions.checkArgument(i2 <= i, "k (%s) > n (%s)", i2, i);
        if (i2 > (i >> 1)) {
            i2 = i - i2;
        }
        int[] iArr = biggestBinomials;
        if (i2 >= 17 || i > iArr[i2]) {
            return Integer.MAX_VALUE;
        }
        if (i2 == 0) {
            return 1;
        }
        if (i2 == 1) {
            return i;
        }
        long j = 1;
        while (i3 < i2) {
            long j2 = j * ((long) (i - i3));
            i3++;
            j = j2 / ((long) i3);
        }
        return (int) j;
    }

    @Beta
    public static int ceilingPowerOfTwo(int i) {
        MathPreconditions.checkPositive(i);
        if (i <= 1073741824) {
            return 1 << (-Integer.numberOfLeadingZeros(i - 1));
        }
        StringBuilder sb = new StringBuilder(58);
        sb.append("ceilingPowerOfTwo(");
        sb.append(i);
        sb.append(") not representable as an int");
        throw new ArithmeticException(sb.toString());
    }

    public static int checkedAdd(int i, int i2) {
        long j = ((long) i) + ((long) i2);
        int i3 = (int) j;
        MathPreconditions.checkNoOverflow(j == ((long) i3), "checkedAdd", i, i2);
        return i3;
    }

    public static int checkedMultiply(int i, int i2) {
        long j = ((long) i) * ((long) i2);
        int i3 = (int) j;
        MathPreconditions.checkNoOverflow(j == ((long) i3), "checkedMultiply", i, i2);
        return i3;
    }

    public static int checkedPow(int i, int i2) {
        MathPreconditions.checkNonNegative("exponent", i2);
        if (i == -2) {
            MathPreconditions.checkNoOverflow(i2 < 32, "checkedPow", i, i2);
            return (i2 & 1) == 0 ? 1 << i2 : (-1) << i2;
        }
        if (i == -1) {
            return (i2 & 1) == 0 ? 1 : -1;
        }
        if (i == 0) {
            return i2 == 0 ? 1 : 0;
        }
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            MathPreconditions.checkNoOverflow(i2 < 31, "checkedPow", i, i2);
            return 1 << i2;
        }
        int iCheckedMultiply = 1;
        while (i2 != 0) {
            if (i2 == 1) {
                return checkedMultiply(iCheckedMultiply, i);
            }
            if ((i2 & 1) != 0) {
                iCheckedMultiply = checkedMultiply(iCheckedMultiply, i);
            }
            i2 >>= 1;
            if (i2 > 0) {
                MathPreconditions.checkNoOverflow((-46340 <= i) & (i <= 46340), "checkedPow", i, i2);
                i *= i;
            }
        }
        return iCheckedMultiply;
    }

    public static int checkedSubtract(int i, int i2) {
        long j = ((long) i) - ((long) i2);
        int i3 = (int) j;
        MathPreconditions.checkNoOverflow(j == ((long) i3), "checkedSubtract", i, i2);
        return i3;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:36:0x0057  */
    /* JADX WARN: Code duplicated, block: B:38:0x005a  */
    /* JADX WARN: Code duplicated, block: B:42:? A[RETURN, SYNTHETIC] */
    public static int divide(int i, int i2, RoundingMode roundingMode) {
        Preconditions.checkNotNull(roundingMode);
        if (i2 == 0) {
            throw new ArithmeticException("/ by zero");
        }
        int i3 = i / i2;
        int i4 = i - (i2 * i3);
        if (i4 == 0) {
            return i3;
        }
        boolean z = true;
        int i5 = ((i ^ i2) >> 31) | 1;
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(i4 == 0);
                z = false;
                if (z) {
                    return i3 + i5;
                }
                return i3;
            case 2:
                z = false;
                if (z) {
                    return i3 + i5;
                }
                return i3;
            case 3:
                if (i5 >= 0) {
                    z = false;
                }
                if (z) {
                    return i3 + i5;
                }
                return i3;
            case 4:
                if (z) {
                    return i3 + i5;
                }
                return i3;
            case 5:
                if (i5 <= 0) {
                    z = false;
                }
                if (z) {
                    return i3 + i5;
                }
                return i3;
            case 6:
            case 7:
            case 8:
                int iAbs = Math.abs(i4);
                int iAbs2 = iAbs - (Math.abs(i2) - iAbs);
                if (iAbs2 == 0) {
                    if (roundingMode != RoundingMode.HALF_UP) {
                        if (!((roundingMode == RoundingMode.HALF_EVEN) & ((i3 & 1) != 0))) {
                            z = false;
                        }
                    }
                } else if (iAbs2 <= 0) {
                    z = false;
                }
                if (z) {
                    return i3 + i5;
                }
                return i3;
            default:
                throw new AssertionError();
        }
    }

    public static int factorial(int i) {
        MathPreconditions.checkNonNegative("n", i);
        int[] iArr = factorials;
        if (i < iArr.length) {
            return iArr[i];
        }
        return Integer.MAX_VALUE;
    }

    @Beta
    public static int floorPowerOfTwo(int i) {
        MathPreconditions.checkPositive(i);
        return Integer.highestOneBit(i);
    }

    public static int gcd(int i, int i2) {
        MathPreconditions.checkNonNegative("a", i);
        MathPreconditions.checkNonNegative("b", i2);
        if (i == 0) {
            return i2;
        }
        if (i2 == 0) {
            return i;
        }
        int iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(i);
        int iNumberOfTrailingZeros2 = i >> iNumberOfTrailingZeros;
        int iNumberOfTrailingZeros3 = Integer.numberOfTrailingZeros(i2);
        int i3 = i2 >> iNumberOfTrailingZeros3;
        while (iNumberOfTrailingZeros2 != i3) {
            int i4 = iNumberOfTrailingZeros2 - i3;
            int i5 = (i4 >> 31) & i4;
            int i6 = (i4 - i5) - i5;
            i3 += i5;
            iNumberOfTrailingZeros2 = i6 >> Integer.numberOfTrailingZeros(i6);
        }
        return iNumberOfTrailingZeros2 << Math.min(iNumberOfTrailingZeros, iNumberOfTrailingZeros3);
    }

    public static boolean isPowerOfTwo(int i) {
        return (i > 0) & ((i & (i + (-1))) == 0);
    }

    @Beta
    @GwtIncompatible
    public static boolean isPrime(int i) {
        return LongMath.isPrime(i);
    }

    @GwtIncompatible
    public static int log10(int i, RoundingMode roundingMode) {
        int i2;
        MathPreconditions.checkPositive(i);
        int iLog10Floor = log10Floor(i);
        int i3 = powersOf10[iLog10Floor];
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(i == i3);
            case 2:
            case 3:
                return iLog10Floor;
            case 4:
            case 5:
                i2 = ~(i3 - i);
                return iLog10Floor + ((~i2) >>> 31);
            case 6:
            case 7:
            case 8:
                i2 = ~(halfPowersOf10[iLog10Floor] - i);
                return iLog10Floor + ((~i2) >>> 31);
            default:
                throw new AssertionError();
        }
    }

    private static int log10Floor(int i) {
        byte b = maxLog10ForLeadingZeros[Integer.numberOfLeadingZeros(i)];
        return b - ((~(~(i - powersOf10[b]))) >>> 31);
    }

    public static int log2(int i, RoundingMode roundingMode) {
        MathPreconditions.checkPositive(i);
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(i));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 32 - Integer.numberOfLeadingZeros(i - 1);
            case 6:
            case 7:
            case 8:
                int iNumberOfLeadingZeros = Integer.numberOfLeadingZeros(i);
                return (31 - iNumberOfLeadingZeros) + ((~(~(((-1257966797) >>> iNumberOfLeadingZeros) - i))) >>> 31);
            default:
                throw new AssertionError();
        }
        return 31 - Integer.numberOfLeadingZeros(i);
    }

    public static int mean(int i, int i2) {
        return (i & i2) + ((i ^ i2) >> 1);
    }

    public static int mod(int i, int i2) {
        if (i2 > 0) {
            int i3 = i % i2;
            return i3 >= 0 ? i3 : i3 + i2;
        }
        StringBuilder sb = new StringBuilder(31);
        sb.append("Modulus ");
        sb.append(i2);
        sb.append(" must be > 0");
        throw new ArithmeticException(sb.toString());
    }

    @GwtIncompatible
    public static int pow(int i, int i2) {
        MathPreconditions.checkNonNegative("exponent", i2);
        if (i == -2) {
            if (i2 < 32) {
                return (i2 & 1) == 0 ? 1 << i2 : -(1 << i2);
            }
            return 0;
        }
        if (i == -1) {
            return (i2 & 1) == 0 ? 1 : -1;
        }
        if (i == 0) {
            return i2 == 0 ? 1 : 0;
        }
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            if (i2 < 32) {
                return 1 << i2;
            }
            return 0;
        }
        int i3 = 1;
        while (i2 != 0) {
            if (i2 == 1) {
                return i * i3;
            }
            i3 *= (i2 & 1) == 0 ? 1 : i;
            i *= i;
            i2 >>= 1;
        }
        return i3;
    }

    @Beta
    public static int saturatedAdd(int i, int i2) {
        return Ints.saturatedCast(((long) i) + ((long) i2));
    }

    @Beta
    public static int saturatedMultiply(int i, int i2) {
        return Ints.saturatedCast(((long) i) * ((long) i2));
    }

    @Beta
    public static int saturatedPow(int i, int i2) {
        MathPreconditions.checkNonNegative("exponent", i2);
        if (i == -2) {
            if (i2 >= 32) {
                return (i2 & 1) + Integer.MAX_VALUE;
            }
            return (i2 & 1) == 0 ? 1 << i2 : (-1) << i2;
        }
        if (i == -1) {
            return (i2 & 1) == 0 ? 1 : -1;
        }
        if (i == 0) {
            return i2 == 0 ? 1 : 0;
        }
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            if (i2 >= 31) {
                return Integer.MAX_VALUE;
            }
            return 1 << i2;
        }
        int i3 = ((i >>> 31) & i2 & 1) + Integer.MAX_VALUE;
        int iSaturatedMultiply = 1;
        while (i2 != 0) {
            if (i2 == 1) {
                return saturatedMultiply(iSaturatedMultiply, i);
            }
            if ((i2 & 1) != 0) {
                iSaturatedMultiply = saturatedMultiply(iSaturatedMultiply, i);
            }
            i2 >>= 1;
            if (i2 > 0) {
                if ((-46340 > i) || (i > 46340)) {
                    return i3;
                }
                i *= i;
            }
        }
        return iSaturatedMultiply;
    }

    @Beta
    public static int saturatedSubtract(int i, int i2) {
        return Ints.saturatedCast(((long) i) - ((long) i2));
    }

    @GwtIncompatible
    public static int sqrt(int i, RoundingMode roundingMode) {
        MathPreconditions.checkNonNegative("x", i);
        int iSqrtFloor = sqrtFloor(i);
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(iSqrtFloor * iSqrtFloor == i);
            case 2:
            case 3:
                return iSqrtFloor;
            case 4:
            case 5:
                return iSqrtFloor + ((~(~((iSqrtFloor * iSqrtFloor) - i))) >>> 31);
            case 6:
            case 7:
            case 8:
                return iSqrtFloor + ((~(~(((iSqrtFloor * iSqrtFloor) + iSqrtFloor) - i))) >>> 31);
            default:
                throw new AssertionError();
        }
    }

    private static int sqrtFloor(int i) {
        return (int) Math.sqrt(i);
    }
}
