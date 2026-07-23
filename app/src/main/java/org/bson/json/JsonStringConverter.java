package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
class JsonStringConverter implements Converter<String> {
    @Override // org.bson.json.Converter
    public void convert(String str, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeString(str);
    }
}
