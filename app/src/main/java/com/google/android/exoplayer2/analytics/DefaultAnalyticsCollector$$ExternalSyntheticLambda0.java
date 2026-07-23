package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda0 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda0(AnalyticsListener.EventTime eventTime, int i) {
        this.$r8$classId = i;
        this.f$0 = eventTime;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((AnalyticsListener) obj).onSeekStarted(this.f$0);
                break;
            case 1:
                ((AnalyticsListener) obj).onDrmKeysLoaded(this.f$0);
                break;
            case 2:
                ((AnalyticsListener) obj).onDrmSessionReleased(this.f$0);
                break;
            case 3:
                ((AnalyticsListener) obj).onPlayerReleased(this.f$0);
                break;
            case 4:
                ((AnalyticsListener) obj).onDrmKeysRemoved(this.f$0);
                break;
            case 5:
                ((AnalyticsListener) obj).onDrmKeysRestored(this.f$0);
                break;
            default:
                ((AnalyticsListener) obj).onSeekProcessed(this.f$0);
                break;
        }
    }
}
