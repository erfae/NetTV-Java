package androidx.transition;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes.dex */
interface ViewOverlayImpl {
    void add(@NonNull Drawable drawable);

    void remove(@NonNull Drawable drawable);
}
