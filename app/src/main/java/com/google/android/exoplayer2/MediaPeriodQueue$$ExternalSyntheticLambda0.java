package com.google.android.exoplayer2;

import android.util.Pair;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.common.collect.ImmutableList;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class MediaPeriodQueue$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ MediaPeriodQueue$$ExternalSyntheticLambda0(Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((MediaPeriodQueue) this.f$0).lambda$notifyQueueUpdate$0((ImmutableList.Builder) this.f$1, (MediaSource.MediaPeriodId) this.f$2);
                break;
            default:
                ((MediaSourceList.ForwardingEventListener) this.f$0).lambda$onDrmSessionManagerError$8((Pair) this.f$1, (Exception) this.f$2);
                break;
        }
    }
}
