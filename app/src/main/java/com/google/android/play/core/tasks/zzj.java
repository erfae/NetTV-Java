package com.google.android.play.core.tasks;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzj extends RuntimeException {
    public zzj(String str) {
        super(str);
    }

    public abstract int getErrorCode();

    public zzj(Throwable th) {
        super(th);
    }
}
