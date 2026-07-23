package com.google.android.play.core.splitinstall;

import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class SplitInstallException extends com.google.android.play.core.tasks.zzj {

    @SplitInstallErrorCode
    private final int zza;

    public SplitInstallException(@SplitInstallErrorCode int i) {
        super(String.format("Split Install Error(%d): %s", Integer.valueOf(i), com.google.android.play.core.splitinstall.model.zza.zzb(i)));
        if (i == 0) {
            throw new IllegalArgumentException("errorCode should not be 0.");
        }
        this.zza = i;
    }

    @Override // com.google.android.play.core.tasks.zzj
    @SplitInstallErrorCode
    public int getErrorCode() {
        return this.zza;
    }
}
