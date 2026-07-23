package org.bson.json;

import java.io.Writer;
import org.bson.AbstractBsonWriter;
import org.bson.BsonBinary;
import org.bson.BsonContextType;
import org.bson.BsonDbPointer;
import org.bson.BsonRegularExpression;
import org.bson.BsonTimestamp;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public class JsonWriter extends AbstractBsonWriter {
    private final JsonWriterSettings settings;
    private final StrictCharacterStreamJsonWriter strictJsonWriter;

    public class Context extends AbstractBsonWriter.Context {
        @Deprecated
        public Context(JsonWriter jsonWriter, Context context, BsonContextType bsonContextType, String str) {
            this(jsonWriter, context, bsonContextType);
        }

        public Context(JsonWriter jsonWriter, Context context, BsonContextType bsonContextType) {
            super(context, bsonContextType);
        }

        @Override // org.bson.AbstractBsonWriter.Context
        public Context getParentContext() {
            return (Context) super.getParentContext();
        }
    }

    public JsonWriter(Writer writer) {
        this(writer, new JsonWriterSettings());
    }

    @Override // org.bson.AbstractBsonWriter
    public final boolean abortPipe() {
        return this.strictJsonWriter.isTruncated();
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteBinaryData(BsonBinary bsonBinary) {
        this.settings.getBinaryConverter().convert(bsonBinary, this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteBoolean(boolean z) {
        this.settings.getBooleanConverter().convert(Boolean.valueOf(z), this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteDBPointer(BsonDbPointer bsonDbPointer) {
        if (this.settings.getOutputMode() == JsonMode.EXTENDED) {
            new Converter<BsonDbPointer>() { // from class: org.bson.json.JsonWriter.1
                @Override // org.bson.json.Converter
                public void convert(BsonDbPointer bsonDbPointer2, StrictJsonWriter strictJsonWriter) {
                    strictJsonWriter.writeStartObject();
                    strictJsonWriter.writeStartObject("$dbPointer");
                    strictJsonWriter.writeString("$ref", bsonDbPointer2.getNamespace());
                    strictJsonWriter.writeName("$id");
                    JsonWriter.this.doWriteObjectId(bsonDbPointer2.getId());
                    strictJsonWriter.writeEndObject();
                    strictJsonWriter.writeEndObject();
                }
            }.convert(bsonDbPointer, (StrictJsonWriter) this.strictJsonWriter);
        } else {
            new Converter<BsonDbPointer>() { // from class: org.bson.json.JsonWriter.2
                @Override // org.bson.json.Converter
                public void convert(BsonDbPointer bsonDbPointer2, StrictJsonWriter strictJsonWriter) {
                    strictJsonWriter.writeStartObject();
                    strictJsonWriter.writeString("$ref", bsonDbPointer2.getNamespace());
                    strictJsonWriter.writeName("$id");
                    JsonWriter.this.doWriteObjectId(bsonDbPointer2.getId());
                    strictJsonWriter.writeEndObject();
                }
            }.convert(bsonDbPointer, (StrictJsonWriter) this.strictJsonWriter);
        }
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteDateTime(long j) {
        this.settings.getDateTimeConverter().convert(Long.valueOf(j), this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteDecimal128(Decimal128 decimal128) {
        this.settings.getDecimal128Converter().convert(decimal128, this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteDouble(double d) {
        this.settings.getDoubleConverter().convert(Double.valueOf(d), this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteEndArray() {
        this.strictJsonWriter.writeEndArray();
        setContext(getContext().getParentContext());
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteEndDocument() {
        this.strictJsonWriter.writeEndObject();
        if (getContext().getContextType() != BsonContextType.SCOPE_DOCUMENT) {
            setContext(getContext().getParentContext());
        } else {
            setContext(getContext().getParentContext());
            writeEndDocument();
        }
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteInt32(int i) {
        this.settings.getInt32Converter().convert(Integer.valueOf(i), this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteInt64(long j) {
        this.settings.getInt64Converter().convert(Long.valueOf(j), this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteJavaScript(String str) {
        this.settings.getJavaScriptConverter().convert(str, this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteJavaScriptWithScope(String str) {
        writeStartDocument();
        writeString("$code", str);
        writeName("$scope");
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteMaxKey() {
        this.settings.getMaxKeyConverter().convert(null, this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteMinKey() {
        this.settings.getMinKeyConverter().convert(null, this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteName(String str) {
        this.strictJsonWriter.writeName(str);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteNull() {
        this.settings.getNullConverter().convert(null, this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteObjectId(ObjectId objectId) {
        this.settings.getObjectIdConverter().convert(objectId, this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteRegularExpression(BsonRegularExpression bsonRegularExpression) {
        this.settings.getRegularExpressionConverter().convert(bsonRegularExpression, this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteStartArray() {
        this.strictJsonWriter.writeStartArray();
        setContext(new Context(this, getContext(), BsonContextType.ARRAY));
    }

    @Override // org.bson.AbstractBsonWriter
    public final void doWriteStartDocument() {
        this.strictJsonWriter.writeStartObject();
        setContext(new Context(this, getContext(), getState() == AbstractBsonWriter.State.SCOPE_DOCUMENT ? BsonContextType.SCOPE_DOCUMENT : BsonContextType.DOCUMENT));
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteString(String str) {
        this.settings.getStringConverter().convert(str, this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteSymbol(String str) {
        this.settings.getSymbolConverter().convert(str, this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteTimestamp(BsonTimestamp bsonTimestamp) {
        this.settings.getTimestampConverter().convert(bsonTimestamp, this.strictJsonWriter);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteUndefined() {
        this.settings.getUndefinedConverter().convert(null, this.strictJsonWriter);
    }

    @Override // org.bson.BsonWriter
    public void flush() {
        this.strictJsonWriter.flush();
    }

    public Writer getWriter() {
        return this.strictJsonWriter.getWriter();
    }

    public boolean isTruncated() {
        return this.strictJsonWriter.isTruncated();
    }

    public JsonWriter(Writer writer, JsonWriterSettings jsonWriterSettings) {
        super(jsonWriterSettings);
        this.settings = jsonWriterSettings;
        setContext(new Context(this, null, BsonContextType.TOP_LEVEL));
        this.strictJsonWriter = new StrictCharacterStreamJsonWriter(writer, StrictCharacterStreamJsonWriterSettings.builder().indent(jsonWriterSettings.isIndent()).newLineCharacters(jsonWriterSettings.getNewLineCharacters()).indentCharacters(jsonWriterSettings.getIndentCharacters()).maxLength(jsonWriterSettings.getMaxLength()).build());
    }

    @Override // org.bson.AbstractBsonWriter
    public final Context getContext() {
        return (Context) super.getContext();
    }
}
