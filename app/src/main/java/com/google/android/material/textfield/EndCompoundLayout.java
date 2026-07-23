package com.google.android.material.textfield;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
@SuppressLint({"ViewConstructor"})
class EndCompoundLayout extends LinearLayout {

    @Nullable
    private final AccessibilityManager accessibilityManager;
    private EditText editText;
    private final TextWatcher editTextWatcher;
    private final LinkedHashSet<TextInputLayout.OnEndIconChangedListener> endIconChangedListeners;
    private final EndIconDelegates endIconDelegates;

    @NonNull
    private final FrameLayout endIconFrame;
    private int endIconMinSize;
    private int endIconMode;
    private View.OnLongClickListener endIconOnLongClickListener;

    @NonNull
    private ImageView.ScaleType endIconScaleType;
    private ColorStateList endIconTintList;
    private PorterDuff.Mode endIconTintMode;

    @NonNull
    private final CheckableImageButton endIconView;
    private View.OnLongClickListener errorIconOnLongClickListener;
    private ColorStateList errorIconTintList;
    private PorterDuff.Mode errorIconTintMode;

    @NonNull
    private final CheckableImageButton errorIconView;
    private boolean hintExpanded;
    private final TextInputLayout.OnEditTextAttachedListener onEditTextAttachedListener;

    @Nullable
    private CharSequence suffixText;

    @NonNull
    private final TextView suffixTextView;
    public final TextInputLayout textInputLayout;

    @Nullable
    private AccessibilityManagerCompat.TouchExplorationStateChangeListener touchExplorationStateChangeListener;

    public static class EndIconDelegates {
        private final int customEndIconDrawableId;
        private final SparseArray<EndIconDelegate> delegates = new SparseArray<>();
        private final EndCompoundLayout endLayout;
        private final int passwordIconDrawableId;

        public EndIconDelegates(EndCompoundLayout endCompoundLayout, TintTypedArray tintTypedArray) {
            this.endLayout = endCompoundLayout;
            this.customEndIconDrawableId = tintTypedArray.getResourceId(R.styleable.TextInputLayout_endIconDrawable, 0);
            this.passwordIconDrawableId = tintTypedArray.getResourceId(R.styleable.TextInputLayout_passwordToggleDrawable, 0);
        }

        private EndIconDelegate create(int i) {
            if (i == -1) {
                return new CustomEndIconDelegate(this.endLayout);
            }
            if (i == 0) {
                return new NoEndIconDelegate(this.endLayout);
            }
            if (i == 1) {
                return new PasswordToggleEndIconDelegate(this.endLayout, this.passwordIconDrawableId);
            }
            if (i == 2) {
                return new ClearTextEndIconDelegate(this.endLayout);
            }
            if (i == 3) {
                return new DropdownMenuEndIconDelegate(this.endLayout);
            }
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("Invalid end icon mode: ", i));
        }

        public final EndIconDelegate get(int i) {
            EndIconDelegate endIconDelegate = this.delegates.get(i);
            if (endIconDelegate != null) {
                return endIconDelegate;
            }
            EndIconDelegate endIconDelegateCreate = create(i);
            this.delegates.append(i, endIconDelegateCreate);
            return endIconDelegateCreate;
        }
    }

    public EndCompoundLayout(TextInputLayout textInputLayout, TintTypedArray tintTypedArray) {
        super(textInputLayout.getContext());
        this.endIconMode = 0;
        this.endIconChangedListeners = new LinkedHashSet<>();
        this.editTextWatcher = new TextWatcherAdapter() { // from class: com.google.android.material.textfield.EndCompoundLayout.1
            @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                EndCompoundLayout.this.getEndIconDelegate().afterEditTextChanged(editable);
            }

            @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                EndCompoundLayout.this.getEndIconDelegate().beforeEditTextChanged();
            }
        };
        TextInputLayout.OnEditTextAttachedListener onEditTextAttachedListener = new TextInputLayout.OnEditTextAttachedListener() { // from class: com.google.android.material.textfield.EndCompoundLayout.2
            @Override // com.google.android.material.textfield.TextInputLayout.OnEditTextAttachedListener
            public void onEditTextAttached(@NonNull TextInputLayout textInputLayout2) {
                if (EndCompoundLayout.this.editText == textInputLayout2.getEditText()) {
                    return;
                }
                if (EndCompoundLayout.this.editText != null) {
                    EndCompoundLayout.this.editText.removeTextChangedListener(EndCompoundLayout.this.editTextWatcher);
                    if (EndCompoundLayout.this.editText.getOnFocusChangeListener() == EndCompoundLayout.this.getEndIconDelegate().getOnEditTextFocusChangeListener()) {
                        EndCompoundLayout.this.editText.setOnFocusChangeListener(null);
                    }
                }
                EndCompoundLayout.this.editText = textInputLayout2.getEditText();
                if (EndCompoundLayout.this.editText != null) {
                    EndCompoundLayout.this.editText.addTextChangedListener(EndCompoundLayout.this.editTextWatcher);
                }
                EndCompoundLayout.this.getEndIconDelegate().onEditTextAttached(EndCompoundLayout.this.editText);
                EndCompoundLayout endCompoundLayout = EndCompoundLayout.this;
                endCompoundLayout.setOnFocusChangeListenersIfNeeded(endCompoundLayout.getEndIconDelegate());
            }
        };
        this.onEditTextAttachedListener = onEditTextAttachedListener;
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        this.textInputLayout = textInputLayout;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, GravityCompat.END));
        FrameLayout frameLayout = new FrameLayout(getContext());
        this.endIconFrame = frameLayout;
        frameLayout.setVisibility(8);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        CheckableImageButton checkableImageButtonCreateIconView = createIconView(this, layoutInflaterFrom, R.id.text_input_error_icon);
        this.errorIconView = checkableImageButtonCreateIconView;
        CheckableImageButton checkableImageButtonCreateIconView2 = createIconView(frameLayout, layoutInflaterFrom, R.id.text_input_end_icon);
        this.endIconView = checkableImageButtonCreateIconView2;
        this.endIconDelegates = new EndIconDelegates(this, tintTypedArray);
        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
        this.suffixTextView = appCompatTextView;
        initErrorIconView(tintTypedArray);
        initEndIconView(tintTypedArray);
        initSuffixTextView(tintTypedArray);
        frameLayout.addView(checkableImageButtonCreateIconView2);
        addView(appCompatTextView);
        addView(frameLayout);
        addView(checkableImageButtonCreateIconView);
        textInputLayout.addOnEditTextAttachedListener(onEditTextAttachedListener);
        addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.google.android.material.textfield.EndCompoundLayout.3
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                EndCompoundLayout.this.addTouchExplorationStateChangeListenerIfNeeded();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                EndCompoundLayout.this.removeTouchExplorationStateChangeListenerIfNeeded();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addTouchExplorationStateChangeListenerIfNeeded() {
        if (this.touchExplorationStateChangeListener == null || this.accessibilityManager == null || !ViewCompat.isAttachedToWindow(this)) {
            return;
        }
        AccessibilityManagerCompat.addTouchExplorationStateChangeListener(this.accessibilityManager, this.touchExplorationStateChangeListener);
    }

    private CheckableImageButton createIconView(ViewGroup viewGroup, LayoutInflater layoutInflater, @IdRes int i) {
        CheckableImageButton checkableImageButton = (CheckableImageButton) layoutInflater.inflate(R.layout.design_text_input_end_icon, viewGroup, false);
        checkableImageButton.setId(i);
        IconHelper.setCompatRippleBackgroundIfNeeded(checkableImageButton);
        if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams(), 0);
        }
        return checkableImageButton;
    }

    private void dispatchOnEndIconChanged(int i) {
        Iterator<TextInputLayout.OnEndIconChangedListener> it = this.endIconChangedListeners.iterator();
        while (it.hasNext()) {
            it.next().onEndIconChanged(this.textInputLayout, i);
        }
    }

    private int getIconResId(EndIconDelegate endIconDelegate) {
        int i = this.endIconDelegates.customEndIconDrawableId;
        return i == 0 ? endIconDelegate.getIconDrawableResId() : i;
    }

    private void initEndIconView(TintTypedArray tintTypedArray) {
        int i = R.styleable.TextInputLayout_passwordToggleEnabled;
        if (!tintTypedArray.hasValue(i)) {
            int i2 = R.styleable.TextInputLayout_endIconTint;
            if (tintTypedArray.hasValue(i2)) {
                this.endIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, i2);
            }
            int i3 = R.styleable.TextInputLayout_endIconTintMode;
            if (tintTypedArray.hasValue(i3)) {
                this.endIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(i3, -1), null);
            }
        }
        int i4 = R.styleable.TextInputLayout_endIconMode;
        if (tintTypedArray.hasValue(i4)) {
            setEndIconMode(tintTypedArray.getInt(i4, 0));
            int i5 = R.styleable.TextInputLayout_endIconContentDescription;
            if (tintTypedArray.hasValue(i5)) {
                setEndIconContentDescription(tintTypedArray.getText(i5));
            }
            setEndIconCheckable(tintTypedArray.getBoolean(R.styleable.TextInputLayout_endIconCheckable, true));
        } else if (tintTypedArray.hasValue(i)) {
            int i6 = R.styleable.TextInputLayout_passwordToggleTint;
            if (tintTypedArray.hasValue(i6)) {
                this.endIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, i6);
            }
            int i7 = R.styleable.TextInputLayout_passwordToggleTintMode;
            if (tintTypedArray.hasValue(i7)) {
                this.endIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(i7, -1), null);
            }
            setEndIconMode(tintTypedArray.getBoolean(i, false) ? 1 : 0);
            setEndIconContentDescription(tintTypedArray.getText(R.styleable.TextInputLayout_passwordToggleContentDescription));
        }
        setEndIconMinSize(tintTypedArray.getDimensionPixelSize(R.styleable.TextInputLayout_endIconMinSize, getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size)));
        int i8 = R.styleable.TextInputLayout_endIconScaleType;
        if (tintTypedArray.hasValue(i8)) {
            ImageView.ScaleType scaleTypeConvertScaleType = IconHelper.convertScaleType(tintTypedArray.getInt(i8, -1));
            this.endIconScaleType = scaleTypeConvertScaleType;
            this.endIconView.setScaleType(scaleTypeConvertScaleType);
            this.errorIconView.setScaleType(scaleTypeConvertScaleType);
        }
    }

    private void initErrorIconView(TintTypedArray tintTypedArray) {
        int i = R.styleable.TextInputLayout_errorIconTint;
        if (tintTypedArray.hasValue(i)) {
            this.errorIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, i);
        }
        int i2 = R.styleable.TextInputLayout_errorIconTintMode;
        if (tintTypedArray.hasValue(i2)) {
            this.errorIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(i2, -1), null);
        }
        int i3 = R.styleable.TextInputLayout_errorIconDrawable;
        if (tintTypedArray.hasValue(i3)) {
            setErrorIconDrawable(tintTypedArray.getDrawable(i3));
        }
        this.errorIconView.setContentDescription(getResources().getText(R.string.error_icon_content_description));
        ViewCompat.setImportantForAccessibility(this.errorIconView, 2);
        this.errorIconView.setClickable(false);
        this.errorIconView.setPressable(false);
        this.errorIconView.setFocusable(false);
    }

    private void initSuffixTextView(TintTypedArray tintTypedArray) {
        this.suffixTextView.setVisibility(8);
        this.suffixTextView.setId(R.id.textinput_suffix_text);
        this.suffixTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 80.0f));
        ViewCompat.setAccessibilityLiveRegion(this.suffixTextView, 1);
        TextViewCompat.setTextAppearance(this.suffixTextView, tintTypedArray.getResourceId(R.styleable.TextInputLayout_suffixTextAppearance, 0));
        int i = R.styleable.TextInputLayout_suffixTextColor;
        if (tintTypedArray.hasValue(i)) {
            this.suffixTextView.setTextColor(tintTypedArray.getColorStateList(i));
        }
        CharSequence text = tintTypedArray.getText(R.styleable.TextInputLayout_suffixText);
        this.suffixText = TextUtils.isEmpty(text) ? null : text;
        this.suffixTextView.setText(text);
        updateSuffixTextVisibility();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeTouchExplorationStateChangeListenerIfNeeded() {
        AccessibilityManager accessibilityManager;
        AccessibilityManagerCompat.TouchExplorationStateChangeListener touchExplorationStateChangeListener = this.touchExplorationStateChangeListener;
        if (touchExplorationStateChangeListener == null || (accessibilityManager = this.accessibilityManager) == null) {
            return;
        }
        AccessibilityManagerCompat.removeTouchExplorationStateChangeListener(accessibilityManager, touchExplorationStateChangeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOnFocusChangeListenersIfNeeded(EndIconDelegate endIconDelegate) {
        if (this.editText == null) {
            return;
        }
        if (endIconDelegate.getOnEditTextFocusChangeListener() != null) {
            this.editText.setOnFocusChangeListener(endIconDelegate.getOnEditTextFocusChangeListener());
        }
        if (endIconDelegate.getOnIconViewFocusChangeListener() != null) {
            this.endIconView.setOnFocusChangeListener(endIconDelegate.getOnIconViewFocusChangeListener());
        }
    }

    private void setUpDelegate(@NonNull EndIconDelegate endIconDelegate) {
        endIconDelegate.setUp();
        this.touchExplorationStateChangeListener = endIconDelegate.getTouchExplorationStateChangeListener();
        addTouchExplorationStateChangeListenerIfNeeded();
    }

    private void tearDownDelegate(@NonNull EndIconDelegate endIconDelegate) {
        removeTouchExplorationStateChangeListenerIfNeeded();
        this.touchExplorationStateChangeListener = null;
        endIconDelegate.tearDown();
    }

    private void tintEndIconOnError(boolean z) {
        if (!z || getEndIconDrawable() == null) {
            IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
            return;
        }
        Drawable drawableMutate = DrawableCompat.wrap(getEndIconDrawable()).mutate();
        DrawableCompat.setTint(drawableMutate, this.textInputLayout.getErrorCurrentTextColors());
        this.endIconView.setImageDrawable(drawableMutate);
    }

    private void updateEndLayoutVisibility() {
        this.endIconFrame.setVisibility((this.endIconView.getVisibility() != 0 || isErrorIconVisible()) ? 8 : 0);
        setVisibility(isEndIconVisible() || isErrorIconVisible() || ((this.suffixText == null || this.hintExpanded) ? '\b' : (char) 0) == 0 ? 0 : 8);
    }

    private void updateErrorIconVisibility() {
        this.errorIconView.setVisibility(this.errorIconView.getDrawable() != null && this.textInputLayout.isErrorEnabled() && this.textInputLayout.shouldShowError() ? 0 : 8);
        updateEndLayoutVisibility();
        updateSuffixTextViewPadding();
        if (hasEndIcon()) {
            return;
        }
        this.textInputLayout.updateDummyDrawables();
    }

    private void updateSuffixTextVisibility() {
        int visibility = this.suffixTextView.getVisibility();
        int i = (this.suffixText == null || this.hintExpanded) ? 8 : 0;
        if (visibility != i) {
            getEndIconDelegate().onSuffixVisibilityChanged(i == 0);
        }
        updateEndLayoutVisibility();
        this.suffixTextView.setVisibility(i);
        this.textInputLayout.updateDummyDrawables();
    }

    public final void addOnEndIconChangedListener(@NonNull TextInputLayout.OnEndIconChangedListener onEndIconChangedListener) {
        this.endIconChangedListeners.add(onEndIconChangedListener);
    }

    public final void checkEndIcon() {
        this.endIconView.performClick();
        this.endIconView.jumpDrawablesToCurrentState();
    }

    public final void clearOnEndIconChangedListeners() {
        this.endIconChangedListeners.clear();
    }

    @Nullable
    public final CheckableImageButton getCurrentEndIconView() {
        if (isErrorIconVisible()) {
            return this.errorIconView;
        }
        if (hasEndIcon() && isEndIconVisible()) {
            return this.endIconView;
        }
        return null;
    }

    @Nullable
    public final CharSequence getEndIconContentDescription() {
        return this.endIconView.getContentDescription();
    }

    public final EndIconDelegate getEndIconDelegate() {
        return this.endIconDelegates.get(this.endIconMode);
    }

    @Nullable
    public final Drawable getEndIconDrawable() {
        return this.endIconView.getDrawable();
    }

    public final int getEndIconMinSize() {
        return this.endIconMinSize;
    }

    public final int getEndIconMode() {
        return this.endIconMode;
    }

    @NonNull
    public final ImageView.ScaleType getEndIconScaleType() {
        return this.endIconScaleType;
    }

    public final CheckableImageButton getEndIconView() {
        return this.endIconView;
    }

    public final Drawable getErrorIconDrawable() {
        return this.errorIconView.getDrawable();
    }

    @Nullable
    public final CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.endIconView.getContentDescription();
    }

    @Nullable
    public final Drawable getPasswordVisibilityToggleDrawable() {
        return this.endIconView.getDrawable();
    }

    @Nullable
    public final CharSequence getSuffixText() {
        return this.suffixText;
    }

    @Nullable
    public final ColorStateList getSuffixTextColor() {
        return this.suffixTextView.getTextColors();
    }

    public final TextView getSuffixTextView() {
        return this.suffixTextView;
    }

    public final boolean hasEndIcon() {
        return this.endIconMode != 0;
    }

    public final boolean isEndIconCheckable() {
        return this.endIconView.isCheckable();
    }

    public final boolean isEndIconChecked() {
        return hasEndIcon() && this.endIconView.isChecked();
    }

    public final boolean isEndIconVisible() {
        return this.endIconFrame.getVisibility() == 0 && this.endIconView.getVisibility() == 0;
    }

    public final boolean isErrorIconVisible() {
        return this.errorIconView.getVisibility() == 0;
    }

    public final boolean isPasswordVisibilityToggleEnabled() {
        return this.endIconMode == 1;
    }

    public final void onHintStateChanged(boolean z) {
        this.hintExpanded = z;
        updateSuffixTextVisibility();
    }

    public final void onTextInputBoxStateUpdated() {
        updateErrorIconVisibility();
        refreshErrorIconDrawableState();
        refreshEndIconDrawableState();
        EndIconDelegate endIconDelegate = getEndIconDelegate();
        Objects.requireNonNull(endIconDelegate);
        if (endIconDelegate instanceof DropdownMenuEndIconDelegate) {
            tintEndIconOnError(this.textInputLayout.shouldShowError());
        }
    }

    public final void refreshEndIconDrawableState() {
        IconHelper.refreshIconDrawableState(this.textInputLayout, this.endIconView, this.endIconTintList);
    }

    public final void refreshErrorIconDrawableState() {
        IconHelper.refreshIconDrawableState(this.textInputLayout, this.errorIconView, this.errorIconTintList);
    }

    public final void refreshIconState(boolean z) {
        boolean z2;
        boolean zIsActivated;
        boolean zIsChecked;
        EndIconDelegate endIconDelegate = getEndIconDelegate();
        boolean z3 = true;
        if (!endIconDelegate.isIconCheckable() || (zIsChecked = this.endIconView.isChecked()) == endIconDelegate.isIconChecked()) {
            z2 = false;
        } else {
            this.endIconView.setChecked(!zIsChecked);
            z2 = true;
        }
        if (!(endIconDelegate instanceof DropdownMenuEndIconDelegate) || (zIsActivated = this.endIconView.isActivated()) == endIconDelegate.isIconActivated()) {
            z3 = z2;
        } else {
            this.endIconView.setActivated(!zIsActivated);
        }
        if (z || z3) {
            refreshEndIconDrawableState();
        }
    }

    public final void removeOnEndIconChangedListener(@NonNull TextInputLayout.OnEndIconChangedListener onEndIconChangedListener) {
        this.endIconChangedListeners.remove(onEndIconChangedListener);
    }

    public final void setEndIconActivated(boolean z) {
        this.endIconView.setActivated(z);
    }

    public final void setEndIconCheckable(boolean z) {
        this.endIconView.setCheckable(z);
    }

    public final void setEndIconContentDescription(@Nullable CharSequence charSequence) {
        if (this.endIconView.getContentDescription() != charSequence) {
            this.endIconView.setContentDescription(charSequence);
        }
    }

    public final void setEndIconDrawable(@Nullable Drawable drawable) {
        this.endIconView.setImageDrawable(drawable);
        if (drawable != null) {
            IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
            refreshEndIconDrawableState();
        }
    }

    public final void setEndIconMinSize(@Px int i) {
        if (i < 0) {
            throw new IllegalArgumentException("endIconSize cannot be less than 0");
        }
        if (i != this.endIconMinSize) {
            this.endIconMinSize = i;
            IconHelper.setIconMinSize(this.endIconView, i);
            IconHelper.setIconMinSize(this.errorIconView, i);
        }
    }

    public final void setEndIconMode(int i) {
        if (this.endIconMode == i) {
            return;
        }
        tearDownDelegate(getEndIconDelegate());
        int i2 = this.endIconMode;
        this.endIconMode = i;
        dispatchOnEndIconChanged(i2);
        setEndIconVisible(i != 0);
        EndIconDelegate endIconDelegate = getEndIconDelegate();
        int iconResId = getIconResId(endIconDelegate);
        setEndIconDrawable(iconResId != 0 ? AppCompatResources.getDrawable(getContext(), iconResId) : null);
        int iconContentDescriptionResId = endIconDelegate.getIconContentDescriptionResId();
        setEndIconContentDescription(iconContentDescriptionResId != 0 ? getResources().getText(iconContentDescriptionResId) : null);
        setEndIconCheckable(endIconDelegate.isIconCheckable());
        if (!endIconDelegate.isBoxBackgroundModeSupported(this.textInputLayout.getBoxBackgroundMode())) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("The current box background mode ");
            sbM.append(this.textInputLayout.getBoxBackgroundMode());
            sbM.append(" is not supported by the end icon mode ");
            sbM.append(i);
            throw new IllegalStateException(sbM.toString());
        }
        setUpDelegate(endIconDelegate);
        IconHelper.setIconOnClickListener(this.endIconView, endIconDelegate.getOnIconClickListener(), this.endIconOnLongClickListener);
        EditText editText = this.editText;
        if (editText != null) {
            endIconDelegate.onEditTextAttached(editText);
            setOnFocusChangeListenersIfNeeded(endIconDelegate);
        }
        IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
        refreshIconState(true);
    }

    public final void setEndIconOnClickListener(@Nullable View.OnClickListener onClickListener) {
        IconHelper.setIconOnClickListener(this.endIconView, onClickListener, this.endIconOnLongClickListener);
    }

    public final void setEndIconOnLongClickListener(@Nullable View.OnLongClickListener onLongClickListener) {
        this.endIconOnLongClickListener = onLongClickListener;
        IconHelper.setIconOnLongClickListener(this.endIconView, onLongClickListener);
    }

    public final void setEndIconScaleType(@NonNull ImageView.ScaleType scaleType) {
        this.endIconScaleType = scaleType;
        this.endIconView.setScaleType(scaleType);
        this.errorIconView.setScaleType(scaleType);
    }

    public final void setEndIconTintList(@Nullable ColorStateList colorStateList) {
        if (this.endIconTintList != colorStateList) {
            this.endIconTintList = colorStateList;
            IconHelper.applyIconTint(this.textInputLayout, this.endIconView, colorStateList, this.endIconTintMode);
        }
    }

    public final void setEndIconTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.endIconTintMode != mode) {
            this.endIconTintMode = mode;
            IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, mode);
        }
    }

    public final void setEndIconVisible(boolean z) {
        if (isEndIconVisible() != z) {
            this.endIconView.setVisibility(z ? 0 : 8);
            updateEndLayoutVisibility();
            updateSuffixTextViewPadding();
            this.textInputLayout.updateDummyDrawables();
        }
    }

    public final void setErrorIconDrawable(@Nullable Drawable drawable) {
        this.errorIconView.setImageDrawable(drawable);
        updateErrorIconVisibility();
        IconHelper.applyIconTint(this.textInputLayout, this.errorIconView, this.errorIconTintList, this.errorIconTintMode);
    }

    public final void setErrorIconOnClickListener(@Nullable View.OnClickListener onClickListener) {
        IconHelper.setIconOnClickListener(this.errorIconView, onClickListener, this.errorIconOnLongClickListener);
    }

    public final void setErrorIconOnLongClickListener(@Nullable View.OnLongClickListener onLongClickListener) {
        this.errorIconOnLongClickListener = onLongClickListener;
        IconHelper.setIconOnLongClickListener(this.errorIconView, onLongClickListener);
    }

    public final void setErrorIconTintList(@Nullable ColorStateList colorStateList) {
        if (this.errorIconTintList != colorStateList) {
            this.errorIconTintList = colorStateList;
            IconHelper.applyIconTint(this.textInputLayout, this.errorIconView, colorStateList, this.errorIconTintMode);
        }
    }

    public final void setErrorIconTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.errorIconTintMode != mode) {
            this.errorIconTintMode = mode;
            IconHelper.applyIconTint(this.textInputLayout, this.errorIconView, this.errorIconTintList, mode);
        }
    }

    public final void setPasswordVisibilityToggleContentDescription(@StringRes int i) {
        this.endIconView.setContentDescription(i != 0 ? getResources().getText(i) : null);
    }

    public final void setPasswordVisibilityToggleDrawable(@DrawableRes int i) {
        this.endIconView.setImageDrawable(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
    }

    public final void setPasswordVisibilityToggleEnabled(boolean z) {
        if (z && this.endIconMode != 1) {
            setEndIconMode(1);
        } else {
            if (z) {
                return;
            }
            setEndIconMode(0);
        }
    }

    public final void setPasswordVisibilityToggleTintList(@Nullable ColorStateList colorStateList) {
        this.endIconTintList = colorStateList;
        IconHelper.applyIconTint(this.textInputLayout, this.endIconView, colorStateList, this.endIconTintMode);
    }

    public final void setPasswordVisibilityToggleTintMode(@Nullable PorterDuff.Mode mode) {
        this.endIconTintMode = mode;
        IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, mode);
    }

    public final void setSuffixText(@Nullable CharSequence charSequence) {
        this.suffixText = TextUtils.isEmpty(charSequence) ? null : charSequence;
        this.suffixTextView.setText(charSequence);
        updateSuffixTextVisibility();
    }

    public final void setSuffixTextAppearance(@StyleRes int i) {
        TextViewCompat.setTextAppearance(this.suffixTextView, i);
    }

    public final void setSuffixTextColor(@NonNull ColorStateList colorStateList) {
        this.suffixTextView.setTextColor(colorStateList);
    }

    public final void togglePasswordVisibilityToggle(boolean z) {
        if (this.endIconMode == 1) {
            this.endIconView.performClick();
            if (z) {
                this.endIconView.jumpDrawablesToCurrentState();
            }
        }
    }

    public final void updateSuffixTextViewPadding() {
        if (this.textInputLayout.editText == null) {
            return;
        }
        ViewCompat.setPaddingRelative(this.suffixTextView, getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding), this.textInputLayout.editText.getPaddingTop(), (isEndIconVisible() || isErrorIconVisible()) ? 0 : ViewCompat.getPaddingEnd(this.textInputLayout.editText), this.textInputLayout.editText.getPaddingBottom());
    }

    public final void setPasswordVisibilityToggleContentDescription(@Nullable CharSequence charSequence) {
        this.endIconView.setContentDescription(charSequence);
    }

    public final void setPasswordVisibilityToggleDrawable(@Nullable Drawable drawable) {
        this.endIconView.setImageDrawable(drawable);
    }
}
