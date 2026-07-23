package org.bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public class BsonDocumentReader extends AbstractBsonReader {
    private BsonValue currentValue;
    private Mark mark;

    /* JADX INFO: renamed from: org.bson.BsonDocumentReader$1, reason: invalid class name */
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
                $SwitchMap$org$bson$BsonContextType[BsonContextType.TOP_LEVEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static class BsonDocumentMarkableIterator<T> implements Iterator<T> {
        private Iterator<T> baseIterator;
        private List<T> markIterator = new ArrayList();
        private int curIndex = 0;
        private boolean marking = false;

        public BsonDocumentMarkableIterator(Iterator<T> it) {
            this.baseIterator = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.baseIterator.hasNext() || this.curIndex < this.markIterator.size();
        }

        public final void mark() {
            this.marking = true;
        }

        @Override // java.util.Iterator
        public T next() {
            T next;
            if (this.curIndex < this.markIterator.size()) {
                next = this.markIterator.get(this.curIndex);
                if (this.marking) {
                    this.curIndex++;
                } else {
                    this.markIterator.remove(0);
                }
            } else {
                next = this.baseIterator.next();
                if (this.marking) {
                    this.markIterator.add(next);
                    this.curIndex++;
                }
            }
            return next;
        }

        @Override // java.util.Iterator
        public void remove() {
        }

        public final void reset() {
            this.curIndex = 0;
            this.marking = false;
        }
    }

    public class Mark extends AbstractBsonReader.Mark {
        private final Context context;
        private final BsonValue currentValue;

        public Mark() {
            super();
            this.currentValue = BsonDocumentReader.this.currentValue;
            Context context = BsonDocumentReader.this.getContext();
            this.context = context;
            context.mark();
        }

        @Override // org.bson.AbstractBsonReader.Mark, org.bson.BsonReaderMark
        public void reset() {
            super.reset();
            BsonDocumentReader.this.currentValue = this.currentValue;
            BsonDocumentReader.this.setContext(this.context);
            this.context.reset();
        }
    }

    public BsonDocumentReader(BsonDocument bsonDocument) {
        setContext(new Context(this, (Context) null, BsonContextType.TOP_LEVEL, bsonDocument));
        this.currentValue = bsonDocument;
    }

    @Override // org.bson.AbstractBsonReader
    public final int doPeekBinarySize() {
        return this.currentValue.asBinary().getData().length;
    }

    @Override // org.bson.AbstractBsonReader
    public final byte doPeekBinarySubType() {
        return this.currentValue.asBinary().getType();
    }

    @Override // org.bson.AbstractBsonReader
    public final BsonBinary doReadBinaryData() {
        return this.currentValue.asBinary();
    }

    @Override // org.bson.AbstractBsonReader
    public final boolean doReadBoolean() {
        return this.currentValue.asBoolean().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    public final BsonDbPointer doReadDBPointer() {
        return this.currentValue.asDBPointer();
    }

    @Override // org.bson.AbstractBsonReader
    public final long doReadDateTime() {
        return this.currentValue.asDateTime().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    public Decimal128 doReadDecimal128() {
        return this.currentValue.asDecimal128().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    public final double doReadDouble() {
        return this.currentValue.asDouble().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadEndArray() {
        setContext(getContext().getParentContext());
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadEndDocument() {
        setContext(getContext().getParentContext());
        int i = AnonymousClass1.$SwitchMap$org$bson$BsonContextType[getContext().getContextType().ordinal()];
        if (i == 1 || i == 2) {
            setState(AbstractBsonReader.State.TYPE);
        } else {
            if (i != 3) {
                throw new BSONException("Unexpected ContextType.");
            }
            setState(AbstractBsonReader.State.DONE);
        }
    }

    @Override // org.bson.AbstractBsonReader
    public final int doReadInt32() {
        return this.currentValue.asInt32().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    public final long doReadInt64() {
        return this.currentValue.asInt64().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    public final String doReadJavaScript() {
        return this.currentValue.asJavaScript().getCode();
    }

    @Override // org.bson.AbstractBsonReader
    public final String doReadJavaScriptWithScope() {
        return this.currentValue.asJavaScriptWithScope().getCode();
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
        return this.currentValue.asObjectId().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    public final BsonRegularExpression doReadRegularExpression() {
        return this.currentValue.asRegularExpression();
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadStartArray() {
        setContext(new Context(this, getContext(), BsonContextType.ARRAY, this.currentValue.asArray()));
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadStartDocument() {
        setContext(new Context(this, getContext(), BsonContextType.DOCUMENT, this.currentValue.getBsonType() == BsonType.JAVASCRIPT_WITH_SCOPE ? this.currentValue.asJavaScriptWithScope().getScope() : this.currentValue.asDocument()));
    }

    @Override // org.bson.AbstractBsonReader
    public final String doReadString() {
        return this.currentValue.asString().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    public final String doReadSymbol() {
        return this.currentValue.asSymbol().getSymbol();
    }

    @Override // org.bson.AbstractBsonReader
    public final BsonTimestamp doReadTimestamp() {
        return this.currentValue.asTimestamp();
    }

    @Override // org.bson.AbstractBsonReader
    public final void doReadUndefined() {
    }

    @Override // org.bson.AbstractBsonReader
    public final void doSkipName() {
    }

    @Override // org.bson.AbstractBsonReader
    public final void doSkipValue() {
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
        if (getState() == AbstractBsonReader.State.INITIAL || getState() == AbstractBsonReader.State.SCOPE_DOCUMENT) {
            setCurrentBsonType(BsonType.DOCUMENT);
            setState(AbstractBsonReader.State.VALUE);
            return getCurrentBsonType();
        }
        AbstractBsonReader.State state = getState();
        AbstractBsonReader.State state2 = AbstractBsonReader.State.TYPE;
        if (state != state2) {
            throwInvalidState("ReadBSONType", state2);
            throw null;
        }
        int i = AnonymousClass1.$SwitchMap$org$bson$BsonContextType[getContext().getContextType().ordinal()];
        if (i == 1) {
            BsonValue nextValue = getContext().getNextValue();
            this.currentValue = nextValue;
            if (nextValue == null) {
                setState(AbstractBsonReader.State.END_OF_ARRAY);
                return BsonType.END_OF_DOCUMENT;
            }
            setState(AbstractBsonReader.State.VALUE);
        } else {
            if (i != 2) {
                throw new BSONException("Invalid ContextType.");
            }
            Map.Entry<String, BsonValue> nextElement = getContext().getNextElement();
            if (nextElement == null) {
                setState(AbstractBsonReader.State.END_OF_DOCUMENT);
                return BsonType.END_OF_DOCUMENT;
            }
            setCurrentName(nextElement.getKey());
            this.currentValue = nextElement.getValue();
            setState(AbstractBsonReader.State.NAME);
        }
        setCurrentBsonType(this.currentValue.getBsonType());
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

    public class Context extends AbstractBsonReader.Context {
        private BsonDocumentMarkableIterator<BsonValue> arrayIterator;
        private BsonDocumentMarkableIterator<Map.Entry<String, BsonValue>> documentIterator;

        public Context(BsonDocumentReader bsonDocumentReader, Context context, BsonContextType bsonContextType, BsonArray bsonArray) {
            super(context, bsonContextType);
            this.arrayIterator = new BsonDocumentMarkableIterator<>(bsonArray.iterator());
        }

        public Map.Entry<String, BsonValue> getNextElement() {
            if (this.documentIterator.hasNext()) {
                return this.documentIterator.next();
            }
            return null;
        }

        public BsonValue getNextValue() {
            if (this.arrayIterator.hasNext()) {
                return this.arrayIterator.next();
            }
            return null;
        }

        public final void mark() {
            BsonDocumentMarkableIterator<Map.Entry<String, BsonValue>> bsonDocumentMarkableIterator = this.documentIterator;
            if (bsonDocumentMarkableIterator != null) {
                bsonDocumentMarkableIterator.mark();
            } else {
                this.arrayIterator.mark();
            }
            if (getParentContext() != null) {
                ((Context) getParentContext()).mark();
            }
        }

        public final void reset() {
            BsonDocumentMarkableIterator<Map.Entry<String, BsonValue>> bsonDocumentMarkableIterator = this.documentIterator;
            if (bsonDocumentMarkableIterator != null) {
                bsonDocumentMarkableIterator.reset();
            } else {
                this.arrayIterator.reset();
            }
            if (getParentContext() != null) {
                ((Context) getParentContext()).reset();
            }
        }

        public Context(BsonDocumentReader bsonDocumentReader, Context context, BsonContextType bsonContextType, BsonDocument bsonDocument) {
            super(context, bsonContextType);
            this.documentIterator = new BsonDocumentMarkableIterator<>(bsonDocument.entrySet().iterator());
        }
    }

    @Override // org.bson.AbstractBsonReader
    public final Context getContext() {
        return (Context) super.getContext();
    }
}
