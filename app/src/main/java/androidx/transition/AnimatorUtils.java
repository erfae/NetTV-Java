package androidx.transition;

import android.animation.Animator;

/* JADX INFO: loaded from: classes.dex */
class AnimatorUtils {

    public interface AnimatorPauseListenerCompat {
        void onAnimationPause(Animator animator);

        void onAnimationResume(Animator animator);
    }

    private AnimatorUtils() {
    }
}
