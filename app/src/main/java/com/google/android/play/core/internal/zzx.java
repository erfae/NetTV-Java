package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzx extends zzl implements zzy {
    public zzx() {
        super("com.google.android.play.core.assetpacks.protocol.IAssetPackExtractionService");
    }

    @Override // com.google.android.play.core.internal.zzl
    public final boolean zza(int i, Parcel parcel) throws RemoteException {
        zzz zzzVar = null;
        if (i == 2) {
            Bundle bundle = (Bundle) zzm.zza(parcel, Bundle.CREATOR);
            IBinder strongBinder = parcel.readStrongBinder();
            if (strongBinder != null) {
                IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.play.core.assetpacks.protocol.IAssetPackExtractionServiceCallback");
                zzzVar = iInterfaceQueryLocalInterface instanceof zzz ? (zzz) iInterfaceQueryLocalInterface : new zzz(strongBinder);
            }
            zzc(bundle, zzzVar);
            return true;
        }
        if (i != 3) {
            return false;
        }
        Bundle bundle2 = (Bundle) zzm.zza(parcel, Bundle.CREATOR);
        IBinder strongBinder2 = parcel.readStrongBinder();
        if (strongBinder2 != null) {
            IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.google.android.play.core.assetpacks.protocol.IAssetPackExtractionServiceCallback");
            zzzVar = iInterfaceQueryLocalInterface2 instanceof zzz ? (zzz) iInterfaceQueryLocalInterface2 : new zzz(strongBinder2);
        }
        zzb(bundle2, zzzVar);
        return true;
    }
}
