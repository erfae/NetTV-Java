package com.google.android.exoplayer2.source;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ConcatenatingMediaSource$$ExternalSyntheticLambda0 implements Handler.Callback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CompositeMediaSource f$0;

    public /* synthetic */ ConcatenatingMediaSource$$ExternalSyntheticLambda0(CompositeMediaSource compositeMediaSource, int i) {
        this.$r8$classId = i;
        this.f$0 = compositeMediaSource;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        switch (this.$r8$classId) {
            case 0:
                return ((ConcatenatingMediaSource) this.f$0).handleMessage(message);
            default:
                return ((ConcatenatingMediaSource2) this.f$0).handleMessage(message);
        }
    }
}
