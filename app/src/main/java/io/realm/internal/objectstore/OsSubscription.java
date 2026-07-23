package io.realm.internal.objectstore;

import io.realm.internal.NativeObject;
import io.realm.mongodb.sync.Subscription;
import java.util.Date;

/* JADX INFO: loaded from: classes2.dex */
public class OsSubscription implements NativeObject, Subscription {
    private static final long nativeFinalizerPtr = nativeGetFinalizerMethodPtr();
    private final long nativePtr;

    public OsSubscription(long j) {
        this.nativePtr = j;
    }

    private static native long nativeCreatedAt(long j);

    private static native long nativeGetFinalizerMethodPtr();

    private static native String nativeName(long j);

    private static native String nativeObjectClassName(long j);

    private static native String nativeQueryString(long j);

    private static native long nativeUpdatedAt(long j);

    public Date getCreatedAt() {
        return new Date(nativeCreatedAt(this.nativePtr));
    }

    public String getName() {
        String strNativeName = nativeName(this.nativePtr);
        if (strNativeName.isEmpty()) {
            return null;
        }
        return strNativeName;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    public String getObjectType() {
        return nativeObjectClassName(this.nativePtr);
    }

    public String getQuery() {
        return nativeQueryString(this.nativePtr);
    }

    public Date getUpdatedAt() {
        return new Date(nativeUpdatedAt(this.nativePtr));
    }
}
