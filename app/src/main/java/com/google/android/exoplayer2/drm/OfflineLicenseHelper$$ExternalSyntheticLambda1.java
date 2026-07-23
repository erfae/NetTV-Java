package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.Format;
import com.google.common.util.concurrent.SettableFuture;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class OfflineLicenseHelper$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ OfflineLicenseHelper$$ExternalSyntheticLambda1(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((OfflineLicenseHelper) this.f$0).lambda$releaseManagerOnHandlerThread$4((SettableFuture) this.f$1);
                break;
            default:
                ((DefaultDrmSessionManager.PreacquiredSessionReference) this.f$0).lambda$acquire$0((Format) this.f$1);
                break;
        }
    }
}
