package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.Motion;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.key.MotionKeyAttributes;
import androidx.constraintlayout.core.motion.key.MotionKeyCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import com.nettv.livestore.activities.MovieActivity$$ExternalSyntheticLambda2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class Transition implements TypedValues {
    public static final int END = 1;
    public static final int INTERPOLATED = 2;
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    private static final int SPLINE_STRING = -1;
    public static final int START = 0;
    public HashMap<Integer, HashMap<String, KeyPosition>> keyPositions = new HashMap<>();
    private HashMap<String, WidgetState> state = new HashMap<>();
    public TypedBundle mBundle = new TypedBundle();
    private int mDefaultInterpolator = 0;
    private String mDefaultInterpolatorString = null;
    private Easing mEasing = null;
    private int mAutoTransition = 0;
    private int mDuration = 400;
    private float mStagger = 0.0f;

    public static class KeyPosition {
        public int frame;
        public float x;
        public float y;

        public KeyPosition(String str, int i, int i2, float f, float f2) {
            this.frame = i;
            this.x = f;
            this.y = f2;
        }
    }

    public static class WidgetState {
        public Motion motionControl;
        public KeyCache myKeyCache = new KeyCache();
        public WidgetFrame start = new WidgetFrame();
        public WidgetFrame end = new WidgetFrame();
        public WidgetFrame interpolated = new WidgetFrame();
        public MotionWidget motionWidgetStart = new MotionWidget(this.start);
        public MotionWidget motionWidgetEnd = new MotionWidget(this.end);
        public MotionWidget motionWidgetInterpolated = new MotionWidget(this.interpolated);

        public WidgetState() {
            Motion motion = new Motion(this.motionWidgetStart);
            this.motionControl = motion;
            motion.setStart(this.motionWidgetStart);
            this.motionControl.setEnd(this.motionWidgetEnd);
        }

        public WidgetFrame getFrame(int i) {
            if (i == 0) {
                return this.start;
            }
            return i == 1 ? this.end : this.interpolated;
        }

        public void interpolate(int i, int i2, float f, Transition transition) {
            this.motionControl.setup(i, i2, 1.0f, System.nanoTime());
            WidgetFrame.interpolate(i, i2, this.interpolated, this.start, this.end, transition, f);
            this.interpolated.interpolatedPos = f;
            this.motionControl.interpolate(this.motionWidgetInterpolated, f, System.nanoTime(), this.myKeyCache);
        }

        public void setKeyAttribute(TypedBundle typedBundle) {
            MotionKeyAttributes motionKeyAttributes = new MotionKeyAttributes();
            typedBundle.applyDelta(motionKeyAttributes);
            this.motionControl.addKey(motionKeyAttributes);
        }

        public void setKeyCycle(TypedBundle typedBundle) {
            MotionKeyCycle motionKeyCycle = new MotionKeyCycle();
            typedBundle.applyDelta(motionKeyCycle);
            this.motionControl.addKey(motionKeyCycle);
        }

        public void setKeyPosition(TypedBundle typedBundle) {
            MotionKeyPosition motionKeyPosition = new MotionKeyPosition();
            typedBundle.applyDelta(motionKeyPosition);
            this.motionControl.addKey(motionKeyPosition);
        }

        public void update(ConstraintWidget constraintWidget, int i) {
            if (i == 0) {
                this.start.update(constraintWidget);
                this.motionControl.setStart(this.motionWidgetStart);
            } else if (i == 1) {
                this.end.update(constraintWidget);
                this.motionControl.setEnd(this.motionWidgetEnd);
            }
        }
    }

    public static Interpolator getInterpolator(int i, String str) {
        switch (i) {
            case -1:
                return new MovieActivity$$ExternalSyntheticLambda2(str, 1);
            case 0:
                return Transition$$ExternalSyntheticLambda0.INSTANCE;
            case 1:
                return Transition$$ExternalSyntheticLambda0.INSTANCE$1;
            case 2:
                return Transition$$ExternalSyntheticLambda0.INSTANCE$2;
            case 3:
                return Transition$$ExternalSyntheticLambda0.INSTANCE$3;
            case 4:
                return Transition$$ExternalSyntheticLambda0.INSTANCE$6;
            case 5:
                return Transition$$ExternalSyntheticLambda0.INSTANCE$5;
            case 6:
                return Transition$$ExternalSyntheticLambda0.INSTANCE$4;
            default:
                return null;
        }
    }

    private WidgetState getWidgetState(String str) {
        return this.state.get(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$0(String str, float f) {
        return (float) Easing.getInterpolator(str).get(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$1(float f) {
        return (float) Easing.getInterpolator("standard").get(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$2(float f) {
        return (float) Easing.getInterpolator("accelerate").get(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$3(float f) {
        return (float) Easing.getInterpolator("decelerate").get(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$4(float f) {
        return (float) Easing.getInterpolator("linear").get(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$5(float f) {
        return (float) Easing.getInterpolator("anticipate").get(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$6(float f) {
        return (float) Easing.getInterpolator("overshoot").get(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$7(float f) {
        return (float) Easing.getInterpolator("spline(0.0, 0.2, 0.4, 0.6, 0.8 ,1.0, 0.8, 1.0, 0.9, 1.0)").get(f);
    }

    public void addCustomColor(int i, String str, String str2, int i2) {
        getWidgetState(str, null, i).getFrame(i).addCustomColor(str2, i2);
    }

    public void addCustomFloat(int i, String str, String str2, float f) {
        getWidgetState(str, null, i).getFrame(i).addCustomFloat(str2, f);
    }

    public void addKeyAttribute(String str, TypedBundle typedBundle) {
        getWidgetState(str, null, 0).setKeyAttribute(typedBundle);
    }

    public void addKeyCycle(String str, TypedBundle typedBundle) {
        getWidgetState(str, null, 0).setKeyCycle(typedBundle);
    }

    public void addKeyPosition(String str, TypedBundle typedBundle) {
        getWidgetState(str, null, 0).setKeyPosition(typedBundle);
    }

    public void clear() {
        this.state.clear();
    }

    public boolean contains(String str) {
        return this.state.containsKey(str);
    }

    public void fillKeyPositions(WidgetFrame widgetFrame, float[] fArr, float[] fArr2, float[] fArr3) {
        KeyPosition keyPosition;
        int i = 0;
        for (int i2 = 0; i2 <= 100; i2++) {
            HashMap<String, KeyPosition> map = this.keyPositions.get(Integer.valueOf(i2));
            if (map != null && (keyPosition = map.get(widgetFrame.widget.stringId)) != null) {
                fArr[i] = keyPosition.x;
                fArr2[i] = keyPosition.y;
                fArr3[i] = keyPosition.frame;
                i++;
            }
        }
    }

    public KeyPosition findNextPosition(String str, int i) {
        KeyPosition keyPosition;
        while (i <= 100) {
            HashMap<String, KeyPosition> map = this.keyPositions.get(Integer.valueOf(i));
            if (map != null && (keyPosition = map.get(str)) != null) {
                return keyPosition;
            }
            i++;
        }
        return null;
    }

    public KeyPosition findPreviousPosition(String str, int i) {
        KeyPosition keyPosition;
        while (i >= 0) {
            HashMap<String, KeyPosition> map = this.keyPositions.get(Integer.valueOf(i));
            if (map != null && (keyPosition = map.get(str)) != null) {
                return keyPosition;
            }
            i--;
        }
        return null;
    }

    public int getAutoTransition() {
        return this.mAutoTransition;
    }

    public WidgetFrame getEnd(String str) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            return null;
        }
        return widgetState.end;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String str) {
        return 0;
    }

    public WidgetFrame getInterpolated(String str) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            return null;
        }
        return widgetState.interpolated;
    }

    public int getKeyFrames(String str, float[] fArr, int[] iArr, int[] iArr2) {
        return this.state.get(str).motionControl.buildKeyFrames(fArr, iArr, iArr2);
    }

    public Motion getMotion(String str) {
        return getWidgetState(str, null, 0).motionControl;
    }

    public int getNumberKeyPositions(WidgetFrame widgetFrame) {
        int i = 0;
        for (int i2 = 0; i2 <= 100; i2++) {
            HashMap<String, KeyPosition> map = this.keyPositions.get(Integer.valueOf(i2));
            if (map != null && map.get(widgetFrame.widget.stringId) != null) {
                i++;
            }
        }
        return i;
    }

    public float[] getPath(String str) {
        float[] fArr = new float[124];
        this.state.get(str).motionControl.buildPath(fArr, 62);
        return fArr;
    }

    public WidgetFrame getStart(String str) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            return null;
        }
        return widgetState.start;
    }

    public boolean hasPositionKeyframes() {
        return this.keyPositions.size() > 0;
    }

    public void interpolate(int i, int i2, float f) {
        Easing easing = this.mEasing;
        if (easing != null) {
            f = (float) easing.get(f);
        }
        Iterator<String> it = this.state.keySet().iterator();
        while (it.hasNext()) {
            this.state.get(it.next()).interpolate(i, i2, f, this);
        }
    }

    public boolean isEmpty() {
        return this.state.isEmpty();
    }

    public void setTransitionProperties(TypedBundle typedBundle) {
        typedBundle.applyDelta(this.mBundle);
        typedBundle.applyDelta(this);
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, float f) {
        if (i != 706) {
            return false;
        }
        this.mStagger = f;
        return false;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, int i2) {
        return false;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, boolean z) {
        return false;
    }

    public void updateFrom(ConstraintWidgetContainer constraintWidgetContainer, int i) {
        ArrayList<ConstraintWidget> children = constraintWidgetContainer.getChildren();
        int size = children.size();
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = children.get(i2);
            getWidgetState(constraintWidget.stringId, null, i).update(constraintWidget, i);
        }
    }

    private WidgetState getWidgetState(String str, ConstraintWidget constraintWidget, int i) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            widgetState = new WidgetState();
            this.mBundle.applyDelta(widgetState.motionControl);
            this.state.put(str, widgetState);
            if (constraintWidget != null) {
                widgetState.update(constraintWidget, i);
            }
        }
        return widgetState;
    }

    public void addKeyPosition(String str, int i, int i2, float f, float f2) {
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.add(TypedValues.PositionType.TYPE_POSITION_TYPE, 2);
        typedBundle.add(100, i);
        typedBundle.add(TypedValues.PositionType.TYPE_PERCENT_X, f);
        typedBundle.add(TypedValues.PositionType.TYPE_PERCENT_Y, f2);
        getWidgetState(str, null, 0).setKeyPosition(typedBundle);
        KeyPosition keyPosition = new KeyPosition(str, i, i2, f, f2);
        HashMap<String, KeyPosition> map = this.keyPositions.get(Integer.valueOf(i));
        if (map == null) {
            map = new HashMap<>();
            this.keyPositions.put(Integer.valueOf(i), map);
        }
        map.put(str, keyPosition);
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, String str) {
        if (i != 705) {
            return false;
        }
        this.mDefaultInterpolatorString = str;
        this.mEasing = Easing.getInterpolator(str);
        return false;
    }

    public WidgetFrame getEnd(ConstraintWidget constraintWidget) {
        return getWidgetState(constraintWidget.stringId, null, 1).end;
    }

    public WidgetFrame getInterpolated(ConstraintWidget constraintWidget) {
        return getWidgetState(constraintWidget.stringId, null, 2).interpolated;
    }

    public WidgetFrame getStart(ConstraintWidget constraintWidget) {
        return getWidgetState(constraintWidget.stringId, null, 0).start;
    }

    public Interpolator getInterpolator() {
        return getInterpolator(this.mDefaultInterpolator, this.mDefaultInterpolatorString);
    }
}
