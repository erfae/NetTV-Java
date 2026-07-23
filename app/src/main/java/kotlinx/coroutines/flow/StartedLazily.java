package kotlinx.coroutines.flow;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: SharingStarted.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016J\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/flow/StartedLazily;", "Lkotlinx/coroutines/flow/SharingStarted;", "()V", "command", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/SharingCommand;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
final class StartedLazily implements SharingStarted {
    @Override // kotlinx.coroutines.flow.SharingStarted
    @NotNull
    public Flow<SharingCommand> command(@NotNull StateFlow<Integer> subscriptionCount) {
        return FlowKt.flow(new AnonymousClass1(subscriptionCount, null));
    }

    @NotNull
    public String toString() {
        return "SharingStarted.Lazily";
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.StartedLazily$command$1, reason: invalid class name */
    /* JADX INFO: compiled from: SharingStarted.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlinx/coroutines/flow/SharingCommand;", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.flow.StartedLazily$command$1", f = "SharingStarted.kt", i = {}, l = {155}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<FlowCollector<? super SharingCommand>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ StateFlow<Integer> $subscriptionCount;
        private /* synthetic */ Object L$0;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(StateFlow<Integer> stateFlow, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$subscriptionCount = stateFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$subscriptionCount, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super SharingCommand> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                StateFlow<Integer> stateFlow = this.$subscriptionCount;
                C00481 c00481 = new C00481(booleanRef, flowCollector);
                this.label = 1;
                if (stateFlow.collect(c00481, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }

        /* JADX INFO: renamed from: kotlinx.coroutines.flow.StartedLazily$command$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: SharingStarted.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "count", "", "emit", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = 48)
        public static final class C00481<T> implements FlowCollector, SuspendFunction {
            public final /* synthetic */ FlowCollector<SharingCommand> $$this$flow;
            public final /* synthetic */ Ref.BooleanRef $started;

            /* JADX WARN: Multi-variable type inference failed */
            public C00481(Ref.BooleanRef booleanRef, FlowCollector<? super SharingCommand> flowCollector) {
                this.$started = booleanRef;
                this.$$this$flow = flowCollector;
            }

            /* JADX WARN: Code duplicated, block: B:7:0x0013  */
            @Nullable
            public final Object emit(int i, @NotNull Continuation<? super Unit> continuation) throws Throwable {
                StartedLazily$command$1$1$emit$1 startedLazily$command$1$1$emit$1;
                if (continuation instanceof StartedLazily$command$1$1$emit$1) {
                    startedLazily$command$1$1$emit$1 = (StartedLazily$command$1$1$emit$1) continuation;
                    int i2 = startedLazily$command$1$1$emit$1.label;
                    if ((i2 & Integer.MIN_VALUE) != 0) {
                        startedLazily$command$1$1$emit$1.label = i2 - Integer.MIN_VALUE;
                    } else {
                        startedLazily$command$1$1$emit$1 = new StartedLazily$command$1$1$emit$1(this, continuation);
                    }
                } else {
                    startedLazily$command$1$1$emit$1 = new StartedLazily$command$1$1$emit$1(this, continuation);
                }
                Object obj = startedLazily$command$1$1$emit$1.result;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i3 = startedLazily$command$1$1$emit$1.label;
                if (i3 == 0) {
                    ResultKt.throwOnFailure(obj);
                    if (i > 0) {
                        Ref.BooleanRef booleanRef = this.$started;
                        if (!booleanRef.element) {
                            booleanRef.element = true;
                            FlowCollector<SharingCommand> flowCollector = this.$$this$flow;
                            SharingCommand sharingCommand = SharingCommand.START;
                            startedLazily$command$1$1$emit$1.label = 1;
                            if (flowCollector.emit(sharingCommand, startedLazily$command$1$1$emit$1) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
                if (i3 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                return emit(((Number) obj).intValue(), (Continuation<? super Unit>) continuation);
            }
        }
    }
}
