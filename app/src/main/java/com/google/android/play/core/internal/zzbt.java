package com.google.android.play.core.internal;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzbt extends RuntimeException {
    public zzbt(String str) {
        super(str);
    }

    public zzbt(String str, Throwable th) {
        super("Failed to initialize FileStorage", th);
    }
}
