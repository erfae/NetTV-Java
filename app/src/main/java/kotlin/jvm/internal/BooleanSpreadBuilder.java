package kotlin.jvm.internal;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: PrimitiveSpreadBuilders.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0018\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\f\u0010\rJ\u000e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003J\u0006\u0010\u0007\u001a\u00020\u0002R\u0014\u0010\b\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lkotlin/jvm/internal/BooleanSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "", "value", "", "add", "toArray", "values", "[Z", "", "size", "<init>", "(I)V", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1})
public final class BooleanSpreadBuilder extends PrimitiveSpreadBuilder<boolean[]> {

    @NotNull
    private final boolean[] values;

    public BooleanSpreadBuilder(int i) {
        super(i);
        this.values = new boolean[i];
    }

    public final void add(boolean value) {
        boolean[] zArr = this.values;
        int position = getPosition();
        setPosition(position + 1);
        zArr[position] = value;
    }

    @Override // kotlin.jvm.internal.PrimitiveSpreadBuilder
    public int getSize(boolean[] zArr) {
        boolean[] zArr2 = zArr;
        Intrinsics.checkNotNullParameter(zArr2, "<this>");
        return zArr2.length;
    }

    @NotNull
    public final boolean[] toArray() {
        boolean[] zArr = this.values;
        boolean[] zArr2 = new boolean[size()];
        toArray(zArr, zArr2);
        return zArr2;
    }
}
