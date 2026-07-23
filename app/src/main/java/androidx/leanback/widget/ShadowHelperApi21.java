package androidx.leanback.widget;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi(21)
class ShadowHelperApi21 {
    public static final ViewOutlineProvider sOutlineProvider = new ViewOutlineProvider() { // from class: androidx.leanback.widget.ShadowHelperApi21.1
        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            outline.setRect(0, 0, view.getWidth(), view.getHeight());
            outline.setAlpha(1.0f);
        }
    };

    public static class ShadowImpl {
        public float mFocusedZ;
        public float mNormalZ;
        public View mShadowContainer;
    }

    private ShadowHelperApi21() {
    }

    public static Object addDynamicShadow(View view, float f, float f2, int i) {
        if (i > 0) {
            RoundedRectHelperApi21.setClipToRoundedOutline(view, true, i);
        } else {
            view.setOutlineProvider(sOutlineProvider);
        }
        ShadowImpl shadowImpl = new ShadowImpl();
        shadowImpl.mShadowContainer = view;
        shadowImpl.mNormalZ = f;
        shadowImpl.mFocusedZ = f2;
        view.setZ(f);
        return shadowImpl;
    }

    public static void setShadowFocusLevel(Object obj, float f) {
        ShadowImpl shadowImpl = (ShadowImpl) obj;
        View view = shadowImpl.mShadowContainer;
        float f2 = shadowImpl.mNormalZ;
        view.setZ(((shadowImpl.mFocusedZ - f2) * f) + f2);
    }
}
