package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.flow.internal.AbortFlowException;
import kotlinx.coroutines.flow.internal.FlowExceptions_commonKt;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Reduce.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u001a!\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001aE\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\"\u0010\u0004\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\t\u001a#\u0010\n\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001aG\u0010\n\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\"\u0010\u0004\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\t\u001ay\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\f*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\r\u001a\u0002H\f2H\b\u0004\u0010\u000e\u001aB\b\u0001\u0012\u0013\u0012\u0011H\f¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u0011H\u0001¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\f0\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u000fH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a!\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001a#\u0010\u0016\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001as\u0010\u0017\u001a\u0002H\u0018\"\u0004\b\u0000\u0010\u0018\"\b\b\u0001\u0010\u0001*\u0002H\u0018*\b\u0012\u0004\u0012\u0002H\u00010\u00022F\u0010\u000e\u001aB\b\u0001\u0012\u0013\u0012\u0011H\u0018¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u0011H\u0001¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u000fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u001a\u001a!\u0010\u001b\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001a#\u0010\u001c\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, d2 = {"first", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/Flow;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "predicate", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "firstOrNull", "fold", "R", "initial", "operation", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "acc", "value", "(Lkotlinx/coroutines/flow/Flow;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "last", "lastOrNull", "reduce", ExifInterface.LATITUDE_SOUTH, "accumulator", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "single", "singleOrNull", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
final /* synthetic */ class FlowKt__ReduceKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$1, reason: invalid class name */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0, 0}, l = {183}, m = "first", n = {"result", "collector$iv"}, s = {"L$0", "L$1"})
    public static final class AnonymousClass1<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public FlowKt__ReduceKt$first$$inlined$collectWhile$1 L$1;
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
            return FlowKt.first(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$3, reason: invalid class name */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0, 0, 0}, l = {183}, m = "first", n = {"predicate", "result", "collector$iv"}, s = {"L$0", "L$1", "L$2"})
    public static final class AnonymousClass3<T> extends ContinuationImpl {
        public Function2 L$0;
        public Ref.ObjectRef L$1;
        public FlowKt__ReduceKt$first$$inlined$collectWhile$2 L$2;
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
            return FlowKt.first(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0, 0}, l = {183}, m = "firstOrNull", n = {"result", "collector$iv"}, s = {"L$0", "L$1"})
    public static final class C01651<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1 L$1;
        public int label;
        public /* synthetic */ Object result;

        public C01651(Continuation<? super C01651> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.firstOrNull(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0, 0}, l = {183}, m = "firstOrNull", n = {"result", "collector$iv"}, s = {"L$0", "L$1"})
    public static final class C01663<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2 L$1;
        public int label;
        public /* synthetic */ Object result;

        public C01663(Continuation<? super C01663> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.firstOrNull(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0}, l = {44}, m = "fold", n = {"accumulator"}, s = {"L$0"})
    public static final class C01671<T, R> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public int label;
        public /* synthetic */ Object result;

        public C01671(Continuation<? super C01671> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__ReduceKt.fold(null, null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$2, reason: invalid class name */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = 176)
    public static final class AnonymousClass2<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ Ref.ObjectRef<R> $accumulator;
        public final /* synthetic */ Function3<R, T, Continuation<? super R>, Object> $operation;

        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass2(Ref.ObjectRef<R> objectRef, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3) {
            this.$accumulator = objectRef;
            this.$operation = function3;
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0013  */
        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) throws Throwable {
            FlowKt__ReduceKt$fold$2$emit$1 flowKt__ReduceKt$fold$2$emit$1;
            Ref.ObjectRef objectRef;
            if (continuation instanceof FlowKt__ReduceKt$fold$2$emit$1) {
                flowKt__ReduceKt$fold$2$emit$1 = (FlowKt__ReduceKt$fold$2$emit$1) continuation;
                int i = flowKt__ReduceKt$fold$2$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    flowKt__ReduceKt$fold$2$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    flowKt__ReduceKt$fold$2$emit$1 = new FlowKt__ReduceKt$fold$2$emit$1(this, continuation);
                }
            } else {
                flowKt__ReduceKt$fold$2$emit$1 = new FlowKt__ReduceKt$fold$2$emit$1(this, continuation);
            }
            Object obj = flowKt__ReduceKt$fold$2$emit$1.result;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = flowKt__ReduceKt$fold$2$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Ref.ObjectRef objectRef2 = this.$accumulator;
                Function3<R, T, Continuation<? super R>, Object> function3 = this.$operation;
                T t2 = objectRef2.element;
                flowKt__ReduceKt$fold$2$emit$1.L$0 = objectRef2;
                flowKt__ReduceKt$fold$2$emit$1.label = 1;
                Object objInvoke = function3.invoke((R) t2, t, flowKt__ReduceKt$fold$2$emit$1);
                if (objInvoke == coroutine_suspended) {
                    return coroutine_suspended;
                }
                obj = (T) objInvoke;
                objectRef = objectRef2;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                objectRef = flowKt__ReduceKt$fold$2$emit$1.L$0;
                ResultKt.throwOnFailure(obj);
            }
            objectRef.element = (T) obj;
            return Unit.INSTANCE;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Nullable
        public final Object emit$$forInline(T t, @NotNull Continuation<? super Unit> continuation) {
            InlineMarker.mark(4);
            new FlowKt__ReduceKt$fold$2$emit$1(this, continuation);
            InlineMarker.mark(5);
            Ref.ObjectRef<R> objectRef = this.$accumulator;
            objectRef.element = (T) this.$operation.invoke((R) objectRef.element, t, (Continuation<? super R>) continuation);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$last$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0}, l = {155}, m = "last", n = {"result"}, s = {"L$0"})
    public static final class C01681<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public int label;
        public /* synthetic */ Object result;

        public C01681(Continuation<? super C01681> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.last(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$last$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "it", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    public static final class C01692<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ Ref.ObjectRef<Object> $result;

        public C01692(Ref.ObjectRef<Object> objectRef) {
            this.$result = objectRef;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
            this.$result.element = t;
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0}, l = {167}, m = "lastOrNull", n = {"result"}, s = {"L$0"})
    public static final class C01701<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public int label;
        public /* synthetic */ Object result;

        public C01701(Continuation<? super C01701> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.lastOrNull(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "it", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    public static final class C01712<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ Ref.ObjectRef<T> $result;

        public C01712(Ref.ObjectRef<T> objectRef) {
            this.$result = objectRef;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
            this.$result.element = t;
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0}, l = {22}, m = "reduce", n = {"accumulator"}, s = {"L$0"})
    public static final class C01721<S, T extends S> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public int label;
        public /* synthetic */ Object result;

        public C01721(Continuation<? super C01721> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.reduce(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0003*\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u0003H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    public static final class C01732<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ Ref.ObjectRef<Object> $accumulator;
        public final /* synthetic */ Function3<S, T, Continuation<? super S>, Object> $operation;

        /* JADX WARN: Multi-variable type inference failed */
        public C01732(Ref.ObjectRef<Object> objectRef, Function3<? super S, ? super T, ? super Continuation<? super S>, ? extends Object> function3) {
            this.$accumulator = objectRef;
            this.$operation = function3;
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0013  */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) throws Throwable {
            FlowKt__ReduceKt$reduce$2$emit$1 flowKt__ReduceKt$reduce$2$emit$1;
            Ref.ObjectRef<Object> objectRef;
            if (continuation instanceof FlowKt__ReduceKt$reduce$2$emit$1) {
                flowKt__ReduceKt$reduce$2$emit$1 = (FlowKt__ReduceKt$reduce$2$emit$1) continuation;
                int i = flowKt__ReduceKt$reduce$2$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    flowKt__ReduceKt$reduce$2$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    flowKt__ReduceKt$reduce$2$emit$1 = new FlowKt__ReduceKt$reduce$2$emit$1(this, continuation);
                }
            } else {
                flowKt__ReduceKt$reduce$2$emit$1 = new FlowKt__ReduceKt$reduce$2$emit$1(this, continuation);
            }
            Object obj = flowKt__ReduceKt$reduce$2$emit$1.result;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = flowKt__ReduceKt$reduce$2$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                objectRef = this.$accumulator;
                Object obj2 = objectRef.element;
                if (obj2 != NullSurrogateKt.NULL) {
                    Function3<S, T, Continuation<? super S>, Object> function3 = this.$operation;
                    flowKt__ReduceKt$reduce$2$emit$1.L$0 = objectRef;
                    flowKt__ReduceKt$reduce$2$emit$1.label = 1;
                    t = (T) function3.invoke((S) obj2, t, flowKt__ReduceKt$reduce$2$emit$1);
                    if (t == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                Ref.ObjectRef<Object> objectRef2 = flowKt__ReduceKt$reduce$2$emit$1.L$0;
                ResultKt.throwOnFailure(obj);
                objectRef = objectRef2;
                t = (T) obj;
            }
            objectRef.element = t;
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$single$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0}, l = {57}, m = "single", n = {"result"}, s = {"L$0"})
    public static final class C01741<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public int label;
        public /* synthetic */ Object result;

        public C01741(Continuation<? super C01741> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.single(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$single$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    public static final class C01752<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ Ref.ObjectRef<Object> $result;

        public C01752(Ref.ObjectRef<Object> objectRef) {
            this.$result = objectRef;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
            Ref.ObjectRef<Object> objectRef = this.$result;
            if (!(objectRef.element == NullSurrogateKt.NULL)) {
                throw new IllegalArgumentException("Flow has more than one element".toString());
            }
            objectRef.element = t;
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$singleOrNull$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", f = "Reduce.kt", i = {0, 0}, l = {183}, m = "singleOrNull", n = {"result", "collector$iv"}, s = {"L$0", "L$1"})
    public static final class C01761<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public FlowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1 L$1;
        public int label;
        public /* synthetic */ Object result;

        public C01761(Continuation<? super C01761> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.singleOrNull(null, this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object first(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowKt__ReduceKt$first$$inlined$collectWhile$1 flowKt__ReduceKt$first$$inlined$collectWhile$1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object obj = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<T> flowCollector = new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$1
                @Override // kotlinx.coroutines.flow.FlowCollector
                @Nullable
                public Object emit(T t, @NotNull Continuation<? super Unit> continuation2) {
                    objectRef2.element = t;
                    throw new AbortFlowException(this);
                }
            };
            try {
                anonymousClass1.L$0 = objectRef2;
                anonymousClass1.L$1 = flowCollector;
                anonymousClass1.label = 1;
                if (flow.collect(flowCollector, anonymousClass1) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowKt__ReduceKt$first$$inlined$collectWhile$1 = flowCollector;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$first$$inlined$collectWhile$1);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowKt__ReduceKt$first$$inlined$collectWhile$1 = anonymousClass1.L$1;
            objectRef = anonymousClass1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$first$$inlined$collectWhile$1);
            }
        }
        T t = objectRef.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException("Expected at least one element");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object firstOrNull(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) throws Throwable {
        C01651 c01651;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1 flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1;
        if (continuation instanceof C01651) {
            c01651 = (C01651) continuation;
            int i = c01651.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01651.label = i - Integer.MIN_VALUE;
            } else {
                c01651 = new C01651(continuation);
            }
        } else {
            c01651 = new C01651(continuation);
        }
        Object obj = c01651.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01651.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            FlowCollector<T> flowCollector = new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1
                @Override // kotlinx.coroutines.flow.FlowCollector
                @Nullable
                public Object emit(T t, @NotNull Continuation<? super Unit> continuation2) {
                    objectRef2.element = t;
                    throw new AbortFlowException(this);
                }
            };
            try {
                c01651.L$0 = objectRef2;
                c01651.L$1 = flowCollector;
                c01651.label = 1;
                if (flow.collect(flowCollector, c01651) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1 = flowCollector;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1 = c01651.L$1;
            objectRef = c01651.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1);
            }
        }
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public static final <T, R> Object fold(@NotNull Flow<? extends T> flow, R r, @NotNull Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3, @NotNull Continuation<? super R> continuation) throws Throwable {
        C01671 c01671;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C01671) {
            c01671 = (C01671) continuation;
            int i = c01671.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01671.label = i - Integer.MIN_VALUE;
            } else {
                c01671 = new C01671(continuation);
            }
        } else {
            c01671 = new C01671(continuation);
        }
        Object obj = c01671.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01671.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = r;
            FlowCollector<? super Object> anonymousClass2 = new AnonymousClass2<>(objectRef2, function3);
            c01671.L$0 = objectRef2;
            c01671.label = 1;
            if (flow.collect(anonymousClass2, c01671) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = c01671.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return objectRef.element;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <T, R> Object fold$$forInline(Flow<? extends T> flow, R r, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3, Continuation<? super R> continuation) {
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = r;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(objectRef, function3);
        InlineMarker.mark(0);
        flow.collect(anonymousClass2, continuation);
        InlineMarker.mark(1);
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object last(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) throws Throwable {
        C01681 c01681;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C01681) {
            c01681 = (C01681) continuation;
            int i = c01681.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01681.label = i - Integer.MIN_VALUE;
            } else {
                c01681 = new C01681(continuation);
            }
        } else {
            c01681 = new C01681(continuation);
        }
        Object obj = c01681.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01681.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<? super Object> c01692 = new C01692<>(objectRef2);
            c01681.L$0 = objectRef2;
            c01681.label = 1;
            if (flow.collect(c01692, c01681) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = c01681.L$0;
            ResultKt.throwOnFailure(obj);
        }
        T t = objectRef.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException("Expected at least one element");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object lastOrNull(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) throws Throwable {
        C01701 c01701;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C01701) {
            c01701 = (C01701) continuation;
            int i = c01701.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01701.label = i - Integer.MIN_VALUE;
            } else {
                c01701 = new C01701(continuation);
            }
        } else {
            c01701 = new C01701(continuation);
        }
        Object obj = c01701.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01701.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            FlowCollector<? super Object> c01712 = new C01712<>(objectRef2);
            c01701.L$0 = objectRef2;
            c01701.label = 1;
            if (flow.collect(c01712, c01701) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = c01701.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlinx.coroutines.internal.Symbol] */
    @Nullable
    public static final <S, T extends S> Object reduce(@NotNull Flow<? extends T> flow, @NotNull Function3<? super S, ? super T, ? super Continuation<? super S>, ? extends Object> function3, @NotNull Continuation<? super S> continuation) throws Throwable {
        C01721 c01721;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C01721) {
            c01721 = (C01721) continuation;
            int i = c01721.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01721.label = i - Integer.MIN_VALUE;
            } else {
                c01721 = new C01721(continuation);
            }
        } else {
            c01721 = new C01721(continuation);
        }
        Object obj = c01721.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01721.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = NullSurrogateKt.NULL;
            FlowCollector<? super Object> c01732 = new C01732<>(objectRef2, function3);
            c01721.L$0 = objectRef2;
            c01721.label = 1;
            if (flow.collect(c01732, c01721) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = c01721.L$0;
            ResultKt.throwOnFailure(obj);
        }
        T t = objectRef.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException("Empty flow can't be reduced");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object single(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) throws Throwable {
        C01741 c01741;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C01741) {
            c01741 = (C01741) continuation;
            int i = c01741.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01741.label = i - Integer.MIN_VALUE;
            } else {
                c01741 = new C01741(continuation);
            }
        } else {
            c01741 = new C01741(continuation);
        }
        Object obj = c01741.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01741.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<? super Object> c01752 = new C01752<>(objectRef2);
            c01741.L$0 = objectRef2;
            c01741.label = 1;
            if (flow.collect(c01752, c01741) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = c01741.L$0;
            ResultKt.throwOnFailure(obj);
        }
        T t = objectRef.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException("Flow is empty");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object singleOrNull(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) throws Throwable {
        C01761 c01761;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1 flowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1;
        if (continuation instanceof C01761) {
            c01761 = (C01761) continuation;
            int i = c01761.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01761.label = i - Integer.MIN_VALUE;
            } else {
                c01761 = new C01761(continuation);
            }
        } else {
            c01761 = new C01761(continuation);
        }
        Object obj = c01761.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01761.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<T> flowCollector = new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1
                @Override // kotlinx.coroutines.flow.FlowCollector
                @Nullable
                public Object emit(T t, @NotNull Continuation<? super Unit> continuation2) {
                    boolean z;
                    Ref.ObjectRef objectRef3 = objectRef2;
                    T t2 = objectRef3.element;
                    T t3 = (T) NullSurrogateKt.NULL;
                    if (t2 == t3) {
                        objectRef3.element = t;
                        z = true;
                    } else {
                        objectRef3.element = t3;
                        z = false;
                    }
                    if (z) {
                        return Unit.INSTANCE;
                    }
                    throw new AbortFlowException(this);
                }
            };
            try {
                c01761.L$0 = objectRef2;
                c01761.L$1 = flowCollector;
                c01761.label = 1;
                if (flow.collect(flowCollector, c01761) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1 = flowCollector;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1 = c01761.L$1;
            objectRef = c01761.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1);
            }
        }
        T t = objectRef.element;
        if (t == NullSurrogateKt.NULL) {
            return null;
        }
        return t;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x006b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:29:0x006c  */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object first(@NotNull Flow<? extends T> flow, @NotNull Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2, @NotNull Continuation<? super T> continuation) throws Throwable {
        AnonymousClass3 anonymousClass3;
        Ref.ObjectRef objectRef;
        AbortFlowException abortFlowException;
        FlowKt__ReduceKt$first$$inlined$collectWhile$2 flowKt__ReduceKt$first$$inlined$collectWhile$2;
        Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function3;
        Ref.ObjectRef objectRef2;
        T t;
        if (continuation instanceof AnonymousClass3) {
            anonymousClass3 = (AnonymousClass3) continuation;
            int i = anonymousClass3.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass3.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass3 = new AnonymousClass3(continuation);
            }
        } else {
            anonymousClass3 = new AnonymousClass3(continuation);
        }
        Object obj = anonymousClass3.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass3.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowKt__ReduceKt$first$$inlined$collectWhile$2 = anonymousClass3.L$2;
            objectRef2 = anonymousClass3.L$1;
            function3 = anonymousClass3.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e) {
                objectRef = objectRef2;
                function2 = function3;
                abortFlowException = e;
                FlowExceptions_commonKt.checkOwnership(abortFlowException, flowKt__ReduceKt$first$$inlined$collectWhile$2);
                function3 = function2;
                objectRef2 = objectRef;
            }
            t = objectRef2.element;
            if (t != NullSurrogateKt.NULL) {
                return t;
            }
            throw new NoSuchElementException(Intrinsics.stringPlus("Expected at least one element matching the predicate ", function3));
        }
        ResultKt.throwOnFailure(obj);
        objectRef = new Ref.ObjectRef();
        objectRef.element = (T) NullSurrogateKt.NULL;
        FlowKt__ReduceKt$first$$inlined$collectWhile$2 flowKt__ReduceKt$first$$inlined$collectWhile$3 = new FlowKt__ReduceKt$first$$inlined$collectWhile$2(function2, objectRef);
        try {
            anonymousClass3.L$0 = function2;
            anonymousClass3.L$1 = objectRef;
            anonymousClass3.L$2 = flowKt__ReduceKt$first$$inlined$collectWhile$3;
            anonymousClass3.label = 1;
            if (flow.collect(flowKt__ReduceKt$first$$inlined$collectWhile$3, anonymousClass3) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } catch (AbortFlowException e2) {
            abortFlowException = e2;
            flowKt__ReduceKt$first$$inlined$collectWhile$2 = flowKt__ReduceKt$first$$inlined$collectWhile$3;
            FlowExceptions_commonKt.checkOwnership(abortFlowException, flowKt__ReduceKt$first$$inlined$collectWhile$2);
        }
        function3 = function2;
        objectRef2 = objectRef;
        t = objectRef2.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException(Intrinsics.stringPlus("Expected at least one element matching the predicate ", function3));
        FlowExceptions_commonKt.checkOwnership(abortFlowException, flowKt__ReduceKt$first$$inlined$collectWhile$2);
        function3 = function2;
        objectRef2 = objectRef;
        t = objectRef2.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException(Intrinsics.stringPlus("Expected at least one element matching the predicate ", function3));
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object firstOrNull(@NotNull Flow<? extends T> flow, @NotNull Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2, @NotNull Continuation<? super T> continuation) throws Throwable {
        C01663 c01663;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2 flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2;
        if (continuation instanceof C01663) {
            c01663 = (C01663) continuation;
            int i = c01663.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c01663.label = i - Integer.MIN_VALUE;
            } else {
                c01663 = new C01663(continuation);
            }
        } else {
            c01663 = new C01663(continuation);
        }
        Object obj = c01663.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c01663.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2 flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$3 = new FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2(function2, objectRef2);
            try {
                c01663.L$0 = objectRef2;
                c01663.L$1 = flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$3;
                c01663.label = 1;
                if (flow.collect(flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$3, c01663) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2 = flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$3;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2 = c01663.L$1;
            objectRef = c01663.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2);
            }
        }
        return objectRef.element;
    }
}
