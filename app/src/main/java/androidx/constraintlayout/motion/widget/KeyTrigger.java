package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class KeyTrigger extends Key {
    public static final String CROSS = "CROSS";
    public static final int KEY_TYPE = 5;
    public static final String NEGATIVE_CROSS = "negativeCross";
    public static final String POSITIVE_CROSS = "positiveCross";
    public static final String POST_LAYOUT = "postLayout";
    private static final String TAG = "KeyTrigger";
    public static final String TRIGGER_COLLISION_ID = "triggerCollisionId";
    public static final String TRIGGER_COLLISION_VIEW = "triggerCollisionView";
    public static final String TRIGGER_ID = "triggerID";
    public static final String TRIGGER_RECEIVER = "triggerReceiver";
    public static final String TRIGGER_SLACK = "triggerSlack";
    public static final String VIEW_TRANSITION_ON_CROSS = "viewTransitionOnCross";
    public static final String VIEW_TRANSITION_ON_NEGATIVE_CROSS = "viewTransitionOnNegativeCross";
    public static final String VIEW_TRANSITION_ON_POSITIVE_CROSS = "viewTransitionOnPositiveCross";
    public RectF mCollisionRect;
    private boolean mFireCrossReset;
    private float mFireLastPos;
    private boolean mFireNegativeReset;
    private boolean mFirePositiveReset;
    private float mFireThreshold;
    public HashMap<String, Method> mMethodHashMap;
    private String mNegativeCross;
    private String mPositiveCross;
    private boolean mPostLayout;
    public RectF mTargetRect;
    private int mTriggerCollisionId;
    private View mTriggerCollisionView;
    private int mTriggerID;
    private int mTriggerReceiver;
    public float mTriggerSlack;
    public int mViewTransitionOnCross;
    public int mViewTransitionOnNegativeCross;
    public int mViewTransitionOnPositiveCross;
    private int mCurveFit = -1;
    private String mCross = null;

    public static class Loader {
        private static final int COLLISION = 9;
        private static final int CROSS = 4;
        private static final int FRAME_POS = 8;
        private static final int NEGATIVE_CROSS = 1;
        private static final int POSITIVE_CROSS = 2;
        private static final int POST_LAYOUT = 10;
        private static final int TARGET_ID = 7;
        private static final int TRIGGER_ID = 6;
        private static final int TRIGGER_RECEIVER = 11;
        private static final int TRIGGER_SLACK = 5;
        private static final int VT_CROSS = 12;
        private static final int VT_NEGATIVE_CROSS = 13;
        private static final int VT_POSITIVE_CROSS = 14;
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyTrigger_framePosition, 8);
            mAttrMap.append(R.styleable.KeyTrigger_onCross, 4);
            mAttrMap.append(R.styleable.KeyTrigger_onNegativeCross, 1);
            mAttrMap.append(R.styleable.KeyTrigger_onPositiveCross, 2);
            mAttrMap.append(R.styleable.KeyTrigger_motionTarget, 7);
            mAttrMap.append(R.styleable.KeyTrigger_triggerId, 6);
            mAttrMap.append(R.styleable.KeyTrigger_triggerSlack, 5);
            mAttrMap.append(R.styleable.KeyTrigger_motion_triggerOnCollision, 9);
            mAttrMap.append(R.styleable.KeyTrigger_motion_postLayoutCollision, 10);
            mAttrMap.append(R.styleable.KeyTrigger_triggerReceiver, 11);
            mAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnCross, 12);
            mAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnNegativeCross, 13);
            mAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnPositiveCross, 14);
        }

        private Loader() {
        }

        public static void read(KeyTrigger c, TypedArray a, Context context) {
            int indexCount = a.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = a.getIndex(i);
                switch (mAttrMap.get(index)) {
                    case 1:
                        c.mNegativeCross = a.getString(index);
                        break;
                    case 2:
                        c.mPositiveCross = a.getString(index);
                        break;
                    case 3:
                    default:
                        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("unused attribute 0x");
                        sbM.append(Integer.toHexString(index));
                        sbM.append("   ");
                        sbM.append(mAttrMap.get(index));
                        Log.e("KeyTrigger", sbM.toString());
                        break;
                    case 4:
                        c.mCross = a.getString(index);
                        break;
                    case 5:
                        c.mTriggerSlack = a.getFloat(index, c.mTriggerSlack);
                        break;
                    case 6:
                        c.mTriggerID = a.getResourceId(index, c.mTriggerID);
                        break;
                    case 7:
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
                    case 8:
                        int integer = a.getInteger(index, c.mFramePosition);
                        c.mFramePosition = integer;
                        c.mFireThreshold = (integer + 0.5f) / 100.0f;
                        break;
                    case 9:
                        c.mTriggerCollisionId = a.getResourceId(index, c.mTriggerCollisionId);
                        break;
                    case 10:
                        c.mPostLayout = a.getBoolean(index, c.mPostLayout);
                        break;
                    case 11:
                        c.mTriggerReceiver = a.getResourceId(index, c.mTriggerReceiver);
                        break;
                    case 12:
                        c.mViewTransitionOnCross = a.getResourceId(index, c.mViewTransitionOnCross);
                        break;
                    case 13:
                        c.mViewTransitionOnNegativeCross = a.getResourceId(index, c.mViewTransitionOnNegativeCross);
                        break;
                    case 14:
                        c.mViewTransitionOnPositiveCross = a.getResourceId(index, c.mViewTransitionOnPositiveCross);
                        break;
                }
            }
        }
    }

    public KeyTrigger() {
        int i = Key.UNSET;
        this.mTriggerReceiver = i;
        this.mNegativeCross = null;
        this.mPositiveCross = null;
        this.mTriggerID = i;
        this.mTriggerCollisionId = i;
        this.mTriggerCollisionView = null;
        this.mTriggerSlack = 0.1f;
        this.mFireCrossReset = true;
        this.mFireNegativeReset = true;
        this.mFirePositiveReset = true;
        this.mFireThreshold = Float.NaN;
        this.mPostLayout = false;
        this.mViewTransitionOnNegativeCross = i;
        this.mViewTransitionOnPositiveCross = i;
        this.mViewTransitionOnCross = i;
        this.mCollisionRect = new RectF();
        this.mTargetRect = new RectF();
        this.mMethodHashMap = new HashMap<>();
        this.mType = 5;
        this.mCustomConstraints = new HashMap<>();
    }

    private void fire(String str, View call) {
        Method method;
        if (str == null) {
            return;
        }
        if (str.startsWith(".")) {
            fireCustom(str, call);
            return;
        }
        if (this.mMethodHashMap.containsKey(str)) {
            method = this.mMethodHashMap.get(str);
            if (method == null) {
                return;
            }
        } else {
            method = null;
        }
        if (method == null) {
            try {
                method = call.getClass().getMethod(str, new Class[0]);
                this.mMethodHashMap.put(str, method);
            } catch (NoSuchMethodException unused) {
                this.mMethodHashMap.put(str, null);
                Log.e("KeyTrigger", "Could not find method \"" + str + "\"on class " + call.getClass().getSimpleName() + " " + Debug.getName(call));
                return;
            }
        }
        try {
            method.invoke(call, new Object[0]);
        } catch (Exception unused2) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Exception in call \"");
            sbM.append(this.mCross);
            sbM.append("\"on class ");
            sbM.append(call.getClass().getSimpleName());
            sbM.append(" ");
            sbM.append(Debug.getName(call));
            Log.e("KeyTrigger", sbM.toString());
        }
    }

    private void fireCustom(String str, View view) {
        boolean z = str.length() == 1;
        if (!z) {
            str = str.substring(1).toLowerCase(Locale.ROOT);
        }
        for (String str2 : this.mCustomConstraints.keySet()) {
            String lowerCase = str2.toLowerCase(Locale.ROOT);
            if (z || lowerCase.matches(str)) {
                ConstraintAttribute constraintAttribute = this.mCustomConstraints.get(str2);
                if (constraintAttribute != null) {
                    constraintAttribute.applyCustom(view);
                }
            }
        }
    }

    private void setUpRect(RectF rect, View child, boolean postLayout) {
        rect.top = child.getTop();
        rect.bottom = child.getBottom();
        rect.left = child.getLeft();
        rect.right = child.getRight();
        if (postLayout) {
            child.getMatrix().mapRect(rect);
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, ViewSpline> splines) {
    }

    /* JADX WARN: Code duplicated, block: B:38:0x008d  */
    /* JADX WARN: Code duplicated, block: B:43:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:45:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:49:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:51:0x00c4  */
    /* JADX WARN: Code duplicated, block: B:54:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:56:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:58:0x00df  */
    public void conditionallyFire(float pos, View child) {
        boolean z;
        boolean z2;
        boolean z3;
        float f;
        float f2;
        boolean z4;
        boolean z5;
        float f3;
        float f4;
        boolean z6;
        if (this.mTriggerCollisionId != Key.UNSET) {
            if (this.mTriggerCollisionView == null) {
                this.mTriggerCollisionView = ((ViewGroup) child.getParent()).findViewById(this.mTriggerCollisionId);
            }
            setUpRect(this.mCollisionRect, this.mTriggerCollisionView, this.mPostLayout);
            setUpRect(this.mTargetRect, child, this.mPostLayout);
            if (this.mCollisionRect.intersect(this.mTargetRect)) {
                if (this.mFireCrossReset) {
                    this.mFireCrossReset = false;
                    z = true;
                } else {
                    z = false;
                }
                if (this.mFirePositiveReset) {
                    this.mFirePositiveReset = false;
                    z6 = true;
                } else {
                    z6 = false;
                }
                this.mFireNegativeReset = true;
                z5 = z6;
                z3 = false;
            } else {
                if (this.mFireCrossReset) {
                    z = false;
                } else {
                    this.mFireCrossReset = true;
                    z = true;
                }
                if (this.mFireNegativeReset) {
                    this.mFireNegativeReset = false;
                    z3 = true;
                } else {
                    z3 = false;
                }
                this.mFirePositiveReset = true;
                z5 = false;
            }
        } else {
            if (this.mFireCrossReset) {
                float f5 = this.mFireThreshold;
                if ((this.mFireLastPos - f5) * (pos - f5) < 0.0f) {
                    this.mFireCrossReset = false;
                    z = true;
                }
                if (this.mFireNegativeReset) {
                    f3 = this.mFireThreshold;
                    f4 = pos - f3;
                    if ((this.mFireLastPos - f3) * f4 >= 0.0f && f4 < 0.0f) {
                        this.mFireNegativeReset = false;
                        z2 = true;
                    }
                    if (this.mFirePositiveReset) {
                        f = this.mFireThreshold;
                        f2 = pos - f;
                        if ((this.mFireLastPos - f) * f2 < 0.0f || f2 <= 0.0f) {
                            z4 = false;
                        } else {
                            this.mFirePositiveReset = false;
                            z4 = true;
                        }
                        boolean z7 = z2;
                        z5 = z4;
                        z3 = z7;
                    } else {
                        if (Math.abs(pos - this.mFireThreshold) > this.mTriggerSlack) {
                            this.mFirePositiveReset = true;
                        }
                        z3 = z2;
                        z5 = false;
                    }
                } else if (Math.abs(pos - this.mFireThreshold) > this.mTriggerSlack) {
                    this.mFireNegativeReset = true;
                }
                z2 = false;
                if (this.mFirePositiveReset) {
                    f = this.mFireThreshold;
                    f2 = pos - f;
                    if ((this.mFireLastPos - f) * f2 < 0.0f) {
                        z4 = false;
                    } else {
                        z4 = false;
                    }
                    boolean z8 = z2;
                    z5 = z4;
                    z3 = z8;
                } else {
                    if (Math.abs(pos - this.mFireThreshold) > this.mTriggerSlack) {
                        this.mFirePositiveReset = true;
                    }
                    z3 = z2;
                    z5 = false;
                }
            } else if (Math.abs(pos - this.mFireThreshold) > this.mTriggerSlack) {
                this.mFireCrossReset = true;
            }
            z = false;
            if (this.mFireNegativeReset) {
                f3 = this.mFireThreshold;
                f4 = pos - f3;
                if ((this.mFireLastPos - f3) * f4 >= 0.0f) {
                }
                if (this.mFirePositiveReset) {
                    f = this.mFireThreshold;
                    f2 = pos - f;
                    if ((this.mFireLastPos - f) * f2 < 0.0f) {
                        z4 = false;
                    } else {
                        z4 = false;
                    }
                    boolean z9 = z2;
                    z5 = z4;
                    z3 = z9;
                } else {
                    if (Math.abs(pos - this.mFireThreshold) > this.mTriggerSlack) {
                        this.mFirePositiveReset = true;
                    }
                    z3 = z2;
                    z5 = false;
                }
            } else if (Math.abs(pos - this.mFireThreshold) > this.mTriggerSlack) {
                this.mFireNegativeReset = true;
            }
            z2 = false;
            if (this.mFirePositiveReset) {
                f = this.mFireThreshold;
                f2 = pos - f;
                if ((this.mFireLastPos - f) * f2 < 0.0f) {
                    z4 = false;
                } else {
                    z4 = false;
                }
                boolean z10 = z2;
                z5 = z4;
                z3 = z10;
            } else {
                if (Math.abs(pos - this.mFireThreshold) > this.mTriggerSlack) {
                    this.mFirePositiveReset = true;
                }
                z3 = z2;
                z5 = false;
            }
        }
        this.mFireLastPos = pos;
        if (z3 || z || z5) {
            ((MotionLayout) child.getParent()).fireTrigger(this.mTriggerID, z5, pos);
        }
        View viewFindViewById = this.mTriggerReceiver == Key.UNSET ? child : ((MotionLayout) child.getParent()).findViewById(this.mTriggerReceiver);
        if (z3) {
            String str = this.mNegativeCross;
            if (str != null) {
                fire(str, viewFindViewById);
            }
            if (this.mViewTransitionOnNegativeCross != Key.UNSET) {
                ((MotionLayout) child.getParent()).viewTransition(this.mViewTransitionOnNegativeCross, viewFindViewById);
            }
        }
        if (z5) {
            String str2 = this.mPositiveCross;
            if (str2 != null) {
                fire(str2, viewFindViewById);
            }
            if (this.mViewTransitionOnPositiveCross != Key.UNSET) {
                ((MotionLayout) child.getParent()).viewTransition(this.mViewTransitionOnPositiveCross, viewFindViewById);
            }
        }
        if (z) {
            String str3 = this.mCross;
            if (str3 != null) {
                fire(str3, viewFindViewById);
            }
            if (this.mViewTransitionOnCross != Key.UNSET) {
                ((MotionLayout) child.getParent()).viewTransition(this.mViewTransitionOnCross, viewFindViewById);
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public Key copy(Key src) {
        super.copy(src);
        KeyTrigger keyTrigger = (KeyTrigger) src;
        this.mCurveFit = keyTrigger.mCurveFit;
        this.mCross = keyTrigger.mCross;
        this.mTriggerReceiver = keyTrigger.mTriggerReceiver;
        this.mNegativeCross = keyTrigger.mNegativeCross;
        this.mPositiveCross = keyTrigger.mPositiveCross;
        this.mTriggerID = keyTrigger.mTriggerID;
        this.mTriggerCollisionId = keyTrigger.mTriggerCollisionId;
        this.mTriggerCollisionView = keyTrigger.mTriggerCollisionView;
        this.mTriggerSlack = keyTrigger.mTriggerSlack;
        this.mFireCrossReset = keyTrigger.mFireCrossReset;
        this.mFireNegativeReset = keyTrigger.mFireNegativeReset;
        this.mFirePositiveReset = keyTrigger.mFirePositiveReset;
        this.mFireThreshold = keyTrigger.mFireThreshold;
        this.mFireLastPos = keyTrigger.mFireLastPos;
        this.mPostLayout = keyTrigger.mPostLayout;
        this.mCollisionRect = keyTrigger.mCollisionRect;
        this.mTargetRect = keyTrigger.mTargetRect;
        this.mMethodHashMap = keyTrigger.mMethodHashMap;
        return this;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void getAttributeNames(HashSet<String> attributes) {
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attrs) {
        Loader.read(this, context.obtainStyledAttributes(attrs, R.styleable.KeyTrigger), context);
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String tag, Object value) {
        Objects.requireNonNull(tag);
        switch (tag) {
            case "positiveCross":
                this.mPositiveCross = value.toString();
                break;
            case "viewTransitionOnPositiveCross":
                this.mViewTransitionOnPositiveCross = toInt(value);
                break;
            case "triggerCollisionId":
                this.mTriggerCollisionId = toInt(value);
                break;
            case "triggerID":
                this.mTriggerID = toInt(value);
                break;
            case "negativeCross":
                this.mNegativeCross = value.toString();
                break;
            case "triggerCollisionView":
                this.mTriggerCollisionView = (View) value;
                break;
            case "viewTransitionOnNegativeCross":
                this.mViewTransitionOnNegativeCross = toInt(value);
                break;
            case "CROSS":
                this.mCross = value.toString();
                break;
            case "triggerSlack":
                this.mTriggerSlack = toFloat(value);
                break;
            case "viewTransitionOnCross":
                this.mViewTransitionOnCross = toInt(value);
                break;
            case "postLayout":
                this.mPostLayout = value instanceof Boolean ? ((Boolean) value).booleanValue() : Boolean.parseBoolean(value.toString());
                break;
            case "triggerReceiver":
                this.mTriggerReceiver = toInt(value);
                break;
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* JADX INFO: renamed from: clone */
    public Key mo7clone() {
        return new KeyTrigger().copy(this);
    }
}
