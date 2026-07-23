package com.nettv.livestore.activities;

import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.DefaultAnalyticsCollector;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.util.ListenerSet;
import com.nettv.livestore.dlgfragment.AudioTrackDlgFragment;
import com.nettv.livestore.dlgfragment.SubtitleTrackDlgFragment;
import java.util.List;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class MoviePlayerActivity$$ExternalSyntheticLambda1 implements SubtitleTrackDlgFragment.ItemPositionListener, AudioTrackDlgFragment.ItemPositionListener, ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Bundleable f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ MoviePlayerActivity$$ExternalSyntheticLambda1(AnalyticsListener.EventTime eventTime, int i, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2) {
        this.$r8$classId = 2;
        this.f$0 = eventTime;
        this.f$3 = i;
        this.f$1 = positionInfo;
        this.f$2 = positionInfo2;
    }

    public /* synthetic */ MoviePlayerActivity$$ExternalSyntheticLambda1(MoviePlayerActivity moviePlayerActivity, List list, TrackGroupArray trackGroupArray, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = moviePlayerActivity;
        this.f$1 = list;
        this.f$2 = trackGroupArray;
        this.f$3 = i;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        DefaultAnalyticsCollector.lambda$onPositionDiscontinuity$43((AnalyticsListener.EventTime) this.f$0, this.f$3, (Player.PositionInfo) this.f$1, (Player.PositionInfo) this.f$2, (AnalyticsListener) obj);
    }

    @Override // com.nettv.livestore.dlgfragment.SubtitleTrackDlgFragment.ItemPositionListener
    public final void onItemPosition(int i) {
        switch (this.$r8$classId) {
            case 0:
                ((MoviePlayerActivity) this.f$0).lambda$showSubTitleTrackDlgFragment$5((List) this.f$1, (TrackGroupArray) this.f$2, this.f$3, i);
                break;
            default:
                ((MoviePlayerActivity) this.f$0).lambda$showAudioTrackDlgFragment$4((List) this.f$1, (TrackGroupArray) this.f$2, this.f$3, i);
                break;
        }
    }
}
