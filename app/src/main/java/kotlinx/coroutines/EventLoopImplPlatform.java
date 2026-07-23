package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: EventLoop.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b \u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lkotlinx/coroutines/EventLoopImplPlatform;", "Lkotlinx/coroutines/EventLoop;", "<init>", "()V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
public abstract class EventLoopImplPlatform extends EventLoop {
    @NotNull
    public abstract Thread getThread();

    public void reschedule(long j, @NotNull EventLoopImplBase.DelayedTask delayedTask) {
        DefaultExecutor.INSTANCE.schedule(j, delayedTask);
    }

    public final void unpark() {
        Unit unit;
        Thread thread = getThread();
        if (Thread.currentThread() != thread) {
            AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
            if (timeSource == null) {
                unit = null;
            } else {
                timeSource.unpark(thread);
                unit = Unit.INSTANCE;
            }
            if (unit == null) {
                LockSupport.unpark(thread);
            }
        }
    }
}
