package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class BaseVector {
    public ByteBuffer bb;
    private int element_size;
    private int length;
    private int vector;

    public final int __element(int i) {
        return (i * this.element_size) + this.vector;
    }

    public final void __reset(int i, int i2, ByteBuffer byteBuffer) {
        this.bb = byteBuffer;
        if (byteBuffer != null) {
            this.vector = i;
            this.length = byteBuffer.getInt(i - 4);
            this.element_size = i2;
        } else {
            this.vector = 0;
            this.length = 0;
            this.element_size = 0;
        }
    }

    public int length() {
        return this.length;
    }

    public void reset() {
        __reset(0, 0, null);
    }
}
