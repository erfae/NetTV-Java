package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: classes.dex */
public final class MetadataList extends Table {

    public static final class Vector extends BaseVector {
        public Vector __assign(int i, int i2, ByteBuffer byteBuffer) {
            __reset(i, i2, byteBuffer);
            return this;
        }

        public MetadataList get(int i) {
            return get(new MetadataList(), i);
        }

        public MetadataList get(MetadataList metadataList, int i) {
            int i__element = __element(i);
            return metadataList.__assign(this.bb.getInt(i__element) + i__element, this.bb);
        }
    }

    public static void ValidateVersion() {
        Constants.FLATBUFFERS_1_12_0();
    }

    public static void addList(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addSourceSha(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static void addVersion(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(0, i, 0);
    }

    public static int createListVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createMetadataList(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3) {
        flatBufferBuilder.startTable(3);
        addSourceSha(flatBufferBuilder, i3);
        addList(flatBufferBuilder, i2);
        addVersion(flatBufferBuilder, i);
        return endMetadataList(flatBufferBuilder);
    }

    public static int endMetadataList(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endTable();
    }

    public static void finishMetadataListBuffer(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.finish(i);
    }

    public static void finishSizePrefixedMetadataListBuffer(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.finishSizePrefixed(i);
    }

    public static MetadataList getRootAsMetadataList(ByteBuffer byteBuffer) {
        return getRootAsMetadataList(byteBuffer, new MetadataList());
    }

    public static void startListVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startMetadataList(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startTable(3);
    }

    public MetadataList __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public void __init(int i, ByteBuffer byteBuffer) {
        __reset(i, byteBuffer);
    }

    public MetadataItem list(int i) {
        return list(new MetadataItem(), i);
    }

    public int listLength() {
        int i__offset = __offset(6);
        if (i__offset != 0) {
            return __vector_len(i__offset);
        }
        return 0;
    }

    public MetadataItem.Vector listVector() {
        return listVector(new MetadataItem.Vector());
    }

    public String sourceSha() {
        int i__offset = __offset(8);
        if (i__offset == 0) {
            return null;
        }
        int i = i__offset + this.bb_pos;
        ByteBuffer byteBuffer = this.bb;
        Utf8 utf8 = this.utf8;
        int i2 = byteBuffer.getInt(i) + i;
        return utf8.decodeUtf8(byteBuffer, i2 + 4, byteBuffer.getInt(i2));
    }

    public ByteBuffer sourceShaAsByteBuffer() {
        return __vector_as_bytebuffer(8, 1);
    }

    public ByteBuffer sourceShaInByteBuffer(ByteBuffer byteBuffer) {
        return __vector_in_bytebuffer(byteBuffer, 8, 1);
    }

    public int version() {
        int i__offset = __offset(4);
        if (i__offset != 0) {
            return this.bb.getInt(i__offset + this.bb_pos);
        }
        return 0;
    }

    public static MetadataList getRootAsMetadataList(ByteBuffer byteBuffer, MetadataList metadataList) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return metadataList.__assign(byteBuffer.position() + byteBuffer.getInt(byteBuffer.position()), byteBuffer);
    }

    public MetadataItem list(MetadataItem metadataItem, int i) {
        int i__offset = __offset(6);
        if (i__offset == 0) {
            return null;
        }
        int i__vector = (i * 4) + __vector(i__offset);
        return metadataItem.__assign(this.bb.getInt(i__vector) + i__vector, this.bb);
    }

    public MetadataItem.Vector listVector(MetadataItem.Vector vector) {
        int i__offset = __offset(6);
        if (i__offset != 0) {
            return vector.__assign(__vector(i__offset), 4, this.bb);
        }
        return null;
    }
}
