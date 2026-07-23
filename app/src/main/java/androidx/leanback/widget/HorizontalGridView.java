package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.leanback.R;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class HorizontalGridView extends BaseGridView {
    private boolean mFadingHighEdge;
    private boolean mFadingLowEdge;
    private LinearGradient mHighFadeShader;
    private int mHighFadeShaderLength;
    private int mHighFadeShaderOffset;
    private LinearGradient mLowFadeShader;
    private int mLowFadeShaderLength;
    private int mLowFadeShaderOffset;
    private Bitmap mTempBitmapHigh;
    private Bitmap mTempBitmapLow;
    private Paint mTempPaint;
    private Rect mTempRect;

    public HorizontalGridView(Context context) {
        this(context, null);
    }

    private Bitmap getTempBitmapHigh() {
        Bitmap bitmap = this.mTempBitmapHigh;
        if (bitmap == null || bitmap.getWidth() != this.mHighFadeShaderLength || this.mTempBitmapHigh.getHeight() != getHeight()) {
            this.mTempBitmapHigh = Bitmap.createBitmap(this.mHighFadeShaderLength, getHeight(), Bitmap.Config.ARGB_8888);
        }
        return this.mTempBitmapHigh;
    }

    private Bitmap getTempBitmapLow() {
        Bitmap bitmap = this.mTempBitmapLow;
        if (bitmap == null || bitmap.getWidth() != this.mLowFadeShaderLength || this.mTempBitmapLow.getHeight() != getHeight()) {
            this.mTempBitmapLow = Bitmap.createBitmap(this.mLowFadeShaderLength, getHeight(), Bitmap.Config.ARGB_8888);
        }
        return this.mTempBitmapLow;
    }

    private boolean needsFadingHighEdge() {
        if (!this.mFadingHighEdge) {
            return false;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            Objects.requireNonNull(this.mLayoutManager);
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) childAt.getLayoutParams();
            Objects.requireNonNull(layoutParams);
            if (childAt.getRight() - layoutParams.mRightInset > (getWidth() - getPaddingRight()) + this.mHighFadeShaderOffset) {
                return true;
            }
        }
        return false;
    }

    private boolean needsFadingLowEdge() {
        if (!this.mFadingLowEdge) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            Objects.requireNonNull(this.mLayoutManager);
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) childAt.getLayoutParams();
            Objects.requireNonNull(layoutParams);
            if (childAt.getLeft() + layoutParams.mLeftInset < getPaddingLeft() - this.mLowFadeShaderOffset) {
                return true;
            }
        }
        return false;
    }

    private void updateLayerType() {
        if (this.mFadingLowEdge || this.mFadingHighEdge) {
            setLayerType(2, null);
            setWillNotDraw(false);
        } else {
            setLayerType(0, null);
            setWillNotDraw(true);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public void draw(Canvas canvas) {
        boolean zNeedsFadingLowEdge = needsFadingLowEdge();
        boolean zNeedsFadingHighEdge = needsFadingHighEdge();
        if (!zNeedsFadingLowEdge) {
            this.mTempBitmapLow = null;
        }
        if (!zNeedsFadingHighEdge) {
            this.mTempBitmapHigh = null;
        }
        if (!zNeedsFadingLowEdge && !zNeedsFadingHighEdge) {
            super.draw(canvas);
            return;
        }
        int paddingLeft = this.mFadingLowEdge ? (getPaddingLeft() - this.mLowFadeShaderOffset) - this.mLowFadeShaderLength : 0;
        int width = this.mFadingHighEdge ? (getWidth() - getPaddingRight()) + this.mHighFadeShaderOffset + this.mHighFadeShaderLength : getWidth();
        int iSave = canvas.save();
        canvas.clipRect((this.mFadingLowEdge ? this.mLowFadeShaderLength : 0) + paddingLeft, 0, width - (this.mFadingHighEdge ? this.mHighFadeShaderLength : 0), getHeight());
        super.draw(canvas);
        canvas.restoreToCount(iSave);
        Canvas canvas2 = new Canvas();
        Rect rect = this.mTempRect;
        rect.top = 0;
        rect.bottom = getHeight();
        if (zNeedsFadingLowEdge && this.mLowFadeShaderLength > 0) {
            Bitmap tempBitmapLow = getTempBitmapLow();
            tempBitmapLow.eraseColor(0);
            canvas2.setBitmap(tempBitmapLow);
            int iSave2 = canvas2.save();
            canvas2.clipRect(0, 0, this.mLowFadeShaderLength, getHeight());
            float f = -paddingLeft;
            canvas2.translate(f, 0.0f);
            super.draw(canvas2);
            canvas2.restoreToCount(iSave2);
            this.mTempPaint.setShader(this.mLowFadeShader);
            canvas2.drawRect(0.0f, 0.0f, this.mLowFadeShaderLength, getHeight(), this.mTempPaint);
            Rect rect2 = this.mTempRect;
            rect2.left = 0;
            rect2.right = this.mLowFadeShaderLength;
            canvas.translate(paddingLeft, 0.0f);
            Rect rect3 = this.mTempRect;
            canvas.drawBitmap(tempBitmapLow, rect3, rect3, (Paint) null);
            canvas.translate(f, 0.0f);
        }
        if (!zNeedsFadingHighEdge || this.mHighFadeShaderLength <= 0) {
            return;
        }
        Bitmap tempBitmapHigh = getTempBitmapHigh();
        tempBitmapHigh.eraseColor(0);
        canvas2.setBitmap(tempBitmapHigh);
        int iSave3 = canvas2.save();
        canvas2.clipRect(0, 0, this.mHighFadeShaderLength, getHeight());
        canvas2.translate(-(width - this.mHighFadeShaderLength), 0.0f);
        super.draw(canvas2);
        canvas2.restoreToCount(iSave3);
        this.mTempPaint.setShader(this.mHighFadeShader);
        canvas2.drawRect(0.0f, 0.0f, this.mHighFadeShaderLength, getHeight(), this.mTempPaint);
        Rect rect4 = this.mTempRect;
        rect4.left = 0;
        int i = this.mHighFadeShaderLength;
        rect4.right = i;
        canvas.translate(width - i, 0.0f);
        Rect rect5 = this.mTempRect;
        canvas.drawBitmap(tempBitmapHigh, rect5, rect5, (Paint) null);
        canvas.translate(-(width - this.mHighFadeShaderLength), 0.0f);
    }

    public final boolean getFadingLeftEdge() {
        return this.mFadingLowEdge;
    }

    public final int getFadingLeftEdgeLength() {
        return this.mLowFadeShaderLength;
    }

    public final int getFadingLeftEdgeOffset() {
        return this.mLowFadeShaderOffset;
    }

    public final boolean getFadingRightEdge() {
        return this.mFadingHighEdge;
    }

    public final int getFadingRightEdgeLength() {
        return this.mHighFadeShaderLength;
    }

    public final int getFadingRightEdgeOffset() {
        return this.mHighFadeShaderOffset;
    }

    public final void setFadingLeftEdge(boolean z) {
        if (this.mFadingLowEdge != z) {
            this.mFadingLowEdge = z;
            if (!z) {
                this.mTempBitmapLow = null;
            }
            invalidate();
            updateLayerType();
        }
    }

    public final void setFadingLeftEdgeLength(int i) {
        if (this.mLowFadeShaderLength != i) {
            this.mLowFadeShaderLength = i;
            if (i != 0) {
                this.mLowFadeShader = new LinearGradient(0.0f, 0.0f, this.mLowFadeShaderLength, 0.0f, 0, -16777216, Shader.TileMode.CLAMP);
            } else {
                this.mLowFadeShader = null;
            }
            invalidate();
        }
    }

    public final void setFadingLeftEdgeOffset(int i) {
        if (this.mLowFadeShaderOffset != i) {
            this.mLowFadeShaderOffset = i;
            invalidate();
        }
    }

    public final void setFadingRightEdge(boolean z) {
        if (this.mFadingHighEdge != z) {
            this.mFadingHighEdge = z;
            if (!z) {
                this.mTempBitmapHigh = null;
            }
            invalidate();
            updateLayerType();
        }
    }

    public final void setFadingRightEdgeLength(int i) {
        if (this.mHighFadeShaderLength != i) {
            this.mHighFadeShaderLength = i;
            if (i != 0) {
                this.mHighFadeShader = new LinearGradient(0.0f, 0.0f, this.mHighFadeShaderLength, 0.0f, -16777216, 0, Shader.TileMode.CLAMP);
            } else {
                this.mHighFadeShader = null;
            }
            invalidate();
        }
    }

    public final void setFadingRightEdgeOffset(int i) {
        if (this.mHighFadeShaderOffset != i) {
            this.mHighFadeShaderOffset = i;
            invalidate();
        }
    }

    public void setNumRows(int i) {
        this.mLayoutManager.setNumRows(i);
        requestLayout();
    }

    public void setRowHeight(TypedArray typedArray) {
        int i = R.styleable.lbHorizontalGridView_rowHeight;
        if (typedArray.peekValue(i) != null) {
            setRowHeight(typedArray.getLayoutDimension(i, 0));
        }
    }

    public HorizontalGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HorizontalGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTempPaint = new Paint();
        this.mTempRect = new Rect();
        this.mLayoutManager.setOrientation(0);
        initBaseGridViewAttributes(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.lbHorizontalGridView);
        setRowHeight(typedArrayObtainStyledAttributes);
        setNumRows(typedArrayObtainStyledAttributes.getInt(R.styleable.lbHorizontalGridView_numberOfRows, 1));
        typedArrayObtainStyledAttributes.recycle();
        updateLayerType();
        Paint paint = new Paint();
        this.mTempPaint = paint;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public void setRowHeight(int i) {
        this.mLayoutManager.setRowHeight(i);
        requestLayout();
    }
}
