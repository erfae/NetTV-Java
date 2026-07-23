package com.google.android.material.textfield;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.StyleRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;

/* JADX INFO: loaded from: classes2.dex */
@SuppressLint({"ViewConstructor"})
class StartCompoundLayout extends LinearLayout {
    private boolean hintExpanded;

    @Nullable
    private CharSequence prefixText;
    private final TextView prefixTextView;
    private int startIconMinSize;
    private View.OnLongClickListener startIconOnLongClickListener;

    @NonNull
    private ImageView.ScaleType startIconScaleType;
    private ColorStateList startIconTintList;
    private PorterDuff.Mode startIconTintMode;
    private final CheckableImageButton startIconView;
    private final TextInputLayout textInputLayout;

    public StartCompoundLayout(TextInputLayout textInputLayout, TintTypedArray tintTypedArray) {
        super(textInputLayout.getContext());
        this.textInputLayout = textInputLayout;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, GravityCompat.START));
        CheckableImageButton checkableImageButton = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_start_icon, (ViewGroup) this, false);
        this.startIconView = checkableImageButton;
        IconHelper.setCompatRippleBackgroundIfNeeded(checkableImageButton);
        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
        this.prefixTextView = appCompatTextView;
        initStartIconView(tintTypedArray);
        initPrefixTextView(tintTypedArray);
        addView(checkableImageButton);
        addView(appCompatTextView);
    }

    private void initPrefixTextView(TintTypedArray tintTypedArray) {
        this.prefixTextView.setVisibility(8);
        this.prefixTextView.setId(R.id.textinput_prefix_text);
        this.prefixTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        ViewCompat.setAccessibilityLiveRegion(this.prefixTextView, 1);
        TextViewCompat.setTextAppearance(this.prefixTextView, tintTypedArray.getResourceId(R.styleable.TextInputLayout_prefixTextAppearance, 0));
        int i = R.styleable.TextInputLayout_prefixTextColor;
        if (tintTypedArray.hasValue(i)) {
            this.prefixTextView.setTextColor(tintTypedArray.getColorStateList(i));
        }
        CharSequence text = tintTypedArray.getText(R.styleable.TextInputLayout_prefixText);
        this.prefixText = TextUtils.isEmpty(text) ? null : text;
        this.prefixTextView.setText(text);
        updateVisibility();
    }

    private void initStartIconView(TintTypedArray tintTypedArray) {
        if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            MarginLayoutParamsCompat.setMarginEnd((ViewGroup.MarginLayoutParams) this.startIconView.getLayoutParams(), 0);
        }
        setStartIconOnClickListener(null);
        setStartIconOnLongClickListener(null);
        int i = R.styleable.TextInputLayout_startIconTint;
        if (tintTypedArray.hasValue(i)) {
            this.startIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, i);
        }
        int i2 = R.styleable.TextInputLayout_startIconTintMode;
        if (tintTypedArray.hasValue(i2)) {
            this.startIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(i2, -1), null);
        }
        int i3 = R.styleable.TextInputLayout_startIconDrawable;
        if (tintTypedArray.hasValue(i3)) {
            setStartIconDrawable(tintTypedArray.getDrawable(i3));
            int i4 = R.styleable.TextInputLayout_startIconContentDescription;
            if (tintTypedArray.hasValue(i4)) {
                setStartIconContentDescription(tintTypedArray.getText(i4));
            }
            this.startIconView.setCheckable(tintTypedArray.getBoolean(R.styleable.TextInputLayout_startIconCheckable, true));
        }
        setStartIconMinSize(tintTypedArray.getDimensionPixelSize(R.styleable.TextInputLayout_startIconMinSize, getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size)));
        int i5 = R.styleable.TextInputLayout_startIconScaleType;
        if (tintTypedArray.hasValue(i5)) {
            ImageView.ScaleType scaleTypeConvertScaleType = IconHelper.convertScaleType(tintTypedArray.getInt(i5, -1));
            this.startIconScaleType = scaleTypeConvertScaleType;
            this.startIconView.setScaleType(scaleTypeConvertScaleType);
        }
    }

    private void updateVisibility() {
        int i = (this.prefixText == null || this.hintExpanded) ? 8 : 0;
        setVisibility(this.startIconView.getVisibility() == 0 || i == 0 ? 0 : 8);
        this.prefixTextView.setVisibility(i);
        this.textInputLayout.updateDummyDrawables();
    }

    @Nullable
    public final CharSequence getPrefixText() {
        return this.prefixText;
    }

    @Nullable
    public final ColorStateList getPrefixTextColor() {
        return this.prefixTextView.getTextColors();
    }

    @NonNull
    public final TextView getPrefixTextView() {
        return this.prefixTextView;
    }

    @Nullable
    public final CharSequence getStartIconContentDescription() {
        return this.startIconView.getContentDescription();
    }

    @Nullable
    public final Drawable getStartIconDrawable() {
        return this.startIconView.getDrawable();
    }

    public final int getStartIconMinSize() {
        return this.startIconMinSize;
    }

    @NonNull
    public final ImageView.ScaleType getStartIconScaleType() {
        return this.startIconScaleType;
    }

    public final boolean isStartIconCheckable() {
        return this.startIconView.isCheckable();
    }

    public final boolean isStartIconVisible() {
        return this.startIconView.getVisibility() == 0;
    }

    public final void onHintStateChanged(boolean z) {
        this.hintExpanded = z;
        updateVisibility();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        updatePrefixTextViewPadding();
    }

    public final void refreshStartIconDrawableState() {
        IconHelper.refreshIconDrawableState(this.textInputLayout, this.startIconView, this.startIconTintList);
    }

    public final void setPrefixText(@Nullable CharSequence charSequence) {
        this.prefixText = TextUtils.isEmpty(charSequence) ? null : charSequence;
        this.prefixTextView.setText(charSequence);
        updateVisibility();
    }

    public final void setPrefixTextAppearance(@StyleRes int i) {
        TextViewCompat.setTextAppearance(this.prefixTextView, i);
    }

    public final void setPrefixTextColor(@NonNull ColorStateList colorStateList) {
        this.prefixTextView.setTextColor(colorStateList);
    }

    public final void setStartIconCheckable(boolean z) {
        this.startIconView.setCheckable(z);
    }

    public final void setStartIconContentDescription(@Nullable CharSequence charSequence) {
        if (this.startIconView.getContentDescription() != charSequence) {
            this.startIconView.setContentDescription(charSequence);
        }
    }

    public final void setStartIconDrawable(@Nullable Drawable drawable) {
        this.startIconView.setImageDrawable(drawable);
        if (drawable != null) {
            IconHelper.applyIconTint(this.textInputLayout, this.startIconView, this.startIconTintList, this.startIconTintMode);
            setStartIconVisible(true);
            IconHelper.refreshIconDrawableState(this.textInputLayout, this.startIconView, this.startIconTintList);
        } else {
            setStartIconVisible(false);
            setStartIconOnClickListener(null);
            setStartIconOnLongClickListener(null);
            setStartIconContentDescription(null);
        }
    }

    public final void setStartIconMinSize(@Px int i) {
        if (i < 0) {
            throw new IllegalArgumentException("startIconSize cannot be less than 0");
        }
        if (i != this.startIconMinSize) {
            this.startIconMinSize = i;
            IconHelper.setIconMinSize(this.startIconView, i);
        }
    }

    public final void setStartIconOnClickListener(@Nullable View.OnClickListener onClickListener) {
        IconHelper.setIconOnClickListener(this.startIconView, onClickListener, this.startIconOnLongClickListener);
    }

    public final void setStartIconOnLongClickListener(@Nullable View.OnLongClickListener onLongClickListener) {
        this.startIconOnLongClickListener = onLongClickListener;
        IconHelper.setIconOnLongClickListener(this.startIconView, onLongClickListener);
    }

    public final void setStartIconScaleType(@NonNull ImageView.ScaleType scaleType) {
        this.startIconScaleType = scaleType;
        this.startIconView.setScaleType(scaleType);
    }

    public final void setStartIconTintList(@Nullable ColorStateList colorStateList) {
        if (this.startIconTintList != colorStateList) {
            this.startIconTintList = colorStateList;
            IconHelper.applyIconTint(this.textInputLayout, this.startIconView, colorStateList, this.startIconTintMode);
        }
    }

    public final void setStartIconTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.startIconTintMode != mode) {
            this.startIconTintMode = mode;
            IconHelper.applyIconTint(this.textInputLayout, this.startIconView, this.startIconTintList, mode);
        }
    }

    public final void setStartIconVisible(boolean z) {
        if (isStartIconVisible() != z) {
            this.startIconView.setVisibility(z ? 0 : 8);
            updatePrefixTextViewPadding();
            updateVisibility();
        }
    }

    public final void setupAccessibilityNodeInfo(@NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        if (this.prefixTextView.getVisibility() != 0) {
            accessibilityNodeInfoCompat.setTraversalAfter(this.startIconView);
        } else {
            accessibilityNodeInfoCompat.setLabelFor(this.prefixTextView);
            accessibilityNodeInfoCompat.setTraversalAfter(this.prefixTextView);
        }
    }

    public final void updatePrefixTextViewPadding() {
        EditText editText = this.textInputLayout.editText;
        if (editText == null) {
            return;
        }
        ViewCompat.setPaddingRelative(this.prefixTextView, isStartIconVisible() ? 0 : ViewCompat.getPaddingStart(editText), editText.getCompoundPaddingTop(), getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding), editText.getCompoundPaddingBottom());
    }
}
