package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda12 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ long f$3;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda12(AnalyticsListener.EventTime eventTime, String str, long j, long j2, int i) {
        this.$r8$classId = i;
        this.f$0 = eventTime;
        this.f$1 = str;
        this.f$2 = j;
        this.f$3 = j2;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DefaultAnalyticsCollector.lambda$onVideoDecoderInitialized$14(this.f$0, this.f$1, this.f$2, this.f$3, (AnalyticsListener) obj);
                break;
            default:
                DefaultAnalyticsCollector.lambda$onAudioDecoderInitialized$4(this.f$0, this.f$1, this.f$2, this.f$3, (AnalyticsListener) obj);
                break;
        }
    }
}
