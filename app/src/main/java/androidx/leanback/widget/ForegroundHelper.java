package androidx.leanback.widget;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class ForegroundHelper {
    private ForegroundHelper() {
    }

    public static Drawable getForeground(View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            return view.getForeground();
        }
        return null;
    }

    public static void setForeground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 23) {
            view.setForeground(drawable);
        }
    }
}
