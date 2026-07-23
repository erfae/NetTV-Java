package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
class RelaxedExtendedJsonInt64Converter implements Converter<Long> {
    @Override // org.bson.json.Converter
    public void convert(Long l, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeNumber(Long.toString(l.longValue()));
    }
}
