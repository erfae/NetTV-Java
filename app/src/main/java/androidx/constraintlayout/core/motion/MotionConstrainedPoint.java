package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.Rect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
class MotionConstrainedPoint implements Comparable<MotionConstrainedPoint> {
    public static final boolean DEBUG = false;
    public static final String TAG = "MotionPaths";
    private float height;
    private Easing mKeyFrameEasing;
    private float position;
    public int visibility;
    private float width;
    private float x;
    private float y;
    private float alpha = 1.0f;
    private boolean applyElevation = false;
    private float elevation = 0.0f;
    private float rotation = 0.0f;
    private float rotationX = 0.0f;
    public float rotationY = 0.0f;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
    private float translationX = 0.0f;
    private float translationY = 0.0f;
    private float translationZ = 0.0f;
    private int mDrawPath = 0;
    private float mPathRotate = Float.NaN;
    private float mProgress = Float.NaN;
    private int mAnimateRelativeTo = -1;
    public LinkedHashMap<String, CustomVariable> mCustomVariable = new LinkedHashMap<>();

    private boolean diff(float f, float f2) {
        if (Float.isNaN(f) || Float.isNaN(f2)) {
            return Float.isNaN(f) != Float.isNaN(f2);
        }
        return Math.abs(f - f2) > 1.0E-6f;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public void addValues(HashMap<String, SplineSet> map, int i) {
        for (String str : map.keySet()) {
            SplineSet splineSet = map.get(str);
            Objects.requireNonNull(str);
            byte b = -1;
            switch (str.hashCode()) {
                case -1249320806:
                    if (str.equals("rotationX")) {
                        b = 0;
                    }
                    break;
                case -1249320805:
                    if (str.equals("rotationY")) {
                        b = 1;
                    }
                    break;
                case -1249320804:
                    if (str.equals("rotationZ")) {
                        b = 2;
                    }
                    break;
                case -1225497657:
                    if (str.equals("translationX")) {
                        b = 3;
                    }
                    break;
                case -1225497656:
                    if (str.equals("translationY")) {
                        b = 4;
                    }
                    break;
                case -1225497655:
                    if (str.equals("translationZ")) {
                        b = 5;
                    }
                    break;
                case -1001078227:
                    if (str.equals("progress")) {
                        b = 6;
                    }
                    break;
                case -987906986:
                    if (str.equals("pivotX")) {
                        b = 7;
                    }
                    break;
                case -987906985:
                    if (str.equals("pivotY")) {
                        b = 8;
                    }
                    break;
                case -908189618:
                    if (str.equals("scaleX")) {
                        b = 9;
                    }
                    break;
                case -908189617:
                    if (str.equals("scaleY")) {
                        b = 10;
                    }
                    break;
                case 92909918:
                    if (str.equals("alpha")) {
                        b = 11;
                    }
                    break;
                case 803192288:
                    if (str.equals("pathRotate")) {
                        b = 12;
                    }
                    break;
            }
            switch (b) {
                case 0:
                    splineSet.setPoint(i, Float.isNaN(this.rotationX) ? 0.0f : this.rotationX);
                    break;
                case 1:
                    splineSet.setPoint(i, Float.isNaN(this.rotationY) ? 0.0f : this.rotationY);
                    break;
                case 2:
                    splineSet.setPoint(i, Float.isNaN(this.rotation) ? 0.0f : this.rotation);
                    break;
                case 3:
                    splineSet.setPoint(i, Float.isNaN(this.translationX) ? 0.0f : this.translationX);
                    break;
                case 4:
                    splineSet.setPoint(i, Float.isNaN(this.translationY) ? 0.0f : this.translationY);
                    break;
                case 5:
                    splineSet.setPoint(i, Float.isNaN(this.translationZ) ? 0.0f : this.translationZ);
                    break;
                case 6:
                    splineSet.setPoint(i, Float.isNaN(this.mProgress) ? 0.0f : this.mProgress);
                    break;
                case 7:
                    splineSet.setPoint(i, Float.isNaN(this.mPivotX) ? 0.0f : this.mPivotX);
                    break;
                case 8:
                    splineSet.setPoint(i, Float.isNaN(this.mPivotY) ? 0.0f : this.mPivotY);
                    break;
                case 9:
                    splineSet.setPoint(i, Float.isNaN(this.scaleX) ? 1.0f : this.scaleX);
                    break;
                case 10:
                    splineSet.setPoint(i, Float.isNaN(this.scaleY) ? 1.0f : this.scaleY);
                    break;
                case 11:
                    splineSet.setPoint(i, Float.isNaN(this.alpha) ? 1.0f : this.alpha);
                    break;
                case 12:
                    splineSet.setPoint(i, Float.isNaN(this.mPathRotate) ? 0.0f : this.mPathRotate);
                    break;
                default:
                    if (str.startsWith("CUSTOM")) {
                        String str2 = str.split(",")[1];
                        if (this.mCustomVariable.containsKey(str2)) {
                            CustomVariable customVariable = this.mCustomVariable.get(str2);
                            if (splineSet instanceof SplineSet.CustomSpline) {
                                ((SplineSet.CustomSpline) splineSet).setPoint(i, customVariable);
                            } else {
                                Utils.loge("MotionPaths", str + " ViewSpline not a CustomSet frame = " + i + ", value" + customVariable.getValueToInterpolate() + splineSet);
                            }
                        }
                    } else {
                        Utils.loge("MotionPaths", "UNKNOWN spline " + str);
                    }
                    break;
            }
        }
    }

    public void applyParameters(MotionWidget motionWidget) {
        this.visibility = motionWidget.getVisibility();
        this.alpha = motionWidget.getVisibility() != 4 ? 0.0f : motionWidget.getAlpha();
        this.applyElevation = false;
        this.rotation = motionWidget.getRotationZ();
        this.rotationX = motionWidget.getRotationX();
        this.rotationY = motionWidget.getRotationY();
        this.scaleX = motionWidget.getScaleX();
        this.scaleY = motionWidget.getScaleY();
        this.mPivotX = motionWidget.getPivotX();
        this.mPivotY = motionWidget.getPivotY();
        this.translationX = motionWidget.getTranslationX();
        this.translationY = motionWidget.getTranslationY();
        this.translationZ = motionWidget.getTranslationZ();
        for (String str : motionWidget.getCustomAttributeNames()) {
            CustomVariable customAttribute = motionWidget.getCustomAttribute(str);
            if (customAttribute != null && customAttribute.isContinuous()) {
                this.mCustomVariable.put(str, customAttribute);
            }
        }
    }

    public final void different(MotionConstrainedPoint motionConstrainedPoint, HashSet<String> hashSet) {
        if (diff(this.alpha, motionConstrainedPoint.alpha)) {
            hashSet.add("alpha");
        }
        if (diff(this.elevation, motionConstrainedPoint.elevation)) {
            hashSet.add("translationZ");
        }
        int i = this.visibility;
        int i2 = motionConstrainedPoint.visibility;
        if (i != i2 && (i == 4 || i2 == 4)) {
            hashSet.add("alpha");
        }
        if (diff(this.rotation, motionConstrainedPoint.rotation)) {
            hashSet.add("rotationZ");
        }
        if (!Float.isNaN(this.mPathRotate) || !Float.isNaN(motionConstrainedPoint.mPathRotate)) {
            hashSet.add("pathRotate");
        }
        if (!Float.isNaN(this.mProgress) || !Float.isNaN(motionConstrainedPoint.mProgress)) {
            hashSet.add("progress");
        }
        if (diff(this.rotationX, motionConstrainedPoint.rotationX)) {
            hashSet.add("rotationX");
        }
        if (diff(this.rotationY, motionConstrainedPoint.rotationY)) {
            hashSet.add("rotationY");
        }
        if (diff(this.mPivotX, motionConstrainedPoint.mPivotX)) {
            hashSet.add("pivotX");
        }
        if (diff(this.mPivotY, motionConstrainedPoint.mPivotY)) {
            hashSet.add("pivotY");
        }
        if (diff(this.scaleX, motionConstrainedPoint.scaleX)) {
            hashSet.add("scaleX");
        }
        if (diff(this.scaleY, motionConstrainedPoint.scaleY)) {
            hashSet.add("scaleY");
        }
        if (diff(this.translationX, motionConstrainedPoint.translationX)) {
            hashSet.add("translationX");
        }
        if (diff(this.translationY, motionConstrainedPoint.translationY)) {
            hashSet.add("translationY");
        }
        if (diff(this.translationZ, motionConstrainedPoint.translationZ)) {
            hashSet.add("translationZ");
        }
        if (diff(this.elevation, motionConstrainedPoint.elevation)) {
            hashSet.add("elevation");
        }
    }

    public void setState(MotionWidget motionWidget) {
        float x = motionWidget.getX();
        float y = motionWidget.getY();
        float width = motionWidget.getWidth();
        float height = motionWidget.getHeight();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        applyParameters(motionWidget);
    }

    @Override // java.lang.Comparable
    public int compareTo(MotionConstrainedPoint motionConstrainedPoint) {
        return Float.compare(this.position, motionConstrainedPoint.position);
    }

    public void setState(Rect rect, MotionWidget motionWidget, int i, float f) {
        float f2 = rect.left;
        float f3 = rect.top;
        float fWidth = rect.width();
        float fHeight = rect.height();
        this.x = f2;
        this.y = f3;
        this.width = fWidth;
        this.height = fHeight;
        applyParameters(motionWidget);
        this.mPivotX = Float.NaN;
        this.mPivotY = Float.NaN;
        if (i == 1) {
            this.rotation = f - 90.0f;
        } else {
            if (i != 2) {
                return;
            }
            this.rotation = f + 90.0f;
        }
    }
}
