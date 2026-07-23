package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.base.Ascii;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class MotionKeyCycle extends MotionKey {
    public static final int KEY_TYPE = 4;
    public static final int SHAPE_BOUNCE = 6;
    public static final int SHAPE_COS_WAVE = 5;
    public static final int SHAPE_REVERSE_SAW_WAVE = 4;
    public static final int SHAPE_SAW_WAVE = 3;
    public static final int SHAPE_SIN_WAVE = 0;
    public static final int SHAPE_SQUARE_WAVE = 1;
    public static final int SHAPE_TRIANGLE_WAVE = 2;
    private static final String TAG = "KeyCycle";
    public static final String WAVE_OFFSET = "waveOffset";
    public static final String WAVE_PERIOD = "wavePeriod";
    public static final String WAVE_PHASE = "wavePhase";
    public static final String WAVE_SHAPE = "waveShape";
    private String mTransitionEasing = null;
    private int mCurveFit = 0;
    private int mWaveShape = -1;
    private String mCustomWaveShape = null;
    private float mWavePeriod = Float.NaN;
    private float mWaveOffset = 0.0f;
    private float mWavePhase = 0.0f;
    private float mProgress = Float.NaN;
    private float mAlpha = Float.NaN;
    private float mElevation = Float.NaN;
    private float mRotation = Float.NaN;
    private float mTransitionPathRotate = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;

    public MotionKeyCycle() {
        this.mType = 4;
        this.mCustom = new HashMap<>();
    }

    public void addCycleValues(HashMap<String, KeyCycleOscillator> map) {
        KeyCycleOscillator keyCycleOscillator;
        KeyCycleOscillator keyCycleOscillator2;
        for (String str : map.keySet()) {
            if (str.startsWith("CUSTOM")) {
                CustomVariable customVariable = this.mCustom.get(str.substring(7));
                if (customVariable != null && customVariable.getType() == 901 && (keyCycleOscillator = map.get(str)) != null) {
                    keyCycleOscillator.setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, -1, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, customVariable.getValueToInterpolate(), customVariable);
                }
            } else {
                float value = getValue(str);
                if (!Float.isNaN(value) && (keyCycleOscillator2 = map.get(str)) != null) {
                    keyCycleOscillator2.setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, -1, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, value);
                }
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void addValues(HashMap<String, SplineSet> map) {
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    /* JADX INFO: renamed from: clone */
    public MotionKey mo4clone() {
        return null;
    }

    public void dump() {
        PrintStream printStream = System.out;
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("MotionKeyCycle{mWaveShape=");
        sbM.append(this.mWaveShape);
        sbM.append(", mWavePeriod=");
        sbM.append(this.mWavePeriod);
        sbM.append(", mWaveOffset=");
        sbM.append(this.mWaveOffset);
        sbM.append(", mWavePhase=");
        sbM.append(this.mWavePhase);
        sbM.append(", mRotation=");
        sbM.append(this.mRotation);
        sbM.append('}');
        printStream.println(sbM.toString());
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void getAttributeNames(HashSet<String> hashSet) {
        if (!Float.isNaN(this.mAlpha)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.mElevation)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.mRotation)) {
            hashSet.add("rotationZ");
        }
        if (!Float.isNaN(this.mRotationX)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.mRotationY)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.mScaleX)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.mScaleY)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            hashSet.add("pathRotate");
        }
        if (!Float.isNaN(this.mTranslationX)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.mTranslationY)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            hashSet.add("translationZ");
        }
        if (this.mCustom.size() > 0) {
            Iterator<String> it = this.mCustom.keySet().iterator();
            while (it.hasNext()) {
                hashSet.add("CUSTOM," + it.next());
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:4:0x000b  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String str) {
        byte b;
        Objects.requireNonNull(str);
        switch (str.hashCode()) {
            case -1581616630:
                if (!str.equals(TypedValues.CycleType.S_CUSTOM_WAVE_SHAPE)) {
                    b = -1;
                } else {
                    b = 0;
                }
                break;
            case -1310311125:
                if (!str.equals("easing")) {
                    b = -1;
                } else {
                    b = 1;
                }
                break;
            case -1249320806:
                if (!str.equals("rotationX")) {
                    b = -1;
                } else {
                    b = 2;
                }
                break;
            case -1249320805:
                if (!str.equals("rotationY")) {
                    b = -1;
                } else {
                    b = 3;
                }
                break;
            case -1249320804:
                if (!str.equals("rotationZ")) {
                    b = -1;
                } else {
                    b = 4;
                }
                break;
            case -1225497657:
                if (!str.equals("translationX")) {
                    b = -1;
                } else {
                    b = 5;
                }
                break;
            case -1225497656:
                if (!str.equals("translationY")) {
                    b = -1;
                } else {
                    b = 6;
                }
                break;
            case -1225497655:
                if (!str.equals("translationZ")) {
                    b = -1;
                } else {
                    b = 7;
                }
                break;
            case -1019779949:
                if (!str.equals(TypedValues.CycleType.S_WAVE_OFFSET)) {
                    b = -1;
                } else {
                    b = 8;
                }
                break;
            case -1001078227:
                if (!str.equals("progress")) {
                    b = -1;
                } else {
                    b = 9;
                }
                break;
            case -991726143:
                if (!str.equals(TypedValues.CycleType.S_WAVE_PERIOD)) {
                    b = -1;
                } else {
                    b = 10;
                }
                break;
            case -987906986:
                if (!str.equals("pivotX")) {
                    b = -1;
                } else {
                    b = 11;
                }
                break;
            case -987906985:
                if (!str.equals("pivotY")) {
                    b = -1;
                } else {
                    b = 12;
                }
                break;
            case -908189618:
                if (!str.equals("scaleX")) {
                    b = -1;
                } else {
                    b = 13;
                }
                break;
            case -908189617:
                if (!str.equals("scaleY")) {
                    b = -1;
                } else {
                    b = 14;
                }
                break;
            case 92909918:
                if (!str.equals("alpha")) {
                    b = -1;
                } else {
                    b = 15;
                }
                break;
            case 106629499:
                if (!str.equals(TypedValues.CycleType.S_WAVE_PHASE)) {
                    b = -1;
                } else {
                    b = 16;
                }
                break;
            case 579057826:
                if (!str.equals("curveFit")) {
                    b = -1;
                } else {
                    b = 17;
                }
                break;
            case 803192288:
                if (!str.equals("pathRotate")) {
                    b = -1;
                } else {
                    b = 18;
                }
                break;
            case 1532805160:
                if (!str.equals("waveShape")) {
                    b = -1;
                } else {
                    b = 19;
                }
                break;
            case 1941332754:
                if (!str.equals("visibility")) {
                    b = -1;
                } else {
                    b = Ascii.DC4;
                }
                break;
            default:
                b = -1;
                break;
        }
        switch (b) {
            case 0:
                return TypedValues.CycleType.TYPE_CUSTOM_WAVE_SHAPE;
            case 1:
                return TypedValues.CycleType.TYPE_EASING;
            case 2:
                return 308;
            case 3:
                return 309;
            case 4:
                return 310;
            case 5:
                return 304;
            case 6:
                return 305;
            case 7:
                return 306;
            case 8:
                return TypedValues.CycleType.TYPE_WAVE_OFFSET;
            case 9:
                return 315;
            case 10:
                return TypedValues.CycleType.TYPE_WAVE_PERIOD;
            case 11:
                return 313;
            case 12:
                return 314;
            case 13:
                return 311;
            case 14:
                return 312;
            case 15:
                return TypedValues.CycleType.TYPE_ALPHA;
            case 16:
                return TypedValues.CycleType.TYPE_WAVE_PHASE;
            case 17:
                return TypedValues.CycleType.TYPE_CURVE_FIT;
            case 18:
                return TypedValues.CycleType.TYPE_PATH_ROTATE;
            case 19:
                return 421;
            case 20:
                return TypedValues.CycleType.TYPE_VISIBILITY;
            default:
                return -1;
        }
    }

    public float getValue(String str) {
        Objects.requireNonNull(str);
        switch (str) {
            case "rotationX":
                return this.mRotationX;
            case "rotationY":
                return this.mRotationY;
            case "rotationZ":
                return this.mRotation;
            case "translationX":
                return this.mTranslationX;
            case "translationY":
                return this.mTranslationY;
            case "translationZ":
                return this.mTranslationZ;
            case "offset":
                return this.mWaveOffset;
            case "progress":
                return this.mProgress;
            case "scaleX":
                return this.mScaleX;
            case "scaleY":
                return this.mScaleY;
            case "elevation":
                return this.mElevation;
            case "alpha":
                return this.mAlpha;
            case "phase":
                return this.mWavePhase;
            case "pathRotate":
                return this.mTransitionPathRotate;
            default:
                return Float.NaN;
        }
    }

    public void printAttributes() {
        HashSet<String> hashSet = new HashSet<>();
        getAttributeNames(hashSet);
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(" ------------- ");
        sbM.append(this.mFramePosition);
        sbM.append(" -------------");
        Utils.log(sbM.toString());
        Utils.log("MotionKeyCycle{Shape=" + this.mWaveShape + ", Period=" + this.mWavePeriod + ", Offset=" + this.mWaveOffset + ", Phase=" + this.mWavePhase + '}');
        String[] strArr = (String[]) hashSet.toArray(new String[0]);
        for (int i = 0; i < strArr.length; i++) {
            TypedValues.AttributesType.CC.getId(strArr[i]);
            Utils.log(strArr[i] + ":" + getValue(strArr[i]));
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, int i2) {
        if (i == 401) {
            this.mCurveFit = i2;
            return true;
        }
        if (i == 421) {
            this.mWaveShape = i2;
            return true;
        }
        if (setValue(i, i2)) {
            return true;
        }
        return super.setValue(i, i2);
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, String str) {
        if (i == 420) {
            this.mTransitionEasing = str;
            return true;
        }
        if (i != 422) {
            return super.setValue(i, str);
        }
        this.mCustomWaveShape = str;
        return true;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, float f) {
        if (i == 315) {
            this.mProgress = f;
            return true;
        }
        if (i == 403) {
            this.mAlpha = f;
            return true;
        }
        if (i != 416) {
            switch (i) {
                case 304:
                    this.mTranslationX = f;
                    return true;
                case 305:
                    this.mTranslationY = f;
                    return true;
                case 306:
                    this.mTranslationZ = f;
                    return true;
                case 307:
                    this.mElevation = f;
                    return true;
                case 308:
                    this.mRotationX = f;
                    return true;
                case 309:
                    this.mRotationY = f;
                    return true;
                case 310:
                    this.mRotation = f;
                    return true;
                case 311:
                    this.mScaleX = f;
                    return true;
                case 312:
                    this.mScaleY = f;
                    return true;
                default:
                    switch (i) {
                        case TypedValues.CycleType.TYPE_WAVE_PERIOD /* 423 */:
                            this.mWavePeriod = f;
                            return true;
                        case TypedValues.CycleType.TYPE_WAVE_OFFSET /* 424 */:
                            this.mWaveOffset = f;
                            return true;
                        case TypedValues.CycleType.TYPE_WAVE_PHASE /* 425 */:
                            this.mWavePhase = f;
                            return true;
                        default:
                            return super.setValue(i, f);
                    }
            }
        }
        this.mTransitionPathRotate = f;
        return true;
    }
}
