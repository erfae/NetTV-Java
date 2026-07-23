package com.makeramen.roundedimageview;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.squareup.picasso.Transformation;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public final class RoundedTransformationBuilder {
    private float[] mCornerRadii = {0.0f, 0.0f, 0.0f, 0.0f};
    private boolean mOval = false;
    private float mBorderWidth = 0.0f;
    private ColorStateList mBorderColor = ColorStateList.valueOf(-16777216);
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;
    private final DisplayMetrics mDisplayMetrics = Resources.getSystem().getDisplayMetrics();

    public RoundedTransformationBuilder borderColor(int i) {
        this.mBorderColor = ColorStateList.valueOf(i);
        return this;
    }

    public RoundedTransformationBuilder borderWidth(float f) {
        this.mBorderWidth = f;
        return this;
    }

    public RoundedTransformationBuilder borderWidthDp(float f) {
        this.mBorderWidth = TypedValue.applyDimension(1, f, this.mDisplayMetrics);
        return this;
    }

    public Transformation build() {
        return new Transformation() { // from class: com.makeramen.roundedimageview.RoundedTransformationBuilder.1
            public String key() {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("r:");
                sbM.append(Arrays.toString(RoundedTransformationBuilder.this.mCornerRadii));
                sbM.append("b:");
                sbM.append(RoundedTransformationBuilder.this.mBorderWidth);
                sbM.append("c:");
                sbM.append(RoundedTransformationBuilder.this.mBorderColor);
                sbM.append("o:");
                sbM.append(RoundedTransformationBuilder.this.mOval);
                return sbM.toString();
            }

            public Bitmap transform(Bitmap bitmap) {
                Bitmap bitmap2 = RoundedDrawable.fromBitmap(bitmap).setScaleType(RoundedTransformationBuilder.this.mScaleType).setCornerRadius(RoundedTransformationBuilder.this.mCornerRadii[0], RoundedTransformationBuilder.this.mCornerRadii[1], RoundedTransformationBuilder.this.mCornerRadii[2], RoundedTransformationBuilder.this.mCornerRadii[3]).setBorderWidth(RoundedTransformationBuilder.this.mBorderWidth).setBorderColor(RoundedTransformationBuilder.this.mBorderColor).setOval(RoundedTransformationBuilder.this.mOval).toBitmap();
                if (!bitmap.equals(bitmap2)) {
                    bitmap.recycle();
                }
                return bitmap2;
            }
        };
    }

    public RoundedTransformationBuilder cornerRadius(float f) {
        float[] fArr = this.mCornerRadii;
        fArr[0] = f;
        fArr[1] = f;
        fArr[2] = f;
        fArr[3] = f;
        return this;
    }

    public RoundedTransformationBuilder cornerRadiusDp(float f) {
        return cornerRadius(TypedValue.applyDimension(1, f, this.mDisplayMetrics));
    }

    public RoundedTransformationBuilder oval(boolean z) {
        this.mOval = z;
        return this;
    }

    public RoundedTransformationBuilder scaleType(ImageView.ScaleType scaleType) {
        this.mScaleType = scaleType;
        return this;
    }

    public RoundedTransformationBuilder borderColor(ColorStateList colorStateList) {
        this.mBorderColor = colorStateList;
        return this;
    }

    public RoundedTransformationBuilder cornerRadiusDp(int i, float f) {
        return cornerRadius(i, TypedValue.applyDimension(1, f, this.mDisplayMetrics));
    }

    public RoundedTransformationBuilder cornerRadius(int i, float f) {
        this.mCornerRadii[i] = f;
        return this;
    }
}
