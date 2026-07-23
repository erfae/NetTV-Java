package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class MathUtils {
    private MathUtils() {
    }

    public static double clampDouble(double d, double d2, double d3) {
        if (d3 < d) {
            return d;
        }
        return d3 > d2 ? d2 : d3;
    }

    public static int clampInt(int i, int i2, int i3) {
        if (i3 < i) {
            return i;
        }
        return i3 > i2 ? i2 : i3;
    }

    public static double differenceDegrees(double d, double d2) {
        return 180.0d - Math.abs(Math.abs(d - d2) - 180.0d);
    }

    public static double lerp(double d, double d2, double d3) {
        return (d3 * d2) + ((1.0d - d3) * d);
    }

    public static double[] matrixMultiply(double[] dArr, double[][] dArr2) {
        return new double[]{(dArr[2] * dArr2[0][2]) + (dArr[1] * dArr2[0][1]) + (dArr[0] * dArr2[0][0]), (dArr[2] * dArr2[1][2]) + (dArr[1] * dArr2[1][1]) + (dArr[0] * dArr2[1][0]), (dArr[2] * dArr2[2][2]) + (dArr[1] * dArr2[2][1]) + (dArr[0] * dArr2[2][0])};
    }

    public static double rotationDirection(double d, double d2) {
        return sanitizeDegreesDouble(d2 - d) <= 180.0d ? 1.0d : -1.0d;
    }

    public static double sanitizeDegreesDouble(double d) {
        double d2 = d % 360.0d;
        return d2 < 0.0d ? d2 + 360.0d : d2;
    }

    public static int sanitizeDegreesInt(int i) {
        int i2 = i % 360;
        return i2 < 0 ? i2 + 360 : i2;
    }

    public static int signum(double d) {
        if (d < 0.0d) {
            return -1;
        }
        return d == 0.0d ? 0 : 1;
    }
}
