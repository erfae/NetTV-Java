package androidx.leanback.app;

import android.R;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/* JADX INFO: loaded from: classes.dex */
public final class ProgressBarManager {
    private static final long DEFAULT_PROGRESS_BAR_DELAY = 1000;
    public boolean mIsShowing;
    public View mProgressBarView;
    public boolean mUserProvidedProgressBar;
    public ViewGroup rootView;
    private long mInitialDelay = 1000;
    private Handler mHandler = new Handler();
    public boolean mEnableProgressBar = true;
    private Runnable runnable = new Runnable() { // from class: androidx.leanback.app.ProgressBarManager.1
        @Override // java.lang.Runnable
        public void run() {
            ProgressBarManager progressBarManager = ProgressBarManager.this;
            if (progressBarManager.mEnableProgressBar) {
                boolean z = progressBarManager.mUserProvidedProgressBar;
                if ((z || progressBarManager.rootView != null) && progressBarManager.mIsShowing) {
                    View view = progressBarManager.mProgressBarView;
                    if (view != null) {
                        if (z) {
                            view.setVisibility(0);
                        }
                    } else {
                        progressBarManager.mProgressBarView = new ProgressBar(ProgressBarManager.this.rootView.getContext(), null, R.attr.progressBarStyleLarge);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
                        layoutParams.gravity = 17;
                        ProgressBarManager progressBarManager2 = ProgressBarManager.this;
                        progressBarManager2.rootView.addView(progressBarManager2.mProgressBarView, layoutParams);
                    }
                }
            }
        }
    };

    public void disableProgressBar() {
        this.mEnableProgressBar = false;
    }

    public void enableProgressBar() {
        this.mEnableProgressBar = true;
    }

    public long getInitialDelay() {
        return this.mInitialDelay;
    }

    public void hide() {
        this.mIsShowing = false;
        if (this.mUserProvidedProgressBar) {
            this.mProgressBarView.setVisibility(4);
        } else {
            View view = this.mProgressBarView;
            if (view != null) {
                this.rootView.removeView(view);
                this.mProgressBarView = null;
            }
        }
        this.mHandler.removeCallbacks(this.runnable);
    }

    public void setInitialDelay(long j) {
        this.mInitialDelay = j;
    }

    public void setProgressBarView(View view) {
        if (view.getParent() == null) {
            throw new IllegalArgumentException("Must have a parent");
        }
        this.mProgressBarView = view;
        view.setVisibility(4);
        this.mUserProvidedProgressBar = true;
    }

    public void setRootView(ViewGroup viewGroup) {
        this.rootView = viewGroup;
    }

    public void show() {
        if (this.mEnableProgressBar) {
            this.mIsShowing = true;
            this.mHandler.postDelayed(this.runnable, this.mInitialDelay);
        }
    }
}
