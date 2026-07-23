package com.google.android.exoplayer2;

import com.google.common.base.Supplier;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ExoPlayerImplInternal$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ExoPlayerImplInternal$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.google.common.base.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return ((ExoPlayerImplInternal) this.f$0).lambda$release$0();
            default:
                return Boolean.valueOf(((AtomicBoolean) this.f$0).get());
        }
    }
}
