package androidx.leanback.widget;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Property;
import android.view.animation.LinearInterpolator;

/* JADX INFO: loaded from: classes.dex */
public abstract class ParallaxTarget {

    public static final class DirectPropertyTarget<T, V extends Number> extends ParallaxTarget {
        public Object mObject;
        public Property<T, V> mProperty;

        public DirectPropertyTarget(Object obj, Property<T, V> property) {
            this.mObject = obj;
            this.mProperty = property;
        }

        @Override // androidx.leanback.widget.ParallaxTarget
        public void directUpdate(Number number) {
            this.mProperty.set((T) this.mObject, (V) number);
        }

        @Override // androidx.leanback.widget.ParallaxTarget
        public boolean isDirectMapping() {
            return true;
        }
    }

    public static final class PropertyValuesHolderTarget extends ParallaxTarget {
        private static final long PSEUDO_DURATION = 1000000;
        private final ObjectAnimator mAnimator;
        private float mFraction;

        public PropertyValuesHolderTarget(Object obj, PropertyValuesHolder propertyValuesHolder) {
            ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(obj, propertyValuesHolder);
            this.mAnimator = objectAnimatorOfPropertyValuesHolder;
            objectAnimatorOfPropertyValuesHolder.setInterpolator(new LinearInterpolator());
            objectAnimatorOfPropertyValuesHolder.setDuration(1000000L);
        }

        @Override // androidx.leanback.widget.ParallaxTarget
        public void update(float f) {
            this.mFraction = f;
            this.mAnimator.setCurrentPlayTime((long) (f * 1000000.0f));
        }
    }

    public void directUpdate(Number number) {
    }

    public boolean isDirectMapping() {
        return false;
    }

    public void update(float f) {
    }
}
