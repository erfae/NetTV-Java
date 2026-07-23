package androidx.core.content.res;

import android.graphics.Color;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import java.util.Objects;
import kotlin.jvm.internal.DoubleCompanionObject;

/* JADX INFO: loaded from: classes.dex */
class CamColor {
    private static final float CHROMA_SEARCH_ENDPOINT = 0.4f;
    private static final float DE_MAX = 1.0f;
    private static final float DL_MAX = 0.2f;
    private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01f;
    private final float mAstar;
    private final float mBstar;
    private final float mChroma;
    private final float mHue;
    private final float mJ;
    private final float mJstar;
    private final float mM;
    private final float mQ;
    private final float mS;

    public CamColor(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.mHue = f;
        this.mChroma = f2;
        this.mJ = f3;
        this.mQ = f4;
        this.mM = f5;
        this.mS = f6;
        this.mJstar = f7;
        this.mAstar = f8;
        this.mBstar = f9;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x00de  */
    /* JADX WARN: Code duplicated, block: B:24:0x00e0  */
    @Nullable
    private static CamColor findCamByJ(@FloatRange(from = 0.0d, to = 360.0d) float f, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f2, @FloatRange(from = 0.0d, to = 100.0d) float f3) {
        float f4 = 1000.0f;
        float f5 = 100.0f;
        CamColor camColor = null;
        float f6 = 1000.0f;
        float f7 = 0.0f;
        float f8 = 100.0f;
        while (Math.abs(f7 - f8) > LIGHTNESS_SEARCH_ENDPOINT) {
            float f9 = ((f8 - f7) / 2.0f) + f7;
            CamColor camColorFromJch = fromJch(f9, f2, f);
            Objects.requireNonNull(camColorFromJch);
            int iViewed = camColorFromJch.viewed(ViewingConditions.DEFAULT);
            float fLinearized = CamUtils.linearized(Color.red(iViewed));
            float fLinearized2 = CamUtils.linearized(Color.green(iViewed));
            float fLinearized3 = CamUtils.linearized(Color.blue(iViewed));
            float[][] fArr = CamUtils.SRGB_TO_XYZ;
            float f10 = ((fLinearized3 * fArr[1][2]) + ((fLinearized2 * fArr[1][1]) + (fLinearized * fArr[1][0]))) / f5;
            float fCbrt = f10 <= 0.008856452f ? f10 * 903.2963f : (((float) Math.cbrt(f10)) * 116.0f) - 16.0f;
            float fAbs = Math.abs(f3 - fCbrt);
            if (fAbs < 0.2f) {
                CamColor camColorFromColor = fromColor(iViewed);
                CamColor camColorFromJch2 = fromJch(camColorFromColor.mJ, camColorFromColor.mChroma, f);
                float f11 = camColorFromColor.mJstar - camColorFromJch2.mJstar;
                float f12 = camColorFromColor.mAstar - camColorFromJch2.mAstar;
                float f13 = camColorFromColor.mBstar - camColorFromJch2.mBstar;
                float fPow = (float) (Math.pow(Math.sqrt((f13 * f13) + (f12 * f12) + (f11 * f11)), 0.63d) * 1.41d);
                if (fPow <= 1.0f) {
                    f6 = fPow;
                    camColor = camColorFromColor;
                    f4 = fAbs;
                }
                if (f4 != 0.0f && f6 == 0.0f) {
                    break;
                }
                if (fCbrt < f3) {
                    f7 = f9;
                } else {
                    f8 = f9;
                }
                f5 = 100.0f;
            }
            f6 = f6;
            if (f4 != 0.0f) {
            }
            if (fCbrt < f3) {
                f7 = f9;
            } else {
                f8 = f9;
            }
            f5 = 100.0f;
        }
        return camColor;
    }

    @NonNull
    public static CamColor fromColor(@ColorInt int i) {
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        float fLinearized = CamUtils.linearized(Color.red(i));
        float fLinearized2 = CamUtils.linearized(Color.green(i));
        float fLinearized3 = CamUtils.linearized(Color.blue(i));
        float[][] fArr = CamUtils.SRGB_TO_XYZ;
        float[] fArr2 = {(fArr[0][2] * fLinearized3) + (fArr[0][1] * fLinearized2) + (fArr[0][0] * fLinearized), (fArr[1][2] * fLinearized3) + (fArr[1][1] * fLinearized2) + (fArr[1][0] * fLinearized), (fLinearized3 * fArr[2][2]) + (fLinearized2 * fArr[2][1]) + (fLinearized * fArr[2][0])};
        float[][] fArr3 = CamUtils.XYZ_TO_CAM16RGB;
        float f = (fArr2[2] * fArr3[0][2]) + (fArr2[1] * fArr3[0][1]) + (fArr2[0] * fArr3[0][0]);
        float f2 = (fArr2[2] * fArr3[1][2]) + (fArr2[1] * fArr3[1][1]) + (fArr2[0] * fArr3[1][0]);
        float f3 = (fArr2[2] * fArr3[2][2]) + (fArr2[1] * fArr3[2][1]) + (fArr2[0] * fArr3[2][0]);
        float f4 = viewingConditions.getRgbD()[0] * f;
        float f5 = viewingConditions.getRgbD()[1] * f2;
        float f6 = viewingConditions.getRgbD()[2] * f3;
        float fPow = (float) Math.pow(((double) (Math.abs(f4) * viewingConditions.getFl())) / 100.0d, 0.42d);
        float fPow2 = (float) Math.pow(((double) (Math.abs(f5) * viewingConditions.getFl())) / 100.0d, 0.42d);
        float fPow3 = (float) Math.pow(((double) (Math.abs(f6) * viewingConditions.getFl())) / 100.0d, 0.42d);
        float fSignum = ((Math.signum(f4) * 400.0f) * fPow) / (fPow + 27.13f);
        float fSignum2 = ((Math.signum(f5) * 400.0f) * fPow2) / (fPow2 + 27.13f);
        float fSignum3 = ((Math.signum(f6) * 400.0f) * fPow3) / (fPow3 + 27.13f);
        double d = fSignum3;
        float f7 = ((float) (((((double) fSignum2) * (-12.0d)) + (((double) fSignum) * 11.0d)) + d)) / 11.0f;
        float f8 = ((float) (((double) (fSignum + fSignum2)) - (d * 2.0d))) / 9.0f;
        float f9 = fSignum2 * 20.0f;
        float f10 = ((21.0f * fSignum3) + ((fSignum * 20.0f) + f9)) / 20.0f;
        float f11 = (((fSignum * 40.0f) + f9) + fSignum3) / 20.0f;
        float fAtan2 = (((float) Math.atan2(f8, f7)) * 180.0f) / 3.1415927f;
        if (fAtan2 < 0.0f) {
            fAtan2 += 360.0f;
        } else if (fAtan2 >= 360.0f) {
            fAtan2 -= 360.0f;
        }
        float f12 = (3.1415927f * fAtan2) / 180.0f;
        float fPow4 = ((float) Math.pow((f11 * viewingConditions.getNbb()) / viewingConditions.getAw(), viewingConditions.getC() * viewingConditions.getZ())) * 100.0f;
        float aw = (viewingConditions.getAw() + 4.0f) * (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(fPow4 / 100.0f)) * viewingConditions.getFlRoot();
        float fPow5 = ((float) Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d)) * ((float) Math.pow((((((((float) (Math.cos(((((double) (((double) fAtan2) < 20.14d ? 360.0f + fAtan2 : fAtan2)) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * viewingConditions.getNc()) * viewingConditions.getNcb()) * ((float) Math.sqrt((f8 * f8) + (f7 * f7)))) / (f10 + 0.305f), 0.9d));
        float fSqrt = fPow5 * ((float) Math.sqrt(((double) fPow4) / 100.0d));
        float flRoot = fSqrt * viewingConditions.getFlRoot();
        float fSqrt2 = ((float) Math.sqrt((fPow5 * viewingConditions.getC()) / (viewingConditions.getAw() + 4.0f))) * 50.0f;
        float f13 = (1.7f * fPow4) / ((0.007f * fPow4) + 1.0f);
        float fLog = ((float) Math.log((0.0228f * flRoot) + 1.0f)) * 43.85965f;
        double d2 = f12;
        return new CamColor(fAtan2, fSqrt, fPow4, aw, flRoot, fSqrt2, f13, fLog * ((float) Math.cos(d2)), fLog * ((float) Math.sin(d2)));
    }

    @NonNull
    private static CamColor fromJch(@FloatRange(from = 0.0d, to = 100.0d) float f, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f2, @FloatRange(from = 0.0d, to = 360.0d) float f3) {
        return fromJchInFrame(f, f2, f3, ViewingConditions.DEFAULT);
    }

    @NonNull
    private static CamColor fromJchInFrame(@FloatRange(from = 0.0d, to = 100.0d) float f, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f2, @FloatRange(from = 0.0d, to = 360.0d) float f3, ViewingConditions viewingConditions) {
        double d = ((double) f) / 100.0d;
        float aw = (viewingConditions.getAw() + 4.0f) * (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(d)) * viewingConditions.getFlRoot();
        float flRoot = f2 * viewingConditions.getFlRoot();
        float fSqrt = ((float) Math.sqrt(((f2 / ((float) Math.sqrt(d))) * viewingConditions.getC()) / (viewingConditions.getAw() + 4.0f))) * 50.0f;
        float f4 = (1.7f * f) / ((0.007f * f) + 1.0f);
        float fLog = ((float) Math.log((((double) flRoot) * 0.0228d) + 1.0d)) * 43.85965f;
        double d2 = (3.1415927f * f3) / 180.0f;
        return new CamColor(f3, f2, f, aw, flRoot, fSqrt, f4, fLog * ((float) Math.cos(d2)), fLog * ((float) Math.sin(d2)));
    }

    public static int toColor(@FloatRange(from = 0.0d, to = 360.0d) float f, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f2, @FloatRange(from = 0.0d, to = 100.0d) float f3) {
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        if (f2 < 1.0d || Math.round(f3) <= 0.0d || Math.round(f3) >= 100.0d) {
            return CamUtils.intFromLStar(f3);
        }
        float fMin = f < 0.0f ? 0.0f : Math.min(360.0f, f);
        float f4 = f2;
        CamColor camColor = null;
        float f5 = 0.0f;
        boolean z = true;
        while (Math.abs(f5 - f2) >= CHROMA_SEARCH_ENDPOINT) {
            CamColor camColorFindCamByJ = findCamByJ(fMin, f4, f3);
            if (z) {
                if (camColorFindCamByJ != null) {
                    return camColorFindCamByJ.viewed(viewingConditions);
                }
                z = false;
            } else if (camColorFindCamByJ == null) {
                f2 = f4;
            } else {
                f5 = f4;
                camColor = camColorFindCamByJ;
            }
            f4 = ((f2 - f5) / 2.0f) + f5;
        }
        return camColor == null ? CamUtils.intFromLStar(f3) : camColor.viewed(viewingConditions);
    }

    @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false)
    public final float getChroma() {
        return this.mChroma;
    }

    @FloatRange(from = 0.0d, to = 360.0d, toInclusive = false)
    public final float getHue() {
        return this.mHue;
    }

    /* JADX WARN: Code duplicated, block: B:8:0x001d  */
    @ColorInt
    public final int viewed(@NonNull ViewingConditions viewingConditions) {
        float fSqrt;
        float f = this.mChroma;
        if (f != 0.0d) {
            double d = this.mJ;
            if (d == 0.0d) {
                fSqrt = 0.0f;
            } else {
                fSqrt = f / ((float) Math.sqrt(d / 100.0d));
            }
        } else {
            fSqrt = 0.0f;
        }
        float fPow = (float) Math.pow(((double) fSqrt) / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d), 1.1111111111111112d);
        double d2 = (this.mHue * 3.1415927f) / 180.0f;
        float fCos = ((float) (Math.cos(2.0d + d2) + 3.8d)) * 0.25f;
        float aw = viewingConditions.getAw() * ((float) Math.pow(((double) this.mJ) / 100.0d, (1.0d / ((double) viewingConditions.getC())) / ((double) viewingConditions.getZ())));
        float nc = fCos * 3846.1538f * viewingConditions.getNc() * viewingConditions.getNcb();
        float nbb = aw / viewingConditions.getNbb();
        float fSin = (float) Math.sin(d2);
        float fCos2 = (float) Math.cos(d2);
        float f2 = (((0.305f + nbb) * 23.0f) * fPow) / (((fPow * 108.0f) * fSin) + (((11.0f * fPow) * fCos2) + (nc * 23.0f)));
        float f3 = fCos2 * f2;
        float f4 = f2 * fSin;
        float f5 = nbb * 460.0f;
        float f6 = ((288.0f * f4) + ((451.0f * f3) + f5)) / 1403.0f;
        float f7 = ((f5 - (891.0f * f3)) - (261.0f * f4)) / 1403.0f;
        float f8 = ((f5 - (f3 * 220.0f)) - (f4 * 6300.0f)) / 1403.0f;
        float fl = (100.0f / viewingConditions.getFl()) * Math.signum(f6) * ((float) Math.pow((float) Math.max(0.0d, (((double) Math.abs(f6)) * 27.13d) / (400.0d - ((double) Math.abs(f6)))), 2.380952380952381d));
        float fl2 = (100.0f / viewingConditions.getFl()) * Math.signum(f7) * ((float) Math.pow((float) Math.max(0.0d, (((double) Math.abs(f7)) * 27.13d) / (400.0d - ((double) Math.abs(f7)))), 2.380952380952381d));
        float fl3 = (100.0f / viewingConditions.getFl()) * Math.signum(f8) * ((float) Math.pow((float) Math.max(0.0d, (((double) Math.abs(f8)) * 27.13d) / (400.0d - ((double) Math.abs(f8)))), 2.380952380952381d));
        float f9 = fl / viewingConditions.getRgbD()[0];
        float f10 = fl2 / viewingConditions.getRgbD()[1];
        float f11 = fl3 / viewingConditions.getRgbD()[2];
        float[][] fArr = CamUtils.CAM16RGB_TO_XYZ;
        return ColorUtils.XYZToColor((fArr[0][2] * f11) + (fArr[0][1] * f10) + (fArr[0][0] * f9), (fArr[1][2] * f11) + (fArr[1][1] * f10) + (fArr[1][0] * f9), (f11 * fArr[2][2]) + (f10 * fArr[2][1]) + (f9 * fArr[2][0]));
    }
}
