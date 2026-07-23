package com.google.android.material.badge;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;
import androidx.annotation.XmlRes;
import com.google.android.material.R;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class BadgeState {
    private static final String BADGE_RESOURCE_TAG = "badge";
    private static final int DEFAULT_MAX_BADGE_CHARACTER_COUNT = 4;
    public final float badgeRadius;
    public final float badgeWidePadding;
    public final float badgeWithTextRadius;
    private final State currentState;
    private final State overridingState;

    public BadgeState(Context context, @XmlRes int i, @AttrRes int i2, @StyleRes int i3, @Nullable State state) {
        State state2 = new State();
        this.currentState = state2;
        state = state == null ? new State() : state;
        if (i != 0) {
            state.badgeResId = i;
        }
        TypedArray typedArrayGenerateTypedArray = generateTypedArray(context, state.badgeResId, i2, i3);
        Resources resources = context.getResources();
        this.badgeRadius = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeRadius, resources.getDimensionPixelSize(R.dimen.mtrl_badge_radius));
        this.badgeWidePadding = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeWidePadding, resources.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding));
        this.badgeWithTextRadius = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeWithTextRadius, resources.getDimensionPixelSize(R.dimen.mtrl_badge_with_text_radius));
        state2.alpha = state.alpha == -2 ? 255 : state.alpha;
        state2.contentDescriptionNumberless = state.contentDescriptionNumberless == null ? context.getString(R.string.mtrl_badge_numberless_content_description) : state.contentDescriptionNumberless;
        state2.contentDescriptionQuantityStrings = state.contentDescriptionQuantityStrings == 0 ? R.plurals.mtrl_badge_content_description : state.contentDescriptionQuantityStrings;
        state2.contentDescriptionExceedsMaxBadgeNumberRes = state.contentDescriptionExceedsMaxBadgeNumberRes == 0 ? R.string.mtrl_exceed_max_badge_number_content_description : state.contentDescriptionExceedsMaxBadgeNumberRes;
        state2.isVisible = Boolean.valueOf(state.isVisible == null || state.isVisible.booleanValue());
        state2.maxCharacterCount = state.maxCharacterCount == -2 ? typedArrayGenerateTypedArray.getInt(R.styleable.Badge_maxCharacterCount, 4) : state.maxCharacterCount;
        if (state.number != -2) {
            state2.number = state.number;
        } else {
            int i4 = R.styleable.Badge_number;
            if (typedArrayGenerateTypedArray.hasValue(i4)) {
                state2.number = typedArrayGenerateTypedArray.getInt(i4, 0);
            } else {
                state2.number = -1;
            }
        }
        state2.backgroundColor = Integer.valueOf(state.backgroundColor == null ? readColorFromAttributes(context, typedArrayGenerateTypedArray, R.styleable.Badge_backgroundColor) : state.backgroundColor.intValue());
        if (state.badgeTextColor != null) {
            state2.badgeTextColor = state.badgeTextColor;
        } else {
            int i5 = R.styleable.Badge_badgeTextColor;
            if (typedArrayGenerateTypedArray.hasValue(i5)) {
                state2.badgeTextColor = Integer.valueOf(readColorFromAttributes(context, typedArrayGenerateTypedArray, i5));
            } else {
                state2.badgeTextColor = Integer.valueOf(new TextAppearance(context, R.style.TextAppearance_MaterialComponents_Badge).getTextColor().getDefaultColor());
            }
        }
        state2.badgeGravity = Integer.valueOf(state.badgeGravity == null ? typedArrayGenerateTypedArray.getInt(R.styleable.Badge_badgeGravity, 8388661) : state.badgeGravity.intValue());
        state2.horizontalOffsetWithoutText = Integer.valueOf(state.horizontalOffsetWithoutText == null ? typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffset, 0) : state.horizontalOffsetWithoutText.intValue());
        state2.verticalOffsetWithoutText = Integer.valueOf(state.verticalOffsetWithoutText == null ? typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffset, 0) : state.verticalOffsetWithoutText.intValue());
        state2.horizontalOffsetWithText = Integer.valueOf(state.horizontalOffsetWithText == null ? typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffsetWithText, state2.horizontalOffsetWithoutText.intValue()) : state.horizontalOffsetWithText.intValue());
        state2.verticalOffsetWithText = Integer.valueOf(state.verticalOffsetWithText == null ? typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffsetWithText, state2.verticalOffsetWithoutText.intValue()) : state.verticalOffsetWithText.intValue());
        state2.additionalHorizontalOffset = Integer.valueOf(state.additionalHorizontalOffset == null ? 0 : state.additionalHorizontalOffset.intValue());
        state2.additionalVerticalOffset = Integer.valueOf(state.additionalVerticalOffset != null ? state.additionalVerticalOffset.intValue() : 0);
        typedArrayGenerateTypedArray.recycle();
        if (state.numberLocale == null) {
            state2.numberLocale = Build.VERSION.SDK_INT >= 24 ? Locale.getDefault(Locale.Category.FORMAT) : Locale.getDefault();
        } else {
            state2.numberLocale = state.numberLocale;
        }
        this.overridingState = state;
    }

    private TypedArray generateTypedArray(Context context, @XmlRes int i, @AttrRes int i2, @StyleRes int i3) {
        AttributeSet attributeSet;
        int styleAttribute;
        if (i != 0) {
            AttributeSet drawableXml = DrawableUtils.parseDrawableXml(context, i, BADGE_RESOURCE_TAG);
            styleAttribute = drawableXml.getStyleAttribute();
            attributeSet = drawableXml;
        } else {
            attributeSet = null;
            styleAttribute = 0;
        }
        return ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.Badge, i2, styleAttribute == 0 ? i3 : styleAttribute, new int[0]);
    }

    private static int readColorFromAttributes(Context context, @NonNull TypedArray typedArray, @StyleableRes int i) {
        return MaterialResources.getColorStateList(context, typedArray, i).getDefaultColor();
    }

    public final void clearNumber() {
        this.overridingState.number = -1;
        this.currentState.number = -1;
    }

    @Dimension(unit = 1)
    public final int getAdditionalHorizontalOffset() {
        return this.currentState.additionalHorizontalOffset.intValue();
    }

    @Dimension(unit = 1)
    public final int getAdditionalVerticalOffset() {
        return this.currentState.additionalVerticalOffset.intValue();
    }

    public final int getAlpha() {
        return this.currentState.alpha;
    }

    @ColorInt
    public final int getBackgroundColor() {
        return this.currentState.backgroundColor.intValue();
    }

    public final int getBadgeGravity() {
        return this.currentState.badgeGravity.intValue();
    }

    @ColorInt
    public final int getBadgeTextColor() {
        return this.currentState.badgeTextColor.intValue();
    }

    @StringRes
    public final int getContentDescriptionExceedsMaxBadgeNumberStringResource() {
        return this.currentState.contentDescriptionExceedsMaxBadgeNumberRes;
    }

    public final CharSequence getContentDescriptionNumberless() {
        return this.currentState.contentDescriptionNumberless;
    }

    @PluralsRes
    public final int getContentDescriptionQuantityStrings() {
        return this.currentState.contentDescriptionQuantityStrings;
    }

    @Dimension(unit = 1)
    public final int getHorizontalOffsetWithText() {
        return this.currentState.horizontalOffsetWithText.intValue();
    }

    @Dimension(unit = 1)
    public final int getHorizontalOffsetWithoutText() {
        return this.currentState.horizontalOffsetWithoutText.intValue();
    }

    public final int getMaxCharacterCount() {
        return this.currentState.maxCharacterCount;
    }

    public final int getNumber() {
        return this.currentState.number;
    }

    public final Locale getNumberLocale() {
        return this.currentState.numberLocale;
    }

    public final State getOverridingState() {
        return this.overridingState;
    }

    @Dimension(unit = 1)
    public final int getVerticalOffsetWithText() {
        return this.currentState.verticalOffsetWithText.intValue();
    }

    @Dimension(unit = 1)
    public final int getVerticalOffsetWithoutText() {
        return this.currentState.verticalOffsetWithoutText.intValue();
    }

    public final boolean hasNumber() {
        return this.currentState.number != -1;
    }

    public final boolean isVisible() {
        return this.currentState.isVisible.booleanValue();
    }

    public final void setAdditionalHorizontalOffset(@Dimension(unit = 1) int i) {
        this.overridingState.additionalHorizontalOffset = Integer.valueOf(i);
        this.currentState.additionalHorizontalOffset = Integer.valueOf(i);
    }

    public final void setAdditionalVerticalOffset(@Dimension(unit = 1) int i) {
        this.overridingState.additionalVerticalOffset = Integer.valueOf(i);
        this.currentState.additionalVerticalOffset = Integer.valueOf(i);
    }

    public final void setAlpha(int i) {
        this.overridingState.alpha = i;
        this.currentState.alpha = i;
    }

    public final void setBackgroundColor(@ColorInt int i) {
        this.overridingState.backgroundColor = Integer.valueOf(i);
        this.currentState.backgroundColor = Integer.valueOf(i);
    }

    public final void setBadgeGravity(int i) {
        this.overridingState.badgeGravity = Integer.valueOf(i);
        this.currentState.badgeGravity = Integer.valueOf(i);
    }

    public final void setBadgeTextColor(@ColorInt int i) {
        this.overridingState.badgeTextColor = Integer.valueOf(i);
        this.currentState.badgeTextColor = Integer.valueOf(i);
    }

    public final void setContentDescriptionExceedsMaxBadgeNumberStringResource(@StringRes int i) {
        this.overridingState.contentDescriptionExceedsMaxBadgeNumberRes = i;
        this.currentState.contentDescriptionExceedsMaxBadgeNumberRes = i;
    }

    public final void setContentDescriptionNumberless(CharSequence charSequence) {
        this.overridingState.contentDescriptionNumberless = charSequence;
        this.currentState.contentDescriptionNumberless = charSequence;
    }

    public final void setContentDescriptionQuantityStringsResource(@PluralsRes int i) {
        this.overridingState.contentDescriptionQuantityStrings = i;
        this.currentState.contentDescriptionQuantityStrings = i;
    }

    public final void setHorizontalOffsetWithText(@Dimension(unit = 1) int i) {
        this.overridingState.horizontalOffsetWithText = Integer.valueOf(i);
        this.currentState.horizontalOffsetWithText = Integer.valueOf(i);
    }

    public final void setHorizontalOffsetWithoutText(@Dimension(unit = 1) int i) {
        this.overridingState.horizontalOffsetWithoutText = Integer.valueOf(i);
        this.currentState.horizontalOffsetWithoutText = Integer.valueOf(i);
    }

    public final void setMaxCharacterCount(int i) {
        this.overridingState.maxCharacterCount = i;
        this.currentState.maxCharacterCount = i;
    }

    public final void setNumber(int i) {
        this.overridingState.number = i;
        this.currentState.number = i;
    }

    public final void setNumberLocale(Locale locale) {
        this.overridingState.numberLocale = locale;
        this.currentState.numberLocale = locale;
    }

    public final void setVerticalOffsetWithText(@Dimension(unit = 1) int i) {
        this.overridingState.verticalOffsetWithText = Integer.valueOf(i);
        this.currentState.verticalOffsetWithText = Integer.valueOf(i);
    }

    public final void setVerticalOffsetWithoutText(@Dimension(unit = 1) int i) {
        this.overridingState.verticalOffsetWithoutText = Integer.valueOf(i);
        this.currentState.verticalOffsetWithoutText = Integer.valueOf(i);
    }

    public final void setVisible(boolean z) {
        this.overridingState.isVisible = Boolean.valueOf(z);
        this.currentState.isVisible = Boolean.valueOf(z);
    }

    public static final class State implements Parcelable {
        private static final int BADGE_NUMBER_NONE = -1;
        public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator<State>() { // from class: com.google.android.material.badge.BadgeState.State.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            @NonNull
            public State createFromParcel(@NonNull Parcel parcel) {
                return new State(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            @NonNull
            public State[] newArray(int i) {
                return new State[i];
            }
        };
        private static final int NOT_SET = -2;

        @Dimension(unit = 1)
        private Integer additionalHorizontalOffset;

        @Dimension(unit = 1)
        private Integer additionalVerticalOffset;
        private int alpha;

        @ColorInt
        private Integer backgroundColor;
        private Integer badgeGravity;

        @XmlRes
        private int badgeResId;

        @ColorInt
        private Integer badgeTextColor;

        @StringRes
        private int contentDescriptionExceedsMaxBadgeNumberRes;

        @Nullable
        private CharSequence contentDescriptionNumberless;

        @PluralsRes
        private int contentDescriptionQuantityStrings;

        @Dimension(unit = 1)
        private Integer horizontalOffsetWithText;

        @Dimension(unit = 1)
        private Integer horizontalOffsetWithoutText;
        private Boolean isVisible;
        private int maxCharacterCount;
        private int number;
        private Locale numberLocale;

        @Dimension(unit = 1)
        private Integer verticalOffsetWithText;

        @Dimension(unit = 1)
        private Integer verticalOffsetWithoutText;

        public State() {
            this.alpha = 255;
            this.number = -2;
            this.maxCharacterCount = -2;
            this.isVisible = Boolean.TRUE;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            parcel.writeInt(this.badgeResId);
            parcel.writeSerializable(this.backgroundColor);
            parcel.writeSerializable(this.badgeTextColor);
            parcel.writeInt(this.alpha);
            parcel.writeInt(this.number);
            parcel.writeInt(this.maxCharacterCount);
            CharSequence charSequence = this.contentDescriptionNumberless;
            parcel.writeString(charSequence == null ? null : charSequence.toString());
            parcel.writeInt(this.contentDescriptionQuantityStrings);
            parcel.writeSerializable(this.badgeGravity);
            parcel.writeSerializable(this.horizontalOffsetWithoutText);
            parcel.writeSerializable(this.verticalOffsetWithoutText);
            parcel.writeSerializable(this.horizontalOffsetWithText);
            parcel.writeSerializable(this.verticalOffsetWithText);
            parcel.writeSerializable(this.additionalHorizontalOffset);
            parcel.writeSerializable(this.additionalVerticalOffset);
            parcel.writeSerializable(this.isVisible);
            parcel.writeSerializable(this.numberLocale);
        }

        public State(@NonNull Parcel parcel) {
            this.alpha = 255;
            this.number = -2;
            this.maxCharacterCount = -2;
            this.isVisible = Boolean.TRUE;
            this.badgeResId = parcel.readInt();
            this.backgroundColor = (Integer) parcel.readSerializable();
            this.badgeTextColor = (Integer) parcel.readSerializable();
            this.alpha = parcel.readInt();
            this.number = parcel.readInt();
            this.maxCharacterCount = parcel.readInt();
            this.contentDescriptionNumberless = parcel.readString();
            this.contentDescriptionQuantityStrings = parcel.readInt();
            this.badgeGravity = (Integer) parcel.readSerializable();
            this.horizontalOffsetWithoutText = (Integer) parcel.readSerializable();
            this.verticalOffsetWithoutText = (Integer) parcel.readSerializable();
            this.horizontalOffsetWithText = (Integer) parcel.readSerializable();
            this.verticalOffsetWithText = (Integer) parcel.readSerializable();
            this.additionalHorizontalOffset = (Integer) parcel.readSerializable();
            this.additionalVerticalOffset = (Integer) parcel.readSerializable();
            this.isVisible = (Boolean) parcel.readSerializable();
            this.numberLocale = (Locale) parcel.readSerializable();
        }
    }
}
