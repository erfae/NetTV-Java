package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.content.pm.PackageManager;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzed {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("PackageStateCache");
    private final Context zzb;
    private int zzc = -1;

    public zzed(Context context) {
        this.zzb = context;
    }

    public final synchronized int zza() {
        if (this.zzc == -1) {
            try {
                this.zzc = this.zzb.getPackageManager().getPackageInfo(this.zzb.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException unused) {
                zza.zzb("The current version of the app could not be retrieved", new Object[0]);
            }
        }
        return this.zzc;
    }
}
