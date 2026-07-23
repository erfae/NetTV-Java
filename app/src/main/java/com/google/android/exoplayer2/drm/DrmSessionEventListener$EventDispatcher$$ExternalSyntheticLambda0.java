package com.google.android.exoplayer2.drm;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DrmSessionEventListener$EventDispatcher$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DrmSessionEventListener.EventDispatcher f$0;
    public final /* synthetic */ DrmSessionEventListener f$1;

    public /* synthetic */ DrmSessionEventListener$EventDispatcher$$ExternalSyntheticLambda0(DrmSessionEventListener.EventDispatcher eventDispatcher, DrmSessionEventListener drmSessionEventListener, int i) {
        this.$r8$classId = i;
        this.f$0 = eventDispatcher;
        this.f$1 = drmSessionEventListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$drmSessionReleased$5(this.f$1);
                break;
            case 1:
                this.f$0.lambda$drmKeysRemoved$4(this.f$1);
                break;
            case 2:
                this.f$0.lambda$drmKeysLoaded$1(this.f$1);
                break;
            default:
                this.f$0.lambda$drmKeysRestored$3(this.f$1);
                break;
        }
    }
}
