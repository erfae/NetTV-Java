package com.google.android.exoplayer2.source;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ProgressiveMediaPeriod$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ProgressiveMediaPeriod f$0;

    public /* synthetic */ ProgressiveMediaPeriod$$ExternalSyntheticLambda0(ProgressiveMediaPeriod progressiveMediaPeriod, int i) {
        this.$r8$classId = i;
        this.f$0 = progressiveMediaPeriod;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.maybeFinishPrepare();
                break;
            case 1:
                this.f$0.lambda$new$0();
                break;
            default:
                this.f$0.lambda$onLengthKnown$2();
                break;
        }
    }
}
