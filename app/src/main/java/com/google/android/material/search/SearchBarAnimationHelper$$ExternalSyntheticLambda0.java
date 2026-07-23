package com.google.android.material.search;

import android.animation.ValueAnimator;
import android.view.View;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import com.google.android.material.internal.FadeThroughDrawable;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class SearchBarAnimationHelper$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SearchBarAnimationHelper$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                ((View) this.f$0).setAlpha(0.0f);
                break;
            case 1:
                SearchViewAnimationHelper.lambda$addFadeThroughDrawableAnimatorIfNeeded$4((FadeThroughDrawable) this.f$0, valueAnimator);
                break;
            default:
                SearchViewAnimationHelper.lambda$addDrawerArrowDrawableAnimatorIfNeeded$3((DrawerArrowDrawable) this.f$0, valueAnimator);
                break;
        }
    }
}
