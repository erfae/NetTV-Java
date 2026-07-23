package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.ObsoleteCoroutinesApi;
import kotlinx.coroutines.selects.SelectClause1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Channels.common.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\u001a\u001a\u0010\u0002\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0001\u001aC\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\b*\b\u0012\u0004\u0012\u0002H\t0\n2\u001d\u0010\u000b\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0004\u0012\u0004\u0012\u0002H\b0\f¢\u0006\u0002\b\rH\u0087\b¢\u0006\u0002\u0010\u000e\u001aP\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\b*\b\u0012\u0004\u0012\u0002H\t0\u00042\u001d\u0010\u000b\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0004\u0012\u0004\u0012\u0002H\b0\f¢\u0006\u0002\b\rH\u0086\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u000f\u001a5\u0010\u0010\u001a\u00020\u0003\"\u0004\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\n2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00030\fH\u0087Hø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a5\u0010\u0010\u001a\u00020\u0003\"\u0004\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\u00042\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00030\fH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a$\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\t0\u0015\"\b\b\u0000\u0010\t*\u00020\u0016*\b\u0012\u0004\u0012\u0002H\t0\u0004H\u0007\u001a'\u0010\u0017\u001a\u0004\u0018\u0001H\t\"\b\b\u0000\u0010\t*\u00020\u0016*\b\u0012\u0004\u0012\u0002H\t0\u0004H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001a'\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\t0\u001a\"\u0004\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\u0004H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0018\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, d2 = {"DEFAULT_CLOSE_MESSAGE", "", "cancelConsumed", "", "Lkotlinx/coroutines/channels/ReceiveChannel;", "cause", "", "consume", "R", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/BroadcastChannel;", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/channels/BroadcastChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "consumeEach", "action", "(Lkotlinx/coroutines/channels/BroadcastChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onReceiveOrNull", "Lkotlinx/coroutines/selects/SelectClause1;", "", "receiveOrNull", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toList", "", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = 48, xs = "kotlinx/coroutines/channels/ChannelsKt")
final /* synthetic */ class ChannelsKt__Channels_commonKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1, reason: invalid class name */
    /* JADX INFO: compiled from: Channels.common.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt", f = "Channels.common.kt", i = {0, 0}, l = {104}, m = "consumeEach", n = {"action", "$this$consume$iv"}, s = {"L$0", "L$1"})
    public static final class AnonymousClass1<E> extends ContinuationImpl {
        public Function1 L$0;
        public ReceiveChannel L$1;
        public ChannelIterator L$2;
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__Channels_commonKt.consumeEach((ReceiveChannel) null, (Function1) null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3, reason: invalid class name */
    /* JADX INFO: compiled from: Channels.common.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt", f = "Channels.common.kt", i = {0, 0}, l = {TsExtractor.TS_STREAM_TYPE_AC3}, m = "consumeEach", n = {"action", "channel$iv"}, s = {"L$0", "L$1"})
    public static final class AnonymousClass3<E> extends ContinuationImpl {
        public Function1 L$0;
        public ReceiveChannel L$1;
        public ChannelIterator L$2;
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__Channels_commonKt.consumeEach((BroadcastChannel) null, (Function1) null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Channels.common.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt", f = "Channels.common.kt", i = {0, 0}, l = {148}, m = "toList", n = {"$this$toList_u24lambda_u2d3", "$this$consume$iv$iv"}, s = {"L$1", "L$2"})
    public static final class C01191<E> extends ContinuationImpl {
        public List L$0;
        public List L$1;
        public ReceiveChannel L$2;
        public ChannelIterator L$3;
        public int label;
        public /* synthetic */ Object result;

        public C01191(Continuation<? super C01191> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt.toList(null, this);
        }
    }

    @PublishedApi
    public static final void cancelConsumed(@NotNull ReceiveChannel<?> receiveChannel, @Nullable Throwable th) {
        CancellationException CancellationException = null;
        if (th != null) {
            CancellationException = th instanceof CancellationException ? (CancellationException) th : null;
            if (CancellationException == null) {
                CancellationException = ExceptionsKt.CancellationException("Channel was consumed, consumer had failed", th);
            }
        }
        receiveChannel.cancel(CancellationException);
    }

    @ObsoleteCoroutinesApi
    public static final <E, R> R consume(@NotNull BroadcastChannel<E> broadcastChannel, @NotNull Function1<? super ReceiveChannel<? extends E>, ? extends R> function1) {
        ReceiveChannel<E> receiveChannelOpenSubscription = broadcastChannel.openSubscription();
        try {
            return function1.invoke(receiveChannelOpenSubscription);
        } finally {
            InlineMarker.finallyStart(1);
            ReceiveChannel.DefaultImpls.cancel$default((ReceiveChannel) receiveChannelOpenSubscription, (CancellationException) null, 1, (Object) null);
            InlineMarker.finallyEnd(1);
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0054 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:24:0x0055  */
    /* JADX WARN: Code duplicated, block: B:27:0x0062 A[Catch: all -> 0x0079, TryCatch #1 {all -> 0x0079, blocks: (B:25:0x0059, B:27:0x0062, B:21:0x0046, B:28:0x006d, B:20:0x0042), top: B:39:0x0042 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0055 -> B:14:0x0032). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @org.jetbrains.annotations.Nullable
    public static final <E> java.lang.Object consumeEach(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.ReceiveChannel<? extends E> r6, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3f
            if (r2 != r3) goto L37
            kotlinx.coroutines.channels.ChannelIterator r6 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r7 = r0.L$1
            kotlin.jvm.functions.Function1 r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L35
            r5 = r0
            r0 = r6
            r6 = r7
            r7 = r2
        L32:
            r2 = r1
            r1 = r5
            goto L59
        L35:
            r6 = move-exception
            goto L7d
        L37:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3f:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch: java.lang.Throwable -> L79
        L46:
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L79
            r0.L$1 = r6     // Catch: java.lang.Throwable -> L79
            r0.L$2 = r8     // Catch: java.lang.Throwable -> L79
            r0.label = r3     // Catch: java.lang.Throwable -> L79
            java.lang.Object r2 = r8.hasNext(r0)     // Catch: java.lang.Throwable -> L79
            if (r2 != r1) goto L55
            return r1
        L55:
            r5 = r0
            r0 = r8
            r8 = r2
            goto L32
        L59:
            r4 = 0
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L79
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Throwable -> L79
            if (r8 == 0) goto L6d
            java.lang.Object r8 = r0.next()     // Catch: java.lang.Throwable -> L79
            r7.invoke(r8)     // Catch: java.lang.Throwable -> L79
            r8 = r0
            r0 = r1
            r1 = r2
            goto L46
        L6d:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L79
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r7
        L79:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
        L7d:
            throw r6     // Catch: java.lang.Throwable -> L7e
        L7e:
            r8 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.consumeEach(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final <E> Object consumeEach$$forInline(ReceiveChannel<? extends E> receiveChannel, Function1<? super E, Unit> function1, Continuation<? super Unit> continuation) {
        try {
            ChannelIterator<? extends E> it = receiveChannel.iterator();
            while (true) {
                InlineMarker.mark(3);
                InlineMarker.mark(0);
                Object objHasNext = it.hasNext(null);
                InlineMarker.mark(1);
                if (!((Boolean) objHasNext).booleanValue()) {
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed(receiveChannel, null);
                    InlineMarker.finallyEnd(1);
                    return unit;
                }
                function1.invoke(it.next());
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                InlineMarker.finallyStart(1);
                ChannelsKt.cancelConsumed(receiveChannel, th);
                InlineMarker.finallyEnd(1);
                throw th2;
            }
        }
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'onReceiveCatching'")
    @NotNull
    public static final <E> SelectClause1<E> onReceiveOrNull(@NotNull ReceiveChannel<? extends E> receiveChannel) {
        return receiveChannel.getOnReceiveOrNull();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'receiveCatching'", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    @Nullable
    public static final <E> Object receiveOrNull(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull Continuation<? super E> continuation) {
        return receiveChannel.receiveOrNull(continuation);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x005b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:25:0x0065 A[Catch: all -> 0x0077, TryCatch #1 {all -> 0x0077, blocks: (B:23:0x005c, B:25:0x0065, B:20:0x004b, B:26:0x006d, B:19:0x0045), top: B:37:0x0045 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0059 -> B:23:0x005c). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @org.jetbrains.annotations.Nullable
    public static final <E> java.lang.Object toList(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.ReceiveChannel<? extends E> r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.util.List<? extends E>> r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.C01191
            if (r0 == 0) goto L13
            r0 = r9
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.C01191) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3e
            if (r2 != r3) goto L36
            kotlinx.coroutines.channels.ChannelIterator r8 = r0.L$3
            kotlinx.coroutines.channels.ReceiveChannel r2 = r0.L$2
            java.util.List r4 = r0.L$1
            java.util.List r5 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L34
            r7 = r2
            r2 = r8
            r8 = r7
            goto L5c
        L34:
            r8 = move-exception
            goto L7a
        L36:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3e:
            kotlin.ResultKt.throwOnFailure(r9)
            java.util.List r9 = kotlin.collections.CollectionsKt.createListBuilder()
            kotlinx.coroutines.channels.ChannelIterator r2 = r8.iterator()     // Catch: java.lang.Throwable -> L77
            r4 = r9
            r5 = r4
        L4b:
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L77
            r0.L$1 = r4     // Catch: java.lang.Throwable -> L77
            r0.L$2 = r8     // Catch: java.lang.Throwable -> L77
            r0.L$3 = r2     // Catch: java.lang.Throwable -> L77
            r0.label = r3     // Catch: java.lang.Throwable -> L77
            java.lang.Object r9 = r2.hasNext(r0)     // Catch: java.lang.Throwable -> L77
            if (r9 != r1) goto L5c
            return r1
        L5c:
            r6 = 0
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch: java.lang.Throwable -> L77
            boolean r9 = r9.booleanValue()     // Catch: java.lang.Throwable -> L77
            if (r9 == 0) goto L6d
            java.lang.Object r9 = r2.next()     // Catch: java.lang.Throwable -> L77
            r4.add(r9)     // Catch: java.lang.Throwable -> L77
            goto L4b
        L6d:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L77
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r6)
            java.util.List r8 = kotlin.collections.CollectionsKt.build(r5)
            return r8
        L77:
            r9 = move-exception
            r2 = r8
            r8 = r9
        L7a:
            throw r8     // Catch: java.lang.Throwable -> L7b
        L7b:
            r9 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.toList(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <E, R> R consume(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull Function1<? super ReceiveChannel<? extends E>, ? extends R> function1) {
        try {
            R rInvoke = function1.invoke(receiveChannel);
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed(receiveChannel, null);
            InlineMarker.finallyEnd(1);
            return rInvoke;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                InlineMarker.finallyStart(1);
                ChannelsKt.cancelConsumed(receiveChannel, th);
                InlineMarker.finallyEnd(1);
                throw th2;
            }
        }
    }

    @ObsoleteCoroutinesApi
    private static final <E> Object consumeEach$$forInline(BroadcastChannel<E> broadcastChannel, Function1<? super E, Unit> function1, Continuation<? super Unit> continuation) {
        ReceiveChannel<E> receiveChannelOpenSubscription = broadcastChannel.openSubscription();
        try {
            ChannelIterator<E> it = receiveChannelOpenSubscription.iterator();
            while (true) {
                InlineMarker.mark(3);
                InlineMarker.mark(0);
                Object objHasNext = it.hasNext(null);
                InlineMarker.mark(1);
                if (!((Boolean) objHasNext).booleanValue()) {
                    return Unit.INSTANCE;
                }
                function1.invoke(it.next());
            }
        } finally {
            InlineMarker.finallyStart(1);
            ReceiveChannel.DefaultImpls.cancel$default((ReceiveChannel) receiveChannelOpenSubscription, (CancellationException) null, 1, (Object) null);
            InlineMarker.finallyEnd(1);
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x005b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:25:0x005c  */
    /* JADX WARN: Code duplicated, block: B:28:0x0068 A[Catch: all -> 0x007f, TryCatch #3 {all -> 0x007f, blocks: (B:26:0x0060, B:28:0x0068, B:29:0x0073), top: B:46:0x0060 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x005c -> B:14:0x0032). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @kotlinx.coroutines.ObsoleteCoroutinesApi
    @org.jetbrains.annotations.Nullable
    public static final <E> java.lang.Object consumeEach(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.BroadcastChannel<E> r6, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.AnonymousClass3
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.AnonymousClass3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L3f
            if (r2 != r4) goto L37
            kotlinx.coroutines.channels.ChannelIterator r6 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r7 = r0.L$1
            kotlin.jvm.functions.Function1 r2 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L35
            r5 = r0
            r0 = r7
            r7 = r2
        L32:
            r2 = r1
            r1 = r5
            goto L60
        L35:
            r6 = move-exception
            goto L89
        L37:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3f:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ReceiveChannel r6 = r6.openSubscription()
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch: java.lang.Throwable -> L85
            r5 = r8
            r8 = r6
            r6 = r5
        L4d:
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L82
            r0.L$1 = r8     // Catch: java.lang.Throwable -> L82
            r0.L$2 = r6     // Catch: java.lang.Throwable -> L82
            r0.label = r4     // Catch: java.lang.Throwable -> L82
            java.lang.Object r2 = r6.hasNext(r0)     // Catch: java.lang.Throwable -> L82
            if (r2 != r1) goto L5c
            return r1
        L5c:
            r5 = r0
            r0 = r8
            r8 = r2
            goto L32
        L60:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L7f
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Throwable -> L7f
            if (r8 == 0) goto L73
            java.lang.Object r8 = r6.next()     // Catch: java.lang.Throwable -> L7f
            r7.invoke(r8)     // Catch: java.lang.Throwable -> L7f
            r8 = r0
            r0 = r1
            r1 = r2
            goto L4d
        L73:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L7f
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default(r0, r3, r4, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            return r6
        L7f:
            r6 = move-exception
            r7 = r0
            goto L89
        L82:
            r6 = move-exception
            r7 = r8
            goto L89
        L85:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
        L89:
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default(r7, r3, r4, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.consumeEach(kotlinx.coroutines.channels.BroadcastChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
