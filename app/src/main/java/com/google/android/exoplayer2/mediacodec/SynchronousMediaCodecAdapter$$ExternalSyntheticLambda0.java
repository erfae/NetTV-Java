package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class SynchronousMediaCodecAdapter$$ExternalSyntheticLambda0 implements MediaCodec.OnFrameRenderedListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ MediaCodecAdapter.OnFrameRenderedListener f$1;

    public /* synthetic */ SynchronousMediaCodecAdapter$$ExternalSyntheticLambda0(Object obj, MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = onFrameRenderedListener;
    }

    @Override // android.media.MediaCodec.OnFrameRenderedListener
    public final void onFrameRendered(MediaCodec mediaCodec, long j, long j2) {
        switch (this.$r8$classId) {
            case 0:
                ((SynchronousMediaCodecAdapter) this.f$0).lambda$setOnFrameRenderedListener$0(this.f$1, mediaCodec, j, j2);
                break;
            default:
                ((AsynchronousMediaCodecAdapter) this.f$0).lambda$setOnFrameRenderedListener$0(this.f$1, mediaCodec, j, j2);
                break;
        }
    }
}
