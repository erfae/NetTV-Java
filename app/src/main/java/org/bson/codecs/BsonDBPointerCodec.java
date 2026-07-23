package org.bson.codecs;

import org.bson.BsonDbPointer;
import org.bson.BsonReader;
import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes2.dex */
public class BsonDBPointerCodec implements Codec<BsonDbPointer> {
    @Override // org.bson.codecs.Encoder
    public Class<BsonDbPointer> getEncoderClass() {
        return BsonDbPointer.class;
    }

    @Override // org.bson.codecs.Decoder
    public BsonDbPointer decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return bsonReader.readDBPointer();
    }

    @Override // org.bson.codecs.Encoder
    public void encode(BsonWriter bsonWriter, BsonDbPointer bsonDbPointer, EncoderContext encoderContext) {
        bsonWriter.writeDBPointer(bsonDbPointer);
    }
}
