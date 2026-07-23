package androidx.emoji2.text.flatbuffer;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class FlexBuffersBuilder {
    public static final int BUILDER_FLAG_NONE = 0;
    public static final int BUILDER_FLAG_SHARE_ALL = 7;
    public static final int BUILDER_FLAG_SHARE_KEYS = 1;
    public static final int BUILDER_FLAG_SHARE_KEYS_AND_STRINGS = 3;
    public static final int BUILDER_FLAG_SHARE_KEY_VECTORS = 4;
    public static final int BUILDER_FLAG_SHARE_STRINGS = 2;
    private static final int WIDTH_16 = 1;
    private static final int WIDTH_32 = 2;
    private static final int WIDTH_64 = 3;
    private static final int WIDTH_8 = 0;
    private final ReadWriteBuf bb;
    private boolean finished;
    private final int flags;
    private Comparator<Value> keyComparator;
    private final HashMap<String, Integer> keyPool;
    private final ArrayList<Value> stack;
    private final HashMap<String, Integer> stringPool;

    public static class Value {
        public final double dValue;
        public long iValue;
        public int key;
        public final int minBitWidth;
        public final int type;

        public Value(int i, int i2, int i3, long j) {
            this.key = i;
            this.type = i2;
            this.minBitWidth = i3;
            this.iValue = j;
            this.dValue = Double.MIN_VALUE;
        }

        public static /* synthetic */ int access$500(long j, int i, int i2) {
            return elemWidth(4, 0, j, i, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int elemWidth(int i, int i2) {
            return elemWidth(this.type, this.minBitWidth, this.iValue, i, i2);
        }

        private static byte packedType(int i, int i2) {
            return (byte) (i | (i2 << 2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int paddingBytes(int i, int i2) {
            return ((~i) + 1) & (i2 - 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public byte storedPackedType() {
            return storedPackedType(0);
        }

        private int storedWidth(int i) {
            int i2 = this.type;
            int i3 = FlexBuffers.FBT_NULL;
            return i2 <= 3 || i2 == 26 ? Math.max(this.minBitWidth, i) : this.minBitWidth;
        }

        private static int elemWidth(int i, int i2, long j, int i3, int i4) {
            int i5 = FlexBuffers.FBT_NULL;
            if (i <= 3 || i == 26) {
                return i2;
            }
            for (int i6 = 1; i6 <= 32; i6 *= 2) {
                int iWidthUInBits = FlexBuffersBuilder.widthUInBits((int) (((long) ((i4 * i6) + (paddingBytes(i3, i6) + i3))) - j));
                if ((1 << iWidthUInBits) == i6) {
                    return iWidthUInBits;
                }
            }
            return 3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public byte storedPackedType(int i) {
            return packedType(storedWidth(i), this.type);
        }

        public Value(int i, int i2, double d) {
            this.key = i;
            this.type = 3;
            this.minBitWidth = i2;
            this.dValue = d;
            this.iValue = Long.MIN_VALUE;
        }
    }

    public FlexBuffersBuilder(int i) {
        this(new ArrayReadWriteBuf(i), 1);
    }

    private int align(int i) {
        int i2 = 1 << i;
        int iPaddingBytes = Value.paddingBytes(this.bb.writePosition(), i2);
        while (true) {
            int i3 = iPaddingBytes - 1;
            if (iPaddingBytes == 0) {
                return i2;
            }
            this.bb.put((byte) 0);
            iPaddingBytes = i3;
        }
    }

    private Value createKeyVector(int i, int i2) {
        long j = i2;
        int iMax = Math.max(0, widthUInBits(j));
        int i3 = i;
        while (i3 < this.stack.size()) {
            i3++;
            iMax = Math.max(iMax, Value.access$500(this.stack.get(i3).key, this.bb.writePosition(), i3));
        }
        int iAlign = align(iMax);
        writeInt(j, iAlign);
        int iWritePosition = this.bb.writePosition();
        while (i < this.stack.size()) {
            int i4 = this.stack.get(i).key;
            writeOffset(this.stack.get(i).key, iAlign);
            i++;
        }
        return new Value(-1, FlexBuffers.toTypedVector(4, 0), iMax, iWritePosition);
    }

    private Value createVector(int i, int i2, int i3, boolean z, boolean z2, Value value) {
        int i4;
        int typedVector;
        int i5 = i3;
        long j = i5;
        int iMax = Math.max(0, widthUInBits(j));
        if (value != null) {
            iMax = Math.max(iMax, value.elemWidth(this.bb.writePosition(), 0));
            i4 = 3;
        } else {
            i4 = 1;
        }
        int i6 = 4;
        int iMax2 = iMax;
        for (int i7 = i2; i7 < this.stack.size(); i7++) {
            iMax2 = Math.max(iMax2, this.stack.get(i7).elemWidth(this.bb.writePosition(), i7 + i4));
            if (z && i7 == i2) {
                i6 = this.stack.get(i7).type;
                if (!FlexBuffers.isTypedVectorElementType(i6)) {
                    throw new FlexBuffers.FlexBufferException("TypedVector does not support this element type");
                }
            }
        }
        int i8 = i2;
        int iAlign = align(iMax2);
        if (value != null) {
            writeOffset(value.iValue, iAlign);
            writeInt(1 << value.minBitWidth, iAlign);
        }
        if (!z2) {
            writeInt(j, iAlign);
        }
        int iWritePosition = this.bb.writePosition();
        for (int i9 = i8; i9 < this.stack.size(); i9++) {
            writeAny(this.stack.get(i9), iAlign);
        }
        if (!z) {
            while (i8 < this.stack.size()) {
                this.bb.put(this.stack.get(i8).storedPackedType(iMax2));
                i8++;
            }
        }
        if (value != null) {
            typedVector = 9;
        } else if (z) {
            if (!z2) {
                i5 = 0;
            }
            typedVector = FlexBuffers.toTypedVector(i6, i5);
        } else {
            typedVector = 10;
        }
        return new Value(i, typedVector, iMax2, iWritePosition);
    }

    private int putKey(String str) {
        if (str == null) {
            return -1;
        }
        int iWritePosition = this.bb.writePosition();
        if ((this.flags & 1) == 0) {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            this.bb.put(bytes, 0, bytes.length);
            this.bb.put((byte) 0);
            this.keyPool.put(str, Integer.valueOf(iWritePosition));
            return iWritePosition;
        }
        Integer num = this.keyPool.get(str);
        if (num != null) {
            return num.intValue();
        }
        byte[] bytes2 = str.getBytes(StandardCharsets.UTF_8);
        this.bb.put(bytes2, 0, bytes2.length);
        this.bb.put((byte) 0);
        this.keyPool.put(str, Integer.valueOf(iWritePosition));
        return iWritePosition;
    }

    public static int widthUInBits(long j) {
        if (j <= 255) {
            return 0;
        }
        if (j <= 65535) {
            return 1;
        }
        return j <= (((long) (-1)) & 4294967295L) ? 2 : 3;
    }

    private void writeAny(Value value, int i) {
        int i2 = value.type;
        if (i2 != 0 && i2 != 1 && i2 != 2) {
            if (i2 == 3) {
                writeDouble(value.dValue, i);
                return;
            } else if (i2 != 26) {
                writeOffset(value.iValue, i);
                return;
            }
        }
        writeInt(value.iValue, i);
    }

    private Value writeBlob(int i, byte[] bArr, int i2, boolean z) {
        int iWidthUInBits = widthUInBits(bArr.length);
        writeInt(bArr.length, align(iWidthUInBits));
        int iWritePosition = this.bb.writePosition();
        this.bb.put(bArr, 0, bArr.length);
        if (z) {
            this.bb.put((byte) 0);
        }
        return new Value(i, i2, iWidthUInBits, iWritePosition);
    }

    private void writeDouble(double d, int i) {
        if (i == 4) {
            this.bb.putFloat((float) d);
        } else if (i == 8) {
            this.bb.putDouble(d);
        }
    }

    private void writeInt(long j, int i) {
        if (i == 1) {
            this.bb.put((byte) j);
            return;
        }
        if (i == 2) {
            this.bb.putShort((short) j);
        } else if (i == 4) {
            this.bb.putInt((int) j);
        } else {
            if (i != 8) {
                return;
            }
            this.bb.putLong(j);
        }
    }

    private void writeOffset(long j, int i) {
        writeInt((int) (((long) this.bb.writePosition()) - j), i);
    }

    private Value writeString(int i, String str) {
        return writeBlob(i, str.getBytes(StandardCharsets.UTF_8), 5, true);
    }

    public int endMap(String str, int i) {
        int iPutKey = putKey(str);
        ArrayList<Value> arrayList = this.stack;
        Collections.sort(arrayList.subList(i, arrayList.size()), this.keyComparator);
        Value valueCreateVector = createVector(iPutKey, i, this.stack.size() - i, false, false, createKeyVector(i, this.stack.size() - i));
        while (this.stack.size() > i) {
            ArrayList<Value> arrayList2 = this.stack;
            arrayList2.remove(arrayList2.size() - 1);
        }
        this.stack.add(valueCreateVector);
        return (int) valueCreateVector.iValue;
    }

    public int endVector(String str, int i, boolean z, boolean z2) {
        Value valueCreateVector = createVector(putKey(str), i, this.stack.size() - i, z, z2, null);
        while (this.stack.size() > i) {
            ArrayList<Value> arrayList = this.stack;
            arrayList.remove(arrayList.size() - 1);
        }
        this.stack.add(valueCreateVector);
        return (int) valueCreateVector.iValue;
    }

    public ByteBuffer finish() {
        int iAlign = align(this.stack.get(0).elemWidth(this.bb.writePosition(), 0));
        writeAny(this.stack.get(0), iAlign);
        this.bb.put(this.stack.get(0).storedPackedType());
        this.bb.put((byte) iAlign);
        this.finished = true;
        return ByteBuffer.wrap(this.bb.data(), 0, this.bb.writePosition());
    }

    public ReadWriteBuf getBuffer() {
        return this.bb;
    }

    public int putBlob(byte[] bArr) {
        return putBlob(null, bArr);
    }

    public void putBoolean(boolean z) {
        putBoolean(null, z);
    }

    public void putFloat(float f) {
        putFloat((String) null, f);
    }

    public void putInt(int i) {
        putInt((String) null, i);
    }

    public int putString(String str) {
        return putString(null, str);
    }

    public void putUInt(int i) {
        putUInt(null, i);
    }

    public void putUInt64(BigInteger bigInteger) {
        putUInt64(null, bigInteger.longValue());
    }

    public int startMap() {
        return this.stack.size();
    }

    public int startVector() {
        return this.stack.size();
    }

    public FlexBuffersBuilder() {
        this(256);
    }

    private void putUInt64(String str, long j) {
        this.stack.add(new Value(putKey(str), 2, 3, j));
    }

    public int putBlob(String str, byte[] bArr) {
        Value valueWriteBlob = writeBlob(putKey(str), bArr, 25, false);
        this.stack.add(valueWriteBlob);
        return (int) valueWriteBlob.iValue;
    }

    public void putBoolean(String str, boolean z) {
        this.stack.add(new Value(putKey(str), 26, 0, z ? 1L : 0L));
    }

    public void putFloat(String str, float f) {
        this.stack.add(new Value(putKey(str), 2, f));
    }

    public void putInt(String str, int i) {
        putInt(str, i);
    }

    public int putString(String str, String str2) {
        long j;
        int iPutKey = putKey(str);
        if ((this.flags & 2) != 0) {
            Integer num = this.stringPool.get(str2);
            if (num != null) {
                this.stack.add(new Value(iPutKey, 5, widthUInBits(str2.length()), num.intValue()));
                return num.intValue();
            }
            Value valueWriteString = writeString(iPutKey, str2);
            this.stringPool.put(str2, Integer.valueOf((int) valueWriteString.iValue));
            this.stack.add(valueWriteString);
            j = valueWriteString.iValue;
        } else {
            Value valueWriteString2 = writeString(iPutKey, str2);
            this.stack.add(valueWriteString2);
            j = valueWriteString2.iValue;
        }
        return (int) j;
    }

    public void putUInt(long j) {
        putUInt(null, j);
    }

    @Deprecated
    public FlexBuffersBuilder(ByteBuffer byteBuffer, int i) {
        this(new ArrayReadWriteBuf(byteBuffer.array()), i);
    }

    private void putUInt(String str, long j) {
        Value value;
        int iPutKey = putKey(str);
        int iWidthUInBits = widthUInBits(j);
        if (iWidthUInBits == 0) {
            value = new Value(iPutKey, 2, 0, (int) j);
        } else if (iWidthUInBits == 1) {
            value = new Value(iPutKey, 2, 1, (int) j);
        } else if (iWidthUInBits == 2) {
            value = new Value(iPutKey, 2, 2, (int) j);
        } else {
            value = new Value(iPutKey, 2, 3, j);
        }
        this.stack.add(value);
    }

    public void putInt(String str, long j) {
        int iPutKey = putKey(str);
        if (-128 <= j && j <= 127) {
            this.stack.add(new Value(iPutKey, 1, 0, (int) j));
            return;
        }
        if (-32768 <= j && j <= 32767) {
            this.stack.add(new Value(iPutKey, 1, 1, (int) j));
        } else if (-2147483648L <= j && j <= 2147483647L) {
            this.stack.add(new Value(iPutKey, 1, 2, (int) j));
        } else {
            this.stack.add(new Value(iPutKey, 1, 3, j));
        }
    }

    public FlexBuffersBuilder(ReadWriteBuf readWriteBuf, int i) {
        this.stack = new ArrayList<>();
        this.keyPool = new HashMap<>();
        this.stringPool = new HashMap<>();
        this.finished = false;
        this.keyComparator = new Comparator<Value>() { // from class: androidx.emoji2.text.flatbuffer.FlexBuffersBuilder.1
            @Override // java.util.Comparator
            public int compare(Value value, Value value2) {
                byte b;
                byte b2;
                int i2 = value.key;
                int i3 = value2.key;
                do {
                    b = FlexBuffersBuilder.this.bb.get(i2);
                    b2 = FlexBuffersBuilder.this.bb.get(i3);
                    if (b == 0) {
                        return b - b2;
                    }
                    i2++;
                    i3++;
                } while (b == b2);
                return b - b2;
            }
        };
        this.bb = readWriteBuf;
        this.flags = i;
    }

    public void putFloat(double d) {
        putFloat((String) null, d);
    }

    public void putFloat(String str, double d) {
        this.stack.add(new Value(putKey(str), 3, d));
    }

    public FlexBuffersBuilder(ByteBuffer byteBuffer) {
        this(byteBuffer, 1);
    }

    public void putInt(long j) {
        putInt((String) null, j);
    }
}
