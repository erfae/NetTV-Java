package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.Format;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class BaseTrackSelection$$ExternalSyntheticLambda0 implements Comparator {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE$1 = new BaseTrackSelection$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE$2 = new BaseTrackSelection$$ExternalSyntheticLambda0(2);
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE$3 = new BaseTrackSelection$$ExternalSyntheticLambda0(3);
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE$4 = new BaseTrackSelection$$ExternalSyntheticLambda0(4);
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE$5 = new BaseTrackSelection$$ExternalSyntheticLambda0(5);
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE$6 = new BaseTrackSelection$$ExternalSyntheticLambda0(6);
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE$7 = new BaseTrackSelection$$ExternalSyntheticLambda0(7);
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE$8 = new BaseTrackSelection$$ExternalSyntheticLambda0(8);
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE$9 = new BaseTrackSelection$$ExternalSyntheticLambda0(9);
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE = new BaseTrackSelection$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE$10 = new BaseTrackSelection$$ExternalSyntheticLambda0(10);
    public static final /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0 INSTANCE$11 = new BaseTrackSelection$$ExternalSyntheticLambda0(11);

    public /* synthetic */ BaseTrackSelection$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return BaseTrackSelection.lambda$new$0((Format) obj, (Format) obj2);
            case 1:
                return DefaultTrackSelector.TextTrackInfo.compareSelections((List) obj, (List) obj2);
            case 2:
                return DefaultTrackSelector.VideoTrackInfo.compareSelections((List) obj, (List) obj2);
            case 3:
                return DefaultTrackSelector.AudioTrackInfo.compareSelections((List) obj, (List) obj2);
            case 4:
                return DefaultTrackSelector.VideoTrackInfo.compareNonQualityPreferences((DefaultTrackSelector.VideoTrackInfo) obj, (DefaultTrackSelector.VideoTrackInfo) obj2);
            case 5:
                return DefaultTrackSelector.VideoTrackInfo.compareNonQualityPreferences((DefaultTrackSelector.VideoTrackInfo) obj, (DefaultTrackSelector.VideoTrackInfo) obj2);
            case 6:
                return DefaultTrackSelector.VideoTrackInfo.compareNonQualityPreferences((DefaultTrackSelector.VideoTrackInfo) obj, (DefaultTrackSelector.VideoTrackInfo) obj2);
            case 7:
                return DefaultTrackSelector.VideoTrackInfo.compareQualityPreferences((DefaultTrackSelector.VideoTrackInfo) obj, (DefaultTrackSelector.VideoTrackInfo) obj2);
            case 8:
                return DefaultTrackSelector.VideoTrackInfo.compareQualityPreferences((DefaultTrackSelector.VideoTrackInfo) obj, (DefaultTrackSelector.VideoTrackInfo) obj2);
            case 9:
                return DefaultTrackSelector.VideoTrackInfo.compareQualityPreferences((DefaultTrackSelector.VideoTrackInfo) obj, (DefaultTrackSelector.VideoTrackInfo) obj2);
            case 10:
                return DefaultTrackSelector.lambda$static$0((Integer) obj, (Integer) obj2);
            default:
                return DefaultTrackSelector.lambda$static$1((Integer) obj, (Integer) obj2);
        }
    }
}
