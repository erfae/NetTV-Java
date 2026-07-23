package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.decoder.DecoderCounters;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioRendererEventListener.EventDispatcher f$0;
    public final /* synthetic */ DecoderCounters f$1;

    public /* synthetic */ AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda0(AudioRendererEventListener.EventDispatcher eventDispatcher, DecoderCounters decoderCounters, int i) {
        this.$r8$classId = i;
        this.f$0 = eventDispatcher;
        this.f$1 = decoderCounters;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$disabled$6(this.f$1);
                break;
            default:
                this.f$0.lambda$enabled$0(this.f$1);
                break;
        }
    }
}
