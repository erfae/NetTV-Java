package com.google.android.exoplayer2;

import com.google.common.base.Supplier;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayer$Builder$$ExternalSyntheticLambda3 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LoadControl f$0;

    public /* synthetic */ ExoPlayer$Builder$$ExternalSyntheticLambda3(LoadControl loadControl, int i) {
        this.$r8$classId = i;
        this.f$0 = loadControl;
    }

    @Override // com.google.common.base.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return ExoPlayer.Builder.lambda$setLoadControl$19(this.f$0);
            default:
                return ExoPlayer.Builder.lambda$new$11(this.f$0);
        }
    }
}
