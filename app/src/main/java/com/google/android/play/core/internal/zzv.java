package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzv extends zzl implements zzw {
    public zzv() {
        super("com.google.android.play.core.assetpacks.protocol.IAssetModuleServiceCallback");
    }

    @Override // com.google.android.play.core.internal.zzl
    public final boolean zza(int i, Parcel parcel) throws RemoteException {
        switch (i) {
            case 2:
                zzn(parcel.readInt(), (Bundle) zzm.zza(parcel, Bundle.CREATOR));
                return true;
            case 3:
                zzb(parcel.readInt(), (Bundle) zzm.zza(parcel, Bundle.CREATOR));
                return true;
            case 4:
                zzf(parcel.readInt(), (Bundle) zzm.zza(parcel, Bundle.CREATOR));
                return true;
            case 5:
                zzg(parcel.createTypedArrayList(Bundle.CREATOR));
                return true;
            case 6:
                Parcelable.Creator creator = Bundle.CREATOR;
                zzi((Bundle) zzm.zza(parcel, creator), (Bundle) zzm.zza(parcel, creator));
                return true;
            case 7:
                zzd((Bundle) zzm.zza(parcel, Bundle.CREATOR));
                return true;
            case 8:
                Parcelable.Creator creator2 = Bundle.CREATOR;
                zzj((Bundle) zzm.zza(parcel, creator2), (Bundle) zzm.zza(parcel, creator2));
                return true;
            case 9:
            default:
                return false;
            case 10:
                Parcelable.Creator creator3 = Bundle.CREATOR;
                zzk((Bundle) zzm.zza(parcel, creator3), (Bundle) zzm.zza(parcel, creator3));
                return true;
            case 11:
                Parcelable.Creator creator4 = Bundle.CREATOR;
                zzh((Bundle) zzm.zza(parcel, creator4), (Bundle) zzm.zza(parcel, creator4));
                return true;
            case 12:
                Parcelable.Creator creator5 = Bundle.CREATOR;
                zze((Bundle) zzm.zza(parcel, creator5), (Bundle) zzm.zza(parcel, creator5));
                return true;
            case 13:
                Parcelable.Creator creator6 = Bundle.CREATOR;
                zzm((Bundle) zzm.zza(parcel, creator6), (Bundle) zzm.zza(parcel, creator6));
                return true;
            case 14:
                Parcelable.Creator creator7 = Bundle.CREATOR;
                zzl((Bundle) zzm.zza(parcel, creator7), (Bundle) zzm.zza(parcel, creator7));
                return true;
            case 15:
                zzc((Bundle) zzm.zza(parcel, Bundle.CREATOR));
                return true;
        }
    }
}
