package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
class JsonJavaScriptConverter implements Converter<String> {
    @Override // org.bson.json.Converter
    public void convert(String str, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeString("$code", str);
        strictJsonWriter.writeEndObject();
    }
}
