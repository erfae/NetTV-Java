package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.annotation.NonNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
public interface IAccountAccessor extends IInterface {

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public static abstract class Stub extends com.google.android.gms.internal.common.zzb implements IAccountAccessor {
        public Stub() {
            super("com.google.android.gms.common.internal.IAccountAccessor");
        }

        @NonNull
        public static IAccountAccessor asInterface(@NonNull IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
            return iInterfaceQueryLocalInterface instanceof IAccountAccessor ? (IAccountAccessor) iInterfaceQueryLocalInterface : new zzv(iBinder);
        }

        @Override // com.google.android.gms.internal.common.zzb
        public final boolean zza(int i, @NonNull Parcel parcel, @NonNull Parcel parcel2) throws RemoteException {
            if (i != 2) {
                return false;
            }
            Account accountZzb = zzb();
            parcel2.writeNoException();
            com.google.android.gms.internal.common.zzc.zzd(parcel2, accountZzb);
            return true;
        }
    }

    @NonNull
    Account zzb() throws RemoteException;
}
