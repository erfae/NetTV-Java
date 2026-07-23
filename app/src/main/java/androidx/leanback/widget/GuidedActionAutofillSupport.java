package androidx.leanback.widget;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface GuidedActionAutofillSupport {

    public interface OnAutofillListener {
        void onAutofill(View view);
    }

    void setOnAutofillListener(OnAutofillListener onAutofillListener);
}
