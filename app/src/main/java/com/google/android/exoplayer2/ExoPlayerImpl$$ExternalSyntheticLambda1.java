package com.google.android.exoplayer2;

import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayerImpl$$ExternalSyntheticLambda1 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ Player.PositionInfo f$1;
    public final /* synthetic */ Player.PositionInfo f$2;

    public /* synthetic */ ExoPlayerImpl$$ExternalSyntheticLambda1(int i, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
        this.f$1 = positionInfo;
        this.f$2 = positionInfo2;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ExoPlayerImpl.lambda$updatePlaybackInfo$13(this.f$0, this.f$1, this.f$2, (Player.Listener) obj);
                break;
            default:
                SimpleBasePlayer.lambda$updateStateAndInformListeners$26(this.f$0, this.f$1, this.f$2, (Player.Listener) obj);
                break;
        }
    }
}
