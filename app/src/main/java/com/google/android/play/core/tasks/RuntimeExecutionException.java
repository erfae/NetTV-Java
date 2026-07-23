package com.google.android.play.core.tasks;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class RuntimeExecutionException extends zzj {
    public RuntimeExecutionException(Throwable th) {
        super(th);
    }

    @Override // com.google.android.play.core.tasks.zzj
    public final int getErrorCode() {
        return -100;
    }
}
