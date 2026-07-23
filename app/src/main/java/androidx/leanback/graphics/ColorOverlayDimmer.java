package androidx.leanback.graphics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
public final class ColorOverlayDimmer {
    private final float mActiveLevel;
    private int mAlpha;
    private float mAlphaFloat;
    private final float mDimmedLevel;
    private final Paint mPaint;

    private ColorOverlayDimmer(int i, float f, float f2) {
        f = f > 1.0f ? 1.0f : f;
        f = f < 0.0f ? 0.0f : f;
        f2 = f2 > 1.0f ? 1.0f : f2;
        float f3 = f2 >= 0.0f ? f2 : 0.0f;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(Color.rgb(Color.red(i), Color.green(i), Color.blue(i)));
        this.mActiveLevel = f;
        this.mDimmedLevel = f3;
        setActiveLevel(1.0f);
    }

    public static ColorOverlayDimmer createColorOverlayDimmer(int i, float f, float f2) {
        return new ColorOverlayDimmer(i, f, f2);
    }

    public static ColorOverlayDimmer createDefault(Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.styleable.LeanbackTheme);
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.LeanbackTheme_overlayDimMaskColor, context.getResources().getColor(R.color.lb_view_dim_mask_color));
        float fraction = typedArrayObtainStyledAttributes.getFraction(R.styleable.LeanbackTheme_overlayDimActiveLevel, 1, 1, context.getResources().getFraction(R.fraction.lb_view_active_level, 1, 0));
        float fraction2 = typedArrayObtainStyledAttributes.getFraction(R.styleable.LeanbackTheme_overlayDimDimmedLevel, 1, 1, context.getResources().getFraction(R.fraction.lb_view_dimmed_level, 1, 1));
        typedArrayObtainStyledAttributes.recycle();
        return new ColorOverlayDimmer(color, fraction, fraction2);
    }

    public int applyToColor(int i) {
        float f = 1.0f - this.mAlphaFloat;
        return Color.argb(Color.alpha(i), (int) (Color.red(i) * f), (int) (Color.green(i) * f), (int) (Color.blue(i) * f));
    }

    public void drawColorOverlay(Canvas canvas, View view, boolean z) {
        canvas.save();
        float translationX = view.getTranslationX() + view.getLeft();
        float translationY = view.getTranslationY() + view.getTop();
        canvas.translate(translationX, translationY);
        canvas.concat(view.getMatrix());
        canvas.translate(-translationX, -translationY);
        if (z) {
            canvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), this.mPaint);
        } else {
            canvas.drawRect(view.getPaddingLeft() + view.getLeft(), view.getPaddingTop() + view.getTop(), view.getRight() - view.getPaddingRight(), view.getBottom() - view.getPaddingBottom(), this.mPaint);
        }
        canvas.restore();
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public float getAlphaFloat() {
        return this.mAlphaFloat;
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public boolean needsDraw() {
        return this.mAlpha != 0;
    }

    public void setActiveLevel(float f) {
        float f2 = this.mDimmedLevel;
        float fM = Insets$$ExternalSyntheticOutline0.m(this.mActiveLevel, f2, f, f2);
        this.mAlphaFloat = fM;
        int i = (int) (fM * 255.0f);
        this.mAlpha = i;
        this.mPaint.setAlpha(i);
    }
}
