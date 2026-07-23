package androidx.leanback.app;

import android.animation.PropertyValuesHolder;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.leanback.R;
import androidx.leanback.graphics.FitWidthBitmapDrawable;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.media.PlaybackGlueHost;
import androidx.leanback.widget.DetailsParallaxDrawable;
import androidx.leanback.widget.ParallaxTarget;
import androidx.leanback.widget.VerticalGridView;

/* JADX INFO: loaded from: classes.dex */
public class DetailsSupportFragmentBackgroundController {
    public Bitmap mCoverBitmap;
    public final DetailsSupportFragment mFragment;
    private Fragment mLastVideoSupportFragmentForGlueHost;
    public DetailsParallaxDrawable mParallaxDrawable;
    public int mParallaxDrawableMaxOffset;
    public PlaybackGlue mPlaybackGlue;
    public int mSolidColor;
    public DetailsBackgroundVideoHelper mVideoHelper;
    public boolean mCanUseHost = false;
    public boolean mInitialControlVisible = false;

    public DetailsSupportFragmentBackgroundController(DetailsSupportFragment detailsSupportFragment) {
        if (detailsSupportFragment.mDetailsBackgroundController != null) {
            throw new IllegalStateException("Each DetailsSupportFragment is allowed to initialize DetailsSupportFragmentBackgroundController once");
        }
        detailsSupportFragment.mDetailsBackgroundController = this;
        this.mFragment = detailsSupportFragment;
    }

    public boolean canNavigateToVideoSupportFragment() {
        return this.mPlaybackGlue != null;
    }

    public void enableParallax() {
        int dimensionPixelSize = this.mParallaxDrawableMaxOffset;
        if (dimensionPixelSize == 0) {
            dimensionPixelSize = this.mFragment.getContext().getResources().getDimensionPixelSize(R.dimen.lb_details_cover_drawable_parallax_movement);
        }
        FitWidthBitmapDrawable fitWidthBitmapDrawable = new FitWidthBitmapDrawable();
        enableParallax(fitWidthBitmapDrawable, new ColorDrawable(), new ParallaxTarget.PropertyValuesHolderTarget(fitWidthBitmapDrawable, PropertyValuesHolder.ofInt(FitWidthBitmapDrawable.PROPERTY_VERTICAL_OFFSET, 0, -dimensionPixelSize)));
    }

    public final Fragment findOrCreateVideoSupportFragment() {
        final DetailsSupportFragment detailsSupportFragment = this.mFragment;
        Fragment fragmentFindFragmentById = detailsSupportFragment.mVideoSupportFragment;
        if (fragmentFindFragmentById == null) {
            FragmentManager childFragmentManager = detailsSupportFragment.getChildFragmentManager();
            int i = R.id.video_surface_container;
            fragmentFindFragmentById = childFragmentManager.findFragmentById(i);
            if (fragmentFindFragmentById == null && detailsSupportFragment.mDetailsBackgroundController != null) {
                FragmentTransaction fragmentTransactionBeginTransaction = detailsSupportFragment.getChildFragmentManager().beginTransaction();
                Fragment fragmentOnCreateVideoSupportFragment = detailsSupportFragment.mDetailsBackgroundController.onCreateVideoSupportFragment();
                fragmentTransactionBeginTransaction.add(i, fragmentOnCreateVideoSupportFragment);
                fragmentTransactionBeginTransaction.commit();
                if (detailsSupportFragment.mPendingFocusOnVideo) {
                    detailsSupportFragment.getView().post(new Runnable() { // from class: androidx.leanback.app.DetailsSupportFragment.12
                        public AnonymousClass12() {
                        }

                        @Override // java.lang.Runnable
                        public void run() {
                            if (DetailsSupportFragment.this.getView() != null) {
                                DetailsSupportFragment.this.switchToVideo();
                            }
                            DetailsSupportFragment.this.mPendingFocusOnVideo = false;
                        }
                    });
                }
                fragmentFindFragmentById = fragmentOnCreateVideoSupportFragment;
            }
            detailsSupportFragment.mVideoSupportFragment = fragmentFindFragmentById;
        }
        return fragmentFindFragmentById;
    }

    public final Drawable getBottomDrawable() {
        DetailsParallaxDrawable detailsParallaxDrawable = this.mParallaxDrawable;
        if (detailsParallaxDrawable == null) {
            return null;
        }
        return detailsParallaxDrawable.getBottomDrawable();
    }

    public final Bitmap getCoverBitmap() {
        return this.mCoverBitmap;
    }

    public final Drawable getCoverDrawable() {
        DetailsParallaxDrawable detailsParallaxDrawable = this.mParallaxDrawable;
        if (detailsParallaxDrawable == null) {
            return null;
        }
        return detailsParallaxDrawable.getCoverDrawable();
    }

    public final int getParallaxDrawableMaxOffset() {
        return this.mParallaxDrawableMaxOffset;
    }

    public final PlaybackGlue getPlaybackGlue() {
        return this.mPlaybackGlue;
    }

    @ColorInt
    public final int getSolidColor() {
        return this.mSolidColor;
    }

    public PlaybackGlueHost onCreateGlueHost() {
        return new VideoSupportFragmentGlueHost((VideoSupportFragment) findOrCreateVideoSupportFragment());
    }

    public Fragment onCreateVideoSupportFragment() {
        return new VideoSupportFragment();
    }

    public final void onStart() {
        if (!this.mCanUseHost) {
            this.mCanUseHost = true;
            PlaybackGlue playbackGlue = this.mPlaybackGlue;
            if (playbackGlue != null) {
                PlaybackGlueHost playbackGlueHostOnCreateGlueHost = onCreateGlueHost();
                if (this.mInitialControlVisible) {
                    playbackGlueHostOnCreateGlueHost.showControlsOverlay(false);
                } else {
                    playbackGlueHostOnCreateGlueHost.hideControlsOverlay(false);
                }
                playbackGlue.setHost(playbackGlueHostOnCreateGlueHost);
                this.mLastVideoSupportFragmentForGlueHost = findOrCreateVideoSupportFragment();
            }
        }
        PlaybackGlue playbackGlue2 = this.mPlaybackGlue;
        if (playbackGlue2 == null || !playbackGlue2.isPrepared()) {
            return;
        }
        this.mPlaybackGlue.play();
    }

    public final void setCoverBitmap(Bitmap bitmap) {
        this.mCoverBitmap = bitmap;
        Drawable coverDrawable = getCoverDrawable();
        if (coverDrawable instanceof FitWidthBitmapDrawable) {
            ((FitWidthBitmapDrawable) coverDrawable).setBitmap(this.mCoverBitmap);
        }
    }

    public final void setParallaxDrawableMaxOffset(int i) {
        if (this.mParallaxDrawable != null) {
            throw new IllegalStateException("enableParallax already called");
        }
        this.mParallaxDrawableMaxOffset = i;
    }

    public final void setSolidColor(@ColorInt int i) {
        this.mSolidColor = i;
        Drawable bottomDrawable = getBottomDrawable();
        if (bottomDrawable instanceof ColorDrawable) {
            ((ColorDrawable) bottomDrawable).setColor(i);
        }
    }

    public void setupVideoPlayback(@NonNull PlaybackGlue playbackGlue) {
        PlaybackGlue playbackGlue2 = this.mPlaybackGlue;
        if (playbackGlue2 == playbackGlue) {
            return;
        }
        PlaybackGlueHost playbackGlueHost = null;
        if (playbackGlue2 != null) {
            PlaybackGlueHost host = playbackGlue2.getHost();
            this.mPlaybackGlue.setHost(null);
            playbackGlueHost = host;
        }
        this.mPlaybackGlue = playbackGlue;
        this.mVideoHelper.setPlaybackGlue(playbackGlue);
        if (!this.mCanUseHost || this.mPlaybackGlue == null) {
            return;
        }
        if (playbackGlueHost != null && this.mLastVideoSupportFragmentForGlueHost == findOrCreateVideoSupportFragment()) {
            this.mPlaybackGlue.setHost(playbackGlueHost);
            return;
        }
        PlaybackGlue playbackGlue3 = this.mPlaybackGlue;
        PlaybackGlueHost playbackGlueHostOnCreateGlueHost = onCreateGlueHost();
        if (this.mInitialControlVisible) {
            playbackGlueHostOnCreateGlueHost.showControlsOverlay(false);
        } else {
            playbackGlueHostOnCreateGlueHost.hideControlsOverlay(false);
        }
        playbackGlue3.setHost(playbackGlueHostOnCreateGlueHost);
        this.mLastVideoSupportFragmentForGlueHost = findOrCreateVideoSupportFragment();
    }

    public final void switchToRows() {
        DetailsSupportFragment detailsSupportFragment = this.mFragment;
        detailsSupportFragment.mPendingFocusOnVideo = false;
        VerticalGridView verticalGridView = detailsSupportFragment.getVerticalGridView();
        if (verticalGridView == null || verticalGridView.getChildCount() <= 0) {
            return;
        }
        verticalGridView.requestFocus();
    }

    public final void switchToVideo() {
        this.mFragment.switchToVideo();
    }

    public void enableParallax(@NonNull Drawable drawable, @NonNull Drawable drawable2, @Nullable ParallaxTarget.PropertyValuesHolderTarget propertyValuesHolderTarget) {
        if (this.mParallaxDrawable != null) {
            return;
        }
        Bitmap bitmap = this.mCoverBitmap;
        if (bitmap != null && (drawable instanceof FitWidthBitmapDrawable)) {
            ((FitWidthBitmapDrawable) drawable).setBitmap(bitmap);
        }
        int i = this.mSolidColor;
        if (i != 0 && (drawable2 instanceof ColorDrawable)) {
            ((ColorDrawable) drawable2).setColor(i);
        }
        if (this.mPlaybackGlue == null) {
            DetailsParallaxDrawable detailsParallaxDrawable = new DetailsParallaxDrawable(this.mFragment.getContext(), this.mFragment.getParallax(), drawable, drawable2, propertyValuesHolderTarget);
            this.mParallaxDrawable = detailsParallaxDrawable;
            DetailsSupportFragment detailsSupportFragment = this.mFragment;
            View view = detailsSupportFragment.mBackgroundView;
            if (view != null) {
                view.setBackground(detailsParallaxDrawable);
            }
            detailsSupportFragment.mBackgroundDrawable = detailsParallaxDrawable;
            this.mVideoHelper = new DetailsBackgroundVideoHelper(this.mFragment.getParallax(), this.mParallaxDrawable.getCoverDrawable());
            return;
        }
        throw new IllegalStateException("enableParallaxDrawable must be called before enableVideoPlayback");
    }
}
