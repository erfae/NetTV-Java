package androidx.leanback.widget;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class FullWidthDetailsOverviewSharedElementHelper extends FullWidthDetailsOverviewRowPresenter.Listener {
    private static final long DEFAULT_TIMEOUT = 5000;
    public Activity mActivityToRunTransition;
    private boolean mAutoStartSharedElementTransition = true;
    public String mSharedElementName;
    private boolean mStartedPostpone;
    public FullWidthDetailsOverviewRowPresenter.ViewHolder mViewHolder;

    public static class TransitionTimeOutRunnable implements Runnable {
        public WeakReference<FullWidthDetailsOverviewSharedElementHelper> mHelperRef;

        public TransitionTimeOutRunnable(FullWidthDetailsOverviewSharedElementHelper fullWidthDetailsOverviewSharedElementHelper) {
            this.mHelperRef = new WeakReference<>(fullWidthDetailsOverviewSharedElementHelper);
        }

        @Override // java.lang.Runnable
        public void run() {
            FullWidthDetailsOverviewSharedElementHelper fullWidthDetailsOverviewSharedElementHelper = this.mHelperRef.get();
            if (fullWidthDetailsOverviewSharedElementHelper == null) {
                return;
            }
            fullWidthDetailsOverviewSharedElementHelper.startPostponedEnterTransition();
        }
    }

    public boolean getAutoStartSharedElementTransition() {
        return this.mAutoStartSharedElementTransition;
    }

    @Override // androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter.Listener
    public void onBindLogo(FullWidthDetailsOverviewRowPresenter.ViewHolder viewHolder) {
        this.mViewHolder = viewHolder;
        if (this.mAutoStartSharedElementTransition) {
            if (viewHolder != null) {
                ViewCompat.setTransitionName(viewHolder.getLogoViewHolder().view, null);
            }
            this.mViewHolder.getDetailsDescriptionFrame().postOnAnimation(new Runnable() { // from class: androidx.leanback.widget.FullWidthDetailsOverviewSharedElementHelper.1
                @Override // java.lang.Runnable
                public void run() {
                    ViewCompat.setTransitionName(FullWidthDetailsOverviewSharedElementHelper.this.mViewHolder.getLogoViewHolder().view, FullWidthDetailsOverviewSharedElementHelper.this.mSharedElementName);
                    Object sharedElementEnterTransition = TransitionHelper.getSharedElementEnterTransition(FullWidthDetailsOverviewSharedElementHelper.this.mActivityToRunTransition.getWindow());
                    if (sharedElementEnterTransition != null) {
                        TransitionHelper.addTransitionListener(sharedElementEnterTransition, new TransitionListener() { // from class: androidx.leanback.widget.FullWidthDetailsOverviewSharedElementHelper.1.1
                            @Override // androidx.leanback.transition.TransitionListener
                            public void onTransitionEnd(Object obj) {
                                if (FullWidthDetailsOverviewSharedElementHelper.this.mViewHolder.getActionsRow().isFocused()) {
                                    FullWidthDetailsOverviewSharedElementHelper.this.mViewHolder.getActionsRow().requestFocus();
                                }
                                TransitionHelper.removeTransitionListener(obj, this);
                            }
                        });
                    }
                    FullWidthDetailsOverviewSharedElementHelper.this.startPostponedEnterTransitionInternal();
                }
            });
        }
    }

    public void setAutoStartSharedElementTransition(boolean z) {
        this.mAutoStartSharedElementTransition = z;
    }

    public void setSharedElementEnterTransition(Activity activity, String str) {
        setSharedElementEnterTransition(activity, str, 5000L);
    }

    public void startPostponedEnterTransition() {
        new Handler().post(new Runnable() { // from class: androidx.leanback.widget.FullWidthDetailsOverviewSharedElementHelper.2
            @Override // java.lang.Runnable
            public void run() {
                FullWidthDetailsOverviewSharedElementHelper.this.startPostponedEnterTransitionInternal();
            }
        });
    }

    public final void startPostponedEnterTransitionInternal() {
        if (this.mStartedPostpone || this.mViewHolder == null) {
            return;
        }
        ActivityCompat.startPostponedEnterTransition(this.mActivityToRunTransition);
        this.mStartedPostpone = true;
    }

    public void setSharedElementEnterTransition(Activity activity, String str, long j) {
        if ((activity == null && !TextUtils.isEmpty(str)) || (activity != null && TextUtils.isEmpty(str))) {
            throw new IllegalArgumentException();
        }
        if (activity == this.mActivityToRunTransition && TextUtils.equals(str, this.mSharedElementName)) {
            return;
        }
        this.mActivityToRunTransition = activity;
        this.mSharedElementName = str;
        setAutoStartSharedElementTransition(TransitionHelper.getSharedElementEnterTransition(activity.getWindow()) != null);
        ActivityCompat.postponeEnterTransition(this.mActivityToRunTransition);
        if (j > 0) {
            new Handler().postDelayed(new TransitionTimeOutRunnable(this), j);
        }
    }
}
