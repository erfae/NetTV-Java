package com.google.android.exoplayer2.offline;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DownloadHelper$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DownloadHelper$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((DownloadHelper) this.f$0).lambda$onMediaPrepared$4();
                break;
            default:
                ((DownloadService.ForegroundNotificationUpdater) this.f$0).update();
                break;
        }
    }
}
