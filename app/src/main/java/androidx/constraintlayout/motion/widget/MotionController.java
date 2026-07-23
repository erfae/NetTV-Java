package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.motion.utils.CustomSupport;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewState;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class MotionController {
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
    private CurveFit mArcSpline;
    private int[] mAttributeInterpolatorCount;
    private String[] mAttributeNames;
    private HashMap<String, ViewSpline> mAttributesMap;
    private HashMap<String, ViewOscillator> mCycleMap;
    public int mId;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private KeyTrigger[] mKeyTriggers;
    private boolean mNoMovement;
    private int mPathMotionArc;
    private Interpolator mQuantizeMotionInterpolator;
    private float mQuantizeMotionPhase;
    private int mQuantizeMotionSteps;
    private CurveFit[] mSpline;
    private HashMap<String, ViewTimeCycle> mTimeCycleAttributesMap;
    private int mTransformPivotTarget;
    private View mTransformPivotView;
    public View mView;
    public Rect mTempRect = new Rect();
    public boolean mForceMeasure = false;
    private int mCurveFitType = -1;
    private MotionPaths mStartMotionPath = new MotionPaths();
    private MotionPaths mEndMotionPath = new MotionPaths();
    private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
    public float mMotionStagger = Float.NaN;
    public float mStaggerOffset = 0.0f;
    public float mStaggerScale = 1.0f;
    private int MAX_DIMENSION = 4;
    private float[] mValuesBuff = new float[4];
    private ArrayList<MotionPaths> mMotionPaths = new ArrayList<>();
    private float[] mVelocity = new float[1];
    private ArrayList<Key> mKeyList = new ArrayList<>();

    public MotionController(View view) {
        int i = Key.UNSET;
        this.mPathMotionArc = i;
        this.mTransformPivotTarget = i;
        this.mTransformPivotView = null;
        this.mQuantizeMotionSteps = i;
        this.mQuantizeMotionPhase = Float.NaN;
        this.mQuantizeMotionInterpolator = null;
        this.mNoMovement = false;
        setView(view);
    }

    private float getAdjustedPosition(float position, float[] velocity) {
        float f = 0.0f;
        if (velocity != null) {
            velocity[0] = 1.0f;
        } else {
            float f2 = this.mStaggerScale;
            if (f2 != 1.0d) {
                float f3 = this.mStaggerOffset;
                if (position < f3) {
                    position = 0.0f;
                }
                if (position > f3 && position < 1.0d) {
                    position = Math.min((position - f3) * f2, 1.0f);
                }
            }
        }
        Easing easing = this.mStartMotionPath.mKeyFrameEasing;
        float f4 = Float.NaN;
        for (MotionPaths motionPaths : this.mMotionPaths) {
            Easing easing2 = motionPaths.mKeyFrameEasing;
            if (easing2 != null) {
                float f5 = motionPaths.time;
                if (f5 < position) {
                    easing = easing2;
                    f = f5;
                } else if (Float.isNaN(f4)) {
                    f4 = motionPaths.time;
                }
            }
        }
        if (easing != null) {
            float f6 = (Float.isNaN(f4) ? 1.0f : f4) - f;
            double d = (position - f) / f6;
            position = (((float) easing.get(d)) * f6) + f;
            if (velocity != null) {
                velocity[0] = (float) easing.getDiff(d);
            }
        }
        return position;
    }

    private static Interpolator getInterpolator(Context context, int type, String interpolatorString, int id) {
        if (type == -2) {
            return AnimationUtils.loadInterpolator(context, id);
        }
        if (type == -1) {
            final Easing interpolator = Easing.getInterpolator(interpolatorString);
            return new Interpolator() { // from class: androidx.constraintlayout.motion.widget.MotionController.1
                @Override // android.animation.TimeInterpolator
                public float getInterpolation(float v) {
                    return (float) interpolator.get(v);
                }
            };
        }
        if (type == 0) {
            return new AccelerateDecelerateInterpolator();
        }
        if (type == 1) {
            return new AccelerateInterpolator();
        }
        if (type == 2) {
            return new DecelerateInterpolator();
        }
        if (type == 4) {
            return new BounceInterpolator();
        }
        if (type != 5) {
            return null;
        }
        return new OvershootInterpolator();
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

    private void insertKey(MotionPaths point) {
        int iBinarySearch = Collections.binarySearch(this.mMotionPaths, point);
        if (iBinarySearch == 0) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(" KeyPath position \"");
            sbM.append(point.position);
            sbM.append("\" outside of range");
            Log.e(TAG, sbM.toString());
        }
        this.mMotionPaths.add((-iBinarySearch) - 1, point);
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((int) this.mView.getX(), (int) this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
    }

    public void addKey(Key key) {
        this.mKeyList.add(key);
    }

    public final void addKeys(ArrayList<Key> list) {
        this.mKeyList.addAll(list);
    }

    public final int buildKeyFrames(float[] keyFrames, int[] mode) {
        if (keyFrames == null) {
            return 0;
        }
        double[] timePoints = this.mSpline[0].getTimePoints();
        if (mode != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            int i = 0;
            while (it.hasNext()) {
                mode[i] = it.next().mMode;
                i++;
            }
        }
        int i2 = 0;
        for (int i3 = 0; i3 < timePoints.length; i3++) {
            this.mSpline[0].getPos(timePoints[i3], this.mInterpolateData);
            this.mStartMotionPath.getCenter(timePoints[i3], this.mInterpolateVariables, this.mInterpolateData, keyFrames, i2);
            i2 += 2;
        }
        return i2 / 2;
    }

    public final void buildPath(float[] points, int pointCount) {
        double d;
        float f = 1.0f;
        float f2 = 1.0f / (pointCount - 1);
        HashMap<String, ViewSpline> map = this.mAttributesMap;
        ViewSpline viewSpline = map == null ? null : map.get("translationX");
        HashMap<String, ViewSpline> map2 = this.mAttributesMap;
        ViewSpline viewSpline2 = map2 == null ? null : map2.get("translationY");
        HashMap<String, ViewOscillator> map3 = this.mCycleMap;
        ViewOscillator viewOscillator = map3 == null ? null : map3.get("translationX");
        HashMap<String, ViewOscillator> map4 = this.mCycleMap;
        ViewOscillator viewOscillator2 = map4 != null ? map4.get("translationY") : null;
        int i = 0;
        while (i < pointCount) {
            float fMin = i * f2;
            float f3 = this.mStaggerScale;
            if (f3 != f) {
                float f4 = this.mStaggerOffset;
                if (fMin < f4) {
                    fMin = 0.0f;
                }
                if (fMin > f4 && fMin < 1.0d) {
                    fMin = Math.min((fMin - f4) * f3, f);
                }
            }
            float f5 = fMin;
            double d2 = f5;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            float f6 = Float.NaN;
            float f7 = 0.0f;
            for (MotionPaths motionPaths : this.mMotionPaths) {
                Easing easing2 = motionPaths.mKeyFrameEasing;
                double d3 = d2;
                if (easing2 != null) {
                    float f8 = motionPaths.time;
                    if (f8 < f5) {
                        f7 = f8;
                        easing = easing2;
                    } else if (Float.isNaN(f6)) {
                        f6 = motionPaths.time;
                    }
                }
                d2 = d3;
            }
            double d4 = d2;
            if (easing != null) {
                if (Float.isNaN(f6)) {
                    f6 = 1.0f;
                }
                float f9 = f6 - f7;
                d = (((float) easing.get((f5 - f7) / f9)) * f9) + f7;
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
            int i2 = i * 2;
            int i3 = i;
            this.mStartMotionPath.getCenter(d, this.mInterpolateVariables, this.mInterpolateData, points, i2);
            if (viewOscillator != null) {
                points[i2] = viewOscillator.get(f5) + points[i2];
            } else if (viewSpline != null) {
                points[i2] = viewSpline.get(f5) + points[i2];
            }
            if (viewOscillator2 != null) {
                int i4 = i2 + 1;
                points[i4] = viewOscillator2.get(f5) + points[i4];
            } else if (viewSpline2 != null) {
                int i5 = i2 + 1;
                points[i5] = viewSpline2.get(f5) + points[i5];
            }
            i = i3 + 1;
            f = 1.0f;
        }
    }

    public final void buildRect(float f, float[] fArr) {
        this.mSpline[0].getPos(getAdjustedPosition(f, null), this.mInterpolateData);
        this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
    }

    public final void buildRectangles(float[] path, int pointCount) {
        float f = 1.0f / (pointCount - 1);
        for (int i = 0; i < pointCount; i++) {
            this.mSpline[0].getPos(getAdjustedPosition(i * f, null), this.mInterpolateData);
            this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, path, i * 8);
        }
    }

    public final void endTrigger(boolean start) {
        if (!"button".equals(Debug.getName(this.mView)) || this.mKeyTriggers == null) {
            return;
        }
        int i = 0;
        while (true) {
            KeyTrigger[] keyTriggerArr = this.mKeyTriggers;
            if (i >= keyTriggerArr.length) {
                return;
            }
            keyTriggerArr[i].conditionallyFire(start ? -100.0f : 100.0f, this.mView);
            i++;
        }
    }

    public int getAnimateRelativeTo() {
        return this.mStartMotionPath.mAnimateRelativeTo;
    }

    public final int getAttributeValues(String str, float[] fArr) {
        ViewSpline viewSpline = this.mAttributesMap.get(str);
        if (viewSpline == null) {
            return -1;
        }
        for (int i = 0; i < fArr.length; i++) {
            fArr[i] = viewSpline.get(i / (fArr.length - 1));
        }
        return fArr.length;
    }

    public void getCenter(double p, float[] pos, float[] vel) {
        double[] dArr = new double[4];
        double[] dArr2 = new double[4];
        this.mSpline[0].getPos(p, dArr);
        this.mSpline[0].getSlope(p, dArr2);
        float f = 0.0f;
        Arrays.fill(vel, 0.0f);
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
        MotionController motionController = motionPaths.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            float[] fArr2 = new float[2];
            motionController.getCenter(p, fArr, fArr2);
            float f13 = fArr[0];
            float f14 = fArr[1];
            float f15 = fArr2[0];
            float f16 = fArr2[1];
            double d = f2;
            double d2 = f3;
            float fSin2 = (float) (((Math.sin(d2) * d) + ((double) f13)) - ((double) (f4 / 2.0f)));
            float fCos = (float) ((((double) f14) - (Math.cos(d2) * d)) - ((double) (f5 / 2.0f)));
            double d3 = f15;
            double d4 = f;
            double d5 = f6;
            float fCos2 = (float) ((Math.cos(d2) * d5) + (Math.sin(d2) * d4) + d3);
            fSin = (float) ((Math.sin(d2) * d5) + (((double) f16) - (Math.cos(d2) * d4)));
            f3 = fCos;
            f12 = fCos2;
            f2 = fSin2;
            f11 = 2.0f;
        }
        pos[0] = (f4 / f11) + f2 + 0.0f;
        pos[1] = (f5 / f11) + f3 + 0.0f;
        vel[0] = f12;
        vel[1] = fSin;
    }

    public float getCenterX() {
        return 0.0f;
    }

    public float getCenterY() {
        return 0.0f;
    }

    public final void getDpDt(float position, float locationX, float locationY, float[] mAnchorDpDt) {
        double[] dArr;
        float adjustedPosition = getAdjustedPosition(position, this.mVelocity);
        CurveFit[] curveFitArr = this.mSpline;
        int i = 0;
        if (curveFitArr == null) {
            MotionPaths motionPaths = this.mEndMotionPath;
            float f = motionPaths.x;
            MotionPaths motionPaths2 = this.mStartMotionPath;
            float f2 = f - motionPaths2.x;
            float f3 = motionPaths.y - motionPaths2.y;
            float f4 = motionPaths.width - motionPaths2.width;
            float f5 = (motionPaths.height - motionPaths2.height) + f3;
            mAnchorDpDt[0] = ((f4 + f2) * locationX) + ((1.0f - locationX) * f2);
            mAnchorDpDt[1] = (f5 * locationY) + ((1.0f - locationY) * f3);
            return;
        }
        double d = adjustedPosition;
        curveFitArr[0].getSlope(d, this.mInterpolateVelocity);
        this.mSpline[0].getPos(d, this.mInterpolateData);
        float f6 = this.mVelocity[0];
        while (true) {
            dArr = this.mInterpolateVelocity;
            if (i >= dArr.length) {
                break;
            }
            dArr[i] = dArr[i] * ((double) f6);
            i++;
        }
        CurveFit curveFit = this.mArcSpline;
        if (curveFit == null) {
            this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, dArr, this.mInterpolateData);
            return;
        }
        double[] dArr2 = this.mInterpolateData;
        if (dArr2.length > 0) {
            curveFit.getPos(d, dArr2);
            this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
            this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
        }
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

    public final MotionPaths getKeyFrame(int i) {
        return this.mMotionPaths.get(i);
    }

    public int getKeyFrameInfo(int type, int[] info) {
        float[] fArr = new float[2];
        int i = 0;
        int i2 = 0;
        for (Key key : this.mKeyList) {
            int i3 = key.mType;
            if (i3 == type || type != -1) {
                info[i2] = 0;
                int i4 = i2 + 1;
                info[i4] = i3;
                int i5 = i4 + 1;
                int i6 = key.mFramePosition;
                info[i5] = i6;
                double d = i6 / 100.0f;
                this.mSpline[0].getPos(d, this.mInterpolateData);
                this.mStartMotionPath.getCenter(d, this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
                int i7 = i5 + 1;
                info[i7] = Float.floatToIntBits(fArr[0]);
                int i8 = i7 + 1;
                info[i8] = Float.floatToIntBits(fArr[1]);
                if (key instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) key;
                    int i9 = i8 + 1;
                    info[i9] = keyPosition.mPositionType;
                    int i10 = i9 + 1;
                    info[i10] = Float.floatToIntBits(keyPosition.mPercentX);
                    i8 = i10 + 1;
                    info[i8] = Float.floatToIntBits(keyPosition.mPercentY);
                }
                int i11 = i8 + 1;
                info[i2] = i11 - i2;
                i++;
                i2 = i11;
            }
        }
        return i;
    }

    public final float getKeyFrameParameter(int type, float x, float y) {
        MotionPaths motionPaths = this.mEndMotionPath;
        float f = motionPaths.x;
        MotionPaths motionPaths2 = this.mStartMotionPath;
        float f2 = motionPaths2.x;
        float f3 = f - f2;
        float f4 = motionPaths.y;
        float f5 = motionPaths2.y;
        float f6 = f4 - f5;
        float f7 = (motionPaths2.width / 2.0f) + f2;
        float f8 = (motionPaths2.height / 2.0f) + f5;
        float fHypot = (float) Math.hypot(f3, f6);
        if (fHypot < 1.0E-7d) {
            return Float.NaN;
        }
        float f9 = x - f7;
        float f10 = y - f8;
        if (((float) Math.hypot(f9, f10)) == 0.0f) {
            return 0.0f;
        }
        float f11 = (f10 * f6) + (f9 * f3);
        if (type == 0) {
            return f11 / fHypot;
        }
        if (type == 1) {
            return (float) Math.sqrt((fHypot * fHypot) - (f11 * f11));
        }
        if (type == 2) {
            return f9 / f3;
        }
        if (type == 3) {
            return f10 / f3;
        }
        if (type == 4) {
            return f9 / f6;
        }
        if (type != 5) {
            return 0.0f;
        }
        return f10 / f6;
    }

    public int getKeyFramePositions(int[] type, float[] pos) {
        int i = 0;
        int i2 = 0;
        for (Key key : this.mKeyList) {
            int i3 = key.mFramePosition;
            type[i] = (key.mType * 1000) + i3;
            double d = i3 / 100.0f;
            this.mSpline[0].getPos(d, this.mInterpolateData);
            this.mStartMotionPath.getCenter(d, this.mInterpolateVariables, this.mInterpolateData, pos, i2);
            i2 += 2;
            i++;
        }
        return i;
    }

    public final double[] getPos(double position) {
        this.mSpline[0].getPos(position, this.mInterpolateData);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                curveFit.getPos(position, dArr);
            }
        }
        return this.mInterpolateData;
    }

    public final KeyPositionBase getPositionKeyframe(int layoutWidth, int layoutHeight, float x, float y) {
        RectF rectF = new RectF();
        MotionPaths motionPaths = this.mStartMotionPath;
        float f = motionPaths.x;
        rectF.left = f;
        float f2 = motionPaths.y;
        rectF.top = f2;
        rectF.right = f + motionPaths.width;
        rectF.bottom = f2 + motionPaths.height;
        RectF rectF2 = new RectF();
        MotionPaths motionPaths2 = this.mEndMotionPath;
        float f3 = motionPaths2.x;
        rectF2.left = f3;
        float f4 = motionPaths2.y;
        rectF2.top = f4;
        rectF2.right = f3 + motionPaths2.width;
        rectF2.bottom = f4 + motionPaths2.height;
        for (Key key : this.mKeyList) {
            if (key instanceof KeyPositionBase) {
                KeyPositionBase keyPositionBase = (KeyPositionBase) key;
                if (keyPositionBase.intersects(layoutWidth, layoutHeight, rectF, rectF2, x, y)) {
                    return keyPositionBase;
                }
            }
        }
        return null;
    }

    public final void getPostLayoutDvDp(float position, int width, int height, float locationX, float locationY, float[] mAnchorDpDt) {
        float adjustedPosition = getAdjustedPosition(position, this.mVelocity);
        HashMap<String, ViewSpline> map = this.mAttributesMap;
        ViewSpline viewSpline = map == null ? null : map.get("translationX");
        HashMap<String, ViewSpline> map2 = this.mAttributesMap;
        ViewSpline viewSpline2 = map2 == null ? null : map2.get("translationY");
        HashMap<String, ViewSpline> map3 = this.mAttributesMap;
        ViewSpline viewSpline3 = map3 == null ? null : map3.get(Key.ROTATION);
        HashMap<String, ViewSpline> map4 = this.mAttributesMap;
        ViewSpline viewSpline4 = map4 == null ? null : map4.get("scaleX");
        HashMap<String, ViewSpline> map5 = this.mAttributesMap;
        ViewSpline viewSpline5 = map5 == null ? null : map5.get("scaleY");
        HashMap<String, ViewOscillator> map6 = this.mCycleMap;
        ViewOscillator viewOscillator = map6 == null ? null : map6.get("translationX");
        HashMap<String, ViewOscillator> map7 = this.mCycleMap;
        ViewOscillator viewOscillator2 = map7 == null ? null : map7.get("translationY");
        HashMap<String, ViewOscillator> map8 = this.mCycleMap;
        ViewOscillator viewOscillator3 = map8 == null ? null : map8.get(Key.ROTATION);
        HashMap<String, ViewOscillator> map9 = this.mCycleMap;
        ViewOscillator viewOscillator4 = map9 == null ? null : map9.get("scaleX");
        HashMap<String, ViewOscillator> map10 = this.mCycleMap;
        ViewOscillator viewOscillator5 = map10 != null ? map10.get("scaleY") : null;
        VelocityMatrix velocityMatrix = new VelocityMatrix();
        velocityMatrix.clear();
        velocityMatrix.setRotationVelocity(viewSpline3, adjustedPosition);
        velocityMatrix.setTranslationVelocity(viewSpline, viewSpline2, adjustedPosition);
        velocityMatrix.setScaleVelocity(viewSpline4, viewSpline5, adjustedPosition);
        velocityMatrix.setRotationVelocity(viewOscillator3, adjustedPosition);
        velocityMatrix.setTranslationVelocity(viewOscillator, viewOscillator2, adjustedPosition);
        velocityMatrix.setScaleVelocity(viewOscillator4, viewOscillator5, adjustedPosition);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                double d = adjustedPosition;
                curveFit.getPos(d, dArr);
                this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
                this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            }
            velocityMatrix.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
            return;
        }
        int i = 0;
        if (this.mSpline == null) {
            MotionPaths motionPaths = this.mEndMotionPath;
            float f = motionPaths.x;
            MotionPaths motionPaths2 = this.mStartMotionPath;
            float f2 = f - motionPaths2.x;
            ViewOscillator viewOscillator6 = viewOscillator5;
            float f3 = motionPaths.y - motionPaths2.y;
            ViewOscillator viewOscillator7 = viewOscillator4;
            float f4 = motionPaths.width - motionPaths2.width;
            float f5 = (motionPaths.height - motionPaths2.height) + f3;
            mAnchorDpDt[0] = ((f4 + f2) * locationX) + ((1.0f - locationX) * f2);
            mAnchorDpDt[1] = (f5 * locationY) + ((1.0f - locationY) * f3);
            velocityMatrix.clear();
            velocityMatrix.setRotationVelocity(viewSpline3, adjustedPosition);
            velocityMatrix.setTranslationVelocity(viewSpline, viewSpline2, adjustedPosition);
            velocityMatrix.setScaleVelocity(viewSpline4, viewSpline5, adjustedPosition);
            velocityMatrix.setRotationVelocity(viewOscillator3, adjustedPosition);
            velocityMatrix.setTranslationVelocity(viewOscillator, viewOscillator2, adjustedPosition);
            velocityMatrix.setScaleVelocity(viewOscillator7, viewOscillator6, adjustedPosition);
            velocityMatrix.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
            return;
        }
        double adjustedPosition2 = getAdjustedPosition(adjustedPosition, this.mVelocity);
        this.mSpline[0].getSlope(adjustedPosition2, this.mInterpolateVelocity);
        this.mSpline[0].getPos(adjustedPosition2, this.mInterpolateData);
        float f6 = this.mVelocity[0];
        while (true) {
            double[] dArr2 = this.mInterpolateVelocity;
            if (i >= dArr2.length) {
                this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, dArr2, this.mInterpolateData);
                velocityMatrix.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
                return;
            } else {
                dArr2[i] = dArr2[i] * ((double) f6);
                i++;
            }
        }
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

    public View getView() {
        return this.mView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean interpolate(View child, float global_position, long time, KeyCache keyCache) {
        ViewTimeCycle.PathRotate pathRotate;
        boolean z;
        float f;
        MotionController motionController;
        char c;
        boolean pathRotate2;
        float f2;
        double d;
        float f3;
        boolean z2;
        float f4;
        float adjustedPosition = getAdjustedPosition(global_position, null);
        int i = this.mQuantizeMotionSteps;
        if (i != Key.UNSET) {
            float f5 = 1.0f / i;
            float fFloor = ((float) Math.floor(adjustedPosition / f5)) * f5;
            float f6 = (adjustedPosition % f5) / f5;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                f6 = (f6 + this.mQuantizeMotionPhase) % 1.0f;
            }
            Interpolator interpolator = this.mQuantizeMotionInterpolator;
            adjustedPosition = ((interpolator != null ? interpolator.getInterpolation(f6) : ((double) f6) > 0.5d ? 1.0f : 0.0f) * f5) + fFloor;
        }
        float f7 = adjustedPosition;
        HashMap<String, ViewSpline> map = this.mAttributesMap;
        if (map != null) {
            Iterator<ViewSpline> it = map.values().iterator();
            while (it.hasNext()) {
                it.next().setProperty(child, f7);
            }
        }
        HashMap<String, ViewTimeCycle> map2 = this.mTimeCycleAttributesMap;
        if (map2 != null) {
            pathRotate = null;
            boolean property = false;
            for (ViewTimeCycle viewTimeCycle : map2.values()) {
                if (viewTimeCycle instanceof ViewTimeCycle.PathRotate) {
                    pathRotate = (ViewTimeCycle.PathRotate) viewTimeCycle;
                } else {
                    property |= viewTimeCycle.setProperty(child, f7, time, keyCache);
                }
            }
            z = property;
        } else {
            pathRotate = null;
            z = false;
        }
        CurveFit[] curveFitArr = this.mSpline;
        if (curveFitArr != null) {
            double d2 = f7;
            curveFitArr[0].getPos(d2, this.mInterpolateData);
            this.mSpline[0].getSlope(d2, this.mInterpolateVelocity);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d2, dArr);
                    this.mArcSpline.getSlope(d2, this.mInterpolateVelocity);
                }
            }
            if (this.mNoMovement) {
                f2 = f7;
                d = d2;
                motionController = this;
            } else {
                MotionPaths motionPaths = this.mStartMotionPath;
                int[] iArr = this.mInterpolateVariables;
                double[] dArr2 = this.mInterpolateData;
                double[] dArr3 = this.mInterpolateVelocity;
                boolean z3 = this.mForceMeasure;
                float f8 = motionPaths.x;
                float f9 = motionPaths.y;
                float f10 = motionPaths.width;
                float f11 = motionPaths.height;
                if (iArr.length != 0) {
                    f3 = f9;
                    if (motionPaths.mTempValue.length <= iArr[iArr.length - 1]) {
                        int i2 = iArr[iArr.length - 1] + 1;
                        motionPaths.mTempValue = new double[i2];
                        motionPaths.mTempDelta = new double[i2];
                    }
                } else {
                    f3 = f9;
                }
                float f12 = f10;
                Arrays.fill(motionPaths.mTempValue, Double.NaN);
                for (int i3 = 0; i3 < iArr.length; i3++) {
                    motionPaths.mTempValue[iArr[i3]] = dArr2[i3];
                    motionPaths.mTempDelta[iArr[i3]] = dArr3[i3];
                }
                float f13 = Float.NaN;
                float f14 = 0.0f;
                float f15 = 0.0f;
                float f16 = f8;
                float f17 = 0.0f;
                float f18 = 0.0f;
                int i4 = 0;
                float f19 = f3;
                float f20 = f11;
                float f21 = f19;
                while (true) {
                    double[] dArr4 = motionPaths.mTempValue;
                    f2 = f7;
                    if (i4 >= dArr4.length) {
                        break;
                    }
                    if (Double.isNaN(dArr4[i4])) {
                        f4 = f18;
                    } else {
                        f4 = f18;
                        float f22 = (float) (Double.isNaN(motionPaths.mTempValue[i4]) ? 0.0d : motionPaths.mTempValue[i4] + 0.0d);
                        f18 = (float) motionPaths.mTempDelta[i4];
                        if (i4 == 1) {
                            f14 = f18;
                            f16 = f22;
                        } else if (i4 != 2) {
                            if (i4 == 3) {
                                f12 = f22;
                            } else if (i4 == 4) {
                                f15 = f18;
                                f20 = f22;
                            } else if (i4 == 5) {
                                f13 = f22;
                            }
                            i4++;
                            f7 = f2;
                        } else {
                            f17 = f18;
                            f21 = f22;
                        }
                    }
                    f18 = f4;
                    i4++;
                    f7 = f2;
                }
                float f23 = f18;
                MotionController motionController2 = motionPaths.mRelativeToController;
                if (motionController2 != null) {
                    float[] fArr = new float[2];
                    float[] fArr2 = new float[2];
                    motionController2.getCenter(d2, fArr, fArr2);
                    float f24 = fArr[0];
                    float f25 = fArr[1];
                    float f26 = fArr2[0];
                    float f27 = fArr2[1];
                    d = d2;
                    double d3 = f16;
                    z2 = z3;
                    double d4 = f21;
                    float fSin = (float) (((Math.sin(d4) * d3) + ((double) f24)) - ((double) (f12 / 2.0f)));
                    float fCos = (float) ((((double) f25) - (Math.cos(d4) * d3)) - ((double) (f20 / 2.0f)));
                    double d5 = f14;
                    double d6 = f17;
                    float fCos2 = (float) ((Math.cos(d4) * d3 * d6) + (Math.sin(d4) * d5) + ((double) f26));
                    float fSin2 = (float) ((Math.sin(d4) * d3 * d6) + (((double) f27) - (Math.cos(d4) * d5)));
                    if (dArr3.length >= 2) {
                        dArr3[0] = fCos2;
                        dArr3[1] = fSin2;
                    }
                    if (!Float.isNaN(f13)) {
                        child.setRotation((float) (Math.toDegrees(Math.atan2(fSin2, fCos2)) + ((double) f13)));
                    }
                    f16 = fSin;
                    f21 = fCos;
                } else {
                    z2 = z3;
                    d = d2;
                    if (!Float.isNaN(f13)) {
                        child.setRotation((float) (Math.toDegrees(Math.atan2((f15 / 2.0f) + f17, (f23 / 2.0f) + f14)) + ((double) f13) + ((double) 0.0f)));
                    }
                }
                if (child instanceof FloatLayout) {
                    ((FloatLayout) child).layout(f16, f21, f12 + f16, f20 + f21);
                } else {
                    float f28 = f16 + 0.5f;
                    int i5 = (int) f28;
                    float f29 = f21 + 0.5f;
                    int i6 = (int) f29;
                    int i7 = (int) (f28 + f12);
                    int i8 = (int) (f29 + f20);
                    int i9 = i7 - i5;
                    int i10 = i8 - i6;
                    if (((i9 == child.getMeasuredWidth() && i10 == child.getMeasuredHeight()) ? false : true) || z2) {
                        child.measure(View.MeasureSpec.makeMeasureSpec(i9, 1073741824), View.MeasureSpec.makeMeasureSpec(i10, 1073741824));
                    }
                    child.layout(i5, i6, i7, i8);
                }
                motionController = this;
                motionController.mForceMeasure = false;
            }
            if (motionController.mTransformPivotTarget != Key.UNSET) {
                if (motionController.mTransformPivotView == null) {
                    motionController.mTransformPivotView = ((View) child.getParent()).findViewById(motionController.mTransformPivotTarget);
                }
                View view = motionController.mTransformPivotView;
                if (view != null) {
                    float bottom = (motionController.mTransformPivotView.getBottom() + view.getTop()) / 2.0f;
                    float right = (motionController.mTransformPivotView.getRight() + motionController.mTransformPivotView.getLeft()) / 2.0f;
                    if (child.getRight() - child.getLeft() > 0 && child.getBottom() - child.getTop() > 0) {
                        float left = right - child.getLeft();
                        float top = bottom - child.getTop();
                        child.setPivotX(left);
                        child.setPivotY(top);
                    }
                }
            }
            HashMap<String, ViewSpline> map3 = motionController.mAttributesMap;
            if (map3 != null) {
                for (ViewSpline viewSpline : map3.values()) {
                    if (viewSpline instanceof ViewSpline.PathRotate) {
                        double[] dArr5 = motionController.mInterpolateVelocity;
                        if (dArr5.length > 1) {
                            ((ViewSpline.PathRotate) viewSpline).setPathRotate(child, f2, dArr5[0], dArr5[1]);
                        }
                    }
                }
            }
            if (pathRotate != 0) {
                double[] dArr6 = motionController.mInterpolateVelocity;
                double d7 = dArr6[0];
                double d8 = dArr6[1];
                c = 1;
                pathRotate2 = z | pathRotate.setPathRotate(child, keyCache, f2, time, d7, d8);
            } else {
                c = 1;
                pathRotate2 = z;
            }
            int i11 = 1;
            while (true) {
                CurveFit[] curveFitArr2 = motionController.mSpline;
                if (i11 >= curveFitArr2.length) {
                    break;
                }
                curveFitArr2[i11].getPos(d, motionController.mValuesBuff);
                CustomSupport.setInterpolatedValue(motionController.mStartMotionPath.attributes.get(motionController.mAttributeNames[i11 - 1]), child, motionController.mValuesBuff);
                i11++;
            }
            MotionConstrainedPoint motionConstrainedPoint = motionController.mStartPoint;
            if (motionConstrainedPoint.mVisibilityMode == 0) {
                if (f2 <= 0.0f) {
                    child.setVisibility(motionConstrainedPoint.visibility);
                } else if (f2 >= 1.0f) {
                    child.setVisibility(motionController.mEndPoint.visibility);
                } else if (motionController.mEndPoint.visibility != motionConstrainedPoint.visibility) {
                    child.setVisibility(0);
                }
            }
            if (motionController.mKeyTriggers != null) {
                int i12 = 0;
                while (true) {
                    KeyTrigger[] keyTriggerArr = motionController.mKeyTriggers;
                    if (i12 >= keyTriggerArr.length) {
                        break;
                    }
                    keyTriggerArr[i12].conditionallyFire(f2, child);
                    i12++;
                }
            }
            f = f2;
        } else {
            f = f7;
            boolean z4 = z;
            motionController = this;
            c = 1;
            MotionPaths motionPaths2 = motionController.mStartMotionPath;
            float f30 = motionPaths2.x;
            MotionPaths motionPaths3 = motionController.mEndMotionPath;
            float fM = Insets$$ExternalSyntheticOutline0.m(motionPaths3.x, f30, f, f30);
            float f31 = motionPaths2.y;
            float fM2 = Insets$$ExternalSyntheticOutline0.m(motionPaths3.y, f31, f, f31);
            float f32 = motionPaths2.width;
            float f33 = motionPaths3.width;
            float fM3 = Insets$$ExternalSyntheticOutline0.m(f33, f32, f, f32);
            float f34 = motionPaths2.height;
            float f35 = motionPaths3.height;
            float f36 = fM + 0.5f;
            int i13 = (int) f36;
            float f37 = fM2 + 0.5f;
            int i14 = (int) f37;
            int i15 = (int) (f36 + fM3);
            int iM = (int) (f37 + Insets$$ExternalSyntheticOutline0.m(f35, f34, f, f34));
            int i16 = i15 - i13;
            int i17 = iM - i14;
            if (f33 != f32 || f35 != f34 || motionController.mForceMeasure) {
                child.measure(View.MeasureSpec.makeMeasureSpec(i16, 1073741824), View.MeasureSpec.makeMeasureSpec(i17, 1073741824));
                motionController.mForceMeasure = false;
            }
            child.layout(i13, i14, i15, iM);
            pathRotate2 = z4;
        }
        HashMap<String, ViewOscillator> map4 = motionController.mCycleMap;
        if (map4 != null) {
            for (ViewOscillator viewOscillator : map4.values()) {
                if (viewOscillator instanceof ViewOscillator.PathRotateSet) {
                    double[] dArr7 = motionController.mInterpolateVelocity;
                    ((ViewOscillator.PathRotateSet) viewOscillator).setPathRotate(child, f, dArr7[0], dArr7[c]);
                } else {
                    viewOscillator.setProperty(child, f);
                }
            }
        }
        return pathRotate2;
    }

    public final void positionKeyframe(View view, KeyPositionBase key, float x, float y, String[] attribute, float[] value) {
        RectF rectF = new RectF();
        MotionPaths motionPaths = this.mStartMotionPath;
        float f = motionPaths.x;
        rectF.left = f;
        float f2 = motionPaths.y;
        rectF.top = f2;
        rectF.right = f + motionPaths.width;
        rectF.bottom = f2 + motionPaths.height;
        RectF rectF2 = new RectF();
        MotionPaths motionPaths2 = this.mEndMotionPath;
        float f3 = motionPaths2.x;
        rectF2.left = f3;
        float f4 = motionPaths2.y;
        rectF2.top = f4;
        rectF2.right = f3 + motionPaths2.width;
        rectF2.bottom = f4 + motionPaths2.height;
        key.positionAttributes(view, rectF, rectF2, x, y, attribute, value);
    }

    public void remeasure() {
        this.mForceMeasure = true;
    }

    public final void rotate(Rect rect, Rect out, int rotation, int preHeight, int preWidth) {
        if (rotation == 1) {
            int i = rect.left + rect.right;
            out.left = ((rect.top + rect.bottom) - rect.width()) / 2;
            out.top = preWidth - ((rect.height() + i) / 2);
            out.right = rect.width() + out.left;
            out.bottom = rect.height() + out.top;
            return;
        }
        if (rotation == 2) {
            int i2 = rect.left + rect.right;
            out.left = preHeight - ((rect.width() + (rect.top + rect.bottom)) / 2);
            out.top = (i2 - rect.height()) / 2;
            out.right = rect.width() + out.left;
            out.bottom = rect.height() + out.top;
            return;
        }
        if (rotation == 3) {
            int i3 = rect.left + rect.right;
            out.left = ((rect.height() / 2) + rect.top) - (i3 / 2);
            out.top = preWidth - ((rect.height() + i3) / 2);
            out.right = rect.width() + out.left;
            out.bottom = rect.height() + out.top;
            return;
        }
        if (rotation != 4) {
            return;
        }
        int i4 = rect.left + rect.right;
        out.left = preHeight - ((rect.width() + (rect.bottom + rect.top)) / 2);
        out.top = (i4 - rect.height()) / 2;
        out.right = rect.width() + out.left;
        out.bottom = rect.height() + out.top;
    }

    public final void setBothStates(View v) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        this.mNoMovement = true;
        motionPaths.setBounds(v.getX(), v.getY(), v.getWidth(), v.getHeight());
        this.mEndMotionPath.setBounds(v.getX(), v.getY(), v.getWidth(), v.getHeight());
        this.mStartPoint.setState(v);
        this.mEndPoint.setState(v);
    }

    public void setDrawPath(int debugMode) {
        this.mStartMotionPath.mDrawPath = debugMode;
    }

    public final void setEndState(Rect cw, ConstraintSet constraintSet, int parentWidth, int parentHeight) {
        int i = constraintSet.mRotate;
        if (i != 0) {
            rotate(cw, this.mTempRect, i, parentWidth, parentHeight);
            cw = this.mTempRect;
        }
        MotionPaths motionPaths = this.mEndMotionPath;
        motionPaths.time = 1.0f;
        motionPaths.position = 1.0f;
        readView(motionPaths);
        this.mEndMotionPath.setBounds(cw.left, cw.top, cw.width(), cw.height());
        this.mEndMotionPath.applyParameters(constraintSet.getParameters(this.mId));
        this.mEndPoint.setState(cw, constraintSet, i, this.mId);
    }

    public void setPathMotionArc(int arc) {
        this.mPathMotionArc = arc;
    }

    public final void setStartCurrentState(View v) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        motionPaths.setBounds(v.getX(), v.getY(), v.getWidth(), v.getHeight());
        this.mStartPoint.setState(v);
    }

    public void setStartState(ViewState rect, View v, int rotation, int preWidth, int preHeight) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        Rect rect2 = new Rect();
        if (rotation == 1) {
            int i = rect.left + rect.right;
            rect2.left = ((rect.top + rect.bottom) - rect.width()) / 2;
            rect2.top = preWidth - ((rect.height() + i) / 2);
            rect2.right = rect.width() + rect2.left;
            rect2.bottom = rect.height() + rect2.top;
        } else if (rotation == 2) {
            int i2 = rect.left + rect.right;
            rect2.left = preHeight - ((rect.width() + (rect.top + rect.bottom)) / 2);
            rect2.top = (i2 - rect.height()) / 2;
            rect2.right = rect.width() + rect2.left;
            rect2.bottom = rect.height() + rect2.top;
        }
        this.mStartMotionPath.setBounds(rect2.left, rect2.top, rect2.width(), rect2.height());
        this.mStartPoint.setState(rect2, v, rotation, rect.rotation);
    }

    public void setTransformPivotTarget(int transformPivotTarget) {
        this.mTransformPivotTarget = transformPivotTarget;
        this.mTransformPivotView = null;
    }

    public void setView(View view) {
        this.mView = view;
        this.mId = view.getId();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams) layoutParams).getConstraintTag();
        }
    }

    public void setup(int parentWidth, int parentHeight, float transitionDuration, long currentTime) {
        ArrayList arrayList;
        String[] strArr;
        Class<double> cls;
        int i;
        ConstraintAttribute constraintAttribute;
        ViewTimeCycle viewTimeCycleMakeSpline;
        ConstraintAttribute constraintAttribute2;
        Integer num;
        ViewSpline viewSplineMakeSpline;
        ConstraintAttribute constraintAttribute3;
        Class<double> cls2 = double.class;
        new HashSet();
        HashSet<String> hashSet = new HashSet<>();
        HashSet<String> hashSet2 = new HashSet<>();
        HashSet<String> hashSet3 = new HashSet<>();
        HashMap<String, Integer> map = new HashMap<>();
        int i2 = this.mPathMotionArc;
        if (i2 != Key.UNSET) {
            this.mStartMotionPath.mPathMotionArc = i2;
        }
        this.mStartPoint.different(this.mEndPoint, hashSet2);
        ArrayList<Key> arrayList2 = this.mKeyList;
        if (arrayList2 != null) {
            arrayList = null;
            for (Key key : arrayList2) {
                if (key instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) key;
                    insertKey(new MotionPaths(parentWidth, parentHeight, keyPosition, this.mStartMotionPath, this.mEndMotionPath));
                    int i3 = keyPosition.mCurveFit;
                    if (i3 != Key.UNSET) {
                        this.mCurveFitType = i3;
                    }
                } else if (key instanceof KeyCycle) {
                    key.getAttributeNames(hashSet3);
                } else if (key instanceof KeyTimeCycle) {
                    key.getAttributeNames(hashSet);
                } else if (key instanceof KeyTrigger) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add((KeyTrigger) key);
                } else {
                    key.setInterpolation(map);
                    key.getAttributeNames(hashSet2);
                }
            }
        } else {
            arrayList = null;
        }
        char c = 0;
        if (arrayList != null) {
            this.mKeyTriggers = (KeyTrigger[]) arrayList.toArray(new KeyTrigger[0]);
        }
        char c2 = 1;
        if (!hashSet2.isEmpty()) {
            this.mAttributesMap = new HashMap<>();
            for (String str : hashSet2) {
                if (str.startsWith("CUSTOM,")) {
                    SparseArray sparseArray = new SparseArray();
                    String str2 = str.split(",")[c2];
                    for (Key key2 : this.mKeyList) {
                        HashMap<String, ConstraintAttribute> map2 = key2.mCustomConstraints;
                        if (map2 != null && (constraintAttribute3 = map2.get(str2)) != null) {
                            sparseArray.append(key2.mFramePosition, constraintAttribute3);
                        }
                    }
                    viewSplineMakeSpline = ViewSpline.makeCustomSpline(str, (SparseArray<ConstraintAttribute>) sparseArray);
                } else {
                    viewSplineMakeSpline = ViewSpline.makeSpline(str);
                }
                if (viewSplineMakeSpline != null) {
                    viewSplineMakeSpline.setType(str);
                    this.mAttributesMap.put(str, viewSplineMakeSpline);
                }
                c2 = 1;
            }
            ArrayList<Key> arrayList3 = this.mKeyList;
            if (arrayList3 != null) {
                for (Key key3 : arrayList3) {
                    if (key3 instanceof KeyAttributes) {
                        key3.addValues(this.mAttributesMap);
                    }
                }
            }
            this.mStartPoint.addValues(this.mAttributesMap, 0);
            this.mEndPoint.addValues(this.mAttributesMap, 100);
            for (String str3 : this.mAttributesMap.keySet()) {
                int iIntValue = (!map.containsKey(str3) || (num = map.get(str3)) == null) ? 0 : num.intValue();
                ViewSpline viewSpline = this.mAttributesMap.get(str3);
                if (viewSpline != null) {
                    viewSpline.setup(iIntValue);
                }
            }
        }
        if (!hashSet.isEmpty()) {
            if (this.mTimeCycleAttributesMap == null) {
                this.mTimeCycleAttributesMap = new HashMap<>();
            }
            for (String str4 : hashSet) {
                if (!this.mTimeCycleAttributesMap.containsKey(str4)) {
                    if (str4.startsWith("CUSTOM,")) {
                        SparseArray sparseArray2 = new SparseArray();
                        String str5 = str4.split(",")[1];
                        for (Key key4 : this.mKeyList) {
                            HashMap<String, ConstraintAttribute> map3 = key4.mCustomConstraints;
                            if (map3 != null && (constraintAttribute2 = map3.get(str5)) != null) {
                                sparseArray2.append(key4.mFramePosition, constraintAttribute2);
                            }
                        }
                        viewTimeCycleMakeSpline = ViewTimeCycle.makeCustomSpline(str4, sparseArray2);
                    } else {
                        viewTimeCycleMakeSpline = ViewTimeCycle.makeSpline(str4, currentTime);
                    }
                    if (viewTimeCycleMakeSpline != null) {
                        viewTimeCycleMakeSpline.setType(str4);
                        this.mTimeCycleAttributesMap.put(str4, viewTimeCycleMakeSpline);
                    }
                }
            }
            ArrayList<Key> arrayList4 = this.mKeyList;
            if (arrayList4 != null) {
                for (Key key5 : arrayList4) {
                    if (key5 instanceof KeyTimeCycle) {
                        ((KeyTimeCycle) key5).addTimeValues(this.mTimeCycleAttributesMap);
                    }
                }
            }
            for (String str6 : this.mTimeCycleAttributesMap.keySet()) {
                this.mTimeCycleAttributesMap.get(str6).setup(map.containsKey(str6) ? map.get(str6).intValue() : 0);
            }
        }
        char c3 = 2;
        int size = this.mMotionPaths.size() + 2;
        MotionPaths[] motionPathsArr = new MotionPaths[size];
        motionPathsArr[0] = this.mStartMotionPath;
        motionPathsArr[size - 1] = this.mEndMotionPath;
        if (this.mMotionPaths.size() > 0 && this.mCurveFitType == -1) {
            this.mCurveFitType = 0;
        }
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        int i4 = 1;
        while (it.hasNext()) {
            motionPathsArr[i4] = it.next();
            i4++;
        }
        HashSet hashSet4 = new HashSet();
        for (String str7 : this.mEndMotionPath.attributes.keySet()) {
            if (this.mStartMotionPath.attributes.containsKey(str7)) {
                if (!hashSet2.contains("CUSTOM," + str7)) {
                    hashSet4.add(str7);
                }
            }
        }
        String[] strArr2 = (String[]) hashSet4.toArray(new String[0]);
        this.mAttributeNames = strArr2;
        this.mAttributeInterpolatorCount = new int[strArr2.length];
        int i5 = 0;
        while (true) {
            strArr = this.mAttributeNames;
            if (i5 >= strArr.length) {
                break;
            }
            String str8 = strArr[i5];
            this.mAttributeInterpolatorCount[i5] = 0;
            for (int i6 = 0; i6 < size; i6++) {
                if (motionPathsArr[i6].attributes.containsKey(str8) && (constraintAttribute = motionPathsArr[i6].attributes.get(str8)) != null) {
                    int[] iArr = this.mAttributeInterpolatorCount;
                    iArr[i5] = constraintAttribute.numberOfInterpolatedValues() + iArr[i5];
                    break;
                }
            }
            i5++;
        }
        boolean z = motionPathsArr[0].mPathMotionArc != Key.UNSET;
        int length = 18 + strArr.length;
        boolean[] zArr = new boolean[length];
        for (int i7 = 1; i7 < size; i7++) {
            motionPathsArr[i7].different(motionPathsArr[i7 - 1], zArr, z);
        }
        int i8 = 0;
        for (int i9 = 1; i9 < length; i9++) {
            if (zArr[i9]) {
                i8++;
            }
        }
        this.mInterpolateVariables = new int[i8];
        int iMax = Math.max(2, i8);
        this.mInterpolateData = new double[iMax];
        this.mInterpolateVelocity = new double[iMax];
        int i10 = 0;
        for (int i11 = 1; i11 < length; i11++) {
            if (zArr[i11]) {
                this.mInterpolateVariables[i10] = i11;
                i10++;
            }
        }
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) cls2, size, this.mInterpolateVariables.length);
        double[] dArr2 = new double[size];
        int i12 = 0;
        while (true) {
            int i13 = 6;
            if (i12 >= size) {
                break;
            }
            MotionPaths motionPaths = motionPathsArr[i12];
            double[] dArr3 = dArr[i12];
            int[] iArr2 = this.mInterpolateVariables;
            float[] fArr = new float[6];
            fArr[c] = motionPaths.position;
            fArr[1] = motionPaths.x;
            fArr[c3] = motionPaths.y;
            fArr[3] = motionPaths.width;
            fArr[4] = motionPaths.height;
            fArr[5] = motionPaths.mPathRotate;
            int i14 = 0;
            int i15 = 0;
            while (i14 < iArr2.length) {
                if (iArr2[i14] < i13) {
                    dArr3[i15] = fArr[iArr2[i14]];
                    i15++;
                }
                i14++;
                i13 = 6;
            }
            dArr2[i12] = motionPathsArr[i12].time;
            i12++;
            c3 = 2;
            c = 0;
        }
        int i16 = 0;
        while (true) {
            int[] iArr3 = this.mInterpolateVariables;
            if (i16 >= iArr3.length) {
                break;
            }
            int i17 = iArr3[i16];
            String[] strArr3 = MotionPaths.names;
            if (i17 < 6) {
                String strM = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(), strArr3[this.mInterpolateVariables[i16]], " [");
                for (int i18 = 0; i18 < size; i18++) {
                    StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(strM);
                    sbM.append(dArr[i18][i16]);
                    strM = sbM.toString();
                }
            }
            i16++;
        }
        this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
        int i19 = 0;
        while (true) {
            String[] strArr4 = this.mAttributeNames;
            if (i19 >= strArr4.length) {
                break;
            }
            String str9 = strArr4[i19];
            int i20 = 0;
            int i21 = 0;
            double[] dArr4 = null;
            double[][] dArr5 = null;
            while (i20 < size) {
                if (motionPathsArr[i20].attributes.containsKey(str9)) {
                    if (dArr5 == null) {
                        dArr4 = new double[size];
                        ConstraintAttribute constraintAttribute4 = motionPathsArr[i20].attributes.get(str9);
                        dArr5 = (double[][]) Array.newInstance((Class<?>) cls2, size, constraintAttribute4 == null ? 0 : constraintAttribute4.numberOfInterpolatedValues());
                    }
                    dArr4[i21] = motionPathsArr[i20].time;
                    MotionPaths motionPaths2 = motionPathsArr[i20];
                    double[] dArr6 = dArr5[i21];
                    ConstraintAttribute constraintAttribute5 = motionPaths2.attributes.get(str9);
                    if (constraintAttribute5 == null) {
                        cls = cls2;
                        i = size;
                        dArr4 = dArr4;
                        dArr5 = dArr5;
                    } else {
                        if (constraintAttribute5.numberOfInterpolatedValues() == 1) {
                            dArr6[0] = constraintAttribute5.getValueToInterpolate();
                        } else {
                            int iNumberOfInterpolatedValues = constraintAttribute5.numberOfInterpolatedValues();
                            float[] fArr2 = new float[iNumberOfInterpolatedValues];
                            constraintAttribute5.getValuesToInterpolate(fArr2);
                            int i22 = 0;
                            int i23 = 0;
                            while (i22 < iNumberOfInterpolatedValues) {
                                dArr6[i23] = fArr2[i22];
                                i22++;
                                iNumberOfInterpolatedValues = iNumberOfInterpolatedValues;
                                size = size;
                                i23++;
                                cls2 = cls2;
                            }
                        }
                        cls = cls2;
                        i = size;
                    }
                    i21++;
                    dArr4 = dArr4;
                    dArr5 = dArr5;
                } else {
                    cls = cls2;
                    i = size;
                    str9 = str9;
                }
                i20++;
                str9 = str9;
                size = i;
                cls2 = cls;
            }
            i19++;
            this.mSpline[i19] = CurveFit.get(this.mCurveFitType, Arrays.copyOf(dArr4, i21), (double[][]) Arrays.copyOf(dArr5, i21));
            size = size;
            cls2 = cls2;
        }
        Class<double> cls3 = cls2;
        int i24 = size;
        this.mSpline[0] = CurveFit.get(this.mCurveFitType, dArr2, dArr);
        if (motionPathsArr[0].mPathMotionArc != Key.UNSET) {
            int[] iArr4 = new int[i24];
            double[] dArr7 = new double[i24];
            double[][] dArr8 = (double[][]) Array.newInstance((Class<?>) cls3, i24, 2);
            for (int i25 = 0; i25 < i24; i25++) {
                iArr4[i25] = motionPathsArr[i25].mPathMotionArc;
                dArr7[i25] = motionPathsArr[i25].time;
                dArr8[i25][0] = motionPathsArr[i25].x;
                dArr8[i25][1] = motionPathsArr[i25].y;
            }
            this.mArcSpline = CurveFit.getArc(iArr4, dArr7, dArr8);
        }
        float preCycleDistance = Float.NaN;
        this.mCycleMap = new HashMap<>();
        if (this.mKeyList != null) {
            for (String str10 : hashSet3) {
                ViewOscillator viewOscillatorMakeSpline = ViewOscillator.makeSpline(str10);
                if (viewOscillatorMakeSpline != null) {
                    if (viewOscillatorMakeSpline.variesByPath() && Float.isNaN(preCycleDistance)) {
                        preCycleDistance = getPreCycleDistance();
                    }
                    viewOscillatorMakeSpline.setType(str10);
                    this.mCycleMap.put(str10, viewOscillatorMakeSpline);
                }
            }
            for (Key key6 : this.mKeyList) {
                if (key6 instanceof KeyCycle) {
                    ((KeyCycle) key6).addCycleValues(this.mCycleMap);
                }
            }
            Iterator<ViewOscillator> it2 = this.mCycleMap.values().iterator();
            while (it2.hasNext()) {
                it2.next().setup(preCycleDistance);
            }
        }
    }

    public void setupRelative(MotionController motionController) {
        this.mStartMotionPath.setupRelative(motionController, motionController.mStartMotionPath);
        this.mEndMotionPath.setupRelative(motionController, motionController.mEndMotionPath);
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

    public final void setStartState(Rect cw, ConstraintSet constraintSet, int parentWidth, int parentHeight) {
        int i = constraintSet.mRotate;
        if (i != 0) {
            rotate(cw, this.mTempRect, i, parentWidth, parentHeight);
        }
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        readView(motionPaths);
        this.mStartMotionPath.setBounds(cw.left, cw.top, cw.width(), cw.height());
        ConstraintSet.Constraint parameters = constraintSet.getParameters(this.mId);
        this.mStartMotionPath.applyParameters(parameters);
        this.mMotionStagger = parameters.motion.mMotionStagger;
        this.mStartPoint.setState(cw, constraintSet, i, this.mId);
        this.mTransformPivotTarget = parameters.transform.transformPivotTarget;
        ConstraintSet.Motion motion = parameters.motion;
        this.mQuantizeMotionSteps = motion.mQuantizeMotionSteps;
        this.mQuantizeMotionPhase = motion.mQuantizeMotionPhase;
        Context context = this.mView.getContext();
        ConstraintSet.Motion motion2 = parameters.motion;
        this.mQuantizeMotionInterpolator = getInterpolator(context, motion2.mQuantizeInterpolatorType, motion2.mQuantizeInterpolatorString, motion2.mQuantizeInterpolatorID);
    }
}
