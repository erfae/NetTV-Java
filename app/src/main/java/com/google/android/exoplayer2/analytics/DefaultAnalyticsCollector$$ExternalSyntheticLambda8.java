package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda8 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;
    public final /* synthetic */ LoadEventInfo f$1;
    public final /* synthetic */ MediaLoadData f$2;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda8(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, int i) {
        this.$r8$classId = i;
        this.f$0 = eventTime;
        this.f$1 = loadEventInfo;
        this.f$2 = mediaLoadData;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((AnalyticsListener) obj).onLoadCanceled(this.f$0, this.f$1, this.f$2);
                break;
            case 1:
                ((AnalyticsListener) obj).onLoadStarted(this.f$0, this.f$1, this.f$2);
                break;
            default:
                ((AnalyticsListener) obj).onLoadCompleted(this.f$0, this.f$1, this.f$2);
                break;
        }
    }
}
