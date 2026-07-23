package org.bson.json;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.io.Writer;
import kotlin.text.Typography;
import org.bson.BSONException;
import org.bson.BsonInvalidOperationException;
import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes2.dex */
public final class StrictCharacterStreamJsonWriter implements StrictJsonWriter {
    private int curLength;
    private boolean isTruncated;
    private final StrictCharacterStreamJsonWriterSettings settings;
    private final Writer writer;
    private StrictJsonContext context = new StrictJsonContext(null, JsonContextType.TOP_LEVEL, "");
    private State state = State.INITIAL;

    public enum JsonContextType {
        TOP_LEVEL,
        DOCUMENT,
        ARRAY
    }

    public enum State {
        INITIAL,
        NAME,
        VALUE,
        DONE
    }

    public static class StrictJsonContext {
        private final JsonContextType contextType;
        private boolean hasElements;
        private final String indentation;
        private final StrictJsonContext parentContext;

        public StrictJsonContext(StrictJsonContext strictJsonContext, JsonContextType jsonContextType, String str) {
            this.parentContext = strictJsonContext;
            this.contextType = jsonContextType;
            this.indentation = strictJsonContext != null ? Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), strictJsonContext.indentation, str) : str;
        }
    }

    public StrictCharacterStreamJsonWriter(Writer writer, StrictCharacterStreamJsonWriterSettings strictCharacterStreamJsonWriterSettings) {
        this.writer = writer;
        this.settings = strictCharacterStreamJsonWriterSettings;
    }

    private void checkState(State state) {
        if (this.state == state) {
            return;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Invalid state ");
        sbM.append(this.state);
        throw new BsonInvalidOperationException(sbM.toString());
    }

    private void preWriteValue() {
        if (this.context.contextType == JsonContextType.ARRAY) {
            if (this.context.hasElements) {
                write(",");
            }
            if (this.settings.isIndent()) {
                write(this.settings.getNewLineCharacters());
                write(this.context.indentation);
            } else if (this.context.hasElements) {
                write(" ");
            }
        }
        this.context.hasElements = true;
    }

    private void setNextState() {
        if (this.context.contextType == JsonContextType.ARRAY) {
            this.state = State.VALUE;
        } else {
            this.state = State.NAME;
        }
    }

    private void throwBSONException(IOException iOException) {
        throw new BSONException("Wrapping IOException", iOException);
    }

    private void write(String str) {
        try {
            if (this.settings.getMaxLength() == 0 || str.length() + this.curLength < this.settings.getMaxLength()) {
                this.writer.write(str);
                this.curLength += str.length();
            } else {
                this.writer.write(str.substring(0, this.settings.getMaxLength() - this.curLength));
                this.curLength = this.settings.getMaxLength();
                this.isTruncated = true;
            }
        } catch (IOException e) {
            throwBSONException(e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0069 A[FALL_THROUGH] */
    /* JADX WARN: Switch 'out' block B:24:0x0069 for B:21:0x0031 already processed. Defaulting to fallback option. */
    private void writeStringHelper(String str) {
        write(Typography.quote);
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\f') {
                write("\\f");
            } else if (cCharAt == '\r') {
                write("\\r");
            } else if (cCharAt == '\"') {
                write("\\\"");
            } else if (cCharAt != '\\') {
                switch (cCharAt) {
                    case '\b':
                        write("\\b");
                        break;
                    case '\t':
                        write("\\t");
                        break;
                    case '\n':
                        write("\\n");
                        break;
                    default:
                        int type = Character.getType(cCharAt);
                        if (type != 1 && type != 2 && type != 3 && type != 5) {
                            switch (type) {
                                default:
                                    switch (type) {
                                        case 20:
                                        case 21:
                                        case 22:
                                        case 23:
                                        case 24:
                                        case 25:
                                        case 26:
                                        case 27:
                                        case 28:
                                        case 29:
                                        case 30:
                                            break;
                                        default:
                                            write("\\u");
                                            write(Integer.toHexString((61440 & cCharAt) >> 12));
                                            write(Integer.toHexString((cCharAt & 3840) >> 8));
                                            write(Integer.toHexString((cCharAt & 240) >> 4));
                                            write(Integer.toHexString(cCharAt & 15));
                                            continue;
                                    }
                                case 9:
                                case 10:
                                case 11:
                                case 12:
                                    write(cCharAt);
                                    break;
                            }
                        } else {
                            write(cCharAt);
                            break;
                        }
                        break;
                }
            } else {
                write("\\\\");
            }
        }
        write(Typography.quote);
    }

    public final void flush() {
        try {
            this.writer.flush();
        } catch (IOException e) {
            throwBSONException(e);
        }
    }

    public int getCurrentLength() {
        return this.curLength;
    }

    public final Writer getWriter() {
        return this.writer;
    }

    @Override // org.bson.json.StrictJsonWriter
    public boolean isTruncated() {
        return this.isTruncated;
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeBoolean(String str, boolean z) {
        Assertions.notNull("name", str);
        writeName(str);
        writeBoolean(z);
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeEndArray() {
        checkState(State.VALUE);
        if (this.context.contextType != JsonContextType.ARRAY) {
            throw new BsonInvalidOperationException("Can't end an array if not in an array");
        }
        if (this.settings.isIndent() && this.context.hasElements) {
            write(this.settings.getNewLineCharacters());
            write(this.context.parentContext.indentation);
        }
        write("]");
        StrictJsonContext strictJsonContext = this.context.parentContext;
        this.context = strictJsonContext;
        if (strictJsonContext.contextType == JsonContextType.TOP_LEVEL) {
            this.state = State.DONE;
        } else {
            setNextState();
        }
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeEndObject() {
        checkState(State.NAME);
        if (this.settings.isIndent() && this.context.hasElements) {
            write(this.settings.getNewLineCharacters());
            write(this.context.parentContext.indentation);
        }
        write("}");
        StrictJsonContext strictJsonContext = this.context.parentContext;
        this.context = strictJsonContext;
        if (strictJsonContext.contextType == JsonContextType.TOP_LEVEL) {
            this.state = State.DONE;
        } else {
            setNextState();
        }
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeName(String str) {
        Assertions.notNull("name", str);
        checkState(State.NAME);
        if (this.context.hasElements) {
            write(",");
        }
        if (this.settings.isIndent()) {
            write(this.settings.getNewLineCharacters());
            write(this.context.indentation);
        } else if (this.context.hasElements) {
            write(" ");
        }
        writeStringHelper(str);
        write(": ");
        this.state = State.VALUE;
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeNull(String str) {
        writeName(str);
        writeNull();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeNumber(String str, String str2) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", str2);
        writeName(str);
        writeNumber(str2);
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeRaw(String str, String str2) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", str2);
        writeName(str);
        writeRaw(str2);
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeStartArray(String str) {
        writeName(str);
        writeStartArray();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeStartObject(String str) {
        writeName(str);
        writeStartObject();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeString(String str, String str2) {
        Assertions.notNull("name", str);
        Assertions.notNull("value", str2);
        writeName(str);
        writeString(str2);
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeNull() {
        checkState(State.VALUE);
        preWriteValue();
        write("null");
        setNextState();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeStartArray() {
        preWriteValue();
        write("[");
        this.context = new StrictJsonContext(this.context, JsonContextType.ARRAY, this.settings.getIndentCharacters());
        this.state = State.VALUE;
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeStartObject() {
        State state = this.state;
        if (state != State.INITIAL && state != State.VALUE) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Invalid state ");
            sbM.append(this.state);
            throw new BsonInvalidOperationException(sbM.toString());
        }
        preWriteValue();
        write("{");
        this.context = new StrictJsonContext(this.context, JsonContextType.DOCUMENT, this.settings.getIndentCharacters());
        this.state = State.NAME;
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeBoolean(boolean z) {
        checkState(State.VALUE);
        preWriteValue();
        write(z ? "true" : "false");
        setNextState();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeNumber(String str) {
        Assertions.notNull("value", str);
        checkState(State.VALUE);
        preWriteValue();
        write(str);
        setNextState();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeRaw(String str) {
        Assertions.notNull("value", str);
        checkState(State.VALUE);
        preWriteValue();
        write(str);
        setNextState();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeString(String str) {
        Assertions.notNull("value", str);
        checkState(State.VALUE);
        preWriteValue();
        writeStringHelper(str);
        setNextState();
    }

    private void write(char c) {
        try {
            if (this.settings.getMaxLength() != 0 && this.curLength >= this.settings.getMaxLength()) {
                this.isTruncated = true;
            } else {
                this.writer.write(c);
                this.curLength++;
            }
        } catch (IOException e) {
            throwBSONException(e);
        }
    }
}
