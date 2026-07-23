package com.google.android.play.core.assetpacks;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzci implements ServiceConnection {
    private final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("ExtractionForegroundServiceConnection");
    private final List zzb = new ArrayList();
    private final Context zzc;

    @Nullable
    private ExtractionForegroundService zzd;

    @Nullable
    private Notification zze;

    public zzci(Context context) {
        this.zzc = context;
    }

    private final void zzd() {
        ArrayList arrayList;
        synchronized (this.zzb) {
            arrayList = new ArrayList(this.zzb);
            this.zzb.clear();
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            try {
                ((com.google.android.play.core.internal.zzz) arrayList.get(i)).zze(new Bundle(), new Bundle());
            } catch (RemoteException unused) {
                this.zza.zzb("Could not resolve Play Store service state update callback.", new Object[0]);
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.zza.zza("Starting foreground installation service.", new Object[0]);
        ExtractionForegroundService extractionForegroundService = ((zzch) iBinder).zza;
        this.zzd = extractionForegroundService;
        extractionForegroundService.startForeground(-1883842196, this.zze);
        zzd();
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
    }

    public final void zza(Notification notification) {
        this.zze = notification;
    }

    public final void zzb() {
        this.zza.zza("Stopping foreground installation service.", new Object[0]);
        this.zzc.unbindService(this);
        ExtractionForegroundService extractionForegroundService = this.zzd;
        if (extractionForegroundService != null) {
            extractionForegroundService.zza();
        }
        zzd();
    }

    public final void zzc(com.google.android.play.core.internal.zzz zzzVar) {
        synchronized (this.zzb) {
            this.zzb.add(zzzVar);
        }
    }
}
