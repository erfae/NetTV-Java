package kotlinx.coroutines.flow.internal;

import androidx.exifinterface.media.ExifInterface;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: AbstractSharedFlow.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b \u0018\u0000*\f\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00012\u00060\u0003j\u0002`\u0004B\u0007¢\u0006\u0004\b\u001a\u0010\fR>\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u00052\u0010\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u00058\u0004@BX\u0084\u000e¢\u0006\u0012\n\u0004\b\u0007\u0010\b\u0012\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR$\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\r8\u0004@BX\u0084\u000e¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0012\u001a\u00020\r8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0012\u0010\u000fR\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u00138\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\r0\u00168F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u001b"}, d2 = {"Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", ExifInterface.LATITUDE_SOUTH, "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "", "<set-?>", "slots", "[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "getSlots", "()[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "getSlots$annotations", "()V", "", "nCollectors", "I", "getNCollectors", "()I", "nextIndex", "Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;", "_subscriptionCount", "Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;", "Lkotlinx/coroutines/flow/StateFlow;", "getSubscriptionCount", "()Lkotlinx/coroutines/flow/StateFlow;", "subscriptionCount", "<init>", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
public abstract class AbstractSharedFlow<S extends AbstractSharedFlowSlot<?>> {

    @Nullable
    private SubscriptionCountStateFlow _subscriptionCount;
    private int nCollectors;
    private int nextIndex;

    @Nullable
    private S[] slots;

    @NotNull
    public final S allocateSlot() {
        S s;
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        synchronized (this) {
            S[] sArr = this.slots;
            if (sArr == null) {
                sArr = (S[]) createSlotArray(2);
                this.slots = sArr;
            } else if (this.nCollectors >= sArr.length) {
                Object[] objArrCopyOf = Arrays.copyOf(sArr, sArr.length * 2);
                Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(this, newSize)");
                this.slots = (S[]) ((AbstractSharedFlowSlot[]) objArrCopyOf);
                sArr = (S[]) ((AbstractSharedFlowSlot[]) objArrCopyOf);
            }
            int i = this.nextIndex;
            do {
                s = sArr[i];
                if (s == null) {
                    s = (S) createSlot();
                    sArr[i] = s;
                }
                i++;
                if (i >= sArr.length) {
                    i = 0;
                }
            } while (!s.allocateLocked(this));
            this.nextIndex = i;
            this.nCollectors++;
            subscriptionCountStateFlow = this._subscriptionCount;
        }
        if (subscriptionCountStateFlow != null) {
            subscriptionCountStateFlow.increment(1);
        }
        return s;
    }

    @NotNull
    public abstract S createSlot();

    @NotNull
    public abstract S[] createSlotArray(int i);

    public final void freeSlot(@NotNull S s) {
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        int i;
        Continuation<Unit>[] continuationArrFreeLocked;
        synchronized (this) {
            int i2 = this.nCollectors - 1;
            this.nCollectors = i2;
            subscriptionCountStateFlow = this._subscriptionCount;
            i = 0;
            if (i2 == 0) {
                this.nextIndex = 0;
            }
            continuationArrFreeLocked = s.freeLocked(this);
        }
        int length = continuationArrFreeLocked.length;
        while (i < length) {
            Continuation<Unit> continuation = continuationArrFreeLocked[i];
            i++;
            if (continuation != null) {
                Result.Companion companion = Result.INSTANCE;
                continuation.resumeWith(Result.m336constructorimpl(Unit.INSTANCE));
            }
        }
        if (subscriptionCountStateFlow == null) {
            return;
        }
        subscriptionCountStateFlow.increment(-1);
    }

    public final int getNCollectors() {
        return this.nCollectors;
    }

    @Nullable
    public final S[] getSlots() {
        return this.slots;
    }

    @NotNull
    public final StateFlow<Integer> getSubscriptionCount() {
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        synchronized (this) {
            subscriptionCountStateFlow = this._subscriptionCount;
            if (subscriptionCountStateFlow == null) {
                subscriptionCountStateFlow = new SubscriptionCountStateFlow(this.nCollectors);
                this._subscriptionCount = subscriptionCountStateFlow;
            }
        }
        return subscriptionCountStateFlow;
    }
}
