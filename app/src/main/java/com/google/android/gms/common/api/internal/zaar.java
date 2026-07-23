package com.google.android.gms.common.api.internal;

import androidx.annotation.BinderThread;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zaar extends com.google.android.gms.signin.internal.zac {
    private final WeakReference<zaaw> zaa;

    public zaar(zaaw zaawVar) {
        this.zaa = new WeakReference<>(zaawVar);
    }

    @Override // com.google.android.gms.signin.internal.zac, com.google.android.gms.signin.internal.zae
    @BinderThread
    public final void zab(com.google.android.gms.signin.internal.zak zakVar) {
        zaaw zaawVar = this.zaa.get();
        if (zaawVar == null) {
            return;
        }
        zaawVar.zaa.zal(new zaaq(zaawVar, zaawVar, zakVar));
    }
}
