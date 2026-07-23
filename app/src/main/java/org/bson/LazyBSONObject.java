package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.UuidCodec;
import org.bson.io.ByteBufferBsonInput;
import org.bson.types.BSONTimestamp;
import org.bson.types.Binary;
import org.bson.types.Code;
import org.bson.types.CodeWScope;
import org.bson.types.MaxKey;
import org.bson.types.MinKey;
import org.bson.types.Symbol;

/* JADX INFO: loaded from: classes2.dex */
public class LazyBSONObject implements BSONObject {
    private final byte[] bytes;
    private final LazyBSONCallback callback;
    private final int offset;

    /* JADX INFO: renamed from: org.bson.LazyBSONObject$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$org$bson$BsonType;

        static {
            int[] iArr = new int[BsonType.values().length];
            $SwitchMap$org$bson$BsonType = iArr;
            try {
                iArr[BsonType.DOCUMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.STRING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.BINARY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.NULL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.UNDEFINED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.OBJECT_ID.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.BOOLEAN.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DATE_TIME.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.REGULAR_EXPRESSION.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DB_POINTER.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.JAVASCRIPT.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.SYMBOL.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.JAVASCRIPT_WITH_SCOPE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.INT32.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.TIMESTAMP.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.INT64.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DECIMAL128.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.MIN_KEY.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.MAX_KEY.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
        }
    }

    public LazyBSONObject(byte[] bArr, LazyBSONCallback lazyBSONCallback) {
        this(bArr, 0, lazyBSONCallback);
    }

    private ByteBuffer getBufferForInternalBytes() {
        byte[] bArr = this.bytes;
        int i = this.offset;
        ByteBuffer byteBufferSlice = ByteBuffer.wrap(bArr, i, bArr.length - i).slice();
        byteBufferSlice.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferSlice.limit(byteBufferSlice.getInt());
        byteBufferSlice.rewind();
        return byteBufferSlice;
    }

    private Object readArray(BsonBinaryReader bsonBinaryReader) {
        int position = bsonBinaryReader.getBsonInput().getPosition();
        bsonBinaryReader.skipValue();
        return this.callback.createArray(this.bytes, this.offset + position);
    }

    private Object readDocument(BsonBinaryReader bsonBinaryReader) {
        int position = bsonBinaryReader.getBsonInput().getPosition();
        bsonBinaryReader.skipValue();
        return this.callback.createObject(this.bytes, this.offset + position);
    }

    private Object readJavaScriptWithScopeDocument(BsonBinaryReader bsonBinaryReader) {
        int position = bsonBinaryReader.getBsonInput().getPosition();
        bsonBinaryReader.readStartDocument();
        while (bsonBinaryReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            bsonBinaryReader.skipName();
            bsonBinaryReader.skipValue();
        }
        bsonBinaryReader.readEndDocument();
        return this.callback.createObject(this.bytes, this.offset + position);
    }

    @Override // org.bson.BSONObject
    public boolean containsField(String str) {
        BsonBinaryReader bsonReader = getBsonReader();
        try {
            bsonReader.readStartDocument();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                if (bsonReader.readName().equals(str)) {
                    bsonReader.close();
                    return true;
                }
                bsonReader.skipValue();
            }
            bsonReader.close();
            return false;
        } catch (Throwable th) {
            bsonReader.close();
            throw th;
        }
    }

    @Override // org.bson.BSONObject
    @Deprecated
    public boolean containsKey(String str) {
        return containsField(str);
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        final ArrayList arrayList = new ArrayList();
        BsonBinaryReader bsonReader = getBsonReader();
        try {
            bsonReader.readStartDocument();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                arrayList.add(new AbstractMap.SimpleImmutableEntry(bsonReader.readName(), readValue(bsonReader)));
            }
            bsonReader.readEndDocument();
            return new Set<Map.Entry<String, Object>>() { // from class: org.bson.LazyBSONObject.1
                @Override // java.util.Set, java.util.Collection
                public boolean addAll(Collection<? extends Map.Entry<String, Object>> collection) {
                    throw new UnsupportedOperationException();
                }

                @Override // java.util.Set, java.util.Collection
                public void clear() {
                    throw new UnsupportedOperationException();
                }

                @Override // java.util.Set, java.util.Collection
                public boolean contains(Object obj) {
                    return arrayList.contains(obj);
                }

                @Override // java.util.Set, java.util.Collection
                public boolean containsAll(Collection<?> collection) {
                    return arrayList.containsAll(collection);
                }

                @Override // java.util.Set, java.util.Collection
                public boolean isEmpty() {
                    return arrayList.isEmpty();
                }

                @Override // java.util.Set, java.util.Collection, java.lang.Iterable
                public Iterator<Map.Entry<String, Object>> iterator() {
                    return arrayList.iterator();
                }

                @Override // java.util.Set, java.util.Collection
                public boolean remove(Object obj) {
                    throw new UnsupportedOperationException();
                }

                @Override // java.util.Set, java.util.Collection
                public boolean removeAll(Collection<?> collection) {
                    throw new UnsupportedOperationException();
                }

                @Override // java.util.Set, java.util.Collection
                public boolean retainAll(Collection<?> collection) {
                    throw new UnsupportedOperationException();
                }

                @Override // java.util.Set, java.util.Collection
                public int size() {
                    return arrayList.size();
                }

                @Override // java.util.Set, java.util.Collection
                public Object[] toArray() {
                    return arrayList.toArray();
                }

                @Override // java.util.Set, java.util.Collection
                public boolean add(Map.Entry<String, Object> entry) {
                    throw new UnsupportedOperationException();
                }

                @Override // java.util.Set, java.util.Collection
                public <T> T[] toArray(T[] tArr) {
                    return (T[]) arrayList.toArray(tArr);
                }
            };
        } finally {
            bsonReader.close();
        }
    }

    public boolean equals(Object obj) {
        byte b;
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            LazyBSONObject lazyBSONObject = (LazyBSONObject) obj;
            byte[] bArr = this.bytes;
            byte[] bArr2 = lazyBSONObject.bytes;
            if (bArr == bArr2 && this.offset == lazyBSONObject.offset) {
                return true;
            }
            if (bArr == null || bArr2 == null || bArr.length == 0 || bArr2.length == 0 || bArr2[lazyBSONObject.offset] != (b = bArr[this.offset])) {
                return false;
            }
            for (int i = 0; i < b; i++) {
                if (this.bytes[this.offset + i] != lazyBSONObject.bytes[lazyBSONObject.offset + i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // org.bson.BSONObject
    public Object get(String str) {
        BsonBinaryReader bsonReader = getBsonReader();
        try {
            bsonReader.readStartDocument();
            Object value = null;
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                if (str.equals(bsonReader.readName())) {
                    value = readValue(bsonReader);
                    break;
                }
                bsonReader.skipValue();
            }
            return value;
        } finally {
            bsonReader.close();
        }
    }

    public int getBSONSize() {
        return getBufferForInternalBytes().getInt();
    }

    public final BsonBinaryReader getBsonReader() {
        return new BsonBinaryReader(new ByteBufferBsonInput(new ByteBufNIO(getBufferForInternalBytes())));
    }

    public int hashCode() {
        int bSONSize = getBSONSize();
        int i = 1;
        for (int i2 = this.offset; i2 < this.offset + bSONSize; i2++) {
            i = (i * 31) + this.bytes[i2];
        }
        return i;
    }

    public boolean isEmpty() {
        return keySet().size() == 0;
    }

    @Override // org.bson.BSONObject
    public Set<String> keySet() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        BsonBinaryReader bsonReader = getBsonReader();
        try {
            bsonReader.readStartDocument();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                linkedHashSet.add(bsonReader.readName());
                bsonReader.skipValue();
            }
            bsonReader.readEndDocument();
            return Collections.unmodifiableSet(linkedHashSet);
        } finally {
            bsonReader.close();
        }
    }

    public int pipe(OutputStream outputStream) throws IOException {
        return Channels.newChannel(outputStream).write(getBufferForInternalBytes());
    }

    @Override // org.bson.BSONObject
    public Object put(String str, Object obj) {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override // org.bson.BSONObject
    public void putAll(BSONObject bSONObject) {
        throw new UnsupportedOperationException("Object is read only");
    }

    public final Object readValue(BsonBinaryReader bsonBinaryReader) {
        switch (AnonymousClass2.$SwitchMap$org$bson$BsonType[bsonBinaryReader.getCurrentBsonType().ordinal()]) {
            case 1:
                return readDocument(bsonBinaryReader);
            case 2:
                return readArray(bsonBinaryReader);
            case 3:
                return Double.valueOf(bsonBinaryReader.readDouble());
            case 4:
                return bsonBinaryReader.readString();
            case 5:
                byte bPeekBinarySubType = bsonBinaryReader.peekBinarySubType();
                if (BsonBinarySubType.isUuid(bPeekBinarySubType) && bsonBinaryReader.peekBinarySize() == 16) {
                    return new UuidCodec(UuidRepresentation.JAVA_LEGACY).decode((BsonReader) bsonBinaryReader, DecoderContext.builder().build());
                }
                BsonBinary binaryData = bsonBinaryReader.readBinaryData();
                return (bPeekBinarySubType == BsonBinarySubType.BINARY.getValue() || bPeekBinarySubType == BsonBinarySubType.OLD_BINARY.getValue()) ? binaryData.getData() : new Binary(binaryData.getType(), binaryData.getData());
            case 6:
                bsonBinaryReader.readNull();
                return null;
            case 7:
                bsonBinaryReader.readUndefined();
                return null;
            case 8:
                return bsonBinaryReader.readObjectId();
            case 9:
                return Boolean.valueOf(bsonBinaryReader.readBoolean());
            case 10:
                return new Date(bsonBinaryReader.readDateTime());
            case 11:
                BsonRegularExpression regularExpression = bsonBinaryReader.readRegularExpression();
                return Pattern.compile(regularExpression.getPattern(), BSON.regexFlags(regularExpression.getOptions()));
            case 12:
                BsonDbPointer dBPointer = bsonBinaryReader.readDBPointer();
                return this.callback.createDBRef(dBPointer.getNamespace(), dBPointer.getId());
            case 13:
                return new Code(bsonBinaryReader.readJavaScript());
            case 14:
                return new Symbol(bsonBinaryReader.readSymbol());
            case 15:
                return new CodeWScope(bsonBinaryReader.readJavaScriptWithScope(), (BSONObject) readJavaScriptWithScopeDocument(bsonBinaryReader));
            case 16:
                return Integer.valueOf(bsonBinaryReader.readInt32());
            case 17:
                BsonTimestamp timestamp = bsonBinaryReader.readTimestamp();
                return new BSONTimestamp(timestamp.getTime(), timestamp.getInc());
            case 18:
                return Long.valueOf(bsonBinaryReader.readInt64());
            case 19:
                return bsonBinaryReader.readDecimal128();
            case 20:
                bsonBinaryReader.readMinKey();
                return new MinKey();
            case 21:
                bsonBinaryReader.readMaxKey();
                return new MaxKey();
            default:
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("unhandled BSON type: ");
                sbM.append(bsonBinaryReader.getCurrentBsonType());
                throw new IllegalArgumentException(sbM.toString());
        }
    }

    @Override // org.bson.BSONObject
    public Object removeField(String str) {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override // org.bson.BSONObject
    public Map toMap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, Object> entry : entrySet()) {
            linkedHashMap.put(entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    public LazyBSONObject(byte[] bArr, int i, LazyBSONCallback lazyBSONCallback) {
        this.bytes = bArr;
        this.callback = lazyBSONCallback;
        this.offset = i;
    }

    @Override // org.bson.BSONObject, java.util.AbstractMap, java.util.Map
    public void putAll(Map map) {
        throw new UnsupportedOperationException("Object is read only");
    }
}
