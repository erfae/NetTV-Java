package com.google.android.exoplayer2.mediacodec;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class MediaCodecUtil$$ExternalSyntheticLambda1 implements MediaCodecUtil.ScoreProvider {
    public static final /* synthetic */ MediaCodecUtil$$ExternalSyntheticLambda1 INSTANCE = new MediaCodecUtil$$ExternalSyntheticLambda1(0);
    public static final /* synthetic */ MediaCodecUtil$$ExternalSyntheticLambda1 INSTANCE$1 = new MediaCodecUtil$$ExternalSyntheticLambda1(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MediaCodecUtil$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.ScoreProvider
    public final int getScore(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return MediaCodecUtil.lambda$applyWorkarounds$1((MediaCodecInfo) obj);
            default:
                return MediaCodecUtil.lambda$applyWorkarounds$2((MediaCodecInfo) obj);
        }
    }
}
