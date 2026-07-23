package com.google.android.play.core.splitcompat;

import android.util.Log;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzp implements Runnable {
    public final /* synthetic */ SplitCompat zza;

    public zzp(SplitCompat splitCompat) {
        this.zza = splitCompat;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.zza.zzc.zzk();
        } catch (Exception e) {
            Log.e("SplitCompat", "Failed to cleanup splitcompat storage", e);
        }
    }
}
