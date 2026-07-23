package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.internal.common.zzc;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
public interface IFragmentWrapper extends IInterface {

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public static abstract class Stub extends com.google.android.gms.internal.common.zzb implements IFragmentWrapper {
        public Stub() {
            super("com.google.android.gms.dynamic.IFragmentWrapper");
        }

        @NonNull
        public static IFragmentWrapper asInterface(@NonNull IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            return iInterfaceQueryLocalInterface instanceof IFragmentWrapper ? (IFragmentWrapper) iInterfaceQueryLocalInterface : new zza(iBinder);
        }

        @Override // com.google.android.gms.internal.common.zzb
        public final boolean zza(int i, @NonNull Parcel parcel, @NonNull Parcel parcel2) throws RemoteException {
            switch (i) {
                case 2:
                    IObjectWrapper iObjectWrapperZzg = zzg();
                    parcel2.writeNoException();
                    zzc.zze(parcel2, iObjectWrapperZzg);
                    return true;
                case 3:
                    Bundle bundleZzd = zzd();
                    parcel2.writeNoException();
                    zzc.zzd(parcel2, bundleZzd);
                    return true;
                case 4:
                    int iZzb = zzb();
                    parcel2.writeNoException();
                    parcel2.writeInt(iZzb);
                    return true;
                case 5:
                    IFragmentWrapper iFragmentWrapperZze = zze();
                    parcel2.writeNoException();
                    zzc.zze(parcel2, iFragmentWrapperZze);
                    return true;
                case 6:
                    IObjectWrapper iObjectWrapperZzh = zzh();
                    parcel2.writeNoException();
                    zzc.zze(parcel2, iObjectWrapperZzh);
                    return true;
                case 7:
                    boolean zZzs = zzs();
                    parcel2.writeNoException();
                    zzc.zzb(parcel2, zZzs);
                    return true;
                case 8:
                    String strZzj = zzj();
                    parcel2.writeNoException();
                    parcel2.writeString(strZzj);
                    return true;
                case 9:
                    IFragmentWrapper iFragmentWrapperZzf = zzf();
                    parcel2.writeNoException();
                    zzc.zze(parcel2, iFragmentWrapperZzf);
                    return true;
                case 10:
                    int iZzc = zzc();
                    parcel2.writeNoException();
                    parcel2.writeInt(iZzc);
                    return true;
                case 11:
                    boolean zZzt = zzt();
                    parcel2.writeNoException();
                    zzc.zzb(parcel2, zZzt);
                    return true;
                case 12:
                    IObjectWrapper iObjectWrapperZzi = zzi();
                    parcel2.writeNoException();
                    zzc.zze(parcel2, iObjectWrapperZzi);
                    return true;
                case 13:
                    boolean zZzu = zzu();
                    parcel2.writeNoException();
                    zzc.zzb(parcel2, zZzu);
                    return true;
                case 14:
                    boolean zZzv = zzv();
                    parcel2.writeNoException();
                    zzc.zzb(parcel2, zZzv);
                    return true;
                case 15:
                    boolean zZzw = zzw();
                    parcel2.writeNoException();
                    zzc.zzb(parcel2, zZzw);
                    return true;
                case 16:
                    boolean zZzx = zzx();
                    parcel2.writeNoException();
                    zzc.zzb(parcel2, zZzx);
                    return true;
                case 17:
                    boolean zZzy = zzy();
                    parcel2.writeNoException();
                    zzc.zzb(parcel2, zZzy);
                    return true;
                case 18:
                    boolean zZzz = zzz();
                    parcel2.writeNoException();
                    zzc.zzb(parcel2, zZzz);
                    return true;
                case 19:
                    boolean zZzA = zzA();
                    parcel2.writeNoException();
                    zzc.zzb(parcel2, zZzA);
                    return true;
                case 20:
                    zzk(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 21:
                    zzl(zzc.zzf(parcel));
                    parcel2.writeNoException();
                    return true;
                case 22:
                    zzm(zzc.zzf(parcel));
                    parcel2.writeNoException();
                    return true;
                case 23:
                    zzn(zzc.zzf(parcel));
                    parcel2.writeNoException();
                    return true;
                case 24:
                    zzo(zzc.zzf(parcel));
                    parcel2.writeNoException();
                    return true;
                case 25:
                    zzp((Intent) zzc.zza(parcel, Intent.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 26:
                    zzq((Intent) zzc.zza(parcel, Intent.CREATOR), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 27:
                    zzr(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                default:
                    return false;
            }
        }
    }

    boolean zzA() throws RemoteException;

    int zzb() throws RemoteException;

    int zzc() throws RemoteException;

    @Nullable
    Bundle zzd() throws RemoteException;

    @Nullable
    IFragmentWrapper zze() throws RemoteException;

    @Nullable
    IFragmentWrapper zzf() throws RemoteException;

    @NonNull
    IObjectWrapper zzg() throws RemoteException;

    @NonNull
    IObjectWrapper zzh() throws RemoteException;

    @NonNull
    IObjectWrapper zzi() throws RemoteException;

    @Nullable
    String zzj() throws RemoteException;

    void zzk(@NonNull IObjectWrapper iObjectWrapper) throws RemoteException;

    void zzl(boolean z) throws RemoteException;

    void zzm(boolean z) throws RemoteException;

    void zzn(boolean z) throws RemoteException;

    void zzo(boolean z) throws RemoteException;

    void zzp(@NonNull Intent intent) throws RemoteException;

    void zzq(@NonNull Intent intent, int i) throws RemoteException;

    void zzr(@NonNull IObjectWrapper iObjectWrapper) throws RemoteException;

    boolean zzs() throws RemoteException;

    boolean zzt() throws RemoteException;

    boolean zzu() throws RemoteException;

    boolean zzv() throws RemoteException;

    boolean zzw() throws RemoteException;

    boolean zzx() throws RemoteException;

    boolean zzy() throws RemoteException;

    boolean zzz() throws RemoteException;
}
