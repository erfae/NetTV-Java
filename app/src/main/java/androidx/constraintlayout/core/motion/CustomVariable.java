package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes.dex */
public class CustomVariable {
    private static final String TAG = "TransitionLayout";
    public boolean mBooleanValue;
    private float mFloatValue;
    private int mIntegerValue;
    public String mName;
    private String mStringValue;
    private int mType;

    public CustomVariable(CustomVariable customVariable) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = customVariable.mName;
        this.mType = customVariable.mType;
        this.mIntegerValue = customVariable.mIntegerValue;
        this.mFloatValue = customVariable.mFloatValue;
        this.mStringValue = customVariable.mStringValue;
        this.mBooleanValue = customVariable.mBooleanValue;
    }

    private static int clamp(int i) {
        int i2 = (i & (~(i >> 31))) - 255;
        return (i2 & (i2 >> 31)) + 255;
    }

    public static String colorString(int i) {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("00000000");
        sbM.append(Integer.toHexString(i));
        String string = sbM.toString();
        StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("#");
        sbM2.append(string.substring(string.length() - 8));
        return sbM2.toString();
    }

    public static int hsvToRgb(float f, float f2, float f3) {
        float f4 = f * 6.0f;
        int i = (int) f4;
        float f5 = f4 - i;
        float f6 = f3 * 255.0f;
        int iM = (int) Insets$$ExternalSyntheticOutline0.m(1.0f, f2, f6, 0.5f);
        int i2 = (int) (((1.0f - (f5 * f2)) * f6) + 0.5f);
        int i3 = (int) (((1.0f - ((1.0f - f5) * f2)) * f6) + 0.5f);
        int i4 = (int) (f6 + 0.5f);
        if (i == 0) {
            return ((i4 << 16) + (i3 << 8) + iM) | (-16777216);
        }
        if (i == 1) {
            return ((i2 << 16) + (i4 << 8) + iM) | (-16777216);
        }
        if (i == 2) {
            return ((iM << 16) + (i4 << 8) + i3) | (-16777216);
        }
        if (i == 3) {
            return ((iM << 16) + (i2 << 8) + i4) | (-16777216);
        }
        if (i == 4) {
            return ((i3 << 16) + (iM << 8) + i4) | (-16777216);
        }
        if (i != 5) {
            return 0;
        }
        return ((i4 << 16) + (iM << 8) + i2) | (-16777216);
    }

    public static int rgbaTocColor(float f, float f2, float f3, float f4) {
        int iClamp = clamp((int) (f * 255.0f));
        int iClamp2 = clamp((int) (f2 * 255.0f));
        return (iClamp << 16) | (clamp((int) (f4 * 255.0f)) << 24) | (iClamp2 << 8) | clamp((int) (f3 * 255.0f));
    }

    public void applyToWidget(MotionWidget motionWidget) {
        int i = this.mType;
        switch (i) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
            case TypedValues.Custom.TYPE_REFERENCE /* 906 */:
                motionWidget.setCustomAttribute(this.mName, i, this.mIntegerValue);
                break;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                motionWidget.setCustomAttribute(this.mName, i, this.mFloatValue);
                break;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                motionWidget.setCustomAttribute(this.mName, i, this.mStringValue);
                break;
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                motionWidget.setCustomAttribute(this.mName, i, this.mBooleanValue);
                break;
        }
    }

    public CustomVariable copy() {
        return new CustomVariable(this);
    }

    public boolean diff(CustomVariable customVariable) {
        int i;
        if (customVariable == null || (i = this.mType) != customVariable.mType) {
            return false;
        }
        switch (i) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
            case TypedValues.Custom.TYPE_REFERENCE /* 906 */:
                return this.mIntegerValue == customVariable.mIntegerValue;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                return this.mFloatValue == customVariable.mFloatValue;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                return this.mIntegerValue == customVariable.mIntegerValue;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                return this.mIntegerValue == customVariable.mIntegerValue;
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                return this.mBooleanValue == customVariable.mBooleanValue;
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                return this.mFloatValue == customVariable.mFloatValue;
            default:
                return false;
        }
    }

    public boolean getBooleanValue() {
        return this.mBooleanValue;
    }

    public int getColorValue() {
        return this.mIntegerValue;
    }

    public float getFloatValue() {
        return this.mFloatValue;
    }

    public int getIntegerValue() {
        return this.mIntegerValue;
    }

    public int getInterpolatedColor(float[] fArr) {
        return (clamp((int) (fArr[3] * 255.0f)) << 24) | (clamp((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f)) << 8) | clamp((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f));
    }

    public String getName() {
        return this.mName;
    }

    public String getStringValue() {
        return this.mStringValue;
    }

    public int getType() {
        return this.mType;
    }

    public float getValueToInterpolate() {
        switch (this.mType) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
                return this.mIntegerValue;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                return this.mFloatValue;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                throw new RuntimeException("Color does not have a single color to interpolate");
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                throw new RuntimeException("Cannot interpolate String");
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                return this.mBooleanValue ? 1.0f : 0.0f;
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                return this.mFloatValue;
            default:
                return Float.NaN;
        }
    }

    public void getValuesToInterpolate(float[] fArr) {
        switch (this.mType) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
                fArr[0] = this.mIntegerValue;
                return;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                fArr[0] = this.mFloatValue;
                return;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                int i = this.mIntegerValue;
                int i2 = (i >> 24) & 255;
                float fPow = (float) Math.pow(((i >> 16) & 255) / 255.0f, 2.2d);
                float fPow2 = (float) Math.pow(((i >> 8) & 255) / 255.0f, 2.2d);
                float fPow3 = (float) Math.pow((i & 255) / 255.0f, 2.2d);
                fArr[0] = fPow;
                fArr[1] = fPow2;
                fArr[2] = fPow3;
                fArr[3] = i2 / 255.0f;
                return;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                throw new RuntimeException("Cannot interpolate String");
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                fArr[0] = this.mBooleanValue ? 1.0f : 0.0f;
                return;
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                fArr[0] = this.mFloatValue;
                return;
            default:
                return;
        }
    }

    public boolean isContinuous() {
        int i = this.mType;
        return (i == 903 || i == 904 || i == 906) ? false : true;
    }

    public int numberOfInterpolatedValues() {
        return this.mType != 902 ? 1 : 4;
    }

    public void setBooleanValue(boolean z) {
        this.mBooleanValue = z;
    }

    public void setFloatValue(float f) {
        this.mFloatValue = f;
    }

    public void setIntValue(int i) {
        this.mIntegerValue = i;
    }

    public void setInterpolatedValue(MotionWidget motionWidget, float[] fArr) {
        int i = this.mType;
        switch (i) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
                motionWidget.setCustomAttribute(this.mName, i, (int) fArr[0]);
                return;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                motionWidget.setCustomAttribute(this.mName, i, fArr[0]);
                return;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                motionWidget.setCustomAttribute(this.mName, this.mType, (clamp((int) (fArr[3] * 255.0f)) << 24) | (clamp((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f)) << 8) | clamp((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f)));
                return;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
            case TypedValues.Custom.TYPE_REFERENCE /* 906 */:
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("unable to interpolate ");
                sbM.append(this.mName);
                throw new RuntimeException(sbM.toString());
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                motionWidget.setCustomAttribute(this.mName, i, fArr[0] > 0.5f);
                return;
            default:
                return;
        }
    }

    public void setStringValue(String str) {
        this.mStringValue = str;
    }

    public void setValue(float[] fArr) {
        switch (this.mType) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
            case TypedValues.Custom.TYPE_REFERENCE /* 906 */:
                this.mIntegerValue = (int) fArr[0];
                return;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                this.mFloatValue = fArr[0];
                return;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                this.mIntegerValue = ((Math.round(fArr[3] * 255.0f) & 255) << 24) | ((Math.round(((float) Math.pow(fArr[0], 0.5d)) * 255.0f) & 255) << 16) | ((Math.round(((float) Math.pow(fArr[1], 0.5d)) * 255.0f) & 255) << 8) | (Math.round(((float) Math.pow(fArr[2], 0.5d)) * 255.0f) & 255);
                return;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                throw new RuntimeException("Cannot interpolate String");
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                this.mBooleanValue = ((double) fArr[0]) > 0.5d;
                return;
            default:
                return;
        }
    }

    public String toString() {
        String str = this.mName + ':';
        switch (this.mType) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(str);
                sbM.append(this.mIntegerValue);
                return sbM.toString();
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m(str);
                sbM2.append(this.mFloatValue);
                return sbM2.toString();
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m(str);
                sbM3.append(colorString(this.mIntegerValue));
                return sbM3.toString();
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                StringBuilder sbM4 = Insets$$ExternalSyntheticOutline0.m(str);
                sbM4.append(this.mStringValue);
                return sbM4.toString();
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                StringBuilder sbM5 = Insets$$ExternalSyntheticOutline0.m(str);
                sbM5.append(Boolean.valueOf(this.mBooleanValue));
                return sbM5.toString();
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                StringBuilder sbM6 = Insets$$ExternalSyntheticOutline0.m(str);
                sbM6.append(this.mFloatValue);
                return sbM6.toString();
            default:
                return Insets$$ExternalSyntheticOutline0.m(str, "????");
        }
    }

    public CustomVariable(String str, int i, String str2) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mName = str;
        this.mType = i;
        this.mStringValue = str2;
    }

    public void setValue(Object obj) {
        switch (this.mType) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
            case TypedValues.Custom.TYPE_REFERENCE /* 906 */:
                this.mIntegerValue = ((Integer) obj).intValue();
                break;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                this.mFloatValue = ((Float) obj).floatValue();
                break;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                this.mIntegerValue = ((Integer) obj).intValue();
                break;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                this.mStringValue = (String) obj;
                break;
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                this.mBooleanValue = ((Boolean) obj).booleanValue();
                break;
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                this.mFloatValue = ((Float) obj).floatValue();
                break;
        }
    }

    public CustomVariable(String str, int i, int i2) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = str;
        this.mType = i;
        if (i == 901) {
            this.mFloatValue = i2;
        } else {
            this.mIntegerValue = i2;
        }
    }

    public CustomVariable(String str, int i, float f) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mStringValue = null;
        this.mName = str;
        this.mType = i;
        this.mFloatValue = f;
    }

    public CustomVariable(String str, int i, boolean z) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = str;
        this.mType = i;
        this.mBooleanValue = z;
    }

    public CustomVariable(String str, int i) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = str;
        this.mType = i;
    }

    public CustomVariable(String str, int i, Object obj) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = str;
        this.mType = i;
        setValue(obj);
    }

    public CustomVariable(CustomVariable customVariable, Object obj) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = customVariable.mName;
        this.mType = customVariable.mType;
        setValue(obj);
    }
}
