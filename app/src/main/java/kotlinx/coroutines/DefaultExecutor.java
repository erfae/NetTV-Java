package kotlinx.coroutines;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.ranges.RangesKt;
import kotlin.time.DurationKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: DefaultExecutor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u000e\bÀ\u0002\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003B\t\b\u0002¢\u0006\u0004\b1\u0010\u0017J\b\u0010\u0005\u001a\u00020\u0004H\u0002J\b\u0010\u0007\u001a\u00020\u0006H\u0002J\b\u0010\t\u001a\u00020\bH\u0002J\b\u0010\n\u001a\u00020\u0004H\u0002J\u0014\u0010\f\u001a\u00020\u00042\n\u0010\u000b\u001a\u00060\u0002j\u0002`\u0003H\u0016J\b\u0010\r\u001a\u00020\u0004H\u0016J$\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u000e2\n\u0010\u0010\u001a\u00060\u0002j\u0002`\u00032\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J\b\u0010\u0015\u001a\u00020\u0004H\u0016J\u000f\u0010\u0018\u001a\u00020\u0004H\u0000¢\u0006\u0004\b\u0016\u0010\u0017J\u000e\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u000eR\u0014\u0010\u001c\u001a\u00020\u001b8\u0006X\u0086T¢\u0006\u0006\n\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u000e8\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020\u000e8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b \u0010\u001fR\u001e\u0010!\u001a\u0004\u0018\u00010\u00068\u0002@\u0002X\u0082\u000e¢\u0006\f\n\u0004\b!\u0010\"\u0012\u0004\b#\u0010\u0017R\u0014\u0010%\u001a\u00020$8\u0002X\u0082T¢\u0006\u0006\n\u0004\b%\u0010&R\u0014\u0010'\u001a\u00020$8\u0002X\u0082T¢\u0006\u0006\n\u0004\b'\u0010&R\u0014\u0010(\u001a\u00020$8\u0002X\u0082T¢\u0006\u0006\n\u0004\b(\u0010&R\u0014\u0010)\u001a\u00020$8\u0002X\u0082T¢\u0006\u0006\n\u0004\b)\u0010&R\u0014\u0010*\u001a\u00020$8\u0002X\u0082T¢\u0006\u0006\n\u0004\b*\u0010&R\u0016\u0010+\u001a\u00020$8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b+\u0010&R\u0014\u0010,\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0014\u0010.\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b.\u0010-R\u0014\u00100\u001a\u00020\b8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b/\u0010-¨\u00062"}, d2 = {"Lkotlinx/coroutines/DefaultExecutor;", "Lkotlinx/coroutines/EventLoopImplBase;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "", "shutdownError", "Ljava/lang/Thread;", "createThreadSync", "", "notifyStartup", "acknowledgeShutdownIfNeeded", "task", "enqueue", "shutdown", "", "timeMillis", "block", "Lkotlin/coroutines/CoroutineContext;", "context", "Lkotlinx/coroutines/DisposableHandle;", "invokeOnTimeout", "run", "ensureStarted$kotlinx_coroutines_core", "()V", "ensureStarted", "timeout", "shutdownForTests", "", "THREAD_NAME", "Ljava/lang/String;", "DEFAULT_KEEP_ALIVE_MS", "J", "KEEP_ALIVE_NANOS", "_thread", "Ljava/lang/Thread;", "get_thread$annotations", "", "FRESH", "I", "ACTIVE", "SHUTDOWN_REQ", "SHUTDOWN_ACK", "SHUTDOWN", "debugStatus", "isShutDown", "()Z", "isShutdownRequested", "isThreadPresent$kotlinx_coroutines_core", "isThreadPresent", "<init>", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
public final class DefaultExecutor extends EventLoopImplBase implements Runnable {
    private static final int ACTIVE = 1;
    private static final long DEFAULT_KEEP_ALIVE_MS = 1000;
    private static final int FRESH = 0;

    @NotNull
    public static final DefaultExecutor INSTANCE;
    private static final long KEEP_ALIVE_NANOS;
    private static final int SHUTDOWN = 4;
    private static final int SHUTDOWN_ACK = 3;
    private static final int SHUTDOWN_REQ = 2;

    @NotNull
    public static final String THREAD_NAME = "kotlinx.coroutines.DefaultExecutor";

    @Nullable
    private static volatile Thread _thread;
    private static volatile int debugStatus;

    static {
        Long l;
        DefaultExecutor defaultExecutor = new DefaultExecutor();
        INSTANCE = defaultExecutor;
        EventLoop.incrementUseCount$default(defaultExecutor, false, 1, null);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        try {
            l = Long.getLong("kotlinx.coroutines.DefaultExecutor.keepAlive", 1000L);
        } catch (SecurityException unused) {
            l = 1000L;
        }
        KEEP_ALIVE_NANOS = timeUnit.toNanos(l.longValue());
    }

    private DefaultExecutor() {
    }

    private final synchronized void acknowledgeShutdownIfNeeded() {
        if (isShutdownRequested()) {
            debugStatus = 3;
            resetAll();
            notifyAll();
        }
    }

    private final synchronized Thread createThreadSync() {
        Thread thread;
        thread = _thread;
        if (thread == null) {
            thread = new Thread(this, THREAD_NAME);
            _thread = thread;
            thread.setDaemon(true);
            thread.start();
        }
        return thread;
    }

    private static /* synthetic */ void get_thread$annotations() {
    }

    private final boolean isShutDown() {
        return debugStatus == 4;
    }

    private final boolean isShutdownRequested() {
        int i = debugStatus;
        return i == 2 || i == 3;
    }

    private final synchronized boolean notifyStartup() {
        if (isShutdownRequested()) {
            return false;
        }
        debugStatus = 1;
        notifyAll();
        return true;
    }

    private final void shutdownError() {
        throw new RejectedExecutionException("DefaultExecutor was shut down. This error indicates that Dispatchers.shutdown() was invoked prior to completion of exiting coroutines, leaving coroutines in incomplete state. Please refer to Dispatchers.shutdown documentation for more details");
    }

    @Override // kotlinx.coroutines.EventLoopImplBase
    public void enqueue(@NotNull Runnable task) {
        if (isShutDown()) {
            shutdownError();
        }
        super.enqueue(task);
    }

    public final synchronized void ensureStarted$kotlinx_coroutines_core() {
        debugStatus = 0;
        createThreadSync();
        while (debugStatus == 0) {
            wait();
        }
    }

    @Override // kotlinx.coroutines.EventLoopImplPlatform
    @NotNull
    public final Thread getThread() {
        Thread thread = _thread;
        return thread == null ? createThreadSync() : thread;
    }

    @Override // kotlinx.coroutines.EventLoopImplBase, kotlinx.coroutines.Delay
    @NotNull
    public DisposableHandle invokeOnTimeout(long timeMillis, @NotNull Runnable block, @NotNull CoroutineContext context) {
        long jDelayToNanos = EventLoop_commonKt.delayToNanos(timeMillis);
        if (jDelayToNanos >= DurationKt.MAX_MILLIS) {
            return NonDisposableHandle.INSTANCE;
        }
        AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
        Long lValueOf = timeSource == null ? null : Long.valueOf(timeSource.nanoTime());
        long jNanoTime = lValueOf == null ? System.nanoTime() : lValueOf.longValue();
        EventLoopImplBase.DelayedRunnableTask delayedRunnableTask = new EventLoopImplBase.DelayedRunnableTask(jDelayToNanos + jNanoTime, block);
        schedule(jNanoTime, delayedRunnableTask);
        return delayedRunnableTask;
    }

    public final boolean isThreadPresent$kotlinx_coroutines_core() {
        return _thread != null;
    }

    @Override // kotlinx.coroutines.EventLoopImplPlatform
    public final void reschedule(long j, @NotNull EventLoopImplBase.DelayedTask delayedTask) {
        shutdownError();
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean zIsEmpty;
        Unit unit;
        ThreadLocalEventLoop.INSTANCE.setEventLoop$kotlinx_coroutines_core(this);
        AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
        if (timeSource != null) {
            timeSource.registerTimeLoopThread();
        }
        try {
            if (!notifyStartup()) {
                if (zIsEmpty) {
                    return;
                } else {
                    return;
                }
            }
            long j = Long.MAX_VALUE;
            while (true) {
                Thread.interrupted();
                long jProcessNextEvent = processNextEvent();
                if (jProcessNextEvent == Long.MAX_VALUE) {
                    AbstractTimeSource timeSource2 = AbstractTimeSourceKt.getTimeSource();
                    Long lValueOf = timeSource2 == null ? null : Long.valueOf(timeSource2.nanoTime());
                    long jNanoTime = lValueOf == null ? System.nanoTime() : lValueOf.longValue();
                    if (j == Long.MAX_VALUE) {
                        j = KEEP_ALIVE_NANOS + jNanoTime;
                    }
                    long j2 = j - jNanoTime;
                    if (j2 <= 0) {
                        if (zIsEmpty) {
                            return;
                        } else {
                            return;
                        }
                    }
                    jProcessNextEvent = RangesKt.coerceAtMost(jProcessNextEvent, j2);
                } else {
                    j = Long.MAX_VALUE;
                }
                if (jProcessNextEvent > 0) {
                    if (isShutdownRequested()) {
                        if (zIsEmpty) {
                            return;
                        } else {
                            return;
                        }
                    }
                    AbstractTimeSource timeSource3 = AbstractTimeSourceKt.getTimeSource();
                    if (timeSource3 == null) {
                        unit = null;
                    } else {
                        timeSource3.parkNanos(this, jProcessNextEvent);
                        unit = Unit.INSTANCE;
                    }
                    if (unit == null) {
                        LockSupport.parkNanos(this, jProcessNextEvent);
                    }
                }
            }
        } finally {
            _thread = null;
            acknowledgeShutdownIfNeeded();
            AbstractTimeSource timeSource4 = AbstractTimeSourceKt.getTimeSource();
            if (timeSource4 != null) {
                timeSource4.unregisterTimeLoopThread();
            }
            if (!isEmpty()) {
                getThread();
            }
        }
    }

    @Override // kotlinx.coroutines.EventLoopImplBase, kotlinx.coroutines.EventLoop
    public void shutdown() {
        debugStatus = 4;
        super.shutdown();
    }

    public final synchronized void shutdownForTests(long timeout) {
        Unit unit;
        long jCurrentTimeMillis = System.currentTimeMillis() + timeout;
        if (!isShutdownRequested()) {
            debugStatus = 2;
        }
        while (debugStatus != 3 && _thread != null) {
            Thread thread = _thread;
            if (thread != null) {
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
            if (jCurrentTimeMillis - System.currentTimeMillis() <= 0) {
                break;
            } else {
                wait(timeout);
            }
        }
        debugStatus = 0;
    }
}
