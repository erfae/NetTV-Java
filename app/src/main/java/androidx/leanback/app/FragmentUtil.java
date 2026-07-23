package androidx.leanback.app;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
class FragmentUtil {
    private FragmentUtil() {
    }

    public static Context getContext(Fragment fragment) {
        return Build.VERSION.SDK_INT >= 23 ? fragment.getContext() : fragment.getActivity();
    }
}
