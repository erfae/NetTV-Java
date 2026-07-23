package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda4 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;
    public final /* synthetic */ Format f$1;
    public final /* synthetic */ DecoderReuseEvaluation f$2;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda4(AnalyticsListener.EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation, int i) {
        this.$r8$classId = i;
        this.f$0 = eventTime;
        this.f$1 = format;
        this.f$2 = decoderReuseEvaluation;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DefaultAnalyticsCollector.lambda$onAudioInputFormatChanged$5(this.f$0, this.f$1, this.f$2, (AnalyticsListener) obj);
                break;
            default:
                DefaultAnalyticsCollector.lambda$onVideoInputFormatChanged$15(this.f$0, this.f$1, this.f$2, (AnalyticsListener) obj);
                break;
        }
    }
}
