package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.RestrictTo;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ThumbsBar extends LinearLayout {
    public final SparseArray<Bitmap> mBitmaps;
    public int mHeroThumbHeightInPixel;
    public int mHeroThumbWidthInPixel;
    private boolean mIsUserSets;
    public int mMeasuredMarginInPixel;
    public int mNumOfThumbs;
    public int mThumbHeightInPixel;
    public int mThumbWidthInPixel;

    public ThumbsBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private int calculateNumOfThumbs(int i) {
        int iRoundUp = roundUp(i - this.mHeroThumbWidthInPixel, this.mThumbWidthInPixel + this.mMeasuredMarginInPixel);
        if (iRoundUp < 2) {
            iRoundUp = 2;
        } else if ((iRoundUp & 1) != 0) {
            iRoundUp++;
        }
        return iRoundUp + 1;
    }

    private static int roundUp(int i, int i2) {
        return ((i + i2) - 1) / i2;
    }

    private void setNumberOfThumbsInternal() {
        while (getChildCount() > this.mNumOfThumbs) {
            removeView(getChildAt(getChildCount() - 1));
        }
        while (getChildCount() < this.mNumOfThumbs) {
            addView(new ImageView(getContext()), new LinearLayout.LayoutParams(this.mThumbWidthInPixel, this.mThumbHeightInPixel));
        }
        int heroIndex = getHeroIndex();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            if (heroIndex == i) {
                layoutParams.width = this.mHeroThumbWidthInPixel;
                layoutParams.height = this.mHeroThumbHeightInPixel;
            } else {
                layoutParams.width = this.mThumbWidthInPixel;
                layoutParams.height = this.mThumbHeightInPixel;
            }
            childAt.setLayoutParams(layoutParams);
        }
    }

    public void clearThumbBitmaps() {
        for (int i = 0; i < getChildCount(); i++) {
            setThumbBitmap(i, null);
        }
        this.mBitmaps.clear();
    }

    public int getHeroIndex() {
        return getChildCount() / 2;
    }

    public Bitmap getThumbBitmap(int i) {
        return this.mBitmaps.get(i);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int heroIndex = getHeroIndex();
        View childAt = getChildAt(heroIndex);
        int width = (getWidth() / 2) - (childAt.getMeasuredWidth() / 2);
        int measuredWidth = (childAt.getMeasuredWidth() / 2) + (getWidth() / 2);
        childAt.layout(width, getPaddingTop(), measuredWidth, childAt.getMeasuredHeight() + getPaddingTop());
        int measuredHeight = (childAt.getMeasuredHeight() / 2) + getPaddingTop();
        for (int i5 = heroIndex - 1; i5 >= 0; i5--) {
            int i6 = width - this.mMeasuredMarginInPixel;
            View childAt2 = getChildAt(i5);
            childAt2.layout(i6 - childAt2.getMeasuredWidth(), measuredHeight - (childAt2.getMeasuredHeight() / 2), i6, (childAt2.getMeasuredHeight() / 2) + measuredHeight);
            width = i6 - childAt2.getMeasuredWidth();
        }
        while (true) {
            heroIndex++;
            if (heroIndex >= this.mNumOfThumbs) {
                return;
            }
            int i7 = measuredWidth + this.mMeasuredMarginInPixel;
            View childAt3 = getChildAt(heroIndex);
            childAt3.layout(i7, measuredHeight - (childAt3.getMeasuredHeight() / 2), childAt3.getMeasuredWidth() + i7, (childAt3.getMeasuredHeight() / 2) + measuredHeight);
            measuredWidth = i7 + childAt3.getMeasuredWidth();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int iCalculateNumOfThumbs;
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        if (this.mIsUserSets || this.mNumOfThumbs == (iCalculateNumOfThumbs = calculateNumOfThumbs(measuredWidth))) {
            return;
        }
        this.mNumOfThumbs = iCalculateNumOfThumbs;
        setNumberOfThumbsInternal();
    }

    public void setHeroThumbSize(int i, int i2) {
        boolean z;
        this.mHeroThumbHeightInPixel = i2;
        this.mHeroThumbWidthInPixel = i;
        int heroIndex = getHeroIndex();
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            if (heroIndex == i3) {
                View childAt = getChildAt(i3);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                boolean z2 = true;
                if (layoutParams.height != i2) {
                    layoutParams.height = i2;
                    z = true;
                } else {
                    z = false;
                }
                if (layoutParams.width != i) {
                    layoutParams.width = i;
                } else {
                    z2 = z;
                }
                if (z2) {
                    childAt.setLayoutParams(layoutParams);
                }
            }
        }
    }

    public void setNumberOfThumbs(int i) {
        this.mIsUserSets = true;
        this.mNumOfThumbs = i;
        setNumberOfThumbsInternal();
    }

    public void setThumbBitmap(int i, Bitmap bitmap) {
        this.mBitmaps.put(i, bitmap);
        ((ImageView) getChildAt(i)).setImageBitmap(bitmap);
    }

    public void setThumbSize(int i, int i2) {
        boolean z;
        this.mThumbHeightInPixel = i2;
        this.mThumbWidthInPixel = i;
        int heroIndex = getHeroIndex();
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            if (heroIndex != i3) {
                View childAt = getChildAt(i3);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                boolean z2 = true;
                if (layoutParams.height != i2) {
                    layoutParams.height = i2;
                    z = true;
                } else {
                    z = false;
                }
                if (layoutParams.width != i) {
                    layoutParams.width = i;
                } else {
                    z2 = z;
                }
                if (z2) {
                    childAt.setLayoutParams(layoutParams);
                }
            }
        }
    }

    public void setThumbSpace(int i) {
        this.mMeasuredMarginInPixel = i;
        requestLayout();
    }

    public ThumbsBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNumOfThumbs = -1;
        this.mBitmaps = new SparseArray<>();
        this.mIsUserSets = false;
        this.mThumbWidthInPixel = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_transport_thumbs_width);
        this.mThumbHeightInPixel = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_transport_thumbs_height);
        this.mHeroThumbHeightInPixel = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_transport_hero_thumbs_width);
        this.mHeroThumbWidthInPixel = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_transport_hero_thumbs_height);
        this.mMeasuredMarginInPixel = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_transport_thumbs_margin);
    }
}
