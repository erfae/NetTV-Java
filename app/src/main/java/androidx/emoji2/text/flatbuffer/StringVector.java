package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class StringVector extends BaseVector {
    private Utf8 utf8 = Utf8.getDefault();

    public StringVector __assign(int i, int i2, ByteBuffer byteBuffer) {
        __reset(i, i2, byteBuffer);
        return this;
    }

    public String get(int i) {
        int i__element = __element(i);
        ByteBuffer byteBuffer = this.bb;
        Utf8 utf8 = this.utf8;
        int i2 = byteBuffer.getInt(i__element) + i__element;
        return utf8.decodeUtf8(byteBuffer, i2 + 4, byteBuffer.getInt(i2));
    }
}
