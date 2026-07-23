package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.google.android.material.internal.ExpandCollapseAnimationHelper;
import com.google.android.material.shape.MaterialShapeDrawable;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class AppBarLayout$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AppBarLayout$$ExternalSyntheticLambda0(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                ((AppBarLayout) this.f$0).lambda$initializeLiftOnScrollWithColor$0((MaterialShapeDrawable) this.f$1, valueAnimator);
                break;
            case 1:
                ((AppBarLayout) this.f$0).lambda$initializeLiftOnScrollWithElevation$1((MaterialShapeDrawable) this.f$1, valueAnimator);
                break;
            default:
                ((ExpandCollapseAnimationHelper) this.f$0).lambda$getExpandCollapseAnimator$0((Rect) this.f$1, valueAnimator);
                break;
        }
    }
}
