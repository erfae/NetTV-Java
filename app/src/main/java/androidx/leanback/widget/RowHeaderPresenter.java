package androidx.leanback.widget;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.RestrictTo;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
public class RowHeaderPresenter extends Presenter {
    private final boolean mAnimateSelect;
    private final Paint mFontMeasurePaint;
    private final int mLayoutResourceId;
    private boolean mNullItemVisibilityGone;

    public RowHeaderPresenter() {
        this(R.layout.lb_row_header);
    }

    public int getSpaceUnderBaseline(ViewHolder viewHolder) {
        int paddingBottom = viewHolder.view.getPaddingBottom();
        View view = viewHolder.view;
        if (!(view instanceof TextView)) {
            return paddingBottom;
        }
        TextView textView = (TextView) view;
        Paint paint = this.mFontMeasurePaint;
        if (paint.getTextSize() != textView.getTextSize()) {
            paint.setTextSize(textView.getTextSize());
        }
        if (paint.getTypeface() != textView.getTypeface()) {
            paint.setTypeface(textView.getTypeface());
        }
        return paddingBottom + ((int) paint.descent());
    }

    public boolean isNullItemVisibilityGone() {
        return this.mNullItemVisibilityGone;
    }

    @Override // androidx.leanback.widget.Presenter
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object obj) {
        HeaderItem headerItem = obj == null ? null : ((Row) obj).getHeaderItem();
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        if (headerItem == null) {
            RowHeaderView rowHeaderView = viewHolder2.mTitleView;
            if (rowHeaderView != null) {
                rowHeaderView.setText((CharSequence) null);
            }
            TextView textView = viewHolder2.mDescriptionView;
            if (textView != null) {
                textView.setText((CharSequence) null);
            }
            viewHolder.view.setContentDescription(null);
            if (this.mNullItemVisibilityGone) {
                viewHolder.view.setVisibility(8);
                return;
            }
            return;
        }
        RowHeaderView rowHeaderView2 = viewHolder2.mTitleView;
        if (rowHeaderView2 != null) {
            rowHeaderView2.setText(headerItem.getName());
        }
        if (viewHolder2.mDescriptionView != null) {
            if (TextUtils.isEmpty(headerItem.getDescription())) {
                viewHolder2.mDescriptionView.setVisibility(8);
            } else {
                viewHolder2.mDescriptionView.setVisibility(0);
            }
            viewHolder2.mDescriptionView.setText(headerItem.getDescription());
        }
        viewHolder.view.setContentDescription(headerItem.getContentDescription());
        viewHolder.view.setVisibility(0);
    }

    @Override // androidx.leanback.widget.Presenter
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(this.mLayoutResourceId, viewGroup, false));
        if (this.mAnimateSelect) {
            setSelectLevel(viewHolder, 0.0f);
        }
        return viewHolder;
    }

    @Override // androidx.leanback.widget.Presenter
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        RowHeaderView rowHeaderView = viewHolder2.mTitleView;
        if (rowHeaderView != null) {
            rowHeaderView.setText((CharSequence) null);
        }
        TextView textView = viewHolder2.mDescriptionView;
        if (textView != null) {
            textView.setText((CharSequence) null);
        }
        if (this.mAnimateSelect) {
            setSelectLevel(viewHolder2, 0.0f);
        }
    }

    public void setNullItemVisibilityGone(boolean z) {
        this.mNullItemVisibilityGone = z;
    }

    public final void setSelectLevel(ViewHolder viewHolder, float f) {
        viewHolder.mSelectLevel = f;
        if (this.mAnimateSelect) {
            View view = viewHolder.view;
            float f2 = viewHolder.mUnselectAlpha;
            view.setAlpha(((1.0f - f2) * f) + f2);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public RowHeaderPresenter(int i) {
        this(i, true);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public RowHeaderPresenter(int i, boolean z) {
        this.mFontMeasurePaint = new Paint(1);
        this.mLayoutResourceId = i;
        this.mAnimateSelect = z;
    }

    public static class ViewHolder extends Presenter.ViewHolder {
        public TextView mDescriptionView;
        public float mSelectLevel;
        public RowHeaderView mTitleView;
        public float mUnselectAlpha;

        public ViewHolder(View view) {
            super(view);
            this.mTitleView = (RowHeaderView) view.findViewById(R.id.row_header);
            this.mDescriptionView = (TextView) view.findViewById(R.id.row_header_description);
            initColors();
        }

        public final float getSelectLevel() {
            return this.mSelectLevel;
        }

        public final void initColors() {
            RowHeaderView rowHeaderView = this.mTitleView;
            if (rowHeaderView != null) {
                rowHeaderView.getCurrentTextColor();
            }
            this.mUnselectAlpha = this.view.getResources().getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public ViewHolder(RowHeaderView rowHeaderView) {
            super(rowHeaderView);
            this.mTitleView = rowHeaderView;
            initColors();
        }
    }
}
