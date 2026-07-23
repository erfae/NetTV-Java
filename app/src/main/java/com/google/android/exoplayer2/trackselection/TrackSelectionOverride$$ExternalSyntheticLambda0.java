package com.google.android.exoplayer2.trackselection;

import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class TrackSelectionOverride$$ExternalSyntheticLambda0 implements Bundleable.Creator {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ TrackSelectionOverride$$ExternalSyntheticLambda0 INSTANCE$1 = new TrackSelectionOverride$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ TrackSelectionOverride$$ExternalSyntheticLambda0 INSTANCE$2 = new TrackSelectionOverride$$ExternalSyntheticLambda0(2);
    public static final /* synthetic */ TrackSelectionOverride$$ExternalSyntheticLambda0 INSTANCE = new TrackSelectionOverride$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ TrackSelectionOverride$$ExternalSyntheticLambda0 INSTANCE$3 = new TrackSelectionOverride$$ExternalSyntheticLambda0(3);

    public /* synthetic */ TrackSelectionOverride$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // com.google.android.exoplayer2.Bundleable.Creator
    public final Bundleable fromBundle(Bundle bundle) {
        switch (this.$r8$classId) {
            case 0:
                return TrackSelectionOverride.lambda$static$0(bundle);
            case 1:
                return DefaultTrackSelector.Parameters.lambda$static$0(bundle);
            case 2:
                return DefaultTrackSelector.SelectionOverride.lambda$static$0(bundle);
            default:
                return TrackSelectionParameters.fromBundle(bundle);
        }
    }
}
