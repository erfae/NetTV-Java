package com.google.android.exoplayer2.text.webvtt;

import java.util.Comparator;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class WebvttSubtitle$$ExternalSyntheticLambda0 implements Comparator {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ WebvttSubtitle$$ExternalSyntheticLambda0 INSTANCE$1 = new WebvttSubtitle$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ WebvttSubtitle$$ExternalSyntheticLambda0 INSTANCE = new WebvttSubtitle$$ExternalSyntheticLambda0(0);

    public /* synthetic */ WebvttSubtitle$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return WebvttSubtitle.lambda$getCues$0((WebvttCueInfo) obj, (WebvttCueInfo) obj2);
            default:
                return WebvttCueParser.Element.lambda$static$0((WebvttCueParser.Element) obj, (WebvttCueParser.Element) obj2);
        }
    }
}
