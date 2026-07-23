package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda14 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda14(AnalyticsListener.EventTime eventTime, int i, boolean z) {
        this.$r8$classId = 0;
        this.f$0 = eventTime;
        this.f$1 = i;
        this.f$2 = z;
    }

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda14(AnalyticsListener.EventTime eventTime, boolean z, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = eventTime;
        this.f$2 = z;
        this.f$1 = i;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((AnalyticsListener) obj).onDeviceVolumeChanged(this.f$0, this.f$1, this.f$2);
                break;
            case 1:
                ((AnalyticsListener) obj).onPlayerStateChanged(this.f$0, this.f$2, this.f$1);
                break;
            default:
                ((AnalyticsListener) obj).onPlayWhenReadyChanged(this.f$0, this.f$2, this.f$1);
                break;
        }
    }
}
