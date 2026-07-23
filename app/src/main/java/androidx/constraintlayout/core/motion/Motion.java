package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.key.MotionKey;
import androidx.constraintlayout.core.motion.key.MotionKeyAttributes;
import androidx.constraintlayout.core.motion.key.MotionKeyCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyTrigger;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.DifferentialInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.KeyFrameArray;
import androidx.constraintlayout.core.motion.utils.Rect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.constraintlayout.core.motion.utils.ViewState;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class Motion implements TypedValues {
    private static final boolean DEBUG = false;
    public static final int DRAW_PATH_AS_CONFIGURED = 4;
    public static final int DRAW_PATH_BASIC = 1;
    public static final int DRAW_PATH_CARTESIAN = 3;
    public static final int DRAW_PATH_NONE = 0;
    public static final int DRAW_PATH_RECTANGLE = 5;
    public static final int DRAW_PATH_RELATIVE = 2;
    public static final int DRAW_PATH_SCREEN = 6;
    private static final boolean FAVOR_FIXED_SIZE_VIEWS = false;
    public static final int HORIZONTAL_PATH_X = 2;
    public static final int HORIZONTAL_PATH_Y = 3;
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    private static final int INTERPOLATOR_UNDEFINED = -3;
    public static final int PATH_PERCENT = 0;
    public static final int PATH_PERPENDICULAR = 1;
    public static final int ROTATION_LEFT = 2;
    public static final int ROTATION_RIGHT = 1;
    private static final int SPLINE_STRING = -1;
    private static final String TAG = "MotionController";
    public static final int VERTICAL_PATH_X = 4;
    public static final int VERTICAL_PATH_Y = 5;
    private int MAX_DIMENSION;
    private CurveFit mArcSpline;
    private int[] mAttributeInterpolatorCount;
    private String[] mAttributeNames;
    private HashMap<String, SplineSet> mAttributesMap;
    private int mCurveFitType;
    private HashMap<String, KeyCycleOscillator> mCycleMap;
    private MotionPaths mEndMotionPath;
    private MotionConstrainedPoint mEndPoint;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private ArrayList<MotionKey> mKeyList;
    private MotionKeyTrigger[] mKeyTriggers;
    private ArrayList<MotionPaths> mMotionPaths;
    private boolean mNoMovement;
    private int mPathMotionArc;
    private DifferentialInterpolator mQuantizeMotionInterpolator;
    private float mQuantizeMotionPhase;
    private int mQuantizeMotionSteps;
    private CurveFit[] mSpline;
    public float mStaggerScale;
    private MotionPaths mStartMotionPath;
    private MotionConstrainedPoint mStartPoint;
    private HashMap<String, TimeCycleSplineSet> mTimeCycleAttributesMap;
    private int mTransformPivotTarget;
    private MotionWidget mTransformPivotView;
    private float[] mValuesBuff;
    private float[] mVelocity;
    public MotionWidget mView;

    public Motion(MotionWidget motionWidget) {
        new Rect();
        this.mCurveFitType = -1;
        this.mStartMotionPath = new MotionPaths();
        this.mEndMotionPath = new MotionPaths();
        this.mStartPoint = new MotionConstrainedPoint();
        this.mEndPoint = new MotionConstrainedPoint();
        this.mStaggerScale = 1.0f;
        this.MAX_DIMENSION = 4;
        this.mValuesBuff = new float[4];
        this.mMotionPaths = new ArrayList<>();
        this.mVelocity = new float[1];
        this.mKeyList = new ArrayList<>();
        this.mPathMotionArc = -1;
        this.mTransformPivotTarget = -1;
        this.mTransformPivotView = null;
        this.mQuantizeMotionSteps = -1;
        this.mQuantizeMotionPhase = Float.NaN;
        this.mQuantizeMotionInterpolator = null;
        this.mNoMovement = false;
        setView(motionWidget);
    }

    private float getAdjustedPosition(float f, float[] fArr) {
        float f2 = 0.0f;
        if (fArr != null) {
            fArr[0] = 1.0f;
        } else {
            float f3 = this.mStaggerScale;
            if (f3 != 1.0d) {
                if (f < 0.0f) {
                    f = 0.0f;
                }
                if (f > 0.0f && f < 1.0d) {
                    f = Math.min((f - 0.0f) * f3, 1.0f);
                }
            }
        }
        Easing easing = this.mStartMotionPath.mKeyFrameEasing;
        float f4 = Float.NaN;
        for (MotionPaths motionPaths : this.mMotionPaths) {
            Easing easing2 = motionPaths.mKeyFrameEasing;
            if (easing2 != null) {
                float f5 = motionPaths.time;
                if (f5 < f) {
                    easing = easing2;
                    f2 = f5;
                } else if (Float.isNaN(f4)) {
                    f4 = motionPaths.time;
                }
            }
        }
        if (easing != null) {
            float f6 = (Float.isNaN(f4) ? 1.0f : f4) - f2;
            double d = (f - f2) / f6;
            f = (((float) easing.get(d)) * f6) + f2;
            if (fArr != null) {
                fArr[0] = (float) easing.getDiff(d);
            }
        }
        return f;
    }

    private static DifferentialInterpolator getInterpolator(int i, String str, int i2) {
        if (i != -1) {
            return null;
        }
        final Easing interpolator = Easing.getInterpolator(str);
        return new DifferentialInterpolator() { // from class: androidx.constraintlayout.core.motion.Motion.1
            public float mX;

            @Override // androidx.constraintlayout.core.motion.utils.DifferentialInterpolator
            public float getInterpolation(float f) {
                this.mX = f;
                return (float) interpolator.get(f);
            }

            @Override // androidx.constraintlayout.core.motion.utils.DifferentialInterpolator
            public float getVelocity() {
                return (float) interpolator.getDiff(this.mX);
            }
        };
    }

    private float getPreCycleDistance() {
        char c;
        float fHypot;
        float[] fArr = new float[2];
        float f = 1.0f / 99;
        double d = 0.0d;
        double d2 = 0.0d;
        float f2 = 0.0f;
        int i = 0;
        while (i < 100) {
            float f3 = i * f;
            double d3 = f3;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            float f4 = Float.NaN;
            float f5 = 0.0f;
            for (MotionPaths motionPaths : this.mMotionPaths) {
                Easing easing2 = motionPaths.mKeyFrameEasing;
                if (easing2 != null) {
                    float f6 = motionPaths.time;
                    if (f6 < f3) {
                        easing = easing2;
                        f5 = f6;
                    } else if (Float.isNaN(f4)) {
                        f4 = motionPaths.time;
                    }
                }
            }
            if (easing != null) {
                if (Float.isNaN(f4)) {
                    f4 = 1.0f;
                }
                float f7 = f4 - f5;
                d3 = (((float) easing.get((f3 - f5) / f7)) * f7) + f5;
            }
            this.mSpline[0].getPos(d3, this.mInterpolateData);
            float f8 = f2;
            int i2 = i;
            this.mStartMotionPath.getCenter(d3, this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
            if (i2 > 0) {
                c = 0;
                fHypot = (float) (Math.hypot(d2 - ((double) fArr[1]), d - ((double) fArr[0])) + ((double) f8));
            } else {
                c = 0;
                fHypot = f8;
            }
            d = fArr[c];
            i = i2 + 1;
            f2 = fHypot;
            d2 = fArr[1];
        }
        return f2;
    }

    private void insertKey(MotionPaths motionPaths) {
        MotionPaths motionPaths2 = null;
        for (MotionPaths motionPaths3 : this.mMotionPaths) {
            if (motionPaths.position == motionPaths3.position) {
                motionPaths2 = motionPaths3;
            }
        }
        if (motionPaths2 != null) {
            this.mMotionPaths.remove(motionPaths2);
        }
        int iBinarySearch = Collections.binarySearch(this.mMotionPaths, motionPaths);
        if (iBinarySearch == 0) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(" KeyPath position \"");
            sbM.append(motionPaths.position);
            sbM.append("\" outside of range");
            Utils.loge(TAG, sbM.toString());
        }
        this.mMotionPaths.add((-iBinarySearch) - 1, motionPaths);
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.setBounds(this.mView.getX(), this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
    }

    public void addKey(MotionKey motionKey) {
        this.mKeyList.add(motionKey);
    }

    public int buildKeyFrames(float[] fArr, int[] iArr, int[] iArr2) {
        if (fArr == null) {
            return 0;
        }
        double[] timePoints = this.mSpline[0].getTimePoints();
        if (iArr != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            int i = 0;
            while (it.hasNext()) {
                iArr[i] = it.next().mMode;
                i++;
            }
        }
        if (iArr2 != null) {
            Iterator<MotionPaths> it2 = this.mMotionPaths.iterator();
            int i2 = 0;
            while (it2.hasNext()) {
                iArr2[i2] = (int) (it2.next().position * 100.0f);
                i2++;
            }
        }
        int i3 = 0;
        for (int i4 = 0; i4 < timePoints.length; i4++) {
            this.mSpline[0].getPos(timePoints[i4], this.mInterpolateData);
            this.mStartMotionPath.getCenter(timePoints[i4], this.mInterpolateVariables, this.mInterpolateData, fArr, i3);
            i3 += 2;
        }
        return i3 / 2;
    }

    public void buildPath(float[] fArr, int i) {
        double d;
        float f = 1.0f;
        float f2 = 1.0f / (i - 1);
        HashMap<String, SplineSet> map = this.mAttributesMap;
        SplineSet splineSet = map == null ? null : map.get("translationX");
        HashMap<String, SplineSet> map2 = this.mAttributesMap;
        SplineSet splineSet2 = map2 == null ? null : map2.get("translationY");
        HashMap<String, KeyCycleOscillator> map3 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator = map3 == null ? null : map3.get("translationX");
        HashMap<String, KeyCycleOscillator> map4 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator2 = map4 != null ? map4.get("translationY") : null;
        int i2 = 0;
        while (i2 < i) {
            float fMin = i2 * f2;
            float f3 = this.mStaggerScale;
            if (f3 != f) {
                if (fMin < 0.0f) {
                    fMin = 0.0f;
                }
                if (fMin > 0.0f && fMin < 1.0d) {
                    fMin = Math.min((fMin - 0.0f) * f3, f);
                }
            }
            float f4 = fMin;
            double d2 = f4;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            float f5 = Float.NaN;
            float f6 = 0.0f;
            for (MotionPaths motionPaths : this.mMotionPaths) {
                Easing easing2 = motionPaths.mKeyFrameEasing;
                double d3 = d2;
                if (easing2 != null) {
                    float f7 = motionPaths.time;
                    if (f7 < f4) {
                        f6 = f7;
                        easing = easing2;
                    } else if (Float.isNaN(f5)) {
                        f5 = motionPaths.time;
                    }
                }
                d2 = d3;
            }
            double d4 = d2;
            if (easing != null) {
                if (Float.isNaN(f5)) {
                    f5 = 1.0f;
                }
                float f8 = f5 - f6;
                d = (((float) easing.get((f4 - f6) / f8)) * f8) + f6;
            } else {
                d = d4;
            }
            this.mSpline[0].getPos(d, this.mInterpolateData);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d, dArr);
                }
            }
            int i3 = i2 * 2;
            int i4 = i2;
            this.mStartMotionPath.getCenter(d, this.mInterpolateVariables, this.mInterpolateData, fArr, i3);
            if (keyCycleOscillator != null) {
                fArr[i3] = keyCycleOscillator.get(f4) + fArr[i3];
            } else if (splineSet != null) {
                fArr[i3] = splineSet.get(f4) + fArr[i3];
            }
            if (keyCycleOscillator2 != null) {
                int i5 = i3 + 1;
                fArr[i5] = keyCycleOscillator2.get(f4) + fArr[i5];
            } else if (splineSet2 != null) {
                int i6 = i3 + 1;
                fArr[i6] = splineSet2.get(f4) + fArr[i6];
            }
            i2 = i4 + 1;
            f = 1.0f;
        }
    }

    public void buildRect(float f, float[] fArr, int i) {
        this.mSpline[0].getPos(getAdjustedPosition(f, null), this.mInterpolateData);
        MotionPaths motionPaths = this.mStartMotionPath;
        int[] iArr = this.mInterpolateVariables;
        double[] dArr = this.mInterpolateData;
        float f2 = motionPaths.x;
        float fCos = motionPaths.y;
        float f3 = motionPaths.width;
        float f4 = motionPaths.height;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f5 = (float) dArr[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f2 = f5;
            } else if (i3 == 2) {
                fCos = f5;
            } else if (i3 == 3) {
                f3 = f5;
            } else if (i3 == 4) {
                f4 = f5;
            }
        }
        Motion motion = motionPaths.mRelativeToController;
        if (motion != null) {
            float centerX = motion.getCenterX();
            float centerY = motionPaths.mRelativeToController.getCenterY();
            double d = f2;
            double d2 = fCos;
            float fSin = (float) (((Math.sin(d2) * d) + ((double) centerX)) - ((double) (f3 / 2.0f)));
            fCos = (float) ((((double) centerY) - (Math.cos(d2) * d)) - ((double) (f4 / 2.0f)));
            f2 = fSin;
        }
        float f6 = f3 + f2;
        float f7 = f4 + fCos;
        Float.isNaN(Float.NaN);
        Float.isNaN(Float.NaN);
        float f8 = f2 + 0.0f;
        float f9 = fCos + 0.0f;
        float f10 = f6 + 0.0f;
        float f11 = f7 + 0.0f;
        int i4 = i + 1;
        fArr[i] = f8;
        int i5 = i4 + 1;
        fArr[i4] = f9;
        int i6 = i5 + 1;
        fArr[i5] = f10;
        int i7 = i6 + 1;
        fArr[i6] = f9;
        int i8 = i7 + 1;
        fArr[i7] = f10;
        int i9 = i8 + 1;
        fArr[i8] = f11;
        fArr[i9] = f8;
        fArr[i9 + 1] = f11;
    }

    public int getAnimateRelativeTo() {
        return this.mStartMotionPath.mAnimateRelativeTo;
    }

    public void getCenter(double d, float[] fArr, float[] fArr2) {
        double[] dArr = new double[4];
        double[] dArr2 = new double[4];
        this.mSpline[0].getPos(d, dArr);
        this.mSpline[0].getSlope(d, dArr2);
        float f = 0.0f;
        Arrays.fill(fArr2, 0.0f);
        MotionPaths motionPaths = this.mStartMotionPath;
        int[] iArr = this.mInterpolateVariables;
        float f2 = motionPaths.x;
        float f3 = motionPaths.y;
        float f4 = motionPaths.width;
        float f5 = motionPaths.height;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float f9 = (float) dArr[i];
            float f10 = (float) dArr2[i];
            int i2 = iArr[i];
            if (i2 == 1) {
                f2 = f9;
                f = f10;
            } else if (i2 == 2) {
                f3 = f9;
                f6 = f10;
            } else if (i2 == 3) {
                f4 = f9;
                f7 = f10;
            } else if (i2 == 4) {
                f5 = f9;
                f8 = f10;
            }
        }
        float f11 = 2.0f;
        float f12 = (f7 / 2.0f) + f;
        float fSin = (f8 / 2.0f) + f6;
        Motion motion = motionPaths.mRelativeToController;
        if (motion != null) {
            float[] fArr3 = new float[2];
            float[] fArr4 = new float[2];
            motion.getCenter(d, fArr3, fArr4);
            float f13 = fArr3[0];
            float f14 = fArr3[1];
            float f15 = fArr4[0];
            float f16 = fArr4[1];
            double d2 = f2;
            double d3 = f3;
            float fSin2 = (float) (((Math.sin(d3) * d2) + ((double) f13)) - ((double) (f4 / 2.0f)));
            float fCos = (float) ((((double) f14) - (Math.cos(d3) * d2)) - ((double) (f5 / 2.0f)));
            double d4 = f15;
            double d5 = f;
            double d6 = f6;
            float fCos2 = (float) ((Math.cos(d3) * d6) + (Math.sin(d3) * d5) + d4);
            fSin = (float) ((Math.sin(d3) * d6) + (((double) f16) - (Math.cos(d3) * d5)));
            f3 = fCos;
            f12 = fCos2;
            f2 = fSin2;
            f11 = 2.0f;
        }
        fArr[0] = (f4 / f11) + f2 + 0.0f;
        fArr[1] = (f5 / f11) + f3 + 0.0f;
        fArr2[0] = f12;
        fArr2[1] = fSin;
    }

    public float getCenterX() {
        return 0.0f;
    }

    public float getCenterY() {
        return 0.0f;
    }

    public int getDrawPath() {
        int iMax = this.mStartMotionPath.mDrawPath;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            iMax = Math.max(iMax, it.next().mDrawPath);
        }
        return Math.max(iMax, this.mEndMotionPath.mDrawPath);
    }

    public float getFinalHeight() {
        return this.mEndMotionPath.height;
    }

    public float getFinalWidth() {
        return this.mEndMotionPath.width;
    }

    public float getFinalX() {
        return this.mEndMotionPath.x;
    }

    public float getFinalY() {
        return this.mEndMotionPath.y;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String str) {
        return 0;
    }

    public MotionPaths getKeyFrame(int i) {
        return this.mMotionPaths.get(i);
    }

    public int getKeyFrameInfo(int i, int[] iArr) {
        float[] fArr = new float[2];
        int i2 = 0;
        int i3 = 0;
        for (MotionKey motionKey : this.mKeyList) {
            int i4 = motionKey.mType;
            if (i4 == i || i != -1) {
                iArr[i3] = 0;
                int i5 = i3 + 1;
                iArr[i5] = i4;
                int i6 = i5 + 1;
                int i7 = motionKey.mFramePosition;
                iArr[i6] = i7;
                double d = i7 / 100.0f;
                this.mSpline[0].getPos(d, this.mInterpolateData);
                this.mStartMotionPath.getCenter(d, this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
                int i8 = i6 + 1;
                iArr[i8] = Float.floatToIntBits(fArr[0]);
                int i9 = i8 + 1;
                iArr[i9] = Float.floatToIntBits(fArr[1]);
                if (motionKey instanceof MotionKeyPosition) {
                    MotionKeyPosition motionKeyPosition = (MotionKeyPosition) motionKey;
                    int i10 = i9 + 1;
                    iArr[i10] = motionKeyPosition.mPositionType;
                    int i11 = i10 + 1;
                    iArr[i11] = Float.floatToIntBits(motionKeyPosition.mPercentX);
                    i9 = i11 + 1;
                    iArr[i9] = Float.floatToIntBits(motionKeyPosition.mPercentY);
                }
                int i12 = i9 + 1;
                iArr[i3] = i12 - i3;
                i2++;
                i3 = i12;
            }
        }
        return i2;
    }

    public int getKeyFramePositions(int[] iArr, float[] fArr) {
        int i = 0;
        int i2 = 0;
        for (MotionKey motionKey : this.mKeyList) {
            int i3 = motionKey.mFramePosition;
            iArr[i] = (motionKey.mType * 1000) + i3;
            double d = i3 / 100.0f;
            this.mSpline[0].getPos(d, this.mInterpolateData);
            this.mStartMotionPath.getCenter(d, this.mInterpolateVariables, this.mInterpolateData, fArr, i2);
            i2 += 2;
            i++;
        }
        return i;
    }

    public final double[] getPos(double d) {
        this.mSpline[0].getPos(d, this.mInterpolateData);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                curveFit.getPos(d, dArr);
            }
        }
        return this.mInterpolateData;
    }

    public float getStartHeight() {
        return this.mStartMotionPath.height;
    }

    public float getStartWidth() {
        return this.mStartMotionPath.width;
    }

    public float getStartX() {
        return this.mStartMotionPath.x;
    }

    public float getStartY() {
        return this.mStartMotionPath.y;
    }

    public int getTransformPivotTarget() {
        return this.mTransformPivotTarget;
    }

    public MotionWidget getView() {
        return this.mView;
    }

    public boolean interpolate(MotionWidget motionWidget, float f, long j, KeyCache keyCache) {
        double d;
        float f2;
        float f3;
        float f4;
        float f5;
        Motion motion = this;
        MotionWidget motionWidget2 = motionWidget;
        float adjustedPosition = motion.getAdjustedPosition(f, null);
        int i = motion.mQuantizeMotionSteps;
        if (i != -1) {
            float f6 = 1.0f / i;
            float fFloor = ((float) Math.floor(adjustedPosition / f6)) * f6;
            float f7 = (adjustedPosition % f6) / f6;
            if (!Float.isNaN(motion.mQuantizeMotionPhase)) {
                f7 = (f7 + motion.mQuantizeMotionPhase) % 1.0f;
            }
            DifferentialInterpolator differentialInterpolator = motion.mQuantizeMotionInterpolator;
            adjustedPosition = ((differentialInterpolator != null ? differentialInterpolator.getInterpolation(f7) : ((double) f7) > 0.5d ? 1.0f : 0.0f) * f6) + fFloor;
        }
        float f8 = adjustedPosition;
        HashMap<String, SplineSet> map = motion.mAttributesMap;
        if (map != null) {
            Iterator<SplineSet> it = map.values().iterator();
            while (it.hasNext()) {
                it.next().setProperty(motionWidget2, f8);
            }
        }
        CurveFit[] curveFitArr = motion.mSpline;
        if (curveFitArr != null) {
            double d2 = f8;
            curveFitArr[0].getPos(d2, motion.mInterpolateData);
            motion.mSpline[0].getSlope(d2, motion.mInterpolateVelocity);
            CurveFit curveFit = motion.mArcSpline;
            if (curveFit != null) {
                double[] dArr = motion.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d2, dArr);
                    motion.mArcSpline.getSlope(d2, motion.mInterpolateVelocity);
                }
            }
            if (motion.mNoMovement) {
                d = d2;
            } else {
                MotionPaths motionPaths = motion.mStartMotionPath;
                int[] iArr = motion.mInterpolateVariables;
                double[] dArr2 = motion.mInterpolateData;
                double[] dArr3 = motion.mInterpolateVelocity;
                float f9 = motionPaths.x;
                float f10 = motionPaths.y;
                float f11 = motionPaths.width;
                float f12 = motionPaths.height;
                if (iArr.length != 0 && motionPaths.mTempValue.length <= iArr[iArr.length - 1]) {
                    int i2 = iArr[iArr.length - 1] + 1;
                    motionPaths.mTempValue = new double[i2];
                    motionPaths.mTempDelta = new double[i2];
                }
                Arrays.fill(motionPaths.mTempValue, Double.NaN);
                for (int i3 = 0; i3 < iArr.length; i3++) {
                    motionPaths.mTempValue[iArr[i3]] = dArr2[i3];
                    motionPaths.mTempDelta[iArr[i3]] = dArr3[i3];
                }
                float f13 = 0.0f;
                float f14 = 0.0f;
                int i4 = 0;
                float f15 = 0.0f;
                float f16 = Float.NaN;
                float f17 = 0.0f;
                while (true) {
                    double[] dArr4 = motionPaths.mTempValue;
                    if (i4 >= dArr4.length) {
                        break;
                    }
                    if (Double.isNaN(dArr4[i4])) {
                        f4 = f15;
                        f5 = f16;
                    } else {
                        float f18 = (float) (Double.isNaN(motionPaths.mTempValue[i4]) ? 0.0d : motionPaths.mTempValue[i4] + 0.0d);
                        f4 = f15;
                        f5 = f16;
                        float f19 = (float) motionPaths.mTempDelta[i4];
                        if (i4 == 1) {
                            f16 = f5;
                            f9 = f18;
                            f13 = f19;
                        } else if (i4 != 2) {
                            if (i4 == 3) {
                                f15 = f4;
                                f16 = f5;
                                f11 = f18;
                                f17 = f19;
                            } else if (i4 == 4) {
                                f16 = f5;
                                f12 = f18;
                                f15 = f19;
                            } else if (i4 == 5) {
                                f16 = f18;
                            }
                            i4++;
                        } else {
                            f16 = f5;
                            f10 = f18;
                            f14 = f19;
                        }
                        f15 = f4;
                        i4++;
                    }
                    f16 = f5;
                    f15 = f4;
                    i4++;
                }
                float f20 = f15;
                float f21 = f16;
                Motion motion2 = motionPaths.mRelativeToController;
                if (motion2 != null) {
                    float[] fArr = new float[2];
                    float[] fArr2 = new float[2];
                    motion2.getCenter(d2, fArr, fArr2);
                    float f22 = fArr[0];
                    float f23 = fArr[1];
                    float f24 = fArr2[0];
                    float f25 = fArr2[1];
                    double d3 = f22;
                    d = d2;
                    double d4 = f9;
                    double d5 = f10;
                    float fSin = (float) (((Math.sin(d5) * d4) + d3) - ((double) (f11 / 2.0f)));
                    float fCos = (float) ((((double) f23) - (Math.cos(d5) * d4)) - ((double) (f12 / 2.0f)));
                    double d6 = f13;
                    f2 = f11;
                    f3 = f12;
                    double d7 = f14;
                    float fCos2 = (float) ((Math.cos(d5) * d4 * d7) + (Math.sin(d5) * d6) + ((double) f24));
                    float fSin2 = (float) ((Math.sin(d5) * d4 * d7) + (((double) f25) - (Math.cos(d5) * d6)));
                    if (dArr3.length >= 2) {
                        dArr3[0] = fCos2;
                        dArr3[1] = fSin2;
                    }
                    if (Float.isNaN(f21)) {
                        motionWidget2 = motionWidget;
                    } else {
                        motionWidget2 = motionWidget;
                        motionWidget2.setRotationZ((float) (Math.toDegrees(Math.atan2(fSin2, fCos2)) + ((double) f21)));
                    }
                    f10 = fCos;
                    f9 = fSin;
                } else {
                    motionWidget2 = motionWidget;
                    d = d2;
                    f2 = f11;
                    f3 = f12;
                    if (!Float.isNaN(f21)) {
                        motionWidget2.setRotationZ((float) (Math.toDegrees(Math.atan2((f20 / 2.0f) + f14, (f17 / 2.0f) + f13)) + ((double) f21) + ((double) 0.0f)));
                    }
                }
                float f26 = f9 + 0.5f;
                float f27 = f10 + 0.5f;
                motionWidget2.layout((int) f26, (int) f27, (int) (f26 + f2), (int) (f27 + f3));
            }
            motion = this;
            if (motion.mTransformPivotTarget != -1) {
                if (motion.mTransformPivotView == null) {
                    motion.mTransformPivotView = motionWidget.getParent().findViewById(motion.mTransformPivotTarget);
                }
                MotionWidget motionWidget3 = motion.mTransformPivotView;
                if (motionWidget3 != null) {
                    float bottom = (motion.mTransformPivotView.getBottom() + motionWidget3.getTop()) / 2.0f;
                    float right = (motion.mTransformPivotView.getRight() + motion.mTransformPivotView.getLeft()) / 2.0f;
                    if (motionWidget.getRight() - motionWidget.getLeft() > 0 && motionWidget.getBottom() - motionWidget.getTop() > 0) {
                        float left = right - motionWidget.getLeft();
                        float top = bottom - motionWidget.getTop();
                        motionWidget2.setPivotX(left);
                        motionWidget2.setPivotY(top);
                    }
                }
            }
            int i5 = 1;
            while (true) {
                CurveFit[] curveFitArr2 = motion.mSpline;
                if (i5 >= curveFitArr2.length) {
                    break;
                }
                curveFitArr2[i5].getPos(d, motion.mValuesBuff);
                motion.mStartMotionPath.customAttributes.get(motion.mAttributeNames[i5 - 1]).setInterpolatedValue(motionWidget2, motion.mValuesBuff);
                i5++;
            }
            Objects.requireNonNull(motion.mStartPoint);
            if (f8 <= 0.0f) {
                motionWidget2.setVisibility(motion.mStartPoint.visibility);
            } else if (f8 >= 1.0f) {
                motionWidget2.setVisibility(motion.mEndPoint.visibility);
            } else if (motion.mEndPoint.visibility != motion.mStartPoint.visibility) {
                motionWidget2.setVisibility(4);
            }
            if (motion.mKeyTriggers != null) {
                int i6 = 0;
                while (true) {
                    MotionKeyTrigger[] motionKeyTriggerArr = motion.mKeyTriggers;
                    if (i6 >= motionKeyTriggerArr.length) {
                        break;
                    }
                    motionKeyTriggerArr[i6].conditionallyFire(f8, motionWidget2);
                    i6++;
                }
            }
            f8 = f8;
        } else {
            MotionPaths motionPaths2 = motion.mStartMotionPath;
            float f28 = motionPaths2.x;
            MotionPaths motionPaths3 = motion.mEndMotionPath;
            float fM = Insets$$ExternalSyntheticOutline0.m(motionPaths3.x, f28, f8, f28);
            float f29 = motionPaths2.y;
            float fM2 = Insets$$ExternalSyntheticOutline0.m(motionPaths3.y, f29, f8, f29);
            float f30 = motionPaths2.width;
            float fM3 = Insets$$ExternalSyntheticOutline0.m(motionPaths3.width, f30, f8, f30);
            float f31 = motionPaths2.height;
            float f32 = fM + 0.5f;
            float f33 = fM2 + 0.5f;
            motionWidget2.layout((int) f32, (int) f33, (int) (f32 + fM3), (int) (f33 + Insets$$ExternalSyntheticOutline0.m(motionPaths3.height, f31, f8, f31)));
        }
        HashMap<String, KeyCycleOscillator> map2 = motion.mCycleMap;
        if (map2 == null) {
            return false;
        }
        for (KeyCycleOscillator keyCycleOscillator : map2.values()) {
            if (keyCycleOscillator instanceof KeyCycleOscillator.PathRotateSet) {
                double[] dArr5 = motion.mInterpolateVelocity;
                ((KeyCycleOscillator.PathRotateSet) keyCycleOscillator).setPathRotate(motionWidget, f8, dArr5[0], dArr5[1]);
            } else {
                keyCycleOscillator.setProperty(motionWidget2, f8);
            }
        }
        return false;
    }

    public void setDrawPath(int i) {
        this.mStartMotionPath.mDrawPath = i;
    }

    public void setEnd(MotionWidget motionWidget) {
        MotionPaths motionPaths = this.mEndMotionPath;
        motionPaths.time = 1.0f;
        motionPaths.position = 1.0f;
        readView(motionPaths);
        this.mEndMotionPath.setBounds(motionWidget.getLeft(), motionWidget.getTop(), motionWidget.getWidth(), motionWidget.getHeight());
        this.mEndMotionPath.applyParameters(motionWidget);
        this.mEndPoint.setState(motionWidget);
    }

    public void setPathMotionArc(int i) {
        this.mPathMotionArc = i;
    }

    public void setStart(MotionWidget motionWidget) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        motionPaths.setBounds(motionWidget.getX(), motionWidget.getY(), motionWidget.getWidth(), motionWidget.getHeight());
        this.mStartMotionPath.applyParameters(motionWidget);
        this.mStartPoint.setState(motionWidget);
    }

    public void setStartState(ViewState viewState, MotionWidget motionWidget, int i, int i2, int i3) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        Rect rect = new Rect();
        if (i == 1) {
            int i4 = viewState.left + viewState.right;
            rect.left = ((viewState.top + viewState.bottom) - viewState.width()) / 2;
            rect.top = i2 - ((viewState.height() + i4) / 2);
            rect.right = viewState.width() + rect.left;
            rect.bottom = viewState.height() + rect.top;
        } else if (i == 2) {
            int i5 = viewState.left + viewState.right;
            rect.left = i3 - ((viewState.width() + (viewState.top + viewState.bottom)) / 2);
            rect.top = (i5 - viewState.height()) / 2;
            rect.right = viewState.width() + rect.left;
            rect.bottom = viewState.height() + rect.top;
        }
        this.mStartMotionPath.setBounds(rect.left, rect.top, rect.width(), rect.height());
        this.mStartPoint.setState(rect, motionWidget, i, viewState.rotation);
    }

    public void setTransformPivotTarget(int i) {
        this.mTransformPivotTarget = i;
        this.mTransformPivotView = null;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, float f) {
        return false;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, int i2) {
        if (i != 509) {
            return i == 704;
        }
        setPathMotionArc(i2);
        return true;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, boolean z) {
        return false;
    }

    public void setView(MotionWidget motionWidget) {
        this.mView = motionWidget;
    }

    public void setup(int i, int i2, float f, long j) {
        ArrayList arrayList;
        String[] strArr;
        Class<double> cls;
        MotionPaths[] motionPathsArr;
        CustomVariable customVariable;
        SplineSet splineSetMakeSpline;
        CustomVariable customVariable2;
        Integer num;
        Iterator<String> it;
        SplineSet splineSetMakeSpline2;
        CustomVariable customVariable3;
        Class<double> cls2 = double.class;
        new HashSet();
        HashSet<String> hashSet = new HashSet<>();
        HashSet<String> hashSet2 = new HashSet<>();
        HashSet<String> hashSet3 = new HashSet<>();
        HashMap<String, Integer> map = new HashMap<>();
        int i3 = this.mPathMotionArc;
        if (i3 != -1) {
            this.mStartMotionPath.mPathMotionArc = i3;
        }
        this.mStartPoint.different(this.mEndPoint, hashSet2);
        ArrayList<MotionKey> arrayList2 = this.mKeyList;
        if (arrayList2 != null) {
            arrayList = null;
            for (MotionKey motionKey : arrayList2) {
                if (motionKey instanceof MotionKeyPosition) {
                    MotionKeyPosition motionKeyPosition = (MotionKeyPosition) motionKey;
                    insertKey(new MotionPaths(i, i2, motionKeyPosition, this.mStartMotionPath, this.mEndMotionPath));
                    int i4 = motionKeyPosition.mCurveFit;
                    if (i4 != -1) {
                        this.mCurveFitType = i4;
                    }
                } else if (motionKey instanceof MotionKeyCycle) {
                    motionKey.getAttributeNames(hashSet3);
                } else if (motionKey instanceof MotionKeyTimeCycle) {
                    motionKey.getAttributeNames(hashSet);
                } else if (motionKey instanceof MotionKeyTrigger) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add((MotionKeyTrigger) motionKey);
                } else {
                    motionKey.setInterpolation(map);
                    motionKey.getAttributeNames(hashSet2);
                }
            }
        } else {
            arrayList = null;
        }
        if (arrayList != null) {
            this.mKeyTriggers = (MotionKeyTrigger[]) arrayList.toArray(new MotionKeyTrigger[0]);
        }
        char c = 1;
        if (!hashSet2.isEmpty()) {
            this.mAttributesMap = new HashMap<>();
            Iterator<String> it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                String next = it2.next();
                if (next.startsWith("CUSTOM,")) {
                    KeyFrameArray.CustomVar customVar = new KeyFrameArray.CustomVar();
                    String str = next.split(",")[c];
                    for (MotionKey motionKey2 : this.mKeyList) {
                        Iterator<String> it3 = it2;
                        HashMap<String, CustomVariable> map2 = motionKey2.mCustom;
                        if (map2 != null && (customVariable3 = map2.get(str)) != null) {
                            customVar.append(motionKey2.mFramePosition, customVariable3);
                        }
                        it2 = it3;
                    }
                    it = it2;
                    splineSetMakeSpline2 = SplineSet.makeCustomSplineSet(next, customVar);
                } else {
                    it = it2;
                    splineSetMakeSpline2 = SplineSet.makeSpline(next, j);
                }
                if (splineSetMakeSpline2 != null) {
                    splineSetMakeSpline2.setType(next);
                    this.mAttributesMap.put(next, splineSetMakeSpline2);
                }
                c = 1;
                it2 = it;
            }
            ArrayList<MotionKey> arrayList3 = this.mKeyList;
            if (arrayList3 != null) {
                for (MotionKey motionKey3 : arrayList3) {
                    if (motionKey3 instanceof MotionKeyAttributes) {
                        motionKey3.addValues(this.mAttributesMap);
                    }
                }
            }
            this.mStartPoint.addValues(this.mAttributesMap, 0);
            this.mEndPoint.addValues(this.mAttributesMap, 100);
            for (String str2 : this.mAttributesMap.keySet()) {
                int iIntValue = (!map.containsKey(str2) || (num = map.get(str2)) == null) ? 0 : num.intValue();
                SplineSet splineSet = this.mAttributesMap.get(str2);
                if (splineSet != null) {
                    splineSet.setup(iIntValue);
                }
            }
        }
        if (!hashSet.isEmpty()) {
            if (this.mTimeCycleAttributesMap == null) {
                this.mTimeCycleAttributesMap = new HashMap<>();
            }
            for (String str3 : hashSet) {
                if (!this.mTimeCycleAttributesMap.containsKey(str3)) {
                    if (str3.startsWith("CUSTOM,")) {
                        KeyFrameArray.CustomVar customVar2 = new KeyFrameArray.CustomVar();
                        String str4 = str3.split(",")[1];
                        for (MotionKey motionKey4 : this.mKeyList) {
                            HashMap<String, CustomVariable> map3 = motionKey4.mCustom;
                            if (map3 != null && (customVariable2 = map3.get(str4)) != null) {
                                customVar2.append(motionKey4.mFramePosition, customVariable2);
                            }
                        }
                        splineSetMakeSpline = SplineSet.makeCustomSplineSet(str3, customVar2);
                    } else {
                        splineSetMakeSpline = SplineSet.makeSpline(str3, j);
                    }
                    if (splineSetMakeSpline != null) {
                        splineSetMakeSpline.setType(str3);
                    }
                }
            }
            ArrayList<MotionKey> arrayList4 = this.mKeyList;
            if (arrayList4 != null) {
                for (MotionKey motionKey5 : arrayList4) {
                    if (motionKey5 instanceof MotionKeyTimeCycle) {
                        ((MotionKeyTimeCycle) motionKey5).addTimeValues(this.mTimeCycleAttributesMap);
                    }
                }
            }
            for (String str5 : this.mTimeCycleAttributesMap.keySet()) {
                this.mTimeCycleAttributesMap.get(str5).setup(map.containsKey(str5) ? map.get(str5).intValue() : 0);
            }
        }
        char c2 = 2;
        int size = this.mMotionPaths.size() + 2;
        MotionPaths[] motionPathsArr2 = new MotionPaths[size];
        motionPathsArr2[0] = this.mStartMotionPath;
        motionPathsArr2[size - 1] = this.mEndMotionPath;
        if (this.mMotionPaths.size() > 0 && this.mCurveFitType == MotionKey.UNSET) {
            this.mCurveFitType = 0;
        }
        Iterator<MotionPaths> it4 = this.mMotionPaths.iterator();
        int i5 = 1;
        while (it4.hasNext()) {
            motionPathsArr2[i5] = it4.next();
            i5++;
        }
        HashSet hashSet4 = new HashSet();
        for (String str6 : this.mEndMotionPath.customAttributes.keySet()) {
            if (this.mStartMotionPath.customAttributes.containsKey(str6)) {
                if (!hashSet2.contains("CUSTOM," + str6)) {
                    hashSet4.add(str6);
                }
            }
        }
        String[] strArr2 = (String[]) hashSet4.toArray(new String[0]);
        this.mAttributeNames = strArr2;
        this.mAttributeInterpolatorCount = new int[strArr2.length];
        int i6 = 0;
        while (true) {
            strArr = this.mAttributeNames;
            if (i6 >= strArr.length) {
                break;
            }
            String str7 = strArr[i6];
            this.mAttributeInterpolatorCount[i6] = 0;
            for (int i7 = 0; i7 < size; i7++) {
                if (motionPathsArr2[i7].customAttributes.containsKey(str7) && (customVariable = motionPathsArr2[i7].customAttributes.get(str7)) != null) {
                    int[] iArr = this.mAttributeInterpolatorCount;
                    iArr[i6] = customVariable.numberOfInterpolatedValues() + iArr[i6];
                    break;
                }
            }
            i6++;
        }
        boolean z = motionPathsArr2[0].mPathMotionArc != -1;
        int length = 18 + strArr.length;
        boolean[] zArr = new boolean[length];
        for (int i8 = 1; i8 < size; i8++) {
            motionPathsArr2[i8].different(motionPathsArr2[i8 - 1], zArr, z);
        }
        int i9 = 0;
        for (int i10 = 1; i10 < length; i10++) {
            if (zArr[i10]) {
                i9++;
            }
        }
        this.mInterpolateVariables = new int[i9];
        int iMax = Math.max(2, i9);
        this.mInterpolateData = new double[iMax];
        this.mInterpolateVelocity = new double[iMax];
        int i11 = 0;
        for (int i12 = 1; i12 < length; i12++) {
            if (zArr[i12]) {
                this.mInterpolateVariables[i11] = i12;
                i11++;
            }
        }
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) cls2, size, this.mInterpolateVariables.length);
        double[] dArr2 = new double[size];
        int i13 = 0;
        while (true) {
            int i14 = 6;
            if (i13 >= size) {
                break;
            }
            MotionPaths motionPaths = motionPathsArr2[i13];
            double[] dArr3 = dArr[i13];
            int[] iArr2 = this.mInterpolateVariables;
            float[] fArr = new float[6];
            fArr[0] = motionPaths.position;
            fArr[1] = motionPaths.x;
            fArr[c2] = motionPaths.y;
            fArr[3] = motionPaths.width;
            fArr[4] = motionPaths.height;
            fArr[5] = motionPaths.mPathRotate;
            int i15 = 0;
            int i16 = 0;
            while (i15 < iArr2.length) {
                if (iArr2[i15] < i14) {
                    dArr3[i16] = fArr[iArr2[i15]];
                    i16++;
                }
                i15++;
                i14 = 6;
            }
            dArr2[i13] = motionPathsArr2[i13].time;
            i13++;
            c2 = 2;
        }
        int i17 = 0;
        while (true) {
            int[] iArr3 = this.mInterpolateVariables;
            if (i17 >= iArr3.length) {
                break;
            }
            int i18 = iArr3[i17];
            String[] strArr3 = MotionPaths.names;
            if (i18 < 6) {
                String strM = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), strArr3[this.mInterpolateVariables[i17]], " [");
                for (int i19 = 0; i19 < size; i19++) {
                    StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(strM);
                    sbM.append(dArr[i19][i17]);
                    strM = sbM.toString();
                }
            }
            i17++;
        }
        this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
        int i20 = 0;
        while (true) {
            String[] strArr4 = this.mAttributeNames;
            if (i20 >= strArr4.length) {
                break;
            }
            String str8 = strArr4[i20];
            int i21 = 0;
            int i22 = 0;
            double[] dArr4 = null;
            double[][] dArr5 = null;
            while (i21 < size) {
                if (motionPathsArr2[i21].customAttributes.containsKey(str8)) {
                    if (dArr5 == null) {
                        dArr4 = new double[size];
                        CustomVariable customVariable4 = motionPathsArr2[i21].customAttributes.get(str8);
                        dArr5 = (double[][]) Array.newInstance((Class<?>) cls2, size, customVariable4 == null ? 0 : customVariable4.numberOfInterpolatedValues());
                    }
                    dArr4[i22] = motionPathsArr2[i21].time;
                    MotionPaths motionPaths2 = motionPathsArr2[i21];
                    double[] dArr6 = dArr5[i22];
                    CustomVariable customVariable5 = motionPaths2.customAttributes.get(str8);
                    if (customVariable5 == null) {
                        cls = cls2;
                        motionPathsArr = motionPathsArr2;
                        dArr4 = dArr4;
                        dArr5 = dArr5;
                    } else {
                        if (customVariable5.numberOfInterpolatedValues() == 1) {
                            dArr6[0] = customVariable5.getValueToInterpolate();
                        } else {
                            int iNumberOfInterpolatedValues = customVariable5.numberOfInterpolatedValues();
                            float[] fArr2 = new float[iNumberOfInterpolatedValues];
                            customVariable5.getValuesToInterpolate(fArr2);
                            int i23 = 0;
                            int i24 = 0;
                            while (i23 < iNumberOfInterpolatedValues) {
                                dArr6[i24] = fArr2[i23];
                                i23++;
                                iNumberOfInterpolatedValues = iNumberOfInterpolatedValues;
                                i24++;
                                cls2 = cls2;
                                motionPathsArr2 = motionPathsArr2;
                            }
                        }
                        cls = cls2;
                        motionPathsArr = motionPathsArr2;
                    }
                    i22++;
                    dArr4 = dArr4;
                    dArr5 = dArr5;
                } else {
                    cls = cls2;
                    motionPathsArr = motionPathsArr2;
                    str8 = str8;
                }
                i21++;
                str8 = str8;
                cls2 = cls;
                motionPathsArr2 = motionPathsArr;
            }
            i20++;
            this.mSpline[i20] = CurveFit.get(this.mCurveFitType, Arrays.copyOf(dArr4, i22), (double[][]) Arrays.copyOf(dArr5, i22));
            cls2 = cls2;
            motionPathsArr2 = motionPathsArr2;
        }
        Class<double> cls3 = cls2;
        MotionPaths[] motionPathsArr3 = motionPathsArr2;
        this.mSpline[0] = CurveFit.get(this.mCurveFitType, dArr2, dArr);
        if (motionPathsArr3[0].mPathMotionArc != -1) {
            int[] iArr4 = new int[size];
            double[] dArr7 = new double[size];
            double[][] dArr8 = (double[][]) Array.newInstance((Class<?>) cls3, size, 2);
            for (int i25 = 0; i25 < size; i25++) {
                iArr4[i25] = motionPathsArr3[i25].mPathMotionArc;
                dArr7[i25] = motionPathsArr3[i25].time;
                dArr8[i25][0] = motionPathsArr3[i25].x;
                dArr8[i25][1] = motionPathsArr3[i25].y;
            }
            this.mArcSpline = CurveFit.getArc(iArr4, dArr7, dArr8);
        }
        float preCycleDistance = Float.NaN;
        this.mCycleMap = new HashMap<>();
        if (this.mKeyList != null) {
            for (String str9 : hashSet3) {
                KeyCycleOscillator keyCycleOscillatorMakeWidgetCycle = KeyCycleOscillator.makeWidgetCycle(str9);
                if (keyCycleOscillatorMakeWidgetCycle != null) {
                    if (keyCycleOscillatorMakeWidgetCycle.variesByPath() && Float.isNaN(preCycleDistance)) {
                        preCycleDistance = getPreCycleDistance();
                    }
                    keyCycleOscillatorMakeWidgetCycle.setType(str9);
                    this.mCycleMap.put(str9, keyCycleOscillatorMakeWidgetCycle);
                }
            }
            for (MotionKey motionKey6 : this.mKeyList) {
                if (motionKey6 instanceof MotionKeyCycle) {
                    ((MotionKeyCycle) motionKey6).addCycleValues(this.mCycleMap);
                }
            }
            Iterator<KeyCycleOscillator> it5 = this.mCycleMap.values().iterator();
            while (it5.hasNext()) {
                it5.next().setup(preCycleDistance);
            }
        }
    }

    public void setupRelative(Motion motion) {
        this.mStartMotionPath.setupRelative(motion, motion.mStartMotionPath);
        this.mEndMotionPath.setupRelative(motion, motion.mEndMotionPath);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(" start: x: ");
        sbM.append(this.mStartMotionPath.x);
        sbM.append(" y: ");
        sbM.append(this.mStartMotionPath.y);
        sbM.append(" end: x: ");
        sbM.append(this.mEndMotionPath.x);
        sbM.append(" y: ");
        sbM.append(this.mEndMotionPath.y);
        return sbM.toString();
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, String str) {
        if (705 == i) {
            System.out.println("TYPE_INTERPOLATOR  " + str);
            this.mQuantizeMotionInterpolator = getInterpolator(-1, str, 0);
        }
        return false;
    }
}
