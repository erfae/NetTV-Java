package androidx.leanback.transition;

import android.content.Context;
import androidx.annotation.RestrictTo;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class LeanbackTransitionHelper {
    private LeanbackTransitionHelper() {
    }

    public static Object loadTitleInTransition(Context context) {
        return TransitionHelper.loadTransition(context, R.transition.lb_title_in);
    }

    public static Object loadTitleOutTransition(Context context) {
        return TransitionHelper.loadTransition(context, R.transition.lb_title_out);
    }
}
