package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda9 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;
    public final /* synthetic */ MediaLoadData f$1;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda9(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData, int i) {
        this.$r8$classId = i;
        this.f$0 = eventTime;
        this.f$1 = mediaLoadData;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((AnalyticsListener) obj).onDownstreamFormatChanged(this.f$0, this.f$1);
                break;
            default:
                ((AnalyticsListener) obj).onUpstreamDiscarded(this.f$0, this.f$1);
                break;
        }
    }
}
