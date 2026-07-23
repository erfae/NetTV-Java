package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class QuantizerWu implements Quantizer {
    private static final int INDEX_BITS = 5;
    private static final int INDEX_COUNT = 33;
    private static final int TOTAL_SIZE = 35937;
    public Box[] cubes;
    public double[] moments;
    public int[] momentsB;
    public int[] momentsG;
    public int[] momentsR;
    public int[] weights;

    /* JADX INFO: renamed from: com.google.android.material.color.utilities.QuantizerWu$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction;

        static {
            int[] iArr = new int[Direction.values().length];
            $SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction = iArr;
            try {
                iArr[Direction.RED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction[Direction.GREEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction[Direction.BLUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static final class CreateBoxesResult {
    }

    public enum Direction {
        RED,
        GREEN,
        BLUE
    }

    public static final class MaximizeResult {
        public int cutLocation;
        public double maximum;

        public MaximizeResult(int i, double d) {
            this.cutLocation = i;
            this.maximum = d;
        }
    }

    public static int bottom(Box box, Direction direction, int[] iArr) {
        int i;
        int i2;
        int i3 = AnonymousClass1.$SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction[direction.ordinal()];
        if (i3 == 1) {
            i = (-iArr[getIndex(box.r0, box.g1, box.b1)]) + iArr[getIndex(box.r0, box.g1, box.b0)] + iArr[getIndex(box.r0, box.g0, box.b1)];
            i2 = iArr[getIndex(box.r0, box.g0, box.b0)];
        } else if (i3 == 2) {
            i = (-iArr[getIndex(box.r1, box.g0, box.b1)]) + iArr[getIndex(box.r1, box.g0, box.b0)] + iArr[getIndex(box.r0, box.g0, box.b1)];
            i2 = iArr[getIndex(box.r0, box.g0, box.b0)];
        } else {
            if (i3 != 3) {
                throw new IllegalArgumentException("unexpected direction " + direction);
            }
            i = (-iArr[getIndex(box.r1, box.g1, box.b0)]) + iArr[getIndex(box.r1, box.g0, box.b0)] + iArr[getIndex(box.r0, box.g1, box.b0)];
            i2 = iArr[getIndex(box.r0, box.g0, box.b0)];
        }
        return i - i2;
    }

    public static int getIndex(int i, int i2, int i3) {
        return (i << 10) + (i << 6) + i + (i2 << 5) + i2 + i3;
    }

    public static int top(Box box, Direction direction, int i, int[] iArr) {
        int i2;
        int i3;
        int i4 = AnonymousClass1.$SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction[direction.ordinal()];
        if (i4 == 1) {
            i2 = (iArr[getIndex(i, box.g1, box.b1)] - iArr[getIndex(i, box.g1, box.b0)]) - iArr[getIndex(i, box.g0, box.b1)];
            i3 = iArr[getIndex(i, box.g0, box.b0)];
        } else if (i4 == 2) {
            i2 = (iArr[getIndex(box.r1, i, box.b1)] - iArr[getIndex(box.r1, i, box.b0)]) - iArr[getIndex(box.r0, i, box.b1)];
            i3 = iArr[getIndex(box.r0, i, box.b0)];
        } else {
            if (i4 != 3) {
                throw new IllegalArgumentException("unexpected direction " + direction);
            }
            i2 = (iArr[getIndex(box.r1, box.g1, i)] - iArr[getIndex(box.r1, box.g0, i)]) - iArr[getIndex(box.r0, box.g1, i)];
            i3 = iArr[getIndex(box.r0, box.g0, i)];
        }
        return i2 + i3;
    }

    public static int volume(Box box, int[] iArr) {
        return ((((((iArr[getIndex(box.r1, box.g1, box.b1)] - iArr[getIndex(box.r1, box.g1, box.b0)]) - iArr[getIndex(box.r1, box.g0, box.b1)]) + iArr[getIndex(box.r1, box.g0, box.b0)]) - iArr[getIndex(box.r0, box.g1, box.b1)]) + iArr[getIndex(box.r0, box.g1, box.b0)]) + iArr[getIndex(box.r0, box.g0, box.b1)]) - iArr[getIndex(box.r0, box.g0, box.b0)];
    }

    public final MaximizeResult maximize(Box box, Direction direction, int i, int i2, int i3, int i4, int i5, int i6) {
        int i7;
        QuantizerWu quantizerWu = this;
        Box box2 = box;
        Direction direction2 = direction;
        int iBottom = bottom(box2, direction2, quantizerWu.momentsR);
        int iBottom2 = bottom(box2, direction2, quantizerWu.momentsG);
        int iBottom3 = bottom(box2, direction2, quantizerWu.momentsB);
        int iBottom4 = bottom(box2, direction2, quantizerWu.weights);
        double d = 0.0d;
        int i8 = -1;
        int i9 = i;
        while (i9 < i2) {
            int pVar = top(box2, direction2, i9, quantizerWu.momentsR) + iBottom;
            int pVar2 = top(box2, direction2, i9, quantizerWu.momentsG) + iBottom2;
            int pVar3 = top(box2, direction2, i9, quantizerWu.momentsB) + iBottom3;
            int pVar4 = top(box2, direction2, i9, quantizerWu.weights) + iBottom4;
            if (pVar4 == 0) {
                i7 = iBottom;
            } else {
                i7 = iBottom;
                double d2 = ((double) ((pVar3 * pVar3) + ((pVar2 * pVar2) + (pVar * pVar)))) / ((double) pVar4);
                int i10 = i3 - pVar;
                int i11 = i4 - pVar2;
                int i12 = i5 - pVar3;
                int i13 = i6 - pVar4;
                if (i13 != 0) {
                    double d3 = (((double) ((i12 * i12) + ((i11 * i11) + (i10 * i10)))) / ((double) i13)) + d2;
                    if (d3 > d) {
                        d = d3;
                        i8 = i9;
                    }
                }
            }
            i9++;
            quantizerWu = this;
            box2 = box;
            direction2 = direction;
            iBottom = i7;
        }
        return new MaximizeResult(i8, d);
    }

    /* JADX WARN: Code duplicated, block: B:47:0x026d  */
    /* JADX WARN: Code duplicated, block: B:49:0x0276  */
    /* JADX WARN: Code duplicated, block: B:50:0x027d  */
    /* JADX WARN: Code duplicated, block: B:53:0x0289  */
    /* JADX WARN: Code duplicated, block: B:54:0x0290  */
    /* JADX WARN: Code duplicated, block: B:56:0x0297  */
    /* JADX WARN: Code duplicated, block: B:59:0x02a5  */
    /* JADX WARN: Code duplicated, block: B:61:0x02ab  */
    /* JADX WARN: Code duplicated, block: B:66:0x02bb A[LOOP:5: B:20:0x0167->B:66:0x02bb, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:85:0x02b8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x02ae A[SYNTHETIC] */
    @Override // com.google.android.material.color.utilities.Quantizer
    public QuantizerResult quantize(int[] iArr, int i) {
        int i2;
        Boolean bool;
        int i3;
        double d;
        int i4;
        Box[] boxArr;
        double dVariance;
        Box[] boxArr2;
        double dVariance2;
        int i5 = i;
        Map<Integer, Integer> map = new QuantizerMap().quantize(iArr, i5).colorToCount;
        this.weights = new int[TOTAL_SIZE];
        this.momentsR = new int[TOTAL_SIZE];
        this.momentsG = new int[TOTAL_SIZE];
        this.momentsB = new int[TOTAL_SIZE];
        this.moments = new double[TOTAL_SIZE];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int iIntValue = entry.getKey().intValue();
            int iIntValue2 = entry.getValue().intValue();
            int iRedFromArgb = ColorUtils.redFromArgb(iIntValue);
            int iGreenFromArgb = ColorUtils.greenFromArgb(iIntValue);
            int iBlueFromArgb = ColorUtils.blueFromArgb(iIntValue);
            int index = getIndex((iRedFromArgb >> 3) + 1, (iGreenFromArgb >> 3) + 1, (iBlueFromArgb >> 3) + 1);
            int[] iArr2 = this.weights;
            iArr2[index] = iArr2[index] + iIntValue2;
            int[] iArr3 = this.momentsR;
            iArr3[index] = (iRedFromArgb * iIntValue2) + iArr3[index];
            int[] iArr4 = this.momentsG;
            iArr4[index] = (iGreenFromArgb * iIntValue2) + iArr4[index];
            int[] iArr5 = this.momentsB;
            iArr5[index] = (iBlueFromArgb * iIntValue2) + iArr5[index];
            double[] dArr = this.moments;
            dArr[index] = dArr[index] + ((double) (((iBlueFromArgb * iBlueFromArgb) + (iGreenFromArgb * iGreenFromArgb) + (iRedFromArgb * iRedFromArgb)) * iIntValue2));
        }
        int i6 = 1;
        while (true) {
            int i7 = 33;
            if (i6 >= 33) {
                break;
            }
            int[] iArr6 = new int[33];
            int[] iArr7 = new int[33];
            int[] iArr8 = new int[33];
            int[] iArr9 = new int[33];
            double[] dArr2 = new double[33];
            int i8 = 1;
            while (i8 < i7) {
                int i9 = 1;
                int i10 = 0;
                int i11 = 0;
                int i12 = 0;
                int i13 = 0;
                double d2 = 0.0d;
                while (i9 < i7) {
                    int index2 = getIndex(i6, i8, i9);
                    i10 += this.weights[index2];
                    i11 += this.momentsR[index2];
                    i12 += this.momentsG[index2];
                    i13 += this.momentsB[index2];
                    d2 += this.moments[index2];
                    iArr6[i9] = iArr6[i9] + i10;
                    iArr7[i9] = iArr7[i9] + i11;
                    iArr8[i9] = iArr8[i9] + i12;
                    iArr9[i9] = iArr9[i9] + i13;
                    dArr2[i9] = dArr2[i9] + d2;
                    int index3 = getIndex(i6 - 1, i8, i9);
                    int[] iArr10 = this.weights;
                    iArr10[index2] = iArr10[index3] + iArr6[i9];
                    int[] iArr11 = this.momentsR;
                    iArr11[index2] = iArr11[index3] + iArr7[i9];
                    int[] iArr12 = this.momentsG;
                    iArr12[index2] = iArr12[index3] + iArr8[i9];
                    int[] iArr13 = this.momentsB;
                    iArr13[index2] = iArr13[index3] + iArr9[i9];
                    double[] dArr3 = this.moments;
                    dArr3[index2] = dArr3[index3] + dArr2[i9];
                    i9++;
                    i7 = 33;
                }
                i8++;
                i7 = 33;
            }
            i6++;
        }
        this.cubes = new Box[i5];
        for (int i14 = 0; i14 < i5; i14++) {
            this.cubes[i14] = new Box(null);
        }
        double[] dArr4 = new double[i5];
        Box box = this.cubes[0];
        box.r1 = 32;
        box.g1 = 32;
        box.b1 = 32;
        int i15 = 1;
        int i16 = 0;
        while (true) {
            if (i15 >= i5) {
                i2 = i;
                break;
            }
            Box[] boxArr3 = this.cubes;
            Box box2 = boxArr3[i16];
            Box box3 = boxArr3[i15];
            int iVolume = volume(box2, this.momentsR);
            int iVolume2 = volume(box2, this.momentsG);
            int iVolume3 = volume(box2, this.momentsB);
            int iVolume4 = volume(box2, this.weights);
            Direction direction = Direction.RED;
            MaximizeResult maximizeResultMaximize = maximize(box2, direction, box2.r0 + 1, box2.r1, iVolume, iVolume2, iVolume3, iVolume4);
            Direction direction2 = Direction.GREEN;
            MaximizeResult maximizeResultMaximize2 = maximize(box2, direction2, box2.g0 + 1, box2.g1, iVolume, iVolume2, iVolume3, iVolume4);
            Direction direction3 = Direction.BLUE;
            int i17 = i15;
            MaximizeResult maximizeResultMaximize3 = maximize(box2, direction3, box2.b0 + 1, box2.b1, iVolume, iVolume2, iVolume3, iVolume4);
            double d3 = maximizeResultMaximize.maximum;
            double d4 = maximizeResultMaximize2.maximum;
            double d5 = maximizeResultMaximize3.maximum;
            if (d3 < d4 || d3 < d5) {
                direction = (d4 < d3 || d4 < d5) ? direction3 : direction2;
            } else {
                if (maximizeResultMaximize.cutLocation < 0) {
                    bool = Boolean.FALSE;
                }
                if (bool.booleanValue()) {
                    boxArr = this.cubes;
                    if (boxArr[i16].vol > 1) {
                        dVariance = variance(boxArr[i16]);
                    } else {
                        dVariance = 0.0d;
                    }
                    dArr4[i16] = dVariance;
                    boxArr2 = this.cubes;
                    if (boxArr2[i17].vol > 1) {
                        dVariance2 = variance(boxArr2[i17]);
                    } else {
                        dVariance2 = 0.0d;
                    }
                    dArr4[i17] = dVariance2;
                    i3 = i17;
                } else {
                    dArr4[i16] = 0.0d;
                    i3 = i17 - 1;
                }
                d = dArr4[0];
                i16 = 0;
                for (i4 = 1; i4 <= i3; i4++) {
                    if (dArr4[i4] > d) {
                        d = dArr4[i4];
                        i16 = i4;
                    }
                }
                if (d <= 0.0d) {
                    i2 = i3 + 1;
                    break;
                }
                i15 = i3 + 1;
                i5 = i;
            }
            box3.r1 = box2.r1;
            box3.g1 = box2.g1;
            box3.b1 = box2.b1;
            int i18 = AnonymousClass1.$SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction[direction.ordinal()];
            if (i18 == 1) {
                int i19 = maximizeResultMaximize.cutLocation;
                box2.r1 = i19;
                box3.r0 = i19;
                box3.g0 = box2.g0;
                box3.b0 = box2.b0;
            } else if (i18 == 2) {
                int i20 = maximizeResultMaximize2.cutLocation;
                box2.g1 = i20;
                box3.r0 = box2.r0;
                box3.g0 = i20;
                box3.b0 = box2.b0;
            } else if (i18 == 3) {
                int i21 = maximizeResultMaximize3.cutLocation;
                box2.b1 = i21;
                box3.r0 = box2.r0;
                box3.g0 = box2.g0;
                box3.b0 = i21;
            }
            box2.vol = (box2.b1 - box2.b0) * (box2.g1 - box2.g0) * (box2.r1 - box2.r0);
            box3.vol = (box3.b1 - box3.b0) * (box3.g1 - box3.g0) * (box3.r1 - box3.r0);
            bool = Boolean.TRUE;
            if (bool.booleanValue()) {
                boxArr = this.cubes;
                if (boxArr[i16].vol > 1) {
                    dVariance = variance(boxArr[i16]);
                } else {
                    dVariance = 0.0d;
                }
                dArr4[i16] = dVariance;
                boxArr2 = this.cubes;
                if (boxArr2[i17].vol > 1) {
                    dVariance2 = variance(boxArr2[i17]);
                } else {
                    dVariance2 = 0.0d;
                }
                dArr4[i17] = dVariance2;
                i3 = i17;
            } else {
                dArr4[i16] = 0.0d;
                i3 = i17 - 1;
            }
            d = dArr4[0];
            i16 = 0;
            while (i4 <= i3) {
                if (dArr4[i4] > d) {
                    d = dArr4[i4];
                    i16 = i4;
                }
            }
            if (d <= 0.0d) {
                i2 = i3 + 1;
                break;
            }
            i15 = i3 + 1;
            i5 = i;
        }
        ArrayList arrayList = new ArrayList();
        for (int i22 = 0; i22 < i2; i22++) {
            Box box4 = this.cubes[i22];
            int iVolume5 = volume(box4, this.weights);
            if (iVolume5 > 0) {
                arrayList.add(Integer.valueOf(((volume(box4, this.momentsB) / iVolume5) & 255) | (-16777216) | (((volume(box4, this.momentsR) / iVolume5) & 255) << 16) | (((volume(box4, this.momentsG) / iVolume5) & 255) << 8)));
            }
        }
        HashMap map2 = new HashMap();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            map2.put(Integer.valueOf(((Integer) it.next()).intValue()), 0);
        }
        return new QuantizerResult(map2);
    }

    public final double variance(Box box) {
        int iVolume = volume(box, this.momentsR);
        int iVolume2 = volume(box, this.momentsG);
        int iVolume3 = volume(box, this.momentsB);
        int i = iVolume3 * iVolume3;
        return (((((((this.moments[getIndex(box.r1, box.g1, box.b1)] - this.moments[getIndex(box.r1, box.g1, box.b0)]) - this.moments[getIndex(box.r1, box.g0, box.b1)]) + this.moments[getIndex(box.r1, box.g0, box.b0)]) - this.moments[getIndex(box.r0, box.g1, box.b1)]) + this.moments[getIndex(box.r0, box.g1, box.b0)]) + this.moments[getIndex(box.r0, box.g0, box.b1)]) - this.moments[getIndex(box.r0, box.g0, box.b0)]) - (((double) (i + ((iVolume2 * iVolume2) + (iVolume * iVolume)))) / ((double) volume(box, this.weights)));
    }

    public static final class Box {
        public int b0;
        public int b1;
        public int g0;
        public int g1;
        public int r0;
        public int r1;
        public int vol;

        private Box() {
            this.r0 = 0;
            this.r1 = 0;
            this.g0 = 0;
            this.g1 = 0;
            this.b0 = 0;
            this.b1 = 0;
            this.vol = 0;
        }

        public /* synthetic */ Box(AnonymousClass1 anonymousClass1) {
            this();
        }
    }
}
