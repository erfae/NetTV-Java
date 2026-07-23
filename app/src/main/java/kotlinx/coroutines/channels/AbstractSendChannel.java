package kotlinx.coroutines.channels;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedList_commonKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: AbstractChannel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002:\u0004CDEFB)\u0012 \u0010A\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\n\u0018\u00010*j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`@¢\u0006\u0004\bB\u0010.J\u001b\u0010\u0006\u001a\u00020\u00052\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0002¢\u0006\u0004\b\u0006\u0010\u0007J#\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00028\u00002\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0002¢\u0006\u0004\b\u0006\u0010\tJ\u001b\u0010\u000b\u001a\u00020\n2\u0006\u0010\b\u001a\u00028\u0000H\u0082@ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\fJ+\u0010\u000e\u001a\u00020\n*\u0006\u0012\u0002\b\u00030\r2\u0006\u0010\b\u001a\u00028\u00002\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ\u0019\u0010\u0011\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\b\u0011\u0010\u0012J\u001b\u0010\u0013\u001a\u00020\n2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0002¢\u0006\u0004\b\u0013\u0010\u0014JX\u0010\u001b\u001a\u00020\n\"\u0004\b\u0001\u0010\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00010\u00162\u0006\u0010\b\u001a\u00028\u00002(\u0010\u001a\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\r\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0018H\u0002ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u001e\u001a\u00020\u001dH\u0002¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010 \u001a\u00020\n2\u0006\u0010\b\u001a\u00028\u0000H\u0086@ø\u0001\u0000¢\u0006\u0004\b \u0010\fJ\u0017\u0010\"\u001a\u00020!2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\"\u0010#J$\u0010'\u001a\b\u0012\u0004\u0012\u00020\n0$2\u0006\u0010\b\u001a\u00028\u0000ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b%\u0010&J\u0019\u0010(\u001a\u00020!2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0005H\u0016¢\u0006\u0004\b(\u0010)J)\u0010-\u001a\u00020\n2\u0018\u0010,\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\n0*j\u0002`+H\u0016¢\u0006\u0004\b-\u0010.J\u000f\u00100\u001a\u00020/H\u0016¢\u0006\u0004\b0\u00101R\u001a\u00103\u001a\u0002028\u0004X\u0084\u0004¢\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u00106R\u0014\u00107\u001a\u00020!8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u0014\u0010:\u001a\u00020/8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b9\u00101R\u0011\u0010;\u001a\u00020!8F¢\u0006\u0006\u001a\u0004\b;\u00108R#\u0010?\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020<8F¢\u0006\u0006\u001a\u0004\b=\u0010>\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006G"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlinx/coroutines/channels/Closed;", "closed", "", "helpCloseAndGetSendException", "(Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "element", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "", "sendSuspend", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlin/coroutines/Continuation;", "helpCloseAndResumeWithSendException", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "cause", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "helpClose", "(Lkotlinx/coroutines/channels/Closed;)V", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "", "countQueueSize", "()I", "send", "", "offer", "(Ljava/lang/Object;)Z", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "trySend", "close", "(Ljava/lang/Throwable;)Z", "Lkotlin/Function1;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "(Lkotlin/jvm/functions/Function1;)V", "", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueue", "()Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "isFullImpl", "()Z", "getQueueDebugStateString", "queueDebugStateString", "isClosedForSend", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "SendBuffered", "SendBufferedDesc", "SendSelect", "TryOfferDesc", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
public abstract class AbstractSendChannel<E> implements SendChannel<E> {
    private static final /* synthetic */ AtomicReferenceFieldUpdater onCloseHandler$FU = AtomicReferenceFieldUpdater.newUpdater(AbstractSendChannel.class, Object.class, "onCloseHandler");

    @JvmField
    @Nullable
    public final Function1<E, Unit> onUndeliveredElement;

    @NotNull
    private final LockFreeLinkedListHead queue = new LockFreeLinkedListHead();

    @NotNull
    private volatile /* synthetic */ Object onCloseHandler = null;

    /* JADX INFO: compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u0012\u0010\u0003\u001a\u00028\u00018\u0006X\u0087\u0004¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0015"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/Send;", "element", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "pollResult", "", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class SendBuffered<E> extends Send {

        @JvmField
        public final E element;

        public SendBuffered(E e) {
            this.element = e;
        }

        @Override // kotlinx.coroutines.channels.Send
        public void completeResumeSend() {
        }

        @Override // kotlinx.coroutines.channels.Send
        @Nullable
        /* JADX INFO: renamed from: getPollResult, reason: from getter */
        public Object getElement() {
            return this.element;
        }

        @Override // kotlinx.coroutines.channels.Send
        public void resumeSendClosed(@NotNull Closed<?> closed) {
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("SendBuffered@");
            sbM.append(DebugStringsKt.getHexAddress(this));
            sbM.append('(');
            sbM.append(this.element);
            sbM.append(')');
            return sbM.toString();
        }

        @Override // kotlinx.coroutines.channels.Send
        @Nullable
        public Symbol tryResumeSend(@Nullable LockFreeLinkedListNode.PrepareOp otherOp) {
            Symbol symbol = CancellableContinuationImplKt.RESUME_TOKEN;
            if (otherOp != null) {
                otherOp.finishPrepare();
            }
            return symbol;
        }
    }

    /* JADX INFO: compiled from: AbstractChannel.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0012\u0018\u0000*\u0004\b\u0001\u0010\u00012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00030\u0002j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0003`\u0004B\u0017\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00028\u0001¢\u0006\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBufferedDesc;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "Lkotlinx/coroutines/internal/AddLastDesc;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "queue", "element", "<init>", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;Ljava/lang/Object;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
    public static class SendBufferedDesc<E> extends LockFreeLinkedListNode.AddLastDesc<SendBuffered<? extends E>> {
        public SendBufferedDesc(@NotNull LockFreeLinkedListHead lockFreeLinkedListHead, E e) {
            super(lockFreeLinkedListHead, new SendBuffered(e));
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        public final Object failure(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
            if (lockFreeLinkedListNode instanceof Closed) {
                return lockFreeLinkedListNode;
            }
            if (lockFreeLinkedListNode instanceof ReceiveOrClosed) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            return null;
        }
    }

    /* JADX INFO: compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u0001*\u0004\b\u0002\u0010\u00022\u00020\u00032\u00020\u0004BV\u0012\u0006\u0010\u0005\u001a\u00028\u0001\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\t\u0012(\u0010\n\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u000bø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u0014\u0010\u0017\u001a\u00020\u00152\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u0015H\u0016R7\u0010\n\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u000b8\u0006X\u0087\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0010R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006!"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendSelect;", ExifInterface.LONGITUDE_EAST, "R", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/DisposableHandle;", "pollResult", "channel", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/AbstractSendChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "getPollResult", "()Ljava/lang/Object;", "Ljava/lang/Object;", "completeResumeSend", "", "dispose", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "undeliveredElement", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class SendSelect<E, R> extends Send implements DisposableHandle {

        @JvmField
        @NotNull
        public final Function2<SendChannel<? super E>, Continuation<? super R>, Object> block;

        @JvmField
        @NotNull
        public final AbstractSendChannel<E> channel;
        private final E pollResult;

        @JvmField
        @NotNull
        public final SelectInstance<R> select;

        /* JADX WARN: Multi-variable type inference failed */
        public SendSelect(E e, @NotNull AbstractSendChannel<E> abstractSendChannel, @NotNull SelectInstance<? super R> selectInstance, @NotNull Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> function2) {
            this.pollResult = e;
            this.channel = abstractSendChannel;
            this.select = selectInstance;
            this.block = function2;
        }

        @Override // kotlinx.coroutines.channels.Send
        public void completeResumeSend() throws Throwable {
            CancellableKt.startCoroutineCancellable$default(this.block, this.channel, this.select.getCompletion(), null, 4, null);
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public void dispose() {
            if (mo1867remove()) {
                undeliveredElement();
            }
        }

        @Override // kotlinx.coroutines.channels.Send
        /* JADX INFO: renamed from: getPollResult */
        public E getElement() {
            return this.pollResult;
        }

        @Override // kotlinx.coroutines.channels.Send
        public void resumeSendClosed(@NotNull Closed<?> closed) {
            if (this.select.trySelect()) {
                this.select.resumeSelectWithException(closed.getSendException());
            }
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("SendSelect@");
            sbM.append(DebugStringsKt.getHexAddress(this));
            sbM.append('(');
            sbM.append(getElement());
            sbM.append(")[");
            sbM.append(this.channel);
            sbM.append(", ");
            sbM.append(this.select);
            sbM.append(']');
            return sbM.toString();
        }

        @Override // kotlinx.coroutines.channels.Send
        @Nullable
        public Symbol tryResumeSend(@Nullable LockFreeLinkedListNode.PrepareOp otherOp) {
            return (Symbol) this.select.trySelectOther(otherOp);
        }

        @Override // kotlinx.coroutines.channels.Send
        public void undeliveredElement() {
            Function1<E, Unit> function1 = this.channel.onUndeliveredElement;
            if (function1 == null) {
                return;
            }
            OnUndeliveredElementKt.callUndeliveredElement(function1, getElement(), this.select.getCompletion().getContext());
        }
    }

    /* JADX INFO: compiled from: AbstractChannel.kt */
    @Metadata(bv = {}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0004\u0018\u0000*\u0004\b\u0001\u0010\u00012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00030\u0002j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0003`\u0004B\u0017\u0012\u0006\u0010\n\u001a\u00028\u0001\u0012\u0006\u0010\r\u001a\u00020\f¢\u0006\u0004\b\u000e\u0010\u000fJ\u0016\u0010\t\u001a\u0004\u0018\u00010\b2\n\u0010\u0007\u001a\u00060\u0005j\u0002`\u0006H\u0016R\u0014\u0010\n\u001a\u00028\u00018\u0006X\u0087\u0004¢\u0006\u0006\n\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "prepareOp", "", "onPrepare", "element", "Ljava/lang/Object;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "queue", "<init>", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
    public static final class TryOfferDesc<E> extends LockFreeLinkedListNode.RemoveFirstDesc<ReceiveOrClosed<? super E>> {

        @JvmField
        public final E element;

        public TryOfferDesc(E e, @NotNull LockFreeLinkedListHead lockFreeLinkedListHead) {
            super(lockFreeLinkedListHead);
            this.element = e;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.RemoveFirstDesc, kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        public final Object failure(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
            if (lockFreeLinkedListNode instanceof Closed) {
                return lockFreeLinkedListNode;
            }
            if (lockFreeLinkedListNode instanceof ReceiveOrClosed) {
                return null;
            }
            return AbstractChannelKt.OFFER_FAILED;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        public Object onPrepare(@NotNull LockFreeLinkedListNode.PrepareOp prepareOp) {
            Symbol symbolTryResumeReceive = ((ReceiveOrClosed) prepareOp.affected).tryResumeReceive(this.element, prepareOp);
            if (symbolTryResumeReceive == null) {
                return LockFreeLinkedList_commonKt.REMOVE_PREPARED;
            }
            Object obj = AtomicKt.RETRY_ATOMIC;
            if (symbolTryResumeReceive == obj) {
                return obj;
            }
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AbstractSendChannel(@Nullable Function1<? super E, Unit> function1) {
        this.onUndeliveredElement = function1;
    }

    private final int countQueueSize() {
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        int i = 0;
        for (LockFreeLinkedListNode nextNode = (LockFreeLinkedListNode) lockFreeLinkedListHead.getNext(); !Intrinsics.areEqual(nextNode, lockFreeLinkedListHead); nextNode = nextNode.getNextNode()) {
            if (nextNode instanceof LockFreeLinkedListNode) {
                i++;
            }
        }
        return i;
    }

    private final String getQueueDebugStateString() {
        String strStringPlus;
        LockFreeLinkedListNode nextNode = this.queue.getNextNode();
        if (nextNode == this.queue) {
            return "EmptyQueue";
        }
        if (nextNode instanceof Closed) {
            strStringPlus = nextNode.toString();
        } else if (nextNode instanceof Receive) {
            strStringPlus = "ReceiveQueued";
        } else {
            strStringPlus = nextNode instanceof Send ? "SendQueued" : Intrinsics.stringPlus("UNEXPECTED:", nextNode);
        }
        LockFreeLinkedListNode prevNode = this.queue.getPrevNode();
        if (prevNode == nextNode) {
            return strStringPlus;
        }
        StringBuilder sbM25m = Insets$$ExternalSyntheticOutline0.m25m(strStringPlus, ",queueSize=");
        sbM25m.append(countQueueSize());
        String string = sbM25m.toString();
        if (!(prevNode instanceof Closed)) {
            return string;
        }
        return string + ",closedForSend=" + prevNode;
    }

    private final void helpClose(Closed<?> closed) {
        Object objM1859constructorimpl$default = InlineList.m1859constructorimpl$default(null, 1, null);
        while (true) {
            LockFreeLinkedListNode prevNode = closed.getPrevNode();
            Receive receive = prevNode instanceof Receive ? (Receive) prevNode : null;
            if (receive == null) {
                break;
            } else if (receive.mo1867remove()) {
                objM1859constructorimpl$default = InlineList.m1864plusFjFbRPM(objM1859constructorimpl$default, receive);
            } else {
                receive.helpRemove();
            }
        }
        if (objM1859constructorimpl$default == null) {
            return;
        }
        if (!(objM1859constructorimpl$default instanceof ArrayList)) {
            ((Receive) objM1859constructorimpl$default).resumeReceiveClosed(closed);
            return;
        }
        ArrayList arrayList = (ArrayList) objM1859constructorimpl$default;
        int size = arrayList.size() - 1;
        if (size < 0) {
            return;
        }
        while (true) {
            int i = size - 1;
            ((Receive) arrayList.get(size)).resumeReceiveClosed(closed);
            if (i < 0) {
                return;
            } else {
                size = i;
            }
        }
    }

    private final Throwable helpCloseAndGetSendException(Closed<?> closed) {
        helpClose(closed);
        return closed.getSendException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void helpCloseAndResumeWithSendException(Continuation<?> continuation, E e, Closed<?> closed) {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        helpClose(closed);
        Throwable sendException = closed.getSendException();
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 == null || (undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, e, null, 2, null)) == null) {
            Result.Companion companion = Result.INSTANCE;
            continuation.resumeWith(Result.m336constructorimpl(ResultKt.createFailure(sendException)));
        } else {
            ExceptionsKt.addSuppressed(undeliveredElementExceptionCallUndeliveredElementCatchingException$default, sendException);
            Result.Companion companion2 = Result.INSTANCE;
            continuation.resumeWith(Result.m336constructorimpl(ResultKt.createFailure(undeliveredElementExceptionCallUndeliveredElementCatchingException$default)));
        }
    }

    private final void invokeOnCloseHandler(Throwable cause) {
        Symbol symbol;
        boolean z;
        Object obj = this.onCloseHandler;
        if (obj == null || obj == (symbol = AbstractChannelKt.HANDLER_INVOKED)) {
            return;
        }
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = onCloseHandler$FU;
        while (true) {
            if (atomicReferenceFieldUpdater.compareAndSet(this, obj, symbol)) {
                z = true;
                break;
            } else if (atomicReferenceFieldUpdater.get(this) != obj) {
                z = false;
                break;
            }
        }
        if (z) {
            ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(obj, 1)).invoke(cause);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isFullImpl() {
        return !(this.queue.getNextNode() instanceof ReceiveOrClosed) && isBufferFull();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <R> void registerSelectSend(SelectInstance<? super R> select, E element, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block) throws Throwable {
        while (!select.isSelected()) {
            if (isFullImpl()) {
                SendSelect sendSelect = new SendSelect(element, this, select, block);
                Object objEnqueueSend = enqueueSend(sendSelect);
                if (objEnqueueSend == null) {
                    select.disposeOnSelect(sendSelect);
                    return;
                }
                if (objEnqueueSend instanceof Closed) {
                    throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException(element, (Closed) objEnqueueSend));
                }
                if (objEnqueueSend != AbstractChannelKt.ENQUEUE_FAILED && !(objEnqueueSend instanceof Receive)) {
                    throw new IllegalStateException(("enqueueSend returned " + objEnqueueSend + ' ').toString());
                }
            }
            Object objOfferSelectInternal = offerSelectInternal(element, select);
            if (objOfferSelectInternal == SelectKt.getALREADY_SELECTED()) {
                return;
            }
            if (objOfferSelectInternal != AbstractChannelKt.OFFER_FAILED && objOfferSelectInternal != AtomicKt.RETRY_ATOMIC) {
                if (objOfferSelectInternal == AbstractChannelKt.OFFER_SUCCESS) {
                    UndispatchedKt.startCoroutineUnintercepted(block, this, select.getCompletion());
                    return;
                } else {
                    if (!(objOfferSelectInternal instanceof Closed)) {
                        throw new IllegalStateException(Intrinsics.stringPlus("offerSelectInternal returned ", objOfferSelectInternal).toString());
                    }
                    throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException(element, (Closed) objOfferSelectInternal));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:26:0x0061  */
    /* JADX WARN: Code duplicated, block: B:31:0x006a A[EDGE_INSN: B:31:0x006a->B:32:0x006f BREAK  A[LOOP:0: B:3:0x0008->B:47:?]] */
    /* JADX WARN: Code duplicated, block: B:40:0x0086  */
    /* JADX WARN: Code duplicated, block: B:45:0x0055 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:0x0066 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:47:? A[LOOP:0: B:3:0x0008->B:47:?, LOOP_END, SYNTHETIC] */
    public final Object sendSuspend(E e, Continuation<? super Unit> continuation) {
        Object objOfferInternal;
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
        while (true) {
            if (isFullImpl()) {
                Send sendElement = this.onUndeliveredElement == null ? new SendElement(e, orCreateCancellableContinuation) : new SendElementWithUndeliveredHandler(e, orCreateCancellableContinuation, this.onUndeliveredElement);
                Object objEnqueueSend = enqueueSend(sendElement);
                if (objEnqueueSend == null) {
                    CancellableContinuationKt.removeOnCancellation(orCreateCancellableContinuation, sendElement);
                    break;
                }
                if (objEnqueueSend instanceof Closed) {
                    helpCloseAndResumeWithSendException(orCreateCancellableContinuation, e, (Closed) objEnqueueSend);
                    break;
                }
                if (objEnqueueSend != AbstractChannelKt.ENQUEUE_FAILED && !(objEnqueueSend instanceof Receive)) {
                    throw new IllegalStateException(Intrinsics.stringPlus("enqueueSend returned ", objEnqueueSend).toString());
                }
                objOfferInternal = offerInternal(e);
                if (objOfferInternal == AbstractChannelKt.OFFER_SUCCESS) {
                    Result.Companion companion = Result.INSTANCE;
                    orCreateCancellableContinuation.resumeWith(Result.m336constructorimpl(Unit.INSTANCE));
                    break;
                }
                if (objOfferInternal == AbstractChannelKt.OFFER_FAILED) {
                    if (objOfferInternal instanceof Closed) {
                        throw new IllegalStateException(Intrinsics.stringPlus("offerInternal returned ", objOfferInternal).toString());
                    }
                    helpCloseAndResumeWithSendException(orCreateCancellableContinuation, e, (Closed) objOfferInternal);
                    break;
                }
            } else {
                objOfferInternal = offerInternal(e);
                if (objOfferInternal == AbstractChannelKt.OFFER_SUCCESS) {
                    Result.Companion companion2 = Result.INSTANCE;
                    orCreateCancellableContinuation.resumeWith(Result.m336constructorimpl(Unit.INSTANCE));
                    break;
                }
                if (objOfferInternal == AbstractChannelKt.OFFER_FAILED) {
                    if (objOfferInternal instanceof Closed) {
                        throw new IllegalStateException(Intrinsics.stringPlus("offerInternal returned ", objOfferInternal).toString());
                    }
                    helpCloseAndResumeWithSendException(orCreateCancellableContinuation, e, (Closed) objOfferInternal);
                    break;
                }
            }
        }
        Object result = orCreateCancellableContinuation.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    /* JADX INFO: renamed from: close */
    public boolean cancel(@Nullable Throwable cause) {
        LockFreeLinkedListNode prevNode;
        boolean z;
        Closed<?> closed = new Closed<>(cause);
        LockFreeLinkedListNode lockFreeLinkedListNode = this.queue;
        do {
            prevNode = lockFreeLinkedListNode.getPrevNode();
            z = true;
            if (!(!(prevNode instanceof Closed))) {
                z = false;
                break;
            }
        } while (!prevNode.addNext(closed, lockFreeLinkedListNode));
        if (!z) {
            closed = (Closed) this.queue.getPrevNode();
        }
        helpClose(closed);
        if (z) {
            invokeOnCloseHandler(cause);
        }
        return z;
    }

    @NotNull
    public final LockFreeLinkedListNode.AddLastDesc<?> describeSendBuffered(E e) {
        return new SendBufferedDesc(this.queue, e);
    }

    @NotNull
    public final TryOfferDesc<E> describeTryOffer(E e) {
        return new TryOfferDesc<>(e, this.queue);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0036  */
    /* JADX WARN: Code duplicated, block: B:24:0x0039 A[ORIG_RETURN, RETURN] */
    @Nullable
    public Object enqueueSend(@NotNull final Send send) {
        int iTryCondAddNext;
        boolean z;
        LockFreeLinkedListNode prevNode;
        if (isBufferAlwaysFull()) {
            LockFreeLinkedListNode lockFreeLinkedListNode = this.queue;
            do {
                prevNode = lockFreeLinkedListNode.getPrevNode();
                if (prevNode instanceof ReceiveOrClosed) {
                    return prevNode;
                }
            } while (!prevNode.addNext(send, lockFreeLinkedListNode));
            return null;
        }
        LockFreeLinkedListNode lockFreeLinkedListNode2 = this.queue;
        LockFreeLinkedListNode.CondAddOp condAddOp = new LockFreeLinkedListNode.CondAddOp(send) { // from class: kotlinx.coroutines.channels.AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1
            @Override // kotlinx.coroutines.internal.AtomicOp
            @Nullable
            public Object prepare(@NotNull LockFreeLinkedListNode affected) {
                if (this.isBufferFull()) {
                    return null;
                }
                return LockFreeLinkedListKt.getCONDITION_FALSE();
            }
        };
        do {
            LockFreeLinkedListNode prevNode2 = lockFreeLinkedListNode2.getPrevNode();
            if (prevNode2 instanceof ReceiveOrClosed) {
                return prevNode2;
            }
            iTryCondAddNext = prevNode2.tryCondAddNext(send, lockFreeLinkedListNode2, condAddOp);
            z = true;
            if (iTryCondAddNext != 1) {
            }
            if (z) {
                return null;
            }
            return AbstractChannelKt.ENQUEUE_FAILED;
        } while (iTryCondAddNext != 2);
        z = false;
        if (z) {
            return AbstractChannelKt.ENQUEUE_FAILED;
        }
        return null;
    }

    @NotNull
    public String getBufferDebugString() {
        return "";
    }

    @Nullable
    public final Closed<?> getClosedForReceive() {
        LockFreeLinkedListNode nextNode = this.queue.getNextNode();
        Closed<?> closed = nextNode instanceof Closed ? (Closed) nextNode : null;
        if (closed == null) {
            return null;
        }
        helpClose(closed);
        return closed;
    }

    @Nullable
    public final Closed<?> getClosedForSend() {
        LockFreeLinkedListNode prevNode = this.queue.getPrevNode();
        Closed<?> closed = prevNode instanceof Closed ? (Closed) prevNode : null;
        if (closed == null) {
            return null;
        }
        helpClose(closed);
        return closed;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    public final SelectClause2<E, SendChannel<E>> getOnSend() {
        return new SelectClause2<E, SendChannel<? super E>>(this) { // from class: kotlinx.coroutines.channels.AbstractSendChannel$onSend$1
            public final /* synthetic */ AbstractSendChannel<E> this$0;

            {
                this.this$0 = this;
            }

            @Override // kotlinx.coroutines.selects.SelectClause2
            public <R> void registerSelectClause2(@NotNull SelectInstance<? super R> select, E param, @NotNull Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block) throws Throwable {
                this.this$0.registerSelectSend(select, param, block);
            }
        };
    }

    @NotNull
    public final LockFreeLinkedListHead getQueue() {
        return this.queue;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public void invokeOnClose(@NotNull Function1<? super Throwable, Unit> handler) {
        boolean z;
        boolean z2;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = onCloseHandler$FU;
        while (true) {
            z = false;
            if (atomicReferenceFieldUpdater.compareAndSet(this, null, handler)) {
                z2 = true;
                break;
            } else if (atomicReferenceFieldUpdater.get(this) != null) {
                z2 = false;
                break;
            }
        }
        if (!z2) {
            Object obj = this.onCloseHandler;
            if (obj != AbstractChannelKt.HANDLER_INVOKED) {
                throw new IllegalStateException(Intrinsics.stringPlus("Another handler was already registered: ", obj));
            }
            throw new IllegalStateException("Another handler was already registered and successfully invoked");
        }
        Closed<?> closedForSend = getClosedForSend();
        if (closedForSend != null) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = onCloseHandler$FU;
            Symbol symbol = AbstractChannelKt.HANDLER_INVOKED;
            do {
                if (atomicReferenceFieldUpdater2.compareAndSet(this, handler, symbol)) {
                    z = true;
                    break;
                }
            } while (atomicReferenceFieldUpdater2.get(this) == handler);
            if (z) {
                handler.invoke(closedForSend.closeCause);
            }
        }
    }

    public abstract boolean isBufferAlwaysFull();

    public abstract boolean isBufferFull();

    @Override // kotlinx.coroutines.channels.SendChannel
    public final boolean isClosedForSend() {
        return getClosedForSend() != null;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean offer(E element) {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        try {
            return SendChannel.DefaultImpls.offer(this, element);
        } catch (Throwable th) {
            Function1<E, Unit> function1 = this.onUndeliveredElement;
            if (function1 == null || (undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, element, null, 2, null)) == null) {
                throw th;
            }
            ExceptionsKt.addSuppressed(undeliveredElementExceptionCallUndeliveredElementCatchingException$default, th);
            throw undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        }
    }

    @NotNull
    public Object offerInternal(E e) {
        ReceiveOrClosed<E> receiveOrClosedTakeFirstReceiveOrPeekClosed;
        do {
            receiveOrClosedTakeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
            if (receiveOrClosedTakeFirstReceiveOrPeekClosed == null) {
                return AbstractChannelKt.OFFER_FAILED;
            }
        } while (receiveOrClosedTakeFirstReceiveOrPeekClosed.tryResumeReceive(e, null) == null);
        receiveOrClosedTakeFirstReceiveOrPeekClosed.completeResumeReceive(e);
        return receiveOrClosedTakeFirstReceiveOrPeekClosed.getOfferResult();
    }

    @NotNull
    public Object offerSelectInternal(E e, @NotNull SelectInstance<?> selectInstance) {
        TryOfferDesc<E> tryOfferDescDescribeTryOffer = describeTryOffer(e);
        Object objPerformAtomicTrySelect = selectInstance.performAtomicTrySelect(tryOfferDescDescribeTryOffer);
        if (objPerformAtomicTrySelect != null) {
            return objPerformAtomicTrySelect;
        }
        ReceiveOrClosed<? super E> result = tryOfferDescDescribeTryOffer.getResult();
        result.completeResumeReceive(e);
        return result.getOfferResult();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Nullable
    public final Object send(E e, @NotNull Continuation<? super Unit> continuation) {
        Object objSendSuspend;
        return (offerInternal(e) != AbstractChannelKt.OFFER_SUCCESS && (objSendSuspend = sendSuspend(e, continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objSendSuspend : Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public final ReceiveOrClosed<?> sendBuffered(E e) {
        LockFreeLinkedListNode prevNode;
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        SendBuffered sendBuffered = new SendBuffered(e);
        do {
            prevNode = lockFreeLinkedListHead.getPrevNode();
            if (prevNode instanceof ReceiveOrClosed) {
                return (ReceiveOrClosed) prevNode;
            }
        } while (!prevNode.addNext(sendBuffered, lockFreeLinkedListHead));
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [kotlinx.coroutines.internal.LockFreeLinkedListNode] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    @Nullable
    public ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed() {
        ?? r1;
        LockFreeLinkedListNode lockFreeLinkedListNodeRemoveOrNext;
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        while (true) {
            r1 = (LockFreeLinkedListNode) lockFreeLinkedListHead.getNext();
            if (r1 == lockFreeLinkedListHead || !(r1 instanceof ReceiveOrClosed)) {
                break;
            }
            if ((!(((ReceiveOrClosed) r1) instanceof Closed) || r1.isRemoved()) && (lockFreeLinkedListNodeRemoveOrNext = r1.removeOrNext()) != null) {
                lockFreeLinkedListNodeRemoveOrNext.helpRemovePrev();
            }
            return (ReceiveOrClosed) r1;
        }
        r1 = 0;
        return (ReceiveOrClosed) r1;
    }

    @Nullable
    public final Send takeFirstSendOrPeekClosed() {
        LockFreeLinkedListNode lockFreeLinkedListNode;
        LockFreeLinkedListNode lockFreeLinkedListNodeRemoveOrNext;
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        while (true) {
            lockFreeLinkedListNode = (LockFreeLinkedListNode) lockFreeLinkedListHead.getNext();
            if (lockFreeLinkedListNode == lockFreeLinkedListHead || !(lockFreeLinkedListNode instanceof Send)) {
                break;
            }
            if ((!(((Send) lockFreeLinkedListNode) instanceof Closed) || lockFreeLinkedListNode.isRemoved()) && (lockFreeLinkedListNodeRemoveOrNext = lockFreeLinkedListNode.removeOrNext()) != null) {
                lockFreeLinkedListNodeRemoveOrNext.helpRemovePrev();
            }
            return (Send) lockFreeLinkedListNode;
        }
        lockFreeLinkedListNode = null;
        return (Send) lockFreeLinkedListNode;
    }

    @NotNull
    public String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this) + '{' + getQueueDebugStateString() + '}' + getBufferDebugString();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    /* JADX INFO: renamed from: trySend-JP2dKIU, reason: not valid java name */
    public final Object mo1823trySendJP2dKIU(E element) {
        Object objOfferInternal = offerInternal(element);
        if (objOfferInternal == AbstractChannelKt.OFFER_SUCCESS) {
            return ChannelResult.INSTANCE.m1843successJP2dKIU(Unit.INSTANCE);
        }
        if (objOfferInternal == AbstractChannelKt.OFFER_FAILED) {
            Closed<?> closedForSend = getClosedForSend();
            return closedForSend == null ? ChannelResult.INSTANCE.m1842failurePtdJZtk() : ChannelResult.INSTANCE.m1841closedJP2dKIU(helpCloseAndGetSendException(closedForSend));
        }
        if (objOfferInternal instanceof Closed) {
            return ChannelResult.INSTANCE.m1841closedJP2dKIU(helpCloseAndGetSendException((Closed) objOfferInternal));
        }
        throw new IllegalStateException(Intrinsics.stringPlus("trySend returned ", objOfferInternal).toString());
    }

    private final Throwable helpCloseAndGetSendException(E element, Closed<?> closed) {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        helpClose(closed);
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 == null || (undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, element, null, 2, null)) == null) {
            return closed.getSendException();
        }
        ExceptionsKt.addSuppressed(undeliveredElementExceptionCallUndeliveredElementCatchingException$default, closed.getSendException());
        throw undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
    }
}
