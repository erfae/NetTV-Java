package com.google.android.exoplayer2.video;

import com.google.android.exoplayer2.decoder.DecoderCounters;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VideoRendererEventListener.EventDispatcher f$0;
    public final /* synthetic */ DecoderCounters f$1;

    public /* synthetic */ VideoRendererEventListener$EventDispatcher$$ExternalSyntheticLambda0(VideoRendererEventListener.EventDispatcher eventDispatcher, DecoderCounters decoderCounters, int i) {
        this.$r8$classId = i;
        this.f$0 = eventDispatcher;
        this.f$1 = decoderCounters;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$disabled$8(this.f$1);
                break;
            default:
                this.f$0.lambda$enabled$0(this.f$1);
                break;
        }
    }
}
