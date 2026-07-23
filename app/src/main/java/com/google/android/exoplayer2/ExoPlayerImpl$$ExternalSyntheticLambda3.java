package com.google.android.exoplayer2;

import com.google.android.exoplayer2.util.ListenerSet;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayerImpl$$ExternalSyntheticLambda3 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaMetadata f$0;

    public /* synthetic */ ExoPlayerImpl$$ExternalSyntheticLambda3(MediaMetadata mediaMetadata, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaMetadata;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((Player.Listener) obj).onMediaMetadataChanged(this.f$0);
                break;
            default:
                ((Player.Listener) obj).onMediaMetadataChanged(this.f$0);
                break;
        }
    }
}
