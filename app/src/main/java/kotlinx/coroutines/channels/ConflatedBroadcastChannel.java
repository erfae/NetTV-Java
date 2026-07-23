package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import io.realm.CollectionUtils;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.ObsoleteCoroutinesApi;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ConflatedBroadcastChannel.kt */
/* JADX INFO: loaded from: classes2.dex */
@ObsoleteCoroutinesApi
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\b\u0007\u0018\u0000 B*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000G:\u0004CBDEB\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00028\u0000¢\u0006\u0004\b\u0003\u0010\u0004B\u0007¢\u0006\u0004\b\u0003\u0010\u0005J?\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00070\u00062\u0014\u0010\b\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0007\u0018\u00010\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0002¢\u0006\u0004\b\n\u0010\u000bJ\u0019\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0017¢\u0006\u0004\b\u000f\u0010\u0010J\u001f\u0010\u000f\u001a\u00020\u00132\u000e\u0010\r\u001a\n\u0018\u00010\u0011j\u0004\u0018\u0001`\u0012H\u0016¢\u0006\u0004\b\u000f\u0010\u0014J\u0019\u0010\u0015\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0016¢\u0006\u0004\b\u0015\u0010\u0010J\u001d\u0010\u0016\u001a\u00020\u00132\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0002¢\u0006\u0004\b\u0016\u0010\u0017J)\u0010\u001b\u001a\u00020\u00132\u0018\u0010\u001a\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u00130\u0018j\u0002`\u0019H\u0016¢\u0006\u0004\b\u001b\u0010\u001cJ\u0019\u0010\u001d\u001a\u00020\u00132\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0002¢\u0006\u0004\b\u001d\u0010\u001eJ\u0019\u0010!\u001a\u0004\u0018\u00010 2\u0006\u0010\u001f\u001a\u00028\u0000H\u0002¢\u0006\u0004\b!\u0010\"J\u0015\u0010$\u001a\b\u0012\u0004\u0012\u00028\u00000#H\u0016¢\u0006\u0004\b$\u0010%JX\u0010.\u001a\u00020\u0013\"\u0004\b\u0001\u0010&2\f\u0010(\u001a\b\u0012\u0004\u0012\u00028\u00010'2\u0006\u0010\u001f\u001a\u00028\u00002(\u0010-\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000*\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010+\u0012\u0006\u0012\u0004\u0018\u00010,0)H\u0002ø\u0001\u0000¢\u0006\u0004\b.\u0010/J?\u00100\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0007\u0018\u00010\u00062\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00070\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0002¢\u0006\u0004\b0\u0010\u000bJ\u001b\u00101\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0004\b1\u00102J&\u00106\u001a\b\u0012\u0004\u0012\u00020\u0013032\u0006\u0010\u001f\u001a\u00028\u0000H\u0016ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b4\u00105R\u0014\u00107\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R&\u0010<\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000*098VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0017\u0010\u0002\u001a\u00028\u00008F¢\u0006\f\u0012\u0004\b?\u0010\u0005\u001a\u0004\b=\u0010>R\u0013\u0010A\u001a\u0004\u0018\u00018\u00008F¢\u0006\u0006\u001a\u0004\b@\u0010>\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006F"}, d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", ExifInterface.LONGITUDE_EAST, "value", "<init>", "(Ljava/lang/Object;)V", "()V", "", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", CollectionUtils.LIST_TYPE, "subscriber", "addSubscriber", "([Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "", "(Ljava/util/concurrent/CancellationException;)V", "close", "closeSubscriber", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)V", "Lkotlin/Function1;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "(Lkotlin/jvm/functions/Function1;)V", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "element", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "offerInternal", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "removeSubscriber", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "trySend", "isClosedForSend", "()Z", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "getValue", "()Ljava/lang/Object;", "getValue$annotations", "getValueOrNull", "valueOrNull", "Companion", "Closed", "State", "Subscriber", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/BroadcastChannel;"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ConflatedBroadcastChannel<E> implements BroadcastChannel<E> {

    @Deprecated
    @NotNull
    private static final State<Object> INITIAL_STATE;

    @Deprecated
    @NotNull
    private static final Symbol UNDEFINED;
    private static final /* synthetic */ AtomicReferenceFieldUpdater _state$FU;
    private static final /* synthetic */ AtomicIntegerFieldUpdater _updating$FU;
    private static final /* synthetic */ AtomicReferenceFieldUpdater onCloseHandler$FU;

    @NotNull
    private volatile /* synthetic */ Object _state;

    @NotNull
    private volatile /* synthetic */ int _updating;

    @NotNull
    private volatile /* synthetic */ Object onCloseHandler;

    @NotNull
    private static final Companion Companion = new Companion(null);

    @Deprecated
    @NotNull
    private static final Closed CLOSED = new Closed(null);

    /* JADX INFO: compiled from: ConflatedBroadcastChannel.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "", "closeCause", "", "(Ljava/lang/Throwable;)V", "sendException", "getSendException", "()Ljava/lang/Throwable;", "valueException", "getValueException", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Closed {

        @JvmField
        @Nullable
        public final Throwable closeCause;

        public Closed(@Nullable Throwable th) {
            this.closeCause = th;
        }

        @NotNull
        public final Throwable getSendException() {
            Throwable th = this.closeCause;
            return th == null ? new ClosedSendChannelException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : th;
        }

        @NotNull
        public final Throwable getValueException() {
            Throwable th = this.closeCause;
            return th == null ? new IllegalStateException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : th;
        }
    }

    /* JADX INFO: compiled from: ConflatedBroadcastChannel.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Companion;", "", "()V", "CLOSED", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "INITIAL_STATE", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$State;", "UNDEFINED", "Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: ConflatedBroadcastChannel.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002B%\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0014\u0010\u0004\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0006\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007R \u0010\u0004\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0006\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$State;", ExifInterface.LONGITUDE_EAST, "", "value", "subscribers", "", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "(Ljava/lang/Object;[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)V", "[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class State<E> {

        @JvmField
        @Nullable
        public final Subscriber<E>[] subscribers;

        @JvmField
        @Nullable
        public final Object value;

        public State(@Nullable Object obj, @Nullable Subscriber<E>[] subscriberArr) {
            this.value = obj;
            this.subscribers = subscriberArr;
        }
    }

    /* JADX INFO: compiled from: ConflatedBroadcastChannel.kt */
    @Metadata(bv = {}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010\u00022\b\u0012\u0004\u0012\u00028\u00010\u0003B\u0015\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\b¢\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00028\u0001H\u0016¢\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/ConflatedChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "element", "", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "broadcastChannel", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "<init>", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
    public static final class Subscriber<E> extends ConflatedChannel<E> implements ReceiveChannel<E> {

        @NotNull
        private final ConflatedBroadcastChannel<E> broadcastChannel;

        public Subscriber(@NotNull ConflatedBroadcastChannel<E> conflatedBroadcastChannel) {
            super(null);
            this.broadcastChannel = conflatedBroadcastChannel;
        }

        @Override // kotlinx.coroutines.channels.ConflatedChannel, kotlinx.coroutines.channels.AbstractSendChannel
        @NotNull
        public Object offerInternal(E element) {
            return super.offerInternal(element);
        }

        @Override // kotlinx.coroutines.channels.ConflatedChannel, kotlinx.coroutines.channels.AbstractChannel
        public final void onCancelIdempotent(boolean z) {
            if (z) {
                this.broadcastChannel.closeSubscriber(this);
            }
        }
    }

    static {
        Symbol symbol = new Symbol("UNDEFINED");
        UNDEFINED = symbol;
        INITIAL_STATE = new State<>(symbol, null);
        _state$FU = AtomicReferenceFieldUpdater.newUpdater(ConflatedBroadcastChannel.class, Object.class, "_state");
        _updating$FU = AtomicIntegerFieldUpdater.newUpdater(ConflatedBroadcastChannel.class, "_updating");
        onCloseHandler$FU = AtomicReferenceFieldUpdater.newUpdater(ConflatedBroadcastChannel.class, Object.class, "onCloseHandler");
    }

    public ConflatedBroadcastChannel() {
        this._state = INITIAL_STATE;
        this._updating = 0;
        this.onCloseHandler = null;
    }

    private final Subscriber<E>[] addSubscriber(Subscriber<E>[] list, Subscriber<E> subscriber) {
        if (list != null) {
            return (Subscriber[]) ArraysKt.plus(list, subscriber);
        }
        Subscriber<E>[] subscriberArr = new Subscriber[1];
        for (int i = 0; i < 1; i++) {
            subscriberArr[i] = subscriber;
        }
        return subscriberArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void closeSubscriber(Subscriber<E> subscriber) {
        boolean z;
        do {
            Object obj = this._state;
            if (obj instanceof Closed) {
                return;
            }
            if (!(obj instanceof State)) {
                throw new IllegalStateException(Intrinsics.stringPlus("Invalid state ", obj).toString());
            }
            State state = (State) obj;
            Object obj2 = state.value;
            Subscriber<E>[] subscriberArr = state.subscribers;
            Intrinsics.checkNotNull(subscriberArr);
            State state2 = new State(obj2, removeSubscriber(subscriberArr, subscriber));
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
            while (true) {
                if (atomicReferenceFieldUpdater.compareAndSet(this, obj, state2)) {
                    z = true;
                    break;
                } else if (atomicReferenceFieldUpdater.get(this) != obj) {
                    z = false;
                    break;
                }
            }
        } while (!z);
    }

    public static /* synthetic */ void getValue$annotations() {
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

    private final Closed offerInternal(E element) {
        Object obj;
        boolean z;
        if (!_updating$FU.compareAndSet(this, 0, 1)) {
            return null;
        }
        do {
            try {
                obj = this._state;
                if (obj instanceof Closed) {
                    Closed closed = (Closed) obj;
                    this._updating = 0;
                    return closed;
                }
                if (!(obj instanceof State)) {
                    throw new IllegalStateException(Intrinsics.stringPlus("Invalid state ", obj).toString());
                }
                State state = new State(element, ((State) obj).subscribers);
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
                while (true) {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, obj, state)) {
                        z = true;
                        break;
                    }
                    if (atomicReferenceFieldUpdater.get(this) != obj) {
                        z = false;
                        break;
                    }
                }
            } catch (Throwable th) {
                this._updating = 0;
                throw th;
            }
        } while (!z);
        Subscriber<E>[] subscriberArr = ((State) obj).subscribers;
        if (subscriberArr != null) {
            int length = subscriberArr.length;
            int i = 0;
            while (i < length) {
                Subscriber<E> subscriber = subscriberArr[i];
                i++;
                subscriber.offerInternal(element);
            }
        }
        this._updating = 0;
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <R> void registerSelectSend(SelectInstance<? super R> select, E element, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block) {
        if (select.trySelect()) {
            Closed closedOfferInternal = offerInternal(element);
            if (closedOfferInternal == null) {
                UndispatchedKt.startCoroutineUnintercepted(block, this, select.getCompletion());
            } else {
                select.resumeSelectWithException(closedOfferInternal.getSendException());
            }
        }
    }

    private final Subscriber<E>[] removeSubscriber(Subscriber<E>[] list, Subscriber<E> subscriber) {
        int length = list.length;
        int iIndexOf = ArraysKt.indexOf(list, subscriber);
        if (length == 1) {
            return null;
        }
        Subscriber<E>[] subscriberArr = new Subscriber[length - 1];
        ArraysKt___ArraysJvmKt.copyInto$default(list, subscriberArr, 0, 0, iIndexOf, 6, (Object) null);
        ArraysKt___ArraysJvmKt.copyInto$default(list, subscriberArr, iIndexOf, iIndexOf + 1, 0, 8, (Object) null);
        return subscriberArr;
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    /* JADX INFO: renamed from: close, reason: merged with bridge method [inline-methods] */
    public boolean cancel(@Nullable Throwable cause) {
        Object obj;
        int i;
        boolean z;
        do {
            obj = this._state;
            i = 0;
            if (obj instanceof Closed) {
                return false;
            }
            if (!(obj instanceof State)) {
                throw new IllegalStateException(Intrinsics.stringPlus("Invalid state ", obj).toString());
            }
            Closed closed = cause == null ? CLOSED : new Closed(cause);
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
            while (true) {
                if (atomicReferenceFieldUpdater.compareAndSet(this, obj, closed)) {
                    z = true;
                    break;
                }
                if (atomicReferenceFieldUpdater.get(this) != obj) {
                    z = false;
                    break;
                }
            }
        } while (!z);
        Subscriber<E>[] subscriberArr = ((State) obj).subscribers;
        if (subscriberArr != null) {
            int length = subscriberArr.length;
            while (i < length) {
                Subscriber<E> subscriber = subscriberArr[i];
                i++;
                subscriber.cancel(cause);
            }
        }
        invokeOnCloseHandler(cause);
        return true;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    public SelectClause2<E, SendChannel<E>> getOnSend() {
        return new SelectClause2<E, SendChannel<? super E>>(this) { // from class: kotlinx.coroutines.channels.ConflatedBroadcastChannel$onSend$1
            public final /* synthetic */ ConflatedBroadcastChannel<E> this$0;

            {
                this.this$0 = this;
            }

            @Override // kotlinx.coroutines.selects.SelectClause2
            public <R> void registerSelectClause2(@NotNull SelectInstance<? super R> select, E param, @NotNull Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block) {
                this.this$0.registerSelectSend(select, param, block);
            }
        };
    }

    public final E getValue() throws Throwable {
        Object obj = this._state;
        if (obj instanceof Closed) {
            throw ((Closed) obj).getValueException();
        }
        if (!(obj instanceof State)) {
            throw new IllegalStateException(Intrinsics.stringPlus("Invalid state ", obj).toString());
        }
        E e = (E) ((State) obj).value;
        if (e != UNDEFINED) {
            return e;
        }
        throw new IllegalStateException("No value");
    }

    @Nullable
    public final E getValueOrNull() {
        Object obj = this._state;
        if (obj instanceof Closed) {
            return null;
        }
        if (!(obj instanceof State)) {
            throw new IllegalStateException(Intrinsics.stringPlus("Invalid state ", obj).toString());
        }
        Symbol symbol = UNDEFINED;
        E e = (E) ((State) obj).value;
        if (e == symbol) {
            return null;
        }
        return e;
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
        Object obj2 = this._state;
        if (obj2 instanceof Closed) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = onCloseHandler$FU;
            Symbol symbol = AbstractChannelKt.HANDLER_INVOKED;
            do {
                if (atomicReferenceFieldUpdater2.compareAndSet(this, handler, symbol)) {
                    z = true;
                    break;
                }
            } while (atomicReferenceFieldUpdater2.get(this) == handler);
            if (z) {
                handler.invoke(((Closed) obj2).closeCause);
            }
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean isClosedForSend() {
        return this._state instanceof Closed;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = {}))
    public boolean offer(E e) {
        return BroadcastChannel.DefaultImpls.offer(this, e);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.channels.BroadcastChannel
    @NotNull
    public ReceiveChannel<E> openSubscription() {
        boolean z;
        Subscriber subscriber = new Subscriber(this);
        do {
            Object obj = this._state;
            if (obj instanceof Closed) {
                subscriber.cancel(((Closed) obj).closeCause);
                return subscriber;
            }
            if (!(obj instanceof State)) {
                throw new IllegalStateException(Intrinsics.stringPlus("Invalid state ", obj).toString());
            }
            State state = (State) obj;
            Object obj2 = state.value;
            if (obj2 != UNDEFINED) {
                subscriber.offerInternal(obj2);
            }
            State state2 = new State(state.value, addSubscriber(state.subscribers, subscriber));
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
            while (true) {
                if (atomicReferenceFieldUpdater.compareAndSet(this, obj, state2)) {
                    z = true;
                    break;
                }
                if (atomicReferenceFieldUpdater.get(this) != obj) {
                    z = false;
                    break;
                }
            }
        } while (!z);
        return subscriber;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Nullable
    public Object send(E e, @NotNull Continuation<? super Unit> continuation) throws Throwable {
        Closed closedOfferInternal = offerInternal(e);
        if (closedOfferInternal != null) {
            throw closedOfferInternal.getSendException();
        }
        if (IntrinsicsKt.getCOROUTINE_SUSPENDED() == null) {
            return null;
        }
        return Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    /* JADX INFO: renamed from: trySend-JP2dKIU */
    public Object mo1823trySendJP2dKIU(E element) {
        Closed closedOfferInternal = offerInternal(element);
        return closedOfferInternal == null ? ChannelResult.INSTANCE.m1843successJP2dKIU(Unit.INSTANCE) : ChannelResult.INSTANCE.m1841closedJP2dKIU(closedOfferInternal.getSendException());
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    public void cancel(@Nullable CancellationException cause) {
        cancel(cause);
    }

    public ConflatedBroadcastChannel(E e) {
        this();
        _state$FU.lazySet(this, new State(e, null));
    }
}
