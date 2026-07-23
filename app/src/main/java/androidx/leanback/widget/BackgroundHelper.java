package androidx.leanback.widget;

import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class BackgroundHelper {
    private BackgroundHelper() {
    }

    public static void setBackgroundPreservingAlpha(View view, Drawable drawable) {
        if (view.getBackground() != null) {
            drawable.setAlpha(view.getBackground().getAlpha());
        }
        view.setBackground(drawable);
    }
}
