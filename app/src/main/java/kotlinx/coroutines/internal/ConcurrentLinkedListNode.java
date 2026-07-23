package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.ConcurrentLinkedListNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ConcurrentLinkedList.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\r\n\u0002\u0010\u0000\n\u0002\b\t\b \u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00002\u00020\u001aB\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\t\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ \u0010\u000e\u001a\u0004\u0018\u00018\u00002\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0086\b¢\u0006\u0004\b\u000e\u0010\u000fJ\r\u0010\u0010\u001a\u00020\u0005¢\u0006\u0004\b\u0010\u0010\u0007J\u0015\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00028\u0000¢\u0006\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\nR\u0016\u0010\u0017\u001a\u0004\u0018\u00018\u00008BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0019\u001a\u0004\u0018\u00018\u00008F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016R\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u001a8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\u0002\u001a\u0004\u0018\u00018\u00008F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0016R\u0014\u0010 \u001a\u00020\b8&X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\nR\u0014\u0010\"\u001a\u00028\u00008BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0016¨\u0006#"}, d2 = {"Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "N", "prev", "<init>", "(Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;)V", "", "cleanPrev", "()V", "", "markAsClosed", "()Z", "Lkotlin/Function0;", "", "onClosedAction", "nextOrIfClosed", "(Lkotlin/jvm/functions/Function0;)Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "remove", "value", "trySetNext", "(Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;)Z", "isTail", "getLeftmostAliveNode", "()Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "leftmostAliveNode", "getNext", "next", "", "getNextOrClosed", "()Ljava/lang/Object;", "nextOrClosed", "getPrev", "getRemoved", "removed", "getRightmostAliveNode", "rightmostAliveNode", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
public abstract class ConcurrentLinkedListNode<N extends ConcurrentLinkedListNode<N>> {
    private static final /* synthetic */ AtomicReferenceFieldUpdater _next$FU = AtomicReferenceFieldUpdater.newUpdater(ConcurrentLinkedListNode.class, Object.class, "_next");
    private static final /* synthetic */ AtomicReferenceFieldUpdater _prev$FU = AtomicReferenceFieldUpdater.newUpdater(ConcurrentLinkedListNode.class, Object.class, "_prev");

    @NotNull
    private volatile /* synthetic */ Object _next = null;

    @NotNull
    private volatile /* synthetic */ Object _prev;

    public ConcurrentLinkedListNode(@Nullable N n) {
        this._prev = n;
    }

    private final N getLeftmostAliveNode() {
        N n = (N) getPrev();
        while (n != null && n.getRemoved()) {
            n = (N) n._prev;
        }
        return n;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getNextOrClosed, reason: from getter */
    public final Object get_next() {
        return this._next;
    }

    private final N getRightmostAliveNode() {
        N n = (N) getNext();
        Intrinsics.checkNotNull(n);
        while (n.getRemoved()) {
            n = (N) n.getNext();
            Intrinsics.checkNotNull(n);
        }
        return n;
    }

    public final void cleanPrev() {
        _prev$FU.lazySet(this, null);
    }

    @Nullable
    public final N getNext() {
        Object obj = get_next();
        if (obj == ConcurrentLinkedListKt.CLOSED) {
            return null;
        }
        return (N) obj;
    }

    @Nullable
    public final N getPrev() {
        return (N) this._prev;
    }

    public abstract boolean getRemoved();

    public final boolean isTail() {
        return getNext() == null;
    }

    public final boolean markAsClosed() {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _next$FU;
        Symbol symbol = ConcurrentLinkedListKt.CLOSED;
        while (!atomicReferenceFieldUpdater.compareAndSet(this, null, symbol)) {
            if (atomicReferenceFieldUpdater.get(this) != null) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public final N nextOrIfClosed(@NotNull Function0 onClosedAction) {
        Object obj = get_next();
        if (obj != ConcurrentLinkedListKt.CLOSED) {
            return (N) obj;
        }
        onClosedAction.invoke();
        throw new KotlinNothingValueException();
    }

    public final void remove() {
        while (true) {
            ConcurrentLinkedListNode leftmostAliveNode = getLeftmostAliveNode();
            ConcurrentLinkedListNode rightmostAliveNode = getRightmostAliveNode();
            rightmostAliveNode._prev = leftmostAliveNode;
            if (leftmostAliveNode != null) {
                leftmostAliveNode._next = rightmostAliveNode;
            }
            if (!rightmostAliveNode.getRemoved() && (leftmostAliveNode == null || !leftmostAliveNode.getRemoved())) {
                return;
            }
        }
    }

    public final boolean trySetNext(@NotNull N value) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _next$FU;
        while (!atomicReferenceFieldUpdater.compareAndSet(this, null, value)) {
            if (atomicReferenceFieldUpdater.get(this) != null) {
                return false;
            }
        }
        return true;
    }
}
