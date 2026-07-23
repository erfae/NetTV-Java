package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
class JsonInt32Converter implements Converter<Integer> {
    @Override // org.bson.json.Converter
    public void convert(Integer num, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeNumber(Integer.toString(num.intValue()));
    }
}
