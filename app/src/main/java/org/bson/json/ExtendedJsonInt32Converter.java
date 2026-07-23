package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
class ExtendedJsonInt32Converter implements Converter<Integer> {
    @Override // org.bson.json.Converter
    public void convert(Integer num, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeName("$numberInt");
        strictJsonWriter.writeString(Integer.toString(num.intValue()));
        strictJsonWriter.writeEndObject();
    }
}
