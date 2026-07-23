package org.bson.json;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import org.bson.BsonMaxKey;

/* JADX INFO: loaded from: classes2.dex */
class ExtendedJsonMaxKeyConverter implements Converter<BsonMaxKey> {
    @Override // org.bson.json.Converter
    public void convert(BsonMaxKey bsonMaxKey, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeNumber("$maxKey", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
        strictJsonWriter.writeEndObject();
    }
}
