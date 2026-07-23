package org.bson.codecs.jsr310;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes2.dex */
abstract class DateTimeBasedCodec<T> implements Codec<T> {
    public final long validateAndReadDateTime(BsonReader bsonReader) {
        BsonType currentBsonType = bsonReader.getCurrentBsonType();
        BsonType bsonType = BsonType.DATE_TIME;
        if (currentBsonType.equals(bsonType)) {
            return bsonReader.readDateTime();
        }
        throw new CodecConfigurationException(String.format("Could not decode into %s, expected '%s' BsonType but got '%s'.", getEncoderClass().getSimpleName(), bsonType, currentBsonType));
    }
}
