package com.google.android.exoplayer2.upstream;

import com.google.common.base.Predicate;
import java.util.Map;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class HttpDataSource$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ HttpDataSource$$ExternalSyntheticLambda0 INSTANCE$1 = new HttpDataSource$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ HttpDataSource$$ExternalSyntheticLambda0 INSTANCE$2 = new HttpDataSource$$ExternalSyntheticLambda0(2);
    public static final /* synthetic */ HttpDataSource$$ExternalSyntheticLambda0 INSTANCE = new HttpDataSource$$ExternalSyntheticLambda0(0);

    public /* synthetic */ HttpDataSource$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // com.google.common.base.Predicate
    public final boolean apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return HttpDataSource.CC.lambda$static$0((String) obj);
            case 1:
                return DefaultHttpDataSource.NullFilteringHeadersMap.lambda$keySet$0((String) obj);
            default:
                return DefaultHttpDataSource.NullFilteringHeadersMap.lambda$entrySet$1((Map.Entry) obj);
        }
    }
}
