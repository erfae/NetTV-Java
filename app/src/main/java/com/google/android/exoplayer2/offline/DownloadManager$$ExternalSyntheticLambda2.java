package com.google.android.exoplayer2.offline;

import java.util.concurrent.Executor;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DownloadManager$$ExternalSyntheticLambda2 implements Executor {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ DownloadManager$$ExternalSyntheticLambda2 INSTANCE$1 = new DownloadManager$$ExternalSyntheticLambda2(1);
    public static final /* synthetic */ DownloadManager$$ExternalSyntheticLambda2 INSTANCE = new DownloadManager$$ExternalSyntheticLambda2(0);
    public static final /* synthetic */ DownloadManager$$ExternalSyntheticLambda2 INSTANCE$2 = new DownloadManager$$ExternalSyntheticLambda2(2);

    public /* synthetic */ DownloadManager$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        switch (this.$r8$classId) {
            case 0:
                runnable.run();
                break;
            case 1:
                runnable.run();
                break;
            default:
                runnable.run();
                break;
        }
    }
}
