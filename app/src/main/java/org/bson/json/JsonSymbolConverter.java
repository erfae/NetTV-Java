package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
class JsonSymbolConverter implements Converter<String> {
    @Override // org.bson.json.Converter
    public void convert(String str, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeString("$symbol", str);
        strictJsonWriter.writeEndObject();
    }
}
