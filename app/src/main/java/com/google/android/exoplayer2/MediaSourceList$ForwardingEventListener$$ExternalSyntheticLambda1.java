package com.google.android.exoplayer2;

import android.util.Pair;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class MediaSourceList$ForwardingEventListener$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaSourceList.ForwardingEventListener f$0;
    public final /* synthetic */ Pair f$1;
    public final /* synthetic */ LoadEventInfo f$2;
    public final /* synthetic */ MediaLoadData f$3;

    public /* synthetic */ MediaSourceList$ForwardingEventListener$$ExternalSyntheticLambda1(MediaSourceList.ForwardingEventListener forwardingEventListener, Pair pair, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, int i) {
        this.$r8$classId = i;
        this.f$0 = forwardingEventListener;
        this.f$1 = pair;
        this.f$2 = loadEventInfo;
        this.f$3 = mediaLoadData;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$onLoadStarted$0(this.f$1, this.f$2, this.f$3);
                break;
            case 1:
                this.f$0.lambda$onLoadCanceled$2(this.f$1, this.f$2, this.f$3);
                break;
            default:
                this.f$0.lambda$onLoadCompleted$1(this.f$1, this.f$2, this.f$3);
                break;
        }
    }
}
