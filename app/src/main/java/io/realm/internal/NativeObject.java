package io.realm.internal;

/* JADX INFO: loaded from: classes2.dex */
public interface NativeObject {
    public static final long NULLPTR = 0;

    long getNativeFinalizerPtr();

    long getNativePtr();
}
