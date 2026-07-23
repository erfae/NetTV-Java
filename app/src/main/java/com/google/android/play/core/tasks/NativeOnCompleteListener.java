package com.google.android.play.core.tasks;

import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class NativeOnCompleteListener implements OnCompleteListener<Object> {
    private final long zza;
    private final int zzb;

    public NativeOnCompleteListener(long j, int i) {
        this.zza = j;
        this.zzb = i;
    }

    public native void nativeOnComplete(long j, int i, @Nullable Object obj, int i2);

    @Override // com.google.android.play.core.tasks.OnCompleteListener
    public void onComplete(Task<Object> task) {
        if (!task.isComplete()) {
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(50, "onComplete called for incomplete task: ", this.zzb));
        }
        if (task.isSuccessful()) {
            nativeOnComplete(this.zza, this.zzb, task.getResult(), 0);
            return;
        }
        Exception exception = task.getException();
        if (!(exception instanceof zzj)) {
            nativeOnComplete(this.zza, this.zzb, null, -100);
            return;
        }
        int errorCode = ((zzj) exception).getErrorCode();
        if (errorCode == 0) {
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(51, "TaskException has error code 0 on task: ", this.zzb));
        }
        nativeOnComplete(this.zza, this.zzb, null, errorCode);
    }
}
