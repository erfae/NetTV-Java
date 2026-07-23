package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda13 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda13(AnalyticsListener.EventTime eventTime, int i, long j) {
        this.f$0 = eventTime;
        this.f$2 = i;
        this.f$1 = j;
    }

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda13(AnalyticsListener.EventTime eventTime, long j, int i) {
        this.f$0 = eventTime;
        this.f$1 = j;
        this.f$2 = i;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((AnalyticsListener) obj).onVideoFrameProcessingOffset(this.f$0, this.f$1, this.f$2);
                break;
            default:
                ((AnalyticsListener) obj).onDroppedVideoFrames(this.f$0, this.f$2, this.f$1);
                break;
        }
    }
}
