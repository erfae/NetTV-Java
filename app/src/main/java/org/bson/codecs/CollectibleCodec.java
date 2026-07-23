package org.bson.codecs;

import org.bson.BsonValue;

/* JADX INFO: loaded from: classes2.dex */
public interface CollectibleCodec<T> extends Codec<T> {
    boolean documentHasId(T t);

    T generateIdIfAbsentFromDocument(T t);

    BsonValue getDocumentId(T t);
}
