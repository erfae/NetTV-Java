package com.nettv.livestore.activities;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SeriesPlayerActivity$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SeriesPlayerActivity f$0;

    public /* synthetic */ SeriesPlayerActivity$$ExternalSyntheticLambda1(SeriesPlayerActivity seriesPlayerActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = seriesPlayerActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$listTimer$1();
                break;
            default:
                this.f$0.lambda$resolutionTimer$2();
                break;
        }
    }
}
