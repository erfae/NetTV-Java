package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Actor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u00022\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\u0003BO\u0012\u0006\u0010#\u001a\u00020\"\u0012\f\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000$\u0012-\u0010\u001a\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000&\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0017¢\u0006\u0002\b'ø\u0001\u0000¢\u0006\u0004\b(\u0010)J\u001b\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\n\u0010\u000bJ&\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\u0006\u0010\u0005\u001a\u00028\u0000H\u0016ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u0012\u0010\u0012\u001a\u00020\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0016JX\u0010\u001b\u001a\u00020\u0006\"\u0004\b\u0001\u0010\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00010\u00142\u0006\u0010\u0016\u001a\u00028\u00002(\u0010\u001a\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0017H\u0016ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\u00188\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001d\u0010\u001eR&\u0010!\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 \u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006*"}, d2 = {"Lkotlinx/coroutines/channels/LazyActorCoroutine;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/ActorCoroutine;", "Lkotlinx/coroutines/selects/SelectClause2;", "Lkotlinx/coroutines/channels/SendChannel;", "element", "", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "offer", "(Ljava/lang/Object;)Z", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "trySend", "", "cause", "close", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "param", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "block", "registerSelectClause2", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "continuation", "Lkotlin/coroutines/Continuation;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "Lkotlin/coroutines/CoroutineContext;", "parentContext", "Lkotlinx/coroutines/channels/Channel;", "channel", "Lkotlinx/coroutines/channels/ActorScope;", "Lkotlin/ExtensionFunctionType;", "<init>", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
final class LazyActorCoroutine<E> extends ActorCoroutine<E> implements SelectClause2<E, SendChannel<? super E>> {

    @NotNull
    private Continuation<? super Unit> continuation;

    public LazyActorCoroutine(@NotNull CoroutineContext coroutineContext, @NotNull Channel<E> channel, @NotNull Function2<? super ActorScope<E>, ? super Continuation<? super Unit>, ? extends Object> function2) {
        super(coroutineContext, channel, false);
        this.continuation = IntrinsicsKt.createCoroutineUnintercepted(function2, this, this);
    }

    @Override // kotlinx.coroutines.channels.ChannelCoroutine, kotlinx.coroutines.channels.SendChannel
    /* JADX INFO: renamed from: close */
    public boolean cancel(@Nullable Throwable cause) {
        boolean zCancel = super.cancel(cause);
        start();
        return zCancel;
    }

    @Override // kotlinx.coroutines.channels.ChannelCoroutine, kotlinx.coroutines.channels.SendChannel
    @NotNull
    public SelectClause2<E, SendChannel<E>> getOnSend() {
        return this;
    }

    @Override // kotlinx.coroutines.channels.ChannelCoroutine, kotlinx.coroutines.channels.SendChannel
    public boolean offer(E element) {
        start();
        return super.offer(element);
    }

    @Override // kotlinx.coroutines.JobSupport
    public final void onStart() throws Throwable {
        CancellableKt.startCoroutineCancellable(this.continuation, this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.selects.SelectClause2
    public <R> void registerSelectClause2(@NotNull SelectInstance<? super R> select, E param, @NotNull Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block) {
        start();
        super.getOnSend().registerSelectClause2(select, param, block);
    }

    @Override // kotlinx.coroutines.channels.ChannelCoroutine, kotlinx.coroutines.channels.SendChannel
    @Nullable
    public Object send(E e, @NotNull Continuation<? super Unit> continuation) {
        start();
        Object objSend = super.send(e, continuation);
        return objSend == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objSend : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.channels.ChannelCoroutine, kotlinx.coroutines.channels.SendChannel
    @NotNull
    /* JADX INFO: renamed from: trySend-JP2dKIU */
    public Object mo1823trySendJP2dKIU(E element) {
        start();
        return super.mo1823trySendJP2dKIU(element);
    }
}
