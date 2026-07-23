package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda7 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;
    public final /* synthetic */ DecoderCounters f$1;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda7(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters, int i) {
        this.$r8$classId = i;
        this.f$0 = eventTime;
        this.f$1 = decoderCounters;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DefaultAnalyticsCollector.lambda$onVideoEnabled$13(this.f$0, this.f$1, (AnalyticsListener) obj);
                break;
            case 1:
                DefaultAnalyticsCollector.lambda$onVideoDisabled$18(this.f$0, this.f$1, (AnalyticsListener) obj);
                break;
            case 2:
                DefaultAnalyticsCollector.lambda$onAudioEnabled$3(this.f$0, this.f$1, (AnalyticsListener) obj);
                break;
            default:
                DefaultAnalyticsCollector.lambda$onAudioDisabled$9(this.f$0, this.f$1, (AnalyticsListener) obj);
                break;
        }
    }
}
