package kotlin.coroutines.jvm.internal;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ContinuationImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@SinceKotlin(version = "1.3")
@Metadata(bv = {}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b!\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u00020\u00032\u00020\u0004B\u0019\u0012\u0010\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001¢\u0006\u0004\b\u0017\u0010\u0018J \u0010\b\u001a\u00020\u00072\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0005ø\u0001\u0000¢\u0006\u0004\b\b\u0010\tJ\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00012\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0016J$\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00012\b\u0010\f\u001a\u0004\u0018\u00010\u00022\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\n\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0016R!\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00018\u0006¢\u0006\f\n\u0004\b\n\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, d2 = {"Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Ljava/io/Serializable;", "Lkotlin/Result;", "result", "", "resumeWith", "(Ljava/lang/Object;)V", "completion", "create", "value", "", "toString", "Ljava/lang/StackTraceElement;", "getStackTraceElement", "Lkotlin/coroutines/Continuation;", "getCompletion", "()Lkotlin/coroutines/Continuation;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "<init>", "(Lkotlin/coroutines/Continuation;)V", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1})
public abstract class BaseContinuationImpl implements Continuation<Object>, CoroutineStackFrame, Serializable {

    @Nullable
    private final Continuation<Object> completion;

    public BaseContinuationImpl(@Nullable Continuation<Object> continuation) {
        this.completion = continuation;
    }

    @NotNull
    public Continuation<Unit> create(@NotNull Continuation<?> completion) {
        Intrinsics.checkNotNullParameter(completion, "completion");
        throw new UnsupportedOperationException("create(Continuation) has not been overridden");
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public CoroutineStackFrame getCallerFrame() {
        Continuation<Object> continuation = this.completion;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Nullable
    public final Continuation<Object> getCompletion() {
        return this.completion;
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public StackTraceElement getStackTraceElement() {
        return DebugMetadataKt.getStackTraceElement(this);
    }

    @Nullable
    public abstract Object invokeSuspend(@NotNull Object obj);

    public void releaseIntercepted() {
    }

    @Override // kotlin.coroutines.Continuation
    public final void resumeWith(@NotNull Object result) {
        Continuation<Object> continuation = this;
        while (true) {
            DebugProbesKt.probeCoroutineResumed(continuation);
            BaseContinuationImpl baseContinuationImpl = (BaseContinuationImpl) continuation;
            Continuation<Object> continuation2 = baseContinuationImpl.completion;
            Intrinsics.checkNotNull(continuation2);
            try {
                Object objInvokeSuspend = baseContinuationImpl.invokeSuspend(result);
                if (objInvokeSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    return;
                }
                Result.Companion companion = Result.INSTANCE;
                result = Result.m336constructorimpl(objInvokeSuspend);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                result = Result.m336constructorimpl(ResultKt.createFailure(th));
            }
            baseContinuationImpl.releaseIntercepted();
            if (!(continuation2 instanceof BaseContinuationImpl)) {
                continuation2.resumeWith(result);
                return;
            }
            continuation = continuation2;
        }
    }

    @NotNull
    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Continuation at ");
        Object stackTraceElement = getStackTraceElement();
        if (stackTraceElement == null) {
            stackTraceElement = getClass().getName();
        }
        sbM.append(stackTraceElement);
        return sbM.toString();
    }

    @NotNull
    public Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> completion) {
        Intrinsics.checkNotNullParameter(completion, "completion");
        throw new UnsupportedOperationException("create(Any?;Continuation) has not been overridden");
    }
}
