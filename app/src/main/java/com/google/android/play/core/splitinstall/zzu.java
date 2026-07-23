package com.google.android.play.core.splitinstall;

import android.content.Context;
import com.google.android.play.core.internal.zzce;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzu {
    private static zzp zza;

    public static synchronized zzp zza(Context context) {
        if (zza == null) {
            zzc zzcVar = new zzc(null);
            zzcVar.zza(new zzac(zzce.zza(context)));
            zza = zzcVar.zzb();
        }
        return zza;
    }
}
