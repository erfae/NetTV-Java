package com.nettv.livestore.activities;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class MoviePlayerActivity$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MoviePlayerActivity f$0;

    public /* synthetic */ MoviePlayerActivity$$ExternalSyntheticLambda3(MoviePlayerActivity moviePlayerActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = moviePlayerActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$resolutionTimer$3();
                break;
            default:
                this.f$0.lambda$listTimer$2();
                break;
        }
    }
}
