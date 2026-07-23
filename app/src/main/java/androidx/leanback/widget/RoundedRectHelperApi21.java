package androidx.leanback.widget;

import android.graphics.Outline;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi(21)
class RoundedRectHelperApi21 {
    private static final int MAX_CACHED_PROVIDER = 32;
    private static SparseArray<ViewOutlineProvider> sRoundedRectProvider;

    public static final class RoundedRectOutlineProvider extends ViewOutlineProvider {
        private int mRadius;

        public RoundedRectOutlineProvider(int i) {
            this.mRadius = i;
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), this.mRadius);
            outline.setAlpha(1.0f);
        }
    }

    private RoundedRectHelperApi21() {
    }

    public static void setClipToRoundedOutline(View view, boolean z, int i) {
        if (z) {
            if (sRoundedRectProvider == null) {
                sRoundedRectProvider = new SparseArray<>();
            }
            ViewOutlineProvider roundedRectOutlineProvider = sRoundedRectProvider.get(i);
            if (roundedRectOutlineProvider == null) {
                roundedRectOutlineProvider = new RoundedRectOutlineProvider(i);
                if (sRoundedRectProvider.size() < 32) {
                    sRoundedRectProvider.put(i, roundedRectOutlineProvider);
                }
            }
            view.setOutlineProvider(roundedRectOutlineProvider);
        } else {
            view.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        }
        view.setClipToOutline(z);
    }
}
