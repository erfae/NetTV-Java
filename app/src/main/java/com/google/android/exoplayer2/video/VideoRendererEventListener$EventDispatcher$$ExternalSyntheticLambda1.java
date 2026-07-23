package com.google.android.exoplayer2.video;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ VideoRendererEventListener.EventDispatcher f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda1(VideoRendererEventListener.EventDispatcher eventDispatcher, int i, long j) {
        this.f$0 = eventDispatcher;
        this.f$2 = i;
        this.f$1 = j;
    }

    public /* synthetic */ VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda1(VideoRendererEventListener.EventDispatcher eventDispatcher, long j, int i) {
        this.f$0 = eventDispatcher;
        this.f$1 = j;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$reportVideoFrameProcessingOffset$4(this.f$1, this.f$2);
                break;
            default:
                this.f$0.lambda$droppedFrames$3(this.f$2, this.f$1);
                break;
        }
    }
}
