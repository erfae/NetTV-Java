package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
class ExtendedJsonDateTimeConverter implements Converter<Long> {
    @Override // org.bson.json.Converter
    public void convert(Long l, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeStartObject("$date");
        strictJsonWriter.writeString("$numberLong", Long.toString(l.longValue()));
        strictJsonWriter.writeEndObject();
        strictJsonWriter.writeEndObject();
    }
}
