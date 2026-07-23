package com.google.android.play.core.missingsplits;

import android.app.Application;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public class MissingSplitsDetectingApplication extends Application {
    private boolean onCreateCalled = false;

    @Override // android.app.Application
    public final void onCreate() {
        if (this.onCreateCalled) {
            throw new IllegalStateException("The onCreate method must be invoked at most once.");
        }
        this.onCreateCalled = true;
        if (MissingSplitsManagerFactory.create(this).disableAppIfMissingRequiredSplits()) {
            return;
        }
        super.onCreate();
        onCreateCustom();
    }

    @Deprecated
    public void onCreateCustom() {
    }
}
