package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.R;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.HashMap;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class KeyPosition extends KeyPositionBase {
    public static final String DRAWPATH = "drawPath";
    public static final String PERCENT_HEIGHT = "percentHeight";
    public static final String PERCENT_WIDTH = "percentWidth";
    public static final String PERCENT_X = "percentX";
    public static final String PERCENT_Y = "percentY";
    public static final String SIZE_PERCENT = "sizePercent";
    private static final String TAG = "KeyPosition";
    public static final String TRANSITION_EASING = "transitionEasing";
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;
    public String mTransitionEasing = null;
    public int mPathMotionArc = Key.UNSET;
    public int mDrawPath = 0;
    public float mPercentWidth = Float.NaN;
    public float mPercentHeight = Float.NaN;
    public float mPercentX = Float.NaN;
    public float mPercentY = Float.NaN;
    public float mAltPercentX = Float.NaN;
    public float mAltPercentY = Float.NaN;
    public int mPositionType = 0;
    private float mCalculatedPositionX = Float.NaN;
    private float mCalculatedPositionY = Float.NaN;

    public static class Loader {
        private static final int CURVE_FIT = 4;
        private static final int DRAW_PATH = 5;
        private static final int FRAME_POSITION = 2;
        private static final int PATH_MOTION_ARC = 10;
        private static final int PERCENT_HEIGHT = 12;
        private static final int PERCENT_WIDTH = 11;
        private static final int PERCENT_X = 6;
        private static final int PERCENT_Y = 7;
        private static final int SIZE_PERCENT = 8;
        private static final int TARGET_ID = 1;
        private static final int TRANSITION_EASING = 3;
        private static final int TYPE = 9;
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyPosition_motionTarget, 1);
            mAttrMap.append(R.styleable.KeyPosition_framePosition, 2);
            mAttrMap.append(R.styleable.KeyPosition_transitionEasing, 3);
            mAttrMap.append(R.styleable.KeyPosition_curveFit, 4);
            mAttrMap.append(R.styleable.KeyPosition_drawPath, 5);
            mAttrMap.append(R.styleable.KeyPosition_percentX, 6);
            mAttrMap.append(R.styleable.KeyPosition_percentY, 7);
            mAttrMap.append(R.styleable.KeyPosition_keyPositionType, 9);
            mAttrMap.append(R.styleable.KeyPosition_sizePercent, 8);
            mAttrMap.append(R.styleable.KeyPosition_percentWidth, 11);
            mAttrMap.append(R.styleable.KeyPosition_percentHeight, 12);
            mAttrMap.append(R.styleable.KeyPosition_pathMotionArc, 10);
        }

        private Loader() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void read(KeyPosition c, TypedArray a) {
            int indexCount = a.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = a.getIndex(i);
                switch (mAttrMap.get(index)) {
                    case 1:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            int resourceId = a.getResourceId(index, c.mTargetId);
                            c.mTargetId = resourceId;
                            if (resourceId == -1) {
                                c.mTargetString = a.getString(index);
                            }
                        } else if (a.peekValue(index).type == 3) {
                            c.mTargetString = a.getString(index);
                        } else {
                            c.mTargetId = a.getResourceId(index, c.mTargetId);
                        }
                        break;
                    case 2:
                        c.mFramePosition = a.getInt(index, c.mFramePosition);
                        break;
                    case 3:
                        if (a.peekValue(index).type == 3) {
                            c.mTransitionEasing = a.getString(index);
                        } else {
                            c.mTransitionEasing = Easing.NAMED_EASING[a.getInteger(index, 0)];
                        }
                        break;
                    case 4:
                        c.mCurveFit = a.getInteger(index, c.mCurveFit);
                        break;
                    case 5:
                        c.mDrawPath = a.getInt(index, c.mDrawPath);
                        break;
                    case 6:
                        c.mPercentX = a.getFloat(index, c.mPercentX);
                        break;
                    case 7:
                        c.mPercentY = a.getFloat(index, c.mPercentY);
                        break;
                    case 8:
                        float f = a.getFloat(index, c.mPercentHeight);
                        c.mPercentWidth = f;
                        c.mPercentHeight = f;
                        break;
                    case 9:
                        c.mPositionType = a.getInt(index, c.mPositionType);
                        break;
                    case 10:
                        c.mPathMotionArc = a.getInt(index, c.mPathMotionArc);
                        break;
                    case 11:
                        c.mPercentWidth = a.getFloat(index, c.mPercentWidth);
                        break;
                    case 12:
                        c.mPercentHeight = a.getFloat(index, c.mPercentHeight);
                        break;
                    default:
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("unused attribute 0x");
                        sbM.append(Integer.toHexString(index));
                        sbM.append("   ");
                        sbM.append(mAttrMap.get(index));
                        Log.e("KeyPosition", sbM.toString());
                        break;
                }
            }
            if (c.mFramePosition == -1) {
                Log.e("KeyPosition", "no frame position");
            }
        }
    }

    public KeyPosition() {
        this.mType = 2;
    }

    private void calcCartesianPosition(float start_x, float start_y, float end_x, float end_y) {
        float f = end_x - start_x;
        float f2 = end_y - start_y;
        float f3 = Float.isNaN(this.mPercentX) ? 0.0f : this.mPercentX;
        float f4 = Float.isNaN(this.mAltPercentY) ? 0.0f : this.mAltPercentY;
        float f5 = Float.isNaN(this.mPercentY) ? 0.0f : this.mPercentY;
        this.mCalculatedPositionX = (int) (((Float.isNaN(this.mAltPercentX) ? 0.0f : this.mAltPercentX) * f2) + (f3 * f) + start_x);
        this.mCalculatedPositionY = (int) ((f2 * f5) + (f * f4) + start_y);
    }

    private void calcPathPosition(float start_x, float start_y, float end_x, float end_y) {
        float f = end_x - start_x;
        float f2 = end_y - start_y;
        float f3 = this.mPercentX;
        float f4 = (f * f3) + start_x;
        float f5 = this.mPercentY;
        this.mCalculatedPositionX = ((-f2) * f5) + f4;
        this.mCalculatedPositionY = (f * f5) + (f2 * f3) + start_y;
    }

    private void calcScreenPosition(int layoutWidth, int layoutHeight) {
        float f = this.mPercentX;
        float f2 = 0;
        this.mCalculatedPositionX = ((layoutWidth - 0) * f) + f2;
        this.mCalculatedPositionY = ((layoutHeight - 0) * f) + f2;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, ViewSpline> splines) {
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public Key copy(Key src) {
        super.copy(src);
        KeyPosition keyPosition = (KeyPosition) src;
        this.mTransitionEasing = keyPosition.mTransitionEasing;
        this.mPathMotionArc = keyPosition.mPathMotionArc;
        this.mDrawPath = keyPosition.mDrawPath;
        this.mPercentWidth = keyPosition.mPercentWidth;
        this.mPercentHeight = Float.NaN;
        this.mPercentX = keyPosition.mPercentX;
        this.mPercentY = keyPosition.mPercentY;
        this.mAltPercentX = keyPosition.mAltPercentX;
        this.mAltPercentY = keyPosition.mAltPercentY;
        this.mCalculatedPositionX = keyPosition.mCalculatedPositionX;
        this.mCalculatedPositionY = keyPosition.mCalculatedPositionY;
        return this;
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public boolean intersects(int layoutWidth, int layoutHeight, RectF start, RectF end, float x, float y) {
        float fCenterX = start.centerX();
        float fCenterY = start.centerY();
        float fCenterX2 = end.centerX();
        float fCenterY2 = end.centerY();
        int i = this.mPositionType;
        if (i == 1) {
            calcPathPosition(fCenterX, fCenterY, fCenterX2, fCenterY2);
        } else if (i != 2) {
            calcCartesianPosition(fCenterX, fCenterY, fCenterX2, fCenterY2);
        } else {
            calcScreenPosition(layoutWidth, layoutHeight);
        }
        return Math.abs(x - this.mCalculatedPositionX) < 20.0f && Math.abs(y - this.mCalculatedPositionY) < 20.0f;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attrs) {
        Loader.read(this, context.obtainStyledAttributes(attrs, R.styleable.KeyPosition));
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public void positionAttributes(View view, RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        int i = this.mPositionType;
        if (i == 1) {
            float fCenterX = start.centerX();
            float fCenterY = start.centerY();
            float fCenterX2 = end.centerX() - fCenterX;
            float fCenterY2 = end.centerY() - fCenterY;
            float fHypot = (float) Math.hypot(fCenterX2, fCenterY2);
            if (fHypot < 1.0E-4d) {
                System.out.println("distance ~ 0");
                value[0] = 0.0f;
                value[1] = 0.0f;
                return;
            }
            float f = fCenterX2 / fHypot;
            float f2 = fCenterY2 / fHypot;
            float f3 = y - fCenterY;
            float f4 = x - fCenterX;
            float f5 = ((f * f3) - (f4 * f2)) / fHypot;
            float f6 = ((f2 * f3) + (f * f4)) / fHypot;
            if (attribute[0] != null) {
                if ("percentX".equals(attribute[0])) {
                    value[0] = f6;
                    value[1] = f5;
                    return;
                }
                return;
            }
            attribute[0] = "percentX";
            attribute[1] = "percentY";
            value[0] = f6;
            value[1] = f5;
            return;
        }
        if (i != 2) {
            float fCenterX3 = start.centerX();
            float fCenterY3 = start.centerY();
            float fCenterX4 = end.centerX() - fCenterX3;
            float fCenterY4 = end.centerY() - fCenterY3;
            if (attribute[0] == null) {
                attribute[0] = "percentX";
                value[0] = (x - fCenterX3) / fCenterX4;
                attribute[1] = "percentY";
                value[1] = (y - fCenterY3) / fCenterY4;
                return;
            }
            if ("percentX".equals(attribute[0])) {
                value[0] = (x - fCenterX3) / fCenterX4;
                value[1] = (y - fCenterY3) / fCenterY4;
                return;
            } else {
                value[1] = (x - fCenterX3) / fCenterX4;
                value[0] = (y - fCenterY3) / fCenterY4;
                return;
            }
        }
        start.centerX();
        start.centerY();
        end.centerX();
        end.centerY();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
        if (attribute[0] == null) {
            attribute[0] = "percentX";
            value[0] = x / width;
            attribute[1] = "percentY";
            value[1] = y / height;
            return;
        }
        if ("percentX".equals(attribute[0])) {
            value[0] = x / width;
            value[1] = y / height;
        } else {
            value[1] = x / width;
            value[0] = y / height;
        }
    }

    public void setType(int type) {
        this.mPositionType = type;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String tag, Object value) {
        Objects.requireNonNull(tag);
        switch (tag) {
            case "transitionEasing":
                this.mTransitionEasing = value.toString();
                break;
            case "percentWidth":
                this.mPercentWidth = toFloat(value);
                break;
            case "percentHeight":
                this.mPercentHeight = toFloat(value);
                break;
            case "drawPath":
                this.mDrawPath = toInt(value);
                break;
            case "sizePercent":
                float f = toFloat(value);
                this.mPercentWidth = f;
                this.mPercentHeight = f;
                break;
            case "percentX":
                this.mPercentX = toFloat(value);
                break;
            case "percentY":
                this.mPercentY = toFloat(value);
                break;
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* JADX INFO: renamed from: clone */
    public Key mo7clone() {
        return new KeyPosition().copy(this);
    }
}
