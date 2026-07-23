package com.google.android.material.textfield;

import android.animation.ValueAnimator;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class ClearTextEndIconDelegate$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ClearTextEndIconDelegate$$ExternalSyntheticLambda0(EndIconDelegate endIconDelegate, int i) {
        this.$r8$classId = i;
        this.f$0 = endIconDelegate;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                ((ClearTextEndIconDelegate) this.f$0).lambda$getScaleAnimator$4(valueAnimator);
                break;
            case 1:
                ((ClearTextEndIconDelegate) this.f$0).lambda$getAlphaAnimator$3(valueAnimator);
                break;
            default:
                ((DropdownMenuEndIconDelegate) this.f$0).lambda$getAlphaAnimator$6(valueAnimator);
                break;
        }
    }
}
