package org.bson.codecs;

import org.bson.BsonWriter;

/* JADX INFO: loaded from: classes2.dex */
public interface Encoder<T> {
    void encode(BsonWriter bsonWriter, T t, EncoderContext encoderContext);

    Class<T> getEncoderClass();
}
