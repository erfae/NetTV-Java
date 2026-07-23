package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobCancellationException;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.selects.SelectClause2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Broadcast.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0012\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u00028\u00000\u00042\b\u0012\u0004\u0012\u00028\u00000\u0005B%\u0012\u0006\u0010.\u001a\u00020-\u0012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010/\u001a\u00020\u000e¢\u0006\u0004\b0\u00101J.\u0010\f\u001a\u00020\u00032#\u0010\u000b\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u00030\u0006H\u0097\u0001J\u0018\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00028\u0000H\u0097\u0001¢\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011H\u0096\u0001J\u001b\u0010\u0013\u001a\u00020\u00032\u0006\u0010\r\u001a\u00028\u0000H\u0096Aø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014J'\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u00152\u0006\u0010\r\u001a\u00028\u0000H\u0096\u0001ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\u0016\u0010\u0017J\u0012\u0010\u0019\u001a\u00020\u000e2\b\u0010\n\u001a\u0004\u0018\u00010\u0007H\u0007J\u0016\u0010\u0019\u001a\u00020\u00032\u000e\u0010\n\u001a\n\u0018\u00010\u001aj\u0004\u0018\u0001`\u001bJ\u0010\u0010\u001c\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0007H\u0016J\u0012\u0010\u001d\u001a\u00020\u000e2\b\u0010\n\u001a\u0004\u0018\u00010\u0007H\u0016R \u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058\u0004X\u0084\u0004¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020\u000e8\u0016X\u0097\u0005¢\u0006\u0006\u001a\u0004\b\"\u0010#R&\u0010(\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000%0$8\u0016X\u0096\u0005¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010)\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010#R\u001a\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00000%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u00062"}, d2 = {"Lkotlinx/coroutines/channels/BroadcastCoroutine;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/AbstractCoroutine;", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlinx/coroutines/channels/BroadcastChannel;", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "handler", "invokeOnClose", "element", "", "offer", "(Ljava/lang/Object;)Z", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "trySend", "cancel", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "close", "_channel", "Lkotlinx/coroutines/channels/BroadcastChannel;", "get_channel", "()Lkotlinx/coroutines/channels/BroadcastChannel;", "isClosedForSend", "()Z", "Lkotlinx/coroutines/selects/SelectClause2;", "Lkotlinx/coroutines/channels/SendChannel;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "isActive", "getChannel", "()Lkotlinx/coroutines/channels/SendChannel;", "channel", "Lkotlin/coroutines/CoroutineContext;", "parentContext", "active", "<init>", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/BroadcastChannel;Z)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
class BroadcastCoroutine<E> extends AbstractCoroutine<Unit> implements ProducerScope<E>, BroadcastChannel<E> {

    @NotNull
    private final BroadcastChannel<E> _channel;

    public BroadcastCoroutine(@NotNull CoroutineContext coroutineContext, @NotNull BroadcastChannel<E> broadcastChannel, boolean z) {
        super(coroutineContext, false, z);
        this._channel = broadcastChannel;
        initParentJob((Job) coroutineContext.get(Job.INSTANCE));
    }

    @Override // kotlinx.coroutines.JobSupport, kotlinx.coroutines.Job
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public final /* synthetic */ boolean cancel(Throwable cause) {
        if (cause == null) {
            cause = new JobCancellationException(cancellationExceptionMessage(), null, this);
        }
        cancelInternal(cause);
        return true;
    }

    @Override // kotlinx.coroutines.JobSupport
    public void cancelInternal(@NotNull Throwable cause) {
        CancellationException cancellationException$default = JobSupport.toCancellationException$default(this, cause, null, 1, null);
        this._channel.cancel(cancellationException$default);
        cancelCoroutine(cancellationException$default);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    /* JADX INFO: renamed from: close */
    public boolean cancel(@Nullable Throwable cause) {
        boolean zCancel = this._channel.cancel(cause);
        start();
        return zCancel;
    }

    @Override // kotlinx.coroutines.channels.ProducerScope
    @NotNull
    public SendChannel<E> getChannel() {
        return this;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    public SelectClause2<E, SendChannel<E>> getOnSend() {
        return this._channel.getOnSend();
    }

    @NotNull
    public final BroadcastChannel<E> get_channel() {
        return this._channel;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @ExperimentalCoroutinesApi
    public void invokeOnClose(@NotNull Function1<? super Throwable, Unit> handler) {
        this._channel.invokeOnClose(handler);
    }

    @Override // kotlinx.coroutines.AbstractCoroutine, kotlinx.coroutines.JobSupport, kotlinx.coroutines.Job
    public boolean isActive() {
        return super.isActive();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean isClosedForSend() {
        return this._channel.isClosedForSend();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = {}))
    public boolean offer(E element) {
        return this._channel.offer(element);
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    public final void onCancelled(@NotNull Throwable th, boolean z) {
        if (this._channel.cancel(th) || z) {
            return;
        }
        CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), th);
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    public void onCompleted(Unit unit) {
        SendChannel.DefaultImpls.close$default(this._channel, null, 1, null);
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    @NotNull
    public ReceiveChannel<E> openSubscription() {
        return this._channel.openSubscription();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Nullable
    public Object send(E e, @NotNull Continuation<? super Unit> continuation) {
        return this._channel.send(e, continuation);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    /* JADX INFO: renamed from: trySend-JP2dKIU */
    public Object mo1823trySendJP2dKIU(E element) {
        return this._channel.mo1823trySendJP2dKIU(element);
    }

    @Override // kotlinx.coroutines.JobSupport, kotlinx.coroutines.Job
    public final void cancel(@Nullable CancellationException cause) {
        if (cause == null) {
            cause = new JobCancellationException(cancellationExceptionMessage(), null, this);
        }
        cancelInternal(cause);
    }
}
