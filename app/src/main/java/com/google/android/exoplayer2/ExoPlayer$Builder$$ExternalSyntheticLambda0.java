package com.google.android.exoplayer2;

import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.util.Clock;
import com.google.common.base.Function;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayer$Builder$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyticsCollector f$0;

    public /* synthetic */ ExoPlayer$Builder$$ExternalSyntheticLambda0(AnalyticsCollector analyticsCollector, int i) {
        this.$r8$classId = i;
        this.f$0 = analyticsCollector;
    }

    @Override // com.google.common.base.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ExoPlayer.Builder.lambda$setAnalyticsCollector$21(this.f$0, (Clock) obj);
            default:
                return ExoPlayer.Builder.lambda$new$13(this.f$0, (Clock) obj);
        }
    }
}
