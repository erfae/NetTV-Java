package com.google.android.exoplayer2;

import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayerImpl$$ExternalSyntheticLambda10 implements ListenerSet.IterationFinishedEvent, ExoPlayerImplInternal.PlaybackInfoUpdateListener, ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ExoPlayerImpl f$0;

    public /* synthetic */ ExoPlayerImpl$$ExternalSyntheticLambda10(ExoPlayerImpl exoPlayerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = exoPlayerImpl;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 2:
                this.f$0.lambda$setPlaylistMetadata$7((Player.Listener) obj);
                break;
            default:
                this.f$0.lambda$updateAvailableCommands$26((Player.Listener) obj);
                break;
        }
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.IterationFinishedEvent
    public final void invoke(Object obj, FlagSet flagSet) {
        this.f$0.lambda$new$0((Player.Listener) obj, flagSet);
    }

    @Override // com.google.android.exoplayer2.ExoPlayerImplInternal.PlaybackInfoUpdateListener
    public final void onPlaybackInfoUpdate(ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
        this.f$0.lambda$new$2(playbackInfoUpdate);
    }
}
