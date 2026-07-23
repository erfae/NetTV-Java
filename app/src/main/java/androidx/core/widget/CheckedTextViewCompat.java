package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.CheckedTextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public final class CheckedTextViewCompat {
    private static final String TAG = "CheckedTextViewCompat";

    public static class Api14Impl {
        private static Field sCheckMarkDrawableField;
        private static boolean sResolved;

        private Api14Impl() {
        }
    }

    @RequiresApi(16)
    public static class Api16Impl {
        private Api16Impl() {
        }
    }

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }
    }

    private CheckedTextViewCompat() {
    }

    @Nullable
    public static Drawable getCheckMarkDrawable(@NonNull CheckedTextView checkedTextView) {
        return checkedTextView.getCheckMarkDrawable();
    }

    @Nullable
    public static ColorStateList getCheckMarkTintList(@NonNull CheckedTextView checkedTextView) {
        return checkedTextView.getCheckMarkTintList();
    }

    @Nullable
    public static PorterDuff.Mode getCheckMarkTintMode(@NonNull CheckedTextView checkedTextView) {
        return checkedTextView.getCheckMarkTintMode();
    }

    public static void setCheckMarkTintList(@NonNull CheckedTextView checkedTextView, @Nullable ColorStateList colorStateList) {
        checkedTextView.setCheckMarkTintList(colorStateList);
    }

    public static void setCheckMarkTintMode(@NonNull CheckedTextView checkedTextView, @Nullable PorterDuff.Mode mode) {
        checkedTextView.setCheckMarkTintMode(mode);
    }
}
