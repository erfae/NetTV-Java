package com.google.android.exoplayer2;

import com.google.common.util.concurrent.ListenableFuture;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayerImpl$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ExoPlayerImpl$$ExternalSyntheticLambda11(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((ExoPlayerImpl) this.f$0).lambda$new$1((ExoPlayerImplInternal.PlaybackInfoUpdate) this.f$1);
                break;
            case 1:
                ((ExoPlayerImplInternal) this.f$0).lambda$sendMessageToTargetThread$1((PlayerMessage) this.f$1);
                break;
            default:
                ((SimpleBasePlayer) this.f$0).lambda$updateStateForPendingOperation$55((ListenableFuture) this.f$1);
                break;
        }
    }
}
