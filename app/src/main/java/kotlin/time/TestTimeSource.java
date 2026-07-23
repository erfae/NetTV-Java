package kotlin.time;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import kotlin.Metadata;
import kotlin.SinceKotlin;

/* JADX INFO: compiled from: TimeSources.kt */
/* JADX INFO: loaded from: classes2.dex */
@SinceKotlin(version = "1.3")
@Metadata(bv = {}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\r\u0010\u000eJ\u001a\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006J\u001b\u0010\t\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\b\u0010\u0006R\u0016\u0010\u000b\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u000b\u0010\f\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, d2 = {"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "Lkotlin/time/Duration;", TypedValues.TransitionType.S_DURATION, "", "overflow-LRDsOJo", "(J)V", "overflow", "plusAssign-LRDsOJo", "plusAssign", "", "reading", "J", "<init>", "()V", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1})
@ExperimentalTime
public final class TestTimeSource extends AbstractLongTimeSource {
    private long reading;

    public TestTimeSource() {
        super(DurationUnit.NANOSECONDS);
    }

    /* JADX INFO: renamed from: overflow-LRDsOJo, reason: not valid java name */
    private final void m1780overflowLRDsOJo(long duration) {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("TestTimeSource will overflow if its reading ");
        sbM.append(this.reading);
        sbM.append("ns is advanced by ");
        sbM.append((Object) Duration.m1696toStringimpl(duration));
        sbM.append('.');
        throw new IllegalStateException(sbM.toString());
    }

    /* JADX INFO: renamed from: plusAssign-LRDsOJo, reason: not valid java name */
    public final void m1781plusAssignLRDsOJo(long duration) {
        long j;
        long jM1693toLongimpl = Duration.m1693toLongimpl(duration, getUnit());
        if (jM1693toLongimpl == Long.MIN_VALUE || jM1693toLongimpl == Long.MAX_VALUE) {
            double dM1690toDoubleimpl = this.reading + Duration.m1690toDoubleimpl(duration, getUnit());
            if (dM1690toDoubleimpl > 9.223372036854776E18d || dM1690toDoubleimpl < -9.223372036854776E18d) {
                m1780overflowLRDsOJo(duration);
            }
            j = (long) dM1690toDoubleimpl;
        } else {
            long j2 = this.reading;
            j = j2 + jM1693toLongimpl;
            if ((jM1693toLongimpl ^ j2) >= 0 && (j2 ^ j) < 0) {
                m1780overflowLRDsOJo(duration);
            }
        }
        this.reading = j;
    }

    @Override // kotlin.time.AbstractLongTimeSource
    /* JADX INFO: renamed from: read, reason: from getter */
    public final long getReading() {
        return this.reading;
    }
}
