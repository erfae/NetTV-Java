package com.google.android.play.core.assetpacks;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class AssetPackExtractionService extends Service {
    public zzb zza;

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.zza;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        zzd.zza(getApplicationContext()).zzb(this);
    }
}
