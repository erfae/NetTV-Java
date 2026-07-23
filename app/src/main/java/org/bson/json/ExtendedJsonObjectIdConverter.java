package org.bson.json;

import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
class ExtendedJsonObjectIdConverter implements Converter<ObjectId> {
    @Override // org.bson.json.Converter
    public void convert(ObjectId objectId, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeString("$oid", objectId.toHexString());
        strictJsonWriter.writeEndObject();
    }
}
