package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzad extends zzl implements zzae {
    public zzad() {
        super("com.google.android.play.core.inappreview.protocol.IInAppReviewServiceCallback");
    }

    @Override // com.google.android.play.core.internal.zzl
    public final boolean zza(int i, Parcel parcel) throws RemoteException {
        if (i != 2) {
            return false;
        }
        zzb((Bundle) zzm.zza(parcel, Bundle.CREATOR));
        return true;
    }
}
