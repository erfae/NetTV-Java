package pl.droidsonroids.gif;

/* JADX INFO: loaded from: classes2.dex */
class ConditionVariable {
    private volatile boolean mCondition;

    public final synchronized void block() throws InterruptedException {
        while (!this.mCondition) {
            wait();
        }
    }

    public final synchronized void close() {
        this.mCondition = false;
    }

    public final synchronized void open() {
        boolean z = this.mCondition;
        this.mCondition = true;
        if (!z) {
            notify();
        }
    }

    public final synchronized void set(boolean z) {
        try {
            if (z) {
                synchronized (this) {
                    boolean z2 = this.mCondition;
                    this.mCondition = true;
                    if (!z2) {
                        notify();
                    }
                }
            } else {
                close();
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
