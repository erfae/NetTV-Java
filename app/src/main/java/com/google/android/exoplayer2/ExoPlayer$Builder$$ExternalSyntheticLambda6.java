package com.google.android.exoplayer2;

import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.common.base.Supplier;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayer$Builder$$ExternalSyntheticLambda6 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TrackSelector f$0;

    public /* synthetic */ ExoPlayer$Builder$$ExternalSyntheticLambda6(TrackSelector trackSelector, int i) {
        this.$r8$classId = i;
        this.f$0 = trackSelector;
    }

    @Override // com.google.common.base.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return ExoPlayer.Builder.lambda$setTrackSelector$18(this.f$0);
            default:
                return ExoPlayer.Builder.lambda$new$10(this.f$0);
        }
    }
}
