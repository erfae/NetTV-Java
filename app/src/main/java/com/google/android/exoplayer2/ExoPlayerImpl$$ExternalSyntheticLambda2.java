package com.google.android.exoplayer2;

import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayerImpl$$ExternalSyntheticLambda2 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaItem f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ExoPlayerImpl$$ExternalSyntheticLambda2(MediaItem mediaItem, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = mediaItem;
        this.f$1 = i;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((Player.Listener) obj).onMediaItemTransition(this.f$0, this.f$1);
                break;
            default:
                ((Player.Listener) obj).onMediaItemTransition(this.f$0, this.f$1);
                break;
        }
    }
}
