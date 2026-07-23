package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
class JsonBooleanConverter implements Converter<Boolean> {
    @Override // org.bson.json.Converter
    public void convert(Boolean bool, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeBoolean(bool.booleanValue());
    }
}
