package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlinx.coroutines.JobKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Context.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\u001f\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\nR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/flow/CancellableFlowImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/CancellableFlow;", "flow", "Lkotlinx/coroutines/flow/Flow;", "(Lkotlinx/coroutines/flow/Flow;)V", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
final class CancellableFlowImpl<T> implements CancellableFlow<T> {

    @NotNull
    private final Flow<T> flow;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.CancellableFlowImpl$collect$2, reason: invalid class name */
    /* JADX INFO: compiled from: Context.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "it", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    public static final class AnonymousClass2<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ FlowCollector<T> $collector;

        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass2(FlowCollector<? super T> flowCollector) {
            this.$collector = flowCollector;
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) throws Throwable {
            CancellableFlowImpl$collect$2$emit$1 cancellableFlowImpl$collect$2$emit$1;
            if (continuation instanceof CancellableFlowImpl$collect$2$emit$1) {
                cancellableFlowImpl$collect$2$emit$1 = (CancellableFlowImpl$collect$2$emit$1) continuation;
                int i = cancellableFlowImpl$collect$2$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    cancellableFlowImpl$collect$2$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    cancellableFlowImpl$collect$2$emit$1 = new CancellableFlowImpl$collect$2$emit$1(this, continuation);
                }
            } else {
                cancellableFlowImpl$collect$2$emit$1 = new CancellableFlowImpl$collect$2$emit$1(this, continuation);
            }
            Object obj = cancellableFlowImpl$collect$2$emit$1.result;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = cancellableFlowImpl$collect$2$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                JobKt.ensureActive(cancellableFlowImpl$collect$2$emit$1.getContext());
                FlowCollector<T> flowCollector = this.$collector;
                cancellableFlowImpl$collect$2$emit$1.label = 1;
                if (flowCollector.emit(t, cancellableFlowImpl$collect$2$emit$1) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CancellableFlowImpl(@NotNull Flow<? extends T> flow) {
        this.flow = flow;
    }

    @Override // kotlinx.coroutines.flow.Flow
    @Nullable
    public Object collect(@NotNull FlowCollector<? super T> flowCollector, @NotNull Continuation<? super Unit> continuation) {
        Object objCollect = this.flow.collect(new AnonymousClass2(flowCollector), continuation);
        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }
}
