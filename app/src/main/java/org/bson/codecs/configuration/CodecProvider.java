package org.bson.codecs.configuration;

import org.bson.codecs.Codec;

/* JADX INFO: loaded from: classes2.dex */
public interface CodecProvider {
    <T> Codec<T> get(Class<T> cls, CodecRegistry codecRegistry);
}
