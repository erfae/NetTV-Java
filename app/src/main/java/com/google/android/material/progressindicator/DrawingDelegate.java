package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;

/* JADX INFO: loaded from: classes.dex */
abstract class DrawingDelegate<S extends BaseProgressIndicatorSpec> {
    public DrawableWithAnimatedVisibilityChange drawable;
    public S spec;

    public DrawingDelegate(S s) {
        this.spec = s;
    }

    public abstract void adjustCanvas(@NonNull Canvas canvas, @NonNull Rect rect, @FloatRange(from = 0.0d, to = 1.0d) float f);

    public abstract void fillIndicator(@NonNull Canvas canvas, @NonNull Paint paint, @FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2, @ColorInt int i);

    public abstract void fillTrack(@NonNull Canvas canvas, @NonNull Paint paint);

    public abstract int getPreferredHeight();

    public abstract int getPreferredWidth();
}
