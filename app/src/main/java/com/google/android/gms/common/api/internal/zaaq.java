package com.google.android.gms.common.api.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zaaq extends zabg {
    public final /* synthetic */ zaaw zaa;
    public final /* synthetic */ com.google.android.gms.signin.internal.zak zab;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaaq(zabf zabfVar, zaaw zaawVar, com.google.android.gms.signin.internal.zak zakVar) {
        super(zabfVar);
        this.zaa = zaawVar;
        this.zab = zakVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabg
    public final void zaa() {
        zaaw.zar(this.zaa, this.zab);
    }
}
