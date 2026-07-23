package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class QuantizerWsmeans {
    private static final int MAX_ITERATIONS = 10;
    private static final double MIN_MOVEMENT_DISTANCE = 3.0d;

    public static final class Distance implements Comparable<Distance> {
        public int index = -1;
        public double distance = -1.0d;

        @Override // java.lang.Comparable
        public int compareTo(Distance distance) {
            return Double.valueOf(this.distance).compareTo(Double.valueOf(distance.distance));
        }
    }

    private QuantizerWsmeans() {
    }

    public static Map<Integer, Integer> quantize(int[] iArr, int[] iArr2, int i) {
        int[] iArr3;
        int i2;
        HashMap map = new HashMap();
        double[][] dArr = new double[iArr.length][];
        int[] iArr4 = new int[iArr.length];
        PointProviderLab pointProviderLab = new PointProviderLab();
        int i3 = 0;
        for (int i4 : iArr) {
            Integer num = (Integer) map.get(Integer.valueOf(i4));
            if (num == null) {
                dArr[i3] = pointProviderLab.fromInt(i4);
                iArr4[i3] = i4;
                i3++;
                map.put(Integer.valueOf(i4), 1);
            } else {
                map.put(Integer.valueOf(i4), Integer.valueOf(num.intValue() + 1));
            }
        }
        int[] iArr5 = new int[i3];
        for (int i5 = 0; i5 < i3; i5++) {
            iArr5[i5] = ((Integer) map.get(Integer.valueOf(iArr4[i5]))).intValue();
        }
        int iMin = Math.min(i, i3);
        if (iArr2.length != 0) {
            iMin = Math.min(iMin, iArr2.length);
        }
        double[][] dArr2 = new double[iMin][];
        int i6 = 0;
        for (int i7 = 0; i7 < iArr2.length; i7++) {
            dArr2[i7] = pointProviderLab.fromInt(iArr2[i7]);
            i6++;
        }
        int i8 = iMin - i6;
        if (i8 > 0) {
            for (int i9 = 0; i9 < i8; i9++) {
            }
        }
        int[] iArr6 = new int[i3];
        for (int i10 = 0; i10 < i3; i10++) {
            iArr6[i10] = (int) Math.floor(Math.random() * ((double) iMin));
        }
        int[][] iArr7 = new int[iMin][];
        for (int i11 = 0; i11 < iMin; i11++) {
            iArr7[i11] = new int[iMin];
        }
        Distance[][] distanceArr = new Distance[iMin][];
        for (int i12 = 0; i12 < iMin; i12++) {
            distanceArr[i12] = new Distance[iMin];
            for (int i13 = 0; i13 < iMin; i13++) {
                distanceArr[i12][i13] = new Distance();
            }
        }
        int[] iArr8 = new int[iMin];
        int i14 = 0;
        while (true) {
            if (i14 >= 10) {
                iArr3 = iArr8;
                break;
            }
            int i15 = 0;
            while (i15 < iMin) {
                int i16 = i15 + 1;
                int i17 = i16;
                while (i17 < iMin) {
                    double dDistance = pointProviderLab.distance(dArr2[i15], dArr2[i17]);
                    distanceArr[i17][i15].distance = dDistance;
                    distanceArr[i17][i15].index = i15;
                    distanceArr[i15][i17].distance = dDistance;
                    distanceArr[i15][i17].index = i17;
                    i17++;
                    iArr8 = iArr8;
                }
                int[] iArr9 = iArr8;
                Arrays.sort(distanceArr[i15]);
                for (int i18 = 0; i18 < iMin; i18++) {
                    iArr7[i15][i18] = distanceArr[i15][i18].index;
                }
                iArr8 = iArr9;
                i15 = i16;
            }
            int[] iArr10 = iArr8;
            int i19 = 0;
            int i20 = 0;
            while (i19 < i3) {
                double[] dArr3 = dArr[i19];
                int i21 = iArr6[i19];
                double dDistance2 = pointProviderLab.distance(dArr3, dArr2[i21]);
                int[][] iArr11 = iArr7;
                int[] iArr12 = iArr5;
                double d = dDistance2;
                int i22 = -1;
                int i23 = 0;
                while (i23 < iMin) {
                    Distance[][] distanceArr2 = distanceArr;
                    int i24 = i3;
                    if (distanceArr[i21][i23].distance < 4.0d * dDistance2) {
                        double dDistance3 = pointProviderLab.distance(dArr3, dArr2[i23]);
                        if (dDistance3 < d) {
                            i22 = i23;
                            d = dDistance3;
                        }
                    }
                    i23++;
                    i3 = i24;
                    distanceArr = distanceArr2;
                }
                int i25 = i3;
                Distance[][] distanceArr3 = distanceArr;
                if (i22 != -1 && Math.abs(Math.sqrt(d) - Math.sqrt(dDistance2)) > MIN_MOVEMENT_DISTANCE) {
                    i20++;
                    iArr6[i19] = i22;
                }
                i19++;
                iArr7 = iArr11;
                iArr5 = iArr12;
                i3 = i25;
                distanceArr = distanceArr3;
            }
            int[] iArr13 = iArr5;
            int[][] iArr14 = iArr7;
            int i26 = i3;
            Distance[][] distanceArr4 = distanceArr;
            if (i20 == 0 && i14 != 0) {
                iArr3 = iArr10;
                break;
            }
            double[] dArr4 = new double[iMin];
            double[] dArr5 = new double[iMin];
            double[] dArr6 = new double[iMin];
            char c = 0;
            Arrays.fill(iArr10, 0);
            int i27 = 0;
            while (true) {
                i2 = i26;
                if (i27 >= i2) {
                    break;
                }
                int i28 = iArr6[i27];
                double[] dArr7 = dArr[i27];
                int i29 = iArr13[i27];
                iArr10[i28] = iArr10[i28] + i29;
                double d2 = i29;
                dArr4[i28] = (dArr7[c] * d2) + dArr4[i28];
                dArr5[i28] = (dArr7[1] * d2) + dArr5[i28];
                dArr6[i28] = (dArr7[2] * d2) + dArr6[i28];
                i27++;
                i14 = i14;
                i26 = i2;
                c = 0;
            }
            int i30 = i14;
            for (int i31 = 0; i31 < iMin; i31++) {
                int i32 = iArr10[i31];
                if (i32 == 0) {
                    dArr2[i31] = new double[]{0.0d, 0.0d, 0.0d};
                } else {
                    double d3 = i32;
                    double d4 = dArr4[i31] / d3;
                    double d5 = dArr5[i31] / d3;
                    double d6 = dArr6[i31] / d3;
                    dArr2[i31][0] = d4;
                    dArr2[i31][1] = d5;
                    dArr2[i31][2] = d6;
                }
            }
            iArr7 = iArr14;
            i14 = i30 + 1;
            iArr8 = iArr10;
            i3 = i2;
            iArr5 = iArr13;
            distanceArr = distanceArr4;
        }
        HashMap map2 = new HashMap();
        for (int i33 = 0; i33 < iMin; i33++) {
            int i34 = iArr3[i33];
            if (i34 != 0) {
                int i35 = pointProviderLab.toInt(dArr2[i33]);
                if (!map2.containsKey(Integer.valueOf(i35))) {
                    map2.put(Integer.valueOf(i35), Integer.valueOf(i34));
                }
            }
        }
        return map2;
    }
}
