package kotlinx.coroutines.channels;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
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

/* JADX INFO: compiled from: ConflatedChannel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B)\u0012 \u0010\u0013\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0012¢\u0006\u0004\b\u0014\u0010\u0015J\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u0002R\u0018\u0010\t\u001a\u00060\u0007j\u0002`\b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00038\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u000b\u0010\fR\u0014\u0010\u000e\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0016"}, d2 = {"Lkotlinx/coroutines/channels/ConflatedChannel;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/AbstractChannel;", "", "element", "Lkotlinx/coroutines/internal/UndeliveredElementException;", "updateValueLocked", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "value", "Ljava/lang/Object;", "", "isEmpty", "()Z", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
public class ConflatedChannel<E> extends AbstractChannel<E> {

    @NotNull
    private final ReentrantLock lock;

    @Nullable
    private Object value;

    public ConflatedChannel(@Nullable Function1<? super E, Unit> function1) {
        super(function1);
        this.lock = new ReentrantLock();
        this.value = AbstractChannelKt.EMPTY;
    }

    private final UndeliveredElementException updateValueLocked(Object element) {
        Function1<E, Unit> function1;
        Object obj = this.value;
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default = null;
        if (obj != AbstractChannelKt.EMPTY && (function1 = this.onUndeliveredElement) != null) {
            undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, obj, null, 2, null);
        }
        this.value = element;
        return undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
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
    @NotNull
    public final String getBufferDebugString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("(value=");
        sbM.append(this.value);
        sbM.append(')');
        return sbM.toString();
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
        return this.value == AbstractChannelKt.EMPTY;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferFull() {
        return false;
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
    public Object offerInternal(E e) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                reentrantLock.unlock();
                return closedForSend;
            }
            if (this.value == AbstractChannelKt.EMPTY) {
                while (true) {
                    ReceiveOrClosed<E> receiveOrClosedTakeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
                    if (receiveOrClosedTakeFirstReceiveOrPeekClosed == null) {
                        break;
                    }
                    if (receiveOrClosedTakeFirstReceiveOrPeekClosed instanceof Closed) {
                        reentrantLock.unlock();
                        return receiveOrClosedTakeFirstReceiveOrPeekClosed;
                    }
                    if (receiveOrClosedTakeFirstReceiveOrPeekClosed.tryResumeReceive(e, null) != null) {
                        Unit unit = Unit.INSTANCE;
                        reentrantLock.unlock();
                        receiveOrClosedTakeFirstReceiveOrPeekClosed.completeResumeReceive(e);
                        return receiveOrClosedTakeFirstReceiveOrPeekClosed.getOfferResult();
                    }
                }
            }
            UndeliveredElementException undeliveredElementExceptionUpdateValueLocked = updateValueLocked(e);
            if (undeliveredElementExceptionUpdateValueLocked != null) {
                throw undeliveredElementExceptionUpdateValueLocked;
            }
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
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                reentrantLock.unlock();
                return closedForSend;
            }
            if (this.value == AbstractChannelKt.EMPTY) {
                while (true) {
                    AbstractSendChannel.TryOfferDesc<E> tryOfferDescDescribeTryOffer = describeTryOffer(e);
                    Object objPerformAtomicTrySelect = selectInstance.performAtomicTrySelect(tryOfferDescDescribeTryOffer);
                    if (objPerformAtomicTrySelect == null) {
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
                        reentrantLock.unlock();
                        return objPerformAtomicTrySelect;
                    }
                }
            }
            if (!selectInstance.trySelect()) {
                Object already_selected = SelectKt.getALREADY_SELECTED();
                reentrantLock.unlock();
                return already_selected;
            }
            UndeliveredElementException undeliveredElementExceptionUpdateValueLocked = updateValueLocked(e);
            if (undeliveredElementExceptionUpdateValueLocked != null) {
                throw undeliveredElementExceptionUpdateValueLocked;
            }
            Symbol symbol = AbstractChannelKt.OFFER_SUCCESS;
            reentrantLock.unlock();
            return symbol;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public void onCancelIdempotent(boolean z) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            UndeliveredElementException undeliveredElementExceptionUpdateValueLocked = updateValueLocked(AbstractChannelKt.EMPTY);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            super.onCancelIdempotent(z);
            if (undeliveredElementExceptionUpdateValueLocked != null) {
                throw undeliveredElementExceptionUpdateValueLocked;
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
            Object obj = this.value;
            Symbol symbol = AbstractChannelKt.EMPTY;
            if (obj != symbol) {
                this.value = symbol;
                Unit unit = Unit.INSTANCE;
                return obj;
            }
            Object closedForSend = getClosedForSend();
            if (closedForSend == null) {
                closedForSend = AbstractChannelKt.POLL_FAILED;
            }
            return closedForSend;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    @Nullable
    public final Object pollSelectInternal(@NotNull SelectInstance<?> selectInstance) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Object obj = this.value;
            Symbol symbol = AbstractChannelKt.EMPTY;
            if (obj == symbol) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                return closedForSend;
            }
            if (!selectInstance.trySelect()) {
                return SelectKt.getALREADY_SELECTED();
            }
            Object obj2 = this.value;
            this.value = symbol;
            Unit unit = Unit.INSTANCE;
            return obj2;
        } finally {
            reentrantLock.unlock();
        }
    }
}
