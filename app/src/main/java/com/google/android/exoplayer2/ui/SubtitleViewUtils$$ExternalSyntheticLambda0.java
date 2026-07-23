package com.google.android.exoplayer2.ui;

import com.google.common.base.Predicate;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class SubtitleViewUtils$$ExternalSyntheticLambda0 implements Predicate {
    public static final /* synthetic */ SubtitleViewUtils$$ExternalSyntheticLambda0 INSTANCE = new SubtitleViewUtils$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ SubtitleViewUtils$$ExternalSyntheticLambda0 INSTANCE$1 = new SubtitleViewUtils$$ExternalSyntheticLambda0(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SubtitleViewUtils$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // com.google.common.base.Predicate
    public final boolean apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return SubtitleViewUtils.lambda$removeEmbeddedFontSizes$1(obj);
            default:
                return SubtitleViewUtils.lambda$removeAllEmbeddedStyling$0(obj);
        }
    }
}
