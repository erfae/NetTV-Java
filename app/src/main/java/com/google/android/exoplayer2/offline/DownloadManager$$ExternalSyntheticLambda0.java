package com.google.android.exoplayer2.offline;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DownloadManager$$ExternalSyntheticLambda0 implements Handler.Callback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DownloadManager$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        switch (this.$r8$classId) {
            case 0:
                return ((DownloadManager) this.f$0).handleMainMessage(message);
            default:
                return ((DownloadHelper.MediaPreparer) this.f$0).handleDownloadHelperCallbackMessage(message);
        }
    }
}
