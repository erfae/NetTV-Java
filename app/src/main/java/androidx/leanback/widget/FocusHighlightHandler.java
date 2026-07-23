package androidx.leanback.widget;

import android.view.View;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
interface FocusHighlightHandler {
    void onInitializeView(View view);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    void onItemFocused(View view, boolean z);
}
