package org.bson.json;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import org.bson.BsonMinKey;

/* JADX INFO: loaded from: classes2.dex */
class ExtendedJsonMinKeyConverter implements Converter<BsonMinKey> {
    @Override // org.bson.json.Converter
    public void convert(BsonMinKey bsonMinKey, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeNumber("$minKey", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
        strictJsonWriter.writeEndObject();
    }
}
