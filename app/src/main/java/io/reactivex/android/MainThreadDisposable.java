package io.reactivex.android;

import android.os.Looper;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes2.dex */
public abstract class MainThreadDisposable implements Disposable {
    private final AtomicBoolean unsubscribed = new AtomicBoolean();

    public static void verifyMainThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Expected to be called on the main thread but was ");
        sbM.append(Thread.currentThread().getName());
        throw new IllegalStateException(sbM.toString());
    }

    public final void dispose() {
        if (this.unsubscribed.compareAndSet(false, true)) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                onDispose();
            } else {
                AndroidSchedulers.mainThread().scheduleDirect(new Runnable() { // from class: io.reactivex.android.MainThreadDisposable.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MainThreadDisposable.this.onDispose();
                    }
                });
            }
        }
    }

    public final boolean isDisposed() {
        return this.unsubscribed.get();
    }

    public abstract void onDispose();
}
