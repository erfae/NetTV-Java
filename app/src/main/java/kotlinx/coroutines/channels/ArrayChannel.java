package kotlinx.coroutines.channels;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ArrayChannel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B9\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012 \u0010\"\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\t\u0018\u00010 j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`!¢\u0006\u0004\b#\u0010$J\u0019\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\b\u001a\u00028\u0000H\u0002¢\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\f\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u00038\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u00108\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0015\u001a\u00060\u0013j\u0002`\u00148\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u00178\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0019\u0010\u001aR\u0016\u0010\u001b\u001a\u00020\u00038\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001b\u0010\u000fR\u0014\u0010\u001d\u001a\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001e¨\u0006%"}, d2 = {"Lkotlinx/coroutines/channels/ArrayChannel;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/AbstractChannel;", "", "currentSize", "Lkotlinx/coroutines/internal/Symbol;", "updateBufferSize", "(I)Lkotlinx/coroutines/internal/Symbol;", "element", "", "enqueueElement", "(ILjava/lang/Object;)V", "ensureCapacity", "(I)V", "capacity", "I", "Lkotlinx/coroutines/channels/BufferOverflow;", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "", "", "buffer", "[Ljava/lang/Object;", TtmlNode.TAG_HEAD, "", "isEmpty", "()Z", "isClosedForReceive", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(ILkotlinx/coroutines/channels/BufferOverflow;Lkotlin/jvm/functions/Function1;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
public class ArrayChannel<E> extends AbstractChannel<E> {

    @NotNull
    private Object[] buffer;
    private final int capacity;
    private int head;

    @NotNull
    private final ReentrantLock lock;

    @NotNull
    private final BufferOverflow onBufferOverflow;

    @NotNull
    private volatile /* synthetic */ int size;

    /* JADX INFO: compiled from: ArrayChannel.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BufferOverflow.values().length];
            iArr[BufferOverflow.SUSPEND.ordinal()] = 1;
            iArr[BufferOverflow.DROP_LATEST.ordinal()] = 2;
            iArr[BufferOverflow.DROP_OLDEST.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ArrayChannel(int i, @NotNull BufferOverflow bufferOverflow, @Nullable Function1<? super E, Unit> function1) {
        super(function1);
        this.capacity = i;
        this.onBufferOverflow = bufferOverflow;
        if (!(i >= 1)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("ArrayChannel capacity must be at least 1, but ", i, " was specified").toString());
        }
        this.lock = new ReentrantLock();
        Object[] objArr = new Object[Math.min(i, 8)];
        ArraysKt___ArraysJvmKt.fill$default(objArr, AbstractChannelKt.EMPTY, 0, 0, 6, (Object) null);
        this.buffer = objArr;
        this.size = 0;
    }

    private final void enqueueElement(int currentSize, E element) {
        if (currentSize < this.capacity) {
            ensureCapacity(currentSize);
            Object[] objArr = this.buffer;
            objArr[(this.head + currentSize) % objArr.length] = element;
        } else {
            Object[] objArr2 = this.buffer;
            int i = this.head;
            objArr2[i % objArr2.length] = null;
            objArr2[(currentSize + i) % objArr2.length] = element;
            this.head = (i + 1) % objArr2.length;
        }
    }

    private final void ensureCapacity(int currentSize) {
        Object[] objArr = this.buffer;
        if (currentSize >= objArr.length) {
            int iMin = Math.min(objArr.length * 2, this.capacity);
            Object[] objArr2 = new Object[iMin];
            for (int i = 0; i < currentSize; i++) {
                Object[] objArr3 = this.buffer;
                objArr2[i] = objArr3[(this.head + i) % objArr3.length];
            }
            ArraysKt.fill((Symbol[]) objArr2, AbstractChannelKt.EMPTY, currentSize, iMin);
            this.buffer = objArr2;
            this.head = 0;
        }
    }

    private final Symbol updateBufferSize(int currentSize) {
        if (currentSize < this.capacity) {
            this.size = currentSize + 1;
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[this.onBufferOverflow.ordinal()];
        if (i == 1) {
            return AbstractChannelKt.OFFER_FAILED;
        }
        if (i == 2) {
            return AbstractChannelKt.OFFER_SUCCESS;
        }
        if (i == 3) {
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean enqueueReceiveInternal(@NotNull Receive<? super E> receive) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return super.enqueueReceiveInternal(receive);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @Nullable
    public final Object enqueueSend(@NotNull Send send) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return super.enqueueSend(send);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    public final String getBufferDebugString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("(buffer:capacity=");
        sbM.append(this.capacity);
        sbM.append(",size=");
        return Insets$$ExternalSyntheticOutline0.m(sbM, this.size, ')');
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean isBufferAlwaysEmpty() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferAlwaysFull() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean isBufferEmpty() {
        return this.size == 0;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferFull() {
        return this.size == this.capacity && this.onBufferOverflow == BufferOverflow.SUSPEND;
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel, kotlinx.coroutines.channels.ReceiveChannel
    public boolean isClosedForReceive() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return super.isClosedForReceive();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel, kotlinx.coroutines.channels.ReceiveChannel
    public boolean isEmpty() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return isEmptyImpl();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    public final Object offerInternal(E e) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int i = this.size;
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                reentrantLock.unlock();
                return closedForSend;
            }
            Symbol symbolUpdateBufferSize = updateBufferSize(i);
            if (symbolUpdateBufferSize != null) {
                reentrantLock.unlock();
                return symbolUpdateBufferSize;
            }
            if (i == 0) {
                while (true) {
                    ReceiveOrClosed<E> receiveOrClosedTakeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
                    if (receiveOrClosedTakeFirstReceiveOrPeekClosed == null) {
                        break;
                    }
                    if (receiveOrClosedTakeFirstReceiveOrPeekClosed instanceof Closed) {
                        this.size = i;
                        reentrantLock.unlock();
                        return receiveOrClosedTakeFirstReceiveOrPeekClosed;
                    }
                    if (receiveOrClosedTakeFirstReceiveOrPeekClosed.tryResumeReceive(e, null) != null) {
                        this.size = i;
                        Unit unit = Unit.INSTANCE;
                        reentrantLock.unlock();
                        receiveOrClosedTakeFirstReceiveOrPeekClosed.completeResumeReceive(e);
                        return receiveOrClosedTakeFirstReceiveOrPeekClosed.getOfferResult();
                    }
                }
            }
            enqueueElement(i, e);
            Symbol symbol = AbstractChannelKt.OFFER_SUCCESS;
            reentrantLock.unlock();
            return symbol;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    public final Object offerSelectInternal(E e, @NotNull SelectInstance<?> selectInstance) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int i = this.size;
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                reentrantLock.unlock();
                return closedForSend;
            }
            Symbol symbolUpdateBufferSize = updateBufferSize(i);
            if (symbolUpdateBufferSize != null) {
                reentrantLock.unlock();
                return symbolUpdateBufferSize;
            }
            if (i == 0) {
                while (true) {
                    AbstractSendChannel.TryOfferDesc<E> tryOfferDescDescribeTryOffer = describeTryOffer(e);
                    Object objPerformAtomicTrySelect = selectInstance.performAtomicTrySelect(tryOfferDescDescribeTryOffer);
                    if (objPerformAtomicTrySelect == null) {
                        this.size = i;
                        ReceiveOrClosed<? super E> result = tryOfferDescDescribeTryOffer.getResult();
                        Unit unit = Unit.INSTANCE;
                        reentrantLock.unlock();
                        Intrinsics.checkNotNull(result);
                        ReceiveOrClosed<? super E> receiveOrClosed = result;
                        receiveOrClosed.completeResumeReceive(e);
                        return receiveOrClosed.getOfferResult();
                    }
                    if (objPerformAtomicTrySelect == AbstractChannelKt.OFFER_FAILED) {
                        break;
                    }
                    if (objPerformAtomicTrySelect != AtomicKt.RETRY_ATOMIC) {
                        if (objPerformAtomicTrySelect != SelectKt.getALREADY_SELECTED() && !(objPerformAtomicTrySelect instanceof Closed)) {
                            throw new IllegalStateException(Intrinsics.stringPlus("performAtomicTrySelect(describeTryOffer) returned ", objPerformAtomicTrySelect).toString());
                        }
                        this.size = i;
                        reentrantLock.unlock();
                        return objPerformAtomicTrySelect;
                    }
                }
            }
            if (selectInstance.trySelect()) {
                enqueueElement(i, e);
                Symbol symbol = AbstractChannelKt.OFFER_SUCCESS;
                reentrantLock.unlock();
                return symbol;
            }
            this.size = i;
            Object already_selected = SelectKt.getALREADY_SELECTED();
            reentrantLock.unlock();
            return already_selected;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final void onCancelIdempotent(boolean z) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int i = this.size;
            UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException = null;
            int i2 = 0;
            while (i2 < i) {
                i2++;
                Object obj = this.buffer[this.head];
                if (function1 != null && obj != AbstractChannelKt.EMPTY) {
                    undeliveredElementExceptionCallUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, undeliveredElementExceptionCallUndeliveredElementCatchingException);
                }
                Object[] objArr = this.buffer;
                int i3 = this.head;
                objArr[i3] = AbstractChannelKt.EMPTY;
                this.head = (i3 + 1) % objArr.length;
            }
            this.size = 0;
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            super.onCancelIdempotent(z);
            if (undeliveredElementExceptionCallUndeliveredElementCatchingException != null) {
                throw undeliveredElementExceptionCallUndeliveredElementCatchingException;
            }
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    @Nullable
    public final Object pollInternal() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int i = this.size;
            if (i == 0) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                reentrantLock.unlock();
                return closedForSend;
            }
            Object[] objArr = this.buffer;
            int i2 = this.head;
            Object obj = objArr[i2];
            Send send = null;
            objArr[i2] = null;
            this.size = i - 1;
            Object element = AbstractChannelKt.POLL_FAILED;
            boolean z = false;
            if (i == this.capacity) {
                Send send2 = null;
                while (true) {
                    Send sendTakeFirstSendOrPeekClosed = takeFirstSendOrPeekClosed();
                    if (sendTakeFirstSendOrPeekClosed == null) {
                        send = send2;
                        break;
                    }
                    if (sendTakeFirstSendOrPeekClosed.tryResumeSend(null) != null) {
                        element = sendTakeFirstSendOrPeekClosed.getElement();
                        send = sendTakeFirstSendOrPeekClosed;
                        z = true;
                        break;
                    }
                    sendTakeFirstSendOrPeekClosed.undeliveredElement();
                    send2 = sendTakeFirstSendOrPeekClosed;
                }
            }
            if (element != AbstractChannelKt.POLL_FAILED && !(element instanceof Closed)) {
                this.size = i;
                Object[] objArr2 = this.buffer;
                objArr2[(this.head + i) % objArr2.length] = element;
            }
            this.head = (this.head + 1) % this.buffer.length;
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            if (z) {
                Intrinsics.checkNotNull(send);
                send.completeResumeSend();
            }
            return obj;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    @Nullable
    public final Object pollSelectInternal(@NotNull SelectInstance<?> selectInstance) {
        boolean z;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int i = this.size;
            if (i == 0) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                reentrantLock.unlock();
                return closedForSend;
            }
            Object[] objArr = this.buffer;
            int i2 = this.head;
            Object obj = objArr[i2];
            Object result = null;
            objArr[i2] = null;
            this.size = i - 1;
            Object element = AbstractChannelKt.POLL_FAILED;
            if (i == this.capacity) {
                while (true) {
                    AbstractChannel.TryPollDesc tryPollDesc = new AbstractChannel.TryPollDesc(getQueue());
                    Object objPerformAtomicTrySelect = selectInstance.performAtomicTrySelect(tryPollDesc);
                    if (objPerformAtomicTrySelect == null) {
                        result = tryPollDesc.getResult();
                        Intrinsics.checkNotNull(result);
                        element = ((Send) result).getElement();
                    } else if (objPerformAtomicTrySelect == AbstractChannelKt.POLL_FAILED) {
                        z = false;
                    } else if (objPerformAtomicTrySelect != AtomicKt.RETRY_ATOMIC) {
                        if (objPerformAtomicTrySelect == SelectKt.getALREADY_SELECTED()) {
                            this.size = i;
                            this.buffer[this.head] = obj;
                            reentrantLock.unlock();
                            return objPerformAtomicTrySelect;
                        }
                        if (!(objPerformAtomicTrySelect instanceof Closed)) {
                            throw new IllegalStateException(Intrinsics.stringPlus("performAtomicTrySelect(describeTryOffer) returned ", objPerformAtomicTrySelect).toString());
                        }
                        element = objPerformAtomicTrySelect;
                        result = element;
                    }
                    z = true;
                }
            } else {
                z = false;
            }
            if (element != AbstractChannelKt.POLL_FAILED && !(element instanceof Closed)) {
                this.size = i;
                Object[] objArr2 = this.buffer;
                objArr2[(this.head + i) % objArr2.length] = element;
            } else if (!selectInstance.trySelect()) {
                this.size = i;
                this.buffer[this.head] = obj;
                Object already_selected = SelectKt.getALREADY_SELECTED();
                reentrantLock.unlock();
                return already_selected;
            }
            this.head = (this.head + 1) % this.buffer.length;
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            if (z) {
                Intrinsics.checkNotNull(result);
                ((Send) result).completeResumeSend();
            }
            return obj;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }
}
