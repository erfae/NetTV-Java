package io.realm.internal.android;

import android.os.Looper;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.realm.internal.Capabilities;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public class AndroidCapabilities implements Capabilities {

    @SuppressFBWarnings({"MS_SHOULD_BE_FINAL", "MS_CANNOT_BE_FINAL"})
    public static boolean EMULATE_MAIN_THREAD;
    private final Looper looper = Looper.myLooper();
    private final boolean isIntentServiceThread = isIntentServiceThread();

    private boolean hasLooper() {
        return this.looper != null;
    }

    private static boolean isIntentServiceThread() {
        String name = Thread.currentThread().getName();
        return name != null && name.startsWith("IntentService[");
    }

    @Override // io.realm.internal.Capabilities
    public boolean canDeliverNotification() {
        return hasLooper() && !this.isIntentServiceThread;
    }

    @Override // io.realm.internal.Capabilities
    public void checkCanDeliverNotification(@Nullable String str) {
        if (!hasLooper()) {
            throw new IllegalStateException(str != null ? Insets$$ExternalSyntheticOutline0.m(str, " ", "Realm cannot be automatically updated on a thread without a looper.") : "");
        }
        if (this.isIntentServiceThread) {
            throw new IllegalStateException(str != null ? Insets$$ExternalSyntheticOutline0.m(str, " ", "Realm cannot be automatically updated on an IntentService thread.") : "");
        }
    }

    @Override // io.realm.internal.Capabilities
    public boolean isMainThread() {
        Looper looper = this.looper;
        return looper != null && (EMULATE_MAIN_THREAD || looper == Looper.getMainLooper());
    }
}
