package androidx.core.content.res;

import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes.dex */
final class ViewingConditions {
    public static final ViewingConditions DEFAULT;
    private final float mAw;
    private final float mC;
    private final float mFl;
    private final float mFlRoot;
    private final float mN;
    private final float mNbb;
    private final float mNc;
    private final float mNcb;
    private final float[] mRgbD;
    private final float mZ;

    static {
        float[] fArr = CamUtils.WHITE_POINT_D65;
        float fYFromLStar = (float) ((((double) CamUtils.yFromLStar()) * 63.66197723675813d) / 100.0d);
        float[][] fArr2 = CamUtils.XYZ_TO_CAM16RGB;
        float f = (fArr[2] * fArr2[0][2]) + (fArr[1] * fArr2[0][1]) + (fArr[0] * fArr2[0][0]);
        float f2 = (fArr[2] * fArr2[1][2]) + (fArr[1] * fArr2[1][1]) + (fArr[0] * fArr2[1][0]);
        float f3 = (fArr[2] * fArr2[2][2]) + (fArr[1] * fArr2[2][1]) + (fArr[0] * fArr2[2][0]);
        float f4 = ((double) 1.0f) >= 0.9d ? 0.69f : 0.655f;
        float fExp = (1.0f - (((float) Math.exp(((-fYFromLStar) - 42.0f) / 92.0f)) * 0.2777778f)) * 1.0f;
        double d = fExp;
        if (d > 1.0d) {
            fExp = 1.0f;
        } else if (d < 0.0d) {
            fExp = 0.0f;
        }
        float[] fArr3 = {(((100.0f / f) * fExp) + 1.0f) - fExp, (((100.0f / f2) * fExp) + 1.0f) - fExp, (((100.0f / f3) * fExp) + 1.0f) - fExp};
        float f5 = 1.0f / ((5.0f * fYFromLStar) + 1.0f);
        float f6 = f5 * f5 * f5 * f5;
        float f7 = 1.0f - f6;
        float fCbrt = (0.1f * f7 * f7 * ((float) Math.cbrt(((double) fYFromLStar) * 5.0d))) + (f6 * fYFromLStar);
        float fYFromLStar2 = CamUtils.yFromLStar() / fArr[1];
        double d2 = fYFromLStar2;
        float fSqrt = ((float) Math.sqrt(d2)) + 1.48f;
        float fPow = 0.725f / ((float) Math.pow(d2, 0.2d));
        float[] fArr4 = {(float) Math.pow(((double) ((fArr3[0] * fCbrt) * f)) / 100.0d, 0.42d), (float) Math.pow(((double) ((fArr3[1] * fCbrt) * f2)) / 100.0d, 0.42d), (float) Math.pow(((double) ((fArr3[2] * fCbrt) * f3)) / 100.0d, 0.42d)};
        float[] fArr5 = {(fArr4[0] * 400.0f) / (fArr4[0] + 27.13f), (fArr4[1] * 400.0f) / (fArr4[1] + 27.13f), (fArr4[2] * 400.0f) / (fArr4[2] + 27.13f)};
        DEFAULT = new ViewingConditions(fYFromLStar2, ((fArr5[2] * 0.05f) + (fArr5[0] * 2.0f) + fArr5[1]) * fPow, fPow, fPow, f4, 1.0f, fArr3, fCbrt, (float) Math.pow(fCbrt, 0.25d), fSqrt);
    }

    private ViewingConditions(float f, float f2, float f3, float f4, float f5, float f6, float[] fArr, float f7, float f8, float f9) {
        this.mN = f;
        this.mAw = f2;
        this.mNbb = f3;
        this.mNcb = f4;
        this.mC = f5;
        this.mNc = f6;
        this.mRgbD = fArr;
        this.mFl = f7;
        this.mFlRoot = f8;
        this.mZ = f9;
    }

    public final float getAw() {
        return this.mAw;
    }

    public final float getC() {
        return this.mC;
    }

    public final float getFl() {
        return this.mFl;
    }

    public final float getFlRoot() {
        return this.mFlRoot;
    }

    public final float getN() {
        return this.mN;
    }

    public final float getNbb() {
        return this.mNbb;
    }

    public final float getNc() {
        return this.mNc;
    }

    public final float getNcb() {
        return this.mNcb;
    }

    @NonNull
    public final float[] getRgbD() {
        return this.mRgbD;
    }

    public final float getZ() {
        return this.mZ;
    }
}
