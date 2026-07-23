package androidx.leanback.widget;

import android.app.Activity;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;
import java.lang.ref.WeakReference;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class DetailsOverviewSharedElementHelper extends SharedElementCallback {
    public Activity mActivityToRunTransition;
    public int mRightPanelHeight;
    public int mRightPanelWidth;
    private Matrix mSavedMatrix;
    private ImageView.ScaleType mSavedScaleType;
    public String mSharedElementName;
    public boolean mStartedPostpone;
    public DetailsOverviewRowPresenter.ViewHolder mViewHolder;

    public static class TransitionTimeOutRunnable implements Runnable {
        public WeakReference<DetailsOverviewSharedElementHelper> mHelperRef;

        public TransitionTimeOutRunnable(DetailsOverviewSharedElementHelper detailsOverviewSharedElementHelper) {
            this.mHelperRef = new WeakReference<>(detailsOverviewSharedElementHelper);
        }

        @Override // java.lang.Runnable
        public void run() {
            DetailsOverviewSharedElementHelper detailsOverviewSharedElementHelper = this.mHelperRef.get();
            if (detailsOverviewSharedElementHelper == null || detailsOverviewSharedElementHelper.mStartedPostpone) {
                return;
            }
            ActivityCompat.startPostponedEnterTransition(detailsOverviewSharedElementHelper.mActivityToRunTransition);
            detailsOverviewSharedElementHelper.mStartedPostpone = true;
        }
    }

    private void changeImageViewScale(View view) {
        ImageView imageView = (ImageView) view;
        ImageView imageView2 = this.mViewHolder.mImageView;
        imageView2.setScaleType(imageView.getScaleType());
        if (imageView.getScaleType() == ImageView.ScaleType.MATRIX) {
            imageView2.setImageMatrix(imageView.getImageMatrix());
        }
        updateImageViewAfterScaleTypeChange(imageView2);
    }

    private boolean hasImageViewScaleChange(View view) {
        return view instanceof ImageView;
    }

    private void restoreImageViewScale() {
        ImageView.ScaleType scaleType = this.mSavedScaleType;
        if (scaleType != null) {
            ImageView imageView = this.mViewHolder.mImageView;
            imageView.setScaleType(scaleType);
            if (this.mSavedScaleType == ImageView.ScaleType.MATRIX) {
                imageView.setImageMatrix(this.mSavedMatrix);
            }
            this.mSavedScaleType = null;
            updateImageViewAfterScaleTypeChange(imageView);
        }
    }

    private void saveImageViewScale() {
        if (this.mSavedScaleType == null) {
            ImageView imageView = this.mViewHolder.mImageView;
            ImageView.ScaleType scaleType = imageView.getScaleType();
            this.mSavedScaleType = scaleType;
            this.mSavedMatrix = scaleType == ImageView.ScaleType.MATRIX ? imageView.getMatrix() : null;
        }
    }

    private static void updateImageViewAfterScaleTypeChange(ImageView imageView) {
        imageView.measure(View.MeasureSpec.makeMeasureSpec(imageView.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(imageView.getMeasuredHeight(), 1073741824));
        imageView.layout(imageView.getLeft(), imageView.getTop(), imageView.getRight(), imageView.getBottom());
    }

    @Override // androidx.core.app.SharedElementCallback
    public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
        if (list2.size() < 1) {
            return;
        }
        View view = list2.get(0);
        DetailsOverviewRowPresenter.ViewHolder viewHolder = this.mViewHolder;
        if (viewHolder == null || viewHolder.mOverviewFrame != view) {
            return;
        }
        restoreImageViewScale();
        this.mViewHolder.mActionsRow.setDescendantFocusability(131072);
        this.mViewHolder.mActionsRow.setVisibility(0);
        this.mViewHolder.mActionsRow.setDescendantFocusability(262144);
        this.mViewHolder.mActionsRow.requestFocus();
        this.mViewHolder.mDetailsDescriptionFrame.setVisibility(0);
    }

    @Override // androidx.core.app.SharedElementCallback
    public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
        if (list2.size() < 1) {
            return;
        }
        View view = list2.get(0);
        DetailsOverviewRowPresenter.ViewHolder viewHolder = this.mViewHolder;
        if (viewHolder == null || viewHolder.mOverviewFrame != view) {
            return;
        }
        View view2 = list3.get(0);
        if (hasImageViewScaleChange(view2)) {
            saveImageViewScale();
            changeImageViewScale(view2);
        }
        ImageView imageView = this.mViewHolder.mImageView;
        int width = view.getWidth();
        int height = view.getHeight();
        imageView.measure(View.MeasureSpec.makeMeasureSpec(width, 1073741824), View.MeasureSpec.makeMeasureSpec(height, 1073741824));
        imageView.layout(0, 0, width, height);
        ViewGroup viewGroup = this.mViewHolder.mRightPanel;
        int i = this.mRightPanelWidth;
        if (i == 0 || this.mRightPanelHeight == 0) {
            viewGroup.offsetLeftAndRight(width - viewGroup.getLeft());
        } else {
            viewGroup.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mRightPanelHeight, 1073741824));
            viewGroup.layout(width, viewGroup.getTop(), this.mRightPanelWidth + width, viewGroup.getTop() + this.mRightPanelHeight);
        }
        this.mViewHolder.mActionsRow.setVisibility(4);
        this.mViewHolder.mDetailsDescriptionFrame.setVisibility(4);
    }
}
