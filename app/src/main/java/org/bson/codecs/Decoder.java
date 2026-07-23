package org.bson.codecs;

import org.bson.BsonReader;

/* JADX INFO: loaded from: classes2.dex */
public interface Decoder<T> {
    T decode(BsonReader bsonReader, DecoderContext decoderContext);
}
