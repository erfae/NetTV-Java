package kotlinx.coroutines.sync;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import kotlin.Metadata;
import kotlin.Unit;
import kotlinx.coroutines.CancelHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Semaphore.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lkotlinx/coroutines/sync/CancelSemaphoreAcquisitionHandler;", "Lkotlinx/coroutines/CancelHandler;", "segment", "Lkotlinx/coroutines/sync/SemaphoreSegment;", "index", "", "(Lkotlinx/coroutines/sync/SemaphoreSegment;I)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
final class CancelSemaphoreAcquisitionHandler extends CancelHandler {
    private final int index;

    @NotNull
    private final SemaphoreSegment segment;

    public CancelSemaphoreAcquisitionHandler(@NotNull SemaphoreSegment semaphoreSegment, int i) {
        this.segment = semaphoreSegment;
        this.index = i;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke2(th);
        return Unit.INSTANCE;
    }

    @NotNull
    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("CancelSemaphoreAcquisitionHandler[");
        sbM.append(this.segment);
        sbM.append(", ");
        return Insets$$ExternalSyntheticOutline0.m(sbM, this.index, ']');
    }

    @Override // kotlinx.coroutines.CancelHandlerBase
    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public void invoke2(@Nullable Throwable cause) {
        this.segment.cancel(this.index);
    }
}
