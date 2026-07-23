package com.google.android.exoplayer2;

import com.google.android.exoplayer2.source.MediaSource;
import com.google.common.base.Supplier;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayer$Builder$$ExternalSyntheticLambda5 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaSource.Factory f$0;

    public /* synthetic */ ExoPlayer$Builder$$ExternalSyntheticLambda5(MediaSource.Factory factory, int i) {
        this.$r8$classId = i;
        this.f$0 = factory;
    }

    @Override // com.google.common.base.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return ExoPlayer.Builder.lambda$setMediaSourceFactory$17(this.f$0);
            case 1:
                return ExoPlayer.Builder.lambda$new$5(this.f$0);
            case 2:
                return ExoPlayer.Builder.lambda$new$7(this.f$0);
            default:
                return ExoPlayer.Builder.lambda$new$9(this.f$0);
        }
    }
}
