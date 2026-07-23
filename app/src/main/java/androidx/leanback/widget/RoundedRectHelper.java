package androidx.leanback.widget;

import android.view.View;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
final class RoundedRectHelper {
    private RoundedRectHelper() {
    }

    public static void setClipToRoundedOutline(View view, boolean z) {
        RoundedRectHelperApi21.setClipToRoundedOutline(view, z, view.getResources().getDimensionPixelSize(R.dimen.lb_rounded_rect_corner_radius));
    }
}
