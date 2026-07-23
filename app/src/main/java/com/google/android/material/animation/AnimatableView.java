package com.google.android.material.animation;

import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes.dex */
public interface AnimatableView {

    public interface Listener {
        void onAnimationEnd();
    }

    void startAnimation(@NonNull Listener listener);

    void stopAnimation();
}
