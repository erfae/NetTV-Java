package org.bson;

import java.util.Arrays;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractBsonReader implements BsonReader {
    private boolean closed;
    private Context context;
    private BsonType currentBsonType;
    private String currentName;
    private State state = State.INITIAL;

    /* JADX INFO: renamed from: org.bson.AbstractBsonReader$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$org$bson$BsonContextType;

        static {
            int[] iArr = new int[BsonContextType.values().length];
            $SwitchMap$org$bson$BsonContextType = iArr;
            try {
                iArr[BsonContextType.ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.DOCUMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.SCOPE_DOCUMENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.TOP_LEVEL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public abstract class Context {
        private final BsonContextType contextType;
        private final Context parentContext;

        public Context(Context context, BsonContextType bsonContextType) {
            this.parentContext = context;
            this.contextType = bsonContextType;
        }

        public BsonContextType getContextType() {
            return this.contextType;
        }

        public final Context getParentContext() {
            return this.parentContext;
        }
    }

    public class Mark implements BsonReaderMark {
        private final BsonContextType contextType;
        private final BsonType currentBsonType;
        private final String currentName;
        private final Context parentContext;
        private final State state;

        public Mark() {
            this.state = AbstractBsonReader.this.state;
            this.parentContext = AbstractBsonReader.this.context.parentContext;
            this.contextType = AbstractBsonReader.this.context.contextType;
            this.currentBsonType = AbstractBsonReader.this.currentBsonType;
            this.currentName = AbstractBsonReader.this.currentName;
        }

        public final BsonContextType getContextType() {
            return this.contextType;
        }

        public final Context getParentContext() {
            return this.parentContext;
        }

        @Override // org.bson.BsonReaderMark
        public void reset() {
            AbstractBsonReader.this.state = this.state;
            AbstractBsonReader.this.currentBsonType = this.currentBsonType;
            AbstractBsonReader.this.currentName = this.currentName;
        }
    }

    public enum State {
        INITIAL,
        TYPE,
        NAME,
        VALUE,
        SCOPE_DOCUMENT,
        END_OF_DOCUMENT,
        END_OF_ARRAY,
        DONE,
        CLOSED
    }

    private void setStateOnEnd() {
        int i = AnonymousClass1.$SwitchMap$org$bson$BsonContextType[getContext().getContextType().ordinal()];
        if (i == 1 || i == 2) {
            this.state = State.TYPE;
        } else {
            if (i != 4) {
                throw new BSONException(String.format("Unexpected ContextType %s.", getContext().getContextType()));
            }
            this.state = State.DONE;
        }
    }

    public final void checkPreconditions(String str, BsonType bsonType) {
        if (this.closed) {
            throw new IllegalStateException("BsonWriter is closed");
        }
        State state = this.state;
        if (state == State.INITIAL || state == State.SCOPE_DOCUMENT || state == State.TYPE) {
            readBsonType();
        }
        if (this.state == State.NAME) {
            skipName();
        }
        State state2 = this.state;
        State state3 = State.VALUE;
        if (state2 != state3) {
            throwInvalidState(str, state3);
            throw null;
        }
        if (this.currentBsonType != bsonType) {
            throw new BsonInvalidOperationException(String.format("%s can only be called when CurrentBSONType is %s, not when CurrentBSONType is %s.", str, bsonType, this.currentBsonType));
        }
    }

    @Override // org.bson.BsonReader, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.closed = true;
    }

    public abstract int doPeekBinarySize();

    public abstract byte doPeekBinarySubType();

    public abstract BsonBinary doReadBinaryData();

    public abstract boolean doReadBoolean();

    public abstract BsonDbPointer doReadDBPointer();

    public abstract long doReadDateTime();

    public abstract Decimal128 doReadDecimal128();

    public abstract double doReadDouble();

    public abstract void doReadEndArray();

    public abstract void doReadEndDocument();

    public abstract int doReadInt32();

    public abstract long doReadInt64();

    public abstract String doReadJavaScript();

    public abstract String doReadJavaScriptWithScope();

    public abstract void doReadMaxKey();

    public abstract void doReadMinKey();

    public abstract void doReadNull();

    public abstract ObjectId doReadObjectId();

    public abstract BsonRegularExpression doReadRegularExpression();

    public abstract void doReadStartArray();

    public abstract void doReadStartDocument();

    public abstract String doReadString();

    public abstract String doReadSymbol();

    public abstract BsonTimestamp doReadTimestamp();

    public abstract void doReadUndefined();

    public abstract void doSkipName();

    public abstract void doSkipValue();

    public Context getContext() {
        return this.context;
    }

    @Override // org.bson.BsonReader
    public BsonType getCurrentBsonType() {
        return this.currentBsonType;
    }

    @Override // org.bson.BsonReader
    public String getCurrentName() {
        State state = this.state;
        State state2 = State.VALUE;
        if (state == state2) {
            return this.currentName;
        }
        throwInvalidState("getCurrentName", state2);
        throw null;
    }

    public final State getNextState() {
        int i = AnonymousClass1.$SwitchMap$org$bson$BsonContextType[this.context.getContextType().ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            return State.TYPE;
        }
        if (i == 4) {
            return State.DONE;
        }
        throw new BSONException(String.format("Unexpected ContextType %s.", this.context.getContextType()));
    }

    public State getState() {
        return this.state;
    }

    public final boolean isClosed() {
        return this.closed;
    }

    @Override // org.bson.BsonReader
    public int peekBinarySize() {
        checkPreconditions("readBinaryData", BsonType.BINARY);
        return doPeekBinarySize();
    }

    @Override // org.bson.BsonReader
    public byte peekBinarySubType() {
        checkPreconditions("readBinaryData", BsonType.BINARY);
        return doPeekBinarySubType();
    }

    @Override // org.bson.BsonReader
    public BsonBinary readBinaryData() {
        checkPreconditions("readBinaryData", BsonType.BINARY);
        this.state = getNextState();
        return doReadBinaryData();
    }

    @Override // org.bson.BsonReader
    public boolean readBoolean() {
        checkPreconditions("readBoolean", BsonType.BOOLEAN);
        this.state = getNextState();
        return doReadBoolean();
    }

    @Override // org.bson.BsonReader
    public abstract BsonType readBsonType();

    @Override // org.bson.BsonReader
    public BsonDbPointer readDBPointer() {
        checkPreconditions("readDBPointer", BsonType.DB_POINTER);
        this.state = getNextState();
        return doReadDBPointer();
    }

    @Override // org.bson.BsonReader
    public long readDateTime() {
        checkPreconditions("readDateTime", BsonType.DATE_TIME);
        this.state = getNextState();
        return doReadDateTime();
    }

    @Override // org.bson.BsonReader
    public Decimal128 readDecimal128() {
        checkPreconditions("readDecimal", BsonType.DECIMAL128);
        this.state = getNextState();
        return doReadDecimal128();
    }

    @Override // org.bson.BsonReader
    public double readDouble() {
        checkPreconditions("readDouble", BsonType.DOUBLE);
        this.state = getNextState();
        return doReadDouble();
    }

    @Override // org.bson.BsonReader
    public void readEndArray() {
        if (this.closed) {
            throw new IllegalStateException("BSONBinaryWriter");
        }
        BsonContextType contextType = getContext().getContextType();
        BsonContextType bsonContextType = BsonContextType.ARRAY;
        if (contextType != bsonContextType) {
            throwInvalidContextType("readEndArray", getContext().getContextType(), bsonContextType);
            throw null;
        }
        if (getState() == State.TYPE) {
            readBsonType();
        }
        State state = getState();
        State state2 = State.END_OF_ARRAY;
        if (state != state2) {
            throwInvalidState("ReadEndArray", state2);
            throw null;
        }
        doReadEndArray();
        setStateOnEnd();
    }

    @Override // org.bson.BsonReader
    public void readEndDocument() {
        if (this.closed) {
            throw new IllegalStateException("BSONBinaryWriter");
        }
        BsonContextType contextType = getContext().getContextType();
        BsonContextType bsonContextType = BsonContextType.DOCUMENT;
        if (contextType != bsonContextType) {
            BsonContextType contextType2 = getContext().getContextType();
            BsonContextType bsonContextType2 = BsonContextType.SCOPE_DOCUMENT;
            if (contextType2 != bsonContextType2) {
                throwInvalidContextType("readEndDocument", getContext().getContextType(), bsonContextType, bsonContextType2);
                throw null;
            }
        }
        if (getState() == State.TYPE) {
            readBsonType();
        }
        State state = getState();
        State state2 = State.END_OF_DOCUMENT;
        if (state != state2) {
            throwInvalidState("readEndDocument", state2);
            throw null;
        }
        doReadEndDocument();
        setStateOnEnd();
    }

    @Override // org.bson.BsonReader
    public int readInt32() {
        checkPreconditions("readInt32", BsonType.INT32);
        this.state = getNextState();
        return doReadInt32();
    }

    @Override // org.bson.BsonReader
    public long readInt64() {
        checkPreconditions("readInt64", BsonType.INT64);
        this.state = getNextState();
        return doReadInt64();
    }

    @Override // org.bson.BsonReader
    public String readJavaScript() {
        checkPreconditions("readJavaScript", BsonType.JAVASCRIPT);
        this.state = getNextState();
        return doReadJavaScript();
    }

    @Override // org.bson.BsonReader
    public String readJavaScriptWithScope() {
        checkPreconditions("readJavaScriptWithScope", BsonType.JAVASCRIPT_WITH_SCOPE);
        this.state = State.SCOPE_DOCUMENT;
        return doReadJavaScriptWithScope();
    }

    @Override // org.bson.BsonReader
    public void readMaxKey() {
        checkPreconditions("readMaxKey", BsonType.MAX_KEY);
        this.state = getNextState();
        doReadMaxKey();
    }

    @Override // org.bson.BsonReader
    public void readMinKey() {
        checkPreconditions("readMinKey", BsonType.MIN_KEY);
        this.state = getNextState();
        doReadMinKey();
    }

    @Override // org.bson.BsonReader
    public String readName() {
        if (this.state == State.TYPE) {
            readBsonType();
        }
        State state = this.state;
        State state2 = State.NAME;
        if (state == state2) {
            this.state = State.VALUE;
            return this.currentName;
        }
        throwInvalidState("readName", state2);
        throw null;
    }

    @Override // org.bson.BsonReader
    public void readNull() {
        checkPreconditions("readNull", BsonType.NULL);
        this.state = getNextState();
        doReadNull();
    }

    @Override // org.bson.BsonReader
    public ObjectId readObjectId() {
        checkPreconditions("readObjectId", BsonType.OBJECT_ID);
        this.state = getNextState();
        return doReadObjectId();
    }

    @Override // org.bson.BsonReader
    public BsonRegularExpression readRegularExpression() {
        checkPreconditions("readRegularExpression", BsonType.REGULAR_EXPRESSION);
        this.state = getNextState();
        return doReadRegularExpression();
    }

    @Override // org.bson.BsonReader
    public void readStartArray() {
        checkPreconditions("readStartArray", BsonType.ARRAY);
        doReadStartArray();
        this.state = State.TYPE;
    }

    @Override // org.bson.BsonReader
    public void readStartDocument() {
        checkPreconditions("readStartDocument", BsonType.DOCUMENT);
        doReadStartDocument();
        this.state = State.TYPE;
    }

    @Override // org.bson.BsonReader
    public String readString() {
        checkPreconditions("readString", BsonType.STRING);
        this.state = getNextState();
        return doReadString();
    }

    @Override // org.bson.BsonReader
    public String readSymbol() {
        checkPreconditions("readSymbol", BsonType.SYMBOL);
        this.state = getNextState();
        return doReadSymbol();
    }

    @Override // org.bson.BsonReader
    public BsonTimestamp readTimestamp() {
        checkPreconditions("readTimestamp", BsonType.TIMESTAMP);
        this.state = getNextState();
        return doReadTimestamp();
    }

    @Override // org.bson.BsonReader
    public void readUndefined() {
        checkPreconditions("readUndefined", BsonType.UNDEFINED);
        this.state = getNextState();
        doReadUndefined();
    }

    public final void setContext(Context context) {
        this.context = context;
    }

    public final void setCurrentBsonType(BsonType bsonType) {
        this.currentBsonType = bsonType;
    }

    public final void setCurrentName(String str) {
        this.currentName = str;
    }

    public final void setState(State state) {
        this.state = state;
    }

    @Override // org.bson.BsonReader
    public void skipName() {
        if (this.closed) {
            throw new IllegalStateException("This instance has been closed");
        }
        State state = getState();
        State state2 = State.NAME;
        if (state != state2) {
            throwInvalidState("skipName", state2);
            throw null;
        }
        this.state = State.VALUE;
        doSkipName();
    }

    @Override // org.bson.BsonReader
    public void skipValue() {
        if (this.closed) {
            throw new IllegalStateException("BSONBinaryWriter");
        }
        State state = getState();
        State state2 = State.VALUE;
        if (state != state2) {
            throwInvalidState("skipValue", state2);
            throw null;
        }
        doSkipValue();
        this.state = State.TYPE;
    }

    public final void throwInvalidContextType(String str, BsonContextType bsonContextType, BsonContextType... bsonContextTypeArr) {
        throw new BsonInvalidOperationException(String.format("%s can only be called when ContextType is %s, not when ContextType is %s.", str, StringUtils.join(" or ", Arrays.asList(bsonContextTypeArr)), bsonContextType));
    }

    public final void throwInvalidState(String str, State... stateArr) {
        throw new BsonInvalidOperationException(String.format("%s can only be called when State is %s, not when State is %s.", str, StringUtils.join(" or ", Arrays.asList(stateArr)), this.state));
    }

    public final void verifyName(String str) {
        readBsonType();
        String name = readName();
        if (!name.equals(str)) {
            throw new BsonSerializationException(String.format("Expected element name to be '%s', not '%s'.", str, name));
        }
    }

    @Override // org.bson.BsonReader
    public BsonBinary readBinaryData(String str) {
        verifyName(str);
        return readBinaryData();
    }

    @Override // org.bson.BsonReader
    public boolean readBoolean(String str) {
        verifyName(str);
        return readBoolean();
    }

    @Override // org.bson.BsonReader
    public BsonDbPointer readDBPointer(String str) {
        verifyName(str);
        return readDBPointer();
    }

    @Override // org.bson.BsonReader
    public long readDateTime(String str) {
        verifyName(str);
        return readDateTime();
    }

    @Override // org.bson.BsonReader
    public Decimal128 readDecimal128(String str) {
        verifyName(str);
        return readDecimal128();
    }

    @Override // org.bson.BsonReader
    public double readDouble(String str) {
        verifyName(str);
        return readDouble();
    }

    @Override // org.bson.BsonReader
    public int readInt32(String str) {
        verifyName(str);
        return readInt32();
    }

    @Override // org.bson.BsonReader
    public long readInt64(String str) {
        verifyName(str);
        return readInt64();
    }

    @Override // org.bson.BsonReader
    public String readJavaScript(String str) {
        verifyName(str);
        return readJavaScript();
    }

    @Override // org.bson.BsonReader
    public String readJavaScriptWithScope(String str) {
        verifyName(str);
        return readJavaScriptWithScope();
    }

    @Override // org.bson.BsonReader
    public void readMaxKey(String str) {
        verifyName(str);
        readMaxKey();
    }

    @Override // org.bson.BsonReader
    public void readMinKey(String str) {
        verifyName(str);
        readMinKey();
    }

    @Override // org.bson.BsonReader
    public void readNull(String str) {
        verifyName(str);
        readNull();
    }

    @Override // org.bson.BsonReader
    public ObjectId readObjectId(String str) {
        verifyName(str);
        return readObjectId();
    }

    @Override // org.bson.BsonReader
    public BsonRegularExpression readRegularExpression(String str) {
        verifyName(str);
        return readRegularExpression();
    }

    @Override // org.bson.BsonReader
    public String readString(String str) {
        verifyName(str);
        return readString();
    }

    @Override // org.bson.BsonReader
    public String readSymbol(String str) {
        verifyName(str);
        return readSymbol();
    }

    @Override // org.bson.BsonReader
    public BsonTimestamp readTimestamp(String str) {
        verifyName(str);
        return readTimestamp();
    }

    @Override // org.bson.BsonReader
    public void readUndefined(String str) {
        verifyName(str);
        readUndefined();
    }

    @Override // org.bson.BsonReader
    public void readName(String str) {
        verifyName(str);
    }
}
