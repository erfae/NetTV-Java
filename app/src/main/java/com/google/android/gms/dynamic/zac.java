package com.google.android.gms.dynamic;

import android.os.Bundle;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zac implements zah {
    public final /* synthetic */ Bundle zaa;
    public final /* synthetic */ DeferredLifecycleHelper zab;

    public zac(DeferredLifecycleHelper deferredLifecycleHelper, Bundle bundle) {
        this.zab = deferredLifecycleHelper;
        this.zaa = bundle;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final int zaa() {
        return 1;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final void zab(LifecycleDelegate lifecycleDelegate) {
        this.zab.zaa.onCreate(this.zaa);
    }
}
