package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzs extends zzk implements zzu {
    public zzs(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.assetpacks.protocol.IAssetModuleService");
    }

    @Override // com.google.android.play.core.internal.zzu
    public final void zzc(String str, List list, Bundle bundle, zzw zzwVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeTypedList(list);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzwVar);
        zzb(14, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzu
    public final void zzd(String str, Bundle bundle, Bundle bundle2, zzw zzwVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzm.zzb(parcelZza, bundle);
        zzm.zzb(parcelZza, bundle2);
        zzm.zzc(parcelZza, zzwVar);
        zzb(11, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzu
    public final void zze(String str, Bundle bundle, zzw zzwVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzwVar);
        zzb(5, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzu
    public final void zzf(String str, Bundle bundle, zzw zzwVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzwVar);
        zzb(10, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzu
    public final void zzg(String str, Bundle bundle, Bundle bundle2, zzw zzwVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzm.zzb(parcelZza, bundle);
        zzm.zzb(parcelZza, bundle2);
        zzm.zzc(parcelZza, zzwVar);
        zzb(6, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzu
    public final void zzh(String str, Bundle bundle, Bundle bundle2, zzw zzwVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzm.zzb(parcelZza, bundle);
        zzm.zzb(parcelZza, bundle2);
        zzm.zzc(parcelZza, zzwVar);
        zzb(7, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzu
    public final void zzi(String str, Bundle bundle, Bundle bundle2, zzw zzwVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzm.zzb(parcelZza, bundle);
        zzm.zzb(parcelZza, bundle2);
        zzm.zzc(parcelZza, zzwVar);
        zzb(9, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzu
    public final void zzj(String str, Bundle bundle, Bundle bundle2, zzw zzwVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzm.zzb(parcelZza, bundle);
        zzm.zzb(parcelZza, bundle2);
        zzm.zzc(parcelZza, zzwVar);
        zzb(13, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzu
    public final void zzk(String str, List list, Bundle bundle, zzw zzwVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeTypedList(list);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzwVar);
        zzb(12, parcelZza);
    }

    @Override // com.google.android.play.core.internal.zzu
    public final void zzl(String str, List list, Bundle bundle, zzw zzwVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeTypedList(list);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzwVar);
        zzb(2, parcelZza);
    }
}
