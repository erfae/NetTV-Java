package com.google.android.exoplayer2.drm;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultDrmSessionManager$ReferenceCountListenerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DefaultDrmSessionManager$ReferenceCountListenerImpl$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((DefaultDrmSession) this.f$0).release(null);
                break;
            default:
                ((DefaultDrmSessionManager.PreacquiredSessionReference) this.f$0).lambda$release$1();
                break;
        }
    }
}
