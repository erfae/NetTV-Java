package androidx.leanback.graphics;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Property;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class CompositeDrawable extends Drawable implements Drawable.Callback {
    public boolean mMutated;
    public CompositeState mState;

    public CompositeDrawable() {
        this.mMutated = false;
        this.mState = new CompositeState();
    }

    public void addChildDrawable(Drawable drawable) {
        this.mState.mChildren.add(new ChildDrawable(drawable, this));
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        ArrayList<ChildDrawable> arrayList = this.mState.mChildren;
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).mDrawable.draw(canvas);
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001c  */
    /* JADX WARN: Code duplicated, block: B:12:0x0021 A[RETURN] */
    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        Drawable drawable;
        ArrayList<ChildDrawable> arrayList = this.mState.mChildren;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            drawable = arrayList.get(i).mDrawable;
            if (drawable != null) {
                if (drawable != null) {
                    return DrawableCompat.getAlpha(drawable);
                }
                return 255;
            }
        }
        drawable = null;
        if (drawable != null) {
            return DrawableCompat.getAlpha(drawable);
        }
        return 255;
    }

    public ChildDrawable getChildAt(int i) {
        return this.mState.mChildren.get(i);
    }

    public int getChildCount() {
        return this.mState.mChildren.size();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    public Drawable getDrawable(int i) {
        return this.mState.mChildren.get(i).mDrawable;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            CompositeState compositeState = new CompositeState(this.mState, this);
            this.mState = compositeState;
            ArrayList<ChildDrawable> arrayList = compositeState.mChildren;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                Drawable drawable = arrayList.get(i).mDrawable;
                if (drawable != null) {
                    drawable.mutate();
                }
            }
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        ArrayList<ChildDrawable> arrayList = this.mState.mChildren;
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).updateBounds(rect);
        }
    }

    public void removeChild(int i) {
        this.mState.mChildren.remove(i);
    }

    public void removeDrawable(Drawable drawable) {
        ArrayList<ChildDrawable> arrayList = this.mState.mChildren;
        for (int i = 0; i < arrayList.size(); i++) {
            if (drawable == arrayList.get(i).mDrawable) {
                arrayList.get(i).mDrawable.setCallback(null);
                arrayList.remove(i);
                return;
            }
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        ArrayList<ChildDrawable> arrayList = this.mState.mChildren;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList.get(i2).mDrawable.setAlpha(i);
        }
    }

    public void setChildDrawableAt(int i, Drawable drawable) {
        this.mState.mChildren.set(i, new ChildDrawable(drawable, this));
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        ArrayList<ChildDrawable> arrayList = this.mState.mChildren;
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).mDrawable.setColorFilter(colorFilter);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    public static class CompositeState extends Drawable.ConstantState {
        public final ArrayList<ChildDrawable> mChildren;

        public CompositeState() {
            this.mChildren = new ArrayList<>();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return new CompositeDrawable(this);
        }

        public CompositeState(CompositeState compositeState, CompositeDrawable compositeDrawable) {
            int size = compositeState.mChildren.size();
            this.mChildren = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                this.mChildren.add(new ChildDrawable(compositeState.mChildren.get(i), compositeDrawable));
            }
        }
    }

    public CompositeDrawable(CompositeState compositeState) {
        this.mMutated = false;
        this.mState = compositeState;
    }

    public static final class ChildDrawable {
        private final Rect adjustedBounds;
        private final BoundsRule mBoundsRule;
        public final Drawable mDrawable;
        public final CompositeDrawable mParent;
        public static final Property<ChildDrawable, Integer> TOP_ABSOLUTE = new Property<ChildDrawable, Integer>() { // from class: androidx.leanback.graphics.CompositeDrawable.ChildDrawable.1
            @Override // android.util.Property
            public Integer get(ChildDrawable childDrawable) {
                return childDrawable.getBoundsRule().top == null ? Integer.valueOf(childDrawable.mParent.getBounds().top) : Integer.valueOf(childDrawable.getBoundsRule().top.getAbsoluteValue());
            }

            @Override // android.util.Property
            public void set(ChildDrawable childDrawable, Integer num) {
                if (childDrawable.getBoundsRule().top == null) {
                    childDrawable.getBoundsRule().top = BoundsRule.ValueRule.absoluteValue(num.intValue());
                } else {
                    childDrawable.getBoundsRule().top.setAbsoluteValue(num.intValue());
                }
                childDrawable.recomputeBounds();
            }
        };
        public static final Property<ChildDrawable, Integer> BOTTOM_ABSOLUTE = new Property<ChildDrawable, Integer>() { // from class: androidx.leanback.graphics.CompositeDrawable.ChildDrawable.2
            @Override // android.util.Property
            public Integer get(ChildDrawable childDrawable) {
                return childDrawable.getBoundsRule().bottom == null ? Integer.valueOf(childDrawable.mParent.getBounds().bottom) : Integer.valueOf(childDrawable.getBoundsRule().bottom.getAbsoluteValue());
            }

            @Override // android.util.Property
            public void set(ChildDrawable childDrawable, Integer num) {
                if (childDrawable.getBoundsRule().bottom == null) {
                    childDrawable.getBoundsRule().bottom = BoundsRule.ValueRule.absoluteValue(num.intValue());
                } else {
                    childDrawable.getBoundsRule().bottom.setAbsoluteValue(num.intValue());
                }
                childDrawable.recomputeBounds();
            }
        };
        public static final Property<ChildDrawable, Integer> LEFT_ABSOLUTE = new Property<ChildDrawable, Integer>() { // from class: androidx.leanback.graphics.CompositeDrawable.ChildDrawable.3
            @Override // android.util.Property
            public Integer get(ChildDrawable childDrawable) {
                return childDrawable.getBoundsRule().left == null ? Integer.valueOf(childDrawable.mParent.getBounds().left) : Integer.valueOf(childDrawable.getBoundsRule().left.getAbsoluteValue());
            }

            @Override // android.util.Property
            public void set(ChildDrawable childDrawable, Integer num) {
                if (childDrawable.getBoundsRule().left == null) {
                    childDrawable.getBoundsRule().left = BoundsRule.ValueRule.absoluteValue(num.intValue());
                } else {
                    childDrawable.getBoundsRule().left.setAbsoluteValue(num.intValue());
                }
                childDrawable.recomputeBounds();
            }
        };
        public static final Property<ChildDrawable, Integer> RIGHT_ABSOLUTE = new Property<ChildDrawable, Integer>() { // from class: androidx.leanback.graphics.CompositeDrawable.ChildDrawable.4
            @Override // android.util.Property
            public Integer get(ChildDrawable childDrawable) {
                return childDrawable.getBoundsRule().right == null ? Integer.valueOf(childDrawable.mParent.getBounds().right) : Integer.valueOf(childDrawable.getBoundsRule().right.getAbsoluteValue());
            }

            @Override // android.util.Property
            public void set(ChildDrawable childDrawable, Integer num) {
                if (childDrawable.getBoundsRule().right == null) {
                    childDrawable.getBoundsRule().right = BoundsRule.ValueRule.absoluteValue(num.intValue());
                } else {
                    childDrawable.getBoundsRule().right.setAbsoluteValue(num.intValue());
                }
                childDrawable.recomputeBounds();
            }
        };
        public static final Property<ChildDrawable, Float> TOP_FRACTION = new Property<ChildDrawable, Float>() { // from class: androidx.leanback.graphics.CompositeDrawable.ChildDrawable.5
            @Override // android.util.Property
            public Float get(ChildDrawable childDrawable) {
                return childDrawable.getBoundsRule().top == null ? Float.valueOf(0.0f) : Float.valueOf(childDrawable.getBoundsRule().top.getFraction());
            }

            @Override // android.util.Property
            public void set(ChildDrawable childDrawable, Float f) {
                if (childDrawable.getBoundsRule().top == null) {
                    childDrawable.getBoundsRule().top = BoundsRule.ValueRule.inheritFromParent(f.floatValue());
                } else {
                    childDrawable.getBoundsRule().top.setFraction(f.floatValue());
                }
                childDrawable.recomputeBounds();
            }
        };
        public static final Property<ChildDrawable, Float> BOTTOM_FRACTION = new Property<ChildDrawable, Float>() { // from class: androidx.leanback.graphics.CompositeDrawable.ChildDrawable.6
            @Override // android.util.Property
            public Float get(ChildDrawable childDrawable) {
                return childDrawable.getBoundsRule().bottom == null ? Float.valueOf(1.0f) : Float.valueOf(childDrawable.getBoundsRule().bottom.getFraction());
            }

            @Override // android.util.Property
            public void set(ChildDrawable childDrawable, Float f) {
                if (childDrawable.getBoundsRule().bottom == null) {
                    childDrawable.getBoundsRule().bottom = BoundsRule.ValueRule.inheritFromParent(f.floatValue());
                } else {
                    childDrawable.getBoundsRule().bottom.setFraction(f.floatValue());
                }
                childDrawable.recomputeBounds();
            }
        };
        public static final Property<ChildDrawable, Float> LEFT_FRACTION = new Property<ChildDrawable, Float>() { // from class: androidx.leanback.graphics.CompositeDrawable.ChildDrawable.7
            @Override // android.util.Property
            public Float get(ChildDrawable childDrawable) {
                return childDrawable.getBoundsRule().left == null ? Float.valueOf(0.0f) : Float.valueOf(childDrawable.getBoundsRule().left.getFraction());
            }

            @Override // android.util.Property
            public void set(ChildDrawable childDrawable, Float f) {
                if (childDrawable.getBoundsRule().left == null) {
                    childDrawable.getBoundsRule().left = BoundsRule.ValueRule.inheritFromParent(f.floatValue());
                } else {
                    childDrawable.getBoundsRule().left.setFraction(f.floatValue());
                }
                childDrawable.recomputeBounds();
            }
        };
        public static final Property<ChildDrawable, Float> RIGHT_FRACTION = new Property<ChildDrawable, Float>() { // from class: androidx.leanback.graphics.CompositeDrawable.ChildDrawable.8
            @Override // android.util.Property
            public Float get(ChildDrawable childDrawable) {
                return childDrawable.getBoundsRule().right == null ? Float.valueOf(1.0f) : Float.valueOf(childDrawable.getBoundsRule().right.getFraction());
            }

            @Override // android.util.Property
            public void set(ChildDrawable childDrawable, Float f) {
                if (childDrawable.getBoundsRule().right == null) {
                    childDrawable.getBoundsRule().right = BoundsRule.ValueRule.inheritFromParent(f.floatValue());
                } else {
                    childDrawable.getBoundsRule().right.setFraction(f.floatValue());
                }
                childDrawable.recomputeBounds();
            }
        };

        public ChildDrawable(Drawable drawable, CompositeDrawable compositeDrawable) {
            this.adjustedBounds = new Rect();
            this.mDrawable = drawable;
            this.mParent = compositeDrawable;
            this.mBoundsRule = new BoundsRule();
            drawable.setCallback(compositeDrawable);
        }

        public BoundsRule getBoundsRule() {
            return this.mBoundsRule;
        }

        public Drawable getDrawable() {
            return this.mDrawable;
        }

        public void recomputeBounds() {
            this.mBoundsRule.calculateBounds(this.mParent.getBounds(), this.adjustedBounds);
            this.mDrawable.setBounds(this.adjustedBounds);
        }

        public final void updateBounds(Rect rect) {
            this.mBoundsRule.calculateBounds(rect, this.adjustedBounds);
            this.mDrawable.setBounds(this.adjustedBounds);
        }

        public ChildDrawable(ChildDrawable childDrawable, CompositeDrawable compositeDrawable) {
            Drawable drawableNewDrawable;
            this.adjustedBounds = new Rect();
            Drawable drawable = childDrawable.mDrawable;
            if (drawable != null) {
                drawableNewDrawable = drawable.getConstantState().newDrawable();
                drawableNewDrawable.setCallback(compositeDrawable);
                DrawableCompat.setLayoutDirection(drawableNewDrawable, DrawableCompat.getLayoutDirection(drawable));
                drawableNewDrawable.setBounds(drawable.getBounds());
                drawableNewDrawable.setLevel(drawable.getLevel());
            } else {
                drawableNewDrawable = null;
            }
            BoundsRule boundsRule = childDrawable.mBoundsRule;
            if (boundsRule != null) {
                this.mBoundsRule = new BoundsRule(boundsRule);
            } else {
                this.mBoundsRule = new BoundsRule();
            }
            this.mDrawable = drawableNewDrawable;
            this.mParent = compositeDrawable;
        }
    }
}
