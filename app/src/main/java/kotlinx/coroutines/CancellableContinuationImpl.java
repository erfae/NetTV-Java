package kotlinx.coroutines;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.Symbol;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: CancellableContinuationImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0011\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u00032\u00060\u0004j\u0002`\u0005B\u001f\u0012\f\u0010t\u001a\b\u0012\u0004\u0012\u00028\u00000s\u0012\u0006\u0010-\u001a\u00020'¢\u0006\u0006\b\u0088\u0001\u0010\u0089\u0001J\u000f\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u000b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\tH\u0002¢\u0006\u0004\b\u000b\u0010\fJ\u001e\u0010\u0010\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0082\b¢\u0006\u0004\b\u0010\u0010\u0011JB\u0010\u0017\u001a\u00020\u000e2'\u0010\u0016\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000e0\u0012j\u0002`\u00152\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0002¢\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u0019\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0019\u0010\bJ\u000f\u0010\u001a\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u001a\u0010\bJ\u0011\u0010\u001c\u001a\u0004\u0018\u00010\u001bH\u0002¢\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\u000eH\u0002¢\u0006\u0004\b\u001e\u0010\u001fJB\u0010\"\u001a\u00020\u000e2'\u0010\u0016\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000e0\u0012j\u0002`\u00152\b\u0010!\u001a\u0004\u0018\u00010 H\u0002¢\u0006\u0004\b\"\u0010#J8\u0010%\u001a\u00020$2'\u0010\u0016\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000e0\u0012j\u0002`\u0015H\u0002¢\u0006\u0004\b%\u0010&J\u0017\u0010)\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020'H\u0002¢\u0006\u0004\b)\u0010*JZ\u00100\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010 2\u0006\u0010-\u001a\u00020'2#\u0010.\u001a\u001f\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u00122\b\u0010/\u001a\u0004\u0018\u00010 H\u0002¢\u0006\u0004\b0\u00101JH\u00102\u001a\u00020\u000e2\b\u0010,\u001a\u0004\u0018\u00010 2\u0006\u0010-\u001a\u00020'2%\b\u0002\u0010.\u001a\u001f\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0012H\u0002¢\u0006\u0004\b2\u00103JJ\u00105\u001a\u0004\u0018\u0001042\b\u0010,\u001a\u0004\u0018\u00010 2\b\u0010/\u001a\u0004\u0018\u00010 2#\u0010.\u001a\u001f\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0012H\u0002¢\u0006\u0004\b5\u00106J\u0019\u00108\u001a\u0002072\b\u0010,\u001a\u0004\u0018\u00010 H\u0002¢\u0006\u0004\b8\u00109J\u000f\u0010:\u001a\u00020\u000eH\u0002¢\u0006\u0004\b:\u0010\u001fJ\u000f\u0010;\u001a\u00020\u000eH\u0016¢\u0006\u0004\b;\u0010\u001fJ\u000f\u0010<\u001a\u00020\u0006H\u0001¢\u0006\u0004\b<\u0010\bJ\u0017\u0010?\u001a\n\u0018\u00010=j\u0004\u0018\u0001`>H\u0016¢\u0006\u0004\b?\u0010@J\u0011\u0010C\u001a\u0004\u0018\u00010 H\u0010¢\u0006\u0004\bA\u0010BJ!\u0010G\u001a\u00020\u000e2\b\u0010D\u001a\u0004\u0018\u00010 2\u0006\u0010\n\u001a\u00020\tH\u0010¢\u0006\u0004\bE\u0010FJ\u0019\u0010H\u001a\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0016¢\u0006\u0004\bH\u0010\fJ\u0017\u0010K\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\tH\u0000¢\u0006\u0004\bI\u0010JJ\u001f\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020$2\b\u0010\n\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\u0017\u0010LJ8\u0010M\u001a\u00020\u000e2!\u0010.\u001a\u001d\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000e0\u00122\u0006\u0010\n\u001a\u00020\t¢\u0006\u0004\bM\u0010\u0018J\u0017\u0010P\u001a\u00020\t2\u0006\u0010O\u001a\u00020NH\u0016¢\u0006\u0004\bP\u0010QJ\u0011\u0010R\u001a\u0004\u0018\u00010 H\u0001¢\u0006\u0004\bR\u0010BJ \u0010U\u001a\u00020\u000e2\f\u0010T\u001a\b\u0012\u0004\u0012\u00028\u00000SH\u0016ø\u0001\u0000¢\u0006\u0004\bU\u0010VJ<\u0010X\u001a\u00020\u000e2\u0006\u0010W\u001a\u00028\u00002#\u0010.\u001a\u001f\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0012H\u0016¢\u0006\u0004\bX\u0010YJ8\u0010Z\u001a\u00020\u000e2'\u0010\u0016\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000e0\u0012j\u0002`\u0015H\u0016¢\u0006\u0004\bZ\u0010[J\u000f\u0010]\u001a\u00020\u000eH\u0000¢\u0006\u0004\b\\\u0010\u001fJ#\u0010\u001a\u001a\u0004\u0018\u00010 2\u0006\u0010W\u001a\u00028\u00002\b\u0010/\u001a\u0004\u0018\u00010 H\u0016¢\u0006\u0004\b\u001a\u0010^JH\u0010\u001a\u001a\u0004\u0018\u00010 2\u0006\u0010W\u001a\u00028\u00002\b\u0010/\u001a\u0004\u0018\u00010 2#\u0010.\u001a\u001f\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0012H\u0016¢\u0006\u0004\b\u001a\u0010_J\u0019\u0010a\u001a\u0004\u0018\u00010 2\u0006\u0010`\u001a\u00020\tH\u0016¢\u0006\u0004\ba\u0010bJ\u0017\u0010d\u001a\u00020\u000e2\u0006\u0010c\u001a\u00020 H\u0016¢\u0006\u0004\bd\u0010VJ\u001b\u0010f\u001a\u00020\u000e*\u00020e2\u0006\u0010W\u001a\u00028\u0000H\u0016¢\u0006\u0004\bf\u0010gJ\u001b\u0010h\u001a\u00020\u000e*\u00020e2\u0006\u0010`\u001a\u00020\tH\u0016¢\u0006\u0004\bh\u0010iJ\u001f\u0010l\u001a\u00028\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010!\u001a\u0004\u0018\u00010 H\u0010¢\u0006\u0004\bj\u0010kJ\u001b\u0010o\u001a\u0004\u0018\u00010\t2\b\u0010!\u001a\u0004\u0018\u00010 H\u0010¢\u0006\u0004\bm\u0010nJ\u000f\u0010q\u001a\u00020pH\u0016¢\u0006\u0004\bq\u0010rR \u0010t\u001a\b\u0012\u0004\u0012\u00028\u00000s8\u0000X\u0080\u0004¢\u0006\f\n\u0004\bt\u0010u\u001a\u0004\bv\u0010wR\u001a\u0010y\u001a\u00020x8\u0016X\u0096\u0004¢\u0006\f\n\u0004\by\u0010z\u001a\u0004\b{\u0010|R\u0018\u0010}\u001a\u0004\u0018\u00010\u001b8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b}\u0010~R\u0015\u0010\u0080\u0001\u001a\u00020p8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u007f\u0010rR\u0017\u0010!\u001a\u0004\u0018\u00010 8@X\u0080\u0004¢\u0006\u0007\u001a\u0005\b\u0081\u0001\u0010BR\u0016\u0010\u0082\u0001\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0082\u0001\u0010\bR\u0016\u0010\u0083\u0001\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0083\u0001\u0010\bR\u0016\u0010\u0084\u0001\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0084\u0001\u0010\bR\u001f\u0010\u0087\u0001\u001a\n\u0018\u00010\u0004j\u0004\u0018\u0001`\u00058VX\u0096\u0004¢\u0006\b\u001a\u0006\b\u0085\u0001\u0010\u0086\u0001\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u008a\u0001"}, d2 = {"Lkotlinx/coroutines/CancellableContinuationImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/DispatchedTask;", "Lkotlinx/coroutines/CancellableContinuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "", "isReusable", "()Z", "", "cause", "cancelLater", "(Ljava/lang/Throwable;)Z", "Lkotlin/Function0;", "", "block", "callCancelHandlerSafely", "(Lkotlin/jvm/functions/Function0;)V", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "handler", "callCancelHandler", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)V", "trySuspend", "tryResume", "Lkotlinx/coroutines/DisposableHandle;", "installParentHandle", "()Lkotlinx/coroutines/DisposableHandle;", "releaseClaimedReusableContinuation", "()V", "", "state", "multipleHandlersError", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;)V", "Lkotlinx/coroutines/CancelHandler;", "makeCancelHandler", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/CancelHandler;", "", "mode", "dispatchResume", "(I)V", "Lkotlinx/coroutines/NotCompleted;", "proposedUpdate", "resumeMode", "onCancellation", "idempotent", "resumedState", "(Lkotlinx/coroutines/NotCompleted;Ljava/lang/Object;ILkotlin/jvm/functions/Function1;Ljava/lang/Object;)Ljava/lang/Object;", "resumeImpl", "(Ljava/lang/Object;ILkotlin/jvm/functions/Function1;)V", "Lkotlinx/coroutines/internal/Symbol;", "tryResumeImpl", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/internal/Symbol;", "", "alreadyResumedError", "(Ljava/lang/Object;)Ljava/lang/Void;", "detachChildIfNonResuable", "initCancellability", "resetStateReusable", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "takeState$kotlinx_coroutines_core", "()Ljava/lang/Object;", "takeState", "takenState", "cancelCompletedResult$kotlinx_coroutines_core", "(Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelCompletedResult", "cancel", "parentCancelled$kotlinx_coroutines_core", "(Ljava/lang/Throwable;)V", "parentCancelled", "(Lkotlinx/coroutines/CancelHandler;Ljava/lang/Throwable;)V", "callOnCancellation", "Lkotlinx/coroutines/Job;", "parent", "getContinuationCancellationCause", "(Lkotlinx/coroutines/Job;)Ljava/lang/Throwable;", "getResult", "Lkotlin/Result;", "result", "resumeWith", "(Ljava/lang/Object;)V", "value", "resume", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "invokeOnCancellation", "(Lkotlin/jvm/functions/Function1;)V", "detachChild$kotlinx_coroutines_core", "detachChild", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "exception", "tryResumeWithException", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "token", "completeResume", "Lkotlinx/coroutines/CoroutineDispatcher;", "resumeUndispatched", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Object;)V", "resumeUndispatchedWithException", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Throwable;)V", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "getSuccessfulResult", "getExceptionalResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "getExceptionalResult", "", "toString", "()Ljava/lang/String;", "Lkotlin/coroutines/Continuation;", "delegate", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "Lkotlin/coroutines/CoroutineContext;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "parentHandle", "Lkotlinx/coroutines/DisposableHandle;", "getStateDebugRepresentation", "stateDebugRepresentation", "getState$kotlinx_coroutines_core", "isActive", "isCompleted", "isCancelled", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "<init>", "(Lkotlin/coroutines/Continuation;I)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
@PublishedApi
public class CancellableContinuationImpl<T> extends DispatchedTask<T> implements CancellableContinuation<T>, CoroutineStackFrame {
    private static final /* synthetic */ AtomicIntegerFieldUpdater _decision$FU = AtomicIntegerFieldUpdater.newUpdater(CancellableContinuationImpl.class, "_decision");
    private static final /* synthetic */ AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(CancellableContinuationImpl.class, Object.class, "_state");

    @NotNull
    private volatile /* synthetic */ int _decision;

    @NotNull
    private volatile /* synthetic */ Object _state;

    @NotNull
    private final CoroutineContext context;

    @NotNull
    private final Continuation<T> delegate;

    @Nullable
    private DisposableHandle parentHandle;

    /* JADX WARN: Multi-variable type inference failed */
    public CancellableContinuationImpl(@NotNull Continuation<? super T> continuation, int i) {
        super(i);
        this.delegate = continuation;
        this.context = continuation.get$context();
        this._decision = 0;
        this._state = Active.INSTANCE;
    }

    private final Void alreadyResumedError(Object proposedUpdate) {
        throw new IllegalStateException(Intrinsics.stringPlus("Already resumed, but proposed with update ", proposedUpdate).toString());
    }

    private final void callCancelHandlerSafely(Function0<Unit> block) {
        try {
            block.invoke();
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(get$context(), new CompletionHandlerException(Intrinsics.stringPlus("Exception in invokeOnCancellation handler for ", this), th));
        }
    }

    private final boolean cancelLater(Throwable cause) {
        if (isReusable()) {
            return ((DispatchedContinuation) this.delegate).postponeCancellation(cause);
        }
        return false;
    }

    private final void detachChildIfNonResuable() {
        if (isReusable()) {
            return;
        }
        detachChild$kotlinx_coroutines_core();
    }

    private final void dispatchResume(int mode) {
        if (tryResume()) {
            return;
        }
        DispatchedTaskKt.dispatch(this, mode);
    }

    private final String getStateDebugRepresentation() {
        Object obj = get_state();
        if (obj instanceof NotCompleted) {
            return "Active";
        }
        return obj instanceof CancelledContinuation ? "Cancelled" : "Completed";
    }

    private final DisposableHandle installParentHandle() {
        Job job = (Job) get$context().get(Job.INSTANCE);
        if (job == null) {
            return null;
        }
        DisposableHandle disposableHandleInvokeOnCompletion$default = Job.DefaultImpls.invokeOnCompletion$default(job, true, false, new ChildContinuation(this), 2, null);
        this.parentHandle = disposableHandleInvokeOnCompletion$default;
        return disposableHandleInvokeOnCompletion$default;
    }

    private final boolean isReusable() {
        return DispatchedTaskKt.isReusableMode(this.resumeMode) && ((DispatchedContinuation) this.delegate).isReusable();
    }

    private final CancelHandler makeCancelHandler(Function1<? super Throwable, Unit> handler) {
        return handler instanceof CancelHandler ? (CancelHandler) handler : new InvokeOnCancel(handler);
    }

    private final void multipleHandlersError(Function1<? super Throwable, Unit> handler, Object state) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + handler + ", already has " + state).toString());
    }

    private final void releaseClaimedReusableContinuation() {
        Continuation<T> continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        Throwable thTryReleaseClaimedContinuation = dispatchedContinuation != null ? dispatchedContinuation.tryReleaseClaimedContinuation(this) : null;
        if (thTryReleaseClaimedContinuation == null) {
            return;
        }
        detachChild$kotlinx_coroutines_core();
        cancel(thTryReleaseClaimedContinuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void resumeImpl(Object proposedUpdate, int resumeMode, Function1<? super Throwable, Unit> onCancellation) {
        boolean z;
        do {
            Object obj = this._state;
            if (!(obj instanceof NotCompleted)) {
                if (obj instanceof CancelledContinuation) {
                    CancelledContinuation cancelledContinuation = (CancelledContinuation) obj;
                    if (cancelledContinuation.makeResumed()) {
                        if (onCancellation == null) {
                            return;
                        }
                        callOnCancellation(onCancellation, cancelledContinuation.cause);
                        return;
                    }
                }
                alreadyResumedError(proposedUpdate);
                throw new KotlinNothingValueException();
            }
            Object objResumedState = resumedState((NotCompleted) obj, proposedUpdate, resumeMode, onCancellation, null);
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
            while (true) {
                if (atomicReferenceFieldUpdater.compareAndSet(this, obj, objResumedState)) {
                    z = true;
                    break;
                } else if (atomicReferenceFieldUpdater.get(this) != obj) {
                    z = false;
                    break;
                }
            }
        } while (!z);
        detachChildIfNonResuable();
        dispatchResume(resumeMode);
    }

    private final Object resumedState(NotCompleted state, Object proposedUpdate, int resumeMode, Function1<? super Throwable, Unit> onCancellation, Object idempotent) {
        if (proposedUpdate instanceof CompletedExceptionally) {
            return proposedUpdate;
        }
        if (!DispatchedTaskKt.isCancellableMode(resumeMode) && idempotent == null) {
            return proposedUpdate;
        }
        if (onCancellation != null || (((state instanceof CancelHandler) && !(state instanceof BeforeResumeCancelHandler)) || idempotent != null)) {
            return new CompletedContinuation(proposedUpdate, state instanceof CancelHandler ? (CancelHandler) state : null, onCancellation, idempotent, null, 16, null);
        }
        return proposedUpdate;
    }

    private final Symbol tryResumeImpl(Object proposedUpdate, Object idempotent, Function1<? super Throwable, Unit> onCancellation) {
        boolean z;
        do {
            Object obj = this._state;
            if (!(obj instanceof NotCompleted)) {
                if ((obj instanceof CompletedContinuation) && idempotent != null && ((CompletedContinuation) obj).idempotentResume == idempotent) {
                    return CancellableContinuationImplKt.RESUME_TOKEN;
                }
                return null;
            }
            Object objResumedState = resumedState((NotCompleted) obj, proposedUpdate, this.resumeMode, onCancellation, idempotent);
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
            while (true) {
                if (atomicReferenceFieldUpdater.compareAndSet(this, obj, objResumedState)) {
                    z = true;
                    break;
                }
                if (atomicReferenceFieldUpdater.get(this) != obj) {
                    z = false;
                    break;
                }
            }
        } while (!z);
        detachChildIfNonResuable();
        return CancellableContinuationImplKt.RESUME_TOKEN;
    }

    private final boolean trySuspend() {
        do {
            int i = this._decision;
            if (i != 0) {
                if (i == 2) {
                    return false;
                }
                throw new IllegalStateException("Already suspended".toString());
            }
        } while (!_decision$FU.compareAndSet(this, 0, 1));
        return true;
    }

    public final void callCancelHandler(@NotNull CancelHandler handler, @Nullable Throwable cause) {
        try {
            handler.invoke(cause);
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(get$context(), new CompletionHandlerException(Intrinsics.stringPlus("Exception in invokeOnCancellation handler for ", this), th));
        }
    }

    public final void callOnCancellation(@NotNull Function1<? super Throwable, Unit> onCancellation, @NotNull Throwable cause) {
        try {
            onCancellation.invoke(cause);
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(get$context(), new CompletionHandlerException(Intrinsics.stringPlus("Exception in resume onCancellation handler for ", this), th));
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean cancel(@Nullable Throwable cause) {
        Object obj;
        boolean z;
        boolean z2;
        do {
            obj = this._state;
            z = false;
            if (!(obj instanceof NotCompleted)) {
                return false;
            }
            z2 = obj instanceof CancelHandler;
            CancelledContinuation cancelledContinuation = new CancelledContinuation(this, cause, z2);
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
            do {
                if (atomicReferenceFieldUpdater.compareAndSet(this, obj, cancelledContinuation)) {
                    z = true;
                    break;
                }
            } while (atomicReferenceFieldUpdater.get(this) == obj);
        } while (!z);
        CancelHandler cancelHandler = z2 ? (CancelHandler) obj : null;
        if (cancelHandler != null) {
            callCancelHandler(cancelHandler, cause);
        }
        detachChildIfNonResuable();
        dispatchResume(this.resumeMode);
        return true;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public void cancelCompletedResult$kotlinx_coroutines_core(@Nullable Object takenState, @NotNull Throwable cause) {
        while (true) {
            Object obj = this._state;
            if (obj instanceof NotCompleted) {
                throw new IllegalStateException("Not completed".toString());
            }
            if (obj instanceof CompletedExceptionally) {
                return;
            }
            boolean z = false;
            if (obj instanceof CompletedContinuation) {
                CompletedContinuation completedContinuation = (CompletedContinuation) obj;
                if (!(!completedContinuation.getCancelled())) {
                    throw new IllegalStateException("Must be called at most once".toString());
                }
                CompletedContinuation completedContinuationCopy$default = CompletedContinuation.copy$default(completedContinuation, null, null, null, null, cause, 15, null);
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
                do {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, obj, completedContinuationCopy$default)) {
                        z = true;
                        break;
                    }
                } while (atomicReferenceFieldUpdater.get(this) == obj);
                if (z) {
                    completedContinuation.invokeHandlers(this, cause);
                    return;
                }
            } else {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = _state$FU;
                CompletedContinuation completedContinuation2 = new CompletedContinuation(obj, null, null, null, cause, 14, null);
                do {
                    if (atomicReferenceFieldUpdater2.compareAndSet(this, obj, completedContinuation2)) {
                        z = true;
                        break;
                    }
                } while (atomicReferenceFieldUpdater2.get(this) == obj);
                if (z) {
                    return;
                }
            }
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void completeResume(@NotNull Object token) {
        dispatchResume(this.resumeMode);
    }

    public final void detachChild$kotlinx_coroutines_core() {
        DisposableHandle disposableHandle = this.parentHandle;
        if (disposableHandle == null) {
            return;
        }
        disposableHandle.dispose();
        this.parentHandle = NonDisposableHandle.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public CoroutineStackFrame getCallerFrame() {
        Continuation<T> continuation = this.delegate;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Override // kotlin.coroutines.Continuation
    @NotNull
    /* JADX INFO: renamed from: getContext, reason: from getter */
    public CoroutineContext get$context() {
        return this.context;
    }

    @NotNull
    public Throwable getContinuationCancellationCause(@NotNull Job parent) {
        return parent.getCancellationException();
    }

    @Override // kotlinx.coroutines.DispatchedTask
    @NotNull
    public final Continuation<T> getDelegate$kotlinx_coroutines_core() {
        return this.delegate;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    @Nullable
    public Throwable getExceptionalResult$kotlinx_coroutines_core(@Nullable Object state) {
        Throwable exceptionalResult$kotlinx_coroutines_core = super.getExceptionalResult$kotlinx_coroutines_core(state);
        if (exceptionalResult$kotlinx_coroutines_core == null) {
            return null;
        }
        getDelegate$kotlinx_coroutines_core();
        return exceptionalResult$kotlinx_coroutines_core;
    }

    @PublishedApi
    @Nullable
    public final Object getResult() {
        Job job;
        boolean zIsReusable = isReusable();
        if (trySuspend()) {
            if (this.parentHandle == null) {
                installParentHandle();
            }
            if (zIsReusable) {
                releaseClaimedReusableContinuation();
            }
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (zIsReusable) {
            releaseClaimedReusableContinuation();
        }
        Object obj = get_state();
        if (obj instanceof CompletedExceptionally) {
            throw ((CompletedExceptionally) obj).cause;
        }
        if (!DispatchedTaskKt.isCancellableMode(this.resumeMode) || (job = (Job) get$context().get(Job.INSTANCE)) == null || job.isActive()) {
            return getSuccessfulResult$kotlinx_coroutines_core(obj);
        }
        CancellationException cancellationException = job.getCancellationException();
        cancelCompletedResult$kotlinx_coroutines_core(obj, cancellationException);
        throw cancellationException;
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public StackTraceElement getStackTraceElement() {
        return null;
    }

    @Nullable
    /* JADX INFO: renamed from: getState$kotlinx_coroutines_core, reason: from getter */
    public final Object get_state() {
        return this._state;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.DispatchedTask
    public <T> T getSuccessfulResult$kotlinx_coroutines_core(@Nullable Object state) {
        return state instanceof CompletedContinuation ? (T) ((CompletedContinuation) state).result : state;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void initCancellability() {
        DisposableHandle disposableHandleInstallParentHandle = installParentHandle();
        if (disposableHandleInstallParentHandle != null && isCompleted()) {
            disposableHandleInstallParentHandle.dispose();
            this.parentHandle = NonDisposableHandle.INSTANCE;
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void invokeOnCancellation(@NotNull Function1<? super Throwable, Unit> handler) {
        CancelHandler cancelHandlerMakeCancelHandler = makeCancelHandler(handler);
        while (true) {
            Object obj = this._state;
            boolean z = false;
            if (obj instanceof Active) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$FU;
                do {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, obj, cancelHandlerMakeCancelHandler)) {
                        z = true;
                        break;
                    }
                } while (atomicReferenceFieldUpdater.get(this) == obj);
                if (z) {
                    return;
                }
            } else if (obj instanceof CancelHandler) {
                multipleHandlersError(handler, obj);
            } else {
                boolean z2 = obj instanceof CompletedExceptionally;
                if (z2) {
                    CompletedExceptionally completedExceptionally = (CompletedExceptionally) obj;
                    if (!completedExceptionally.makeHandled()) {
                        multipleHandlersError(handler, obj);
                    }
                    if (obj instanceof CancelledContinuation) {
                        if (!z2) {
                            completedExceptionally = null;
                        }
                        callCancelHandler(handler, completedExceptionally != null ? completedExceptionally.cause : null);
                        return;
                    }
                    return;
                }
                if (obj instanceof CompletedContinuation) {
                    CompletedContinuation completedContinuation = (CompletedContinuation) obj;
                    if (completedContinuation.cancelHandler != null) {
                        multipleHandlersError(handler, obj);
                    }
                    if (cancelHandlerMakeCancelHandler instanceof BeforeResumeCancelHandler) {
                        return;
                    }
                    if (completedContinuation.getCancelled()) {
                        callCancelHandler(handler, completedContinuation.cancelCause);
                        return;
                    }
                    CompletedContinuation completedContinuationCopy$default = CompletedContinuation.copy$default(completedContinuation, null, cancelHandlerMakeCancelHandler, null, null, null, 29, null);
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = _state$FU;
                    do {
                        if (atomicReferenceFieldUpdater2.compareAndSet(this, obj, completedContinuationCopy$default)) {
                            z = true;
                            break;
                        }
                    } while (atomicReferenceFieldUpdater2.get(this) == obj);
                    if (z) {
                        return;
                    }
                } else {
                    if (cancelHandlerMakeCancelHandler instanceof BeforeResumeCancelHandler) {
                        return;
                    }
                    CompletedContinuation completedContinuation2 = new CompletedContinuation(obj, cancelHandlerMakeCancelHandler, null, null, null, 28, null);
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3 = _state$FU;
                    do {
                        if (atomicReferenceFieldUpdater3.compareAndSet(this, obj, completedContinuation2)) {
                            z = true;
                            break;
                        }
                    } while (atomicReferenceFieldUpdater3.get(this) == obj);
                    if (z) {
                        return;
                    }
                }
            }
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean isActive() {
        return get_state() instanceof NotCompleted;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean isCancelled() {
        return get_state() instanceof CancelledContinuation;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean isCompleted() {
        return !(get_state() instanceof NotCompleted);
    }

    @NotNull
    public String nameString() {
        return "CancellableContinuation";
    }

    public final void parentCancelled$kotlinx_coroutines_core(@NotNull Throwable cause) {
        if (cancelLater(cause)) {
            return;
        }
        cancel(cause);
        detachChildIfNonResuable();
    }

    @JvmName(name = "resetStateReusable")
    public final boolean resetStateReusable() {
        Object obj = this._state;
        if ((obj instanceof CompletedContinuation) && ((CompletedContinuation) obj).idempotentResume != null) {
            detachChild$kotlinx_coroutines_core();
            return false;
        }
        this._decision = 0;
        this._state = Active.INSTANCE;
        return true;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void resume(T value, @Nullable Function1<? super Throwable, Unit> onCancellation) {
        resumeImpl(value, this.resumeMode, onCancellation);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void resumeUndispatched(@NotNull CoroutineDispatcher coroutineDispatcher, T t) {
        Continuation<T> continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        resumeImpl(t, (dispatchedContinuation != null ? dispatchedContinuation.dispatcher : null) == coroutineDispatcher ? 4 : this.resumeMode, null);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void resumeUndispatchedWithException(@NotNull CoroutineDispatcher coroutineDispatcher, @NotNull Throwable th) {
        Continuation<T> continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        resumeImpl(new CompletedExceptionally(th, false, 2, null), (dispatchedContinuation != null ? dispatchedContinuation.dispatcher : null) == coroutineDispatcher ? 4 : this.resumeMode, null);
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(@NotNull Object result) {
        resumeImpl(CompletionStateKt.toState(result, this), this.resumeMode, null);
    }

    @Override // kotlinx.coroutines.DispatchedTask
    @Nullable
    public Object takeState$kotlinx_coroutines_core() {
        return get_state();
    }

    @NotNull
    public String toString() {
        return nameString() + '(' + DebugStringsKt.toDebugString(this.delegate) + "){" + getStateDebugRepresentation() + "}@" + DebugStringsKt.getHexAddress(this);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    @Nullable
    public Object tryResume(T value, @Nullable Object idempotent) {
        return tryResumeImpl(value, idempotent, null);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    @Nullable
    public Object tryResumeWithException(@NotNull Throwable exception) {
        return tryResumeImpl(new CompletedExceptionally(exception, false, 2, null), null, null);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    @Nullable
    public Object tryResume(T value, @Nullable Object idempotent, @Nullable Function1<? super Throwable, Unit> onCancellation) {
        return tryResumeImpl(value, idempotent, onCancellation);
    }

    private final boolean tryResume() {
        do {
            int i = this._decision;
            if (i != 0) {
                if (i == 1) {
                    return false;
                }
                throw new IllegalStateException("Already resumed".toString());
            }
        } while (!_decision$FU.compareAndSet(this, 0, 2));
        return true;
    }

    private final void callCancelHandler(Function1<? super Throwable, Unit> handler, Throwable cause) {
        try {
            handler.invoke(cause);
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(get$context(), new CompletionHandlerException(Intrinsics.stringPlus("Exception in invokeOnCancellation handler for ", this), th));
        }
    }
}
