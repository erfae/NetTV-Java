package org.bson.json;

/* JADX INFO: loaded from: classes2.dex */
public interface Converter<T> {
    void convert(T t, StrictJsonWriter strictJsonWriter);
}
