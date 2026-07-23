package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda2 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ long f$3;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda2(AnalyticsListener.EventTime eventTime, int i, long j, long j2, int i2) {
        this.$r8$classId = i2;
        this.f$0 = eventTime;
        this.f$1 = i;
        this.f$2 = j;
        this.f$3 = j2;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((AnalyticsListener) obj).onAudioUnderrun(this.f$0, this.f$1, this.f$2, this.f$3);
                break;
            default:
                ((AnalyticsListener) obj).onBandwidthEstimate(this.f$0, this.f$1, this.f$2, this.f$3);
                break;
        }
    }
}
