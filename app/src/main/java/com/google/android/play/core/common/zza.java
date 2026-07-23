package com.google.android.play.core.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zza {
    private final Map zza = new HashMap();
    private final AtomicBoolean zzb = new AtomicBoolean(false);

    private final synchronized void zzb() {
        this.zza.put("assetOnlyUpdates", Boolean.FALSE);
    }

    public final synchronized boolean zza(String str) {
        if (!this.zzb.get()) {
            zzb();
        }
        Object obj = this.zza.get("assetOnlyUpdates");
        if (!(obj instanceof Boolean)) {
            return false;
        }
        return ((Boolean) obj).booleanValue();
    }
}
