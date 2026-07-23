package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class ViewingConditions {
    public static final ViewingConditions DEFAULT;
    private final double aw;
    private final double c;
    private final double fl;
    private final double flRoot;
    private final double n;
    private final double nbb;
    private final double nc;
    private final double ncb;
    private final double[] rgbD;
    private final double z;

    static {
        double[] dArr = {ColorUtils.whitePointD65()[0], ColorUtils.whitePointD65()[1], ColorUtils.whitePointD65()[2]};
        double dYFromLstar = (ColorUtils.yFromLstar(50.0d) * 63.66197723675813d) / 100.0d;
        double[][] dArr2 = Cam16.XYZ_TO_CAM16RGB;
        double d = (dArr[2] * dArr2[0][2]) + (dArr[1] * dArr2[0][1]) + (dArr[0] * dArr2[0][0]);
        double d2 = (dArr[2] * dArr2[1][2]) + (dArr[1] * dArr2[1][1]) + (dArr[0] * dArr2[1][0]);
        double d3 = (dArr[2] * dArr2[2][2]) + (dArr[1] * dArr2[2][1]) + (dArr[0] * dArr2[2][0]);
        double dLerp = MathUtils.lerp(0.59d, 0.69d, 0.9999999999999998d);
        double dClampDouble = MathUtils.clampDouble(0.0d, 1.0d, (1.0d - (Math.exp(((-dYFromLstar) - 42.0d) / 92.0d) * 0.2777777777777778d)) * 1.0d);
        double[] dArr3 = {(((100.0d / d) * dClampDouble) + 1.0d) - dClampDouble, (((100.0d / d2) * dClampDouble) + 1.0d) - dClampDouble, (((100.0d / d3) * dClampDouble) + 1.0d) - dClampDouble};
        double d4 = 5.0d * dYFromLstar;
        double d5 = 1.0d / (d4 + 1.0d);
        double d6 = d5 * d5 * d5 * d5;
        double d7 = 1.0d - d6;
        double dCbrt = (Math.cbrt(d4) * 0.1d * d7 * d7) + (d6 * dYFromLstar);
        double dYFromLstar2 = ColorUtils.yFromLstar(50.0d) / dArr[1];
        double dSqrt = Math.sqrt(dYFromLstar2) + 1.48d;
        double dPow = 0.725d / Math.pow(dYFromLstar2, 0.2d);
        double[] dArr4 = {Math.pow(((dArr3[0] * dCbrt) * d) / 100.0d, 0.42d), Math.pow(((dArr3[1] * dCbrt) * d2) / 100.0d, 0.42d), Math.pow(((dArr3[2] * dCbrt) * d3) / 100.0d, 0.42d)};
        double[] dArr5 = {(dArr4[0] * 400.0d) / (dArr4[0] + 27.13d), (dArr4[1] * 400.0d) / (dArr4[1] + 27.13d), (dArr4[2] * 400.0d) / (dArr4[2] + 27.13d)};
        DEFAULT = new ViewingConditions(dYFromLstar2, dPow * ((dArr5[2] * 0.05d) + (dArr5[0] * 2.0d) + dArr5[1]), dPow, dPow, dLerp, 1.0d, dArr3, dCbrt, Math.pow(dCbrt, 0.25d), dSqrt);
    }

    private ViewingConditions(double d, double d2, double d3, double d4, double d5, double d6, double[] dArr, double d7, double d8, double d9) {
        this.n = d;
        this.aw = d2;
        this.nbb = d3;
        this.ncb = d4;
        this.c = d5;
        this.nc = d6;
        this.rgbD = dArr;
        this.fl = d7;
        this.flRoot = d8;
        this.z = d9;
    }

    public double getAw() {
        return this.aw;
    }

    public final double getC() {
        return this.c;
    }

    public final double getFl() {
        return this.fl;
    }

    public double getFlRoot() {
        return this.flRoot;
    }

    public double getN() {
        return this.n;
    }

    public double getNbb() {
        return this.nbb;
    }

    public final double getNc() {
        return this.nc;
    }

    public final double getNcb() {
        return this.ncb;
    }

    public double[] getRgbD() {
        return this.rgbD;
    }

    public final double getZ() {
        return this.z;
    }
}
