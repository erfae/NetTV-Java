package com.google.android.material.textfield;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

/* JADX INFO: loaded from: classes2.dex */
class CutoutDrawable extends MaterialShapeDrawable {
    public static final /* synthetic */ int $r8$clinit = 0;

    @NonNull
    public final RectF cutoutBounds;

    public static class ImplApi14 extends CutoutDrawable {
        private Paint cutoutPaint;
        private int savedLayer;

        private Paint getCutoutPaint() {
            if (this.cutoutPaint == null) {
                Paint paint = new Paint(1);
                this.cutoutPaint = paint;
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                this.cutoutPaint.setColor(-1);
                this.cutoutPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            }
            return this.cutoutPaint;
        }

        private void postDraw(@NonNull Canvas canvas) {
            if (useHardwareLayer(getCallback())) {
                return;
            }
            canvas.restoreToCount(this.savedLayer);
        }

        private void preDraw(@NonNull Canvas canvas) {
            Drawable.Callback callback = getCallback();
            if (!useHardwareLayer(callback)) {
                saveCanvasLayer(canvas);
                return;
            }
            View view = (View) callback;
            if (view.getLayerType() != 2) {
                view.setLayerType(2, null);
            }
        }

        private void saveCanvasLayer(@NonNull Canvas canvas) {
            this.savedLayer = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null);
        }

        private boolean useHardwareLayer(Drawable.Callback callback) {
            return callback instanceof View;
        }

        @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
        public void draw(@NonNull Canvas canvas) {
            preDraw(canvas);
            super.draw(canvas);
            postDraw(canvas);
        }

        @Override // com.google.android.material.shape.MaterialShapeDrawable
        public final void drawStrokeShape(@NonNull Canvas canvas) {
            super.drawStrokeShape(canvas);
            canvas.drawRect(this.cutoutBounds, getCutoutPaint());
        }
    }

    @TargetApi(18)
    public static class ImplApi18 extends CutoutDrawable {
        public ImplApi18(@Nullable ShapeAppearanceModel shapeAppearanceModel) {
            super(shapeAppearanceModel);
        }

        @Override // com.google.android.material.shape.MaterialShapeDrawable
        public final void drawStrokeShape(@NonNull Canvas canvas) {
            if (this.cutoutBounds.isEmpty()) {
                super.drawStrokeShape(canvas);
                return;
            }
            canvas.save();
            if (Build.VERSION.SDK_INT >= 26) {
                canvas.clipOutRect(this.cutoutBounds);
            } else {
                canvas.clipRect(this.cutoutBounds, Region.Op.DIFFERENCE);
            }
            super.drawStrokeShape(canvas);
            canvas.restore();
        }
    }

    public final void setCutout(float f, float f2, float f3, float f4) {
        RectF rectF = this.cutoutBounds;
        if (f == rectF.left && f2 == rectF.top && f3 == rectF.right && f4 == rectF.bottom) {
            return;
        }
        rectF.set(f, f2, f3, f4);
        invalidateSelf();
    }

    private CutoutDrawable(@Nullable ShapeAppearanceModel shapeAppearanceModel) {
        super(shapeAppearanceModel == null ? new ShapeAppearanceModel() : shapeAppearanceModel);
        this.cutoutBounds = new RectF();
    }
}
