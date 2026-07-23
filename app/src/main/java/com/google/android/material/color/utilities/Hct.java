package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class Hct {
    private int argb;
    private double chroma;
    private double hue;
    private double tone;

    private Hct(int i) {
        setInternalState(i);
    }

    public static Hct from(double d, double d2, double d3) {
        return new Hct(HctSolver.solveToInt(d, d2, d3));
    }

    public static Hct fromInt(int i) {
        return new Hct(i);
    }

    private void setInternalState(int i) {
        this.argb = i;
        Cam16 cam16FromInt = Cam16.fromInt(i);
        this.hue = cam16FromInt.getHue();
        this.chroma = cam16FromInt.getChroma();
        this.tone = ColorUtils.lstarFromArgb(i);
    }

    public double getChroma() {
        return this.chroma;
    }

    public double getHue() {
        return this.hue;
    }

    public double getTone() {
        return this.tone;
    }

    public void setChroma(double d) {
        setInternalState(HctSolver.solveToInt(this.hue, d, this.tone));
    }

    public void setHue(double d) {
        setInternalState(HctSolver.solveToInt(d, this.chroma, this.tone));
    }

    public void setTone(double d) {
        setInternalState(HctSolver.solveToInt(this.hue, this.chroma, d));
    }

    public int toInt() {
        return this.argb;
    }
}
