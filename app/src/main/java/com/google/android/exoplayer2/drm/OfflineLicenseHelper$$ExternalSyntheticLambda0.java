package com.google.android.exoplayer2.drm;

import com.google.common.util.concurrent.SettableFuture;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class OfflineLicenseHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OfflineLicenseHelper f$0;
    public final /* synthetic */ DrmSession f$1;
    public final /* synthetic */ SettableFuture f$2;

    public /* synthetic */ OfflineLicenseHelper$$ExternalSyntheticLambda0(OfflineLicenseHelper offlineLicenseHelper, DrmSession drmSession, SettableFuture settableFuture) {
        this.$r8$classId = 0;
        this.f$0 = offlineLicenseHelper;
        this.f$1 = drmSession;
        this.f$2 = settableFuture;
    }

    public /* synthetic */ OfflineLicenseHelper$$ExternalSyntheticLambda0(OfflineLicenseHelper offlineLicenseHelper, SettableFuture settableFuture, DrmSession drmSession, int i) {
        this.$r8$classId = i;
        this.f$0 = offlineLicenseHelper;
        this.f$2 = settableFuture;
        this.f$1 = drmSession;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$acquireFirstSessionOnHandlerThread$3(this.f$1, this.f$2);
                break;
            case 1:
                this.f$0.lambda$acquireSessionAndGetOfflineLicenseKeySetIdOnHandlerThread$1(this.f$2, this.f$1);
                break;
            default:
                this.f$0.lambda$getLicenseDurationRemainingSec$0(this.f$2, this.f$1);
                break;
        }
    }
}
