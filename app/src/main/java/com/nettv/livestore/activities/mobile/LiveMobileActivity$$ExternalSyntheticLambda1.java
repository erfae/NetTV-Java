package com.nettv.livestore.activities.mobile;

import com.nettv.livestore.helper.RealmChangeItemListener;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class LiveMobileActivity$$ExternalSyntheticLambda1 implements RealmChangeItemListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LiveMobileActivity f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ LiveMobileActivity$$ExternalSyntheticLambda1(LiveMobileActivity liveMobileActivity, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = liveMobileActivity;
        this.f$1 = i;
    }

    @Override // com.nettv.livestore.helper.RealmChangeItemListener
    public final void onItemChanged() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$controlFav$5(this.f$1);
                break;
            default:
                this.f$0.lambda$controlLock$6(this.f$1);
                break;
        }
    }
}
