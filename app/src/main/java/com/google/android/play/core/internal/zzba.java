package com.google.android.play.core.internal;

import android.os.Build;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzba {
    public static zzaz zza() {
        switch (Build.VERSION.SDK_INT) {
            case 21:
                return new zzbf();
            case 22:
                return new zzbg();
            case 23:
                return new zzbk();
            case 24:
                return new zzbl();
            case 25:
                return new zzbm();
            case 26:
                return new zzbp();
            case 27:
                if (Build.VERSION.PREVIEW_SDK_INT == 0) {
                    return new zzbq();
                }
                break;
        }
        return new zzbs();
    }
}
