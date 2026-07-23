package com.google.android.exoplayer2.ui;

import android.graphics.Color;
import androidx.annotation.ColorInt;
import com.google.android.exoplayer2.util.Util;

/* JADX INFO: loaded from: classes.dex */
final class HtmlUtils {
    private HtmlUtils() {
    }

    public static String cssAllClassDescendantsSelector(String str) {
        return "." + str + ",." + str + " *";
    }

    public static String toCssRgba(@ColorInt int i) {
        return Util.formatInvariant("rgba(%d,%d,%d,%.3f)", Integer.valueOf(Color.red(i)), Integer.valueOf(Color.green(i)), Integer.valueOf(Color.blue(i)), Double.valueOf(((double) Color.alpha(i)) / 255.0d));
    }
}
