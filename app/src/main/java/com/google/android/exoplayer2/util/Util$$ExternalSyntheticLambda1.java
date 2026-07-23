package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Util$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ Util$$ExternalSyntheticLambda1(SettableFuture settableFuture, Runnable runnable, Object obj) {
        this.$r8$classId = 4;
        this.f$1 = settableFuture;
        this.f$0 = runnable;
        this.f$2 = obj;
    }

    public /* synthetic */ Util$$ExternalSyntheticLambda1(Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Util.lambda$transformFutureAsync$2((ListenableFuture) this.f$0, (SettableFuture) this.f$1, (AsyncFunction) this.f$2);
                break;
            case 1:
                ((AudioRendererEventListener.EventDispatcher) this.f$0).lambda$inputFormatChanged$2((Format) this.f$1, (DecoderReuseEvaluation) this.f$2);
                break;
            case 2:
                ((DrmSessionEventListener.EventDispatcher) this.f$0).lambda$drmSessionManagerError$2((DrmSessionEventListener) this.f$1, (Exception) this.f$2);
                break;
            case 3:
                ((MediaSourceEventListener.EventDispatcher) this.f$0).lambda$downstreamFormatChanged$5((MediaSourceEventListener) this.f$1, (MediaLoadData) this.f$2);
                break;
            case 4:
                Util.lambda$postOrRunWithCompletion$0((SettableFuture) this.f$1, (Runnable) this.f$0, this.f$2);
                break;
            default:
                ((VideoRendererEventListener.EventDispatcher) this.f$0).lambda$inputFormatChanged$2((Format) this.f$1, (DecoderReuseEvaluation) this.f$2);
                break;
        }
    }
}
