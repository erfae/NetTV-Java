package com.google.android.play.core.appupdate;

import android.content.Context;
import com.google.android.play.core.internal.zzce;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zza {
    private static zzaa zza;

    public static synchronized zzaa zza(Context context) {
        if (zza == null) {
            zzy zzyVar = new zzy(null);
            zzyVar.zza(new zzh(zzce.zza(context)));
            zza = zzyVar.zzb();
        }
        return zza;
    }
}
