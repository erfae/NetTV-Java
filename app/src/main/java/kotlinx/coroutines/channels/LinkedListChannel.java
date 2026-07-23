package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
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

/* JADX INFO: compiled from: LinkedListChannel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B)\u0012 \u0010\u0006\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0005¢\u0006\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lkotlinx/coroutines/channels/LinkedListChannel;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/AbstractChannel;", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
public class LinkedListChannel<E> extends AbstractChannel<E> {
    public LinkedListChannel(@Nullable Function1<? super E, Unit> function1) {
        super(function1);
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean isBufferAlwaysEmpty() {
        return true;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferAlwaysFull() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean isBufferEmpty() {
        return true;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferFull() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    public final Object offerInternal(E e) {
        ReceiveOrClosed<?> receiveOrClosedSendBuffered;
        do {
            Object objOfferInternal = super.offerInternal(e);
            Symbol symbol = AbstractChannelKt.OFFER_SUCCESS;
            if (objOfferInternal == symbol) {
                return symbol;
            }
            if (objOfferInternal != AbstractChannelKt.OFFER_FAILED) {
                if (objOfferInternal instanceof Closed) {
                    return objOfferInternal;
                }
                throw new IllegalStateException(Intrinsics.stringPlus("Invalid offerInternal result ", objOfferInternal).toString());
            }
            receiveOrClosedSendBuffered = sendBuffered(e);
            if (receiveOrClosedSendBuffered == null) {
                return symbol;
            }
        } while (!(receiveOrClosedSendBuffered instanceof Closed));
        return receiveOrClosedSendBuffered;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    public final Object offerSelectInternal(E e, @NotNull SelectInstance<?> selectInstance) {
        Object objPerformAtomicTrySelect;
        while (true) {
            if (getQueue().getNextNode() instanceof ReceiveOrClosed) {
                objPerformAtomicTrySelect = super.offerSelectInternal(e, selectInstance);
            } else {
                objPerformAtomicTrySelect = selectInstance.performAtomicTrySelect(describeSendBuffered(e));
                if (objPerformAtomicTrySelect == null) {
                    objPerformAtomicTrySelect = AbstractChannelKt.OFFER_SUCCESS;
                }
            }
            if (objPerformAtomicTrySelect == SelectKt.getALREADY_SELECTED()) {
                return SelectKt.getALREADY_SELECTED();
            }
            Symbol symbol = AbstractChannelKt.OFFER_SUCCESS;
            if (objPerformAtomicTrySelect == symbol) {
                return symbol;
            }
            if (objPerformAtomicTrySelect != AbstractChannelKt.OFFER_FAILED && objPerformAtomicTrySelect != AtomicKt.RETRY_ATOMIC) {
                if (objPerformAtomicTrySelect instanceof Closed) {
                    return objPerformAtomicTrySelect;
                }
                throw new IllegalStateException(Intrinsics.stringPlus("Invalid result ", objPerformAtomicTrySelect).toString());
            }
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    /* JADX INFO: renamed from: onCancelIdempotentList-w-w6eGU */
    public final void mo1820onCancelIdempotentListww6eGU(@NotNull Object obj, @NotNull Closed<?> closed) {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException = null;
        if (obj != null) {
            if (obj instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) obj;
                int size = arrayList.size() - 1;
                if (size >= 0) {
                    UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException2 = null;
                    while (true) {
                        int i = size - 1;
                        Send send = (Send) arrayList.get(size);
                        if (send instanceof AbstractSendChannel.SendBuffered) {
                            Function1<E, Unit> function1 = this.onUndeliveredElement;
                            undeliveredElementExceptionCallUndeliveredElementCatchingException2 = function1 == null ? null : OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, ((AbstractSendChannel.SendBuffered) send).element, undeliveredElementExceptionCallUndeliveredElementCatchingException2);
                        } else {
                            send.resumeSendClosed(closed);
                        }
                        if (i < 0) {
                            break;
                        } else {
                            size = i;
                        }
                    }
                    undeliveredElementExceptionCallUndeliveredElementCatchingException = undeliveredElementExceptionCallUndeliveredElementCatchingException2;
                }
            } else {
                Send send2 = (Send) obj;
                if (send2 instanceof AbstractSendChannel.SendBuffered) {
                    Function1<E, Unit> function2 = this.onUndeliveredElement;
                    if (function2 != null) {
                        undeliveredElementExceptionCallUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function2, ((AbstractSendChannel.SendBuffered) send2).element, null);
                    }
                } else {
                    send2.resumeSendClosed(closed);
                }
            }
        }
        if (undeliveredElementExceptionCallUndeliveredElementCatchingException != null) {
            throw undeliveredElementExceptionCallUndeliveredElementCatchingException;
        }
    }
}
