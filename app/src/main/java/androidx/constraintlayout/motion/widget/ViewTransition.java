package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.util.Util$$ExternalSyntheticLambda0;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class ViewTransition {
    public static final String CONSTRAINT_OVERRIDE = "ConstraintOverride";
    public static final String CUSTOM_ATTRIBUTE = "CustomAttribute";
    public static final String CUSTOM_METHOD = "CustomMethod";
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    public static final String KEY_FRAME_SET_TAG = "KeyFrameSet";
    public static final int ONSTATE_ACTION_DOWN = 1;
    public static final int ONSTATE_ACTION_DOWN_UP = 3;
    public static final int ONSTATE_ACTION_UP = 2;
    public static final int ONSTATE_SHARED_VALUE_SET = 4;
    public static final int ONSTATE_SHARED_VALUE_UNSET = 5;
    private static final int SPLINE_STRING = -1;
    private static String TAG = "ViewTransition";
    private static final int UNSET = -1;
    public static final String VIEW_TRANSITION_TAG = "ViewTransition";
    public ConstraintSet.Constraint mConstraintDelta;
    public Context mContext;
    private int mId;
    public KeyFrames mKeyFrames;
    private int mTargetId;
    private String mTargetString;
    public int mViewTransitionMode;
    private int mOnStateTransition = -1;
    private boolean mDisabled = false;
    private int mPathMotionArc = 0;
    private int mDuration = -1;
    private int mUpDuration = -1;
    private int mDefaultInterpolator = 0;
    private String mDefaultInterpolatorString = null;
    private int mDefaultInterpolatorID = -1;
    private int mSetsTag = -1;
    private int mClearsTag = -1;
    private int mIfTagSet = -1;
    private int mIfTagNotSet = -1;
    private int mSharedValueTarget = -1;
    private int mSharedValueID = -1;
    private int mSharedValueCurrent = -1;

    public static class Animate {
        public boolean hold_at_100;
        private final int mClearsTag;
        public float mDpositionDt;
        public Interpolator mInterpolator;
        public MotionController mMC;
        public float mPosition;
        private final int mSetsTag;
        public int mUpDuration;
        public ViewTransitionController mVtController;
        public KeyCache mCache = new KeyCache();
        public boolean reverse = false;
        public Rect mTempRec = new Rect();
        public long mLastRender = System.nanoTime();

        public Animate(ViewTransitionController controller, MotionController motionController, int duration, int upDuration, int mode, Interpolator interpolator, int setTag, int clearTag) {
            this.hold_at_100 = false;
            this.mVtController = controller;
            this.mMC = motionController;
            this.mUpDuration = upDuration;
            ViewTransitionController viewTransitionController = this.mVtController;
            if (viewTransitionController.animations == null) {
                viewTransitionController.animations = new ArrayList<>();
            }
            viewTransitionController.animations.add(this);
            this.mInterpolator = interpolator;
            this.mSetsTag = setTag;
            this.mClearsTag = clearTag;
            if (mode == 3) {
                this.hold_at_100 = true;
            }
            this.mDpositionDt = duration == 0 ? Float.MAX_VALUE : 1.0f / duration;
            mutate();
        }

        public final void mutate() {
            if (this.reverse) {
                long jNanoTime = System.nanoTime();
                long j = jNanoTime - this.mLastRender;
                this.mLastRender = jNanoTime;
                float f = this.mPosition - (((float) (j * 1.0E-6d)) * this.mDpositionDt);
                this.mPosition = f;
                if (f < 0.0f) {
                    this.mPosition = 0.0f;
                }
                Interpolator interpolator = this.mInterpolator;
                float interpolation = interpolator == null ? this.mPosition : interpolator.getInterpolation(this.mPosition);
                MotionController motionController = this.mMC;
                boolean zInterpolate = motionController.interpolate(motionController.mView, interpolation, jNanoTime, this.mCache);
                if (this.mPosition <= 0.0f) {
                    if (this.mSetsTag != -1) {
                        this.mMC.getView().setTag(this.mSetsTag, Long.valueOf(System.nanoTime()));
                    }
                    if (this.mClearsTag != -1) {
                        this.mMC.getView().setTag(this.mClearsTag, null);
                    }
                    this.mVtController.removeList.add(this);
                }
                if (this.mPosition > 0.0f || zInterpolate) {
                    this.mVtController.invalidate();
                    return;
                }
                return;
            }
            long jNanoTime2 = System.nanoTime();
            long j2 = jNanoTime2 - this.mLastRender;
            this.mLastRender = jNanoTime2;
            float f2 = (((float) (j2 * 1.0E-6d)) * this.mDpositionDt) + this.mPosition;
            this.mPosition = f2;
            if (f2 >= 1.0f) {
                this.mPosition = 1.0f;
            }
            Interpolator interpolator2 = this.mInterpolator;
            float interpolation2 = interpolator2 == null ? this.mPosition : interpolator2.getInterpolation(this.mPosition);
            MotionController motionController2 = this.mMC;
            boolean zInterpolate2 = motionController2.interpolate(motionController2.mView, interpolation2, jNanoTime2, this.mCache);
            if (this.mPosition >= 1.0f) {
                if (this.mSetsTag != -1) {
                    this.mMC.getView().setTag(this.mSetsTag, Long.valueOf(System.nanoTime()));
                }
                if (this.mClearsTag != -1) {
                    this.mMC.getView().setTag(this.mClearsTag, null);
                }
                if (!this.hold_at_100) {
                    this.mVtController.removeList.add(this);
                }
            }
            if (this.mPosition < 1.0f || zInterpolate2) {
                this.mVtController.invalidate();
            }
        }

        public void reactTo(int action, float x, float y) {
            if (action == 1) {
                if (this.reverse) {
                    return;
                }
                reverse();
            } else {
                if (action != 2) {
                    return;
                }
                this.mMC.getView().getHitRect(this.mTempRec);
                if (this.mTempRec.contains((int) x, (int) y) || this.reverse) {
                    return;
                }
                reverse();
            }
        }

        public final void reverse() {
            this.reverse = true;
            int i = this.mUpDuration;
            if (i != -1) {
                this.mDpositionDt = i == 0 ? Float.MAX_VALUE : 1.0f / i;
            }
            this.mVtController.invalidate();
            this.mLastRender = System.nanoTime();
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x007f  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public ViewTransition(Context context, XmlPullParser parser) {
        byte b;
        this.mContext = context;
        try {
            int eventType = parser.getEventType();
            while (eventType != 1) {
                if (eventType == 2) {
                    String name = parser.getName();
                    switch (name.hashCode()) {
                        case -1962203927:
                            if (!name.equals(CONSTRAINT_OVERRIDE)) {
                                b = -1;
                            } else {
                                b = 2;
                            }
                            break;
                        case -1239391468:
                            if (!name.equals(KEY_FRAME_SET_TAG)) {
                                b = -1;
                            } else {
                                b = 1;
                            }
                            break;
                        case 61998586:
                            if (!name.equals(VIEW_TRANSITION_TAG)) {
                                b = -1;
                            } else {
                                b = 0;
                            }
                            break;
                        case 366511058:
                            if (!name.equals(CUSTOM_METHOD)) {
                                b = -1;
                            } else {
                                b = 4;
                            }
                            break;
                        case 1791837707:
                            if (!name.equals(CUSTOM_ATTRIBUTE)) {
                                b = -1;
                            } else {
                                b = 3;
                            }
                            break;
                        default:
                            b = -1;
                            break;
                    }
                    if (b == 0) {
                        parseViewTransitionTags(context, parser);
                    } else if (b == 1) {
                        this.mKeyFrames = new KeyFrames(context, parser);
                    } else if (b == 2) {
                        this.mConstraintDelta = ConstraintSet.buildDelta(context, parser);
                    } else if (b == 3 || b == 4) {
                        ConstraintAttribute.parse(context, parser, this.mConstraintDelta.mCustomConstraints);
                    } else {
                        Log.e(TAG, Debug.getLoc() + " unknown tag " + name);
                        Log.e(TAG, ".xml:" + parser.getLineNumber());
                    }
                } else if (eventType == 3 && VIEW_TRANSITION_TAG.equals(parser.getName())) {
                    return;
                }
                eventType = parser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyTransition$0(View[] viewArr) {
        if (this.mSetsTag != -1) {
            for (View view : viewArr) {
                view.setTag(this.mSetsTag, Long.valueOf(System.nanoTime()));
            }
        }
        if (this.mClearsTag != -1) {
            for (View view2 : viewArr) {
                view2.setTag(this.mClearsTag, null);
            }
        }
    }

    private void parseViewTransitionTags(Context context, XmlPullParser parser) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(parser), R.styleable.ViewTransition);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == R.styleable.ViewTransition_android_id) {
                this.mId = typedArrayObtainStyledAttributes.getResourceId(index, this.mId);
            } else if (index == R.styleable.ViewTransition_motionTarget) {
                if (MotionLayout.IS_IN_EDIT_MODE) {
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.mTargetId);
                    this.mTargetId = resourceId;
                    if (resourceId == -1) {
                        this.mTargetString = typedArrayObtainStyledAttributes.getString(index);
                    }
                } else if (typedArrayObtainStyledAttributes.peekValue(index).type == 3) {
                    this.mTargetString = typedArrayObtainStyledAttributes.getString(index);
                } else {
                    this.mTargetId = typedArrayObtainStyledAttributes.getResourceId(index, this.mTargetId);
                }
            } else if (index == R.styleable.ViewTransition_onStateTransition) {
                this.mOnStateTransition = typedArrayObtainStyledAttributes.getInt(index, this.mOnStateTransition);
            } else if (index == R.styleable.ViewTransition_transitionDisable) {
                this.mDisabled = typedArrayObtainStyledAttributes.getBoolean(index, this.mDisabled);
            } else if (index == R.styleable.ViewTransition_pathMotionArc) {
                this.mPathMotionArc = typedArrayObtainStyledAttributes.getInt(index, this.mPathMotionArc);
            } else if (index == R.styleable.ViewTransition_duration) {
                this.mDuration = typedArrayObtainStyledAttributes.getInt(index, this.mDuration);
            } else if (index == R.styleable.ViewTransition_upDuration) {
                this.mUpDuration = typedArrayObtainStyledAttributes.getInt(index, this.mUpDuration);
            } else if (index == R.styleable.ViewTransition_viewTransitionMode) {
                this.mViewTransitionMode = typedArrayObtainStyledAttributes.getInt(index, this.mViewTransitionMode);
            } else if (index == R.styleable.ViewTransition_motionInterpolator) {
                int i2 = typedArrayObtainStyledAttributes.peekValue(index).type;
                if (i2 == 1) {
                    int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                    this.mDefaultInterpolatorID = resourceId2;
                    if (resourceId2 != -1) {
                        this.mDefaultInterpolator = -2;
                    }
                } else if (i2 == 3) {
                    String string = typedArrayObtainStyledAttributes.getString(index);
                    this.mDefaultInterpolatorString = string;
                    if (string == null || string.indexOf("/") <= 0) {
                        this.mDefaultInterpolator = -1;
                    } else {
                        this.mDefaultInterpolatorID = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                        this.mDefaultInterpolator = -2;
                    }
                } else {
                    this.mDefaultInterpolator = typedArrayObtainStyledAttributes.getInteger(index, this.mDefaultInterpolator);
                }
            } else if (index == R.styleable.ViewTransition_setsTag) {
                this.mSetsTag = typedArrayObtainStyledAttributes.getResourceId(index, this.mSetsTag);
            } else if (index == R.styleable.ViewTransition_clearsTag) {
                this.mClearsTag = typedArrayObtainStyledAttributes.getResourceId(index, this.mClearsTag);
            } else if (index == R.styleable.ViewTransition_ifTagSet) {
                this.mIfTagSet = typedArrayObtainStyledAttributes.getResourceId(index, this.mIfTagSet);
            } else if (index == R.styleable.ViewTransition_ifTagNotSet) {
                this.mIfTagNotSet = typedArrayObtainStyledAttributes.getResourceId(index, this.mIfTagNotSet);
            } else if (index == R.styleable.ViewTransition_SharedValueId) {
                this.mSharedValueID = typedArrayObtainStyledAttributes.getResourceId(index, this.mSharedValueID);
            } else if (index == R.styleable.ViewTransition_SharedValue) {
                this.mSharedValueTarget = typedArrayObtainStyledAttributes.getInteger(index, this.mSharedValueTarget);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void updateTransition(MotionScene.Transition transition, View view) {
        int i = this.mDuration;
        if (i != -1) {
            transition.setDuration(i);
        }
        transition.setPathMotionArc(this.mPathMotionArc);
        transition.setInterpolatorInfo(this.mDefaultInterpolator, this.mDefaultInterpolatorString, this.mDefaultInterpolatorID);
        int id = view.getId();
        KeyFrames keyFrames = this.mKeyFrames;
        if (keyFrames != null) {
            ArrayList<Key> keyFramesForView = keyFrames.getKeyFramesForView(-1);
            KeyFrames keyFrames2 = new KeyFrames();
            Iterator<Key> it = keyFramesForView.iterator();
            while (it.hasNext()) {
                keyFrames2.addKey(it.next().mo7clone().setViewId(id));
            }
            transition.addKeyFrame(keyFrames2);
        }
    }

    public final void applyTransition(ViewTransitionController controller, MotionLayout layout, int fromId, ConstraintSet current, View... views) {
        Interpolator interpolatorLoadInterpolator;
        Interpolator interpolator;
        if (this.mDisabled) {
            return;
        }
        int i = this.mViewTransitionMode;
        int i2 = 1;
        if (i == 2) {
            View view = views[0];
            MotionController motionController = new MotionController(view);
            motionController.setBothStates(view);
            this.mKeyFrames.addAllFrames(motionController);
            motionController.setup(layout.getWidth(), layout.getHeight(), this.mDuration, System.nanoTime());
            int i3 = this.mDuration;
            int i4 = this.mUpDuration;
            int i5 = this.mOnStateTransition;
            Context context = layout.getContext();
            int i6 = this.mDefaultInterpolator;
            if (i6 != -2) {
                if (i6 == -1) {
                    final Easing interpolator2 = Easing.getInterpolator(this.mDefaultInterpolatorString);
                    interpolator = new Interpolator() { // from class: androidx.constraintlayout.motion.widget.ViewTransition.1
                        @Override // android.animation.TimeInterpolator
                        public float getInterpolation(float v) {
                            return (float) interpolator2.get(v);
                        }
                    };
                } else if (i6 == 0) {
                    interpolatorLoadInterpolator = new AccelerateDecelerateInterpolator();
                } else if (i6 == 1) {
                    interpolatorLoadInterpolator = new AccelerateInterpolator();
                } else if (i6 == 2) {
                    interpolatorLoadInterpolator = new DecelerateInterpolator();
                } else if (i6 == 4) {
                    interpolatorLoadInterpolator = new BounceInterpolator();
                } else if (i6 != 5) {
                    interpolatorLoadInterpolator = i6 != 6 ? null : new AnticipateInterpolator();
                } else {
                    interpolatorLoadInterpolator = new OvershootInterpolator();
                }
                new Animate(controller, motionController, i3, i4, i5, interpolator, this.mSetsTag, this.mClearsTag);
                return;
            }
            interpolatorLoadInterpolator = AnimationUtils.loadInterpolator(context, this.mDefaultInterpolatorID);
            interpolator = interpolatorLoadInterpolator;
            new Animate(controller, motionController, i3, i4, i5, interpolator, this.mSetsTag, this.mClearsTag);
            return;
        }
        if (i == 1) {
            for (int i7 : layout.getConstraintSetIds()) {
                if (i7 != fromId) {
                    ConstraintSet constraintSet = layout.getConstraintSet(i7);
                    for (View view2 : views) {
                        ConstraintSet.Constraint constraint = constraintSet.getConstraint(view2.getId());
                        ConstraintSet.Constraint constraint2 = this.mConstraintDelta;
                        if (constraint2 != null) {
                            constraint2.applyDelta(constraint);
                            constraint.mCustomConstraints.putAll(this.mConstraintDelta.mCustomConstraints);
                        }
                    }
                }
            }
        }
        ConstraintSet constraintSet2 = new ConstraintSet();
        constraintSet2.clone(current);
        for (View view3 : views) {
            ConstraintSet.Constraint constraint3 = constraintSet2.getConstraint(view3.getId());
            ConstraintSet.Constraint constraint4 = this.mConstraintDelta;
            if (constraint4 != null) {
                constraint4.applyDelta(constraint3);
                constraint3.mCustomConstraints.putAll(this.mConstraintDelta.mCustomConstraints);
            }
        }
        layout.updateState(fromId, constraintSet2);
        int i8 = R.id.view_transition;
        layout.updateState(i8, current);
        layout.setState(i8, -1, -1);
        MotionScene.Transition transition = new MotionScene.Transition(-1, layout.mScene, i8, fromId);
        for (View view4 : views) {
            updateTransition(transition, view4);
        }
        layout.setTransition(transition);
        layout.transitionToEnd(new Util$$ExternalSyntheticLambda0(this, views, i2));
    }

    public final boolean checkTags(View view) {
        int i = this.mIfTagSet;
        boolean z = i == -1 || view.getTag(i) != null;
        int i2 = this.mIfTagNotSet;
        return z && (i2 == -1 || view.getTag(i2) == null);
    }

    public final int getId() {
        return this.mId;
    }

    public int getSharedValue() {
        return this.mSharedValueTarget;
    }

    public int getSharedValueCurrent() {
        return this.mSharedValueCurrent;
    }

    public int getSharedValueID() {
        return this.mSharedValueID;
    }

    public int getStateTransition() {
        return this.mOnStateTransition;
    }

    public final boolean isEnabled() {
        return !this.mDisabled;
    }

    public final boolean matchesView(View view) {
        String str;
        if (view == null) {
            return false;
        }
        if ((this.mTargetId == -1 && this.mTargetString == null) || !checkTags(view)) {
            return false;
        }
        if (view.getId() == this.mTargetId) {
            return true;
        }
        return this.mTargetString != null && (view.getLayoutParams() instanceof ConstraintLayout.LayoutParams) && (str = ((ConstraintLayout.LayoutParams) view.getLayoutParams()).constraintTag) != null && str.matches(this.mTargetString);
    }

    public final void setEnabled(boolean enable) {
        this.mDisabled = !enable;
    }

    public void setSharedValue(int sharedValue) {
        this.mSharedValueTarget = sharedValue;
    }

    public void setSharedValueCurrent(int sharedValueCurrent) {
        this.mSharedValueCurrent = sharedValueCurrent;
    }

    public void setSharedValueID(int sharedValueID) {
        this.mSharedValueID = sharedValueID;
    }

    public void setStateTransition(int stateTransition) {
        this.mOnStateTransition = stateTransition;
    }

    public final boolean supports(int action) {
        int i = this.mOnStateTransition;
        if (i == 1) {
            return action == 0;
        }
        if (i == 2) {
            return action == 1;
        }
        return i == 3 && action == 0;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("ViewTransition(");
        sbM.append(Debug.getName(this.mContext, this.mId));
        sbM.append(")");
        return sbM.toString();
    }
}
