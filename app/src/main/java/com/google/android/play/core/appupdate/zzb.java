package com.google.android.play.core.appupdate;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.internal.zzag;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzb extends com.google.android.play.core.listener.zzc {
    public zzb(Context context) {
        super(new zzag("AppUpdateListenerRegistry"), new IntentFilter("com.google.android.play.core.install.ACTION_INSTALL_STATUS"), context);
    }

    @Override // com.google.android.play.core.listener.zzc
    public final void zza(Context context, Intent intent) {
        if (!context.getPackageName().equals(intent.getStringExtra("package.name"))) {
            this.zza.zza("ListenerRegistryBroadcastReceiver received broadcast for third party app: %s", intent.getStringExtra("package.name"));
            return;
        }
        this.zza.zza("List of extras in received intent:", new Object[0]);
        for (String str : intent.getExtras().keySet()) {
            this.zza.zza("Key: %s; value: %s", str, intent.getExtras().get(str));
        }
        InstallState installStateZzb = InstallState.zzb(intent, this.zza);
        this.zza.zza("ListenerRegistryBroadcastReceiver.onReceive: %s", installStateZzb);
        zzi(installStateZzb);
    }
}
