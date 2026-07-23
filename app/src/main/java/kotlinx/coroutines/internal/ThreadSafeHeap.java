package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import java.lang.Comparable;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.InternalCoroutinesApi;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ThreadSafeHeap.kt */
/* JADX INFO: loaded from: classes2.dex */
@InternalCoroutinesApi
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0018\n\u0002\u0010\u0000\n\u0002\u0018\u0002\b\u0017\u0018\u0000*\u0012\b\u0000\u0010\u0003*\u00020\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00022\u000602j\u0002`3B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00028\u0000H\u0001¢\u0006\u0004\b\b\u0010\tJ\u0015\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0004\b\n\u0010\tJ.\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00028\u00002\u0014\u0010\r\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\f0\u000bH\u0086\b¢\u0006\u0004\b\u000e\u0010\u000fJ\r\u0010\u0010\u001a\u00020\u0007¢\u0006\u0004\b\u0010\u0010\u0005J\u0011\u0010\u0011\u001a\u0004\u0018\u00018\u0000H\u0001¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\u0013\u0010\u0012J\u0017\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0014H\u0002¢\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u0019H\u0001¢\u0006\u0004\b\u001b\u0010\u001cJ&\u0010\u001e\u001a\u0004\u0018\u00018\u00002\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000bH\u0086\b¢\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010 \u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b \u0010\u0012J\u0018\u0010\"\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0019H\u0082\u0010¢\u0006\u0004\b\"\u0010#J\u0018\u0010$\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0019H\u0082\u0010¢\u0006\u0004\b$\u0010#J\u001f\u0010&\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\u0019H\u0002¢\u0006\u0004\b&\u0010'R \u0010(\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u00148\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b(\u0010)R\u0011\u0010*\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b*\u0010+R$\u00100\u001a\u00020\u00192\u0006\u0010,\u001a\u00020\u00198F@BX\u0086\u000e¢\u0006\f\u001a\u0004\b-\u0010.\"\u0004\b/\u0010#¨\u00061"}, d2 = {"Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "", ExifInterface.GPS_DIRECTION_TRUE, "<init>", "()V", "node", "", "addImpl", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)V", "addLast", "Lkotlin/Function1;", "", "cond", "addLastIf", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;Lkotlin/jvm/functions/Function1;)Z", "clear", "firstImpl", "()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "peek", "", "realloc", "()[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "remove", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)Z", "", "index", "removeAtImpl", "(I)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "predicate", "removeFirstIf", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "removeFirstOrNull", "i", "siftDownFrom", "(I)V", "siftUpFrom", "j", "swap", "(II)V", "a", "[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "isEmpty", "()Z", "value", "getSize", "()I", "setSize", "size", "kotlinx-coroutines-core", "", "Lkotlinx/coroutines/internal/SynchronizedObject;"}, k = 1, mv = {1, 6, 0}, xi = 48)
public class ThreadSafeHeap<T extends ThreadSafeHeapNode & Comparable<? super T>> {

    @NotNull
    private volatile /* synthetic */ int _size = 0;

    @Nullable
    private T[] a;

    private final T[] realloc() {
        T[] tArr = this.a;
        if (tArr == null) {
            T[] tArr2 = (T[]) new ThreadSafeHeapNode[4];
            this.a = tArr2;
            return tArr2;
        }
        if (get_size() < tArr.length) {
            return tArr;
        }
        Object[] objArrCopyOf = Arrays.copyOf(tArr, get_size() * 2);
        Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(this, newSize)");
        T[] tArr3 = (T[]) ((ThreadSafeHeapNode[]) objArrCopyOf);
        this.a = tArr3;
        return tArr3;
    }

    private final void setSize(int i) {
        this._size = i;
    }

    private final void siftDownFrom(int i) {
        while (true) {
            int i2 = (i * 2) + 1;
            if (i2 >= get_size()) {
                return;
            }
            T[] tArr = this.a;
            Intrinsics.checkNotNull(tArr);
            int i3 = i2 + 1;
            if (i3 < get_size()) {
                T t = tArr[i3];
                Intrinsics.checkNotNull(t);
                T t2 = tArr[i2];
                Intrinsics.checkNotNull(t2);
                if (((Comparable) t).compareTo(t2) < 0) {
                    i2 = i3;
                }
            }
            T t3 = tArr[i];
            Intrinsics.checkNotNull(t3);
            T t4 = tArr[i2];
            Intrinsics.checkNotNull(t4);
            if (((Comparable) t3).compareTo(t4) <= 0) {
                return;
            }
            swap(i, i2);
            i = i2;
        }
    }

    private final void siftUpFrom(int i) {
        while (i > 0) {
            T[] tArr = this.a;
            Intrinsics.checkNotNull(tArr);
            int i2 = (i - 1) / 2;
            T t = tArr[i2];
            Intrinsics.checkNotNull(t);
            T t2 = tArr[i];
            Intrinsics.checkNotNull(t2);
            if (((Comparable) t).compareTo(t2) <= 0) {
                return;
            }
            swap(i, i2);
            i = i2;
        }
    }

    private final void swap(int i, int j) {
        T[] tArr = this.a;
        Intrinsics.checkNotNull(tArr);
        T t = tArr[j];
        Intrinsics.checkNotNull(t);
        T t2 = tArr[i];
        Intrinsics.checkNotNull(t2);
        tArr[i] = t;
        tArr[j] = t2;
        t.setIndex(i);
        t2.setIndex(j);
    }

    @PublishedApi
    public final void addImpl(@NotNull T node) {
        node.setHeap(this);
        ThreadSafeHeapNode[] threadSafeHeapNodeArrRealloc = realloc();
        int i = get_size();
        setSize(i + 1);
        threadSafeHeapNodeArrRealloc[i] = node;
        node.setIndex(i);
        siftUpFrom(i);
    }

    public final void addLast(@NotNull T node) {
        synchronized (this) {
            addImpl(node);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final boolean addLastIf(@NotNull T node, @NotNull Function1<? super T, Boolean> cond) {
        boolean z;
        synchronized (this) {
            try {
                if (cond.invoke(firstImpl()).booleanValue()) {
                    addImpl(node);
                    z = true;
                } else {
                    z = false;
                }
                InlineMarker.finallyStart(1);
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
        InlineMarker.finallyEnd(1);
        return z;
    }

    public final void clear() {
        synchronized (this) {
            T[] tArr = this.a;
            if (tArr != null) {
                ArraysKt___ArraysJvmKt.fill$default(tArr, (Object) null, 0, 0, 6, (Object) null);
            }
            this._size = 0;
            Unit unit = Unit.INSTANCE;
        }
    }

    @PublishedApi
    @Nullable
    public final T firstImpl() {
        T[] tArr = this.a;
        if (tArr == null) {
            return null;
        }
        return tArr[0];
    }

    /* JADX INFO: renamed from: getSize, reason: from getter */
    public final int get_size() {
        return this._size;
    }

    public final boolean isEmpty() {
        return get_size() == 0;
    }

    @Nullable
    public final T peek() {
        T t;
        synchronized (this) {
            t = (T) firstImpl();
        }
        return t;
    }

    public final boolean remove(@NotNull T node) {
        boolean z;
        synchronized (this) {
            if (node.getHeap() == null) {
                z = false;
            } else {
                removeAtImpl(node.getIndex());
                z = true;
            }
        }
        return z;
    }

    /* JADX WARN: Code duplicated, block: B:9:0x003a  */
    @PublishedApi
    @NotNull
    public final T removeAtImpl(int index) {
        T[] tArr = this.a;
        Intrinsics.checkNotNull(tArr);
        setSize(get_size() - 1);
        if (index < get_size()) {
            swap(index, get_size());
            int i = (index - 1) / 2;
            if (index > 0) {
                T t = tArr[index];
                Intrinsics.checkNotNull(t);
                T t2 = tArr[i];
                Intrinsics.checkNotNull(t2);
                if (((Comparable) t).compareTo(t2) < 0) {
                    swap(index, i);
                    siftUpFrom(i);
                } else {
                    siftDownFrom(index);
                }
            } else {
                siftDownFrom(index);
            }
        }
        T t3 = tArr[get_size()];
        Intrinsics.checkNotNull(t3);
        t3.setHeap(null);
        t3.setIndex(-1);
        tArr[get_size()] = null;
        return t3;
    }

    @Nullable
    public final T removeFirstIf(@NotNull Function1<? super T, Boolean> predicate) {
        synchronized (this) {
            int i = 1;
            try {
                ThreadSafeHeapNode threadSafeHeapNodeFirstImpl = firstImpl();
                T t = null;
                if (threadSafeHeapNodeFirstImpl == null) {
                    InlineMarker.finallyStart(2);
                    return null;
                }
                if (predicate.invoke(threadSafeHeapNodeFirstImpl).booleanValue()) {
                    t = (T) removeAtImpl(0);
                }
                InlineMarker.finallyStart(i);
                return t;
            } finally {
                InlineMarker.finallyStart(i);
                InlineMarker.finallyEnd(i);
            }
        }
    }

    @Nullable
    public final T removeFirstOrNull() {
        T t;
        synchronized (this) {
            t = get_size() > 0 ? (T) removeAtImpl(0) : null;
        }
        return t;
    }
}
