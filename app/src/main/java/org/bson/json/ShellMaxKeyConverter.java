package org.bson.json;

import org.bson.BsonMaxKey;

/* JADX INFO: loaded from: classes2.dex */
class ShellMaxKeyConverter implements Converter<BsonMaxKey> {
    @Override // org.bson.json.Converter
    public void convert(BsonMaxKey bsonMaxKey, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeRaw("MaxKey");
    }
}
