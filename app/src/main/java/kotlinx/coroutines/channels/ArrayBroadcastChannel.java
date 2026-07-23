package kotlinx.coroutines.channels;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.internal.ConcurrentKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ArrayBroadcastChannel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003:\u0001>B\u000f\u0012\u0006\u0010 \u001a\u00020\u001f¢\u0006\u0004\b=\u0010;J\u0019\u0010\u0007\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0002¢\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0002¢\u0006\u0004\b\n\u0010\u000bJ4\u0010\u000f\u001a\u00020\t2\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\f2\u0010\b\u0002\u0010\u000e\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\fH\u0082\u0010¢\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0012\u001a\u00020\u0011H\u0002¢\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00020\u0011H\u0002¢\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017H\u0016¢\u0006\u0004\b\u0018\u0010\u0019J\u0019\u0010\u001a\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0004\b\u001a\u0010\bJ\u0019\u0010\u001b\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0017¢\u0006\u0004\b\u001b\u0010\bJ\u001f\u0010\u001b\u001a\u00020\t2\u000e\u0010\u0005\u001a\n\u0018\u00010\u001cj\u0004\u0018\u0001`\u001dH\u0016¢\u0006\u0004\b\u001b\u0010\u001eR\u0017\u0010 \u001a\u00020\u001f8\u0006¢\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R\u0018\u0010&\u001a\u00060$j\u0002`%8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b&\u0010'R\u001c\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010)0(8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b*\u0010+R6\u0010.\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f0,j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f`-8\u0002X\u0082\u0004¢\u0006\f\n\u0004\b.\u0010/\u0012\u0004\b0\u0010\u000bR$\u00105\u001a\u00020\u00112\u0006\u00101\u001a\u00020\u00118B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b2\u0010\u0013\"\u0004\b3\u00104R$\u00108\u001a\u00020\u00112\u0006\u00101\u001a\u00020\u00118B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b6\u0010\u0013\"\u0004\b7\u00104R$\u0010<\u001a\u00020\u001f2\u0006\u00101\u001a\u00020\u001f8B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b9\u0010#\"\u0004\b:\u0010;¨\u0006?"}, d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/BroadcastChannel;", "", "cause", "", "cancelInternal", "(Ljava/lang/Throwable;)Z", "", "checkSubOffers", "()V", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "addSub", "removeSub", "updateHead", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;)V", "", "computeMinHead", "()J", "index", "elementAt", "(J)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "close", "cancel", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "(Ljava/util/concurrent/CancellationException;)V", "", "capacity", "I", "getCapacity", "()I", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "bufferLock", "Ljava/util/concurrent/locks/ReentrantLock;", "", "", "buffer", "[Ljava/lang/Object;", "", "Lkotlinx/coroutines/internal/SubscribersList;", "subscribers", "Ljava/util/List;", "getSubscribers$annotations", "value", "getHead", "setHead", "(J)V", TtmlNode.TAG_HEAD, "getTail", "setTail", "tail", "getSize", "setSize", "(I)V", "size", "<init>", "Subscriber", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
public final class ArrayBroadcastChannel<E> extends AbstractSendChannel<E> implements BroadcastChannel<E> {

    @NotNull
    private volatile /* synthetic */ long _head;

    @NotNull
    private volatile /* synthetic */ int _size;

    @NotNull
    private volatile /* synthetic */ long _tail;

    @NotNull
    private final Object[] buffer;

    @NotNull
    private final ReentrantLock bufferLock;
    private final int capacity;

    @NotNull
    private final List<Subscriber<E>> subscribers;

    /* JADX INFO: compiled from: ArrayBroadcastChannel.kt */
    @Metadata(bv = {}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\t\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010\u00022\b\u0012\u0004\u0012\u00028\u00010\u0003B\u0015\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u000f¢\u0006\u0004\b\u001d\u0010\u001eJ\u000f\u0010\u0005\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0005\u0010\u0006J\u0011\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0002¢\u0006\u0004\b\b\u0010\tJ\u0019\u0010\f\u001a\u00020\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0016¢\u0006\u0004\b\f\u0010\rJ\r\u0010\u000e\u001a\u00020\u0004¢\u0006\u0004\b\u000e\u0010\u0006R\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u000f8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0014\u001a\u00060\u0012j\u0002`\u00138\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0014\u0010\u0015R$\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00168F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006\u001f"}, d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/AbstractChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "", "needsToCheckOfferWithoutLock", "()Z", "", "peekUnderLock", "()Ljava/lang/Object;", "", "cause", "close", "(Ljava/lang/Throwable;)Z", "checkOffer", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "broadcastChannel", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "subLock", "Ljava/util/concurrent/locks/ReentrantLock;", "", "value", "getSubHead", "()J", "setSubHead", "(J)V", "subHead", "<init>", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
    public static final class Subscriber<E> extends AbstractChannel<E> implements ReceiveChannel<E> {

        @NotNull
        private volatile /* synthetic */ long _subHead;

        @NotNull
        private final ArrayBroadcastChannel<E> broadcastChannel;

        @NotNull
        private final ReentrantLock subLock;

        public Subscriber(@NotNull ArrayBroadcastChannel<E> arrayBroadcastChannel) {
            super(null);
            this.broadcastChannel = arrayBroadcastChannel;
            this.subLock = new ReentrantLock();
            this._subHead = 0L;
        }

        private final boolean needsToCheckOfferWithoutLock() {
            if (getClosedForReceive() != null) {
                return false;
            }
            return (isBufferEmpty() && this.broadcastChannel.getClosedForReceive() == null) ? false : true;
        }

        private final Object peekUnderLock() {
            long j = get_subHead();
            Closed<?> closedForReceive = this.broadcastChannel.getClosedForReceive();
            if (j >= this.broadcastChannel.get_tail()) {
                if (closedForReceive == null) {
                    closedForReceive = getClosedForReceive();
                }
                return closedForReceive == null ? AbstractChannelKt.POLL_FAILED : closedForReceive;
            }
            Object objElementAt = this.broadcastChannel.elementAt(j);
            Closed<?> closedForReceive2 = getClosedForReceive();
            return closedForReceive2 != null ? closedForReceive2 : objElementAt;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final boolean checkOffer() {
            Closed closed;
            boolean z = false;
            while (true) {
                closed = null;
                if (!needsToCheckOfferWithoutLock() || !this.subLock.tryLock()) {
                    break;
                }
                try {
                    Object objPeekUnderLock = peekUnderLock();
                    if (objPeekUnderLock != AbstractChannelKt.POLL_FAILED) {
                        if (objPeekUnderLock instanceof Closed) {
                            closed = (Closed) objPeekUnderLock;
                        } else {
                            ReceiveOrClosed<E> receiveOrClosedTakeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
                            if (receiveOrClosedTakeFirstReceiveOrPeekClosed != 0 && !(receiveOrClosedTakeFirstReceiveOrPeekClosed instanceof Closed)) {
                                if (receiveOrClosedTakeFirstReceiveOrPeekClosed.tryResumeReceive(objPeekUnderLock, null) != null) {
                                    setSubHead(get_subHead() + 1);
                                    z = true;
                                    this.subLock.unlock();
                                    receiveOrClosedTakeFirstReceiveOrPeekClosed.completeResumeReceive(objPeekUnderLock);
                                }
                            }
                        }
                        this.subLock.unlock();
                        break;
                    }
                    this.subLock.unlock();
                } catch (Throwable th) {
                    this.subLock.unlock();
                    throw th;
                }
            }
            if (closed != null) {
                close(closed.closeCause);
            }
            return z;
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel, kotlinx.coroutines.channels.SendChannel
        public boolean close(@Nullable Throwable cause) {
            boolean zClose = super.close(cause);
            if (zClose) {
                ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, this, 1);
                ReentrantLock reentrantLock = this.subLock;
                reentrantLock.lock();
                try {
                    setSubHead(this.broadcastChannel.get_tail());
                    Unit unit = Unit.INSTANCE;
                } finally {
                    reentrantLock.unlock();
                }
            }
            return zClose;
        }

        /* JADX INFO: renamed from: getSubHead, reason: from getter */
        public final long get_subHead() {
            return this._subHead;
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        public final boolean isBufferAlwaysEmpty() {
            return false;
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel
        public final boolean isBufferAlwaysFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        public final boolean isBufferEmpty() {
            return get_subHead() >= this.broadcastChannel.get_tail();
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel
        public final boolean isBufferFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        @Nullable
        public final Object pollInternal() {
            boolean z;
            ReentrantLock reentrantLock = this.subLock;
            reentrantLock.lock();
            try {
                Object objPeekUnderLock = peekUnderLock();
                if ((objPeekUnderLock instanceof Closed) || objPeekUnderLock == AbstractChannelKt.POLL_FAILED) {
                    z = false;
                } else {
                    setSubHead(get_subHead() + 1);
                    z = true;
                }
                reentrantLock.unlock();
                Closed closed = objPeekUnderLock instanceof Closed ? (Closed) objPeekUnderLock : null;
                if (closed != null) {
                    close(closed.closeCause);
                }
                if (checkOffer() ? true : z) {
                    ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3);
                }
                return objPeekUnderLock;
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        @Nullable
        public final Object pollSelectInternal(@NotNull SelectInstance<?> selectInstance) {
            ReentrantLock reentrantLock = this.subLock;
            reentrantLock.lock();
            try {
                Object objPeekUnderLock = peekUnderLock();
                boolean z = false;
                if (!(objPeekUnderLock instanceof Closed) && objPeekUnderLock != AbstractChannelKt.POLL_FAILED) {
                    if (selectInstance.trySelect()) {
                        setSubHead(get_subHead() + 1);
                        z = true;
                    } else {
                        objPeekUnderLock = SelectKt.getALREADY_SELECTED();
                    }
                }
                reentrantLock.unlock();
                Closed closed = objPeekUnderLock instanceof Closed ? (Closed) objPeekUnderLock : null;
                if (closed != null) {
                    close(closed.closeCause);
                }
                if (checkOffer() ? true : z) {
                    ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3);
                }
                return objPeekUnderLock;
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }

        public final void setSubHead(long j) {
            this._subHead = j;
        }
    }

    public ArrayBroadcastChannel(int i) {
        super(null);
        this.capacity = i;
        if (!(i >= 1)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("ArrayBroadcastChannel capacity must be at least 1, but ");
            sbM.append(getCapacity());
            sbM.append(" was specified");
            throw new IllegalArgumentException(sbM.toString().toString());
        }
        this.bufferLock = new ReentrantLock();
        this.buffer = new Object[i];
        this._head = 0L;
        this._tail = 0L;
        this._size = 0;
        this.subscribers = ConcurrentKt.subscriberList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // kotlinx.coroutines.channels.BroadcastChannel
    /* JADX INFO: renamed from: cancelInternal, reason: merged with bridge method [inline-methods] */
    public final boolean cancel(Throwable cause) {
        boolean zClose = close(cause);
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        while (it.hasNext()) {
            it.next().cancel(cause);
        }
        return zClose;
    }

    private final void checkSubOffers() {
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            if (it.next().checkOffer()) {
                z = true;
            }
            z2 = true;
        }
        if (z || !z2) {
            updateHead$default(this, null, null, 3);
        }
    }

    private final long computeMinHead() {
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        long jCoerceAtMost = Long.MAX_VALUE;
        while (it.hasNext()) {
            jCoerceAtMost = RangesKt.coerceAtMost(jCoerceAtMost, it.next().get_subHead());
        }
        return jCoerceAtMost;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final E elementAt(long index) {
        return (E) this.buffer[(int) (index % ((long) this.capacity))];
    }

    /* JADX INFO: renamed from: getHead, reason: from getter */
    private final long get_head() {
        return this._head;
    }

    /* JADX INFO: renamed from: getSize, reason: from getter */
    private final int get_size() {
        return this._size;
    }

    private static /* synthetic */ void getSubscribers$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getTail, reason: from getter */
    public final long get_tail() {
        return this._tail;
    }

    private final void setHead(long j) {
        this._head = j;
    }

    private final void setSize(int i) {
        this._size = i;
    }

    private final void setTail(long j) {
        this._tail = j;
    }

    private final void updateHead(Subscriber<E> addSub, Subscriber<E> removeSub) {
        Send sendTakeFirstSendOrPeekClosed;
        while (true) {
            ReentrantLock reentrantLock = this.bufferLock;
            reentrantLock.lock();
            if (addSub != null) {
                try {
                    addSub.setSubHead(get_tail());
                    boolean zIsEmpty = this.subscribers.isEmpty();
                    this.subscribers.add(addSub);
                    if (!zIsEmpty) {
                        reentrantLock.unlock();
                        return;
                    }
                } catch (Throwable th) {
                    reentrantLock.unlock();
                    throw th;
                }
            }
            if (removeSub != null) {
                this.subscribers.remove(removeSub);
                if (get_head() != removeSub.get_subHead()) {
                    reentrantLock.unlock();
                    return;
                }
            }
            long jComputeMinHead = computeMinHead();
            long j = get_tail();
            long j2 = get_head();
            long jCoerceAtMost = RangesKt.coerceAtMost(jComputeMinHead, j);
            if (jCoerceAtMost <= j2) {
                reentrantLock.unlock();
                return;
            }
            int i = get_size();
            while (true) {
                if (j2 >= jCoerceAtMost) {
                    reentrantLock.unlock();
                    return;
                }
                this.buffer[(int) (j2 % ((long) getCapacity()))] = null;
                boolean z = i >= getCapacity();
                j2++;
                setHead(j2);
                i--;
                setSize(i);
                if (z) {
                    while (true) {
                        sendTakeFirstSendOrPeekClosed = takeFirstSendOrPeekClosed();
                        if (sendTakeFirstSendOrPeekClosed != null && !(sendTakeFirstSendOrPeekClosed instanceof Closed)) {
                            if (sendTakeFirstSendOrPeekClosed.tryResumeSend(null) != null) {
                                break;
                            }
                        }
                    }
                }
            }
            this.buffer[(int) (j % ((long) getCapacity()))] = sendTakeFirstSendOrPeekClosed.getElement();
            setSize(i + 1);
            setTail(j + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            sendTakeFirstSendOrPeekClosed.completeResumeSend();
            checkSubOffers();
            addSub = null;
            removeSub = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void updateHead$default(ArrayBroadcastChannel arrayBroadcastChannel, Subscriber subscriber, Subscriber subscriber2, int i) {
        if ((i & 1) != 0) {
            subscriber = null;
        }
        if ((i & 2) != 0) {
            subscriber2 = null;
        }
        arrayBroadcastChannel.updateHead(subscriber, subscriber2);
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel, kotlinx.coroutines.channels.SendChannel
    public boolean close(@Nullable Throwable cause) {
        if (!super.close(cause)) {
            return false;
        }
        checkSubOffers();
        return true;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    public final String getBufferDebugString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("(buffer:capacity=");
        sbM.append(this.buffer.length);
        sbM.append(",size=");
        return Insets$$ExternalSyntheticOutline0.m(sbM, get_size(), ')');
    }

    public final int getCapacity() {
        return this.capacity;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferAlwaysFull() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferFull() {
        return get_size() >= this.capacity;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    public final Object offerInternal(E e) {
        ReentrantLock reentrantLock = this.bufferLock;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                reentrantLock.unlock();
                return closedForSend;
            }
            int i = get_size();
            if (i >= getCapacity()) {
                Symbol symbol = AbstractChannelKt.OFFER_FAILED;
                reentrantLock.unlock();
                return symbol;
            }
            long j = get_tail();
            this.buffer[(int) (j % ((long) getCapacity()))] = e;
            setSize(i + 1);
            setTail(j + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            checkSubOffers();
            return AbstractChannelKt.OFFER_SUCCESS;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    public final Object offerSelectInternal(E e, @NotNull SelectInstance<?> selectInstance) {
        ReentrantLock reentrantLock = this.bufferLock;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                reentrantLock.unlock();
                return closedForSend;
            }
            int i = get_size();
            if (i >= getCapacity()) {
                Symbol symbol = AbstractChannelKt.OFFER_FAILED;
                reentrantLock.unlock();
                return symbol;
            }
            if (!selectInstance.trySelect()) {
                Object already_selected = SelectKt.getALREADY_SELECTED();
                reentrantLock.unlock();
                return already_selected;
            }
            long j = get_tail();
            this.buffer[(int) (j % ((long) getCapacity()))] = e;
            setSize(i + 1);
            setTail(j + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            checkSubOffers();
            return AbstractChannelKt.OFFER_SUCCESS;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    @NotNull
    public ReceiveChannel<E> openSubscription() {
        Subscriber subscriber = new Subscriber(this);
        updateHead$default(this, subscriber, null, 2);
        return subscriber;
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    public void cancel(@Nullable CancellationException cause) {
        cancel(cause);
    }
}
