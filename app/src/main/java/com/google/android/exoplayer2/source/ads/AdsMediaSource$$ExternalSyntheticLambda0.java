package com.google.android.exoplayer2.source.ads;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class AdsMediaSource$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AdsMediaSource f$0;
    public final /* synthetic */ AdsMediaSource.ComponentListener f$1;

    public /* synthetic */ AdsMediaSource$$ExternalSyntheticLambda0(AdsMediaSource adsMediaSource, AdsMediaSource.ComponentListener componentListener, int i) {
        this.$r8$classId = i;
        this.f$0 = adsMediaSource;
        this.f$1 = componentListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$releaseSourceInternal$1(this.f$1);
                break;
            default:
                this.f$0.lambda$prepareSourceInternal$0(this.f$1);
                break;
        }
    }
}
