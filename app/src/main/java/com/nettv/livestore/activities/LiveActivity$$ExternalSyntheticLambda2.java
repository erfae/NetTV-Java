package com.nettv.livestore.activities;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class LiveActivity$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LiveActivity f$0;

    public /* synthetic */ LiveActivity$$ExternalSyntheticLambda2(LiveActivity liveActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = liveActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$moveTimer$8();
                break;
            default:
                this.f$0.lambda$mInfoHideTimer$7();
                break;
        }
    }
}
