package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class TonalPalette {
    public Map<Integer, Integer> cache = new HashMap();
    public double chroma;
    public double hue;

    private TonalPalette(double d, double d2) {
        this.hue = d;
        this.chroma = d2;
    }

    public static final TonalPalette fromHueAndChroma(double d, double d2) {
        return new TonalPalette(d, d2);
    }

    public static final TonalPalette fromInt(int i) {
        Hct hctFromInt = Hct.fromInt(i);
        return fromHueAndChroma(hctFromInt.getHue(), hctFromInt.getChroma());
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.HashMap, java.util.Map<java.lang.Integer, java.lang.Integer>] */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.util.HashMap, java.util.Map<java.lang.Integer, java.lang.Integer>] */
    public int tone(int i) {
        Integer numValueOf = (Integer) this.cache.get(Integer.valueOf(i));
        if (numValueOf == null) {
            numValueOf = Integer.valueOf(Hct.from(this.hue, this.chroma, i).toInt());
            this.cache.put(Integer.valueOf(i), numValueOf);
        }
        return numValueOf.intValue();
    }
}
