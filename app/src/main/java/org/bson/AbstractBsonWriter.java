package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.io.Closeable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.bson.assertions.Assertions;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractBsonWriter implements BsonWriter, Closeable {
    private boolean closed;
    private Context context;
    private final Stack<FieldNameValidator> fieldNameValidatorStack;
    private int serializationDepth;
    private final BsonWriterSettings settings;
    private State state;

    /* JADX INFO: renamed from: org.bson.AbstractBsonWriter$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
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
                $SwitchMap$org$bson$BsonType[BsonType.UNDEFINED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.OBJECT_ID.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.BOOLEAN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DATE_TIME.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.NULL.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.REGULAR_EXPRESSION.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.JAVASCRIPT.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.SYMBOL.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.JAVASCRIPT_WITH_SCOPE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.INT32.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.TIMESTAMP.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.INT64.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DECIMAL128.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.MIN_KEY.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DB_POINTER.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.MAX_KEY.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
        }
    }

    public class Mark {
        private final String currentName;
        private final Context markedContext;
        private final State markedState;
        private final int serializationDepth;

        public Mark() {
            this.markedContext = AbstractBsonWriter.this.context.copy();
            this.markedState = AbstractBsonWriter.this.state;
            this.currentName = AbstractBsonWriter.this.context.name;
            this.serializationDepth = AbstractBsonWriter.this.serializationDepth;
        }

        public void reset() {
            AbstractBsonWriter.this.setContext(this.markedContext);
            AbstractBsonWriter.this.setState(this.markedState);
            AbstractBsonWriter.this.context.name = this.currentName;
            AbstractBsonWriter.this.serializationDepth = this.serializationDepth;
        }
    }

    public enum State {
        INITIAL,
        NAME,
        VALUE,
        SCOPE_DOCUMENT,
        DONE,
        CLOSED
    }

    public AbstractBsonWriter(BsonWriterSettings bsonWriterSettings) {
        this(bsonWriterSettings, new NoOpFieldNameValidator());
    }

    private void pipeArray(BsonReader bsonReader) {
        bsonReader.readStartArray();
        writeStartArray();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            pipeValue(bsonReader);
            if (abortPipe()) {
                return;
            }
        }
        bsonReader.readEndArray();
        writeEndArray();
    }

    private void pipeDocument(BsonReader bsonReader, List<BsonElement> list) {
        bsonReader.readStartDocument();
        writeStartDocument();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            writeName(bsonReader.readName());
            pipeValue(bsonReader);
            if (abortPipe()) {
                return;
            }
        }
        bsonReader.readEndDocument();
        if (list != null) {
            pipeExtraElements(list);
        }
        writeEndDocument();
    }

    private void pipeJavascriptWithScope(BsonReader bsonReader) {
        writeJavaScriptWithScope(bsonReader.readJavaScriptWithScope());
        pipeDocument(bsonReader, null);
    }

    private void pipeValue(BsonReader bsonReader) {
        switch (AnonymousClass1.$SwitchMap$org$bson$BsonType[bsonReader.getCurrentBsonType().ordinal()]) {
            case 1:
                pipeDocument(bsonReader, null);
                return;
            case 2:
                pipeArray(bsonReader);
                return;
            case 3:
                writeDouble(bsonReader.readDouble());
                return;
            case 4:
                writeString(bsonReader.readString());
                return;
            case 5:
                writeBinaryData(bsonReader.readBinaryData());
                return;
            case 6:
                bsonReader.readUndefined();
                writeUndefined();
                return;
            case 7:
                writeObjectId(bsonReader.readObjectId());
                return;
            case 8:
                writeBoolean(bsonReader.readBoolean());
                return;
            case 9:
                writeDateTime(bsonReader.readDateTime());
                return;
            case 10:
                bsonReader.readNull();
                writeNull();
                return;
            case 11:
                writeRegularExpression(bsonReader.readRegularExpression());
                return;
            case 12:
                writeJavaScript(bsonReader.readJavaScript());
                return;
            case 13:
                writeSymbol(bsonReader.readSymbol());
                return;
            case 14:
                pipeJavascriptWithScope(bsonReader);
                return;
            case 15:
                writeInt32(bsonReader.readInt32());
                return;
            case 16:
                writeTimestamp(bsonReader.readTimestamp());
                return;
            case 17:
                writeInt64(bsonReader.readInt64());
                return;
            case 18:
                writeDecimal128(bsonReader.readDecimal128());
                return;
            case 19:
                bsonReader.readMinKey();
                writeMinKey();
                return;
            case 20:
                writeDBPointer(bsonReader.readDBPointer());
                return;
            case 21:
                bsonReader.readMaxKey();
                writeMaxKey();
                return;
            default:
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("unhandled BSON type: ");
                sbM.append(bsonReader.getCurrentBsonType());
                throw new IllegalArgumentException(sbM.toString());
        }
    }

    public boolean abortPipe() {
        return false;
    }

    public final void checkPreconditions(String str, State... stateArr) {
        if (this.closed) {
            throw new IllegalStateException("BsonWriter is closed");
        }
        boolean z = false;
        for (State state : stateArr) {
            if (state == this.state) {
                z = true;
                break;
            }
        }
        if (z) {
            return;
        }
        throwInvalidState(str, stateArr);
        throw null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.closed = true;
    }

    public abstract void doWriteBinaryData(BsonBinary bsonBinary);

    public abstract void doWriteBoolean(boolean z);

    public abstract void doWriteDBPointer(BsonDbPointer bsonDbPointer);

    public abstract void doWriteDateTime(long j);

    public abstract void doWriteDecimal128(Decimal128 decimal128);

    public abstract void doWriteDouble(double d);

    public abstract void doWriteEndArray();

    public abstract void doWriteEndDocument();

    public abstract void doWriteInt32(int i);

    public abstract void doWriteInt64(long j);

    public abstract void doWriteJavaScript(String str);

    public abstract void doWriteJavaScriptWithScope(String str);

    public abstract void doWriteMaxKey();

    public abstract void doWriteMinKey();

    public void doWriteName(String str) {
    }

    public abstract void doWriteNull();

    public abstract void doWriteObjectId(ObjectId objectId);

    public abstract void doWriteRegularExpression(BsonRegularExpression bsonRegularExpression);

    public abstract void doWriteStartArray();

    public abstract void doWriteStartDocument();

    public abstract void doWriteString(String str);

    public abstract void doWriteSymbol(String str);

    public abstract void doWriteTimestamp(BsonTimestamp bsonTimestamp);

    public abstract void doWriteUndefined();

    public Context getContext() {
        return this.context;
    }

    public String getName() {
        return this.context.name;
    }

    public final State getNextState() {
        return getContext().getContextType() == BsonContextType.ARRAY ? State.VALUE : State.NAME;
    }

    public final State getState() {
        return this.state;
    }

    @Override // org.bson.BsonWriter
    public void pipe(BsonReader bsonReader) {
        Assertions.notNull("reader", bsonReader);
        pipeDocument(bsonReader, null);
    }

    public final void pipeExtraElements(List<BsonElement> list) {
        Assertions.notNull("extraElements", list);
        for (BsonElement bsonElement : list) {
            writeName(bsonElement.getName());
            pipeValue(bsonElement.getValue());
        }
    }

    public final void setContext(Context context) {
        this.context = context;
    }

    public final void setState(State state) {
        this.state = state;
    }

    public final void throwInvalidContextType(String str, BsonContextType bsonContextType, BsonContextType... bsonContextTypeArr) {
        throw new BsonInvalidOperationException(String.format("%s can only be called when ContextType is %s, not when ContextType is %s.", str, StringUtils.join(" or ", Arrays.asList(bsonContextTypeArr)), bsonContextType));
    }

    public final void throwInvalidState(String str, State... stateArr) {
        State state = this.state;
        if ((state != State.INITIAL && state != State.SCOPE_DOCUMENT && state != State.DONE) || str.startsWith(TtmlNode.END) || str.equals("writeName")) {
            throw new BsonInvalidOperationException(String.format("%s can only be called when State is %s, not when State is %s", str, StringUtils.join(" or ", Arrays.asList(stateArr)), this.state));
        }
        String strSubstring = str.substring(5);
        if (strSubstring.startsWith(TtmlNode.START)) {
            strSubstring = strSubstring.substring(5);
        }
        throw new BsonInvalidOperationException(String.format("%s %s value cannot be written to the root level of a BSON document.", Arrays.asList('A', 'E', 'I', 'O', 'U').contains(Character.valueOf(strSubstring.charAt(0))) ? "An" : ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, strSubstring));
    }

    @Override // org.bson.BsonWriter
    public void writeBinaryData(String str, BsonBinary bsonBinary) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", bsonBinary);
        writeName(str);
        writeBinaryData(bsonBinary);
    }

    @Override // org.bson.BsonWriter
    public void writeBoolean(String str, boolean z) {
        writeName(str);
        writeBoolean(z);
    }

    @Override // org.bson.BsonWriter
    public void writeDBPointer(String str, BsonDbPointer bsonDbPointer) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", bsonDbPointer);
        writeName(str);
        writeDBPointer(bsonDbPointer);
    }

    @Override // org.bson.BsonWriter
    public void writeDateTime(String str, long j) {
        writeName(str);
        writeDateTime(j);
    }

    @Override // org.bson.BsonWriter
    public void writeDecimal128(Decimal128 decimal128) {
        Assertions.notNull("value", decimal128);
        checkPreconditions("writeInt64", State.VALUE);
        doWriteDecimal128(decimal128);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeDouble(String str, double d) {
        writeName(str);
        writeDouble(d);
    }

    @Override // org.bson.BsonWriter
    public void writeEndArray() {
        checkPreconditions("writeEndArray", State.VALUE);
        BsonContextType contextType = getContext().getContextType();
        BsonContextType bsonContextType = BsonContextType.ARRAY;
        if (contextType != bsonContextType) {
            throwInvalidContextType("WriteEndArray", getContext().getContextType(), bsonContextType);
            throw null;
        }
        if (this.context.getParentContext() != null && this.context.getParentContext().name != null) {
            this.fieldNameValidatorStack.pop();
        }
        this.serializationDepth--;
        doWriteEndArray();
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeEndDocument() {
        BsonContextType bsonContextType;
        checkPreconditions("writeEndDocument", State.NAME);
        BsonContextType contextType = getContext().getContextType();
        BsonContextType bsonContextType2 = BsonContextType.DOCUMENT;
        if (contextType != bsonContextType2 && contextType != (bsonContextType = BsonContextType.SCOPE_DOCUMENT)) {
            throwInvalidContextType("WriteEndDocument", contextType, bsonContextType2, bsonContextType);
            throw null;
        }
        if (this.context.getParentContext() != null && this.context.getParentContext().name != null) {
            this.fieldNameValidatorStack.pop();
        }
        this.serializationDepth--;
        doWriteEndDocument();
        if (getContext() == null || getContext().getContextType() == BsonContextType.TOP_LEVEL) {
            this.state = State.DONE;
        } else {
            this.state = getNextState();
        }
    }

    @Override // org.bson.BsonWriter
    public void writeInt32(String str, int i) {
        writeName(str);
        writeInt32(i);
    }

    @Override // org.bson.BsonWriter
    public void writeInt64(String str, long j) {
        writeName(str);
        writeInt64(j);
    }

    @Override // org.bson.BsonWriter
    public void writeJavaScript(String str, String str2) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", str2);
        writeName(str);
        writeJavaScript(str2);
    }

    @Override // org.bson.BsonWriter
    public void writeJavaScriptWithScope(String str, String str2) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", str2);
        writeName(str);
        writeJavaScriptWithScope(str2);
    }

    @Override // org.bson.BsonWriter
    public void writeMaxKey(String str) {
        writeName(str);
        writeMaxKey();
    }

    @Override // org.bson.BsonWriter
    public void writeMinKey(String str) {
        writeName(str);
        writeMinKey();
    }

    @Override // org.bson.BsonWriter
    public void writeName(String str) {
        Assertions.notNull("name", str);
        State state = this.state;
        State state2 = State.NAME;
        if (state != state2) {
            throwInvalidState("WriteName", state2);
            throw null;
        }
        if (!this.fieldNameValidatorStack.peek().validate(str)) {
            throw new IllegalArgumentException(String.format("Invalid BSON field name %s", str));
        }
        doWriteName(str);
        this.context.name = str;
        this.state = State.VALUE;
    }

    @Override // org.bson.BsonWriter
    public void writeNull(String str) {
        writeName(str);
        writeNull();
    }

    @Override // org.bson.BsonWriter
    public void writeObjectId(String str, ObjectId objectId) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", objectId);
        writeName(str);
        writeObjectId(objectId);
    }

    @Override // org.bson.BsonWriter
    public void writeRegularExpression(String str, BsonRegularExpression bsonRegularExpression) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", bsonRegularExpression);
        writeName(str);
        writeRegularExpression(bsonRegularExpression);
    }

    @Override // org.bson.BsonWriter
    public void writeStartArray(String str) {
        writeName(str);
        writeStartArray();
    }

    @Override // org.bson.BsonWriter
    public void writeStartDocument(String str) {
        writeName(str);
        writeStartDocument();
    }

    @Override // org.bson.BsonWriter
    public void writeString(String str, String str2) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", str2);
        writeName(str);
        writeString(str2);
    }

    @Override // org.bson.BsonWriter
    public void writeSymbol(String str, String str2) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", str2);
        writeName(str);
        writeSymbol(str2);
    }

    @Override // org.bson.BsonWriter
    public void writeTimestamp(String str, BsonTimestamp bsonTimestamp) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", bsonTimestamp);
        writeName(str);
        writeTimestamp(bsonTimestamp);
    }

    @Override // org.bson.BsonWriter
    public void writeUndefined(String str) {
        writeName(str);
        writeUndefined();
    }

    public AbstractBsonWriter(BsonWriterSettings bsonWriterSettings, FieldNameValidator fieldNameValidator) {
        Stack<FieldNameValidator> stack = new Stack<>();
        this.fieldNameValidatorStack = stack;
        if (fieldNameValidator == null) {
            throw new IllegalArgumentException("Validator can not be null");
        }
        this.settings = bsonWriterSettings;
        stack.push(fieldNameValidator);
        this.state = State.INITIAL;
    }

    public class Context {
        private final BsonContextType contextType;
        private String name;
        private final Context parentContext;

        public Context(Context context) {
            this.parentContext = context.parentContext;
            this.contextType = context.contextType;
        }

        public Context copy() {
            return AbstractBsonWriter.this.new Context(this);
        }

        public BsonContextType getContextType() {
            return this.contextType;
        }

        public Context getParentContext() {
            return this.parentContext;
        }

        public Context(Context context, BsonContextType bsonContextType) {
            this.parentContext = context;
            this.contextType = bsonContextType;
        }
    }

    private void pipeJavascriptWithScope(BsonJavaScriptWithScope bsonJavaScriptWithScope) {
        writeJavaScriptWithScope(bsonJavaScriptWithScope.getCode());
        pipeDocument(bsonJavaScriptWithScope.getScope());
    }

    public void pipe(BsonReader bsonReader, List<BsonElement> list) {
        Assertions.notNull("reader", bsonReader);
        Assertions.notNull("extraElements", list);
        pipeDocument(bsonReader, list);
    }

    @Override // org.bson.BsonWriter
    public void writeBoolean(boolean z) {
        checkPreconditions("writeBoolean", State.VALUE, State.INITIAL);
        doWriteBoolean(z);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeDateTime(long j) {
        checkPreconditions("writeDateTime", State.VALUE, State.INITIAL);
        doWriteDateTime(j);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeDouble(double d) {
        checkPreconditions("writeDBPointer", State.VALUE, State.INITIAL);
        doWriteDouble(d);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeInt32(int i) {
        checkPreconditions("writeInt32", State.VALUE);
        doWriteInt32(i);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeInt64(long j) {
        checkPreconditions("writeInt64", State.VALUE);
        doWriteInt64(j);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeMaxKey() {
        checkPreconditions("writeMaxKey", State.VALUE);
        doWriteMaxKey();
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeMinKey() {
        checkPreconditions("writeMinKey", State.VALUE);
        doWriteMinKey();
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeNull() {
        checkPreconditions("writeNull", State.VALUE);
        doWriteNull();
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeStartArray() {
        State state = State.VALUE;
        checkPreconditions("writeStartArray", state);
        Context context = this.context;
        if (context != null && context.name != null) {
            Stack<FieldNameValidator> stack = this.fieldNameValidatorStack;
            stack.push(stack.peek().getValidatorForField(getName()));
        }
        int i = this.serializationDepth + 1;
        this.serializationDepth = i;
        if (i <= this.settings.getMaxSerializationDepth()) {
            doWriteStartArray();
            this.state = state;
            return;
        }
        throw new BsonSerializationException("Maximum serialization depth exceeded (does the object being serialized have a circular reference?).");
    }

    @Override // org.bson.BsonWriter
    public void writeStartDocument() {
        checkPreconditions("writeStartDocument", State.INITIAL, State.VALUE, State.SCOPE_DOCUMENT, State.DONE);
        Context context = this.context;
        if (context != null && context.name != null) {
            Stack<FieldNameValidator> stack = this.fieldNameValidatorStack;
            stack.push(stack.peek().getValidatorForField(getName()));
        }
        int i = this.serializationDepth + 1;
        this.serializationDepth = i;
        if (i <= this.settings.getMaxSerializationDepth()) {
            doWriteStartDocument();
            this.state = State.NAME;
            return;
        }
        throw new BsonSerializationException("Maximum serialization depth exceeded (does the object being serialized have a circular reference?).");
    }

    @Override // org.bson.BsonWriter
    public void writeUndefined() {
        checkPreconditions("writeUndefined", State.VALUE);
        doWriteUndefined();
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeBinaryData(BsonBinary bsonBinary) {
        Assertions.notNull("value", bsonBinary);
        checkPreconditions("writeBinaryData", State.VALUE, State.INITIAL);
        doWriteBinaryData(bsonBinary);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeDBPointer(BsonDbPointer bsonDbPointer) {
        Assertions.notNull("value", bsonDbPointer);
        checkPreconditions("writeDBPointer", State.VALUE, State.INITIAL);
        doWriteDBPointer(bsonDbPointer);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeJavaScript(String str) {
        Assertions.notNull("value", str);
        checkPreconditions("writeJavaScript", State.VALUE);
        doWriteJavaScript(str);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeJavaScriptWithScope(String str) {
        Assertions.notNull("value", str);
        checkPreconditions("writeJavaScriptWithScope", State.VALUE);
        doWriteJavaScriptWithScope(str);
        this.state = State.SCOPE_DOCUMENT;
    }

    @Override // org.bson.BsonWriter
    public void writeObjectId(ObjectId objectId) {
        Assertions.notNull("value", objectId);
        checkPreconditions("writeObjectId", State.VALUE);
        doWriteObjectId(objectId);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeRegularExpression(BsonRegularExpression bsonRegularExpression) {
        Assertions.notNull("value", bsonRegularExpression);
        checkPreconditions("writeRegularExpression", State.VALUE);
        doWriteRegularExpression(bsonRegularExpression);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeString(String str) {
        Assertions.notNull("value", str);
        checkPreconditions("writeString", State.VALUE);
        doWriteString(str);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeSymbol(String str) {
        Assertions.notNull("value", str);
        checkPreconditions("writeSymbol", State.VALUE);
        doWriteSymbol(str);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeTimestamp(BsonTimestamp bsonTimestamp) {
        Assertions.notNull("value", bsonTimestamp);
        checkPreconditions("writeTimestamp", State.VALUE);
        doWriteTimestamp(bsonTimestamp);
        this.state = getNextState();
    }

    @Override // org.bson.BsonWriter
    public void writeDecimal128(String str, Decimal128 decimal128) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", decimal128);
        writeName(str);
        writeDecimal128(decimal128);
    }

    private void pipeArray(BsonArray bsonArray) {
        writeStartArray();
        Iterator<BsonValue> it = bsonArray.iterator();
        while (it.hasNext()) {
            pipeValue(it.next());
        }
        writeEndArray();
    }

    private void pipeDocument(BsonDocument bsonDocument) {
        writeStartDocument();
        for (Map.Entry<String, BsonValue> entry : bsonDocument.entrySet()) {
            writeName(entry.getKey());
            pipeValue(entry.getValue());
        }
        writeEndDocument();
    }

    private void pipeValue(BsonValue bsonValue) {
        switch (AnonymousClass1.$SwitchMap$org$bson$BsonType[bsonValue.getBsonType().ordinal()]) {
            case 1:
                pipeDocument(bsonValue.asDocument());
                return;
            case 2:
                pipeArray(bsonValue.asArray());
                return;
            case 3:
                writeDouble(bsonValue.asDouble().getValue());
                return;
            case 4:
                writeString(bsonValue.asString().getValue());
                return;
            case 5:
                writeBinaryData(bsonValue.asBinary());
                return;
            case 6:
                writeUndefined();
                return;
            case 7:
                writeObjectId(bsonValue.asObjectId().getValue());
                return;
            case 8:
                writeBoolean(bsonValue.asBoolean().getValue());
                return;
            case 9:
                writeDateTime(bsonValue.asDateTime().getValue());
                return;
            case 10:
                writeNull();
                return;
            case 11:
                writeRegularExpression(bsonValue.asRegularExpression());
                return;
            case 12:
                writeJavaScript(bsonValue.asJavaScript().getCode());
                return;
            case 13:
                writeSymbol(bsonValue.asSymbol().getSymbol());
                return;
            case 14:
                pipeJavascriptWithScope(bsonValue.asJavaScriptWithScope());
                return;
            case 15:
                writeInt32(bsonValue.asInt32().getValue());
                return;
            case 16:
                writeTimestamp(bsonValue.asTimestamp());
                return;
            case 17:
                writeInt64(bsonValue.asInt64().getValue());
                return;
            case 18:
                writeDecimal128(bsonValue.asDecimal128().getValue());
                return;
            case 19:
                writeMinKey();
                return;
            case 20:
                writeDBPointer(bsonValue.asDBPointer());
                return;
            case 21:
                writeMaxKey();
                return;
            default:
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("unhandled BSON type: ");
                sbM.append(bsonValue.getBsonType());
                throw new IllegalArgumentException(sbM.toString());
        }
    }
}
