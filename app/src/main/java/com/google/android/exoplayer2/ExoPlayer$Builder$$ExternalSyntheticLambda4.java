package com.google.android.exoplayer2;

import com.google.common.base.Supplier;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayer$Builder$$ExternalSyntheticLambda4 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RenderersFactory f$0;

    public /* synthetic */ ExoPlayer$Builder$$ExternalSyntheticLambda4(RenderersFactory renderersFactory, int i) {
        this.$r8$classId = i;
        this.f$0 = renderersFactory;
    }

    @Override // com.google.common.base.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return ExoPlayer.Builder.lambda$setRenderersFactory$16(this.f$0);
            case 1:
                return ExoPlayer.Builder.lambda$new$6(this.f$0);
            case 2:
                return ExoPlayer.Builder.lambda$new$2(this.f$0);
            default:
                return ExoPlayer.Builder.lambda$new$8(this.f$0);
        }
    }
}
