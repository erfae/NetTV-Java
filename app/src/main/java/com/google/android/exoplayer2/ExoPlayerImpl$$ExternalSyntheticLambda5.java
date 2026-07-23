package com.google.android.exoplayer2;

import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayerImpl$$ExternalSyntheticLambda5 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ExoPlayerImpl$$ExternalSyntheticLambda5(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ExoPlayerImpl.lambda$updatePlaybackInfo$12((PlaybackInfo) this.f$0, this.f$1, (Player.Listener) obj);
                break;
            case 1:
                ExoPlayerImpl.lambda$updatePlaybackInfo$22((PlaybackInfo) this.f$0, this.f$1, (Player.Listener) obj);
                break;
            default:
                SimpleBasePlayer.lambda$updateStateAndInformListeners$25((SimpleBasePlayer.State) this.f$0, this.f$1, (Player.Listener) obj);
                break;
        }
    }
}
