package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class MotionPaths implements Comparable<MotionPaths> {
    public static final int CARTESIAN = 0;
    public static final boolean DEBUG = false;
    public static final boolean OLD_WAY = false;
    public static final int PERPENDICULAR = 1;
    public static final int SCREEN = 2;
    public static final String TAG = "MotionPaths";
    public static String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    public HashMap<String, CustomVariable> customAttributes;
    public float height;
    public int mAnimateRelativeTo;
    public int mDrawPath;
    public Easing mKeyFrameEasing;
    public int mMode;
    public int mPathMotionArc;
    public float mPathRotate;
    public float mProgress;
    public float mRelativeAngle;
    public Motion mRelativeToController;
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
        this.mPathMotionArc = -1;
        this.mAnimateRelativeTo = -1;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.customAttributes = new HashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
    }

    private boolean diff(float f, float f2) {
        if (Float.isNaN(f) || Float.isNaN(f2)) {
            return Float.isNaN(f) != Float.isNaN(f2);
        }
        return Math.abs(f - f2) > 1.0E-6f;
    }

    private static final float xRotate(float f, float f2, float f3, float f4, float f5, float f6) {
        return (((f5 - f3) * f2) - ((f6 - f4) * f)) + f3;
    }

    private static final float yRotate(float f, float f2, float f3, float f4, float f5, float f6) {
        return ((f6 - f4) * f2) + ((f5 - f3) * f) + f4;
    }

    public void applyParameters(MotionWidget motionWidget) {
        this.mKeyFrameEasing = Easing.getInterpolator(motionWidget.motion.mTransitionEasing);
        MotionWidget.Motion motion = motionWidget.motion;
        this.mPathMotionArc = motion.mPathMotionArc;
        this.mAnimateRelativeTo = motion.mAnimateRelativeTo;
        this.mPathRotate = motion.mPathRotate;
        this.mDrawPath = motion.mDrawPath;
        int i = motion.mAnimateCircleAngleTo;
        this.mProgress = motionWidget.propertySet.mProgress;
        this.mRelativeAngle = 0.0f;
        for (String str : motionWidget.getCustomAttributeNames()) {
            CustomVariable customAttribute = motionWidget.getCustomAttribute(str);
            if (customAttribute != null && customAttribute.isContinuous()) {
                this.customAttributes.put(str, customAttribute);
            }
        }
    }

    public void configureRelativeTo(Motion motion) {
        motion.getPos(this.mProgress);
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

    public final void getCenter(double d, int[] iArr, double[] dArr, float[] fArr, int i) {
        float fSin = this.x;
        float fCos = this.y;
        float f = this.width;
        float f2 = this.height;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f3 = (float) dArr[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                fSin = f3;
            } else if (i3 == 2) {
                fCos = f3;
            } else if (i3 == 3) {
                f = f3;
            } else if (i3 == 4) {
                f2 = f3;
            }
        }
        Motion motion = this.mRelativeToController;
        if (motion != null) {
            float[] fArr2 = new float[2];
            motion.getCenter(d, fArr2, new float[2]);
            float f4 = fArr2[0];
            float f5 = fArr2[1];
            double d2 = f4;
            double d3 = fSin;
            double d4 = fCos;
            fSin = (float) (((Math.sin(d4) * d3) + d2) - ((double) (f / 2.0f)));
            fCos = (float) ((((double) f5) - (Math.cos(d4) * d3)) - ((double) (f2 / 2.0f)));
        }
        fArr[i] = (f / 2.0f) + fSin + 0.0f;
        fArr[i + 1] = (f2 / 2.0f) + fCos + 0.0f;
    }

    public final void setBounds(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public void setupRelative(Motion motion, MotionPaths motionPaths) {
        double d = (((this.width / 2.0f) + this.x) - motionPaths.x) - (motionPaths.width / 2.0f);
        double d2 = (((this.height / 2.0f) + this.y) - motionPaths.y) - (motionPaths.height / 2.0f);
        this.mRelativeToController = motion;
        this.x = (float) Math.hypot(d2, d);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.y = (float) (Math.atan2(d2, d) + 1.5707963267948966d);
        } else {
            this.y = (float) Math.toRadians(this.mRelativeAngle);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(MotionPaths motionPaths) {
        return Float.compare(this.position, motionPaths.position);
    }

    public MotionPaths(int i, int i2, MotionKeyPosition motionKeyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f;
        int i3;
        float fMin;
        float fM;
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = -1;
        this.mAnimateRelativeTo = -1;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.customAttributes = new HashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        if (motionPaths.mAnimateRelativeTo != -1) {
            float f2 = motionKeyPosition.mFramePosition / 100.0f;
            this.time = f2;
            this.mDrawPath = motionKeyPosition.mDrawPath;
            this.mMode = motionKeyPosition.mPositionType;
            float f3 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f2 : motionKeyPosition.mPercentWidth;
            float f4 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f2 : motionKeyPosition.mPercentHeight;
            float f5 = motionPaths2.width;
            float f6 = motionPaths.width;
            float f7 = motionPaths2.height;
            float f8 = motionPaths.height;
            this.position = this.time;
            this.width = (int) (((f5 - f6) * f3) + f6);
            this.height = (int) (((f7 - f8) * f4) + f8);
            int i4 = motionKeyPosition.mPositionType;
            if (i4 == 1) {
                float f9 = Float.isNaN(motionKeyPosition.mPercentX) ? f2 : motionKeyPosition.mPercentX;
                float f10 = motionPaths2.x;
                float f11 = motionPaths.x;
                this.x = Insets$$ExternalSyntheticOutline0.m(f10, f11, f9, f11);
                f2 = Float.isNaN(motionKeyPosition.mPercentY) ? f2 : motionKeyPosition.mPercentY;
                float f12 = motionPaths2.y;
                float f13 = motionPaths.y;
                this.y = Insets$$ExternalSyntheticOutline0.m(f12, f13, f2, f13);
            } else if (i4 != 2) {
                float f14 = Float.isNaN(motionKeyPosition.mPercentX) ? f2 : motionKeyPosition.mPercentX;
                float f15 = motionPaths2.x;
                float f16 = motionPaths.x;
                this.x = Insets$$ExternalSyntheticOutline0.m(f15, f16, f14, f16);
                f2 = Float.isNaN(motionKeyPosition.mPercentY) ? f2 : motionKeyPosition.mPercentY;
                float f17 = motionPaths2.y;
                float f18 = motionPaths.y;
                this.y = Insets$$ExternalSyntheticOutline0.m(f17, f18, f2, f18);
            } else {
                if (Float.isNaN(motionKeyPosition.mPercentX)) {
                    float f19 = motionPaths2.x;
                    float f20 = motionPaths.x;
                    fMin = Insets$$ExternalSyntheticOutline0.m(f19, f20, f2, f20);
                } else {
                    fMin = motionKeyPosition.mPercentX * Math.min(f4, f3);
                }
                this.x = fMin;
                if (Float.isNaN(motionKeyPosition.mPercentY)) {
                    float f21 = motionPaths2.y;
                    float f22 = motionPaths.y;
                    fM = Insets$$ExternalSyntheticOutline0.m(f21, f22, f2, f22);
                } else {
                    fM = motionKeyPosition.mPercentY;
                }
                this.y = fM;
            }
            this.mAnimateRelativeTo = motionPaths.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
            this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
            return;
        }
        int i5 = motionKeyPosition.mPositionType;
        if (i5 == 1) {
            float f23 = motionKeyPosition.mFramePosition / 100.0f;
            this.time = f23;
            this.mDrawPath = motionKeyPosition.mDrawPath;
            float f24 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f23 : motionKeyPosition.mPercentWidth;
            float f25 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f23 : motionKeyPosition.mPercentHeight;
            float f26 = motionPaths2.width - motionPaths.width;
            float f27 = motionPaths2.height - motionPaths.height;
            this.position = this.time;
            f23 = Float.isNaN(motionKeyPosition.mPercentX) ? f23 : motionKeyPosition.mPercentX;
            float f28 = motionPaths.x;
            float f29 = motionPaths.width;
            float f30 = motionPaths.y;
            float f31 = motionPaths.height;
            float f32 = ((motionPaths2.width / 2.0f) + motionPaths2.x) - ((f29 / 2.0f) + f28);
            float f33 = ((motionPaths2.height / 2.0f) + motionPaths2.y) - ((f31 / 2.0f) + f30);
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
            float f40 = Float.isNaN(motionKeyPosition.mPercentY) ? 0.0f : motionKeyPosition.mPercentY;
            this.mMode = 1;
            float f41 = (int) ((motionPaths.x + f34) - f36);
            float f42 = (int) ((motionPaths.y + f37) - f39);
            this.x = f41 + ((-f33) * f40);
            this.y = f42 + (f32 * f40);
            this.mAnimateRelativeTo = this.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
            this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
            return;
        }
        if (i5 != 2) {
            float f43 = motionKeyPosition.mFramePosition / 100.0f;
            this.time = f43;
            this.mDrawPath = motionKeyPosition.mDrawPath;
            float f44 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f43 : motionKeyPosition.mPercentWidth;
            float f45 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f43 : motionKeyPosition.mPercentHeight;
            float f46 = motionPaths2.width;
            float f47 = motionPaths.width;
            float f48 = f46 - f47;
            float f49 = motionPaths2.height;
            float f50 = motionPaths.height;
            float f51 = f49 - f50;
            this.position = this.time;
            float f52 = motionPaths.x;
            float f53 = motionPaths.y;
            float f54 = ((f46 / 2.0f) + motionPaths2.x) - ((f47 / 2.0f) + f52);
            float f55 = ((f49 / 2.0f) + motionPaths2.y) - ((f50 / 2.0f) + f53);
            float f56 = f48 * f44;
            float f57 = f56 / 2.0f;
            this.x = (int) (((f54 * f43) + f52) - f57);
            float f58 = (f55 * f43) + f53;
            float f59 = f51 * f45;
            float f60 = f59 / 2.0f;
            this.y = (int) (f58 - f60);
            this.width = (int) (f47 + f56);
            this.height = (int) (f50 + f59);
            float f61 = Float.isNaN(motionKeyPosition.mPercentX) ? f43 : motionKeyPosition.mPercentX;
            float f62 = Float.isNaN(motionKeyPosition.mAltPercentY) ? 0.0f : motionKeyPosition.mAltPercentY;
            f43 = Float.isNaN(motionKeyPosition.mPercentY) ? f43 : motionKeyPosition.mPercentY;
            if (Float.isNaN(motionKeyPosition.mAltPercentX)) {
                i3 = 0;
                f = 0.0f;
            } else {
                f = motionKeyPosition.mAltPercentX;
                i3 = 0;
            }
            this.mMode = i3;
            this.x = (int) (((f * f55) + ((f61 * f54) + motionPaths.x)) - f57);
            this.y = (int) (((f55 * f43) + ((f54 * f62) + motionPaths.y)) - f60);
            this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
            this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
            return;
        }
        float f63 = motionKeyPosition.mFramePosition / 100.0f;
        this.time = f63;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        float f64 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f63 : motionKeyPosition.mPercentWidth;
        float f65 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f63 : motionKeyPosition.mPercentHeight;
        float f66 = motionPaths2.width;
        float f67 = motionPaths.width;
        float f68 = f66 - f67;
        float f69 = motionPaths2.height;
        float f70 = motionPaths.height;
        float f71 = f69 - f70;
        this.position = this.time;
        float f72 = motionPaths.x;
        float f73 = motionPaths.y;
        float f74 = (f66 / 2.0f) + motionPaths2.x;
        float f75 = (f69 / 2.0f) + motionPaths2.y;
        float f76 = f68 * f64;
        this.x = (int) ((((f74 - ((f67 / 2.0f) + f72)) * f63) + f72) - (f76 / 2.0f));
        float f77 = f71 * f65;
        this.y = (int) ((((f75 - ((f70 / 2.0f) + f73)) * f63) + f73) - (f77 / 2.0f));
        this.width = (int) (f67 + f76);
        this.height = (int) (f70 + f77);
        this.mMode = 2;
        if (!Float.isNaN(motionKeyPosition.mPercentX)) {
            this.x = (int) (motionKeyPosition.mPercentX * ((int) (i - this.width)));
        }
        if (!Float.isNaN(motionKeyPosition.mPercentY)) {
            this.y = (int) (motionKeyPosition.mPercentY * ((int) (i2 - this.height)));
        }
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
    }
}
