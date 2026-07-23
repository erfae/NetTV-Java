package androidx.leanback.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
public class DetailsOverviewLogoPresenter extends Presenter {

    public static class ViewHolder extends Presenter.ViewHolder {
        public FullWidthDetailsOverviewRowPresenter mParentPresenter;
        public FullWidthDetailsOverviewRowPresenter.ViewHolder mParentViewHolder;
        private boolean mSizeFromDrawableIntrinsic;

        public ViewHolder(View view) {
            super(view);
        }

        public FullWidthDetailsOverviewRowPresenter getParentPresenter() {
            return this.mParentPresenter;
        }

        public FullWidthDetailsOverviewRowPresenter.ViewHolder getParentViewHolder() {
            return this.mParentViewHolder;
        }

        public boolean isSizeFromDrawableIntrinsic() {
            return this.mSizeFromDrawableIntrinsic;
        }

        public void setSizeFromDrawableIntrinsic(boolean z) {
            this.mSizeFromDrawableIntrinsic = z;
        }
    }

    public boolean isBoundToImage(ViewHolder viewHolder, DetailsOverviewRow detailsOverviewRow) {
        return (detailsOverviewRow == null || detailsOverviewRow.getImageDrawable() == null) ? false : true;
    }

    @Override // androidx.leanback.widget.Presenter
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object obj) {
        DetailsOverviewRow detailsOverviewRow = (DetailsOverviewRow) obj;
        ImageView imageView = (ImageView) viewHolder.view;
        imageView.setImageDrawable(detailsOverviewRow.getImageDrawable());
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        if (isBoundToImage(viewHolder2, detailsOverviewRow)) {
            if (viewHolder2.isSizeFromDrawableIntrinsic()) {
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.width = detailsOverviewRow.getImageDrawable().getIntrinsicWidth();
                layoutParams.height = detailsOverviewRow.getImageDrawable().getIntrinsicHeight();
                if (imageView.getMaxWidth() > 0 || imageView.getMaxHeight() > 0) {
                    float maxHeight = 1.0f;
                    float maxWidth = (imageView.getMaxWidth() <= 0 || layoutParams.width <= imageView.getMaxWidth()) ? 1.0f : imageView.getMaxWidth() / layoutParams.width;
                    if (imageView.getMaxHeight() > 0 && layoutParams.height > imageView.getMaxHeight()) {
                        maxHeight = imageView.getMaxHeight() / layoutParams.height;
                    }
                    float fMin = Math.min(maxWidth, maxHeight);
                    layoutParams.width = (int) (layoutParams.width * fMin);
                    layoutParams.height = (int) (layoutParams.height * fMin);
                }
                imageView.setLayoutParams(layoutParams);
            }
            viewHolder2.mParentPresenter.notifyOnBindLogo(viewHolder2.mParentViewHolder);
        }
    }

    public View onCreateView(ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lb_fullwidth_details_overview_logo, viewGroup, false);
    }

    @Override // androidx.leanback.widget.Presenter
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        View viewOnCreateView = onCreateView(viewGroup);
        ViewHolder viewHolder = new ViewHolder(viewOnCreateView);
        ViewGroup.LayoutParams layoutParams = viewOnCreateView.getLayoutParams();
        viewHolder.setSizeFromDrawableIntrinsic(layoutParams.width == -2 && layoutParams.height == -2);
        return viewHolder;
    }

    @Override // androidx.leanback.widget.Presenter
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
    }

    public void setContext(ViewHolder viewHolder, FullWidthDetailsOverviewRowPresenter.ViewHolder viewHolder2, FullWidthDetailsOverviewRowPresenter fullWidthDetailsOverviewRowPresenter) {
        viewHolder.mParentViewHolder = viewHolder2;
        viewHolder.mParentPresenter = fullWidthDetailsOverviewRowPresenter;
    }
}
