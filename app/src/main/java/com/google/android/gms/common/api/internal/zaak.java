package com.google.android.gms.common.api.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zaak implements Runnable {
    public final /* synthetic */ zaaw zaa;

    public zaak(zaaw zaawVar) {
        this.zaa = zaawVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zaaw zaawVar = this.zaa;
        zaawVar.zad.cancelAvailabilityErrorNotifications(zaawVar.zac);
    }
}
