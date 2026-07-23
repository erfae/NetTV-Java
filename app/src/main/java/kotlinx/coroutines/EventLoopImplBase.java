package kotlinx.coroutines;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.time.DurationKt;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: EventLoop.common.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0013\b \u0018\u00002\u00020\u00012\u00020\u0002:\u0004/012B\u0007¢\u0006\u0004\b.\u0010\rJ\u001b\u0010\u0007\u001a\u00020\u00062\n\u0010\u0005\u001a\u00060\u0003j\u0002`\u0004H\u0002¢\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\t\u001a\n\u0018\u00010\u0003j\u0004\u0018\u0001`\u0004H\u0002¢\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u000eH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u001f\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u000eH\u0002¢\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0017\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\u0017\u0010\rJ\u000f\u0010\u0018\u001a\u00020\u000bH\u0016¢\u0006\u0004\b\u0018\u0010\rJ%\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u00112\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001aH\u0016¢\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\u0011H\u0016¢\u0006\u0004\b\u001e\u0010\u001fJ!\u0010#\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020 2\n\u0010\"\u001a\u00060\u0003j\u0002`\u0004¢\u0006\u0004\b#\u0010$J\u001b\u0010%\u001a\u00020\u000b2\n\u0010\u0005\u001a\u00060\u0003j\u0002`\u0004H\u0016¢\u0006\u0004\b%\u0010&J\u001d\u0010'\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u000e¢\u0006\u0004\b'\u0010(R$\u0010*\u001a\u00020\u00062\u0006\u0010)\u001a\u00020\u00068B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-¨\u00063"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase;", "Lkotlinx/coroutines/EventLoopImplPlatform;", "Lkotlinx/coroutines/Delay;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "task", "", "enqueueImpl", "(Ljava/lang/Runnable;)Z", "dequeue", "()Ljava/lang/Runnable;", "", "closeQueue", "()V", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "shouldUnpark", "(Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;)Z", "", "now", "delayedTask", "", "scheduleImpl", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)I", "rescheduleAllDelayed", "shutdown", "timeMillis", "Lkotlinx/coroutines/CancellableContinuation;", "continuation", "scheduleResumeAfterDelay", "(JLkotlinx/coroutines/CancellableContinuation;)V", "processNextEvent", "()J", "Lkotlin/coroutines/CoroutineContext;", "context", "block", "dispatch", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "enqueue", "(Ljava/lang/Runnable;)V", "schedule", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)V", "value", "isCompleted", "()Z", "setCompleted", "(Z)V", "<init>", "DelayedResumeTask", "DelayedRunnableTask", "DelayedTask", "DelayedTaskQueue", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
public abstract class EventLoopImplBase extends EventLoopImplPlatform implements Delay {
    private static final /* synthetic */ AtomicReferenceFieldUpdater _queue$FU = AtomicReferenceFieldUpdater.newUpdater(EventLoopImplBase.class, Object.class, "_queue");
    private static final /* synthetic */ AtomicReferenceFieldUpdater _delayed$FU = AtomicReferenceFieldUpdater.newUpdater(EventLoopImplBase.class, Object.class, "_delayed");

    @NotNull
    private volatile /* synthetic */ Object _queue = null;

    @NotNull
    private volatile /* synthetic */ Object _delayed = null;

    @NotNull
    private volatile /* synthetic */ int _isCompleted = 0;

    /* JADX INFO: compiled from: EventLoop.common.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedResumeTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/EventLoopImplBase;JLkotlinx/coroutines/CancellableContinuation;)V", "run", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public final class DelayedResumeTask extends DelayedTask {

        @NotNull
        private final CancellableContinuation<Unit> cont;

        /* JADX WARN: Multi-variable type inference failed */
        public DelayedResumeTask(@NotNull long j, CancellableContinuation<? super Unit> cancellableContinuation) {
            super(j);
            this.cont = cancellableContinuation;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.cont.resumeUndispatched(EventLoopImplBase.this, Unit.INSTANCE);
        }

        @Override // kotlinx.coroutines.EventLoopImplBase.DelayedTask
        @NotNull
        public String toString() {
            return Intrinsics.stringPlus(super.toString(), this.cont);
        }
    }

    /* JADX INFO: compiled from: EventLoop.common.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0012\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedRunnableTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "(JLjava/lang/Runnable;)V", "run", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class DelayedRunnableTask extends DelayedTask {

        @NotNull
        private final Runnable block;

        public DelayedRunnableTask(long j, @NotNull Runnable runnable) {
            super(j);
            this.block = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.block.run();
        }

        @Override // kotlinx.coroutines.EventLoopImplBase.DelayedTask
        @NotNull
        public String toString() {
            return Intrinsics.stringPlus(super.toString(), this.block);
        }
    }

    /* JADX INFO: compiled from: EventLoop.common.kt */
    @Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b \u0018\u00002\u00060\u0001j\u0002`\u00022\b\u0012\u0004\u0012\u00020\u00000\u00032\u00020\u00042\u00020\u0005B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0011\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0000H\u0096\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\u001e\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020#2\u0006\u0010\u001d\u001a\u00020\u0007J\b\u0010$\u001a\u00020%H\u0016R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R0\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f2\f\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "nanoTime", "", "(J)V", "_heap", "", "value", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "heap", "getHeap", "()Lkotlinx/coroutines/internal/ThreadSafeHeap;", "setHeap", "(Lkotlinx/coroutines/internal/ThreadSafeHeap;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "compareTo", "other", "dispose", "", "scheduleTask", "now", "delayed", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "eventLoop", "Lkotlinx/coroutines/EventLoopImplBase;", "timeToExecute", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static abstract class DelayedTask implements Runnable, Comparable<DelayedTask>, DisposableHandle, ThreadSafeHeapNode {

        @Nullable
        private Object _heap;
        private int index = -1;

        @JvmField
        public long nanoTime;

        public DelayedTask(long j) {
            this.nanoTime = j;
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public final synchronized void dispose() {
            Object obj = this._heap;
            if (obj == EventLoop_commonKt.DISPOSED_TASK) {
                return;
            }
            DelayedTaskQueue delayedTaskQueue = obj instanceof DelayedTaskQueue ? (DelayedTaskQueue) obj : null;
            if (delayedTaskQueue != null) {
                delayedTaskQueue.remove(this);
            }
            this._heap = EventLoop_commonKt.DISPOSED_TASK;
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        @Nullable
        public ThreadSafeHeap<?> getHeap() {
            Object obj = this._heap;
            if (obj instanceof ThreadSafeHeap) {
                return (ThreadSafeHeap) obj;
            }
            return null;
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public int getIndex() {
            return this.index;
        }

        public final synchronized int scheduleTask(long now, @NotNull DelayedTaskQueue delayed, @NotNull EventLoopImplBase eventLoop) {
            if (this._heap == EventLoop_commonKt.DISPOSED_TASK) {
                return 2;
            }
            synchronized (delayed) {
                DelayedTask delayedTaskFirstImpl = delayed.firstImpl();
                if (eventLoop.isCompleted()) {
                    return 1;
                }
                if (delayedTaskFirstImpl == null) {
                    delayed.timeNow = now;
                } else {
                    long j = delayedTaskFirstImpl.nanoTime;
                    if (j - now < 0) {
                        now = j;
                    }
                    if (now - delayed.timeNow > 0) {
                        delayed.timeNow = now;
                    }
                }
                long j2 = this.nanoTime;
                long j3 = delayed.timeNow;
                if (j2 - j3 < 0) {
                    this.nanoTime = j3;
                }
                delayed.addImpl(this);
                return 0;
            }
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public void setHeap(@Nullable ThreadSafeHeap<?> threadSafeHeap) {
            if (!(this._heap != EventLoop_commonKt.DISPOSED_TASK)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            this._heap = threadSafeHeap;
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public void setIndex(int i) {
            this.index = i;
        }

        public final boolean timeToExecute(long now) {
            return now - this.nanoTime >= 0;
        }

        @NotNull
        public String toString() {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Delayed[nanos=");
            sbM.append(this.nanoTime);
            sbM.append(']');
            return sbM.toString();
        }

        @Override // java.lang.Comparable
        public int compareTo(@NotNull DelayedTask other) {
            long j = this.nanoTime - other.nanoTime;
            if (j > 0) {
                return 1;
            }
            return j < 0 ? -1 : 0;
        }
    }

    /* JADX INFO: compiled from: EventLoop.common.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "timeNow", "", "(J)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class DelayedTaskQueue extends ThreadSafeHeap<DelayedTask> {

        @JvmField
        public long timeNow;

        public DelayedTaskQueue(long j) {
            this.timeNow = j;
        }
    }

    private final void closeQueue() {
        while (true) {
            Object obj = this._queue;
            boolean z = false;
            if (obj == null) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _queue$FU;
                Symbol symbol = EventLoop_commonKt.CLOSED_EMPTY;
                do {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, null, symbol)) {
                        z = true;
                        break;
                    }
                } while (atomicReferenceFieldUpdater.get(this) == null);
                if (z) {
                    return;
                }
            } else {
                if (obj instanceof LockFreeTaskQueueCore) {
                    ((LockFreeTaskQueueCore) obj).close();
                    return;
                }
                if (obj == EventLoop_commonKt.CLOSED_EMPTY) {
                    return;
                }
                LockFreeTaskQueueCore lockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
                lockFreeTaskQueueCore.addLast((Runnable) obj);
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = _queue$FU;
                do {
                    if (atomicReferenceFieldUpdater2.compareAndSet(this, obj, lockFreeTaskQueueCore)) {
                        z = true;
                        break;
                    }
                } while (atomicReferenceFieldUpdater2.get(this) == obj);
                if (z) {
                    return;
                }
            }
        }
    }

    private final Runnable dequeue() {
        boolean z;
        while (true) {
            Object obj = this._queue;
            if (obj == null) {
                return null;
            }
            if (obj instanceof LockFreeTaskQueueCore) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) obj;
                Object objRemoveFirstOrNull = lockFreeTaskQueueCore.removeFirstOrNull();
                if (objRemoveFirstOrNull != LockFreeTaskQueueCore.REMOVE_FROZEN) {
                    return (Runnable) objRemoveFirstOrNull;
                }
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _queue$FU;
                LockFreeTaskQueueCore next = lockFreeTaskQueueCore.next();
                while (!atomicReferenceFieldUpdater.compareAndSet(this, obj, next) && atomicReferenceFieldUpdater.get(this) == obj) {
                }
            } else {
                if (obj == EventLoop_commonKt.CLOSED_EMPTY) {
                    return null;
                }
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = _queue$FU;
                while (true) {
                    if (atomicReferenceFieldUpdater2.compareAndSet(this, obj, null)) {
                        z = true;
                        break;
                    }
                    if (atomicReferenceFieldUpdater2.get(this) != obj) {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    return (Runnable) obj;
                }
            }
        }
    }

    private final boolean enqueueImpl(Runnable task) {
        while (true) {
            Object obj = this._queue;
            boolean z = false;
            if (isCompleted()) {
                return false;
            }
            if (obj == null) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _queue$FU;
                do {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, null, task)) {
                        z = true;
                        break;
                    }
                } while (atomicReferenceFieldUpdater.get(this) == null);
                if (z) {
                    return true;
                }
            } else if (obj instanceof LockFreeTaskQueueCore) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) obj;
                int iAddLast = lockFreeTaskQueueCore.addLast(task);
                if (iAddLast == 0) {
                    return true;
                }
                if (iAddLast == 1) {
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = _queue$FU;
                    LockFreeTaskQueueCore next = lockFreeTaskQueueCore.next();
                    while (!atomicReferenceFieldUpdater2.compareAndSet(this, obj, next) && atomicReferenceFieldUpdater2.get(this) == obj) {
                    }
                } else if (iAddLast == 2) {
                    return false;
                }
            } else {
                if (obj == EventLoop_commonKt.CLOSED_EMPTY) {
                    return false;
                }
                LockFreeTaskQueueCore lockFreeTaskQueueCore2 = new LockFreeTaskQueueCore(8, true);
                lockFreeTaskQueueCore2.addLast((Runnable) obj);
                lockFreeTaskQueueCore2.addLast(task);
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3 = _queue$FU;
                do {
                    if (atomicReferenceFieldUpdater3.compareAndSet(this, obj, lockFreeTaskQueueCore2)) {
                        z = true;
                        break;
                    }
                } while (atomicReferenceFieldUpdater3.get(this) == obj);
                if (z) {
                    return true;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [boolean, int] */
    public final boolean isCompleted() {
        return this._isCompleted;
    }

    private final void rescheduleAllDelayed() {
        AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
        Long lValueOf = timeSource == null ? null : Long.valueOf(timeSource.nanoTime());
        long jNanoTime = lValueOf == null ? System.nanoTime() : lValueOf.longValue();
        while (true) {
            DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
            DelayedTask delayedTaskRemoveFirstOrNull = delayedTaskQueue == null ? null : delayedTaskQueue.removeFirstOrNull();
            if (delayedTaskRemoveFirstOrNull == null) {
                return;
            } else {
                reschedule(jNanoTime, delayedTaskRemoveFirstOrNull);
            }
        }
    }

    private final int scheduleImpl(long now, DelayedTask delayedTask) {
        if (isCompleted()) {
            return 1;
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        if (delayedTaskQueue == null) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _delayed$FU;
            DelayedTaskQueue delayedTaskQueue2 = new DelayedTaskQueue(now);
            while (!atomicReferenceFieldUpdater.compareAndSet(this, null, delayedTaskQueue2) && atomicReferenceFieldUpdater.get(this) == null) {
            }
            Object obj = this._delayed;
            Intrinsics.checkNotNull(obj);
            delayedTaskQueue = (DelayedTaskQueue) obj;
        }
        return delayedTask.scheduleTask(now, delayedTaskQueue, this);
    }

    private final void setCompleted(boolean z) {
        this._isCompleted = z ? 1 : 0;
    }

    private final boolean shouldUnpark(DelayedTask task) {
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        return (delayedTaskQueue == null ? null : delayedTaskQueue.peek()) == task;
    }

    @Override // kotlinx.coroutines.Delay
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
    @Nullable
    public Object delay(long j, @NotNull Continuation<? super Unit> continuation) {
        return Delay.DefaultImpls.delay(this, j, continuation);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    /* JADX INFO: renamed from: dispatch */
    public final void mo1868dispatch(@NotNull CoroutineContext context, @NotNull Runnable block) {
        enqueue(block);
    }

    public void enqueue(@NotNull Runnable task) {
        if (enqueueImpl(task)) {
            unpark();
        } else {
            DefaultExecutor.INSTANCE.enqueue(task);
        }
    }

    @NotNull
    public DisposableHandle invokeOnTimeout(long j, @NotNull Runnable runnable, @NotNull CoroutineContext coroutineContext) {
        return Delay.DefaultImpls.invokeOnTimeout(this, j, runnable, coroutineContext);
    }

    public final boolean isEmpty() {
        if (!isUnconfinedQueueEmpty()) {
            return false;
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        if (delayedTaskQueue != null && !delayedTaskQueue.isEmpty()) {
            return false;
        }
        Object obj = this._queue;
        if (obj != null) {
            if (obj instanceof LockFreeTaskQueueCore) {
                return ((LockFreeTaskQueueCore) obj).isEmpty();
            }
            if (obj != EventLoop_commonKt.CLOSED_EMPTY) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:53:0x008b  */
    /* JADX WARN: Code duplicated, block: B:54:0x008d  */
    /* JADX WARN: Code duplicated, block: B:57:0x0096  */
    /* JADX WARN: Code duplicated, block: B:60:0x009f  */
    /* JADX WARN: Code duplicated, block: B:62:0x00a9  */
    /* JADX WARN: Code duplicated, block: B:63:0x00ae  */
    @Override // kotlinx.coroutines.EventLoop
    public long processNextEvent() {
        DelayedTaskQueue delayedTaskQueue;
        DelayedTask delayedTaskPeek;
        Long lValueOf;
        long jLongValue;
        DelayedTask delayedTaskRemoveAtImpl;
        if (processUnconfinedEvent()) {
            return 0L;
        }
        DelayedTaskQueue delayedTaskQueue2 = (DelayedTaskQueue) this._delayed;
        if (delayedTaskQueue2 != null && !delayedTaskQueue2.isEmpty()) {
            AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
            Long lValueOf2 = timeSource == null ? null : Long.valueOf(timeSource.nanoTime());
            long jNanoTime = lValueOf2 == null ? System.nanoTime() : lValueOf2.longValue();
            do {
                synchronized (delayedTaskQueue2) {
                    DelayedTask delayedTaskFirstImpl = delayedTaskQueue2.firstImpl();
                    if (delayedTaskFirstImpl == null) {
                        delayedTaskRemoveAtImpl = null;
                    } else {
                        DelayedTask delayedTask = delayedTaskFirstImpl;
                        delayedTaskRemoveAtImpl = delayedTask.timeToExecute(jNanoTime) ? enqueueImpl(delayedTask) : false ? delayedTaskQueue2.removeAtImpl(0) : null;
                    }
                }
            } while (delayedTaskRemoveAtImpl != null);
        }
        Runnable runnableDequeue = dequeue();
        if (runnableDequeue != null) {
            runnableDequeue.run();
            return 0L;
        }
        if (super.getNextTime() == 0) {
            return 0L;
        }
        Object obj = this._queue;
        if (obj == null) {
            delayedTaskQueue = (DelayedTaskQueue) this._delayed;
            if (delayedTaskQueue == null) {
                delayedTaskPeek = null;
            } else {
                delayedTaskPeek = delayedTaskQueue.peek();
            }
            if (delayedTaskPeek != null) {
                long j = delayedTaskPeek.nanoTime;
                AbstractTimeSource timeSource2 = AbstractTimeSourceKt.getTimeSource();
                if (timeSource2 != null) {
                }
                if (lValueOf == null) {
                    jLongValue = System.nanoTime();
                } else {
                    jLongValue = lValueOf.longValue();
                }
                return RangesKt.coerceAtLeast(j - jLongValue, 0L);
            }
        } else if (obj instanceof LockFreeTaskQueueCore) {
            if (!((LockFreeTaskQueueCore) obj).isEmpty()) {
                return 0L;
            }
            delayedTaskQueue = (DelayedTaskQueue) this._delayed;
            if (delayedTaskQueue == null) {
                delayedTaskPeek = null;
            } else {
                delayedTaskPeek = delayedTaskQueue.peek();
            }
            if (delayedTaskPeek != null) {
                long j2 = delayedTaskPeek.nanoTime;
                AbstractTimeSource timeSource3 = AbstractTimeSourceKt.getTimeSource();
                lValueOf = timeSource3 != null ? Long.valueOf(timeSource3.nanoTime()) : null;
                if (lValueOf == null) {
                    jLongValue = System.nanoTime();
                } else {
                    jLongValue = lValueOf.longValue();
                }
                return RangesKt.coerceAtLeast(j2 - jLongValue, 0L);
            }
        } else if (obj != EventLoop_commonKt.CLOSED_EMPTY) {
            return 0L;
        }
        return Long.MAX_VALUE;
    }

    public final void resetAll() {
        this._queue = null;
        this._delayed = null;
    }

    public final void schedule(long now, @NotNull DelayedTask delayedTask) {
        int iScheduleImpl = scheduleImpl(now, delayedTask);
        if (iScheduleImpl == 0) {
            if (shouldUnpark(delayedTask)) {
                unpark();
            }
        } else if (iScheduleImpl == 1) {
            reschedule(now, delayedTask);
        } else if (iScheduleImpl != 2) {
            throw new IllegalStateException("unexpected result".toString());
        }
    }

    @Override // kotlinx.coroutines.Delay
    /* JADX INFO: renamed from: scheduleResumeAfterDelay */
    public void mo1869scheduleResumeAfterDelay(long timeMillis, @NotNull CancellableContinuation<? super Unit> continuation) {
        long jDelayToNanos = EventLoop_commonKt.delayToNanos(timeMillis);
        if (jDelayToNanos < DurationKt.MAX_MILLIS) {
            AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
            Long lValueOf = timeSource == null ? null : Long.valueOf(timeSource.nanoTime());
            long jNanoTime = lValueOf == null ? System.nanoTime() : lValueOf.longValue();
            DelayedResumeTask delayedResumeTask = new DelayedResumeTask(jDelayToNanos + jNanoTime, continuation);
            CancellableContinuationKt.disposeOnCancellation(continuation, delayedResumeTask);
            schedule(jNanoTime, delayedResumeTask);
        }
    }

    @Override // kotlinx.coroutines.EventLoop
    public void shutdown() {
        ThreadLocalEventLoop.INSTANCE.resetEventLoop$kotlinx_coroutines_core();
        setCompleted(true);
        closeQueue();
        while (processNextEvent() <= 0) {
        }
        rescheduleAllDelayed();
    }
}
