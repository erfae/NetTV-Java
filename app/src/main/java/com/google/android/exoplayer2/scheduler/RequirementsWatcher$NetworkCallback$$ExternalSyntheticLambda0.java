package com.google.android.exoplayer2.scheduler;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class RequirementsWatcher$NetworkCallback$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RequirementsWatcher.NetworkCallback f$0;

    public /* synthetic */ RequirementsWatcher$NetworkCallback$$ExternalSyntheticLambda0(RequirementsWatcher.NetworkCallback networkCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = networkCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$postRecheckNotMetNetworkRequirements$1();
                break;
            default:
                this.f$0.lambda$postCheckRequirements$0();
                break;
        }
    }
}
