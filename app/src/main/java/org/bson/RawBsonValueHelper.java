package org.bson;

import org.bson.codecs.BsonValueCodecProvider;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.io.BsonInputMark;

/* JADX INFO: loaded from: classes2.dex */
final class RawBsonValueHelper {
    private static final CodecRegistry REGISTRY = CodecRegistries.fromProviders(new BsonValueCodecProvider());

    private RawBsonValueHelper() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static BsonValue decode(byte[] bArr, BsonBinaryReader bsonBinaryReader) {
        BsonType currentBsonType = bsonBinaryReader.getCurrentBsonType();
        BsonType bsonType = BsonType.DOCUMENT;
        if (currentBsonType != bsonType && bsonBinaryReader.getCurrentBsonType() != BsonType.ARRAY) {
            return (BsonValue) REGISTRY.get(BsonValueCodecProvider.getClassForBsonType(bsonBinaryReader.getCurrentBsonType())).decode(bsonBinaryReader, DecoderContext.builder().build());
        }
        int position = bsonBinaryReader.getBsonInput().getPosition();
        BsonInputMark mark = bsonBinaryReader.getBsonInput().getMark(4);
        int int32 = bsonBinaryReader.getBsonInput().readInt32();
        mark.reset();
        bsonBinaryReader.skipValue();
        return bsonBinaryReader.getCurrentBsonType() == bsonType ? new RawBsonDocument(bArr, position, int32) : new RawBsonArray(bArr, position, int32);
    }
}
