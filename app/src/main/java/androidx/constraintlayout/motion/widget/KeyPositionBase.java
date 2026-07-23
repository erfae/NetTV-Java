package androidx.constraintlayout.motion.widget;

import android.graphics.RectF;
import android.view.View;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
abstract class KeyPositionBase extends Key {
    public int mCurveFit = Key.UNSET;

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void getAttributeNames(HashSet<String> attributes) {
    }

    public abstract boolean intersects(int layoutWidth, int layoutHeight, RectF start, RectF end, float x, float y);

    public abstract void positionAttributes(View view, RectF start, RectF end, float x, float y, String[] attribute, float[] value);
}
