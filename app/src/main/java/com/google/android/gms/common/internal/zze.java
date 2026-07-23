package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.common.util.VisibleForTesting;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
@VisibleForTesting
public final class zze implements ServiceConnection {
    public final /* synthetic */ BaseGmsClient zza;
    private final int zzb;

    public zze(BaseGmsClient baseGmsClient, int i) {
        this.zza = baseGmsClient;
        this.zzb = i;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        BaseGmsClient baseGmsClient = this.zza;
        if (iBinder == null) {
            BaseGmsClient.zzk(baseGmsClient);
            return;
        }
        synchronized (baseGmsClient.zzq) {
            BaseGmsClient baseGmsClient2 = this.zza;
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
            baseGmsClient2.zzr = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IGmsServiceBroker)) ? new zzac(iBinder) : (IGmsServiceBroker) iInterfaceQueryLocalInterface;
        }
        BaseGmsClient baseGmsClient3 = this.zza;
        int i = this.zzb;
        Handler handler = baseGmsClient3.zzb;
        handler.sendMessage(handler.obtainMessage(7, i, -1, new zzg(baseGmsClient3, 0, null)));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.zza.zzq) {
            this.zza.zzr = null;
        }
        Handler handler = this.zza.zzb;
        handler.sendMessage(handler.obtainMessage(6, this.zzb, 1));
    }
}
