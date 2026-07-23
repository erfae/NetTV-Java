package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzn extends zzk implements zzp {
    public zzn(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.appupdate.protocol.IAppUpdateService");
    }

    @Override // com.google.android.play.core.internal.zzp
    public final void zzc(String str, Bundle bundle, zzr zzrVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzrVar);
        zzb(3, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzp
    public final void zzd(String str, Bundle bundle, zzr zzrVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzrVar);
        zzb(2, parcelZza);
    }
}
