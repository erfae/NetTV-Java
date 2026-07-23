package kotlin.jvm.internal;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: PrimitiveSpreadBuilders.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\b\u0006\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\u0014\u0010\u000eJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00028\u0000¢\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\b\u0010\tR\"\u0010\n\u001a\u00020\u00078\u0004@\u0004X\u0084\u000e¢\u0006\u0012\n\u0004\b\n\u0010\t\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000f8\u0002X\u0082\u0004¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u0012\u0004\b\u0012\u0010\u0013¨\u0006\u0015"}, d2 = {"Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", ExifInterface.GPS_DIRECTION_TRUE, "spreadArgument", "", "addSpread", "(Ljava/lang/Object;)V", "", "size", "I", "position", "getPosition", "()I", "setPosition", "(I)V", "", "spreads", "[Ljava/lang/Object;", "getSpreads$annotations", "()V", "<init>", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1})
public abstract class PrimitiveSpreadBuilder<T> {
    private int position;
    private final int size;

    @NotNull
    private final T[] spreads;

    public PrimitiveSpreadBuilder(int i) {
        this.size = i;
        this.spreads = (T[]) new Object[i];
    }

    private static /* synthetic */ void getSpreads$annotations() {
    }

    public final void addSpread(@NotNull T spreadArgument) {
        Intrinsics.checkNotNullParameter(spreadArgument, "spreadArgument");
        T[] tArr = this.spreads;
        int i = this.position;
        this.position = i + 1;
        tArr[i] = spreadArgument;
    }

    public final int getPosition() {
        return this.position;
    }

    public abstract int getSize(@NotNull T t);

    public final void setPosition(int i) {
        this.position = i;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    public final int size() {
        int size = 0;
        ?? it = new IntRange(0, this.size - 1).iterator();
        while (it.hasNext()) {
            T t = this.spreads[it.nextInt()];
            size += t != null ? getSize(t) : 1;
        }
        return size;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    @NotNull
    public final T toArray(@NotNull T values, @NotNull T result) {
        Intrinsics.checkNotNullParameter(values, "values");
        Intrinsics.checkNotNullParameter(result, "result");
        ?? it = new IntRange(0, this.size - 1).iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            int iNextInt = it.nextInt();
            T t = this.spreads[iNextInt];
            if (t != null) {
                if (i < iNextInt) {
                    int i3 = iNextInt - i;
                    System.arraycopy(values, i, result, i2, i3);
                    i2 += i3;
                }
                int size = getSize(t);
                System.arraycopy(t, 0, result, i2, size);
                i2 += size;
                i = iNextInt + 1;
            }
        }
        int i4 = this.size;
        if (i < i4) {
            System.arraycopy(values, i, result, i2, i4 - i);
        }
        return result;
    }
}
