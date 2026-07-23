package com.nettv.livestore.activities;

import com.nettv.livestore.helper.RealmChangeItemListener;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class LiveChannelActivity$$ExternalSyntheticLambda0 implements RealmChangeItemListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LiveChannelActivity f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ LiveChannelActivity$$ExternalSyntheticLambda0(LiveChannelActivity liveChannelActivity, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = liveChannelActivity;
        this.f$1 = i;
    }

    @Override // com.nettv.livestore.helper.RealmChangeItemListener
    public final void onItemChanged() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$controlLock$5(this.f$1);
                break;
            default:
                this.f$0.lambda$controlFav$4(this.f$1);
                break;
        }
    }
}
