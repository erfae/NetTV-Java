package com.google.android.exoplayer2.source.ads;

import com.google.android.exoplayer2.source.MediaSource;
import com.google.common.collect.ImmutableMap;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class AdsMediaSource$AdPrepareListener$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AdsMediaSource$AdPrepareListener$$ExternalSyntheticLambda0(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((AdsMediaSource.AdPrepareListener) this.f$0).lambda$onPrepareComplete$0((MediaSource.MediaPeriodId) this.f$1);
                break;
            case 1:
                ((AdsMediaSource.ComponentListener) this.f$0).lambda$onAdPlaybackState$0((AdPlaybackState) this.f$1);
                break;
            default:
                ((ServerSideAdInsertionMediaSource) this.f$0).lambda$setAdPlaybackStates$0((ImmutableMap) this.f$1);
                break;
        }
    }
}
