package com.google.android.exoplayer2.offline;

import java.io.IOException;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DownloadHelper$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DownloadHelper$$ExternalSyntheticLambda5(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((DownloadHelper) this.f$0).lambda$onMediaPreparationFailed$5((IOException) this.f$1);
                break;
            case 1:
                ((DownloadService.DownloadManagerHelper) this.f$0).lambda$attachService$0((DownloadService) this.f$1);
                break;
            default:
                ((DownloadHelper) this.f$0).lambda$prepare$3((DownloadHelper.Callback) this.f$1);
                break;
        }
    }
}
