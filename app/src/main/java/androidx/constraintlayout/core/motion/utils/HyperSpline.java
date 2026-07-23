package androidx.constraintlayout.core.motion.utils;

import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
public class HyperSpline {
    public double[][] mCtl;
    public Cubic[][] mCurve;
    public double[] mCurveLength;
    public int mDimensionality;
    public int mPoints;
    public double mTotalLength;

    public static class Cubic {
        public double mA;
        public double mB;
        public double mC;
        public double mD;

        public Cubic(double d, double d2, double d3, double d4) {
            this.mA = d;
            this.mB = d2;
            this.mC = d3;
            this.mD = d4;
        }

        public double eval(double d) {
            return (((((this.mD * d) + this.mC) * d) + this.mB) * d) + this.mA;
        }

        public double vel(double d) {
            return (((this.mC * 2.0d) + (this.mD * 3.0d * d)) * d) + this.mB;
        }
    }

    public HyperSpline(double[][] dArr) {
        setup(dArr);
    }

    public double approxLength(Cubic[] cubicArr) {
        int i;
        int length = cubicArr.length;
        double[] dArr = new double[cubicArr.length];
        double d = 0.0d;
        double d2 = 0.0d;
        double dSqrt = 0.0d;
        while (true) {
            i = 0;
            if (d2 >= 1.0d) {
                break;
            }
            double d3 = 0.0d;
            while (i < cubicArr.length) {
                double d4 = dArr[i];
                double dEval = cubicArr[i].eval(d2);
                dArr[i] = dEval;
                double d5 = d4 - dEval;
                d3 += d5 * d5;
                i++;
            }
            if (d2 > 0.0d) {
                dSqrt += Math.sqrt(d3);
            }
            d2 += 0.1d;
        }
        while (i < cubicArr.length) {
            double d6 = dArr[i];
            double dEval2 = cubicArr[i].eval(1.0d);
            dArr[i] = dEval2;
            double d7 = d6 - dEval2;
            d += d7 * d7;
            i++;
        }
        return Math.sqrt(d) + dSqrt;
    }

    public void getPos(double d, double[] dArr) {
        double d2 = d * this.mTotalLength;
        int i = 0;
        while (true) {
            double[] dArr2 = this.mCurveLength;
            if (i >= dArr2.length - 1 || dArr2[i] >= d2) {
                break;
            }
            d2 -= dArr2[i];
            i++;
        }
        for (int i2 = 0; i2 < dArr.length; i2++) {
            dArr[i2] = this.mCurve[i2][i].eval(d2 / this.mCurveLength[i]);
        }
    }

    public void getVelocity(double d, double[] dArr) {
        double d2 = d * this.mTotalLength;
        int i = 0;
        while (true) {
            double[] dArr2 = this.mCurveLength;
            if (i >= dArr2.length - 1 || dArr2[i] >= d2) {
                break;
            }
            d2 -= dArr2[i];
            i++;
        }
        for (int i2 = 0; i2 < dArr.length; i2++) {
            dArr[i2] = this.mCurve[i2][i].vel(d2 / this.mCurveLength[i]);
        }
    }

    public void setup(double[][] dArr) {
        int i;
        int length = dArr[0].length;
        this.mDimensionality = length;
        int length2 = dArr.length;
        this.mPoints = length2;
        this.mCtl = (double[][]) Array.newInstance((Class<?>) double.class, length, length2);
        this.mCurve = new Cubic[this.mDimensionality][];
        for (int i2 = 0; i2 < this.mDimensionality; i2++) {
            for (int i3 = 0; i3 < this.mPoints; i3++) {
                this.mCtl[i2][i3] = dArr[i3][i2];
            }
        }
        int i4 = 0;
        while (true) {
            i = this.mDimensionality;
            if (i4 >= i) {
                break;
            }
            Cubic[][] cubicArr = this.mCurve;
            double[][] dArr2 = this.mCtl;
            int length3 = dArr2[i4].length;
            double[] dArr3 = dArr2[i4];
            double[] dArr4 = new double[length3];
            double[] dArr5 = new double[length3];
            double[] dArr6 = new double[length3];
            int i5 = length3 - 1;
            dArr4[0] = 0.5d;
            for (int i6 = 1; i6 < i5; i6++) {
                dArr4[i6] = 1.0d / (4.0d - dArr4[i6 - 1]);
            }
            int i7 = i5 - 1;
            dArr4[i5] = 1.0d / (2.0d - dArr4[i7]);
            dArr5[0] = (dArr3[1] - dArr3[0]) * 3.0d * dArr4[0];
            int i8 = 1;
            while (i8 < i5) {
                int i9 = i8 + 1;
                int i10 = i8 - 1;
                dArr5[i8] = (((dArr3[i9] - dArr3[i10]) * 3.0d) - dArr5[i10]) * dArr4[i8];
                i8 = i9;
            }
            dArr5[i5] = (((dArr3[i5] - dArr3[i7]) * 3.0d) - dArr5[i7]) * dArr4[i5];
            dArr6[i5] = dArr5[i5];
            while (i7 >= 0) {
                dArr6[i7] = dArr5[i7] - (dArr4[i7] * dArr6[i7 + 1]);
                i7--;
            }
            Cubic[] cubicArr2 = new Cubic[i5];
            int i11 = 0;
            while (i11 < i5) {
                int i12 = i11 + 1;
                cubicArr2[i11] = new Cubic((float) dArr3[i11], dArr6[i11], (((dArr3[i12] - dArr3[i11]) * 3.0d) - (dArr6[i11] * 2.0d)) - dArr6[i12], ((dArr3[i11] - dArr3[i12]) * 2.0d) + dArr6[i11] + dArr6[i12]);
                i11 = i12;
            }
            cubicArr[i4] = cubicArr2;
            i4++;
        }
        this.mCurveLength = new double[this.mPoints - 1];
        this.mTotalLength = 0.0d;
        Cubic[] cubicArr3 = new Cubic[i];
        for (int i13 = 0; i13 < this.mCurveLength.length; i13++) {
            for (int i14 = 0; i14 < this.mDimensionality; i14++) {
                cubicArr3[i14] = this.mCurve[i14][i13];
            }
            double d = this.mTotalLength;
            double[] dArr7 = this.mCurveLength;
            double dApproxLength = approxLength(cubicArr3);
            dArr7[i13] = dApproxLength;
            this.mTotalLength = d + dApproxLength;
        }
    }

    public HyperSpline() {
    }

    public void getPos(double d, float[] fArr) {
        double d2 = d * this.mTotalLength;
        int i = 0;
        while (true) {
            double[] dArr = this.mCurveLength;
            if (i >= dArr.length - 1 || dArr[i] >= d2) {
                break;
            }
            d2 -= dArr[i];
            i++;
        }
        for (int i2 = 0; i2 < fArr.length; i2++) {
            fArr[i2] = (float) this.mCurve[i2][i].eval(d2 / this.mCurveLength[i]);
        }
    }

    public double getPos(double d, int i) {
        double[] dArr;
        double d2 = d * this.mTotalLength;
        int i2 = 0;
        while (true) {
            dArr = this.mCurveLength;
            if (i2 >= dArr.length - 1 || dArr[i2] >= d2) {
                break;
            }
            d2 -= dArr[i2];
            i2++;
        }
        return this.mCurve[i][i2].eval(d2 / dArr[i2]);
    }
}
