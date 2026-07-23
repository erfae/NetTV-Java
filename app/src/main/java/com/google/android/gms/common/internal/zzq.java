package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.core.os.EnvironmentCompat;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzq implements Handler.Callback {
    public final /* synthetic */ zzr zza;

    public /* synthetic */ zzq(zzr zzrVar) {
        this.zza = zzrVar;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            synchronized (this.zza.zzb) {
                zzn zznVar = (zzn) message.obj;
                zzo zzoVar = (zzo) this.zza.zzb.get(zznVar);
                if (zzoVar != null && zzoVar.zzi()) {
                    if (zzoVar.zzj()) {
                        zzoVar.zzg("GmsClientSupervisor");
                    }
                    this.zza.zzb.remove(zznVar);
                }
            }
            return true;
        }
        if (i != 1) {
            return false;
        }
        synchronized (this.zza.zzb) {
            zzn zznVar2 = (zzn) message.obj;
            zzo zzoVar2 = (zzo) this.zza.zzb.get(zznVar2);
            if (zzoVar2 != null && zzoVar2.zza() == 3) {
                String strValueOf = String.valueOf(zznVar2);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 47);
                sb.append("Timeout waiting for ServiceConnection callback ");
                sb.append(strValueOf);
                Log.e("GmsClientSupervisor", sb.toString(), new Exception());
                ComponentName componentNameZzb = zzoVar2.zzb();
                if (componentNameZzb == null) {
                    componentNameZzb = zznVar2.zzb();
                }
                if (componentNameZzb == null) {
                    String strZzd = zznVar2.zzd();
                    Preconditions.checkNotNull(strZzd);
                    componentNameZzb = new ComponentName(strZzd, EnvironmentCompat.MEDIA_UNKNOWN);
                }
                zzoVar2.onServiceDisconnected(componentNameZzb);
            }
        }
        return true;
    }
}
