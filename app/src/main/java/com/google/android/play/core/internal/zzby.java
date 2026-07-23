package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzby extends zzk implements zzca {
    public zzby(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.splitinstall.protocol.ISplitInstallService");
    }

    @Override // com.google.android.play.core.internal.zzca
    public final void zzc(String str, int i, Bundle bundle, zzcc zzccVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeInt(i);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzccVar);
        zzb(4, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzca
    public final void zzd(String str, List list, Bundle bundle, zzcc zzccVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeTypedList(list);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzccVar);
        zzb(8, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzca
    public final void zze(String str, List list, Bundle bundle, zzcc zzccVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeTypedList(list);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzccVar);
        zzb(13, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzca
    public final void zzf(String str, List list, Bundle bundle, zzcc zzccVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeTypedList(list);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzccVar);
        zzb(14, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzca
    public final void zzg(String str, List list, Bundle bundle, zzcc zzccVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeTypedList(list);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzccVar);
        zzb(7, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzca
    public final void zzh(String str, int i, zzcc zzccVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeInt(i);
        zzm.zzc(parcelZza, zzccVar);
        zzb(5, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzca
    public final void zzi(String str, zzcc zzccVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzm.zzc(parcelZza, zzccVar);
        zzb(6, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzca
    public final void zzj(String str, List list, Bundle bundle, zzcc zzccVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeTypedList(list);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzccVar);
        zzb(2, parcelZza);
    }
}
