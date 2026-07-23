package androidx.leanback.app;

import android.animation.PropertyValuesHolder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.R;
import androidx.leanback.graphics.FitWidthBitmapDrawable;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.media.PlaybackGlueHost;
import androidx.leanback.widget.DetailsParallaxDrawable;
import androidx.leanback.widget.ParallaxTarget;
import androidx.leanback.widget.VerticalGridView;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class DetailsFragmentBackgroundController {
    public Bitmap mCoverBitmap;
    public final DetailsFragment mFragment;
    private Fragment mLastVideoFragmentForGlueHost;
    public DetailsParallaxDrawable mParallaxDrawable;
    public int mParallaxDrawableMaxOffset;
    public PlaybackGlue mPlaybackGlue;
    public int mSolidColor;
    public DetailsBackgroundVideoHelper mVideoHelper;
    public boolean mCanUseHost = false;
    public boolean mInitialControlVisible = false;

    public DetailsFragmentBackgroundController(DetailsFragment detailsFragment) {
        if (detailsFragment.mDetailsBackgroundController != null) {
            throw new IllegalStateException("Each DetailsFragment is allowed to initialize DetailsFragmentBackgroundController once");
        }
        detailsFragment.mDetailsBackgroundController = this;
        this.mFragment = detailsFragment;
    }

    public boolean canNavigateToVideoFragment() {
        return this.mPlaybackGlue != null;
    }

    public void enableParallax() {
        int dimensionPixelSize = this.mParallaxDrawableMaxOffset;
        if (dimensionPixelSize == 0) {
            dimensionPixelSize = FragmentUtil.getContext(this.mFragment).getResources().getDimensionPixelSize(R.dimen.lb_details_cover_drawable_parallax_movement);
        }
        FitWidthBitmapDrawable fitWidthBitmapDrawable = new FitWidthBitmapDrawable();
        enableParallax(fitWidthBitmapDrawable, new ColorDrawable(), new ParallaxTarget.PropertyValuesHolderTarget(fitWidthBitmapDrawable, PropertyValuesHolder.ofInt(FitWidthBitmapDrawable.PROPERTY_VERTICAL_OFFSET, 0, -dimensionPixelSize)));
    }

    public final Fragment findOrCreateVideoFragment() {
        final DetailsFragment detailsFragment = this.mFragment;
        Fragment fragmentFindFragmentById = detailsFragment.mVideoFragment;
        if (fragmentFindFragmentById == null) {
            FragmentManager childFragmentManager = detailsFragment.getChildFragmentManager();
            int i = R.id.video_surface_container;
            fragmentFindFragmentById = childFragmentManager.findFragmentById(i);
            if (fragmentFindFragmentById == null && detailsFragment.mDetailsBackgroundController != null) {
                FragmentTransaction fragmentTransactionBeginTransaction = detailsFragment.getChildFragmentManager().beginTransaction();
                Fragment fragmentOnCreateVideoFragment = detailsFragment.mDetailsBackgroundController.onCreateVideoFragment();
                fragmentTransactionBeginTransaction.add(i, fragmentOnCreateVideoFragment);
                fragmentTransactionBeginTransaction.commit();
                if (detailsFragment.mPendingFocusOnVideo) {
                    detailsFragment.getView().post(new Runnable() { // from class: androidx.leanback.app.DetailsFragment.12
                        public AnonymousClass12() {
                        }

                        @Override // java.lang.Runnable
                        public void run() {
                            if (DetailsFragment.this.getView() != null) {
                                DetailsFragment.this.switchToVideo();
                            }
                            DetailsFragment.this.mPendingFocusOnVideo = false;
                        }
                    });
                }
                fragmentFindFragmentById = fragmentOnCreateVideoFragment;
            }
            detailsFragment.mVideoFragment = fragmentFindFragmentById;
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
        return new VideoFragmentGlueHost((VideoFragment) findOrCreateVideoFragment());
    }

    public Fragment onCreateVideoFragment() {
        return new VideoFragment();
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
                this.mLastVideoFragmentForGlueHost = findOrCreateVideoFragment();
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
        if (playbackGlueHost != null && this.mLastVideoFragmentForGlueHost == findOrCreateVideoFragment()) {
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
        this.mLastVideoFragmentForGlueHost = findOrCreateVideoFragment();
    }

    public final void switchToRows() {
        DetailsFragment detailsFragment = this.mFragment;
        detailsFragment.mPendingFocusOnVideo = false;
        VerticalGridView verticalGridView = detailsFragment.getVerticalGridView();
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
            DetailsParallaxDrawable detailsParallaxDrawable = new DetailsParallaxDrawable(FragmentUtil.getContext(this.mFragment), this.mFragment.getParallax(), drawable, drawable2, propertyValuesHolderTarget);
            this.mParallaxDrawable = detailsParallaxDrawable;
            DetailsFragment detailsFragment = this.mFragment;
            View view = detailsFragment.mBackgroundView;
            if (view != null) {
                view.setBackground(detailsParallaxDrawable);
            }
            detailsFragment.mBackgroundDrawable = detailsParallaxDrawable;
            this.mVideoHelper = new DetailsBackgroundVideoHelper(this.mFragment.getParallax(), this.mParallaxDrawable.getCoverDrawable());
            return;
        }
        throw new IllegalStateException("enableParallaxDrawable must be called before enableVideoPlayback");
    }
}
