package com.google.android.exoplayer2.offline;

import java.util.Comparator;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DownloadManager$InternalHandler$$ExternalSyntheticLambda0 implements Comparator {
    public static final /* synthetic */ DownloadManager$InternalHandler$$ExternalSyntheticLambda0 INSTANCE = new DownloadManager$InternalHandler$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ DownloadManager$InternalHandler$$ExternalSyntheticLambda0 INSTANCE$1 = new DownloadManager$InternalHandler$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ DownloadManager$InternalHandler$$ExternalSyntheticLambda0 INSTANCE$2 = new DownloadManager$InternalHandler$$ExternalSyntheticLambda0(2);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DownloadManager$InternalHandler$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0002. Please report as an issue. */
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.$r8$classId) {
        }
        return DownloadManager.InternalHandler.compareStartTimes((Download) obj, (Download) obj2);
    }
}
