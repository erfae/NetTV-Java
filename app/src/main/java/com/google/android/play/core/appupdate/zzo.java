package com.google.android.play.core.appupdate;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.internal.zzag;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzo extends zzn {
    public zzo(zzq zzqVar, com.google.android.play.core.tasks.zzi zziVar) {
        super(zzqVar, new zzag("OnCompleteUpdateCallback"), zziVar);
    }

    @Override // com.google.android.play.core.appupdate.zzn, com.google.android.play.core.internal.zzr
    public final void zzb(Bundle bundle) throws RemoteException {
        super.zzb(bundle);
        int i = zzq.$r8$clinit;
        if (bundle.getInt("error.code", -2) != 0) {
            this.zzb.zzd(new InstallException(bundle.getInt("error.code", -2)));
        } else {
            this.zzb.zze(null);
        }
    }
}
