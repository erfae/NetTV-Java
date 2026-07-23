package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public class BsonDocumentWriter extends AbstractBsonWriter {
    private final BsonDocument document;

    /* JADX INFO: renamed from: org.bson.BsonDocumentWriter$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$org$bson$AbstractBsonWriter$State;

        static {
            int[] iArr = new int[AbstractBsonWriter.State.values().length];
            $SwitchMap$org$bson$AbstractBsonWriter$State = iArr;
            try {
                iArr[AbstractBsonWriter.State.INITIAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$AbstractBsonWriter$State[AbstractBsonWriter.State.VALUE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$AbstractBsonWriter$State[AbstractBsonWriter.State.SCOPE_DOCUMENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public BsonDocumentWriter(BsonDocument bsonDocument) {
        super(new BsonWriterSettings());
        this.document = bsonDocument;
        setContext(new Context());
    }

    private void write(BsonValue bsonValue) {
        getContext().add(bsonValue);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteBinaryData(BsonBinary bsonBinary) {
        write(bsonBinary);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteBoolean(boolean z) {
        write(BsonBoolean.valueOf(z));
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteDBPointer(BsonDbPointer bsonDbPointer) {
        write(bsonDbPointer);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteDateTime(long j) {
        write(new BsonDateTime(j));
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteDecimal128(Decimal128 decimal128) {
        write(new BsonDecimal128(decimal128));
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteDouble(double d) {
        write(new BsonDouble(d));
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteEndArray() {
        BsonValue bsonValue = getContext().container;
        setContext(getContext().getParentContext());
        write(bsonValue);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteEndDocument() {
        BsonValue bsonValue = getContext().container;
        setContext(getContext().getParentContext());
        if (getContext().getContextType() != BsonContextType.JAVASCRIPT_WITH_SCOPE) {
            if (getContext().getContextType() != BsonContextType.TOP_LEVEL) {
                write(bsonValue);
            }
        } else {
            BsonString bsonString = (BsonString) getContext().container;
            setContext(getContext().getParentContext());
            write(new BsonJavaScriptWithScope(bsonString.getValue(), (BsonDocument) bsonValue));
        }
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteInt32(int i) {
        write(new BsonInt32(i));
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteInt64(long j) {
        write(new BsonInt64(j));
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteJavaScript(String str) {
        write(new BsonJavaScript(str));
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteJavaScriptWithScope(String str) {
        setContext(new Context(new BsonString(str), BsonContextType.JAVASCRIPT_WITH_SCOPE, getContext()));
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteMaxKey() {
        write(new BsonMaxKey());
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteMinKey() {
        write(new BsonMinKey());
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteNull() {
        write(BsonNull.VALUE);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteObjectId(ObjectId objectId) {
        write(new BsonObjectId(objectId));
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteRegularExpression(BsonRegularExpression bsonRegularExpression) {
        write(bsonRegularExpression);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteStartArray() {
        setContext(new Context(new BsonArray(), BsonContextType.ARRAY, getContext()));
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteStartDocument() {
        int i = AnonymousClass1.$SwitchMap$org$bson$AbstractBsonWriter$State[getState().ordinal()];
        if (i == 1) {
            setContext(new Context(this.document, BsonContextType.DOCUMENT, getContext()));
            return;
        }
        if (i == 2) {
            setContext(new Context(new BsonDocument(), BsonContextType.DOCUMENT, getContext()));
        } else if (i == 3) {
            setContext(new Context(new BsonDocument(), BsonContextType.SCOPE_DOCUMENT, getContext()));
        } else {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Unexpected state ");
            sbM.append(getState());
            throw new BsonInvalidOperationException(sbM.toString());
        }
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteString(String str) {
        write(new BsonString(str));
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteSymbol(String str) {
        write(new BsonSymbol(str));
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteTimestamp(BsonTimestamp bsonTimestamp) {
        write(bsonTimestamp);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteUndefined() {
        write(new BsonUndefined());
    }

    @Override // org.bson.BsonWriter
    public void flush() {
    }

    public BsonDocument getDocument() {
        return this.document;
    }

    @Override // org.bson.AbstractBsonWriter
    public final Context getContext() {
        return (Context) super.getContext();
    }

    public class Context extends AbstractBsonWriter.Context {
        private BsonValue container;

        public Context(BsonValue bsonValue, BsonContextType bsonContextType, Context context) {
            super(context, bsonContextType);
            this.container = bsonValue;
        }

        public final void add(BsonValue bsonValue) {
            BsonValue bsonValue2 = this.container;
            if (bsonValue2 instanceof BsonArray) {
                ((BsonArray) bsonValue2).add(bsonValue);
            } else {
                ((BsonDocument) bsonValue2).put(BsonDocumentWriter.this.getName(), bsonValue);
            }
        }

        public Context() {
            super(null, BsonContextType.TOP_LEVEL);
        }
    }
}
