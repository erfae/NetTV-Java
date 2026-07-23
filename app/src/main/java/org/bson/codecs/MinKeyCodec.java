package org.bson.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.types.MinKey;

/* JADX INFO: loaded from: classes2.dex */
public class MinKeyCodec implements Codec<MinKey> {
    @Override // org.bson.codecs.Encoder
    public Class<MinKey> getEncoderClass() {
        return MinKey.class;
    }

    @Override // org.bson.codecs.Decoder
    public MinKey decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readMinKey();
        return new MinKey();
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, MinKey minKey, EncoderContext encoderContext) {
        bsonWriter.writeMinKey();
    }
}
