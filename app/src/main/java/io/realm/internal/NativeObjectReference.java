package io.realm.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/* JADX INFO: loaded from: classes2.dex */
final class NativeObjectReference extends PhantomReference<NativeObject> {
    private static ReferencePool referencePool = new ReferencePool();
    private final NativeContext context;
    private final long nativeFinalizerPtr;
    private final long nativePtr;
    private NativeObjectReference next;
    private NativeObjectReference prev;

    public static class ReferencePool {
        public NativeObjectReference head;

        private ReferencePool() {
        }
    }

    public NativeObjectReference(NativeContext nativeContext, NativeObject nativeObject, ReferenceQueue<? super NativeObject> referenceQueue) {
        super(nativeObject, referenceQueue);
        this.nativePtr = nativeObject.getNativePtr();
        this.nativeFinalizerPtr = nativeObject.getNativeFinalizerPtr();
        this.context = nativeContext;
        ReferencePool referencePool2 = referencePool;
        synchronized (referencePool2) {
            this.prev = null;
            this.next = referencePool2.head;
            NativeObjectReference nativeObjectReference = referencePool2.head;
            if (nativeObjectReference != null) {
                nativeObjectReference.prev = this;
            }
            referencePool2.head = this;
        }
    }

    public static native void nativeCleanUp(long j, long j2);

    public final void cleanup() {
        synchronized (this.context) {
            nativeCleanUp(this.nativeFinalizerPtr, this.nativePtr);
        }
        ReferencePool referencePool2 = referencePool;
        synchronized (referencePool2) {
            NativeObjectReference nativeObjectReference = this.next;
            NativeObjectReference nativeObjectReference2 = this.prev;
            this.next = null;
            this.prev = null;
            if (nativeObjectReference2 != null) {
                nativeObjectReference2.next = nativeObjectReference;
            } else {
                referencePool2.head = nativeObjectReference;
            }
            if (nativeObjectReference != null) {
                nativeObjectReference.prev = nativeObjectReference2;
            }
        }
    }
}
