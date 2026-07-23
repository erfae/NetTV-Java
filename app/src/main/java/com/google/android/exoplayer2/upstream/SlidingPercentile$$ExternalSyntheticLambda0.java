package com.google.android.exoplayer2.upstream;

import java.util.Comparator;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class SlidingPercentile$$ExternalSyntheticLambda0 implements Comparator {
    public static final /* synthetic */ SlidingPercentile$$ExternalSyntheticLambda0 INSTANCE = new SlidingPercentile$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ SlidingPercentile$$ExternalSyntheticLambda0 INSTANCE$1 = new SlidingPercentile$$ExternalSyntheticLambda0(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SlidingPercentile$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return SlidingPercentile.lambda$static$0((SlidingPercentile.Sample) obj, (SlidingPercentile.Sample) obj2);
            default:
                return SlidingPercentile.lambda$static$1((SlidingPercentile.Sample) obj, (SlidingPercentile.Sample) obj2);
        }
    }
}
