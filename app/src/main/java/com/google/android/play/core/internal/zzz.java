package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzz extends zzk {
    public zzz(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.assetpacks.protocol.IAssetPackExtractionServiceCallback");
    }

    public final void zzc(Bundle bundle) throws RemoteException {
        Parcel parcelZza = zza();
        zzm.zzb(parcelZza, bundle);
        zzb(4, parcelZza);
    }

    public final void zzd(Bundle bundle) throws RemoteException {
        Parcel parcelZza = zza();
        zzm.zzb(parcelZza, bundle);
        zzb(3, parcelZza);
    }

    public final void zze(Bundle bundle, Bundle bundle2) throws RemoteException {
        Parcel parcelZza = zza();
        zzm.zzb(parcelZza, bundle);
        zzm.zzb(parcelZza, bundle2);
        zzb(2, parcelZza);
    }
}
