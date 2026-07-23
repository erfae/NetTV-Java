package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda6 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;
    public final /* synthetic */ PlaybackException f$1;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda6(AnalyticsListener.EventTime eventTime, PlaybackException playbackException, int i) {
        this.$r8$classId = i;
        this.f$0 = eventTime;
        this.f$1 = playbackException;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((AnalyticsListener) obj).onPlayerErrorChanged(this.f$0, this.f$1);
                break;
            default:
                ((AnalyticsListener) obj).onPlayerError(this.f$0, this.f$1);
                break;
        }
    }
}
