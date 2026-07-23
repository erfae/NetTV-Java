package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzx extends com.google.android.play.core.listener.zzc {

    @Nullable
    private static zzx zzc;
    private final Handler zzd;
    private final zzg zze;
    private final Set zzf;

    @VisibleForTesting
    public zzx(Context context, zzg zzgVar) {
        super(new com.google.android.play.core.internal.zzag("SplitInstallListenerRegistry"), new IntentFilter("com.google.android.play.core.splitinstall.receiver.SplitInstallUpdateIntentService"), context);
        this.zzd = new Handler(Looper.getMainLooper());
        this.zzf = new LinkedHashSet();
        this.zze = zzgVar;
    }

    public static synchronized zzx zzc(Context context) {
        if (zzc == null) {
            zzc = new zzx(context, zzo.INSTANCE);
        }
        return zzc;
    }

    @Override // com.google.android.play.core.listener.zzc
    public final void zza(Context context, Intent intent) {
        Bundle bundleExtra = intent.getBundleExtra("session_state");
        if (bundleExtra == null) {
            return;
        }
        SplitInstallSessionState splitInstallSessionStateZzd = SplitInstallSessionState.zzd(bundleExtra);
        this.zza.zza("ListenerRegistryBroadcastReceiver.onReceive: %s", splitInstallSessionStateZzd);
        zzh zzhVarZza = this.zze.zza();
        if (splitInstallSessionStateZzd.status() != 3 || zzhVarZza == null) {
            zzm(splitInstallSessionStateZzd);
        } else {
            zzhVarZza.zzd(splitInstallSessionStateZzd.zzc(), new zzv(this, splitInstallSessionStateZzd, intent, context));
        }
    }

    public final synchronized void zzk(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.zzf.add(splitInstallStateUpdatedListener);
    }

    public final synchronized void zzl(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.zzf.remove(splitInstallStateUpdatedListener);
    }

    public final synchronized void zzm(SplitInstallSessionState splitInstallSessionState) {
        Iterator it = new LinkedHashSet(this.zzf).iterator();
        while (it.hasNext()) {
            ((SplitInstallStateUpdatedListener) it.next()).onStateUpdate(splitInstallSessionState);
        }
        super.zzi(splitInstallSessionState);
    }
}
