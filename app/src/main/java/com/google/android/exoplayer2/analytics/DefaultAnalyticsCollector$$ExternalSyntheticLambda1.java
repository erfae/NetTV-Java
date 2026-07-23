package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda1 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda1(AnalyticsListener.EventTime eventTime, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = eventTime;
        this.f$1 = i;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((AnalyticsListener) obj).onPlaybackStateChanged(this.f$0, this.f$1);
                break;
            case 1:
                ((AnalyticsListener) obj).onAudioSessionIdChanged(this.f$0, this.f$1);
                break;
            case 2:
                ((AnalyticsListener) obj).onPlaybackSuppressionReasonChanged(this.f$0, this.f$1);
                break;
            case 3:
                ((AnalyticsListener) obj).onRepeatModeChanged(this.f$0, this.f$1);
                break;
            case 4:
                DefaultAnalyticsCollector.lambda$onDrmSessionAcquired$62(this.f$0, this.f$1, (AnalyticsListener) obj);
                break;
            default:
                ((AnalyticsListener) obj).onTimelineChanged(this.f$0, this.f$1);
                break;
        }
    }
}
