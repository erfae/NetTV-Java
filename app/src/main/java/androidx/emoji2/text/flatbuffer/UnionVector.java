package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class UnionVector extends BaseVector {
    public UnionVector __assign(int i, int i2, ByteBuffer byteBuffer) {
        __reset(i, i2, byteBuffer);
        return this;
    }

    public Table get(Table table, int i) {
        int i__element = __element(i);
        ByteBuffer byteBuffer = this.bb;
        table.__reset(byteBuffer.getInt(i__element) + i__element, byteBuffer);
        return table;
    }
}
