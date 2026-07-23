package androidx.leanback.app;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.widget.DetailsParallax;
import androidx.leanback.widget.Parallax;
import androidx.leanback.widget.ParallaxEffect;
import androidx.leanback.widget.ParallaxTarget;

/* JADX INFO: loaded from: classes.dex */
final class DetailsBackgroundVideoHelper {
    private static final long BACKGROUND_CROSS_FADE_DURATION = 500;
    private static final long CROSSFADE_DELAY = 1000;
    public ValueAnimator mBackgroundAnimator;
    public Drawable mBackgroundDrawable;
    private final DetailsParallax mDetailsParallax;
    private ParallaxEffect mParallaxEffect;
    private int mCurrentState = 0;
    public PlaybackControlStateCallback mControlStateCallback = new PlaybackControlStateCallback();
    private PlaybackGlue mPlaybackGlue = null;
    private boolean mBackgroundDrawableVisible = true;

    public class PlaybackControlStateCallback extends PlaybackGlue.PlayerCallback {
        public PlaybackControlStateCallback() {
        }

        @Override // androidx.leanback.media.PlaybackGlue.PlayerCallback
        public void onPreparedStateChanged(PlaybackGlue playbackGlue) {
            if (playbackGlue.isPrepared()) {
                DetailsBackgroundVideoHelper.this.internalStartPlayback();
            }
        }
    }

    public DetailsBackgroundVideoHelper(DetailsParallax detailsParallax, Drawable drawable) {
        this.mDetailsParallax = detailsParallax;
        this.mBackgroundDrawable = drawable;
        drawable.setAlpha(255);
        if (this.mParallaxEffect != null) {
            return;
        }
        Parallax.IntProperty overviewRowTop = detailsParallax.getOverviewRowTop();
        this.mParallaxEffect = detailsParallax.addEffect(overviewRowTop.atFraction(1.0f), overviewRowTop.atFraction(0.0f)).target(new ParallaxTarget() { // from class: androidx.leanback.app.DetailsBackgroundVideoHelper.1
            @Override // androidx.leanback.widget.ParallaxTarget
            public void update(float f) {
                if (f == 1.0f) {
                    DetailsBackgroundVideoHelper.this.updateState(2);
                } else {
                    DetailsBackgroundVideoHelper.this.updateState(1);
                }
            }
        });
        detailsParallax.updateValues();
    }

    private void applyState() {
        int i = this.mCurrentState;
        if (i != 1) {
            if (i != 2) {
                return;
            }
            crossFadeBackgroundToVideo(false, false);
            PlaybackGlue playbackGlue = this.mPlaybackGlue;
            if (playbackGlue != null) {
                playbackGlue.removePlayerCallback(this.mControlStateCallback);
                this.mPlaybackGlue.pause();
                return;
            }
            return;
        }
        PlaybackGlue playbackGlue2 = this.mPlaybackGlue;
        if (playbackGlue2 == null) {
            crossFadeBackgroundToVideo(false, false);
        } else if (playbackGlue2.isPrepared()) {
            internalStartPlayback();
        } else {
            this.mPlaybackGlue.addPlayerCallback(this.mControlStateCallback);
        }
    }

    public final void crossFadeBackgroundToVideo(boolean z, boolean z2) {
        boolean z3 = !z;
        if (this.mBackgroundDrawableVisible == z3) {
            if (z2) {
                ValueAnimator valueAnimator = this.mBackgroundAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    this.mBackgroundAnimator = null;
                }
                Drawable drawable = this.mBackgroundDrawable;
                if (drawable != null) {
                    drawable.setAlpha(z ? 0 : 255);
                    return;
                }
                return;
            }
            return;
        }
        this.mBackgroundDrawableVisible = z3;
        ValueAnimator valueAnimator2 = this.mBackgroundAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
            this.mBackgroundAnimator = null;
        }
        float f = z ? 1.0f : 0.0f;
        float f2 = z ? 0.0f : 1.0f;
        Drawable drawable2 = this.mBackgroundDrawable;
        if (drawable2 == null) {
            return;
        }
        if (z2) {
            drawable2.setAlpha(z ? 0 : 255);
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        this.mBackgroundAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(500L);
        this.mBackgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.leanback.app.DetailsBackgroundVideoHelper.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator3) {
                DetailsBackgroundVideoHelper.this.mBackgroundDrawable.setAlpha((int) (((Float) valueAnimator3.getAnimatedValue()).floatValue() * 255.0f));
            }
        });
        this.mBackgroundAnimator.addListener(new Animator.AnimatorListener() { // from class: androidx.leanback.app.DetailsBackgroundVideoHelper.4
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                DetailsBackgroundVideoHelper.this.mBackgroundAnimator = null;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        });
        this.mBackgroundAnimator.start();
    }

    public final void internalStartPlayback() {
        PlaybackGlue playbackGlue = this.mPlaybackGlue;
        if (playbackGlue != null) {
            playbackGlue.play();
        }
        this.mDetailsParallax.getRecyclerView().postDelayed(new Runnable() { // from class: androidx.leanback.app.DetailsBackgroundVideoHelper.2
            @Override // java.lang.Runnable
            public void run() {
                DetailsBackgroundVideoHelper.this.crossFadeBackgroundToVideo(true, false);
            }
        }, 1000L);
    }

    public final boolean isVideoVisible() {
        return this.mCurrentState == 1;
    }

    public final void setPlaybackGlue(PlaybackGlue playbackGlue) {
        PlaybackGlue playbackGlue2 = this.mPlaybackGlue;
        if (playbackGlue2 != null) {
            playbackGlue2.removePlayerCallback(this.mControlStateCallback);
        }
        this.mPlaybackGlue = playbackGlue;
        applyState();
    }

    public final void stopParallax() {
        this.mDetailsParallax.removeEffect(this.mParallaxEffect);
    }

    public final void updateState(int i) {
        if (i == this.mCurrentState) {
            return;
        }
        this.mCurrentState = i;
        applyState();
    }
}
