package androidx.leanback.widget;

import android.util.Property;
import androidx.annotation.CallSuper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class Parallax<PropertyT extends Property> {
    private final List<ParallaxEffect> mEffects;
    private float[] mFloatValues;
    public final List<PropertyT> mProperties;
    public final List<PropertyT> mPropertiesReadOnly;
    private int[] mValues;

    public static class FloatProperty extends Property<Parallax, Float> {
        public static final float UNKNOWN_AFTER = Float.MAX_VALUE;
        public static final float UNKNOWN_BEFORE = -3.4028235E38f;
        private final int mIndex;

        public FloatProperty(String str, int i) {
            super(Float.class, str);
            this.mIndex = i;
        }

        public final PropertyMarkerValue at(float f, float f2) {
            return new FloatPropertyMarkerValue(this, f, f2);
        }

        public final PropertyMarkerValue atAbsolute(float f) {
            return new FloatPropertyMarkerValue(this, f, 0.0f);
        }

        public final PropertyMarkerValue atFraction(float f) {
            return new FloatPropertyMarkerValue(this, 0.0f, f);
        }

        public final PropertyMarkerValue atMax() {
            return new FloatPropertyMarkerValue(this, 0.0f, 1.0f);
        }

        public final PropertyMarkerValue atMin() {
            return new FloatPropertyMarkerValue(this, 0.0f, 0.0f);
        }

        public final int getIndex() {
            return this.mIndex;
        }

        public final float getValue(Parallax parallax) {
            return parallax.getFloatPropertyValue(this.mIndex);
        }

        public final void setValue(Parallax parallax, float f) {
            parallax.setFloatPropertyValue(this.mIndex, f);
        }

        @Override // android.util.Property
        public final Float get(Parallax parallax) {
            return Float.valueOf(parallax.getFloatPropertyValue(this.mIndex));
        }

        @Override // android.util.Property
        public final void set(Parallax parallax, Float f) {
            parallax.setFloatPropertyValue(this.mIndex, f.floatValue());
        }
    }

    public static class FloatPropertyMarkerValue extends PropertyMarkerValue<FloatProperty> {
        private final float mFactionOfMax;
        private final float mValue;

        public FloatPropertyMarkerValue(FloatProperty floatProperty, float f, float f2) {
            super(floatProperty);
            this.mValue = f;
            this.mFactionOfMax = f2;
        }

        public final float getMarkerValue(Parallax parallax) {
            if (this.mFactionOfMax == 0.0f) {
                return this.mValue;
            }
            return (parallax.getMaxValue() * this.mFactionOfMax) + this.mValue;
        }
    }

    public static class IntProperty extends Property<Parallax, Integer> {
        public static final int UNKNOWN_AFTER = Integer.MAX_VALUE;
        public static final int UNKNOWN_BEFORE = Integer.MIN_VALUE;
        private final int mIndex;

        public IntProperty(String str, int i) {
            super(Integer.class, str);
            this.mIndex = i;
        }

        public final PropertyMarkerValue at(int i, float f) {
            return new IntPropertyMarkerValue(this, i, f);
        }

        public final PropertyMarkerValue atAbsolute(int i) {
            return new IntPropertyMarkerValue(this, i, 0.0f);
        }

        public final PropertyMarkerValue atFraction(float f) {
            return new IntPropertyMarkerValue(this, 0, f);
        }

        public final PropertyMarkerValue atMax() {
            return new IntPropertyMarkerValue(this, 0, 1.0f);
        }

        public final PropertyMarkerValue atMin() {
            return new IntPropertyMarkerValue(this, 0, 0.0f);
        }

        public final int getIndex() {
            return this.mIndex;
        }

        public final int getValue(Parallax parallax) {
            return parallax.getIntPropertyValue(this.mIndex);
        }

        public final void setValue(Parallax parallax, int i) {
            parallax.setIntPropertyValue(this.mIndex, i);
        }

        @Override // android.util.Property
        public final Integer get(Parallax parallax) {
            return Integer.valueOf(parallax.getIntPropertyValue(this.mIndex));
        }

        @Override // android.util.Property
        public final void set(Parallax parallax, Integer num) {
            parallax.setIntPropertyValue(this.mIndex, num.intValue());
        }
    }

    public static class IntPropertyMarkerValue extends PropertyMarkerValue<IntProperty> {
        private final float mFactionOfMax;
        private final int mValue;

        public IntPropertyMarkerValue(IntProperty intProperty, int i, float f) {
            super(intProperty);
            this.mValue = i;
            this.mFactionOfMax = f;
        }

        public final int getMarkerValue(Parallax parallax) {
            if (this.mFactionOfMax == 0.0f) {
                return this.mValue;
            }
            return Math.round(parallax.getMaxValue() * this.mFactionOfMax) + this.mValue;
        }
    }

    public static class PropertyMarkerValue<PropertyT> {
        private final PropertyT mProperty;

        public PropertyMarkerValue(PropertyT propertyt) {
            this.mProperty = propertyt;
        }

        public PropertyT getProperty() {
            return this.mProperty;
        }
    }

    public Parallax() {
        ArrayList arrayList = new ArrayList();
        this.mProperties = arrayList;
        this.mPropertiesReadOnly = Collections.unmodifiableList(arrayList);
        this.mValues = new int[4];
        this.mFloatValues = new float[4];
        this.mEffects = new ArrayList(4);
    }

    public ParallaxEffect addEffect(PropertyMarkerValue... propertyMarkerValueArr) {
        ParallaxEffect intEffect = propertyMarkerValueArr[0].getProperty() instanceof IntProperty ? new ParallaxEffect.IntEffect() : new ParallaxEffect.FloatEffect();
        intEffect.setPropertyRanges(propertyMarkerValueArr);
        this.mEffects.add(intEffect);
        return intEffect;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.ArrayList, java.util.List<PropertyT extends android.util.Property>] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.util.ArrayList, java.util.List<PropertyT extends android.util.Property>] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public final PropertyT addProperty(String str) {
        int size = this.mProperties.size();
        PropertyT propertyt = (PropertyT) createProperty(str, size);
        int i = 0;
        if (propertyt instanceof IntProperty) {
            int length = this.mValues.length;
            if (length == size) {
                int[] iArr = new int[length * 2];
                while (i < length) {
                    iArr[i] = this.mValues[i];
                    i++;
                }
                this.mValues = iArr;
            }
            this.mValues[size] = Integer.MAX_VALUE;
        } else {
            if (!(propertyt instanceof FloatProperty)) {
                throw new IllegalArgumentException("Invalid Property type");
            }
            int length2 = this.mFloatValues.length;
            if (length2 == size) {
                float[] fArr = new float[length2 * 2];
                while (i < length2) {
                    fArr[i] = this.mFloatValues[i];
                    i++;
                }
                this.mFloatValues = fArr;
            }
            this.mFloatValues[size] = Float.MAX_VALUE;
        }
        this.mProperties.add(propertyt);
        return propertyt;
    }

    public abstract PropertyT createProperty(String str, int i);

    public List<ParallaxEffect> getEffects() {
        return this.mEffects;
    }

    public final float getFloatPropertyValue(int i) {
        return this.mFloatValues[i];
    }

    public final int getIntPropertyValue(int i) {
        return this.mValues[i];
    }

    public abstract float getMaxValue();

    public final List<PropertyT> getProperties() {
        return this.mPropertiesReadOnly;
    }

    public void removeAllEffects() {
        this.mEffects.clear();
    }

    public void removeEffect(ParallaxEffect parallaxEffect) {
        this.mEffects.remove(parallaxEffect);
    }

    public final void setFloatPropertyValue(int i, float f) {
        if (i >= this.mProperties.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.mFloatValues[i] = f;
    }

    public final void setIntPropertyValue(int i, int i2) {
        if (i >= this.mProperties.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.mValues[i] = i2;
    }

    @CallSuper
    public void updateValues() {
        for (int i = 0; i < this.mEffects.size(); i++) {
            this.mEffects.get(i).performMapping(this);
        }
    }

    public final void verifyFloatProperties() throws IllegalStateException {
        if (this.mProperties.size() < 2) {
            return;
        }
        float f = this.mFloatValues[0];
        int i = 1;
        while (i < this.mProperties.size()) {
            float f2 = this.mFloatValues[i];
            if (f2 < f) {
                int i2 = i - 1;
                throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is smaller than Property[%d]\"%s\"", Integer.valueOf(i), ((Property) this.mProperties.get(i)).getName(), Integer.valueOf(i2), ((Property) this.mProperties.get(i2)).getName()));
            }
            if (f == -3.4028235E38f && f2 == Float.MAX_VALUE) {
                int i3 = i - 1;
                throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is UNKNOWN_BEFORE and Property[%d]\"%s\" is UNKNOWN_AFTER", Integer.valueOf(i3), ((Property) this.mProperties.get(i3)).getName(), Integer.valueOf(i), ((Property) this.mProperties.get(i)).getName()));
            }
            i++;
            f = f2;
        }
    }

    public final void verifyIntProperties() throws IllegalStateException {
        if (this.mProperties.size() < 2) {
            return;
        }
        int i = this.mValues[0];
        int i2 = 1;
        while (i2 < this.mProperties.size()) {
            int i3 = this.mValues[i2];
            if (i3 < i) {
                int i4 = i2 - 1;
                throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is smaller than Property[%d]\"%s\"", Integer.valueOf(i2), ((Property) this.mProperties.get(i2)).getName(), Integer.valueOf(i4), ((Property) this.mProperties.get(i4)).getName()));
            }
            if (i == Integer.MIN_VALUE && i3 == Integer.MAX_VALUE) {
                int i5 = i2 - 1;
                throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is UNKNOWN_BEFORE and Property[%d]\"%s\" is UNKNOWN_AFTER", Integer.valueOf(i5), ((Property) this.mProperties.get(i5)).getName(), Integer.valueOf(i2), ((Property) this.mProperties.get(i2)).getName()));
            }
            i2++;
            i = i3;
        }
    }
}
