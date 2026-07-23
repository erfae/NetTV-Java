package org.bson.json;

import org.bson.BsonTimestamp;
import org.bson.internal.UnsignedLongs;

/* JADX INFO: loaded from: classes2.dex */
class ExtendedJsonTimestampConverter implements Converter<BsonTimestamp> {
    private long toUnsignedLong(int i) {
        return ((long) i) & 4294967295L;
    }

    @Override // org.bson.json.Converter
    public void convert(BsonTimestamp bsonTimestamp, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeStartObject("$timestamp");
        strictJsonWriter.writeNumber("t", UnsignedLongs.toString(toUnsignedLong(bsonTimestamp.getTime())));
        strictJsonWriter.writeNumber("i", UnsignedLongs.toString(toUnsignedLong(bsonTimestamp.getInc())));
        strictJsonWriter.writeEndObject();
        strictJsonWriter.writeEndObject();
    }
}
