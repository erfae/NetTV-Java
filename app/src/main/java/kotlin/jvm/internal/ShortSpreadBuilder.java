package kotlin.jvm.internal;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: PrimitiveSpreadBuilders.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0017\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\f\u0010\rJ\u000e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003J\u0006\u0010\u0007\u001a\u00020\u0002R\u0014\u0010\b\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lkotlin/jvm/internal/ShortSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "", "value", "", "add", "toArray", "values", "[S", "", "size", "<init>", "(I)V", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1})
public final class ShortSpreadBuilder extends PrimitiveSpreadBuilder<short[]> {

    @NotNull
    private final short[] values;

    public ShortSpreadBuilder(int i) {
        super(i);
        this.values = new short[i];
    }

    public final void add(short value) {
        short[] sArr = this.values;
        int position = getPosition();
        setPosition(position + 1);
        sArr[position] = value;
    }

    @Override // kotlin.jvm.internal.PrimitiveSpreadBuilder
    public int getSize(short[] sArr) {
        short[] sArr2 = sArr;
        Intrinsics.checkNotNullParameter(sArr2, "<this>");
        return sArr2.length;
    }

    @NotNull
    public final short[] toArray() {
        short[] sArr = this.values;
        short[] sArr2 = new short[size()];
        toArray(sArr, sArr2);
        return sArr2;
    }
}
