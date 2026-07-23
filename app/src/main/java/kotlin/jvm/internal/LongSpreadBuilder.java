package kotlin.jvm.internal;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: PrimitiveSpreadBuilders.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0016\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\f\u0010\rJ\u000e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003J\u0006\u0010\u0007\u001a\u00020\u0002R\u0014\u0010\b\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lkotlin/jvm/internal/LongSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "", "value", "", "add", "toArray", "values", "[J", "", "size", "<init>", "(I)V", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1})
public final class LongSpreadBuilder extends PrimitiveSpreadBuilder<long[]> {

    @NotNull
    private final long[] values;

    public LongSpreadBuilder(int i) {
        super(i);
        this.values = new long[i];
    }

    public final void add(long value) {
        long[] jArr = this.values;
        int position = getPosition();
        setPosition(position + 1);
        jArr[position] = value;
    }

    @Override // kotlin.jvm.internal.PrimitiveSpreadBuilder
    public int getSize(long[] jArr) {
        long[] jArr2 = jArr;
        Intrinsics.checkNotNullParameter(jArr2, "<this>");
        return jArr2.length;
    }

    @NotNull
    public final long[] toArray() {
        long[] jArr = this.values;
        long[] jArr2 = new long[size()];
        toArray(jArr, jArr2);
        return jArr2;
    }
}
