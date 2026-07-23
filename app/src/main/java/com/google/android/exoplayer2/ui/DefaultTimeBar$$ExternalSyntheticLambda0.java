package com.google.android.exoplayer2.ui;

import android.animation.ValueAnimator;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DefaultTimeBar$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DefaultTimeBar$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                ((DefaultTimeBar) this.f$0).lambda$new$1(valueAnimator);
                break;
            case 1:
                ((StyledPlayerControlViewLayoutManager) this.f$0).lambda$new$2(valueAnimator);
                break;
            case 2:
                ((StyledPlayerControlViewLayoutManager) this.f$0).lambda$new$3(valueAnimator);
                break;
            case 3:
                ((StyledPlayerControlViewLayoutManager) this.f$0).lambda$new$0(valueAnimator);
                break;
            default:
                ((StyledPlayerControlViewLayoutManager) this.f$0).lambda$new$1(valueAnimator);
                break;
        }
    }
}
