package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.R;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: classes.dex */
class TouchResponse {
    public static final int COMPLETE_MODE_CONTINUOUS_VELOCITY = 0;
    public static final int COMPLETE_MODE_SPRING = 1;
    private static final boolean DEBUG = false;
    private static final float EPSILON = 1.0E-7f;
    private static final int SEC_TO_MILLISECONDS = 1000;
    private static final int SIDE_BOTTOM = 3;
    private static final int SIDE_END = 6;
    private static final int SIDE_LEFT = 1;
    private static final int SIDE_MIDDLE = 4;
    private static final int SIDE_RIGHT = 2;
    private static final int SIDE_START = 5;
    private static final int SIDE_TOP = 0;
    private static final String TAG = "TouchResponse";
    private static final int TOUCH_DOWN = 1;
    private static final int TOUCH_END = 5;
    private static final int TOUCH_LEFT = 2;
    private static final int TOUCH_RIGHT = 3;
    private static final int TOUCH_START = 4;
    private static final int TOUCH_UP = 0;
    private float[] mAnchorDpDt;
    private int mAutoCompleteMode;
    private float mDragScale;
    private boolean mDragStarted;
    private float mDragThreshold;
    private int mFlags;
    public boolean mIsRotateMode;
    private float mLastTouchX;
    private float mLastTouchY;
    private int mLimitBoundsTo;
    private float mMaxAcceleration;
    private float mMaxVelocity;
    private final MotionLayout mMotionLayout;
    private boolean mMoveWhenScrollAtTop;
    private int mOnTouchUp;
    public float mRotateCenterX;
    public float mRotateCenterY;
    private int mRotationCenterId;
    private int mSpringBoundary;
    private float mSpringDamping;
    private float mSpringMass;
    private float mSpringStiffness;
    private float mSpringStopThreshold;
    private int[] mTempLoc;
    private int mTouchAnchorId;
    private int mTouchAnchorSide;
    private float mTouchAnchorX;
    private float mTouchAnchorY;
    private float mTouchDirectionX;
    private float mTouchDirectionY;
    private int mTouchRegionId;
    private int mTouchSide;
    private static final float[][] TOUCH_SIDES = {new float[]{0.5f, 0.0f}, new float[]{0.0f, 0.5f}, new float[]{1.0f, 0.5f}, new float[]{0.5f, 1.0f}, new float[]{0.5f, 0.5f}, new float[]{0.0f, 0.5f}, new float[]{1.0f, 0.5f}};
    private static final float[][] TOUCH_DIRECTION = {new float[]{0.0f, -1.0f}, new float[]{0.0f, 1.0f}, new float[]{-1.0f, 0.0f}, new float[]{1.0f, 0.0f}, new float[]{-1.0f, 0.0f}, new float[]{1.0f, 0.0f}};

    public TouchResponse(Context context, MotionLayout layout, XmlPullParser parser) {
        this.mTouchAnchorSide = 0;
        this.mTouchSide = 0;
        this.mOnTouchUp = 0;
        this.mTouchAnchorId = -1;
        this.mTouchRegionId = -1;
        this.mLimitBoundsTo = -1;
        this.mTouchAnchorY = 0.5f;
        this.mTouchAnchorX = 0.5f;
        this.mRotateCenterX = 0.5f;
        this.mRotateCenterY = 0.5f;
        this.mRotationCenterId = -1;
        this.mIsRotateMode = false;
        this.mTouchDirectionX = 0.0f;
        this.mTouchDirectionY = 1.0f;
        this.mDragStarted = false;
        this.mAnchorDpDt = new float[2];
        this.mTempLoc = new int[2];
        this.mMaxVelocity = 4.0f;
        this.mMaxAcceleration = 1.2f;
        this.mMoveWhenScrollAtTop = true;
        this.mDragScale = 1.0f;
        this.mFlags = 0;
        this.mDragThreshold = 10.0f;
        this.mSpringDamping = 10.0f;
        this.mSpringMass = 1.0f;
        this.mSpringStiffness = Float.NaN;
        this.mSpringStopThreshold = Float.NaN;
        this.mSpringBoundary = 0;
        this.mAutoCompleteMode = 0;
        this.mMotionLayout = layout;
        fillFromAttributeList(context, Xml.asAttributeSet(parser));
    }

    private void fill(TypedArray a) {
        int indexCount = a.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = a.getIndex(i);
            if (index == R.styleable.OnSwipe_touchAnchorId) {
                this.mTouchAnchorId = a.getResourceId(index, this.mTouchAnchorId);
            } else if (index == R.styleable.OnSwipe_touchAnchorSide) {
                int i2 = a.getInt(index, this.mTouchAnchorSide);
                this.mTouchAnchorSide = i2;
                float[][] fArr = TOUCH_SIDES;
                this.mTouchAnchorX = fArr[i2][0];
                this.mTouchAnchorY = fArr[i2][1];
            } else if (index == R.styleable.OnSwipe_dragDirection) {
                int i3 = a.getInt(index, this.mTouchSide);
                this.mTouchSide = i3;
                float[][] fArr2 = TOUCH_DIRECTION;
                if (i3 < fArr2.length) {
                    this.mTouchDirectionX = fArr2[i3][0];
                    this.mTouchDirectionY = fArr2[i3][1];
                } else {
                    this.mTouchDirectionY = Float.NaN;
                    this.mTouchDirectionX = Float.NaN;
                    this.mIsRotateMode = true;
                }
            } else if (index == R.styleable.OnSwipe_maxVelocity) {
                this.mMaxVelocity = a.getFloat(index, this.mMaxVelocity);
            } else if (index == R.styleable.OnSwipe_maxAcceleration) {
                this.mMaxAcceleration = a.getFloat(index, this.mMaxAcceleration);
            } else if (index == R.styleable.OnSwipe_moveWhenScrollAtTop) {
                this.mMoveWhenScrollAtTop = a.getBoolean(index, this.mMoveWhenScrollAtTop);
            } else if (index == R.styleable.OnSwipe_dragScale) {
                this.mDragScale = a.getFloat(index, this.mDragScale);
            } else if (index == R.styleable.OnSwipe_dragThreshold) {
                this.mDragThreshold = a.getFloat(index, this.mDragThreshold);
            } else if (index == R.styleable.OnSwipe_touchRegionId) {
                this.mTouchRegionId = a.getResourceId(index, this.mTouchRegionId);
            } else if (index == R.styleable.OnSwipe_onTouchUp) {
                this.mOnTouchUp = a.getInt(index, this.mOnTouchUp);
            } else if (index == R.styleable.OnSwipe_nestedScrollFlags) {
                this.mFlags = a.getInteger(index, 0);
            } else if (index == R.styleable.OnSwipe_limitBoundsTo) {
                this.mLimitBoundsTo = a.getResourceId(index, 0);
            } else if (index == R.styleable.OnSwipe_rotationCenterId) {
                this.mRotationCenterId = a.getResourceId(index, this.mRotationCenterId);
            } else if (index == R.styleable.OnSwipe_springDamping) {
                this.mSpringDamping = a.getFloat(index, this.mSpringDamping);
            } else if (index == R.styleable.OnSwipe_springMass) {
                this.mSpringMass = a.getFloat(index, this.mSpringMass);
            } else if (index == R.styleable.OnSwipe_springStiffness) {
                this.mSpringStiffness = a.getFloat(index, this.mSpringStiffness);
            } else if (index == R.styleable.OnSwipe_springStopThreshold) {
                this.mSpringStopThreshold = a.getFloat(index, this.mSpringStopThreshold);
            } else if (index == R.styleable.OnSwipe_springBoundary) {
                this.mSpringBoundary = a.getInt(index, this.mSpringBoundary);
            } else if (index == R.styleable.OnSwipe_autoCompleteMode) {
                this.mAutoCompleteMode = a.getInt(index, this.mAutoCompleteMode);
            }
        }
    }

    private void fillFromAttributeList(Context context, AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.OnSwipe);
        fill(typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
    }

    public final float dot(float dx, float dy) {
        return (dy * this.mTouchDirectionY) + (dx * this.mTouchDirectionX);
    }

    public int getAnchorId() {
        return this.mTouchAnchorId;
    }

    public int getAutoCompleteMode() {
        return this.mAutoCompleteMode;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public final RectF getLimitBoundsTo(ViewGroup layout, RectF rect) {
        View viewFindViewById;
        int i = this.mLimitBoundsTo;
        if (i == -1 || (viewFindViewById = layout.findViewById(i)) == null) {
            return null;
        }
        rect.set(viewFindViewById.getLeft(), viewFindViewById.getTop(), viewFindViewById.getRight(), viewFindViewById.getBottom());
        return rect;
    }

    public final float getMaxAcceleration() {
        return this.mMaxAcceleration;
    }

    public float getMaxVelocity() {
        return this.mMaxVelocity;
    }

    public final boolean getMoveWhenScrollAtTop() {
        return this.mMoveWhenScrollAtTop;
    }

    public final float getProgressDirection(float dx, float dy) {
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, this.mMotionLayout.getProgress(), this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        float f = this.mTouchDirectionX;
        if (f != 0.0f) {
            float[] fArr = this.mAnchorDpDt;
            if (fArr[0] == 0.0f) {
                fArr[0] = 1.0E-7f;
            }
            return (dx * f) / fArr[0];
        }
        float[] fArr2 = this.mAnchorDpDt;
        if (fArr2[1] == 0.0f) {
            fArr2[1] = 1.0E-7f;
        }
        return (dy * this.mTouchDirectionY) / fArr2[1];
    }

    public int getSpringBoundary() {
        return this.mSpringBoundary;
    }

    public float getSpringDamping() {
        return this.mSpringDamping;
    }

    public float getSpringMass() {
        return this.mSpringMass;
    }

    public float getSpringStiffness() {
        return this.mSpringStiffness;
    }

    public float getSpringStopThreshold() {
        return this.mSpringStopThreshold;
    }

    public final RectF getTouchRegion(ViewGroup layout, RectF rect) {
        View viewFindViewById;
        int i = this.mTouchRegionId;
        if (i == -1 || (viewFindViewById = layout.findViewById(i)) == null) {
            return null;
        }
        rect.set(viewFindViewById.getLeft(), viewFindViewById.getTop(), viewFindViewById.getRight(), viewFindViewById.getBottom());
        return rect;
    }

    public final int getTouchRegionId() {
        return this.mTouchRegionId;
    }

    public final boolean isDragStarted() {
        return this.mDragStarted;
    }

    /* JADX WARN: Code duplicated, block: B:59:0x0296  */
    /* JADX WARN: Code duplicated, block: B:60:0x02ba  */
    /* JADX WARN: Code duplicated, block: B:63:0x02d6  */
    /* JADX WARN: Code duplicated, block: B:64:0x02e3  */
    public final void processTouchEvent(MotionEvent motionEvent, MotionLayout.MotionTracker motionTracker) {
        char c;
        int i;
        char c2;
        char c3;
        float right;
        float f;
        int top;
        int bottom;
        int i2;
        float degrees;
        float f2;
        int i3;
        if (!this.mIsRotateMode) {
            motionTracker.addMovement(motionEvent);
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mLastTouchX = motionEvent.getRawX();
                this.mLastTouchY = motionEvent.getRawY();
                this.mDragStarted = false;
                return;
            }
            if (action == 1) {
                this.mDragStarted = false;
                motionTracker.computeCurrentVelocity(1000);
                float xVelocity = motionTracker.getXVelocity();
                float yVelocity = motionTracker.getYVelocity();
                float progress = this.mMotionLayout.getProgress();
                int i4 = this.mTouchAnchorId;
                if (i4 != -1) {
                    this.mMotionLayout.getAnchorDpDt(i4, progress, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
                    c = 0;
                } else {
                    float fMin = Math.min(this.mMotionLayout.getWidth(), this.mMotionLayout.getHeight());
                    float[] fArr = this.mAnchorDpDt;
                    fArr[1] = this.mTouchDirectionY * fMin;
                    c = 0;
                    fArr[0] = fMin * this.mTouchDirectionX;
                }
                float f3 = this.mTouchDirectionX;
                float[] fArr2 = this.mAnchorDpDt;
                float f4 = fArr2[c];
                float f5 = fArr2[1];
                float fAbs = f3 != 0.0f ? xVelocity / fArr2[c] : yVelocity / fArr2[1];
                float f6 = !Float.isNaN(fAbs) ? (fAbs / 3.0f) + progress : progress;
                if (f6 == 0.0f || f6 == 1.0f || (i = this.mOnTouchUp) == 3) {
                    if (0.0f >= f6 || 1.0f <= f6) {
                        this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
                        return;
                    }
                    return;
                }
                float f7 = ((double) f6) < 0.5d ? 0.0f : 1.0f;
                if (i == 6) {
                    if (progress + fAbs < 0.0f) {
                        fAbs = Math.abs(fAbs);
                    }
                    f7 = 1.0f;
                }
                if (this.mOnTouchUp == 7) {
                    if (progress + fAbs > 1.0f) {
                        fAbs = -Math.abs(fAbs);
                    }
                    f7 = 0.0f;
                }
                this.mMotionLayout.touchAnimateTo(this.mOnTouchUp, f7, fAbs);
                if (0.0f >= progress || 1.0f <= progress) {
                    this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
                    return;
                }
                return;
            }
            if (action != 2) {
                return;
            }
            float rawY = motionEvent.getRawY() - this.mLastTouchY;
            float rawX = motionEvent.getRawX() - this.mLastTouchX;
            if (Math.abs((this.mTouchDirectionY * rawY) + (this.mTouchDirectionX * rawX)) > this.mDragThreshold || this.mDragStarted) {
                float progress2 = this.mMotionLayout.getProgress();
                if (!this.mDragStarted) {
                    this.mDragStarted = true;
                    this.mMotionLayout.setProgress(progress2);
                }
                int i5 = this.mTouchAnchorId;
                if (i5 != -1) {
                    this.mMotionLayout.getAnchorDpDt(i5, progress2, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
                    c2 = 0;
                } else {
                    float fMin2 = Math.min(this.mMotionLayout.getWidth(), this.mMotionLayout.getHeight());
                    float[] fArr3 = this.mAnchorDpDt;
                    fArr3[1] = this.mTouchDirectionY * fMin2;
                    c2 = 0;
                    fArr3[0] = fMin2 * this.mTouchDirectionX;
                }
                float f8 = this.mTouchDirectionX;
                float[] fArr4 = this.mAnchorDpDt;
                if (Math.abs(((this.mTouchDirectionY * fArr4[1]) + (f8 * fArr4[c2])) * this.mDragScale) < 0.01d) {
                    float[] fArr5 = this.mAnchorDpDt;
                    c3 = 0;
                    fArr5[0] = 0.01f;
                    fArr5[1] = 0.01f;
                } else {
                    c3 = 0;
                }
                float fMax = Math.max(Math.min(progress2 + (this.mTouchDirectionX != 0.0f ? rawX / this.mAnchorDpDt[c3] : rawY / this.mAnchorDpDt[1]), 1.0f), 0.0f);
                if (this.mOnTouchUp == 6) {
                    fMax = Math.max(fMax, 0.01f);
                }
                if (this.mOnTouchUp == 7) {
                    fMax = Math.min(fMax, 0.99f);
                }
                float progress3 = this.mMotionLayout.getProgress();
                if (fMax != progress3) {
                    if (progress3 == 0.0f || progress3 == 1.0f) {
                        this.mMotionLayout.endTrigger(progress3 == 0.0f);
                    }
                    this.mMotionLayout.setProgress(fMax);
                    motionTracker.computeCurrentVelocity(1000);
                    this.mMotionLayout.mLastVelocity = this.mTouchDirectionX != 0.0f ? motionTracker.getXVelocity() / this.mAnchorDpDt[0] : motionTracker.getYVelocity() / this.mAnchorDpDt[1];
                } else {
                    this.mMotionLayout.mLastVelocity = 0.0f;
                }
                this.mLastTouchX = motionEvent.getRawX();
                this.mLastTouchY = motionEvent.getRawY();
                return;
            }
            return;
        }
        motionTracker.addMovement(motionEvent);
        int action2 = motionEvent.getAction();
        if (action2 == 0) {
            this.mLastTouchX = motionEvent.getRawX();
            this.mLastTouchY = motionEvent.getRawY();
            this.mDragStarted = false;
            return;
        }
        if (action2 != 1) {
            if (action2 != 2) {
                return;
            }
            motionEvent.getRawY();
            motionEvent.getRawX();
            float width = this.mMotionLayout.getWidth() / 2.0f;
            float height = this.mMotionLayout.getHeight() / 2.0f;
            int i6 = this.mRotationCenterId;
            if (i6 != -1) {
                View viewFindViewById = this.mMotionLayout.findViewById(i6);
                this.mMotionLayout.getLocationOnScreen(this.mTempLoc);
                float right2 = this.mTempLoc[0] + ((viewFindViewById.getRight() + viewFindViewById.getLeft()) / 2.0f);
                height = ((viewFindViewById.getBottom() + viewFindViewById.getTop()) / 2.0f) + this.mTempLoc[1];
                width = right2;
            } else {
                int i7 = this.mTouchAnchorId;
                if (i7 != -1) {
                    MotionLayout motionLayout = this.mMotionLayout;
                    View viewFindViewById2 = this.mMotionLayout.findViewById(motionLayout.mFrameArrayList.get(motionLayout.findViewById(i7)).getAnimateRelativeTo());
                    if (viewFindViewById2 == null) {
                        Log.e(TAG, "could not find view to animate to");
                    } else {
                        this.mMotionLayout.getLocationOnScreen(this.mTempLoc);
                        width = this.mTempLoc[0] + ((viewFindViewById2.getRight() + viewFindViewById2.getLeft()) / 2.0f);
                        height = this.mTempLoc[1] + ((viewFindViewById2.getBottom() + viewFindViewById2.getTop()) / 2.0f);
                    }
                }
            }
            float rawX2 = motionEvent.getRawX() - width;
            float rawY2 = motionEvent.getRawY() - height;
            double dAtan2 = Math.atan2(motionEvent.getRawY() - height, motionEvent.getRawX() - width);
            float fAtan2 = (float) (((dAtan2 - Math.atan2(this.mLastTouchY - height, this.mLastTouchX - width)) * 180.0d) / 3.141592653589793d);
            if (fAtan2 > 330.0f) {
                fAtan2 -= 360.0f;
            } else if (fAtan2 < -330.0f) {
                fAtan2 += 360.0f;
            }
            if (Math.abs(fAtan2) > 0.01d || this.mDragStarted) {
                float progress4 = this.mMotionLayout.getProgress();
                if (!this.mDragStarted) {
                    this.mDragStarted = true;
                    this.mMotionLayout.setProgress(progress4);
                }
                int i8 = this.mTouchAnchorId;
                if (i8 != -1) {
                    this.mMotionLayout.getAnchorDpDt(i8, progress4, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
                    float[] fArr6 = this.mAnchorDpDt;
                    fArr6[1] = (float) Math.toDegrees(fArr6[1]);
                } else {
                    this.mAnchorDpDt[1] = 360.0f;
                }
                float fMax2 = Math.max(Math.min(((fAtan2 * this.mDragScale) / this.mAnchorDpDt[1]) + progress4, 1.0f), 0.0f);
                float progress5 = this.mMotionLayout.getProgress();
                if (fMax2 != progress5) {
                    if (progress5 == 0.0f || progress5 == 1.0f) {
                        this.mMotionLayout.endTrigger(progress5 == 0.0f);
                    }
                    this.mMotionLayout.setProgress(fMax2);
                    motionTracker.computeCurrentVelocity(1000);
                    float xVelocity2 = motionTracker.getXVelocity();
                    double yVelocity2 = motionTracker.getYVelocity();
                    double d = xVelocity2;
                    this.mMotionLayout.mLastVelocity = (float) Math.toDegrees((float) ((Math.sin(Math.atan2(yVelocity2, d) - dAtan2) * Math.hypot(yVelocity2, d)) / Math.hypot(rawX2, rawY2)));
                } else {
                    this.mMotionLayout.mLastVelocity = 0.0f;
                }
                this.mLastTouchX = motionEvent.getRawX();
                this.mLastTouchY = motionEvent.getRawY();
                return;
            }
            return;
        }
        this.mDragStarted = false;
        motionTracker.computeCurrentVelocity(16);
        float xVelocity3 = motionTracker.getXVelocity();
        float yVelocity3 = motionTracker.getYVelocity();
        float progress6 = this.mMotionLayout.getProgress();
        float width2 = this.mMotionLayout.getWidth() / 2.0f;
        float height2 = this.mMotionLayout.getHeight() / 2.0f;
        int i9 = this.mRotationCenterId;
        if (i9 == -1) {
            int i10 = this.mTouchAnchorId;
            if (i10 != -1) {
                MotionLayout motionLayout2 = this.mMotionLayout;
                View viewFindViewById3 = this.mMotionLayout.findViewById(motionLayout2.mFrameArrayList.get(motionLayout2.findViewById(i10)).getAnimateRelativeTo());
                this.mMotionLayout.getLocationOnScreen(this.mTempLoc);
                right = ((viewFindViewById3.getRight() + viewFindViewById3.getLeft()) / 2.0f) + this.mTempLoc[0];
                f = this.mTempLoc[1];
                top = viewFindViewById3.getTop();
                bottom = viewFindViewById3.getBottom();
            }
            float rawX3 = motionEvent.getRawX() - width2;
            float rawY3 = motionEvent.getRawY() - height2;
            double degrees2 = Math.toDegrees(Math.atan2(rawY3, rawX3));
            i2 = this.mTouchAnchorId;
            if (i2 != -1) {
                this.mMotionLayout.getAnchorDpDt(i2, progress6, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
                float[] fArr7 = this.mAnchorDpDt;
                fArr7[1] = (float) Math.toDegrees(fArr7[1]);
            } else {
                this.mAnchorDpDt[1] = 360.0f;
            }
            degrees = ((float) (Math.toDegrees(Math.atan2(yVelocity3 + rawY3, xVelocity3 + rawX3)) - degrees2)) * 62.5f;
            if (Float.isNaN(degrees)) {
                f2 = progress6;
            } else {
                f2 = (((degrees * 3.0f) * this.mDragScale) / this.mAnchorDpDt[1]) + progress6;
            }
            if (f2 != 0.0f || f2 == 1.0f || (i3 = this.mOnTouchUp) == 3) {
                if (0.0f < f2 || 1.0f <= f2) {
                    this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
                }
                return;
            }
            float fAbs2 = (degrees * this.mDragScale) / this.mAnchorDpDt[1];
            float f9 = ((double) f2) < 0.5d ? 0.0f : 1.0f;
            if (i3 == 6) {
                if (progress6 + fAbs2 < 0.0f) {
                    fAbs2 = Math.abs(fAbs2);
                }
                f9 = 1.0f;
            }
            if (this.mOnTouchUp == 7) {
                if (progress6 + fAbs2 > 1.0f) {
                    fAbs2 = -Math.abs(fAbs2);
                }
                f9 = 0.0f;
            }
            this.mMotionLayout.touchAnimateTo(this.mOnTouchUp, f9, fAbs2 * 3.0f);
            if (0.0f >= progress6 || 1.0f <= progress6) {
                this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
                return;
            }
            return;
        }
        View viewFindViewById4 = this.mMotionLayout.findViewById(i9);
        this.mMotionLayout.getLocationOnScreen(this.mTempLoc);
        right = ((viewFindViewById4.getRight() + viewFindViewById4.getLeft()) / 2.0f) + this.mTempLoc[0];
        f = this.mTempLoc[1];
        top = viewFindViewById4.getTop();
        bottom = viewFindViewById4.getBottom();
        height2 = f + ((bottom + top) / 2.0f);
        width2 = right;
        float rawX4 = motionEvent.getRawX() - width2;
        float rawY4 = motionEvent.getRawY() - height2;
        double degrees3 = Math.toDegrees(Math.atan2(rawY4, rawX4));
        i2 = this.mTouchAnchorId;
        if (i2 != -1) {
            this.mMotionLayout.getAnchorDpDt(i2, progress6, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
            float[] fArr8 = this.mAnchorDpDt;
            fArr8[1] = (float) Math.toDegrees(fArr8[1]);
        } else {
            this.mAnchorDpDt[1] = 360.0f;
        }
        degrees = ((float) (Math.toDegrees(Math.atan2(yVelocity3 + rawY4, xVelocity3 + rawX4)) - degrees3)) * 62.5f;
        if (Float.isNaN(degrees)) {
            f2 = (((degrees * 3.0f) * this.mDragScale) / this.mAnchorDpDt[1]) + progress6;
        } else {
            f2 = progress6;
        }
        if (f2 != 0.0f) {
        }
        if (0.0f < f2) {
        }
        this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
    }

    public final void scrollMove(float dx, float dy) {
        float progress = this.mMotionLayout.getProgress();
        if (!this.mDragStarted) {
            this.mDragStarted = true;
            this.mMotionLayout.setProgress(progress);
        }
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, progress, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        float f = this.mTouchDirectionX;
        float[] fArr = this.mAnchorDpDt;
        if (Math.abs((this.mTouchDirectionY * fArr[1]) + (f * fArr[0])) < 0.01d) {
            float[] fArr2 = this.mAnchorDpDt;
            fArr2[0] = 0.01f;
            fArr2[1] = 0.01f;
        }
        float f2 = this.mTouchDirectionX;
        float fMax = Math.max(Math.min(progress + (f2 != 0.0f ? (dx * f2) / this.mAnchorDpDt[0] : (dy * this.mTouchDirectionY) / this.mAnchorDpDt[1]), 1.0f), 0.0f);
        if (fMax != this.mMotionLayout.getProgress()) {
            this.mMotionLayout.setProgress(fMax);
        }
    }

    public final void scrollUp(float dx, float dy) {
        this.mDragStarted = false;
        float progress = this.mMotionLayout.getProgress();
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, progress, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        float f = this.mTouchDirectionX;
        float[] fArr = this.mAnchorDpDt;
        float f2 = fArr[0];
        float f3 = this.mTouchDirectionY;
        float f4 = fArr[1];
        float f5 = f != 0.0f ? (dx * f) / fArr[0] : (dy * f3) / fArr[1];
        if (!Float.isNaN(f5)) {
            progress += f5 / 3.0f;
        }
        if (progress != 0.0f) {
            boolean z = progress != 1.0f;
            int i = this.mOnTouchUp;
            if ((i != 3) && z) {
                this.mMotionLayout.touchAnimateTo(i, ((double) progress) >= 0.5d ? 1.0f : 0.0f, f5);
            }
        }
    }

    public void setAnchorId(int id) {
        this.mTouchAnchorId = id;
    }

    public final void setDown(float lastTouchX, float lastTouchY) {
        this.mLastTouchX = lastTouchX;
        this.mLastTouchY = lastTouchY;
    }

    public void setMaxAcceleration(float acceleration) {
        this.mMaxAcceleration = acceleration;
    }

    public void setMaxVelocity(float velocity) {
        this.mMaxVelocity = velocity;
    }

    public void setRTL(boolean rtl) {
        if (rtl) {
            float[][] fArr = TOUCH_DIRECTION;
            fArr[4] = fArr[3];
            fArr[5] = fArr[2];
            float[][] fArr2 = TOUCH_SIDES;
            fArr2[5] = fArr2[2];
            fArr2[6] = fArr2[1];
        } else {
            float[][] fArr3 = TOUCH_DIRECTION;
            fArr3[4] = fArr3[2];
            fArr3[5] = fArr3[3];
            float[][] fArr4 = TOUCH_SIDES;
            fArr4[5] = fArr4[1];
            fArr4[6] = fArr4[2];
        }
        float[][] fArr5 = TOUCH_SIDES;
        int i = this.mTouchAnchorSide;
        this.mTouchAnchorX = fArr5[i][0];
        this.mTouchAnchorY = fArr5[i][1];
        int i2 = this.mTouchSide;
        float[][] fArr6 = TOUCH_DIRECTION;
        if (i2 >= fArr6.length) {
            return;
        }
        this.mTouchDirectionX = fArr6[i2][0];
        this.mTouchDirectionY = fArr6[i2][1];
    }

    public void setTouchAnchorLocation(float x, float y) {
        this.mTouchAnchorX = x;
        this.mTouchAnchorY = y;
    }

    public void setTouchUpMode(int touchUpMode) {
        this.mOnTouchUp = touchUpMode;
    }

    public final void setUpTouchEvent(float lastTouchX, float lastTouchY) {
        this.mLastTouchX = lastTouchX;
        this.mLastTouchY = lastTouchY;
        this.mDragStarted = false;
    }

    public final void setupTouch() {
        View viewFindViewById;
        int i = this.mTouchAnchorId;
        if (i != -1) {
            viewFindViewById = this.mMotionLayout.findViewById(i);
            if (viewFindViewById == null) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("cannot find TouchAnchorId @id/");
                sbM.append(Debug.getName(this.mMotionLayout.getContext(), this.mTouchAnchorId));
                Log.e(TAG, sbM.toString());
            }
        } else {
            viewFindViewById = null;
        }
        if (viewFindViewById instanceof NestedScrollView) {
            NestedScrollView nestedScrollView = (NestedScrollView) viewFindViewById;
            nestedScrollView.setOnTouchListener(new View.OnTouchListener() { // from class: androidx.constraintlayout.motion.widget.TouchResponse.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: androidx.constraintlayout.motion.widget.TouchResponse.2
                @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                }
            });
        }
    }

    public String toString() {
        if (Float.isNaN(this.mTouchDirectionX)) {
            return Key.ROTATION;
        }
        return this.mTouchDirectionX + " , " + this.mTouchDirectionY;
    }

    public TouchResponse(MotionLayout layout, OnSwipe onSwipe) {
        this.mTouchAnchorSide = 0;
        this.mTouchSide = 0;
        this.mOnTouchUp = 0;
        this.mTouchAnchorId = -1;
        this.mTouchRegionId = -1;
        this.mLimitBoundsTo = -1;
        this.mTouchAnchorY = 0.5f;
        this.mTouchAnchorX = 0.5f;
        this.mRotateCenterX = 0.5f;
        this.mRotateCenterY = 0.5f;
        this.mRotationCenterId = -1;
        this.mIsRotateMode = false;
        this.mTouchDirectionX = 0.0f;
        this.mTouchDirectionY = 1.0f;
        this.mDragStarted = false;
        this.mAnchorDpDt = new float[2];
        this.mTempLoc = new int[2];
        this.mMaxVelocity = 4.0f;
        this.mMaxAcceleration = 1.2f;
        this.mMoveWhenScrollAtTop = true;
        this.mDragScale = 1.0f;
        this.mFlags = 0;
        this.mDragThreshold = 10.0f;
        this.mSpringDamping = 10.0f;
        this.mSpringMass = 1.0f;
        this.mSpringStiffness = Float.NaN;
        this.mSpringStopThreshold = Float.NaN;
        this.mSpringBoundary = 0;
        this.mAutoCompleteMode = 0;
        this.mMotionLayout = layout;
        this.mTouchAnchorId = onSwipe.getTouchAnchorId();
        int touchAnchorSide = onSwipe.getTouchAnchorSide();
        this.mTouchAnchorSide = touchAnchorSide;
        if (touchAnchorSide != -1) {
            float[][] fArr = TOUCH_SIDES;
            this.mTouchAnchorX = fArr[touchAnchorSide][0];
            this.mTouchAnchorY = fArr[touchAnchorSide][1];
        }
        int dragDirection = onSwipe.getDragDirection();
        this.mTouchSide = dragDirection;
        float[][] fArr2 = TOUCH_DIRECTION;
        if (dragDirection < fArr2.length) {
            this.mTouchDirectionX = fArr2[dragDirection][0];
            this.mTouchDirectionY = fArr2[dragDirection][1];
        } else {
            this.mTouchDirectionY = Float.NaN;
            this.mTouchDirectionX = Float.NaN;
            this.mIsRotateMode = true;
        }
        this.mMaxVelocity = onSwipe.getMaxVelocity();
        this.mMaxAcceleration = onSwipe.getMaxAcceleration();
        this.mMoveWhenScrollAtTop = onSwipe.getMoveWhenScrollAtTop();
        this.mDragScale = onSwipe.getDragScale();
        this.mDragThreshold = onSwipe.getDragThreshold();
        this.mTouchRegionId = onSwipe.getTouchRegionId();
        this.mOnTouchUp = onSwipe.getOnTouchUp();
        this.mFlags = onSwipe.getNestedScrollFlags();
        this.mLimitBoundsTo = onSwipe.getLimitBoundsTo();
        this.mRotationCenterId = onSwipe.getRotationCenterId();
        this.mSpringBoundary = onSwipe.getSpringBoundary();
        this.mSpringDamping = onSwipe.getSpringDamping();
        this.mSpringMass = onSwipe.getSpringMass();
        this.mSpringStiffness = onSwipe.getSpringStiffness();
        this.mSpringStopThreshold = onSwipe.getSpringStopThreshold();
        this.mAutoCompleteMode = onSwipe.getAutoCompleteMode();
    }
}
