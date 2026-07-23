package com.android.volley.toolbox;

import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
final class Threads {
    private Threads() {
    }

    public static void throwIfNotOnMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Must be invoked from the main thread.");
        }
    }
}
