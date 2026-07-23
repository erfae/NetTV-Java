package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
public abstract class KeyCycleOscillator {
    private static final String TAG = "KeyCycleOscillator";
    private CurveFit mCurveFit;
    private CycleOscillator mCycleOscillator;
    private String mType;
    private int mWaveShape = 0;
    private String mWaveString = null;
    public int mVariesBy = 0;
    public ArrayList<WavePoint> mWavePoints = new ArrayList<>();

    public static class CoreSpline extends KeyCycleOscillator {
        public int typeId;

        public CoreSpline(String str) {
            this.typeId = TypedValues.CycleType.CC.getId(str);
        }

        @Override // androidx.constraintlayout.core.motion.utils.KeyCycleOscillator
        public void setProperty(MotionWidget motionWidget, float f) {
            motionWidget.setValue(this.typeId, get(f));
        }
    }

    public static class CycleOscillator {
        private static final String TAG = "CycleOscillator";
        private final int OFFST;
        private final int PHASE;
        private final int VALUE;
        public CurveFit mCurveFit;
        public float[] mOffset;
        public Oscillator mOscillator;
        public float[] mPeriod;
        public float[] mPhase;
        public double[] mPosition;
        public double[] mSplineSlopeCache;
        public double[] mSplineValueCache;
        public float[] mValues;
        private final int mVariesBy;

        public CycleOscillator(int i, String str, int i2, int i3) {
            Oscillator oscillator = new Oscillator();
            this.mOscillator = oscillator;
            this.OFFST = 0;
            this.PHASE = 1;
            this.VALUE = 2;
            this.mVariesBy = i2;
            oscillator.setType(i, str);
            this.mValues = new float[i3];
            this.mPosition = new double[i3];
            this.mPeriod = new float[i3];
            this.mOffset = new float[i3];
            this.mPhase = new float[i3];
            float[] fArr = new float[i3];
        }

        public double getLastPhase() {
            return this.mSplineValueCache[1];
        }

        public double getSlope(float f) {
            CurveFit curveFit = this.mCurveFit;
            if (curveFit != null) {
                double d = f;
                curveFit.getSlope(d, this.mSplineSlopeCache);
                this.mCurveFit.getPos(d, this.mSplineValueCache);
            } else {
                double[] dArr = this.mSplineSlopeCache;
                dArr[0] = 0.0d;
                dArr[1] = 0.0d;
                dArr[2] = 0.0d;
            }
            double d2 = f;
            double value = this.mOscillator.getValue(d2, this.mSplineValueCache[1]);
            double slope = this.mOscillator.getSlope(d2, this.mSplineValueCache[1], this.mSplineSlopeCache[1]);
            double[] dArr2 = this.mSplineSlopeCache;
            return (slope * this.mSplineValueCache[2]) + (value * dArr2[2]) + dArr2[0];
        }

        public double getValues(float f) {
            CurveFit curveFit = this.mCurveFit;
            if (curveFit != null) {
                curveFit.getPos(f, this.mSplineValueCache);
            } else {
                double[] dArr = this.mSplineValueCache;
                dArr[0] = this.mOffset[0];
                dArr[1] = this.mPhase[0];
                dArr[2] = this.mValues[0];
            }
            double[] dArr2 = this.mSplineValueCache;
            return (this.mOscillator.getValue(f, dArr2[1]) * this.mSplineValueCache[2]) + dArr2[0];
        }

        public void setPoint(int i, int i2, float f, float f2, float f3, float f4) {
            this.mPosition[i] = ((double) i2) / 100.0d;
            this.mPeriod[i] = f;
            this.mOffset[i] = f2;
            this.mPhase[i] = f3;
            this.mValues[i] = f4;
        }

        public void setup(float f) {
            double[][] dArr = (double[][]) Array.newInstance((Class<?>) double.class, this.mPosition.length, 3);
            float[] fArr = this.mValues;
            this.mSplineValueCache = new double[fArr.length + 2];
            this.mSplineSlopeCache = new double[fArr.length + 2];
            if (this.mPosition[0] > 0.0d) {
                this.mOscillator.addPoint(0.0d, this.mPeriod[0]);
            }
            double[] dArr2 = this.mPosition;
            int length = dArr2.length - 1;
            if (dArr2[length] < 1.0d) {
                this.mOscillator.addPoint(1.0d, this.mPeriod[length]);
            }
            for (int i = 0; i < dArr.length; i++) {
                dArr[i][0] = this.mOffset[i];
                dArr[i][1] = this.mPhase[i];
                dArr[i][2] = this.mValues[i];
                this.mOscillator.addPoint(this.mPosition[i], this.mPeriod[i]);
            }
            this.mOscillator.normalize();
            double[] dArr3 = this.mPosition;
            if (dArr3.length > 1) {
                this.mCurveFit = CurveFit.get(0, dArr3, dArr);
            } else {
                this.mCurveFit = null;
            }
        }
    }

    public static class IntDoubleSort {
        private IntDoubleSort() {
        }

        private static int partition(int[] iArr, float[] fArr, int i, int i2) {
            int i3 = iArr[i2];
            int i4 = i;
            while (i < i2) {
                if (iArr[i] <= i3) {
                    swap(iArr, fArr, i4, i);
                    i4++;
                }
                i++;
            }
            swap(iArr, fArr, i4, i2);
            return i4;
        }

        private static void swap(int[] iArr, float[] fArr, int i, int i2) {
            int i3 = iArr[i];
            iArr[i] = iArr[i2];
            iArr[i2] = i3;
            float f = fArr[i];
            fArr[i] = fArr[i2];
            fArr[i2] = f;
        }
    }

    public static class IntFloatFloatSort {
        private IntFloatFloatSort() {
        }

        private static int partition(int[] iArr, float[] fArr, float[] fArr2, int i, int i2) {
            int i3 = iArr[i2];
            int i4 = i;
            while (i < i2) {
                if (iArr[i] <= i3) {
                    swap(iArr, fArr, fArr2, i4, i);
                    i4++;
                }
                i++;
            }
            swap(iArr, fArr, fArr2, i4, i2);
            return i4;
        }

        private static void swap(int[] iArr, float[] fArr, float[] fArr2, int i, int i2) {
            int i3 = iArr[i];
            iArr[i] = iArr[i2];
            iArr[i2] = i3;
            float f = fArr[i];
            fArr[i] = fArr[i2];
            fArr[i2] = f;
            float f2 = fArr2[i];
            fArr2[i] = fArr2[i2];
            fArr2[i2] = f2;
        }
    }

    public static class PathRotateSet extends KeyCycleOscillator {
        public int typeId;

        public PathRotateSet(String str) {
            this.typeId = TypedValues.CycleType.CC.getId(str);
        }

        public void setPathRotate(MotionWidget motionWidget, float f, double d, double d2) {
            motionWidget.setRotationZ(get(f) + ((float) Math.toDegrees(Math.atan2(d2, d))));
        }

        @Override // androidx.constraintlayout.core.motion.utils.KeyCycleOscillator
        public void setProperty(MotionWidget motionWidget, float f) {
            motionWidget.setValue(this.typeId, get(f));
        }
    }

    public static class WavePoint {
        public float mOffset;
        public float mPeriod;
        public float mPhase;
        public int mPosition;
        public float mValue;

        public WavePoint(int i, float f, float f2, float f3, float f4) {
            this.mPosition = i;
            this.mValue = f4;
            this.mOffset = f2;
            this.mPeriod = f;
            this.mPhase = f3;
        }
    }

    public static KeyCycleOscillator makeWidgetCycle(String str) {
        return str.equals("pathRotate") ? new PathRotateSet(str) : new CoreSpline(str);
    }

    public float get(float f) {
        return (float) this.mCycleOscillator.getValues(f);
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    public float getSlope(float f) {
        return (float) this.mCycleOscillator.getSlope(f);
    }

    public void setCustom(Object obj) {
    }

    public void setPoint(int i, int i2, String str, int i3, float f, float f2, float f3, float f4, Object obj) {
        this.mWavePoints.add(new WavePoint(i, f, f2, f3, f4));
        if (i3 != -1) {
            this.mVariesBy = i3;
        }
        this.mWaveShape = i2;
        setCustom(obj);
        this.mWaveString = str;
    }

    public void setProperty(MotionWidget motionWidget, float f) {
    }

    public void setType(String str) {
        this.mType = str;
    }

    public void setup(float f) {
        int size = this.mWavePoints.size();
        if (size == 0) {
            return;
        }
        Collections.sort(this.mWavePoints, new Comparator<WavePoint>() { // from class: androidx.constraintlayout.core.motion.utils.KeyCycleOscillator.1
            @Override // java.util.Comparator
            public int compare(WavePoint wavePoint, WavePoint wavePoint2) {
                return Integer.compare(wavePoint.mPosition, wavePoint2.mPosition);
            }
        });
        double[] dArr = new double[size];
        char c = 0;
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) double.class, size, 3);
        this.mCycleOscillator = new CycleOscillator(this.mWaveShape, this.mWaveString, this.mVariesBy, size);
        int i = 0;
        for (WavePoint wavePoint : this.mWavePoints) {
            float f2 = wavePoint.mPeriod;
            dArr[i] = ((double) f2) * 0.01d;
            double[] dArr3 = dArr2[i];
            float f3 = wavePoint.mValue;
            dArr3[c] = f3;
            double[] dArr4 = dArr2[i];
            float f4 = wavePoint.mOffset;
            dArr4[1] = f4;
            double[] dArr5 = dArr2[i];
            float f5 = wavePoint.mPhase;
            dArr5[2] = f5;
            this.mCycleOscillator.setPoint(i, wavePoint.mPosition, f2, f4, f5, f3);
            i++;
            c = 0;
        }
        this.mCycleOscillator.setup(f);
        this.mCurveFit = CurveFit.get(0, dArr, dArr2);
    }

    public String toString() {
        String string = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (WavePoint wavePoint : this.mWavePoints) {
            StringBuilder sbM25m = Insets$$ExternalSyntheticOutline0.m25m(string, "[");
            sbM25m.append(wavePoint.mPosition);
            sbM25m.append(" , ");
            sbM25m.append(decimalFormat.format(wavePoint.mValue));
            sbM25m.append("] ");
            string = sbM25m.toString();
        }
        return string;
    }

    public boolean variesByPath() {
        return this.mVariesBy == 1;
    }

    public void setPoint(int i, int i2, String str, int i3, float f, float f2, float f3, float f4) {
        this.mWavePoints.add(new WavePoint(i, f, f2, f3, f4));
        if (i3 != -1) {
            this.mVariesBy = i3;
        }
        this.mWaveShape = i2;
        this.mWaveString = str;
    }
}
