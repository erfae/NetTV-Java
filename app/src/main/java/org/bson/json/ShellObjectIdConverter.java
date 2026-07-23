package org.bson.json;

import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
class ShellObjectIdConverter implements Converter<ObjectId> {
    @Override // org.bson.json.Converter
    public void convert(ObjectId objectId, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeRaw(String.format("ObjectId(\"%s\")", objectId.toHexString()));
    }
}
