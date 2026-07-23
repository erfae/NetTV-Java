package androidx.constraintlayout.motion.widget;

import androidx.annotation.NonNull;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.LinkedHashMap;

/* JADX INFO: loaded from: classes.dex */
class MotionPaths implements Comparable<MotionPaths> {
    public static final boolean DEBUG = false;
    public static final boolean OLD_WAY = false;
    public static final String TAG = "MotionPaths";
    public static String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    public LinkedHashMap<String, ConstraintAttribute> attributes;
    public float height;
    public int mAnimateRelativeTo;
    public int mDrawPath;
    public Easing mKeyFrameEasing;
    public int mMode;
    public int mPathMotionArc;
    public float mPathRotate;
    public float mProgress;
    public float mRelativeAngle;
    public MotionController mRelativeToController;
    public double[] mTempDelta;
    public double[] mTempValue;
    public float position;
    public float time;
    public float width;
    public float x;
    public float y;

    public MotionPaths() {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        int i = Key.UNSET;
        this.mPathMotionArc = i;
        this.mAnimateRelativeTo = i;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
    }

    private boolean diff(float a, float b) {
        if (Float.isNaN(a) || Float.isNaN(b)) {
            return Float.isNaN(a) != Float.isNaN(b);
        }
        return Math.abs(a - b) > 1.0E-6f;
    }

    private static final float xRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return (((x - cx) * cos) - ((y - cy) * sin)) + cx;
    }

    private static final float yRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return ((y - cy) * cos) + ((x - cx) * sin) + cy;
    }

    public void applyParameters(ConstraintSet.Constraint c) {
        this.mKeyFrameEasing = Easing.getInterpolator(c.motion.mTransitionEasing);
        ConstraintSet.Motion motion = c.motion;
        this.mPathMotionArc = motion.mPathMotionArc;
        this.mAnimateRelativeTo = motion.mAnimateRelativeTo;
        this.mPathRotate = motion.mPathRotate;
        this.mDrawPath = motion.mDrawPath;
        int i = motion.mAnimateCircleAngleTo;
        this.mProgress = c.propertySet.mProgress;
        this.mRelativeAngle = c.layout.circleAngle;
        for (String str : c.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = c.mCustomConstraints.get(str);
            if (constraintAttribute != null && constraintAttribute.isContinuous()) {
                this.attributes.put(str, constraintAttribute);
            }
        }
    }

    public void configureRelativeTo(MotionController toOrbit) {
        toOrbit.getPos(this.mProgress);
    }

    public final void different(MotionPaths motionPaths, boolean[] zArr, boolean z) {
        boolean zDiff = diff(this.x, motionPaths.x);
        boolean zDiff2 = diff(this.y, motionPaths.y);
        zArr[0] = zArr[0] | diff(this.position, motionPaths.position);
        boolean z2 = z | zDiff | zDiff2;
        zArr[1] = zArr[1] | z2;
        zArr[2] = z2 | zArr[2];
        zArr[3] = zArr[3] | diff(this.width, motionPaths.width);
        zArr[4] = diff(this.height, motionPaths.height) | zArr[4];
    }

    public final void getCenter(double p, int[] toUse, double[] data, float[] point, int offset) {
        float fSin = this.x;
        float fCos = this.y;
        float f = this.width;
        float f2 = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float f3 = (float) data[i];
            int i2 = toUse[i];
            if (i2 == 1) {
                fSin = f3;
            } else if (i2 == 2) {
                fCos = f3;
            } else if (i2 == 3) {
                f = f3;
            } else if (i2 == 4) {
                f2 = f3;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            motionController.getCenter(p, fArr, new float[2]);
            float f4 = fArr[0];
            float f5 = fArr[1];
            double d = f4;
            double d2 = fSin;
            double d3 = fCos;
            fSin = (float) (((Math.sin(d3) * d2) + d) - ((double) (f / 2.0f)));
            fCos = (float) ((((double) f5) - (Math.cos(d3) * d2)) - ((double) (f2 / 2.0f)));
        }
        point[offset] = (f / 2.0f) + fSin + 0.0f;
        point[offset + 1] = (f2 / 2.0f) + fCos + 0.0f;
    }

    public final void getRect(int[] toUse, double[] data, float[] path, int offset) {
        float f = this.x;
        float fCos = this.y;
        float f2 = this.width;
        float f3 = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float f4 = (float) data[i];
            int i2 = toUse[i];
            if (i2 == 1) {
                f = f4;
            } else if (i2 == 2) {
                fCos = f4;
            } else if (i2 == 3) {
                f2 = f4;
            } else if (i2 == 4) {
                f3 = f4;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float centerX = motionController.getCenterX();
            float centerY = this.mRelativeToController.getCenterY();
            double d = f;
            double d2 = fCos;
            float fSin = (float) (((Math.sin(d2) * d) + ((double) centerX)) - ((double) (f2 / 2.0f)));
            fCos = (float) ((((double) centerY) - (Math.cos(d2) * d)) - ((double) (f3 / 2.0f)));
            f = fSin;
        }
        float f5 = f2 + f;
        float f6 = f3 + fCos;
        Float.isNaN(Float.NaN);
        Float.isNaN(Float.NaN);
        int i3 = offset + 1;
        path[offset] = f + 0.0f;
        int i4 = i3 + 1;
        path[i3] = fCos + 0.0f;
        int i5 = i4 + 1;
        path[i4] = f5 + 0.0f;
        int i6 = i5 + 1;
        path[i5] = fCos + 0.0f;
        int i7 = i6 + 1;
        path[i6] = f5 + 0.0f;
        int i8 = i7 + 1;
        path[i7] = f6 + 0.0f;
        path[i8] = f + 0.0f;
        path[i8 + 1] = f6 + 0.0f;
    }

    public final void setBounds(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public final void setDpDt(float locationX, float locationY, float[] mAnchorDpDt, int[] toUse, double[] deltaData, double[] data) {
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        for (int i = 0; i < toUse.length; i++) {
            float f5 = (float) deltaData[i];
            double d = data[i];
            int i2 = toUse[i];
            if (i2 == 1) {
                f = f5;
            } else if (i2 == 2) {
                f3 = f5;
            } else if (i2 == 3) {
                f2 = f5;
            } else if (i2 == 4) {
                f4 = f5;
            }
        }
        float f6 = f - ((0.0f * f2) / 2.0f);
        float f7 = f3 - ((0.0f * f4) / 2.0f);
        mAnchorDpDt[0] = (((f2 * 1.0f) + f6) * locationX) + ((1.0f - locationX) * f6) + 0.0f;
        mAnchorDpDt[1] = (((f4 * 1.0f) + f7) * locationY) + ((1.0f - locationY) * f7) + 0.0f;
    }

    public void setupRelative(MotionController mc, MotionPaths relative) {
        double d = (((this.width / 2.0f) + this.x) - relative.x) - (relative.width / 2.0f);
        double d2 = (((this.height / 2.0f) + this.y) - relative.y) - (relative.height / 2.0f);
        this.mRelativeToController = mc;
        this.x = (float) Math.hypot(d2, d);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.y = (float) (Math.atan2(d2, d) + 1.5707963267948966d);
        } else {
            this.y = (float) Math.toRadians(this.mRelativeAngle);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull MotionPaths o) {
        return Float.compare(this.position, o.position);
    }

    public MotionPaths(int parentWidth, int parentHeight, KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float f;
        int i;
        float fMin;
        float fM;
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        int i2 = Key.UNSET;
        this.mPathMotionArc = i2;
        this.mAnimateRelativeTo = i2;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        if (startTimePoint.mAnimateRelativeTo != Key.UNSET) {
            float f2 = c.mFramePosition / 100.0f;
            this.time = f2;
            this.mDrawPath = c.mDrawPath;
            this.mMode = c.mPositionType;
            float f3 = Float.isNaN(c.mPercentWidth) ? f2 : c.mPercentWidth;
            float f4 = Float.isNaN(c.mPercentHeight) ? f2 : c.mPercentHeight;
            float f5 = endTimePoint.width;
            float f6 = startTimePoint.width;
            float f7 = endTimePoint.height;
            float f8 = startTimePoint.height;
            this.position = this.time;
            this.width = (int) (((f5 - f6) * f3) + f6);
            this.height = (int) (((f7 - f8) * f4) + f8);
            int i3 = c.mPositionType;
            if (i3 == 1) {
                float f9 = Float.isNaN(c.mPercentX) ? f2 : c.mPercentX;
                float f10 = endTimePoint.x;
                float f11 = startTimePoint.x;
                this.x = Insets$$ExternalSyntheticOutline0.m(f10, f11, f9, f11);
                f2 = Float.isNaN(c.mPercentY) ? f2 : c.mPercentY;
                float f12 = endTimePoint.y;
                float f13 = startTimePoint.y;
                this.y = Insets$$ExternalSyntheticOutline0.m(f12, f13, f2, f13);
            } else if (i3 != 2) {
                float f14 = Float.isNaN(c.mPercentX) ? f2 : c.mPercentX;
                float f15 = endTimePoint.x;
                float f16 = startTimePoint.x;
                this.x = Insets$$ExternalSyntheticOutline0.m(f15, f16, f14, f16);
                f2 = Float.isNaN(c.mPercentY) ? f2 : c.mPercentY;
                float f17 = endTimePoint.y;
                float f18 = startTimePoint.y;
                this.y = Insets$$ExternalSyntheticOutline0.m(f17, f18, f2, f18);
            } else {
                if (Float.isNaN(c.mPercentX)) {
                    float f19 = endTimePoint.x;
                    float f20 = startTimePoint.x;
                    fMin = Insets$$ExternalSyntheticOutline0.m(f19, f20, f2, f20);
                } else {
                    fMin = Math.min(f4, f3) * c.mPercentX;
                }
                this.x = fMin;
                if (Float.isNaN(c.mPercentY)) {
                    float f21 = endTimePoint.y;
                    float f22 = startTimePoint.y;
                    fM = Insets$$ExternalSyntheticOutline0.m(f21, f22, f2, f22);
                } else {
                    fM = c.mPercentY;
                }
                this.y = fM;
            }
            this.mAnimateRelativeTo = startTimePoint.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
            this.mPathMotionArc = c.mPathMotionArc;
            return;
        }
        int i4 = c.mPositionType;
        if (i4 == 1) {
            float f23 = c.mFramePosition / 100.0f;
            this.time = f23;
            this.mDrawPath = c.mDrawPath;
            float f24 = Float.isNaN(c.mPercentWidth) ? f23 : c.mPercentWidth;
            float f25 = Float.isNaN(c.mPercentHeight) ? f23 : c.mPercentHeight;
            float f26 = endTimePoint.width - startTimePoint.width;
            float f27 = endTimePoint.height - startTimePoint.height;
            this.position = this.time;
            f23 = Float.isNaN(c.mPercentX) ? f23 : c.mPercentX;
            float f28 = startTimePoint.x;
            float f29 = startTimePoint.width;
            float f30 = startTimePoint.y;
            float f31 = startTimePoint.height;
            float f32 = ((endTimePoint.width / 2.0f) + endTimePoint.x) - ((f29 / 2.0f) + f28);
            float f33 = ((endTimePoint.height / 2.0f) + endTimePoint.y) - ((f31 / 2.0f) + f30);
            float f34 = f32 * f23;
            float f35 = f26 * f24;
            float f36 = f35 / 2.0f;
            this.x = (int) ((f28 + f34) - f36);
            float f37 = f23 * f33;
            float f38 = f27 * f25;
            float f39 = f38 / 2.0f;
            this.y = (int) ((f30 + f37) - f39);
            this.width = (int) (f29 + f35);
            this.height = (int) (f31 + f38);
            float f40 = Float.isNaN(c.mPercentY) ? 0.0f : c.mPercentY;
            this.mMode = 1;
            float f41 = (int) ((startTimePoint.x + f34) - f36);
            float f42 = (int) ((startTimePoint.y + f37) - f39);
            this.x = f41 + ((-f33) * f40);
            this.y = f42 + (f32 * f40);
            this.mAnimateRelativeTo = this.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
            this.mPathMotionArc = c.mPathMotionArc;
            return;
        }
        if (i4 != 2) {
            float f43 = c.mFramePosition / 100.0f;
            this.time = f43;
            this.mDrawPath = c.mDrawPath;
            float f44 = Float.isNaN(c.mPercentWidth) ? f43 : c.mPercentWidth;
            float f45 = Float.isNaN(c.mPercentHeight) ? f43 : c.mPercentHeight;
            float f46 = endTimePoint.width;
            float f47 = startTimePoint.width;
            float f48 = f46 - f47;
            float f49 = endTimePoint.height;
            float f50 = startTimePoint.height;
            float f51 = f49 - f50;
            this.position = this.time;
            float f52 = startTimePoint.x;
            float f53 = startTimePoint.y;
            float f54 = ((f46 / 2.0f) + endTimePoint.x) - ((f47 / 2.0f) + f52);
            float f55 = ((f49 / 2.0f) + endTimePoint.y) - ((f50 / 2.0f) + f53);
            float f56 = f48 * f44;
            float f57 = f56 / 2.0f;
            this.x = (int) (((f54 * f43) + f52) - f57);
            float f58 = (f55 * f43) + f53;
            float f59 = f51 * f45;
            float f60 = f59 / 2.0f;
            this.y = (int) (f58 - f60);
            this.width = (int) (f47 + f56);
            this.height = (int) (f50 + f59);
            float f61 = Float.isNaN(c.mPercentX) ? f43 : c.mPercentX;
            float f62 = Float.isNaN(c.mAltPercentY) ? 0.0f : c.mAltPercentY;
            f43 = Float.isNaN(c.mPercentY) ? f43 : c.mPercentY;
            if (Float.isNaN(c.mAltPercentX)) {
                i = 0;
                f = 0.0f;
            } else {
                f = c.mAltPercentX;
                i = 0;
            }
            this.mMode = i;
            this.x = (int) (((f * f55) + ((f61 * f54) + startTimePoint.x)) - f57);
            this.y = (int) (((f55 * f43) + ((f54 * f62) + startTimePoint.y)) - f60);
            this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
            this.mPathMotionArc = c.mPathMotionArc;
            return;
        }
        float f63 = c.mFramePosition / 100.0f;
        this.time = f63;
        this.mDrawPath = c.mDrawPath;
        float f64 = Float.isNaN(c.mPercentWidth) ? f63 : c.mPercentWidth;
        float f65 = Float.isNaN(c.mPercentHeight) ? f63 : c.mPercentHeight;
        float f66 = endTimePoint.width;
        float f67 = startTimePoint.width;
        float f68 = f66 - f67;
        float f69 = endTimePoint.height;
        float f70 = startTimePoint.height;
        float f71 = f69 - f70;
        this.position = this.time;
        float f72 = startTimePoint.x;
        float f73 = startTimePoint.y;
        float f74 = (f66 / 2.0f) + endTimePoint.x;
        float f75 = (f69 / 2.0f) + endTimePoint.y;
        float f76 = f68 * f64;
        this.x = (int) ((((f74 - ((f67 / 2.0f) + f72)) * f63) + f72) - (f76 / 2.0f));
        float f77 = f71 * f65;
        this.y = (int) ((((f75 - ((f70 / 2.0f) + f73)) * f63) + f73) - (f77 / 2.0f));
        this.width = (int) (f67 + f76);
        this.height = (int) (f70 + f77);
        this.mMode = 2;
        if (!Float.isNaN(c.mPercentX)) {
            this.x = (int) (c.mPercentX * ((int) (parentWidth - this.width)));
        }
        if (!Float.isNaN(c.mPercentY)) {
            this.y = (int) (c.mPercentY * ((int) (parentHeight - this.height)));
        }
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }
}
