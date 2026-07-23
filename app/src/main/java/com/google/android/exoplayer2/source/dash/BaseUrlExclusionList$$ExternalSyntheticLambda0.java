package com.google.android.exoplayer2.source.dash;

import com.google.android.exoplayer2.metadata.mp4.SlowMotionData;
import com.google.android.exoplayer2.source.dash.manifest.BaseUrl;
import com.google.android.exoplayer2.upstream.cache.CacheSpan;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import java.util.Comparator;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class BaseUrlExclusionList$$ExternalSyntheticLambda0 implements Comparator {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ BaseUrlExclusionList$$ExternalSyntheticLambda0 INSTANCE$1 = new BaseUrlExclusionList$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ BaseUrlExclusionList$$ExternalSyntheticLambda0 INSTANCE = new BaseUrlExclusionList$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ BaseUrlExclusionList$$ExternalSyntheticLambda0 INSTANCE$2 = new BaseUrlExclusionList$$ExternalSyntheticLambda0(2);

    public /* synthetic */ BaseUrlExclusionList$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return BaseUrlExclusionList.compareBaseUrl((BaseUrl) obj, (BaseUrl) obj2);
            case 1:
                return SlowMotionData.Segment.lambda$static$0((SlowMotionData.Segment) obj, (SlowMotionData.Segment) obj2);
            default:
                return LeastRecentlyUsedCacheEvictor.compare((CacheSpan) obj, (CacheSpan) obj2);
        }
    }
}
