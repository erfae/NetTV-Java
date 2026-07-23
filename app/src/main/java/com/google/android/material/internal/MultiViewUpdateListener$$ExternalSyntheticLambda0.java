package com.google.android.material.internal;

import android.animation.ValueAnimator;
import android.view.View;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class MultiViewUpdateListener$$ExternalSyntheticLambda0 implements MultiViewUpdateListener.Listener {
    public static final /* synthetic */ MultiViewUpdateListener$$ExternalSyntheticLambda0 INSTANCE = new MultiViewUpdateListener$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ MultiViewUpdateListener$$ExternalSyntheticLambda0 INSTANCE$1 = new MultiViewUpdateListener$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ MultiViewUpdateListener$$ExternalSyntheticLambda0 INSTANCE$2 = new MultiViewUpdateListener$$ExternalSyntheticLambda0(2);
    public static final /* synthetic */ MultiViewUpdateListener$$ExternalSyntheticLambda0 INSTANCE$3 = new MultiViewUpdateListener$$ExternalSyntheticLambda0(3);
    public static final /* synthetic */ MultiViewUpdateListener$$ExternalSyntheticLambda0 INSTANCE$4 = new MultiViewUpdateListener$$ExternalSyntheticLambda0(4);
    public static final /* synthetic */ MultiViewUpdateListener$$ExternalSyntheticLambda0 INSTANCE$5 = new MultiViewUpdateListener$$ExternalSyntheticLambda0(5);
    public static final /* synthetic */ MultiViewUpdateListener$$ExternalSyntheticLambda0 INSTANCE$6 = new MultiViewUpdateListener$$ExternalSyntheticLambda0(6);
    public static final /* synthetic */ MultiViewUpdateListener$$ExternalSyntheticLambda0 INSTANCE$7 = new MultiViewUpdateListener$$ExternalSyntheticLambda0(7);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MultiViewUpdateListener$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // com.google.android.material.internal.MultiViewUpdateListener.Listener
    public final void onAnimationUpdate(ValueAnimator valueAnimator, View view) {
        switch (this.$r8$classId) {
            case 0:
                MultiViewUpdateListener.setTranslationX(valueAnimator, view);
                break;
            case 1:
                MultiViewUpdateListener.setScale(valueAnimator, view);
                break;
            case 2:
                MultiViewUpdateListener.setScale(valueAnimator, view);
                break;
            case 3:
                MultiViewUpdateListener.setTranslationX(valueAnimator, view);
                break;
            case 4:
                MultiViewUpdateListener.setTranslationY(valueAnimator, view);
                break;
            case 5:
                MultiViewUpdateListener.setAlpha(valueAnimator, view);
                break;
            case 6:
                MultiViewUpdateListener.setTranslationY(valueAnimator, view);
                break;
            default:
                MultiViewUpdateListener.setAlpha(valueAnimator, view);
                break;
        }
    }
}
