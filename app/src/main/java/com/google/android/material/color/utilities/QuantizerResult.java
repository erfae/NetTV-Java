package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class QuantizerResult {
    public final Map<Integer, Integer> colorToCount;

    public QuantizerResult(Map<Integer, Integer> map) {
        this.colorToCount = map;
    }
}
