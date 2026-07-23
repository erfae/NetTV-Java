package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzaa extends zzk implements zzac {
    public zzaa(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.inappreview.protocol.IInAppReviewService");
    }

    @Override // com.google.android.play.core.internal.zzac
    public final void zzc(String str, Bundle bundle, zzae zzaeVar) throws RemoteException {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzm.zzb(parcelZza, bundle);
        zzm.zzc(parcelZza, zzaeVar);
        zzb(2, parcelZza);
    }
}
