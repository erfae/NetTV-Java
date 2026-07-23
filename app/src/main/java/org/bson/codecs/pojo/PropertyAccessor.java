package org.bson.codecs.pojo;

/* JADX INFO: loaded from: classes2.dex */
public interface PropertyAccessor<T> {
    <S> T get(S s);

    <S> void set(S s, T t);
}
