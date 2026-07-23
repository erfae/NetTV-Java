package eightbitlab.com.blurview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes2.dex */
public class BlurView extends FrameLayout {
    private static final String TAG = "BlurView";
    public BlurController blurController;

    @ColorInt
    private int overlayColor;

    public BlurView(Context context) {
        super(context);
        this.blurController = new NoOpController();
        init(null, 0);
    }

    private void init(AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.BlurView, i, 0);
        this.overlayColor = typedArrayObtainStyledAttributes.getColor(R.styleable.BlurView_blurOverlayColor, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (this.blurController.draw(canvas)) {
            super.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isHardwareAccelerated()) {
            this.blurController.setBlurAutoUpdate(true);
        } else {
            Log.e(TAG, "BlurView can't be used in not hardware-accelerated window!");
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.blurController.setBlurAutoUpdate(false);
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.blurController.updateBlurViewSize();
    }

    public BlurViewFacade setBlurAutoUpdate(boolean z) {
        return this.blurController.setBlurAutoUpdate(z);
    }

    public BlurViewFacade setBlurEnabled(boolean z) {
        return this.blurController.setBlurEnabled(z);
    }

    public BlurViewFacade setBlurRadius(float f) {
        return this.blurController.setBlurRadius(f);
    }

    public BlurViewFacade setOverlayColor(@ColorInt int i) {
        this.overlayColor = i;
        return this.blurController.setOverlayColor(i);
    }

    public BlurViewFacade setupWith(@NonNull ViewGroup viewGroup) {
        BlockingBlurController blockingBlurController = new BlockingBlurController(this, viewGroup, this.overlayColor);
        this.blurController.destroy();
        this.blurController = blockingBlurController;
        return blockingBlurController;
    }

    public BlurView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.blurController = new NoOpController();
        init(attributeSet, 0);
    }

    public BlurView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.blurController = new NoOpController();
        init(attributeSet, i);
    }
}
