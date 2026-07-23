package com.nettv.livestore.activities;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class LiveChannelActivity$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LiveChannelActivity f$0;

    public /* synthetic */ LiveChannelActivity$$ExternalSyntheticLambda2(LiveChannelActivity liveChannelActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = liveChannelActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$moveTimer$7();
                break;
            default:
                this.f$0.lambda$mInfoHideTimer$6();
                break;
        }
    }
}
