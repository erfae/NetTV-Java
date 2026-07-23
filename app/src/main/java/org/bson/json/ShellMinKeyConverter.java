package org.bson.json;

import org.bson.BsonMinKey;

/* JADX INFO: loaded from: classes2.dex */
class ShellMinKeyConverter implements Converter<BsonMinKey> {
    @Override // org.bson.json.Converter
    public void convert(BsonMinKey bsonMinKey, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeRaw("MinKey");
    }
}
