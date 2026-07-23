package io.realm.internal;

import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes2.dex */
public class NativeContext {
    public static final NativeContext dummyContext;
    private static final Thread finalizingThread;
    private static final ReferenceQueue<NativeObject> referenceQueue;

    public static class ManualReleaseNativeContext extends NativeContext {
        private final LinkedList<NativeObject> references = new LinkedList<>();

        @Override // io.realm.internal.NativeContext
        public void addReference(NativeObject nativeObject) {
            this.references.add(nativeObject);
        }

        public void release() {
            for (NativeObject nativeObject : this.references) {
                NativeObjectReference.nativeCleanUp(nativeObject.getNativeFinalizerPtr(), nativeObject.getNativePtr());
            }
        }
    }

    public interface NativeContextRunnable {
        void run(NativeContext nativeContext);
    }

    static {
        ReferenceQueue<NativeObject> referenceQueue2 = new ReferenceQueue<>();
        referenceQueue = referenceQueue2;
        Thread thread = new Thread(new FinalizerRunnable(referenceQueue2));
        finalizingThread = thread;
        dummyContext = new NativeContext();
        thread.setName("RealmFinalizingDaemon");
        thread.start();
    }

    public void addReference(NativeObject nativeObject) {
        new NativeObjectReference(this, nativeObject, referenceQueue);
    }
}
