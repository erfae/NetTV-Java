package com.google.android.exoplayer2.source;

import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.analytics.PlayerId;
import com.google.android.exoplayer2.util.Consumer;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class TrackGroup$$ExternalSyntheticLambda0 implements Consumer, ProgressiveMediaExtractor.Factory, Bundleable.Creator {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ TrackGroup$$ExternalSyntheticLambda0 INSTANCE$1 = new TrackGroup$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ TrackGroup$$ExternalSyntheticLambda0 INSTANCE$2 = new TrackGroup$$ExternalSyntheticLambda0(2);
    public static final /* synthetic */ TrackGroup$$ExternalSyntheticLambda0 INSTANCE$3 = new TrackGroup$$ExternalSyntheticLambda0(3);
    public static final /* synthetic */ TrackGroup$$ExternalSyntheticLambda0 INSTANCE = new TrackGroup$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ TrackGroup$$ExternalSyntheticLambda0 INSTANCE$4 = new TrackGroup$$ExternalSyntheticLambda0(4);

    public /* synthetic */ TrackGroup$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // com.google.android.exoplayer2.util.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 1:
                SampleQueue.lambda$new$0((SampleQueue.SharedSampleMetadata) obj);
                break;
            default:
                SpannedData.lambda$new$0(obj);
                break;
        }
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor.Factory
    public final ProgressiveMediaExtractor createProgressiveMediaExtractor(PlayerId playerId) {
        return new MediaParserExtractorAdapter(playerId);
    }

    @Override // com.google.android.exoplayer2.Bundleable.Creator
    public final Bundleable fromBundle(Bundle bundle) {
        switch (this.$r8$classId) {
            case 0:
                return TrackGroup.lambda$static$0(bundle);
            default:
                return TrackGroupArray.lambda$static$0(bundle);
        }
    }
}
