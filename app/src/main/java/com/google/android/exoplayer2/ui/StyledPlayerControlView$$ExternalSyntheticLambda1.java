package com.google.android.exoplayer2.ui;

import android.view.View;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class StyledPlayerControlView$$ExternalSyntheticLambda1 implements View.OnLayoutChangeListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StyledPlayerControlView$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        switch (this.$r8$classId) {
            case 0:
                ((StyledPlayerControlView) this.f$0).onLayoutChange(view, i, i2, i3, i4, i5, i6, i7, i8);
                break;
            default:
                ((StyledPlayerControlViewLayoutManager) this.f$0).onLayoutChange(view, i, i2, i3, i4, i5, i6, i7, i8);
                break;
        }
    }
}
