package org.bson.json;

import org.bson.types.Decimal128;

/* JADX INFO: loaded from: classes2.dex */
class ExtendedJsonDecimal128Converter implements Converter<Decimal128> {
    @Override // org.bson.json.Converter
    public void convert(Decimal128 decimal128, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeStartObject();
        strictJsonWriter.writeName("$numberDecimal");
        strictJsonWriter.writeString(decimal128.toString());
        strictJsonWriter.writeEndObject();
    }
}
