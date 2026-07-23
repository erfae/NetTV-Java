package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.HashMap;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzr extends GmsClientSupervisor {

    @GuardedBy("connectionStatus")
    private final HashMap<zzn, zzo> zzb = new HashMap<>();
    private final Context zzc;
    private volatile Handler zzd;
    private final zzq zze;
    private final ConnectionTracker zzf;
    private final long zzg;
    private final long zzh;

    public zzr(Context context, Looper looper) {
        zzq zzqVar = new zzq(this);
        this.zze = zzqVar;
        this.zzc = context.getApplicationContext();
        this.zzd = new com.google.android.gms.internal.common.zzi(looper, zzqVar);
        this.zzf = ConnectionTracker.getInstance();
        this.zzg = 5000L;
        this.zzh = 300000L;
    }

    @Override // com.google.android.gms.common.internal.GmsClientSupervisor
    public final void zza(zzn zznVar, ServiceConnection serviceConnection, String str) {
        Preconditions.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zzb) {
            zzo zzoVar = this.zzb.get(zznVar);
            if (zzoVar == null) {
                String string = zznVar.toString();
                StringBuilder sb = new StringBuilder(string.length() + 50);
                sb.append("Nonexistent connection status for service config: ");
                sb.append(string);
                throw new IllegalStateException(sb.toString());
            }
            if (!zzoVar.zzh(serviceConnection)) {
                String string2 = zznVar.toString();
                StringBuilder sb2 = new StringBuilder(string2.length() + 76);
                sb2.append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=");
                sb2.append(string2);
                throw new IllegalStateException(sb2.toString());
            }
            zzoVar.zzf(serviceConnection, str);
            if (zzoVar.zzi()) {
                this.zzd.sendMessageDelayed(this.zzd.obtainMessage(0, zznVar), this.zzg);
            }
        }
    }

    @Override // com.google.android.gms.common.internal.GmsClientSupervisor
    public final boolean zzc(zzn zznVar, ServiceConnection serviceConnection, String str, @Nullable Executor executor) {
        boolean zZzj;
        Preconditions.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zzb) {
            zzo zzoVar = this.zzb.get(zznVar);
            if (zzoVar == null) {
                zzoVar = new zzo(this, zznVar);
                zzoVar.zzd(serviceConnection, serviceConnection, str);
                zzoVar.zze(str, executor);
                this.zzb.put(zznVar, zzoVar);
            } else {
                this.zzd.removeMessages(0, zznVar);
                if (zzoVar.zzh(serviceConnection)) {
                    String string = zznVar.toString();
                    StringBuilder sb = new StringBuilder(string.length() + 81);
                    sb.append("Trying to bind a GmsServiceConnection that was already connected before.  config=");
                    sb.append(string);
                    throw new IllegalStateException(sb.toString());
                }
                zzoVar.zzd(serviceConnection, serviceConnection, str);
                int iZza = zzoVar.zza();
                if (iZza == 1) {
                    serviceConnection.onServiceConnected(zzoVar.zzb(), zzoVar.zzc());
                } else if (iZza == 2) {
                    zzoVar.zze(str, executor);
                }
            }
            zZzj = zzoVar.zzj();
        }
        return zZzj;
    }

    public final void zzi(Looper looper) {
        synchronized (this.zzb) {
            this.zzd = new com.google.android.gms.internal.common.zzi(looper, this.zze);
        }
    }
}
