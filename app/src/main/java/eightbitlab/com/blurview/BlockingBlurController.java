package eightbitlab.com.blurview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
final class BlockingBlurController implements BlurController {
    public final View blurView;

    @Nullable
    private Drawable frameClearDrawable;
    private boolean hasFixedTransformationMatrix;
    private boolean initialized;
    private Bitmap internalBitmap;
    private BlurViewCanvas internalCanvas;
    private int overlayColor;
    private final ViewGroup rootView;
    private float blurRadius = 16.0f;
    private final int[] rootLocation = new int[2];
    private final int[] blurViewLocation = new int[2];
    private final SizeScaler sizeScaler = new SizeScaler(8.0f);
    private float scaleFactor = 1.0f;
    private final ViewTreeObserver.OnPreDrawListener drawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: eightbitlab.com.blurview.BlockingBlurController.1
        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            BlockingBlurController.this.updateBlur();
            return true;
        }
    };
    private boolean blurEnabled = true;
    private final Paint paint = new Paint(2);
    private BlurAlgorithm blurAlgorithm = new NoOpBlurAlgorithm();

    public BlockingBlurController(@NonNull View view, @NonNull ViewGroup viewGroup, @ColorInt int i) {
        this.rootView = viewGroup;
        this.blurView = view;
        this.overlayColor = i;
        init(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    private void allocateBitmap(int i, int i2) {
        SizeScaler.Size sizeScale = this.sizeScaler.scale(i, i2);
        this.scaleFactor = sizeScale.scaleFactor;
        this.internalBitmap = Bitmap.createBitmap(sizeScale.width, sizeScale.height, this.blurAlgorithm.getSupportedBitmapConfig());
    }

    private void blurAndSave() {
        this.internalBitmap = this.blurAlgorithm.blur(this.internalBitmap, this.blurRadius);
        if (this.blurAlgorithm.canModifyBitmap()) {
            return;
        }
        this.internalCanvas.setBitmap(this.internalBitmap);
    }

    private void setupInternalCanvasMatrix() {
        this.rootView.getLocationOnScreen(this.rootLocation);
        this.blurView.getLocationOnScreen(this.blurViewLocation);
        int[] iArr = this.blurViewLocation;
        int i = iArr[0];
        int[] iArr2 = this.rootLocation;
        int i2 = i - iArr2[0];
        int i3 = iArr[1] - iArr2[1];
        float f = -i2;
        float f2 = this.scaleFactor;
        this.internalCanvas.translate(f / f2, (-i3) / f2);
        BlurViewCanvas blurViewCanvas = this.internalCanvas;
        float f3 = this.scaleFactor;
        blurViewCanvas.scale(1.0f / f3, 1.0f / f3);
    }

    @Override // eightbitlab.com.blurview.BlurController
    public void destroy() {
        setBlurAutoUpdate(false);
        this.blurAlgorithm.destroy();
        this.initialized = false;
    }

    @Override // eightbitlab.com.blurview.BlurController
    public boolean draw(Canvas canvas) {
        if (this.blurEnabled && this.initialized) {
            if (canvas instanceof BlurViewCanvas) {
                return false;
            }
            updateBlur();
            canvas.save();
            float f = this.scaleFactor;
            canvas.scale(f, f);
            canvas.drawBitmap(this.internalBitmap, 0.0f, 0.0f, this.paint);
            canvas.restore();
            int i = this.overlayColor;
            if (i != 0) {
                canvas.drawColor(i);
            }
        }
        return true;
    }

    public final void init(int i, int i2) {
        if (this.sizeScaler.isZeroSized(i, i2)) {
            this.blurView.setWillNotDraw(true);
            return;
        }
        this.blurView.setWillNotDraw(false);
        allocateBitmap(i, i2);
        this.internalCanvas = new BlurViewCanvas(this.internalBitmap);
        this.initialized = true;
        if (this.hasFixedTransformationMatrix) {
            setupInternalCanvasMatrix();
        }
    }

    @Override // eightbitlab.com.blurview.BlurViewFacade
    public BlurViewFacade setBlurAlgorithm(BlurAlgorithm blurAlgorithm) {
        this.blurAlgorithm = blurAlgorithm;
        return this;
    }

    @Override // eightbitlab.com.blurview.BlurViewFacade
    public BlurViewFacade setBlurAutoUpdate(boolean z) {
        this.blurView.getViewTreeObserver().removeOnPreDrawListener(this.drawListener);
        if (z) {
            this.blurView.getViewTreeObserver().addOnPreDrawListener(this.drawListener);
        }
        return this;
    }

    @Override // eightbitlab.com.blurview.BlurViewFacade
    public BlurViewFacade setBlurEnabled(boolean z) {
        this.blurEnabled = z;
        setBlurAutoUpdate(z);
        this.blurView.invalidate();
        return this;
    }

    @Override // eightbitlab.com.blurview.BlurViewFacade
    public BlurViewFacade setBlurRadius(float f) {
        this.blurRadius = f;
        return this;
    }

    @Override // eightbitlab.com.blurview.BlurViewFacade
    public BlurViewFacade setFrameClearDrawable(@Nullable Drawable drawable) {
        this.frameClearDrawable = drawable;
        return this;
    }

    @Override // eightbitlab.com.blurview.BlurViewFacade
    public BlurViewFacade setHasFixedTransformationMatrix(boolean z) {
        this.hasFixedTransformationMatrix = z;
        return this;
    }

    @Override // eightbitlab.com.blurview.BlurViewFacade
    public BlurViewFacade setOverlayColor(int i) {
        if (this.overlayColor != i) {
            this.overlayColor = i;
            this.blurView.invalidate();
        }
        return this;
    }

    public final void updateBlur() {
        if (this.blurEnabled && this.initialized) {
            Drawable drawable = this.frameClearDrawable;
            if (drawable == null) {
                this.internalBitmap.eraseColor(0);
            } else {
                drawable.draw(this.internalCanvas);
            }
            if (this.hasFixedTransformationMatrix) {
                this.rootView.draw(this.internalCanvas);
            } else {
                this.internalCanvas.save();
                setupInternalCanvasMatrix();
                this.rootView.draw(this.internalCanvas);
                this.internalCanvas.restore();
            }
            blurAndSave();
        }
    }

    @Override // eightbitlab.com.blurview.BlurController
    public void updateBlurViewSize() {
        init(this.blurView.getMeasuredWidth(), this.blurView.getMeasuredHeight());
    }
}
