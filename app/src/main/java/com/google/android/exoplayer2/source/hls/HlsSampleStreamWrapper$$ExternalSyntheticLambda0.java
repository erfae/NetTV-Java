package com.google.android.exoplayer2.source.hls;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class HlsSampleStreamWrapper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ HlsSampleStreamWrapper$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((HlsSampleStreamWrapper) this.f$0).maybeFinishPrepare();
                break;
            case 1:
                ((HlsSampleStreamWrapper) this.f$0).onTracksEnded();
                break;
            default:
                ((HlsSampleStreamWrapper.Callback) this.f$0).onPrepared();
                break;
        }
    }
}
