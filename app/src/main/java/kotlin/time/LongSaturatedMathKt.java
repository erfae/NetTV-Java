package kotlin.time;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;

/* JADX INFO: compiled from: longSaturatedMath.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a\"\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0000ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\"\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\n\u001a \u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0010"}, d2 = {"checkInfiniteSumDefined", "", "longNs", TypedValues.TransitionType.S_DURATION, "Lkotlin/time/Duration;", "durationNs", "checkInfiniteSumDefined-PjuGub4", "(JJJ)J", "saturatingAdd", "saturatingAdd-pTJri5U", "(JJ)J", "saturatingAddInHalves", "saturatingAddInHalves-pTJri5U", "saturatingDiff", "valueNs", "originNs", "kotlin-stdlib"}, k = 2, mv = {1, 7, 1}, xi = 48)
public final class LongSaturatedMathKt {
    /* JADX INFO: renamed from: checkInfiniteSumDefined-PjuGub4, reason: not valid java name */
    private static final long m1774checkInfiniteSumDefinedPjuGub4(long j, long j2, long j3) {
        if (!Duration.m1679isInfiniteimpl(j2) || (j ^ j3) >= 0) {
            return j;
        }
        throw new IllegalArgumentException("Summing infinities of different signs");
    }

    /* JADX INFO: renamed from: saturatingAdd-pTJri5U, reason: not valid java name */
    public static final long m1775saturatingAddpTJri5U(long j, long j2) {
        long jM1667getInWholeNanosecondsimpl = Duration.m1667getInWholeNanosecondsimpl(j2);
        if (((j - 1) | 1) == Long.MAX_VALUE) {
            return m1774checkInfiniteSumDefinedPjuGub4(j, j2, jM1667getInWholeNanosecondsimpl);
        }
        if ((1 | (jM1667getInWholeNanosecondsimpl - 1)) == Long.MAX_VALUE) {
            return m1776saturatingAddInHalvespTJri5U(j, j2);
        }
        long j3 = j + jM1667getInWholeNanosecondsimpl;
        if (((j ^ j3) & (jM1667getInWholeNanosecondsimpl ^ j3)) < 0) {
            return j < 0 ? Long.MIN_VALUE : Long.MAX_VALUE;
        }
        return j3;
    }

    /* JADX INFO: renamed from: saturatingAddInHalves-pTJri5U, reason: not valid java name */
    private static final long m1776saturatingAddInHalvespTJri5U(long j, long j2) {
        long jM1650divUwyO8pc = Duration.m1650divUwyO8pc(j2, 2);
        if (((Duration.m1667getInWholeNanosecondsimpl(jM1650divUwyO8pc) - 1) | 1) != Long.MAX_VALUE) {
            return m1775saturatingAddpTJri5U(m1775saturatingAddpTJri5U(j, jM1650divUwyO8pc), jM1650divUwyO8pc);
        }
        return (long) (Duration.m1690toDoubleimpl(j2, DurationUnit.NANOSECONDS) + j);
    }

    public static final long saturatingDiff(long j, long j2) {
        if ((1 | (j2 - 1)) == Long.MAX_VALUE) {
            return Duration.m1699unaryMinusUwyO8pc(DurationKt.toDuration(j2, DurationUnit.DAYS));
        }
        long j3 = j - j2;
        if (((j3 ^ j) & (~(j3 ^ j2))) >= 0) {
            Duration.Companion companion = Duration.INSTANCE;
            return DurationKt.toDuration(j3, DurationUnit.NANOSECONDS);
        }
        long j4 = 1000000;
        long j5 = (j / j4) - (j2 / j4);
        long j6 = (j % j4) - (j2 % j4);
        Duration.Companion companion2 = Duration.INSTANCE;
        return Duration.m1683plusLRDsOJo(DurationKt.toDuration(j5, DurationUnit.MILLISECONDS), DurationKt.toDuration(j6, DurationUnit.NANOSECONDS));
    }
}
