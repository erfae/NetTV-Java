package com.google.android.exoplayer2.source;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class MediaSourceEventListener$EventDispatcher$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaSourceEventListener.EventDispatcher f$0;
    public final /* synthetic */ MediaSourceEventListener f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ MediaLoadData f$3;

    public /* synthetic */ MediaSourceEventListener$EventDispatcher$$ExternalSyntheticLambda0(MediaSourceEventListener.EventDispatcher eventDispatcher, MediaSourceEventListener mediaSourceEventListener, Object obj, MediaLoadData mediaLoadData, int i) {
        this.$r8$classId = i;
        this.f$0 = eventDispatcher;
        this.f$1 = mediaSourceEventListener;
        this.f$2 = obj;
        this.f$3 = mediaLoadData;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$loadStarted$0(this.f$1, (LoadEventInfo) this.f$2, this.f$3);
                break;
            case 1:
                this.f$0.lambda$loadCompleted$1(this.f$1, (LoadEventInfo) this.f$2, this.f$3);
                break;
            case 2:
                this.f$0.lambda$loadCanceled$2(this.f$1, (LoadEventInfo) this.f$2, this.f$3);
                break;
            default:
                this.f$0.lambda$upstreamDiscarded$4(this.f$1, (MediaSource.MediaPeriodId) this.f$2, this.f$3);
                break;
        }
    }
}
