package com.google.android.exoplayer2;

import android.util.Pair;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class MediaSourceList$ForwardingEventListener$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaSourceList.ForwardingEventListener f$0;
    public final /* synthetic */ Pair f$1;

    public /* synthetic */ MediaSourceList$ForwardingEventListener$$ExternalSyntheticLambda0(MediaSourceList.ForwardingEventListener forwardingEventListener, Pair pair, int i) {
        this.$r8$classId = i;
        this.f$0 = forwardingEventListener;
        this.f$1 = pair;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$onDrmKeysRemoved$10(this.f$1);
                break;
            case 1:
                this.f$0.lambda$onDrmSessionReleased$11(this.f$1);
                break;
            case 2:
                this.f$0.lambda$onDrmKeysLoaded$7(this.f$1);
                break;
            default:
                this.f$0.lambda$onDrmKeysRestored$9(this.f$1);
                break;
        }
    }
}
