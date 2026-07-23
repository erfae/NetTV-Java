package androidx.leanback.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
public class TitleView extends FrameLayout implements TitleViewAdapter.Provider {
    private int flags;
    private ImageView mBadgeView;
    private boolean mHasSearchListener;
    private SearchOrbView mSearchOrbView;
    private TextView mTextView;
    private final TitleViewAdapter mTitleViewAdapter;

    public TitleView(Context context) {
        this(context, null);
    }

    private void updateBadgeVisibility() {
        if (this.mBadgeView.getDrawable() != null) {
            this.mBadgeView.setVisibility(0);
            this.mTextView.setVisibility(8);
        } else {
            this.mBadgeView.setVisibility(8);
            this.mTextView.setVisibility(0);
        }
    }

    private void updateSearchOrbViewVisiblity() {
        int i = 4;
        if (this.mHasSearchListener && (this.flags & 4) == 4) {
            i = 0;
        }
        this.mSearchOrbView.setVisibility(i);
    }

    public void enableAnimation(boolean z) {
        SearchOrbView searchOrbView = this.mSearchOrbView;
        searchOrbView.enableOrbColorAnimation(z && searchOrbView.hasFocus());
    }

    public Drawable getBadgeDrawable() {
        return this.mBadgeView.getDrawable();
    }

    public SearchOrbView.Colors getSearchAffordanceColors() {
        return this.mSearchOrbView.getOrbColors();
    }

    public View getSearchAffordanceView() {
        return this.mSearchOrbView;
    }

    public CharSequence getTitle() {
        return this.mTextView.getText();
    }

    @Override // androidx.leanback.widget.TitleViewAdapter.Provider
    public TitleViewAdapter getTitleViewAdapter() {
        return this.mTitleViewAdapter;
    }

    public void setBadgeDrawable(Drawable drawable) {
        this.mBadgeView.setImageDrawable(drawable);
        updateBadgeVisibility();
    }

    public void setOnSearchClickedListener(View.OnClickListener onClickListener) {
        this.mHasSearchListener = onClickListener != null;
        this.mSearchOrbView.setOnOrbClickedListener(onClickListener);
        updateSearchOrbViewVisiblity();
    }

    public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
        this.mSearchOrbView.setOrbColors(colors);
    }

    public void setTitle(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
        updateBadgeVisibility();
    }

    public void updateComponentsVisibility(int i) {
        this.flags = i;
        if ((i & 2) == 2) {
            updateBadgeVisibility();
        } else {
            this.mBadgeView.setVisibility(8);
            this.mTextView.setVisibility(8);
        }
        updateSearchOrbViewVisiblity();
    }

    public TitleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.browseTitleViewStyle);
    }

    public TitleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.flags = 6;
        this.mHasSearchListener = false;
        this.mTitleViewAdapter = new TitleViewAdapter() { // from class: androidx.leanback.widget.TitleView.1
            @Override // androidx.leanback.widget.TitleViewAdapter
            public Drawable getBadgeDrawable() {
                return TitleView.this.getBadgeDrawable();
            }

            @Override // androidx.leanback.widget.TitleViewAdapter
            public SearchOrbView.Colors getSearchAffordanceColors() {
                return TitleView.this.getSearchAffordanceColors();
            }

            @Override // androidx.leanback.widget.TitleViewAdapter
            public View getSearchAffordanceView() {
                return TitleView.this.getSearchAffordanceView();
            }

            @Override // androidx.leanback.widget.TitleViewAdapter
            public CharSequence getTitle() {
                return TitleView.this.getTitle();
            }

            @Override // androidx.leanback.widget.TitleViewAdapter
            public void setAnimationEnabled(boolean z) {
                TitleView.this.enableAnimation(z);
            }

            @Override // androidx.leanback.widget.TitleViewAdapter
            public void setBadgeDrawable(Drawable drawable) {
                TitleView.this.setBadgeDrawable(drawable);
            }

            @Override // androidx.leanback.widget.TitleViewAdapter
            public void setOnSearchClickedListener(View.OnClickListener onClickListener) {
                TitleView.this.setOnSearchClickedListener(onClickListener);
            }

            @Override // androidx.leanback.widget.TitleViewAdapter
            public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
                TitleView.this.setSearchAffordanceColors(colors);
            }

            @Override // androidx.leanback.widget.TitleViewAdapter
            public void setTitle(CharSequence charSequence) {
                TitleView.this.setTitle(charSequence);
            }

            @Override // androidx.leanback.widget.TitleViewAdapter
            public void updateComponentsVisibility(int i2) {
                TitleView.this.updateComponentsVisibility(i2);
            }
        };
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.lb_title_view, this);
        this.mBadgeView = (ImageView) viewInflate.findViewById(R.id.title_badge);
        this.mTextView = (TextView) viewInflate.findViewById(R.id.title_text);
        this.mSearchOrbView = (SearchOrbView) viewInflate.findViewById(R.id.title_orb);
        setClipToPadding(false);
        setClipChildren(false);
    }
}
