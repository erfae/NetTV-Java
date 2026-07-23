package kotlin.time;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: TimeSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@SinceKotlin(version = "1.3")
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H&ø\u0001\u0000ø\u0001\u0001J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0005H\u0016J\u0014\u0010\u0007\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0003H\u0096\u0002ø\u0001\u0001J\u0014\u0010\t\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0003H\u0096\u0002ø\u0001\u0001\u0082\u0002\b\n\u0002\b!\n\u0002\b\u0019¨\u0006\n"}, d2 = {"Lkotlin/time/TimeMark;", "", "elapsedNow", "Lkotlin/time/Duration;", "hasNotPassedNow", "", "hasPassedNow", "minus", TypedValues.TransitionType.S_DURATION, "plus", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1}, xi = 48)
@ExperimentalTime
public interface TimeMark {

    /* JADX INFO: compiled from: TimeSource.kt */
    @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
    public static final class DefaultImpls {
        public static boolean hasNotPassedNow(@NotNull TimeMark timeMark) {
            return Duration.m1680isNegativeimpl(timeMark.mo1639elapsedNowUwyO8pc());
        }

        public static boolean hasPassedNow(@NotNull TimeMark timeMark) {
            return !Duration.m1680isNegativeimpl(timeMark.mo1639elapsedNowUwyO8pc());
        }

        @NotNull
        /* JADX INFO: renamed from: minus-LRDsOJo, reason: not valid java name */
        public static TimeMark m1782minusLRDsOJo(@NotNull TimeMark timeMark, long j) {
            return timeMark.mo1641plusLRDsOJo(Duration.m1699unaryMinusUwyO8pc(j));
        }

        @NotNull
        /* JADX INFO: renamed from: plus-LRDsOJo, reason: not valid java name */
        public static TimeMark m1783plusLRDsOJo(@NotNull TimeMark timeMark, long j) {
            return new AdjustedTimeMark(timeMark, j, null);
        }
    }

    /* JADX INFO: renamed from: elapsedNow-UwyO8pc */
    long mo1639elapsedNowUwyO8pc();

    boolean hasNotPassedNow();

    boolean hasPassedNow();

    @NotNull
    /* JADX INFO: renamed from: minus-LRDsOJo */
    TimeMark mo1640minusLRDsOJo(long j);

    @NotNull
    /* JADX INFO: renamed from: plus-LRDsOJo */
    TimeMark mo1641plusLRDsOJo(long j);
}
