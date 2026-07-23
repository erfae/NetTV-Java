package org.bson.json;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.net.HttpHeaders;
import java.io.Reader;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.bson.AbstractBsonReader;
import org.bson.BSONException;
import org.bson.BsonBinary;
import org.bson.BsonBinarySubType;
import org.bson.BsonContextType;
import org.bson.BsonDbPointer;
import org.bson.BsonInvalidOperationException;
import org.bson.BsonReaderMark;
import org.bson.BsonRegularExpression;
import org.bson.BsonTimestamp;
import org.bson.BsonType;
import org.bson.BsonUndefined;
import org.bson.internal.Base64;
import org.bson.types.Decimal128;
import org.bson.types.MaxKey;
import org.bson.types.MinKey;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public class JsonReader extends AbstractBsonReader {
    private Object currentValue;
    private Mark mark;
    private JsonToken pushedToken;
    private final JsonScanner scanner;

    /* JADX INFO: renamed from: org.bson.json.JsonReader$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$org$bson$BsonContextType;
        public static final /* synthetic */ int[] $SwitchMap$org$bson$BsonType;
        public static final /* synthetic */ int[] $SwitchMap$org$bson$json$JsonTokenType;

        static {
            int[] iArr = new int[BsonType.values().length];
            $SwitchMap$org$bson$BsonType = iArr;
            try {
                iArr[BsonType.ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.BINARY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DATE_TIME.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DOCUMENT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DOUBLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.INT32.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.INT64.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DECIMAL128.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.JAVASCRIPT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.JAVASCRIPT_WITH_SCOPE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.MAX_KEY.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.MIN_KEY.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.NULL.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.OBJECT_ID.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.REGULAR_EXPRESSION.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.STRING.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.SYMBOL.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.TIMESTAMP.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.UNDEFINED.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            int[] iArr2 = new int[BsonContextType.values().length];
            $SwitchMap$org$bson$BsonContextType = iArr2;
            try {
                iArr2[BsonContextType.DOCUMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.SCOPE_DOCUMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.JAVASCRIPT_WITH_SCOPE.ordinal()] = 4;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.TOP_LEVEL.ordinal()] = 5;
            } catch (NoSuchFieldError unused25) {
            }
            int[] iArr3 = new int[JsonTokenType.values().length];
            $SwitchMap$org$bson$json$JsonTokenType = iArr3;
            try {
                iArr3[JsonTokenType.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.UNQUOTED_STRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.END_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.BEGIN_ARRAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.BEGIN_OBJECT.ordinal()] = 5;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.DOUBLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.END_OF_FILE.ordinal()] = 7;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.INT32.ordinal()] = 8;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.INT64.ordinal()] = 9;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.REGULAR_EXPRESSION.ordinal()] = 10;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.COMMA.ordinal()] = 11;
            } catch (NoSuchFieldError unused36) {
            }
        }
    }

    public class Context extends AbstractBsonReader.Context {
        public Context(JsonReader jsonReader, AbstractBsonReader.Context context, BsonContextType bsonContextType) {
            super(context, bsonContextType);
        }

        @Override // org.bson.AbstractBsonReader.Context
        public final BsonContextType getContextType() {
            return super.getContextType();
        }

        @Override // org.bson.AbstractBsonReader.Context
        public final Context getParentContext() {
            return (Context) getParentContext();
        }
    }

    public class Mark extends AbstractBsonReader.Mark {
        private final Object currentValue;
        private final int markPos;
        private final JsonToken pushedToken;

        public Mark() {
            super();
            this.pushedToken = JsonReader.this.pushedToken;
            this.currentValue = JsonReader.this.currentValue;
            this.markPos = JsonReader.this.scanner.mark();
        }

        public void discard() {
            JsonReader.this.scanner.discard(this.markPos);
        }

        @Override // org.bson.AbstractBsonReader.Mark, org.bson.BsonReaderMark
        public void reset() {
            super.reset();
            JsonReader.this.pushedToken = this.pushedToken;
            JsonReader.this.currentValue = this.currentValue;
            JsonReader.this.scanner.reset(this.markPos);
            JsonReader jsonReader = JsonReader.this;
            jsonReader.setContext(new Context(jsonReader, getParentContext(), getContextType()));
        }
    }

    public JsonReader(String str) {
        this(new JsonScanner(str));
    }

    private static byte[] decodeHex(String str) {
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("A hex string must contain an even number of characters: ", str));
        }
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            int iDigit = Character.digit(str.charAt(i), 16);
            int iDigit2 = Character.digit(str.charAt(i + 1), 16);
            if (iDigit == -1 || iDigit2 == -1) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("A hex string can only contain the characters 0-9, A-F, a-f: ", str));
            }
            bArr[i / 2] = (byte) ((iDigit * 16) + iDigit2);
        }
        return bArr;
    }

    private JsonToken popToken() {
        JsonToken jsonToken = this.pushedToken;
        if (jsonToken == null) {
            return this.scanner.nextToken();
        }
        this.pushedToken = null;
        return jsonToken;
    }

    private void pushToken(JsonToken jsonToken) {
        if (this.pushedToken != null) {
            throw new BsonInvalidOperationException("There is already a pending token.");
        }
        this.pushedToken = jsonToken;
    }

    private byte readBinarySubtypeFromExtendedJson() {
        JsonToken jsonTokenPopToken = popToken();
        JsonTokenType type = jsonTokenPopToken.getType();
        JsonTokenType jsonTokenType = JsonTokenType.STRING;
        if (type == jsonTokenType || jsonTokenPopToken.getType() == JsonTokenType.INT32) {
            return jsonTokenPopToken.getType() == jsonTokenType ? (byte) Integer.parseInt((String) jsonTokenPopToken.getValue(String.class), 16) : ((Integer) jsonTokenPopToken.getValue(Integer.class)).byteValue();
        }
        throw new JsonParseException("JSON reader expected a string or number but found '%s'.", jsonTokenPopToken.getValue());
    }

    private ObjectId readDbPointerIdFromExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        verifyToken(JsonTokenType.BEGIN_OBJECT);
        verifyToken(JsonTokenType.STRING, "$oid");
        return visitObjectIdExtendedJson();
    }

    private int readIntFromExtendedJson() {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.INT32) {
            return ((Integer) jsonTokenPopToken.getValue(Integer.class)).intValue();
        }
        if (jsonTokenPopToken.getType() == JsonTokenType.INT64) {
            return ((Long) jsonTokenPopToken.getValue(Long.class)).intValue();
        }
        throw new JsonParseException("JSON reader expected an integer but found '%s'.", jsonTokenPopToken.getValue());
    }

    private String readStringFromExtendedJson() {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.STRING) {
            return (String) jsonTokenPopToken.getValue(String.class);
        }
        throw new JsonParseException("JSON reader expected a string but found '%s'.", jsonTokenPopToken.getValue());
    }

    private void verifyString(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Can't be null");
        }
        JsonToken jsonTokenPopToken = popToken();
        JsonTokenType type = jsonTokenPopToken.getType();
        if ((type != JsonTokenType.STRING && type != JsonTokenType.UNQUOTED_STRING) || !str.equals(jsonTokenPopToken.getValue())) {
            throw new JsonParseException("JSON reader expected '%s' but found '%s'.", str, jsonTokenPopToken.getValue());
        }
    }

    private void verifyToken(JsonTokenType jsonTokenType) {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenType != jsonTokenPopToken.getType()) {
            throw new JsonParseException("JSON reader expected token type '%s' but found '%s'.", jsonTokenType, jsonTokenPopToken.getValue());
        }
    }

    private BsonBinary visitBinDataConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() != JsonTokenType.INT32) {
            throw new JsonParseException("JSON reader expected a binary subtype but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(JsonTokenType.COMMA);
        JsonToken jsonTokenPopToken2 = popToken();
        if (jsonTokenPopToken2.getType() != JsonTokenType.UNQUOTED_STRING && jsonTokenPopToken2.getType() != JsonTokenType.STRING) {
            throw new JsonParseException("JSON reader expected a string but found '%s'.", jsonTokenPopToken2.getValue());
        }
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return new BsonBinary(((Integer) jsonTokenPopToken.getValue(Integer.class)).byteValue(), Base64.decode((String) jsonTokenPopToken2.getValue(String.class)));
    }

    private BsonBinary visitBinDataExtendedJson(String str) {
        byte binarySubtypeFromExtendedJson;
        byte[] bArrDecode;
        Mark mark = new Mark();
        try {
            JsonTokenType jsonTokenType = JsonTokenType.COLON;
            verifyToken(jsonTokenType);
            if (!str.equals("$binary")) {
                mark.reset();
                BsonBinary bsonBinaryVisitLegacyBinaryExtendedJson = visitLegacyBinaryExtendedJson(str);
                mark.discard();
                return bsonBinaryVisitLegacyBinaryExtendedJson;
            }
            if (popToken().getType() != JsonTokenType.BEGIN_OBJECT) {
                mark.reset();
                BsonBinary bsonBinaryVisitLegacyBinaryExtendedJson2 = visitLegacyBinaryExtendedJson(str);
                mark.discard();
                return bsonBinaryVisitLegacyBinaryExtendedJson2;
            }
            String str2 = (String) popToken().getValue(String.class);
            if (str2.equals("base64")) {
                verifyToken(jsonTokenType);
                bArrDecode = Base64.decode(readStringFromExtendedJson());
                verifyToken(JsonTokenType.COMMA);
                verifyString("subType");
                verifyToken(jsonTokenType);
                binarySubtypeFromExtendedJson = readBinarySubtypeFromExtendedJson();
            } else {
                if (!str2.equals("subType")) {
                    throw new JsonParseException("Unexpected key for $binary: " + str2);
                }
                verifyToken(jsonTokenType);
                byte binarySubtypeFromExtendedJson2 = readBinarySubtypeFromExtendedJson();
                verifyToken(JsonTokenType.COMMA);
                verifyString("base64");
                verifyToken(jsonTokenType);
                binarySubtypeFromExtendedJson = binarySubtypeFromExtendedJson2;
                bArrDecode = Base64.decode(readStringFromExtendedJson());
            }
            JsonTokenType jsonTokenType2 = JsonTokenType.END_OBJECT;
            verifyToken(jsonTokenType2);
            verifyToken(jsonTokenType2);
            BsonBinary bsonBinary = new BsonBinary(binarySubtypeFromExtendedJson, bArrDecode);
            mark.discard();
            return bsonBinary;
        } catch (Throwable th) {
            mark.discard();
            throw th;
        }
    }

    private BsonDbPointer visitDBPointerConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        String stringFromExtendedJson = readStringFromExtendedJson();
        verifyToken(JsonTokenType.COMMA);
        ObjectId objectId = new ObjectId(readStringFromExtendedJson());
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return new BsonDbPointer(stringFromExtendedJson, objectId);
    }

    private long visitDateTimeConstructor() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        JsonTokenType type = jsonTokenPopToken.getType();
        JsonTokenType jsonTokenType = JsonTokenType.RIGHT_PAREN;
        if (type == jsonTokenType) {
            return new Date().getTime();
        }
        if (jsonTokenPopToken.getType() == JsonTokenType.STRING) {
            verifyToken(jsonTokenType);
            String str = (String) jsonTokenPopToken.getValue(String.class);
            ParsePosition parsePosition = new ParsePosition(0);
            Date date = simpleDateFormat.parse(str, parsePosition);
            if (date == null || parsePosition.getIndex() != str.length()) {
                throw new JsonParseException("JSON reader expected a date in 'EEE MMM dd yyyy HH:mm:ss z' format but found '%s'.", str);
            }
            return date.getTime();
        }
        if (jsonTokenPopToken.getType() != JsonTokenType.INT32 && jsonTokenPopToken.getType() != JsonTokenType.INT64) {
            throw new JsonParseException("JSON reader expected an integer or a string but found '%s'.", jsonTokenPopToken.getValue());
        }
        long[] jArr = new long[7];
        int i = 0;
        while (true) {
            if (i < 7) {
                jArr[i] = ((Long) jsonTokenPopToken.getValue(Long.class)).longValue();
                i++;
            }
            JsonToken jsonTokenPopToken2 = popToken();
            if (jsonTokenPopToken2.getType() == JsonTokenType.RIGHT_PAREN) {
                if (i == 1) {
                    return jArr[0];
                }
                if (i < 3 || i > 7) {
                    throw new JsonParseException("JSON reader expected 1 or 3-7 integers but found %d.", Integer.valueOf(i));
                }
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.set(1, (int) jArr[0]);
                calendar.set(2, (int) jArr[1]);
                calendar.set(5, (int) jArr[2]);
                calendar.set(11, (int) jArr[3]);
                calendar.set(12, (int) jArr[4]);
                calendar.set(13, (int) jArr[5]);
                calendar.set(14, (int) jArr[6]);
                return calendar.getTimeInMillis();
            }
            if (jsonTokenPopToken2.getType() != JsonTokenType.COMMA) {
                throw new JsonParseException("JSON reader expected a ',' or a ')' but found '%s'.", jsonTokenPopToken2.getValue());
            }
            jsonTokenPopToken = popToken();
            if (jsonTokenPopToken.getType() != JsonTokenType.INT32 && jsonTokenPopToken.getType() != JsonTokenType.INT64) {
                throw new JsonParseException("JSON reader expected an integer but found '%s'.", jsonTokenPopToken.getValue());
            }
        }
    }

    private String visitDateTimeConstructorWithOutNew() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() != JsonTokenType.RIGHT_PAREN) {
            while (jsonTokenPopToken.getType() != JsonTokenType.END_OF_FILE) {
                jsonTokenPopToken = popToken();
                if (jsonTokenPopToken.getType() == JsonTokenType.RIGHT_PAREN) {
                    break;
                }
            }
            if (jsonTokenPopToken.getType() != JsonTokenType.RIGHT_PAREN) {
                throw new JsonParseException("JSON reader expected a ')' but found '%s'.", jsonTokenPopToken.getValue());
            }
        }
        return new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH).format(new Date());
    }

    private long visitDateTimeExtendedJson() {
        long jLongValue;
        verifyToken(JsonTokenType.COLON);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.BEGIN_OBJECT) {
            String str = (String) popToken().getValue(String.class);
            if (!str.equals("$numberLong")) {
                throw new JsonParseException(String.format("JSON reader expected $numberLong within $date, but found %s", str));
            }
            long jLongValue2 = visitNumberLongExtendedJson().longValue();
            verifyToken(JsonTokenType.END_OBJECT);
            return jLongValue2;
        }
        if (jsonTokenPopToken.getType() == JsonTokenType.INT32 || jsonTokenPopToken.getType() == JsonTokenType.INT64) {
            jLongValue = ((Long) jsonTokenPopToken.getValue(Long.class)).longValue();
        } else {
            if (jsonTokenPopToken.getType() != JsonTokenType.STRING) {
                throw new JsonParseException("JSON reader expected an integer or string but found '%s'.", jsonTokenPopToken.getValue());
            }
            try {
                jLongValue = DateTimeFormatter.parse((String) jsonTokenPopToken.getValue(String.class));
            } catch (IllegalArgumentException e) {
                throw new JsonParseException("Failed to parse string as a date", e);
            }
        }
        verifyToken(JsonTokenType.END_OBJECT);
        return jLongValue;
    }

    private BsonDbPointer visitDbPointerExtendedJson() {
        ObjectId dbPointerIdFromExtendedJson;
        String stringFromExtendedJson;
        JsonTokenType jsonTokenType = JsonTokenType.COLON;
        verifyToken(jsonTokenType);
        verifyToken(JsonTokenType.BEGIN_OBJECT);
        String stringFromExtendedJson2 = readStringFromExtendedJson();
        if (stringFromExtendedJson2.equals("$ref")) {
            verifyToken(jsonTokenType);
            stringFromExtendedJson = readStringFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString("$id");
            dbPointerIdFromExtendedJson = readDbPointerIdFromExtendedJson();
            verifyToken(JsonTokenType.END_OBJECT);
        } else {
            if (!stringFromExtendedJson2.equals("$id")) {
                throw new JsonParseException(Insets$$ExternalSyntheticOutline0.m("Expected $ref and $id fields in $dbPointer document but found ", stringFromExtendedJson2));
            }
            dbPointerIdFromExtendedJson = readDbPointerIdFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString("$ref");
            verifyToken(jsonTokenType);
            stringFromExtendedJson = readStringFromExtendedJson();
        }
        verifyToken(JsonTokenType.END_OBJECT);
        return new BsonDbPointer(stringFromExtendedJson, dbPointerIdFromExtendedJson);
    }

    private void visitEmptyConstructor() {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.LEFT_PAREN) {
            verifyToken(JsonTokenType.RIGHT_PAREN);
        } else {
            pushToken(jsonTokenPopToken);
        }
    }

    private void visitExtendedJSON() {
        JsonToken jsonTokenPopToken = popToken();
        String str = (String) jsonTokenPopToken.getValue(String.class);
        JsonTokenType type = jsonTokenPopToken.getType();
        if (type == JsonTokenType.STRING || type == JsonTokenType.UNQUOTED_STRING) {
            if ("$binary".equals(str) || "$type".equals(str)) {
                BsonBinary bsonBinaryVisitBinDataExtendedJson = visitBinDataExtendedJson(str);
                this.currentValue = bsonBinaryVisitBinDataExtendedJson;
                if (bsonBinaryVisitBinDataExtendedJson != null) {
                    setCurrentBsonType(BsonType.BINARY);
                    return;
                }
            } else if ("$regex".equals(str) || "$options".equals(str)) {
                BsonRegularExpression bsonRegularExpressionVisitRegularExpressionExtendedJson = visitRegularExpressionExtendedJson(str);
                this.currentValue = bsonRegularExpressionVisitRegularExpressionExtendedJson;
                if (bsonRegularExpressionVisitRegularExpressionExtendedJson != null) {
                    setCurrentBsonType(BsonType.REGULAR_EXPRESSION);
                    return;
                }
            } else {
                if ("$code".equals(str)) {
                    visitJavaScriptExtendedJson();
                    return;
                }
                if ("$date".equals(str)) {
                    this.currentValue = Long.valueOf(visitDateTimeExtendedJson());
                    setCurrentBsonType(BsonType.DATE_TIME);
                    return;
                }
                if ("$maxKey".equals(str)) {
                    this.currentValue = visitMaxKeyExtendedJson();
                    setCurrentBsonType(BsonType.MAX_KEY);
                    return;
                }
                if ("$minKey".equals(str)) {
                    this.currentValue = visitMinKeyExtendedJson();
                    setCurrentBsonType(BsonType.MIN_KEY);
                    return;
                }
                if ("$oid".equals(str)) {
                    this.currentValue = visitObjectIdExtendedJson();
                    setCurrentBsonType(BsonType.OBJECT_ID);
                    return;
                }
                if ("$regularExpression".equals(str)) {
                    this.currentValue = visitNewRegularExpressionExtendedJson();
                    setCurrentBsonType(BsonType.REGULAR_EXPRESSION);
                    return;
                }
                if ("$symbol".equals(str)) {
                    this.currentValue = visitSymbolExtendedJson();
                    setCurrentBsonType(BsonType.SYMBOL);
                    return;
                }
                if ("$timestamp".equals(str)) {
                    this.currentValue = visitTimestampExtendedJson();
                    setCurrentBsonType(BsonType.TIMESTAMP);
                    return;
                }
                if ("$undefined".equals(str)) {
                    this.currentValue = visitUndefinedExtendedJson();
                    setCurrentBsonType(BsonType.UNDEFINED);
                    return;
                }
                if ("$numberLong".equals(str)) {
                    this.currentValue = visitNumberLongExtendedJson();
                    setCurrentBsonType(BsonType.INT64);
                    return;
                }
                if ("$numberInt".equals(str)) {
                    this.currentValue = visitNumberIntExtendedJson();
                    setCurrentBsonType(BsonType.INT32);
                    return;
                }
                if ("$numberDouble".equals(str)) {
                    this.currentValue = visitNumberDoubleExtendedJson();
                    setCurrentBsonType(BsonType.DOUBLE);
                    return;
                } else if ("$numberDecimal".equals(str)) {
                    this.currentValue = visitNumberDecimalExtendedJson();
                    setCurrentBsonType(BsonType.DECIMAL128);
                    return;
                } else if ("$dbPointer".equals(str)) {
                    this.currentValue = visitDbPointerExtendedJson();
                    setCurrentBsonType(BsonType.DB_POINTER);
                    return;
                }
            }
        }
        pushToken(jsonTokenPopToken);
        setCurrentBsonType(BsonType.DOCUMENT);
    }

    private BsonBinary visitHexDataConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() != JsonTokenType.INT32) {
            throw new JsonParseException("JSON reader expected a binary subtype but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(JsonTokenType.COMMA);
        String stringFromExtendedJson = readStringFromExtendedJson();
        verifyToken(JsonTokenType.RIGHT_PAREN);
        if ((stringFromExtendedJson.length() & 1) != 0) {
            stringFromExtendedJson = Insets$$ExternalSyntheticOutline0.m("0", stringFromExtendedJson);
        }
        for (BsonBinarySubType bsonBinarySubType : BsonBinarySubType.values()) {
            if (bsonBinarySubType.getValue() == ((Integer) jsonTokenPopToken.getValue(Integer.class)).intValue()) {
                return new BsonBinary(bsonBinarySubType, decodeHex(stringFromExtendedJson));
            }
        }
        return new BsonBinary(decodeHex(stringFromExtendedJson));
    }

    private long visitISODateTimeConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        JsonTokenType type = jsonTokenPopToken.getType();
        JsonTokenType jsonTokenType = JsonTokenType.RIGHT_PAREN;
        if (type == jsonTokenType) {
            return new Date().getTime();
        }
        if (jsonTokenPopToken.getType() != JsonTokenType.STRING) {
            throw new JsonParseException("JSON reader expected a string but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(jsonTokenType);
        String[] strArr = {"yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ssz", "yyyy-MM-dd'T'HH:mm:ss.SSSz"};
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strArr[0], Locale.ENGLISH);
        ParsePosition parsePosition = new ParsePosition(0);
        String str = (String) jsonTokenPopToken.getValue(String.class);
        if (str.endsWith("Z")) {
            str = str.substring(0, str.length() - 1) + "GMT-00:00";
        }
        for (int i = 0; i < 3; i++) {
            simpleDateFormat.applyPattern(strArr[i]);
            simpleDateFormat.setLenient(true);
            parsePosition.setIndex(0);
            Date date = simpleDateFormat.parse(str, parsePosition);
            if (date != null && parsePosition.getIndex() == str.length()) {
                return date.getTime();
            }
        }
        throw new JsonParseException("Invalid date format.");
    }

    private void visitJavaScriptExtendedJson() {
        JsonTokenType jsonTokenType = JsonTokenType.COLON;
        verifyToken(jsonTokenType);
        String stringFromExtendedJson = readStringFromExtendedJson();
        JsonToken jsonTokenPopToken = popToken();
        int i = AnonymousClass1.$SwitchMap$org$bson$json$JsonTokenType[jsonTokenPopToken.getType().ordinal()];
        if (i == 3) {
            this.currentValue = stringFromExtendedJson;
            setCurrentBsonType(BsonType.JAVASCRIPT);
        } else {
            if (i != 11) {
                throw new JsonParseException("JSON reader expected ',' or '}' but found '%s'.", jsonTokenPopToken);
            }
            verifyString("$scope");
            verifyToken(jsonTokenType);
            setState(AbstractBsonReader.State.VALUE);
            this.currentValue = stringFromExtendedJson;
            setCurrentBsonType(BsonType.JAVASCRIPT_WITH_SCOPE);
            setContext(new Context(this, getContext(), BsonContextType.SCOPE_DOCUMENT));
        }
    }

    private BsonBinary visitLegacyBinaryExtendedJson(String str) {
        byte binarySubtypeFromExtendedJson;
        byte[] bArrDecode;
        Mark mark = new Mark();
        try {
            JsonTokenType jsonTokenType = JsonTokenType.COLON;
            verifyToken(jsonTokenType);
            if (str.equals("$binary")) {
                bArrDecode = Base64.decode(readStringFromExtendedJson());
                verifyToken(JsonTokenType.COMMA);
                verifyString("$type");
                verifyToken(jsonTokenType);
                binarySubtypeFromExtendedJson = readBinarySubtypeFromExtendedJson();
            } else {
                byte binarySubtypeFromExtendedJson2 = readBinarySubtypeFromExtendedJson();
                verifyToken(JsonTokenType.COMMA);
                verifyString("$binary");
                verifyToken(jsonTokenType);
                binarySubtypeFromExtendedJson = binarySubtypeFromExtendedJson2;
                bArrDecode = Base64.decode(readStringFromExtendedJson());
            }
            verifyToken(JsonTokenType.END_OBJECT);
            return new BsonBinary(binarySubtypeFromExtendedJson, bArrDecode);
        } catch (NumberFormatException unused) {
            mark.reset();
            return null;
        } catch (JsonParseException unused2) {
            mark.reset();
            return null;
        } finally {
            mark.discard();
        }
    }

    private MaxKey visitMaxKeyExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        verifyToken(JsonTokenType.INT32, 1);
        verifyToken(JsonTokenType.END_OBJECT);
        return new MaxKey();
    }

    private MinKey visitMinKeyExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        verifyToken(JsonTokenType.INT32, 1);
        verifyToken(JsonTokenType.END_OBJECT);
        return new MinKey();
    }

    private void visitNew() {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() != JsonTokenType.UNQUOTED_STRING) {
            throw new JsonParseException("JSON reader expected a type name but found '%s'.", jsonTokenPopToken.getValue());
        }
        String str = (String) jsonTokenPopToken.getValue(String.class);
        if ("MinKey".equals(str)) {
            visitEmptyConstructor();
            setCurrentBsonType(BsonType.MIN_KEY);
            this.currentValue = new MinKey();
            return;
        }
        if ("MaxKey".equals(str)) {
            visitEmptyConstructor();
            setCurrentBsonType(BsonType.MAX_KEY);
            this.currentValue = new MaxKey();
            return;
        }
        if ("BinData".equals(str)) {
            this.currentValue = visitBinDataConstructor();
            setCurrentBsonType(BsonType.BINARY);
            return;
        }
        if (HttpHeaders.DATE.equals(str)) {
            this.currentValue = Long.valueOf(visitDateTimeConstructor());
            setCurrentBsonType(BsonType.DATE_TIME);
            return;
        }
        if ("HexData".equals(str)) {
            this.currentValue = visitHexDataConstructor();
            setCurrentBsonType(BsonType.BINARY);
            return;
        }
        if ("ISODate".equals(str)) {
            this.currentValue = Long.valueOf(visitISODateTimeConstructor());
            setCurrentBsonType(BsonType.DATE_TIME);
            return;
        }
        if ("NumberInt".equals(str)) {
            this.currentValue = Integer.valueOf(visitNumberIntConstructor());
            setCurrentBsonType(BsonType.INT32);
            return;
        }
        if ("NumberLong".equals(str)) {
            this.currentValue = Long.valueOf(visitNumberLongConstructor());
            setCurrentBsonType(BsonType.INT64);
            return;
        }
        if ("NumberDecimal".equals(str)) {
            this.currentValue = visitNumberDecimalConstructor();
            setCurrentBsonType(BsonType.DECIMAL128);
            return;
        }
        if ("ObjectId".equals(str)) {
            this.currentValue = visitObjectIdConstructor();
            setCurrentBsonType(BsonType.OBJECT_ID);
            return;
        }
        if ("RegExp".equals(str)) {
            this.currentValue = visitRegularExpressionConstructor();
            setCurrentBsonType(BsonType.REGULAR_EXPRESSION);
            return;
        }
        if ("DBPointer".equals(str)) {
            this.currentValue = visitDBPointerConstructor();
            setCurrentBsonType(BsonType.DB_POINTER);
            return;
        }
        if (!"UUID".equals(str) && !"GUID".equals(str) && !"CSUUID".equals(str) && !"CSGUID".equals(str) && !"JUUID".equals(str) && !"JGUID".equals(str) && !"PYUUID".equals(str) && !"PYGUID".equals(str)) {
            throw new JsonParseException("JSON reader expected a type name but found '%s'.", str);
        }
        this.currentValue = visitUUIDConstructor(str);
        setCurrentBsonType(BsonType.BINARY);
    }

    private BsonRegularExpression visitNewRegularExpressionExtendedJson() {
        String stringFromExtendedJson;
        String stringFromExtendedJson2;
        JsonTokenType jsonTokenType = JsonTokenType.COLON;
        verifyToken(jsonTokenType);
        verifyToken(JsonTokenType.BEGIN_OBJECT);
        String stringFromExtendedJson3 = readStringFromExtendedJson();
        if (stringFromExtendedJson3.equals("pattern")) {
            verifyToken(jsonTokenType);
            stringFromExtendedJson = readStringFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString("options");
            verifyToken(jsonTokenType);
            stringFromExtendedJson2 = readStringFromExtendedJson();
        } else {
            if (!stringFromExtendedJson3.equals("options")) {
                throw new JsonParseException(Insets$$ExternalSyntheticOutline0.m("Expected 't' and 'i' fields in $timestamp document but found ", stringFromExtendedJson3));
            }
            verifyToken(jsonTokenType);
            String stringFromExtendedJson4 = readStringFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString("pattern");
            verifyToken(jsonTokenType);
            stringFromExtendedJson = readStringFromExtendedJson();
            stringFromExtendedJson2 = stringFromExtendedJson4;
        }
        JsonTokenType jsonTokenType2 = JsonTokenType.END_OBJECT;
        verifyToken(jsonTokenType2);
        verifyToken(jsonTokenType2);
        return new BsonRegularExpression(stringFromExtendedJson, stringFromExtendedJson2);
    }

    private Decimal128 visitNumberDecimalConstructor() {
        Decimal128 decimal128;
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.INT32 || jsonTokenPopToken.getType() == JsonTokenType.INT64 || jsonTokenPopToken.getType() == JsonTokenType.DOUBLE) {
            decimal128 = (Decimal128) jsonTokenPopToken.getValue(Decimal128.class);
        } else {
            if (jsonTokenPopToken.getType() != JsonTokenType.STRING) {
                throw new JsonParseException("JSON reader expected a number or a string but found '%s'.", jsonTokenPopToken.getValue());
            }
            decimal128 = Decimal128.parse((String) jsonTokenPopToken.getValue(String.class));
        }
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return decimal128;
    }

    private Decimal128 visitNumberDecimalExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        String stringFromExtendedJson = readStringFromExtendedJson();
        try {
            Decimal128 decimal128 = Decimal128.parse(stringFromExtendedJson);
            verifyToken(JsonTokenType.END_OBJECT);
            return decimal128;
        } catch (NumberFormatException e) {
            throw new JsonParseException(String.format("Exception converting value '%s' to type %s", stringFromExtendedJson, Decimal128.class.getName()), e);
        }
    }

    private Double visitNumberDoubleExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        String stringFromExtendedJson = readStringFromExtendedJson();
        try {
            Double dValueOf = Double.valueOf(stringFromExtendedJson);
            verifyToken(JsonTokenType.END_OBJECT);
            return dValueOf;
        } catch (NumberFormatException e) {
            throw new JsonParseException(String.format("Exception converting value '%s' to type %s", stringFromExtendedJson, Double.class.getName()), e);
        }
    }

    private int visitNumberIntConstructor() {
        int iIntValue;
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.INT32) {
            iIntValue = ((Integer) jsonTokenPopToken.getValue(Integer.class)).intValue();
        } else {
            if (jsonTokenPopToken.getType() != JsonTokenType.STRING) {
                throw new JsonParseException("JSON reader expected an integer or a string but found '%s'.", jsonTokenPopToken.getValue());
            }
            iIntValue = Integer.parseInt((String) jsonTokenPopToken.getValue(String.class));
        }
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return iIntValue;
    }

    private Integer visitNumberIntExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        String stringFromExtendedJson = readStringFromExtendedJson();
        try {
            Integer numValueOf = Integer.valueOf(stringFromExtendedJson);
            verifyToken(JsonTokenType.END_OBJECT);
            return numValueOf;
        } catch (NumberFormatException e) {
            throw new JsonParseException(String.format("Exception converting value '%s' to type %s", stringFromExtendedJson, Integer.class.getName()), e);
        }
    }

    private long visitNumberLongConstructor() {
        long jLongValue;
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.INT32 || jsonTokenPopToken.getType() == JsonTokenType.INT64) {
            jLongValue = ((Long) jsonTokenPopToken.getValue(Long.class)).longValue();
        } else {
            if (jsonTokenPopToken.getType() != JsonTokenType.STRING) {
                throw new JsonParseException("JSON reader expected an integer or a string but found '%s'.", jsonTokenPopToken.getValue());
            }
            jLongValue = Long.parseLong((String) jsonTokenPopToken.getValue(String.class));
        }
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return jLongValue;
    }

    private Long visitNumberLongExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        String stringFromExtendedJson = readStringFromExtendedJson();
        try {
            Long lValueOf = Long.valueOf(stringFromExtendedJson);
            verifyToken(JsonTokenType.END_OBJECT);
            return lValueOf;
        } catch (NumberFormatException e) {
            throw new JsonParseException(String.format("Exception converting value '%s' to type %s", stringFromExtendedJson, Long.class.getName()), e);
        }
    }

    private ObjectId visitObjectIdConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        ObjectId objectId = new ObjectId(readStringFromExtendedJson());
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return objectId;
    }

    private ObjectId visitObjectIdExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        ObjectId objectId = new ObjectId(readStringFromExtendedJson());
        verifyToken(JsonTokenType.END_OBJECT);
        return objectId;
    }

    private BsonRegularExpression visitRegularExpressionConstructor() {
        String stringFromExtendedJson;
        verifyToken(JsonTokenType.LEFT_PAREN);
        String stringFromExtendedJson2 = readStringFromExtendedJson();
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.COMMA) {
            stringFromExtendedJson = readStringFromExtendedJson();
        } else {
            pushToken(jsonTokenPopToken);
            stringFromExtendedJson = "";
        }
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return new BsonRegularExpression(stringFromExtendedJson2, stringFromExtendedJson);
    }

    private BsonRegularExpression visitRegularExpressionExtendedJson(String str) {
        String stringFromExtendedJson;
        String stringFromExtendedJson2;
        Mark mark = new Mark();
        try {
            JsonTokenType jsonTokenType = JsonTokenType.COLON;
            verifyToken(jsonTokenType);
            if (str.equals("$regex")) {
                stringFromExtendedJson2 = readStringFromExtendedJson();
                verifyToken(JsonTokenType.COMMA);
                verifyString("$options");
                verifyToken(jsonTokenType);
                stringFromExtendedJson = readStringFromExtendedJson();
            } else {
                String stringFromExtendedJson3 = readStringFromExtendedJson();
                verifyToken(JsonTokenType.COMMA);
                verifyString("$regex");
                verifyToken(jsonTokenType);
                stringFromExtendedJson = stringFromExtendedJson3;
                stringFromExtendedJson2 = readStringFromExtendedJson();
            }
            verifyToken(JsonTokenType.END_OBJECT);
            return new BsonRegularExpression(stringFromExtendedJson2, stringFromExtendedJson);
        } catch (JsonParseException unused) {
            mark.reset();
            return null;
        } finally {
            mark.discard();
        }
    }

    private String visitSymbolExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        String stringFromExtendedJson = readStringFromExtendedJson();
        verifyToken(JsonTokenType.END_OBJECT);
        return stringFromExtendedJson;
    }

    private BsonTimestamp visitTimestampConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        JsonTokenType type = jsonTokenPopToken.getType();
        JsonTokenType jsonTokenType = JsonTokenType.INT32;
        if (type != jsonTokenType) {
            throw new JsonParseException("JSON reader expected an integer but found '%s'.", jsonTokenPopToken.getValue());
        }
        int iIntValue = ((Integer) jsonTokenPopToken.getValue(Integer.class)).intValue();
        verifyToken(JsonTokenType.COMMA);
        JsonToken jsonTokenPopToken2 = popToken();
        if (jsonTokenPopToken2.getType() != jsonTokenType) {
            throw new JsonParseException("JSON reader expected an integer but found '%s'.", jsonTokenPopToken.getValue());
        }
        int iIntValue2 = ((Integer) jsonTokenPopToken2.getValue(Integer.class)).intValue();
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return new BsonTimestamp(iIntValue, iIntValue2);
    }

    private BsonTimestamp visitTimestampExtendedJson() {
        int intFromExtendedJson;
        int intFromExtendedJson2;
        JsonTokenType jsonTokenType = JsonTokenType.COLON;
        verifyToken(jsonTokenType);
        verifyToken(JsonTokenType.BEGIN_OBJECT);
        String stringFromExtendedJson = readStringFromExtendedJson();
        if (stringFromExtendedJson.equals("t")) {
            verifyToken(jsonTokenType);
            intFromExtendedJson = readIntFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString("i");
            verifyToken(jsonTokenType);
            intFromExtendedJson2 = readIntFromExtendedJson();
        } else {
            if (!stringFromExtendedJson.equals("i")) {
                throw new JsonParseException(Insets$$ExternalSyntheticOutline0.m("Expected 't' and 'i' fields in $timestamp document but found ", stringFromExtendedJson));
            }
            verifyToken(jsonTokenType);
            int intFromExtendedJson3 = readIntFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString("t");
            verifyToken(jsonTokenType);
            intFromExtendedJson = readIntFromExtendedJson();
            intFromExtendedJson2 = intFromExtendedJson3;
        }
        JsonTokenType jsonTokenType2 = JsonTokenType.END_OBJECT;
        verifyToken(jsonTokenType2);
        verifyToken(jsonTokenType2);
        return new BsonTimestamp(intFromExtendedJson, intFromExtendedJson2);
    }

    private BsonBinary visitUUIDConstructor(String str) {
        verifyToken(JsonTokenType.LEFT_PAREN);
        String strReplaceAll = readStringFromExtendedJson().replaceAll("\\{", "").replaceAll("}", "").replaceAll("-", "");
        verifyToken(JsonTokenType.RIGHT_PAREN);
        byte[] bArrDecodeHex = decodeHex(strReplaceAll);
        BsonBinarySubType bsonBinarySubType = BsonBinarySubType.UUID_STANDARD;
        if (!"UUID".equals(str) || !"GUID".equals(str)) {
            bsonBinarySubType = BsonBinarySubType.UUID_LEGACY;
        }
        return new BsonBinary(bsonBinarySubType, bArrDecodeHex);
    }

    private BsonUndefined visitUndefinedExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        JsonToken jsonTokenPopToken = popToken();
        if (!((String) jsonTokenPopToken.getValue(String.class)).equals("true")) {
            throw new JsonParseException("JSON reader requires $undefined to have the value of true but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(JsonTokenType.END_OBJECT);
        return new BsonUndefined();
    }

    @Override // org.bson.AbstractBsonReader
    public final int doPeekBinarySize() {
        return ((BsonBinary) this.currentValue).getData().length;
    }

    @Override // org.bson.AbstractBsonReader
    public final byte doPeekBinarySubType() {
        return ((BsonBinary) this.currentValue).getType();
    }

    @Override // org.bson.AbstractBsonReader
    public final BsonBinary doReadBinaryData() {
        return (BsonBinary) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    public final boolean doReadBoolean() {
        return ((Boolean) this.currentValue).booleanValue();
    }

    @Override // org.bson.AbstractBsonReader
    public final BsonDbPointer doReadDBPointer() {
        return (BsonDbPointer) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    public final long doReadDateTime() {
        return ((Long) this.currentValue).longValue();
    }

    @Override // org.bson.AbstractBsonReader
    public Decimal128 doReadDecimal128() {
        return (Decimal128) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    public final double doReadDouble() {
        return ((Double) this.currentValue).doubleValue();
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadEndArray() {
        setContext(getContext().getParentContext());
        if (getContext().getContextType() == BsonContextType.ARRAY || getContext().getContextType() == BsonContextType.DOCUMENT) {
            JsonToken jsonTokenPopToken = popToken();
            if (jsonTokenPopToken.getType() != JsonTokenType.COMMA) {
                pushToken(jsonTokenPopToken);
            }
        }
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadEndDocument() {
        setContext(getContext().getParentContext());
        if (getContext() != null && getContext().getContextType() == BsonContextType.SCOPE_DOCUMENT) {
            setContext(getContext().getParentContext());
            verifyToken(JsonTokenType.END_OBJECT);
        }
        if (getContext() == null) {
            throw new JsonParseException("Unexpected end of document.");
        }
        if (getContext().getContextType() == BsonContextType.ARRAY || getContext().getContextType() == BsonContextType.DOCUMENT) {
            JsonToken jsonTokenPopToken = popToken();
            if (jsonTokenPopToken.getType() != JsonTokenType.COMMA) {
                pushToken(jsonTokenPopToken);
            }
        }
    }

    @Override // org.bson.AbstractBsonReader
    public final int doReadInt32() {
        return ((Integer) this.currentValue).intValue();
    }

    @Override // org.bson.AbstractBsonReader
    public final long doReadInt64() {
        return ((Long) this.currentValue).longValue();
    }

    @Override // org.bson.AbstractBsonReader
    public final String doReadJavaScript() {
        return (String) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    public final String doReadJavaScriptWithScope() {
        return (String) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadMaxKey() {
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadMinKey() {
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadNull() {
    }

    @Override // org.bson.AbstractBsonReader
    public final ObjectId doReadObjectId() {
        return (ObjectId) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    public final BsonRegularExpression doReadRegularExpression() {
        return (BsonRegularExpression) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadStartArray() {
        setContext(new Context(this, getContext(), BsonContextType.ARRAY));
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadStartDocument() {
        setContext(new Context(this, getContext(), BsonContextType.DOCUMENT));
    }

    @Override // org.bson.AbstractBsonReader
    public final String doReadString() {
        return (String) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    public final String doReadSymbol() {
        return (String) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    public final BsonTimestamp doReadTimestamp() {
        return (BsonTimestamp) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadUndefined() {
    }

    @Override // org.bson.AbstractBsonReader
    public final void doSkipName() {
    }

    @Override // org.bson.AbstractBsonReader
    public final void doSkipValue() {
        switch (AnonymousClass1.$SwitchMap$org$bson$BsonType[getCurrentBsonType().ordinal()]) {
            case 1:
                readStartArray();
                while (readBsonType() != BsonType.END_OF_DOCUMENT) {
                    skipValue();
                }
                readEndArray();
                break;
            case 2:
                readBinaryData();
                break;
            case 3:
                readBoolean();
                break;
            case 4:
                readDateTime();
                break;
            case 5:
                readStartDocument();
                while (readBsonType() != BsonType.END_OF_DOCUMENT) {
                    skipName();
                    skipValue();
                }
                readEndDocument();
                break;
            case 6:
                readDouble();
                break;
            case 7:
                readInt32();
                break;
            case 8:
                readInt64();
                break;
            case 9:
                readDecimal128();
                break;
            case 10:
                readJavaScript();
                break;
            case 11:
                readJavaScriptWithScope();
                readStartDocument();
                while (readBsonType() != BsonType.END_OF_DOCUMENT) {
                    skipName();
                    skipValue();
                }
                readEndDocument();
                break;
            case 12:
                readMaxKey();
                break;
            case 13:
                readMinKey();
                break;
            case 14:
                readNull();
                break;
            case 15:
                readObjectId();
                break;
            case 16:
                readRegularExpression();
                break;
            case 17:
                readString();
                break;
            case 18:
                readSymbol();
                break;
            case 19:
                readTimestamp();
                break;
            case 20:
                readUndefined();
                break;
        }
    }

    @Override // org.bson.BsonReader
    public BsonReaderMark getMark() {
        return new Mark();
    }

    @Override // org.bson.BsonReader
    @Deprecated
    public void mark() {
        if (this.mark != null) {
            throw new BSONException("A mark already exists; it needs to be reset before creating a new one");
        }
        this.mark = new Mark();
    }

    @Override // org.bson.AbstractBsonReader, org.bson.BsonReader
    public BsonType readBsonType() {
        boolean z;
        if (isClosed()) {
            throw new IllegalStateException("This instance has been closed");
        }
        if (getState() == AbstractBsonReader.State.INITIAL || getState() == AbstractBsonReader.State.DONE || getState() == AbstractBsonReader.State.SCOPE_DOCUMENT) {
            setState(AbstractBsonReader.State.TYPE);
        }
        AbstractBsonReader.State state = getState();
        AbstractBsonReader.State state2 = AbstractBsonReader.State.TYPE;
        if (state != state2) {
            throwInvalidState("readBSONType", state2);
            throw null;
        }
        BsonContextType contextType = getContext().getContextType();
        BsonContextType bsonContextType = BsonContextType.DOCUMENT;
        if (contextType == bsonContextType) {
            JsonToken jsonTokenPopToken = popToken();
            int i = AnonymousClass1.$SwitchMap$org$bson$json$JsonTokenType[jsonTokenPopToken.getType().ordinal()];
            if (i != 1 && i != 2) {
                if (i != 3) {
                    throw new JsonParseException("JSON reader was expecting a name but found '%s'.", jsonTokenPopToken.getValue());
                }
                setState(AbstractBsonReader.State.END_OF_DOCUMENT);
                return BsonType.END_OF_DOCUMENT;
            }
            setCurrentName((String) jsonTokenPopToken.getValue(String.class));
            JsonToken jsonTokenPopToken2 = popToken();
            if (jsonTokenPopToken2.getType() != JsonTokenType.COLON) {
                throw new JsonParseException("JSON reader was expecting ':' but found '%s'.", jsonTokenPopToken2.getValue());
            }
        }
        JsonToken jsonTokenPopToken3 = popToken();
        BsonContextType contextType2 = getContext().getContextType();
        BsonContextType bsonContextType2 = BsonContextType.ARRAY;
        if (contextType2 == bsonContextType2 && jsonTokenPopToken3.getType() == JsonTokenType.END_ARRAY) {
            setState(AbstractBsonReader.State.END_OF_ARRAY);
            return BsonType.END_OF_DOCUMENT;
        }
        switch (AnonymousClass1.$SwitchMap$org$bson$json$JsonTokenType[jsonTokenPopToken3.getType().ordinal()]) {
            case 1:
                setCurrentBsonType(BsonType.STRING);
                this.currentValue = jsonTokenPopToken3.getValue();
                z = false;
                break;
            case 2:
                String str = (String) jsonTokenPopToken3.getValue(String.class);
                if ("false".equals(str) || "true".equals(str)) {
                    setCurrentBsonType(BsonType.BOOLEAN);
                    this.currentValue = Boolean.valueOf(Boolean.parseBoolean(str));
                } else if ("Infinity".equals(str)) {
                    setCurrentBsonType(BsonType.DOUBLE);
                    this.currentValue = Double.valueOf(Double.POSITIVE_INFINITY);
                } else if ("NaN".equals(str)) {
                    setCurrentBsonType(BsonType.DOUBLE);
                    this.currentValue = Double.valueOf(Double.NaN);
                } else if ("null".equals(str)) {
                    setCurrentBsonType(BsonType.NULL);
                } else if ("undefined".equals(str)) {
                    setCurrentBsonType(BsonType.UNDEFINED);
                } else if ("MinKey".equals(str)) {
                    visitEmptyConstructor();
                    setCurrentBsonType(BsonType.MIN_KEY);
                    this.currentValue = new MinKey();
                } else if ("MaxKey".equals(str)) {
                    visitEmptyConstructor();
                    setCurrentBsonType(BsonType.MAX_KEY);
                    this.currentValue = new MaxKey();
                } else if ("BinData".equals(str)) {
                    setCurrentBsonType(BsonType.BINARY);
                    this.currentValue = visitBinDataConstructor();
                } else if (HttpHeaders.DATE.equals(str)) {
                    this.currentValue = visitDateTimeConstructorWithOutNew();
                    setCurrentBsonType(BsonType.STRING);
                } else if ("HexData".equals(str)) {
                    setCurrentBsonType(BsonType.BINARY);
                    this.currentValue = visitHexDataConstructor();
                } else if ("ISODate".equals(str)) {
                    setCurrentBsonType(BsonType.DATE_TIME);
                    this.currentValue = Long.valueOf(visitISODateTimeConstructor());
                } else if ("NumberInt".equals(str)) {
                    setCurrentBsonType(BsonType.INT32);
                    this.currentValue = Integer.valueOf(visitNumberIntConstructor());
                } else if ("NumberLong".equals(str)) {
                    setCurrentBsonType(BsonType.INT64);
                    this.currentValue = Long.valueOf(visitNumberLongConstructor());
                } else if ("NumberDecimal".equals(str)) {
                    setCurrentBsonType(BsonType.DECIMAL128);
                    this.currentValue = visitNumberDecimalConstructor();
                } else if ("ObjectId".equals(str)) {
                    setCurrentBsonType(BsonType.OBJECT_ID);
                    this.currentValue = visitObjectIdConstructor();
                } else if ("Timestamp".equals(str)) {
                    setCurrentBsonType(BsonType.TIMESTAMP);
                    this.currentValue = visitTimestampConstructor();
                } else if ("RegExp".equals(str)) {
                    setCurrentBsonType(BsonType.REGULAR_EXPRESSION);
                    this.currentValue = visitRegularExpressionConstructor();
                } else if ("DBPointer".equals(str)) {
                    setCurrentBsonType(BsonType.DB_POINTER);
                    this.currentValue = visitDBPointerConstructor();
                } else if ("UUID".equals(str) || "GUID".equals(str) || "CSUUID".equals(str) || "CSGUID".equals(str) || "JUUID".equals(str) || "JGUID".equals(str) || "PYUUID".equals(str) || "PYGUID".equals(str)) {
                    setCurrentBsonType(BsonType.BINARY);
                    this.currentValue = visitUUIDConstructor(str);
                } else if ("new".equals(str)) {
                    visitNew();
                }
                z = false;
            case 3:
            default:
                z = true;
                break;
            case 4:
                setCurrentBsonType(BsonType.ARRAY);
                z = false;
                break;
            case 5:
                visitExtendedJSON();
                z = false;
                break;
            case 6:
                setCurrentBsonType(BsonType.DOUBLE);
                this.currentValue = jsonTokenPopToken3.getValue();
                z = false;
                break;
            case 7:
                setCurrentBsonType(BsonType.END_OF_DOCUMENT);
                z = false;
                break;
            case 8:
                setCurrentBsonType(BsonType.INT32);
                this.currentValue = jsonTokenPopToken3.getValue();
                z = false;
                break;
            case 9:
                setCurrentBsonType(BsonType.INT64);
                this.currentValue = jsonTokenPopToken3.getValue();
                z = false;
                break;
            case 10:
                setCurrentBsonType(BsonType.REGULAR_EXPRESSION);
                this.currentValue = jsonTokenPopToken3.getValue();
                z = false;
                break;
        }
        if (z) {
            throw new JsonParseException("JSON reader was expecting a value but found '%s'.", jsonTokenPopToken3.getValue());
        }
        if (getContext().getContextType() == bsonContextType2 || getContext().getContextType() == bsonContextType) {
            JsonToken jsonTokenPopToken4 = popToken();
            if (jsonTokenPopToken4.getType() != JsonTokenType.COMMA) {
                pushToken(jsonTokenPopToken4);
            }
        }
        int i2 = AnonymousClass1.$SwitchMap$org$bson$BsonContextType[getContext().getContextType().ordinal()];
        if (i2 == 3 || i2 == 4 || i2 == 5) {
            setState(AbstractBsonReader.State.VALUE);
        } else {
            setState(AbstractBsonReader.State.NAME);
        }
        return getCurrentBsonType();
    }

    @Override // org.bson.BsonReader
    @Deprecated
    public void reset() {
        Mark mark = this.mark;
        if (mark == null) {
            throw new BSONException("trying to reset a mark before creating it");
        }
        mark.reset();
        this.mark = null;
    }

    public JsonReader(Reader reader) {
        this(new JsonScanner(reader));
    }

    @Override // org.bson.AbstractBsonReader
    public final Context getContext() {
        return (Context) super.getContext();
    }

    private JsonReader(JsonScanner jsonScanner) {
        this.scanner = jsonScanner;
        setContext(new Context(this, null, BsonContextType.TOP_LEVEL));
    }

    private void verifyToken(JsonTokenType jsonTokenType, Object obj) {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenType == jsonTokenPopToken.getType()) {
            if (!obj.equals(jsonTokenPopToken.getValue())) {
                throw new JsonParseException("JSON reader expected '%s' but found '%s'.", obj, jsonTokenPopToken.getValue());
            }
            return;
        }
        throw new JsonParseException("JSON reader expected token type '%s' but found '%s'.", jsonTokenType, jsonTokenPopToken.getValue());
    }
}
