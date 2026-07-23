package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: LockFreeLinkedList.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0011\u0010\u000bJ-\u0010\u0007\u001a\u00020\u0005\"\u000e\b\u0000\u0010\u0003\u0018\u0001*\u00060\u0001j\u0002`\u00022\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00050\u0004H\u0086\bJ\u0006\u0010\t\u001a\u00020\bJ\u000f\u0010\f\u001a\u00020\u0005H\u0000¢\u0006\u0004\b\n\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u0012"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Function1;", "", "block", "forEach", "", "remove", "validate$kotlinx_coroutines_core", "()V", "validate", "", "isEmpty", "()Z", "isRemoved", "<init>", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0})
public class LockFreeLinkedListHead extends LockFreeLinkedListNode {
    public final /* synthetic */ <T extends LockFreeLinkedListNode> void forEach(Function1<? super T, Unit> block) {
        for (LockFreeLinkedListNode nextNode = (LockFreeLinkedListNode) getNext(); !Intrinsics.areEqual(nextNode, this); nextNode = nextNode.getNextNode()) {
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (nextNode instanceof LockFreeLinkedListNode) {
                block.invoke(nextNode);
            }
        }
    }

    public final boolean isEmpty() {
        return getNext() == this;
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    public boolean isRemoved() {
        return false;
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    /* JADX INFO: renamed from: remove, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo1867remove() {
        return ((Boolean) remove()).booleanValue();
    }

    public final void validate$kotlinx_coroutines_core() {
        LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) getNext();
        LockFreeLinkedListNode lockFreeLinkedListNode2 = this;
        while (!Intrinsics.areEqual(lockFreeLinkedListNode, this)) {
            LockFreeLinkedListNode nextNode = lockFreeLinkedListNode.getNextNode();
            lockFreeLinkedListNode.validateNode$kotlinx_coroutines_core(lockFreeLinkedListNode2, nextNode);
            lockFreeLinkedListNode2 = lockFreeLinkedListNode;
            lockFreeLinkedListNode = nextNode;
        }
        validateNode$kotlinx_coroutines_core(lockFreeLinkedListNode2, (LockFreeLinkedListNode) getNext());
    }

    @NotNull
    public final Void remove() {
        throw new IllegalStateException("head cannot be removed".toString());
    }
}
