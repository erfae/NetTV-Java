package androidx.leanback.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerViewParallax extends Parallax<ChildPositionProperty> {
    public boolean mIsVertical;
    public RecyclerView mRecylerView;
    public RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() { // from class: androidx.leanback.widget.RecyclerViewParallax.1
        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            RecyclerViewParallax.this.updateValues();
        }
    };
    public View.OnLayoutChangeListener mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: androidx.leanback.widget.RecyclerViewParallax.2
        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            RecyclerViewParallax.this.updateValues();
        }
    };

    public static final class ChildPositionProperty extends Parallax.IntProperty {
        public int mAdapterPosition;
        public float mFraction;
        public int mOffset;
        public int mViewId;

        public ChildPositionProperty(String str, int i) {
            super(str, i);
        }

        public ChildPositionProperty adapterPosition(int i) {
            this.mAdapterPosition = i;
            return this;
        }

        public ChildPositionProperty fraction(float f) {
            this.mFraction = f;
            return this;
        }

        public int getAdapterPosition() {
            return this.mAdapterPosition;
        }

        public float getFraction() {
            return this.mFraction;
        }

        public int getOffset() {
            return this.mOffset;
        }

        public int getViewId() {
            return this.mViewId;
        }

        public ChildPositionProperty offset(int i) {
            this.mOffset = i;
            return this;
        }

        public ChildPositionProperty viewId(int i) {
            this.mViewId = i;
            return this;
        }
    }

    @Override // androidx.leanback.widget.Parallax
    public float getMaxValue() {
        RecyclerView recyclerView = this.mRecylerView;
        if (recyclerView == null) {
            return 0.0f;
        }
        return this.mIsVertical ? recyclerView.getHeight() : recyclerView.getWidth();
    }

    public RecyclerView getRecyclerView() {
        return this.mRecylerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.mRecylerView;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            recyclerView2.removeOnScrollListener(this.mOnScrollListener);
            this.mRecylerView.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
        this.mRecylerView = recyclerView;
        if (recyclerView != null) {
            recyclerView.getLayoutManager();
            this.mIsVertical = RecyclerView.LayoutManager.getProperties(this.mRecylerView.getContext(), null, 0, 0).orientation == 1;
            this.mRecylerView.addOnScrollListener(this.mOnScrollListener);
            this.mRecylerView.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
    }

    @Override // androidx.leanback.widget.Parallax
    public void updateValues() {
        for (ChildPositionProperty childPositionProperty : getProperties()) {
            Objects.requireNonNull(childPositionProperty);
            RecyclerView recyclerView = this.mRecylerView;
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = recyclerView == null ? null : recyclerView.findViewHolderForAdapterPosition(childPositionProperty.mAdapterPosition);
            if (viewHolderFindViewHolderForAdapterPosition != null) {
                View viewFindViewById = viewHolderFindViewHolderForAdapterPosition.itemView.findViewById(childPositionProperty.mViewId);
                if (viewFindViewById != null) {
                    Rect rect = new Rect(0, 0, viewFindViewById.getWidth(), viewFindViewById.getHeight());
                    recyclerView.offsetDescendantRectToMyCoords(viewFindViewById, rect);
                    float f = 0.0f;
                    float translationY = 0.0f;
                    while (viewFindViewById != recyclerView && viewFindViewById != null) {
                        if (viewFindViewById.getParent() != recyclerView || !recyclerView.isAnimating()) {
                            float translationX = viewFindViewById.getTranslationX() + f;
                            translationY = viewFindViewById.getTranslationY() + translationY;
                            f = translationX;
                        }
                        viewFindViewById = (View) viewFindViewById.getParent();
                    }
                    rect.offset((int) f, (int) translationY);
                    if (this.mIsVertical) {
                        setIntPropertyValue(childPositionProperty.getIndex(), rect.top + childPositionProperty.mOffset + ((int) (childPositionProperty.mFraction * rect.height())));
                    } else {
                        setIntPropertyValue(childPositionProperty.getIndex(), rect.left + childPositionProperty.mOffset + ((int) (childPositionProperty.mFraction * rect.width())));
                    }
                }
            } else if (recyclerView == null || recyclerView.getLayoutManager().getChildCount() == 0) {
                setIntPropertyValue(childPositionProperty.getIndex(), Integer.MAX_VALUE);
            } else if (recyclerView.findContainingViewHolder(recyclerView.getLayoutManager().getChildAt(0)).getAdapterPosition() < childPositionProperty.mAdapterPosition) {
                setIntPropertyValue(childPositionProperty.getIndex(), Integer.MAX_VALUE);
            } else {
                setIntPropertyValue(childPositionProperty.getIndex(), Integer.MIN_VALUE);
            }
        }
        super.updateValues();
    }

    @Override // androidx.leanback.widget.Parallax
    public ChildPositionProperty createProperty(String str, int i) {
        return new ChildPositionProperty(str, i);
    }
}
