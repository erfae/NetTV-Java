package com.google.android.play.core.splitcompat;

import android.util.Log;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzq implements Runnable {
    public final /* synthetic */ Set zza;
    public final /* synthetic */ SplitCompat zzb;

    public zzq(SplitCompat splitCompat, Set set) {
        this.zzb = splitCompat;
        this.zza = set;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.zzb.zzg(this.zza);
        } catch (Exception e) {
            Log.e("SplitCompat", "Failed to remove from splitcompat storage split that is already installed", e);
        }
    }
}
