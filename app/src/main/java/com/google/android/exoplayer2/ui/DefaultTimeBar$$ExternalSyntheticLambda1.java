package com.google.android.exoplayer2.ui;

import android.view.View;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultTimeBar$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ View f$0;

    public /* synthetic */ DefaultTimeBar$$ExternalSyntheticLambda1(View view, int i) {
        this.$r8$classId = i;
        this.f$0 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((DefaultTimeBar) this.f$0).lambda$new$0();
                break;
            default:
                ((StyledPlayerControlView) this.f$0).updateProgress();
                break;
        }
    }
}
