package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.R;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
class AppCompatBackgroundHelper {
    private TintInfo mBackgroundTint;
    private TintInfo mInternalBackgroundTint;
    private TintInfo mTmpInfo;

    @NonNull
    private final View mView;
    private int mBackgroundResId = -1;
    private final AppCompatDrawableManager mDrawableManager = AppCompatDrawableManager.get();

    public AppCompatBackgroundHelper(@NonNull View view) {
        this.mView = view;
    }

    private boolean applyFrameworkTintUsingColorFilter(@NonNull Drawable drawable) {
        if (this.mTmpInfo == null) {
            this.mTmpInfo = new TintInfo();
        }
        TintInfo tintInfo = this.mTmpInfo;
        tintInfo.mTintList = null;
        tintInfo.mHasTintList = false;
        tintInfo.mTintMode = null;
        tintInfo.mHasTintMode = false;
        ColorStateList backgroundTintList = ViewCompat.getBackgroundTintList(this.mView);
        if (backgroundTintList != null) {
            tintInfo.mHasTintList = true;
            tintInfo.mTintList = backgroundTintList;
        }
        PorterDuff.Mode backgroundTintMode = ViewCompat.getBackgroundTintMode(this.mView);
        if (backgroundTintMode != null) {
            tintInfo.mHasTintMode = true;
            tintInfo.mTintMode = backgroundTintMode;
        }
        if (!tintInfo.mHasTintList && !tintInfo.mHasTintMode) {
            return false;
        }
        int[] drawableState = this.mView.getDrawableState();
        int i = AppCompatDrawableManager.$r8$clinit;
        ResourceManagerInternal.tintDrawable(drawable, tintInfo, drawableState);
        return true;
    }

    private boolean shouldApplyFrameworkTintUsingColorFilter() {
        int i = Build.VERSION.SDK_INT;
        if (i > 21) {
            return this.mInternalBackgroundTint != null;
        }
        return i == 21;
    }

    public final void applySupportBackgroundTint() {
        Drawable background = this.mView.getBackground();
        if (background != null) {
            if (shouldApplyFrameworkTintUsingColorFilter() && applyFrameworkTintUsingColorFilter(background)) {
                return;
            }
            TintInfo tintInfo = this.mBackgroundTint;
            if (tintInfo != null) {
                int[] drawableState = this.mView.getDrawableState();
                int i = AppCompatDrawableManager.$r8$clinit;
                ResourceManagerInternal.tintDrawable(background, tintInfo, drawableState);
            } else {
                TintInfo tintInfo2 = this.mInternalBackgroundTint;
                if (tintInfo2 != null) {
                    int[] drawableState2 = this.mView.getDrawableState();
                    int i2 = AppCompatDrawableManager.$r8$clinit;
                    ResourceManagerInternal.tintDrawable(background, tintInfo2, drawableState2);
                }
            }
        }
    }

    public final ColorStateList getSupportBackgroundTintList() {
        TintInfo tintInfo = this.mBackgroundTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    public final PorterDuff.Mode getSupportBackgroundTintMode() {
        TintInfo tintInfo = this.mBackgroundTint;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    public final void loadFromAttributes(@Nullable AttributeSet attributeSet, int i) {
        Context context = this.mView.getContext();
        int[] iArr = R.styleable.ViewBackgroundHelper;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        View view = this.mView;
        ViewCompat.saveAttributeDataForStyleable(view, view.getContext(), iArr, attributeSet, tintTypedArrayObtainStyledAttributes.getWrappedTypeArray(), i, 0);
        try {
            int i2 = R.styleable.ViewBackgroundHelper_android_background;
            if (tintTypedArrayObtainStyledAttributes.hasValue(i2)) {
                this.mBackgroundResId = tintTypedArrayObtainStyledAttributes.getResourceId(i2, -1);
                ColorStateList tintList = this.mDrawableManager.getTintList(this.mView.getContext(), this.mBackgroundResId);
                if (tintList != null) {
                    setInternalBackgroundTint(tintList);
                }
            }
            int i3 = R.styleable.ViewBackgroundHelper_backgroundTint;
            if (tintTypedArrayObtainStyledAttributes.hasValue(i3)) {
                ViewCompat.setBackgroundTintList(this.mView, tintTypedArrayObtainStyledAttributes.getColorStateList(i3));
            }
            int i4 = R.styleable.ViewBackgroundHelper_backgroundTintMode;
            if (tintTypedArrayObtainStyledAttributes.hasValue(i4)) {
                ViewCompat.setBackgroundTintMode(this.mView, DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(i4, -1), null));
            }
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle();
        }
    }

    public final void onSetBackgroundDrawable() {
        this.mBackgroundResId = -1;
        setInternalBackgroundTint(null);
        applySupportBackgroundTint();
    }

    public final void onSetBackgroundResource(int i) {
        this.mBackgroundResId = i;
        AppCompatDrawableManager appCompatDrawableManager = this.mDrawableManager;
        setInternalBackgroundTint(appCompatDrawableManager != null ? appCompatDrawableManager.getTintList(this.mView.getContext(), i) : null);
        applySupportBackgroundTint();
    }

    public final void setInternalBackgroundTint(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.mInternalBackgroundTint == null) {
                this.mInternalBackgroundTint = new TintInfo();
            }
            TintInfo tintInfo = this.mInternalBackgroundTint;
            tintInfo.mTintList = colorStateList;
            tintInfo.mHasTintList = true;
        } else {
            this.mInternalBackgroundTint = null;
        }
        applySupportBackgroundTint();
    }

    public final void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        TintInfo tintInfo = this.mBackgroundTint;
        tintInfo.mTintList = colorStateList;
        tintInfo.mHasTintList = true;
        applySupportBackgroundTint();
    }

    public final void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        TintInfo tintInfo = this.mBackgroundTint;
        tintInfo.mTintMode = mode;
        tintInfo.mHasTintMode = true;
        applySupportBackgroundTint();
    }
}
