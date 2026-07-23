package org.bson.conversions;

import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes2.dex */
public interface Bson {
    <TDocument> BsonDocument toBsonDocument(Class<TDocument> cls, CodecRegistry codecRegistry);
}
