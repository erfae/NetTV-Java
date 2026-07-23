package com.google.android.gms.common.api.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zas {
    public final /* synthetic */ BasePendingResult zaa;

    public /* synthetic */ zas(BasePendingResult basePendingResult) {
        this.zaa = basePendingResult;
    }

    public final void finalize() throws Throwable {
        BasePendingResult.zal(this.zaa.zaj);
        super.finalize();
    }
}
