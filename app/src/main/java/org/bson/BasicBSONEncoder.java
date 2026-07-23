package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import org.bson.io.BasicOutputBuffer;
import org.bson.io.OutputBuffer;
import org.bson.types.BSONTimestamp;
import org.bson.types.Binary;
import org.bson.types.Code;
import org.bson.types.CodeWScope;
import org.bson.types.Decimal128;
import org.bson.types.MaxKey;
import org.bson.types.MinKey;
import org.bson.types.ObjectId;
import org.bson.types.Symbol;

/* JADX INFO: loaded from: classes2.dex */
public class BasicBSONEncoder implements BSONEncoder {
    private BsonBinaryWriter bsonWriter;
    private OutputBuffer outputBuffer;

    private boolean isTopLevelDocument() {
        return this.bsonWriter.getContext().getParentContext() == null;
    }

    private static void writeLongToArrayLittleEndian(byte[] bArr, int i, long j) {
        bArr[i] = (byte) (j & 255);
        bArr[i + 1] = (byte) ((j >> 8) & 255);
        bArr[i + 2] = (byte) ((j >> 16) & 255);
        bArr[i + 3] = (byte) ((j >> 24) & 255);
        bArr[i + 4] = (byte) ((j >> 32) & 255);
        bArr[i + 5] = (byte) ((j >> 40) & 255);
        bArr[i + 6] = (byte) ((j >> 48) & 255);
        bArr[i + 7] = (byte) ((j >> 56) & 255);
    }

    public final void _putObjectField(String str, Object obj) {
        if ("_transientFields".equals(str)) {
            return;
        }
        if (str.contains("\u0000")) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Document field names can't have a NULL character. (Bad Key: '", str, "')"));
        }
        if ("$where".equals(str) && (obj instanceof String)) {
            Code code = new Code((String) obj);
            putName(str);
            this.bsonWriter.writeJavaScript(code.getCode());
        }
        Object objApplyEncodingHooks = BSON.applyEncodingHooks(obj);
        if (objApplyEncodingHooks == null) {
            putName(str);
            this.bsonWriter.writeNull();
            return;
        }
        if (objApplyEncodingHooks instanceof Date) {
            putName(str);
            this.bsonWriter.writeDateTime(((Date) objApplyEncodingHooks).getTime());
            return;
        }
        if (objApplyEncodingHooks instanceof Decimal128) {
            putName(str);
            this.bsonWriter.writeDecimal128((Decimal128) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Number) {
            Number number = (Number) objApplyEncodingHooks;
            putName(str);
            if ((number instanceof Integer) || (number instanceof Short) || (number instanceof Byte) || (number instanceof AtomicInteger)) {
                this.bsonWriter.writeInt32(number.intValue());
                return;
            }
            if ((number instanceof Long) || (number instanceof AtomicLong)) {
                this.bsonWriter.writeInt64(number.longValue());
                return;
            } else if ((number instanceof Float) || (number instanceof Double)) {
                this.bsonWriter.writeDouble(number.doubleValue());
                return;
            } else {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Can't serialize ");
                sbM.append(number.getClass());
                throw new IllegalArgumentException(sbM.toString());
            }
        }
        if (objApplyEncodingHooks instanceof Character) {
            String string = objApplyEncodingHooks.toString();
            putName(str);
            this.bsonWriter.writeString(string);
            return;
        }
        if (objApplyEncodingHooks instanceof String) {
            String string2 = objApplyEncodingHooks.toString();
            putName(str);
            this.bsonWriter.writeString(string2);
            return;
        }
        if (objApplyEncodingHooks instanceof ObjectId) {
            putName(str);
            this.bsonWriter.writeObjectId((ObjectId) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Boolean) {
            putName(str);
            this.bsonWriter.writeBoolean(((Boolean) objApplyEncodingHooks).booleanValue());
            return;
        }
        if (objApplyEncodingHooks instanceof Pattern) {
            Pattern pattern = (Pattern) objApplyEncodingHooks;
            putName(str);
            this.bsonWriter.writeRegularExpression(new BsonRegularExpression(pattern.pattern(), BSON.regexFlags(pattern.flags())));
            return;
        }
        int i = 0;
        if (objApplyEncodingHooks instanceof Iterable) {
            putName(str);
            this.bsonWriter.writeStartArray();
            Iterator it = ((Iterable) objApplyEncodingHooks).iterator();
            while (it.hasNext()) {
                _putObjectField(String.valueOf(0), it.next());
            }
            this.bsonWriter.writeEndArray();
            return;
        }
        if (objApplyEncodingHooks instanceof BSONObject) {
            putName(str);
            putObject((BSONObject) objApplyEncodingHooks);
            return;
        }
        if (objApplyEncodingHooks instanceof Map) {
            putName(str);
            this.bsonWriter.writeStartDocument();
            for (Map.Entry entry : ((Map) objApplyEncodingHooks).entrySet()) {
                _putObjectField((String) entry.getKey(), entry.getValue());
            }
            this.bsonWriter.writeEndDocument();
            return;
        }
        boolean z = objApplyEncodingHooks instanceof byte[];
        if (z) {
            putName(str);
            this.bsonWriter.writeBinaryData(new BsonBinary((byte[]) objApplyEncodingHooks));
            return;
        }
        if (objApplyEncodingHooks instanceof Binary) {
            Binary binary = (Binary) objApplyEncodingHooks;
            putName(str);
            this.bsonWriter.writeBinaryData(new BsonBinary(binary.getType(), binary.getData()));
            return;
        }
        if (objApplyEncodingHooks instanceof UUID) {
            UUID uuid = (UUID) objApplyEncodingHooks;
            putName(str);
            byte[] bArr = new byte[16];
            writeLongToArrayLittleEndian(bArr, 0, uuid.getMostSignificantBits());
            writeLongToArrayLittleEndian(bArr, 8, uuid.getLeastSignificantBits());
            this.bsonWriter.writeBinaryData(new BsonBinary(BsonBinarySubType.UUID_LEGACY, bArr));
            return;
        }
        if (!objApplyEncodingHooks.getClass().isArray()) {
            if (objApplyEncodingHooks instanceof Symbol) {
                putName(str);
                this.bsonWriter.writeSymbol(((Symbol) objApplyEncodingHooks).getSymbol());
                return;
            }
            if (objApplyEncodingHooks instanceof BSONTimestamp) {
                BSONTimestamp bSONTimestamp = (BSONTimestamp) objApplyEncodingHooks;
                putName(str);
                this.bsonWriter.writeTimestamp(new BsonTimestamp(bSONTimestamp.getTime(), bSONTimestamp.getInc()));
                return;
            }
            if (objApplyEncodingHooks instanceof CodeWScope) {
                CodeWScope codeWScope = (CodeWScope) objApplyEncodingHooks;
                putName(str);
                this.bsonWriter.writeJavaScriptWithScope(codeWScope.getCode());
                putObject(codeWScope.getScope());
                return;
            }
            if (objApplyEncodingHooks instanceof Code) {
                putName(str);
                this.bsonWriter.writeJavaScript(((Code) objApplyEncodingHooks).getCode());
                return;
            } else if (objApplyEncodingHooks instanceof MinKey) {
                putName(str);
                this.bsonWriter.writeMinKey();
                return;
            } else if (objApplyEncodingHooks instanceof MaxKey) {
                putName(str);
                this.bsonWriter.writeMaxKey();
                return;
            } else {
                StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Can't serialize ");
                sbM2.append(objApplyEncodingHooks.getClass());
                throw new IllegalArgumentException(sbM2.toString());
            }
        }
        putName(str);
        this.bsonWriter.writeStartArray();
        if (objApplyEncodingHooks instanceof int[]) {
            int[] iArr = (int[]) objApplyEncodingHooks;
            int length = iArr.length;
            while (i < length) {
                this.bsonWriter.writeInt32(iArr[i]);
                i++;
            }
        } else if (objApplyEncodingHooks instanceof long[]) {
            long[] jArr = (long[]) objApplyEncodingHooks;
            int length2 = jArr.length;
            while (i < length2) {
                this.bsonWriter.writeInt64(jArr[i]);
                i++;
            }
        } else if (objApplyEncodingHooks instanceof float[]) {
            float[] fArr = (float[]) objApplyEncodingHooks;
            int length3 = fArr.length;
            while (i < length3) {
                this.bsonWriter.writeDouble(fArr[i]);
                i++;
            }
        } else if (objApplyEncodingHooks instanceof short[]) {
            short[] sArr = (short[]) objApplyEncodingHooks;
            int length4 = sArr.length;
            while (i < length4) {
                this.bsonWriter.writeInt32(sArr[i]);
                i++;
            }
        } else if (z) {
            byte[] bArr2 = (byte[]) objApplyEncodingHooks;
            int length5 = bArr2.length;
            while (i < length5) {
                this.bsonWriter.writeInt32(bArr2[i]);
                i++;
            }
        } else if (objApplyEncodingHooks instanceof double[]) {
            double[] dArr = (double[]) objApplyEncodingHooks;
            int length6 = dArr.length;
            while (i < length6) {
                this.bsonWriter.writeDouble(dArr[i]);
                i++;
            }
        } else if (objApplyEncodingHooks instanceof boolean[]) {
            boolean[] zArr = (boolean[]) objApplyEncodingHooks;
            int length7 = zArr.length;
            while (i < length7) {
                this.bsonWriter.writeBoolean(zArr[i]);
                i++;
            }
        } else if (objApplyEncodingHooks instanceof String[]) {
            String[] strArr = (String[]) objApplyEncodingHooks;
            int length8 = strArr.length;
            while (i < length8) {
                this.bsonWriter.writeString(strArr[i]);
                i++;
            }
        } else {
            int length9 = Array.getLength(objApplyEncodingHooks);
            while (i < length9) {
                _putObjectField(String.valueOf(i), Array.get(objApplyEncodingHooks, i));
                i++;
            }
        }
        this.bsonWriter.writeEndArray();
    }

    @Override // org.bson.BSONEncoder
    public void done() {
        this.bsonWriter.close();
        this.bsonWriter = null;
    }

    @Override // org.bson.BSONEncoder
    public byte[] encode(BSONObject bSONObject) {
        BasicOutputBuffer basicOutputBuffer = new BasicOutputBuffer();
        set(basicOutputBuffer);
        putObject(bSONObject);
        done();
        return basicOutputBuffer.toByteArray();
    }

    public final void putName(String str) {
        if (this.bsonWriter.getState() == AbstractBsonWriter.State.NAME) {
            this.bsonWriter.writeName(str);
        }
    }

    @Override // org.bson.BSONEncoder
    public int putObject(BSONObject bSONObject) {
        int position = this.outputBuffer.getPosition();
        this.bsonWriter.writeStartDocument();
        if (isTopLevelDocument() && bSONObject.containsField("_id")) {
            _putObjectField("_id", bSONObject.get("_id"));
        }
        for (String str : bSONObject.keySet()) {
            if (!isTopLevelDocument() || !str.equals("_id")) {
                _putObjectField(str, bSONObject.get(str));
            }
        }
        this.bsonWriter.writeEndDocument();
        return this.outputBuffer.getPosition() - position;
    }

    @Override // org.bson.BSONEncoder
    public void set(OutputBuffer outputBuffer) {
        if (this.bsonWriter != null) {
            throw new IllegalStateException("Performing another operation at this moment");
        }
        this.outputBuffer = outputBuffer;
        this.bsonWriter = new BsonBinaryWriter(outputBuffer);
    }
}
