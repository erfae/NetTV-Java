package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
class JsonDoubleConverter implements Converter<Double> {
    @Override // org.bson.json.Converter
    public void convert(Double d, StrictJsonWriter strictJsonWriter) {
        strictJsonWriter.writeNumber(Double.toString(d.doubleValue()));
    }
}
