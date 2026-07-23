package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultAnalyticsCollector$$ExternalSyntheticLambda5 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsListener.EventTime f$0;
    public final /* synthetic */ MediaMetadata f$1;

    public /* synthetic */ DefaultAnalyticsCollector$$ExternalSyntheticLambda5(AnalyticsListener.EventTime eventTime, MediaMetadata mediaMetadata, int i) {
        this.$r8$classId = i;
        this.f$0 = eventTime;
        this.f$1 = mediaMetadata;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((AnalyticsListener) obj).onMediaMetadataChanged(this.f$0, this.f$1);
                break;
            default:
                ((AnalyticsListener) obj).onPlaylistMetadataChanged(this.f$0, this.f$1);
                break;
        }
    }
}
