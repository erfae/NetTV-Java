package com.google.android.play.core.assetpacks;

import android.R;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzb extends com.google.android.play.core.internal.zzx {

    @VisibleForTesting
    public final NotificationManager zza;
    private final com.google.android.play.core.internal.zzag zzb = new com.google.android.play.core.internal.zzag("AssetPackExtractionService");
    private final Context zzc;
    private final zzbh zzd;
    private final zzl zze;
    private final zzci zzf;

    public zzb(Context context, zzbh zzbhVar, zzl zzlVar, zzci zzciVar) {
        this.zzc = context;
        this.zzd = zzbhVar;
        this.zze = zzlVar;
        this.zzf = zzciVar;
        this.zza = (NotificationManager) context.getSystemService("notification");
    }

    @TargetApi(26)
    private final synchronized void zzd(@Nullable String str) {
        if (str == null) {
            str = "File downloads by Play";
        }
        this.zza.createNotificationChannel(new NotificationChannel("playcore-assetpacks-service-notification-channel", str, 2));
    }

    private final synchronized void zze(Bundle bundle, com.google.android.play.core.internal.zzz zzzVar) throws RemoteException {
        this.zzb.zza("updateServiceState AIDL call", new Object[0]);
        if (com.google.android.play.core.internal.zzch.zzb(this.zzc) && com.google.android.play.core.internal.zzch.zza(this.zzc)) {
            int i = bundle.getInt("action_type");
            this.zzf.zzc(zzzVar);
            if (i != 1) {
                if (i == 2) {
                    this.zze.zzg(false);
                    this.zzf.zzb();
                    return;
                } else {
                    this.zzb.zzb("Unknown action type received: %d", Integer.valueOf(i));
                    zzzVar.zzd(new Bundle());
                    return;
                }
            }
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 26) {
                zzd(bundle.getString("notification_channel_name"));
            }
            this.zze.zzg(true);
            zzci zzciVar = this.zzf;
            String string = bundle.getString("notification_title");
            String string2 = bundle.getString("notification_subtext");
            long j = bundle.getLong("notification_timeout", 600000L);
            Parcelable parcelable = bundle.getParcelable("notification_on_click_intent");
            Notification.Builder timeoutAfter = i2 >= 26 ? new Notification.Builder(this.zzc, "playcore-assetpacks-service-notification-channel").setTimeoutAfter(j) : new Notification.Builder(this.zzc).setPriority(-2);
            if (parcelable instanceof PendingIntent) {
                timeoutAfter.setContentIntent((PendingIntent) parcelable);
            }
            Notification.Builder ongoing = timeoutAfter.setSmallIcon(R.drawable.stat_sys_download).setOngoing(false);
            if (string == null) {
                string = "Downloading additional file";
            }
            Notification.Builder contentTitle = ongoing.setContentTitle(string);
            if (string2 == null) {
                string2 = "Transferring";
            }
            contentTitle.setSubText(string2);
            int i3 = bundle.getInt("notification_color");
            if (i3 != 0) {
                timeoutAfter.setColor(i3).setVisibility(-1);
            }
            zzciVar.zza(timeoutAfter.build());
            this.zzc.bindService(new Intent(this.zzc, (Class<?>) ExtractionForegroundService.class), this.zzf, 1);
            return;
        }
        zzzVar.zzd(new Bundle());
    }

    @Override // com.google.android.play.core.internal.zzy
    public final void zzb(Bundle bundle, com.google.android.play.core.internal.zzz zzzVar) throws RemoteException {
        this.zzb.zza("clearAssetPackStorage AIDL call", new Object[0]);
        if (!com.google.android.play.core.internal.zzch.zzb(this.zzc) || !com.google.android.play.core.internal.zzch.zza(this.zzc)) {
            zzzVar.zzd(new Bundle());
        } else {
            this.zzd.zzz();
            zzzVar.zzc(new Bundle());
        }
    }

    @Override // com.google.android.play.core.internal.zzy
    public final void zzc(Bundle bundle, com.google.android.play.core.internal.zzz zzzVar) throws RemoteException {
        zze(bundle, zzzVar);
    }
}
