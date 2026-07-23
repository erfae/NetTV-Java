package org.bson.json;

import org.bson.BsonNull;

/* JADX INFO: loaded from: classes2.dex */
class JsonNullConverter implements Converter<BsonNull> {
    @Override // org.bson.json.Converter
    public void convert(BsonNull bsonNull, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeNull();
    }
}
