package kotlin.jvm.internal;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: PrimitiveSpreadBuilders.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\n\u001a\u00020\u0003¢\u0006\u0004\b\u000b\u0010\fJ\u000e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003J\u0006\u0010\u0007\u001a\u00020\u0002R\u0014\u0010\b\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lkotlin/jvm/internal/IntSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "", "value", "", "add", "toArray", "values", "[I", "size", "<init>", "(I)V", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1})
public final class IntSpreadBuilder extends PrimitiveSpreadBuilder<int[]> {

    @NotNull
    private final int[] values;

    public IntSpreadBuilder(int i) {
        super(i);
        this.values = new int[i];
    }

    public final void add(int value) {
        int[] iArr = this.values;
        int position = getPosition();
        setPosition(position + 1);
        iArr[position] = value;
    }

    @Override // kotlin.jvm.internal.PrimitiveSpreadBuilder
    public int getSize(int[] iArr) {
        int[] iArr2 = iArr;
        Intrinsics.checkNotNullParameter(iArr2, "<this>");
        return iArr2.length;
    }

    @NotNull
    public final int[] toArray() {
        int[] iArr = this.values;
        int[] iArr2 = new int[size()];
        toArray(iArr, iArr2);
        return iArr2;
    }
}
