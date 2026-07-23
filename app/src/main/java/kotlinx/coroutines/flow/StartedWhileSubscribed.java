package kotlinx.coroutines.flow;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.DelayKt;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: SharingStarted.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u000bH\u0017J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lkotlinx/coroutines/flow/StartedWhileSubscribed;", "Lkotlinx/coroutines/flow/SharingStarted;", "stopTimeout", "", "replayExpiration", "(JJ)V", "command", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/SharingCommand;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "", "equals", "", "other", "", "hashCode", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
final class StartedWhileSubscribed implements SharingStarted {
    private final long replayExpiration;
    private final long stopTimeout;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.StartedWhileSubscribed$command$1, reason: invalid class name */
    /* JADX INFO: compiled from: SharingStarted.kt */
    @Metadata(bv = {}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlinx/coroutines/flow/SharingCommand;", "", "count", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.flow.StartedWhileSubscribed$command$1", f = "SharingStarted.kt", i = {1, 2, 3}, l = {178, 180, 182, 183, 185}, m = "invokeSuspend", n = {"$this$transformLatest", "$this$transformLatest", "$this$transformLatest"}, s = {"L$0", "L$0", "L$0"})
    public static final class AnonymousClass1 extends SuspendLambda implements Function3<FlowCollector<? super SharingCommand>, Integer, Continuation<? super Unit>, Object> {
        public /* synthetic */ int I$0;
        private /* synthetic */ Object L$0;
        public int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Object invoke(FlowCollector<? super SharingCommand> flowCollector, Integer num, Continuation<? super Unit> continuation) {
            return invoke(flowCollector, num.intValue(), continuation);
        }

        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super SharingCommand> flowCollector, int i, @Nullable Continuation<? super Unit> continuation) {
            AnonymousClass1 anonymousClass1 = StartedWhileSubscribed.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = flowCollector;
            anonymousClass1.I$0 = i;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:26:0x0070  */
        /* JADX WARN: Code duplicated, block: B:28:0x007c A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:31:0x008d A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:34:0x009b A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            FlowCollector flowCollector;
            SharingCommand sharingCommand;
            long j;
            SharingCommand sharingCommand2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        flowCollector = (FlowCollector) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        if (StartedWhileSubscribed.this.replayExpiration > 0) {
                            sharingCommand = SharingCommand.STOP;
                            this.L$0 = flowCollector;
                            this.label = 3;
                            if (flowCollector.emit(sharingCommand, this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            j = StartedWhileSubscribed.this.replayExpiration;
                            this.L$0 = flowCollector;
                            this.label = 4;
                            if (DelayKt.delay(j, this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        }
                    } else if (i == 3) {
                        flowCollector = (FlowCollector) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        j = StartedWhileSubscribed.this.replayExpiration;
                        this.L$0 = flowCollector;
                        this.label = 4;
                        if (DelayKt.delay(j, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else if (i == 4) {
                        flowCollector = (FlowCollector) this.L$0;
                        ResultKt.throwOnFailure(obj);
                    } else if (i != 5) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    sharingCommand2 = SharingCommand.STOP_AND_RESET_REPLAY_CACHE;
                    this.L$0 = null;
                    this.label = 5;
                    if (flowCollector.emit(sharingCommand2, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
                ResultKt.throwOnFailure(obj);
            } else {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                if (this.I$0 > 0) {
                    SharingCommand sharingCommand3 = SharingCommand.START;
                    this.label = 1;
                    if (flowCollector.emit(sharingCommand3, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    long j2 = StartedWhileSubscribed.this.stopTimeout;
                    this.L$0 = flowCollector;
                    this.label = 2;
                    if (DelayKt.delay(j2, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    if (StartedWhileSubscribed.this.replayExpiration > 0) {
                        sharingCommand = SharingCommand.STOP;
                        this.L$0 = flowCollector;
                        this.label = 3;
                        if (flowCollector.emit(sharingCommand, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        j = StartedWhileSubscribed.this.replayExpiration;
                        this.L$0 = flowCollector;
                        this.label = 4;
                        if (DelayKt.delay(j, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                    sharingCommand2 = SharingCommand.STOP_AND_RESET_REPLAY_CACHE;
                    this.L$0 = null;
                    this.label = 5;
                    if (flowCollector.emit(sharingCommand2, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.StartedWhileSubscribed$command$2, reason: invalid class name */
    /* JADX INFO: compiled from: SharingStarted.kt */
    @Metadata(bv = {}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/SharingCommand;", "it", "", "<anonymous>"}, k = 3, mv = {1, 6, 0})
    @DebugMetadata(c = "kotlinx.coroutines.flow.StartedWhileSubscribed$command$2", f = "SharingStarted.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<SharingCommand, Continuation<? super Boolean>, Object> {
        public /* synthetic */ Object L$0;

        public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull SharingCommand sharingCommand, @Nullable Continuation<? super Boolean> continuation) {
            return ((AnonymousClass2) create(sharingCommand, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            ResultKt.throwOnFailure(obj);
            return Boxing.boxBoolean(((SharingCommand) this.L$0) != SharingCommand.START);
        }
    }

    public StartedWhileSubscribed(long j, long j2) {
        this.stopTimeout = j;
        this.replayExpiration = j2;
        if (!(j >= 0)) {
            throw new IllegalArgumentException(("stopTimeout(" + j + " ms) cannot be negative").toString());
        }
        if (j2 >= 0) {
            return;
        }
        throw new IllegalArgumentException(("replayExpiration(" + j2 + " ms) cannot be negative").toString());
    }

    @Override // kotlinx.coroutines.flow.SharingStarted
    @NotNull
    public Flow<SharingCommand> command(@NotNull StateFlow<Integer> subscriptionCount) {
        return FlowKt.distinctUntilChanged(FlowKt.dropWhile(FlowKt.transformLatest(subscriptionCount, new AnonymousClass1(null)), new AnonymousClass2(null)));
    }

    public boolean equals(@Nullable Object other) {
        if (other instanceof StartedWhileSubscribed) {
            StartedWhileSubscribed startedWhileSubscribed = (StartedWhileSubscribed) other;
            if (this.stopTimeout == startedWhileSubscribed.stopTimeout && this.replayExpiration == startedWhileSubscribed.replayExpiration) {
                return true;
            }
        }
        return false;
    }

    @IgnoreJRERequirement
    public int hashCode() {
        long j = this.stopTimeout;
        int i = ((int) (j ^ (j >>> 32))) * 31;
        long j2 = this.replayExpiration;
        return i + ((int) (j2 ^ (j2 >>> 32)));
    }

    @NotNull
    public String toString() {
        List listCreateListBuilder = CollectionsKt.createListBuilder(2);
        if (this.stopTimeout > 0) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("stopTimeout=");
            sbM.append(this.stopTimeout);
            sbM.append("ms");
            listCreateListBuilder.add(sbM.toString());
        }
        if (this.replayExpiration < Long.MAX_VALUE) {
            StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("replayExpiration=");
            sbM2.append(this.replayExpiration);
            sbM2.append("ms");
            listCreateListBuilder.add(sbM2.toString());
        }
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("SharingStarted.WhileSubscribed(");
        sbM3.append(CollectionsKt___CollectionsKt.joinToString$default(listBuild, null, null, null, 0, null, null, 63, null));
        sbM3.append(')');
        return sbM3.toString();
    }
}
