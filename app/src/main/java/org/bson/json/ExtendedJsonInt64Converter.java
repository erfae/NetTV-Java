package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
class ExtendedJsonInt64Converter implements Converter<Long> {
    @Override // org.bson.json.Converter
    public void convert(Long l, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeName("$numberLong");
        strictJsonWriter.writeString(Long.toString(l.longValue()));
        strictJsonWriter.writeEndObject();
    }
}
