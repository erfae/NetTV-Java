package androidx.dynamicanimation.animation;

import androidx.annotation.FloatRange;

/* JADX INFO: loaded from: classes.dex */
public final class FlingAnimation extends DynamicAnimation<FlingAnimation> {
    private final DragForce mFlingForce;

    public static final class DragForce implements Force {
        private static final float DEFAULT_FRICTION = -4.2f;
        private static final float VELOCITY_THRESHOLD_MULTIPLIER = 62.5f;
        private float mFriction = DEFAULT_FRICTION;
        private final DynamicAnimation.MassState mMassState = new DynamicAnimation.MassState();
        private float mVelocityThreshold;

        @Override // androidx.dynamicanimation.animation.Force
        public float getAcceleration(float f, float f2) {
            return f2 * this.mFriction;
        }

        public final float getFrictionScalar() {
            return this.mFriction / DEFAULT_FRICTION;
        }

        @Override // androidx.dynamicanimation.animation.Force
        public boolean isAtEquilibrium(float f, float f2) {
            return Math.abs(f2) < this.mVelocityThreshold;
        }

        public final void setFrictionScalar(float f) {
            this.mFriction = f * DEFAULT_FRICTION;
        }

        public final void setValueThreshold(float f) {
            this.mVelocityThreshold = f * VELOCITY_THRESHOLD_MULTIPLIER;
        }

        public final DynamicAnimation.MassState updateValueAndVelocity(float f, float f2, long j) {
            float f3 = j;
            this.mMassState.mVelocity = (float) (Math.exp((f3 / 1000.0f) * this.mFriction) * ((double) f2));
            DynamicAnimation.MassState massState = this.mMassState;
            float f4 = this.mFriction;
            float f5 = f2 / f4;
            massState.mValue = (float) ((Math.exp((f4 * f3) / 1000.0f) * ((double) f5)) + ((double) (f - f5)));
            DynamicAnimation.MassState massState2 = this.mMassState;
            if (isAtEquilibrium(massState2.mValue, massState2.mVelocity)) {
                this.mMassState.mVelocity = 0.0f;
            }
            return this.mMassState;
        }
    }

    public FlingAnimation(FloatValueHolder floatValueHolder) {
        super(floatValueHolder);
        DragForce dragForce = new DragForce();
        this.mFlingForce = dragForce;
        dragForce.setValueThreshold(getValueThreshold());
    }

    public float getFriction() {
        return this.mFlingForce.getFrictionScalar();
    }

    public FlingAnimation setFriction(@FloatRange(from = 0.0d, fromInclusive = false) float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Friction must be positive");
        }
        this.mFlingForce.setFrictionScalar(f);
        return this;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public final void setValueThreshold(float f) {
        this.mFlingForce.setValueThreshold(f);
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public final boolean updateValueAndVelocity(long j) {
        DynamicAnimation.MassState massStateUpdateValueAndVelocity = this.mFlingForce.updateValueAndVelocity(this.mValue, this.mVelocity, j);
        float f = massStateUpdateValueAndVelocity.mValue;
        this.mValue = f;
        float f2 = massStateUpdateValueAndVelocity.mVelocity;
        this.mVelocity = f2;
        float f3 = this.mMinValue;
        if (f < f3) {
            this.mValue = f3;
            return true;
        }
        float f4 = this.mMaxValue;
        if (f <= f4) {
            return (f > f4 ? 1 : (f == f4 ? 0 : -1)) >= 0 || (f > f3 ? 1 : (f == f3 ? 0 : -1)) <= 0 || this.mFlingForce.isAtEquilibrium(f, f2);
        }
        this.mValue = f4;
        return true;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public FlingAnimation setMaxValue(float f) {
        super.setMaxValue(f);
        return this;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public FlingAnimation setMinValue(float f) {
        super.setMinValue(f);
        return this;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public FlingAnimation setStartVelocity(float f) {
        super.setStartVelocity(f);
        return this;
    }

    public <K> FlingAnimation(K k, FloatPropertyCompat<K> floatPropertyCompat) {
        super(k, floatPropertyCompat);
        DragForce dragForce = new DragForce();
        this.mFlingForce = dragForce;
        dragForce.setValueThreshold(getValueThreshold());
    }
}
