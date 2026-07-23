package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.internal.BaseGmsClient;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zabp implements BaseGmsClient.SignOutCallbacks {
    public final /* synthetic */ zabq zaa;

    public zabp(zabq zabqVar) {
        this.zaa = zabqVar;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.SignOutCallbacks
    public final void onSignOutComplete() {
        this.zaa.zaa.zat.post(new zabo(this));
    }
}
