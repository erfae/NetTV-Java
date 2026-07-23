package androidx.leanback.animation;

import android.animation.TimeInterpolator;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class LogDecelerateInterpolator implements TimeInterpolator {
    public int mBase;
    public int mDrift;
    public final float mLogScale;

    public LogDecelerateInterpolator(int i, int i2) {
        this.mBase = i;
        this.mDrift = i2;
        this.mLogScale = 1.0f / ((i2 * 1.0f) + (((float) (-Math.pow(i, -1.0f))) + 1.0f));
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return ((this.mDrift * f) + ((float) (-Math.pow(this.mBase, -f))) + 1.0f) * this.mLogScale;
    }
}
