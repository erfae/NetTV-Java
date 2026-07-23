package com.google.android.material.transition;

/* JADX INFO: loaded from: classes2.dex */
class FadeModeResult {
    public final int endAlpha;
    public final boolean endOnTop;
    public final int startAlpha;

    private FadeModeResult(int i, int i2, boolean z) {
        this.startAlpha = i;
        this.endAlpha = i2;
        this.endOnTop = z;
    }

    public static FadeModeResult endOnTop(int i) {
        return new FadeModeResult(255, i, true);
    }

    public static FadeModeResult startOnTop(int i, int i2) {
        return new FadeModeResult(i, i2, false);
    }
}
