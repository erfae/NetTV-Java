package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.HashMap;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public class MotionKeyPosition extends MotionKey {
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;
    public float mAltPercentX;
    public float mAltPercentY;
    private float mCalculatedPositionX;
    private float mCalculatedPositionY;
    public int mCurveFit;
    public int mDrawPath;
    public int mPathMotionArc;
    public float mPercentHeight;
    public float mPercentWidth;
    public float mPercentX;
    public float mPercentY;
    public int mPositionType;
    public String mTransitionEasing;

    public MotionKeyPosition() {
        int i = MotionKey.UNSET;
        this.mCurveFit = i;
        this.mTransitionEasing = null;
        this.mPathMotionArc = i;
        this.mDrawPath = 0;
        this.mPercentWidth = Float.NaN;
        this.mPercentHeight = Float.NaN;
        this.mPercentX = Float.NaN;
        this.mPercentY = Float.NaN;
        this.mAltPercentX = Float.NaN;
        this.mAltPercentY = Float.NaN;
        this.mPositionType = 0;
        this.mCalculatedPositionX = Float.NaN;
        this.mCalculatedPositionY = Float.NaN;
        this.mType = 2;
    }

    private void calcCartesianPosition(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        float f7 = Float.isNaN(this.mPercentX) ? 0.0f : this.mPercentX;
        float f8 = Float.isNaN(this.mAltPercentY) ? 0.0f : this.mAltPercentY;
        float f9 = Float.isNaN(this.mPercentY) ? 0.0f : this.mPercentY;
        this.mCalculatedPositionX = (int) (((Float.isNaN(this.mAltPercentX) ? 0.0f : this.mAltPercentX) * f6) + (f7 * f5) + f);
        this.mCalculatedPositionY = (int) ((f6 * f9) + (f5 * f8) + f2);
    }

    private void calcPathPosition(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        float f7 = this.mPercentX;
        float f8 = (f5 * f7) + f;
        float f9 = this.mPercentY;
        this.mCalculatedPositionX = ((-f6) * f9) + f8;
        this.mCalculatedPositionY = (f5 * f9) + (f6 * f7) + f2;
    }

    private void calcScreenPosition(int i, int i2) {
        float f = this.mPercentX;
        float f2 = 0;
        this.mCalculatedPositionX = ((i - 0) * f) + f2;
        this.mCalculatedPositionY = ((i2 - 0) * f) + f2;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void addValues(HashMap<String, SplineSet> map) {
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public MotionKey copy(MotionKey motionKey) {
        super.copy(motionKey);
        MotionKeyPosition motionKeyPosition = (MotionKeyPosition) motionKey;
        this.mTransitionEasing = motionKeyPosition.mTransitionEasing;
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        this.mPercentWidth = motionKeyPosition.mPercentWidth;
        this.mPercentHeight = Float.NaN;
        this.mPercentX = motionKeyPosition.mPercentX;
        this.mPercentY = motionKeyPosition.mPercentY;
        this.mAltPercentX = motionKeyPosition.mAltPercentX;
        this.mAltPercentY = motionKeyPosition.mAltPercentY;
        this.mCalculatedPositionX = motionKeyPosition.mCalculatedPositionX;
        this.mCalculatedPositionY = motionKeyPosition.mCalculatedPositionY;
        return this;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void getAttributeNames(HashSet<String> hashSet) {
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String str) {
        return TypedValues.PositionType.CC.getId(str);
    }

    public boolean intersects(int i, int i2, FloatRect floatRect, FloatRect floatRect2, float f, float f2) {
        float fCenterX = floatRect.centerX();
        float fCenterY = floatRect.centerY();
        float fCenterX2 = floatRect2.centerX();
        float fCenterY2 = floatRect2.centerY();
        int i3 = this.mPositionType;
        if (i3 == 1) {
            calcPathPosition(fCenterX, fCenterY, fCenterX2, fCenterY2);
        } else if (i3 != 2) {
            calcCartesianPosition(fCenterX, fCenterY, fCenterX2, fCenterY2);
        } else {
            calcScreenPosition(i, i2);
        }
        return Math.abs(f - this.mCalculatedPositionX) < 20.0f && Math.abs(f2 - this.mCalculatedPositionY) < 20.0f;
    }

    public void positionAttributes(MotionWidget motionWidget, FloatRect floatRect, FloatRect floatRect2, float f, float f2, String[] strArr, float[] fArr) {
        int i = this.mPositionType;
        if (i == 1) {
            float fCenterX = floatRect.centerX();
            float fCenterY = floatRect.centerY();
            float fCenterX2 = floatRect2.centerX() - fCenterX;
            float fCenterY2 = floatRect2.centerY() - fCenterY;
            float fHypot = (float) Math.hypot(fCenterX2, fCenterY2);
            if (fHypot < 1.0E-4d) {
                System.out.println("distance ~ 0");
                fArr[0] = 0.0f;
                fArr[1] = 0.0f;
                return;
            }
            float f3 = fCenterX2 / fHypot;
            float f4 = fCenterY2 / fHypot;
            float f5 = f2 - fCenterY;
            float f6 = f - fCenterX;
            float f7 = ((f3 * f5) - (f6 * f4)) / fHypot;
            float f8 = ((f4 * f5) + (f3 * f6)) / fHypot;
            if (strArr[0] != null) {
                if ("percentX".equals(strArr[0])) {
                    fArr[0] = f8;
                    fArr[1] = f7;
                    return;
                }
                return;
            }
            strArr[0] = "percentX";
            strArr[1] = "percentY";
            fArr[0] = f8;
            fArr[1] = f7;
            return;
        }
        if (i != 2) {
            float fCenterX3 = floatRect.centerX();
            float fCenterY3 = floatRect.centerY();
            float fCenterX4 = floatRect2.centerX() - fCenterX3;
            float fCenterY4 = floatRect2.centerY() - fCenterY3;
            if (strArr[0] == null) {
                strArr[0] = "percentX";
                fArr[0] = (f - fCenterX3) / fCenterX4;
                strArr[1] = "percentY";
                fArr[1] = (f2 - fCenterY3) / fCenterY4;
                return;
            }
            if ("percentX".equals(strArr[0])) {
                fArr[0] = (f - fCenterX3) / fCenterX4;
                fArr[1] = (f2 - fCenterY3) / fCenterY4;
                return;
            } else {
                fArr[1] = (f - fCenterX3) / fCenterX4;
                fArr[0] = (f2 - fCenterY3) / fCenterY4;
                return;
            }
        }
        floatRect.centerX();
        floatRect.centerY();
        floatRect2.centerX();
        floatRect2.centerY();
        MotionWidget parent = motionWidget.getParent();
        int width = parent.getWidth();
        int height = parent.getHeight();
        if (strArr[0] == null) {
            strArr[0] = "percentX";
            fArr[0] = f / width;
            strArr[1] = "percentY";
            fArr[1] = f2 / height;
            return;
        }
        if ("percentX".equals(strArr[0])) {
            fArr[0] = f / width;
            fArr[1] = f2 / height;
        } else {
            fArr[1] = f / width;
            fArr[0] = f2 / height;
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, int i2) {
        if (i == 100) {
            this.mFramePosition = i2;
            return true;
        }
        if (i == 508) {
            this.mCurveFit = i2;
            return true;
        }
        if (i != 510) {
            return super.setValue(i, i2);
        }
        this.mPositionType = i2;
        return true;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    /* JADX INFO: renamed from: clone */
    public MotionKey mo4clone() {
        return new MotionKeyPosition().copy(this);
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, float f) {
        switch (i) {
            case TypedValues.PositionType.TYPE_PERCENT_WIDTH /* 503 */:
                this.mPercentWidth = f;
                return true;
            case TypedValues.PositionType.TYPE_PERCENT_HEIGHT /* 504 */:
                this.mPercentHeight = f;
                return true;
            case TypedValues.PositionType.TYPE_SIZE_PERCENT /* 505 */:
                this.mPercentWidth = f;
                this.mPercentHeight = f;
                return true;
            case TypedValues.PositionType.TYPE_PERCENT_X /* 506 */:
                this.mPercentX = f;
                return true;
            case TypedValues.PositionType.TYPE_PERCENT_Y /* 507 */:
                this.mPercentY = f;
                return true;
            default:
                return super.setValue(i, f);
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, String str) {
        if (i != 501) {
            return super.setValue(i, str);
        }
        this.mTransitionEasing = str.toString();
        return true;
    }
}
