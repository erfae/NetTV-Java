package com.google.android.gms.common.api.internal;

import android.app.Dialog;
import android.app.PendingIntent;
import androidx.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zao implements Runnable {
    public final /* synthetic */ zap zaa;
    private final zam zab;

    public zao(zap zapVar, zam zamVar) {
        this.zaa = zapVar;
        this.zab = zamVar;
    }

    @Override // java.lang.Runnable
    @MainThread
    public final void run() {
        if (this.zaa.zaa) {
            ConnectionResult connectionResultZab = this.zab.zab();
            if (connectionResultZab.hasResolution()) {
                zap zapVar = this.zaa;
                zapVar.mLifecycleFragment.startActivityForResult(GoogleApiActivity.zaa(zapVar.getActivity(), (PendingIntent) Preconditions.checkNotNull(connectionResultZab.getResolution()), this.zab.zaa(), false), 1);
                return;
            }
            zap zapVar2 = this.zaa;
            if (zapVar2.zac.getErrorResolutionIntent(zapVar2.getActivity(), connectionResultZab.getErrorCode(), null) != null) {
                zap zapVar3 = this.zaa;
                zapVar3.zac.zag(zapVar3.getActivity(), this.zaa.mLifecycleFragment, connectionResultZab.getErrorCode(), 2, this.zaa);
            } else {
                if (connectionResultZab.getErrorCode() != 18) {
                    this.zaa.zaa(connectionResultZab, this.zab.zaa());
                    return;
                }
                zap zapVar4 = this.zaa;
                Dialog dialogZab = zapVar4.zac.zab(zapVar4.getActivity(), this.zaa);
                zap zapVar5 = this.zaa;
                zapVar5.zac.zac(zapVar5.getActivity().getApplicationContext(), new zan(this, dialogZab));
            }
        }
    }
}
