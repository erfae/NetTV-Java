package pl.droidsonroids.gif.transforms;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes2.dex */
public class CornerRadiusTransform implements Transform {
    private float mCornerRadius;
    private final RectF mDstRectF = new RectF();
    private Shader mShader;

    public CornerRadiusTransform(@FloatRange(from = 0.0d) float f) {
        setCornerRadiusSafely(f);
    }

    private void setCornerRadiusSafely(@FloatRange(from = 0.0d) float f) {
        float fMax = Math.max(0.0f, f);
        if (fMax != this.mCornerRadius) {
            this.mCornerRadius = fMax;
            this.mShader = null;
        }
    }

    @NonNull
    public RectF getBounds() {
        return this.mDstRectF;
    }

    @FloatRange(from = 0.0d)
    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    @Override // pl.droidsonroids.gif.transforms.Transform
    public void onBoundsChange(Rect rect) {
        this.mDstRectF.set(rect);
        this.mShader = null;
    }

    @Override // pl.droidsonroids.gif.transforms.Transform
    public void onDraw(Canvas canvas, Paint paint, Bitmap bitmap) {
        if (this.mCornerRadius == 0.0f) {
            canvas.drawBitmap(bitmap, (Rect) null, this.mDstRectF, paint);
            return;
        }
        if (this.mShader == null) {
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            this.mShader = new BitmapShader(bitmap, tileMode, tileMode);
            Matrix matrix = new Matrix();
            RectF rectF = this.mDstRectF;
            matrix.setTranslate(rectF.left, rectF.top);
            matrix.preScale(this.mDstRectF.width() / bitmap.getWidth(), this.mDstRectF.height() / bitmap.getHeight());
            this.mShader.setLocalMatrix(matrix);
        }
        paint.setShader(this.mShader);
        RectF rectF2 = this.mDstRectF;
        float f = this.mCornerRadius;
        canvas.drawRoundRect(rectF2, f, f, paint);
    }

    public void setCornerRadius(@FloatRange(from = 0.0d) float f) {
        setCornerRadiusSafely(f);
    }
}
