package com.nettv.livestore.apps;

import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.DefaultAnalyticsCollector;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.CueGroup;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.video.VideoSize;
import com.nettv.livestore.activities.SettingActivity;
import com.nettv.livestore.dlgfragment.LanguageDlgFragment;
import com.nettv.livestore.net.NetworkTask;
import io.realm.RealmResults;
import java.util.List;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class BaseActivity$$ExternalSyntheticLambda0 implements ListenerSet.Event, ListenerSet.IterationFinishedEvent, DefaultTrackSelector.TrackInfo.Factory, LanguageDlgFragment.ItemPositionListener, NetworkTask.OnCompleteListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BaseActivity$$ExternalSyntheticLambda0(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // com.google.android.exoplayer2.trackselection.DefaultTrackSelector.TrackInfo.Factory
    public final List create(int i, TrackGroup trackGroup, int[] iArr) {
        switch (this.$r8$classId) {
            case 12:
                return DefaultTrackSelector.lambda$selectTextTrack$4((DefaultTrackSelector.Parameters) this.f$0, (String) this.f$1, i, trackGroup, iArr);
            default:
                return DefaultTrackSelector.lambda$selectVideoTrack$2((DefaultTrackSelector.Parameters) this.f$0, (int[]) this.f$1, i, trackGroup, iArr);
        }
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 1:
                ((AnalyticsListener) obj).onMetadata((AnalyticsListener.EventTime) this.f$0, (Metadata) this.f$1);
                break;
            case 2:
                ((AnalyticsListener) obj).onDeviceInfoChanged((AnalyticsListener.EventTime) this.f$0, (DeviceInfo) this.f$1);
                break;
            case 3:
                ((AnalyticsListener) obj).onTrackSelectionParametersChanged((AnalyticsListener.EventTime) this.f$0, (TrackSelectionParameters) this.f$1);
                break;
            case 4:
            default:
                ((AnalyticsListener) obj).onCues((AnalyticsListener.EventTime) this.f$0, (List<Cue>) this.f$1);
                break;
            case 5:
                DefaultAnalyticsCollector.lambda$onVideoSizeChanged$57((AnalyticsListener.EventTime) this.f$0, (VideoSize) this.f$1, (AnalyticsListener) obj);
                break;
            case 6:
                ((AnalyticsListener) obj).onTracksChanged((AnalyticsListener.EventTime) this.f$0, (Tracks) this.f$1);
                break;
            case 7:
                ((AnalyticsListener) obj).onAudioAttributesChanged((AnalyticsListener.EventTime) this.f$0, (AudioAttributes) this.f$1);
                break;
            case 8:
                ((AnalyticsListener) obj).onAvailableCommandsChanged((AnalyticsListener.EventTime) this.f$0, (Player.Commands) this.f$1);
                break;
            case 9:
                ((AnalyticsListener) obj).onCues((AnalyticsListener.EventTime) this.f$0, (CueGroup) this.f$1);
                break;
            case 10:
                ((AnalyticsListener) obj).onPlaybackParametersChanged((AnalyticsListener.EventTime) this.f$0, (PlaybackParameters) this.f$1);
                break;
        }
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.IterationFinishedEvent
    public final void invoke(Object obj, FlagSet flagSet) {
        ((DefaultAnalyticsCollector) this.f$0).lambda$setPlayer$1((Player) this.f$1, (AnalyticsListener) obj, flagSet);
    }

    @Override // com.nettv.livestore.net.NetworkTask.OnCompleteListener
    public final void onComplete(Object obj) {
        ((BaseActivity) this.f$0).lambda$getEpisodeModels$17((RealmResults) this.f$1, (List) obj);
    }

    @Override // com.nettv.livestore.dlgfragment.LanguageDlgFragment.ItemPositionListener
    public final void onItemPosition(int i) {
        ((SettingActivity) this.f$0).lambda$showChangeLangDlgFragment$2((List) this.f$1, i);
    }
}
