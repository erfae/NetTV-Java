package com.google.android.material.internal;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class MultiViewUpdateListener implements ValueAnimator.AnimatorUpdateListener {
    private final Listener listener;
    private final View[] views;

    public interface Listener {
        void onAnimationUpdate(@NonNull ValueAnimator valueAnimator, @NonNull View view);
    }

    @SuppressLint({"LambdaLast"})
    public MultiViewUpdateListener(@NonNull Listener listener, @NonNull View... viewArr) {
        this.listener = listener;
        this.views = viewArr;
    }

    @NonNull
    public static MultiViewUpdateListener alphaListener(@NonNull View... viewArr) {
        return new MultiViewUpdateListener(MultiViewUpdateListener$$ExternalSyntheticLambda0.INSTANCE$7, viewArr);
    }

    @NonNull
    public static MultiViewUpdateListener scaleListener(@NonNull View... viewArr) {
        return new MultiViewUpdateListener(MultiViewUpdateListener$$ExternalSyntheticLambda0.INSTANCE$2, viewArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setAlpha(@NonNull ValueAnimator valueAnimator, @NonNull View view) {
        view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setScale(@NonNull ValueAnimator valueAnimator, @NonNull View view) {
        Float f = (Float) valueAnimator.getAnimatedValue();
        view.setScaleX(f.floatValue());
        view.setScaleY(f.floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setTranslationX(@NonNull ValueAnimator valueAnimator, @NonNull View view) {
        view.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setTranslationY(@NonNull ValueAnimator valueAnimator, @NonNull View view) {
        view.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    @NonNull
    public static MultiViewUpdateListener translationXListener(@NonNull View... viewArr) {
        return new MultiViewUpdateListener(MultiViewUpdateListener$$ExternalSyntheticLambda0.INSTANCE$3, viewArr);
    }

    @NonNull
    public static MultiViewUpdateListener translationYListener(@NonNull View... viewArr) {
        return new MultiViewUpdateListener(MultiViewUpdateListener$$ExternalSyntheticLambda0.INSTANCE$4, viewArr);
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
        for (View view : this.views) {
            this.listener.onAnimationUpdate(valueAnimator, view);
        }
    }

    @NonNull
    public static MultiViewUpdateListener alphaListener(@NonNull Collection<View> collection) {
        return new MultiViewUpdateListener(MultiViewUpdateListener$$ExternalSyntheticLambda0.INSTANCE$5, collection);
    }

    @NonNull
    public static MultiViewUpdateListener scaleListener(@NonNull Collection<View> collection) {
        return new MultiViewUpdateListener(MultiViewUpdateListener$$ExternalSyntheticLambda0.INSTANCE$1, collection);
    }

    @NonNull
    public static MultiViewUpdateListener translationXListener(@NonNull Collection<View> collection) {
        return new MultiViewUpdateListener(MultiViewUpdateListener$$ExternalSyntheticLambda0.INSTANCE, collection);
    }

    @NonNull
    public static MultiViewUpdateListener translationYListener(@NonNull Collection<View> collection) {
        return new MultiViewUpdateListener(MultiViewUpdateListener$$ExternalSyntheticLambda0.INSTANCE$6, collection);
    }

    @SuppressLint({"LambdaLast"})
    public MultiViewUpdateListener(@NonNull Listener listener, @NonNull Collection<View> collection) {
        this.listener = listener;
        this.views = (View[]) collection.toArray(new View[0]);
    }
}
