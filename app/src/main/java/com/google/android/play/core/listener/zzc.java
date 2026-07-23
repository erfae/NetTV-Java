package com.google.android.play.core.listener;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.Nullable;
import com.google.android.play.core.internal.zzag;
import com.google.android.play.core.internal.zzce;
import com.google.android.play.core.internal.zzci;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzc {
    public final zzag zza;
    private final IntentFilter zzc;
    private final Context zzd;
    public final HashSet zzb = new HashSet();

    @Nullable
    private zzb zze = null;
    private volatile boolean zzf = false;

    public zzc(zzag zzagVar, IntentFilter intentFilter, Context context) {
        this.zza = zzagVar;
        this.zzc = intentFilter;
        this.zzd = zzce.zza(context);
    }

    private final void zzb() {
        zzb zzbVar;
        if ((this.zzf || !this.zzb.isEmpty()) && this.zze == null) {
            zzb zzbVar2 = new zzb(this);
            this.zze = zzbVar2;
            this.zzd.registerReceiver(zzbVar2, this.zzc);
        }
        if (this.zzf || !this.zzb.isEmpty() || (zzbVar = this.zze) == null) {
            return;
        }
        this.zzd.unregisterReceiver(zzbVar);
        this.zze = null;
    }

    public abstract void zza(Context context, Intent intent);

    public final synchronized void zze() {
        this.zza.zzd("clearListeners", new Object[0]);
        this.zzb.clear();
        zzb();
    }

    public final synchronized void zzf(StateUpdatedListener stateUpdatedListener) {
        this.zza.zzd("registerListener", new Object[0]);
        zzci.zza(stateUpdatedListener, "Registered Play Core listener should not be null.");
        this.zzb.add(stateUpdatedListener);
        zzb();
    }

    public final synchronized void zzg(boolean z) {
        this.zzf = z;
        zzb();
    }

    public final synchronized void zzh(StateUpdatedListener stateUpdatedListener) {
        this.zza.zzd("unregisterListener", new Object[0]);
        zzci.zza(stateUpdatedListener, "Unregistered Play Core listener should not be null.");
        this.zzb.remove(stateUpdatedListener);
        zzb();
    }

    public final synchronized void zzi(Object obj) {
        Iterator it = new HashSet(this.zzb).iterator();
        while (it.hasNext()) {
            ((StateUpdatedListener) it.next()).onStateUpdate(obj);
        }
    }

    public final synchronized boolean zzj() {
        return this.zze != null;
    }
}
