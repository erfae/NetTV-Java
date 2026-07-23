package com.google.android.exoplayer2;

import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.common.base.Supplier;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayer$Builder$$ExternalSyntheticLambda7 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BandwidthMeter f$0;

    public /* synthetic */ ExoPlayer$Builder$$ExternalSyntheticLambda7(BandwidthMeter bandwidthMeter, int i) {
        this.$r8$classId = i;
        this.f$0 = bandwidthMeter;
    }

    @Override // com.google.common.base.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return ExoPlayer.Builder.lambda$setBandwidthMeter$20(this.f$0);
            default:
                return ExoPlayer.Builder.lambda$new$12(this.f$0);
        }
    }
}
