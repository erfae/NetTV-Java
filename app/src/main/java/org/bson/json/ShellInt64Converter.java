package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
class ShellInt64Converter implements Converter<Long> {
    @Override // org.bson.json.Converter
    public void convert(Long l, StrictJsonWriter strictJsonWriter) {
        if (l.longValue() < -2147483648L || l.longValue() > 2147483647L) {
            strictJsonWriter.writeRaw(String.format("NumberLong(\"%d\")", l));
        } else {
            strictJsonWriter.writeRaw(String.format("NumberLong(%d)", l));
        }
    }
}
