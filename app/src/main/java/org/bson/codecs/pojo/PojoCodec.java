package org.bson.codecs.pojo;

import org.bson.codecs.Codec;

/* JADX INFO: loaded from: classes2.dex */
abstract class PojoCodec<T> implements Codec<T> {
    public abstract ClassModel<T> getClassModel();
}
