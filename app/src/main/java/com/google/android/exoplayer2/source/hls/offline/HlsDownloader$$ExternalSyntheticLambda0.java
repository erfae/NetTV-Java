package com.google.android.exoplayer2.source.hls.offline;

import java.util.concurrent.Executor;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class HlsDownloader$$ExternalSyntheticLambda0 implements Executor {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ HlsDownloader$$ExternalSyntheticLambda0 INSTANCE$1 = new HlsDownloader$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ HlsDownloader$$ExternalSyntheticLambda0 INSTANCE = new HlsDownloader$$ExternalSyntheticLambda0(0);

    public /* synthetic */ HlsDownloader$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        switch (this.$r8$classId) {
            case 0:
                runnable.run();
                break;
            default:
                runnable.run();
                break;
        }
    }
}
