package androidx.constraintlayout.motion.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.CollectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class CustomSupport {
    private static final String TAG = "CustomSupport";

    /* JADX INFO: renamed from: androidx.constraintlayout.motion.utils.CustomSupport$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType;

        static {
            int[] iArr = new int[ConstraintAttribute.AttributeType.values().length];
            $SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType = iArr;
            try {
                iArr[ConstraintAttribute.AttributeType.INT_TYPE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[ConstraintAttribute.AttributeType.FLOAT_TYPE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[ConstraintAttribute.AttributeType.COLOR_DRAWABLE_TYPE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[ConstraintAttribute.AttributeType.COLOR_TYPE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[ConstraintAttribute.AttributeType.STRING_TYPE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[ConstraintAttribute.AttributeType.BOOLEAN_TYPE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[ConstraintAttribute.AttributeType.DIMENSION_TYPE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private static int clamp(int c) {
        int i = (c & (~(c >> 31))) - 255;
        return (i & (i >> 31)) + 255;
    }

    public static void setInterpolatedValue(ConstraintAttribute att, View view, float[] value) {
        Class<?> cls = view.getClass();
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m(CollectionUtils.SET_TYPE);
        sbM.append(att.getName());
        String string = sbM.toString();
        try {
            boolean z = true;
            switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[att.getType().ordinal()]) {
                case 1:
                    cls.getMethod(string, Integer.TYPE).invoke(view, Integer.valueOf((int) value[0]));
                    return;
                case 2:
                    cls.getMethod(string, Float.TYPE).invoke(view, Float.valueOf(value[0]));
                    return;
                case 3:
                    Method method = cls.getMethod(string, Drawable.class);
                    int iClamp = (clamp((int) (((float) Math.pow(value[0], 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (value[3] * 255.0f)) << 24) | (clamp((int) (((float) Math.pow(value[1], 0.45454545454545453d)) * 255.0f)) << 8) | clamp((int) (((float) Math.pow(value[2], 0.45454545454545453d)) * 255.0f));
                    ColorDrawable colorDrawable = new ColorDrawable();
                    colorDrawable.setColor(iClamp);
                    method.invoke(view, colorDrawable);
                    return;
                case 4:
                    cls.getMethod(string, Integer.TYPE).invoke(view, Integer.valueOf((clamp((int) (((float) Math.pow(value[0], 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (value[3] * 255.0f)) << 24) | (clamp((int) (((float) Math.pow(value[1], 0.45454545454545453d)) * 255.0f)) << 8) | clamp((int) (((float) Math.pow(value[2], 0.45454545454545453d)) * 255.0f))));
                    return;
                case 5:
                    throw new RuntimeException("unable to interpolate strings " + att.getName());
                case 6:
                    Method method2 = cls.getMethod(string, Boolean.TYPE);
                    Object[] objArr = new Object[1];
                    if (value[0] <= 0.5f) {
                        z = false;
                    }
                    objArr[0] = Boolean.valueOf(z);
                    method2.invoke(view, objArr);
                    return;
                case 7:
                    cls.getMethod(string, Float.TYPE).invoke(view, Float.valueOf(value[0]));
                    return;
                default:
                    return;
            }
        } catch (IllegalAccessException e) {
            StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("cannot access method ", string, " on View \"");
            sbM26m.append(Debug.getName(view));
            sbM26m.append("\"");
            Log.e(TAG, sbM26m.toString());
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            StringBuilder sbM26m2 = Insets$$ExternalSyntheticOutline0.m26m("no method ", string, " on View \"");
            sbM26m2.append(Debug.getName(view));
            sbM26m2.append("\"");
            Log.e(TAG, sbM26m2.toString());
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
    }
}
