package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes2.dex */
public class DoubleCodec implements Codec<Double> {
    @Override // org.bson.codecs.Encoder
    public Class<Double> getEncoderClass() {
        return Double.class;
    }

    @Override // org.bson.codecs.Decoder
    public Double decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return Double.valueOf(NumberCodecHelper.decodeDouble(bsonReader));
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, Double d, EncoderContext encoderContext) {
        bsonWriter.writeDouble(d.doubleValue());
    }
}
