package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.JobKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Share.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
@DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1", f = "Share.kt", i = {}, l = {340}, m = "invokeSuspend", n = {}, s = {})
public final class FlowKt__ShareKt$launchSharingDeferred$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ CompletableDeferred<StateFlow<T>> $result;
    public final /* synthetic */ Flow<T> $upstream;
    private /* synthetic */ Object L$0;
    public int label;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: Share.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    public static final class AnonymousClass1<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ CoroutineScope $$this$launch;
        public final /* synthetic */ CompletableDeferred<StateFlow<T>> $result;
        public final /* synthetic */ Ref.ObjectRef<MutableStateFlow<T>> $state;

        public AnonymousClass1(Ref.ObjectRef<MutableStateFlow<T>> objectRef, CoroutineScope coroutineScope, CompletableDeferred<StateFlow<T>> completableDeferred) {
            this.$state = objectRef;
            this.$$this$launch = coroutineScope;
            this.$result = completableDeferred;
        }

        /* JADX WARN: Type inference failed for: r4v2, types: [T, kotlinx.coroutines.flow.MutableStateFlow, kotlinx.coroutines.flow.StateFlow] */
        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
            Unit unit;
            MutableStateFlow<T> mutableStateFlow = this.$state.element;
            if (mutableStateFlow == null) {
                unit = null;
            } else {
                mutableStateFlow.setValue(t);
                unit = Unit.INSTANCE;
            }
            if (unit == null) {
                CoroutineScope coroutineScope = this.$$this$launch;
                Ref.ObjectRef<MutableStateFlow<T>> objectRef = this.$state;
                CompletableDeferred<StateFlow<T>> completableDeferred = this.$result;
                ?? r4 = (T) StateFlowKt.MutableStateFlow(t);
                completableDeferred.complete(new ReadonlyStateFlow(r4, JobKt.getJob(coroutineScope.getCoroutineContext())));
                objectRef.element = r4;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FlowKt__ShareKt$launchSharingDeferred$1(Flow<? extends T> flow, CompletableDeferred<StateFlow<T>> completableDeferred, Continuation<? super FlowKt__ShareKt$launchSharingDeferred$1> continuation) {
        super(2, continuation);
        this.$upstream = flow;
        this.$result = completableDeferred;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
        FlowKt__ShareKt$launchSharingDeferred$1 flowKt__ShareKt$launchSharingDeferred$1 = new FlowKt__ShareKt$launchSharingDeferred$1(this.$upstream, this.$result, continuation);
        flowKt__ShareKt$launchSharingDeferred$1.L$0 = obj;
        return flowKt__ShareKt$launchSharingDeferred$1;
    }

    @Override // kotlin.jvm.functions.Function2
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
        return ((FlowKt__ShareKt$launchSharingDeferred$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type kotlin.coroutines.Continuation to kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1 for r6v1 'this'  kotlin.coroutines.Continuation
        	at jadx.core.dex.instructions.args.SSAVar.setType(SSAVar.java:114)
        	at jadx.core.dex.instructions.args.RegisterArg.setType(RegisterArg.java:52)
        	at jadx.core.dex.visitors.ModVisitor.removeCheckCast(ModVisitor.java:417)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:152)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
        /*
            r6 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r6.label
            r2 = 1
            if (r1 == 0) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> Lf
            goto L37
        Lf:
            r7 = move-exception
            goto L3a
        L11:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L19:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            kotlinx.coroutines.CoroutineScope r7 = (kotlinx.coroutines.CoroutineScope) r7
            kotlin.jvm.internal.Ref$ObjectRef r1 = new kotlin.jvm.internal.Ref$ObjectRef     // Catch: java.lang.Throwable -> Lf
            r1.<init>()     // Catch: java.lang.Throwable -> Lf
            kotlinx.coroutines.flow.Flow<T> r3 = r6.$upstream     // Catch: java.lang.Throwable -> Lf
            kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1$1 r4 = new kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1$1     // Catch: java.lang.Throwable -> Lf
            kotlinx.coroutines.CompletableDeferred<kotlinx.coroutines.flow.StateFlow<T>> r5 = r6.$result     // Catch: java.lang.Throwable -> Lf
            r4.<init>(r1, r7, r5)     // Catch: java.lang.Throwable -> Lf
            r6.label = r2     // Catch: java.lang.Throwable -> Lf
            java.lang.Object r7 = r3.collect(r4, r6)     // Catch: java.lang.Throwable -> Lf
            if (r7 != r0) goto L37
            return r0
        L37:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L3a:
            kotlinx.coroutines.CompletableDeferred<kotlinx.coroutines.flow.StateFlow<T>> r0 = r6.$result
            r0.completeExceptionally(r7)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
